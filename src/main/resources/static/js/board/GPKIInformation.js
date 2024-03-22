var front = {
    st: 0,
   
    init: function() {
        this.tab();
        this.gnb();
        this.lnb();
    },
    tab: function() {
		// 탭(li) 클릭시
		$('.tabs .tab').on('click', function() {
			// $(this).addClass('on').siblings().removeClass('on');
			// 클릭한 탭과 동일 클래스인 결과창 표출됨
			if ($(this).hasClass('tab_01')) {
				$('.tab_01').addClass('on').siblings().removeClass('on');
			} else if ($(this).hasClass('tab_02')) {
				$('.tab_02').addClass('on').siblings().removeClass('on');
			} else if ($(this).hasClass('tab_03')) {
				$('.tab_03').addClass('on').siblings().removeClass('on');
			} else if ($(this).hasClass('tab_04')) {
				$('.tab_04').addClass('on').siblings().removeClass('on');
			} else if ($(this).hasClass('tab_05')) {
				$('.tab_05').addClass('on').siblings().removeClass('on');
			}
		})
	},

    gnb: function() {
        header =  $('#header');
        megaMenuWrap =  $('#header .mega_menu_wrap');
        
        $('#header .gnb').on('mouseenter', function() {
            megaMenuWrap.addClass('on');
        });
        header.on('mouseleave', function() {
            megaMenuWrap.removeClass('on');
        });
    },

    lnb: function() {
        $('.lnb>li>a').on('click', function() {
            $(this).siblings('.depth2').toggleClass('on').parent().toggleClass('on');
        });
    }
    

}

$(document).ready(function() {
    front.init();
    $('#gotoMain').on('click', function () {
        location.href = "/";
    })
});


