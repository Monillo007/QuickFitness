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


$(document).ready(function(){

    var htmlTabla = "<table class='width90 gradient-style' id='{0}'>\n\
<thead>\n\
</thead>\n\
<tbody>\n\
</tbody>\n\
<tfoot>\n\
</tfoot>\n\
</table>";

    //    var ajax_comboHtml = "<select class='Combo width90' name='{0}' id='{0}'><option value=''>Seleccionar...</option></select>";

    $(".ajaxestado").change(function (event){
        var target = $(event.target);
        var idMun = target.attr("idmunicipio")!=undefined ? target.attr("idmunicipio"):"IDMUNICIPIO";
        var jMun = $("#"+idMun);
        if(jMun.length>0){
            dwr.util.removeAllOptions(idMun);
            dwr.util.addOptions(idMun, [{
                value:"",
                text:"SELECCIONAR..."
            }], "value", "text");
            if(target.val()!=""){
                DWRGeneral.getMunicipios(target.val(),function (data){
                    if(data!=null && data!=undefined && data.length>0){
                        dwr.util.addOptions(idMun, data,"IDMUNICIPIO","DESCRIPCION");
                    }
                });
            }
            jMun.trigger("change");
        }
    });

    $(".ajaxmunicipio").change(function (event){
        var target = $(event.target);
        var idCol = target.attr("idcolonia")!=undefined ? target.attr("idcolonia"):"IDCOLONIA";
        var idCalle = target.attr("idcalle")!=undefined ? target.attr("idcalle"):"IDCALLE";
        var idBuscaCalle = target.attr("idbuscacalle")!=undefined ? target.attr("idbuscacalle"):"IDBUSCACALLE";
        var jCol = $("#"+idCol);
        var jCalle = $("#"+idCalle);
        var jBuscaCalle = $("#"+idBuscaCalle);
        if(jCol.length>0){
            //dwr.util.removeAllOptions(idCol);
            jCol.empty();
            dwr.util.addOptions(idCol, [{
                value:"",
                text:"SELECCIONAR..."
            }], "value", "text");
            if(target.val()!=""){
                DWRGeneral.getColonias(target.val(),function (data){
                    if(data!=null && data!=undefined && data.length>0){
                        dwr.util.addOptions(idCol, data,"IDCOLONIA","DESCRIPCION");
                    }
                });
            }
            jCol.trigger("change");
        }
        if(jCalle.length>0){
            if(jBuscaCalle.length>0){
                jBuscaCalle.blur();
                var buscarcalle = function(event){
                    var alto = jCalle.outerHeight();
                    var ancho = jCalle.outerWidth();
                    jCalle.html("<option value=''>SELECCIONAR...</option>");
                    if($(event.target).val()!="" && $(event.target).val().length>=2){
                        //
                        DWRGeneral.getCalles(target.val(),$(event.target).val(),function (data){
                            if(data!=null && data!=undefined && data.length>0){
                                var newHtml="";
                                var options = new Array();
                                for(var ix =0;ix<data.length;ix++){
                                    newHtml = newHtml + "<option value='"+data[ix].IDCALLE+"'>"+data[ix].DESCRIPCION+"</option>";
                                //                            jCalle[0].options[ix+1] = new Option(data[ix].DESCRIPCION,data[ix].IDCALLE);
                                }
                                //jCalle.append(newHtml);
                                jCalle.html("<option value=''>SELECCIONAR...</option>"+newHtml);
                            //jCalle[0].options = options;
                            //dwr.util.addOptions(idCalle, data,"IDCALLE","DESCRIPCION");
                            }
                        });
                        //            jCalle.appendTo(parent);
                        jCalle.trigger("change");
                    }
                    jCalle.height(alto);
                    jCalle.width(ancho);
                }
                jBuscaCalle.unbind('blur');
                jBuscaCalle.blur(buscarcalle);
            }
        //            //var idMun = jCalle.attr("idmunicipio")!= undefined && jCalle.attr("idmunicipio").trim().length>0? jCalle.attr("idmunicipio"):"IDMUNICIPIO";
        //            var clonCalle = jCalle.clone(true);
        //            var parent = jCalle.parent();
        //            var alto = jCalle.outerHeight();
        //            var ancho = jCalle.outerWidth();
        //
        //            jCalle.html("<option value=''>SELECCIONAR...</option>").remove();
        //            jCalle = clonCalle;
        //            jCalle.html("<option value=''>SELECCIONAR...</option>");
        //            //jCalle.empty();
        //            //dwr.util.removeAllOptions(idCalle);
        ////                        dwr.util.addOptions(idCalle, [{
        ////                            value:"",
        ////                            text:"SELECCIONAR..."
        ////                        }], "value", "text");
        //
        //            if(target.val()!=""){
        //                DWRGeneral.getCalles(target.val(),function (data){
        //                    if(data!=null && data!=undefined && data.length>0){
        //                        var newHtml="";
        //                        var options = new Array();
        //                        for(var ix =0;ix<data.length;ix++){
        //                            newHtml = newHtml + "<option value='"+data[ix].IDCALLE+"'>"+data[ix].DESCRIPCION+"</option>";
        //                        //                            jCalle[0].options[ix+1] = new Option(data[ix].DESCRIPCION,data[ix].IDCALLE);
        //                        }
        //                        //jCalle.append(newHtml);
        //                        jCalle.html("<option value=''>SELECCIONAR...</option>"+newHtml);
        //                    //jCalle[0].options = options;
        //                    //dwr.util.addOptions(idCalle, data,"IDCALLE","DESCRIPCION");
        //                    }
        //                });
        //            }
        //            jCalle.height(alto);
        //            jCalle.width(ancho);
        //            jCalle.appendTo(parent);
        //            jCalle.trigger("change");
        }
    });

    $("input[type=text], textarea").live('keypress',function(event){
        if(event.keyCode != 9 && event.keyCode != 16) return true;
        else return false;
    }).live('blur',function(){
        focusado=false;
    }).live('focus',function(){
        focusado=true;
    });
    $(".clickborrar").click(function(event){
        $(event.target).val("");
    });
    //$(".formatofecha").mask("99/99/9999");
    
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
            //divDialog.html(response);
            //$("#_PART_SELECUSUARIO_FRMBUSCAR").live('submit',function(event){
            
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
    $(".selecpersona").click(function(event){
        var target = $(event.target);
        var inactivos = target.attr("inactivos") == "true"?true:false;
        var rfcid = target.attr("rfcid");
        var evento = target.attr("evento");
        var nombreid = target.attr("nombreid");
        var paternoid = target.attr("paternoid");
        var maternoid = target.attr("maternoid");
        var personaid = target.attr("personaid");
        var divDialog = $("<div title='BUSQUEDA DE PERSONAS'></div>");
        target.data("part_selectusuario_dialog",1);
        divDialog.load("/Modulos/jsp_part/part_selecpersona_dialog.jsp",{
            i:inactivos
        },function(response){
            //divDialog.html(response);
            //$("#_PART_SELECUSUARIO_FRMBUSCAR").live('submit',function(event){

            if(rfcid!=undefined){
                divDialog.find("#_PART_SELECPERSONA_RFC").val($("#"+rfcid).val());
            }
            divDialog.find("#_PART_SELECPERSONA_RFC")[0].focus();
            divDialog.dialog({
                close: function(event, ui) {
                    $(this).html("");
                },
                height: 300,
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
//                                var nombre = $.trim($(tds[3]).text());
//                                var apaterno = $.trim($(tds[4]).text());
//                                var amaterno = $.trim($(tds[5]).text());
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
        //divDialog.load("/Modulos/jsp_part/part_selecusuario_dialog.jsp",function(){
        divDialog.dialog({
            close: function(event, ui) {
                $(this).remove();
            },
            height: 150,
            width:250,
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

    $("#_PART_SELECUSUARIO_FRMBUSCAR").live('submit',function(event){
        var jForm = $("#_PART_SELECUSUARIO_FRMBUSCAR");
        var jRfc = jForm.find("#_PART_SELECUSUARIO_RFC");
        var jApaterno = jForm.find("#_PART_SELECUSUARIO_APATERNO");
        var jAmaterno = jForm.find("#_PART_SELECUSUARIO_AMATERNO");
        var jNombre = jForm.find("#_PART_SELECUSUARIO_NOMBRE");
        var jInactivos = jForm.find("#_PART_SELECUSUARIO_INACTIVOS");
        if(jRfc.val()!="" || jApaterno.val()!="" || jAmaterno.val()!="" || jNombre.val()!=""){
            $("#_PART_SELECUSUARIO_RESULTADOS").html("<img src='/Imagenes/gif/circleBlue.gif'/>BUSCANDO...");
            DWRGeneral.getUsuarios(jRfc.val(),jApaterno.val(),jAmaterno.val(),jNombre.val(),jInactivos,function(data){
                if(data!=null && data!=undefined && data.length>0){

                    var arHeader = ["RFC","AP. PATERNO","AP. MATERNO","NOMBRE"];
                    var html= $.format(htmlTabla,"_PART_SELECUSUARIO_TABLA");
                    var divResultados = $("#_PART_SELECUSUARIO_RESULTADOS").html(html);
                    var thead =divResultados.find("thead");
                    var tbody =divResultados.find("tbody");
                    var htmlHead = "<tr><th>SEL.</th><th>#</th>"
                    for(var h in arHeader){
                        htmlHead+="<th>"+arHeader[h]+"</th>";
                    }
                    htmlHead+="</tr>";
                    thead.html(htmlHead);
                    var htmlBody="";
                    for(var i=0;i<data.length;i++){
                        var x = data[i];
                        htmlBody += "<tr >"+
                        "<td><input type='radio' name='IDUSER' value='"+x.IDUSER+"' /></td>"+
                        "<td>"+(i+1)+"</td>"+
                        "<td>"+x.IDUSER+"</td>"+
                        "<td>"+x.APATERNO+"</td>"+
                        "<td>"+x.AMATERNO+"</td>"+
                        "<td>"+x.NOMBRE+"</td></tr>";
                    }
                    tbody.html(htmlBody);
                }else{
                    $("#_PART_SELECUSUARIO_RESULTADOS").html("<span>NO SE ENCONTRARON REGISTROS QUE COINCIDIERAN CON LOS CRITERIOS DE BUSQUEDA</span>")
                }
            });
        }else{
            $("#_PART_SELECUSUARIO_RESULTADOS").html("<span>INTRODUZCA AL MENOS UN CRITERIO DE BUSQUEDA</span>");
        }
        return false;
    });
    $("#_PART_SELECPERSONA_FRMBUSCAR").live('submit',function(event){
        var jForm = $("#_PART_SELECPERSONA_FRMBUSCAR");
        var jRfc = jForm.find("#_PART_SELECPERSONA_RFC");
        var jApaterno = jForm.find("#_PART_SELECPERSONA_APATERNO");
        var jAmaterno = jForm.find("#_PART_SELECPERSONA_AMATERNO");
        var jNombre = jForm.find("#_PART_SELECPERSONA_NOMBRE");
        var jInactivos = jForm.find("#_PART_SELECPERSONA_INACTIVOS");
        if(jRfc.val()!="" || jApaterno.val()!="" || jAmaterno.val()!="" || jNombre.val()!=""){
            $("#_PART_SELECPERSONA_RESULTADOS").html("<img src='/Imagenes/gif/loaderBlue.gif'/>BUSCANDO...");
            DWRGeneral.getPersonas(jRfc.val(),jApaterno.val(),jAmaterno.val(),jNombre.val(),jInactivos.val(),function(data){
                if(data!=null && data!=undefined && data.length>0){

                    var arHeader = ["RFC","AP. PATERNO","AP. MATERNO","NOMBRE"];
                    var html= $.format(htmlTabla,"_PART_SELECPERSONA_TABLA");
                    var divResultados = $("#_PART_SELECPERSONA_RESULTADOS").html(html);
                    var thead =divResultados.find("thead");
                    var tbody =divResultados.find("tbody");
                    var htmlHead = "<tr class='EncabezadoSmall'><th>SEL.</th><th>#</th>"
                    for(var h in arHeader){
                        htmlHead+="<th>"+arHeader[h]+"</th>";
                    }
                    htmlHead+="</tr>";
                    thead.html(htmlHead);
                    var htmlBody="";
                    for(var i=0;i<data.length;i++){
                        var x = data[i];
                        htmlBody += "<tr class='contenidoovertrSmall'>"+
                        "<td><input type='radio' name='IDPERSONA' value='"+x.IDPERSONA+"' /></td>"+
                        "<td>"+(i+1)+"</td>"+
                        "<td>"+x.RFC+"</td>"+
                        "<td>"+x.APATERNO+"</td>"+
                        "<td>"+x.AMATERNO+"</td>"+
                        "<td>"+x.NOMBRE+"</td></tr>";
                    }
                    tbody.html(htmlBody);
                }else{
                    $("#_PART_SELECPERSONA_RESULTADOS").html("<span class='TituloTexto10px'>NO SE ENCONTRARON REGISTROS QUE COINCIDIERAN CON LOS CRITERIOS DE BUSQUEDA</span>")
                }
            });
        }else{
            $("#_PART_SELECPERSONA_RESULTADOS").html("<span class='TituloTexto10px'>INTRODUZCA AL MENOS UN CRITERIO DE BUSQUEDA</span>");
        }
        return false;
    });
});