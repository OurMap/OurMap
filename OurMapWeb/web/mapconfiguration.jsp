<%-- ***************************************************************************

mapconfiguration.jsp
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
    Created on : 08-mar-2010, 13:05:25
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<%@taglib  uri="/WEB-INF/cuesta.tld" prefix="cuesta" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu4.jsp" type="page" />
  <tiles:put name="content" type="string"  >

<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />
   <script type="text/javascript" src="ajax.js"></script>
   <script type="text/javascript" >
       function privacyChanged(src) {
           var forma = document.forms[0];
           forma.action = 'editmapprivacy.do' ;
           forma.submit();
       }

       function showPrivacy() {
          showLayer('mapViewingRightsDiv');
          showLayer('closePrivacyDiv');
       }

       function hidePrivacy() {
           hideLayer('mapViewingRightsDiv');
           hideLayer('closePrivacyDiv');
       }

       function initPage() {
           <% if ( request.getParameter("showTab") != null && request.getParameter("showTab").equalsIgnoreCase("privacy") ) { %>
                   showPrivacy();
           <% } %>
       }

       window.onload = initPage ;

   </script>

      <span class="Estilo6" >MAP CONFIGURATION OPTIONS</span>
      <br/>
      <br/>
      <form name="form1" method="post" action="#" >
          <input type="hidden" name="mapid" value="<bean:write name="map" property="mapid" />" />

          <table>

  <tr>
          <td width="483" height="25" >
              <span class="Estilo4">Edit Map Attributes:</span>          </td>
          <td width="63" >&nbsp;</td>
  </tr>

      <tr>
          <td height="25"  style="padding-left:18px"><a href="editmap.do?mapid=<bean:write name="map" property="mapid" />" ><span class="Estilo1">Title and Description</span></a></td>
          <td  style="padding-left:18px">&nbsp;</td>
      </tr>
      <!--
      <tr>
          <td height="25"  style="padding-left:18px">
              <a href="#" >
                  <span class="Estilo1">Background Map</span>
              </a>          </td>
          <td  style="padding-left:18px">&nbsp;</td>
      </tr>
      -->
      <tr>
          <td height="25"  style="padding-left:18px">
              <a href="editmapboundaries.do?mapid=<bean:write name="map" property="mapid" />" >
                  <span class="Estilo1">Map Boundaries</span>
              </a>          </td>
          <td  style="padding-left:18px">&nbsp;</td>
      </tr>
      <tr>
          <td height="25" style="padding-left:18px">
              <a href="editmaplayers.do?mapid=<bean:write name="map" property="mapid" />" >
                  <span class="Estilo1">Layers</span>
              </a>          </td>
          <td style="padding-left:18px">&nbsp;</td>
      </tr>




      <tr>
          <td height="25" style="padding-left:18px">
              <a href="editkeywords.do?mapid=<bean:write name="map" property="mapid" />" >
                  <span class="Estilo1">Keywords</span>
              </a>          </td>
          <td style="padding-left:18px">&nbsp;</td>
      </tr>


      <tr>
          <td height="25" style="padding-left:18px">
              <a href="orgcats.do?mapid=<bean:write name="map" property="mapid" />" >
                  <span class="Estilo1">Categories</span>
              </a>          </td>
          <td style="padding-left:18px">&nbsp;</td>
      </tr>
      
      <tr>
          <td height="25" style="padding-left:18px">
              <a href="editdisplaykeywords.do?mapid=<bean:write name="map" property="mapid" />" >
                  <span class="Estilo1">Keywords and Categories: Display Options</span>
              </a>          </td>
          <td style="padding-left:18px">&nbsp;</td>
      </tr>

      <tr>
          <td height="25" style="padding-left:18px">
              <a href="editicons.do?mapid=<bean:write name="map" property="mapid" />" >
                  <span class="Estilo1">Icons</span>
              </a>          </td>
          <td style="padding-left:18px">&nbsp;</td>
      </tr>

      <tr><td><br/></td></tr>

      <tr>
          <td height="25"  >
              <a href="maphsdisplaymode.do?mapid=<bean:write name="map" property="mapid" />" >
                  <span class="Estilo4">Hotspot Content: Display Options</span>
              </a>          </td>
          <td style="padding-left:18px">&nbsp;</td>
      </tr>

      <tr><td><br/></td></tr>

      <tr>
          <td height="25" class="Estilo4" >
              <a href="mapmembers.do?mapid=<bean:write name="map" property="mapid" />" >
                  Add/Manage Map Members
              </a></td>
          <td class="Estilo4" >&nbsp;</td>
      </tr>

      <tr><td><br/></td></tr>

      <tr>
          
          <td height="25" class="Estilo4">
              <a href="editmapprivacy.do?mapid=<bean:write name="map" property="mapid" />"  >
                  Viewing and Editing Rights
              </a>
          </td>
          
      </tr>

      <easy:ValidatePermission actionId="delete" elementId="<%=map.getMapid()%>" elementType="<%=com.bnmi.ourmap.Constantes.MAP%>" >
      <tr><td><br/></td></tr>
      <tr>
          <td height="25" class="Estilo4"><a href="deletemap.do?mapid=<bean:write name="map" property="mapid" />" >Delete This Map</a></td>
          <td class="Estilo4">&nbsp;</td>
      </tr>
      </easy:ValidatePermission>

      </table>
      
    </form>

  </tiles:put>

      <tiles:put name="sidearea" type="string" />
      
</tiles:insert>


