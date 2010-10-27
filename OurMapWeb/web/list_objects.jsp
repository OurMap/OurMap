<%-- ***************************************************************************

list_objects.jsp
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
    Created on : 27-ene-2010, 15:07:00
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout.jsp">
  <tiles:put name="header" value="header.jsp" type="page" />
  <tiles:put name="leftMenu" value="leftMenu.jsp" type="page" />
  <tiles:put name="content" type="string"  >
<!-- CONTENT HERE _________________________________________________________________ -->

<script src="ajax.js"></script>
<script language="javascript" >
    function changeType( src ) {
        //var type = getSelectedValue( src );
        var forma = document.forms['ListObjectsForm'];
        forma.submit();
        //alert( type );
    }
</script>


OBJECTS IN THE SYSTEM
<br/>
<logic:present name="message" scope="request" >
    <bean:write name="message" scope="request" />
    <br/>
</logic:present>
<form name="NewObjectForm" action="newobject.do" method="post" enctype="multipart/form-data" >
    <input type="file" name="file" />

      <select name="newType"  >
                <option value="1" >Image</option>
                <option value="2" >Audio</option>
                <option value="3" >Video</option>
                <option value="4" >Icon</option>
            </select>
    <input type="submit" value="Add Object" />
</form>
<br/>
<form name="ListObjectsForm" action="listobjects.do" method="post" >

<table>
    <tr>
      <td width="45">ID</td>
        <td width="286">
            Name        </td>
        <td width="81">
      <select name="type" onchange="changeType( this )" >
                <option value="null" >All</option>
                <option value="1" >Image</option>
                <option value="2" >Audio</option>
                <option value="3" >Video</option>
                <option value="4" >Icon</option>
            </select>        </td>
        <td width="64">&nbsp;</td>
    </tr>

<logic:iterate name="objects" type="EasyObject" id="obj" scope="request" >
    <tr>
      <td><bean:write name="obj" property="objectId" /></td>
        <td>
            <a href="<easy:GetObjectTag id="<%=obj.getObjectId()%>" />" >
            <bean:write name="obj" property="objName" />
            </a>        </td>
        <td>
            <bean:write name="obj" property="typeName" />        </td>
        <td><a href="deleteobject.do?objectId=<bean:write name="obj" property="objectId" />" >Delete</a></td>
    </tr>
</logic:iterate>

<script typte="text/javascript" >
    var selected = <bean:write name="selected" scope="request" />;
    var forma = document.forms['ListObjectsForm'];
    forma.type.selectedIndex = selected
</script>
</table>
</form>




<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>
</tiles:insert>

