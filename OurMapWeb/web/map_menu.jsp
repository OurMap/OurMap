<%-- ***************************************************************************

map_menu.jsp
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
<%@ page import="com.bnmi.ourmap.web.Utils" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bnmi.ourmap.web.Utils" %>
<%@ page import="com.inga.utils.SigarUtils" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<%@taglib  uri="/WEB-INF/cuesta.tld" prefix="cuesta" %>


<bean:define id="project" name="project"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Project" />
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />
<bean:define id="principal" name="<%=Constantes.USER%>"  scope="session" toScope="page" type="com.bnmi.ourmap.model.User" />

<html>
    <head>
<link href="tabs/tabstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="ajax.js"></script>
<script type="text/javascript" src="treeview/ua.js" ></script>
<script type="text/javascript" src="treeview/ftiens4.js" ></script>

<script type="text/javascript" >

    function hideAll() {

        hideLayer('divLegend');
        hideLayer('divLayers');
        hideLayer('divQuery');
        hideLayer('divEditFeatures');
        hideLayer('divSettings');

    }

    function showTab(divId) {
        hideRest(divId);
        toggleLayer(divId);
    }

    function hideRest(divId) {
        if ( divId != 'divLegend')
            hideLayer('divLegend');
        if ( divId != 'divLayers')
            hideLayer('divLayers');
        if ( divId != 'divQuery' )
            hideLayer('divQuery');



        if ( divId != 'divEditFeatures')
            hideLayer('divEditFeatures');




        if ( divId != 'divSettings' )
            hideLayer('divSettings');


        showGpsButton();
        stopNewHotspot();

    }

    function newHotspot2() {

        var forma = document.forms['NewHotspotForm'];

    <logic:empty name="layers" scope="request" >
               alert('Add layer first!');
    </logic:empty>
    <logic:notEmpty name="layers" scope="request" >
               forma.submit();
    </logic:notEmpty>

           }

           function showEnterGpsMenu() {
               hideLayer('clickForm');
               showLayer('enterGpsForm');
           }

           function showEnterCoordsMenu() {
               hideLayer('enterGpsForm');
               hideLayer('clickForm');
           }

           function showClickForm() {
               showLayer('clickForm');
               hideLayer('enterGpsForm');
               hideLayer('enterCoordsMenu');
           }



           function newHotspot() {

               var forma = document.forms['NewHotspotForm'];
               forma.lon.value = '';
               forma.lat.value = '';


               var divMap = document.getElementById('map');
               divMap.style.cursor = "crosshair";

               map.addControl( clickControl );
               clickControl.activate();

               //map.addControl(click);
               //click.activate();

               //map.events.register("mousemove", map, followPosition );

           }

           function stopNewHotspot() {

               var divMap = document.getElementById('map');
               divMap.style.cursor = "auto";

               map.removeControl( clickControl );
               clickControl.deactivate();

               pointLayer.destroyFeatures();

           }

           function onCoordsEntered() {

               var forma = document.forms['NewHotspotForm'];

               var valid = validateCoords();
               if ( valid )
               {
                   forma.currentZoom.value = map.getZoom();
                   var currentCenter = map.getCenter();
                   currentCenter.transform(pro1,pro2);
                   forma.currentLon.value = currentCenter.lon;
                   forma.currentLat.value = currentCenter.lat;
                   forma.submit();
               }
               else
                   alert('Invalid value');

           }

           function validateCoords() {

               var forma = document.forms['NewHotspotForm'];

               if ( ! coordsAreValid( forma.lon.value, forma.lat.value ) )
                   return false;

               if ( ! validatePoint( forma.lon.value, forma.lat.value ) )
                   return false;

               return true;

           }

           function coordsAreValid(lon,lat) {

               if (  ! validarNumero( lon ) )
                   return false;

               if (  ! validarNumero( lat ) )
                   return false;

               return true;
           }

           function onCoordTyped( src ) {
               var valid = validateCoords();

               enableAcceptButton( valid );
           }

           function enableAcceptButton( valid ) {
               var acceptCoordsButton = document.getElementById('acceptCoordsButton');
               if ( valid )
                   acceptCoordsButton.innerHTML = '<span class="Estilo6" onclick="onCoordsEntered()" style="cursor:pointer" >Accept</span>';
               else
                   acceptCoordsButton.innerHTML = '<span class="Estilo15" >Accept</span>';

           }

           function showGpsButton() {
               showLayer('divAddHsButton');
               hideLayer('enterGpsForm');
               stopNewHotspot();
           }

           function showGpsForm() {
               hideLayer('divAddHsButton');
               showLayer('enterGpsForm');
               newHotspot();
           }

           function focusCoords() {

               var forma = document.forms['NewHotspotForm'];

               // Are the numbers in the right format?
               if ( ! coordsAreValid( forma.lon.value, forma.lat.value ) )
                   return;

               // Is the point between the boundaries?
               if ( ! validatePoint( forma.lon.value, forma.lat.value ) )
               {
                   alert('Out of range');
                   return false;
               }

               var currentBounds = map.calculateBounds();
               //alert( currentBounds );

               var desiredLocation = getLonLat(forma.lon.value, forma.lat.value);


               if ( pointFeature != null)
                   pointLayer.removeFeatures( pointFeature );

               var newPoint = getLonLat( forma.lon.value, forma.lat.value );

               drawRedDot( newPoint);

               if( ! currentBounds.containsLonLat( desiredLocation ) )
               {
                  centerMap( forma.lon.value, forma.lat.value, map.getZoom() );
               }


           }


