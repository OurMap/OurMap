<%-- ***************************************************************************

viewmap.jsp
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
<%@ page import="java.util.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<tiles:insert template="layout.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="map_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >


<link rel="stylesheet" href="styles/popup.css" type="text/css" />
<link href="styles/nifty.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="theme/default/google.css" type="text/css">


<bean:define id="project" name="project"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Project" />
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />

<script type="text/javascript" src='http://maps.google.com/maps?file=api&amp;v=2&amp;key=<%=Constantes.googleKey%>'></script><script type="text/javascript" src="lib/OpenLayers.js" ></script>
<script type="text/javascript" src="maplibs/OpenStreetMap.js"></script>


<script type="text/javascript" src="csspopup.js"></script>
<script type="text/javascript" src="message_popup.js"></script>
<script type="text/javascript" src="ourmap.js" ></script>
<script type="text/javascript" >

      var pointFeature = null;
      var obj     = new OpenLayers.Style();

      obj.fillColor     = "red";
      obj.fillOpacity   = 0.5;
      obj.strokeColor   = "red";
      obj.strokeOpacity = 1;
      obj.strokeWidth   = 6;
      obj.strokeLinecap = 'round';
      obj.pointRadius   = 2;

      var myPopups = new Array();

        function updatePos(lonlat) {

            var temp = new OpenLayers.LonLat( lonlat.lon, lonlat.lat );
            temp.transform(pro1,pro2);

            var hsForm = document.forms['NewHotspotForm'];
            hsForm.lon.value = temp.lon;
            hsForm.lat.value = temp.lat;


            var valid = validateCoords();
            enableAcceptButton( valid );


            if ( pointFeature != null)
               pointLayer.removeFeatures( pointFeature );

            drawRedDot( lonlat );


            if ( ! valid )
            {
                alert('Out of range');
            }
            else
            {
            }

        }

        function drawRedDot( lonlat ) {
            var point = new OpenLayers.Geometry.Point( lonlat.lon, lonlat.lat );
            pointFeature = new OpenLayers.Feature.Vector(point,null,obj);
            pointLayer.addFeatures( pointFeature );
            pointLayer.redraw();
        }


        // Click control -------------------------------------------------------
        //
        OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {
                defaultHandlerOptions: {
                    'single': true,
                    'double': false,
                    'pixelTolerance': 0,
                    'stopSingle': false,
                    'stopDouble': false
                },

                initialize: function(options) {
                    this.handlerOptions = OpenLayers.Util.extend(
                        {}, this.defaultHandlerOptions
                    );
                    OpenLayers.Control.prototype.initialize.apply(
                        this, arguments
                    );
                    this.handler = new OpenLayers.Handler.Click(
                        this, {
                            'click': this.trigger
                        }, this.handlerOptions
                    );
                },

                trigger: function(e) {
                    var lonlat = map.getLonLatFromViewPortPx(e.xy);
                    updatePos( lonlat );
                }

            });

        var clickControl = new OpenLayers.Control.Click();

        // End Click control ---------------------------------------------------


        var size = new OpenLayers.Size(20,20);
        var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
        var layerIcons = new Array();
        var hsIcons = new Array();
        var layerNames = new Array();

<logic:notPresent name="no_edit" scope="request" >
      <logic:iterate name="layers" id="layer" scope="request" type="Layer" >
        layerIcons['<bean:write name="layer" property="layerId" />'] = new OpenLayers.Icon('<easy:GetObjectTag id="<%=layer.getIconId()%>" />', size, offset );
        layerNames[<bean:write name="layer" property="layerId" />] = '<bean:write name="layer" property="layerName"/>';
      </logic:iterate>
