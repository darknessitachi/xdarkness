
NumberValidator = Validator.extend({
    type: "number", 

    doValidate: function(value,srcElement,name) {
      var numberPattern=/(^\d+$)|(^\d+\.\d+$)/;
      
      if (numberPattern.test(value)) {
        return "";
      }
      
      return name + "'" + value + "' 不是有效的数字." ;
    }
});
