/*
 * sisibd.java
 *
 * Created on 21 de abril de 2007, 01:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


package Interfacesbd;


import java.sql.*;
import java.util.Properties;


/**
 *
 * @author Ing. Luis Navarro
 */
public class InterfaceBD {
    private static Connection conexion;
    private static String usuario = "PGJDEVELOP";
    private static String password = "beiberlin";
    private static String baseDeDatos = "XE";
    private static String servidor = "192.168.202.123";
    private static String puerto = "1521";
    private static String esquema = "SIAD";

    /**
     * Crea una nueva instancia de la clase de conexión inicializada
     * con los parámetros por defecto.
     */
    public InterfaceBD() {
    }

    /**
     * Crea una nueva instancia de la clase de conexión a la base de datos
     * especificando los parámetros para realizar la conexión.
     * @param servidor El servidor al que se va a conectar.
     * @param puerto El puerto sobre el cual se va a conectar.
     * @param baseDeDatos La base de datos a la que se va a conectar.
     * @param usuario El usuario requerido para realizar la conexión.
     * @param password La contraseña de acceso a la base de datos.
     * @param esquema El esquema elegido para la conexión.
     */
    public InterfaceBD(String servidor, String puerto, String baseDeDatos,
                       String usuario, String password, String esquema){
       setServidor(servidor);
       setPuerto(puerto);
       setBaseDeDatos(baseDeDatos);
       setUsuario(usuario);
       setPassword(password);
       setEsquema(esquema);
    }

    /**
     * Establece el objeto de conexión a la base de datos
     *
     * @param aConexion the conexion to set
     */
    public static void setConexion(Connection aConexion) {
        conexion = aConexion;
    }

    /**
     * Obtiene el usuario con el cual se establece la conexión a la base de datos
     * @return the usuario
     */
    public static String getUsuario() {
        return usuario;
    }

    /**
     * Obtiene el password con el cual se establece la conexión a la base de datos
     * @return the password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * Obtiene la base de datos a la cual se establece la conexión
     * @return the baseDeDatos
     */
    public static String getBaseDeDatos() {
        return baseDeDatos;
    }

    /**
     * Establece la base de datos a la cual se realizará la conexión
     * @param aBaseDeDatos the baseDeDatos to set
     */
    public static void setBaseDeDatos(String aBaseDeDatos) {
        baseDeDatos = aBaseDeDatos;
    }

    /**
     * Obtiene el servidor con el cual se establece la conexión a la base de datos
     * @return the servidor
     */
    public static String getServidor() {
        return servidor;
    }

    /**
     * Establece el servidor con el cual se establece la conexión a la base de datos
     * @param aServidor the servidor to set
     */
    public static void setServidor(String aServidor) {
        servidor = aServidor;
    }

    /**
     * Obtiene el puerto de conexión a la base de datos, por defecto es 1521
     * @return the puerto
     */
    public static String getPuerto() {
        return puerto;
    }

    /**
     * Establece el puerto con el cual se establece la conexión a la base de datos,
     * por defecto es 1521
     * @param aPuerto the puerto to set
     */
    public static void setPuerto(String aPuerto) {
        puerto = aPuerto;
    }

    /**
     * Obtiene el esquema con el cual se establece la conexión a la base de datos
     * @return the esquema
     */
    public static String getEsquema() {
        return esquema;
    }

    /**
     * Establece el esquema con el cual se establece la conexión a la base de datos,
     * por defecto convierte el nombre del esquema a mayúsculas.
     * @param aEsquema the esquema to set
     */
    public static void setEsquema(String aEsquema) {
        aEsquema = aEsquema.toUpperCase();
        esquema = aEsquema;
        baseDeDatos = aEsquema;
    }        
    
    public static Connection getConexion(){
        return conexion;
    }

    /**
     * Establece el usuario con el cual se realizará la conexión a la base de datos
     * @param parusuario
     */
    public static void setUsuario(String parusuario){
        usuario = parusuario;
    }