</script>





<link href="styles/easy.css" rel="stylesheet" type="text/css" />
<link href="styles/nifty.css" rel="stylesheet" type="text/css" />
</head>
<body>


                <script type="text/javascript" >
                    //
                    // Copyright (c) 2006 by Conor O'Mahony.
                    // For enquiries, please email GubuSoft@GubuSoft.com.
                    // Please keep all copyright notices below.
                    // Original author of TreeView script is Marcelino Martins.
                    //
                    // This document includes the TreeView script.
                    // The TreeView script can be found at http://www.TreeView.net.
                    // The script is Copyright (c) 2006 by Conor O'Mahony.
                    //
                    // You can find general instructions for this file at www.treeview.net.
                    //

                    USETEXTLINKS = 1
                    STARTALLOPEN = 0
                    USEFRAMES = 0
                    USEICONS = 0
                    WRAPTEXT = 1
                    PRESERVESTATE = 1

                    //
                    // The following code constructs the tree.  This code produces a tree that looks like:
                    //
                    // Tree Options
                    //  - Expand for example with pics and flags
                    //    - United States
                    //      - Boston
                    //      - Tiny pic of New York City
                    //      - Washington
                    //    - Europe
                    //      - London
                    //      - Lisbon
                    //  - Types of node
                    //    - Expandable with link
                    //      - London
                    //    - Expandable without link
                    //      - NYC
                    //    - Opens in new window
                    //

                    foldersTree = gFld("<img id='treeButton' onclick='toggleTree()' src='images/circle_arrow_right.png' width='15px' height='15px' />", "" )
                    foldersTree.treeID = "Frameless"

                    <logic:iterate name="layerMap" id="layerEntry" type="java.util.Map.Entry<Layer,LinkedHashMap<Category,LinkedHashSet<Keyword>>>" scope="request" >
                        <bean:define name="layerEntry" property="key" id="layer" type="Layer" />
                        <bean:define name="layerEntry" property="value" id="layerData" type="LinkedHashMap<Category,LinkedHashSet<Keyword>>" />

                    <logic:notEqual name="map" property="iconsMode" value="1" >
                      layerNode =
                          insFld(foldersTree, gFld("<input type='checkbox' checked='checked' name='layer_<bean:write name="layer" property="layerId" />' onclick=\"checkedLayer( this.checked, '<bean:write name="layer" property="layerId" />')\" /> <span class='Estilo4' ><%=Utils.unscapeHtml(layer.getLayerName())%> </span>" , ""))
                    </logic:notEqual>
                    <logic:equal name="map" property="iconsMode" value="1" >
                      layerNode =
                      insFld(foldersTree, gFld("<input type='checkbox' checked='checked' name='layer_<bean:write name="layer" property="layerId" />'     onclick=\"checkedLayer( this.checked, '<bean:write name="layer" property="layerId" />')\" /> <span class='Estilo4' > <%=Utils.unscapeHtml(layer.getLayerName())%> &nbsp;&nbsp; <img src='<easy:GetObjectTag id="<%=layer.getIconId()%>" />' width='18' height='18' /> </span>" , ""))
                    </logic:equal>


                        <logic:iterate name="layerData" id="layerDataEntry" type="java.util.Map.Entry<Category,LinkedHashSet<Keyword>>" >
                            <bean:define name="layerDataEntry" id="cat" property="key" type="Category" />
                            <bean:define name="layerDataEntry" id="catKeywords" property="value" type="LinkedHashSet<Keyword>" />
                            <logic:notEqual name="cat" property="catId" value="-1" >
                              <logic:notEqual name="map" property="iconsMode" value="2" >
                              catNode =
                                  insFld( layerNode, gFld("<input type='checkbox' checked='checked' name='layer_<bean:write name="layer" property="layerId" />_cat_<bean:write name="cat" property="catId" />' onclick=\"toggleCategory(this.checked,'<bean:write name="layer" property="layerId" />','<bean:write name="cat" property="catId" />')\" /> <bean:write name="cat" property="catName" />", ""))
                              </logic:notEqual>
                              <logic:equal name="map" property="iconsMode" value="2" >
                              catNode =
                                  insFld( layerNode, gFld("<input type='checkbox' checked='checked' name='layer_<bean:write name="layer" property="layerId" />_cat_<bean:write name="cat" property="catId" />' onclick=\"toggleCategory(this.checked,'<bean:write name="layer" property="layerId" />','<bean:write name="cat" property="catId" />')\" /> <bean:write name="cat" property="catName" /> &nbsp;&nbsp; <img src='<easy:GetObjectTag id="<%=cat.getIconId()%>" />' width='18' height='18' />", ""))
                              </logic:equal>


                                 <logic:iterate name="catKeywords" id="key" type="Keyword" >
                                     <logic:notEqual name="map" property="iconsMode" value="3" >
                                 keywordNode =
                                     insFld( catNode, gFld("<input type='checkbox' checked='checked' name='layer_<bean:write name="layer" property="layerId" />_cat_<bean:write name="cat" property="catId" />_key_<bean:write name="key" property="kwId" />' onclick=\"toggleKeyword(this.checked,'<bean:write name="layer" property="layerId" />','<bean:write name="key" property="kwId" />','<bean:write name="cat" property="catId" />')\" /> <bean:write name="key" property="kwValue" /> ", ""))
                                     </logic:notEqual>
                                     <logic:equal name="map" property="iconsMode" value="3" >
                                 keywordNode =
                                     insFld( catNode, gFld("<input type='checkbox' checked='checked' name='layer_<bean:write name="layer" property="layerId" />_cat_<bean:write name="cat" property="catId" />_key_<bean:write name="key" property="kwId" />' onclick=\"toggleKeyword(this.checked,'<bean:write name="layer" property="layerId" />','<bean:write name="key" property="kwId" />','<bean:write name="cat" property="catId" />')\" /> <bean:write name="key" property="kwValue" /> &nbsp;&nbsp; <img src='<easy:GetObjectTag id="<%=key.getIconId()%>" />' width='18' height='18' />", ""))
                                     </logic:equal>

                                 </logic:iterate>
                            </logic:notEqual>

                            <logic:equal name="cat" property="catId" value="-1" >
                                <logic:iterate name="catKeywords" id="key" type="Keyword" >
                                     <logic:notEqual name="map" property="iconsMode" value="3" >
                                 keywordNode =
                                     insFld( layerNode, gFld("<input type='checkbox' checked='checked' name='layer_<bean:write name="layer" property="layerId" />_cat_<bean:write name="cat" property="catId" />_key_<bean:write name="key" property="kwId" />' onclick=\"toggleKeyword(this.checked,'<bean:write name="layer" property="layerId" />','<bean:write name="key" property="kwId" />','<bean:write name="cat" property="catId" />')\" /> <bean:write name="key" property="kwValue" /> ", ""))
                                     </logic:notEqual>
                                     <logic:equal name="map" property="iconsMode" value="3" >
                                 keywordNode =
                                     insFld( layerNode, gFld("<input type='checkbox' checked='checked' name='layer_<bean:write name="layer" property="layerId" />_cat_<bean:write name="cat" property="catId" />_key_<bean:write name="key" property="kwId" />' onclick=\"toggleKeyword(this.checked,'<bean:write name="layer" property="layerId" />','<bean:write name="key" property="kwId" />','<bean:write name="cat" property="catId" />')\" /> <bean:write name="key" property="kwValue" /> &nbsp;&nbsp; <img src='<easy:GetObjectTag id="<%=key.getIconId()%>" />' width='18' height='18' />", ""))
                                     </logic:equal>
                                </logic:iterate>
                            </logic:equal>

                        </logic:iterate>

                    </logic:iterate>

                </script>



