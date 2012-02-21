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
           $("body").fadeOut(1000, $("#modificar").attr('action','frmAltaGimnasio.jsp').submit());
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
           $("body").fadeOut(1000, $("#modificar").attr('action','frmAdminClienteGimnasio.jsp').submit());
        }else{
             $.gritter.add({
                            text:"DEBES SELECCIONAR UN GIMNASIO!",
                            title:"ERROR",
                            image:"/QuickFitness/Imagenes/gritter/mje.png"
             });
        }
    });
    
    $("#btnMembresias").live('click',function(){
        if($("#modificar :checked").length == 1){           
           $("body").fadeOut(1000, $("#modificar").attr('action','frmMembresia.jsp').submit());
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