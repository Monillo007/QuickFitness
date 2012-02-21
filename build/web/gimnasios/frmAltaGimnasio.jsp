<%-- 
    Document   : frmAltaUsuario
    Created on : 1/10/2011, 09:01:11 PM
    Author     : Luis Angel Pastrana
--%>

<%@page import="util.Util"%>
<%@page import="bd.gimnasio"%>
<%@page import="Interfacesbd.*"%>
<%@page import="util.UtilHTML"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../login.jsp?mje=NO EXISTE SESION ABIERTA!");
    } else {

        String accion = "C";
        String idgimnasio = "";
        gimnasio gym = null;

        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema("fitness");
        bd.bdConexion();
        /*while (bd.getConexion() == null) {
            bd.bdConexion();
        }*/
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        registros.setConexion(bd.getConexion());

        if (request.getParameter("idgimnasio") != null && !request.getParameter("idgimnasio").equals("")) {
            idgimnasio = request.getParameter("idgimnasio");
            accion = "M";
            gym = (gimnasio) registros.getRegistro("bd.gimnasio", "idgimnasio= " + idgimnasio);
            request.setAttribute("reg", gym);

        }
        bd.bdCierra();

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QuickFitness Mexico :: Alta de gimnasio</title>
        <link rel="stylesheet" href="../CSS/formStyle.css"/>   
        <link rel="stylesheet" href="../CSS/botones.css"/>   
        <script type="text/javascript" src="../Modulos/jquery/jquery.js"></script>
        <script type="text/javascript" src="../Modulos/jquery/jquery.validate.min.js"></script>
        <script type="text/javascript" src="../Modulos/jquery/jquery.validate.messages_es.js"></script>
        <script type="text/javascript" src="../Modulos/jquery/jquery.maskedinput.min.js" ></script>
        <link rel="stylesheet" type="text/css" media="all" href="../Modulos/calendario/calendar-blue.css" title="win2k-cold-1" />
        <script type="text/javascript" src="../Modulos/calendario/calendar_stripped.js"></script>
        <script type="text/javascript" src="../Modulos/calendario/lang/calendar-es.js" ></script>
        <script type="text/javascript" src="../Modulos/calendario/calendar-setup.js"></script>
        <script type="text/javascript" src="../Modulos/jquery/jquery.gritter.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../CSS/jquery.gritter.css"/>
        <script type="text/javascript" src="../Modulos/jquery/jquery-extensions_v2.js"></script>
        <script type="text/javascript" src="js/jsAltaGimnasio.js"></script>
        <jsp:useBean id="reg" scope="request" class="bd.gimnasio" type="bd.gimnasio"></jsp:useBean>
    </head>
    <body style="display:none;">
        <div class="contenedor">
            <form method="POST" id="formDatos" action='GUARDAR REGISTRO'>
                <div style="padding: 10px;">
                    <table>
                        <tr>
                            <td><img src="../Imagenes/img/id.png" style="height: 50px;"/></td>
                            <td><h1>Administración de Gimnasios</h1></td>
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
                                * RFC
                            </td>
                            <td class="NombreDeCampo">
                                * Responsable
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="nombre" id="nombre" class="field required" maxlength="100" value="<%=gym!=null?gym.getnombre():""%>"/>
                            </td>
                            <td>
                                <input type="text" name="rfc" id="rfc" class="field required" maxlength="15" value="<%=gym!=null?gym.getrfc():""%>"/>
                            </td>
                            <td>
                                <input type="text" name="responsable" id="responsable" class="field required" maxlength="100" value="<%=gym!=null?gym.getresponsable():""%>"/>
                            </td>

                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                Calle
                            </td>
                            <td class="NombreDeCampo">
                                Colonia
                            </td>
                            <td class="NombreDeCampo">
                                <table style="width:100%;">
                                    <colgroup style="width:50%;">
                                    <colgroup style="width:50%;">
                                    <tr>
                                        <td class="NombreDeCampo">
                                            Num. Ext.
                                        </td>
                                        <td class="NombreDeCampo">
                                            Num. Int.
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="calle" id="calle" class="field" maxlength="100" value="<%=gym!=null?gym.getcalle():""%>"/>
                            </td>
                            <td>
                                <input type="text" name="colonia" id="colonia" class="field" maxlength="100" value="<%=gym!=null?gym.getcolonia():""%>"/>
                            </td>
                            <td class="NombreDeCampo">
                                <table style="width:100%;">
                                    <colgroup style="width:50%;">
                                    <colgroup style="width:50%;">
                                    <tr>
                                        <td>
                                            <input type="text" name="numext" id="numext" class="field" maxlength="100" value="<%=gym!=null?gym.getnumext():""%>"/>
                                        </td>
                                        <td >
                                            <input type="text" name="numint" id="numint" class="field" maxlength="100" value="<%=gym!=null?gym.getnumint():""%>"/>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                Telefono
                            </td>
                            <td class="NombreDeCampo">
                                Correo-e
                            </td>
                            <td class="NombreDeCampo">
                                Nextel
                            </td>                            
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="telefono" id="telefono" class="field" maxlength="20" value="<%=gym!=null?gym.gettelefono():""%>"/>
                            </td>
                            <td>
                                <input type="text" name="correoe" id="correoe" class="field email" maxlength="100" value="<%=gym!=null?gym.getcorreoe():""%>"/>
                            </td>
                            <td>
                                <input type="text" name="nextel" id="nextel" class="field" maxlength="25" value="<%=gym!=null?gym.getnextel():""%>"/>
                            </td>                           
                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                * Vigencia
                            </td>                                                       
                        </tr>
                        <tr>
                            <td class="NombreDeCampo" colspan="2">
                                del &nbsp;&nbsp;
                                <input type="text" name="vigenciadel" id="vigenciadel" value="<%=Util.convierteFechaHoraMostrar((gym!=null?gym.getvigenciadel():null), true)%>" size="12" class="field formatofecha required" />
                                <img src="/QuickFitness/Imagenes/icons/calendar.png" alt="CALENDARIO" id="boton_vigenciadel" class="imgCalendario calendario" formato='fecha' align="bottom" title="SELECCION DE FECHA" />                                        
                                &nbsp;&nbsp; al &nbsp;&nbsp; 
                                <input type="text" name="vigenciaal" id="vigenciaal" value="<%=Util.convierteFechaHoraMostrar((gym!=null?gym.getvigenciaal():null), true)%>" size="12" class="field formatofecha required" />
                                <img src="/QuickFitness/Imagenes/icons/calendar.png" alt="CALENDARIO" id="boton_vigenciaal" class="imgCalendario calendario" formato='fecha' align="bottom" title="SELECCION DE FECHA" />
                                
                            </td>                                                    
                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                &nbsp;
                            </td>                                                       
                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                Observaciones
                            </td>                                                       
                        </tr>
                        <tr>
                            <td class="NombreDeCampo" colspan="3">
                                <textarea id="observaciones" name="observaciones" rows="3" style="width: 90%;" class="AreaTexto"><%=gym!=null?gym.getobservaciones():""%></textarea>
                            </td>                                                    
                        </tr>

                    </tbody>
                </table>
                <input type="hidden" id="idgimnasio" name="idgimnasio" value="<%=idgimnasio%>"/>                
                <input type="hidden" id="accion" name="accion" value="<%=accion%>"/>
                <input type="hidden" id="tablaBD" name="tablaBD" value="bd.gimnasio"/>
                <input type="hidden" id="pagRespuesta" name="pagRespuesta" value="/QuickFitness/gimnasios/frmAltaGimnasio.jsp?mje=Se ha guardado con exito."/>                
                <input type="hidden" id="condicion" name="condicion" value="idgimnasio=<%=idgimnasio%>"/>
                <input type="hidden" id="autoInc" name="autoInc" value="true"/>
                <input type="hidden" id="ESQUEMA" name="ESQUEMA" value="fitness"/>
                <input type="hidden" name="mje" id="mje" value="<%=request.getParameter("mje") != null ? request.getParameter("mje") : ""%>">
                <br/>
                <div class="divBotones" align="center">
                    <a href="/GUARDAR" id="btnGuardar"><img src="/QuickFitness/Imagenes/icons/ok.png" alt="Guardar"/>Guardar Gimnasio</a>
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

                    int cont = registros.bdConreg("SELECT * FROM gimnasio where estatusregistro <> 99");

                    bd.bdCierra();
                    if (cont > 0) {
                        String sql = "SELECT "
                                + " idgimnasio,"
                                + " nombre,"
                                + " responsable,	"
                                + " correoe,"
                                + " nextel"
                                + " FROM "
                                + " gimnasio"
                                + " WHERE estatusregistro <> 99"
                                + " order by nombre";
                        out.println(UtilHTML.generarGridConsulta(
                                sql,
                                "nombre,responsable,correoe,nextel",
                                "Gimnasio,Responsable,Correo-e,Nextel",
                                "style='width:80%' ",
                                null,
                                "",
                                null,
                                "",
                                true,
                                "idgimnasio",
                                "idgimnasio",
                                "",
                                "fitness", 1, "100"));
                %>
                <div class='divBotones'>
                    <a id='btnModificar' href='/MODIFICAR'><img src='/QuickFitness/Imagenes/icons/modificar.png' alt='MODIFICAR'/>Modificar</a>
                    &nbsp;&nbsp;<a id='btnClientes' href='/Clientes'><img src='/QuickFitness/Imagenes/icons/clientes.png' alt='CLIENTES'/>Administrar Clientes</a>
                    &nbsp;&nbsp;<a id='btnMembresias' href='/Membresias'><img src='/QuickFitness/Imagenes/icons/mem.png' alt='MEMBRESIAS'/>Administrar Membresias</a>
                        <%--&nbsp;&nbsp;<a id='btnEliminar' href='/ELIMINAR' class='negative'><img src='/Imagenes/icons/eliminar.png' alt='ELIMINAR'/>Eliminar</a>--%>
                </div>
            </form>

            <%
                }
            %>

        </div>
    </body>
</html>
<%
    
    }
%>