<div id="menu14">
    <ul>
        <!-- CSS Tabs -->
        <li>
            <a href="javascript:showTab('divLegend')"><span>Map Description</span></a>
            <div id="divLegend" style="width:200px;display:inline">
                <table width="200px" >
                    <tr>
                        <td width="200px" align='left'  class="Estilo1">
                            <%
                    if (map.getDescription() != null) {
                        if (map.getDescription().length() > 200) {%>
                            <textarea  name="textarea" cols="<bean:write name="cols" scope="request" />" rows="5" class="SmallMatchItem">
    <%=SigarUtils.breakWord( map.getDescription() , 70 ) %>
                            </textarea>
                            <% } else {%>
    <%=SigarUtils.breakWord( Utils.unscapeHtml( map.getDescription() ), 25 ) %>
                            <% }
                                        }
                            %>    </td>
                    </tr>
                </table>
            </div>
        </li>
        <li id="current" ><a href="javascript:showTab('divLayers')"><span>Layers</span></a>
            <div id="divLayers" >
                <form name="LayersForm" action="javascript:" >




                    <A style="font-size:7pt;text-decoration:none;color:silver;display:none" href="http://www.treemenu.net/" target=_blank>Javascript Tree Menu</A>

                <!-- Build the browser's objects and display default view  -->
                <!-- of the tree.                                          -->
                <SCRIPT type="text/javascript" >initializeDocument()</SCRIPT>
                <NOSCRIPT>
                    A tree for site navigation will open here if you enable JavaScript in your browser.
                </NOSCRIPT>


            </form>
            </div>
        </li>

        <li style="display:none" ><a href="#" onclick="showTab('divQuery')"><span>Search/Query</span></a>
            <div id="divQuery" >
                <div id="divQueryForm" >
                    <form name="QueryForm" action="javascript:searchHotspots()" method="post" >
                        <input type="hidden" name="mapid" value="<bean:write name='map' property='mapid' />" />
                        <table>
                            <tr>
                                <td><input type="text" name="general" /></td>
                            </tr>
                            <tr>
                                <td>
                                    <logic:notPresent name="no_edit" scope="request" >
                                        <button type="button" name="queryButton" value="Search" onclick="searchHotspots()" >Search</button>
                                    </logic:notPresent>
                                    <logic:present name="no_edit" scope="request" >
                                        <button type="button" name="queryButton" value="Search" >Search</button>
                                    </logic:present>

                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div id="divQueryMessage" ></div>
                <div id="divQueryResults" >
                    <table id="queryResultsTable" >
                    </table>
                </div>
            </div>
        </li>


        <li
            <logic:notPresent name="no_edit" scope="request" >
                <easy:hasNotPermission actionId="add-hotspot"  elementId="<%=map.getMapid()%>" elementType="<%=com.bnmi.ourmap.Constantes.MAP%>" >
                    style="display:none"
                </easy:hasNotPermission>
            </logic:notPresent>
            ><a href="javascript:showTab('divEditFeatures');"><span>Add Hotspots</span></a>

            <div id="divEditFeatures" >
                <form name='NewHotspotForm' action='newhotspot.do' method='post' >
                    <input type="hidden" name="mapid" value="<%=map.getMapid()%>" />
                    <input type="hidden" name="currentZoom" />
                    <input type="hidden" name="currentLon" />
                    <input type="hidden" name="currentLat" />
                    <input type="hidden" name="op" value="step0" />
                


                    <div id="divAddHsButton" >
                        <br/>
                        <span class="Estilo6" onclick="showGpsForm()" style="cursor:pointer" >Add a New Hotspot</span>
                    </div>

                    <div id="enterGpsForm" style="display:none" >
                        <span class="Estilo1" >
                            Enter the GPS coordinates below or click a point on the map to add your hotspot, then hit "Accept". This button will not be available if your coordinates lie outside the map boundaries
                            <br/><br/>
                            <table>
                                <tr>
                                    <td width="33">Long:</td>
                                    <td width="142"><input type="text" name="lon" onkeyup="onCoordTyped(this)"/></td>
                                </tr>
                                <tr>
                                    <td>Latt:</td>
                                    <td><input type="text" name="lat" onkeyup="onCoordTyped(this)" /></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td><span class="rbottom Estilo16" style="cursor:pointer" onclick="focusCoords()" ><u>show this location</u></span><br/></td>
                                </tr>
                                <tr>
                                    <td><br/></td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <td><div id="acceptCoordsButton" ><span class="Estilo15" >Accept</span></div>                        </td>
                                    <td >
                                        <span class="Estilo6" onclick="showGpsButton()" style="cursor:pointer;padding-left:10px" >Cancel</span>
                                    </td>
                                </tr>
                            </table>

                        </span>

                    </div>
                    <br/>

                    <br/>
                </form>
            </div>

        </li>



        <li
            <logic:notPresent name="no_edit" scope="request" >
                <easy:hasNotPermission actionId="map-creator-owner"  elementId="<%=map.getMapid()%>" elementType="<%=com.bnmi.ourmap.Constantes.MAP%>" >
                    style="display:none"
                </easy:hasNotPermission>
            </logic:notPresent>
            ><a href="javascript:showTab('divSettings')"><span>Map Configuration</span></a>
            <div id="divSettings" >


                <br/>
                <span class="Estilo6" style="cursor:pointer" onclick="document.location='mapconfiguration.do?mapid=<bean:write name="map" property="mapid" />'">
                    Go to Configuration Pages
                </span>
                <br/>
                <br/>


            </div>
        </li>


        <li class="SmallMatchItem"></li>
    </ul>

