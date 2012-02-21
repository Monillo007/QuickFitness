/*
* Clase generada automaticamente en una aplicación
* escrita por el Ing. Luis Manuel Navarro R. (Make It Bit)
*/

package bd;

import util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.File;


public class cliente extends Tabla{
     private Integer idcliente;
     private Integer idgimnasio;
     private String nombre;
     private String apaterno;
     private String amaterno;
     private String correoe;
     private String telefono;
     private String persona_accidente;
     private Date fechanacimiento;
     private File foto;
     private Date vigenciadel;
     private Date vigenciaal;
     private Integer idmembresia;
     private String observaciones;
     private Integer estatusregistro;
     private Integer usuariocreacion;
     private Date fechacreacion;
     private Integer usuariomodificacion;
     private Date fechamodificacion;

     public cliente () {
          campos = new String[19];
          campos[0] = "idcliente";
          campos[1] = "idgimnasio";
          campos[2] = "nombre";
          campos[3] = "apaterno";
          campos[4] = "amaterno";
          campos[5] = "correoe";
          campos[6] = "telefono";
          campos[7] = "persona_accidente";
          campos[8] = "fechanacimiento";
          campos[9] = "foto";
          campos[10] = "vigenciadel";
          campos[11] = "vigenciaal";
          campos[12] = "idmembresia";
          campos[13] = "observaciones";
          campos[14] = "estatusregistro";
          campos[15] = "usuariocreacion";
          campos[16] = "fechacreacion";
          campos[17] = "usuariomodificacion";
          campos[18] = "fechamodificacion";
     }


     public cliente (Integer idcliente, Integer idgimnasio, String nombre, String apaterno, String amaterno, String correoe, String telefono, String persona_accidente, Date fechanacimiento, File foto, Date vigenciadel, Date vigenciaal, Integer idmembresia, String observaciones, Integer estatusregistro, Integer usuariocreacion, Date fechacreacion, Integer usuariomodificacion, Date fechamodificacion){

          this();
          this.idcliente = idcliente;
          this.idgimnasio = idgimnasio;
          this.nombre = nombre;
          this.apaterno = apaterno;
          this.amaterno = amaterno;
          this.correoe = correoe;
          this.telefono = telefono;
          this.persona_accidente = persona_accidente;
          this.fechanacimiento = fechanacimiento;
          this.foto = foto;
          this.vigenciadel = vigenciadel;
          this.vigenciaal = vigenciaal;
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
          if(p_nombrecampo.equals("idcliente")){
               setidcliente((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("idgimnasio")){
               setidgimnasio((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("nombre")){
               setnombre((String)p_valorcampo);
          }else if(p_nombrecampo.equals("apaterno")){
               setapaterno((String)p_valorcampo);
          }else if(p_nombrecampo.equals("amaterno")){
               setamaterno((String)p_valorcampo);
          }else if(p_nombrecampo.equals("correoe")){
               setcorreoe((String)p_valorcampo);
          }else if(p_nombrecampo.equals("telefono")){
               settelefono((String)p_valorcampo);
          }else if(p_nombrecampo.equals("persona_accidente")){
               setpersona_accidente((String)p_valorcampo);
          }else if(p_nombrecampo.equals("fechanacimiento")){
               setfechanacimiento((Date)p_valorcampo);
          }else if(p_nombrecampo.equals("foto")){
               
          }else if(p_nombrecampo.equals("vigenciadel")){
               setvigenciadel((Date)p_valorcampo);
          }else if(p_nombrecampo.equals("vigenciaal")){
               setvigenciaal((Date)p_valorcampo);
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
          ", "+getidgimnasio()+""+
          ", '"+getnombre()+"'"+
          ", '"+getapaterno()+"'"+
          ", '"+getamaterno()+"'"+
          ", '"+getcorreoe()+"'"+
          ", '"+gettelefono()+"'"+
          ", '"+getpersona_accidente()+"'"+
          ", '"+Util.convierteFechaBDMySQL(getfechanacimiento())+"'"+
          ", null"+
          ", '"+Util.convierteFechaBDMySQL(getvigenciadel())+"'"+
          ", '"+Util.convierteFechaBDMySQL(getvigenciaal())+"'"+
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
          "  idgimnasio = "+getidgimnasio()+""+
          ", nombre = '"+getnombre()+"'"+
          ", apaterno = '"+getapaterno()+"'"+
          ", amaterno = '"+getamaterno()+"'"+
          ", correoe = '"+getcorreoe()+"'"+
          ", telefono = '"+gettelefono()+"'"+
          ", persona_accidente = '"+getpersona_accidente()+"'"+
          ", fechanacimiento = '"+Util.convierteFechaBDMySQL(getfechanacimiento())+"'"+
          ", vigenciadel = '"+Util.convierteFechaBDMySQL(getvigenciadel())+"'"+
          ", vigenciaal = '"+Util.convierteFechaBDMySQL(getvigenciaal())+"'"+
          ", idmembresia = "+getidmembresia()+""+
          ", observaciones = '"+getobservaciones()+"'"+
          ", estatusregistro = "+getestatusregistro()+""+
          ", usuariomodificacion = "+getusuariomodificacion()+""+
          ", fechamodificacion = '"+Util.convierteFechaBDMySQL(getfechamodificacion())+"'"+
          "";

          return serializar;
     }
     public Integer getidcliente(){
                  return idcliente;
     }

     public void setidcliente(Integer idcliente){
          this.idcliente = idcliente;
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

     public String getapaterno(){
          if(apaterno==null){
               return "";
          }else{
                  return apaterno;
          }
     }

     public void setapaterno(String apaterno){
          this.apaterno = apaterno;
     }

     public String getamaterno(){
          if(amaterno==null){
               return "";
          }else{
                  return amaterno;
          }
     }

     public void setamaterno(String amaterno){
          this.amaterno = amaterno;
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

     public String getpersona_accidente(){
          if(persona_accidente==null){
               return "";
          }else{
                  return persona_accidente;
          }
     }

     public void setpersona_accidente(String persona_accidente){
          this.persona_accidente = persona_accidente;
     }

     public Date getfechanacimiento(){
                  return fechanacimiento;
     }

     public void setfechanacimiento(Date fechanacimiento){
          this.fechanacimiento = fechanacimiento;
     }

     public File getfoto(){
                  return foto;
     }

     public void setfoto(File foto){
          this.foto = foto;
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
          return "idcliente";
     }

}
