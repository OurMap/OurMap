<%-- ***************************************************************************

view_user.jsp
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

<jsp:include page="header.jsp" />
          
        <table width="100%" height="100%" border="0">   

        <table width="100%" height="100%" border="0">

          <tr> 
            <td width="182" height="426" valign="top">
			<jsp:include page="leftMenu.jsp" />
			</td>
            <td height="426"><table width="62%" border="0">
              <tr>
                <td width="38%" class="Estilo1">Id</td>
                <td width="62%" class="Estilo1"><bean:write name="usuario" property="id" /></td>
              </tr>
              <tr>
                <td class="Estilo1">Password</td>
                <td class="Estilo1"><bean:write name="usuario" property="pwd" /></td>
              </tr>
              <tr>
                <td class="Estilo1">Name</td>
                <td class="Estilo1"><bean:write name="usuario" property="nombre" /></td>
              </tr>
              <tr>
                <td class="Estilo1">Last Name </td>
                <td class="Estilo1"><bean:write name="usuario" property="lastname" /></td>
              </tr>
              <tr>
                <td class="Estilo1">Description</td>
                <td class="Estilo1"><bean:write name="usuario" property="descr" /></td>
              </tr>
            </table>
            <p>Roles</p>
            <table width="36%" border="0">
              <tr>
                <td>&nbsp;</td>
                <td class="Estilo7">admin</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td class="Estilo8">participant</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td class="Estilo7">visitor</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td class="Estilo7">overlord</td>
              </tr>
            </table>
            <p>&nbsp;</p></td>
          </tr>
          <tr>
            <td width="182">&nbsp;</td>
            <td height="37">&nbsp;</td>
          </tr>
    </table>
<jsp:include page="footer.jsp" />