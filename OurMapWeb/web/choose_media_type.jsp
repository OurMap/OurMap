<%-- ***************************************************************************

choose_media_type.jsp
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
    Created on : 14-abr-2010, 16:46:40
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<bean:define id="project" name="project"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Project" />
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />


<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />

  <logic:present name="hsId" scope="request" >
  <tiles:put name="leftMenu" value="edithsmenu.jsp" type="page" />
  </logic:present>

  <logic:notPresent name="hsId" scope="request" >
  <tiles:put name="leftMenu" value="newhs_left_menu.jsp" type="page" />
  </logic:notPresent>

  <tiles:put name="content" type="string"  >
   <script type="text/javascript" src="ajax.js"></script>
   <script type="text/javascript" >
       
   </script>

      <span class="Estilo6" >CHOOSE WHAT KIND OF MEDIA TO ADD</span>
      <br/>
      <br/>
      <span class="Estilo1" >
          Please select what kind of media file you want to add to your hotspot, then press "Next" 
      </span>

      <form name="MediaTypeForm" action="choosemediatype.do" method="post" >
          <input type="hidden" name="formSubmited" value="formSubmited" />
          <input type="hidden" name="mapid" value="<bean:write name="map" property="mapid" ignore="true" />" />
          <input type="hidden" name="hsId" value="<bean:write name="hsId" ignore="true"/>" />
    <br/>
        <p class="Estilo1">
          <input name="mediaType" type="radio" id="image" value="1" checked="true" />
          Image<br/>
          <input type="radio" name="mediaType" id="audio" value="2" />
          Audio<br/>
          <input type="radio" name="mediaType" id="text" value="5" />
          Text<br/>
          <input type="radio" name="mediaType" id="video" value="3" />
          Video<br/>
        </p>
        <br/>
        <logic:notPresent name="hsId" >
        <button type="button" onclick="document.location='addnewmediahs.do'" >Cancel</button>
        <button type="submit" >Next</button>
        </logic:notPresent>

        <logic:present name="hsId" >
            <button type="button" onclick="document.location='edithsmedia.do?hsId=<bean:write name="hsId" />'" >Cancel</button>
            <button type="submit" >Next</button>
        </logic:present>

      </form>
  </tiles:put>
  <tiles:put name="sidearea" type="string" >
      
  </tiles:put>
</tiles:insert>


