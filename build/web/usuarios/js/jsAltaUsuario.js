$(document).ready(function() {
    eliminarValoresSelect();
    $("body").css("display", "none");
    $("body").fadeIn(2000);
    
    if($("#mje").val() != ''){
        $.gritter.add({
            text:$("#mje").val(),
            title:"MENSAJE",
            image:"/QuickFitness/Imagenes/gritter/mje.png"
        });
    }
    
    //    $("a.transicion").click(function(event){
    //        event.preventDefault();
    //        linkDestino = this.href;
    //        $("body").fadeOut(1000, redireccionarPag);      
    //    });
        
    function redireccionarPag() {
        window.location = linkDestino;
    }
    
    $("#formDatos").validate({
        submitHandler: function(form) {            
            form.action = "/QuickFitness/ManejadorBD";
            $("body").fadeOut(1000, form.submit());
        //form.submit();
        }
    });
    
    $("#btnModificar").live('click',function(){
        if($("#modificar :checked").length == 1){
           //$(".divBotones").html("<img src='/Imagenes/gif/loaderBlue.gif' />");
           $("body").fadeOut(1000, $("#modificar").attr('action','/QuickFitness/usuarios/frmAltaUsuario.jsp').submit());
        }else{
             $.gritter.add({
                            text:"DEBES SELECCIONAR UN USUARIO!",
                            title:"ERROR",
                            image:"/QuickFitness/Imagenes/gritter/mje.png"
             });
        }
    });
    
    $("#btnGuardar").live('click',function(){        
        if($("#contrasena").val() != ''){
            if($("#contrasena").val() == $("#contrasena_c").val()){
                if($("#accion").val() == 'C' || ($("#accion").val() == 'M' && $("#usuario").val() != $("#nombreusuario_o").val()) ){
                    $.ajax({
                        type: 'POST',
                        url: '/QuickFitness/SrvFitness',
                        data: 'accion=USUARIO_DUPLICADO&USUARIO='+$("#usuario").val(),
                        success: function(data){
                            var datos = data.split("$$");
                            if(datos[0] == 'E'){
                                $.gritter.add({
                                    text:datos[1],
                                    title:"MENSAJE",
                                    image:"/QuickFitness/Imagenes/gritter/mje.png"
                                });
                            }else{
                                $("#formDatos").submit();   
                            }
                        },
                        beforeSend: function(xhr){
                            return true;
                        },
                        error:function(){
                            $.gritter.add({
                                text:'Ocurrió un error, intentalo de nuevo.',
                                title:"MENSAJE",
                                image:"/QuickFitness/Imagenes/gritter/mje.png"
                            });
                        }
                    });                    
                }else{
                    $("#formDatos").submit();
                }
                         
            }else{
                $.gritter.add({
                    text:'Las contraseñas no coinciden',
                    title:"ERROR",
                    image:"/QuickFitness/Imagenes/gritter/cancel.png"
                });
            }            
        }else{
            $.gritter.add({
                text:'La contraseña no debe estar vacía',
                title:"ERROR",
                image:"/QuickFitness/Imagenes/gritter/cancel.png"
            });
        }
    });
    
});