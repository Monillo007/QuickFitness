/*
* Clase generada automaticamente en una aplicación
* escrita por el Ing. Luis Manuel Navarro R. (Make It Bit)
*/

package bd;

import util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.File;


public class bitacora_sistema extends Tabla{
     private Integer idbitacora;
     private Integer idusuario;
     private String host;
     private String equipo;
     private Date fechor_entrada;
     private Date fechor_salida;
     private Integer numingreso;
     private Integer estatusregistro;

     public bitacora_sistema () {
          campos = new String[8];
          campos[0] = "idbitacora";
          campos[1] = "idusuario";
          campos[2] = "host";
          campos[3] = "equipo";
          campos[4] = "fechor_entrada";
          campos[5] = "fechor_salida";
          campos[6] = "numingreso";
          campos[7] = "estatusregistro";
     }


     public bitacora_sistema (Integer idbitacora, Integer idusuario, String host, String equipo, Date fechor_entrada, Date fechor_salida, Integer numingreso, Integer estatusregistro){

          this();
          this.idbitacora = idbitacora;
          this.idusuario = idusuario;
          this.host = host;
          this.equipo = equipo;
          this.fechor_entrada = fechor_entrada;
          this.fechor_salida = fechor_salida;
          this.numingreso = numingreso;
          this.estatusregistro = estatusregistro;
     }

     @Override
     public void setCampo(String p_nombrecampo, Object p_valorcampo){
          if(p_nombrecampo.equals("idbitacora")){
               setidbitacora((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("idusuario")){
               setidusuario((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("host")){
               sethost((String)p_valorcampo);
          }else if(p_nombrecampo.equals("equipo")){
               setequipo((String)p_valorcampo);
          }else if(p_nombrecampo.equals("fechor_entrada")){
               setfechor_entrada((Date)p_valorcampo);
          }else if(p_nombrecampo.equals("fechor_salida")){
               setfechor_salida((Date)p_valorcampo);
          }else if(p_nombrecampo.equals("numingreso")){
               setnumingreso((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("estatusregistro")){
               setestatusregistro((Integer)p_valorcampo);
          }
     }

     @Override
     public String getSQLInsertar(){
          String serializar;
          serializar = " VALUES("+
          ""+getidbitacora()+""+
          ", "+getidusuario()+""+
          ", '"+gethost()+"'"+
          ", '"+getequipo()+"'"+
          ", '"+Util.convierteFechaBDMySQL(getfechor_entrada())+"'"+
          ", '"+Util.convierteFechaBDMySQL(getfechor_salida())+"'"+
          ", "+getnumingreso()+""+
          ", "+getestatusregistro()+""+
          ")";

          return serializar;
     }

     @Override
     public String getSQLModificar(){
          String serializar;
          serializar = " SET "+
          "idbitacora = "+getidbitacora()+""+
          ", idusuario = "+getidusuario()+""+
          ", host = '"+gethost()+"'"+
          ", equipo = '"+getequipo()+"'"+
          ", fechor_entrada = '"+Util.convierteFechaBDMySQL(getfechor_entrada())+"'"+
          ", fechor_salida = '"+Util.convierteFechaBDMySQL(getfechor_salida())+"'"+
          ", numingreso = "+getnumingreso()+""+
          ", estatusregistro = "+getestatusregistro()+""+
          "";

          return serializar;
     }
     public Integer getidbitacora(){
                  return idbitacora;
     }

     public void setidbitacora(Integer idbitacora){
          this.idbitacora = idbitacora;
     }

     public Integer getidusuario(){
                  return idusuario;
     }

     public void setidusuario(Integer idusuario){
          this.idusuario = idusuario;
     }

     public String gethost(){
          if(host==null){
               return "";
          }else{
                  return host;
          }
     }

     public void sethost(String host){
          this.host = host;
     }

     public String getequipo(){
          if(equipo==null){
               return "";
          }else{
                  return equipo;
          }
     }

     public void setequipo(String equipo){
          this.equipo = equipo;
     }

     public Date getfechor_entrada(){
                  return fechor_entrada;
     }

     public void setfechor_entrada(Date fechor_entrada){
          this.fechor_entrada = fechor_entrada;
     }

     public Date getfechor_salida(){
                  return fechor_salida;
     }

     public void setfechor_salida(Date fechor_salida){
          this.fechor_salida = fechor_salida;
     }

     public Integer getnumingreso(){
                  return numingreso;
     }

     public void setnumingreso(Integer numingreso){
          this.numingreso = numingreso;
     }

     public Integer getestatusregistro(){
                  return estatusregistro;
     }

     public void setestatusregistro(Integer estatusregistro){
          this.estatusregistro = estatusregistro;
     }

     public String getID() {
          return "idbitacora";
     }

}
