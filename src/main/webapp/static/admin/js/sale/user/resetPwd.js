var vueIndex = null;
+(function () {
    $('#resetPassword').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            user: {
                plainPwd: '',
                loginPwd: '',
                confirmPwd: ''
            },
            submitBtnClick: false
        },
        validators: {
            password: function (val) {
                return /^[a-zA-Z0-9@\.]*$/.test(val);
            }
        },
        methods: {
            submit: function () {
                var self = this;
                self.submitBtnClick = true;
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        if (self.user.loginPwd != self.user.confirmPwd) {
                            Vue.toastr.error("两次密码不一致！");
                            return false;
                        }
                        self.disabled = true;
                        self.$http.post('/api/users/updatePwd', self.user, {emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                            }
                        }).finally(function () {
                            self.disabled = false;
                        });
                    }
                });
            }
        },
        created: function () {
        },
        ready: function () {
        }
    });
})
();