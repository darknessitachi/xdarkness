
CrossValidator = Class.create();                          //#5
Object.extend(CrossValidator.prototype, {
    type: "none",
    crossError: 0,
    crossInputs: 0,

    initialize: function(framework,                           //#B
                         p_crossInputs,                       //#B
                         p_crossError) {                      //#B
      framework.crossValidators.push(this);                   //#C
      this.crossError = p_crossError;
      this.crossInputs = p_crossInputs;
    },

    validate: function() {
      errorMsg = this.doValidate(
        this.crossInputs);                                    //#D
      this.crossError.innerHTML = errorMsg;
      return (errorMsg.length == 0);
    },

    clearErrors: function() {                                 //#E
      this.crossError.innerHTML = "";
    }
});