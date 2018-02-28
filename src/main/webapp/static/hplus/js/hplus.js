$(document).ajaxError(function(e, xmlhttp, opt) {
    if (xmlhttp.readyState == 4) toastr.error('网络异常或返回值格式不正确');
});

// 全局AJAX请求异常处理
$(document).ajaxSuccess(function(e, xmlhttp, opt) {
    if (opt.dataType != 'json') return false;
    var res = JSON.parse(xmlhttp.responseText);
    if ($.isNumeric(res.code) && res.code == 0) {
        toastr.error(res.message);
    }
});


// Custom scripts
$(document).ready(function() {

    // MetsiMenu
    $('#side-menu').metisMenu();

    // Collapse ibox function
    // 修改为事件委托
    $('body').on('click', '.collapse-link', function() {
        var ibox = $(this).closest('div.ibox');
        var button = $(this).find('i');
        var content = ibox.find('div.ibox-content');
        content.slideToggle(200);
        button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
        ibox.toggleClass('').toggleClass('border-bottom');
        setTimeout(function() {
            ibox.resize();
            ibox.find('[id^=map-]').resize();
        }, 50);
    });

    // Close ibox function
    // 关闭窗口
    $('body').on('click', '.close-link', function() {
        var content = $(this).closest('div.ibox');
        content.remove();
    });

    // Small todo handler
    $('.check-link').click(function() {
        var button = $(this).find('i');
        var label = $(this).next('span');
        button.toggleClass('fa-check-square').toggleClass('fa-square-o');
        label.toggleClass('todo-completed');
        return false;
    });

    // 加入config box
    // $.get("skin-config.html", function (data) {
    //     $('body').append(data);
    // });

    // minimalize menu
    $('.navbar-minimalize').click(function() {
        $("body").toggleClass("mini-navbar");
        SmoothlyMenu();
    });

    // tooltips
    $('.tooltip-demo').tooltip({
        selector: "[data-toggle=tooltip]",
        container: "body"
    })

    // Move modal to body
    // Fix Bootstrap backdrop issu with animation.css
    $('.modal').appendTo("body");

    // Full height of sidebar
    function fix_height() {
        var heightWithoutNavbar = $("body > #wrapper").height() - 61;
        $(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");
    }
    fix_height();

    // Fixed Sidebar
    // unComment this only whe you have a fixed-sidebar
    // 侧边栏固定
    $(window).bind("load", function() {
        if ($("body").hasClass('fixed-sidebar')) {
            // $('.sidebar-collapse').slimScroll({
            //     height: '100%',
            //     railOpacity: 0.9,
            // });
            $('.sidebar-collapse').perfectScrollbar();
        }
    });

    $(window).bind("load resize click scroll", function() {
        if (!$("body").hasClass('body-small')) {
            fix_height();
        }
    });

    // $("[data-toggle=popover]")
    //     .popover();
});


// For demo purpose - animation css script
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function() {
            element.addClass('animated ' + animation);
        },
        function() {
            //wait for animation to finish before removing classes
            window.setTimeout(function() {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

// Minimalize menu when screen is less than 768px
$(function() {
    $(window).bind("load resize", function() {
        if ($(this).width() < 769) {
            $('body').addClass('body-small')
        } else {
            $('body').removeClass('body-small')
        }
    });
});

function SmoothlyMenu() {
    if (!$('body').hasClass('mini-navbar') || $('body').hasClass('body-small')) {
        // Hide menu in order to smoothly turn on when maximize menu
        $('#side-menu').hide();
        // For smoothly turn on menu
        setTimeout(
            function() {
                $('#side-menu').fadeIn(500);
            }, 100);
    } else if ($('body').hasClass('fixed-sidebar')) {
        $('#side-menu').hide();
        setTimeout(
            function() {
                $('#side-menu').fadeIn(500);
            }, 300);
    } else {
        // Remove all inline style from jquery fadeIn function to reset menu state
        $('#side-menu').removeAttr('style');
    }
}

// Dragable panels
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
            handle: handle,
            connectWith: connect,
            tolerance: 'pointer',
            forcePlaceholderSize: true,
            opacity: 0.8,
        })
        .disableSelection();
};