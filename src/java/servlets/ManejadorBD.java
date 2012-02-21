package servlets;

import Interfacesbd.*;
import bd.usuario;
import util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ing. Luis Manuel Navarro Rangel.
 */
public class ManejadorBD extends HttpServlet {

    /* 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Indica el formato de la página de respuesta*/
        response.setContentType("text/html;charset=UTF-8");
        String referer = request.getHeader("referer");

        /*El objeto utilizado para escribir la página*/
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        /*La acción a realizar (C-rear,M-odificar,E-liminar)*/
        String accion = "";
        String[] parametrosObligatorios = new String[]{"tablaBD", "accion", "pagRespuesta", "ESQUEMA"};
        Class clase;
        String[] campos;
        InterfaceBDPool basedatos;
        InterfaceRegistrosBDPool registros;
        String mje = "";
        String ultimoAgregado = "";
        int idUser = 0;

        String tablaBD, pagRespuesta = "", ESQUEMA, condicion, autoInc = "true", idTabla;

        basedatos = new InterfaceBDPool();
        registros = new InterfaceRegistrosBDPool();

        HttpSession sesion = request.getSession();
        System.out.println("--ManejadorBD: " + referer);

        for (String par : parametrosObligatorios) {
            if ((request.getParameter(par) == null || request.getParameter(par).equals(""))
                    && (sesion.getAttribute(par) == null || ((String) sesion.getAttribute(par)).equals(""))) {
                ServletException ex = new ServletException("El parámetro necesario '" + par + "' no ha sido definido.");
                System.out.println(ex);
                throw ex;
            } else {
                if (sesion.getAttribute(par) != null && !((String) sesion.getAttribute(par)).equals("")) {
                    if (par.equals("accion")) {
                        accion = (String) sesion.getAttribute(par);
                    }
                    if (par.equals("tablaBD")) {
                        tablaBD = (String) sesion.getAttribute(par);
                    }
                    if (par.equals("ESQUEMA")) {
                        ESQUEMA = (String) sesion.getAttribute(par);
                    }
                    if (par.equals("pagRespuesta")) {
                        pagRespuesta = (String) sesion.getAttribute(par);
                    }
                    if (par.equals("autoInc")) {
                        autoInc = (String) sesion.getAttribute(par);
                    }
                } else {
                    if (par.equals("accion")) {
                        accion = request.getParameter(par);
                    }
                    if (par.equals("tablaBD")) {
                        tablaBD = request.getParameter(par);
                    }
                    if (par.equals("ESQUEMA")) {
                        ESQUEMA = request.getParameter(par);
                    }
                    if (par.equals("pagRespuesta")) {
                        pagRespuesta = request.getParameter(par);
                    }
                    if (par.equals("autoInc")) {
                        autoInc = request.getParameter(par);
                    }
                }
                idUser = (Integer) ((usuario)sesion.getAttribute("usuario")).getidusuario();
            }
        }
        response.setHeader("Location", pagRespuesta);
        if (accion.equalsIgnoreCase("M")
                && (request.getParameter("condicion") == null || request.getParameter("condicion").equals(""))
                && (sesion.getAttribute("condicion") == null || ((String) sesion.getAttribute("condicion")).equals(""))) {
            System.out.println("ManejadorBD - El parámetro necesario 'condicion' no ha sido definido.");
            throw new ServletException("El parámetro necesario 'condicion' no ha sido definido.");
        } else {
            if (sesion.getAttribute("condicion") != null && !((String) sesion.getAttribute("condicion")).equals("")) {
                condicion = (String) sesion.getAttribute("condicion");
            } else {
                condicion = (String) request.getParameter("condicion");
            }
        }
        //System.out.println("Accion: "+accion);
        if ((request.getParameter("realizarConsulta") != null && request.getParameter("realizarConsulta").equals("true"))
                || (sesion.getAttribute("realizarConsulta") != null && ((String) sesion.getAttribute("realizarConsulta")).equals("true"))) {
            String esquemaConsultaPrevia;
            String consultaPrevia;

            if (request.getParameter("esquemaConsultaPrevia") == null && sesion.getAttribute("esquemaConsultaPrevia") == null) {
                throw new ServletException("El parámetro 'esquemaConsultaPrevia' no ha sido definido.");
            }
            if (request.getParameter("consultaPrevia") == null && sesion.getAttribute("consultaPrevia") == null) {
                throw new ServletException("El parámetro 'consultaPrevia' no ha sido definido.");
            }

            if (sesion.getAttribute("esquemaConsultaPrevia") != null) {
                esquemaConsultaPrevia = (String) sesion.getAttribute("esquemaConsultaPrevia");
            } else {
                esquemaConsultaPrevia = request.getParameter("esquemaConsultaPrevia");
            }

            if (sesion.getAttribute("consultaPrevia") != null) {
                consultaPrevia = (String) sesion.getAttribute("consultaPrevia");
            } else {
                consultaPrevia = request.getParameter("consultaPrevia");
            }

            if (request.getParameter("esquemaConsultaPrevia") != null && request.getParameter("esquemaConsultaPrevia").equals("DBCARTASP")) {
                consultaPrevia = "UpDate SPDOMICILIOS set ACTUAL=0 Where IDPERSONA=" + (String) session.getAttribute("IDPERSON");
            }

            basedatos.setEsquema(esquemaConsultaPrevia);
            basedatos.bdConexion();
            registros.setConexion(basedatos.getConexion());
            basedatos.bdEjesql(consultaPrevia);
            basedatos.bdCierra();

        }

