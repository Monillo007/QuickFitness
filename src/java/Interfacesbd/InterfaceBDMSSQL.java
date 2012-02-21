/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfacesbd;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;


/**
 *
 * @author gbustamanteol
 */
public class InterfaceBDMSSQL {

   private Connection conexion;
    private String esquema = null;
  

    /**
     * Crea una nueva instancia de la clase de conexión inicializada
     * con los parámetros por defecto.
     */
    public InterfaceBDMSSQL() {
    }

    /**
     * Establece el objeto de conexión a la base de datos
     * @param aConexion the conexion to set
     */
    public void setConexion(Connection aConexion) {
        conexion = aConexion;
    }

    /**
     * Establece el esquema con el cual se establece la conexión a la base de datos,
     * por defecto convierte el nombre del esquema a mayúsculas.
     * @param aEsquema the esquema to set
     */
    public void setEsquema(String aEsquema) {

        esquema = aEsquema;
    }

    /**
     * Obtiene el esquema con el cual se establece la conexión a la base de datos
     * @return El esquema actual
     */
    public String getEsquema() {
        return esquema;
    }

    /**
     * Obtiene la conexión a la base de datos
     * @return la conexión realizada o null
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Crea la conexión a la base de datos en hacia el esquema definido (por defecto a DBCATALOGO)
     * utilizando un pool de conexiones.
     * @return true si se pudo realizar la conexion, false de lo contrario
     */
    public boolean bdConexion() {
       /*try {
            if (getConexion() != null && !conexion.isClosed()) {
                return true;
            }

            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            conexion = DriverManager.getConnection("jdbc:sqlserver://192.168.202.231:1433;databaseName="+esquema, "Dynasearch", "omega3");
            getConexion().setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e);
            return false;
        }*/
        return true;
    }

    /**
     * Cierra la conexión a la base de datos en hacia el esquema definido (por defecto a DBCATALOGO)
     * utilizando un pool de conexiones.
     * @return true si se pudo realizar la conexion, false de lo contrario
     */
    public boolean bdCierra() {
        if (getConexion() != null) {
            try {
                getConexion().close();
                return true;
            } catch (SQLException ex) {
                System.out.println("No se pudo cerrar la conexion: -Con. Id: " + getConexion() + ", ESQUEMA:" + getEsquema());
            }
        }
        return false;
    }
    /**
     * Cierra un Statement lidiando con nulos y excepciones
     * @return true si se pudo realizar la conexion, false de lo contrario
     */
    public boolean bdCierra(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
                return true;
            } catch (SQLException ex) {
                System.out.println("No se pudo cerrar el estatement: -Stmt. Id: " + stmt + ", ESQUEMA:" + getEsquema());
            }
        }
        return false;
    }
    /**
     * Cierra un PreparedStatement lidiando con nulos y excepciones
     * @return true si se pudo realizar la conexion, false de lo contrario
     */
    public boolean bdCierra(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
                return true;
            } catch (SQLException ex) {
                System.out.println("No se pudo cerrar el estatement: -PreparedStmt. Id: " + stmt + ", ESQUEMA:" + getEsquema());
            }
        }
        return false;
    }
    /**
     * Cierra un ResultSet lidiando con nulos y excepciones
     * @return true si se pudo realizar la conexion, false de lo contrario
     */
    public boolean bdCierra(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
                return true;
            } catch (SQLException ex) {
                System.out.println("No se pudo cerrar el estatement: -ResultSet. Id: " + resultSet + ", ESQUEMA:" + getEsquema());
            }
        }
        return false;
    }

    /**
     * Ejecuta una operacion SQL directamente a la base de datos.
     * @param strSql La operación a realizar
     * @return true si se efectúa correctamente, false de lo contrario
     */
    public boolean bdEjesql(String strSql) {
        try {
            Statement stmt = null;

            stmt = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            //System.out.println(strSql);
            stmt.executeUpdate(strSql);
            getConexion().commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e);
            return false;
        }

        return true;
    }

    /**
     * Cuenta los registros obtenidos de una consulta
     * @param wrptabla La tabla o tablas de donde de va a consultar
     * @param wrpcondicion La condicion a aplicar
     * @return int contador de los registros
     */
    public int bdConreg(String wrptabla, String wrpcondicion) {
        String strSQL;
        ResultSet Registros = null;
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

        //System.out.println(strSQL);
        try {
            Statement SentenciaSQL = getConexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Registros = SentenciaSQL.executeQuery(strSQL);
            Registros.next();
            numreg = Registros.getInt(1);
            return numreg;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e);
            return 0;
        }
    }
}
