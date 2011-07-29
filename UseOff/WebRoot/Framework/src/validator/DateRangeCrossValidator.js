var DateRangeCrossValidator =                                 //#6
  Class.create();                                             //#6
Object.extend(DateRangeCrossValidator.prototype,
              CrossValidator.prototype);
Object.extend(DateRangeCrossValidator.prototype, {

  doValidate: function(inputs) {
    var startDate = Date.parse(inputs[0].value);
    var endDate = Date.parse(inputs[1].value);
    if (startDate > endDate) {
      return "The start date cannot be after the end date.";
    } else {
      return "";
    }
  }

});