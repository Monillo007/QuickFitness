/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Administrador
 */
public class ServletGenerico extends HttpServlet {

    public static final int RETENER_CAMPOS_OBJETO = 1;
    public static final int USAR_CAMPOS_REQUEST = 2;
    public static final String RUTA_UPLOADS = "temp_uploads";
    public static final String[] IMAGEN_MIMETYPES = {"image/bmp", "image/png", "image/x-png", "image/png", "image/gif", "image/jpeg", "image/jpg", "image/pjpeg", "image/jpeg"};
    public static final String[] VIDEO_MIMETYPES = {"video/mpeg", "video/quicktime", "video/quicktime", "video/mp4", "video/x-flv", "video/x-ms-wmv"};
    public static final String[] AUDIO_MIMETYPES = {"audio/basic", "audio/mid", "audio/mpeg", "audio/x-wav"};
    public static final String[] DOCUMENTO_MIMETYPES = {"application/pdf", "application/msword", "application/vnd.ms-excel", "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",};
    public static final String[] TEXTO_MIMETYPES = {"text/plain", "text/html", "text/rtf", "text/xml"};

    public static boolean esImagen(String mimeType) {
        boolean result = false;
        if (mimeType != null) {
            String type = mimeType.toLowerCase();
            for (String mime : IMAGEN_MIMETYPES) {
                if (mime.toLowerCase().equals(type)) {
                    return true;
                }
            }
        }

        return result;
    }

    public static boolean esAudio(String mimeType) {
        boolean result = false;
        if (mimeType != null) {
            String type = mimeType.toLowerCase();
            for (String mime : AUDIO_MIMETYPES) {
                if (mime.toLowerCase().equals(type)) {
                    return true;
                }
            }
        }
        return result;
    }

    public static boolean esVideo(String mimeType) {
        boolean result = false;
        if (mimeType != null) {
            String type = mimeType.toLowerCase();
            for (String mime : VIDEO_MIMETYPES) {
                if (mime.toLowerCase().equals(type)) {
                    return true;
                }
            }
        }
        return result;
    }

    public static boolean esDocumento(String mimeType) {
        boolean result = false;
        if (mimeType != null) {
            String type = mimeType.toLowerCase();
            for (String mime : DOCUMENTO_MIMETYPES) {
                if (mime.toLowerCase().equals(type)) {
                    return true;
                }
            }
        }
        return result;
    }

