/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Interfacesbd.InterfaceBDPool;
import Interfacesbd.InterfaceRegistrosBDPool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author rjuarezja
 */
public class Reporter {

    Map<String, Object> map = new HashMap<String, Object>();
    
    private static String RUTA_REPORTES="C:/Users/rjuarezja/Documents/NetBeansProjects/SistemaWebEstatal/build/web/";
    

    private Reporter() {
    }

    public static Reporter newReport() {
        return new Reporter();
    }

    public Reporter idReporte(int idReporte) {
        map.put("idReporte", idReporte);
        return this;
    }

    public Reporter formato(String formato) {
        map.put("formato", formato);
        return this;
    }

    public Reporter params(Map<String, Object> ma) {
        Map<String, Object> params = (Map<String, Object>) map.get("params");
        if (params == null) {
            params =new HashMap<String,Object>();
            map.put("params",params );
        }
        params.putAll(ma);
        return this;
    }

    public Reporter params(String nombre, Object valor) {
        Map<String, Object> params = (Map<String, Object>) map.get("params");
        if (params == null) {
            params= new HashMap<String, Object>();
            map.put("params",params);
        }
        params.put(nombre, valor);
        return this;
    }

    public File crear() {
        File f = null;

        String sIdReporte = String.valueOf(map.get("idReporte"));
        String formato = String.valueOf(map.get("formato"));
        Map<String, Object> params = (Map<String, Object>) map.get("params");
        if(sIdReporte==null){
            sIdReporte=(String) params.get("idReporte");
        }
        if(formato==null){
            formato=(String) params.get("formato");
        }
        Map<String, Object> parametrosReporte = null;

        if (sIdReporte != null) {
            int idReporte = Integer.parseInt(sIdReporte);
            InterfaceBDPool bd = new InterfaceBDPool();
            InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
            bd.setEsquema("DBCATALOGO");
            bd.bdConexion();
            registros.setConexion(bd.getConexion());

            Catalogo.CAT_REPORTES reporte = (Catalogo.CAT_REPORTES) registros.getRegistro("Catalogo.CAT_REPORTES", "IDREPORTE = " + idReporte);
            bd.bdCierra();
            if (reporte != null) {
                if (!reporte.getREQ_PARAM().equals("0")) {
                    parametrosReporte = getParametros(idReporte, params);
                }
                
                log(String.valueOf(parametrosReporte));
                
                try {
                    String[] arRutaReporte = reporte.getRUTA_REPORTE().split("\\.");
                    String[] rutaReporte = reporte.getRUTA_REPORTE().split("\\.", arRutaReporte.length - 1);
                    String esquema = reporte.getESQUEMA();
                    String realPath = RUTA_REPORTES.concat("WEB-INF/classes");
                    for (String string : rutaReporte) {
                        realPath = realPath.concat("/").concat(string);
                    }

                    log(realPath);


                    bd.setEsquema(esquema);
                    bd.bdConexion();
                    parametrosReporte.put(JRParameter.REPORT_CONNECTION, bd.getConexion());
                    parametrosReporte.put(JRParameter.REPORT_LOCALE, new Locale("es", "MX"));

                    if ("xls".equalsIgnoreCase(formato)) {
                        boolean ignorarPag = params.get("ignorarPag") == null || "1".equals(params.get("ignorarPag"));
                        parametrosReporte.put(JRParameter.IS_IGNORE_PAGINATION, ignorarPag);
                        f = exportXls(realPath, parametrosReporte);
                    } else if ("pdf".equalsIgnoreCase(formato)) {
                        //parametrosReporte.put(JRParameter.IS_IGNORE_PAGINATION, false);
                        f = exportPDF(realPath, parametrosReporte);
                    }else{
                        throw new IllegalArgumentException("Formato no soportado:".concat(String.valueOf(formato)));
                    }
                } catch (Exception ex) {
                    log(ex);
                } finally {
                    bd.bdCierra();
                }
            }

        }


        return f;
    }

