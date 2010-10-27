<%-- ***************************************************************************

edit_profile.jsp
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
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib  uri="/WEB-INF/cuesta.tld" prefix="cuesta" %>

<tiles:insert template="layout.jsp">
  <tiles:put name="header" value="header.jsp" type="page" />
  <tiles:put name="leftMenu" value="admin_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >


            
<bean:define id="usuario" name="usuario" scope="request" type="User" />
<script language="javascript" >

  	function back() {
	   document.location.href = 'viewprofile.do?userid=<bean:write name="usuario" property="id" />' ;
	}

	function save() {
	   document.forms[0].submit();
	}

</script>
           <html:form method="post" action="editprofile.do" >
    <input type="hidden" name="userid" value="<bean:write name="usuario" property="id" scope="request" />" />

    <span class="Estilo9" ><bean:write name="error-message" ignore="true" /></span>
    <table width="62%" border="0">

      <tr>
        <td width="38%" height="30" class="Estilo1">Id</td>
        <td width="62%" height="30" class="Estilo1"><bean:write name="usuario" property="id" scope="request" /></td>
      </tr>
      <tr>
        <td height="30" class="Estilo1">Password</td>
        <td height="30" class="Estilo1"><html:text property="pwd" /></td>
      </tr>
      <tr>
        <td height="30" class="Estilo1">Name</td>
        <td height="30" class="Estilo1"><html:text property="nombre"  /></td>
      </tr>
      <tr>
        <td height="30" class="Estilo1">Last Name </td>
        <td height="30" class="Estilo1">
            <html:text property="lastname" />
        </td>
      </tr>
      <tr>
        <td height="30" class="Estilo1">Status </td>
        <td height="30" class="Estilo1">
        <cuesta:combo list="roles" addNull="false" label="rolname" value="rol" name="rol" selected="selectedRol" />
        </td>
      </tr>

    </table>



<br/>

                <input type="hidden" name="formSubmited" value="yes" />
                <button type="button" onclick="back()" >Cancel</button>
                <button type="button" onclick="save()">Save</button>
                <p>&nbsp;</p>
    </html:form>

  </tiles:put>
</tiles:insert>
