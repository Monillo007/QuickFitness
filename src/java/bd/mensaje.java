/*
* Clase generada automaticamente en una aplicación
* escrita por el Ing. Luis Manuel Navarro R. (Make It Bit)
*/

package bd;

import util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.File;


public class mensaje extends Tabla{
     private Integer idmensaje;
     private Integer idcliente;
     private Integer idtipomensaje;
     private String mensaje;
     private Date fecha;
     private String observaciones;
     private Integer estatusregistro;
     private Integer usuariocreacion;
     private Date fechacreacion;
     private Integer usuariomodificacion;
     private Date fechamodificacion;

     public mensaje () {
          campos = new String[11];
          campos[0] = "idmensaje";
          campos[1] = "idcliente";
          campos[2] = "idtipomensaje";
          campos[3] = "mensaje";
          campos[4] = "fecha";
          campos[5] = "observaciones";
          campos[6] = "estatusregistro";
          campos[7] = "usuariocreacion";
          campos[8] = "fechacreacion";
          campos[9] = "usuariomodificacion";
          campos[10] = "fechamodificacion";
     }


     public mensaje (Integer idmensaje, Integer idcliente, Integer idtipomensaje, String mensaje, Date fecha, String observaciones, Integer estatusregistro, Integer usuariocreacion, Date fechacreacion, Integer usuariomodificacion, Date fechamodificacion){

          this();
          this.idmensaje = idmensaje;
          this.idcliente = idcliente;
          this.idtipomensaje = idtipomensaje;
          this.mensaje = mensaje;
          this.fecha = fecha;
          this.observaciones = observaciones;
          this.estatusregistro = estatusregistro;
          this.usuariocreacion = usuariocreacion;
          this.fechacreacion = fechacreacion;
          this.usuariomodificacion = usuariomodificacion;
          this.fechamodificacion = fechamodificacion;
     }

     @Override
     public void setCampo(String p_nombrecampo, Object p_valorcampo){
          if(p_nombrecampo.equals("idmensaje")){
               setidmensaje((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("idcliente")){
               setidcliente((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("idtipomensaje")){
               setidtipomensaje((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("mensaje")){
               setmensaje((String)p_valorcampo);
          }else if(p_nombrecampo.equals("fecha")){
               setfecha((Date)p_valorcampo);
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
          ""+getidmensaje()+""+
          ", "+getidcliente()+""+
          ", "+getidtipomensaje()+""+
          ", '"+getmensaje()+"'"+
          ", '"+Util.convierteFechaBDMySQL(getfecha())+"'"+
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
          "idmensaje = "+getidmensaje()+""+
          ", idcliente = "+getidcliente()+""+
          ", idtipomensaje = "+getidtipomensaje()+""+
          ", mensaje = '"+getmensaje()+"'"+
          ", fecha = '"+Util.convierteFechaBDMySQL(getfecha())+"'"+
          ", observaciones = '"+getobservaciones()+"'"+
          ", estatusregistro = "+getestatusregistro()+""+
          ", usuariocreacion = "+getusuariocreacion()+""+
          ", fechacreacion = '"+Util.convierteFechaBDMySQL(getfechacreacion())+"'"+
          ", usuariomodificacion = "+getusuariomodificacion()+""+
          ", fechamodificacion = '"+Util.convierteFechaBDMySQL(getfechamodificacion())+"'"+
          "";

          return serializar;
     }
     public Integer getidmensaje(){
                  return idmensaje;
     }

     public void setidmensaje(Integer idmensaje){
          this.idmensaje = idmensaje;
     }

     public Integer getidcliente(){
                  return idcliente;
     }

     public void setidcliente(Integer idcliente){
          this.idcliente = idcliente;
     }

     public Integer getidtipomensaje(){
                  return idtipomensaje;
     }

     public void setidtipomensaje(Integer idtipomensaje){
          this.idtipomensaje = idtipomensaje;
     }

     public String getmensaje(){
          if(mensaje==null){
               return "";
          }else{
                  return mensaje;
          }
     }

     public void setmensaje(String mensaje){
          this.mensaje = mensaje;
     }

     public Date getfecha(){
                  return fecha;
     }

     public void setfecha(Date fecha){
          this.fecha = fecha;
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
          return "idmensaje";
     }

}
