<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="emotion.aspx.cs" Inherits="Sky.WebIm.emotion" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>无标题页</title>
    <style type="text/css">
    table
    {
        border-collapse: collapse;
    }
    
    td
    {
        width: 19px;
        height: 19px;
        border: solid 1px #ffffff;
        background-color: #c8d0d4;
    }
    </style>
</head>
<body>
    <form id="form1" runat="server">
        <table id="tableEmotion" runat="server">
        </table>

        <script language="javascript" type="text/javascript" src="../include/script/im/emotion.js"></script>

    </form>
</body>
</html>