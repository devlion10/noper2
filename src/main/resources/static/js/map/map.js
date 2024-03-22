

//지도객체 네임스페이스
var MAP={

        //geoserverUrl:'http://192.168.0.2:8080/geoserver',
        // geoserverUrl:'',
        //geoserverUrl:'http://geonworks.iptime.org:20004/geoserver',//rcic지오서버(개발)
        vworldKey : "856D1588-BE74-3C9B-8E3A-850338D678C4",
        _map : {},//전역 ol 맵 객체
        ///전역 레이ㅓ 정의
        layers:{
                  baseLayers:{},
                  wmsLayers:{},
                  wfsLayers:{}
        },//ol map 객체에 포함될 레이어들을 저장하는 객체, baselayer,wms, wfs 등등

        mapView :{},//mapView

        airImgWinRef:null,


        createMapClass: function() {
                console.info("CREATE MAP CLASS");
                // 기본이 될 Layer 저장
                // 레이어 (단일 또는 다중)
                this.layers.baseLayers={}
                this.layers.baseLayers["VWorld"] = new ol.layer.Tile({
                  title: "VWorld Gray Map",
                  visible: true,
                  id: "VWorld",
                  type: "base",
                  source: new ol.source.XYZ({
                    url: "http://api.vworld.kr/req/wmts/1.0.0/"+this.vworldKey+"/Base/{z}/{y}/{x}.png",
                    crossOrigin: "anonymous",
                  }),
                }),

                this.layers.baseLayers["OSM"] = new ol.layer.Tile({
                      source: new ol.source.OSM(),

               });

                /* ========================================================================== */
                /* =========================지오서버레이어================================================= */


                 /* =========================지오서버레이어================================================= */

                // 지도그리기
                this.mapView = new ol.View({
                    center: [14151646.114382012, 4497700.055161816],//5174
                    //center: [952903.5895988757, 1952229.2979214708],//5179
                    zoom: 14,
                    minZoom: 7,
                    maxZoom: 18
                    ,projection : 'EPSG:5174'
                });


                // 기본지도 그리기
                this._map = new ol.Map({
                    target: 'map',
                     layers :[
                                  // baseLayers['VWorld'], tmprVectorLayer
                                  //baseLayers를 제외하고 융합정보 나열시 CheckBox랑 순서 똑같이 맞춰야됨
                                  this.layers.baseLayers['OSM']//, this.wmsLayer
                              ],
                    overlays: [],
                    view: this.mapView
                    //,controls: ol.control.defaults({ attributionOptions: { collapsible: true } })
                    //.extend([new ol.control.ZoomToExtent({ extent: [13599573.582313137, 4044710.672790877, 14753466.961306157, 4621963.110400528] })])
                    //.extend([new ol.control.FullScreen()])
                });





        },


        addBaseTileLayer: function(layerNm) {
                this._map.getLayers().clear();
                console.info(this.layers.baseLayers);

                this._map.addLayer(this.layers.baseLayers[layerNm]);


                //성남 서현역 epsg 3857
                //his._map.getView().setCenter([14151760.769924419, 4492177.479868216]);
                //서울시청 epsg 3857
                //this._map.getView().setCenter([14135136.120264426, 4518353.625482665]);
                //서울시청 epsg 5174
                this._map.getView().setCenter([198003.10465620566, 451588.32130529406]);
                //성남 서현역 epsg 5179
               // this._map.getView().setCenter([966715.3202278443, 1931764.6642554018]);
                //서울 마포 용산쪽  epsg 5179
                //this._map.getView().setCenter([952903.5895988757, 1952229.2979214708]);
                //서울 시청  epsg 5186
                //this._map.getView().setCenter([198082.76597843564, 551903.6850339735]);
                //서울 시청  epsg 4326
                //this._map.getView().setCenter([126.97831737391309, 37.566619172927574]);


                //wms 레이어 클릭 이벤트
                this._map.on('singleclick', function(evt) {
                         console.info("singleclick")
                         MAP.getAirialImgFiles(evt);

                    })

        },


        getAirialImgFiles: function(evt){
                //console.info(evt);
                //var leftPx=evt.pixel[0];
                //var topPx=evt.pixel[1];
                console.log("coordinate");
                var coordinate = ol.proj.transform(evt.coordinate, 'EPSG:5174', 'EPSG:5174');
                //let coordinate = evt.coordinate;
                console.log(coordinate);


                //클릭 이벤트라 mapView 사용하려면 this.mapView로 하면 안되고 MAP사용 해야한다.
                console.info(MAP.mapView);
                var viewResolution =MAP.mapView.getResolution();

                console.info(viewResolution);

//                    for(var key in MAP.layers.wmsLayers) {
//                          console.log("key: ", key)
//
//                          console.log("value: ", MAP.layers.wmsLayers[key])
//                          console.log("----------------")
//                    }
                //피쳐요청 url 얻기위해 이미 존재하는 layer를 이용
                var wmsSource= MAP.layers.wmsLayers['noper2:noper2_layerGroup'].getSource();
                //파라미터만 layergroup으로 적용하여 getFeatureUrl 얻음
                var featureUrl = wmsSource.getGetFeatureInfoUrl(
                    evt.coordinate, viewResolution, 'EPSG:5174',
                    //url요청 시 응답을 json형식으로 요청하기 위함
                    { INFO_FORMAT: 'application/json' ,
                       FEATURE_COUNT:50,
                        X:50,
                        Y:50,
                      //'INFO_FORMAT': 'text/html' ,
                      exceptions: '"application/vnd.ogc.se_inimage',
                      //QUERY_LAYERS:'noper2:noper5174_1200_garo,noper2:noper5174_1200_sero',
                      //QUERY_LAYERS:'noper2:noper2_layerGroup',
                      LAYERS:'noper2:noper2_layerGroup'
                    }
                );
                console.info(featureUrl);

                $.ajax({
                        url : featureUrl,
                        async : false,
                        success : function(response) {
                            console.info(response);
                            features=response.features;
                            console.info(features);
                            var paramObj={};
                            paramObj.paramList=[];
                            debugger;
                            for(var i=0;i<features.length;i++){
                                  var indIdn=features[i].properties.IND_NO;
                                  paramObj.paramList.push(indIdn);

                            }

                            console.info(paramObj);

                            if(features.length > 1){
                                console.info("feature count is not only");
                            }
                            //db에서 도면 이미지 파일 가져오는 부분
                             fetch("/file/getAirialImgFilesNmList",
                                    {
                                      method: "POST",
                                      headers: {
                                        "Content-Type": "application/json",
                                      },
                                      body: JSON.stringify(paramObj),
                                    })
                                    .then((response) => response.json())
                                    .then(function(data) {
                                           console.info(data);
                                           //해당 클릭 지점에 팝업div 생성
                                           if(document.getElementById('airlialImgNames')===null){//이미 요소 존재하면

                                           }else{
                                                document.getElementById('airlialImgNames').remove();
                                           }
                                           var airlialImgNamesDiv=document.createElement('div');
                                           airlialImgNamesDiv.setAttribute('id','airlialImgNames');
                                           airlialImgNamesDiv.classList.add("speech_bubble");

                                           // 리스트 영역 추가
                                           const listContainer = document.createElement('ul');
                                           listContainer.classList.add("list_file");
                                           airlialImgNamesDiv.appendChild(listContainer);

                                           document.body.appendChild(airlialImgNamesDiv);

                                           airlialImgNamesDiv.style.position='absolute';
                                           //airlialImgNamesDiv.style.left=leftPx+'px';
                                           //airlialImgNamesDiv.style.top=topPx+'px';
                                           airlialImgNamesDiv.style.width ='max-content';

                                           var overlay = new ol.Overlay({
                                                     element: airlialImgNamesDiv,
                                                     autoPan: {
                                                       animation: {
                                                         duration: 250,
                                                       },
                                                     },
                                           });

                                           MAP._map.addOverlay(overlay);//이미지파일 오버레이 맵에 추가
                                           overlay.setPosition(coordinate);



                                           for(var i=0;i<data.resultList.length;i++){
                                                    var scale=data.resultList[i].IDN_SCA;
                                                    if(scale === "1/1,200세로"){

                                                        var visible=MAP.layers.wmsLayers["noper2:noper5174_1200_sero"].S.visible
                                                        if(visible === true){

                                                        }else{//해당 축척 레이어 활성화 안 되어있으면 스킵
                                                            continue;
                                                        }

                                                    }else if(scale === "1/1,200가로"){

                                                        var visible=MAP.layers.wmsLayers["noper2:noper5174_1200_garo"].S.visible
                                                        if(visible === true){

                                                        }else{//해당 축척 레이어 활성화 안 되어있으면 스킵
                                                            continue;
                                                        }
                                                    }else if(scale === "1/1,000"){

                                                        var visible=MAP.layers.wmsLayers["noper2:noper5174_1000"].S.visible
                                                        if(visible === true){

                                                        }else{//해당 축척 레이어 활성화 안 되어있으면 스킵
                                                            continue;
                                                        }
                                                    }

                                                    var buttonList=document.body.querySelector("#buttonList");
                                                    console.info(buttonList);

                                                    var fileFullNm=data.resultList[i].IMG_NM+'.'+data.resultList[i].FILE_CDE;
                                                    var guNm=data.resultList[i].GU_NM;

                                                    var liEle=document.createElement('li');
                                                    liEle.setAttribute('id',data.resultList[i].IMG_NM);
                                                    liEle.setAttribute('data-guNm',guNm);
                                                    liEle.setAttribute('data-scale',scale);
                                                    liEle.innerText=fileFullNm;
                                                    liEle.classList.add('fileElement')

                                                    liEle.setAttribute('onclick','MAP.getAirialImgFile(this)');
                                                    listContainer.appendChild(liEle);
                                           }
                                           console.info("listContainer");
                                           console.info(listContainer);
                                           if(airlialImgNamesDiv.querySelectorAll('.list_file')[0].childNodes.length===0){//켜져있는 레이어 중 클릭시 결과 없을 경우
                                                 airlialImgNamesDiv.remove();// 파일 목록 표출 DIV 삭제
                                                 return false;
                                           }else{
                                              var setHeightVal=airlialImgNamesDiv.offsetHeight;

                                              var airlialImgNamesDiv_parent_ol_top=airlialImgNamesDiv.offsetTop;
                                              //console로 찍으면 0으로나옴 relative 부모요소로부터의 거리를 가져오기때문 하지만 자식요소의 좌표를 수정할 때 기준점으로 사용 가능

                                              //div 표시 위치 bottom으로 조정
                                              airlialImgNamesDiv.style.top=(airlialImgNamesDiv_parent_ol_top-setHeightVal)+'px';

                                               //var base64code=data.resultList[0].base64;
                                               //var imgEle= document.querySelector('#arialImg');
                                               //imgEle.setAttribute('src', 'data:image/pdf;base64,'+base64code);

                                           }







                                    })
                        },
                        error: function(error) {
                            console.info(error);

                        }
                    })



        },


          getAirialImgFile: function(_this){
                            console.info(_this) ;
                            var guNm=_this.getAttribute('data-gunm');;
                            var imgFileNm=_this.innerText;

                            var myForm = document.popForm;
                            var url='/getAirialImgFile'
                            console.info(url);
                            window.open("_blank" ,"popForm");
                            myForm.action=url;
                            myForm.method='get';
                            myForm.target='popForm';

                            myForm.guNm.value=guNm;
                            myForm.imgFileNm.value=imgFileNm;
                            myForm.submit();




                           // this.airImgWinRef=window.open(url,"_blank",'width=#, height=#');


//                             if(this.airImgWinRef===null || this.airImgWinRef.closed===true){
//                                                    this.airImgWinRef=window.open("/getAirialImgFile","gg"
//                                                    ,'width=#, height=#');
//                                                }else{
//                                                    this.airImgWinRef.focus();
//                            }




          }








}











