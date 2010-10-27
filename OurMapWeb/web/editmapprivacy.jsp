<%-- ***************************************************************************

editmapparivacy.jsp
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
    Created on : 09-mar-2010, 23:24:19
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
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
   </script>

   <span class="Estilo6" >
       SET VIEWING AND EDITING RIGHTS
   </span>
   <br/>
   <br/>
   <span class="Estilo1" >Use the following settings to determine who will be able to view this map (by linking to it directly from the parent project page), add hotspots to it, and edit those hotspots:
   </span>
   <br/>
   <br/>
      <span class="Estilo8" >
 Who should be allowed to view this map, besides the map's Owner and Co-owners?
      </span>
      <br/>
      <br/>
      <html:form action="editmapprivacy" method="post" >

          <input type="hidden" name="mapid" value="<bean:write name="map" property="mapid" />" />
          <input type="hidden" name="formSubmited" value="yes" />

          <html:radio value="0" property="privacy" />
              No One (even map Members cannot view it)
              <br/>
          <html:radio value="1" property="privacy" />
              Members only
              <br/>

          <html:radio value="2" property="privacy" />
              Everyone (all site visitors who can view the parent project can view this map)
              <br/>
              <br/>


      <span class="Estilo8" >

Who should be allowed to add new hotspots to this map, besides the map's Owner and Co-owners?
      </span>
      <br/>
      <br/>


          <html:radio value="0" property="editableHs" />
             No One (even map Members cannot add hotspots)
              <br/>
          <html:radio value="1" property="editableHs" />
              Members only
              <br/>

          <html:radio value="2" property="editableHs" />
              Everyone (all site visitors who can view this map can add hotspots)
              <br/>
              <br/>


      <span class="Estilo8" >

Who should be allowed to access the "Edit" tab for hotspots that have already been created on this map, besides this map's Owner and Co-owners? (The "Edit" tab allows users to add/edit hotspot media files, and change other hotspot settings)
      </span>
      <br/>
      <br/>


          <html:radio value="0" property="editableMedia" />
Hotspot Creators Only (only the person who created the hotspot can edit it)
              <br/>
          <html:radio value="1" property="editableMedia" />
All Members (Members of this map can freely edit hotspots created by others)
              <br/>

          <html:radio value="2" property="editableMedia" />
              Everyone (all site visitors who can view the map can change existing hotspots)
              <br/>

          <html:radio value="3" property="editableMedia" />
No One (not even the person who first created the hotspot can edit it)

              <br/>

              <br/>


              <button type="button" onclick="document.location='mapconfiguration.do?mapid=<bean:write name="map" property="mapid" />'" >Cancel</button>
              <button type="submit" >Save</button>
      </html:form>



  </tiles:put>
  <tiles:put name="sidearea" type="string" ></tiles:put>
</tiles:insert>


