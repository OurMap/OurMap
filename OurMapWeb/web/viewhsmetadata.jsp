<%-- ***************************************************************************

viewhsmetadata.jsp
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

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<bean:define name="hs" id="hs" scope="request" toScope="page" type="Hotspot" />
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="popup_layout.jsp">



<tiles:put name="hs-subtitle" type="string" >
    <span class="Estilo6" >Hotspot Information</span>
</tiles:put>

<tiles:put name="hs-tabs" type="string" >
<div id="tabsJ" >
<ul style="padding-left:<bean:write name="offset" scope="request" />px" >
<logic:present name="hs" property="hsDescription" >
<li><a href="viewhsdescription.do?hsId=<bean:write name="hs" property="hsId" />"><span>Summary</span></a></li>
</logic:present>
<li><a href="viewhscontent.do?hsId=<bean:write name="hs" property="hsId" />"><span>Content</span></a></li>
<li id="current" ><a href="viewhsmetadata.do?hsId=<bean:write name="hs" property="hsId" />"><span>Info</span></a></li>

<easy:ValidatePermission actionId="edit-media" elementId="<%=hs.getHsId()%>" elementType="<%=com.bnmi.ourmap.Constantes.HOTSPOT%>" >
<li><a href="viewhsedit.do?hsId=<bean:write name="hs" property="hsId" />"><span>Edit</span></a></li>
</easy:ValidatePermission>



</ul>
</div>
</tiles:put>



<tiles:put name="hs-content" type="string" >

<bean:define name="hs" id="hs" scope="request" toScope="page" type="Hotspot" />
<bean:define name="hs" id="pos" property="hsPos" type="com.inga.utils.Point" />



<table width="450" border="0" >
  <tr>
    <td width="114"><span class="Estilo8">Hotspot Title:</span></td>
    <td width="157"><span class="Estilo1"><bean:write name="hs" property="hsName" /> </span></td>
  </tr>
  <tr>
    <td><span class="Estilo8">Date created:</span></td>
    <td><span class="Estilo1"><bean:write name="hs" property="created" format="MM/dd/yyyy" /></span></td>
  </tr>
  <tr>
    <td><span class="Estilo8">Time created:</span></td>
    <td><span class="Estilo1"><bean:write name="hs" property="created" format="h:mm:ss a Z" /></span></td>
  </tr>
  <tr>
    <td><span class="Estilo8">Created by:</span></td>
    <td><span class="Estilo1"><bean:write name="hs" property="creatorName" ignore="true" /></span></td>
  </tr>
  <tr>
    <td><span class="Estilo8">GPS Coordinates:</span></td>
    <td><span class="Estilo1">
        Longitude : <bean:write name="pos" property="x" /><br/>
        Latitude : <bean:write name="pos" property="y" />
        </span>
    </td>
  </tr>


  <tr>
    <td><span class="Estilo8">Layer:</span></td>
    <td><span class="Estilo1"><bean:write name="layer" property="layerName" /></span></td>
  </tr>


  <logic:present name="category" scope="request" >
  <tr>
    <td><span class="Estilo8">Category:</span></td>
    <td><span class="Estilo1"><bean:write name="category" property="catName" scope="request" /></span></td>
  </tr>
  </logic:present>

  <logic:present name="keyword" scope="request" >
  <tr>
    <td><span class="Estilo8">Keyword:</span></td>
    <td><span class="Estilo1">
            <bean:write name="keyword" property="kwValue" scope="request" />
        </span></td>
  </tr>
  </logic:present>

  <tr>
    <td><span class="Estilo8">Icon:</span></td>
    <td><span class="Estilo1"><img src="<easy:GetObjectTag id="<%=hs.getIconId()%>" />" /></span></td>
  </tr>


</table>

</tiles:put>

<tiles:put name="hs-footer" value="hs-footer.jsp" type="page"  >
</tiles:put>
</tiles:insert>