        try {

            if (sesion.getAttribute("tablaBD") != null) {
                tablaBD = (String) sesion.getAttribute("tablaBD");
            } else {
                tablaBD = request.getParameter("tablaBD");
            }

            if (sesion.getAttribute("ESQUEMA") != null) {
                ESQUEMA = (String) sesion.getAttribute("ESQUEMA");
            } else {
                ESQUEMA = request.getParameter("ESQUEMA");
            }

            if (sesion.getAttribute("autoInc") != null) {
                autoInc = (String) sesion.getAttribute("autoInc");
            } else {
                autoInc = request.getParameter("autoInc");
            }

            basedatos.setEsquema(ESQUEMA);
            registros = new InterfaceRegistrosBDPool();
            while(basedatos.getConexion() == null){
                basedatos.bdConexion();                
            }
            registros.setConexion(basedatos.getConexion());
            clase = Class.forName(tablaBD);
            Tabla tabla = (Tabla) clase.newInstance();
            Tabla tablaSpecs = null;
            while(tablaSpecs == null){
                tablaSpecs = registros.setCaracteristicasTabla(tablaBD);
            }
            campos = tabla.getCampos();
            if (!accion.equalsIgnoreCase("C")) {
                tabla = registros.getRegistro(tablaBD, condicion);
                if (tabla == null) {
                    throw new Exception("Imposible obtener el registro de la tabla " + tablaBD + " (" + request.getParameter("condicion") + ")");
                }
            }
            int cont = 0;
            for (String campo : campos) {
                cont++;
                if (cont == 1) {
                    if (accion.equalsIgnoreCase("C")) {
                        if (autoInc.equals("true")) {
//                            int sec = registros.getSecuencia(tablaBD.split("\\.")[tablaBD.split("\\.").length - 1]);
//                            if (sec != null) {
//                                tabla.setCampo(campo, sec);
//                            } else {
//                                tabla.setCampo(campo, registros.getSiguienteSecuenciaDec(tablaBD, campo, "1=1"));
//                            }
                        } else {
                            try {
                                tabla.setCampo(campo, new Integer(request.getParameter(campo)));
                            } catch (Exception e) {
                                tabla.setCampo(campo, request.getParameter(campo).toUpperCase());
                            }
                        }
                    }
                } else {

                    if (!accion.equalsIgnoreCase("B")) {
                        if (campo.equals("fechacreacion") && accion.equalsIgnoreCase("C")) {
                            tabla.setCampo(campo, new Date());
                        } else if (campo.equals("fechamodificacion") && !accion.equalsIgnoreCase("C")) {
                            tabla.setCampo(campo, new Date());
                        } else if (campo.equals("usuariomodificacion") && !accion.equalsIgnoreCase("C")) {
                            tabla.setCampo(campo, idUser);
                        } else if (campo.equals("usuariocreacion") && accion.equalsIgnoreCase("C")) {
                            tabla.setCampo(campo, idUser);
                        } else {
                            if (tablaSpecs != null && tablaSpecs.isObligatorio(campo) && (request.getParameter(campo) == null
                                    || request.getParameter(campo).equals("")
                                    || request.getParameter(campo).equals(" ")
                                    || request.getParameter(campo).equals("undefined"))
                                    && accion.equals("C")) {

                                if (tablaSpecs.getTipo(campo).equals("String")) {
                                    tabla.setCampo(campo, "");
                                }
                                if (tablaSpecs.getTipo(campo).equals("BigDecimal")) {
                                    tabla.setCampo(campo, new BigDecimal("0"));
                                }
                                if (tablaSpecs.getTipo(campo).equals("Integer")) {
                                    tabla.setCampo(campo, 0);
                                }
                                if (tablaSpecs.getTipo(campo).equals("Date")) {
                                    //System.out.println("FECHA UNO--->");
                                    tabla.setCampo(campo, Util.convierteFecha("01/01/1900"));
                                }

                            } else {
//                                System.out.println("Campo: "+campo);
//                                System.out.println("Tipo: "+tablaSpecs.getTipo(campo));
                                if (request.getParameter(campo) == null && accion.equalsIgnoreCase("M")) {
                                } else {
                                    if (tablaSpecs.getTipo(campo).equals("Date")) {
                                        tabla.setCampo(campo, Util.convierteFecha(request.getParameter(campo)));
                                    } else if (tablaSpecs.getTipo(campo).equals("BigDecimal")) {
                                        try {
                                            tabla.setCampo(campo, new BigDecimal(request.getParameter(campo)));
                                        } catch (Exception ex) {
                                            tabla.setCampo(campo, new BigDecimal(0));
                                        }
                                    }else if (tablaSpecs.getTipo(campo).equals("Integer")) {
                                        try {
                                            tabla.setCampo(campo, Integer.parseInt(request.getParameter(campo)));
                                        } catch (Exception ex) {
                                            tabla.setCampo(campo, 0);
                                        }
                                    } else {
                                        if (accion.equalsIgnoreCase("C")) {
                                            try {
                                                tabla.setCampo(campo, request.getParameter(campo).toUpperCase());
                                            } catch (Exception e) {
                                                try {
                                                    tabla.setCampo(campo, "");                                                    
                                                } catch (Exception ex) {
                                                    tabla.setCampo(campo, Integer.parseInt(request.getParameter(campo)));
                                                }
                                            }
                                        } else {
                                            if (request.getParameter(campo) != null) {
                                                tabla.setCampo(campo, request.getParameter(campo).toUpperCase());
                                            }
                                        }

                                    }
                                }
                            }
                        }

                    } else {
                        if (campo.equals("estatusregistro") && accion.equalsIgnoreCase("B")) {
                            tabla.setCampo(campo, 99);
                        } else if (campo.equals("fechamodificacion") && !accion.equalsIgnoreCase("C")) {
                            tabla.setCampo(campo, new Date());
                        } else if (campo.equals("usuariomodificacion") && !accion.equalsIgnoreCase("C")) {
                            tabla.setCampo(campo, idUser);
                        }

                    }
                }
            }
            if (accion.equalsIgnoreCase("C")) {
                crearRegistro(ESQUEMA, tablaBD, tabla, campos[0], request.getSession());
                //ultimoAgregado = tabla.getCampos()[0];
                Object obj = tabla.getClass().getMethod("get" + tabla.getCampos()[0], new Class[0]).invoke(tabla, new Object[0]);
                if (obj != null) {
                    ultimoAgregado = obj.toString();
                }
                System.out.println("ultimoAgregado = " + ultimoAgregado);
            } else if (accion.equalsIgnoreCase("M")) {
                registros.modificar(tablaBD, tabla, condicion);
            } else if (accion.equalsIgnoreCase("B")) {
                registros.modificar(tablaBD, tabla, condicion);
            }
            mje = "La plaza fue creada exitosamente.";
        } catch (Exception e) {
            System.out.println("ManejadorBD -No fue posible crear el registro. " + e.getMessage() + " - Desde: " + referer);
            e.printStackTrace();
            mje = "No fue posible crear la plaza.";
        } finally {
            //basedatos.bdCierra();
        }

