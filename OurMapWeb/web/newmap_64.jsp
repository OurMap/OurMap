<%-- ***************************************************************************

newmap_64.jsp
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
    Created on : 29-ene-2010, 0:43:41
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="newmap_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="new_map_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >
<!-- CONTENT HERE _________________________________________________________________ -->
<bean:define id="map" name="new_map"  scope="session" toScope="page" type="com.bnmi.ourmap.model.Map" />

<script type="text/javascript" src="ajax.js"></script>
<script type="text/javascript"  >
    function onCatsNowSelected() {
        var forma = document.forms['CatsNowForm'];
        var val = getCheckedValue( forma.catsnow);

        if ( val == 't' )
        {
            forma.status.value = '6.5';
        }
        else
            forma.status.value = '6.6';

        forma.submit(); 

    }
</script>
<link href="styles/easy.css" rel="stylesheet" type="text/css">

<p><span class="Estilo6" >ORGANIZE KEYWORDS INTO CATEGORIES</span>
    <br/>
    </p>
<p>&nbsp;</p>
<p><span class="Estilo1">Categories are another optional map feature. It&rsquo;s simple:  if you have added a lot of Keywords to your map, you can group them into different Categories to help keep them organized. For example, if all of your Keywords are animals, you might choose to group them into Categories like &quot;Carnivores&quot;, &quot;Herbivores&quot;, &quot;Omnivores&quot;, etc. </span></p>
<p><br/>
</p>
<form name="CatsNowForm" method="post" action="newmap.do" >
    <input type="hidden" name="status" />
    <span class="Estilo1" >
        Do you want to organize your keywords into categories now?<br/>
        <input type="radio" name="catsnow" value="t" >Yes</input><br/>
        <input type="radio" name="catsnow" value="f" >No</input>
    </span>
    <br/>
    <br/>
    <button type="button" onclick="document.location='newmap.do?nav=back'" >Back</button>
    <button type="button" onclick="onCatsNowSelected()" >Save</button>

</form>
<script type="text/javascript" >
    var forma = document.forms['CatsNowForm'];
    var selected = 1;

   <logic:equal name="map" property="catsEnabled" value="t" >
       selected = 0;
   </logic:equal>


    forma.catsnow[selected].checked =true;
</script>
<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>

<tiles:put name="sidearea" type="string" />

</tiles:insert>

