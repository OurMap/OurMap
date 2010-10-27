<%-- ***************************************************************************

newmap_3.jsp
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
  <tiles:put name="header" value="newmap_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="new_map_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >
<!-- CONTENT HERE _________________________________________________________________ -->

<bean:define id="map" name="new_map"  scope="session" toScope="page" type="com.bnmi.ourmap.model.Map" />


<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false" ></script>
<script type="text/javascript" src="lib/OpenLayers.js" ></script>
<script type="text/javascript" src="maplibs/OpenStreetMap.js"></script>
<script type="text/javascript" src="ajax.js" ></script>
<script type="text/javascript" src="ourmap.js" ></script>

<script type="text/javascript">

        var geocoder = new google.maps.Geocoder();
        
        function showAddress(address) {

          if ( geocoder )
          {
              geocoder.geocode( {'address': address }, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    var lng = results[0].geometry.location.lng();
                    var lat = results[0].geometry.location.lat();
                    var point = getLonLat( lng, lat );
                    map.setCenter( point, 10 );
                }
              });
          }
          else
          {
            //niente
            //alert('error');
          }

        }

        <logic:notPresent name="map" property="leftBottom" >
            center = getLonLat(-98.26171875, 50.680797145322);
            zoom = 4;
        </logic:notPresent>
        <logic:present name="map" property="leftBottom" >
            center = getLonLat(<%=map.getCenter().getPrintX()%>,<%=map.getCenter().getPrintY()%> );
            zoom = <%=map.getZoom()%>;
        </logic:present>


        var savedExtent;
        var savedCenter;
        var previewMap = null;

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
            var gphy = new OpenLayers.Layer.Google(
                "Google Physical",
                {type: google.maps.MapTypeId.TERRAIN, 'sphericalMercator': true }
            );

            var gmap = new OpenLayers.Layer.Google(
                "Google Streets",
                {'sphericalMercator': true}
            );

            var gsat = new OpenLayers.Layer.Google(
                "Google Satellite",
                {type: google.maps.MapTypeId.SATELLITE, 'sphericalMercator': true, numZoomLevels: 22}
            );

            var ghyb = new OpenLayers.Layer.Google(
                "Google Hybrid",
                {type: google.maps.MapTypeId.HYBRID, 'sphericalMercator': true }
            );



            // create OSM layer
            var mapnik = new OpenLayers.Layer.OSM();

            // create a vector layer for drawing
            var vector = new OpenLayers.Layer.Vector("Editable Vectors");

            map.addLayers([
                            ghyb,
                            gmap,
                            gsat,
                            gphy,
                            mapnik,
                            vector

                        ]);

        }

        function getLonLat( lon, lat ) {
            return new OpenLayers.LonLat(lon,lat).transform(
            pro2,
            pro1
            );
        }


        function searchAddress() {
            var forma = document.forms['NewMapForm'];
            var address = forma.address.value;
            showAddress( address );
        }


        function zoomOut( sp ) {
            sp.style.color= '#ff0000';
            map.zoomTo( map.getZoom() - 1 );
        }

        function zoomIn( sp ) {
            sp.style.color= '#ff0000';
            map.zoomTo( map.getZoom() + 1 );
        }


        function restoreSpan( sp ) {
            sp.style.color= '#000000';
        }


        function saveThis() {



            savedExtent = map.getExtent();

            savedCenter = map.getCenter();

            var forma = document.forms['NewMapForm'];

                savedExtent.transform(pro1,pro2);
                savedCenter.transform(pro1,pro2);

            var bbox = savedExtent;
            forma.bbox.value = bbox;
            forma.zoom.value = map.getZoom();
            forma.center.value = savedCenter.toShortString();

            var lay = getSelectedValue( forma.layer );
            forma.backmap.value = baseMapsNames[ lay ];

            //alert( forma.backmap.value );
            forma.action = 'newmap.do';

            forma.submit();

        }

        function changeLayer( lay ) {
            lay = parseInt( lay );
            map.setBaseLayer( map.layers[lay] );
            var forma = document.forms['NewMapForm'];
            setSelectValue( forma.layer, lay );
        }


        function showInfo() {
            var extents = map.getExtent();
            alert( extents + ' \n' + extents.toBBOX() + '\nzoom=' + map.getZoom() );
        }

        function initPage() {
            init();
            try
            {
                initialMap = parseInt( baseMapsIndex['<bean:write name="map" property="backmap" />'] ) ;
            }
            catch ( e ) {}

            changeLayer( initialMap );
            fullExtent();

        }

        window.onload = initPage ;

</script>


<div id="googleDiv" >
<p>
<form name="NewMapForm" action="javascript:searchAddress()" >
  <input type="hidden" name="bbox" />
  <input type="hidden" name="zoom" />
  <input type="hidden" name="center" />
  <input type="hidden" name="backmap" />
  <input type="hidden" name="status" value="3.1" />

  <span class="Estilo6" >SET THE MAP BOUNDARIES</span></p>

<br/>
<br/>
<div align="justify" class="Estilo1">
Now you need to decide where the edges of your map will be, and what area it will cover. Note that your map users will ONLY be able to plot map data INSIDE THIS AREA, so make sure that your map boundaries cover the entire area you want to map.
<ul>
    <li>
	Use the blue zoom control (+/-) to zoom the map.
    </li>
    <li>
	To pan the map, use your mouse to click and drag the map image.
    </li>
    <li>
	You can also use the "Location Search" tool to type in the name of a specific place you want to zero in on, and then hit GO to find it.
    </li>
    <li>
        Use the Background Map tool to view different map styles.
    </li>
</ul>
Once you have panned and zoomed the map view window so that its outside edges cover the exact area you want to map, and chosen which style of background map best suits your project, then scroll to the bottom of the screen and press "Save".


</div>
<p class="Estilo1">&nbsp;</p>
      <div id="asdf" style="height:25px; vertical-align: middle; display: table-cell;" width="100%" >
            <table width="100%" border="0">

<tr>
                              <td width="16">&nbsp;</td>
            <td width="406" class="Estilo1" >
                Location Search
                <input type="text" name="address" />
              <button type="button"  onclick="searchAddress()" >GO</button>                </td>


              <td width="98" valign="center">               </td>
      <td width="99">

      </td>
              <td width="83">&nbsp;</td>
        <td width="468" >
                  <span class="Estilo8"  >Background Map</span>

        <select name="layer" onchange="changeLayer( getSelectedValue(this) )" >
        <option value="0" >Google Hybrid</option>
        <option value="1" >Google Streets</option>
        <option value="2" >Google Satellite</option>
        <option value="3" >Google Physical</option>
        <option value="4" >OpenStreetMap</option>
        </select>

        </td>
              </tr>
            </table>
            </div>
</form>
</div><div id='map' class='fullmap' ></div>
<br/>
<button type="button" onclick="document.location='newmap.do?status=0'">Back</button>
<button type="button" onclick="saveThis()">Save</button>






<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>
<tiles:put name="sidearea" type="string" ></tiles:put>
</tiles:insert>

