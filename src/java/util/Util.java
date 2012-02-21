
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Interfacesbd.InterfaceBDPool;
import Interfacesbd.InterfaceRegistrosBDPool;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author joseantonio
 */
public class Util {

    public Util() {
    }

    /***
     * Devuelve la fecha y hora en formato ddMMyyyy_kkmmss
     * @param fecha La fecha que se desea convertir
     * @return Una cadena de texto con la fecha en el formato especificado
     */
    public static String getFechaHoraCorta(Date fecha) {
        SimpleDateFormat forfec = new SimpleDateFormat("ddMMyyyy_kkmmss");
        return forfec.format(fecha);
    }

    /***
     * Devuelve la fecha y hora en formato dd/MM/yyyy kk:mm:ss
     * @param fecha La fecha que se desea convertir
     * @return Una cadena de texto con la fecha en el formato especificado
     */
    public static String getFechaHora(Date fecha) {
        SimpleDateFormat forfec = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        return forfec.format(fecha);
    }
    
    public static String convierteFechaBDMySQL(Date p_fecha) {
        if (p_fecha == null) {
            p_fecha = new Date();
        }
        SimpleDateFormat forfec = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return forfec.format(p_fecha);
    }

    public static String convierteSoloFechaBDMySQL(Date p_fecha) {
        if (p_fecha == null) {
            p_fecha = new Date();
        }
        SimpleDateFormat forfec = new SimpleDateFormat("yyyy-MM-dd");
        return forfec.format(p_fecha);
    }

    /***
     * Obtiene la fecha y hora actuales del servidor de bases de datos.
     * @return Una variable tipo java.util.Date con la fecha y hora actuales o null si ha ocurrido un error.
     */
    public static Date getNowDate() {
        Date fechor;
        InterfaceBDPool bd = new InterfaceBDPool();
        InterfaceRegistrosBDPool registros = new InterfaceRegistrosBDPool();
        bd.bdConexion();
        registros.setConexion(bd.getConexion());
        ResultSet resultado = registros.getRegistros("SELECT CURRENT_DATE as fechor FROM dual");
        try {
            if (resultado.next()) {
                fechor = resultado.getTimestamp("fechor");
            } else {
                System.out.println("No se ha devuelto la fecha.");
                fechor = null;
            }
            resultado.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            fechor = null;
        }
        bd.bdCierra();
        return fechor;
    }

    /***
     * Devuelve la fecha en formato dd/MM/yyyy
     * @param fecha La fecha que se desea convertir
     * @return Una cadena de texto con la fecha en el formato especificado
     */
    public static String getFechaCorta(Date fecha) {
        SimpleDateFormat forfec = new SimpleDateFormat("dd/MM/yyyy");
        return forfec.format(fecha);
    }

    /***
     * Devuelve la fecha en formato MM/dd/yyyy
     * @param fecha La fecha que se desea convertir
     * @return Una cadena de texto con la fecha en el formato especificado
     */
    public static String getFechaCortaBD(Date fecha) {
        SimpleDateFormat forfec = new SimpleDateFormat("dd/MM/yyyy");
        return forfec.format(fecha);
    }

    /***
     * Devuelve la fecha en formato d MMMMMMMMMM yyyy
     * @param fecha La fecha que se desea convertir
     * @return Una cadena de texto con la fecha en el formato especificado
     */
    public static String getFechaLarga(Date fecha) {
        SimpleDateFormat forfec = new SimpleDateFormat("d MMMMMMMMMM yyyy");
        return forfec.format(fecha);
    }

    /**
     * Obtiene el valor entero a partir de una cadena
     * @param valor String que contiene el valor
     * @return int entero obtenido por la cadena, 0 si la cadena no es válida
     */
    public static int getInt(String valor) {
        int importe;

        if (valor.equals("0")) {
            return 0;
        }
        try {
            valor = valor.replace(",", "");
            importe = Integer.valueOf(valor);
        } catch (Exception excepcion) {
            importe = 0;
        }
        return importe;
    }

