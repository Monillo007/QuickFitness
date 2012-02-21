/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author lnavarrora
 */
public class Mailer {

    private static boolean esEnvioMultipart(Mailer aThis) {
        boolean b = false;
        List<?> listaArchivos = (List<?>) aThis.map.get("archivos");
        List<?> listaReportes = (List<?>) aThis.map.get("archivos");
        if ((listaArchivos != null && listaArchivos.size() > 0)
                || listaReportes != null && listaReportes.size() > 0) {
            b = true;
        }
        return b;
    }

    private static class MailAttach {

        String rutaArchivo;
        String nombreEnCorreoCorreo;

        MailAttach(String ruta, String nombreEnCorreo) {
            this.rutaArchivo = ruta;
            this.nombreEnCorreoCorreo = nombreEnCorreo;
        }
    }

    private static class ReporteBD {

        int idReporte;
        String formato;
        String nombreEnCorreo;
        Map<String, Object> params;

        ReporteBD(int idReporte, String formato) {
            this.idReporte = idReporte;
            this.formato = formato;
        }

        ReporteBD(int idReporte, String formato, String nombre) {
            this.idReporte = idReporte;
            this.formato = formato;
            this.nombreEnCorreo = nombre;
        }

        ReporteBD(int idReporte, String formato, String nombre, Map<String, Object> params) {
            this.idReporte = idReporte;
            this.formato = formato;
            this.nombreEnCorreo = nombre;
            this.params = params;
        }
    }
    public static final String CORREO_SALIDA = "pgj.helpdesk@gmail.com";
    private static String PWD = "helpdesk.pgj";
    Map<String, Object> map = new HashMap<String, Object>();

    private Mailer() {
    }

    public static Mailer newMail() {
        return new Mailer().origen(CORREO_SALIDA).pwd(PWD);
    }

    public Mailer destino(String correoDestino) {
        List<String> destinos = (List<String>) map.get("destinos");
        if (destinos == null) {
            destinos = new ArrayList<String>();
            map.put("destinos", destinos);
        }
        destinos.add(correoDestino);
        return this;
    }

    public Mailer origen(String correoSalida) {
        map.put("origen", correoSalida);
        return this;
    }

    public Mailer pwd(String pwd) {
        map.put("pwd", pwd);
        return this;
    }

    public Mailer asunto(String asunto) {
        map.put("asunto", asunto);
        return this;
    }

//    public Mailer conAdjuntos(boolean tieneAdjuntos) {
//        map.put("conAdjuntos", tieneAdjuntos);
//        return this;
//    }

    public Mailer mensaje(String mensaje) {
        map.put("mensaje", mensaje);
        return this;
    }

    public Mailer reporteBD(int idReporte, String nombreEnCorreo, String formato) {
        return this.reporteBD(idReporte, nombreEnCorreo, formato, null);
    }

    public Mailer reporteBD(int idReporte) {
        return this.reporteBD(idReporte, null);
    }

    public Mailer reporteBD(int idReporte, String nombreEnCorreo) {
        return this.reporteBD(idReporte, nombreEnCorreo, "pdf");
    }
    
    public Mailer reporteBD(Map<String,Object> params) {
        List<ReporteBD> reporteBD = (List<ReporteBD>) map.get("reportes");
        if (reporteBD == null) {
            reporteBD = new ArrayList<ReporteBD>();
            map.put("reportes", reporteBD);
        }
        ReporteBD r = new ReporteBD(Integer.parseInt(String.valueOf(params.get("idReporte"))),String.valueOf(params.get("formato")),(String)params.get("nombreEnCorreo"),params);
        reporteBD.add(r);
        return this;
    }

    public Mailer reporteBD(int idReporte, String nombreEnCorreo, String formato, Map<String, Object> params) {
        List<ReporteBD> reporteBD = (List<ReporteBD>) map.get("reportes");
        if (reporteBD == null) {
            reporteBD = new ArrayList<ReporteBD>();
            map.put("reportes", reporteBD);
        }
        reporteBD.add(new ReporteBD(idReporte, formato, nombreEnCorreo, params));
        return this;
    }

