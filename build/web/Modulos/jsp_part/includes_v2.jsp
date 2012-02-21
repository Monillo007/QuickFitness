<link rel="stylesheet" type="text/css" href="CSS/formStyle.css"/>
<link rel="stylesheet" type="text/css" href="CSS/botones.css"/>
<link rel="stylesheet" type="text/css" href="CSS/EstiloTab_v2.css"/>

<script type="text/javascript" src="Modulos/jquery/jquery.js"></script>
<script type="text/javascript" src="Modulos/jquery/jquery.validate.min.js"></script>
<script type="text/javascript" src="Modulos/jquery/jquery.validate.messages_es.js"></script>
<script type="text/javascript" src="Modulos/jquery/jquery.maskedinput.min.js" ></script>


<%
    String plugins = request.getParameter("p");
    if (plugins != null && plugins.length() > 0) {%>
<%if (plugins.contains(".calendar.")) {%>
<link rel="stylesheet" type="text/css" media="all" href="Modulos/calendario/calendar-blue.css" title="win2k-cold-1" />
<script type="text/javascript" src="Modulos/calendario/calendar_stripped.js"></script>
<script type="text/javascript" src="Modulos/calendario/lang/calendar-es.js" ></script>
<script type="text/javascript" src="Modulos/calendario/calendar-setup.js"></script>
<%}%>
<%if (plugins.contains(".ui.")) {%>
<link rel="stylesheet" type="text/css" media="all" href="CSS/ui-theme/jquery-ui-custom.css" title="jquery-ui" />
<script type="text/javascript" src="Modulos/jquery/jquery-ui-custom.min.js"></script>
<%}%>
<%if (plugins.contains(".gritter.")) {%>
<script type="text/javascript" src="Modulos/jquery/jquery.gritter.min.js"></script>
<link rel="stylesheet" type="text/css" href="CSS/jquery.gritter.css"/>
<%}%>
<%if (plugins.contains(".block.")) {%>
<script type="text/javascript" src="Modulos/jquery/jquery.blockUI.js"></script>
<%}%>
<%if (plugins.contains(".dump.")) {%>
<script type="text/javascript" src="Modulos/jquery/jquery.dump.js"></script>
<%}%>
<%if (plugins.contains(".alert.")) {%>
<script type="text/javascript" src="Modulos/jquery/apprise-1.5.full.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="CSS/apprise.css" title="apprise-dlg" />
<%}%>
<%if (plugins.contains(".calendarf.")) {%>
<link rel="stylesheet" type="text/css" media="screen" href="CSS/fullcalendar.css" title="fullcalendar" />
<link rel="stylesheet" type="text/css" media="print" href="CSS/fullcalendar.print.css" title="fullcalendar-print" />
<script type="text/javascript" src="Modulos/jquery/fullcalendar.min.js"></script>
<%}%>
<%if (plugins.contains(".print.")) {%>
<script type="text/javascript" src="Modulos/jquery/jquery.printElement.min.js"></script>
<%}%>
<%}%>
<script type="text/javascript" src="Modulos/jquery/jquery-extensions_v2.js"></script>
