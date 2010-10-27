<%-- ***************************************************************************

map_header.jsp
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
    Created on : 15-ago-2010, 9:22:07
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@ page import="com.bnmi.ourmap.control.EasyDelegate" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<link href="styles/easy.css" rel="stylesheet" type="text/css" />


<bean:define id="principal" name="<%=Constantes.USER%>"  scope="session" toScope="page" type="com.bnmi.ourmap.model.User" />
<bean:define id="project" name="project"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Project" />
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />


<%
  EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE) ;

  String myStatus = del.getStatus( principal, map.getMapid(), com.bnmi.ourmap.Constantes.MAP );
  int maxSize = 32;
  String projectName = project.getNombre();
  if ( projectName == null )
      projectName = "";

  if ( projectName.length() > maxSize )
      projectName = projectName.substring(0,maxSize) + "..." ;

  if ( request.getAttribute("no_edit") != null )
  {
      myStatus = "Owner";
  }



%>



<table width="100%" border="0" >
  <tr>
      
    <td width="226" >
        <img src="images/ourmap_logo.jpg" alt="OurMap" width="225" >
    </td>

    <td valign="top" >
      <table width="100%" border="0" >
          <tr>
            <td width="46%" >
                <table width="100%" border="0" >
              <tr>
                <td width="20%" class="Estilo25" ><b>MAP TITLE:</b></td>
                <td width="80%" >

<span class="Estilo25">
    <b><bean:write name="map" property="mapname" /></b>
</span>

                </td>
              </tr>
              <tr>
                <td class="Estilo25" ><b>SUB-TITLE:</b></td>
                <td>
<span class="Estilo25"><b><bean:write name="map" property="subtitle" /></b></span>
                </td>
              </tr>

              <tr>
                  <td colspan="2" style="padding-top: 3px">
                    <span class="Estilo1" >
                        Welcome <bean:write name="principal" property="nombre" />
                    </span>
                  </td>
              </tr>


            </table></td>
      <td width="20%" >
          <table border="0" width="100%" >
              <tr>
                  <td width="30%" >
<div align='left' >
                <span class="Estilo8">Parent Project: </span>


</div>
                  </td>
                  <td width="70%" class="Estilo1" >

            <%=projectName%>



                  </td>
              </tr>
              <tr>
                  <td>
                    <span class="Estilo8">Map Owner:</span>

                  </td>
                  <td class="Estilo1" >
                      <bean:write name="map" property="creatorName" ignore="true" />
                  </td>
              </tr>
              <tr>
                  <td>
                    <span class="Estilo8">My Status:</span>
                  </td>
                  <td class="Estilo1" >
                      <%=myStatus%>
                  </td>
              </tr>
          </table>


            </td>
        </tr>

        </table>
    </td>
  </tr>
  </table>
