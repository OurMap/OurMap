<%-- ***************************************************************************

edithsinfo.jsp
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
<%@taglib  uri="/WEB-INF/cuesta.tld" prefix="cuesta" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />

  <tiles:put name="leftMenu" value="edithsmenu.jsp" type="page" />

  <tiles:put name="content" type="string"  >

<bean:define name="hs" id="hs" scope="request" toScope="page" type="Hotspot" />
<bean:define name="hs" id="pos" property="hsPos" type="com.inga.utils.Point" />
<bean:define name="leftBottom" id="leftBottom" type="com.inga.utils.Point" />
<bean:define name="rightTop" id="rightTop" type="com.inga.utils.Point" />

<link rel="stylesheet" href="styles/icons.css" type="text/css" />

<script src="ajax.js"></script>
<script language="javascript" >

    var top     = parseFloat(<bean:write name="rightTop" property="printY" />) ;
    var right   = parseFloat(<bean:write name="rightTop" property="printX" />) ;
    var left    = parseFloat(<bean:write name="leftBottom" property="printX" />) ;
    var bottom  = parseFloat(<bean:write name="leftBottom" property="printY" />) ;

    function validatePoint( lon, lat ) {

        lon = parseFloat( lon );
        lat = parseFloat( lat );

        if ( lon > right | lon < left )
            return false;

        if ( lat > top | lat < bottom )
            return false;

        return true;
        
    }

    function saveThis() {

        var forma = document.forms['EditHsInfoForm'];

        if ( ! validarCadena( forma.title.value) )
        {
            alert('Hotspot Title is required');
            return;
        }

        if ( ! validarNumero( forma.lon.value) )
        {
            alert('Invalid longitude value');
            return;
        }

        if ( ! validarNumero( forma.lat.value) )
        {
            alert('Invalid latitude value');
            return;
        }

        if ( ! validatePoint( forma.lon.value, forma.lat.value) )
        {
            alert('Coordinates are out of range');
            return;
        }



        forma.submit();

    }

    function onIconSelected( objectId ) {


        var theIcon = document.getElementById('theIcon');
        var img = getChild( theIcon, 'IMG' );
        img.src = 'getObject.do?objectId=' + objectId ;
        var forma = document.forms['EditHsInfoForm'];
        forma.iconId.value = objectId;

        hideIconsDiv();
    }

    function showIconsDiv() {
        //showLayer('iconsDiv');
        //hideLayer('theIcon');

        nuevaVentana('viewicons.do');

    }

    function hideIconsDiv() {
        hideLayer('iconsDiv');
        showLayer('theIcon');
    }
    
</script>
<form name="EditHsInfoForm" action="edithsinfo.do" method="post" >
    <input type="hidden" value="<bean:write name="hs" property="hsId" />" name="hsId" />
    <input type="hidden" name="formSubmited" value="yes" />
    <logic:equal name="map" property="iconsMode" value="4" >
    <input type="hidden" name="iconId" value="<bean:write name="hs" property="iconId" />" />
    </logic:equal>

    <span class="Estilo6" >EDIT HOTSPOT INFORMATION</span>
    <br/>
    <br/>

    <div align="justify" class="Estilo1" >
This page allows you to make changes to your hotspot’s “information” or “metadata”. Use the interactive boxes below to edit these hotspot details.
<br/>
<br/>
You can always change the Title, GPS Coordinates, and Radius of your hotspot, and you can also change which map Layer it appears on. You may also be allowed to change other details (like which Keyword or Icon is assigned to the hotspot) depending on the settings for this map.

    </div>
    <br/>
