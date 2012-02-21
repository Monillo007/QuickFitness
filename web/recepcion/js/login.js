$(function(){
    if($("#mje").val() != ''){
        $.gritter.error($("#mje").val());
    }
        
    if($("#usuario").val() == ''){
        $("#emailHint").html('<span>Usuario...</span>'); 
    }else{
        $("#emailHint").html(' ');           
    }
    if($("#pass").val() == ''){
        $("#emailHint2").html('<span>Contraseña...</span>'); 
    }else{
        $("#emailHint2").html(' ');
    } 
       
    $("#usuario").focus(function(){
        $("#emailHint").html(' ');
    });
    $("#usuario").blur(function(){
        if($("#usuario").val() == ''){
            $("#emailHint").html('<span>Usuario...</span>'); 
        } 
    });
    $("#pass").focus(function(){
        $("#emailHint2").html(' ');
    });
    $("#pass").blur(function(){
        if($("#pass").val() == ''){
            $("#emailHint2").html('<span>Contraseña...</span>'); 
        } 
    });
    
    $("#submit").click(function(){
        //event.preventDefault();
        if($("#pass").val() != '' && $("#usuario").val() != ''){
            var form = $("#loginData");
            form.attr('action','/QuickFitness/SrvRecepcion/doLogin')[0].submit();                
            //$("#loginData").attr('action','SrvFitness/doLogin').submit();
        }else{
            $.gritter.error('FALTA INFORMACIÓN NECESARIA PARA INGRESAR!');
        }
    });
    
});