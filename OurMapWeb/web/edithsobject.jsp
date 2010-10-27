<%-- ***************************************************************************

edithsobject.jsp
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
    Created on : 12-feb-2010, 12:19:04
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >
<!-- CONTENT HERE _________________________________________________________________ -->
<script type="text/javascript" src="ajax.js"></script>
<script type="text/javascript" >

    function saveThis() {
        
        var forma = document.forms['EditHsObjectForm'];

        <logic:notPresent name="hsId" >
        forma.action = 'editsessionobject.do' ;
        </logic:notPresent>

        if ( ! validarCadena( forma.mediaTitle.value) )
        {
            alert('Title is required');
            return;
        }

        forma.submit();
    }


</script>

    <span class="Estilo6" >EDIT MEDIA FILE</span>
    <br/>
    <br/>


<form name="EditHsObjectForm" method="post" action="edithsobject.do" charset="UTF-8" >
    <input type="hidden" name="objectId" value="<bean:write name="eo" property="objectId" />" />
    <input type="hidden" name="formSubmited" value="yes" />
    <span class="Estilo1" >
        Please edit the details of this added media file in the space below:
    </span>
<logic:equal name="eo" property="objType" value="1" >


    <br/>
    <br/>
<p class="Estilo1"><span class="Estilo8">Image Title<br/>
      <input type="text" name="mediaTitle" value="<bean:write name="eo" property="objName" scope="request" />" />
      <br/>
  <br/>

</span></p>
<p class="Estilo8">Image Caption:</p>
<p class="Estilo1">
  <textarea name="mediaComments" id="mediaComments" cols="50" rows="5"><bean:write name="eo" property="objDescription" scope="request" /></textarea>
</p>
</logic:equal>

<logic:equal name="eo" property="objType" value="2" >

    <br/>
    <br/>
<p class="Estilo1"><span class="Estilo8">Audio Title<br/>
      <input type="text" name="mediaTitle" value="<bean:write name="eo" property="objName" scope="request" />" />
      <br/>
  <br/>

</span></p>
<p class="Estilo8">Audio Caption:</p>
<p class="Estilo1">
  <textarea name="mediaComments" id="mediaComments" cols="50" rows="5"><bean:write name="eo" property="objDescription" scope="request" /></textarea>
</p>
</logic:equal>

<logic:equal name="eo" property="objType" value="3" >

    <br/>
    <br/>
<p class="Estilo1"><span class="Estilo8">Video Title<br/>
      <input type="text" name="mediaTitle" value="<bean:write name="eo" property="objName" scope="request" />" />
      <br/>
  <br/>

</span></p>
<p class="Estilo8">Video Caption:</p>
<p class="Estilo1">
  <textarea name="mediaComments" id="mediaComments" cols="50" rows="5"><bean:write name="eo" property="objDescription" scope="request" /></textarea>
</p>
</logic:equal>



<logic:equal name="eo" property="objType" value="5" >

    <br/>
    <br/>
<p class="Estilo1"><span class="Estilo8">Text Title<br/>
      <input type="text" name="mediaTitle" value="<bean:write name="eo" property="objName" scope="request" />" />
      <br/>
  <br/>

</span></p>
<p class="Estilo8">Text Body</p>
<p class="Estilo1">
  <textarea name="mediaComments" id="mediaComments" cols="50" rows="5"><bean:write name="eo" property="objDescription" scope="request" /></textarea>
</p>
</logic:equal>


<br/>

        <logic:present name="hsId" >
<button type="button" onclick="document.location='edithsmedia.do?hsId=<bean:write name="hsId" scope="request" />'" >Cancel</button>
<button type="button" onclick="saveThis()" >Save</button>
        </logic:present>

<logic:notPresent name="hsId" >
<button type="button" onclick="document.location='addnewmediahs.do'" >Cancel</button>
<button type="button" onclick="saveThis()" >Save</button>
</logic:notPresent>

</form>

<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>
  <tiles:put name="sidearea" type="string" ></tiles:put>
</tiles:insert>

