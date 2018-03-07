(function() {
    // 设置刷新时间戳
    function chgUrl() {
        var timestamp = (new Date()).valueOf();
        var url = ctx + '/generateVerifyCode';
        url = url + "?timestamp=" + timestamp;
        return url;
    }

    //刷新验证码
    //$('#imgVerifyCode').on('click',function(event){
    //    $(this).attr('src',chgUrl());
    //});


    $("#close_msg_span").on('click', function() {
        $('#errorMessage').hide('slow');
    });

    $('#forgetPassword').on('click', function(event) {
        alert("请联系系统管理员");
    });

    $(function() {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });


        $('#username').focus();

        $("#loginForm").validate({
            submitHandler: function(form) {
                $('#errorMessage').hide();
                $('#submitBtn').attr('disabled', 'disabled');

                $('#showMsgContainer').html('');

                $.ajax({
                    url: ctx + '/api/login',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        username: form.username.value,
                        password: form.password.value,
                        userType: 'ADMIN'
                            //verifyCode: form.verifyCode.value
                            //rememberMe: form.rememberMe.checked ? 'true' : 'false'
                    },
                    success: function(res) {
                        if (res.code == "1") {
                            var user_info = JSON.stringify(res.data);
                            sessionStorage.setItem("USER_INFO", user_info);
                            location.href = DameiUtils.parseQueryString().successUrl || ctx + '/admin/index';
                        } else if (res.code == "0") {
                            $('#showMsgContainer').html(res.message);
                        } else if (res.code == "1000") {
                            //$('#imgVerifyCode').attr('src',chgUrl());
                            $("#showMoreThan3Time").css("display", "block");
                            // $("#verifyCode").attr("required","true");
                            $('#showMsgContainer').html(res.message);
                        }
                    },
                    error: function(response) {
                        $('#errorMessage').show();
                        $ //('#imgVerifyCode').trigger('click');
                        $('#errorMessage .text').html(response.responseText);
                    },
                    complete: function() {
                        $('#submitBtn').removeAttr('disabled');
                    }
                });
            }
        });
    });
})();