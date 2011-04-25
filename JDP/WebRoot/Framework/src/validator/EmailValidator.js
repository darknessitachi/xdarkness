
EmailValidator = Validator.extend({
    type: "email", 

    doValidate: function(value,srcElement,name) {
      var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      
      if (pattern.test(value)) {
        return "";
      }
      return name + "'" + value + "'不是有效的电子邮件地址." ;
    }
});
