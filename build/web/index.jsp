<%-- 
    Document   : index
    Created on : 13/09/2011, 02:20:59 PM
    Author     : pool


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/Modulos/jsp_part/includes_v2.jsp?p=.gritter.ui."></jsp:include>
        <script type='text/javascript' src='js/index.js'></script>
    </head>
    <body>
        <div class="contenedor">
            <form method='post' action="#" id='formulario' name='formulario'>
                <h1>Titulo</h1>
                <table class='width80'>
                    <tr>
                        <td>Texto:</td>
                        <td>
                            <fieldset title="SECCION DE LA PAGINA" style="height:100%;">
                                <legend id="lgnd">SECCION</legend>
                                <table class='width80'>
                                    <tr>
                                        <td>
                                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sit amet ipsum nec enim suscipit sodales eget nec odio. Maecenas condimentum consequat imperdiet. Phasellus mollis, orci nec congue blandit, quam arcu condimentum mi, eu eleifend neque ipsum ut libero. Quisque at ligula a lacus ultricies facilisis. Duis aliquam ullamcorper leo et vestibulum. Fusce a tempor justo. In quis est vel enim tincidunt pretium. Donec viverra massa in risus pulvinar in lobortis purus ultrices. Etiam ullamcorper nisi quis diam vehicula vitae euismod nisi ornare. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin justo mauris, placerat sit amet lacinia at, faucibus quis nibh.
                                            <br/>
                                            In consequat mauris in leo posuere nec fermentum dui pulvinar. Mauris vel ipsum nibh, a bibendum nulla. Etiam porttitor ligula a justo rutrum ac dictum orci pulvinar. Cras non mauris risus. Vestibulum nunc sapien, congue vitae tincidunt in, adipiscing a leo. Suspendisse tincidunt elementum lorem eu suscipit. Donec rhoncus porta urna mollis fringilla. Proin libero ipsum, facilisis at rutrum at, mattis vitae nisi. Nulla vitae mauris dolor, quis tempor mi. Pellentesque sollicitudin arcu a lectus laoreet iaculis. Sed et molestie eros. Quisque faucibus scelerisque hendrerit. Ut ut ligula arcu. Suspendisse libero mi, venenatis non aliquam imperdiet, pellentesque vel sapien.
                                        </td>                        
                                    </tr>
                                </table>
                            </fieldset>
                        </td>                        
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sit amet ipsum nec enim suscipit sodales eget nec odio. Maecenas condimentum consequat imperdiet. Phasellus mollis, orci nec congue blandit, quam arcu condimentum mi, eu eleifend neque ipsum ut libero. Quisque at ligula a lacus ultricies facilisis. Duis aliquam ullamcorper leo et vestibulum. Fusce a tempor justo. In quis est vel enim tincidunt pretium. Donec viverra massa in risus pulvinar in lobortis purus ultrices. Etiam ullamcorper nisi quis diam vehicula vitae euismod nisi ornare. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin justo mauris, placerat sit amet lacinia at, faucibus quis nibh.
                            <br/>
                            In consequat mauris in leo posuere nec fermentum dui pulvinar. Mauris vel ipsum nibh, a bibendum nulla. Etiam porttitor ligula a justo rutrum ac dictum orci pulvinar. Cras non mauris risus. Vestibulum nunc sapien, congue vitae tincidunt in, adipiscing a leo. Suspendisse tincidunt elementum lorem eu suscipit. Donec rhoncus porta urna mollis fringilla. Proin libero ipsum, facilisis at rutrum at, mattis vitae nisi. Nulla vitae mauris dolor, quis tempor mi. Pellentesque sollicitudin arcu a lectus laoreet iaculis. Sed et molestie eros. Quisque faucibus scelerisque hendrerit. Ut ut ligula arcu. Suspendisse libero mi, venenatis non aliquam imperdiet, pellentesque vel sapien.
                        </td>
                    </tr>
                    <tr>
                        <td>Cuadro de texto:</td>
                        <td><input type='text' size="50" class="CuadroTexto"/></td>                        
                    </tr>
                    <tr>
                        <td>Combo:</td>
                        <td>
                            <select id='ciudad' name='ciudad' class='Combo'>
                                <option value="Leon">Leon</option>
                                <option value="Irapuato">Irapuato</option>
                                <option value="Guanajuato">Guanajuato</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Area de texto:</td>
                        <td>
                            <textarea class="AreaTexto width80" id='area' name="area" rows="5" ></textarea>
                        </td>                        
                    </tr>
                    <tr>
                        <td>Botones</td>
                        <td>
                            <div class='divBotones'>
                                <a href="/OK" class='positive' id='btnOk'><img src='Imagenes/icons/ok.png' alt='OK'/>Ok</a>
                                <a href="/Cancelar" class='negative' id='btnCancel'><img src='Imagenes/icons/cancel.png' alt='CANCELAR'/>Cancelar</a>
                                <a href="/Normal" id='btnNormal'><img src='Imagenes/icons/document.png' alt='OTRO'/>Otro</a>
                            </div>
                        </td>                        
                    </tr>
                    <tr>
                        <td>Emergentes:</td>
                        <td>
                            <div class='divBotones'>
                                <a href="/Alerta" id='btnAlertaSistema'><img src='Imagenes/icons/star.png' alt='Alerta Sistema'/>Alerta del Sistema</a>
                                <a href="/Alerta" id='btnAlertaDialog'><img src='Imagenes/icons/star.png' alt='Alerta con Estilo'/>Alerta con estilo</a>
                                <a href="/Confirmar" id='btnConfirmarSistema'><img src='Imagenes/icons/star.png' alt='Confirmar del Sistema'/>Confirmar del Sistema</a>
                                <a href="/Confirmar" id='btnConfirmarDialog'><img src='Imagenes/icons/star.png' alt='Confirmar con Estilo'/>Confirmar con Estilo</a>
                            </div>
                        </td>                        
                    </tr>
                    <tr>
                        <td>Informativos:</td>
                        <td>
                            <div class='divBotones'>
                                <a href="/Alerta" id='btnInfOk' class="positive"><img src='Imagenes/icons/ok.png' alt='Ok'/>OK</a>
                                <a href="/Alerta" id='btnInfError' class="negative"><img src='Imagenes/icons/cancel.png' alt='Error'/>Error!</a>                                
                            </div>
                        </td>                        
                    </tr>
                    <tr>
                        <td>Mensajes:</td>
                        <td>
                            <div class="mjeError" style="width:30%">Mensaje de Error...</div>
                        </td>
                    </tr>                    
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <div class="mjeInfo" style="width:30%">Mensaje Informativo...</div>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <div class="mjeAlerta" style="width:30%">Mensaje de Alerta...</div>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <div class="mjeDescarga" style="width:30%">Mensaje de Descarga...</div>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <div class="mjeNuevo" style="width:30%">Mensaje de Novedad...</div>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <div class="mjeNota" style="width:30%">Nota...</div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>--%>
<%
    response.sendRedirect("../index.jsp");
%>