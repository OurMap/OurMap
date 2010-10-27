<%-- ***************************************************************************

nosession.jsp
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
    Created on : 22-sep-2010, 17:38:00
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"  %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<!DOCTYPE html >
<html>
    <head>
    <meta http-equiv="Pragma" content="no-cache" >
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
    <meta http-equiv="X-UA-Compatible" content="chrome=1" >

<title>
    <logic:present name="map" scope="request" >
    <bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />
    <bean:write name="map" property="mapname" /> -
    </logic:present>
        OurMap
</title>

        <LINK REL="stylesheet" TYPE="text/css" HREF="styles/easy.css" >
        <link REL="SHORTCUT ICON" HREF="images/favico.png" >
</head>
<body>
<table width="100%" border="0">
  <tr>
    <td width="226" >
        <img src="images/ourmap_logo.jpg" alt="OurMap" width="225"  >   </td>
<td valign="top" >
    </td>
  </tr>
  </table>

<hr/>
<table width="100%" border="0" >
      <tr>
        <td width="225" height="380" valign="top">
        <!-- LEFT MENU HERE _______________________________________________________________ -->
        <!-- LEFT MENU ENDS HERE __________________________________________________________ -->
        </td>
        <td height="426" valign="top">
        <!-- CONTENT HERE _________________________________________________________________ -->

        <span class="Estilo9" >Sorry !</span>
        <br/>
        <br/>

        <span class="Estilo1" >
            There was an error with your request.
            
            Probably your session expired, or another user logged in and your session was cancelled.
            <br/>
            <br/>
            Please log in again and re-open your map to continue.
        </span>
        <!-- CONTENT ENDS HERE ____________________________________________________________ -->
        </td>
      </tr>
</table>
<jsp:include page="footer.jsp" />
</body>
</html>

