<%@page import="Interfacesbd.InterfaceRegistrosBDPool"%>
<%@page import="bd.usuario"%>
<%@page import="Interfacesbd.InterfaceBDPool"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head>
        <%
            if(request.getParameter("s")!= null && request.getParameter("s").equals("i")){
                session.invalidate();
            }
        %>
        <title>QuickFitness México</title>
        <meta name="title" content="QuickFitness México"/>
        <meta name="description" content="Administra tu gimnasio"/>
        <meta name="viewport" content="width=320, user-scalable=yes"/>
        <script type="text/javascript" src="/Modulos/jquery/jquery.js"></script>
        <script type="text/javascript" src="/Modulos/jquery/jquery.blockUI.js"></script>
        <script type="text/javascript" src="/Modulos/jquery/jquery.gritter.min.js"></script>
        <link rel="stylesheet" type="text/css" href="/CSS/jquery.gritter.css"/>
        <script type="text/javascript" src="js/login.js"></script>
        <script type="text/javascript" src="/Modulos/jquery/jquery-extensions_v2.js"></script>

        <link rel="stylesheet" href="/CSS/bolt.css" type="text/css"/>
        <link rel="stylesheet" href="/CSS/bolt2.css" type="text/css"/>

        <link media="only screen and (max-device-width: 480px)" href="/CSS/bGeneral.css" type="text/css" rel="stylesheet"/>
        <link media="only screen and (max-device-width: 480px)" href="/CSS/bGeneral2.css" type="text/css" rel="stylesheet"/>
        <!--[if IE]>
        <link rel="stylesheet" href="CSS/boIE.css" type="text/css"/>
        <![endif]-->

    </head>
    <body id="backdrop">

        <div id="login"><a href="#">Info</a></div>

        <script type="text/javascript">
            if ($.browser.msie) {
                document.write('<img id="ie_bg" src="/Imagenes/style/ie_bg2.png" alt="" style="position:absolute;left:0px;top:0px;width:100%;height:100%;z-index:-1"/>');
            }
        </script>

        <div id="elephantLogo">
            <img src="/Imagenes/LogoFitness.png" style="width:25%;"/>
        </div>

        <div id="container">
            <form name="loginData" id="loginData" method="POST" action="LOGIN">
                <div id="modalbox">
                    <div id="gradient1"></div>
                    <div id="gradient2"></div>
                    <div id="logoContainer">
                        <img src='/Imagenes/Title.png'/>
                    </div>
                    <div id="heading">Recepción de clientes&nbsp;</div>
                    <div id="emailContainer">

                        <label for="usuario" id="emailHint"><span>Usuario...</span></label>
                        <input type="text" class="field" name="usuario" id="usuario"/>
                    </div>
                    <div id="emailContainer2">

                        <label for="pass" id="emailHint2"><span>Contraseña...</span></label>
                        <input type="password" class="field" name="pass" id="pass"/>

                    </div>
                    <div id="submitContainer">
                        <div id="submit" name="submit" style="text-align: center;padding: 3px 10px 3px 10px; ">Ingresar</button>
                        </div>
                    </div>
                    <div id="formErrors"></div>
                    <div id="haveKeyMessage"><a href="#">Necesitas ayuda para ingresar?</a></div>
                </div>
                    <input type="hidden" name="mje" id="mje" value="<%=request.getParameter("mje")!=null ? request.getParameter("mje") : ""%>">
            </form>
        </div>
    </body></html>