</logic:notPresent>


        var myExtent;
        var osmCenter;
        var osmExtent;


        <logic:present name="map" property="leftBottom" >
        zoom = <%=map.getZoom()%>;
        myExtent = new OpenLayers.Bounds( <%=map.getLeftBottom().getPrintX()%>,
                                              <%=map.getLeftBottom().getPrintY()%>,
                                              <%=map.getRightTop().getPrintX()%>,
                                              <%=map.getRightTop().getPrintY()%> );

        myExtent.transform(pro2,pro1);

        center = getLonLat(<%=map.getCenter().getPrintX()%>,<%=map.getCenter().getPrintY()%> );

        </logic:present>

        <% if ( request.getParameter("cLon") != null && request.getParameter("cLat") != null ){ %>
        center = getLonLat( <%=request.getParameter("cLon")%>, <%=request.getParameter("cLat")%> );
        <% } %>
        <% if ( request.getParameter("cZoom") != null ){ %>
        zoom = <%=request.getParameter("cZoom")%>;
        <% } %>



        var popup;

        var hotspots = new Array();
        var markers = new Array();

      <logic:iterate name="layers" id="layer" scope="request" type="Layer" >
        var layer_<%=layer.getLayerId()%>;
      </logic:iterate>

      <logic:iterate name="layers" id="layer" scope="request" type="Layer" >
        layer_<%=layer.getLayerId()%> = new OpenLayers.Layer.Markers( "<%=layer.getLayerName()%>" );
      </logic:iterate>

      // Boundaries layer
      var boxes  = new OpenLayers.Layer.Boxes( "Boxes" );
      //var box = new OpenLayers.Feature.Vector( myExtent.toGeometry() );
      var box = new OpenLayers.Marker.Box( myExtent );
      //boxes.addFeatures(box);
      boxes.addMarker(box);

      // New hotspot layer
      var pointLayer = new OpenLayers.Layer.Vector("Point Layer"  );



      pointLayer.style = obj;






        function showInfo() {
            var extents = map.getExtent();
            alert( myExtent + '\n' + extents + '\ncenter:' + center + '\ncenter:'  + map.getCenter() + '\nRestricted:' + map.restrictedExtent );
        }



        function init() {

            var options = {
                projection: pro1,
                displayProjection: pro2,
                units: "m",
                numZoomLevels: 18,
                maxResolution: 156543.0339,

                maxExtent: new OpenLayers.Bounds(-20037508, -20037508,20037508, 20037508.34)
            };

            map = new OpenLayers.Map('map', options);


            // create Google Mercator layers
            var gmap = new OpenLayers.Layer.Google(
                "Google Streets",
                {'sphericalMercator': true}
            );
            var gsat = new OpenLayers.Layer.Google(
                "Google Satellite",
                {type: G_SATELLITE_MAP, 'sphericalMercator': true}
            );
            var ghyb = new OpenLayers.Layer.Google(
                "Google Hybrid",
                {type: G_HYBRID_MAP, 'sphericalMercator': true}
            );
            var gphy = new OpenLayers.Layer.Google(
                "Google Physical",
                {type: G_PHYSICAL_MAP, 'sphericalMercator': true}
            );


            // create OSM layer
            var mapnik = new OpenLayers.Layer.OSM();

            // create OSM layer
            var osmarender = new OpenLayers.Layer.OSM(
                "OpenStreetMap (Tiles@Home)",
                "http://tah.openstreetmap.org/Tiles/tile/${z}/${x}/${y}.png"
            );


            // create WMS layer
            var wms = new OpenLayers.Layer.WMS(
                "World Map",
                "http://world.freemap.in/tiles/",
                {'layers': 'factbook-overlay', 'format':'png'},
                {
                    'opacity': 0.4, visibility: false,
                    'isBaseLayer': false,'wrapDateLine': true
                }
            );

            // create a vector layer for drawing
            var vector = new OpenLayers.Layer.Vector("Editable Vectors");

            var editControl = new OpenLayers.Control.EditingToolbar(vector);

            map.addLayers([
                            ghyb,
                            gmap,
                            gsat,
                            gphy,
                            mapnik,
                            vector,
                            boxes,
                            pointLayer
                            <bean:write name="layerString" scope="request" />

                        ]);

            //map.addControl(new OpenLayers.Control.LayerSwitcher());
            //map.addControl( editControl );
            //map.addControl(new OpenLayers.Control.Permalink());
            var mpControl = new OpenLayers.Control.MousePosition();
            map.addControl( mpControl ); 


        }

        function getLonLat( lon, lat ) {
            return new OpenLayers.LonLat(lon,lat).transform(
            pro2,
            pro1
            );
        }

        function showIcon( hsId ) {
            var thisMarker = markers[hsId];

            if ( thisMarker != null )
            {
                //alert( thisMarker.display );
                thisMarker.display( true ) ;
            }

        }

        function hideIcon( hsId )
        {
            var thisMarker = markers[hsId];
            if ( thisMarker != null )
                thisMarker.display(false);
        }

        function refreshLayer( layerId, layerName, layerIcon ) {

            var size = new OpenLayers.Size(20,20);
            var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
            var myLayers = map.getLayersByName( layerName );
            var myLayer = myLayers[0];

            myLayer.clearMarkers();

            var params = 'hsLayer=' + layerId;

            var http_request = createRequest();

            makePOSTRequest( http_request, 'listhotspots.do' , params, function() {
            if (http_request.readyState == 4 && http_request.status == 200 )
            {
               var xmlDoc = http_request.responseXML;
               var root = xmlDoc.documentElement;
               var hsNodes = getChildren( root, 'hotspot' );

               for(var k = 0; k < hsNodes.length; k++ )
               {
                   var hs = hsNodes[k];
                   var hsId = getChild( hs, 'id' );
                   hsId = getText( hsId );

                   hotspots[ hsId ] = hs;

                   var pos = getChild( hs, 'pos' );
                   var lon = getChild( pos, 'lon' );
                   var lat = getChild( pos, 'lat' );
                   lon = getText( lon );
                   lat = getText( lat );
                   var markPos = getLonLat(lon,lat);
                   var hsIconId = getText( getChild( hs,'icon') );
                   //alert( hsId );

                   var iconUrl = '';
                   iconUrl = 'getObject.do?objectId=' + hsIconId ;
                   
                   if ( hsIcons[ hsIconId ] == null  )
                       hsIcons[ hsIconId ] = new OpenLayers.Icon( iconUrl, size, offset );

                   var hsIcon = hsIcons[ hsIconId ].clone();

                   var marker;

                   marker = new OpenLayers.Marker( markPos, hsIcon );

                   myLayer.addMarker( marker );
                   markers[hsId] = marker;


                   marker.events.register("mouseout", hs,
                        function (evt) {
                           hideLayer('hs_info');
                           OpenLayers.Event.stop(evt);
                        }
                   );

                   marker.events.register("mouseover", hs,
                        function (evt) {
                           var myId = getChild( this, 'id' );
                           hsId = getText( myId );
                           var myMarker = markers[hsId];
                           var myHotspot = hotspots[ hsId ];


                           var creator = getChild( myHotspot, 'creator' );
                           var hsTitle = getText( getChild(myHotspot, 'name'));
                           var creatorName = '';
                           
                           try
                           {
                               if ( creator )
                                   creatorName = getText(getChild( creator, 'nombre'));
                           }
                           catch ( e )
                           {
                               
                           }

                           var myPx = map.getViewPortPxFromLonLat(myMarker.lonlat);
                           var infoHsDiv = document.getElementById('hs_info');

                           //alert( myPx.x + ' ' + myPx.y );
                           hideLayer('hs_info');

                           infoHsDiv.innerHTML = '<span class=\'Estilo1\' >' + hsTitle + '<br/><i>Created by ' + creatorName + '</i>' + '</span>';

                           infoHsDiv.style.top = myPx.y + 'px';
                           var myX = myPx.x - 138;
                           infoHsDiv.style.left = myX + 'px' ;
                           showLayer('hs_info');

                           OpenLayers.Event.stop(evt);

                        }
                   );


                   marker.events.register("mousedown", hs,
                        function (evt) {


                           var myId = getChild( this, 'id' );
                           hsId = getText( myId );


                           // Find my popup
                           var myPopup = getPopup( hsId );


                           if ( ! myPopup )
                           {
                               createPopupForHotspot( hsId, layerIcons[hsId] );
                           }
                           else
                           {
                               var myFrame = document.getElementById('frame_' + hsId );
                               myFrame.src = 'hscontent.do?hsId=' + hsId ;
                               //alert( myFrame );
                               myPopup.show();
                           }

                           OpenLayers.Event.stop(evt);

                       }
                  );
               }
             }
            });


        }


        function createPopupForHotspot( hsId, icon ) {

           var hs = hotspots[ hsId ];

           var pos = getChild( hs, 'pos' );
           var lon = getText( getChild( pos, 'lon' ) );
           var lat = getText( getChild( pos, 'lat' ) );
           var markPos = getLonLat(lon,lat);

           var hsName = getText( getChild( hs, 'name' ) );
           var hsDescription = getText( getChild( hs, 'description' ) );
           if ( ! hsDescription )
                hsDescription = ' ';

           var hsCreatedBy = getText( getChild( hs, 'createdBy')  );

           var popupContent = '';

           var defaultHsView = "<IFRAME id='frame_" + hsId + "' SRC='hscontent.do?hsId=" + hsId + "' frameborder='0' WIDTH='550' HEIGHT='450' ></IFRAME>";

           <logic:present name="focusHs" scope="request" >
               var theFocusedHs = <bean:write name="focusHs" scope="request" />;
               if ( hsId == theFocusedHs )
               {
                       <logic:present name="viewHsEditPage" scope="request" >
                   popupContent = "<IFRAME id='frame_" + hsId + "' SRC='viewhsedit.do?hsId=" + hsId + "' frameborder='0' WIDTH='550' HEIGHT='450' ></IFRAME>";
                       </logic:present>
                       <logic:notPresent name="viewHsEditPage" scope="request" >
                   popupContent = defaultHsView ;
                       </logic:notPresent>
               }
               else
               {
                   popupContent = defaultHsView ;
               }
           </logic:present>

           <logic:notPresent name="focusHs" scope="request" >
           popupContent = defaultHsView ;
           </logic:notPresent>


           var myPopup = new OpenLayers.Popup.FramedCloud( hsId,
                                    markPos,
                                    new OpenLayers.Size(700,500),
                                    popupContent,
                                    icon,
                                    true,null
           );

           //myPopup.panMapIfOutOfView = false;
           //myPopup.autoSize = false;
           myPopups[ hsId ] = myPopup ;
           map.removePopup( myPopup );
           map.addPopup( myPopup );


        }




        function nuevaVentana( url ) {
            window.open( url,'popup', 'resizable=yes,menubar=no,location=no,toolbar=no,status=no,scrollbars=yes,directories=no,width=550,height=430,left=80,top=50');
        }


        function checkedLayer( isDisplayed, layerId ) {

            var myLayers = map.getLayersByName(layerNames[layerId]);
            var myLayer = myLayers[0];

            //myLayer.setVisibility( isDisplayed );

            //Look for all checkbox elements under this layer, and set the same visibility as the layer
            var forma = document.forms['LayersForm'];
            var element = null;
            var elementName = null;
            var patternStr = 'layer_' + layerId ;
            var pattern = new RegExp( patternStr,'');
            for ( var i = 0; i < forma.elements.length; i++ )
            {
                element = forma.elements[i];
                elementName = element.name ;
                //alert( elementName );
                if ( elementName.match( pattern) )
                {
                   //alert( elementName );
                   element.checked = isDisplayed ;
                }

            }


            // Setting the layer visibility is enough, but as the visibility of categories and keys
            // is managed by the "display" attribute of each hotspot, then set that attribute as well
            // to be in synch with the categories and keywords visibility settings
            for ( var hs in hotspots )
            {
                hs = hotspots[hs];
                var hsId = getText( getChild( hs, 'id') );
                var hsLayerId = getText( getChild( hs, 'layer') );

                hsLayerId = parseInt( hsLayerId );

                if ( layerId == hsLayerId )
                {
                    markers[hsId].display( isDisplayed );
                }

            }



        }

        function refreshAllLayers() {

          myPopups = [];

          <logic:iterate name="layers" id="layer" scope="request" type="Layer" >
              refreshLayer( '<%=layer.getLayerId()%>', '<%=layer.getLayerName()%>', layerIcons['<%=layer.getLayerId()%>']);
          </logic:iterate>
        }

        function searchHotspots() {
            var forma = document.forms['QueryForm'];
            var general = forma.general.value;
            var mapid = forma.mapid.value;
            var params = 'general=' + general + '&mapid=' + mapid;
            var t = document.getElementById('queryResultsTable');

            var http_request = createRequest();

            makePOSTRequest( http_request, 'listhotspots.do' , params, function() {
                if (http_request.readyState == 4)
                {
                    if (http_request.status == 200)
                    {
                       var xmlDoc = http_request.responseXML;
                       var root = xmlDoc.documentElement;
                       var hsNodes = getChildren( root, 'hotspot' );

                       // Delete all rows in the query results table
                       while ( t.rows.length >  0 )
                              t.deleteRow( 0 );

                       var numMatches = hsNodes.length;
                       var divQueryMessage = document.getElementById('divQueryMessage');
                       if ( numMatches == 1 )
                           divQueryMessage.innerHTML = "<u>(" + general + ') ' + numMatches + ' match found:</u>';
                       else
                           divQueryMessage.innerHTML = "<u>(" + general + ') ' + numMatches + ' matches found:</u>';
                       showLayer( 'divQueryMessage' );

                       for(var k = 0; k < numMatches; k++ )
                       {
                           var hs = hsNodes[k];

                           var hsId = getText( getChild( hs, 'id'));

                           var row = t.insertRow(-1);

                           var hsName = getChild( hs, 'name' );
                           hsName = getText( hsName );

                           var hsNameCell = row.insertCell(-1);
                           hsNameCell.innerHTML = "<span style='cursor: pointer;' onclick='focusHotspot(" + hsId + ")' >" + hsName + "</span>";


                       }
                       showLayer('divQueryResults');

                    }
                    else
                    {
                        alert('There was a problem with the request.');
                    }
                }
            });

        }

        function centerMap( lon, lat, newZoom ) {
            var newCenter = getLonLat( lon, lat ) ;
            map.setCenter( newCenter, newZoom );
        }

        function focusHotspot( hsId ) {
           var hs = hotspots[ hsId ];

           var pos = getChild( hs , 'pos' );
           var lon = getText( getChild(pos,'lon'));
           var lat = getText( getChild(pos,'lat'));

           //centerMap( lon, lat, map.getZoom() );

           // Find my popup
           var myPopup = getPopup( hsId );

           if ( ! myPopup )
           {
               createPopupForHotspot( hsId, layerIcons[hsId] );
           }
           else
           {
               myPopup.show();
           }



        }

        function getPopup( myId ) {

           var popups = map.popups;
           var myPopup = null;
           for ( var i = 0; i < popups.length; i++ )
           {

               var pop = popups[i];

               if ( pop.id == myId )
               {
                   myPopup = pop;
                   foundIt = true;
                   break;
               }
           }
           return myPopup;
        }



        function changeLayer( lay ) {

            lay = parseInt( lay );
            map.setBaseLayer( map.layers[lay] );
            var forma = document.forms['GoogleForm'];
            setSelectValue( forma.layer, lay );


            var backmap = baseMapsNames[lay];
            executeAjax('setbasemap.do', 'mapid=<bean:write name="map" property="mapid" />&backmap=' + backmap, null );

            redrawLayers();

        }


        function validatePoint( lon, lat ) {
            var myPlace = getLonLat(lon,lat);
            return myExtent.containsLonLat( myPlace );
        }

        function toggleCategory(isDisplayed, layerId, catId ) {

            //Look for keywords on this cat, on this layer
            var forma = document.forms['LayersForm'];
            var element = null;
            var elementName = null;
            var patternStr = 'layer_' + layerId + '_cat_' + catId + '_key_' ;
            var pattern = new RegExp( patternStr,'');
            //alert( pattern );

            // Find all keywords under this category and set the same
            // visibility as this category
            for ( var i = 0; i < forma.elements.length; i++ )
            {
                element = forma.elements[i];
                elementName = element.name ;
                //alert( elementName );
                if ( elementName.match( pattern) )
                {
                   //alert( elementName );
                   element.checked = isDisplayed ;
                }

                // If category is visible, then check the layer box
                if ( isDisplayed == true )
                {
                    if ( elementName == 'layer_' + layerId )
                         element.checked = isDisplayed ;

                    var myLayers = map.getLayersByName(layerNames[layerId]);
                    var myLayer = myLayers[0];
                    myLayer.setVisibility( isDisplayed );

                }
            }


            layerId = parseInt( layerId );
            catId = parseInt( catId );
            for ( var hs in hotspots )
            {
                //if ( isIE() )
                    hs = hotspots[hs];
                var hsId = getText( getChild( hs, 'id') );
                var hsLayerId = getText( getChild( hs, 'layer') );
                var hsCatId = getText( getChild( hs, 'catId' ) );

                hsLayerId = parseInt( hsLayerId );
                hsCatId = parseInt( hsCatId );

                //    alert( hsId + ' ' + hsLayerId + ' ' + hsCatId );

                //markers[hsId].display( isDisplayed );
                if ( layerId == hsLayerId && hsCatId == catId )
                {
                    markers[hsId].display( isDisplayed );
                }

            }

        }

        function redrawLayers() {
            var la = null;
            for( var i = 0; i < map.layers.length; i ++ )
            {
                la = map.layers[i];
                la.redraw();
            }
        }

        function toggleKeyword(isDisplayed, layerId, kwId, catId ) {


            // If the keyword is displayed, check its category and layer boxes
            // and make the layer visible
            if ( isDisplayed )
            {
                // Get the category checkbox of this keyword
                // If the keyword doesn't have a category, then
                // myCatElement = null
                var forma = document.forms['LayersForm'];
                var chckName = 'layer_' + layerId + '_cat_' + catId ;
                var chck = document.getElementsByName( chckName );
                var myCatElement = null;
                if ( chck != null && chck.length > 0 )
                {
                    myCatElement = chck[0];
                    myCatElement.checked = isDisplayed ;
                }

                // Check layer box
                var myLayerElements = document.getElementsByName( 'layer_' + layerId );
                if ( myLayerElements != null && myLayerElements.length > 0 )
                   myLayerElements[0].checked = isDisplayed ;

                // Make layer visible
                var myLayers = map.getLayersByName(layerNames[layerId]);
                var myLayer = myLayers[0];
                myLayer.setVisibility( isDisplayed );



            }

            // Iterates hotspots
            layerId = parseInt( layerId );
            kwId = parseInt( kwId );
            for ( var hs in hotspots )
            {
                hs = hotspots[hs];
                var hsId = getText( getChild( hs, 'id') );
                var hsLayerId = getText( getChild( hs, 'layer') );
                var hsKwId = getText( getChild( hs, 'keywordId' ) );

                hsLayerId = parseInt( hsLayerId );
                hsKwId = parseInt( hsKwId );

                //alert( hsId + ' ' + hsLayerId + ' ' + layerId + ' ' + hsKwId + ' ' + kwId);

                if ( layerId == hsLayerId && hsKwId == kwId )
                {
                    markers[hsId].display( isDisplayed );
                }

            }


        }



