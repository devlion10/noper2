function jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
                      detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo) {
    if(jusoFlag.match('jusoBtn')) {
        const tFlag = jusoFlag.replace('jusoBtn', '');
        if(tFlag === '') {
            document.getElementById('gmnaddr').value = roadFullAddr;
            document.getElementById('gmaddr').value = jibunAddr;
            document.getElementById('gmcsah').value = mtYn;
        } else {
            document.getElementById('gcnaddr').value = roadFullAddr;
            document.getElementById('gcaddr').value = jibunAddr;
        }
    } else if(jusoFlag.match('resiJusoBtn')) {
        const tFlag = jusoFlag.replace('resiJusoBtn', '');
        document.getElementById('resiNewAddress' + tFlag).value = roadFullAddr;
        document.getElementById('resiAddress' + tFlag).value = jibunAddr;
    }
}

function jusoClick(el) {
    el.addEventListener('click', (e) => {
        jusoFlag = e.target.id;
        const w = window.open('/common/juso', 'juso', 'width=500px,height=500px');
        w.focus();
    })
}

let jusoFlag = '';
let sd = '';
// 각자 화면 개발할 때의
const type = {
    type: 'viobldmng',
    fileName: 750,
    size: 200
};

function clearDF() {
    document.getElementById('illegalConstruction').querySelectorAll('*').forEach((el) => {
        if((el.localName === 'input' && !(el.type === 'file' || el.classList.contains('ag-input-field-input'))) || el.localName === 'select') {
            el.removeAttribute('required');
            el.setAttribute('disabled', '');
        }
    })
    document.getElementById('unauthorizedChange').querySelectorAll('*').forEach((el) => {
        if((el.localName === 'input' && !(el.type === 'file' || el.classList.contains('ag-input-field-input'))) || el.localName === 'select') {
            el.removeAttribute('required');
            el.setAttribute('disabled', '');
        }
    })
    document.getElementById('etc').querySelectorAll('*').forEach((el) => {
        if((el.localName === 'input' && !(el.type === 'file' || el.classList.contains('ag-input-field-input'))) || el.localName === 'select') {
            el.removeAttribute('required');
            el.setAttribute('disabled', '');
        }
    })
    document.getElementById('noper').querySelectorAll('*').forEach((el) => {
        if((el.localName === 'input' && !(el.type === 'file' || el.classList.contains('ag-input-field-input'))) || el.localName === 'select') {
            el.removeAttribute('required');
            el.setAttribute('disabled', '');
        }
    })
}

async function uploaderInit(uploader, parentKey) {
    uploader.parentKey = parentKey;
    await uploader.loadFiles();
    uploader.getFiles().forEach(file=>{
        let $link = $("<a>")
            .text(`${file.name} (${file.vsize})`)
            .attr("title", file.name)
            .attr("href", `${file.name}`)
            .data("fseq", file.fseq)
            .data("seq", file.seq)
            .on("click", (e)=>{
                e.preventDefault();
                let seq = $(e.target).data("seq");
                let fseq =$(e.target).data("fseq");

                if (file.type.match(/jpg|jpeg|png|gif/ig)) {
                    return $(`<a onclick='' href='javascript:;'>${file.name}</a>`)
                        .on("click", uploader.imgPreview(file))[0];
                } else {
                    uploader.downloadFile(file);
                }
            });
        const $deleteLink = $(`<a href="javascript:;" class="ms-2"><i class='fa fa-trash'></i></a>`);
        $deleteLink.on("click", (e) => {
            $(e.target).closest("li").remove()
            uploader.deleteFiles(file)
        });
        $("<li class='list-inline-item'>")
            .append($link)
            .append($deleteLink)
            .appendTo($(`#files${uploader.flag}`));
    });
    uploader.clearFiles()
}

