<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/views/js/importjs.jsp" %>
<html>
  <head>
    <title>Validator Test</title>
   
   
    <script type="text/javascript">
      
      function validateTest() {
      	
      	var msg = Validator.validateNotNull($('notnull').value, "测试非空");
      	showMessage('notnull', msg);
      	
      	msg = Validator.validateNumber($('number').value, "测试Number");
      	showMessage('number', msg);
      	
      	/* 是否为日期型，如 2010-01-28 */
      	msg = Validator.validateDate($('date').value,"测试日期");
      	showMessage('date', msg);
      	
      	/* 是否为时间日期型，如 2010-01-28 12:25:28 */
      	msg = Validator.validateDateTime($('datetime').value,"测试日期时间");
      	showMessage('datetime', msg);
      	
      	/* 是否为时间型，如 12:25:28 */
      	msg = Validator.validateTime($('time').value,"测试时间");
      	showMessage('time', msg);
      	
      	msg = Validator.validateInt($('int').value,"测试Int");
      	showMessage('int', msg);
      	
      	msg = Validator.validateEmail($('email').value,"测试Email");
      	showMessage('email', msg);
      	
      	msg = Validator.validateLength($('length').value, "测试长度", ">", 1);
      	showMessage('length', msg);
      	
      	if(msg == "") {
      		msg = Validator.validateLength($('length').value, "测试长度", "<", 3);
      		showMessage('length', msg);
      	}	
      }
      
    </script>
    
  </head>
  <body>
      <div> 测试非空 :
        <input type="text" id="notnull"/>
      </div>
      
      <div> 测试Int :
        <input type="text" id="int" />
      </div>
      
      <div> 测试Number :
        <input type="text" id="number" />
      </div>
      <div> 测试日期 (如 2010-01-28):
        <input type="text" id="date" />
      </div>
      <div> 测试日期时间 (如 2010-01-28 12:25:28):
        <input type="text" id="datetime" />
      </div>
      <div> 测试时间 (如 12:25:28):
        <input type="text" id="time" />
      </div>
      
      <div> 测试长度 :
        <input type="text" id="length" />
      </div>
      
      <div> 测试Email :
        <input type="text" id="email" />
      </div>
      
      <div><input type="button" onclick="validateTest()" value="验证" /></div>
  </body>
</html>