    private File exportPDF(String rutaReporte, Map<String, Object> parametros) throws JRException, IOException {
//        System.out.println("PDF");
//        System.out.println("Esquema: " + esquema);
        File reportFile = new File(rutaReporte);

        byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros);

        File f = File.createTempFile("reporte_", ".pdf");
        OutputStream os = new FileOutputStream(f);

        os.write(bytes, 0, bytes.length);
        os.close();
        return f;
    }

    private File exportXls(String rutaReporte, Map<String, Object> parametros) throws IOException, JRException {
        File reportFile = new File(rutaReporte);
        InterfaceBDPool bd = new InterfaceBDPool();
        JasperPrint print = JasperFillManager.fillReport(reportFile.getPath(), parametros, bd.getConexion());
        File archivoTemporal = File.createTempFile("reporte_", ".xls");
        OutputStream os = new FileOutputStream(archivoTemporal);
        JExcelApiExporter exporterXLS = new JExcelApiExporter();
        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, new Integer(65500));
        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, os);
        exporterXLS.exportReport();

        try {
            os.close();
        } catch (Exception ex) {
            log("no se puede cerrar el output stream");
        }


        bd.bdCierra();

        return archivoTemporal;


    }

    private Map<String, Object> getParametros(int idReporte, Map<String, Object> params) {
        Map<String, Object> parametros = new HashMap();

        InterfaceBDPool bd = new InterfaceBDPool();
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.setEsquema("DBCATALOGO");
        bd.bdConexion();
        registros.setConexion(bd.getConexion());

        ArrayList<Catalogo.CAT_FILTROSREPORTE> filtros = (ArrayList<Catalogo.CAT_FILTROSREPORTE>) registros.getListaRegistros("Catalogo.CAT_FILTROSREPORTE", "IDREPORTE = " + idReporte);

        if (filtros == null) {
            return null;
        }
        for (Iterator<Catalogo.CAT_FILTROSREPORTE> it = filtros.iterator(); it.hasNext();) {
            Catalogo.CAT_FILTROSREPORTE filtro = it.next();

            String nombreParametro = filtro.getNOMBREFILTRO();
            Object valorParametro = params.get(nombreParametro);

            String tipoFiltro = filtro.getTIPO();

            if (tipoFiltro.equals("F")) {
                Date fecha = Util.convierteFecha(String.valueOf(valorParametro));
                String fecha1900 = Util.convierteFechaBD(fecha, true);
                if (fecha1900 == null || "".equals(fecha1900)) {
                    parametros.put(nombreParametro, null);
                } else {
                    parametros.put(nombreParametro, fecha);
                }
            } else if (tipoFiltro.equals("I")) {
                try {
                    Integer i = Integer.parseInt(String.valueOf(valorParametro));
                    parametros.put(nombreParametro, i);
                } catch (Exception ex) {
                    Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, ex);
                    parametros.put(nombreParametro,null);
                }
            } else if (tipoFiltro.equals("D")) {
                try {
                    BigDecimal d = new BigDecimal(String.valueOf(valorParametro));
                    parametros.put(nombreParametro, d);
                } catch (Exception ex) {
                    Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, ex);
                    parametros.put(nombreParametro,null);
                }
            } else if (tipoFiltro.equals("B")) {
                String tmp = String.valueOf(valorParametro);
                Boolean b = Boolean.FALSE;
                if (tmp.equals("1") || tmp.equalsIgnoreCase("TRUE")) {
                    b = Boolean.TRUE;
                }
                parametros.put(nombreParametro, b);
            } else {
                parametros.put(nombreParametro, valorParametro);
            }
            //parametros.put(filtro.getNOMBREFILTRO(),);
        }
        bd.bdCierra();
        return parametros;
    }

    private void log(String msg) {
        Logger.getLogger(Reporter.class.getName()).info(msg);
    }

    private void log(Throwable t) {
        Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, t);
    }
}