async function selectDisplay(target) {
    clearDF();
    switch (target) {
        case '위법시공 건축물':
            document.getElementById('illegalConstruction').style.display = 'block'
            document.getElementById('unauthorizedChange').style.display = 'none'
            document.getElementById('etc').style.display = 'none'
            document.getElementById('noper').style.display = 'none'
            document.getElementById('illegalConstruction').querySelectorAll('*').forEach((el) => {
                if((el.localName === 'input' && !(el.type === 'file' || el.classList.contains('ag-input-field-input'))) || el.localName === 'select') {
                    el.removeAttribute('disabled');
                    el.setAttribute('required', '');
                }
            })
            break
        case '무단 용도변경 건축물':
            document.getElementById('illegalConstruction').style.display = 'none'
            document.getElementById('unauthorizedChange').style.display = 'block'
            document.getElementById('etc').style.display = 'none'
            document.getElementById('noper').style.display = 'none'
            document.getElementById('unauthorizedChange').querySelectorAll('*').forEach((el) => {
                if((el.localName === 'input' && !(el.type === 'file' || el.classList.contains('ag-input-field-input'))) || el.localName === 'select') {
                    el.removeAttribute('disabled');
                    el.setAttribute('required', '');
                }
            })
            break;
        case '기타 위반건축물':
            document.getElementById('illegalConstruction').style.display = 'none'
            document.getElementById('unauthorizedChange').style.display = 'none'
            document.getElementById('etc').style.display = 'block'
            document.getElementById('noper').style.display = 'none'
            document.getElementById('etc').querySelectorAll('*').forEach((el) => {
                if((el.localName === 'input' && !(el.type === 'file' || el.classList.contains('ag-input-field-input'))) || el.localName === 'select') {
                    el.removeAttribute('disabled');
                    el.setAttribute('required', '');
                }
            })
            break;
        case '무허가 건축물':
            document.getElementById('illegalConstruction').style.display = 'none'
            document.getElementById('unauthorizedChange').style.display = 'none'
            document.getElementById('etc').style.display = 'noe'
            document.getElementById('noper').style.display = 'block'
            document.getElementById('noper').querySelectorAll('*').forEach((el) => {
                if((el.localName === 'input' && !(el.type === 'file' || el.classList.contains('ag-input-field-input'))) || el.localName === 'select') {
                    el.removeAttribute('disabled');
                    el.setAttribute('required', '');
                }
            })
            break;
    }
    sd = target;
}

