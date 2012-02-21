/*
* Clase generada automaticamente en una aplicación
* escrita por el Ing. Luis Manuel Navarro R. (Make It Bit)
*/

package bd;

import util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.File;


public class configuracion_sistema extends Tabla{
     private Integer idconfiguracion;
     private String persona_contacto;
     private String telefono_contacto;
     private String correoe_credenciales;
     private String correoe_contacto;
     private String domicilio_contacto;
     private String nombre_sistema;
     private String descripcion_sistema;
     private String descripcion_corta;
     private String aviso_legal;

     public configuracion_sistema () {
          campos = new String[10];
          campos[0] = "idconfiguracion";
          campos[1] = "persona_contacto";
          campos[2] = "telefono_contacto";
          campos[3] = "correoe_credenciales";
          campos[4] = "correoe_contacto";
          campos[5] = "domicilio_contacto";
          campos[6] = "nombre_sistema";
          campos[7] = "descripcion_sistema";
          campos[8] = "descripcion_corta";
          campos[9] = "aviso_legal";
     }


     public configuracion_sistema (Integer idconfiguracion, String persona_contacto, String telefono_contacto, String correoe_credenciales, String correoe_contacto, String domicilio_contacto){

          this();
          this.idconfiguracion = idconfiguracion;
          this.persona_contacto = persona_contacto;
          this.telefono_contacto = telefono_contacto;
          this.correoe_credenciales = correoe_credenciales;
          this.correoe_contacto = correoe_contacto;
          this.domicilio_contacto = domicilio_contacto;
     }

     @Override
     public void setCampo(String p_nombrecampo, Object p_valorcampo){
          if(p_nombrecampo.equals("idconfiguracion")){
               setidconfiguracion((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("persona_contacto")){
               setpersona_contacto((String)p_valorcampo);
          }else if(p_nombrecampo.equals("telefono_contacto")){
               settelefono_contacto((String)p_valorcampo);
          }else if(p_nombrecampo.equals("correoe_credenciales")){
               setcorreoe_credenciales((String)p_valorcampo);
          }else if(p_nombrecampo.equals("correoe_contacto")){
               setcorreoe_contacto((String)p_valorcampo);
          }else if(p_nombrecampo.equals("domicilio_contacto")){
               setdomicilio_contacto((String)p_valorcampo);          
          }else if(p_nombrecampo.equals("nombre_sistema")){
               setnombre_sistema((String)p_valorcampo);
          }else if(p_nombrecampo.equals("descripcion_sistema")){
               setdescripcion_sistema((String)p_valorcampo);
          }else if(p_nombrecampo.equals("descripcion_corta")){
               setdescripcion_corta((String)p_valorcampo);
          }else if(p_nombrecampo.equals("aviso_legal")){
               setaviso_legal((String)p_valorcampo);
          }
     }

     @Override
     public String getSQLInsertar(){
          String serializar;
          serializar = " VALUES("+
          ""+getidconfiguracion()+""+
          ", '"+getpersona_contacto()+"'"+
          ", '"+gettelefono_contacto()+"'"+
          ", '"+getcorreoe_credenciales()+"'"+
          ", '"+getcorreoe_contacto()+"'"+
          ", '"+getdomicilio_contacto()+"'"+
          ", '"+getnombre_sistema()+"'"+
          ", '"+getdescripcion_sistema()+"'"+
          ", '"+getdescripcion_corta()+"'"+
          ", '"+getaviso_legal()+"'"+
          ")";

          return serializar;
     }

     @Override
     public String getSQLModificar(){
          String serializar;
          serializar = " SET "+
          "idconfiguracion = "+getidconfiguracion()+""+
          ", persona_contacto = '"+getpersona_contacto()+"'"+
          ", telefono_contacto = '"+gettelefono_contacto()+"'"+
          ", correoe_credenciales = '"+getcorreoe_credenciales()+"'"+
          ", correoe_contacto = '"+getcorreoe_contacto()+"'"+
          ", domicilio_contacto = '"+getdomicilio_contacto()+"'"+
          ", nombre_sistema = '"+getnombre_sistema()+"'"+
          ", descripcion_sistema = '"+getdescripcion_sistema()+"'"+
          ", descripcion_corta = '"+getdescripcion_corta()+"'"+
          ", aviso_legal = '"+getaviso_legal()+"'"+
          "";

          return serializar;
     }
     public Integer getidconfiguracion(){
                  return idconfiguracion;
     }

     public void setidconfiguracion(Integer idconfiguracion){
          this.idconfiguracion = idconfiguracion;
     }

     public String getpersona_contacto(){
          if(persona_contacto==null){
               return "";
          }else{
                  return persona_contacto;
          }
     }

     public void setpersona_contacto(String persona_contacto){
          this.persona_contacto = persona_contacto;
     }

     public String gettelefono_contacto(){
          if(telefono_contacto==null){
               return "";
          }else{
                  return telefono_contacto;
          }
     }

     public void settelefono_contacto(String telefono_contacto){
          this.telefono_contacto = telefono_contacto;
     }

     public String getcorreoe_credenciales(){
          if(correoe_credenciales==null){
               return "";
          }else{
                  return correoe_credenciales;
          }
     }

     public void setcorreoe_credenciales(String correoe_credenciales){
          this.correoe_credenciales = correoe_credenciales;
     }

     public String getcorreoe_contacto(){
          if(correoe_contacto==null){
               return "";
          }else{
                  return correoe_contacto;
          }
     }

     public void setcorreoe_contacto(String correoe_contacto){
          this.correoe_contacto = correoe_contacto;
     }

     public String getdomicilio_contacto(){
          if(domicilio_contacto==null){
               return "";
          }else{
                  return domicilio_contacto;
          }
     }

     public void setdomicilio_contacto(String domicilio_contacto){
          this.domicilio_contacto = domicilio_contacto;
     }

     public String getID() {
          return "idconfiguracion";
     }

    /**
     * @return the nombre_sistema
     */
    public String getnombre_sistema() {
        if(nombre_sistema == null){
            return "";
        }else{
            return nombre_sistema;
        }
    }

    /**
     * @param nombre_sistema the nombre_sistema to set
     */
    public void setnombre_sistema(String nombre_sistema) {
        this.nombre_sistema = nombre_sistema;
    }

    /**
     * @return the descripcion_sistema
     */
    public String getdescripcion_sistema() {
        if(descripcion_sistema == null){
            return "";
        }else{
            return descripcion_sistema;
        }
    }

    /**
     * @param descripcion_sistema the descripcion_sistema to set
     */
    public void setdescripcion_sistema(String descripcion_sistema) {
        this.descripcion_sistema = descripcion_sistema;
    }

    /**
     * @return the descripcion_corta
     */
    public String getdescripcion_corta() {
        if(descripcion_corta == null){
            return "";
        }else{
            return descripcion_corta;
        }
    }

    /**
     * @param descripcion_corta the descripcion_corta to set
     */
    public void setdescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }

    /**
     * @return the aviso_legal
     */
    public String getaviso_legal() {
        if(aviso_legal == null){
            return "";
        }else{
            return aviso_legal;
        }
    }

    /**
     * @param aviso_legal the aviso_legal to set
     */
    public void setaviso_legal(String aviso_legal) {
        this.aviso_legal = aviso_legal;
    }

}
