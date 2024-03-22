const permit = (function () {
    let modalGrid1;
    let modalGrid2;
    let grid2Data = [];

    const gridOptions2 = {
        columnDefs: [
            { headerName: "이름", field: 'userName', headerCheckboxSelection: true, checkboxSelection: true},
            { headerName: "자치구", field: 'skkCd' },
            { headerName: "부서명", field: 'deptCd' },
            { headerName: "전화번호", field: 'telNo' },
        ],
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        // width: 160,
        sortable: true,
        editable: false,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
        minWidth: 100,
        pagination: true,
        paginationPageSize: 10,
        rowGroupPanelShow: 'always',
        rowSelection: 'multiple',
        domLayout: 'autoHeight',
        animateRows: true,
        //autoGroupColumnDef: autoGroupColumnDef,
        /*
        columnTypes: {
            addrColumn: {width: 150, filter:false},
        },
        */
        rowData: [],
        getRowId: (params) => { if(params.data.userId != null) { return params.data.userId } else { return '' }},

        onGridReady: function (event) {
            // 행 넓이 자동 조절
            event.api.sizeColumnsToFit();
        },
        autoGroupColumnDef: {
            headerCheckboxSelection: true,
            field: 'athlete',
            flex: 1,
            minWidth: 240,
            cellRendererParams: {
                checkbox: true,
            },
        },
        suppressRowClickSelection: true,
        suppressAggFuncInHeader: true,
    };

    const gridOptions3 = {
        columnDefs: [
            { headerName: "이름", field: 'userName', headerCheckboxSelection: true, checkboxSelection: true },
            { headerName: "자치구", field: 'skkCd' },
            { headerName: "부서명", field: 'deptCd' },
            { headerName: "전화번호", field: 'telNo' },
        ],
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        // width: 160,
        sortable: true,
        editable: false,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
        minWidth: 100,
        pagination: true,
        paginationPageSize: 10,
        rowGroupPanelShow: 'always',
        rowSelection: 'multiple',
        //autoGroupColumnDef: autoGroupColumnDef,
        /*
        columnTypes: {
            addrColumn: {width: 150, filter:false},
        },
        */
        rowData: [],
        getRowId: (params) => { if(params.data.userId != null) { return params.data.userId } else { return '' }},

        onGridReady: function (event) {
            // 행 넓이 자동 조절
            event.api.sizeColumnsToFit();
        },
        autoGroupColumnDef: {
            headerCheckboxSelection: true,
            field: 'athlete',
            flex: 1,
            minWidth: 240,
            cellRendererParams: {
                checkbox: true,
            },
        },
        suppressRowClickSelection: true,
        suppressAggFuncInHeader: true,
    };

    function getUserList() {
        $.get('/api/user/permit/user/perId/select/list', `perId=${$('#perId').val()}`, function (data) {
            $('#total > h3').text("Total "+data.length)
            gridOptions2.api.setRowData([]);
            gridOptions2.api.setRowData(JSON.parse(JSON.stringify(data)));
        })
    }

    document.addEventListener('DOMContentLoaded', () => {
        const gridDiv2 = document.querySelector('#myGrid2');
        const gridDiv3 = document.querySelector('#myGrid3');

        if(gridDiv2) {
            new agGrid.Grid(gridDiv2, gridOptions2);
            getUserList()
            $('#listCnt').change(function() {
                gridOptions2.api.paginationSetPageSize(Number(this.value));
            })
        }
        if(gridDiv3) {
            new agGrid.Grid(gridDiv3, gridOptions3);
            $.get('/api/user/permit/user/perId/select/list', `perId=${$('#perId').val()}`, function (data) {
                $('#total > h3').text("Total "+data.length)
                gridOptions3.api.setRowData([]);
                gridOptions3.api.setRowData(JSON.parse(JSON.stringify(data)));
            })
            $('#listCnt').change(function() {
                gridOptions3.api.paginationSetPageSize(Number(this.value));
            })
        }

    });

    $(document).ready(function () {
        let nameCk = false;
        let sToS = false;
        $('#goLst').click(function (e) {
            snAlert.confirm('목록으로 이동시 입력중인 권한 정보가 저장되지 않습니다. 취소하시겠습니까?').then((data) => {if(data.isConfirmed) location.href = '/admin/permit/list' });
        })

        $('#goLstInInfo').click(function (e) {
            location.href = '/admin/permit/list';
        })

        $('#cancel').click(function (e) {
            snAlert.confirm('취소시 입력중인 권한 정보가 저장되지 않습니다. 취소하시겠습니까?').then((data) => {if(data.isConfirmed) location.href = '/admin/permit/list'})
        })

        $('#delUserBtn').click(function (e) {
            snAlert.confirm('선택한 사용자의 권한을 삭제하시겠습니까?').then((data) => {
                const userId = [];
                gridOptions2.api.getSelectedRows().forEach(user => userId.push(user.userId));
                if(data.isConfirmed) {
                    $.get('/api/user/permit/user/perId/delete/list', { userId: userId }, function () {
                        snAlert.alert('삭제되었습니다.').then(() => {getUserList()});
                    });
                }
            })
        })

        $('#delBtn').click(function (e) {
            if($('#initFlag').val() === "Y") snAlert.alert('초기권한은 삭제할 수 없습니다.');
            else if(grid2Data.length > 0) snAlert.alert('권한이 부여된 사용자가 있는 경우 삭제할 수 없습니다. 사용자 제거후 삭제해주세요.');
            else {
                snAlert.confirm('권한 삭제시 복구할 수 없습니다. 삭제하시겠습니까?').then((data) => {
                    if(data.isConfirmed) {
                        $.post('/api/user/permit/delete', `perId=${$('#perId').val()}`, function () {
                            snAlert.alert('삭제되었습니다.').then(() => {location.href='/admin/permit/list'});
                        });
                    }
                })
            }
        })

        $('#modBtn').click(function (e) {
            if($('#initFlag').val() === "Y") snAlert.alert('초기권한은 수정할 수 없습니다.');
            else {
                const form = document.createElement("form");
                const userIdElement = document.createElement("input");
                form.appendChild(userIdElement);
                document.body.appendChild(form);
                userIdElement.type = 'text';
                userIdElement.name = 'perId';
                userIdElement.value = $('#perId').val();
                form.action = '/admin/permit/modify';
                form.method = 'post';
                form.submit();
            }
        })

        $('#addPermissionForm').submit(function (e) {
            e.preventDefault();
            let parents = "";
            const selectedNodeIds = $('#jstree').jstree('get_selected', true).map( function (node) {
                if(node.parents.length > 1) parents += parents.indexOf(node.parents[node.parents.length - 2]) === -1 ? node.parents[node.parents.length - 2] : '';
                return node.id
            });
            const perName = $('#perName');
            const checkVal = $('#checkVal');
            if(perName.val().length > 20 || perName.val().length <= 0) {
                checkVal.text(perName.val().length > 20 ? '20자 이내로 입력해주세요' : '권한명을 입력해주세요');
                nameCk = false;
                perName.addClass('is-invalid');
                perName.removeClass('is-valid');
            } else {
                $.get('/api/user/permit/check/name', `perName=${perName.val()}`, function (data) {
                    if(data || perName.val() === $('#oriName').val()) {
                        checkVal.text('사용 가능한 권한명 입니다.');
                        nameCk = true;
                        perName.removeClass('is-invalid');
                        perName.addClass('is-valid');
                    } else {
                        checkVal.text('이미 사용중인 권한명 입니다.');
                        nameCk = false;
                        perName.addClass('is-invalid');
                        perName.removeClass('is-valid');
                    }
                })
            }
            if(sToS) {
                sToS = false;
                return false;
            }
            if(selectedNodeIds.length <= 0) {
                snAlert.alert('권한을 선택해주세요.');
            } else if (!this.checkValidity() || !nameCk) {
                return false;
            } else {
                $.post($('#oriName').val() === '' ? '/api/user/permit/create' : '/api/user/permit/update', {
                    'perId': $('#perId').val(),
                    'perName': $('#perName').val(),
                    'perComment': $('#perComment').val(),
                    'menuIds': selectedNodeIds,
                    'parents': parents
                }, function () {
                    snAlert.alert('권한저장을 완료하였습니다.').then(() => {location.href = '/admin/permit/list'})
                })
            }
        });

        $('#rmUrPmt').click(function () {
            const userId = [];
            gridOptions3.api.getSelectedRows().forEach(d => userId.push(d.userId));
            snAlert.confirm('사용자의 권한을 회수하시겠습니까?').then((data) => {
                if(data.isConfirmed) {
                    $.post('/api/user/permit/user/remove', {
                        'userId': userId
                    }, function () {
                        snAlert.alert('회수되었습니다.').then(() => {location.reload()});
                    });
                }
            })
        })

        $('#perName').focusout(function () {
            sToS = true;
            $('#addPermissionForm').submit();
        })
    })

    return {

    };
})();

