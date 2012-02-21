<%@page import="bd.configuracion_sistema"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*,bd.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title></title>
        <link rel="stylesheet" href="CSS/principal.css"/>
    </head>

    <body style="margin:0px;padding:0px;">
        <div class='bottom'>
            <table style="width: 100%" border="0">
                <tr>
                    <td align="center">
                        <div class="bottomInner">
                            <div style="height:5px;"></div>
                            <%=((configuracion_sistema) session.getAttribute("conf")).getnombre_sistema()%><br/>
                            <%=((configuracion_sistema) session.getAttribute("conf")).getdescripcion_corta()%><br/>
                            <br/>
                            <%=((configuracion_sistema) session.getAttribute("conf")).getaviso_legal()%><br/>
                        </div> 
                    </td>
                </tr>
            </table>

        </div>

    </body>
</html>
