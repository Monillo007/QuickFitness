/*
* Clase generada automaticamente en una aplicación
* escrita por el Ing. Luis Manuel Navarro R. (Make It Bit)
*/

package bd;

import util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.File;


public class pago extends Tabla{
     private Integer idpago;
     private Integer idcliente;
     private BigDecimal monto;
     private Date fecha;
     private Integer idmembresia;
     private String observaciones;
     private Integer estatusregistro;
     private Integer usuariocreacion;
     private Date fechacreacion;
     private Integer usuariomodificacion;
     private Date fechamodificacion;

     public pago () {
          campos = new String[11];
          campos[0] = "idpago";
          campos[1] = "idcliente";
          campos[2] = "monto";
          campos[3] = "fecha";
          campos[4] = "idmembresia";
          campos[5] = "observaciones";
          campos[6] = "estatusregistro";
          campos[7] = "usuariocreacion";
          campos[8] = "fechacreacion";
          campos[9] = "usuariomodificacion";
          campos[10] = "fechamodificacion";
     }


     public pago (Integer idpago, Integer idcliente, BigDecimal monto, Date fecha, Integer idmembresia, String observaciones, Integer estatusregistro, Integer usuariocreacion, Date fechacreacion, Integer usuariomodificacion, Date fechamodificacion){

          this();
          this.idpago = idpago;
          this.idcliente = idcliente;
          this.monto = monto;
          this.fecha = fecha;
          this.idmembresia = idmembresia;
          this.observaciones = observaciones;
          this.estatusregistro = estatusregistro;
          this.usuariocreacion = usuariocreacion;
          this.fechacreacion = fechacreacion;
          this.usuariomodificacion = usuariomodificacion;
          this.fechamodificacion = fechamodificacion;
     }

     @Override
     public void setCampo(String p_nombrecampo, Object p_valorcampo){
          if(p_nombrecampo.equals("idpago")){
               setidpago((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("idcliente")){
               setidcliente((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("monto")){
               setmonto((BigDecimal)p_valorcampo);
          }else if(p_nombrecampo.equals("fecha")){
               setfecha((Date)p_valorcampo);
          }else if(p_nombrecampo.equals("idmembresia")){
               setidmembresia((Integer)p_valorcampo);
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
          ", "+getidcliente()+""+
          ", "+getmonto()+""+
          ", '"+Util.convierteFechaBDMySQL(getfecha())+"'"+
          ", "+getidmembresia()+""+
          ", '"+getobservaciones()+"'"+
          ", "+getestatusregistro()+""+
          ", "+getusuariocreacion()+""+
          ", '"+Util.convierteFechaBDMySQL(getfechacreacion())+"'"+
          ", null"+
          ", null"+
          ")";

          return serializar;
     }

     @Override
     public String getSQLModificar(){
          String serializar;
          serializar = " SET "+
          " idcliente = "+getidcliente()+""+
          ", monto = "+getmonto()+""+
          ", fecha = '"+Util.convierteFechaBDMySQL(getfecha())+"'"+
          ", idmembresia = "+getidmembresia()+""+
          ", observaciones = '"+getobservaciones()+"'"+
          ", estatusregistro = "+getestatusregistro()+""+
          ", usuariomodificacion = "+getusuariomodificacion()+""+
          ", fechamodificacion = '"+Util.convierteFechaBDMySQL(getfechamodificacion())+"'"+
          "";

          return serializar;
     }
     public Integer getidpago(){
                  return idpago;
     }

     public void setidpago(Integer idpago){
          this.idpago = idpago;
     }

     public Integer getidcliente(){
                  return idcliente;
     }

     public void setidcliente(Integer idcliente){
          this.idcliente = idcliente;
     }

     public BigDecimal getmonto(){
                  return monto;
     }

     public void setmonto(BigDecimal monto){
          this.monto = monto;
     }

     public Date getfecha(){
                  return fecha;
     }

     public void setfecha(Date fecha){
          this.fecha = fecha;
     }

     public Integer getidmembresia(){
                  return idmembresia;
     }

     public void setidmembresia(Integer idmembresia){
          this.idmembresia = idmembresia;
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
          return "idpago";
     }

}
