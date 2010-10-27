<%-- ***************************************************************************

new_hotspot2.jsp
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
    Created on : 04-feb-2010, 0:04:59
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<%@taglib  uri="/WEB-INF/cuesta.tld" prefix="cuesta" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_hint.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="newhs_left_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >
<!-- CONTENT HERE _________________________________________________________________ -->
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />
<bean:define id="hs" name="new_hotspot" scope="session" toScope="page" type="Hotspot" />

<link rel="stylesheet" href="styles/icons.css" type="text/css" />


<script type="text/javascript" src="ajax.js" ></script>

<script type="text/javascript" >

    var mapid = <bean:write name="map" property="mapid" /> ;

    
    function init() {

    
    }

    function showNewKeyDiv() {
    }

    function showSelectExistingKeyDiv() {
    }


    function showIconsDiv() {
        nuevaVentana('viewicons.do');
    }


    function onIconSelected(objectId) {

        var forma = document.forms['NewHotspotForm'];
        forma.iconId.value = objectId;

       var iconImg = document.getElementById('iconImg');
       iconImg.src = 'getObject.do?objectId=' + objectId ;

       showChangeIcon();

    }

    function saveHotspot() {

        var forma = document.forms['NewHotspotForm'];

        forma.formSubmited.value = 'yes';

        //forma.selectedKeyword.value = '';
        //alert( forma.newKeyword.value );
        
        forma.submit();

    }

    function cancelHotspot() {

        if ( !confirm('Hotspot information will be lost. Continue?') )
            return;
        var forma = document.forms['NewHotspotForm'];
        forma.op.value = 'cancel';
        forma.mapMode.value ="newHs";

        forma.submit();
    }

    function backHotspot() {

        var forma = document.forms['NewHotspotForm'];
        forma.op.value = 'back';
        forma.mapMode.value ="newHs";

        forma.submit();
    }

    function showAddIcon() {
        showLayer('addIconDiv');
        hideLayer('changeIconDiv');
    }

    function showChangeIcon() {
        showLayer('changeIconDiv');
        hideLayer('addIconDiv');
    }


</script>


<!-- New Hs Form __________________________________________________ -->
<div id="divNewHsForm"  >
<html:form action="newhotspot" method="post" enctype="multipart/form-data" >

          <input type="hidden" name="lon" value="" />
          <input type="hidden" name="lat" value="" />
          <input type="hidden" name="mapid" value="<bean:write name="mapid" scope="request" />" />
          <input type="hidden" name="formSubmited" />
          <input type="hidden" name="iconId" />
          <input type="hidden" name="op" />
          <input type="hidden" name="mapMode" />

          <input type="hidden" name="actualValue" />
          <input type="hidden" name="comboValue" />


        <p class="Estilo6">CREATE A NEW HOTSPOT</p>
        <p class="Estilo6">&nbsp;</p>
        <div align="justify" class="Estilo1"  >
Please enter a Title and (if needed) a Summary for your new hotspot in the boxes below.
        </div>
<br/>

<table width="100%" border="0">
              <tr>
                  <td colspan="2" ><span class="Estilo9" ><logic:present name="hsName.required" ><bean:write name="hsName.required" /></logic:present></span></td>
              </tr>

          <tr>
            <td height="48" colspan="2"><p class="Estilo1">Hotspot Title:</p>

              <p>
                <input type="text" name="hsName" id="hsName" size="40" onkeypress="return disableEnterKey(event)" value="<bean:write name="hs" property="hsName" />" />
              </p></td>
      </tr>




          <tr>
            <td colspan="2"><p class="Estilo1">Summary:</p>
              <p>
                <textarea name="hsDescription" id="hsDescription" cols="50" rows="4"><bean:write name="hs" property="hsDescription" /></textarea>
              </p></td>
          </tr>
          <tr><td colspan="2" ><br/></tr>


          <tr>
              <td colspan="2" class="Estilo1" >
          Next, please assign your hotspot to a specific Layer on the map:<br/><br/>
              </td>
          </tr>

          <tr>
            <td width="88" height="25" class="Estilo1">Layer</td>
     <td width="413">
               <cuesta:combo name="hsLayer" value="layerId" label="layerName" list="layers" addNull="false" selected="selectedLayer"  />
     </td>
          </tr>

          <tr>
              <td colspan="2" class="Estilo1" ><br/>
              </td>
          </tr>


          <logic:equal name="map" property="hotspotsMode" value="1" >
          <tr>
              <td colspan="2" class="Estilo1" >
