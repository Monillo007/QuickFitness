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
            form.action = "/QuickFitness/ManejadorBD";
            $("body").fadeOut(1000, form.submit());
        }
    });
    
    $("#btnModificar").live('click',function(){
        if($("#modificar :checked").length == 1){           
           $("body").fadeOut(1000, $("#modificar").attr('action','frmMembresia.jsp').submit());
        }else{
             $.gritter.add({
                            text:"DEBES SELECCIONAR UNA MEMBRESIA!",
                            title:"ERROR",
                            image:"/QuickFitness/Imagenes/gritter/mje.png"
             });
        }
    });
    
   
    $("#btnEliminar").live('click',function(){
        if($("#modificar :checked").length == 1){  
           if(confirm('CONFIRMA QUE DESEA ELIMINAR LA MEMBRESIA SELECCIONADA?')){
               $("body").fadeOut(1000, $("#modificar").attr('action','/QuickFitness/SrvFitness/eliminarMembresia').submit());               
           }
        }else{
             $.gritter.add({
                            text:"DEBES SELECCIONAR UNA MEMBRESIA!",
                            title:"ERROR",
                            image:"/QuickFitness/Imagenes/gritter/mje.png"
             });
        }
    });
       
    $("#btnGuardar").live('click',function(event){
        event.preventDefault();
        $("#formDatos").submit();           
    });
    
});