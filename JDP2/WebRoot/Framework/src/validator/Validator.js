
Validator = Base.extend({
  type: "all",

  constructor: function() { 
  },

  doValidate: function(value) { 
    return "";
  },

  validate: function(value,srcElement,name,op,fValue) {  
    return this.doValidate(value,srcElement,name,op,fValue);
  }
});