$(function(){

        console.info("mapinit")
        console.info(document.body.querySelector("#geoserverUrl").value);
        MAP.geoserverUrl=document.body.querySelector("#geoserverUrl").value;
        console.info("crsProj4 reg..");
        var crsVal5174=crsUtil.getProj4("EPSG:5174");
        console.info(crsVal5174);
        proj4.defs('EPSG:5174',crsVal5174);

        var crsVal5179=crsUtil.getProj4("EPSG:5179");
        console.info(crsVal5179);
        proj4.defs('EPSG:5179',crsVal5179);

        var crsVal5186=crsUtil.getProj4("EPSG:5186");
        console.info(crsVal5186);
        proj4.defs('EPSG:5186',crsVal5186);

        //ol 지도 객체 생성
        MAP.createMapClass();
        MAP.addBaseTileLayer('VWorld');
        //MAP.addBaseTileLayer('OSM');
        //가로레이어 레이어 생성후 전역레이어리스트에추가 후 지도에 레이어 add
        LAYER.addWmsImgLayer("noper2:noper5174_1000",false);
        LAYER.addWmsImgLayer("noper2:noper5174_1200_sero",false);
        LAYER.addWmsImgLayer("noper2:noper5174_1200_garo",false);
        LAYER.addWmsImgLayer("noper2:noper2_layerGroup",false);
        //LAYER.addWmsImgLayer("rcic:longitudinal_slope5174",true);




});







