<%-- ***************************************************************************

editmap.jsp
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

<%@page import="com.bnmi.ourmap.model.*" %>
<%@page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>



<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu2.jsp" type="page" />

  <tiles:put name="content" type="string"  >

<script type="text/javascript" src="ajax.js"></script>
<script type="text/javascript" >
    function saveThis() {
        var forma = document.forms[0];
        forma.submit();
    }
</script>

<span class="Estilo6" >EDIT MAP TITLE AND DESCRIPTION</span>
<br/>
<br/>

<span class="Estilo1" >Please use the boxes below to edit your map's Title, Sub-title and Description. When you are finished making your changes, press "Save".
</span>
<br/>
<br/>
<bean:write name="error-message" ignore="true" />

<div id="editMapDiv" >

    <html:form method="post" action="editmap" >
    <input type='hidden' name='mapid' value='<bean:write name="mapid" scope="request" />' />
    <input type="hidden" name="formSubmited" value="yes" />


    <table id="detailsTable" width="62%" border="0" >
    <tr>
    <td width="12%" height="28"  class="Estilo8"><span class="Estilo8" >Map Title:</span></td>
    <td width="38%"  class="Estilo1">
        <html:text property="mapname" />
    </td>
    </tr>

    <tr>
    <td width="12%"  class="Estilo8"><span class="Estilo8" >Subtitle:</span></td>
    <td width="38%"  class="Estilo1">
    <html:text property="subtitle"  />
    </td>
    </tr>

    <tr>
    <td colspan="2" >
        <br/>
    </td>
    </tr>

    <tr>
    <td colspan="2"  class="Estilo8"><span class="Estilo8" >Description:</span></td>
    </tr>

    <tr>
    <td colspan="2"  class="Estilo1" >
        <html:textarea cols="45" rows="5" property="description" />
    </td>
    </tr>

    </table>

    <br/>
    <button type="button" onclick="document.location='mapconfiguration.do?mapid=<bean:write name="mapid" />'" >Cancel</button>
    <button type="button" onclick="saveThis()" >Save</button>

    </html:form>
                
                <logic:notPresent name="map" property="hotspotsMode" >
                <button type="button"  onclick="showKeywordTool()" >Next</button>
                </logic:notPresent>


                
</div>
                






  </tiles:put>

<tiles:put name="sidearea" type="string" />

</tiles:insert>



