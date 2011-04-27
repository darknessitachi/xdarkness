<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/views/js/importjs.jsp" %>
<html>
  <head>
    <title>Validator Test</title>
   
   
    <script type="text/javascript">
      
      function validateTest() {
      	
      	var msg = Validator.validateNotNull($('notnull').value, "���Էǿ�");
      	showMessage('notnull', msg);
      	
      	msg = Validator.validateNumber($('number').value, "����Number");
      	showMessage('number', msg);
      	
      	/* �Ƿ�Ϊ�����ͣ��� 2010-01-28 */
      	msg = Validator.validateDate($('date').value,"��������");
      	showMessage('date', msg);
      	
      	/* �Ƿ�Ϊʱ�������ͣ��� 2010-01-28 12:25:28 */
      	msg = Validator.validateDateTime($('datetime').value,"��������ʱ��");
      	showMessage('datetime', msg);
      	
      	/* �Ƿ�Ϊʱ���ͣ��� 12:25:28 */
      	msg = Validator.validateTime($('time').value,"����ʱ��");
      	showMessage('time', msg);
      	
      	msg = Validator.validateInt($('int').value,"����Int");
      	showMessage('int', msg);
      	
      	msg = Validator.validateEmail($('email').value,"����Email");
      	showMessage('email', msg);
      	
      	msg = Validator.validateLength($('length').value, "���Գ���", ">", 1);
      	showMessage('length', msg);
      	
      	if(msg == "") {
      		msg = Validator.validateLength($('length').value, "���Գ���", "<", 3);
      		showMessage('length', msg);
      	}	
      }
      
    </script>
    
  </head>
  <body>
      <div> ���Էǿ� :
        <input type="text" id="notnull"/>
      </div>
      
      <div> ����Int :
        <input type="text" id="int" />
      </div>
      
      <div> ����Number :
        <input type="text" id="number" />
      </div>
      <div> �������� (�� 2010-01-28):
        <input type="text" id="date" />
      </div>
      <div> ��������ʱ�� (�� 2010-01-28 12:25:28):
        <input type="text" id="datetime" />
      </div>
      <div> ����ʱ�� (�� 12:25:28):
        <input type="text" id="time" />
      </div>
      
      <div> ���Գ��� :
        <input type="text" id="length" />
      </div>
      
      <div> ����Email :
        <input type="text" id="email" />
      </div>
      
      <div><input type="button" onclick="validateTest()" value="��֤" /></div>
  </body>
</html>
