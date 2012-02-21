/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;
import Interfacesbd.InterfaceBDPool;
import Interfacesbd.InterfaceRegistrosBDPool;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.sql.*;
import java.text.DateFormat;
/**
 *
 * @author Gerardo
 */
public class DateServer {
    public DateServer() {
     
    }
    
    public String Fecha() {
        String fechaServer="";
        
        Calendar c = Calendar.getInstance();
        int mes = c.get(GregorianCalendar.MONTH) + 1;
        int dia = c.get(GregorianCalendar.DATE);
        String mm = Integer.toString(mes);
        String dd = Integer.toString(dia);
       
        //fechaServer = c.get(GregorianCalendar.YEAR) +"-"+ (mes < 10 ? "0" + mm : mm) +"-"+ (dia < 10 ? "0" + dd : dd);
        fechaServer =  (dia < 10 ? "0" + dd : dd) +"-"+ (mes < 10 ? "0" + mm : mm) +"-"+ c.get(GregorianCalendar.YEAR);
        return fechaServer;    
    }
    
     public String Hora(){
        String horaServer="";

        Calendar c = Calendar.getInstance();
        int hora = c.get(GregorianCalendar.HOUR_OF_DAY);
        int minuto = c.get(GregorianCalendar.MINUTE);
        int segundo = c.get(GregorianCalendar.SECOND);
        String hour = Integer.toString(hora);
        String minute = Integer.toString(minuto);
        String second = Integer.toString(segundo);                   
        
        horaServer = (hora < 10 ? "0" + hour : hour) +":"+ (minuto < 10 ? "0" + minute : minute) +":"+ (segundo < 10 ? "0" + second : second);//cal.get(cal.HOUR_OF_DAY)+":"+cal.get(cal.MINUTE)+":"+cal.get(cal.SECOND);
        //System.out.println("Hora Server: "+horaServer);
        return horaServer;    
    }

     public String FechaHoy() {
        Date fecha =new Date();
        SimpleDateFormat formatoFecha=new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(formatoFecha.format(fecha));
        return formatoFecha.format(fecha);

    }