    public Mailer archivo(String archivo, String nombreEnCorreo) {
        List<MailAttach> archivos = (List<MailAttach>) map.get("archivos");
        if (archivos == null) {
            archivos = new ArrayList<MailAttach>();
            map.put("archivos", archivos);
        }
        archivos.add(new MailAttach(archivo, nombreEnCorreo));
        return this;
    }

    public Mailer archivo(File archivo) {
        return this.archivo(archivo.getAbsolutePath(), archivo.getName());
    }

    public Mailer enviar() throws AddressException, MessagingException {
        Properties props = new Properties();

        // Nombre del host de correo, es smtp.gmail.com
        props.setProperty("mail.smtp.host", "smtp.gmail.com");

        // TLS si está disponible
        props.setProperty("mail.smtp.starttls.enable", "true");

        // Puerto de gmail para envio de correos
        props.setProperty("mail.smtp.port", "465");

        // SSL
        props.setProperty("mail.smtp.ssl.enable", "true");

        // Nombre del usuario
        // Quien envia el correo
        props.setProperty("mail.smtp.user", (String) map.get("origen"));

        // Si requiere o no usuario y password para conectarse.
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        // A quien va dirigido
        List<String> destinos = (List<String>) map.get("destinos");
        if (destinos != null) {
            for (String destino : destinos) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            }
        }

        //Agregamos todas las partes al correo (texto, archivos, reportes)
        Multipart multipart = new MimeMultipart();
        String texto = (String) map.get("mensaje");
        MimeBodyPart messageBodyPart = null;
        //1. Texto
        if (texto != null) {
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(texto, "text/html");
            multipart.addBodyPart(messageBodyPart);
        }

        //2. Archivos
        List<MailAttach> archivos = (List<MailAttach>) map.get("archivos");
        if (archivos != null) {
            for (MailAttach archivo : archivos) {
                DataSource source = new FileDataSource(archivo.rutaArchivo);
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(archivo.nombreEnCorreoCorreo);
                multipart.addBodyPart(messageBodyPart);
            }
        }
        //3. Reportes
        List<ReporteBD> reportes = (List<ReporteBD>) map.get("reportes");
        if (reportes != null) {
            for (ReporteBD reporte : reportes) {
                //Generamos el reporte
                File reporteGenerado = Reporter.newReport().formato(reporte.formato).idReporte(reporte.idReporte).params(reporte.params).crear();

                DataSource source = new FileDataSource(reporteGenerado);
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setDataHandler(new DataHandler(source));
                if(reporte.nombreEnCorreo==null || reporte.nombreEnCorreo.trim().length()==0){
                    messageBodyPart.setFileName(reporteGenerado.getName());
                }else{
                    messageBodyPart.setFileName(reporte.nombreEnCorreo);
                }
                multipart.addBodyPart(messageBodyPart);
            }
        }

        //if (esEnvioMultipart(this)) {
            message.setContent(multipart);
        //} else {
        //    message.setContent(texto != null ? texto : "", "text/html");
        //}

        message.setSubject((String) map.get("asunto"));

        Transport t = session.getTransport("smtp");
        t.connect((String) map.get("origen"), (String) map.get("pwd"));
        t.sendMessage(message, message.getAllRecipients());
        t.close();
        return this;
    }

    public static void main(String[] args) throws AddressException, MessagingException, IOException {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("idReporte","28");
        params.put("formato", "pdf");
        params.put("ASIGNACION",14);
        
        newMail().asunto("Reporte de Prueba").mensaje("Prueba 1").destino("pgj.helpdesk@gmail.com").reporteBD(28, "Asignacion Finalizada.pdf", "pdf", params).enviar();
//        newMail().destino("pgj.helpdesk@gmail.com").mensaje("Saludos").archivo(File.createTempFile("hola_", ".txt")).enviar();
//        newMail().destino("pgj.helpdesk@gmail.com").asunto("Asunto de prueba").mensaje("Saludos").enviar();
    }
}
