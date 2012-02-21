/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Ing. Luis Navarro 
 */
public abstract class Tabla {
    protected String[] campos;
    protected String[] tipos;
    protected int[] tamanos;
    protected boolean[] obligatorios;
    
    public  Tabla(){
        
    }

    public abstract void setCampo(String p_nombrecampo, Object p_valorcampo);
    
    public abstract String getSQLInsertar();
    
    public abstract String getSQLModificar();

    public abstract String getID();
    
    public String[] getCampos(){
        return campos;
    }
    
    public String[] getTipos(){
        return tipos;
    }
    
    public int[] getTamanos(){
        return tamanos;
    }
    
    public int getTamano(String p_campo){
        int posicion = 0;
	while (posicion < campos.length && !campos[posicion].equals(p_campo))
  	{
            posicion ++;
	}
        if (posicion >= campos.length)
            posicion = 0;
        return tamanos[posicion];
    }
    
    public void setTamano(int posicion, int tamano){
        if (tamanos == null){
            int numcampos = campos.length;
            tamanos = new int[numcampos];
        }
        tamanos[posicion] = tamano;
    }

    public void setObligatorios(int posicion, boolean obligatorio){
        if(obligatorios == null){
            int numcampos = campos.length;
            obligatorios = new boolean[numcampos];
        }
        obligatorios[posicion] = obligatorio;
    }

    public boolean isObligatorio(String campo){
        int posicion = 0;
        while (posicion < campos.length && !campos[posicion].equals(campo)){
          posicion ++;
        }
        if (posicion >= campos.length)posicion = 0;

        return obligatorios[posicion];
    }

    public void setTipos(int posicion, String tipo){
       // System.out.println("Posicion: "+posicion+" Tipo: "+tipo);
        if(tipos == null){
            tipos = new String[campos.length];
        }
        tipos[posicion] = tipo;
    }

    public String getTipo(String campo){
        int posicion = 0;
        while (posicion < campos.length && !campos[posicion].equals(campo))
        {
                posicion ++;
        }
        if (posicion >= campos.length)
            posicion = 0;
        String tipo = "";

        if(tipos[posicion].equals("VARCHAR2") || tipos[posicion].equals("CHAR") || tipos[posicion].equals("NVARCHAR2") || tipos[posicion].equals("VARCHAR")){
            tipo = new String("String");
        }else if(tipos[posicion].equals("NUMBER") || tipos[posicion].equals("DECIMAL")){
            tipo = "BigDecimal";
        }else if(tipos[posicion].equals("INTEGER") || tipos[posicion].equals("INT")){
            tipo = "Integer";
        }else if(tipos[posicion].equals("DATE") || tipos[posicion].equals("DATETIME")){
            tipo = "Date";
        }else if(tipos[posicion].equals("LONG RAW")){
            tipo = "File";
        }
        System.out.println("Campo: "+campo);
        System.out.println("tipo: "+tipos[posicion]);
        return tipo;
    }
    
}