document.addEventListener('DOMContentLoaded', async () => {
    let resiSource = $('#resi-template').html();
    let resiTemplate = Handlebars.compile(resiSource);
    let impoSource = $('#impo-template').html();
    let impoTemplate = Handlebars.compile(impoSource);
    let collSource = $('#coll-template').html();
    let collTemplate = Handlebars.compile(collSource);

    let resiCount = 1;
    let impoCount = 1;
    let collCount = 1;

    function elementAdd (type) {
        switch (type) {
            case 'resi':
                const resiRow = $('#resiBody').append(resiTemplate(resiCount++))[0];
                keyEventUtil.onlyString(resiRow)
                break;
            case 'impo':
                const impoRow = $('#impoBody').append(impoTemplate(impoCount++))[0];
                keyEventUtil.onlyNumber(impoRow);
                keyEventUtil.numberAmount(impoRow);
                keyEventUtil.numberAmountSumSource(impoRow, 'impoTotal');
                break;
            case 'coll':
                const collRow = $('#collBody').append(collTemplate(collCount++))[0];
                keyEventUtil.onlyNumber(collRow);
                keyEventUtil.numberAmount(collRow);
                keyEventUtil.numberAmountSumSource(collRow, 'collTotal');
                break;
        }
    }

    if(document.getElementById('resiCount')) {
        resiCount = Number(document.getElementById('resiCount').value) + 1
    } else {
        elementAdd('resi')
    }
    if(document.getElementById('impoCount')) {
        impoCount = Number(document.getElementById('impoCount').value)+ 1
    } else {
        elementAdd('impo')
    }
    if(document.getElementById('collCount')) {
        collCount = Number(document.getElementById('collCount').value) + 1;
    } else {
        elementAdd('coll');
    }

    document.querySelectorAll('button[name="jusoBtn"]').forEach(el => {
        jusoClick(el);
    });

    type.modalId = 'modal-1'
    const uploader1 = new snFileUpload.Uploader(`file1`, type);
    type.modalId = 'modal-2'
    const uploader2 = new snFileUpload.Uploader(`file2`, type);
    type.modalId = 'modal-3'
    const uploader3 = new snFileUpload.Uploader(`file3`, type);
    type.modalId = 'modal-4'
    const uploader4 = new snFileUpload.Uploader(`file4`, type);
    type.modalId = 'modal-5'
    const actionDetail = new snFileUpload.Uploader(`file5`, type);

    uploader1.flag = '1';
    uploader2.flag = '2';
    uploader3.flag = '3';
    uploader4.flag = '4';
    actionDetail.flag = '5';

    keyEventUtil.setInputTypeByForm('violatedAdd');

    /*
    * gmseqco 가 null 이 아니면 파일 로드
    * null 이면 파일 목록 리스트 숨기기
    */
    if (document.getElementById('gmseqco') !== null) {
        const parentKey = `${document.getElementById('gmskk').value}-${document.getElementById('gmdjgb').value === '등재' ? 1 : 2}-${document.getElementById('gmseqco').value}`;
        await uploaderInit(uploader1, parentKey);
        await uploaderInit(uploader2, parentKey);
        await uploaderInit(uploader3, parentKey);
        await uploaderInit(uploader4, parentKey);
        await uploaderInit(actionDetail, parentKey);
    } else {
        document.querySelectorAll('.files').forEach(e => {
            e.style.display = 'none'
        })
    }

    document.getElementById('gmtype').addEventListener('change', async (e) => {
        const target = e.target.value
        await selectDisplay(target);
    });

    document.getElementById('list').addEventListener('click', () => {
        const a = document.createElement('a')
        a.href = '/viobldmng/list'
        a.click()
    })
    if (new URL(location.href).pathname === '/viobldmng/modify') {
        document.getElementById('allDownload5').addEventListener('click', () => {
            actionDetail.downloadAllFile()
        })
    }

    document.getElementById('violatedAdd').addEventListener('submit', (e) => {
        e.preventDefault();
        e.stopPropagation();
        document.querySelectorAll('[readonly]').forEach((el) => {
            if(el.value === '') {
                el.classList.remove('is-valid');
                el.classList.add('is-invalid')
            } else {
                el.classList.remove('is-invalid');
                el.classList.add('is-valid')
            }
        })
        if (document.getElementById('violatedAdd').checkValidity() === false) return false;
        $('#violatedAdd').find('[name=impoAmount]').toArray().forEach((el) => { el.value = el.value.replaceAll(',', ''); })
        $('#violatedAdd').find('[name=collAmount]').toArray().forEach((el) => { el.value = el.value.replaceAll(',', ''); })
        $.post('/api/viobldmng/update', $('#violatedAdd').serializeObject(), (gmskk) => {
            const sd = document.getElementById('gmtype').value;
            switch (sd) {
                case '무단 용도변경 건축물':
                    uploader1.parentKey = gmskk;
                    uploader1.uploadFiles(false);
                    break;
                case '위법시공 건축물':
                    uploader2.parentKey = gmskk;
                    uploader2.uploadFiles(false);
                    break
                case '기타 위반건축물':
                    uploader3.parentKey = gmskk;
                    uploader3.uploadFiles(false);
                    break;
                case '무허가 건축물':
                    uploader4.parentKey = gmskk;
                    uploader4.uploadFiles(false);
                    break;
            }
            actionDetail.parentKey = gmskk;
            actionDetail.uploadFiles(false);
        })
        snAlert.alert('저장되었습니다.').then(() => {location.href = '/viobldmng/list'});

    })

    document.getElementById('resiAdd').addEventListener('click', (e) => {
        elementAdd('resi');
    })

    document.getElementById('impoAdd').addEventListener('click', (e) => {
        elementAdd('impo');
    })

    document.getElementById('collAdd').addEventListener('click', (e) => {
        elementAdd('coll');
    })
});