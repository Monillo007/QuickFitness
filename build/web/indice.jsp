<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%
    if(session.getAttribute("usuario") == null){
        response.sendRedirect("login.jsp?mje=NO EXISTE SESION ABIERTA!");
    }else{
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Bienvenido a QuickFitness</title>
</head>

    <frameset rows="80,*,80" frameborder="no" border="0" framespacing="0" id="mainFrame">
    <frame src="top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
    <frame src="inicio.jsp" name="principal" id="principal" title="principal" />
  <frame src="bottom.jsp" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" title="bottomFrame" />
</frameset>
<noframes><body>
</body>
</noframes></html>

<%
    }
%>