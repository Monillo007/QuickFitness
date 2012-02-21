//if (top.location == self.location){
//    window.top.location.href = "/login.jsp?mensaje=No Existe Sesion Activa";
//}
var focusado = false;
document.oncontextmenu=function(){
    return false;
}
//document.ondragstart=function(){return false;}
document.onkeydown = function() {
    if(window.event && window.event.keyCode == 117){
        window.event.keyCode = 505;
    }else if(window.event && window.event.keyCode == 116){
        window.event.keyCode = 505;
    }else if(window.event && window.event.keyCode == 8){
        if(!focusado) window.event.keyCode = 505;
    }

    if(window.event && window.event.keyCode == 505){
        return false;
    }
}

function eliminarValoresSelect(){
    var select = $("select");
    if(select.length>0){
        select.each(function(index,element){
            eliminarValorSelect(element);
        });
    }
}

function eliminarValorSelect(element){
    for(var i=0;i<element.options.length;i++){
        if(element.options[i].text.toUpperCase() == "SELECCIONA..."
            || element.options[i].text.toUpperCase() == "SELECCIONAR..."){
            element.options[i].value ="";
            break;
        }
    }
}

$.format=function(source,params){
    if(arguments.length==1)return function(){
        var args=$.makeArray(arguments);
        args.unshift(source);
        return $.format.apply(this,args);
    };

    if(arguments.length>2&&params.constructor!=Array){
        params=$.makeArray(arguments).slice(1);
    }
    if(params.constructor!=Array){
        params=[params];
    }
    $.each(params,function(i,n){
        source=source.replace(new RegExp("\\{"+i+"\\}","g"),n);
    });
    return source;
};

String.prototype.trim = function(){
    return $.trim(this);
}

$.getEmpty = function(s){
    if(s==null || s==undefined) return "";
    else return s;
}

$.getField = function(v){
    if(v==null || v==undefined) return "";
    if($.type(v)==="date"){
        var fechaString = v.print("%d/%m/%Y");
        return fechaString=="01/01/1900" ? "" : fechaString;
    }else if($.type(v)==="string"){
        return $.getEmpty(v);
    }
    return v;
}

$.edad=function(fecha){
    var hoy = new Date();
    var array_fecha = fecha.split("/");
    var ano = parseInt(array_fecha[2], 10);
    var mes = parseInt(array_fecha[1], 10);
    var dia = parseInt(array_fecha[0], 10);
    var edad= 0;
    if (isNaN(ano) || isNaN(mes) || isNaN(dia)){
        return 0;
    }
    edad = hoy.getFullYear() - ano - 1;

    if (hoy.getMonth() + 1 - mes < 0) {
        return edad;

    }
    if (hoy.getMonth() + 1 - mes > 0) {
        edad = edad + 1;
        return edad;
    }

    if (hoy.getUTCDate() - dia >= 0) {
        edad = edad + 1;
        return edad;
    }

    return edad;
}

if($.gritter!=null){
    $.gritter.ok = function(mensaje){
        $.gritter.add({
            text:mensaje,
            title:"OK",
            image:"Imagenes/gritter/ok.png"
        });
    }

    $.gritter.error = function(mensaje){
        $.gritter.add({
            text:mensaje,
            title:"ERROR",
            image:"Imagenes/gritter/cancel.png"
        });
    }
}else{
    $.gritter = new Object();
    $.gritter.ok = function(mensaje){
        alert("OK: "+mensaje);
    }

    $.gritter.error = function(mensaje){
        alert("ERROR: "+mensaje);
    }
}

$.error = function(mensaje){
    $.gritter.error(mensaje);
}
$.ok = function(mensaje){
    $.gritter.error(mensaje);
}