You also need to choose a Keyword for your hotspot or, if the option is enabled on this map, type your own Keyword into the box below:</td>
          </tr>
          <tr>
              <td colspan="2" class="Estilo1" ><br/>
              </td>
          </tr>
          <tr>
            <td class="Estilo1" >Keyword</td>
            <td>
                <cuesta:combo name="selectedKeyword" value="kwId" label="kwValue" list="keywords" addNull="false" selected="selectedKeyword"  /> 
            </td>
          </tr>
          </logic:equal>


          <!-- User keywords here __________________________________________ -->
          <logic:equal name="map" property="hotspotsMode" value="2" >

          <logic:present name="hsKeyword.required" >
          <tr>
            <td colspan="2" >
                <span class="Estilo1" style="color:red" >
                    <bean:write name="hsKeyword.required" />
                </span>
            </td>
          </tr>
          </logic:present>

          <tr>
            <td class="Estilo1" colspan="2" >
You also need to choose a Keyword for your hotspot or, if the option is enabled on this map, type your own Keyword into the box below:
<br/><br/>
            </td>
          </tr>

          <tr>
            <td class="Estilo1" >Keyword</td>
            <td>
                <input type="text" name="newKeyword" value="<bean:write name="newkeyword" scope="session" ignore="true" />" />
            </td>
          </tr>


          <script type="text/javascript" >
                init();
          </script>

          </logic:equal>


              <tr>
                  <td colspan="2" ><br/></td>
              </tr>




          <logic:equal name="map" property="iconsMode" value="4" >

<logic:present name="iconId.required" >
              <tr>
                  <td colspan="2" ><span class="Estilo9" ><bean:write name="iconId.required" /></span></td>
              </tr>
</logic:present>

              <tr>
                  <td colspan="2" class="Estilo1" >
Finally, please assign an Icon to your hotspot. This is the symbol that will mark your hotspot on the map:
                  </td>
              </tr>

              <tr>
                  <td colspan="2" >
                  <br/>
                  </td>
              </tr>


          <tr>
              <td class="Estilo1" >
                  Icon
              </td>
            <td>
                    
                    <div id="changeIconDiv" style="display:none" >
                    <img id="iconImg" alt="icon" src="<easy:GetObjectTag id="<%=hs.getIconId()%>" />" />
                    <a href="javascript:showIconsDiv()" >
                    Change
                    </a>
                    </div>

                    <div id="addIconDiv" style="display:none" >
                    <a href="javascript:showIconsDiv()" >
                    Add Icon
                    </a>
                    </div>



            </td>
          </tr>
          </logic:equal>


       </table>

<br/>

        <hr/>
        <p>
          <button type="button" onclick="saveHotspot()" >Next</button>

        </p>
</div>


     <script type="text/javascript" >
                <logic:present name="hs" property="iconId" >
                    showChangeIcon();
                </logic:present>
                <logic:notPresent name="hs" property="iconId" >
                    showAddIcon();
                </logic:notPresent>
     </script>


</html:form>

<!-- End New Hs Form __________________________________________________ -->

<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>

<tiles:put name="hints" type="string" >

    1) Short titles work best.<br/><br/>

    2)    The hotspot Summary will be shown as a tab on your hotspot’s content window. If you enter a Summary, then this text will be the first thing that people see when they click on your hotspot. So this is a good place to put a short description, introduction, or other general information about your hotspot.


</tiles:put>

</tiles:insert>