<table width="450" border="0" >
  <tr>
    <td width="145"><span class="Estilo8">Hotspot Title:</span></td> 
    <td width="295"><span class="Estilo1">
       <input type="text" name="title" value="<bean:write name="hs" property="hsName" />" />
    </td>
  </tr>
  <tr>
    <td><span class="Estilo8">Date created:</span></td>
    <td><span class="Estilo1"><bean:write name="hs" property="created" format="MM/dd/yyyy" /></span></td>
  </tr>
  <tr>
    <td><span class="Estilo8">Time created:</span></td>
    <td><span class="Estilo1"><bean:write name="hs" property="created" format="h:mm:ss a Z" /> (Timezone)</span></td>
  </tr>
  <tr>
    <td><span class="Estilo8">Created by:</span></td>
    <td><span class="Estilo1"><bean:write name="creatorName" scope="request" /></span></td>
  </tr>
  <tr>
    <td><span class="Estilo8">GPS Coordinates:</span></td>
    <td>
        <span class="Estilo1">
            <table width="289" >
    <tr>
                    <td width="76">Longitude:</td>
                    <td width="201" >
                      <input name="lon" type="text" value="<bean:write name="pos" property="x" />" />
                    </td>
              </tr>
                <tr>
                    <td>Latitude:</td>
                    <td>
                         <input name="lat" type="text" value="<bean:write name="pos" property="y" />" />
                    </td>
                </tr>

            </table>


        </span>
    </td>
  </tr>
  <tr>
    <td><span class="Estilo8">Proximity radius:</span></td>
    <td><span class="Estilo1">
            <input type="text" name="proximityRadius" value="<bean:write name="hs" property="proximityRadius" />" /> metres</span></td>
  </tr>

  <tr>
    <td><span class="Estilo8">Layer:</span></td>
    <td><span class="Estilo1">
            <cuesta:combo list="layers" label="layerName" value="layerId" name="layerId" addNull="false" scope="request" selected="selectedLayer" />
        </span></td>
  </tr>


  <logic:present name="category" scope="request" >
  <tr>
    <td><span class="Estilo8">Category:</span></td>
    <td><span class="Estilo1"><bean:write name="category" property="catName" scope="request" /></span></td>
  </tr>
  </logic:present>

  <logic:present name="keyword" scope="request" >
  <tr>
    <td><span class="Estilo8">Keyword:</span></td>
    <td><span class="Estilo1">

      <logic:equal name="map" property="hotspotsMode" value="1" scope="request" >
          <cuesta:combo name="kwId" list="keys" value="kwId" label="kwValue" addNull="false" scope="request" selected="selectedKey" />
      </logic:equal>

      <logic:equal name="map" property="hotspotsMode" value="2" scope="request" >
          <input type="text" name="kwValue" value="<bean:write name="keyword" property="kwValue" scope="request" />" />
      </logic:equal>

        </span></td>
  </tr>

  </logic:present>

  <tr>
    <td><span class="Estilo8">Icon:</span></td>
    <td><span class="Estilo1">

            <logic:notEqual name="map" property="iconsMode" value="4" >
            <img src="<easy:GetObjectTag id="<%=hs.getIconId()%>" />" />
            </logic:notEqual>

            <logic:equal name="map" property="iconsMode" value="4" >

            <div id="theIcon" >
            <img src="<easy:GetObjectTag id="<%=hs.getIconId()%>" />" />
            &nbsp;&nbsp;
            <span class="Estilo6" style="cursor:pointer" onclick="showIconsDiv()" >Change</span>
            </div>

<div id="iconsDiv" style="display:none" >

<table width="262" height="154" border="0" >
<tr>
<td width="252"><span class="Estilo8" >Choose an icon from the following:</span>
<ol class="icons">
<logic:iterate id="icon" name="icons" scope="request" type="EasyObject" >

    <li>
    <a title="accept" >
        <img id="<bean:write name="icon" property="objectId" />" src="<easy:GetObjectTag id="<%=icon.getObjectId()%>" />" onclick="onIconSelected(this)" />
    </a>
    </li>

</logic:iterate>
</ol>

</td>
</tr>
</table>
<a href="javascript:hideIconsDiv()" >Close</a>
</div>

            </logic:equal>

        </span></td>
  </tr>


</table>

  <br/>
  <br/>
  <button type="button" onclick="cancelThis('viewmap.do?mapid=<bean:write name="mapid" scope="request" />&focusHs=<bean:write name="hs" property="hsId" />&viewHsEditPage=yes')">Cancel</button>
  <button type="button" onclick="saveThis()" >Save</button>
</form>

  </tiles:put>

<tiles:put name="sidearea" type="string" >
</tiles:put>

</tiles:insert>