</script>



<form name="GoogleForm" action="#" >
<table width="963" border="0">
  <tr>
    <td width="378">&nbsp;</td>
  <td width="260">&nbsp;</td>
    <td width="311">
    <div align="right" >
    <span class="Estilo8"  >Background Map</span>

      <select name="layer" onchange="changeLayer( getSelectedValue(this) )" >
        <option value="0" >Google Hybrid</option>
        <option value="1" >Google Streets</option>
        <option value="2" >Google Satellite</option>
        <option value="3" >Google Physical</option>
        <option value="4" >OpenStreetMap</option>
      </select>


      </div>
      </td>
  </tr>
</table>

</form>

<div id="blanket" style="display:none;"></div>
<div id="popUpDiv" style="display:none;">

</div>


<div id='map'class='fullmap' >
    <div id="labelHolder" style="position: relative" >
        <div style=" width: 120px; z-index: 5000; border: silver; border-style: solid; border-width: medium; display:none; position:absolute; background-color: #ffffcc; padding:1px 7px 1px 7px" id="hs_info" >
    </div>
    </div>
</div>


<!-- Legend ________________________________________________________________ -->
<br/>
<logic:notEqual name="map" property="iconsMode" value="4" >
<div style="background-color:#eeeeee;padding: 10px 10px 10px 10px;border-width:thin;border-style:solid;border-color:#000000;width:940px" >

 <span class="Estilo4" ><b>LEGEND:</b><br/></span>

 <br/>
            <logic:present name="map" property="iconsMode" >

                <logic:equal name="map" property="iconsMode" value="1" >
            <table  border="0" >
              <logic:iterate name="layers" type="Layer" id="layer" scope="request" >
              <tr>
                <td width="111"  class="Estilo1" ><bean:write name="layer" property="layerName" /></td>
              <td width="32"  class="Estilo1" >
                   <img src="<easy:GetObjectTag id="<%=layer.getIconId()%>" />" width="20" height="20" />
              </td>
              </tr>
                </logic:iterate>
            </table>
              </logic:equal>

                <!-- Icons assigned to keywords ________________________________________________ -->
                <logic:equal name="map" property="iconsMode" value="3" >

            <table  border="0" >
            <logic:empty name="catmap" >
              <logic:iterate name="keywords" type="Keyword" id="key" scope="request" >
              <tr>
                <td width="111"  class="Estilo1" ><bean:write name="key" property="kwValue" /></td>
              <td width="32"  class="Estilo1" >
                   <img src="<easy:GetObjectTag id="<%=key.getIconId()%>" />" width="20" height="20" />
              </td>
              </tr>
                </logic:iterate>
            </table>
            </logic:empty>

              <logic:notEmpty name="catmap" >
                  <table border="0" width="100%" >
                      <tr>
