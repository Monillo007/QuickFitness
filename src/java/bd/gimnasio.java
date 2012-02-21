/*
* Clase generada automaticamente en una aplicación
* escrita por el Ing. Luis Manuel Navarro R. (Make It Bit)
*/

package bd;

import util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.File;


public class gimnasio extends Tabla{
     private Integer idgimnasio;
     private String nombre;
     private String rfc;
     private String calle;
     private String colonia;
     private String numext;
     private String numint;
     private String telefono;
     private String correoe;
     private String responsable;
     private String nextel;
     private Date vigenciadel;
     private Date vigenciaal;
     private String observaciones;
     private Integer estatusregistro;
     private Integer usuariocreacion;
     private Date fechacreacion;
     private Integer usuariomodificacion;
     private Date fechamodificacion;

     public gimnasio () {
          campos = new String[19];
          campos[0] = "idgimnasio";
          campos[1] = "nombre";
          campos[2] = "rfc";
          campos[3] = "calle";
          campos[4] = "colonia";
          campos[5] = "numext";
          campos[6] = "numint";
          campos[7] = "telefono";
          campos[8] = "correoe";
          campos[9] = "responsable";
          campos[10] = "nextel";
          campos[11] = "vigenciadel";
          campos[12] = "vigenciaal";
          campos[13] = "observaciones";
          campos[14] = "estatusregistro";
          campos[15] = "usuariocreacion";
          campos[16] = "fechacreacion";
          campos[17] = "usuariomodificacion";
          campos[18] = "fechamodificacion";
     }


     public gimnasio (Integer idgimnasio, String nombre, String rfc, String calle, String colonia, String numext, String numint, String telefono, String correoe, String responsable, String nextel, Date vigenciadel, Date vigenciaal, String observaciones, Integer estatusregistro, Integer usuariocreacion, Date fechacreacion, Integer usuariomodificacion, Date fechamodificacion){

          this();
          this.idgimnasio = idgimnasio;
          this.nombre = nombre;
          this.rfc = rfc;
          this.calle = calle;
          this.colonia = colonia;
          this.numext = numext;
          this.numint = numint;
          this.telefono = telefono;
          this.correoe = correoe;
          this.responsable = responsable;
          this.nextel = nextel;
          this.vigenciadel = vigenciadel;
          this.vigenciaal = vigenciaal;
          this.observaciones = observaciones;
          this.estatusregistro = estatusregistro;
          this.usuariocreacion = usuariocreacion;
          this.fechacreacion = fechacreacion;
          this.usuariomodificacion = usuariomodificacion;
          this.fechamodificacion = fechamodificacion;
     }