    /**
     * Establece la contraseña con la cual se establece la conexión a la base de datos
     * @param parpassword
     */
    public static void setPassword(String parpassword){
        password = parpassword;
    }


    /**
     * Crea la conexión a la base de datos en base a los parámetros definidos
     * @return true si se pudo realizar la conexion, false de lo contrario
     */
    public boolean bdConexion()
    {    
        try
        {
             if (getConexion() != null && !conexion.isClosed()){
                return true;
            }

        /***********--- DB2 ---*******************/
//        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//        String BaseDeDatos = "jdbc:odbc:jbdhorarios";
        
             
        /***********--- MYSQL ---*******************/
     //   Class.forName("com.mysql.jdbc.Driver");
     //   String BaseDeDatos = "jdbc:mysql://localhost/sissoc?user=usuario1&password=usuario1&useCursorFetch=true";

        /***********--- ORACLE ---*******************/
        Class.forName("oracle.jdbc.OracleDriver");
        String BaseDeDatos = "jdbc:oracle:thin:@"+servidor+":"+puerto+":"+baseDeDatos;
        //String BaseDeDatos = "jdbc:oracle:thin:@//"+servidor+":"+puerto+"/"+baseDeDatos+"";
        Properties props = new Properties();
        props.put("user", usuario );
        props.put("password", password);
        props.put("SetBigStringTryClob", "true");

        setConexion(DriverManager.getConnection(BaseDeDatos,props));
        //conexion = DriverManager.getConnection(BaseDeDatos);
           
        //String BaseDeDatos = "jdbc:db2://localhost:50000/sociosbd"; //:currentSchema=socios;";
      /*  Properties connectProperties = new Properties();
        connectProperties.put("currentSchema",esquema);  // Debe de ser en mayusculas
        connectProperties.put("user",usuario);
        connectProperties.put("password",password);*/
        /*
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        conexion = DriverManager.getConnection(BaseDeDatos,connectProperties);
        */
            getConexion().setAutoCommit(false);
        }
        catch(ClassNotFoundException e){
            System.out.println("Clase no encontrada");
            System.out.println(e);
            return false; 
        } 
        catch(SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e);
            return false;
        }
        return true;
    }


    /**
     * Realiza el commit a todas las operaciones realizadas en la base de datos
     * y posteriormente cierra la conexión
     * @return
     */
    public boolean  bdCierra(){    
        try
        {
            if(getConexion()!=null){
                getConexion().commit();
                getConexion().close();
            }
        }
        catch(SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e);
            return false;
        }
        return true;
        
    }

    /**
     * Ejecuta una operacion SQL directamente a la base de datos.
     * @param strSql La operación a realizar
     * @return true si se efectúa correctamente, false de lo contrario
     */
    public boolean bdEjesql(String strSql){
        try
        {
            Statement stmt = null;
            int registros_afectados = 0;
            // CONCUR_READ_ONLY  no bloquea el registro
            stmt = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY); // , ResultSet. .CLOSE_CURSORS_AT_COMMIT); 
            System.out.println(strSql);
            registros_afectados = stmt.executeUpdate(strSql);
            getConexion().commit();
            stmt.close(); 	    
        }
        catch(SQLException e){
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
    public int bdConreg(String wrptabla, String wrpcondicion){
        String strSQL;
        ResultSet Registros = null;
        int numreg;
        String [] nombreTabla = wrptabla.split("\\.");
        String nombre="";
        if(true){
            nombre = wrptabla;
        }else{
            nombre = nombreTabla[nombreTabla.length-1];
        }
        
        strSQL = "SELECT count(*)";
        strSQL = strSQL + " FROM " + nombre ;
        
        if (!wrpcondicion.equals("")){
            strSQL = strSQL + " WHERE " + wrpcondicion;
        }
        
        //System.out.println(strSQL);
        try
        {
            Statement SentenciaSQL = getConexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Registros = SentenciaSQL.executeQuery(strSQL);
            Registros.next();
            numreg = Registros.getInt(1);
            return numreg;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e);
            return 0;
        }
    }
    

    
}
