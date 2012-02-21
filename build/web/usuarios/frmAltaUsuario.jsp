<%-- 
    Document   : frmAltaUsuario
    Created on : 1/10/2011, 09:01:11 PM
    Author     : Luis Angel Pastrana
--%>

<%@page import="bd.usuario"%>
<%@page import="Interfacesbd.*"%>
<%@page import="util.UtilHTML"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("/QuickFitness/login.jsp?mje=NO EXISTE SESION ABIERTA!");
    } else {

        String accion = "C";
        String idusuario = "";
        usuario usu = null;

        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema("fitness");
        bd.bdConexion();
        
        bd.bdConexion();
        
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        registros.setConexion(bd.getConexion());

        if (request.getParameter("idusuario") != null && !request.getParameter("idusuario").equals("")) {
            //System.out.println("dentro de if--------------------");
            idusuario = request.getParameter("idusuario");
            accion = "M";
            usu = (usuario) registros.getRegistro("bd.usuario", "idusuario= " + idusuario);
            request.setAttribute("reg", usu);

        }else{
            //System.out.println("-------fuera de if-------");
        }
        bd.bdCierra();

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QuickFitness Mexico :: Alta de usuario</title>
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
        <jsp:useBean id="reg" scope="request" class="bd.usuario" type="bd.usuario"></jsp:useBean>
        </head>
        <body>
            <div class="contenedor">
                <form method="POST" id="formDatos" action='GUARDAR REGISTRO'>
                    <div style="padding: 10px;">
                        <table>
                            <tr>
                                <td><img src="../Imagenes/img/group.png" style="height: 50px;"/></td>
                                <td><h1>Administración de Usuarios</h1></td>
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
                                    <input type="text" name="usuario" id="usuario" class="field required" maxlength="25" value="<%=usu!=null?usu.getusuario():""%>"/>
                            </td>
                            <td>
                                <input type="password" name="contrasena" id="contrasena" class="field required" maxlength="25" value="<%=usu!=null?usu.getcontrasena():""%>"/>
                            </td>
                            <td>
                                <input type="password" name="contrasena_c" id="contrasena_c" class="field" maxlength="25" value="<%=usu!=null?usu.getcontrasena():""%>"/>
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
                                            UtilHTML.getComboRegistros("gimnasio", "estatusregistro <> 99", "nombre", "idgimnasio", "nombre", "" + reg.getidgimnasio(), "class='Combo' id='idgimnasio' name='idgimnasio'"));
                                %>
                            </td>
                            <td>
                                <%
                                    out.println(
                                            UtilHTML.getComboRegistros("cat_tipousuario", "estatusregistro <> 99", "descripcion", "idtipousuario", "descripcion", "" + reg.getidtipousuario(), "class='Combo required' id='idtipousuario' name='idtipousuario'"));
                                %>
                            </td>
                            <td>
                                <select id='estatusregistro' name='estatusregistro' class='Combo required'>
                                    <option value=''>Selecciona...</option>
                                    <option value='0' <%=usu != null && usu.getestatusregistro() == 0 ? "selected" : ""%>>Activo</option>
                                    <option value='1' <%=usu != null && usu.getestatusregistro() == 1 ? "selected" : ""%>>Inactivo</option>
                                </select>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" id="idusuario" name="idusuario" value="<%=idusuario%>"/>                
                <input type="hidden" id="nombreusuario_o" name="nombreusuario_o" value="<%=(usu!=null?usu.getusuario():"")%>"/>                
                <input type="hidden" id="accion" name="accion" value="<%=accion%>"/>
                <input type="hidden" id="tablaBD" name="tablaBD" value="bd.usuario"/>
                <input type="hidden" id="pagRespuesta" name="pagRespuesta" value="/QuickFitness/usuarios/frmAltaUsuario.jsp?mje=Se ha creado el usuario"/>                
                <input type="hidden" id="condicion" name="condicion" value="idusuario=<%=idusuario%>"/>
                <input type="hidden" id="autoInc" name="autoInc" value="true"/>
                <input type="hidden" id="ESQUEMA" name="ESQUEMA" value="fitness"/>
                <input type="hidden" name="mje" id="mje" value="<%=request.getParameter("mje") != null ? request.getParameter("mje") : ""%>">
                <br/>
                <div class="divBotones" align="center">
                    <a href="/GUARDAR" id="btnGuardar"><img src="/QuickFitness/Imagenes/icons/ok.png" alt="Guardar"/>Guardar Usuario</a>
                </div>
                <br/>
            </form>
            <br/>
        </div>
        <br/>
        <div align='center' id='gridDatos' name='gridDatos' class='width100'>
            <form name='modificar' id='modificar' method='POST' action='MODIFICAR'>
                <%
                    bd.bdConexion();
                    registros.setConexion(bd.getConexion());

                    int cont = registros.bdConreg("SELECT * FROM usuario where estatusregistro <> 99");

                    bd.bdCierra();
                    if (cont > 0) {
                        String sql = "SELECT "
                                + " idusuario,"
                                + " usuario,	"
                                + " tu.descripcion as tipo,"
                                + " case"
                                + "	when usuario.estatusregistro = 0 then 'Activo'"
                                + "	when usuario.estatusregistro = 1 then 'Inactivo'"
                                + " end as estatus,"
                                + " gi.nombre as gim"
                                + " FROM "
                                + " usuario"
                                + " LEFT JOIN"
                                + " cat_tipousuario tu on usuario.idtipousuario = tu.idtipousuario"
                                + " LEFT JOIN"
                                + " gimnasio gi on usuario.idgimnasio = gi.idgimnasio"
                                + " WHERE usuario.estatusregistro <> 99"
                                + " order by gi.nombre,usuario";
                        out.println(UtilHTML.generarGridConsulta(
                                sql,
                                "usuario,gim,tipo,estatus",
                                "Usuario,Gimnasio,Tipo,Estatus",
                                "style='width:80%' ",
                                null,
                                "",
                                null,
                                "",
                                true,
                                "idusuario",
                                "idusuario",
                                "",
                                "fitness", 1, "100"));
                %>
                <div class='divBotones'>
                    <a id='btnModificar' href='/MODIFICAR'><img src='/QuickFitness/Imagenes/icons/modificar.png' alt='MODIFICAR'/>Modificar</a>
                        <%--&nbsp;&nbsp;<a id='btnEliminar' href='/ELIMINAR' class='negative'><img src='/Imagenes/icons/eliminar.png' alt='ELIMINAR'/>Eliminar</a>--%>
                </div>
            </form>

            <%
                }

            %>

        </div>
    </body>
</html>

<%    }
%>
