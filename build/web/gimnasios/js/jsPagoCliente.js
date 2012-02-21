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
    
        
    function redireccionarPag() {
        window.location = linkDestino;
    }
    
    $("#formDatos").validate({
        submitHandler: function(form) {            
            form.action = "/QuickFitness/SrvFitness/guardarPagoCliente";
            $("body").fadeOut(1000, form.submit());        
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
    
    $("#btnEliminar").live('click',function(){
        if($("#modificar :checked").length == 1){  
           if(confirm('CONFIRMA QUE DESEA ELIMINAR EL CLIENTE SELECCIONADO?')){
               $("body").fadeOut(1000, $("#modificar").attr('action','/SrvFitness/eliminarClienteGimnasio').submit());               
           }
        }else{
             $.gritter.add({
                            text:"DEBES SELECCIONAR UN GIMNASIO!",
                            title:"ERROR",
                            image:"/QuickFitness/Imagenes/gritter/mje.png"
             });
        }
    });
    
    
    $("#btnGuardar").live('click',function(){        
        if(confirm("CONFIRMA QUE DESEA REGISTRAR EL PAGO POR $"+$("#monto").val())){
            $("#formDatos").submit();           
        }
    });
    
});