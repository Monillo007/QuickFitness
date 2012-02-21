/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Interfacesbd.InterfaceBDPool;
import Interfacesbd.InterfaceRegistrosBDPool;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import util.*;
import java.util.*;
import javax.servlet.http.HttpSession;

/**
 *
 * @author zeus
 */
public class AdmonReportes extends HttpServlet {

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
        InterfaceBDPool bd = new InterfaceBDPool();
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        Map parametros = null;
        ServletOutputStream os = response.getOutputStream();
        OutputStream out;
        String realPath = "";
        String esquema = "";

        /*if(!bd.getEsquema().equals("DBCATALOGO")){
        //bd.bdCierra();
        }*/

        bd.setEsquema("DBCATALOGO");
        bd.bdConexion();
        registros.setConexion(bd.getConexion());

        Catalogo.CAT_REPORTES reporte = (Catalogo.CAT_REPORTES) registros.getRegistro("Catalogo.CAT_REPORTES", "IDREPORTE = " + request.getParameter("idReporte"));
        bd.bdCierra();
        if (reporte != null) {
            if (reporte.getRUTA_REPORTE().contains(".jsp")) {
                String reportURL = reporte.getRUTA_REPORTE() + "" + getURLParametros(request.getParameter("idReporte"), request);
                try {
                    response.sendRedirect(reportURL);
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            } else {
                if (!reporte.getREQ_PARAM().equals("0")) {
                    parametros = getParametros(request.getParameter("idReporte"), request);
                }
                try {
                    //String[] rutaReporte = reporte.getRUTA_REPORTE().split("\\.", 4);
                    String[] rutaReporte = reporte.getRUTA_REPORTE().split("\\.", (reporte.getRUTA_REPORTE().split("\\.").length)-1);
                    esquema = reporte.getESQUEMA();
                    realPath = "WEB-INF//classes";
                    for (String string : rutaReporte) {
                        realPath += "//" + string;
                    }
                    System.out.println(realPath);

                    /*if(!bd.getEsquema().equals(esquema)){
                    //bd.bdCierra();
                    }*/

                    bd.setEsquema(esquema);
                    bd.bdConexion();
                    registros.setConexion(bd.getConexion());
                    if (parametros == null) {
                        parametros = new HashMap();
                    }
                    parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "MX"));

                    if (request.getParameter("formato").equalsIgnoreCase("xls")) {
                        boolean ignorarPag = request.getParameter("ignorarPag")==null || "1".equals(request.getParameter("ignorarPag"));
                        parametros.put(JRParameter.IS_IGNORE_PAGINATION, ignorarPag);
                        exportXls(response, realPath, parametros, esquema);
                    } else if (request.getParameter("formato").equalsIgnoreCase("pdf")) {
                        parametros.put(JRParameter.IS_IGNORE_PAGINATION, false);
                        exportPDF(response, realPath, parametros, esquema);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    bd.bdCierra();
                }
            }
        }

    }

    private void exportPDF(HttpServletResponse response, String realPath, Map parametros, String esquema) throws JRException, IOException {
        System.out.println("PDF");
        System.out.println("Esquema: " + esquema);
        OutputStream os = response.getOutputStream();
        File reportFile = new File(getServletContext().getRealPath(realPath));
        InterfaceBDPool bd = new InterfaceBDPool();
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        /*if(!bd.getEsquema().equals(esquema)){
        //bd.bdCierra();
        }*/

        bd.setEsquema(esquema);
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros, bd.getConexion());
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        bd.bdCierra();
        os.write(bytes, 0, bytes.length);
    }

    private void exportXls(HttpServletResponse response, String realPath, Map parametros, String esquema) throws IOException {
        System.out.println("XLS");
        try {

            File reportFile = new File(getServletContext().getRealPath(realPath));
            InterfaceBDPool bd = new InterfaceBDPool();
            InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
            bd.setEsquema(esquema);
            bd.bdConexion();
            registros.setConexion(bd.getConexion());
//            File fillTemporal = File.createTempFile("fillxls", null);
            JasperPrint print = JasperFillManager.fillReport(reportFile.getPath(), parametros, bd.getConexion());
//            JasperFillManager.fillReportToFile(reportFile.getPath(),fillTemporal.getPath(), parametros, bd.getConexion());
            OutputStream os = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=reporte.xls");
            response.setContentType("application/vnd.ms-excel");
//            File archivoTemporal = File.createTempFile("xlsr", null);
            //JRXlsExporter exporterXLS = new JRXlsExporter();
            JExcelApiExporter exporterXLS = new JExcelApiExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
//            exporterXLS.setParameter(JRXlsExporterParameter.INPUT_FILE_NAME, fillTemporal.getPath());
            //exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
//            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE, archivoTemporal);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, new Integer(65500));
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, os);
            exporterXLS.exportReport();


            //ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

//            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(archivoTemporal));
//            FileInputStream fstream = new FileInputStream(archivoTemporal);
//            response.setContentLength(fstream.available());
//            byte[] f = new byte[4096];
//            int cont = 0;
//            while((cont=fstream.read(f))!=-1){
//                os.write(f,0,cont);
//            }
            //response.setContentLength(arrayOutputStream.toByteArray().length);
//            archivoTemporal.s
//            os.flush();
//            os.close();
//            fstream.close();
//            archivoTemporal.delete();
            bd.bdCierra();

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    private Map getParametros(String report, HttpServletRequest request) {
        Map parametros = new HashMap();
        HttpSession session = request.getSession();

        /*if(!bd.getEsquema().equals("DBCATALOGO")){
        //bd.bdCierra();
        }*/
        InterfaceBDPool bd = new InterfaceBDPool();
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.setEsquema("DBCATALOGO");
        bd.bdConexion();
        registros.setConexion(bd.getConexion());

        ArrayList<Catalogo.CAT_FILTROSREPORTE> filtros = (ArrayList<Catalogo.CAT_FILTROSREPORTE>) registros.getListaRegistros("Catalogo.CAT_FILTROSREPORTE", "IDREPORTE = " + report);

        if (filtros == null) {
            System.out.println("filtros es null");
            return null;
        }
        for (Iterator<Catalogo.CAT_FILTROSREPORTE> it = filtros.iterator(); it.hasNext();) {
            Catalogo.CAT_FILTROSREPORTE filtro = it.next();
            String detalle = "";
            System.out.println("filtro: " + filtro.getNOMBREFILTRO() + " tipo: " + filtro.getTIPO() + " valor: " + request.getParameter(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO()));
            System.out.println("filtro (sesion): " + filtro.getNOMBREFILTRO() + " tipo: " + filtro.getTIPO() + " valor: " + session.getAttribute(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO()));

            if (filtro.getTIPO().equals("F")) {
                if (request.getParameter(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO()) == null || request.getParameter(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO()).replace(" ", "").equals("")) {
                    if (filtro.getNOMBREFILTRO().equalsIgnoreCase("AL")) {
                        parametros.put(filtro.getNOMBREFILTRO(), getFechaActual());
                    } else {
                        parametros.put(filtro.getNOMBREFILTRO(), "01-01-1900");
                    }
                } else {
                    parametros.put(filtro.getNOMBREFILTRO(), request.getParameter(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO()).replace("/", "-"));
                }
            } else {
                if (request.getParameter(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO()) != null) {
                    parametros.put(filtro.getNOMBREFILTRO(), request.getParameter(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO()).toUpperCase());
                } else if(session.getAttribute(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO())!=null) {
                    parametros.put(filtro.getNOMBREFILTRO(), session.getAttribute(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO()).toString().toUpperCase());
                }else{
                    parametros.put(filtro.getNOMBREFILTRO(), "");
                }
            }
            //parametros.put(filtro.getNOMBREFILTRO(),);
        }
        bd.bdCierra();
        return parametros;
    }

    private String getURLParametros(String report, HttpServletRequest request) {
        String parametros = "";

        /*if(!bd.getEsquema().equals("DBCATALOGO")){
        //bd.bdCierra();
        }*/
        InterfaceBDPool bd = new InterfaceBDPool();
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();

        bd.setEsquema("DBCATALOGO");
        bd.bdConexion();
        registros.setConexion(bd.getConexion());

        ArrayList<Catalogo.CAT_FILTROSREPORTE> filtros = (ArrayList<Catalogo.CAT_FILTROSREPORTE>) registros.getListaRegistros("Catalogo.CAT_FILTROSREPORTE", "IDREPORTE = " + report);
        int cant = 0;
        if (filtros != null) {
            for (Iterator<Catalogo.CAT_FILTROSREPORTE> it = filtros.iterator(); it.hasNext();) {
                cant++;
                if (cant == 1) {
                    parametros += "?";
                }
                if (cant > 1) {
                    parametros += "&";
                }
                Catalogo.CAT_FILTROSREPORTE filtro = it.next();
                System.out.println("filtro: " + filtro.getNOMBREFILTRO() + " tipo: " + filtro.getTIPO() + " valor: " + request.getParameter(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO()));
                parametros += filtro.getNOMBREFILTRO() + "=" + request.getParameter(filtro.getTIPO() + "" + filtro.getNOMBREFILTRO()).toUpperCase();
            }
        }
        System.out.println("Parametros url = " + parametros);
        bd.bdCierra();
        return parametros;
    }

    private String getFechaActual() {
        return Util.getFechaCorta(new Date()).replace("/", "-");
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
