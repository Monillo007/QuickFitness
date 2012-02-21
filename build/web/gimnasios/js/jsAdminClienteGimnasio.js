$(document).ready(function() {
    eliminarValoresSelect();
    $("body").css("display", "none");
    $("body").fadeIn(1000);
    
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
            form.action = "/QuickFitness/SrvFitness/guardarClienteGimnasio";
            $("body").fadeOut(1000, form.submit());
        //form.submit();
        }
    });
    
    $("#btnModificar").live('click',function(){
        if($("#modificar :checked").length == 1){           
           $("body").fadeOut(1000, $("#modificar").attr('action','frmAdminClienteGimnasio.jsp').submit());
        }else{
             $.gritter.add({
                            text:"DEBES SELECCIONAR UN CLIENTE!",
                            title:"ERROR",
                            image:"/QuickFitness/Imagenes/gritter/mje.png"
             });
        }
    });
    
    $("#btnPago").live('click',function(){
        if($("#modificar :checked").length == 1){           
           $("body").fadeOut(1000, $("#modificar").attr('action','frmPagoCliente.jsp').submit());
        }else{
             $.gritter.add({
                            text:"DEBES SELECCIONAR UN CLIENTE!",
                            title:"ERROR",
                            image:"/QuickFitness/Imagenes/gritter/mje.png"
             });
        }
    });
    
    $("#btnMensaje").live('click',function(){
        if($("#modificar :checked").length == 1){           
           $("body").fadeOut(1000, $("#modificar").attr('action','frmMensaje.jsp').submit());
        }else{
             $.gritter.add({
                            text:"DEBES SELECCIONAR UN CLIENTE!",
                            title:"ERROR",
                            image:"/QuickFitness/Imagenes/gritter/mje.png"
             });
        }
    });
    
    $("#btnEliminar").live('click',function(){
        if($("#modificar :checked").length == 1){  
           if(confirm('CONFIRMA QUE DESEA ELIMINAR EL CLIENTE SELECCIONADO?')){
               $("body").fadeOut(1000, $("#modificar").attr('action','/QuickFitness/SrvFitness/eliminarClienteGimnasio').submit());               
           }
        }else{
             $.gritter.add({
                            text:"DEBES SELECCIONAR UN GIMNASIO!",
                            title:"ERROR",
                            image:"/QuickFitness/Imagenes/gritter/mje.png"
             });
        }
    });
    
    $("#btnClientes").live('click',function(){
        if($("#modificar :checked").length == 1){
           //$(".divBotones").html("<img src='/QuickFitness/Imagenes/gif/loaderBlue.gif' />");
           $("body").fadeOut(1000, $("#modificar").attr('action','frmAdminClienteGimnasio.jsp').submit());
        }else{
             $.gritter.add({
                            text:"DEBES SELECCIONAR UN GIMNASIO!",
                            title:"ERROR",
                            image:"/QuickFitness/Imagenes/gritter/mje.png"
             });
        }
    });
    
    $("#btnGuardar").live('click',function(){        
        $("#formDatos").submit();           
    });
    
});