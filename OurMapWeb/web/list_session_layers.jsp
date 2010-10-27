<?xml version="1.0" encoding="utf-8"?>

<%-- ***************************************************************************

list_session_layers.jsp
Version: 1.0

********************************************************************************
Original Authors:
Manuel Cuesta, lead programmer <camilocuesta@hotmail.com>
Angus Leech, lead designer <alpinefabulist@yahoo.com>
Full credits at: <http://www.ourmapmaker.ca/content/about-ourmap/credits>  

For questions or comments please contact us at: [ourmap@ourmapmaker.ca]
********************************************************************************

OurMap is Copyright (c) 2010, The Banff Centre <ourmap@ourmapmaker.ca>
All rights reserved.

Published under the terms of the new BSD license.

See  <www.ourmapmaker.ca/content/about-ourmap>  for more information about the 
OurMap software and the license.

Full sourcecode, documentation and license info is also available here:
http://github.com/OurMap/OurMap

LICENSE:

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of The Banff Centre nor the names of its contributors may be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


********************************************************************************
Revision History / Change Log:

Version 1.0 released Oct.2010

********************************************************************************
Notes:

*************************************************************************** --%>


<%@page contentType="text/xml"%>
<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<link href="styles/easy.css" rel="stylesheet" type="text/css" />

<table width="629" border='0' id="layersTable" >
<tr>

<td width="367" class="Estilo8">Layers</td>
<td width="36" class="Estilo8"></td>
<td width="52" class="Estilo8"></td>
<td width="69" class="Estilo8"></td>
<td width="83" class="Estilo8"></td>

</tr>
<% int cont = 1; %>
<% int numLayers = ((java.util.List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS)).size(); %>

<logic:empty name="<%=Constantes.NEW_MAP_LAYERS%>" >
    <span class="Estilo1" >Map has no layers</span>
</logic:empty>

<logic:iterate name="<%=Constantes.NEW_MAP_LAYERS%>" id="layer" type="Layer" scope="session" >

<tr>

    <td><bean:write name="layer" property="layerName" /></td>
    

    <td>
        <a href="#" onclick='showEditLayer(this)' >Edit</a>
    </td>
    <td>
        <a href="#" onclick="deleteLayer(this)" >Delete</a>
    </td>
    <td>
        <% if ( cont > 1 ) {%>
        <a href="#" onclick='moveUp(this)' >Move Up</a>
        <% } %>
    </td>
    <td>
        <% if ( cont < numLayers ) {%>
        <a href="#" onclick='moveDown(this)' >Move Down</a>
        <% } %>
    </td>
</tr>
<% cont++; %>
</logic:iterate>

</table>




