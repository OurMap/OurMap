<%-- ***************************************************************************

newhs_left_menu.jsp
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

<bean:define id="p1" name="<%=Constantes.USER%>"  scope="session" toScope="page" type="com.bnmi.ourmap.model.User" />
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />
<bean:define id="furthest" name="<%=Constantes.FURTHEST_NEWHS_STATUS%>" scope="session" toScope="page" />

<script type="text/javascript" >

    var currentStatus = <bean:write name="<%=Constantes.NEWHS_STATUS%>" scope="session" /> ;

    function cancelHotspot() {

        if ( !confirm('Hotspot information will be lost. Continue?') )
            return;

        document.location = 'newhotspot.do?op=cancel&formSubmited=yes&mapid=<bean:write name="map" property="mapid" />' ;
        
    }

    function gotostep( step ) {
       if ( currentStatus == 6 )
       {
           if ( confirm('Unsaved data will be lost. Continue?') )
               location = step ;
       }
       else
           location = step ;

    }
    
</script>




<table width="100%" border="0">


      <tr>
        <td class="Estilo4">
            HOTSPOT CREATION
        </td>
      </tr>

    <tr>
        <td><br/></td>
    </tr>

    <logic:greaterEqual name="<%=Constantes.FURTHEST_NEWHS_STATUS%>" value="1" scope="session" >
        <tr><td>
        <logic:equal name="<%=Constantes.NEWHS_STATUS%>" value="1" scope="session" >
            <span class="Estilo6"  >
        </logic:equal>
        <logic:notEqual name="<%=Constantes.NEWHS_STATUS%>" value="1" scope="session" >
            <span onclick="gotostep('newhotspot.do?status=1')" style=" cursor: pointer" >
        </logic:notEqual>
            Create Hotspot
        <logic:notEqual name="<%=Constantes.NEWHS_STATUS%>" value="1" scope="session" >
            </span>
        </logic:notEqual>
        <logic:equal name="<%=Constantes.NEWHS_STATUS%>" value="1" scope="session" >
            </span>
        </logic:equal>
            </td></tr>
    </logic:greaterEqual>

    <logic:equal  name="map" property="displayMode" value="<%=String.valueOf(com.bnmi.ourmap.Constantes.MAP_HS_CONTENT_FLEXIBLE)%>" >
    <logic:greaterEqual name="<%=Constantes.FURTHEST_NEWHS_STATUS%>" value="2" scope="session" >
        <tr><td>
        <logic:equal name="<%=Constantes.NEWHS_STATUS%>" value="2" scope="session" >
            <span class="Estilo6"  >
        </logic:equal>
        <logic:notEqual name="<%=Constantes.NEWHS_STATUS%>" value="2" scope="session" >
            <span onclick="gotostep('newhotspotdisplaymode.do')" style=" cursor: pointer" >
        </logic:notEqual>
            Display Option
        <logic:notEqual name="<%=Constantes.NEWHS_STATUS%>" value="2" scope="session" >
            </span>
        </logic:notEqual>
        <logic:equal name="<%=Constantes.NEWHS_STATUS%>" value="2" scope="session" >
            </span>
        </logic:equal>
            </td></tr>
    </logic:greaterEqual>
    </logic:equal>

    <logic:greaterEqual name="<%=Constantes.FURTHEST_NEWHS_STATUS%>" value="3" scope="session" >
        <tr>
            <td>
            Add Media
            </td>
        </tr>
        <tr><td style="padding-left: 10px">
        <logic:equal name="<%=Constantes.NEWHS_STATUS%>" value="3" scope="session" >
            <span class="Estilo6"  >
        </logic:equal>
        <logic:notEqual name="<%=Constantes.NEWHS_STATUS%>" value="3" scope="session" >
            <span onclick="gotostep('addcontentnew.do')" style=" cursor: pointer" >
        </logic:notEqual>
            Choose Option
        <logic:notEqual name="<%=Constantes.NEWHS_STATUS%>" value="3" scope="session" >
            </span>
        </logic:notEqual>
        <logic:equal name="<%=Constantes.NEWHS_STATUS%>" value="3" scope="session" >
            </span>
        </logic:equal>
            </td></tr>
    </logic:greaterEqual>


    <logic:greaterEqual name="<%=Constantes.FURTHEST_NEWHS_STATUS%>" value="4" scope="session" >
        <tr><td style="padding-left:10px">
        <logic:greaterEqual name="<%=Constantes.NEWHS_STATUS%>" value="4" scope="session" >
            <span class="Estilo6"  >
        </logic:greaterEqual>
        <logic:notEqual name="<%=Constantes.NEWHS_STATUS%>" value="4" scope="session" >
            <span onclick="gotostep('addnewmediahs.do')" style=" cursor: pointer" >
        </logic:notEqual>
            Add/Edit Content
        <logic:notEqual name="<%=Constantes.NEWHS_STATUS%>" value="4" scope="session" >
            </span>
        </logic:notEqual>
        <logic:greaterEqual name="<%=Constantes.NEWHS_STATUS%>" value="4" scope="session" >
            </span>
        </logic:greaterEqual>
            </td></tr>
    </logic:greaterEqual>



    <logic:greaterEqual name="<%=Constantes.FURTHEST_NEWHS_STATUS%>" value="7" scope="session" >
        <tr><td>
        <logic:equal name="<%=Constantes.NEWHS_STATUS%>" value="7" scope="session" >
            <span class="Estilo6"  >
        </logic:equal>
        <logic:notEqual name="<%=Constantes.NEWHS_STATUS%>" value="7" scope="session" >
            <span onclick="gotostep('newhotspotdisplaymode.do')" style=" cursor: pointer" >
        </logic:notEqual>
            Display Option
        <logic:notEqual name="<%=Constantes.NEWHS_STATUS%>" value="7" scope="session" >
            </span>
        </logic:notEqual>
        <logic:equal name="<%=Constantes.NEWHS_STATUS%>" value="7" scope="session" >
            </span>
        </logic:equal>
            </td></tr>
    </logic:greaterEqual>


    <tr>
        <td><br/></td>
    </tr>

      <tr>
        <td class="Estilo4">
            <a href="javascript:cancelHotspot()" >
            
            Cancel Hotspot Creation
            
            </a>
        </td>
      </tr>




</table>