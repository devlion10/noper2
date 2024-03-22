const snFileUpload = (() => {
    function Uploader(elementId, option) {
        // MicroModal Reset
        MicroModal.init({
            disableScroll: false,
            awaitCloseAnimation: true
        })
        let that = this;
        this.element = document.getElementById(elementId);
        this.parentKey = 0;
        this.flag = "";
        this.filesData= [];
        this.separator = option.type;
        this.validExtensions = "";
        this.fileCount = 0;
        this.fileSize = 0;
        this.maxSize = 0;
        this.option = option
        this.imgURL = '';
        this.modalId = option.modalId;

        let html = `
              <div class="ag-theme-alpine mb-1" style="height: 200px; width:100%;"></div>
              
              <div id='${this.modalId}' class="modal micromodal-slide" aria-hidden="true">
                <div class="modal__overlay" tabindex="-1" data-micromodal-close>
                  <div class="modal__container" role="dialog" aria-modal="true" aria-labelledby="modal-1-title">
                    <header class="modal__header">
                      <h2 class="modal__title text-center modal-1-title">
                      </h2>
                      <button class="modal__close ms-lg-auto" aria-label="Close modal" data-micromodal-close></button>
                    </header>
                    <main class="modal__content modal-1-content">
                    <div class="imageDiv">
                        <img class="imagePreview" src="" alt="이미지 미리보기" style="width: 500px; height: 300px">
                    </div>
                    </main>
                    <footer class="d-flex modal__footer justify-content-center">
                      <button type="button" class="modal__btn" data-micromodal-close aria-label="Close this dialog window">닫기</button>
                      <button type="button" class="modal__btn modal__btn-primary imgDown ml10">다운로드</button>
                    </footer>
                  </div>
                </div>
              </div>
            `;
        let uploadHtml = `
              <div class="d-flex">
                <small class="text-muted h6 extensions"></small>
                <div class="ms-lg-auto">
                    <button type="button" class="btn btn-sm btn-danger"><i class="fa-solid fa-minus"></i> 삭제</button>
                    <button type="button" class="btn btn-sm btn-primary"><i class="fa-regular fa-add"></i> 추가</button>
                </div>
                <input type="file" multiple style="display:none"/>
              </div>
              <small class="text-muted h6 d-flex"><p class="fileCount">0</p>개&nbsp;<p class="fileSize">0 B</p>&nbsp;추가됨</small>
              `
        let columnDefs;
        if (option.type === "ledgerLayout" || option.type === "ledgerGreenBeltLayout") {
            columnDefs = [
                {
                    headerName: "선택",
                    headerCheckboxSelection: true,
                    checkboxSelection: true,
                    width: 70,
                    cellStyle: {textAlign: "center"}
                },
                {headerName: "파일구분", hide: "true", field: 'fileFlag', width: 200, cellStyle: {textAlign: "center"}},
                {
                    headerName: "파일명",
                    field: 'name',
                    width: (option.fileName ?? 820),
                    cellStyle: {textAlign: "center"}
                },
                {
                    headerName: "파일사이즈(bytes)",
                    field: 'vsize',
                    width: (option.size ?? 200),
                    cellStyle: {textAlign: "center"}
                },
                {headerName: "등록일자", field: 'registTs', hide: "true", width: (option.size ?? 200), cellStyle: {textAlign: "center"}},
                {headerName: "gmskk", field: 'gsmkk', hide: "true"},
                {headerName: "gmdjgb", field: 'gmdjgb', hide: "true"},
                {headerName: "gmseqco", field: 'gmseqco', hide: "true"},
                {headerName: "admSeq", field: 'seq', hide: "true"},
                {headerName: "physicalFilename", field: 'physicalFilename', hide: "true"},
                {headerName: "filePath", field: 'filePath', hide: "true"},
                {headerName: "logicalFilename", field: 'logicalFilename', hide: "true"}
            ];
        } else if (option.type === "ownerChange") {
            columnDefs = [
                {
                    headerName: "선택",
                    headerCheckboxSelection: true,
                    checkboxSelection: true,
                    width: 70,
                    cellStyle: {textAlign: "center"}
                },
                {headerName: "파일구분", field: 'fileFlag', width: 200, cellStyle: {textAlign: "center"}, hide: "true"},
                {
                    headerName: "파일명",
                    field: 'name',
                    width: (option.fileName ?? 820),
                    cellStyle: {textAlign: "center"}
                },
                {
                    headerName: "파일사이즈(bytes)",
                    field: 'vsize',
                    width: (option.size ?? 200),
                    cellStyle: {textAlign: "center"}
                },
                {headerName: "등록일자", field: 'registTs', width: (option.size ?? 200), cellStyle: {textAlign: "center"}},
                {headerName: "gmskk", field: 'gsmkk', hide: "true"},
                {headerName: "gmdjgb", field: 'gmdjgb', hide: "true"},
                {headerName: "gmseqco", field: 'gmseqco', hide: "true"},
                {headerName: "physicalFilename", field: 'physicalFilename', hide: "true"},
                {headerName: "gmjilno", field: 'gmjilno', hide: "true"},
                {headerName: "suilno", field: 'suilno', hide: "true"},
                {headerName: "filePath", field: 'filePath', hide: "true"},
                {headerName: "fileSub", field: 'fileSub', hide: "true"},
                {headerName: "logicalFilename", field: 'logicalFilename', hide: "true"}
            ];
        } else {
            columnDefs = [
                {
                    headerName: "선택",
                    headerCheckboxSelection: true,
                    checkboxSelection: true,
                    width: 70,
                    cellStyle: {textAlign: "center"}
                },
                {headerName: "파일명", field: 'name', width: (option.fileName ?? 820)},
                {headerName: "파일크기", field: 'vsize', width: (option.size ?? 200)},
                {headerName: "physicalFilename", field: 'physicalFilename', hide: "true"},
                {headerName: "filePath", field: 'filePath', hide: "true"}
            ];
        }
        this.gridOptions = {
            columnDefs: columnDefs,
            rowData: [],
            rowSelection: 'multiple',
            overlayNoRowsTemplate: `
                <div onclick='alert(11)'>
                  이곳을 두번 클릭 또는 파일을 끌어다 놓으세요.
                </div>
              `,
        };

        let div = document.createElement("div");
        div.innerHTML = option.readOnly === 'Y' ? html : html + uploadHtml;
        /*
            페이지 로딩이 빠르면 잠깐이라도 나타날 수 있으니
            appendChild 하기 전에 style 추가
        */
        if (option.visible === true) {
            div.querySelectorAll('.ag-theme-alpine')[0].style.display = 'none'
        }
        this.element.appendChild(div);

        if (option.readOnly !== 'Y') {
            const fileExt = ["gif", "jpg", "jpeg", "tif", "dwg", "pdf", "bmp", "psd", "tqa", "png", "hwp", "hwpx", "xls", "xlsx"]
            const fileSize = 100
            this.validExtensions = fileExt
            this.maxSize = (fileSize * 1024 * 1024)
            this.element.querySelectorAll('.extensions')[0].innerText = `최대 ${bytesToSize(this.maxSize)} 제한 (허용 확장자: ${fileExt.join(', ')})`
        }

        const gridDiv = this.element.querySelector('div.ag-theme-alpine');
        new agGrid.Grid(gridDiv, this.gridOptions);

        if (option.readOnly !== 'Y') {
            this.element.addEventListener('dragover', function (e) {
                e.preventDefault();
            });
            this.element.addEventListener('dragenter', function (e) {
                e.dataTransfer.dropEffect = "copy";
            });
            this.element.addEventListener('drop', function (e) {
                e.preventDefault();
                const files = e.dataTransfer.files;
                addFiles(files);
            });
            // add file
            this.element.querySelector("button.btn-primary").addEventListener("click", () => {
                that.element.querySelector("input[type=file]").click();
            });

            // remove file
            this.element.querySelector("button.btn-danger").addEventListener("click", async () => {
                const rows = that.gridOptions.api.getSelectedRows();
                if (!rows || rows.length === 0) return;
                that.gridOptions.api.applyTransaction({remove: rows});
                // 선택한 파일 배열 값
                const fileSize = rows.map(f => f.size)
                // 삭제할 파일 사이즈 값
                const deleteFileSize = fileSize.reduce((acc, size) => acc + size, 0);
                that.element.querySelectorAll('.fileCount')[0].innerText = `${(that.fileCount - rows.length)}`
                that.element.querySelectorAll('.fileSize')[0].innerText = that.fileSize === deleteFileSize ? '0 B' : `${bytesToSize(that.fileSize - deleteFileSize)}`
                // 파일 개수 반영
                that.fileCount -= rows.length
                // 파일 사이즈 반영
                that.fileSize -= deleteFileSize
            });

            // change file
            this.element.querySelector("input[type=file]").addEventListener("change", (e) => {
                addFiles(e.target.files);
            })

        }
        // for debugger;
        window.grid = this.gridOptions;

        function addFiles(files) {
            if (files == null) return;

            let size = 0;
            // 파일 최대용량 체크해야함
            Array.from(files).forEach(file => {
                if (!checkFileExtension(file.name, that.validExtensions)) return snAlert.error('지원되지않는 확장자입니다.', `허용 확장자: ${that.validExtensions.join(', ')}`)
                // 파일 최대크기 되면 반환
                const inputFileSize = that.fileSize + size + file.size
                if (inputFileSize > that.maxSize) return snAlert.error('용량이 최대크기에 도달하였습니다.', '')

                size += file.size
                that.fileSize += file.size
                that.fileCount += 1

                if (file.size <= 0) return;
                that.gridOptions.api.applyTransaction({
                    add: [{
                        name: file.name,
                        type: file.type,
                        isClient: 'Y',
                        vsize: bytesToSize(file.size),
                        size: file.size,
                        file
                    }]
                });
            });
            that.element.querySelectorAll('.fileCount')[0].innerText = `${that.fileCount}`
            that.element.querySelectorAll('.fileSize')[0].innerText = `${bytesToSize(that.fileSize)}`
        }
    }
    // ["pdf", "doc", "txt", "jpg"]
    function checkFileExtension(filename, validExtensions) {
        const extension = filename.slice(((filename.lastIndexOf(".") - 1) >>> 0) + 2);
        return validExtensions.includes(extension.toLowerCase());
    }
    function bytesToSize(bytes) {
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB']
        if (bytes === 0) return 'n/a'
        const i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)), 10)
        if (i === 0) return `${bytes} ${sizes[i]}`
        return `${(bytes / (1024 ** i)).toFixed(1)} ${sizes[i]}`
    }

    Uploader.prototype.getFiles = function() {
        let files = [];
        if(this.separator === "ledgerSiteChk"){
            files = this.filesData;
            //debugger;
        }else{
            this.gridOptions.api.rowModel.forEachNode(m=>files.push(m.data));
        }
        return files;
    }
    Uploader.prototype.getFileSize = function() {
        let files = [];
        this.gridOptions.api.rowModel.forEachNode(m=>files.push(m.data));
        return files.map(f=>f.file.size).reduce((a,i)=>a+i, 0);
    }

    Uploader.prototype.getFileCount = function() {
        let files = [];
        this.gridOptions.api.rowModel.forEachNode(m=>files.push(m.data));
        return files.length;
    }

    Uploader.prototype.addFile = function (res) {
        if(this.separator !== "ledgerSiteChk"){
            this.fileSize += res.fileSize
            this.fileCount += 1
            this.gridOptions.api.applyTransaction({
                add:[{
                    fseq: res.fseq,
                    seq: res.seq,
                    name: res.logicalFilename,
                    isClient: res.isClient,
                    type: res.fileEnd,
                    vsize: `${bytesToSize(res.fileSize)}`,
                    size: res.fileSize,
                    fileFlag: res.fileFlag,
                    registTs: res.registTs,
                    gmskk: res.gmskk,
                    gmdjgb: res.gmdjgb,
                    gmseqco: res.gmseqco,
                    admSeq: res.seq,
                    physicalFilename: res.physicalFilename,
                    logicalFilename: res.logicalFilename,
                    filePath: res.filePath
                }]
            });
        }
    };
    Uploader.prototype.setFlag = function(flag) {
        this.flag = flag;
    }
    Uploader.prototype.clearFiles = function() {
        this.gridOptions.api.setRowData([]);
    }
    Uploader.prototype.deleteFiles = function(data) {
        if(data.chkilno != null){
            this.separator = "ledgerSiteChk";
            this.type = "ledgerSiteChk";
        }
        $.ajax({
            type: "DELETE",
            contentType: 'application/json',
            url: encodeURI(`/file/${this.separator}`),
            data: JSON.stringify(Array(data)),
        }).done(() => {
            if (this.option.readOnly !== 'Y' || !this.option.visible) {
                this.element.querySelectorAll('.fileSize')[0].innerText = this.fileSize === data.size ? '0 B' : `${bytesToSize(this.fileSize - data.size)}`
                this.element.querySelectorAll('.fileCount')[0].innerText = `${(this.fileCount - 1)}`;
            }
        });
    }
    Uploader.prototype.imgPreview = function(data) {
        const clickHandler = () => {
            const win = window.open('about:blank', '_blank', 'height=' + screen.height + ',width=' + screen.width + 'fullscreen=yes')
            const imgTag = document.createElement('img')
            imgTag.src = this.imgURL
            win.document.body.style.position = 'relative';
            imgTag.style = 'position: absolute; ' +
                           'top: 50%; left:50%;' +
                           'transform: translate(-50%, -50%);';
            win.document.body.append(imgTag)
        };
        $.ajax({
            url: encodeURI(`/file/download-file/`),
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            cache: false,
            xhrFields: {
                responseType: "blob",
            },
        }).then((response) => {
            const blob = new Blob([response], {type: 'application/octet-stream'});
            const imgURL = URL.createObjectURL(blob);
            const img = this.element.querySelectorAll('.imagePreview')[0]
            const imgTitle = this.element.querySelectorAll('.modal-1-title')[0]
            imgTitle.innerText = `${name}`
            img.src = imgURL;
            this.imgURL = imgURL;

            img.removeEventListener('click', clickHandler)
            img.addEventListener('click', clickHandler)
            MicroModal.show(`${this.modalId}`);
            //이벤트리스너 초기화
            console.info("이벤트리스너 초기화")
            //console.info(document.querySelector('.imgDown') .click[0].listener);


            this.element.querySelectorAll('.imgDown')[0].addEventListener('click', (e) => {
                const a = document.createElement('a')
                a.href = `${this.element.querySelectorAll('.imagePreview')[0].src}`
                if(data.name === null || data.name === undefined){
                    a.download = `${data.logicalFilename}`
                }else{
                    a.download = `${data.name}`
                }
                a.click()
            }, { once : true });
        });
    }
    Uploader.prototype.loadFiles = async function() {
        this.filesData = [];
        return $.ajax({
            type: "GET",
            url: encodeURI(`/file/${this.separator}/${this.parentKey}/${this.flag}`),
            success: (result) => {
                this.gridOptions.api.setRowData([]);
                this.fileSize = 0
                this.fileCount = 0
                result.forEach(f => {
                    if(f.flag === '1'){
                        f.fileFlag = '배치도';
                    }else if(f.flag === '2'){
                        f.fileFlag = '사진';
                    }
                    if(this.separator === "ledgerSiteChk"){
                        this.filesData.push(f);
                    }else{
                        this.addFile(f)
                        this.filesData.push(f);
                    }
                })
                if (this.option.readOnly !== 'Y' || !this.option.visible) {
                    this.element.querySelectorAll('.fileSize')[0].innerText = this.fileSize !== 0 ?`${bytesToSize(this.fileSize)}` : '0 B';
                    this.element.querySelectorAll('.fileCount')[0].innerText = `${this.fileCount}`
                }
            }
        })
    }

    Uploader.prototype.downloadFile = function (file) {
        $.ajax({
            url: encodeURI(`/file/download-file`),
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(file),
            cache: false,
            xhrFields: {
                responseType: "blob",
            },
        }).done((response) => {
            const blob = new Blob([response], {type: 'application/octet-stream'});
            const downloadUrl = URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = downloadUrl;
            if(file.name == undefined){
                a.download = file.logicalFilename;
            }else{
                a.download = file.name;
            }
            // add
            document.body.appendChild(a);
            a.click();
            // remove
            document.body.removeChild(a)
            URL.revokeObjectURL(downloadUrl);
        });
    };

    Uploader.prototype.downloadAllFile = function() {
        let gridData = this.getFiles().filter(file => file.isClient === 'N');
        if (gridData.length === 0) {
            gridData = this.filesData
        }
        $.ajax({
            url: encodeURI("/file/download-file/all"),
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                seq: this.parentKey,
                type: this.separator,
                grid: gridData
            }),
            cache: false,
            xhrFields: {
                responseType: "blob",
            },
            success: (res) => {
                const blob = new Blob([res], {type: 'application/zip'});
                const downloadUrl = URL.createObjectURL(blob);
                const a = document.createElement("a");
                a.href = downloadUrl;
                const today = new Date();
                a.download = `download-${moment().format('YYYY-MM-DD')}.zip`;

                // add
                document.body.appendChild(a);
                a.click();
                // remove
                document.body.removeChild(a)
                URL.revokeObjectURL(downloadUrl);
            }
        });
    }

    Uploader.prototype.uploadFiles = function(alert = true) {
        let files = [];
        this.gridOptions.api.rowModel.forEachNode(m => {
            if (m.data.isClient === 'Y') {
                files.push(m.data)
            }
        });
        // upload 구현
        const formData = new FormData();

        formData.append("seq", this.parentKey);
        formData.append("businessName", this.separator);
        formData.append("flag", this.flag);
        files.forEach(f => {
            formData.append("file", f.file);
        })

        if (files.length > 0) {
            const type = this.separator
            $.ajax({
                type: "POST",
                url: encodeURI(`/file/${type}/write`),
                processData: false,
                contentType: false,
                data: formData,
                success: () => {
                    alert === true
                        ? snAlert.success('업로드 성공')
                        : console.log('업로드 성공')
                }
            })
        }
        console.dir(files);
    }

    return {
        Uploader
    }
})()

const snExcelDownload = (() => {
    function excelDownload (promise, name){
        promise.done((response)=> {
            const createDownloadLink = () => {
                const blob = new Blob([response], {type: 'application/vnd.ms-excel'});
                const downloadUrl = URL.createObjectURL(blob);

                const a = document.createElement("a");
                a.href = downloadUrl;
                a.download = `${name}.xlsx`;
                // add
                document.body.appendChild(a);
                a.click();
                // remove
                document.body.removeChild(a)
                URL.revokeObjectURL(downloadUrl);
            }

            if (document.readyState === 'complete') {
                createDownloadLink();
            } else {
                window.addEventListener('load', createDownloadLink);
            }
        });
    }
    return {
        excelDownload
    }
})()
 // snExcelDownload.excelDownload($.ajax({
 //     url: '/download/excel',
 //     type: 'GET',
 //     cache: false,
 //     xhrFields: {
 //         responseType: "blob",
 //     },
 // }), 'fileName')
 // snExcelDownload.excelDownload(ajax요청영역, 파일이름)