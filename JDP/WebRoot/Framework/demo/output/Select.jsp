<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib prefix="sky" uri="/WEB-INF/tags/sky.tld" %>
<html>
  <head>
    
    <title>Select</title>
	<%@ include file="/views/js/importjs.jsp" %>
	
  </head>
  
  <body>
  	<sky:select>
  	<select id="s">
  		<option value="a">a</option>
  		<option value="b">b</option>
  		<option value="c">c</option>
  	</select>
  	</sky:select>
  	
  	<br><br>
  	Ô´´úÂë£º
  	<pre>
  	&lt;sky:select>
		&lt;select id="s">
			&lt;option value="a">a</option>
			&lt;option value="b">b</option>
			&lt;option value="c">c</option>
		&lt;/select>
  	&lt;/sky:select>
  	</pre>
  </body>
</html>
