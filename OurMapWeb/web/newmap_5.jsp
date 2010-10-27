<%-- ***************************************************************************

newmap_5.jsp
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
    Created on : 26-ene-2010, 9:28:32
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
    function onKeyoptsSelected() {

        var forma = document.forms['KeywordForm'];
        var opt =  parseInt( getCheckedValue( forma.hotspotsMode ) );

        switch ( opt )
        {
            case 1:
                forma.status.value = '6';
                break;
            case 2:
                forma.status.value = '6.6';
                break;
            default:
                forma.status.value = '7';
        }

        forma.submit();
    }
</script>
<link href="styles/easy.css" rel="stylesheet" type="text/css">

    <form name="KeywordForm" action="newmap.do" >
        <input type="hidden" name="status" value="7" />

        <p><span class="Estilo6" >CHOOSE A KEYWORD OPTION</span><br/>
      </p>

        <p>&nbsp;</p>
        <p class="Estilo1">Keywords can be a useful tool for labeling, organizing and searching the hotspots on your map. But not every map needs Keywords.</p>
        <p><br/>
          <span class="Estilo8"><strong>Do you want your map users to be able to assign Keywords to the hotspots they create?</strong></span></p>
        <p><span class="Estilo1">
        <input type="radio" name="hotspotsMode" selected="yes" value="1" >
         Yes, I want to pre-set a list of Keywords that users can assign to new hotspots when they create them
        <br/>
        <input type="radio" name="hotspotsMode" value="2" >
        Yes, but I&rsquo;d prefer to allow users to make up their own Keywords when they create new hotspots<br/>
        <input type="radio" name="hotspotsMode" value="3" >
        No, I don't want to use Keywords on this map<br/>
          </span>          </p>
        <p>&nbsp;</p>

        <button type="button" onclick="document.location='newmap.do?status=4'"  >Back</button>
      <button type="button" onclick="onKeyoptsSelected()" >Save</button>

      <br/>


    <br/>
    <br/>

    <p>&nbsp;</p>
    </form>
<script language="javascript" >
    var forma = document.forms['KeywordForm'];
    var selected = 0;
<logic:present name="map" property="hotspotsMode" >
    selected = <bean:write name="map" property="hotspotsMode" /> - 1;
</logic:present>
    var obj = forma.hotspotsMode[selected].checked = 'checked';

</script>

<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>

<tiles:put name="hints" type="string" >
        <p class="Estilo1">Keywords are an <u>optional</u> map feature.  You are not required to use Keywords on your map. However, if your map is going  to contain lots of different types or categories of mapped information (ie.,  &ldquo;hotspots&rdquo;), then Keywords may be worth considering.</p>
        <p></p>
      <p align="justify">&nbsp;</p>
      <p align="justify" class="Estilo1">Keywords work independently from map Layers as follows:  Each hotspot will be assigned to a map Layer when it is created, but if Keywords are enabled then each hotspot will also be independently assigned a Keyword. Subsequently, if any given map Layer contains at least one hotspot with a specific Keyword, then that Keyword will be displayed on the Layers menu on the left side of your map, underneath the specific Layer it belongs to. This makes it easy to sub-divide the hotspots in a Layer into sub-groups of similar hotspots (almost like &ldquo;sub-layers&rdquo;), and also makes it easy to search for and view hotspots on the map according to these sub-groupings. </p>
        <p align="justify">&nbsp;</p>
        <p align="justify"><span class="Estilo1">There are actually many possible ways to use Layers and Keywords in different combinations to organize the hotspots on your map. Probably the best way to understand how this feature can work for your own mapping projects is to play around and experiment with the set-up of your first map. Overall, we recommend keeping things VERY SIMPLE to start with, and then trying out more advanced combinations of Layers and Keywords once you better understand how they work in practice. Remember, you can always change the configuration of your Layers and Keywords later, even after you have created and saved your map. So, perhaps you should start by trying a few different combinations of Layers and Keywords on your map, in order to explore the possibilities. We also suggest looking at some of the other maps on this site as further examples of how Keywords can be used.</span><br>
        </p>
        <p><br/>
            <br/>
        </p>

</tiles:put>

</tiles:insert>

