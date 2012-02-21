/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Interfacesbd.InterfaceBDPool;
import Interfacesbd.InterfaceRegistrosBDPool;
import bd.cat_membresia;
import bd.cliente;
import bd.configuracion_sistema;
import bd.mensaje;
import bd.pago;
import bd.usuario;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.ServletGenerico;
import util.Util;

/**
 *
 * @author Luis Angel Pastrana
 */
public class SrvFitness extends ServletGenerico {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, String> params = crearMapaParametros(request);
        //PrintWriter response.getWriter() = response.getWriter();

        HttpSession session = request.getSession();

        String accion = params.get("accion");

        String respuesta = "";
        Logger.getLogger(SrvFitness.class.getName()).info(String.valueOf(params));
        Logger.getLogger(SrvFitness.class.getName()).info("pathInfo = " + request.getPathInfo());

        String path = request.getPathInfo();

        System.out.println("path=" + path);
        try {
            if (accion != null && !accion.equals("C") && !accion.equals("M")) {
                if (accion.equals("USUARIO_DUPLICADO")) {
                    String u = params.get("USUARIO");
                    InterfaceBDPool bd = new InterfaceBDPool();
                    bd.setEsquema("fitness");
                    InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();

                    while (bd.getConexion() == null) {
                        bd.bdConexion();
                    }

                    if (bd.getConexion() != null) {
                        registros.setConexion(bd.getConexion());
                        usuario usu = (usuario) registros.getRegistro("bd.usuario", "usuario = '" + u + "'");
                        if (usu != null) {
                            respuesta = "E$$El nombre de usuario ya existe!";
                        } else {
                            respuesta = "O$$";
                        }
                    }
                    System.out.println("respuesta:" + respuesta);
                    out.print(respuesta);
                    bd.bdCierra();
                }
            } else if (path.equals("/doLogout")) {
                session.invalidate();
                redireccionar("/QuickFitness/login.jsp?target=mainFrame", request, response);
            } else if (path.contains("/doLogin")) {
                InterfaceBDPool bd = new InterfaceBDPool();
                bd.setEsquema("fitness");
                InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();

                //while (bd.getConexion() == null) {
                bd.bdConexion();
                //}

                if (bd.getConexion() != null) {
                    registros.setConexion(bd.getConexion());
                    String usuario = request.getParameter("usuario").replace("'", "");
                    usuario usu = (usuario) registros.getRegistro("bd.usuario", "usuario = '" + usuario + "'");
                    if (usu != null) {
                        if (usu.getestatusregistro() == 0) {
                            if (request.getParameter("pass").replace("'", "").equals(usu.getcontrasena())) {
                                configuracion_sistema conf = (configuracion_sistema) registros.getRegistro("bd.configuracion_sistema", "idconfiguracion = 1");
                                session.setAttribute("usuario", usu);
                                session.setAttribute("conf", conf);
                                System.out.println("/QuickFitness/indice.jsp");
                                redireccionar("/QuickFitness/indice.jsp", request, response);
                                bd.bdCierra();
                            } else {
                                bd.bdCierra();
                                redireccionar("/QuickFitness/login.jsp?mje=CONTRASEÑA INCORRECTA!", request, response);
                                session.invalidate();
                            }
                        } else if (usu.getestatusregistro() == 1) {
                            bd.bdCierra();
                            redireccionar("/QuickFitness/login.jsp?mje=EL USUARIO ESTA DESHABILITADO!", request, response);
                            session.invalidate();
                        }
                    } else {
                        bd.bdCierra();
                        redireccionar("/QuickFitness/login.jsp?mje=USUARIO INCORRECTO!", request, response);
                        session.invalidate();

                    }
                }


            } else if (path.equals("/eliminarMensaje")) {
                if (session.getAttribute("usuario") == null) {
                    response.sendRedirect("/QuickFitness/login.jsp?mje=NO EXISTE SESION ABIERTA!");
                } else {
                    usuario u = (usuario) session.getAttribute("usuario");
                    int idcliente = 0;
                    InterfaceBDPool bd = new InterfaceBDPool();
                    bd.setEsquema("fitness");
                    InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
                    String mensaje = "No fue posible eliminar el mensaje!";

                    bd.bdConexion();

                    if (bd.getConexion() != null) {
                        registros.setConexion(bd.getConexion());
                        mensaje men = (mensaje) registros.getRegistro("bd.mensaje", "idmensaje = " + params.get("idmensaje"));
                        idcliente = men.getidcliente();
                        if (men != null) {
                            
                            men.setestatusregistro(99);
                            men.setfechamodificacion(new Date());
                            men.setusuariomodificacion(u.getidusuario());

                            registros.modificar("bd.mensaje", men, "idmensaje = " + params.get("idmensaje"));
                            mensaje = "El mensaje se elimino correctamente!";
                        }
                    }
                    bd.bdCierra();
                    redireccionar("/QuickFitness/gimnasios/frmMensaje.jsp?mje=" + mensaje + "&idcliente=" + idcliente, request, response);
                }
            } else if (path.equals("/eliminarMembresia")) {
                if (session.getAttribute("usuario") == null) {
                    response.sendRedirect("/QuickFitness/login.jsp?mje=NO EXISTE SESION ABIERTA!");
                } else {
                    usuario u = (usuario) session.getAttribute("usuario");
                    int idgimnasio = 0;
                    InterfaceBDPool bd = new InterfaceBDPool();
                    bd.setEsquema("fitness");
                    InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
                    String mensaje = "No fue posible eliminar la membresia!";

                    bd.bdConexion();

                    if (bd.getConexion() != null) {
                        registros.setConexion(bd.getConexion());
                        cat_membresia mem = (cat_membresia) registros.getRegistro("bd.cat_membresia", "idmembresia = " + params.get("idmembresia"));
                        idgimnasio = mem.getidgimnasio();
                        if (mem != null) {
                            
                            mem.setestatusregistro(99);
                            mem.setfechamodificacion(new Date());
                            mem.setusuariomodificacion(u.getidusuario());

                            registros.modificar("bd.cat_membresia", mem, "idmembresia = " + params.get("idmembresia"));
                            mensaje = "La membresia se elimino correctamente!";
                        }
                    }
                    bd.bdCierra();
                    redireccionar("/QuickFitness/gimnasios/frmMembresia.jsp?mje=" + mensaje + "&idgimnasio=" + idgimnasio, request, response);
                    
                }
            } else if (path.equals("/eliminarClienteGimnasio")) {
                if (session.getAttribute("usuario") == null) {
                    response.sendRedirect("/QuickFitness/login.jsp?mje=NO EXISTE SESION ABIERTA!");
                } else {
                    usuario u = (usuario) session.getAttribute("usuario");
                    int idgimnasio = u.getidgimnasio();
                    InterfaceBDPool bd = new InterfaceBDPool();
                    bd.setEsquema("fitness");
                    InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
                    String mensaje = "No fue posible eliminar al cliente!";

                    while (bd.getConexion() == null) {
                        bd.bdConexion();
                    }

                    if (bd.getConexion() != null) {
                        registros.setConexion(bd.getConexion());
                        cliente c = (cliente) registros.getRegistro("bd.cliente", "idcliente = " + params.get("idcliente"));
                        if (c != null) {
                            idgimnasio = c.getidgimnasio();
                            c.setestatusregistro(99);
                            c.setfechamodificacion(new Date());
                            c.setusuariomodificacion(u.getidusuario());

                            registros.modificar("bd.cliente", c, "idcliente = " + params.get("idcliente"));
                            mensaje = "El cliente se eliminó correctamente!";
                        }
                    }
                    bd.bdCierra();
                    redireccionar("/QuickFitness/gimnasios/frmAdminClienteGimnasio.jsp?mje=" + mensaje + "&idgimnasio=" + idgimnasio, request, response);
                }
            } else if (path.equals("/guardarClienteGimnasio")) {
                if (session.getAttribute("usuario") == null) {
                    response.sendRedirect("/QuickFitness/login.jsp?mje=NO EXISTE SESION ABIERTA!");
                } else {
                    usuario u = (usuario) session.getAttribute("usuario");
                    InterfaceBDPool bd = new InterfaceBDPool();
                    bd.setEsquema("fitness");
                    InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();

                    while (bd.getConexion() == null) {
                        bd.bdConexion();
                    }

                    if (bd.getConexion() != null) {
                        registros.setConexion(bd.getConexion());

                        if (params.get("accion") != null && params.get("accion").equals("C")) {
                            System.out.println("creando registro");
                            cliente c = new cliente();
                            c.setidgimnasio(Integer.parseInt(params.get("idgimnasio")));
                            c.setnombre(params.get("nombre"));
                            c.setapaterno(params.get("apaterno"));
                            c.setamaterno(params.get("amaterno"));
                            c.setcorreoe(params.get("correoe"));
                            c.settelefono(params.get("telefono"));
                            c.setpersona_accidente(params.get("persona_accidente"));
                            c.setfechanacimiento(Util.convierteFecha(params.get("fechanacimiento")));
                            c.setvigenciadel(Util.convierteFecha(params.get("vigenciadel")));
                            c.setvigenciaal(Util.convierteFecha(params.get("vigenciaal")));
                            c.setidmembresia(Integer.parseInt(params.get("idmembresia")));
                            c.setobservaciones(params.get("observaciones"));
                            c.setestatusregistro(0);
                            c.setusuariocreacion(u.getidusuario());

                            System.out.println("registros agregar");
                            registros.agregar("bd.cliente", c);

                            c = (cliente) registros.getRegistro("bd.cliente", "idcliente = (select max(idcliente) from cliente where usuariocreacion=" + u.getidusuario() + ")");

                            //guardar la foto
                            File fotoFile = new File(params.get("foto"));
                            String mensaje = "";

                            if (fotoFile.exists()) {
                                BigDecimal t = new BigDecimal(params.get("foto_SIZE"));
                                if (t.compareTo(new BigDecimal(124000)) <= 0) {
                                    if (registros.guardarArchivoDB(fotoFile.getAbsolutePath(), "" + c.getidcliente() + "." + params.get("foto_CONTENTTYPE").split("/")[1], "cliente", "foto", null, "idcliente = " + c.getidcliente())) {
                                        mensaje = mensaje.concat("Cliente Almacenado Exitosamente");
                                    } else {
                                        mensaje = mensaje.concat("No fue posible guardar la foto.");
                                    }
                                } else {
                                    mensaje = mensaje.concat("El tamaño de la imagen es mayor al permitido (max. 100Kb)");
                                }
                            }
                            bd.bdCierra();
                            redireccionar("/QuickFitness/gimnasios/frmAdminClienteGimnasio.jsp?mje=" + mensaje + "&idgimnasio=" + c.getidgimnasio(), request, response);

                        } else if (params.get("accion") != null && params.get("accion").equals("M")) {
                            System.out.println("modificando registro");
                            cliente c = (cliente) registros.getRegistro("bd.cliente", params.get("condicion"));
                            c.setidgimnasio(Integer.parseInt(params.get("idgimnasio")));
                            c.setnombre(params.get("nombre"));
                            c.setapaterno(params.get("apaterno"));
                            c.setamaterno(params.get("amaterno"));
                            c.setcorreoe(params.get("correoe"));
                            c.settelefono(params.get("telefono"));
                            c.setpersona_accidente(params.get("persona_accidente"));
                            c.setfechanacimiento(Util.convierteFecha(params.get("fechanacimiento")));
                            c.setvigenciadel(Util.convierteFecha(params.get("vigenciadel")));
                            c.setvigenciaal(Util.convierteFecha(params.get("vigenciaal")));
                            c.setidmembresia(Integer.parseInt(params.get("idmembresia")));
                            c.setobservaciones(params.get("observaciones"));
                            c.setestatusregistro(0);
                            c.setusuariocreacion(u.getidusuario());

                            System.out.println("registros modificar");
                            registros.modificar("bd.cliente", c, params.get("condicion"));

                            //c = (cliente)registros.getRegistro("bd.cliente","idcliente = (select max(idcliente) from cliente where usuariocreacion="+u.getidusuario()+")");

                            //guardar la foto
                            String mensaje = "";
                            if (params.get("foto") != null) {
                                File fotoFile = new File(params.get("foto"));

                                if (fotoFile.exists()) {
                                    BigDecimal t = new BigDecimal(params.get("foto_SIZE"));
                                    if (t.compareTo(new BigDecimal(124000)) <= 0) {
                                        if (registros.guardarArchivoDB(fotoFile.getAbsolutePath(), "" + c.getidcliente() + "." + params.get("foto_CONTENTTYPE").split("/")[1], "cliente", "foto", null, "idcliente = " + c.getidcliente())) {
                                            mensaje = mensaje.concat("Cliente Almacenado Exitosamente");
                                        } else {
                                            mensaje = mensaje.concat("No fue posible guardar la foto.");
                                        }
                                    } else {
                                        mensaje = mensaje.concat("El tamaño de la imagen es mayor al permitido (max. 100Kb)");
                                    }
                                }
                            } else {
                                mensaje = mensaje.concat("Cliente Almacenado Exitosamente");
                            }
                            bd.bdCierra();
                            redireccionar("/QuickFitness/gimnasios/frmAdminClienteGimnasio.jsp?mje=" + mensaje + "&idgimnasio=" + c.getidgimnasio(), request, response);
                        } else {
                            //no hay accion                            
                        }
                    }

                    bd.bdCierra();

                }
            } else if (path.equals("/guardarPagoCliente")) {
                if (session.getAttribute("usuario") == null) {
                    response.sendRedirect("/QuickFitness/login.jsp?mje=NO EXISTE SESION ABIERTA!");
                } else {
                    usuario u = (usuario) session.getAttribute("usuario");
                    InterfaceBDPool bd = new InterfaceBDPool();
                    bd.setEsquema("fitness");
                    InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
                    String mensaje = "";

                    while (bd.getConexion() == null) {
                        bd.bdConexion();
                    }

                    if (bd.getConexion() != null) {
                        registros.setConexion(bd.getConexion());

                        if (params.get("accion") != null && params.get("accion").equals("C")) {
                            pago pag = new pago();
                            pag.setidcliente(Integer.parseInt(params.get("idcliente")));
                            pag.setmonto(new BigDecimal(params.get("monto")));
                            pag.setfecha(Util.convierteFecha(params.get("fecha")));
                            pag.setidmembresia(Integer.parseInt(params.get("idmembresia")));
                            pag.setobservaciones(params.get("observaciones"));
                            pag.setestatusregistro(0);
                            pag.setusuariocreacion(u.getidusuario());

                            //se obtienen los datos de la membresia
                            cliente cli = (cliente) registros.getRegistro("bd.cliente", "idcliente= " + pag.getidcliente());
                            cat_membresia mem = (cat_membresia) registros.getRegistro("bd.cat_membresia", "idmembresia = " + pag.getidmembresia());
                            if (cli != null && mem != null) {
                                int dias = mem.getdias();
                                Date vdel = cli.getvigenciadel();
                                Date val = cli.getvigenciaal();
                                Date hoy = new Date();

                                if (val.before(hoy)) {
                                    vdel = pag.getfecha();
                                    Calendar calAl = Calendar.getInstance();
                                    calAl.setTime(vdel);
                                    calAl.set(Calendar.DAY_OF_YEAR, calAl.get(Calendar.DAY_OF_YEAR) + dias);
                                    cli.setvigenciadel(vdel);
                                    cli.setvigenciaal(calAl.getTime());
                                } else {
                                    Calendar calAl = Calendar.getInstance();
                                    calAl.setTime(val);
                                    calAl.set(Calendar.DAY_OF_YEAR, calAl.get(Calendar.DAY_OF_YEAR) + dias);
                                    cli.setvigenciaal(calAl.getTime());
                                }
                                cli.setidmembresia(mem.getidmembresia());
                            }

                            registros.agregar("bd.pago", pag);
                            registros.modificar("bd.cliente", cli, "idcliente = " + cli.getidcliente());
                            mensaje = "PAGO REGISTRADO EXITOSAMENTE!";
                        }

                        bd.bdCierra();
                    }
                    redireccionar("/QuickFitness/gimnasios/frmPagoCliente.jsp?mje=" + mensaje + "&idcliente=" + params.get("idcliente"), request, response);
                }
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
