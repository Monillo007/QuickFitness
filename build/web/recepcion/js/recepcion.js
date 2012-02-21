$(function(){
    $("#idcliente").keypress(function(event){
        if(event.keyCode == 13 && $("#idcliente").val() != ''){
            event.preventDefault();
            
            $.ajax({
                type: 'POST',
                url: '/QuickFitness/SrvRecepcion/doClienteLogin',
                data: 'idcliente='+$("#idcliente").val(),
                success: function(data){
                    $("#datosCliente").html(data);
                    $("#datosCliente").unblock();
                },
                beforeSend: function(xhr){
                    $("#datosCliente").html('');
                    $("#idcliente").val('');
                    $("#datosCliente").block();
                    return true;
                },
                error:function(){
                    $("#datosCliente").unblock();
                    $.gritter.add({
                        text:$("#mje").val(),
                        title:"MENSAJE",
                        image:"/Imagenes/gritter/mje.png"
                    });
                }
            });
        }else if(event.keyCode == 13){
            event.preventDefault();
        }
    });
    
});