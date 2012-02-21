<%@page import="bd.usuario"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*,bd.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    if(session.getAttribute("usuario") == null){
        response.sendRedirect("login.jsp?mje=NO EXISTE SESION ABIERTA!");
    }else{
        usuario u = (usuario)session.getAttribute("usuario");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>QuickFitness México</title>
        <link rel="stylesheet" href="CSS/principal.css"/QuickFitness/>   
        <script type="text/javascript" src="Modulos/jquery/jquery.js"></script>
        <script type="text/javascript" src="Modulos/jquery/jquery.blockUI.js"></script>
        <script type="text/javascript" src="Modulos/jquery/jquery.gritter.min.js"></script>
        <link rel="stylesheet" type="text/css" href="CSS/jquery.gritter.css"/QuickFitness/>
        <script type="text/javascript" src="Modulos/jquery/jquery-extensions_v2.js"></script>
        <script type="text/javascript" src="js/top.js"></script>
    </head>

    <body style="margin:0px;padding:0px;">
        <div class="top">
            <div style="height:5px;"></div>

            <div id='center' class='center'>
                <div class='infoCuenta'>
                    <span id='usuario'>Bienvenido <font color="#BE0000"><%=((usuario)session.getAttribute("usuario")).getusuario()%></font>!</span>
                    <div id='links' class='links'>
                        <a href="inicio.jsp" target="principal" id="inicio">Inicio</a>
                        <% if(u.getidtipousuario() == 1){ %>
                        <span>•</span>
                        <a href="#" id="usuarios">Usuarios</a>
                        <span>•</span>
                        <a href="#" id="gimnasios">Gimnasios</a>
                        <% } 
                        if(u.getidtipousuario() == 2){ %>
                        <span>•</span>
                        <a href="QuickFitness/gimnasios/frmAdminClienteGimnasio.jsp" target="principal" id="clientes">Clientes</a>
                        <span>•</span>
                        <a href="/QuickFitness/gimnasios/frmMembresia.jsp" target="principal" id="membresias">Membresias</a>
                        <% } %>
                        <span>•</span>
                        <a href="#" id="micuenta">Mi Cuenta</a>
                        <span>•</span>
                        <a href="#" id="ayuda">Ayuda</a>
                        <span>•</span>
                        <a href="/QuickFitness/SrvFitness/doLogout" id="salir">Salir</a>
                    </div>
                    <div id='linksUsuarios' class='links2' style="display:none;">
                        Usuarios >> 
                        <%--<a href="usuarios/frmConsultaUsuario.jsp" target="principal">Consultar / Modificar</a>
                        <span> : </span>--%>
                        <a href="/QuickFitness/usuarios/frmAltaUsuario.jsp" target="principal">Administracion de Usuarios</a>                        
                    </div>
                    <div id='linksGimnasios' class='links2' style="display:none;">
                        Gimnasios >> 
                        <%--<a href="#">Consultar / Modificar</a>
                        <span> : </span>--%>
                        <a href="/QuickFitness/gimnasios/frmAltaGimnasio.jsp" target="principal">Administracion de Gimnasios</a>                        
                    </div>
                    <div id='linksClientes' class='links2' style="display:none;">
                        Clientes >> 
                        <a href="/QuickFitness/gimnasios/frmAdminClienteGimnasio.jsp" target="principal">Agregar / Consultar / Modificar</a>                                              
                    </div>
                    <div id='linksMembresias' class='links2' style="display:none;">
                        Membresias >> 
                        <a href="/QuickFitness/gimnasios/frmMembresia.jsp" target="principal">Agregar / Consultar / Modificar</a>                                              
                    </div>
                </div>
                <img src="/QuickFitness/Imagenes/LogoTitulo.png" alt="QuickFitness"/>    	
            </div>

        </div>
    </body>
</html>

<%
    }
%>
