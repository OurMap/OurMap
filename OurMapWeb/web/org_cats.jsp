<%-- ***************************************************************************

org_cats.jsp
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

<%--
    Created on : 19-ene-2010, 0:58:38
--%>

<%@ page  pageEncoding="UTF-8" %>
<!DOCTYPE html >
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<link href="styles/easy.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="dragrepis/repis.css" type="text/css" media="screen" />
<script type="text/javascript" src="dragrepis/drag.js"></script>

<bean:define id="keymap" name="keymap"  scope="request" toScope="page" type="LinkedHashMap<Category,List<Keyword>>" />

<!-- tables inside this DIV could have draggable content -->
<div id="drag">

<logic:notEmpty name="keymap" scope="request" >
<span class="Estilo1" >
    Drag and drop the keywords into the corresponding categories:
</span>
<br/>
<br/>

    
<table id="table1">

<tr>
<logic:iterate name="keymap" id="k" type="java.util.Map.Entry<Category,List<Keyword>>" scope="request" >
    <bean:define name="k" property="key" id="cat" type="Category" />
    <bean:define name="k" property="value" id="keywords" type="List<Keyword>" />
    <td  class="mark" id="<bean:write name="cat" property="catId"/>" >
        <b><bean:write name="cat" property="catName"  /></b>
        <a href="#" onclick="deleteCategory(<bean:write name="cat" property="catId"/>)" >Remove</a>
    </td>
</logic:iterate>
</tr>

<tr>
<logic:iterate name="keymap" id="k" type="java.util.Map.Entry<Category,List<Keyword>>" scope="request" >
    <bean:define name="k" property="key" id="cat" type="Category" />
    <bean:define name="k" property="value" id="keywords" type="List<Keyword>" />
    <td>
        <logic:iterate name="keywords" id="key" type="Keyword" >
            <div id="<bean:write name="key" property="kwId" />" class="drag t1">
            <bean:write name="key" property="kwValue" />
            </div>
        </logic:iterate>
    </td>
</logic:iterate>
</tr>
</table>

</logic:notEmpty>
    
<table id="nocatstable"  >
    <tr>
        <td id="null" class="mark"  >
            <b>Keywords not in a category</b>
        </td>
    </tr>
    <tr>
        <td>
        <logic:iterate name="nocat" id="key" type="Keyword" >
            <div id="<bean:write name="key" property="kwId" />" class="drag t1">
            <bean:write name="key" property="kwValue" />
            </div>
        </logic:iterate>

        </td>
    </tr>
</table>

<div id="obj_new"></div>
<div id="dragInfoDiv" style="display:none" >

<table id="table3">
<colgroup><col width="100"/><col width="100"/><col width="100"/><col width="100"/><col width="100"/></colgroup>
<tr style="background-color: #eee">
<td id="message" class="mark" title="You can not drop here">Table3</td>
</tr>
</table>
</div>

</div>

