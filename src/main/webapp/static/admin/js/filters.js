+(function() {
  Vue.filter('user-type-filter', function(value) {
    switch (value) {
      case 0:
        return '超级管理员';
      default:
        return '';
    }
  });

  Vue.filter('user-img-filter',function(value){
    return value || '/static/img/defImg.jpg';
  });
})(Vue);