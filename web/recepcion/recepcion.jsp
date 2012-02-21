<%-- 
    Document   : recepcion
    Created on : 12/12/2011, 02:30:40 PM
    Author     : Luis Angel Pastrana
--%>

<%@page import="bd.usuario"%>
<%@page import="Interfacesbd.InterfaceRegistrosBDPool"%>
<%@page import="Interfacesbd.InterfaceBDPool"%>
<%@page import="bd.gimnasio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        if (session.getAttribute("usuario") == null) {
            response.sendRedirect("/recepcion/login.jsp?mje=NO EXISTE SESION ABIERTA!");
        } else {
            usuario usu = (usuario) session.getAttribute("usuario");
            InterfaceBDPool bd = new InterfaceBDPool();
            bd.setEsquema("fitness");
            bd.bdConexion();
            while (bd.getConexion() == null) {
                bd.bdConexion();
            }
            InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
            registros.setConexion(bd.getConexion());
            
            gimnasio gym = null;
            gym = (gimnasio) registros.getRegistro("bd.gimnasio", "idgimnasio= " + usu.getidgimnasio());
            
            if(gym == null){
                %>
                <script>
                    alert('El usuario no esta asociado a ningun gimnasio!')
                </script>
                <%
                response.sendRedirect("/recepcion/login.jsp?mje=NO HAY GIMNASIO ASOCIADO!");
            }else{
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QuickFitness México :: Recepcion</title>
        <meta name="title" content="QuickFitness México"/>
        <meta name="description" content="Administra tu gimnasio"/>
        <meta name="viewport" content="width=320, user-scalable=yes"/>
        <script type="text/javascript" src="/Modulos/jquery/jquery.js"></script>
        <script type="text/javascript" src="/Modulos/jquery/jquery.blockUI.js"></script>
        <script type="text/javascript" src="/Modulos/jquery/jquery.gritter.min.js"></script>
        <link rel="stylesheet" type="text/css" href="/CSS/jquery.gritter.css"/>
        <script type="text/javascript" src="/Modulos/jquery/jquery-extensions_v2.js"></script>
        <script type="text/javascript" src="/recepcion/js/recepcion.js"></script>

        <link rel="stylesheet" href="/CSS/bolt.css" type="text/css"/>
        <link rel="stylesheet" href="/CSS/bolt2.css" type="text/css"/>

        <link media="only screen and (max-device-width: 480px)" href="/CSS/bGeneral.css" type="text/css" rel="stylesheet"/>
        <link media="only screen and (max-device-width: 480px)" href="/CSS/bGeneral2.css" type="text/css" rel="stylesheet"/>
    </head>
    <body id="backdrop">
        <div id="elephantLogo">
            <img src="/Imagenes/LogoFitness.png" style="width:25%;"/>
        </div>
        <div id="container">
            <form name="loginData" id="loginData" method="POST" action="LOGIN">
                <div id="modalboxal">
                    <div id="gradiental"></div>                    
                    <div class="txtRecepcion" style="text-align:center;">
                        Bienvenido!<br/>
                        <span style="font-size:16px;font-weight: normal;">(desliza tu credencial por el lector o escribe tu número de cliente)</span><br/><br/>
                        Cliente: <input type="text" placeholder="Cliente ID..." name="idcliente" id="idcliente" value="" size="50" class="field required" style="height: 35px;font-size: 20px;line-height: 25px;"/>                       
                        <br/><br/>
                        <div id="datosCliente">
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                            ok<br/>
                        </div>
                    </div>

                </div>
                <input type="hidden" name="mje" id="mje" value="<%=request.getParameter("mje") != null ? request.getParameter("mje") : ""%>">
            </form>
        </div>
    </body>
    <%
            }//gym != null
        }
    %>
</html>