    /**
     * Obtiene el valor entero a partir de un valor booleano
     * @param valor tipo booleano que se desea verificar
     * @return 1 si se obtiene un true, 0 si es false
     */
    public static int getInt(Boolean valor) {
        if (valor) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Obtiene un booleano a partir de un entero
     * @param valor el entero a verificar
     * @return false si es 0,de lo contrario 1
     */
    public static boolean getBoolean(int valor) {
        if (valor == 0) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Obtiene un BigDecimal a partir de una cadena de texto
     * @param valor tipo String del cual se extraerá el BigDecimal
     * @return el valor obtenido de la cadena (si es valida), 0 si no es válida
     */
    public static BigDecimal getBigDecimal(String valor) {
        BigDecimal importe;
        try {
            valor = valor.replace(",", "");
            valor = valor.replace(" ", "");
            importe = BigDecimal.valueOf(Double.parseDouble(valor));
        } catch (Exception excepcion) {
            importe = BigDecimal.valueOf(0F);
        }
        return (importe);
    }

    /**
     * Obtiene un BigDecimal a partir de una cadena de texto
     * @param valor tipo String del cual se extraerá el BigDecimal
     * @return el valor obtenido de la cadena (si es valida), 0 si no es válida
     */
    public static BigDecimal getBigDecimal(Object valor) {
        if (valor != null) {
            return (getBigDecimal(valor.toString()));
        } else {
            return BigDecimal.valueOf(0F);
        }
    }

    /**
     * Convierte una fecha a su formato yyyy-MM-dd utilizado por la mayoría de las bd's
     * @param p_fecha La fecha a la cual se le dará formato.
     * @return la cadena con la fecha en el formato indicado
     */
    public static String convierteFechaBD(Date p_fecha) {
        if (p_fecha == null) {
            p_fecha = convierteFecha("01/01/1900 00:00:00");
        }
        //SimpleDateFormat forfec = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat forfec = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String fechaString = forfec.format(p_fecha);
        //return "to_date('"+fechaString+"','dd-mm-yyyy')";
        return "to_date('" + fechaString + "','MM-DD-YYYY HH24:MI:SS')";
    }

    /**
     * Convierte una fecha a su formato yyyy-MM-dd utilizado por la mayoría de las bd's
     * @param p_fecha La fecha a la cual se le dará formato.
     * @return la cadena con la fecha en el formato indicado
     */
    public static String convierteFechaMostrar(Date p_fecha) {
        try {
            if (p_fecha == null) {
                p_fecha = convierteFecha("01/01/1900");
            }
            SimpleDateFormat forfec = new SimpleDateFormat("dd/MM/yyyy");
            return forfec.format(p_fecha);
        } catch (Exception e) {
            return "01/01/1900";
        }
    }

    /**
     * Convierte una fecha a su formato yyyy-MM-dd utilizado por la mayoría de las bd's
     * @param p_fecha La fecha a la cual se le dará formato.
     * @return la cadena con la fecha en el formato indicado
     */
    public static String convierteFechaHoraMostrar(Date p_fecha) {
        try {
            if (p_fecha == null) {
                p_fecha = convierteFecha("01/01/1900 00:00:00");
            }
            SimpleDateFormat forfec = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            //return DateFormat.getInstance().format(p_fecha);
            return forfec.format(p_fecha);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Convierte una fecha a su formato yyyy-MM-dd utilizado por la mayoría de las bd's
     * @param p_fecha La fecha a la cual se le dará formato.
     * @param regresarVacio En caso de enviar true y p_fecha es null, se regresa la cadena vacia en lugar de 01/01/1900
     * @return la cadena con la fecha en el formato indicado
     */
    public static String convierteFechaHoraMostrar(Date p_fecha, boolean regresarVacio) {
        try {
            if (p_fecha == null) {
                return "";
            } else if (p_fecha.equals(new Date(-2208965004000L))) {
                return "";
            }
            SimpleDateFormat forfec = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            //return DateFormat.getInstance().format(p_fecha);
            return forfec.format(p_fecha);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Convierte una fecha en formato yyyy-mm-dd hh:mm:ss a formato dd/MM/yyyy hh:mm:ss
     * @param p_fecha La fecha a la cual se le dará formato.
     * @return la cadena con la fecha en el formato indicado
     */
    public static String convierteFechaHoraMostrar(String p_fecha) {
        String fechaConvertida = "";
        String[] fechor = p_fecha.split(" ");
        String[] fecha = fechor[0].split("-");
        String soloFecha = fecha[2] + "/" + fecha[1] + "/" + fecha[0];

        fechaConvertida = soloFecha + " " + fechor[1].replace(".0", "");

        return fechaConvertida;
    }

    /**
     * Convierte una fecha a su formato yyyy-MM-dd utilizado por la mayoría de las bd's
     * @param p_hora La fecha a la cual se le dará formato.
     * @return la cadena con la fecha en el formato indicado
     */
    public static String convierteHora(Date p_hora) {
        try {
            SimpleDateFormat forfec = new SimpleDateFormat("HH:mm:ss");
            return forfec.format(p_hora);
        } catch (Exception e) {
            return "00:00";
        }
    }

    /**
     * Convierte un campo booleano al formato utilizado por la mayoría de las bd's
     * @param p_boolean el valor booleano en formato integer.
     * @return la cadena con el valor formato manejado por la base de datos
     */
    public static String convierteBooleanBD(int p_boolean) {
        String cadena;
        cadena = Integer.toString(p_boolean);
//        cadena = "'" + cadena + "'";
        return cadena;
    }

    /**
     * Convierte un campo booleano al formato utilizado por la mayoría de las bd's
     * @param p_boolean el valor booleano en formato integer.
     * @return la cadena con el valor formato manejado por la base de datos
     */
    public static int convierteBooleanBDEntero(Object p_boolean) {
        int Entero = 0;

        if (p_boolean instanceof String) {
            Entero = Integer.parseInt((String) p_boolean);
        } else if (p_boolean instanceof Integer) {
            Entero = (Integer) p_boolean;
        } else if (p_boolean instanceof Boolean) {
            if ((Boolean) p_boolean) {
                Entero = 1;
            } else {
                Entero = 0;
            }
        }
        return Entero;
    }

    public static Date convierteFecha(String p_fecha) {
        SimpleDateFormat forfec = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //System.out.println("Fecha: "+p_fecha);
        Date fecha;
        try {
            fecha = forfec.parse(p_fecha);
        } catch (Exception e) {
            System.out.println("Formateando fecha...");
            if (p_fecha != null && !p_fecha.trim().equals("")) {
                if (p_fecha.contains("-")) {
                    fecha = convierteFechaRegistro(p_fecha);
                } else {
                    if (p_fecha.contains("/") && !p_fecha.contains(":")) {
                        fecha = convierteFecha(p_fecha + " 00:00:00");
                    } else {
                        fecha = convierteFecha("01/01/1900 00:00:00");
                    }
                }
            } else {
                fecha = convierteFecha("01/01/1900 00:00:00");
            }
        }
        
        return fecha;
    }

    /**
     * Convierte un una cadena de fecha en Fecha con formato dd/MM/yyyy
     * @param p_fecha el valor String de la fecha.
     * @return la fecha con formato
     */
    public static Date convierteFechaRegistro(String p_fecha) {
        SimpleDateFormat forfec = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("Fecha A CONVERTIR: "+p_fecha);
        Date fecha;
        try {
            fecha = forfec.parse(p_fecha);
        } catch (NullPointerException nu) {
            fecha = convierteFechaRegistro("1900-01-01 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
            if (p_fecha != null && !p_fecha.trim().equals("")) {
                if (p_fecha.contains("/")) {
                    fecha = convierteFecha(p_fecha + " 00:00:00");
                } else {
                    fecha = convierteFechaRegistro(p_fecha + " 00:00:00");
                }
            } else {
                fecha = convierteFechaRegistro("1900-01-01 00:00:00");
            }
        }
        return fecha;
    }

    /**
     * Convierte una fecha a su formato yyyy-MM-dd utilizado por la mayoría de las bd's
     * @param p_fecha La fecha a la cual se le dará formato.
     * @param regresarNull En caso de que p_fecha sea null se regresa la cadena null y no la fecha de 1900
     * @return la cadena con la fecha en el formato indicado
     */
    public static String convierteFechaMostrar(Date p_fecha, boolean regresarNull) {
        try {
            if (p_fecha == null && regresarNull) {
                return "";
            } else if (p_fecha != null && p_fecha.equals(new Date(-2208965004000L)) && regresarNull) {
                return "";
            }
            SimpleDateFormat forfec = new SimpleDateFormat("dd/MM/yyyy");
            return forfec.format(p_fecha);
        } catch (Exception e) {
            return "01/01/1900";
        }
    }

    /**
     * Convierte una fecha a su formato yyyy-MM-dd utilizado por la mayoría de las bd's
     * @param p_fecha La fecha a la cual se le dará formato.
     * @param regresarNull Si la fecha es null, se regresa la cadena null y no una fecha de 1900
     * @return la cadena con la fecha en el formato indicado
     */
    public static String convierteFechaBD(Date p_fecha, boolean regresarNull) {
        if (p_fecha == null) {
            if (regresarNull) {
                return "null";
            } else {
                p_fecha = convierteFecha("01/01/1900 00:00:00");
            }
        }
        //SimpleDateFormat forfec = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat forfec = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String fechaString = forfec.format(p_fecha);
        //return "to_date('"+fechaString+"','dd-mm-yyyy')";
        return "to_date('" + fechaString + "','MM-DD-YYYY HH24:MI:SS')";
    }

    /***
     * Devuelve la fecha y hora en formato dd/MM/yyyy kk:mm:ss
     * @param fecha La fecha que se desea convertir
     * @param respetarNull Si la fecha es nulo se regresara la cadena vacia
     * @return Una cadena de texto con la fecha en el formato especificado
     */
    public static String getFechaHora(Date fecha, boolean respetarNull) {
        if (fecha == null && respetarNull) {
            return "";
        }
        SimpleDateFormat forfec = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        return forfec.format(fecha);
    }

    public static Map<String, String> toUpperCase(Map parametros) {
        Map<String, String> map = new HashMap<String, String>();

        Set<String> params = parametros.keySet();
        for (String key : params) {
            Object param = parametros.get(key);
            if (param != null && param.toString().trim().length() > 0) {
                map.put(key, param.toString().trim().toUpperCase());
            }
        }
        return map;
    }

}
