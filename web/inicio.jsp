<%-- 
    Document   : inicio
    Created on : 1/10/2011, 08:05:41 PM
    Author     : Luis Angel Pastrana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QuickFitness Mexico</title>
        <link rel="stylesheet" href="CSS/formStyle.css"/>
        <script type="text/javascript" src="Modulos/jquery/jquery.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
    
                $("body").css("display", "none");
                $("body").fadeIn(2000);
    
                $("a.transicion").click(function(event){
                    event.preventDefault();
                    linkDestino = this.href;
                    $("body").fadeOut(1000, redireccionarPag);      
                });
        
                function redireccionarPag() {
                    window.location = linkDestino;
                }
    
            });
        </script>
    </head>
    <body>
        <table style="width: 100%" border="0">
            <tr>
                <td align="center">
                    <img src='Imagenes/LogoFitness.png'/>
                </td>
            </tr>
        </table>
    </body>
</html>
