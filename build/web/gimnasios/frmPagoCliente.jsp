<%-- 
    Document   : frmAltaUsuario
    Created on : 1/10/2011, 09:01:11 PM
    Author     : Luis Angel Pastrana
--%>

<%@page import="java.util.Date"%>
<%@page import="bd.pago"%>
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
        String idpago = "";
        gimnasio gym = null;
        cliente cli = null;
        pago pag = null;
        usuario usu = (usuario) session.getAttribute("usuario");

        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema("fitness");
        bd.bdConexion();
        /*while (bd.getConexion() == null) {
            bd.bdConexion();
        }*/
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        registros.setConexion(bd.getConexion());

        if (request.getParameter("idcliente") != null && !request.getParameter("idcliente").equals("")) {
            idcliente = request.getParameter("idcliente");
            System.out.println("idcliente ="+idcliente);
        }
        
        if (request.getParameter("idpago") != null && !request.getParameter("idpago").equals("")) {
            idpago = request.getParameter("idpago");
            accion = "M";
            pag = (pago) registros.getRegistro("bd.pago", "idpago= " + idpago);
            idcliente = ""+pag.getidcliente();
            request.setAttribute("reg", pag);
        }
        
        if(idcliente != null && !idcliente.equals("")){
            cli = (cliente) registros.getRegistro("bd.cliente", "idcliente= " + idcliente);
            idgimnasio = "" + cli.getidgimnasio();
            gym = (gimnasio) registros.getRegistro("bd.gimnasio", "idgimnasio= " + cli.getidgimnasio());
        }
        bd.bdCierra();

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QuickFitness Mexico :: Pagos</title>
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
        <script type="text/javascript" src="js/jsPagoCliente.js"></script>
        <jsp:useBean id="reg" scope="request" class="bd.pago" type="bd.pago"></jsp:useBean>
    </head>
    <body style="display:none;">
        <div class="contenedor">
            <form method="POST" id="formDatos" action='GUARDAR REGISTRO' enctype="multipart/form-data">
                <div style="padding: 10px;">
                    <table>
                        <tr>
                            <td><img src="/QuickFitness/Imagenes/img/coin.png" style="height: 50px;"/></td>
                            <td><h1><%=cli != null ? cli.getnombre() +" "+cli.getapaterno() + " "+cli.getamaterno(): ""%> : Pagos</h1></td>
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
                                * Fecha del pago
                            </td>
                            
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="fecha" id="fecha" value="<%=Util.convierteFechaHoraMostrar((pag!=null ? pag.getfecha():new Date()), true)%>" size="12" class="field formatofecha required" />
                                <img src="/QuickFitness/Imagenes/icons/calendar.png" alt="CALENDARIO" id="boton_fecha" class="imgCalendario calendario" formato='fecha' align="bottom" title="SELECCION DE FECHA" />                                        
                            </td>                            
                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                Monto
                            </td>
                            <td class="NombreDeCampo">
                                * Membresía
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="monto" id="monto" class="field number" maxlength="10" value="<%=pag!=null?pag.getmonto():""%>" style="width:50%"/>
                            </td>
                            <td>
                                <%
                                    out.println(
                                            UtilHTML.getComboRegistros("cat_membresia",
                                            "estatusregistro <> 99 and idgimnasio = " + idgimnasio,
                                            "descripcion",
                                            "idmembresia",
                                            "descripcion",
                                            "" + (pag!=null?""+pag.getidmembresia():""),
                                            "class='Combo required' id='idmembresia' name='idmembresia'"));
                                %>
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
                                <textarea id="observaciones" name="observaciones" rows="3" style="width: 95%;" class="AreaTexto"><%=pag!=null?pag.getobservaciones():""%></textarea>
                            </td>                                                    
                        </tr>

                    </tbody>
                </table>
                <input type="hidden" id="idpago" name="idpago" value="<%=idpago%>"/>                
                <input type="hidden" id="idcliente" name="idcliente" value="<%=idcliente%>"/>                
                <input type="hidden" id="idgimnasio" name="idgimnasio" value="<%=idgimnasio%>"/>                
                <input type="hidden" id="accion" name="accion" value="<%=accion%>"/>
                <input type="hidden" id="tablaBD" name="tablaBD" value="bd.pago"/>
                <input type="hidden" id="pagRespuesta" name="pagRespuesta" value="/QuickFitness/gimnasios/frmPagoCliente.jsp"/>                
                <input type="hidden" id="condicion" name="condicion" value="idpago=<%=idpago%>"/>
                <input type="hidden" id="autoInc" name="autoInc" value="true"/>
                <input type="hidden" id="ESQUEMA" name="ESQUEMA" value="fitness"/>
                <input type="hidden" name="mje" id="mje" value="<%=request.getParameter("mje") != null ? request.getParameter("mje") : ""%>">
                <br/>
                
                <div class="divBotones" align="center">
                    <a href="/GUARDAR" id="btnGuardar"><img src="/QuickFitness/Imagenes/icons/ok.png" alt="Guardar"/>Guardar Pago</a>
                </div>
                
                <br/>
            </form>
            <br/>
        </div>

        <%
            bd.bdConexion();
            registros.setConexion(bd.getConexion());

            int cont = registros.bdConreg("SELECT * FROM pago where estatusregistro <> 99 and idcliente = " + idcliente);
            bd.bdCierra();
            
            if (cont > 0) { 
        %>
        <br/>
        <div align='center' id='gridDatos' name='gridDatos' class='width100'>
            <form name='modificar' id='modificar' method='POST' action='MODIFICAR'>
                <%
                        //String sql = "SELECT * from pago where estatusregistro <> 99 and idcliente = "+idcliente;
                        String sql = "SELECT"
                                     +"       pago.idpago as idpago,"
                                     +"       pago.fecha as fecha,"
                                     +"       cat_membresia.descripcion as membresia,"
                                     +"       pago.monto as monto,"
                                     +"       pago.observaciones as observaciones,"
                                     +"       usuario.usuario as creo"
                                     +" FROM"
                                     +"       pago"
                                     +" LEFT JOIN"
                                     +"       cat_membresia on pago.idmembresia = cat_membresia.idmembresia"
                                     +" LEFT JOIN	"
                                     +"       usuario on pago.usuariocreacion = usuario.idusuario "
                                     +" WHERE pago.estatusregistro <> 99 and pago.idcliente = "+idcliente;
                                
                        out.println(UtilHTML.generarGridConsulta(
                                sql,
                                "fecha,membresia,monto,observaciones,creo",
                                "Fecha,Membresia,Monto,Observaciones,Registrado por",
                                "style='width:80%' ",
                                null,
                                "",
                                null,
                                "",
                                false,
                                "idpago",
                                "idpago",
                                "",
                                "fitness", 1, "100"));
                %>
                <div class='divBotones'>                                   
                    <!--<a id='btnModificar' href='/MODIFICAR'><img src='/QuickFitness/Imagenes/icons/modificar.png' alt='MODIFICAR'/>Modificar</a>                    
                    &nbsp;&nbsp;<a id='btnEliminar' href='/ELIMINAR' class='negative'><img src='/QuickFitness/Imagenes/icons/eliminar.png' alt='ELIMINAR'/>Eliminar</a>-->
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
