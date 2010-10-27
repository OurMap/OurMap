<%-- ***************************************************************************

view_profile.jsp
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

<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.inga.utils.*" %>
<%@ page import="com.bnmi.ourmap.control.EasyDelegate" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%
   EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);
   User principal = (User) session.getAttribute(Constantes.USER);
%>

<tiles:insert template="layout.jsp">
  <tiles:put name="header" value="header.jsp" type="page" />
  <tiles:put name="leftMenu" value="project_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >


            

              <p>
              <!-- CONTENT HERE ----------------------------------------------------------------- -->
              <span class="Estilo4"><bean:write name="project" property="nombre" /></span></p>
            <p class="Estilo1"><bean:write name="project" property="descr" /></p>

            <br/>
            <p class="Estilo4">Maps</p>
            <br/>
            <table width="200" border="0">
                <logic:iterate id="map" name="maps" scope="request" type="com.bnmi.ourmap.model.Map" >
<%
   ReturnMessage r = del.hasPermission( principal, "view", map.getMapid(), com.bnmi.ourmap.Constantes.MAP );
   boolean canView = r.isSuccess();
%>
              <tr> 
                <td>
                    <a href="javascript:
                       <% if ( canView ) { %>
                            document.location = 'viewmap.do?mapid=<bean:write name="map" property="mapid" />';
                       <% } else { %>
                            alert('Sorry!\n\nYou don\'t have permission to do that!');
                       <% } %>
                        "
                    >
                    <bean:write name="map" property="mapname" /></a>

                </td>
              <td>

                </td>
              </tr>
                
                </logic:iterate>
            </table>
  </tiles:put>
</tiles:insert>
