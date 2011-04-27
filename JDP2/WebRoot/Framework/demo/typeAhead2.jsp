<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
    <head>
        <title>Type Ahead</title>
        <%@ include file="/views/js/importjs.jsp" %>
        <script type="text/javascript">
            
            
            window.onload = function(){
                new TextSuggest('txtUserInput','typeAheadXML.js',{
                	matchAnywhere: true,
            		ignoreCase: true
                });
            };
       
       
       
        </script>
       
    </head>
    <body>
 AutoComplete Text Box:<input type="text" id="txtUserInput"/><input type="hidden" id="txtUserValue" ID="hidden1" />
    </body>
</html>