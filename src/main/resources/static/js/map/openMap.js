


//지도객체 네임스페이스
var OPENMAP={

              airImgWinRef:null,
              openAerialPhotoMap: function(_this) {


                    console.info('open map___');
                    if(this.airImgWinRef===null || this.airImgWinRef.closed===true){
                        this.airImgWinRef=window.open("/getIntroMap","_blank");
                    }else{
                        this.airImgWinRef.focus();
                    }





              }






}