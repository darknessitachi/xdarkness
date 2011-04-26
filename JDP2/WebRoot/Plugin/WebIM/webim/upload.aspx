<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="upload.aspx.cs" Inherits="Sky.WebIm.upload" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
    <title>上传</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="../include/script/jquery/jquery.js"></script>
    <script type="text/javascript" src="../include/script/jquery/interface.js"></script>
    <style type="text/css">
        *{ margin:0; padding:0; }
        #fileImg
        {
            border: none;
        }
    </style>
    <script type="text/javascript">
        
	    var uploadSelect = function(){
	        var el = document.getElementById("fileImg");
	        if(!parent.LayoutSettings.checkImgExt(el.value))
	        {
	            return;
	        }
	        
	        document.getElementById("currentImg").value = parent.LayoutSettings.currentHeadImg;
	        document.getElementById("userId").value = parent.document.getElementById("hidSender").value;
	       
		    $(el).fadeOut("show");
		    parent.LayoutSettings.uploading(document.getElementById("<%=fileImg.ClientID %>").value);
		    $("#<%=frmUpload.ClientID %>").submit();
		    
	    };
 	    
    </script>
</head>
<body>
    <form runat="server" id="frmUpload" method="post" enctype="multipart/form-data">
        <input type="file" runat="server" id="fileImg" size="20"  onchange="uploadSelect();" />	    
        <asp:HiddenField ID="userId" runat="server" Value="" />
        <asp:HiddenField ID="currentImg" runat="server" Value="" />
        <asp:HiddenField ID="imgSize" runat="server" Value="1024" />
    </form>
</body>
</html>