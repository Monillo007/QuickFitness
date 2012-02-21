package Interfacesbd;

import javax.naming.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
//import org.apache.log4j.Logger;

/**
 * @author Ing. Luis Navarro
 */
public class InterfaceBDPool {

    private Connection conexion;
    private String esquema = "fitness";
//    private static Logger logger = Logger.getLogger(InterfaceBDPool.class);
//    private static boolean debug = logger.isDebugEnabled();
//    private static boolean info = logger.isInfoEnabled();

    /**
     * Crea una nueva instancia de la clase de conexión inicializada
     * con los parámetros por defecto.
     */
    public InterfaceBDPool() {
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
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/" + esquema);

//            if (ds.getConnectionCachingEnabled() == false) {
//                ds.setConnectionCachingEnabled(true);
//                Properties props = new Properties();
//                props.setProperty("AbandonedConnectionTimeout", "5");
//                props.setProperty("InactivityTimeout", "3");
//
//                ds.setConnectionCacheProperties(props);
//            }
            if (envContext == null) {
                throw new Exception("Error: No existe un contexto definido");
            }
            if (ds == null) {
                throw new Exception("Error: No se ha localizado el origen de datos '" + esquema + "' (verificar el nombre del esquema y/o la configuración de los archivos META-INF/context.xml y WEB-INF/web.xml)");
            }
            if (ds != null) {
                //((OraclePooledConnection((oracle.jdbc.pool.OracleConnectionPoolDataSource)ds).getConnection())).
                //((OraclePooledConnection)ds.getPooledConnection()).get
                conexion = ds.getConnection();//getConnection();
            }
            if (conexion != null) {
//                ((Connection)conexion).setDefaultRowPrefetch(100);
                return true;
            } else {
                throw new Exception("Error: No se pudo realizar la conexión (verificar la configuración de los archivos META-INF/context.xml y WEB-INF/web.xml)");
            }
        } catch (Exception e) {
            Logger.getLogger(InterfaceBDPool.class.getName()).log(Level.SEVERE,null,e);
            return false;
        }
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
            if(!strSql.contains("CON_DILIGENCIA") || !strSql.contains("AVE_DILIGENCIA")){
                System.out.println(strSql);                
            }
            
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

        Logger.getLogger(InterfaceBDPool.class.getName()).info("bdConReg - "+strSQL);
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
     
    //Transaccion API
    public boolean beginTransaccion() {
        try {
            this.conexion.setAutoCommit(false);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceBDPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean rollback() {
        try {
            this.conexion.rollback();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceBDPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean commit() {
        try {
            this.conexion.commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceRegistrosBDPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
