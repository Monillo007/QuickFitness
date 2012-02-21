// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode fieldsfirst 
// Source File Name:   UtilHTML.java
package util;

import Interfacesbd.InterfaceBDPool;
import Interfacesbd.InterfaceRegistrosBDPool;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

// Referenced classes of package Utilerias:
//			Util
public class UtilHTML {

    public UtilHTML() {
    }

    public static String getComboRegistros(String tabla, String condicion, String orden, String campoID, String campoEtiqueta, String idSelect) {
        return getComboRegistros(tabla, condicion, orden, campoID, campoEtiqueta, null, (new StringBuilder()).append(" id='").append(idSelect).append("' ").toString());
    }

    public static String getComboRegistros(String tabla, String condicion, String campoID, String campoEtiqueta) {
        return getComboRegistros(tabla, condicion, null, campoID, campoEtiqueta, null, null);
    }

    public static String getComboRegistros(String tabla, String campoID, String campoEtiqueta) {
        return getComboRegistros(tabla, null, null, campoID, campoEtiqueta, null, null);
    }

    public static String getComboRegistros(String tabla, String condicion, String campoID, String campoEtiqueta, String opcionSeleccionada) {
        return getComboRegistros(tabla, condicion, null, campoID, campoEtiqueta, opcionSeleccionada, null);
    }

    public static String getComboRegistros(String tabla, String condicion, String orden, String campoID, String campoEtiqueta, String opcionSeleccionada, String caracteristicas) {
        return getComboRegistros(tabla, condicion, orden, campoID, campoEtiqueta, opcionSeleccionada, caracteristicas, false, "DBCATALOGO");
    }

    public static String getComboRegistros(String tabla, String condicion, String orden, String campoID, String campoEtiqueta, String opcionSeleccionada, String caracteristicas, String esquema) {
        return getComboRegistros(tabla, condicion, orden, campoID, campoEtiqueta, opcionSeleccionada, caracteristicas, false, esquema);
    }