if(jQuery.ui!=null){
    $.alerta = function(mensaje,titulo,callback){
        var ops={
            msg:mensaje,
            textoSi:"ACEPTAR",
            tipo:"ALERTA"
        };
        if($.isFunction(titulo)){
            ops.callback=titulo;
        }else{
            if($.type(titulo)=="string")
                ops.titulo=titulo;
            ops.callback=callback;
        }
        $._mensaje(ops);
    };
    
    $.confirmar = function(mensaje,titulo,callback){
        var ops={
            msg:mensaje,
            textoSi:"SI",
            textoNo:"NO",
            tipo:"CONFIRMAR"
        };
        if($.isFunction(titulo)){
            ops.callback=titulo;
        }else{
            if($.type(titulo)=="string")
                ops.titulo=titulo;
            ops.callback=callback;
        }
        $._mensaje(ops);
    };
    
    $.aceptar = function(mensaje,titulo,callback){
        var ops={
            msg:mensaje,
            textoSi:"ACEPTAR",
            textoNo:"CANCELAR",
            tipo:"ACEPTAR"
        };
        if($.isFunction(titulo)){
            ops.callback=titulo;
        }else{
            if($.type(titulo)=="string")
                ops.titulo=titulo;
            ops.callback=callback;
        }
        $._mensaje(ops);
    }
    
    $._mensaje=function(opts){
        var llamada = null;
        var callbackEjecutado = false;
        var ops = {
            tipo:'CONFIRMAR',
            msg:"MENSAJE",
            titulo:"CONFIRMAR",
            textoSi:"SI",
            textoNo:"NO",
            alto:'auto',
            mostrar:'fade',
            ocultar:'fade',
            callback:null
        }
        $.extend(ops,opts);
        llamada = ops.callback;
        var botones = [
        {
            text: ops.textoSi,
            click: function() {
                var b = true;
                if(llamada){
                    b = llamada(true);
                    callbackEjecutado=true;
                }
                if(b==true || b==undefined) $( this ).dialog( "close" );
            }
        }];
        
        if(ops.tipo=="CONFIRMAR" || ops.tipo=="ACEPTAR"){
            botones.push({
                text: ops.textoNo,
                click: function() {
                    var b = true;
                    if(llamada){
                        b = llamada(false);
                        callbackEjecutado=true;
                    }
                    if(b==true || b==undefined) $( this ).dialog( "close" );
                }
            });
        }
        
        $( "<div title=\""+ops.titulo+"\"></div>" ).html(ops.msg).appendTo($("body")).dialog({
            resizable: false,
            height:ops.alto,
            modal: true,
            show:ops.mostrar,
            hide:ops.ocultar,
            buttons: botones,
            close:function(){
                $(this).dialog('destroy').remove();
            },
            beforeClose: function() { 
                if(llamada)
                    if(callbackEjecutado==false){
                        return false;
                    }
                return true;
            }
        });
    }
}

//dwr.engine.setErrorHandler(function(message, ex) {
//    dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);
//    // Ignore NS_ERROR_NOT_AVAILABLE if Mozilla is being narky
//    if (message.indexOf("0x80040111") != -1) dwr.engine._debug(message);
//    else $.gritter.error(message=="Error" || message==null || message==""? "Ocurrio un error inesperado. Contacte a Soporte Tecnico":message);
//});