    public static boolean esDocumentoTexto(String mimeType) {
        boolean result = false;
        if (mimeType != null) {
            String type = mimeType.toLowerCase();
            for (String mime : TEXTO_MIMETYPES) {
                if (mime.toLowerCase().equals(type)) {
                    return true;
                }
            }
        }
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet Generico";
    }
    // </editor-fold>

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    public Map<String, String> crearMapaParametros(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        String lineSeparator = System.getProperty("file.separator");

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            System.out.println("Multipart Request - " + request.getRequestURI());
            DiskFileItemFactory factory = new DiskFileItemFactory(1024000, new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = null;

            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, e);
            }
//            Iterator itr = items.iterator();
//            while (itr.hasNext()) {
            for (FileItem item : items) {
//                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString();
                    if (!name.trim().equals("") && value != null && !value.trim().equals("")) {
                        map.put(name, value.trim().toUpperCase());
                    }

                } else {
                    try {
                        String itemName = item.getName();
                        //System.out.println("itemName = " + itemName);
                        if (itemName != null && !itemName.trim().equals("")) {
                            Random r = new Random(System.currentTimeMillis());
                            String nombreArchivo = getServletContext().getRealPath("/")
                                    + RUTA_UPLOADS + lineSeparator + r.nextInt() + "_" + r.nextInt();
                            int dotPos = itemName.lastIndexOf(".");
                            if(dotPos!=-1){
                                String extension = itemName.substring(dotPos+1);
                                nombreArchivo=nombreArchivo.concat(".").concat(extension);
                            }
                            File savedFile = new File(nombreArchivo);
                            item.write(savedFile);
//                        item.write(String)
                            map.put(item.getFieldName(), nombreArchivo);
                            map.put(item.getFieldName() + "_CONTENTTYPE", item.getContentType().toUpperCase());
                            map.put(item.getFieldName() + "_SIZE", "" + item.getSize());
                        }
                    } catch (Exception e) {
                        Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        } else {
            Enumeration<String> params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String param = params.nextElement();
                if (!request.getParameter(param).trim().equals("")) {
                    map.put(param, request.getParameter(param).trim().toUpperCase());
                }
            }
        }
        return map;
    }

    public static String parametrosNecesarios(Map<String, String> map, String... params) {
        //Map.Entry<String,String> entradas = (Entry<String, String>) map.entrySet();
        for (int i = 0; i
                < params.length; i++) {
            String p = params[i];
            if (map.containsKey(p) == false) {
                return p;
            }
        }
        return null;
    }

    public static void redireccionar(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(path);
        } catch (Exception e) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void incluir(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(path).include(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void forward(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tabla poblarObjeto(Tabla tabla, HttpServletRequest request, int tipoOperacion) {
        int cont = 0;
        Map<String, String> params = crearMapaParametros(request);
        switch (tipoOperacion) {
            case RETENER_CAMPOS_OBJETO:
                String[] iterar = tabla.getCampos();
                for (int i = 0; i
                        < iterar.length; i++) {
                    String campo = iterar[i];
                    try {
                        Object obj = Tabla.class.getMethod("get" + campo, new Class[0]).invoke(tabla, new Object[0]);

                        if (obj == null || obj.toString().trim().equals("") || obj.toString().toLowerCase().equals("null") || obj.equals(new Date(-2208965004000L))) {
                            establecerCampo(tabla, campo, params.get(campo));
                        }
                    } catch (Exception ex) {
                    }
                }
                break;

            case USAR_CAMPOS_REQUEST:
                ArrayList<String> array = new ArrayList<String>(params.keySet());
                String[] campos = tabla.getCampos();
                for (int i = 0; i
                        < campos.length; i++) {
                    String campo = campos[i];
                    try {
                        //if (array.contains(campo)) {
                        establecerCampo(tabla, campo, params.get(campo));
                        //}
                    } catch (Exception ex) {
                    }
                }
            default:
                break;
        }
        return tabla;
    }

    public static void establecerCampo(Tabla tabla, String campo, String valor) {
//        System.out.println("EstablecerCampo : Campo:"+ campo+", Valor = " + valor);
        try {
            String tipoDato = tabla.getClass().getMethod("get" + campo, new Class[0]).getReturnType().getCanonicalName();

            if (tipoDato.equals("java.lang.String")) {
                tabla.setCampo(campo, valor);

            } else if (tipoDato.equals("java.math.BigDecimal")) {
                try {
                    tabla.setCampo(campo, new BigDecimal(valor));
                } catch (Exception ex) {
                    //System.out.println("ServletGenerico: No se pudo establecer el campo: " + campo + " no es un BigDecimal Valido");
                    Logger.getLogger(ServletGenerico.class.getName()).log(Level.INFO, "No se pudo establecer el campo: " + campo + " no es un BigDecimal Valido");
                    //ex.printStackTrace();
                }
            } else if (tipoDato.equals("java.util.Date")) {

                SimpleDateFormat forfec = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;

                try {
                    date = forfec.parse(valor);
                } catch (Exception ex) {
//                    System.out.println("ServletGenerico: No se pudo convertir a fecha :" + valor);
                    Logger.getLogger(ServletGenerico.class.getName()).log(Level.INFO, "No se pudo convertir a fecha :" + valor);
                } //String fechaBD = Util.convierteFechaBD(date, true);
                //System.out.println("fechaBD = " + fechaBD);
                tabla.setCampo(campo, date);
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void copiarObjeto(Object origen, Object destino) {
        try {
            BeanUtils.copyProperties(destino, origen);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static byte[] getFileBytes(String archivo) {
        byte[] f = new byte[4096];
        byte[] result = null;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileInputStream stream = null;
        int cont = 0;
        try {
            stream = new FileInputStream(archivo);
            while ((cont = stream.read(f)) != -1) {
                output.write(f, 0, cont);
            }
            output.close();
            result = output.toByteArray();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public static void forwardToAdmonReportes(final Map<String, String> params, HttpServletRequest request, HttpServletResponse response) {
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request) {

            @Override
            public String getParameter(String param) {
                if (params.get(param.substring(1)) != null) {
                    return params.get(param.substring(1));
                } else if (params.get(param) != null) {
                    return params.get(param);
                }
                return super.getParameter(param);
            }
        };
        if (params.get("url") != null) {
            try {
                request.getRequestDispatcher(request.getParameter("url")).forward(wrapper, response);
            } catch (ServletException ex) {
                Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                request.getRequestDispatcher("/AdmonReportes").forward(wrapper, response);
            } catch (ServletException ex) {
                Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServletGenerico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