    public static String getComboRegistros(String tabla, String condicion, String orden, String campoID, String campoEtiqueta, String opcionSeleccionada, String caracteristicas, boolean distinct,
            String esquema) {
        String nombreTabla[] = tabla.split("\\.");
        String nombre = "";
        String ids[] = null;
        String etiquetas[] = null;
        if (campoID.contains(",")) {
            ids = campoID.split(",");
        }
        if (campoEtiqueta.contains(",")) {
            etiquetas = campoEtiqueta.split(",");
        }
        if (nombreTabla.length == 1) {
            nombre = tabla;
        } else {
            nombre = nombreTabla[1];
        }
        StringBuilder combo = new StringBuilder();
        combo.append("<select ");
        if (caracteristicas != null && !caracteristicas.equals("")) {
            combo.append(" ".concat(caracteristicas).concat(" "));
        }
        combo.append(">");
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema("fitness");
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        while(bd.getConexion()==null){
            bd.bdConexion();            
        }
        registros.setConexion(bd.getConexion());
        ResultSet resultado = null;
        while(resultado == null){
            resultado = registros.getListaRegistrosJoin(tabla, condicion, orden, distinct, campoEtiqueta);
        }
        try {
            combo.append("<option value='0'>Selecciona...</option>");
            for (; resultado!= null && resultado.next(); combo.append("</option>")) {
                if (ids != null) {
                    String id = "";
                    String arr$[] = ids;
                    int len$ = arr$.length;
                    for (int i$ = 0; i$ < len$; i$++) {
                        String element = arr$[i$];
                        if (!id.trim().equals("")) {
                            id = id.concat("-");
                        }
                        id = id.concat((new StringBuilder()).append("").append(resultado.getString(element)).toString());
                    }

                    combo.append("<option value='".concat(id).concat("'"));
                } else {
                    combo.append("<option value='".concat(resultado.getString(campoID)).concat("'"));
                }
                if (opcionSeleccionada != null) {
                    if (campoID.contains(",")) {
                        ids = campoID.split(",");
                        if (resultado.getString(ids[ids.length - 1]).trim().equals(opcionSeleccionada)) {
                            combo.append(" SELECTED ");
                        }
                    } else if (resultado.getString(campoID).trim().equals(opcionSeleccionada)) {
                        combo.append(" SELECTED ");
                    }
                }
                combo.append(">");
                if (distinct) {
                    combo.append(resultado.getString(campoEtiqueta.split(",")[0]));
                } else if (campoEtiqueta.contains(",")) {
                    for (int i = 0; i < etiquetas.length; i++) {
                        String et = etiquetas[i];
                        String strResult = resultado.getString(et);
                        strResult = strResult == null ? "" : strResult;
                        combo.append(strResult.concat(" "));
                    }

                } else {
                    combo.append(resultado.getString(campoEtiqueta));
                }
            }

        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".getComboRegistros").log(Level.SEVERE, "Tabla:" + tabla + "  Esquema:" + esquema, e);
            return null;
        }
        combo.append("</select>");
        bd.bdCierra();
        return new String(combo);
    }

    public static String getComboRegistrosUnico(String tabla, String condicion, String orden, String campoID, String campoEtiqueta, String opcionSeleccionada, String caracteristicas) {
        return getComboRegistrosUnico(tabla, condicion, orden, campoID, campoEtiqueta, opcionSeleccionada, caracteristicas, "DBCATALOGO");
    }

    public static String getComboRegistrosUnico(String tabla, String condicion, String orden, String campoID, String campoEtiqueta, String opcionSeleccionada, String caracteristicas, String esquema) {
        String nombreTabla[] = tabla.split("\\.");
        String nombre = "";
        if (nombreTabla.length == 1) {
            nombre = tabla;
        } else {
            nombre = nombreTabla[1];
        }
        String combo = "<select ";
        if (caracteristicas != null && !caracteristicas.equals("")) {
            combo = (new StringBuilder()).append(combo).append(" ").append(caracteristicas).append(" ").toString();
        }
        combo = (new StringBuilder()).append(combo).append(">").toString();
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        ResultSet resultado = registros.getListaRegistrosJoin(tabla, condicion);
        try {
            combo = (new StringBuilder()).append(combo).append("<option value='-1'>Selecciona...</option>").toString();
            do {
                if (!resultado.next()) {
                    break;
                }
                if (opcionSeleccionada != null && resultado.getString(campoID).trim().equals(opcionSeleccionada)) {
                    combo = (new StringBuilder()).append(combo).append("<option value='").append(resultado.getString(campoID)).append("'").toString();
                    combo = (new StringBuilder()).append(combo).append(" SELECTED ").toString();
                    combo = (new StringBuilder()).append(combo).append(">").toString();
                    combo = (new StringBuilder()).append(combo).append(resultado.getString(campoEtiqueta)).toString();
                    combo = (new StringBuilder()).append(combo).append("</option>").toString();
                }
            } while (true);
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".getComboRegistrosUnico").log(Level.SEVERE, "Tabla:" + tabla + " - Condicion:" + condicion + " - Esquema:" + esquema, e);
            return null;
        }
        combo = (new StringBuilder()).append(combo).append("</select>").toString();
        bd.bdCierra();
        return combo;
    }

    public static String generarGrid(String tabla, String p_campos, String p_encabezados, String condicion, String orden, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros, boolean seleccionable, String campoID, String nombreRadio) {
        return generarGrid(tabla, p_campos, p_encabezados, condicion, orden, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, "DBCATALOGO");
    }

    public static String generarGrid(String tabla, String p_campos, String p_encabezados, String condicion, String orden, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros, boolean seleccionable, String campoID, String nombreRadio, String esquema, int ini,
            int fin) {
        return generarGrid(tabla, p_campos, p_encabezados, condicion, orden, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, esquema, ini, fin, null);
    }

    public static String generarGrid(String tabla, String p_campos, String p_encabezados, String condicion, String orden, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros, boolean seleccionable, String campoID, String nombreRadio, String esquema, int ini,
            int fin, String p_tamanos) {
        return generarGrid(tabla, p_campos, p_encabezados, condicion, orden, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, esquema, ini, fin, p_tamanos, false);
    }

    public static String generarGrid(String tabla, String p_campos, String p_encabezados, String condicion, String orden, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros, boolean seleccionable, String campoID, String nombreRadio, String esquema, int ini,
            int fin, String p_tamanos, boolean efecto) {
        String tamanos[] = null;
        int columna = 0;
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder();
        stGrid.append("<div class='DivGridContenido' id='elementos' name='elementos'>");
        stGrid.append("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String sqlP = (new StringBuilder()).append("SELECT ").append(p_campos).append(" FROM ").append(tabla).toString();
        if (condicion != null) {
            sqlP = (new StringBuilder()).append(sqlP).append(" WHERE ").append(condicion).toString();
        }
        if (orden != null) {
            sqlP = (new StringBuilder()).append(sqlP).append(" ORDER BY ").append(orden).toString();
        }
        double paginasReal = 0.0D;
        float paginasFloat = 0.0F;
        int contReg = bd.bdConreg(tabla, condicion);
        paginasReal = (double) contReg / ((Double.parseDouble((new StringBuilder()).append(fin).append("").toString()) - Double.parseDouble((new StringBuilder()).append(ini).append("").toString())) + 1.0D);
        paginasFloat = contReg / ((fin - ini) + 1);
        int totalPaginas = (int) paginasReal;
        int paginaActual = (fin + 1) / ((fin - ini) + 1);
        if (paginasReal > (double) totalPaginas) {
            totalPaginas++;
        }
        String sql = (new StringBuilder()).append("SELECT * FROM (SELECT ROWNUM rnum,a.* FROM (").append(sqlP).append(") a WHERE ROWNUM <= ").append(fin).append(") WHERE rnum > ").append(ini).toString();
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        if (p_tamanos != null) {
            tamanos = p_tamanos.split(",");
        }
        stGrid.append("<thead><tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado));
            if (tamanos != null) {
                stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                columna++;
            }
            stGrid.append(">Sel.</th>");
        }
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            if (tamanos != null) {
                stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                columna++;
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</th>"));
        }

        stGrid.append("</tr></thead>");
        try {
            ResultSet datosTabla = registros.getRegistros(sql);
            int cont = ((fin - ini) + 1) * (paginaActual - 1) + 1;
            if (datosTabla != null) {
                stGrid.append("<tbody>");
                while (datosTabla.next()) {
                    columna = 0;
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td align='center' ");
                        if (tamanos != null) {
                            stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                            columna++;
                        }
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        stGrid.append(">");
                        stGrid.append("<input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\" onClick=\"cam();\" "));
                        stGrid.append("/></td>");
                    }
                    stGrid.append("<td align='center' width='30px' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append((new StringBuilder()).append("").append(cont).toString());
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if (tamanos != null) {
                            stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                            columna++;
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }
                stGrid.append("</tbody>");
            }
            datosTabla.close();
        } catch (IndexOutOfBoundsException e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGrid").log(Level.SEVERE, "La cantidad de tama\361os indicada no corresponde con la cantidad de columnas.", e);

        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGrid").log(Level.SEVERE, "Excepcion", e);
        }
        stGrid.append("</table></div>");
        stGrid.append("<br/>");
        if (esquema.equals("DBPERSONAL")) {
            if (ini != 0) {
                stGrid.append("<img onmouseover=\"this.src='../../Imagenes/png/btnAnterior1.png'\" onmouseout=\"this.src='../../Imagenes/png/btnAnterior.png'\" src='../../Imagenes/png/btnAnterior.png' onclick='buscarAnterior();' alt='Buscar Anterior'>");
            }
            stGrid.append((new StringBuilder()).append(" - Pagina ").append(paginaActual).append(" de ").append(totalPaginas).append(". - ").toString());
            if (fin < contReg) {
                stGrid.append("<img onmouseover=\"this.src='../../Imagenes/png/btnSiguiente1.png'\" onmouseout=\"this.src='../../Imagenes/png/btnSiguiente.png'\" src='../../Imagenes/png/btnSiguiente.png' onclick='buscarSiguiente();' alt='Buscar Siguiente'>");
            }
        } else {
            if (ini != 0) {
                stGrid.append("<img onmouseover=\"this.src='../../../Imagenes/png/btnAnterior1.png'\" onmouseout=\"this.src='../../../Imagenes/png/btnAnterior.png'\" src='../../../Imagenes/png/btnAnterior.png' onclick='buscarAnterior();' alt='Buscar Anterior'>");
            }
            stGrid.append((new StringBuilder()).append(" - Pagina ").append(paginaActual).append(" de ").append(totalPaginas).append(". - ").toString());
            if (fin < contReg) {
                stGrid.append("<img onmouseover=\"this.src='../../../Imagenes/png/btnSiguiente1.png'\" onmouseout=\"this.src='../../../Imagenes/png/btnSiguiente.png'\" src='../../../Imagenes/png/btnSiguiente.png' onclick='buscarSiguiente();' alt='Buscar Siguiente'>");
            }
        }
        bd.bdCierra();
        return stGrid.toString();
    }

    public static String generarGridConsulta(String sqlConsulta, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String esquema, int ini, int fin, String p_tamanos) {
        return generarGridConsulta(sqlConsulta, p_campos, p_encabezados, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, esquema, ini, fin, p_tamanos, null);
    }

    public static String generarGridConsulta(String sqlConsulta, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String esquema, int ini, int fin, String p_tamanos,
            String seleccionado) {
        String tamanos[] = null;
        int columna = 0;
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder();
        stGrid.append("<div class='DivGridContenido' id='elementos' name='elementos'>");
        stGrid.append("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String sqlP = sqlConsulta;
        double paginasReal = 0.0D;
        float paginasFloat = 0.0F;
        int contReg = registros.bdConreg(sqlConsulta);
        paginasReal = (double) contReg / ((Double.parseDouble((new StringBuilder()).append(fin).append("").toString()) - Double.parseDouble((new StringBuilder()).append(ini).append("").toString())) + 1.0D);
        paginasFloat = contReg / ((fin - ini) + 1);
        int totalPaginas = (int) paginasReal;
        int paginaActual = fin / (fin - ini);
        if (paginasReal > (double) totalPaginas) {
            totalPaginas++;
        }
        String sql = (new StringBuilder()).append("SELECT * FROM (SELECT ROWNUM rnum,a.* FROM (").append(sqlP).append(") a WHERE ROWNUM <= ").append(fin).append(") WHERE rnum >= ").append(ini).toString();
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        if (p_tamanos != null) {
            tamanos = p_tamanos.split(",");
        }
        stGrid.append("<thead>");
        stGrid.append("<tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado));
            if (tamanos != null) {
                stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                columna++;
            }
            stGrid.append(">Sel.</th>");
        }
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            if (tamanos != null) {
                stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                columna++;
            }
            stGrid.append(">");
            stGrid.append((new StringBuilder()).append(encabezado).append("</th>").toString());
        }

        stGrid.append("</tr>");
        stGrid.append("</thead>");
        try {
            ResultSet datosTabla = registros.getRegistros(sql);
            int cont = ini;
            if (datosTabla != null) {
                stGrid.append("<tbody>");
                while (datosTabla.next()) {
                    columna = 0;
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td align='center' ");
                        if (tamanos != null) {
                            stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                            columna++;
                        }
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        stGrid.append(">");
                        if (seleccionado != null && !seleccionado.equals("") && seleccionado.equals(datosTabla.getString(campoID))) {
                            stGrid.append("<input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\" onClick=\"cam();\" checked='checked' "));
                        } else {
                            stGrid.append("<input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\" onClick=\"cam();\" "));
                        }
                        stGrid.append("/></td>");
                    }
                    stGrid.append("<td align='center' width='30px' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append((new StringBuilder()).append("").append(cont).toString());
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if (tamanos != null) {
                            stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                            columna++;
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }
                stGrid.append("</tbody>");
            }
            datosTabla.close();
        } catch (IndexOutOfBoundsException e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridConsulta").log(Level.SEVERE, "La cantidad de tama\361os indicada no corresponde con la cantidad de columnas.", e);
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridConsulta").log(Level.SEVERE, "Excepcion", e);
        }
        stGrid.append("</table></div>");
        stGrid.append("<br/>");
        if (esquema.equals("DBPERSONAL")) {
            if (paginaActual > 1) {
                stGrid.append("<img onmouseover=\"this.src='../../Imagenes/png/btnAnterior1.png'\" onmouseout=\"this.src='../../Imagenes/png/btnAnterior.png'\" src='../../Imagenes/png/btnAnterior.png' onclick='buscarAnterior();' alt='Buscar Anterior'>");
            }
            stGrid.append((new StringBuilder()).append(" - Pagina ").append(paginaActual).append(" de ").append(totalPaginas).append(". - ").toString());
            if (fin < contReg) {
                stGrid.append("<img onmouseover=\"this.src='../../Imagenes/png/btnSiguiente1.png'\" onmouseout=\"this.src='../../Imagenes/png/btnSiguiente.png'\" src='../../Imagenes/png/btnSiguiente.png' onclick='buscarSiguiente();' alt='Buscar Siguiente'>");
            }
        } else {
            if (paginaActual > 1) {
                stGrid.append("<img onmouseover=\"this.src='../../../Imagenes/png/btnAnterior1.png'\" onmouseout=\"this.src='../../../Imagenes/png/btnAnterior.png'\" src='../../../Imagenes/png/btnAnterior.png' onclick='buscarAnterior();' alt='Buscar Anterior'>");
            }
            stGrid.append((new StringBuilder()).append(" - Pagina ").append(paginaActual).append(" de ").append(totalPaginas).append(". - ").toString());
            if (fin < contReg) {
                stGrid.append("<img onmouseover=\"this.src='../../../Imagenes/png/btnSiguiente1.png'\" onmouseout=\"this.src='../../../Imagenes/png/btnSiguiente.png'\" src='../../../Imagenes/png/btnSiguiente.png' onclick='buscarSiguiente();' alt='Buscar Siguiente'>");
            }
        }
        bd.bdCierra();
        return stGrid.toString();
    }

    public static String generarGridConsultaTotal(String sqlConsulta, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String esquema, int totalRegistros, int ini, int fin,
            String p_tamanos) {
        return generarGridConsultaTotal(sqlConsulta, p_campos, p_encabezados, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, esquema, totalRegistros, ini, fin, p_tamanos, "cam()");
    }

    public static String generarGridConsultaTotal(String sqlConsulta, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String esquema, int totalRegistros, int ini, int fin,
            String p_tamanos, String cam) {
        String tamanos[] = null;
        int columna = 0;
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder();
        stGrid.append("<div class='DivGridContenido' id='elementos' name='elementos'>");
        stGrid.append("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String sqlP = sqlConsulta;
        double paginasReal = 0.0D;
        float paginasFloat = 0.0F;
        int contReg = totalRegistros;
        paginasReal = (double) contReg / ((Double.parseDouble((new StringBuilder()).append(fin).append("").toString()) - Double.parseDouble((new StringBuilder()).append(ini).append("").toString())) + 1.0D);
        paginasFloat = contReg / ((fin - ini) + 1);
        int totalPaginas = (int) paginasReal;
        int paginaActual = fin / (fin - ini);
        if (paginasReal > (double) totalPaginas) {
            totalPaginas++;
        }
        String sql = (new StringBuilder()).append("SELECT * FROM (SELECT ROWNUM rnum,a.* FROM (").append(sqlP).append(") a WHERE ROWNUM <= ").append(fin).append(") WHERE rnum >= ").append(ini).toString();
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        if (p_tamanos != null) {
            tamanos = p_tamanos.split(",");
        }
        stGrid.append("<thead>");
        stGrid.append("<tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado));
            if (tamanos != null) {
                stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                columna++;
            }
            stGrid.append(">Sel.</th>");
        }
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            if (tamanos != null) {
                stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                columna++;
            }
            stGrid.append(">");
            stGrid.append((new StringBuilder()).append(encabezado).append("</th>").toString());
        }

        stGrid.append("</tr>");
        stGrid.append("</thead>");
        try {
            ResultSet datosTabla = registros.getRegistros(sql);
            int cont = ini;
            if (datosTabla != null) {
                stGrid.append("<tbody>");
                while (datosTabla.next()) {
                    columna = 0;
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td align='center' ");
                        if (tamanos != null) {
                            stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                            columna++;
                        }
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        stGrid.append(">");
                        stGrid.append("<input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat((new StringBuilder()).append("\" onClick=\"").append(cam).append("\" ").toString()));
                        stGrid.append("/></td>");
                    }
                    stGrid.append("<td align='center' width='30px' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append((new StringBuilder()).append("").append(cont).toString());
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if (tamanos != null) {
                            stGrid.append(" style=\"width:".concat(tamanos[columna]).concat("px;\" "));
                            columna++;
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }
                stGrid.append("</tbody>");
            }
            datosTabla.close();
        } catch (IndexOutOfBoundsException e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridConsultaTotal").log(Level.SEVERE, "La cantidad de tama\361os indicada no corresponde con la cantidad de columnas.", e);
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridConsultaTotal").log(Level.SEVERE, "Excepcion", e);
        }
        stGrid.append("</table></div>");
        stGrid.append("<br/>");
        if (paginaActual > 1) {
            stGrid.append("<img onmouseover=\"this.src='../../../Imagenes/png/btnAnterior1.png'\" onmouseout=\"this.src='../../../Imagenes/png/btnAnterior.png'\" src='../../../Imagenes/png/btnAnterior.png' onclick='buscarAnterior();' alt='Buscar Anterior'>");
        }
        stGrid.append((new StringBuilder()).append(" - Pagina ").append(paginaActual).append(" de ").append(totalPaginas).append(". - ").toString());
        if (fin < contReg) {
            stGrid.append("<img onmouseover=\"this.src='../../../Imagenes/png/btnSiguiente1.png'\" onmouseout=\"this.src='../../../Imagenes/png/btnSiguiente.png'\" src='../../../Imagenes/png/btnSiguiente.png' onclick='buscarSiguiente();' alt='Buscar Siguiente'>");
        }
        bd.bdCierra();
        return stGrid.toString();
    }

    public static String generarGrid(String tabla, String p_campos, String p_encabezados, String condicion, String orden, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros, boolean seleccionable, String campoID, String nombreRadio, String esquema) {
        //HARDCODE ESTILO TABLA
        opcionesTDEncabezado = opcionesTDEncabezado.equals("class='Encabezado'") ? "" : opcionesTDEncabezado;
        opcionesTDRegistros = opcionesTDRegistros.equals("class='contenidoovertr'") ? "" : opcionesTDRegistros;
        opcionesTabla = opcionesTabla.concat(" class='gradient-style'");
        opcionesTabla = opcionesTabla.replace("width='", "style='width:");
        //FIN HARCODE

        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String sql = (new StringBuilder()).append("SELECT ").append(p_campos).append(" FROM ").append(tabla).toString();
        if (condicion != null) {
            sql = (new StringBuilder()).append(sql).append(" WHERE ").append(condicion).toString();
        }
        if (orden != null) {
            sql = (new StringBuilder()).append(sql).append(" ORDER BY ").append(orden).toString();
        }
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        stGrid.append("<thead><tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado).concat(">&nbsp;</th>"));
        }
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</th>"));
        }

        stGrid.append("</tr></thead>");
        try {
            ResultSet datosTabla = registros.getListaRegistrosJoin(tabla, condicion, orden);
            int cont = 1;
            if (datosTabla != null) {
                stGrid.append("<tbody>");
                while (datosTabla.next()) {
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td><input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\" onClick=\"cam();\"/></td>"));
                    }
                    stGrid.append("<td align='center' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append((new StringBuilder()).append("").append(cont).toString());
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                if (fechaS.contains("00:00:00")) {
                                    stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                                } else {
                                    stGrid.append(">".concat(Util.convierteFechaHoraMostrar(fechaS)).concat("</td>"));
                                }
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }
                stGrid.append("</tbody>");
            }
            datosTabla.close();
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGrid").log(Level.SEVERE, "Excepcion", e);
        }
        stGrid.append("</table>");
        bd.bdCierra();
        return stGrid.toString();
    }

    public static String generarGridResultSet(ResultSet datosTabla, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio) {
        StringBuilder stGrid = new StringBuilder("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        stGrid.append("<thead>");
        stGrid.append("<tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado).concat(">&nbsp;</th>"));
        }
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</th>"));
        }

        stGrid.append("</tr>");
        stGrid.append("</thead>");
        try {
            int cont = 1;
            if (datosTabla != null) {
                stGrid.append("<tbody>");
                while (datosTabla.next()) {
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td><input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\" onClick=\"cam();\"/></td>"));
                    }
                    stGrid.append("<td align='center' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append((new StringBuilder()).append("").append(cont).toString());
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }
                stGrid.append("</tbody>");
            }
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridResultSet").log(Level.SEVERE, "Excepcion", e);
        }
        stGrid.append("</table>");
        return stGrid.toString();
    }

    public static String generarGridList(List datosTabla, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio) {
        return generarGridList(datosTabla, p_campos, p_encabezados, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, null, 1, "cam()");
    }

    public static String generarGridList(List datosTabla, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, int iniciaEn) {
        return generarGridList(datosTabla, p_campos, p_encabezados, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, null, iniciaEn, "cam()");
    }

    public static String generarGridList(List datosTabla, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String opcionesDiv, int iniciaEn) {
        return generarGridList(datosTabla, p_campos, p_encabezados, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, opcionesDiv, iniciaEn, "cam()");
    }

    public static String generarGridList(List datosTabla, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String opcionesDiv, int iniciaEn, String onClick) {
        StringBuilder stGrid = new StringBuilder();
        stGrid.append("<div ");
        if (opcionesDiv != null) {
            stGrid.append(opcionesDiv.concat(">"));
        } else {
            stGrid.append("<div>");
        }
        stGrid.append("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        stGrid.append("<thead><tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado).concat(">&nbsp;</th>"));
        }
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</th>"));
        }

        stGrid.append("</tr></thead>");
        try {
            int cont = iniciaEn;
            if (!datosTabla.isEmpty()) {
                stGrid.append("<tbody> ");
                for (Iterator i$ = datosTabla.iterator(); i$.hasNext();) {
                    Map reg = (Map) i$.next();
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td><input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat((new StringBuilder()).append("").append(reg.get(campoID)).toString()).concat((new StringBuilder()).append("\" onClick=\"").append(onClick).append("\"/></td>").toString()));
                    }
                    stGrid.append("<td align='center' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append((new StringBuilder()).append("").append(cont).toString());
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if ((reg.get(campos[i]) instanceof Date) || (reg.get(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = (Date) reg.get(campos[i]);
                            String fechaS = (new StringBuilder()).append("").append(reg.get(campos[i])).toString();
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (reg.get(campos[i]) != null && !(new StringBuilder()).append("").append(reg.get(campos[i])).toString().equalsIgnoreCase("null")) {
                            stGrid.append(">".concat((new StringBuilder()).append("").append(reg.get(campos[i])).toString()).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }

                stGrid.append("</tbody> ");
            }
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridList").log(Level.SEVERE, "Excepcion", e);
        }
        stGrid.append("</table></div>");
        return stGrid.toString();
    }

    public static String generarGridConsulta(String consulta, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String onClick, String esquema) {
        return generarGridConsulta(consulta, p_campos, p_encabezados, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, onClick, esquema, 1, null);
    }

    public static String generarGridConsulta(String consulta, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String onClick, String esquema, String tamano) {
        return generarGridConsulta(consulta, p_campos, p_encabezados, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, onClick, esquema, 1, tamano);
    }

    public static String generarGridConsulta(String consulta, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String onClick, String esquema, int iniciaEn) {
        return generarGridConsulta(consulta, p_campos, p_encabezados, opcionesTabla, opcionesTREncabezado, opcionesTDEncabezado, opcionesTRRegistros, opcionesTDRegistros, seleccionable, campoID, nombreRadio, onClick, esquema, iniciaEn, null);
    }

    public static String generarGridConsulta(String consulta, String p_campos, String p_encabezados, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String onClick, String esquema, int iniciaEn, String tamano) {
        //HARDCODE ESTILO TABLA
        opcionesTDEncabezado = opcionesTDEncabezado.equals("class='Encabezado'") ? "" : opcionesTDEncabezado;
        opcionesTDRegistros = opcionesTDRegistros.equals("class='contenidoovertr'") ? "" : opcionesTDRegistros;
        opcionesTabla = opcionesTabla.concat(" class='gradient-style'");
        opcionesTabla = opcionesTabla.replace("width='", "style='width:");
        //FIN HARCODE

        ResultSet datosTabla = null;
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        while(bd.getConexion() == null){
            bd.bdConexion();            
        }
        registros.setConexion(bd.getConexion());
        String tamanoDiv = tamano == null ? "250px" : tamano;
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
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        stGrid.append("<thead><tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado).concat(">&nbsp;</th>"));
        }
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</th>"));
        }

        stGrid.append("</tr></thead>");
        try {
            int cont = 1;
            if (iniciaEn != 1) {
                cont = iniciaEn;
            }
            int contError =0;
            while(datosTabla == null){
                datosTabla = registros.getRegistros(consulta);
                contError ++;
                if(contError == 3)break;
            }
            
            if (datosTabla != null) {
                stGrid.append("<tbody> ");
                while (datosTabla.next()) {
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td><input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\" onClick=\"").concat(onC).concat("\"/></td>"));
                    }
                    stGrid.append("<td align='center' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append("".concat((new StringBuilder()).append("").append(cont).toString()));
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }
                stGrid.append("</tbody> ");
            }
            datosTabla.close();
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridConsulta").log(Level.SEVERE, "Excepcion", e);
        }
        bd.bdCierra();
        stGrid.append("</table>");
        if (!tamanoDiv.equals("-1")) {
            stGrid.append("</div>");
        }
        return stGrid.toString();
    }

    public static String generarGrid(String tabla, String p_campos, String p_encabezados, String condicion, String orden, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros, boolean seleccionable, String campoID, String nombreRadio, String onClic, String esquema) {
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String sql = (new StringBuilder()).append("SELECT ").append(p_campos).append(" FROM ").append(tabla).toString();
        if (condicion != null) {
            sql = (new StringBuilder()).append(sql).append(" WHERE ").append(condicion).toString();
        }
        if (orden != null) {
            sql = (new StringBuilder()).append(sql).append(" ORDER BY ").append(orden).toString();
        }
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        stGrid.append("<thead>");
        stGrid.append("<tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado).concat(">&nbsp;</th>"));
        }
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</th>"));
        }

        stGrid.append("</tr>");
        stGrid.append("</thead>");
        try {
            ResultSet datosTabla = registros.getListaRegistrosJoin(tabla, condicion, orden);
            int cont = 1;
            if (datosTabla != null) {
                stGrid.append("<tbody>");
                while (datosTabla.next()) {
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td><input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\" onClick=\"").concat(onClic).concat("\"/></td>"));
                    }
                    stGrid.append("<td align='center' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append((new StringBuilder()).append("").append(cont).toString());
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }
                stGrid.append("</tbody>");
            }
            datosTabla.close();
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGrid").log(Level.SEVERE, "Excepcion", e);
        }
        bd.bdCierra();
        stGrid.append("</table>");
        return stGrid.toString();
    }

    public static String generarGridVarios(String tabla, String p_campos, String p_encabezados, String condicion, String orden, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros, boolean seleccionable, String campoID, String nombreRadio, String esquema, String funcionValida) {
        String valida = "cam();";
        if (funcionValida != null) {
            valida = funcionValida;
        }
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String sql = (new StringBuilder()).append("SELECT ").append(p_campos).append(" FROM ").append(tabla).toString();
        if (condicion != null) {
            sql = (new StringBuilder()).append(sql).append(" WHERE ").append(condicion).toString();
        }
        if (orden != null) {
            sql = (new StringBuilder()).append(sql).append(" ORDER BY ").append(orden).toString();
        }
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        stGrid.append("<thead>");
        stGrid.append("<tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado).concat(">&nbsp;</th>"));
        }
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</th>"));
        }

        stGrid.append("</tr>");
        stGrid.append("</thead>");
        try {
            ResultSet datosTabla = registros.getListaRegistrosJoin(tabla, condicion, orden);
            int cont = 1;
            if (datosTabla != null) {
                stGrid.append("<tbody> ");
                while (datosTabla.next()) {
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td><input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\" onClick=\"").concat(valida).concat("\"/></td>"));
                    }
                    stGrid.append("<td align='center' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append((new StringBuilder()).append("").append(cont).toString());
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }
                stGrid.append("<tbody> ");
                stGrid.append("</table>");
            } else {
                stGrid.append("<font color='red'>No hay datos capturados</font><br/>");
            }
            if (cont == 1) {
                stGrid.append("<font color='red'>No hay datos capturados</font><br/>");
            }
            datosTabla.close();
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridVarios").log(Level.SEVERE, "Excepcion", e);
        }
        bd.bdCierra();
        return stGrid.toString();
    }

    public static String generarGridCheck(String tabla, String p_campos, String p_encabezados, String condicion, String orden, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros, boolean seleccionable, String campoID, String nombreCheck, String esquema) {
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String sql = (new StringBuilder()).append("SELECT ").append(p_campos).append(" FROM ").append(tabla).toString();
        if (condicion != null) {
            sql = (new StringBuilder()).append(sql).append(" WHERE ").append(condicion).toString();
        }
        if (orden != null) {
            sql = (new StringBuilder()).append(sql).append(" ORDER BY ").append(orden).toString();
        }
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        stGrid.append("<thead><tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado).concat(">&nbsp;</th>"));
        }
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</th>"));
        }

        stGrid.append("</tr></thead>");
        try {
            ResultSet datosTabla = registros.getListaRegistrosJoin(tabla, condicion, orden);
            int cont = 1;
            if (datosTabla != null) {
                stGrid.append("<tbody> ");
                while (datosTabla.next()) {
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td><input type=\"checkbox\" name=\"".concat(nombreCheck).concat((new StringBuilder()).append("_").append(cont).append("\" id='").toString()).concat(nombreCheck).concat((new StringBuilder()).append("_").append(cont).append("' value=\"").toString()).concat(datosTabla.getString(campoID)).concat("\" onClick=\"seleccionaCheck(this);\"/></td>"));
                    }
                    stGrid.append("<td align='center' ");
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        stGrid.append(opcionesTDRegistros);
                    }
                    stGrid.append(">");
                    stGrid.append((new StringBuilder()).append("").append(cont).toString());
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }
                stGrid.append("</tbody> ");
            }
            datosTabla.close();
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridCheck").log(Level.SEVERE, "Excepcion", e);
        }
        stGrid.append("</table>");
        bd.bdCierra();
        return stGrid.toString();
    }

    public static String generarGridCheckConsulta(String consulta, String p_campos, String p_encabezados, String orden, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado, String opcionesTRRegistros,
            String opcionesTDRegistros, boolean seleccionable, String campoID, String nombreCheck, String esquema, int inicio, int fin) {
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String sql = (new StringBuilder()).append("SELECT ROWNUM,RESULTADOS2.* FROM(SELECT ROWNUM as R1,RESULTADO1.* FROM (").append(consulta).append(") RESULTADO1 WHERE ROWNUM <= ").append(fin).append(") RESULTADOS2 WHERE RESULTADOS2.R1 >= ").append(inicio).toString();
        Logger.getLogger(UtilHTML.class.getName() + ".generarGridCheckConsulta").log(Level.INFO, (new StringBuilder()).append("SQL: ").append(sql).toString());
        String encabezados[] = (new StringBuilder()).append("#,").append(p_encabezados).toString().split(",");
        String campos[] = p_campos.split(",");
        stGrid.append("<thead>");
        stGrid.append("<tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado).concat(">&nbsp;</th>"));
        }
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</th>"));
        }

        stGrid.append("</tr>");
        stGrid.append("</thead>");
        try {
            ResultSet datosTabla = registros.getRegistros(sql);
            int cont = inicio - 1;
            if (datosTabla != null) {
                stGrid.append("<tbody>");
                for (; datosTabla.next(); stGrid.append("</tr>")) {
                    cont++;
                    stGrid.append("<tr ");
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td><input type=\"checkbox\" name=\"".concat(nombreCheck).concat((new StringBuilder()).append("_").append(cont).append("\" id='").toString()).concat(nombreCheck).concat((new StringBuilder()).append("_").append(cont).append("' value=\"").toString()).concat(datosTabla.getString(campoID)).concat("\" onClick=\"seleccionaCheck(this);\"/></td>"));
                    }
                    stGrid.append((new StringBuilder()).append("<td ").append(opcionesTDRegistros == null ? "" : opcionesTDRegistros).append(">".concat((new StringBuilder()).append("").append(cont).toString()).concat("</td>")).toString());
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                }
                stGrid.append("</tbody>");
            }

            datosTabla.close();
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridCheckConsulta").log(Level.SEVERE, "Excepcion", e);
        }
        stGrid.append("</table>");
        bd.bdCierra();
        return stGrid.toString();
    }

    public static String getComboRegistros(ArrayList datos, Object opcionSeleccionada, String caracteristicas) {
        String combo = "";
        combo = (new StringBuilder()).append(combo).append("<select ").toString();
        if (caracteristicas != null) {
            combo = (new StringBuilder()).append(combo).append(caracteristicas).toString();
        }
        combo = (new StringBuilder()).append(combo).append(">").toString();
        combo = (new StringBuilder()).append(combo).append("<option value='0'>Selecciona...</option>").toString();
        int contador = 1;
        for (Iterator it = datos.iterator(); it.hasNext(); contador++) {
            Object object = it.next();
            if (object instanceof String) {
                if (opcionSeleccionada != null && ((String) opcionSeleccionada).equals((String) object)) {
                    combo = (new StringBuilder()).append(combo).append("<option value='").append(contador).append("' SELECTED>").append((String) object).append("</option>").toString();
                } else {
                    combo = (new StringBuilder()).append(combo).append("<option value='").append(contador).append("'>").append((String) object).append("</option>").toString();
                }
            }
            if (!(object instanceof Integer)) {
                continue;
            }
            if (opcionSeleccionada != null && ((Integer) opcionSeleccionada).intValue() == ((Integer) object).intValue()) {
                combo = (new StringBuilder()).append(combo).append("<option value='").append(contador).append("' SELECTED>").append((Integer) object).append("</option>").toString();
            } else {
                combo = (new StringBuilder()).append(combo).append("<option value='").append(contador).append("'>").append((Integer) object).append("</option>").toString();
            }
        }

        combo = (new StringBuilder()).append(combo).append("</select>").toString();
        return combo;
    }

    public static String getComboRegistros(String datos[], String opcionSeleccionada, String caracteristicas) {
        String combo = "";
        combo = (new StringBuilder()).append(combo).append("<select ").toString();
        if (caracteristicas != null) {
            combo = (new StringBuilder()).append(combo).append(caracteristicas).toString();
        }
        combo = (new StringBuilder()).append(combo).append(">").toString();
        combo = (new StringBuilder()).append(combo).append("<option value='0'>Selecciona...</option>").toString();
        int contador = 1;
        String arr$[] = datos;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String string = arr$[i$];
            if (contador == Integer.parseInt(opcionSeleccionada)) {
                combo = (new StringBuilder()).append(combo).append("<option value='").append(contador).append("' SELECTED>").append(string).append("</option>").toString();
            } else {
                combo = (new StringBuilder()).append(combo).append("<option value='").append(contador).append("'>").append(string).append("</option>").toString();
            }
            contador++;
        }

        combo = (new StringBuilder()).append(combo).append("</select>").toString();
        return combo;
    }

    public static String getComboRegistrosString(String datos[], String opcionSeleccionada, String caracteristicas) {
        String combo = "";
        combo = (new StringBuilder()).append(combo).append("<select ").toString();
        if (caracteristicas != null) {
            combo = (new StringBuilder()).append(combo).append(caracteristicas).toString();
        }
        combo = (new StringBuilder()).append(combo).append(">").toString();
        int contador = 1;
        String arr$[] = datos;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String string = arr$[i$];
            if (opcionSeleccionada != null && string.equals(opcionSeleccionada)) {
                combo = (new StringBuilder()).append(combo).append("<option value='").append(string).append("' SELECTED>").append(string).append("</option>").toString();
            } else {
                combo = (new StringBuilder()).append(combo).append("<option value='").append(string).append("'>").append(string).append("</option>").toString();
            }
            contador++;
        }

        combo = (new StringBuilder()).append(combo).append("</select>").toString();
        return combo;
    }

    /**
     * Método que se utiliza para generar una tabla de datos de bd resaltando algunas filas
     * @param sqlConsulta La consulta
     * @param p_campos Los campos a incluir
     * @param p_encabezados Los encabezados
     * @param opcionesTabla
     * @param opcionesTREncabezado
     * @param opcionesTDEncabezado
     * @param opcionesTRRegistros
     * @param opcionesTDRegistros
     * @param seleccionable
     * @param campoID
     * @param nombreRadio
     * @param esquema
     * @param ini
     * @param fin
     * @param campoResalta
     * @param valorResalta
     * @param estiloResalta
     * @return
     */
    public static String generarGridConsultaResaltados(String sqlConsulta, String p_campos, String p_encabezados,
            String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String esquema,
            int ini, int fin, String campoResalta, String valorResalta, String estiloResalta) {

        String[] encabezados;
        String[] campos;
        String[] tamanos = null;
        int columna = 0;
        String[] valoresResaltados = valorResalta.split(",");
        ResultSet datosTabla;
        InterfaceBDPool bd;
        InterfaceRegistrosBDPool registros;
        bd = new InterfaceBDPool();

        bd.setEsquema(esquema);
        registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder();

        stGrid.append("<div class='DivGridContenido' id='elementos' name='elementos'>");
        stGrid.append("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");

        String sqlP = sqlConsulta;

        double paginasReal = 0.0;
        float paginasFloat = 0;

        int contReg = registros.bdConreg(sqlConsulta);
        paginasReal = contReg / ((Double.parseDouble(fin + "") - Double.parseDouble(ini + "")) + 1);
        paginasFloat = contReg / ((fin - ini) + 1);

        int totalPaginas = (int) (paginasReal);
        int paginaActual = (int) ((fin) / ((fin - ini)));
        if (paginasReal > totalPaginas) {
            totalPaginas++;
        }

        String sql = "SELECT * FROM (SELECT ROWNUM rnum,a.* FROM (" + sqlP + ") a WHERE ROWNUM <= " + fin + ") WHERE rnum >= " + ini;
        encabezados = p_encabezados.split(",");
        campos = p_campos.split(",");
        stGrid.append("<thead>");
        stGrid.append("<tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado));
            if (tamanos != null) {
                stGrid.append(" style='width:".concat(tamanos[columna]).concat("px;' "));
                columna++;
            }
            stGrid.append(">Sel.</th>");
        }

        //CONTADOR
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");

        for (String encabezado : encabezados) {
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            if (tamanos != null) {
                stGrid.append(" style='width:".concat(tamanos[columna]).concat("px;' "));
                columna++;
            }
            stGrid.append(">");
            stGrid.append(encabezado + "</th>");
        }
        stGrid.append("</tr>");
        stGrid.append("</thead>");

        try {
            datosTabla = registros.getRegistros(sql);
            int cont = ini;

            if (datosTabla != null) {
                stGrid.append("<tbody>");
                while (datosTabla.next()) {

                    columna = 0;
                    stGrid.append("<tr ");
                    String estiloTr = "";
                    String estiloTd = "";
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        estiloTr = opcionesTRRegistros;
                    }
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        estiloTd = opcionesTDRegistros;
                    }
                    if (datosTabla.getString(campoResalta) != null) {
                        for (String resalta : valoresResaltados) {
                            if (datosTabla.getString(campoResalta).equals(resalta)) {
                                if (estiloResalta != null && !estiloResalta.equals("")) {
                                    //stGrid.append(estiloResalta);
                                    estiloTr = estiloResalta;
                                    estiloTd = estiloResalta;
                                }
                                break;
                            }
                        }
                    }

                    stGrid.append(estiloTr);
                    stGrid.append(">");


                    if (seleccionable) {
                        stGrid.append("<td><input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\"/></td>"));
                    }

                    //CONTADOR
                    stGrid.append("<td align='center' width='30px' ");

                    stGrid.append(estiloTd);
                    stGrid.append(">");
                    stGrid.append("" + cont);
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        stGrid.append(estiloTd);
//                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
//                            stGrid.append(opcionesTDRegistros);
//                        }
                        if (tamanos != null) {
                            stGrid.append(" style='width:".concat(tamanos[columna]).concat("px;' "));
                            columna++;
                        }

                        if (datosTabla.getObject(campos[i]) instanceof java.util.Date || datosTabla.getObject(campos[i]) instanceof java.sql.Date) {
                            java.util.Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
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
                stGrid.append("</tbody>");
            } else {
            }
            datosTabla.close();
        } catch (IndexOutOfBoundsException e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridConsultaResaltados").log(Level.SEVERE, "La cantidad de tamaños indicada no corresponde con la cantidad de columnas.", e);
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridConsultaResaltados").log(Level.SEVERE, "Excepcion", e);
        }

        stGrid.append("</table></div>");

        stGrid.append("<br/>");

        if (paginaActual > 1) {
            stGrid.append("<img onmouseover='this.src='../../../Imagenes/png/btnAnterior1.png'' onmouseout='this.src='../../../Imagenes/png/btnAnterior.png'' src='../../../Imagenes/png/btnAnterior.png' onclick='buscarAnterior();' alt='Buscar Anterior'>");
        }
        stGrid.append(" - Pagina " + paginaActual + " de " + (totalPaginas) + ". - ");
        if (fin < contReg) {
            stGrid.append("<img onmouseover='this.src='../../../Imagenes/png/btnSiguiente1.png'' onmouseout='this.src='../../../Imagenes/png/btnSiguiente.png'' src='../../../Imagenes/png/btnSiguiente.png' onclick='buscarSiguiente();' alt='Buscar Siguiente'>");
        }

        bd.bdCierra();
        return stGrid.toString();
    }

    /***
     * Método utilizado para obtener una tabla HTML a partir de una consulta en la BD,
     * resaltado registros que cumplan con cierta condición.
     * @param tabla La(s) tabla(s) sobre la(s) cual(es) se harÃ¡ la consulta.
     * @param p_campos Los campos de la(s) tabla a obtener separados por comas.
     * @param p_encabezados Los nombres de los encabezados de la tabla separados por comas
     * @param condicion La condiciÃ³n para generar la tabla (el where de la consulta).
     * @param orden El orden a aplicar.
     * @param opcionesTabla Las opciones a aplicar en la tabla (estilos, id, longitud,etc).
     * @param opcionesTREncabezado Las opciones a aplicar en la etiqueta TR de los encabezados (estilos, id, longitud,etc).
     * @param opcionesTDEncabezado Las opciones a aplicar en la etiqueta TD de los encabezados (estilos, id, longitud,etc).
     * @param opcionesTRRegistros Las opciones a aplicar en la etiqueta TR de los registros (estilos, id, longitud,etc).
     * @param opcionesTDRegistros Las opciones a aplicar en la etiqueta TD de los registros (estilos, id, longitud,etc).
     * @param seleccionable Indica si aparecerÃ¡ un radio botÃ³n para seleccionar la fila.
     * @param campoID El campo que será el identificador para el registro.
     * @param nombreRadio El nombre del radiobotón que servirá para seleccionar el registro
     * @param esquema El esquema de la base de datos que contiene la tabla.
     * @param campoResaltados El campo que se utilizará para identificar los registros a resaltar.
     * @param valorResaltados El valor(es) que debe tener el campo para que sea resaltado.
     * @param estiloResaltados El estilo a aplicar sobre los registros resaltados.
     * @return
     */
    //HARDCODEADO PARA QUE FUNCIONE
    public static String generarGridConsultaResaltados(int as, String sqlConsulta, String p_campos, String p_encabezados,
            String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros,
            boolean seleccionable, String campoID, String nombreRadio, String esquema,
            int ini, int fin, String campoResalta, String valorResalta, String estiloResalta) {

        String[] encabezados;
        String[] campos;
        String[] tamanos = null;
        int columna = 0;
        String[] valoresResaltados = valorResalta.split(",");
        ResultSet datosTabla;
        InterfaceBDPool bd;
        InterfaceRegistrosBDPool registros;
        bd = new InterfaceBDPool();

        bd.setEsquema(esquema);
        registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder();

        stGrid.append("<div class='DivGridContenido' id='elementos' name='elementos'>");
        stGrid.append("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");

        String sqlP = sqlConsulta;

        double paginasReal = 0.0;
        float paginasFloat = 0;

        int contReg = registros.bdConreg(sqlConsulta);
        paginasReal = contReg / ((Double.parseDouble(fin + "") - Double.parseDouble(ini + "")) + 1);
        paginasFloat = contReg / ((fin - ini) + 1);

        int totalPaginas = (int) (paginasReal);
        int paginaActual = (int) ((fin) / ((fin - ini)));
        if (paginasReal > totalPaginas) {
            totalPaginas++;
        }

        String sql = "SELECT * FROM (SELECT ROWNUM rnum,a.* FROM (" + sqlP + ") a WHERE ROWNUM <= " + fin + ") WHERE rnum >= " + ini;
        encabezados = p_encabezados.split(",");
        campos = p_campos.split(",");
        stGrid.append("<thead>");
        stGrid.append("<tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<th ".concat(opcionesTDEncabezado));
            if (tamanos != null) {
                stGrid.append(" style='width:".concat(tamanos[columna]).concat("px;' "));
                columna++;
            }
            stGrid.append(">Sel.</th>");
        }

        //CONTADOR
        stGrid.append("<th ".concat(opcionesTDEncabezado));
        stGrid.append(">#</th>");

        for (String encabezado : encabezados) {
            stGrid.append("<th ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            if (tamanos != null) {
                stGrid.append(" style='width:".concat(tamanos[columna]).concat("px;' "));
                columna++;
            }
            stGrid.append(">");
            stGrid.append(encabezado + "</th>");
        }
        stGrid.append("</tr>");
        stGrid.append("</thead>");

        try {
            datosTabla = registros.getRegistros(sql);
            int cont = ini;

            if (datosTabla != null) {
                stGrid.append("<tbody>");
                while (datosTabla.next()) {

                    columna = 0;
                    stGrid.append("<tr ");
                    String estiloTr = "";
                    String estiloTd = "";
                    if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        estiloTr = opcionesTRRegistros;
                    }
                    if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                        estiloTd = opcionesTDRegistros;
                    }
                    if (datosTabla.getString(campoResalta) != null) {
                        for (String resalta : valoresResaltados) {
                            if (datosTabla.getString(campoResalta).equals(resalta)) {
                                if (estiloResalta != null && !estiloResalta.equals("")) {
                                    //stGrid.append(estiloResalta);
                                    estiloTr = estiloResalta;
                                    estiloTd = estiloResalta;
                                }
                                break;
                            }
                        }
                    }

                    stGrid.append(estiloTr);
                    stGrid.append(">");


                    if (seleccionable) {
                        stGrid.append("<td><input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\"/></td>"));
                    }

                    //CONTADOR
                    stGrid.append("<td align='center' width='30px' ");

                    stGrid.append(estiloTd);
                    stGrid.append(">");
                    stGrid.append("" + cont);
                    stGrid.append("</td>");
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        stGrid.append(estiloTd);
//                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
//                            stGrid.append(opcionesTDRegistros);
//                        }
                        if (tamanos != null) {
                            stGrid.append(" style='width:".concat(tamanos[columna]).concat("px;' "));
                            columna++;
                        }

                        if (datosTabla.getObject(campos[i]) instanceof java.util.Date || datosTabla.getObject(campos[i]) instanceof java.sql.Date) {
                            java.util.Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
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
                stGrid.append("</tbody>");
            } else {
            }
            datosTabla.close();
        } catch (IndexOutOfBoundsException e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridConsultaResaltados").log(Level.SEVERE, "La cantidad de tamaños indicada no corresponde con la cantidad de columnas.", e);
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridConsultaResaltados").log(Level.SEVERE, "Excepcion", e);
        }

        stGrid.append("</table></div>");

        stGrid.append("<br/>");

        if (paginaActual > 1) {
            stGrid.append("<img onmouseover='this.src='../../../Imagenes/png/btnAnterior1.png'' onmouseout='this.src='../../../Imagenes/png/btnAnterior.png'' src='../../../Imagenes/png/btnAnterior.png' onclick='buscarAnterior();' alt='Buscar Anterior'>");
        }
        stGrid.append(" - Pagina " + paginaActual + " de " + (totalPaginas) + ". - ");
        if (fin < contReg) {
            stGrid.append("<img onmouseover='this.src='../../../Imagenes/png/btnSiguiente1.png'' onmouseout='this.src='../../../Imagenes/png/btnSiguiente.png'' src='../../../Imagenes/png/btnSiguiente.png' onclick='buscarSiguiente();' alt='Buscar Siguiente'>");
        }

        bd.bdCierra();
        return stGrid.toString();
    }

    public static String generarGridResaltados(String tabla, String p_campos, String p_encabezados, String condicion, String orden, String opcionesTabla, String opcionesTREncabezado, String opcionesTDEncabezado,
            String opcionesTRRegistros, String opcionesTDRegistros, boolean seleccionable, String campoID, String nombreRadio, String esquema, String campoResaltados,
            String valorResaltados, String estiloResaltados) {
        String valoresResaltados[] = valorResaltados.split(",");
        InterfaceBDPool bd = new InterfaceBDPool();
        bd.setEsquema(esquema);
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        StringBuilder stGrid = new StringBuilder("<table ");
        if (opcionesTabla != null && !opcionesTabla.equals("")) {
            stGrid.append(opcionesTabla);
        }
        stGrid.append(">");
        String sql = (new StringBuilder()).append("SELECT ").append(p_campos).append(" FROM ").append(tabla).toString();
        if (condicion != null) {
            sql = (new StringBuilder()).append(sql).append(" WHERE ").append(condicion).toString();
        }
        if (orden != null) {
            sql = (new StringBuilder()).append(sql).append(" ORDER BY ").append(orden).toString();
        }
        String encabezados[] = p_encabezados.split(",");
        String campos[] = p_campos.split(",");
        stGrid.append("<tr ");
        if (opcionesTREncabezado != null && !opcionesTREncabezado.equals("")) {
            stGrid.append(opcionesTREncabezado);
        }
        stGrid.append(">");
        if (seleccionable) {
            stGrid.append("<td ".concat(opcionesTDEncabezado).concat(">&nbsp;</td>"));
        }
        String arr$[] = encabezados;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String encabezado = arr$[i$];
            stGrid.append("<td ");
            if (opcionesTDEncabezado != null && !opcionesTDEncabezado.equals("")) {
                stGrid.append(opcionesTDEncabezado);
            }
            stGrid.append(">");
            stGrid.append(encabezado.concat("</td>"));
        }

        stGrid.append("</tr>");
        try {
            ResultSet datosTabla = registros.getRegistros(sql);
            int cont = 0;
            if (datosTabla != null) {
                while (datosTabla.next()) {
                    stGrid.append("<tr ");
                    if (datosTabla.getString(campoResaltados) != null) {
                        arr$ = valoresResaltados;
                        len$ = arr$.length;
                        int i$ = 0;
                        do {
                            if (i$ >= len$) {
                                break;
                            }
                            String resalta = arr$[i$];
                            if (datosTabla.getString(campoResaltados).equals(resalta)) {
                                if (estiloResaltados != null && !estiloResaltados.equals("")) {
                                    stGrid.append(estiloResaltados);
                                }
                                break;
                            }
                            i$++;
                        } while (true);
                    } else if (opcionesTRRegistros != null && !opcionesTRRegistros.equals("")) {
                        stGrid.append(opcionesTRRegistros);
                    }
                    stGrid.append(">");
                    if (seleccionable) {
                        stGrid.append("<td><input type=\"radio\" name=\"".concat(nombreRadio).concat("\" id='").concat(nombreRadio).concat("' value=\"").concat(datosTabla.getString(campoID)).concat("\" onClick=\"cam();\"/></td>"));
                    }
                    for (int i = 0; i < campos.length; i++) {
                        stGrid.append("<td ");
                        if (opcionesTDRegistros != null && !opcionesTDRegistros.equals("")) {
                            stGrid.append(opcionesTDRegistros);
                        }
                        if ((datosTabla.getObject(campos[i]) instanceof Date) || (datosTabla.getObject(campos[i]) instanceof java.sql.Date)) {
                            Date fecha = datosTabla.getDate(campos[i]);
                            String fechaS = datosTabla.getString(campos[i]);
                            if (fecha != null && !fechaS.contains("1900")) {
                                stGrid.append(">".concat(Util.convierteFechaMostrar(fecha)).concat("</td>"));
                            } else {
                                stGrid.append(">&nbsp;</td>");
                            }
                            continue;
                        }
                        if (datosTabla.getString(campos[i]) != null && !datosTabla.getString(campos[i]).equalsIgnoreCase("null")) {
                            stGrid.append(">".concat(datosTabla.getString(campos[i])).concat("</td>"));
                        } else {
                            stGrid.append(">&nbsp;</td>");
                        }
                    }

                    stGrid.append("</tr>");
                    cont++;
                }
            }
            datosTabla.close();
        } catch (Exception e) {
            Logger.getLogger(UtilHTML.class.getName() + ".generarGridResaltados").log(Level.SEVERE, "Excepcion", e);
        }
        stGrid.append("</table>");
        bd.bdCierra();
        return stGrid.toString();
    }

    public static String getComboAnnio(int annioInicial, String opcionesHTML, int annioSeleccionado) {
        String combo = "<select ";
        combo = (new StringBuilder()).append(combo).append(opcionesHTML).toString();
        combo = (new StringBuilder()).append(combo).append(">").toString();
        if (annioInicial == 0) {
            annioInicial = 2006;
        }
        Calendar fechaActual = Calendar.getInstance();
        int annioActual = fechaActual.get(1);
        if (annioSeleccionado == 0) {
            annioSeleccionado = annioActual;
        }
        combo = (new StringBuilder()).append(combo).append("\n     <option value='0'>Selecciona...</option>").toString();
        for (int i = annioInicial; i <= annioActual; i++) {
            if (i == annioSeleccionado) {
                combo = (new StringBuilder()).append(combo).append("\n     <option value='").append(i).append("' SELECTED>").append(i).append("</option>").toString();
            } else {
                combo = (new StringBuilder()).append(combo).append("\n     <option value='").append(i).append("'>").append(i).append("</option>").toString();
            }
        }

        combo = (new StringBuilder()).append(combo).append("\n</select>").toString();
        return combo;
    }

    public static String getComboAnnio() {
        return getComboAnnio(0, "", 0);
    }

    public static String getComboAnnio(int annioInicial) {
        return getComboAnnio(annioInicial, "", 0);
    }

    public static String getComboHoras(String opcionesHTML, int horaSeleccionada) {
        String combo = "<select ";
        combo = (new StringBuilder()).append(combo).append(opcionesHTML).toString();
        combo = (new StringBuilder()).append(combo).append(">").toString();
        for (int i = 0; i <= 23; i++) {
            if (i == horaSeleccionada) {
                combo = (new StringBuilder()).append(combo).append("\n     <option value='").append(i > 9 ? ((Object) (Integer.valueOf(i))) : ((Object) ((new StringBuilder()).append("0").append(i).toString()))).append("' SELECTED>").append(i > 9 ? ((Object) (Integer.valueOf(i))) : ((Object) ((new StringBuilder()).append("0").append(i).toString()))).append("</option>").toString();
            } else {
                combo = (new StringBuilder()).append(combo).append("\n     <option value='").append(i > 9 ? ((Object) (Integer.valueOf(i))) : ((Object) ((new StringBuilder()).append("0").append(i).toString()))).append("'>").append(i > 9 ? ((Object) (Integer.valueOf(i))) : ((Object) ((new StringBuilder()).append("0").append(i).toString()))).append("</option>").toString();
            }
        }

        combo = (new StringBuilder()).append(combo).append("\n</select>").toString();
        return combo;
    }

    public static String getComboMinutos(String opcionesHTML, int minutoSeleccionado) {
        String combo = "<select ";
        combo = (new StringBuilder()).append(combo).append(opcionesHTML).toString();
        combo = (new StringBuilder()).append(combo).append(">").toString();
        for (int i = 0; i <= 59; i++) {
            if (i == minutoSeleccionado) {
                combo = (new StringBuilder()).append(combo).append("\n     <option value='").append(i > 9 ? ((Object) (Integer.valueOf(i))) : ((Object) ((new StringBuilder()).append("0").append(i).toString()))).append("' SELECTED>").append(i > 9 ? ((Object) (Integer.valueOf(i))) : ((Object) ((new StringBuilder()).append("0").append(i).toString()))).append("</option>").toString();
            } else {
                combo = (new StringBuilder()).append(combo).append("\n     <option value='").append(i > 9 ? ((Object) (Integer.valueOf(i))) : ((Object) ((new StringBuilder()).append("0").append(i).toString()))).append("'>").append(i > 9 ? ((Object) (Integer.valueOf(i))) : ((Object) ((new StringBuilder()).append("0").append(i).toString()))).append("</option>").toString();
            }
        }

        combo = (new StringBuilder()).append(combo).append("\n</select>").toString();
        return combo;
    }

    public static String getComboMeses(int mesSeleccionado, String opcionesHTML) {
        String combo = "";
        String meses[] = {
            "Selecciona...", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE",
            "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"
        };
        Calendar fechaActual = Calendar.getInstance();
        int mesSelected = fechaActual.get(2) + 1;
        if (mesSeleccionado != 0) {
            mesSelected = mesSeleccionado;
        }
        if (mesSeleccionado == -1) {
            mesSelected = 0;
        }
        combo = (new StringBuilder()).append(combo).append("<select ").toString();
        combo = (new StringBuilder()).append(combo).append(opcionesHTML).toString();
        combo = (new StringBuilder()).append(combo).append(">").toString();
        int i = 0;
        String arr$[] = meses;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String mes = arr$[i$];
            if (mesSelected == i) {
                combo = (new StringBuilder()).append(combo).append("\n     <option value='").append(i).append("' SELECTED>").append(mes).append("</option>").toString();
            } else {
                combo = (new StringBuilder()).append(combo).append("\n     <option value='").append(i).append("'>").append(mes).append("</option>").toString();
            }
            i++;
        }

        combo = (new StringBuilder()).append(combo).append("\n</select>").toString();
        return combo;
    }

    public static String getComboMeses() {
        return getComboMeses(0, "");
    }

    public static String getComboMeses(String opcionesHTML) {
        return getComboMeses(0, opcionesHTML);
    }

    public static String generaCampoFecha(String idCampo, String value, boolean readonly, boolean requerido) {
        String respuesta = "";
        respuesta = (new StringBuilder()).append(respuesta).append("<input type='text' class='CuadroTextoSL").append(requerido ? " required " : "").append("' name='").append(idCampo).append("' id='").append(idCampo).append("' value='").append(value).append("' size='12'").append(readonly ? " readonly='true' " : "").append(" />").toString();
        respuesta = (new StringBuilder()).append(respuesta).append("<img src='../../../Imagenes/gif/imgCalendario.gif' alt='CALENDARIO' name='boton_").append(idCampo).append("'  id='boton").append(idCampo).append("' style='cursor: pointer; border: 1px solid;' align='bottom' title='Selecci&oacute;n Fecha' />").toString();
        respuesta = (new StringBuilder()).append(respuesta).append("<script type='text/javascript'>").toString();
        respuesta = (new StringBuilder()).append(respuesta).append("Calendar.setup({").toString();
        respuesta = (new StringBuilder()).append(respuesta).append("   inputField     :    '").append(idCampo).append("',     ").toString();
        respuesta = (new StringBuilder()).append(respuesta).append("   ifFormat     :     '%d/%m/%Y',     ").toString();
        respuesta = (new StringBuilder()).append(respuesta).append("  button     :    'boton_").append(idCampo).append("'     ").toString();
        respuesta = (new StringBuilder()).append(respuesta).append("});").toString();
        respuesta = (new StringBuilder()).append(respuesta).append("</script>").toString();
        return respuesta;
    }
}
