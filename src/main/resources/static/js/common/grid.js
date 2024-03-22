const snGrid = (function($){
    function _exportCsv() {
        gridOptions.api.exportDataAsCsv();
    }
    return {
        exportCsv:_exportCsv
    }
})();