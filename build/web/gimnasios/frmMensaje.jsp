<%-- 
    Document   : frmAltaUsuario
    Created on : 1/10/2011, 09:01:11 PM
    Author     : Luis Angel Pastrana
--%>

<%@page import="bd.mensaje"%>
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
        String idmensaje = "";
        String idcliente = "";
        gimnasio gym = null;
        cliente cli = null;
        mensaje men = null;
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
        
        if (request.getParameter("idmensaje") != null && !request.getParameter("idmensaje").equals("")) {
            idmensaje = request.getParameter("idmensaje");
            accion = "M";
            men = (mensaje) registros.getRegistro("bd.mensaje", "idmensaje = " + idmensaje);            
            request.setAttribute("reg", men);
            idcliente= "" + men.getidcliente();
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
        <title>QuickFitness Mexico :: Mensajes</title>
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
        <script type="text/javascript" src="js/jsMensaje.js"></script>
        <jsp:useBean id="reg" scope="request" class="bd.mensaje" type="bd.mensaje"></jsp:useBean>
    </head>
    <body style="display:none;">
        <div class="contenedor">
            <form method="POST" id="formDatos" action='GUARDAR REGISTRO'>
                <div style="padding: 10px;">
                    <table>
                        <tr>
                            <td><img src="/QuickFitness/Imagenes/img/mail.png" style="height: 50px;"/></td>
                            <td><h1><%=cli.getnombre()+" "+cli.getapaterno()+" "+cli.getamaterno()%> : Mensajes</h1></td>
                        </tr>
                    </table>
                </div>
                <table id="tblDatos" align="center"  style="width: 90%;border-collapse:collapse;">                    
                    <colgroup style="width: 100%;"/>
                    <tbody>
                        <tr>
                            <td class="NombreDeCampo">
                                * Fecha
                            </td>                                                                              
                        </tr>
                        <tr>
                            <td>
                                 <input type="text" name="fecha" id="fecha" value="<%=Util.convierteFechaHoraMostrar((men!=null?men.getfecha():null), true)%>" size="12" class="field formatofecha required" />
                                <img src="/QuickFitness/Imagenes/icons/calendar.png" alt="CALENDARIO" id="boton_fecha" class="imgCalendario calendario" formato='fecha' align="bottom" title="SELECCION DE FECHA" />
                            </td>                                                                                  
                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                * Mensaje
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <textarea id="mensaje" name='mensaje' class="AreaTexto width90 required" rows="3"><%=men!=null?men.getmensaje():""%></textarea>
                            </td>                            
                        </tr>                        
                        <tr>
                            <td class="NombreDeCampo">
                                Observaciones
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <textarea id="observaciones" name='observaciones' class="AreaTexto width90" rows="3"><%=men!=null?men.getobservaciones():""%></textarea>
                            </td>                            
                        </tr>                        
                        <tr>
                            <td class="NombreDeCampo">
                                &nbsp;
                            </td>                                                       
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" id="idmensaje" name="idmensaje" value="<%=idmensaje%>"/>                
                <input type="hidden" id="idcliente" name="idcliente" value="<%=idcliente%>"/>                
                <input type="hidden" id="idtipomensaje" name="idtipomensaje" value="1"/>                
                <input type="hidden" id="estatusregistro" name="estatusregistro" value="0"/>                
                <input type="hidden" id="accion" name="accion" value="<%=accion%>"/>
                <input type="hidden" id="tablaBD" name="tablaBD" value="bd.mensaje"/>
                <input type="hidden" id="pagRespuesta" name="pagRespuesta" value="/QuickFitness/gimnasios/frmMensaje.jsp?idcliente=<%=idcliente%>"/>
                <input type="hidden" id="condicion" name="condicion" value="idmensaje=<%=idmensaje%>"/>
                <input type="hidden" id="autoInc" name="autoInc" value="true"/>
                <input type="hidden" id="ESQUEMA" name="ESQUEMA" value="fitness"/>
                <input type="hidden" name="mje" id="mje" value="<%=request.getParameter("mje") != null ? request.getParameter("mje") : ""%>">
                <br/>
                
                <div class="divBotones" align="center">
                    <a href="/GUARDAR" id="btnGuardar"><img src="/QuickFitness/Imagenes/icons/ok.png" alt="Guardar"/>Guardar Mensaje</a>
                </div>
                
                <br/>
            </form>
            <br/>
        </div>

        <%
            bd.bdConexion();
            registros.setConexion(bd.getConexion());

            int cont = registros.bdConreg("SELECT * FROM mensaje where estatusregistro <> 99 and idcliente = " + idcliente);
            bd.bdCierra();
            
            if (cont > 0) { 
        %>
        <br/>
        <div align='center' id='gridDatos' name='gridDatos' class='width100'>
            <form name='modificar' id='modificar' method='POST' action='MODIFICAR'>
                <%
                        //String sql = "SELECT * from pago where estatusregistro <> 99 and idcliente = "+idcliente;
                        String sql = "SELECT"
                                     +"       men.idmensaje as idmensaje,"
                                     +"       men.mensaje as mensaje,"
                                     +"       men.fecha as fecha,"
                                     +"       usuario.usuario as creo"
                                     +" FROM"
                                     +"       mensaje men"
                                     +" LEFT JOIN	"
                                     +"       usuario on men.usuariocreacion = usuario.idusuario "
                                     +" WHERE men.estatusregistro <> 99 and men.idcliente = "+idcliente ;
                                
                        out.println(UtilHTML.generarGridConsulta(
                                sql,
                                "mensaje,fecha,creo",
                                "Mensaje,Fecha,Creado por",
                                "style='width:80%' ",
                                null,
                                "",
                                null,
                                "",
                                true,
                                "idmensaje",
                                "idmensaje",
                                "",
                                "fitness", 1, "100"));
                %>
                <div class='divBotones'>                                   
                    <a id='btnModificar' href='/MODIFICAR'><img src='/QuickFitness/Imagenes/icons/modificar.png' alt='MODIFICAR'/>Modificar</a>                    
                    &nbsp;&nbsp;<a id='btnEliminar' href='/ELIMINAR' class='negative'><img src='/QuickFitness/Imagenes/icons/eliminar.png' alt='ELIMINAR'/>Eliminar</a>
                </div>
                <input type="hidden" id="idcliente" name="idcliente" value="<%=idcliente%>"/>   
            </form>

            <%
                }
                
            %>

        </div>
    </body>
</html>

<%    }
%>
