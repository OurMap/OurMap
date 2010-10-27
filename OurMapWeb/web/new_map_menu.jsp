<%-- ***************************************************************************

new_map_menu.jsp
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
<link href="styles/easy.css" rel="stylesheet" type="text/css" />
<script src="ajax.js" type="text/javascript" ></script>
<script type="text/javascript" >
    
    function cancelCreation() {
        if ( confirm('Data will be lost. Do you want to cancel map creation?') )
        {
            //executeAjax('newmap.do?status=10','',null);
            executeAjax('logout.do','',null);
            window.top.opener=null;
            window.close();

        }
    }

</script>

<bean:define id="principal" name="<%=Constantes.USER%>"  scope="session" toScope="page" type="com.bnmi.ourmap.model.User" />

<bean:define id="map" name="new_map"  scope="session" toScope="page" type="com.bnmi.ourmap.model.Map" />

<%
    float current = 0;
    float furthest = 0;
    if ( session.getAttribute(Constantes.FURTHEST_NEW_MAP_STATUS) != null )
        furthest = Float.parseFloat( (String) session.getAttribute(Constantes.FURTHEST_NEW_MAP_STATUS));
    if ( session.getAttribute(Constantes.NEW_MAP_STATUS) != null )
        current = Float.parseFloat( (String) session.getAttribute(Constantes.NEW_MAP_STATUS));
    String estilo = "Estilo1";
%>


<table width="181" border="0">
    <tr>
    <span class="Estilo4" >MAP CREATION<br/><br/></span>
    </tr>
  <tr>
    <% if ( furthest >= 0 ) { %>
    <td width="171" >
    <% if ( current == 0 ) { %>
    <span class="Estilo6" >Title and Description</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=0'" style="cursor:pointer" >Title and Description</div>
    <% } %>
    
    </td>
    <% } %>
  </tr>
  


  <tr>
    <% if ( furthest >= 3 ) { %>
    <td   >
    <% if ( current == 3 ) { %>
    <span class="Estilo6" >Map Boundaries</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=3'" style="cursor:pointer" >Map Boundaries</div>
    <% } %>

    </td>
    <% } %>
  </tr>
  <tr>
    <% if ( furthest >= 4 ) { %>
    <td width="171" >
    <% if ( current == 4 ) { %>
    <span class="Estilo6" >Map Layers</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=4'" style="cursor:pointer" >Map Layers</div>
    <% } %>

    </td>
    <% } %>
  </tr>

    <% if ( furthest >= 5 ) { %>
    <tr>
    <td>
    <span >Map Keywords</span>
    </td>
    </tr>
    <% } %>

    <% if ( furthest >= 5 ) { %>
    <tr>
    <td style="padding-left:10px" >
    <% if ( current == 5 ) { %>
    <span class="Estilo6" >Keyword Option</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=5'" style="cursor:pointer" >Keyword Option</div>
    <% } %>

    </td>
    </tr>
    <% } %>


<logic:equal name="map" property="hotspotsMode" value="1" >

  <tr>
    <% if ( furthest >= 6 ) { %>
    <td style="padding-left:10px" >
    <% if ( current == 6 ) { %>
    <span class="Estilo6" >Create Keywords</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=6'" style="cursor:pointer" >Create Keywords</div>
    <% } %>

    </td>
    <% } %>
  </tr>

    <% if ( furthest >= 6.4f ) { %>
  <tr>
    <td style="padding-left:10px" >
    <% if ( current == 6.4f ) { %>
    <span class="Estilo6" >Categories Option</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=6.4'" style="cursor:pointer;padding-right:10px" >Categories Option</div>
    <% } %>
    </td>
  </tr>
    <% } %>


    <logic:equal name="map" property="catsEnabled" value="t" >
  <tr>
    <% if ( furthest >= 6.5f ) { %>
    <td style="padding-left:10px" >
    <% if ( current == 6.5f ) { %>
    <span class="Estilo6" >Set Categories</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=6.5'" style="cursor:pointer;padding-right:10px" >Set Categories</div>
    <% } %>

    </td>
    <% } %> 
  </tr>
        </logic:equal>



</logic:equal>

<logic:notEqual name="map" property="hotspotsMode" value="3" >
    <% if ( furthest >= 6.6f ) { %>
  <tr>
    <td style="padding-left:10px" >
    <% if ( current == 6.6f ) { %>
    <span class="Estilo6" >Display Keywords</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=6.6'" style="cursor:pointer;padding-right:10px" >Display Keywords</div>
    <% } %>
    </td>
  </tr>
    <% } %>

</logic:notEqual>

  <tr>
    <% if ( furthest >= 7 ) { %>
    <td width="171" >
    <% if ( current == 7 ) { %>
    <span class="Estilo6" >Map Icons</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=7'" style="cursor:pointer" >Map Icons</div>
    <% } %>

    </td>
    <% } %>
  </tr>

  <logic:equal name="map" property="iconsMode" value="1" >
    <% if ( furthest >= 7.1f ) { %>
    <tr>
    <td width="171" style="padding-left:10px" >
    <% if ( current == 7.1f ) { %>
    <span class="Estilo6" >Layer Icons</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=7.1'" style="cursor:pointer" >Icon Layers</div>
    <% } %>
    </td>
  </tr>
    <% } %>
  </logic:equal>

  <logic:equal name="map" property="hotspotsMode" value="1" >
      <logic:equal name="map" property="catsEnabled" value="t" >
  <logic:equal name="map" property="iconsMode" value="2" >
    <% if ( furthest >= 7.2f ) { %>
    <tr>
    <td width="171" style="padding-left:10px" >
    <% if ( current == 7.2f ) { %>
    <span class="Estilo6" >Category Icons</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=7.2'" style="cursor:pointer" >Category Icons</div>
    <% } %>
    </td>
  </tr>
    <% } %>
  </logic:equal>
      </logic:equal>

  <logic:equal name="map" property="iconsMode" value="3" >
    <% if ( furthest >= 7.3f ) { %>
    <tr>
    <td width="171" style="padding-left:10px" >
    <% if ( current == 7.3f ) { %>
    <span class="Estilo6" >Keyword Icons</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=7.3'" style="cursor:pointer" >Keyword Icons</div>
    <% } %>
    </td>
  </tr>
    <% } %>
  </logic:equal>
  </logic:equal>


  <tr>
    <% if ( furthest >= 8 ) { %>
    <td width="171" >
    <% if ( current == 8 ) { %>
    <span class="Estilo6" >Save</span>
    <% } else {%>
        <div onclick="location='newmap.do?status=8'" style="cursor:pointer" >Save</div>
    <% } %>

    </td>
    <% } %>
  </tr>

</table>
<p>&nbsp;</p>

<p class="Estilo4">
    <a href="javascript:cancelCreation()" >Cancel Map Creation</a>
    <br/>
</p>