<logic:iterate name="catmap" id="k" type="java.util.Map.Entry<Category,List<Keyword>>" scope="request" >

<bean:define name="k" property="key" id="cat" type="Category" />
<bean:define name="k" property="value" id="keywords" type="List<Keyword>" />
<logic:notEmpty name="keywords" >


    <td>
    <table border="0" >
    <tr>
        <td class="Estilo8" >
            <bean:write name="cat" property="catName" />
        </td>
        <td width="111px" ></td>
        <td width="32px" ></td>
    </tr>
    <logic:iterate name="keywords" id="key" type="Keyword" >
    <tr>
        <td></td>
        <td>
            <bean:write name="key" property="kwValue" />
        </td>
        <td >
           <img src="<easy:GetObjectTag id="<%=key.getIconId()%>" />" width="20" height="20" />
        </td>
    </tr>
    </logic:iterate>
    </table>
                          </td>
</logic:notEmpty>
</logic:iterate>

                      </tr>

                  <logic:notEmpty name="_nocats_" >
                      <logic:iterate name="_nocats_" id="key" type="Keyword" >
    <tr>
        <td width="200px" >
            <bean:write name="key" property="kwValue" />
        </td>
        <td >
           <img src="<easy:GetObjectTag id="<%=key.getIconId()%>" />" width="20" height="20" />
        </td>
    </tr>
                      </logic:iterate>
                  </logic:notEmpty>

                  </table>

              </logic:notEmpty>


              </logic:equal>



                <logic:equal name="map" property="iconsMode" value="2" >
            <table  border="0" >
              <logic:iterate name="cats" type="Category" id="cat" scope="request" >
              <tr>
                <td width="111"  class="Estilo1" ><bean:write name="cat" property="catName" /></td>
              <td width="32"  class="Estilo1" >
                   <img src="<easy:GetObjectTag id="<%=cat.getIconId()%>" />" width="20" height="20"  />
              </td>
              </tr>
                </logic:iterate>
            </table>
              </logic:equal>

            </logic:present>