        if (request.getParameter("varSesion") != null && !request.getParameter("varSesion").trim().equals("") && !request.getParameter("accion").equalsIgnoreCase("M")) {
            request.getSession().setAttribute(request.getParameter("varSesion"), ultimoAgregado);
        }
        basedatos.bdCierra();
        redireccionar(pagRespuesta, request, response);
    }

    public String crearRegistro(String esquema, String tablaBD, Tabla tabla, String campoID, HttpSession sesion) {
        InterfaceBDPool basedatos = new InterfaceBDPool();
        basedatos.setEsquema(esquema);
        basedatos.bdConexion();
        while(basedatos.getConexion() == null){
            basedatos.bdConexion();            
        }
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        registros.setConexion(basedatos.getConexion());
        registros.agregar(tablaBD, tabla);
        Object obj = null;
        try {
            obj = tabla.getClass().getMethod("get" + tabla.getCampos()[0], new Class[0]).invoke(tabla, new Object[0]);
        } catch (Exception ex) {
            Logger.getLogger(ManejadorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (obj != null) {
            sesion.setAttribute("idTabla", obj.toString());
        }
        return "" +obj;
    }

    public void redireccionar(String path, HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("Entrando a redireccionar...");
        try {
            response.sendRedirect(path);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
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
        return "Servlet utilizado para realizar operaciones sobre la base de datos";
    }// </editor-fold>
}
