<%-- ***************************************************************************

newmap_7.jsp
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
    Created on : 26-ene-2010, 12:05:31
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_hint.jsp">
  <tiles:put name="header" value="newmap_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="new_map_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >
<!-- CONTENT HERE _________________________________________________________________ -->
<bean:define id="map" name="new_map"  scope="session" toScope="page" type="com.bnmi.ourmap.model.Map" />
<script src="ajax.js"></script>
<script language="javascript" >
    function onIconsOptionSelected() {
        var forma = document.forms['IconsForm'];
        var iconsMode = getCheckedValue( forma.iconsMode );

        iconsMode = parseInt( iconsMode );
        switch ( iconsMode )
        {
            // Layers
            case 1:
                forma.status.value = "7.1";
                break;
            // Keywords and cats
            case 2:
                forma.status.value = "7.2";
                break;
            case 3:
                forma.status.value = "7.3";
                break;
            default:
                forma.status.value = "8";

        }
        //alert( iconsMode );
        forma.submit();

    }
</script>
<link href="styles/easy.css" rel="stylesheet" type="text/css">


<form name="IconsForm" action="newmap.do" method="post" >
    <p>
      <input type="hidden" name="status" />
      <span class="Estilo6" >ICON SETTINGS</span>
      
      <br/>
      </p>
    <p>&nbsp;</p>
    <p align="justify" class="Estilo1">Map Icons are the symbols that mark the location of hotspots on your map. There are several possible settings for Icons.
        </p>
    <p align="justify"><br/>
      <span class="Estilo1" >
          <span class="Estilo4" >Please choose one of the following options:</span> <br/>
        <input type="radio" name="iconsMode" value="1" >
        Assign icons to map Layers
        </input>
        <br/>
        <logic:equal name="map" property="hotspotsMode" value="1" >


          <input type="radio" name="iconsMode" value="3" >
          Assign icons to Keywords
          </input>
          <br/>

          <logic:equal name="map" property="catsEnabled" value="t" >
            <input type="radio" name="iconsMode" value="2" >
            Assign icons to Categories
            </input>
            <br/>
          </logic:equal>
          

        </logic:equal>
        
        <input type="radio" name="iconsMode" value="4" >
        Allow user to choose their own icon for each hotspot they create<br/>
      </span>
    </p>
<br/>

        <br/>
        
    <button type="button" onclick="document.location='newmap.do?nav=back'" >Back</button>
<button type="button" onclick="onIconsOptionSelected()">Next</button>
</form>

<script language="javascript" >
    var forma = document.forms['IconsForm'];
    var selected = 0;

    <logic:present name="map" property="iconsMode" >
        selected = <bean:write name="map" property="iconsMode" />;
    </logic:present>

    var size = forma.iconsMode.length;
    var selectedIndex = 0;
    for ( var i = 0; i < size; i++ )
    {
        var value = forma.iconsMode[i].value;
//        alert ( value );
        if ( value == selected )
        {
            selectedIndex = i;
            break;
        }
    }


    forma.iconsMode[selectedIndex].checked = 'checked';


</script>
<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>
  <tiles:put name="hints" type="string" >


• If Icons are assigned to Layers, then all of the hotspots in a map Layer will have the same Icon.
<br/><br/>
• If Icons are assigned to Keywords, then all of the hotspots with a particular Keyword will have the same Icon.
<br/><br/>
• If Icons are assigned to Categories, then all of the hotspots with Keywords belonging to that Category will have the same Icon.
<br/><br/>
• Or, you can leave icons "open" and allow users to choose a unique Icon for every new hotspot they create.


  </tiles:put>
</tiles:insert>