</div>
</logic:notEqual>
<!-- End Legend ____________________________________________________________ -->

<div id="transparent_blanket" style="display:none;"></div>
<div id="popUpMessageDiv" style="display:none;background-color:#eeeeee;" >
<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
<div style=" border-color: black; border-width: thick;background-color:#eeeeee;padding: 0px 20px 0px 20px" >
<logic:present name="no_edit" scope="request" >


<p align="center" >
<br/>
This is a preview of the changes you have just entered.  Click "Back" to return to the previous step, or "Next" to accept this configuration and continue
    <br/>
    <br/>
<table  border="0" width="100%">
  <tr>
    <td  class="Estilo4">
        <a
            href="newmap.do?nav=back"
        >
        <div align="right" class="Estilo12">
            &lt; Back
        </div>
        </a>
    </td>
    <td width="100px" ></td>
    <td  class="Estilo4">
        <a
            <logic:equal name="<%=Constantes.NEW_MAP_STATUS%>" value="1" >
            href="newmap.do?status=3"
            </logic:equal>
            <logic:equal name="<%=Constantes.NEW_MAP_STATUS%>" value="3.1" >
            href="newmap.do?status=4"
            </logic:equal>
            <logic:equal name="<%=Constantes.NEW_MAP_STATUS%>" value="4.1" >
            href="newmap.do?status=5"
            </logic:equal>

            <logic:equal name="<%=Constantes.NEW_MAP_STATUS%>" value="6.9" >
            href="newmap.do?status=7"
            </logic:equal>

            <logic:equal name="<%=Constantes.NEW_MAP_STATUS%>" value="7.5" >
            href="newmap.do?status=8"
            </logic:equal>

        >
        <div align="left" class="Estilo12">Next &gt;</div>
        </a>
    </td>
  </tr>
