<%-- ***************************************************************************

list_projects.jsp
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

<tiles:insert template="layout.jsp">
  <tiles:put name="header" value="header.jsp" type="page" />
  <tiles:put name="leftMenu" value="leftMenu.jsp" type="page" />
  <tiles:put name="content" type="string"  >

<script type="javascript" >
    function deleteProject(pid) {

        if ( !confirm('Project will be deleted. Continue?') )
            return;

        var forma = document.forms['DeleteProjectForm'];
        forma.pid.value = pid;
        forma.submit();
    }
</script>


      <span class="Estilo6" >Current OurMap Projects</span>
      <br/>
      <br/>
<form name="DeleteProjectForm" method="post" action="deleteProject.do" >
    <input type="hidden" name="pid" />
</form>
<table width="74%" border="0">
  <tr>
    <td width="21%"><span class="Estilo8" >Name</span></td>
    <td width="43%"><span class="Estilo8" ></span></td>
    <td width="18%"><span class="Estilo8" ></span></td>
    <td width="9%"></td>
    <td width="9%"></td>
  </tr>

        <logic:iterate name="projects" scope="request" type="Project" id="p" >
  <tr>
    <td><a href='viewproject.do?pid=<bean:write name="p" property="id" />' >
    <bean:write name="p" property="nombre"  />
        </a></td>
    <td><bean:write name="p" property="descr"  /></td>
    <td><bean:write name="p" property="ownerName"  /></td>
    <td>
    </td>
    <td>
        <easy:ValidatePermission actionId="overlord" >
        <a href='javascript:deleteProject("<bean:write name="p" property="id"  />")' >Delete</a>
        </easy:ValidatePermission>
    </td>
  </tr>
        </logic:iterate>
</table>






  </tiles:put>
</tiles:insert>



