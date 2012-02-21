/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import Interfacesbd.InterfaceBDPool;
import Interfacesbd.InterfaceRegistrosBDPool;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author Administrador
 */
public class UtilArchivos {
    
    public UtilArchivos() {
    }

    public void procesarArchivo(){
        File archivo = new File("web/Temp/texto.txt");
        File archivo2 = new File("/usr/local/tomcat/webapps/ROOT/Archivo/SIIA/Temp/archivo2.txt");
        if(!archivo2.exists()){
            try {
                archivo.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println("El archivo no existe.");
        }
        //System.out.println(archivo.getAbsolutePath());
        //System.out.println(archivoACadena(archivo));
        cadenaAArchivo(archivoACadena(archivo), archivo2);
    }

    public String getCadena(){
        return "";
    }

    public static String archivoACadena(File archivo){
        return archivoACadena(archivo, false);
    }

    public static String archivoACadena(File archivo, boolean saltoLinea){
        StringBuffer buffer = new StringBuffer();
        String linea = "";
        FileReader fr;
        BufferedReader br;

        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            while((linea = br.readLine())!=null){
                buffer.append(linea);
                if(saltoLinea)buffer.append("\n");
            }
            fr.close();
            br.close();
        }catch(FileNotFoundException ef){
            System.out.println("No se encontró el archivo "+archivo.getName());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void cadenaAArchivo(String cadena, File archivo){

        if(!archivo.exists()){
            try {
                archivo.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bw;
            FileWriter fw ;
            fw = new FileWriter(archivo);
            bw = new BufferedWriter(fw);
            bw.write(cadena);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
