<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="savemessage.aspx.cs" Inherits="Sky.WebIm.savemessage" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head runat="server">
    <title>无标题页</title>
</head>

<body>
    <form id="form1" runat="server">
        <asp:TextBox ID="txtCnSender" runat="server"></asp:TextBox>
        <asp:TextBox ID="txtCnReceiver" runat="server"></asp:TextBox>
        <asp:TextBox ID="txtWriteContent" runat="server" TextMode="MultiLine"></asp:TextBox>
        <asp:Button id="btnWrite" runat="server" Text="Write" OnClick="btnWrite_Click" />
    </form>
</body>

</html>