</table>

</logic:present>
</div>
<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b>
	</div>




<script type="text/javascript" >

    var treeExpanded = false;

    function collapseAll() {
        clickOnNodeObj(foldersTree);
        clickOnNodeObj(foldersTree);
    }

    function expandAll() {
        expandTree(foldersTree);
    }

    function expandTree(folderObj) {
       var childObj;
       var i;

       // Open the folder
       if (!folderObj.isOpen)
          clickOnNodeObj(folderObj)

       // Call this function for all children
       for (i=0 ; i < folderObj.nChildren; i++)
       {
           childObj = folderObj.children[i]
           if (typeof childObj.setState != "undefined")
           { // If this is a folder
              expandTree(childObj)
           }
       }
    }

    function toggleTree() {
        //alert( treeExpanded );
        var treeButton = document.getElementById('treeButton');

        if ( treeExpanded )
        {
           collapseAll();
           treeButton.src = 'images/circle_arrow_right.png' ;
        }
        else
        {
            expandAll();
            treeButton.src = 'images/circle_arrow_down.png' ;
        }

        treeExpanded = !treeExpanded ;


    }


    function myInit() {

     //alert('in init');
     collapseAll();

<logic:present name="map" property="leftBottom" >
    init();

    fullExtent();

    <logic:notPresent name="no_edit" scope="request" >
     refreshAllLayers();

     var http_request = createRequest();

     var params =  'mapid=<bean:write name="map" property="mapid" />';

     makePOSTRequest( http_request, 'getbasemap.do' , params, function() {
     if (http_request.readyState == 4 && http_request.status == 200 )
     {
       var xmlDoc = http_request.responseXML;
       var root = xmlDoc.documentElement;
       var estado = getChild( root, 'estado' );
       if ( estado && getText(estado) == 'OK' )
       {
           var xmlDoc = http_request.responseXML;
           var root = xmlDoc.documentElement;
           var backmap = getText(getChild( root, 'mensaje' ));
           initialMap = baseMapsIndex[backmap];
           changeLayer( initialMap );
       }
       else if ( estado && getText(estado) == 'FAIL' )
       {
            initialMap = parseInt( baseMapsIndex['<bean:write name="map" property="backmap" />'] ) ;
            changeLayer( initialMap );
       }

     }
     });
    </logic:notPresent>
    <logic:present name="no_edit" scope="request" >
    initialMap = parseInt( baseMapsIndex['<bean:write name="map" property="backmap" />'] ) ;
    changeLayer( initialMap );
    </logic:present>


    <logic:present name="focusHs" scope="request" >
    setTimeout( 'focusHotspot( <bean:write name="focusHs" scope="request" /> )',
    2000
    );
    </logic:present>


</logic:present>
    }


    window.onload = myInit ;