</div>

<logic:notPresent name="no_edit" scope="request" >
    <script language="javascript" >
        hideAll();
    </script>
</logic:notPresent>

<logic:present name="no_edit" scope="request" >
    <script language="javascript" >
        hideAll();

        <logic:equal name="<%=Constantes.NEW_MAP_STATUS%>" value="1" >
            showLayer('divLegend');
        </logic:equal>
        <logic:equal name="<%=Constantes.NEW_MAP_STATUS%>" value="4.1" >
            showLayer('divLayers');
        </logic:equal>
        <logic:equal name="<%=Constantes.NEW_MAP_STATUS%>" value="6.9" >
            showLayer('divLayers');
        </logic:equal>

        <logic:equal name="<%=Constantes.NEW_MAP_STATUS%>" value="7.5" >
            <logic:equal name="map" property="iconsMode" value="1" >
                showLayer('divLayers');
            </logic:equal>
        </logic:equal>


    </script>
</logic:present>


<logic:notPresent name="no_edit" scope="request" >


    <table width="100%" border="0">


        <tr>
            <td class="Estilo4">
            </td>
        </tr>

        <logic:present name="standalone" scope="session" >
      <easy:ValidatePermission actionId="overlord" >
        <tr>
            <td class="Estilo4">
                <a href="main.jsp" ><span class="Estilo6">Main Menu</span></a>
            </td>
        </tr>
      </easy:ValidatePermission>
        <tr>
            <td class="Estilo4">
                <a href="logout.do"  ><span class="Estilo6" >Log out</span></a>
            </td>
        </tr>
        </logic:present>

    </table>

</logic:notPresent>


<logic:present name="mapMode" scope="request" >
    <logic:equal name="mapMode" value="newHs" >
        <script language="javascript" >


            showLayer('divEditFeatures');


        </script>
    </logic:equal>
</logic:present>
</body>
</html>