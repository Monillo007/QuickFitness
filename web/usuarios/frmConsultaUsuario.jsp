<%-- 
    Document   : frmAltaUsuario
    Created on : 1/10/2011, 09:01:11 PM
    Author     : Luis Angel Pastrana
--%>

<%@page import="util.UtilHTML"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../login.jsp?mje=NO EXISTE SESION ABIERTA!");
    } else {
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QuickFitness Mexico :: Consulta de usuarios</title>
        <link rel="stylesheet" href="../CSS/formStyle.css"/>   
        <link rel="stylesheet" href="../CSS/botones.css"/>   
        <script type="text/javascript" src="../Modulos/jquery/jquery.js"></script>
        <script type="text/javascript" src="../Modulos/jquery/jquery.validate.min.js"></script>
        <script type="text/javascript" src="../Modulos/jquery/jquery.validate.messages_es.js"></script>
        <script type="text/javascript" src="../Modulos/jquery/jquery.maskedinput.min.js" ></script>
        <script type="text/javascript" src="../Modulos/jquery/jquery.gritter.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../CSS/jquery.gritter.css"/>
        <script type="text/javascript" src="../Modulos/jquery/jquery-extensions_v2.js"></script>
        <script type="text/javascript" src="js/jsAltaUsuario.js"></script>
    </head>
    <body>
        <div class="contenedor">
            <form method="POST" id="formDatos" action='GUARDAR REGISTRO'>
                <div style="padding: 10px;">
                    <table>
                        <tr>
                            <td><img src="../Imagenes/img/group.png" style="height: 50px;"/></td>
                            <td><h1>Consulta de Usuarios</h1></td>
                        </tr>
                    </table>
                </div>
                <table id="tblDatos" align="center"  style="width: 90%;border-collapse:collapse;">                    
                    <colgroup style="width: 33%;"/>
                    <colgroup style="width: 33%;"/>
                    <colgroup style="width: 33%;"/>
                    <tbody>
                        <tr>
                            <td class="NombreDeCampo">
                                * Nombre
                            </td>
                            <td class="NombreDeCampo">
                                * Contraseña
                            </td>
                            <td class="NombreDeCampo">
                                Confirmar Contraseña
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="usuario" id="usuario" class="field required" maxlength="25"/>
                            </td>
                            <td>
                                <input type="password" name="contrasena" id="contrasena" class="field required" maxlength="25"/>
                            </td>
                            <td>
                                <input type="password" name="contrasena_c" id="contrasena_c" class="field" maxlength="25"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                Gimnasio
                            </td>
                            <td class="NombreDeCampo">
                                * Tipo de Usuario
                            </td>
                            <td class="NombreDeCampo">
                                * Estatus
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <%
                                    out.println(
                                            UtilHTML.getComboRegistros("gimnasio", "estatusregistro <> 99", "nombre", "idgimnasio", "nombre", "", "class='Combo' id='idgimnasio' name='idgimnasio'"));
                                %>
                            </td>
                            <td>
                                <%
                                    out.println(
                                            UtilHTML.getComboRegistros("cat_tipousuario", "estatusregistro <> 99", "descripcion", "idtipousuario", "descripcion", "", "class='Combo required' id='idtipousuario' name='idtipousuario'"));
                                %>
                            </td>
                            <td>
                                <select id='estatusregistro' name='estatusregistro' class='Combo required'>
                                    <option value=''>Selecciona...</option>
                                    <option value='0'>Activo</option>
                                    <option value='1'>Inactivo</option>
                                </select>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" id="idusuario" name="idusuario" value=""/>                
                <input type="hidden" id="accion" name="accion" value="C"/>
                <input type="hidden" id="tablaBD" name="tablaBD" value="bd.usuario"/>
                <input type="hidden" id="pagRespuesta" name="pagRespuesta" value="usuarios/frmAltaUsuario.jsp?mje=Se ha creado el usuario"/>                
                <input type="hidden" id="condicion" name="condicion" value="1<>1"/>
                <input type="hidden" id="autoInc" name="autoInc" value="true"/>
                <input type="hidden" id="ESQUEMA" name="ESQUEMA" value="fitness"/>
                <input type="hidden" name="mje" id="mje" value="<%=request.getParameter("mje")!=null ? request.getParameter("mje") : ""%>">
                <br/>
                <div class="divBotones" align="center">
                    <a href="/GUARDAR" id="btnGuardar"><img src="../Imagenes/icons/ok.png" alt="Guardar"/>Guardar Usuario</a>
                </div>
                <br/>
            </form>

        </div>
    </body>
</html>

<%    }
%>
