/*
* Clase generada automaticamente en una aplicación
* escrita por el Ing. Luis Manuel Navarro R. (Make It Bit)
*/

package bd;

import util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.File;


public class cat_tipousuario extends Tabla{
     private Integer idtipousuario;
     private String descripcion;
     private String observaciones;
     private Integer estatusregistro;
     private Integer usuariocreacion;
     private Date fechacreacion;
     private Integer usuariomodificacion;
     private Date fechamodificacion;

     public cat_tipousuario () {
          campos = new String[8];
          campos[0] = "idtipousuario";
          campos[1] = "descripcion";
          campos[2] = "observaciones";
          campos[3] = "estatusregistro";
          campos[4] = "usuariocreacion";
          campos[5] = "fechacreacion";
          campos[6] = "usuariomodificacion";
          campos[7] = "fechamodificacion";
     }


     public cat_tipousuario (Integer idtipousuario, String descripcion, String observaciones, Integer estatusregistro, Integer usuariocreacion, Date fechacreacion, Integer usuariomodificacion, Date fechamodificacion){

          this();
          this.idtipousuario = idtipousuario;
          this.descripcion = descripcion;
          this.observaciones = observaciones;
          this.estatusregistro = estatusregistro;
          this.usuariocreacion = usuariocreacion;
          this.fechacreacion = fechacreacion;
          this.usuariomodificacion = usuariomodificacion;
          this.fechamodificacion = fechamodificacion;
     }

     @Override
     public void setCampo(String p_nombrecampo, Object p_valorcampo){
          if(p_nombrecampo.equals("idtipousuario")){
               setidtipousuario((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("descripcion")){
               setdescripcion((String)p_valorcampo);
          }else if(p_nombrecampo.equals("observaciones")){
               setobservaciones((String)p_valorcampo);
          }else if(p_nombrecampo.equals("estatusregistro")){
               setestatusregistro((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("usuariocreacion")){
               setusuariocreacion((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("fechacreacion")){
               setfechacreacion((Date)p_valorcampo);
          }else if(p_nombrecampo.equals("usuariomodificacion")){
               setusuariomodificacion((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("fechamodificacion")){
               setfechamodificacion((Date)p_valorcampo);
          }
     }

     @Override
     public String getSQLInsertar(){
          String serializar;
          serializar = " VALUES("+
          ""+getidtipousuario()+""+
          ", '"+getdescripcion()+"'"+
          ", '"+getobservaciones()+"'"+
          ", "+getestatusregistro()+""+
          ", "+getusuariocreacion()+""+
          ", '"+Util.convierteFechaBDMySQL(getfechacreacion())+"'"+
          ", "+getusuariomodificacion()+""+
          ", '"+Util.convierteFechaBDMySQL(getfechamodificacion())+"'"+
          ")";

          return serializar;
     }

     @Override
     public String getSQLModificar(){
          String serializar;
          serializar = " SET "+
          "idtipousuario = "+getidtipousuario()+""+
          ", descripcion = '"+getdescripcion()+"'"+
          ", observaciones = '"+getobservaciones()+"'"+
          ", estatusregistro = "+getestatusregistro()+""+
          ", usuariocreacion = "+getusuariocreacion()+""+
          ", fechacreacion = '"+Util.convierteFechaBDMySQL(getfechacreacion())+"'"+
          ", usuariomodificacion = "+getusuariomodificacion()+""+
          ", fechamodificacion = '"+Util.convierteFechaBDMySQL(getfechamodificacion())+"'"+
          "";

          return serializar;
     }
     public Integer getidtipousuario(){
                  return idtipousuario;
     }

     public void setidtipousuario(Integer idtipousuario){
          this.idtipousuario = idtipousuario;
     }

     public String getdescripcion(){
          if(descripcion==null){
               return "";
          }else{
                  return descripcion;
          }
     }

     public void setdescripcion(String descripcion){
          this.descripcion = descripcion;
     }

     public String getobservaciones(){
          if(observaciones==null){
               return "";
          }else{
                  return observaciones;
          }
     }

     public void setobservaciones(String observaciones){
          this.observaciones = observaciones;
     }

     public Integer getestatusregistro(){
                  return estatusregistro;
     }

     public void setestatusregistro(Integer estatusregistro){
          this.estatusregistro = estatusregistro;
     }

     public Integer getusuariocreacion(){
                  return usuariocreacion;
     }

     public void setusuariocreacion(Integer usuariocreacion){
          this.usuariocreacion = usuariocreacion;
     }

     public Date getfechacreacion(){
                  return fechacreacion;
     }

     public void setfechacreacion(Date fechacreacion){
          this.fechacreacion = fechacreacion;
     }

     public Integer getusuariomodificacion(){
                  return usuariomodificacion;
     }

     public void setusuariomodificacion(Integer usuariomodificacion){
          this.usuariomodificacion = usuariomodificacion;
     }

     public Date getfechamodificacion(){
                  return fechamodificacion;
     }

     public void setfechamodificacion(Date fechamodificacion){
          this.fechamodificacion = fechamodificacion;
     }

     public String getID() {
          return "idtipousuario";
     }

}
