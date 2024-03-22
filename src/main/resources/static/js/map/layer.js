
//레이어 정의 네임 스페이스
var LAYER={

        addWmsImgLayer:function(layerNm, visible){
                 console.info(MAP);

                //wms 벡터소스 변수 정의->Using TileWMS, label repeated probelms occurs, so change Tile to Image
                 var wmsSource= new ol.source.ImageWMS({
                                          url: MAP.geoserverUrl+'/noper2/wms',
                                          //url: MAP.geoserverUrl+'/rcic/wms',
                                          params: {
                                                     //'FORMAT': 'application/openlayers',
                                                     "FORMAT": "image/png",
                                                     "VERSION": "1.1.1",
                                                     "STYLES": '',
                                                     "LAYERS": layerNm,//"noper2:noper5174_1200_garo", // "noper2:noper5174_1200_garo",
                                                     //,"noper2:noper5174_1200_sero"
                                                     //"CQL_FILTER": obj.CQL_FILTER,
                                                     tiled: true,
                                                 },

                                          crossOrigin: 'anonymous',
                                          serverType: 'geoserver'
                                      })

                 //wms 벡터레이어 변수 정의->Using Tile, label repeated probelms occurs, so change Tile to Image
                 var wmsLayer = new ol.layer.Image({
                                        id: layerNm+"_id"
                                        ,visible: true
                                        //,declutter: true
                                        ,source: wmsSource
                 });


                debugger;
                 MAP.layers.wmsLayers[layerNm]=wmsLayer;
                 MAP._map.addLayer(wmsLayer);
                 MAP.layers.wmsLayers[layerNm].setVisible(visible)





        },


          setVisibleWmsImgLayer:function(layerNm, visible){

                 if(MAP.layers.wmsLayers[layerNm].a.visible===true){
                        MAP.layers.wmsLayers[layerNm].setVisible(false);
                 }else{
                        MAP.layers.wmsLayers[layerNm].setVisible(true);
                 }



          }




}