$(document).ready(function(){

    var htmlTabla = "<table class='width90 gradient-style' id='{0}'>\n\
<thead>\n\
</thead>\n\
<tbody>\n\
</tbody>\n\
<tfoot>\n\
</tfoot>\n\
</table>";

//    $(".ajaxestado").change(function (event){
//        var target = $(event.target);
//        var idMun = target.attr("idmunicipio")!=undefined ? target.attr("idmunicipio"):"IDMUNICIPIO";
//        var jMun = $("#"+idMun);
//        if(jMun.length>0){
//            dwr.util.removeAllOptions(idMun);
//            dwr.util.addOptions(idMun, [{
//                value:"",
//                text:"SELECCIONAR..."
//            }], "value", "text");
//            if(target.val()!=""){
//                DWRGeneral.getMunicipios(target.val(),function (data){
//                    if(data!=null && data!=undefined && data.length>0){
//                        dwr.util.addOptions(idMun, data,"IDMUNICIPIO","DESCRIPCION");
//                    }
//                });
//            }
//            jMun.trigger("change");
//            jMun.trigger('extensions.cambioOpciones');
//        }
//    });
//
//    $(".ajaxmunicipio").change(function (event){
//        var target = $(event.target);
//        var idCol = target.attr("idcolonia")!=undefined ? target.attr("idcolonia"):"IDCOLONIA";
//        var idCalle = target.attr("idcalle")!=undefined ? target.attr("idcalle"):"IDCALLE";
//        var idBuscaCalle = target.attr("idbuscacalle")!=undefined ? target.attr("idbuscacalle"):"IDBUSCACALLE";
//        var jCol = $("#"+idCol);
//        var jCalle = $("#"+idCalle);
//        var jBuscaCalle = $("#"+idBuscaCalle);
//        if(jCol.length>0){
//            jCol.empty();
//            dwr.util.addOptions(idCol, [{
//                value:"",
//                text:"SELECCIONAR..."
//            }], "value", "text");
//            if(target.val()!=""){
//                DWRGeneral.getColonias(target.val(),function (data){
//                    if(data!=null && data!=undefined && data.length>0){
//                        dwr.util.addOptions(idCol, data,"IDCOLONIA","DESCRIPCION");
//                    }
//                });
//            }
//            jCol.trigger("change");
//            jCol.trigger('extensions.cambioOpciones');
//        }
//        if(jCalle.length>0){
//            if(jBuscaCalle.length>0){
//                jBuscaCalle.blur();
//                var buscarcalle = function(event){
//                    var alto = jCalle.outerHeight();
//                    var ancho = jCalle.outerWidth();
//                    jCalle.html("<option value=''>SELECCIONAR...</option>");
//                    if($(event.target).val()!="" && $(event.target).val().length>=2){
//                        //
//                        DWRGeneral.getCalles(target.val(),$(event.target).val(),function (data){
//                            if(data!=null && data!=undefined && data.length>0){
//                                var newHtml="";
//                                var options = new Array();
//                                for(var ix =0;ix<data.length;ix++){
//                                    newHtml = newHtml + "<option value='"+data[ix].IDCALLE+"'>"+data[ix].DESCRIPCION+"</option>";
//                                //                            jCalle[0].options[ix+1] = new Option(data[ix].DESCRIPCION,data[ix].IDCALLE);
//                                }
//                                //jCalle.append(newHtml);
//                                jCalle.html("<option value=''>SELECCIONAR...</option>"+newHtml);
//                            //jCalle[0].options = options;
//                            //dwr.util.addOptions(idCalle, data,"IDCALLE","DESCRIPCION");
//                            }
//                        });
//                        //            jCalle.appendTo(parent);
//                        jCalle.trigger("change");
//                    }
//                    jCalle.height(alto);
//                    jCalle.width(ancho);
//                };
//                jBuscaCalle.unbind('blur');
//                jBuscaCalle.blur(buscarcalle);
//            }
//        }
//    });

    $("input[type=text], textarea").keypress(function(event){
        if(event.keyCode != 9 && event.keyCode != 16) return true;
        else return false;
    }).blur(function(){
        focusado=false;
    }).focus(function(){
        focusado=true;
    });
    
    $(".clickborrar").click(function(event){
        $(event.target).val("");
    });
    
    $(".calendario").each(function(index,elem){
        var $elem = $(elem);
        var idBoton = $elem.attr("id");
        var formato = "%d/%m/%Y";
        var formatoMascara = "99/99/9999";
        var fmt = $elem.attr("formato");
        var muestraTiempo = false;
        var width = 100;
        if(fmt=="hora"){
            formato = "%H:%M:%S";
            muestraTiempo=true;
            formatoMascara = "99:99:99";
        }else if (fmt=="fechahora"){
            formato = "%d/%m/%Y %H:%M:%S";
            muestraTiempo=true;
            formatoMascara = "99/99/9999 99:99:99";
            width=130;
        }
        
        var idInput = idBoton.split("_");
        Calendar.setup({
            inputField     :    idInput.slice(1).join("_"),
            ifFormat     :     formato,
            button     :    idBoton,
            timeFormat:"24",
            showsTime:muestraTiempo
        });
        $("#"+idInput.slice(1).join("_")).css("width",width+"px").mask(formatoMascara);
    });
    
    $(".divBotones a").click(function(event){
        event.preventDefault();
    });

    $(".selecusuario").click(function(event){
        var target = $(event.target);
        var nombreid = target.attr("nombreid");
        var paternoid = target.attr("paternoid");
        var maternoid = target.attr("maternoid");
        var evento = target.attr("evento");
        var inactivos = target.attr("inactivos") == "true"?true:false;
        var rfcid = target.attr("rfcid");
        var divDialog = $("<div title='BUSQUEDA DE PERSONAS'></div>");
        target.data("part_selectusuario_dialog",1);
        divDialog.load("/Modulos/jsp_part/part_selecusuario_dialog.jsp",{
            i:inactivos
        },function(response){
            if(rfcid!=undefined){
                divDialog.find("#_PART_SELECUSUARIO_RFC").val($("#"+rfcid).val());
            }
            divDialog.find("#_PART_SELECUSUARIO_RFC")[0].focus();
            divDialog.dialog({
                close: function(event, ui) {
                    $(this).html("");
                },
                height: 500,
                width:550,
                modal: true,
                buttons: {
                    'CANCELAR':function(){
                        $(this).dialog('close');
                    },
                    'ACEPTAR':function(){
                        var jChecked = divDialog.find(":checked");
                        if(jChecked.length==1){
                            $("#"+rfcid).val(jChecked.val());
                            if(nombreid!=undefined && nombreid.length>0 && $("#"+nombreid).length>0){
                                var jNombre = $("#"+nombreid);
                                var tds = jChecked.parent().parent().find("td");
                                var nombre = $.trim($(tds[5]).text());
                                var apaterno = $.trim($(tds[3]).text());
                                var amaterno = $.trim($(tds[4]).text());

                                if(paternoid!=undefined && paternoid.length>0 && $("#"+paternoid).length>0 &&
                                    maternoid!=undefined && maternoid.length>0 && $("#"+maternoid).length>0
                                    ){
                                    var jPaterno = $("#"+paternoid);
                                    var jMaterno = $("#"+maternoid);

                                    jNombre.val(nombre);
                                    jPaterno.val(apaterno);
                                    jMaterno.val(amaterno);
                                }else{
                                    jNombre.val(nombre+" "+apaterno+" "+amaterno);
                                }

                            }
                            $(this).dialog('close');
                            if(evento!=undefined){
                                $(target).trigger(evento);
                            }
                        }else{
                            alert("SELECCIONE UN REGISTRO");
                        }
                    }
                }
            });
        });
    });
    
    $(".selecpersona[rfcbuscarid]").each(function(index,elem){
        var $elem = $(elem);
        var rfcbuscarid = $elem.attr("rfcbuscarid");
        if(rfcbuscarid!=null){
            $("#"+rfcbuscarid).bind('change',function(event){
                $elem.trigger('click',["rfcbuscarid"]);
            });
            var imgbuscarid = $elem.attr("imgbuscarid");
            if(imgbuscarid!=null){
                $("#"+imgbuscarid).bind('click',function(event){
                    $elem.trigger('click',["rfcbuscarid"]);
                });
            }
        }
    });
    
    $(".selecpersona").click(function(event,param){
        var target = $(event.target);
        var inactivos = target.attr("inactivos") == "true"?true:false;
        var idur = target.attr("ur")!=undefined? target.attr("ur").trim() : "";
        var idcargo = target.attr("cargo")!=undefined? target.attr("cargo").trim() : "";
        var rfcid = target.attr("rfcid");
        var evento = target.attr("evento");
        var txtrfcbuscarid = target.attr("rfcbuscarid");
        var rfcbuscarid = param;
        var nombreid = target.attr("nombreid");
        var paternoid = target.attr("paternoid");
        var maternoid = target.attr("maternoid");
        var personaid = target.attr("personaid");
        var divDialog = $("<div title='BUSQUEDA DE PERSONAS'></div>");
        target.data("part_selectusuario_dialog",1);
        divDialog.load("/Modulos/jsp_part/part_selecpersona_dialog.jsp",{
            i:inactivos,
            ur:idur,
            cargo:idcargo
        },function(response){
            if(rfcid!=undefined){
                divDialog.find("#_PART_SELECPERSONA_RFC").val($("#"+rfcid).val());
            }
            if(rfcbuscarid!=null){
                divDialog.find("#_PART_SELECPERSONA_RFC").val($("#"+txtrfcbuscarid).val());
            }
            divDialog.find("#_PART_SELECPERSONA_RFC")[0].focus();
            divDialog.dialog({
                close: function(event, ui) {
                    $(this).html("");
                },
                height: 350,
                width:550,
                modal: true,
                buttons: {
                    'CANCELAR':function(){
                        $(this).dialog('close');
                    },
                    'ACEPTAR':function(){
                        var jChecked = divDialog.find(":checked");
                        if(jChecked.length==1){
                            $("#"+personaid).val(jChecked.val());
                            var tds = jChecked.parent().parent().find("td");
                            $("#"+rfcid).val($(tds[2]).text());
                            if(nombreid!=undefined && nombreid.length>0 && $("#"+nombreid).length>0){
                                var nombre = $.trim($(tds[5]).text());
                                var apaterno = $.trim($(tds[3]).text());
                                var amaterno = $.trim($(tds[4]).text());
                                var jNombre = $("#"+nombreid);

                                if(paternoid!=undefined && paternoid.length>0 && $("#"+paternoid).length>0 &&
                                    maternoid!=undefined && maternoid.length>0 && $("#"+maternoid).length>0
                                    ){
                                    var jPaterno = $("#"+paternoid);
                                    var jMaterno = $("#"+maternoid);

                                    jNombre.val(nombre);
                                    jPaterno.val(apaterno);
                                    jMaterno.val(amaterno);
                                }else{                                    
                                    jNombre.val(nombre+" "+apaterno+" "+amaterno);
                                }
                            }
                            $(this).dialog('close');
                            if(evento!=undefined){
                                $(target).trigger(evento);
                            }
                        }else{
                            alert("SELECCIONE UN REGISTRO");
                        }
                    }
                }
            });
            if(rfcbuscarid!=null){
                if($.trim($("#"+txtrfcbuscarid).val())!=""){
                    divDialog.find("#_PART_SELECPERSONA_btnBuscar").click();
                }
            }
        });
    });

    $(".catadmon").click(function(event){
        var target = $(event.target);
        var esq = target.attr("e");
        var tbl = target.attr("t");
        var comboid = target.attr("comboid");
        var permiteDuplicados = target.attr("dup") != undefined && $.trim(target.attr("dup")).length>0 ? target.attr("dup"):"0";
        var enProceso = 0;
        var divDialog = $("<div title='AGREGAR OPCION'><table class='width100'><tr><td class='NombreDeCampo'>DESCRIPCION</td></tr><tr><td>\n\
                        <input class='CuadroTexto width90' type='text' name='DESCRIPCION' id='_part_catadmon_descripcion' /></td></tr></table></div>");
        divDialog.dialog({
            close: function(event, ui) {
                $(this).remove();
            },
            height: 190,
            width:350,
            modal: true,
            buttons: {
                'CANCELAR':function(){
                    $(this).dialog('close');
                },
                'ACEPTAR':function(){
                    var jDesc = divDialog.find("#_part_catadmon_descripcion");
                    if($.trim(jDesc.val())!=""){
                        if(enProceso==0){
                            enProceso=1;
                            DWRGeneral.agregarOpcionCatalogo(esq,tbl,jDesc.val(),permiteDuplicados,function(data){
                                if(data!=null && data!=undefined){
                                    if(parseInt(data)>0){
                                        var options = $("#"+comboid)[0].options;
                                        options[options.length] = new Option($.trim(jDesc.val()), parseInt(data), false, true);
                                        $(divDialog).dialog('close');
                                    }else if(data==-1){
                                        alert("REGISTRO DUPLICADO. VERIFIQUE");
                                        jDesc[0].focus();
                                        enProceso=0;
                                    }
                                }else{
                                    alert("NO SE PUDO AGREGAR EL REGISTRO. CONTACTE AL ADMINISTRADOR DEL SISTEMA");
                                    enProceso=0;
                                    jDesc[0].focus();
                                }
                            });
                        }else{
                    //HACER NADA
                    }
                    }else{
                        alert("INTRODUZCA LA DESCRIPCION DE LA OPCION A AGREGAR");
                        jDesc[0].focus();
                    }
                }
            }
        });
    //});
    });

//    $("#_PART_SELECUSUARIO_FRMBUSCAR").live('submit',function(event){
//        var jForm = $("#_PART_SELECUSUARIO_FRMBUSCAR");
//        var jRfc = jForm.find("#_PART_SELECUSUARIO_RFC");
//        var jApaterno = jForm.find("#_PART_SELECUSUARIO_APATERNO");
//        var jAmaterno = jForm.find("#_PART_SELECUSUARIO_AMATERNO");
//        var jNombre = jForm.find("#_PART_SELECUSUARIO_NOMBRE");
//        var jInactivos = jForm.find("#_PART_SELECUSUARIO_INACTIVOS");
//        if(jRfc.val()!="" || jApaterno.val()!="" || jAmaterno.val()!="" || jNombre.val()!=""){
//            $("#_PART_SELECUSUARIO_RESULTADOS").html("<img src='Imagenes/gif/circleBlue.gif'/>BUSCANDO...");
//            DWRGeneral.getUsuarios(jRfc.val(),jApaterno.val(),jAmaterno.val(),jNombre.val(),jInactivos,function(data){
//                if(data!=null && data!=undefined && data.length>0){
//
//                    var arHeader = ["RFC","AP. PATERNO","AP. MATERNO","NOMBRE"];
//                    var html= $.format(htmlTabla,"_PART_SELECUSUARIO_TABLA");
//                    var divResultados = $("#_PART_SELECUSUARIO_RESULTADOS").html(html);
//                    var thead =divResultados.find("thead");
//                    var tbody =divResultados.find("tbody");
//                    var htmlHead = "<tr><th>SEL.</th><th>#</th>"
//                    for(var h in arHeader){
//                        htmlHead+="<th>"+arHeader[h]+"</th>";
//                    }
//                    htmlHead+="</tr>";
//                    thead.html(htmlHead);
//                    var htmlBody="";
//                    for(var i=0;i<data.length;i++){
//                        var x = data[i];
//                        htmlBody += "<tr >"+
//                        "<td><input type='radio' name='IDUSER' value='"+x.IDUSER+"' /></td>"+
//                        "<td>"+(i+1)+"</td>"+
//                        "<td>"+x.IDUSER+"</td>"+
//                        "<td>"+x.APATERNO+"</td>"+
//                        "<td>"+x.AMATERNO+"</td>"+
//                        "<td>"+x.NOMBRE+"</td></tr>";
//                    }
//                    tbody.html(htmlBody);
//                }else{
//                    $("#_PART_SELECUSUARIO_RESULTADOS").html("<span>NO SE ENCONTRARON REGISTROS QUE COINCIDIERAN CON LOS CRITERIOS DE BUSQUEDA</span>")
//                }
//            });
//        }else{
//            $("#_PART_SELECUSUARIO_RESULTADOS").html("<span>INTRODUZCA AL MENOS UN CRITERIO DE BUSQUEDA</span>");
//        }
//        return false;
//    });
//    $("#_PART_SELECPERSONA_FRMBUSCAR").live('submit',function(event){
//        var jForm = $("#_PART_SELECPERSONA_FRMBUSCAR");
//        var jRfc = jForm.find("#_PART_SELECPERSONA_RFC");
//        var jApaterno = jForm.find("#_PART_SELECPERSONA_APATERNO");
//        var jAmaterno = jForm.find("#_PART_SELECPERSONA_AMATERNO");
//        var jNombre = jForm.find("#_PART_SELECPERSONA_NOMBRE");
//        var jInactivos = jForm.find("#_PART_SELECPERSONA_INACTIVOS");
//        if(jRfc.val()!="" || jApaterno.val()!="" || jAmaterno.val()!="" || jNombre.val()!=""){
//            $("#_PART_SELECPERSONA_RESULTADOS").html("<img src='Imagenes/gif/loaderBlue.gif'/>BUSCANDO...");
//            var params = new Object();
//            jForm.find("input,select").not(".not").each(function(index,elem){
//                var $this = $(elem);
//                if($this.val().toString().trim()!=""){
//                    params[$this.attr("name")]=$this.val();
//                }
//            });
//            params["INACTIVOS"]=jInactivos.val();
//            DWRGeneral.buscarPersonas(params,function(data){
//                if(data!=null && data!=undefined && data.length>1){
//
//                    var arHeader = ["RFC","AP. PATERNO","AP. MATERNO","NOMBRE"];
//                    var html= $.format(htmlTabla,"_PART_SELECPERSONA_TABLA");
//                    var divResultados = $("#_PART_SELECPERSONA_RESULTADOS").html(html);
//                    var thead =divResultados.find("thead");
//                    var tbody =divResultados.find("tbody");
//                    var htmlHead = "<tr class='EncabezadoSmall'><th>SEL.</th><th>#</th>"
//                    for(var h in arHeader){
//                        htmlHead+="<th>"+arHeader[h]+"</th>";
//                    }
//                    htmlHead+="</tr>";
//                    thead.html(htmlHead);
//                    var htmlBody="";
//                    for(var i=1;i<data.length;i++){
//                        var x = data[i];
//                        htmlBody += "<tr class='contenidoovertrSmall'>"+
//                        "<td><input type='radio' name='IDPERSONA' value='"+x.IDPERSONA+"' /></td>"+
//                        "<td>"+(i)+"</td>"+
//                        "<td>"+x.RFC+"</td>"+
//                        "<td>"+x.APATERNO+"</td>"+
//                        "<td>"+x.AMATERNO+"</td>"+
//                        "<td>"+x.NOMBRE+"</td></tr>";
//                    }
//                    tbody.html(htmlBody);
//                }else{
//                    $("#_PART_SELECPERSONA_RESULTADOS").html("<span class='TituloTexto10px'>NO SE ENCONTRARON REGISTROS QUE COINCIDIERAN CON LOS CRITERIOS DE BUSQUEDA</span>")
//                }
//            });
//        }else{
//            $("#_PART_SELECPERSONA_RESULTADOS").html("<span class='TituloTexto10px'>INTRODUZCA AL MENOS UN CRITERIO DE BUSQUEDA</span>");
//        }
//        return false;
//    });
    
    //Inicia: Para resaltar las columnas seleccionadas en una tabla
    $(".gradient-style tr td:nth-child(1) input[type=radio]").live('click',function(event){
        var $parent=$(event.target).parent();//td
        var $table = $parent.parent().parent().parent();
        var lastClicked = $table.data("_lastClicked");
        if(lastClicked!=null){
            lastClicked.parent().nextAll("td").andSelf().removeClass("seleccionado");
        }
        $parent.nextAll("td").andSelf().addClass("seleccionado");
        $table.data("_lastClicked",$(event.target));
        event.stopPropagation();
    });
    $(".gradient-style tr td:nth-child(1) input[type=checkbox]").live('click',function(event,autoclick){
        var $target=$(this);
        var $parent=$(event.target).parent();//td
        if(autoclick=="autoclick"){
            if($target.attr("checked")==true)
                $parent.nextAll("td").andSelf().removeClass("seleccionado");
            else
                $parent.nextAll("td").andSelf().addClass("seleccionado");
        }else{
            if($target.attr("checked")==true)
                $parent.nextAll("td").andSelf().addClass("seleccionado");
            else
                $parent.nextAll("td").andSelf().removeClass("seleccionado");
        }
        event.stopPropagation();
    });
    
    $(".gradient-style tr td").live('click',function(event){
        var $this = $(this);
        var $parent = $this.parent();
        var jChecks=$parent.find("td:nth-child(1) input[type=checkbox]");
        $parent.find("td:nth-child(1) input[type=radio]").click();
        jChecks.trigger('click',["autoclick"]);
    });
    
    //Termina: Para resaltar las columnas seleccionadas en una tabla
    
    //Inicia: Combos en cascada
//    $(".ajaxcascada").change(function(event){
//        var $target=$(event.target);
//        var idCombo=$target.attr("idcombo");
//        var params = new Object();
//        function addP(ar){
//            for(var ix=0;ix<ar.length;ix++){
//                var pv = $target.attr(ar[ix])!=null ? $target.attr(ar[ix]):null;
//                if(pv!=null) params[ar[ix]]=pv;
//            }
//        }
//        addP(["e","t","d","cr","c","i","o"]);
//        params["val"]=$target.val();
//               
//        dwr.util.removeAllOptions(idCombo);
//        dwr.util.addOptions(idCombo, [{
//            value:"",
//            text:"SELECCIONAR..."
//        }], "value", "text");
//        if($target.val()!=""){
//            DWRGeneral.getListaCombo(params,function(data){
//                if(data!=null && data.length>0)
//                    dwr.util.addOptions(idCombo, data,"ID","DESCRIPCION");
//            });
//        }
//        $("#"+idCombo).change();
//        $("#"+idCombo).trigger('extensions.cambioOpciones');
//    });
    //Termina: Combos en cascada
    
    //Inicia: Para filtrar las opciones de un combo
    $(".combobuscar").each(function(index,elem){
        var $elem = $(elem);
        $("#"+$elem.attr("idcombo")).bind("extensions.cambioOpciones",function(){
            $elem.removeData(".opcioncombo.");
            $elem.val("");
        });
    }).change(function(event){
        var jCombo = $("#"+$(this).attr("idcombo"));
        var opcionesCombo = $(this).data(".opcioncombo.");
        if(opcionesCombo==null){
            opcionesCombo=jCombo.clone();
            $(this).data(".opcioncombo.",opcionesCombo);
        }
        var alto = jCombo.outerHeight();
        var ancho = jCombo.outerWidth();
        if($(this).val()!=""){
            jCombo.empty();
            jCombo.append("<option value=''>SELECCIONAR...</option>");
            opcionesCombo.find("option:contains("+$.trim($(this).val()).toUpperCase()+")").clone().appendTo(jCombo);
        }else{
            jCombo.empty();
            jCombo.append(opcionesCombo.find("option").clone());
        }
        jCombo.height(alto);
        jCombo.width(ancho);
    });
//Termina para filtrar las opciones de un combo
});
