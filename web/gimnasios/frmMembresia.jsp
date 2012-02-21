<%-- 
    Document   : frmAltaUsuario
    Created on : 1/10/2011, 09:01:11 PM
    Author     : Luis Angel Pastrana
--%>

<%@page import="bd.cat_membresia"%>
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
        String idmembresia = "";
        gimnasio gym = null;
        cat_membresia mem = null;
        usuario usu = (usuario) session.getAttribute("usuario");

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
            System.out.println("idgimnasio ="+idgimnasio);
        } else if (usu != null && usu.getidgimnasio() != null && usu.getidgimnasio() != 0) {
            System.out.println("sesion");
            idgimnasio = "" + usu.getidgimnasio();
            gym = (gimnasio) registros.getRegistro("bd.gimnasio", "idgimnasio= " + usu.getidgimnasio());
        }
        
        if (request.getParameter("idmembresia") != null && !request.getParameter("idmembresia").equals("")) {
            idmembresia = request.getParameter("idmembresia");
            accion = "M";
            mem = (cat_membresia) registros.getRegistro("bd.cat_membresia", "idmembresia= " + idmembresia);            
            request.setAttribute("reg", mem);
            idgimnasio = "" + mem.getidgimnasio();
            gym = (gimnasio) registros.getRegistro("bd.gimnasio", "idgimnasio= " + mem.getidgimnasio());
        }
        
        if(idgimnasio != null && !idgimnasio.equals("") && gym == null){
            System.out.println("obteniendo gimnasio");
            gym = (gimnasio) registros.getRegistro("bd.gimnasio", "idgimnasio= " + idgimnasio);
        }                
        bd.bdCierra();

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QuickFitness Mexico :: Membresias</title>
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
        <script type="text/javascript" src="js/jsMembresia.js"></script>
        <jsp:useBean id="reg" scope="request" class="bd.cat_membresia" type="bd.cat_membresia"></jsp:useBean>
    </head>
    <body style="display:none;">
        <div class="contenedor">
            <form method="POST" id="formDatos" action='GUARDAR REGISTRO'>
                <div style="padding: 10px;">
                    <table>
                        <tr>
                            <td><img src="/QuickFitness/Imagenes/img/mem.png" style="height: 50px;"/></td>
                            <td><h1><%=gym.getnombre()%> : Membresias</h1></td>
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
                                * Dias
                            </td>                            
                            <td class="NombreDeCampo">
                                * Creditos diarios
                            </td>                            
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="descripcion" id="descripcion" value="<%=mem!=null?mem.getdescripcion():""%>" size="50" class="field required" />
                            </td>                            
                            <td>
                                <input type="text" name="dias" id="dias" value="<%=mem!=null?mem.getdias():""%>" size="3" class="field number required" />
                            </td>                            
                            <td>
                                <input type="text" name="creditosxdia" id="creditosxdia" value="<%=mem!=null?mem.getcreditosxdia():""%>" size="3" class="field number required" />
                            </td>                            
                        </tr>
                        <tr>
                            <td class="NombreDeCampo">
                                * Costo
                            </td>
                            <td class="NombreDeCampo">
                                Observaciones
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="monto" id="monto" class="field number required" maxlength="10" value="<%=mem!=null?mem.getmonto():""%>" style="width:50%"/>
                            </td>
                            <td colspan="2">
                                <textarea id="observaciones" name='observaciones' class="AreaTexto width90" rows="3"><%=mem!=null?mem.getobservaciones():""%></textarea>
                            </td>
                            
                        </tr>                        
                        <tr>
                            <td class="NombreDeCampo">
                                &nbsp;
                            </td>                                                       
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" id="idmembresia" name="idmembresia" value="<%=idmembresia%>"/>                
                <input type="hidden" id="idgimnasio" name="idgimnasio" value="<%=idgimnasio%>"/>                
                <input type="hidden" id="estatusregistro" name="estatusregistro" value="0"/>                
                <input type="hidden" id="accion" name="accion" value="<%=accion%>"/>
                <input type="hidden" id="tablaBD" name="tablaBD" value="bd.cat_membresia"/>
                <input type="hidden" id="pagRespuesta" name="pagRespuesta" value="/QuickFitness/gimnasios/frmMembresia.jsp?idgimnasio=<%=idgimnasio%>"/>
                <input type="hidden" id="condicion" name="condicion" value="idmembresia=<%=idmembresia%>"/>
                <input type="hidden" id="autoInc" name="autoInc" value="true"/>
                <input type="hidden" id="ESQUEMA" name="ESQUEMA" value="fitness"/>
                <input type="hidden" name="mje" id="mje" value="<%=request.getParameter("mje") != null ? request.getParameter("mje") : ""%>">
                <br/>
                
                <div class="divBotones" align="center">
                    <a href="/GUARDAR" id="btnGuardar"><img src="/QuickFitness/Imagenes/icons/ok.png" alt="Guardar"/>Guardar Membresia</a>
                </div>
                
                <br/>
            </form>
            <br/>
        </div>

        <%
            bd.bdConexion();
            registros.setConexion(bd.getConexion());

            int cont = registros.bdConreg("SELECT * FROM cat_membresia where estatusregistro <> 99 and idgimnasio = " + idgimnasio);
            bd.bdCierra();
            
            if (cont > 0) { 
        %>
        <br/>
        <div align='center' id='gridDatos' name='gridDatos' class='width100'>
            <form name='modificar' id='modificar' method='POST' action='MODIFICAR'>
                <%
                        //String sql = "SELECT * from pago where estatusregistro <> 99 and idcliente = "+idcliente;
                        String sql = "SELECT"
                                     +"       membresia.idmembresia as idmembresia,"
                                     +"       membresia.descripcion as descripcion,"
                                     +"       membresia.dias as dias,"
                                     +"       membresia.creditosxdia as creditosxdia,"
                                     +"       membresia.monto as monto,"
                                     +"       usuario.usuario as creo"
                                     +" FROM"
                                     +"       cat_membresia membresia"
                                     +" LEFT JOIN	"
                                     +"       usuario on membresia.usuariocreacion = usuario.idusuario "
                                     +" WHERE membresia.estatusregistro <> 99 and membresia.idgimnasio  = "+idgimnasio ;
                                
                        out.println(UtilHTML.generarGridConsulta(
                                sql,
                                "descripcion,dias,creditosxdia,monto,creo",
                                "Nombre,Dias,Creditos diarios,Costo,Creado por",
                                "style='width:80%' ",
                                null,
                                "",
                                null,
                                "",
                                true,
                                "idmembresia",
                                "idmembresia",
                                "",
                                "fitness", 1, "100"));
                %>
                <div class='divBotones'>                                   
                    <a id='btnModificar' href='/MODIFICAR'><img src='/QuickFitness/Imagenes/icons/modificar.png' alt='MODIFICAR'/>Modificar</a>                    
                    &nbsp;&nbsp;<a id='btnEliminar' href='/ELIMINAR' class='negative'><img src='/QuickFitness/Imagenes/icons/eliminar.png' alt='ELIMINAR'/>Eliminar</a>
                </div>
                <input type="hidden" id="idgimnasio" name="idgimnasio" value="<%=idgimnasio%>"/>   
            </form>

            <%
                }
                
            %>

        </div>
    </body>
</html>

<%    }
%>