     /***
      * Método para obtener la edad en base a la fecha de nacimiento.
      * @param fechanacimiento La fecha de nacimiento en formato dd/MM/yyyy
      * @return Una cadena de texto con la edad, en caso de error devuelve un 0(cero).
      */
public static String Edad(String fechanacimiento){
            String edad = "";
            String [] fechanac = fechanacimiento.split("/");
            try {
                int dia=Integer.parseInt(fechanac[0]);
                int mes=Integer.parseInt(fechanac[1]);
                int anio=Integer.parseInt(fechanac[2]);
                Date datosFecha =  new Date();


                SimpleDateFormat SdfAnio = new SimpleDateFormat("yyyy");
                SimpleDateFormat SdfMes = new SimpleDateFormat("MM");
                SimpleDateFormat SdfDia = new SimpleDateFormat("dd");

                int reganio = Integer.parseInt(SdfAnio.format(datosFecha))-anio;
                int regmes = Integer.parseInt(SdfMes.format(datosFecha));
                int regdia = Integer.parseInt(SdfDia.format(datosFecha));
                edad=reganio+"";
                if(regmes==mes){
                    if(regdia<dia){
                        reganio-=1;
                        edad=reganio+"";
                    }
                }
                if(regmes<mes){
                    reganio-=1;
                    edad=reganio+"";
                }
            } catch (Exception e) {
                e.printStackTrace();
                edad = "0";
            }

            return(edad);
    }

//Se agrega para el sistema de Conciliadoras
    /* METODO PARA CONVERTIR LA HORA hh:mm EN TEXTO
     *
     *@param hora STRING EN FORMATO hh:ss
     *@return STRING DEVUELVE EL TEXTO DE LA HORA SI EN CUENTRA ERROR MANDA EL MENSAJE "ERROR"
     *
     */
public static String getHoraLetra(String hora){
        System.out.println("Hora recibida: "+hora);
        String[] array=hora.split(":");
        String hora1="";
        int horaInt = 0;
        String hora2="";
        String horas = array[0];
        String minutos = array[1].split(" ")[0];
        String tiempo = "";
        if(array[1].split(" ").length >1 ){
            tiempo = array[1].split(" ")[1];
        }
        System.out.println("Horas: "+horas+" minutos: "+minutos+" tiempo:"+tiempo);
        String horaletra="";

        horaInt = Util.getInt(array[0]);

        if(!tiempo.equals("") && tiempo.equalsIgnoreCase("PM") && horaInt != 12){
            horaInt = (Util.getInt(array[0])+12);
        }else if(!tiempo.equals("") && tiempo.equalsIgnoreCase("AM") && horaInt == 12){
            horaInt = (Util.getInt(array[0])-12);
        }else{
            horaInt = Util.getInt(array[0]);
        }
        System.out.println("HORA INT: "+horaInt);
        ///HORAS
      if(Util.getInt(horas)>=0 && Util.getInt(horas)<=23 && Util.getInt(minutos)>=0 &&Util.getInt(minutos)<=59)  {


        switch(horaInt){//checa la hora
            case 1:hora1="1 (UNA) HORAS ";break;
            case 2:hora1="2 (DOS) HORAS ";break;
            case 3:hora1="3 (TRES) HORAS ";break;
            case 4:hora1="4 (CUATRO) HORAS ";break;
            case 5:hora1="5 (CINCO) HORAS ";break;
            case 6:hora1="6 (SEIS) HORAS ";break;
            case 7:hora1="7 (SIETE) HORAS ";break;
            case 8:hora1="8 (OCHO) HORAS ";break;
            case 9:hora1="9 (NUEVE) HORAS ";break;
            case 10:hora1="10 (DIEZ) HORAS ";break;
            case 11:hora1="11 (ONCE) HORAS ";break;
            case 12:hora1="12 (DOCE) HORAS ";break;
            case 13:hora1="13 (TRECE) HORAS ";break;
            case 14:hora1="14 (CATORCE) HORAS ";break;
            case 15:hora1="15 (QUINCE) HORAS ";break;
            case 16:hora1="16 (DIECISEIS) HORAS ";break;
            case 17:hora1="17 (DIECISIETE) HORAS ";break;
            case 18:hora1="18 (DIECIOCHO) HORAS ";break;
            case 19:hora1="19 (DIECINUEVE) HORAS ";break;
            case 20:hora1="20 (VEINTE) HORAS ";break;
            case 21:hora1="21 (VEINTIUN) HORAS ";break;
            case 22:hora1="22 (VEINTIDOS) HORAS ";break;
            case 23:hora1="23 (VEINTITRES) HORAS ";break;
            case 0:hora1="0 (CERO) HORAS ";break;
            default:hora1="";break;
        }
        ///MINUTOS
            switch(Util.getInt(minutos)){//checa los minutos
            case 0:hora2="";break;
            case 1:hora2="CON 1 (UN) MINUTOS";break;
            case 2:hora2="CON 2 (DOS) MINUTOS";break;
            case 3:hora2="CON 3 (TRES) MINUTOS";break;
            case 4:hora2="CON 4 (CUATRO) MINUTOS";break;
            case 5:hora2="CON 5 (CINCO) MINUTOS";break;
            case 6:hora2="CON 6 (SEIS) MINUTOS";break;
            case 7:hora2="CON 7 (SIETE) MINUTOS";break;
            case 8:hora2="CON 8 (OCHO) MINUTOS";break;
            case 9:hora2="CON 9 (NUEVE) MINUTOS";break;
            case 10:hora2="CON 10 (DIEZ) MINUTOS";break;
            case 11:hora2="CON 11 (ONCE) MINUTOS";break;
            case 12:hora2="CON 12 (DOCE) MINUTOS";break;
            case 13:hora2="CON 13 (TRECE) MINUTOS";break;
            case 14:hora2="CON 14 (CATORCE) MINUTOS";break;
            case 15:hora2="CON 15 (QUINCE) MINUTOS";break;
            case 16:hora2="CON 16 (DIECISEIS) MINUTOS";break;
            case 17:hora2="CON 17 (DIECISIETE) MINUTOS";break;
            case 18:hora2="CON 18 (DIECIOCHO) MINUTOS";break;
            case 19:hora2="CON 19 (DIECINUEVE) MINUTOS";break;
            case 20:hora2="CON 20 (VEINTE) MINUTOS";break;
            case 21:hora2="CON 21 (VEINTIUN) MINUTOS";break;
            case 22:hora2="CON 22 (VEINTIDOS) MINUTOS";break;
            case 23:hora2="CON 23 (VEINTITRES) MINUTOS";break;
            case 24:hora2="CON 24 (VEINTICUATRO) MINUTOS";break;
            case 25:hora2="CON 25 (VEINTICINCO) MINUTOS";break;
            case 26:hora2="CON 26 (VEINTISEIS) MINUTOS";break;
            case 27:hora2="CON 27 (VEINTISIETE) MINUTOS";break;
            case 28:hora2="CON 28 (VEINTIOCHO) MINUTOS";break;
            case 29:hora2="CON 29 (VEINTINUEVE) MINUTOS";break;
            case 30:hora2="CON 30 (TREINTA) MINUTOS";break;
            case 31:hora2="CON 31 (TREINTA Y UN) MINUTOS";break;
            case 32:hora2="CON 32 (TREINTA Y DOS) MINUTOS";break;
            case 33:hora2="CON 33 (TREINTA Y TRES) MINUTOS";break;
            case 34:hora2="CON 34 (TREINTA Y CUATRO) MINUTOS";break;
            case 35:hora2="CON 35 (TREINTA Y CINCO) MINUTOS";break;
            case 36:hora2="CON 36 (TREINTA Y SEIS) MINUTOS";break;
            case 37:hora2="CON 37 (TREINTA Y SIETE) MINUTOS";break;
            case 38:hora2="CON 38 (TREINTA Y OCHO) MINUTOS";break;
            case 39:hora2="CON 39 (TREINTA Y NUEVE) MINUTOS";break;
            case 40:hora2="CON 40 (CUARENTA) MINUTOS";break;
            case 41:hora2="CON 41 (CUARENTA Y UN) MINUTOS";break;
            case 42:hora2="CON 42 (CUARENTA Y DOS) MINUTOS";break;
            case 43:hora2="CON 43 (CUARENTA Y TRES )MINUTOS";break;
            case 44:hora2="CON 44 (CUARENTA Y CUATRO) MINUTOS";break;
            case 45:hora2="CON 45 (CUARENTA Y CINCO) MINUTOS";break;
            case 46:hora2="CON 46 (CUARENTA Y SEIS) MINUTOS";break;
            case 47:hora2="CON 47 (CUARENTA Y SIETE) MINUTOS";break;
            case 48:hora2="CON 48 (CUARENTA Y OCHO) MINUTOS";break;
            case 49:hora2="CON 49 (CUARENTA Y NUEVE) MINUTOS";break;
            case 50:hora2="CON 50 (CINCUENTA) MINUTOS";break;
            case 51:hora2="CON 51 (CINCUENTA Y UN) MINUTOS";break;
            case 52:hora2="CON 52 (CINCUENTA Y DOS) MINUTOS";break;
            case 53:hora2="CON 53 (CINCUENTA Y TRES) MINUTOS";break;
            case 54:hora2="CON 54 (CINCUENTA Y CUATRO) MINUTOS";break;
            case 55:hora2="CON 55 (CINCUENTA Y CINCO) MINUTOS";break;
            case 56:hora2="CON 56 (CINCUENTA Y SEIS) MINUTOS";break;
            case 57:hora2="CON 57 (CINCUENTA Y SIETE) MINUTOS";break;
            case 58:hora2="CON 58 (CINCUENTA Y OCHO) MINUTOS";break;
            case 59:hora2="CON 59 (CINCUENTA Y NUEVE) MINUTOS";break;
            default:hora2="";break;
        }

        horaletra=hora1+hora2;
        }else{
           horaletra="ERROR";
        }        
        return horaletra;
    }

    /**
     * Compara 2 fechas tomando en cuenta únicamente los días (no las horas)
     * @return 0 si las fechas son iguales, -1 si la fechaUno es menor a la fechaDos, 
     * 1 si la fechaDos es menor a la fechaUno
     */
    public static int comparaFechas(Date fechaUno, Date fechaDos){
        int res = 0;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fechaUno);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(fechaDos);

        if(cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)){
            res = 0;
        }else{
            res = cal1.get(Calendar.DAY_OF_YEAR) - cal2.get(Calendar.DAY_OF_YEAR);
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(DateServer.getHoraLetra(DateFormat.getTimeInstance(DateFormat.SHORT, new java.util.Locale("es", "MX")).format(new Date())));
    }

}