     @Override
     public void setCampo(String p_nombrecampo, Object p_valorcampo){
          if(p_nombrecampo.equals("idgimnasio")){
               setidgimnasio((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("nombre")){
               setnombre((String)p_valorcampo);
          }else if(p_nombrecampo.equals("rfc")){
               setrfc((String)p_valorcampo);
          }else if(p_nombrecampo.equals("calle")){
               setcalle((String)p_valorcampo);
          }else if(p_nombrecampo.equals("colonia")){
               setcolonia((String)p_valorcampo);
          }else if(p_nombrecampo.equals("numext")){
               setnumext((String)p_valorcampo);
          }else if(p_nombrecampo.equals("numint")){
               setnumint((String)p_valorcampo);
          }else if(p_nombrecampo.equals("telefono")){
               settelefono((String)p_valorcampo);
          }else if(p_nombrecampo.equals("correoe")){
               setcorreoe((String)p_valorcampo);
          }else if(p_nombrecampo.equals("responsable")){
               setresponsable((String)p_valorcampo);
          }else if(p_nombrecampo.equals("nextel")){
               setnextel((String)p_valorcampo);
          }else if(p_nombrecampo.equals("vigenciadel")){
               setvigenciadel((Date)p_valorcampo);
          }else if(p_nombrecampo.equals("vigenciaal")){
               setvigenciaal((Date)p_valorcampo);
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
          ", '"+getnombre()+"'"+
          ", '"+getrfc()+"'"+
          ", '"+getcalle()+"'"+
          ", '"+getcolonia()+"'"+
          ", '"+getnumext()+"'"+
          ", '"+getnumint()+"'"+
          ", '"+gettelefono()+"'"+
          ", '"+getcorreoe()+"'"+
          ", '"+getresponsable()+"'"+
          ", '"+getnextel()+"'"+
          ", '"+Util.convierteFechaBDMySQL(getvigenciadel())+"'"+
          ", '"+Util.convierteFechaBDMySQL(getvigenciaal())+"'"+
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
          "idgimnasio = "+getidgimnasio()+""+
          ", nombre = '"+getnombre()+"'"+
          ", rfc = '"+getrfc()+"'"+
          ", calle = '"+getcalle()+"'"+
          ", colonia = '"+getcolonia()+"'"+
          ", numext = '"+getnumext()+"'"+
          ", numint = '"+getnumint()+"'"+
          ", telefono = '"+gettelefono()+"'"+
          ", correoe = '"+getcorreoe()+"'"+
          ", responsable = '"+getresponsable()+"'"+
          ", nextel = '"+getnextel()+"'"+
          ", vigenciadel = '"+Util.convierteFechaBDMySQL(getvigenciadel())+"'"+
          ", vigenciaal = '"+Util.convierteFechaBDMySQL(getvigenciaal())+"'"+
          ", observaciones = '"+getobservaciones()+"'"+
          ", estatusregistro = "+getestatusregistro()+""+
          ", usuariocreacion = "+getusuariocreacion()+""+
          ", fechacreacion = '"+Util.convierteFechaBDMySQL(getfechacreacion())+"'"+
          ", usuariomodificacion = "+getusuariomodificacion()+""+
          ", fechamodificacion = '"+Util.convierteFechaBDMySQL(getfechamodificacion())+"'"+
          "";

          return serializar;
     }
     public Integer getidgimnasio(){
                  return idgimnasio;
     }

     public void setidgimnasio(Integer idgimnasio){
          this.idgimnasio = idgimnasio;
     }

     public String getnombre(){
          if(nombre==null){
               return "";
          }else{
                  return nombre;
          }
     }

     public void setnombre(String nombre){
          this.nombre = nombre;
     }

     public String getrfc(){
          if(rfc==null){
               return "";
          }else{
                  return rfc;
          }
     }

     public void setrfc(String rfc){
          this.rfc = rfc;
     }

     public String getcalle(){
          if(calle==null){
               return "";
          }else{
                  return calle;
          }
     }

     public void setcalle(String calle){
          this.calle = calle;
     }

     public String getcolonia(){
          if(colonia==null){
               return "";
          }else{
                  return colonia;
          }
     }

     public void setcolonia(String colonia){
          this.colonia = colonia;
     }

     public String getnumext(){
          if(numext==null){
               return "";
          }else{
                  return numext;
          }
     }

     public void setnumext(String numext){
          this.numext = numext;
     }

     public String getnumint(){
          if(numint==null){
               return "";
          }else{
                  return numint;
          }
     }

     public void setnumint(String numint){
          this.numint = numint;
     }

     public String gettelefono(){
          if(telefono==null){
               return "";
          }else{
                  return telefono;
          }
     }

     public void settelefono(String telefono){
          this.telefono = telefono;
     }

     public String getcorreoe(){
          if(correoe==null){
               return "";
          }else{
                  return correoe;
          }
     }

     public void setcorreoe(String correoe){
          this.correoe = correoe;
     }

     public String getresponsable(){
          if(responsable==null){
               return "";
          }else{
                  return responsable;
          }
     }

     public void setresponsable(String responsable){
          this.responsable = responsable;
     }

     public String getnextel(){
          if(nextel==null){
               return "";
          }else{
                  return nextel;
          }
     }

     public void setnextel(String nextel){
          this.nextel = nextel;
     }

     public Date getvigenciadel(){
                  return vigenciadel;
     }

     public void setvigenciadel(Date vigenciadel){
          this.vigenciadel = vigenciadel;
     }

     public Date getvigenciaal(){
                  return vigenciaal;
     }

     public void setvigenciaal(Date vigenciaal){
          this.vigenciaal = vigenciaal;
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
          return "idgimnasio";
     }

}
