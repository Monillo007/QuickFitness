/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Interfacesbd.InterfaceBDPool;
import Interfacesbd.InterfaceRegistrosBDPool;
import bd.cliente;
import bd.configuracion_sistema;
import bd.usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.ServletGenerico;

/**
 *
 * @author Luis Angel Pastrana
 */
public class SrvRecepcion extends ServletGenerico {

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
        Logger.getLogger(SrvRecepcion.class.getName()).info(String.valueOf(params));
        Logger.getLogger(SrvRecepcion.class.getName()).info("pathInfo = " + request.getPathInfo());

        String path = request.getPathInfo();

        System.out.println("path=" + path);
        try {
            if (path.equals("/doLogin")) {
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
                                redireccionar("/QuickFitness/recepcion/recepcion.jsp", request, response);
                                bd.bdCierra();
                            } else {
                                bd.bdCierra();
                                redireccionar("/QuickFitness/recepcion/login.jsp?mje=CONTRASEÑA INCORRECTA!", request, response);
                                session.invalidate();
                            }
                        } else if (usu.getestatusregistro() == 1) {
                            bd.bdCierra();
                            redireccionar("/QuickFitness/recepcion/login.jsp?mje=EL USUARIO ESTA DESHABILITADO!", request, response);
                            session.invalidate();
                        }
                    } else {
                        bd.bdCierra();
                        redireccionar("/QuickFitness/recepcion/login.jsp?mje=USUARIO INCORRECTO!", request, response);
                        session.invalidate();

                    }
                }
            } else if (path.equals("/doClienteLogin")) {
                String mensaje="";
                if (request.getParameter("idcliente") != null) {
                    InterfaceBDPool bd = new InterfaceBDPool();
                    bd.setEsquema("fitness");
                    InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
                    
                    bd.bdConexion();

                    if (bd.getConexion() != null) {
                        registros.setConexion(bd.getConexion());
                        cliente c = (cliente)registros.getRegistro("bd.cliente", "idcliente="+request.getParameter("idcliente"));
                        if(c != null){
                            mensaje = "Bienvenido "+c.getnombre();
                        }else{
                            mensaje = "El cliente indicado no existe!";
                        }
                    }
                    bd.bdCierra();
                } else {
                    mensaje = "No se recibieron los datos del cliente!";
                }
                out.print(mensaje);
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
