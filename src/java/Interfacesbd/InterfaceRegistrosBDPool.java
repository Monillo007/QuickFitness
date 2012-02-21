/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfacesbd;

//import Bitacora.BD.BIT_SQL;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import util.Tabla;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import oracle.jdbc.*;
import util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Ing. Luis Navarro
 */
public class InterfaceRegistrosBDPool {

    Connection conexion;
    String usuario, host;

    public InterfaceRegistrosBDPool() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene un registro de la tabla especificada en la bd.
     * @param p_tabla La tabla de la cual se desea obtener el registro.
     * @param p_condicion La condición que debe cumplir el registro (el WHERE de la consulta SQL)
     * @return Un objeto del tipo de la tabla especificada o null en caso de que no exista el registro indicado.
     */
    public Tabla getRegistro(String p_tabla, String p_condicion) {
        String strSQL;
        String[] nombreTabla = p_tabla.split("\\.");
        ResultSet wrkResultSet = null;
        ResultSetMetaData md;
        Tabla wrkTabla = null;
        Class clase;
        String wrkcampos[];

        strSQL = "SELECT *";
        strSQL = strSQL + " FROM " + nombreTabla[nombreTabla.length - 1];
        strSQL = strSQL + " WHERE " + p_condicion;

        getLogger().info(strSQL);
        try {
            Statement SentenciaSQL = (Statement) conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            registraBitacora(strSQL);
            wrkResultSet = SentenciaSQL.executeQuery(strSQL);
            md = wrkResultSet.getMetaData();
            if (!wrkResultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("m:" + e.getMessage());
            System.out.println("e:" + e);
            return null;
        }
        try {
            clase = Class.forName(p_tabla);
            wrkTabla = (Tabla) clase.newInstance();
            wrkcampos = wrkTabla.getCampos();

            for (int i = 1; i <= wrkcampos.length; i++) {
                wrkTabla.setTamano((i - 1), md.getPrecision(i));
                if (wrkResultSet.getObject(i) instanceof String) {
                    String cadena = "";
                    cadena = wrkResultSet.getString(i).trim();
                    if (cadena != null && !cadena.equalsIgnoreCase("null")) {
                        wrkTabla.setCampo(wrkcampos[i - 1], (Object) cadena);
                    } else {
                        wrkTabla.setCampo(wrkcampos[i - 1], "");
                    }
                } else {
                    if (md.getColumnTypeName(i).equals("LONG RAW")) {
                        wrkTabla.setCampo(wrkcampos[i - 1], null);
                    } else if (md.getColumnTypeName(i).equals("CLOB")) {
                        wrkTabla.setCampo(wrkcampos[i - 1], wrkResultSet.getString(i));
                    } else {
                        if (md.getColumnTypeName(i).equalsIgnoreCase("DATE") || md.getColumnTypeName(i).equalsIgnoreCase("DATETIME")) {
                            ////System.out.println("FECHA STRING-----> "+wrkResultSet.getString(i));
                            wrkTabla.setCampo(wrkcampos[i - 1], Util.convierteFechaRegistro(wrkResultSet.getString(i)));
                        } else {
                            wrkTabla.setCampo(wrkcampos[i - 1], wrkResultSet.getObject(i));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (wrkTabla);
    }

    public HashMap getRegistroHash(String strSQL) {
        HashMap registro = new HashMap();
        ResultSet wrkResultSet = null;
        ResultSetMetaData md;
        Statement SentenciaSQL;
        getLogger().info(strSQL);
        try {
            SentenciaSQL = (Statement) conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            registraBitacora(strSQL);
            wrkResultSet = SentenciaSQL.executeQuery(strSQL);
            md = wrkResultSet.getMetaData();
            if (!wrkResultSet.next()) {
                SentenciaSQL.close();
                wrkResultSet.close();
                return null;
            }
        } catch (SQLException e) {
            System.out.println("m:" + e.getMessage());
            System.out.println("e:" + e);
            return null;
        }
        try {
            int cantCampos = md.getColumnCount();

            for (int i = 1; i <= cantCampos; i++) {

                if (wrkResultSet.getObject(i) instanceof String) {
                    String cadena = "";
                    cadena = wrkResultSet.getString(i).trim();
                    if (cadena != null && !cadena.equalsIgnoreCase("null")) {
                        registro.put(md.getColumnName(i), (Object) cadena);
                    } else {
                        registro.put(md.getColumnName(i), "");
                    }
                } else {
                    if (md.getColumnTypeName(i).equals("LONG RAW")) {
                        registro.put(md.getColumnName(i), null);
                    } else if (md.getColumnTypeName(i).equals("CLOB")) {
                        registro.put(md.getColumnName(i), wrkResultSet.getString(i));
                    } else {
                        if (md.getColumnTypeName(i).equals("DATE")) {
                            registro.put(md.getColumnName(i), Util.convierteFechaRegistro(wrkResultSet.getString(i)));
                        } else {
                            registro.put(md.getColumnName(i), wrkResultSet.getObject(i));
                        }
                    }
                }
            }
            SentenciaSQL.close();
            wrkResultSet.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return registro;
    }

    public List<HashMap> getListaRegistrosHash(String strSQL) {
        List lista = new ArrayList<HashMap>();
        ResultSet wrkResultSet = null;
        ResultSetMetaData md;

        System.out.println(strSQL);
        try {
            Statement SentenciaSQL = (Statement) conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            registraBitacora(strSQL);
            wrkResultSet = SentenciaSQL.executeQuery(strSQL);
            md = wrkResultSet.getMetaData();
            int cantCampos = md.getColumnCount();

            while (wrkResultSet.next()) {
                HashMap registro = new HashMap();
                for (int i = 1; i <= cantCampos; i++) {

                    if (wrkResultSet.getObject(i) instanceof String) {
                        String cadena = "";
                        cadena = wrkResultSet.getString(i).trim();
                        if (cadena != null && !cadena.equalsIgnoreCase("null")) {
                            registro.put(md.getColumnName(i), (Object) cadena);
                        } else {
                            registro.put(md.getColumnName(i), "");
                        }
                    } else {
                        if (md.getColumnTypeName(i).equals("LONG RAW")) {
                            registro.put(md.getColumnName(i), null);
                        } else if (md.getColumnTypeName(i).equals("CLOB")) {
                            registro.put(md.getColumnName(i), wrkResultSet.getString(i));
                        } else {
                            if (md.getColumnTypeName(i).equals("DATE")) {
                                registro.put(md.getColumnName(i), Util.convierteFechaRegistro(wrkResultSet.getString(i)));
                            } else {
                                registro.put(md.getColumnName(i), wrkResultSet.getObject(i));
                            }
                        }
                    }
                }
                lista.add(registro);
            }
            SentenciaSQL.close();
            wrkResultSet.close();
        } catch (SQLException e) {
            System.out.println("m:" + e.getMessage());
            System.out.println("e:" + e);
        }

        return lista;
    }

    /**
     * Obtiene el valor de un campo de un registro de la base de datos.
     * @param tabla  La tabla de la cual se desea obtener el registro.
     * @param condicion
     * @param campo
     * @return 
     */
    public Object getCampoBD(String tabla, String condicion, String campo) {
        String strSQL;
        String[] nombreTabla = tabla.split("\\.");
        ResultSet wrkResultSet = null;

        strSQL = "SELECT " + campo;
        strSQL = strSQL + " FROM " + nombreTabla[nombreTabla.length - 1];
        strSQL = strSQL + " WHERE " + condicion;

        //System.out.println(strSQL);
        try {
            Statement SentenciaSQL = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            SentenciaSQL.setQueryTimeout(1);
            registraBitacora(strSQL);
            wrkResultSet = SentenciaSQL.executeQuery(strSQL);
            if (!wrkResultSet.next()) {
                return null;
            } else {
                return wrkResultSet.getObject(campo);
            }
        } catch (SQLException e) {
            System.out.println("m:" + e.getMessage());
            System.out.println("e:" + e);
            return null;
        }
    }

    public Tabla getRegistroLock(String p_tabla, String p_condicion) {
        String strSQL;
        String[] nombreTabla = p_tabla.split("\\.");
        ResultSet wrkResultSet = null;
        ResultSetMetaData md;
        Tabla wrkTabla = null;
        Class clase;
        String wrkcampos[];

        strSQL = "SELECT *";
        strSQL = strSQL + " FROM " + nombreTabla[nombreTabla.length - 1];
        strSQL = strSQL + " WHERE " + p_condicion;
        strSQL = strSQL + " FOR UPDATE ";


        //System.out.println(strSQL);
        try {
            conexion.commit();

            Statement SentenciaSQL = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE);
            SentenciaSQL.setQueryTimeout(1);
            registraBitacora(strSQL);
            wrkResultSet = SentenciaSQL.executeQuery(strSQL);
            md = wrkResultSet.getMetaData();
            wrkResultSet.next();
        } catch (SQLException e) {
            System.out.println("m:" + e.getMessage());
            System.out.println("e:" + e);
            return null;
        }
        try {
            clase = Class.forName("Tablas." + p_tabla);
            wrkTabla = (Tabla) clase.newInstance();
            wrkcampos = wrkTabla.getCampos();

            for (int i = 1; i <= wrkcampos.length; i++) {
                wrkTabla.setTamano((i - 1), md.getPrecision(i));
                wrkTabla.setCampo(wrkcampos[i - 1], wrkResultSet.getObject(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (wrkTabla);
    }

    public List<? extends Tabla> getListaRegistros(String p_tabla, String p_condicion) {
        return getListaRegistros(p_tabla, p_condicion, null);
    }

    public List<? extends Tabla> getListaRegistros(String p_tabla, String p_condicion, String p_orden) {
        List<Tabla> registros = new ArrayList<Tabla>();
        String strSQL;
        String[] nombreTabla = p_tabla.split("\\.");
        ResultSet wrkResultSet = null;
        ResultSetMetaData md;
        Class clase;
        String wrkcampos[];

        strSQL = "SELECT *";
        strSQL = strSQL + " FROM " + nombreTabla[nombreTabla.length - 1];
        strSQL = strSQL + " WHERE " + p_condicion;
        if (p_orden != null) {
            strSQL += " ORDER BY " + p_orden;
        }

        getLogger().info(strSQL);
        try {
            Statement SentenciaSQL = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);

            wrkResultSet = SentenciaSQL.executeQuery(strSQL);
            registraBitacora(strSQL);
            md = wrkResultSet.getMetaData();
            clase = Class.forName(p_tabla);
            while (wrkResultSet.next()) {
                Tabla wrkTabla = (Tabla) clase.newInstance();
                wrkcampos = wrkTabla.getCampos();
                for (int i = 1; i <= wrkcampos.length; i++) {
                    wrkTabla.setTamano((i - 1), md.getPrecision(i));
                    if (wrkResultSet.getObject(i) instanceof String) {
                        String cadena;
                        cadena = wrkResultSet.getString(i).trim();
                        if (cadena != null && !cadena.equalsIgnoreCase("null")) {
                            wrkTabla.setCampo(wrkcampos[i - 1], (Object) cadena);
                        } else {
                            wrkTabla.setCampo(wrkcampos[i - 1], "");
                        }
                    } else {
                        wrkTabla.setCampo(wrkcampos[i - 1], wrkResultSet.getObject(i));
                    }
                }
                registros.add(wrkTabla);
            }
        } catch (SQLException e) {
            System.out.println("m:" + e.getMessage());
            System.out.println("e:" + e);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (registros);
    }

    public ResultSet getListaRegistrosJoin(String p_tablas, String p_condicion, boolean distinct, String campoDistinct) {
        return getListaRegistrosJoin(p_tablas, p_condicion, null, distinct, campoDistinct);
    }

    public ResultSet getListaRegistrosJoin(String p_tablas, String p_condicion) {
        return getListaRegistrosJoin(p_tablas, p_condicion, null);
    }

    public ResultSet getListaRegistrosJoin(String p_tablas) {
        return getListaRegistrosJoin(p_tablas, null, null);
    }

    public ResultSet getListaRegistrosJoin(String p_tablas, String p_condicion, String p_orden) {
        return getListaRegistrosJoin(p_tablas, p_condicion, p_orden, false, "");
    }

    public ResultSet getListaRegistrosJoin(String p_tablas, String p_condicion, String p_orden, boolean distinct, String campoDistinct) {
        String strSQL;
        ResultSet wrkResultSet = null;
        ResultSetMetaData md;
        String dis = "*";

        if (distinct) {
            dis = "DISTINCT " + campoDistinct;

        }

        strSQL = "SELECT " + dis;
        strSQL = strSQL + " FROM " + p_tablas;
        if (p_condicion != null) {
            strSQL = strSQL + " WHERE " + p_condicion;
        }
        if (p_orden != null) {
            strSQL += " ORDER BY " + p_orden;
        }

        System.out.println(strSQL);
        Logger.getLogger(InterfaceRegistrosBDPool.class.getName()).info(strSQL);
        try {
            Statement SentenciaSQL = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            wrkResultSet = SentenciaSQL.executeQuery(strSQL);
            registraBitacora(strSQL);
            md = wrkResultSet.getMetaData();
        } catch (SQLException e) {
            System.out.println("m:" + e.getMessage());
            System.out.println("e:" + e);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wrkResultSet;
    }

    public Tabla setCaracteristicasTabla(String p_tabla) {
        String strSQL;
        String[] nombreTabla = p_tabla.split("\\.");
        String nombre = "";
        if (nombreTabla.length == 1) {
            nombre = p_tabla;
        } else {
            nombre = nombreTabla[nombreTabla.length - 1];
        }
        ResultSet wrkResultSet = null;
        ResultSetMetaData md;
        Tabla wrkTabla = null;
        Class clase;
        String wrkcampos[];

        strSQL = "SELECT *";
        strSQL = strSQL + " FROM " + nombre+" LIMIT 1";

        System.out.println(strSQL);
        try {
            Statement SentenciaSQL = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            SentenciaSQL.setQueryTimeout(1);
            registraBitacora(strSQL);
            wrkResultSet = SentenciaSQL.executeQuery(strSQL);
            md = wrkResultSet.getMetaData();
        } catch (SQLException e) {
            System.out.println("m:" + e.getMessage());
            System.out.println("e:" + e);
            return null;
        }
        try {
            clase = Class.forName(p_tabla);
            wrkTabla = (Tabla) clase.newInstance();
            wrkcampos = wrkTabla.getCampos();

            for (int i = 1; i <= wrkcampos.length; i++) {
                wrkTabla.setTamano((i - 1), md.getPrecision(i));
                ////System.out.println("Campo: "+md.getColumnName(i)+" Tipo: "+md.getColumnTypeName(i));
                if (md.columnNullable == md.isNullable(i)) {
                    wrkTabla.setObligatorios(i - 1, false);
                } else {
                    wrkTabla.setObligatorios(i - 1, true);
                }
                wrkTabla.setTipos(i - 1, md.getColumnTypeName(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (wrkTabla);
    }

    public void agregar(String p_tabla, Tabla p_registro) {
        String strSQL;
        String[] nombreTabla = p_tabla.split("\\.");
        String nombre = "";
        if (nombreTabla.length == 1) {
            nombre = p_tabla;
        } else {
            nombre = nombreTabla[nombreTabla.length - 1];
        }
        String serializar;

        serializar = serializarSQLInsertar(p_registro);
        strSQL = "INSERT INTO " + nombre + " " + serializar;
        while(!ejecSQL(strSQL)){}

    }

    public void modificar(String p_tabla, Tabla p_registro, String p_condicion) {
        String strSQL;
        String serializar;
        String[] nombreTabla = p_tabla.split("\\.");
        String nombre = "";
        if (nombreTabla.length == 1) {
            nombre = p_tabla;
        } else {
            nombre = nombreTabla[nombreTabla.length - 1];
        }

        serializar = serializarSQLModificar(p_registro);
        strSQL = "UPDATE " + nombre + " " + serializar;
        strSQL = strSQL + " WHERE " + p_condicion;
        ejecSQL(strSQL);
    }

    public void eliminar(String p_tabla, String p_condicion) {
        String strSQL;
        String[] nombreTabla = p_tabla.split("\\.");
        String nombre = "";
        if (nombreTabla.length == 1) {
            nombre = p_tabla;
        } else {
            nombre = nombreTabla[nombreTabla.length - 1];
        }

        strSQL = "DELETE FROM " + nombre;
        strSQL = strSQL + " WHERE " + p_condicion;
        ejecSQL(strSQL);
    }

    private String serializarSQLInsertar(Tabla p_tabla) {
        String serializa0 = "";
        String serializa1 = "";

        String wrkcampos[];

        wrkcampos = p_tabla.getCampos();
        for (int i = 0; i < wrkcampos.length; i++) {
            if (i > 0) {
                serializa0 = serializa0 + ", ";
            }
            serializa0 = serializa0 + wrkcampos[i];
        }
        serializa0 = "(" + serializa0 + ") ";
        serializa1 = p_tabla.getSQLInsertar();

        return (serializa0 + serializa1);
    }

    private String serializarSQLModificar(Tabla p_tabla) {
        String serializa0 = "";

        serializa0 = p_tabla.getSQLModificar();

        return (serializa0);
    }

    public int getSiguienteSecuencia(String partabla, String parcampo, String parcondicion) {
        int numsec = 0;
        String strSQL;
        String[] nombreTabla = partabla.split("\\.");
        String nombre = "";
        if (nombreTabla.length == 1) {
            nombre = partabla;
        } else {
            nombre = nombreTabla[nombreTabla.length - 1];
        }

        numsec = bdConreg(nombre, parcondicion);
        ResultSet wrkResultSet;

        if (numsec != 0) {
            strSQL = "SELECT max(" + parcampo + ")";
            strSQL = strSQL + " FROM " + nombre;

            if (!parcondicion.equals("")) {
                strSQL = strSQL + " WHERE " + parcondicion;
            }
            try {
                Statement SentenciaSQL = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
                wrkResultSet = SentenciaSQL.executeQuery(strSQL);
                //registraBitacora(strSQL);

                if (wrkResultSet.next()) {
                    numsec = wrkResultSet.getInt(1);
                }

            } catch (SQLException e) {
                System.out.println("m:" + e.getMessage());
                System.out.println("e:" + e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return (numsec + 1);

    }

    public BigDecimal getSiguienteSecuenciaDec(String partabla, String parcampo, String parcondicion) {
        BigDecimal numsec = new BigDecimal(0);
        String strSQL;
        String[] nombreTabla = partabla.split("\\.");
        String nombre = "";
        if (nombreTabla.length == 1) {
            nombre = partabla;
        } else {
            nombre = nombreTabla[nombreTabla.length - 1];
        }

        numsec = new BigDecimal(bdConreg(nombre, parcondicion));
        ResultSet wrkResultSet;

        if (!numsec.equals(new BigDecimal(0))) {
            strSQL = "SELECT max(" + parcampo + ")";
            strSQL = strSQL + " FROM " + nombre;

            if (!parcondicion.equals("")) {
                strSQL = strSQL + " WHERE " + parcondicion;
            }

            try {
                Statement SentenciaSQL = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
                wrkResultSet = SentenciaSQL.executeQuery(strSQL);
                //registraBitacora(strSQL);
                if (wrkResultSet.next()) {
                    numsec = wrkResultSet.getBigDecimal(1);
                }

            } catch (SQLException e) {
                System.out.println("m:" + e.getMessage());
                System.out.println("e:" + e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return (numsec.add(new BigDecimal(1)));
    }

    public boolean guardarArchivoDB(String rutaArchivo, String tabla, String campoArchivo, String campoNombre, String condicion) {
        return guardarArchivoDB(rutaArchivo, null, tabla, campoArchivo, campoNombre, condicion);
    }

    public boolean guardarArchivoDB(String rutaArchivo, String nombreFile, String tabla, String campoArchivo, String campoNombre, String condicion) {
        File fBlob = new File(rutaArchivo);
        String[] archivo = rutaArchivo.split("/");
        String nombreArchivo = archivo[archivo.length - 1];
        String[] nombreTabla = tabla.split("\\.");
        String nombre = "";
        if (nombreTabla.length == 1) {
            nombre = tabla;
        } else {
            nombre = nombreTabla[nombreTabla.length - 1];
        }

        if (nombreFile != null) {
            nombreArchivo = nombreFile;
        }
        System.out.println("fBlob = " + fBlob.getAbsolutePath());
        try {
            if (fBlob.exists()) {
                
                String strSQL = "";
                if(campoNombre != null){
                    strSQL = "UPDATE " + nombre + " SET " + campoArchivo + " = ?, " + campoNombre + "= ? WHERE " + condicion;                    
                }else{
                    strSQL = "UPDATE " + nombre + " SET " + campoArchivo + " = ? WHERE " + condicion;                                        
                }
                registraBitacora(strSQL);
                System.out.println("strSQL = " + strSQL);
                PreparedStatement pstmt =  conexion.prepareStatement(strSQL);
                FileInputStream is = new FileInputStream(fBlob);
                pstmt.setBinaryStream(1, is, (int) fBlob.length());
                if(campoNombre!=null){
                    pstmt.setString(2, nombreArchivo.toUpperCase());                    
                }
                pstmt.executeUpdate();
                pstmt.close();
//                conexion.setAutoCommit(false);
//                conexion.commit();
//                conexion.setAutoCommit(true);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getArchivoBD(String tabla, String campoArchivo, String campoNombre, String condicion, String carpeta, Boolean borrar) {
        return this.getArchivoBD(tabla, campoArchivo, campoNombre, condicion, carpeta, borrar, null);
    }

    //1ro
    public String getArchivoBD(String tabla, String campoArchivo, String campoNombre, String condicion, String carpeta, Boolean borrar, String esquema) {
        FileOutputStream archivoNuevo = null;
        String nombre = null;
        String[] nombreTabla = tabla.split("\\.");
        String nombreT = "";
        String Esquema = "DBAUDITORIA";
        if (esquema != null) {
            Esquema = esquema;
        }
        //String ruta = "C:/Documents and Settings/rjuarezja/Mis documentos/NetBeansProjects/SGBI/build/web/Archivo/"+carpeta+"/";
        //String ruta = "C:/Users/Luis Angel Pastrana/Desktop/QuickFitness App/QuickFitness/build/web/" + carpeta + "/";
        String ruta = "/home/hann/public_html/" + carpeta + "/";
//       String ruta = "C:/apache-tomcat-6.0.18/webapps/ROOT/Archivo/"+carpeta+"/";
         //String ruta = "/apps/Desarrollos/src/SistemaWebEstatal/build/web/Archivo/"+carpeta+"/";
        //String ruta = "/usr/local/tomcat/webapps/ROOT/Archivo/"+carpeta+"/";
         if(nombreTabla.length==1){
            nombreT = tabla;
        } else {
            nombreT = nombreTabla[nombreTabla.length - 1];
        }
        try {
            String sql = "SELECT * FROM " + nombreT + " WHERE " + condicion;
            //System.out.println(sql);
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            registraBitacora(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] archivoStream = rs.getBytes(campoArchivo);
                nombre = rs.getString(campoNombre);
                if (borrar == true) {
                    File a = new File(ruta + "/" + nombre);
                    if (a.exists()) {
                        a.delete();
                        nombre = null;
                    }
                } else {
                    archivoNuevo = new FileOutputStream(ruta + "/" + nombre);
                    archivoNuevo.write(archivoStream);
                }
                pstmt.close();
            }
        } catch (Exception e) {
            System.out.println("No se ha podido recuperar la foto. " + e.getLocalizedMessage());
            //e.printStackTrace();
            return null;
        }


        return nombre;
    }

    //2do
    public String getArchivoBD(String tabla, String campoArchivo, String campoNombre, String condicion, String carpeta) {
        FileOutputStream archivoNuevo = null;
        String nombre = null;
        String nombreT = tabla;
        
        //String ruta = "/Documents and Settings/Administrador/Mis documentos/NetBeansProjects/SIAD/build/web/Archivo/"+carpeta+"/";
        //String ruta = "/apps/Desarrollos/src/SistemaWebEstatal/build/web/Archivo/"+carpeta+"/";
        //String ruta = "C:/Users/Luis Angel Pastrana/Desktop/QuickFitness App/QuickFitness/build/web/Archivos/" + carpeta + "/";
        String ruta = "/home/hann/public_html/" + carpeta + "/";

        try {
            String sql = "SELECT * FROM " + nombreT + " WHERE " + condicion;
            PreparedStatement pstmt = (PreparedStatement) conexion.prepareStatement(sql);
            registraBitacora(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] archivoStream = rs.getBytes(campoArchivo);
                if(campoNombre != null){
                    nombre = rs.getString(campoNombre);                    
                }else{
                    nombre = "tempFile_"+new Date().getTime();
                }

                if (archivoStream != null) {
                    archivoNuevo = new FileOutputStream(ruta + "/" + nombre);
                    archivoNuevo.write(archivoStream);
                    archivoNuevo.close();
                } else {
                    nombre = null;
                }
                pstmt.close();
            }
        } catch (Exception e) {

            Logger.getLogger(InterfaceRegistrosBDPool.class.getName()).log(Level.SEVERE, "No se ha podido recuperar la foto.", e);
            return null;
        }


        return nombre;
    }

    public ResultSet getRegistros(String sql) {
        Statement SentenciaSQL;
        ResultSet wrkResultSet = null;
        //System.out.println("getRegistros:" + sql);
        Logger.getLogger(InterfaceRegistrosBDPool.class.getName()).info(sql);
        try {
            SentenciaSQL = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            wrkResultSet = SentenciaSQL.executeQuery(sql);
            registraBitacora(sql);
        } catch (SQLException ex) {
            //ex.printStackTrace();
            System.out.println(ex.getMessage() + " - " + sql);
        }
        return wrkResultSet;
    }

    public String getArchivoTxt(String tabla, String campoArchivo, String campoNombre, String condicion, String carpeta, Boolean borrar) {
        FileOutputStream archivoNuevo = null;
        String rutaA = "";
        File a = null;
        String nombre = null;
        String[] nombreTabla = tabla.split("\\.");
        String nombreT = "";

        //String ruta = "/Documents and Settings/Administrador/Mis documentos/NetBeansProjects/SIAD/build/web/Archivo/"+carpeta+"/";
        //String ruta = "/apps/Desarrollos/src/SistemaWebEstatal/build/web/Archivo/"+carpeta+"/";
        String ruta = "/usr/local/tomcat/webapps/ROOT/Archivo/"+carpeta+"/";
       
        if(nombreTabla.length==1){
            nombreT = tabla;
        } else {
            nombreT = nombreTabla[nombreTabla.length - 1];
        }

        try {
            String sql = "SELECT * FROM " + nombreT + " WHERE " + condicion;
            ////System.out.println(sql);
            PreparedStatement pstmt = (PreparedStatement) conexion.prepareStatement(sql);
            registraBitacora(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] archivoStream = rs.getBytes(campoArchivo);
                nombre = rs.getString(campoNombre);
                if (borrar == true) {
                    a = new File(ruta + "/" + nombre);
                    if (a.exists()) {
                        a.delete();
                        nombre = null;
                    }
                } else {
                    archivoNuevo = new FileOutputStream(ruta + "/" + nombre);
                    archivoNuevo.write(archivoStream);
                }
                rutaA = ruta + "/" + nombre;
            }
        } catch (Exception e) {
            //System.out.println("No se ha podido recuperar el archivo.");
            e.printStackTrace();
            return null;
        }
        ////System.out.println(rutaA);
        return rutaA;
    }

    /**
     * Sube los datos de un archivo tipo XLS (Microsoft Excel) directamente a una tabla de la base de datos.
     * @param tablaBD La tabla de la base de datos a la que se subirán los registros.
     * @param esquemaBD El esquema al que pertenece dicha tabla.
     * @param rutaArchivo La ruta del archivo fuente.
     * @param usuario El usuario que realiza el proceso.
     * @param borrar Indica si se desea borrar el archivo tras finalizar el proceso (true) o no (false).
     * @return true si se subió correctamente, false si no.
     */
    public boolean xlsABaseDatos(String tablaBD, String esquemaBD, String rutaArchivo, String usuario, boolean borrar) {
        boolean operacionCorrecta = true;
        FileInputStream inputfile;
        Tabla tabla = null;
        Tabla tablaTipos = null;
        Class clase = null;
        String[] campos;
        String[] tipos;
        HSSFSheet sheet;

        try {

            if (!new File(rutaArchivo).exists()) {
                throw new Exception("El archivo " + rutaArchivo + " no existe.");
            }

            inputfile = new FileInputStream(rutaArchivo);
            /* creamos un HSSFWorkbook con nuestro excel */
            HSSFWorkbook xls = new HSSFWorkbook(inputfile);

            InterfaceBDPool bd = new InterfaceBDPool();
            bd.setEsquema(esquemaBD);
            bd.bdConexion();

            clase = Class.forName(tablaBD);
            tabla = (Tabla) clase.newInstance();
            tablaTipos = this.setCaracteristicasTabla(tablaBD);
            tipos = tablaTipos.getTipos();
            campos = tabla.getCampos();

            /* Elegimos la hoja que queremos leer del excel */
            sheet = xls.getSheetAt(0);
            int contFilas = 1;
            HSSFRow row;

            /* Nos recorremos las filas */
            while (sheet.getRow(contFilas) != null) {

                row = sheet.getRow(contFilas);
                HSSFCell cell;
                int tipo;

                String contenido = "";
                int contColumnas = 0;
                while (row.getCell(contColumnas) != null) {
                    cell = row.getCell(contColumnas);
                    tipo = cell.getCellType();

                    ////System.out.println("Campo: "+campos[contColumnas]);

                    if (campos[contColumnas].equals(tabla.getID())) {
                        tabla.setCampo(campos[contColumnas], this.getSiguienteSecuenciaDec(tablaBD, tabla.getID(), "1=1"));
                    } else {
                        switch (tipo) {
                            case HSSFCell.CELL_TYPE_FORMULA:
                                contenido = contenido + "\t" + cell.getCellFormula();
                                tabla.setCampo(campos[contColumnas], "" + cell.getCellFormula());
                                break;

                            case HSSFCell.CELL_TYPE_NUMERIC:
                                try {
                                    ////System.out.println("Campo "+campos[contColumnas]+": "+ cell.getNumericCellValue());
                                    tabla.setCampo(campos[contColumnas], new BigDecimal(cell.getNumericCellValue()));
                                } catch (Exception e) {
                                    ////System.out.println("Campo "+campos[contColumnas]+": "+ cell.getRichStringCellValue().getString());
                                    tabla.setCampo(campos[contColumnas], new BigDecimal(cell.getRichStringCellValue().getString()));
                                }
                                break;

                            case HSSFCell.CELL_TYPE_STRING:
                                if (tipos[contColumnas].equals("DATE")) {
                                    ////System.out.println("Campo tipo fecha "+campos[contColumnas]+" : "+ cell.getRichStringCellValue().getString());
                                    tabla.setCampo(campos[contColumnas], Util.convierteFecha(cell.getRichStringCellValue().getString()));
                                } else if (tipos[contColumnas].equals("NUMBER")) {
                                    ////System.out.println("Campo tipo numero "+campos[contColumnas]+" : "+ cell.getRichStringCellValue().getString());
                                    tabla.setCampo(campos[contColumnas], new BigDecimal(cell.getRichStringCellValue().getString()));
                                } else {
                                    ////System.out.println("Campo tipo cadena "+campos[contColumnas]+" : "+ cell.getRichStringCellValue().getString());
                                    tabla.setCampo(campos[contColumnas], cell.getRichStringCellValue().getString());
                                }
                                break;
                        }
                    }
                    tabla.setCampo("USUARIOCREACION", usuario);
                    tabla.setCampo("FECHACREACION", new Date());
                    tabla.setCampo("ESTATUSREGISTRO", new BigDecimal(0));
                    contColumnas++;
                }
                this.agregar(tablaBD, tabla);
                contenido = "";
                contFilas++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            operacionCorrecta = false;
        }
        if (operacionCorrecta && borrar) {
            new File(rutaArchivo).delete();
        }
        return operacionCorrecta;
    }

    public boolean ejecSQL(String strSQL) {
        boolean p = true;
        try {
            Statement stmt = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            registraBitacora(strSQL);
            if(!strSQL.contains("CON_DILIGENCIA") || !strSQL.contains("AVE_DILIGENCIA")){
                Logger.getLogger(InterfaceRegistrosBDPool.class.getName()).info(strSQL);
            }
            stmt.executeUpdate(strSQL);
            //Se comenta la linea para poder realizar transacciones
            //getConexion().commit();
            /////////////////
            stmt.close();
        } catch (SQLException ex) {
            //System.out.println("Excepcion: " + ex);
            Logger.getLogger(InterfaceRegistrosBDPool.class.getName()).log(Level.SEVERE, null, ex);
            p = false;
        }
        return p;
    }

    public int bdConreg(String wrptabla, String wrpcondicion) {
        String strSQL;
        ResultSet Registros = null;
        Statement SentenciaSQL = null;
        int numreg;
        String[] nombreTabla = wrptabla.split("\\.");
        String nombre = "";
        if (true) {
            nombre = wrptabla;
        } else {
            nombre = nombreTabla[nombreTabla.length - 1];
        }

        strSQL = "SELECT count(*)";
        strSQL = strSQL + " FROM " + nombre;

        if (!wrpcondicion.equals("")) {
            strSQL = strSQL + " WHERE " + wrpcondicion;
        }

        getLogger().info(strSQL);
        try {
            SentenciaSQL = getConexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Registros = SentenciaSQL.executeQuery(strSQL);
            registraBitacora(strSQL);
            Registros.next();
            numreg = Registros.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e);
            numreg = 0;
        } finally {
            try {
                SentenciaSQL.close();
                Registros.close();
            } catch (Exception e) {
            }
        }
        return numreg;
    }

    public int bdConreg(String condicion) {
        String strSQL;
        ResultSet Registros = null;
        Statement SentenciaSQL = null;
        int numreg;

        strSQL = condicion;

        try {
            SentenciaSQL = getConexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            getLogger().info("SELECT COUNT(*) FROM (" + strSQL + ") AS T1");
            Registros = SentenciaSQL.executeQuery("SELECT COUNT(*) FROM (" + strSQL + ") AS T1");
            //Registros.last();
            Registros.next();
//            numreg = Integer.parseInt(Registros.getString(1));
            numreg = Registros.getInt(1);
            //numreg = Registros.getRow();
            Registros.close();
            SentenciaSQL.close();
            return numreg;
        } catch (SQLException e) {
            getLogger().log(Level.SEVERE,null,e);
            //System.out.println(e.getMessage());
            //System.out.println(e);
            return 0;
        } finally {
        }
    }
    
    public int bdConregMSSQL(String condicion){
        String strSQL;
        ResultSet Registros = null;
        Statement SentenciaSQL = null;
        int numreg;

        strSQL = condicion;

        try
        {            
            SentenciaSQL =  getConexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println("SELECT TO_CHAR(COUNT(*)) FROM ("+strSQL+")");
            Registros = SentenciaSQL.executeQuery("SELECT TO_CHAR(COUNT(*)) FROM ("+strSQL+")");
            //Registros.last();
            Registros.next();
            numreg = Integer.parseInt(Registros.getString(1));
            //numreg = Registros.getRow();
            Registros.close();
            SentenciaSQL.close();
            return numreg;
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e);
            return 0;
        }finally{
        }
    }
    
    public int bdConregMYSQL(String condicion){
        String strSQL;
        ResultSet Registros = null;
        Statement SentenciaSQL = null;
        int numreg;

        strSQL = condicion;

        try
        {            
            SentenciaSQL =  getConexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println("SELECT TO_CHAR(COUNT(*)) FROM ("+strSQL+")");
            Registros = SentenciaSQL.executeQuery("SELECT TO_CHAR(COUNT(*)) FROM ("+strSQL+")");
            //Registros.last();
            Registros.next();
            numreg = Integer.parseInt(Registros.getString(1));
            //numreg = Registros.getRow();
            Registros.close();
            SentenciaSQL.close();
            return numreg;
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e);
            return 0;
        }finally{
        }
    }


    public List<Map> getListaRegistrosMap(String strSQL) {
        List lista = new ArrayList<HashMap>();
        ResultSet wrkResultSet = null;
        ResultSetMetaData md;

        getLogger().info(strSQL);
        try {
            Statement SentenciaSQL = (Statement) conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            registraBitacora(strSQL);
            wrkResultSet = SentenciaSQL.executeQuery(strSQL);
            md = wrkResultSet.getMetaData();
            int cantCampos = md.getColumnCount();
            int cont = 1;
            while (wrkResultSet.next()) {
                HashMap registro = new HashMap();
//                //System.out.println("cont hash= " + cont);
                for (int i = 1; i <= cantCampos; i++) {

                    if (wrkResultSet.getObject(i) instanceof String) {
                        String cadena = "";
                        cadena = wrkResultSet.getString(i).trim();
                        if (cadena != null && !cadena.equalsIgnoreCase("null")) {
                            registro.put(md.getColumnName(i), (Object) cadena);
                        } else {
                            registro.put(md.getColumnName(i), "");
                        }
                    } else {
                        if (md.getColumnTypeName(i).equals("LONG RAW")) {
                            registro.put(md.getColumnName(i), null);
                        } else if (md.getColumnTypeName(i).equals("CLOB")) {
                            registro.put(md.getColumnName(i), wrkResultSet.getString(i));
                        } else {
                            if (md.getColumnTypeName(i).equals("DATE")) {
                                registro.put(md.getColumnName(i), Util.convierteFechaRegistro(wrkResultSet.getString(i)));
                            } else {
                                if (wrkResultSet.getObject(i) != null && !("" + wrkResultSet.getObject(i)).equalsIgnoreCase("null")) {
                                    registro.put(md.getColumnName(i), wrkResultSet.getObject(i));
                                } else {
                                    registro.put(md.getColumnName(i), "");
                                }
                            }
                        }
                    }
                }
                lista.add(registro);
                cont++;
            }
            SentenciaSQL.close();
            wrkResultSet.close();
        } catch (SQLException e) {
            System.out.println("m:" + e.getMessage());
            System.out.println("e:" + e);
        }

        return lista;
    }

    void registraBitacora(String consulta) {
        /*BIT_SQL bitacora = new BIT_SQL();
        
        InterfaceBDPool bdBitacora = new InterfaceBDPool();
        bdBitacora.setEsquema("DBBITACORA");
        bdBitacora.bdConexion();
        
        InterfaceRegistrosBDPool registrosBitacora = new InterfaceRegistrosBDPool();
        registrosBitacora.setConexion(bdBitacora.getConexion());
        
        bitacora.setIDSQL(registrosBitacora.getSiguienteSecuenciaDec("Bitacora.BD.BIT_SQL", "IDSQL", "1=1"));
        bitacora.setUSUARIO(getUsuario());
        bitacora.setCONSULTA(consulta);
        bitacora.setHOST(getHost());
        bitacora.setFECHAHORA(Util.getNowDate());
        
        registrosBitacora.agregar("Bitacora.BD.BIT_SQL", bitacora);*/
    }
 
//    public int getSecuencia(String tablaBD) {
//        ResultSet siguiente = getRegistros("SELECT " + tablaBD + "_SEC.NEXTVAL FROM DUAL");
//        BigDecimal sec = null;
//        try {
//            if (siguiente != null && siguiente.next()) {
//                sec = siguiente.getInteger(1);
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error obteniendo secuencia de " + tablaBD + ": " + ex.getMessage());
//        } finally {
//            try {
//                siguiente.close();
//            } catch (Exception e) {
//            }
//        }
//        return sec;
//    }
    
    private static Logger getLogger(){
        return Logger.getLogger(InterfaceRegistrosBDPool.class.getName());
    }

 }
