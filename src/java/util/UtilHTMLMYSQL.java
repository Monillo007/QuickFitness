/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Interfacesbd.InterfacBDMySQL;
import Interfacesbd.InterfaceRegistrosBDPool;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author gbustamanteol
 */
public class UtilHTMLMYSQL {

    public UtilHTMLMYSQL() {
    }

    public static String generarGridConsulta(String consulta, String p_campos, String p_encabezados,
            String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String onClick, String esquema, int iniciaEn, String tamano) {
        String[] encabezados;
        String[] campos;
        ResultSet datosTabla = null;
        InterfacBDMySQL bd;
        InterfaceRegistrosBDPool registros;
        bd = new InterfacBDMySQL();

        bd.setEsquema(esquema);
        registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());

        String tamanoDiv = tamano != null ? tamano : "250px";

        StringBuilder stGrid = new StringBuilder();
        if (!tamanoDiv.equals("-1")) {
            stGrid.append("<div style='overflow:auto; height:".concat(tamanoDiv).concat(";'> "));
        }
        stGrid.append("<table ");
        String onC = "cam();";
        if (onClick != null) {
            onC = onClick;
        }

        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");

        encabezados = p_encabezados.split(",");
        campos = p_campos.split(",");

        stGrid.append("<tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<td ".concat(opcionesTDEncabezado).concat(">&nbsp;</td>"));
        }

        //CONTADOR
        stGrid.append("<td ".concat(opcionesTDEncabezado));
        stGrid.append(">#</td>");

        for (String encabezado : encabezados) {
            stGrid.append("<td ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</td>"));
        }
        stGrid.append("</tr>");

        try {

            int cont = 1;

            if (iniciaEn != 1) {
                cont = iniciaEn;
            }

            datosTabla = registros.getRegistros(consulta);
            if (datosTabla != null) {
                while (datosTabla.next()) {
                    //System.out.println("registro = "+cont);
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");

                    if (seleccionable) {
                        stGrid.append("<td><input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\" onClick=\"").concat(onC).concat("\"/></td>"));
                    }

                    //CONTADOR
                    stGrid.append("<td align='center' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append("".concat("" + cont));
                    stGrid.append("</td>");

                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }

                        if (datosTabla.getObject(campos[i]) instanceof java.util.Date || datosTabla.getObject(campos[i]) instanceof java.sql.Date) {
                            java.util.Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(fecha + "").concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                        } else {
                            if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                                stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                        }
                    }
                    stGrid.append("</tr>");
                    cont++;
                }
            }
            datosTabla.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bd.bdCierra();
        stGrid.append("</table>");
        if (!tamanoDiv.equals("-1")) {
            stGrid.append("</div>");
        }
        return stGrid.toString();
    }
}
