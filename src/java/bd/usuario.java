/*
* Clase generada automaticamente en una aplicación
* escrita por el Ing. Luis Manuel Navarro R. (Make It Bit)
*/

package bd;

import util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.File;


public class usuario extends Tabla{
     private Integer idusuario;
     private String usuario;
     private String contrasena;
     private Integer idgimnasio;
     private Integer idtipousuario;
     private Integer estatusregistro;
     private Integer usuariocreacion;
     private Date fechacreacion;
     private Integer usuariomodificacion;
     private Date fechamodificacion;

     public usuario () {
          campos = new String[10];
          campos[0] = "idusuario";
          campos[1] = "usuario";
          campos[2] = "contrasena";
          campos[3] = "idgimnasio";
          campos[4] = "idtipousuario";
          campos[5] = "estatusregistro";
          campos[6] = "usuariocreacion";
          campos[7] = "fechacreacion";
          campos[8] = "usuariomodificacion";
          campos[9] = "fechamodificacion";
     }


     public usuario (Integer idusuario, String usuario, String contrasena, Integer idgimnasio, Integer idtipousuario, Integer estatusregistro, Integer usuariocreacion, Date fechacreacion, Integer usuariomodificacion, Date fechamodificacion){

          this();
          this.idusuario = idusuario;
          this.usuario = usuario;
          this.contrasena = contrasena;
          this.idgimnasio = idgimnasio;
          this.idtipousuario = idtipousuario;
          this.estatusregistro = estatusregistro;
          this.usuariocreacion = usuariocreacion;
          this.fechacreacion = fechacreacion;
          this.usuariomodificacion = usuariomodificacion;
          this.fechamodificacion = fechamodificacion;
     }

     @Override
     public void setCampo(String p_nombrecampo, Object p_valorcampo){
          if(p_nombrecampo.equals("idusuario")){
               setidusuario((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("usuario")){
               setusuario((String)p_valorcampo);
          }else if(p_nombrecampo.equals("contrasena")){
               setcontrasena((String)p_valorcampo);
          }else if(p_nombrecampo.equals("idgimnasio")){
               setidgimnasio((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("idtipousuario")){
               setidtipousuario((Integer)p_valorcampo);
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
          "default"+
          ", '"+getusuario()+"'"+
          ", '"+getcontrasena()+"'"+
          ", "+getidgimnasio()+""+
          ", "+getidtipousuario()+""+
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
          "idusuario = "+getidusuario()+""+
          ", usuario = '"+getusuario()+"'"+
          ", contrasena = '"+getcontrasena()+"'"+
          ", idgimnasio = "+getidgimnasio()+""+
          ", idtipousuario = "+getidtipousuario()+""+
          ", estatusregistro = "+getestatusregistro()+""+
          ", usuariocreacion = "+getusuariocreacion()+""+
          ", fechacreacion = '"+Util.convierteFechaBDMySQL(getfechacreacion())+"'"+
          ", usuariomodificacion = "+getusuariomodificacion()+""+
          ", fechamodificacion = '"+Util.convierteFechaBDMySQL(getfechamodificacion())+"'"+
          "";

          return serializar;
     }
     public Integer getidusuario(){
                  return idusuario;
     }

     public void setidusuario(Integer idusuario){
          this.idusuario = idusuario;
     }

     public String getusuario(){
          if(usuario==null){
               return "";
          }else{
                  return usuario;
          }
     }

     public void setusuario(String usuario){
          this.usuario = usuario;
     }

     public String getcontrasena(){
          if(contrasena==null){
               return "";
          }else{
                  return contrasena;
          }
     }

     public void setcontrasena(String contrasena){
          this.contrasena = contrasena;
     }

     public Integer getidgimnasio(){
                  return idgimnasio;
     }

     public void setidgimnasio(Integer idgimnasio){
          this.idgimnasio = idgimnasio;
     }

     public Integer getidtipousuario(){
                  return idtipousuario;
     }

     public void setidtipousuario(Integer idtipousuario){
          this.idtipousuario = idtipousuario;
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
          return "idusuario";
     }

}
