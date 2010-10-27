<%-- ***************************************************************************

index.jsp
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
<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/html"%>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<html>
<head><title>OurMap</title>
        <LINK REL="stylesheet" TYPE="text/css" HREF="styles/easy.css" />
        <script src="jshash-2.2/md5-min.js" type="text/javascript" ></script>
        <script type="text/javascript" >
            function doLogin() {
                var forma = document.forms[0];
                var pass = forma.apwd.value;
                forma.apwd.value = hex_md5( pass );
                forma.submit();
            }

        </script>
</head>
<body onLoad="initPage()" >
<p>&nbsp;</p>
<p align="center" class="Estilo1"><img src="images/ourmap_logo.jpg" alt="OurMap" ></p>

<br/>


<form id="form1" name="form1" method="post" action="logon.do">
    <div align="center" >
<table width="344" border="0" align="center" >
  <tr> 
    <td width="143">
        <span class="Estilo7">User</span>
    </td>
    <td width="90" >
        <input type="text" name="user" size="20" />
    </td>
  </tr>
  <tr>
    <td width="143">
        <span class="Estilo7">Password</span></td>
    <td width="90" >
      <input type="password" name="apwd" size="20" />
    </td>
  </tr>
</table>

<logic:present name="message" scope="request" >
    <br/>
<p align="center" class="Estilo9"><bean:write name="message" scope="request" /></p>
</logic:present>

<div align='center' >
  <p>&nbsp;    </p>
  <p>
      <button type="button" name="Submit" value="    Go    " onclick="doLogin()" >&nbsp;&nbsp;Go&nbsp;&nbsp;</button>
  </p>
</div>

<br/>
<span class="Estilo7" >Version <%=Constantes.VERSION%></span>

</div>
</form>
<p>&nbsp;</p>
<p>
  
  
</p>
<script type="text/javascript" >

        function parentExists()
        {
             return (parent.location == window.location)? false : true;
        }

        function initPage() {
            var isPopup = parentExists();
            //alert( isPopup );
            if ( isPopup )
                top.location.href = 'index.jsp';

        }


</script>


</body>
</html>