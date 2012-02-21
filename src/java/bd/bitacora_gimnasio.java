/*
* Clase generada automaticamente en una aplicación
* escrita por el Ing. Luis Manuel Navarro R. (Make It Bit)
*/

package bd;

import util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.File;


public class bitacora_gimnasio extends Tabla{
     private Integer idbitacora;
     private Integer idcliente;
     private Date fechor_entrada;
     private Date fechor_salida;
     private Integer numingreso;
     private Integer estatusregistro;

     public bitacora_gimnasio () {
          campos = new String[6];
          campos[0] = "idbitacora";
          campos[1] = "idcliente";
          campos[2] = "fechor_entrada";
          campos[3] = "fechor_salida";
          campos[4] = "numingreso";
          campos[5] = "estatusregistro";
     }


     public bitacora_gimnasio (Integer idbitacora, Integer idcliente, Date fechor_entrada, Date fechor_salida, Integer numingreso, Integer estatusregistro){

          this();
          this.idbitacora = idbitacora;
          this.idcliente = idcliente;
          this.fechor_entrada = fechor_entrada;
          this.fechor_salida = fechor_salida;
          this.numingreso = numingreso;
          this.estatusregistro = estatusregistro;
     }

     @Override
     public void setCampo(String p_nombrecampo, Object p_valorcampo){
          if(p_nombrecampo.equals("idbitacora")){
               setidbitacora((Integer)p_valorcampo);
          }else if(p_nombrecampo.equals("idcliente")){
               setidcliente((Integer)p_valorcampo);
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
          ", "+getidcliente()+""+
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
          ", idcliente = "+getidcliente()+""+
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

     public Integer getidcliente(){
                  return idcliente;
     }

     public void setidcliente(Integer idcliente){
          this.idcliente = idcliente;
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