</script>






<logic:present name="no_edit" scope="request" >

        <script language="javascript" >
            popupMessage('popUpMessageDiv');
        </script>

</logic:present>

<logic:present name="mapMode" scope="request" >
<logic:equal name="mapMode" value="newHs" >
  <logic:present name="new_hotspot" property="hsPos"  >

    <bean:define id="newHsPos" name="new_hotspot" property="hsPos" scope="session" toScope="page" type="com.inga.utils.Point" />
<script language="javascript" >
  var currentLon = <bean:write name="currentCenter" property="x" />;
  var currentLat = <bean:write name="currentCenter" property="y" />;
  var currentZoom = <bean:write name="currentZoom" /> ;

  centerMap( currentLon, currentLat, currentZoom );

  var hsPos = getLonLat( <bean:write name="newHsPos" property="x" />, <bean:write name="newHsPos" property="y" /> );
  drawRedDot( hsPos );
  showGpsForm();

   var forma = document.forms['NewHotspotForm'];

   forma.lon.value = <bean:write name="newHsPos" property="x" />;
   forma.lat.value = <bean:write name="newHsPos" property="y" />;
   var valid = validateCoords();
   enableAcceptButton( valid );


</script>
    </logic:present>



</logic:equal>
</logic:present>


  </tiles:put>
</tiles:insert>
