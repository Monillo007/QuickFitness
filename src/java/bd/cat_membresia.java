/*
* Clase generada automaticamente en una aplicación
* escrita por el Ing. Luis Manuel Navarro R. (Make It Bit)
*/

package bd;

import util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.File;


public class cat_membresia extends Tabla{
     private Integer idmembresia;
     private Integer idgimnasio;
     private String descripcion;
     private Integer dias;
     private Integer creditosxdia;
     private BigDecimal monto;
     private String observaciones;
     private Integer estatusregistro;
     private Integer usuariocreacion;
     private Date fechacreacion;
     private Integer usuariomodificacion;
     private Date fechamodificacion;

     public cat_membresia () {
          campos = new String[12];
          campos[0] = "idmembresia";
          campos[1] = "idgimnasio";
          campos[2] = "descripcion";
          campos[3] = "dias";
          campos[4] = "creditosxdia";
          campos[5] = "monto";
          campos[6] = "observaciones";
          campos[7] = "estatusregistro";
          campos[8] = "usuariocreacion";
          campos[9] = "fechacreacion";
          campos[10] = "usuariomodificacion";
          campos[11] = "fechamodificacion";
     }


     public cat_membresia (Integer idmembresia, Integer idgimnasio, String descripcion, Integer dias, Integer creditosxdia, BigDecimal monto, String observaciones, Integer estatusregistro, Integer usuariocreacion, Date fechacreacion, Integer usuariomodificacion, Date fechamodificacion){

          this();
          this.idmembresia = idmembresia;
          this.idgimnasio = idgimnasio;
          this.descripcion = descripcion;
          this.dias = dias;
          this.creditosxdia = creditosxdia;
          this.monto = monto;
          this.observaciones = observaciones;
          this.estatusregistro = estatusregistro;
          this.usuariocreacion = usuariocreacion;
          this.fechacreacion = fechacreacion;
          this.usuariomodificacion = usuariomodificacion;
          this.fechamodificacion = fechamodificacion;
     }

     @Override
     public void setCampo(String p_nombrecampo, Object p_valorcampo){
          if(p_nombrecampo.equals("idmembresia")){
               setidmembresia((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("idgimnasio")){
               setidgimnasio((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("descripcion")){
               setdescripcion((String)p_valorcampo);
          }else if(p_nombrecampo.equals("dias")){
               setdias((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("creditosxdia")){
               setcreditosxdia((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("monto")){
               setmonto((BigDecimal)p_valorcampo);
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
          "default"+
          ", "+getidgimnasio()+""+
          ", '"+getdescripcion()+"'"+
          ", "+getdias()+""+
          ", "+getcreditosxdia()+""+
          ", "+getmonto()+""+
          ", '"+getobservaciones()+"'"+
          ", "+getestatusregistro()+""+
          ", "+getusuariocreacion()+""+
          ", '"+Util.convierteFechaBDMySQL(getfechacreacion())+"'"+
          ", NULL"+
          ", NULL"+
          ")";

          return serializar;
     }

     @Override
     public String getSQLModificar(){
          String serializar;
          serializar = " SET "+
          " idgimnasio = "+getidgimnasio()+""+
          ", descripcion = '"+getdescripcion()+"'"+
          ", dias = "+getdias()+""+
          ", creditosxdia = "+getcreditosxdia()+""+
          ", monto = "+getmonto()+""+
          ", observaciones = '"+getobservaciones()+"'"+
          ", estatusregistro = "+getestatusregistro()+""+
          ", usuariomodificacion = "+getusuariomodificacion()+""+
          ", fechamodificacion = '"+Util.convierteFechaBDMySQL(getfechamodificacion())+"'"+
          "";

          return serializar;
     }
     public Integer getidmembresia(){
                  return idmembresia;
     }

     public void setidmembresia(Integer idmembresia){
          this.idmembresia = idmembresia;
     }

     public Integer getidgimnasio(){
                  return idgimnasio;
     }

     public void setidgimnasio(Integer idgimnasio){
          this.idgimnasio = idgimnasio;
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

     public Integer getdias(){
                  return dias;
     }

     public void setdias(Integer dias){
          this.dias = dias;
     }

     public Integer getcreditosxdia(){
                  return creditosxdia;
     }

     public void setcreditosxdia(Integer creditosxdia){
          this.creditosxdia = creditosxdia;
     }

     public BigDecimal getmonto(){
                  return monto;
     }

     public void setmonto(BigDecimal monto){
          this.monto = monto;
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
          return "idmembresia";
     }

}
