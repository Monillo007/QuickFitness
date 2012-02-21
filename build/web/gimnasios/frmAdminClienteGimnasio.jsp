<%-- 
    Document   : frmAltaUsuario
    Created on : 1/10/2011, 09:01:11 PM
    Author     : Luis Angel Pastrana
--%>

<%@page import="bd.usuario"%>
<%@page import="bd.cliente"%>
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
        String idcliente = "";
        gimnasio gym = null;
        cliente cli = null;
        usuario usu = (usuario) session.getAttribute("usuario");

        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema("fitness");
        bd.bdConexion();
        while (bd.getConexion() == null) {
            bd.bdConexion();
        }
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        registros.setConexion(bd.getConexion());

        if (request.getParameter("idgimnasio") != null && !request.getParameter("idgimnasio").equals("")) {
            System.out.println("request");
            idgimnasio = request.getParameter("idgimnasio");
            gym = (gimnasio) registros.getRegistro("bd.gimnasio", "idgimnasio= " + idgimnasio);
        } else if (usu != null && usu.getidgimnasio() != null && usu.getidgimnasio() != 0) {
            System.out.println("sesion");
            idgimnasio = "" + usu.getidgimnasio();
            gym = (gimnasio) registros.getRegistro("bd.gimnasio", "idgimnasio= " + usu.getidgimnasio());
        }

        if (request.getParameter("idcliente") != null && !request.getParameter("idcliente").equals("")) {
            idcliente = request.getParameter("idcliente");
            accion = "M";
            cli = (cliente) registros.getRegistro("bd.cliente", "idcliente= " + idcliente);
            if(cli != null && cli.getidgimnasio() != null){
                idgimnasio = "" + cli.getidgimnasio();
                gym = (gimnasio) registros.getRegistro("bd.gimnasio", "idgimnasio= " + cli.getidgimnasio());                
            }

            request.setAttribute("reg", cli);
        }
        

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QuickFitness Mexico :: Administración de Clientes</title>
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
        <script type="text/javascript" src="js/jsAdminClienteGimnasio.js"></script>
        <jsp:useBean id="reg" scope="request" class="bd.cliente" type="bd.cliente"></jsp:useBean>
    </head>
    <body style="display:none;">
        <div class="contenedor">
            <form method="POST" id="formDatos" action='GUARDAR REGISTRO' enctype="multipart/form-data">
                <div style="padding: 10px;">
                    <table>
                        <tr>
                            <td><img src="/QuickFitness/Imagenes/img/clientes.png" style="height: 50px;"/></td>
                            <td><h1><%=gym != null ? gym.getnombre() : ""%> : Administración de Clientes</h1></td>
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
                                * Ap. Paterno
                            </td>
                            <td class="NombreDeCampo">
                                * Ap. Materno
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="nombre" id="nombre" class="field required" maxlength="50" value="<%=cli != null ? cli.getnombre() : ""%>"/>
                            </td>
                            <td>
                                <input type="text" name="apaterno" id="apaterno" class="field required" maxlength="50" value="<%=cli != null ? cli.getapaterno() : ""%>"/>
                            </td>
                            <td>
                                <input type="text" name="amaterno" id="amaterno" class="field required" maxlength="50" value="<%=cli != null ? cli.getamaterno() : ""%>"/>
                            </td>

                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                Correo-e
                            </td>
                            <td class="NombreDeCampo">
                                Teléfono
                            </td>
                            <td class="NombreDeCampo">
                                Persona a notificar en caso de accidente
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="correoe" id="correoe" class="field email" maxlength="100" value="<%=cli != null ? cli.getcorreoe() : ""%>"/>
                            </td>
                            <td>
                                <input type="text" name="telefono" id="telefono" class="field" maxlength="25" value="<%=cli != null ? cli.gettelefono() : ""%>"/>
                            </td>
                            <td class="NombreDeCampo">
                                <input type="text" name="persona_accidente" id="persona_accidente" class="field" maxlength="100" value="<%=cli != null ? cli.getpersona_accidente() : ""%>"/>                               
                            </td>
                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                Fecha de Nacimiento
                            </td>
                            <td class="NombreDeCampo">
                                * Tipo de Membresía
                            </td>
                            <td class="NombreDeCampo">
                                Foto
                            </td>                            
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="fechanacimiento" id="fechanacimiento" value="<%=Util.convierteFechaHoraMostrar((cli!=null?cli.getfechanacimiento():null), true)%>" size="12" class="field formatofecha" />
                                <img src="/QuickFitness/Imagenes/icons/calendar.png" alt="CALENDARIO" id="boton_fechanacimiento" class="imgCalendario calendario" formato='fecha' align="bottom" title="SELECCION DE FECHA" />                                        
                            </td>
                            <td>
                                <%
                                    out.println(
                                            UtilHTML.getComboRegistros("cat_membresia",
                                            "estatusregistro <> 99 and idgimnasio = " + idgimnasio,
                                            "descripcion",
                                            "idmembresia",
                                            "descripcion",
                                            "" + (cli!=null?cli.getidmembresia():""),
                                            "class='Combo required' id='idmembresia' name='idmembresia'"));
                                %>
                            </td>
                            <td rowspan="4" align="center">
                                <%
                                    String foto = "/QuickFitness/Imagenes/img/administrator.png";
                                    if (!idcliente.equals("")) {
                                        String fotoTemp = registros.getArchivoBD("cliente", "foto", null, "idcliente= " + idcliente, "QuickFitness/Archivos/Sesion");
                                        System.out.println(fotoTemp);
                                        if (fotoTemp != null && !fotoTemp.equals("")) {
                                            foto = "/QuickFitness/Archivos/Sesion/" + fotoTemp;
                                        }
                                    }
                                    bd.bdCierra();
                                %>
                                <img src="<%=foto%>" style="width:70px;"/>
                                <br/>
                                <input type="file" name="foto" id="foto"/>
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
                                <input type="text" name="vigenciadel" id="vigenciadel" value="<%=Util.convierteFechaHoraMostrar((cli!=null?cli.getvigenciadel():null), true)%>" size="12" class="field formatofecha required" />
                                <img src="/QuickFitness/Imagenes/icons/calendar.png" alt="CALENDARIO" id="boton_vigenciadel" class="imgCalendario calendario" formato='fecha' align="bottom" title="SELECCION DE FECHA" />                                        
                                &nbsp;&nbsp; al &nbsp;&nbsp; 
                                <input type="text" name="vigenciaal" id="vigenciaal" value="<%=Util.convierteFechaHoraMostrar((cli!=null?cli.getvigenciaal():null), true)%>" size="12" class="field formatofecha required" />
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
                            <td class="NombreDeCampo" colspan="2">
                                <textarea id="observaciones" name="observaciones" rows="3" style="width: 95%;" class="AreaTexto"><%=cli != null ? cli.getobservaciones() : ""%></textarea>
                            </td>                                                    
                        </tr>

                    </tbody>
                </table>
                <input type="hidden" id="idcliente" name="idcliente" value="<%=idcliente%>"/>                
                <input type="hidden" id="idgimnasio" name="idgimnasio" value="<%=idgimnasio%>"/>                
                <input type="hidden" id="accion" name="accion" value="<%=accion%>"/>
                <input type="hidden" id="tablaBD" name="tablaBD" value="bd.cliente"/>
                <input type="hidden" id="pagRespuesta" name="pagRespuesta" value="/QuickFitness/gimnasios/frmAdminClienteGimnasio.jsp"/>                
                <input type="hidden" id="condicion" name="condicion" value="idcliente=<%=idcliente%>"/>
                <input type="hidden" id="autoInc" name="autoInc" value="true"/>
                <input type="hidden" id="ESQUEMA" name="ESQUEMA" value="fitness"/>
                <input type="hidden" name="mje" id="mje" value="<%=request.getParameter("mje") != null ? request.getParameter("mje") : ""%>">
                <br/>
                <% if (gym != null) {%>
                <div class="divBotones" align="center">
                    <a href="/GUARDAR" id="btnGuardar"><img src="/QuickFitness/Imagenes/icons/ok.png" alt="Guardar"/>Guardar Cliente</a>
                </div>
                <% } else {%>
                <div class="mjeAlerta">
                    No se tiene un gimnasio asociado a la cuenta.<br/>
                    Favor de reportarlo con el administrador del sitio.
                </div>
                <% }%>
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

                    int cont = registros.bdConreg("SELECT count(*) FROM cliente where estatusregistro <> 99 and idgimnasio = " + idgimnasio);

                    bd.bdCierra();
                    
                    if (cont > 0) {
                        String sql = "SELECT "
                                + " idcliente,"
                                + " idgimnasio,"
                                + " nombre,"
                                + " apaterno,"
                                + " amaterno,	"
                                + " vigenciadel,"
                                + " vigenciaal"
                                + " FROM "
                                + " cliente"
                                + " WHERE estatusregistro <> 99 and idgimnasio = " + idgimnasio
                                + " order by nombre";
                        out.println(UtilHTML.generarGridConsulta(
                                sql,
                                "nombre,apaterno,amaterno",
                                "Nombre,Ap.Paterno,Ap.Materno",
                                "style='width:80%' ",
                                null,
                                "",
                                null,
                                "",
                                true,
                                "idcliente",
                                "idcliente",
                                "",
                                "fitness", 1, "100"));
                %>
                <div class='divBotones'>
                    <a id='btnPago' href='/PAGO'><img src='/QuickFitness/Imagenes/icons/moneda.png' class="positive" alt='PAGO'/>Registrar Pago</a>                    
                    <a id='btnMensaje' href='/MENSAJE'><img src='/QuickFitness/Imagenes/icons/mail.png' alt='Mensaje'/>Agregar Mensaje</a>                    
                    &nbsp;&nbsp;<a id='btnModificar' href='/MODIFICAR'><img src='/QuickFitness/Imagenes/icons/modificar.png' alt='MODIFICAR'/>Modificar</a>                    
                    &nbsp;&nbsp;<a id='btnEliminar' href='/ELIMINAR' class='negative'><img src='/QuickFitness/Imagenes/icons/eliminar.png' alt='ELIMINAR'/>Eliminar</a>
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
