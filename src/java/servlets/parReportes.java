/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import Catalogo.CAT_FILTROSREPORTE;
import Catalogo.CAT_OPCIONFILTROS;
import Interfacesbd.InterfaceBDPool;
import Interfacesbd.InterfaceRegistrosBDPool;
import util.UtilHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zeus
 */
public class parReportes extends HttpServlet {

    InterfaceBDPool bd = new InterfaceBDPool();
    InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
    ArrayList <CAT_OPCIONFILTROS> opciones;
    String idReporte = "";

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
        idReporte = request.getParameter("idReporte");
        String cadena = "";
        

        if(idReporte!=null && !idReporte.equals("")){
            
            bd.setEsquema("DBCATALOGO");
            bd.bdConexion(); registros.setConexion(bd.getConexion());
            cadena += "<input type='hidden' id='idReporte' name='idReporte' value='"+idReporte+"' />";
            Catalogo.CAT_REPORTES reporte = (Catalogo.CAT_REPORTES) registros.getRegistro("Catalogo.CAT_REPORTES", "IDREPORTE = "+idReporte);
            if(reporte!=null){
                ArrayList<Catalogo.CAT_FILTROSREPORTE> filtros = (ArrayList<Catalogo.CAT_FILTROSREPORTE>)registros.getListaRegistros("Catalogo.CAT_FILTROSREPORTE", "IDREPORTE = "+idReporte);                
                if(filtros!=null && !filtros.isEmpty()){
                    cadena += "<table>";
                    for (Iterator<CAT_FILTROSREPORTE> it = filtros.iterator(); it.hasNext();) {
                        CAT_FILTROSREPORTE fr = it.next();
                        cadena+="<tr><td>"+fr.getNOMBREFILTRO()+"</td>";
                        cadena+="<td>"+getCampoHTML(fr.getTIPO(), fr.getNOMBREFILTRO(), fr.getIDFILTRO())+"</td></tr>";
                    }
                    cadena += "</table>";
                    if(reporte.getRUTA_REPORTE().contains(".jsp")){
                        cadena += "<input type='button' value='Generar Reporte' onclick=\"evento('accion',document.getElementById('idReporte').value,'form1','../../../AdmonReportes');\">";
                    }else{
                        cadena += "<input type='button' value='Buscar' onclick=\"enviarFormulario('../../../BusquedasReporte','form1','resultados')\">";
                    }
                }else{
                    if(reporte.getRUTA_REPORTE().contains(".jsp")){
                        cadena += "<input type='button' value='Generar Reporte' onclick=\"evento('accion',document.getElementById('idReporte').value,'form1','../../../AdmonReportes');\">";
                    }else{
                        cadena += "<input type='button' value='Reporte (PDF)' onclick=\"evento('accion',document.getElementById('idReporte').value,'form1','../../../AdmonReportes');\">";
                        cadena += "<input type='button' value='Reporte (Excel)' onclick=\"evento2('accion',document.getElementById('idReporte').value,'form1','../../../AdmonReportes');\">";
                    }
                }
                cadena += "<div align='rigth'>";
                /*
                if(reporte.getRUTA_REPORTE().contains(".jsp")){
                    cadena += "<input type='button' value='Generar Reporte' onclick=\"eventoBoton('accion',document.getElementById('idReporte').value,'form1','../../../AdmonReportes');\">";
                }else{
                    cadena += "<input type='button' value='Generar Reporte PDF' onclick=\"eventoBoton('accion',document.getElementById('idReporte').value,'form1','../../../AdmonReportes');\">";
                }*/
                cadena += "</div>";
            }


        }

        try {
            out.print(cadena);
        } finally {
            out.close();
        }
    }

    private String getCampoHTML(String tipo, String nombre, BigDecimal idFiltro){
        String campo = "";
        if(tipo.equals("T")){
            campo += "<input type='text' name='T"+nombre+"' id='t"+nombre+"' value='' size='20%' class='CuadroTexto'/>";
        }else if(tipo.equals("N")){
            campo += "<input type='text' name='N"+nombre+"' id='n"+nombre+"' value='' size='20%' class='CuadroTexto'/>";
        }else if(tipo.equals("F")){
            campo += "<input type='text' name='F"+nombre+"' id='f"+nombre+"' value='' size='20%' class='CuadroTexto'/>";
            campo += "<img src='../../../Imagenes/gif/imgCalendario.gif' onclick=\"displayDatePicker('f"+nombre+"');\" align='absmiddle'>";
        }else if(tipo.equals("C")){
            opciones = (ArrayList<CAT_OPCIONFILTROS>)registros.getListaRegistros("Catalogo.CAT_OPCIONFILTROS", "IDREPORTE = "+idReporte+"  AND IDFILTROREPORTE = "+idFiltro);
            campo += "<select name=\"CTIPO\" id=\"cTIPO\">";
            for (Iterator<CAT_OPCIONFILTROS> it = opciones.iterator(); it.hasNext();) {
                CAT_OPCIONFILTROS cat_opcionfiltros = it.next();
                campo+= "<option value=\""+cat_opcionfiltros.getOPCION()+"\">"+cat_opcionfiltros.getDESCRIPCION()+"</option>";
            }
            campo += "</select>";
        }else if(tipo.equals("S")){
            if(idReporte.equals("26")){
                campo += UtilHTML.getComboRegistros("PER_HISTCONCENTRADOTEMP", "1=1", "IDHISTORICO", "IDHISTORICO", "FECHAHORA", null, "id='SSELECCIONA' name='SSELECCIONA'", false, "DBPERSONAL");
            }
        }
        return campo;
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

