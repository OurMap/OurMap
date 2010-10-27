<%-- ***************************************************************************

add_content_new.jsp
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

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />

  <logic:equal name="mapMode" value="newHs" >
  <tiles:put name="leftMenu" value="newhs_left_menu.jsp" type="page" />
  </logic:equal>

  <logic:notEqual name="mapMode" value="newHs" >
  <tiles:put name="leftMenu" value="newhs_left_menu.jsp" type="page" />
  </logic:notEqual>

  <tiles:put name="content" type="string"  >
<!-- CONTENT HERE _________________________________________________________________ -->
<link href="styles/easy.css" rel="stylesheet" type="text/css">
<link href="styles/nifty.css" rel="stylesheet" type="text/css">
<script src="ajax.js"></script>
<script language="javascript" >
    function onSelected() {
        
        var forma = document.forms['form1'];

        var opt = parseInt( getCheckedValue(forma.hotNow) );
        var nextButton = document.getElementById('nextButton');
        
        switch( opt )
        {
            case 1:
                forma.action = 'addnewmediahs.do';
                nextButton.innerHTML = 'Next' ;
                break;
            case 2:
                forma.action = 'SaveMapFeature.do';
                nextButton.innerHTML = 'Save Hotspot' ;
                break;
        }
    }

    function next() {
        var forma = document.forms['form1'];
        forma.submit();
    }

</script>

<p><span class="Estilo6" >ADD MEDIA TO YOUR HOTSPOT</span>    </p>
<p><br/>
    </p>
    <div align="justify" class="Estilo1" style="width:600px">
If you like, you can add some media content to your hotspot now. Or, you can wait and add content to your hotspot at any time in the future, by using the Edit tab on your hotspot’s pop-up window. 
    </div>
<br/>
<form name="form1" method="post" action="addnewmediahs.do" >
  <p><span class="Estilo1">Do you want to add media to your hotspot now? <br/>
  </span></p>
  <p> <span class="Estilo1">
    <label>
        <input type="radio" name="hotNow" value="1" onchange="onSelected()" >
    </label>
    Yes</span></p>
  <p> <span class="Estilo1">
    <label>
    <input type="radio" name="hotNow" value="2" onchange="onSelected()" >
    </label>
    No</span></p>
  <p>&nbsp;</p>
  <p>
    <logic:equal  name="map" property="displayMode" value="<%=String.valueOf(com.bnmi.ourmap.Constantes.MAP_HS_CONTENT_FLEXIBLE)%>" >
        <button type="button" onclick="document.location='newhotspotdisplaymode.do';" >Back</button>
    </logic:equal>
    <logic:notEqual  name="map" property="displayMode" value="<%=String.valueOf(com.bnmi.ourmap.Constantes.MAP_HS_CONTENT_FLEXIBLE)%>" >
        <button type="button" onclick="document.location='newhotspot.do';" >Back</button>
    </logic:notEqual>
        <button id="nextButton" type="button" onclick="next()"  >Next</button>
  </p>
  <p></p>
</form>
<p><br/>
</p>
<p><span class="Estilo1"><br/>
  <br/>
  
  
      </span>

<script type="text/javascript" >
    var forma = document.forms['form1'];
    forma.hotNow[0].checked = "true";
</script>
      <!-- CONTENT ENDS HERE ____________________________________________________________ -->
</p>
</tiles:put>

<tiles:put name="sidearea" type="string" />
</tiles:insert>

