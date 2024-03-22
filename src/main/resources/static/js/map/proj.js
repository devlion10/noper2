// 좌표계 유틸
var crsUtil = {

    // coordinate 좌표 변환
    transformCoordinate: function(coordinate, oldCrs, newCrs) {        return ol.proj.transform(coordinate, oldCrs, newCrs);
    },

    // bbox 좌표 변환
    transformBbox: function(bbox, oldCrs, newCrs) {
        return ol.proj.transformExtent(bbox, oldCrs, newCrs);

    },

    // 좌표계 초기화
    initProj4: function() {

        for (var int = 0; int < this.crsList.length; int++) {

            var code = this.crsList[int].code;
            var proj = this.crsList[int].proj;
            proj4.defs(code, proj);

            var olProj = ol.proj.get(code);
            var newProj = new ol.proj.Projection({
                code: olProj.getCode(),
                units: olProj.getUnits(),
                extent: olProj.getExtent(),
                axisOrientation: 'enu'
            });
            ol.proj.addProjection(newProj);

        }

    },

    // 좌표계 정보 가져오기
    getProj4: function(code) {

        var projCode = null;
        for (var i = 0; i < this.crsList.length; i++) {
            if (this.crsList[i].code == code) {
                projCode = this.crsList[i].proj;
                break;
            }
        }
        return projCode;

    },

    // 좌표계 목록
    crsList : [
        {
            code: "EPSG:4326",
            proj: "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs"
        }, {
            code: "EPSG:4004",
            proj: "+proj=longlat +ellps=bessel +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"
        }, {
            code: "EPSG:4019",
            proj: "+proj=longlat +ellps=GRS80 +no_defs"
        }, {
            code: "EPSG:3857",
            proj: "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +no_defs"
        }, {
            code: "EPSG:32652",
            proj: "+proj=utm +zone=52 +ellps=WGS84 +datum=WGS84 +units=m +no_defs"
        }, {
            code: "EPSG:32651",
            proj: "+proj=utm +zone=51 +ellps=WGS84 +datum=WGS84 +units=m +no_defs"
        }, {
            code: "EPSG:2096",
            proj: "+proj=tmerc +lat_0=38 +lon_0=129 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"
        }, {
            code: "EPSG:2097",
            proj: "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs"
        }, {
            code: "EPSG:2098",
            proj: "+proj=tmerc +lat_0=38 +lon_0=125 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"
        }, {
            code: "EPSG:5173",
            proj: "+proj=tmerc +lat_0=38 +lon_0=125.0028902777778 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"
        }, {
            code: "EPSG:5174",
            proj: "+proj=tmerc +lat_0=38 +lon_0=127.0028902777778 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"
        }, {
            code: "EPSG:5175",
            proj: "+proj=tmerc +lat_0=38 +lon_0=127.0028902777778 +k=1 +x_0=200000 +y_0=550000 +ellps=bessel +units=m +no_defs  +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"
        }, {
            code: "EPSG:5176",
            proj: "+proj=tmerc +lat_0=38 +lon_0=129.0028902777778 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"
        }, {
            code: "EPSG:5177",
            proj: "+proj=tmerc +lat_0=38 +lon_0=131.0028902777778 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs  +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"
        }, {
            code: "EPSG:5178",
            proj: "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"
        }, {
            code: "EPSG:5179",
            proj: "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs"
        }, {
            code: "EPSG:5180",
            proj: "+proj=tmerc +lat_0=38 +lon_0=125 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs"
        }, {
            code: "EPSG:5181",
            proj: "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs"
        }, {
            code: "EPSG:5182",
            proj: "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=550000 +ellps=GRS80 +units=m +no_defs"
        }, {
            code: "EPSG:5183",
            proj: "+proj=tmerc +lat_0=38 +lon_0=129 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs"
        }, {
            code: "EPSG:5184",
            proj: "+proj=tmerc +lat_0=38 +lon_0=131 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs"
        }, {
            code: "EPSG:5185",
            proj: "+proj=tmerc +lat_0=38 +lon_0=125 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs"
        }, {
            code: "EPSG:5186",
            proj: "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs"
        }, {
            code: "EPSG:5187",
            proj: "+proj=tmerc +lat_0=38 +lon_0=129 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs"
        }, {
            code: "EPSG:5188",
            proj: "+proj=tmerc +lat_0=38 +lon_0=131 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs"
        }
    ]

};