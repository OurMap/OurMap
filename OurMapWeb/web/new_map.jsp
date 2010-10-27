<%-- ***************************************************************************

new_map.jsp
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
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<script src="ajax.js"></script>
<link href="styles/nifty.css" rel="stylesheet" type="text/css" />

<link href="styles/easy.css" rel="stylesheet" type="text/css" />


<tiles:insert template="layout.jsp">
<div id="mainPageDiv" >
  <tiles:put name="header" value="header.jsp" type="page" />
  <tiles:put name="leftMenu" value="project_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >

                <!-- CONTENT HERE _________________________________________________________________ -->

<bean:define id="project" name="project"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Project" />

<script src='http://maps.google.com/maps?file=api&amp;v=2&amp;key=<%=Constantes.googleKey%>'></script>
  <script type="text/javascript" src="/lib/OpenLayers.js" />
<script type="text/javascript" src="maplibs/OpenStreetMap.js"></script>
<script type="text/javascript" src="maplibs/yahoo.js"></script>
<script type="text/javascript" src="maplibs/bing.js"></script>


<script type="text/javascript">

        var map = null;

        var pro1 = new OpenLayers.Projection("EPSG:900913");
        var pro2 = new OpenLayers.Projection("EPSG:4326");

        var geocoder = new GClientGeocoder();

        var isOsm = false;
        var center = getLonLat(-98.26171875, 50.680797145322);
        var osmCenter = getLonLat(-98.26171875, 50.680797145322);
        osmCenter.transform(pro2,pro1);
        var zoom = 4;

        var savedExtent;
        var savedZoom;
        var savedCenter;
        var previewMap = null;

        var initialMap = 5;
        var layIndex = initialMap;


        function init() {

            map = new OpenLayers.Map ("map", {
                controls:[
                    new OpenLayers.Control.Navigation(),
                    new OpenLayers.Control.ZoomPanel(),
                    new OpenLayers.Control.Attribution()

					],
                units: 'm',
                projection: pro1,
                displayProjection: pro2
            } );
            
            var wms = new OpenLayers.Layer.WMS( "OpenLayers WMS",
                      "http://labs.metacarta.com/wms/vmap0",
                      {layers: 'basic'} );


            var gphy = new OpenLayers.Layer.Google(
                "Google Physical",
                {type: G_PHYSICAL_MAP }
            );
            var gmap = new OpenLayers.Layer.Google(
                "Google Streets", // the default
                {}
            );
            var ghyb = new OpenLayers.Layer.Google(
                "Google Hybrid",
                {type: G_HYBRID_MAP }
            );
            var gsat = new OpenLayers.Layer.Google(
                "Google Satellite",
                {type: G_SATELLITE_MAP}
            );

            var shaded = new OpenLayers.Layer.VirtualEarth("Shaded", {
                type: VEMapStyle.Shaded
            });
            var hybrid = new OpenLayers.Layer.VirtualEarth("Hybrid", {
                type: VEMapStyle.Hybrid
            });
            var aerial = new OpenLayers.Layer.VirtualEarth("Aerial", {
                type: VEMapStyle.Aerial
            });

            var yahoo = new OpenLayers.Layer.Yahoo("Yahoo");

            map.addLayers([shaded, hybrid, aerial, gphy, gmap, ghyb, gsat, yahoo ]);

            map.setCenter( center, zoom );


        }

        function getLonLat( lon, lat ) {
        if ( isOsm )
        {
            return new OpenLayers.LonLat(lon,lat).transform(
            pro2,
            pro1
            );
        }
        else
            return new OpenLayers.LonLat(lon,lat);
        }

        function toggleOsm() {

                zoom = map.getZoom();

                if ( isOsm )
                {
                    osmCenter = map.getCenter();
                    center = map.getCenter();
                    center.transform( pro1, pro2 );
                }
                else
                {
                    center = map.getCenter();
                    osmCenter = map.getCenter();
                    osmCenter.transform( pro2, pro1 );
                }

                map.destroy();
                
                isOsm = ! isOsm;

                if ( isOsm )
                    showOsm();
                else
                    init();

        }

        function showOsm() {


	    isOsm = true;

            map = new OpenLayers.Map ("map", {
                controls:[

                    new OpenLayers.Control.Navigation(),
                    new OpenLayers.Control.ZoomPanel(),
                    new OpenLayers.Control.Attribution()
		    
					],
                units: 'm',
                projection: pro1,
                displayProjection: pro2
            } );

            var tah = new OpenLayers.Layer.OSM.Osmarender("Open Street Map");
            
            map.addLayer( tah );

            map.setCenter( osmCenter, zoom );
        }

        function test() {
            alert( map.getExtent() );
            alert( map.getCenter() );
        }

        function showPreviewMap() {
            savedExtent = map.getExtent();
            savedZoom = map.getZoom();
            savedCenter = map.getCenter();
            //toStep(3);
        }

        function onBaseMapOptionSelected() {

            var forma = document.forms['BaseOptionsForm'];
            var val = getCheckedValue( forma.baseMapOption );
            val = parseInt( val );

            switch (val)
            {
                case 1:

                    showLayer('googleDiv');
                    showLayer('map');
                    showLayer('buttonsDiv');
                    if ( !map )
                    {
                        init();
                        changeLayer( layIndex );
                    }

                    break;
                case 2:
                    showLayer('existingBaseDiv');
                    break;
                case 3:
                    showLayer('uploadMapDiv');
                    break;
            }
            
        }

        function toStep(step) {

            step = parseInt( step );
            
            // States of the creation -------------------------------------------
            switch ( step )
            {
                // INITIAL SCREEN, MAP NAME, SUBTILE, DESCRIPTION
                case 1:
                    //alert('case 1');
                    showLayer('mainPageDiv');
                    showLayer('step1');
                    hideLayer('step3Div');
                    hideLayer('previewMapDiv');
                    hideLayer('previewPageDiv');
                    hideLayer('previewMapTitle');
                    hideLayer('googleDiv');
                    hideLayer('map');
                    hideLayer('buttonsDiv');
                    hideLayer( 'baseOptionsDiv' );
                    hideLayer( 'niftyFloat' );
                    
                    break;
                case 2:
                    var forma = document.forms['Step1Form'];
                    var mapname = forma.mapname.value;
                    if ( !validarCadena( mapname) )
                    {
                        alert('Enter a map title');
                        return;
                    }

                    showLayer('mainPageDiv');
                    hideLayer('step1');
                    hideLayer('googleDiv');
                    hideLayer('map');
                    hideLayer('buttonsDiv');
                    hideLayer( 'niftyFloat' );
                    hideLayer('previewPageDiv');

                    showLayer('baseOptionsDiv');
 
                    var mapname = document.forms['Step1Form'].mapname.value;
                    var mapnameDiv = document.getElementById('mapnameDiv');
                    var mapnameDiv2 = document.getElementById('mapnameDiv2');
                    mapnameDiv.innerHTML = mapname;
                    mapnameDiv2.innerHTML = mapname;


                    var forma2 = document.forms['NewMapForm'];
                    changeLayer( getSelectedValue(forma2.layer) );

                    break;
                case 3:
                    showLayer('mainPageDiv');
                    hideLayer('googleDiv');
                    hideLayer('map');
                    hideLayer('buttonsDiv');
                    showLayer('previewMapTitle');
                    showLayer('step3Div');
                    showLayer('previewMapDiv');
                    hideLayer( 'niftyFloat' );
                    hideLayer('previewPageDiv');

                    var forma2 = document.forms['NewMapForm'];
                    layIndex = parseInt( getSelectedValue(forma2.layer) );

                    if ( previewMap )
                        previewMap.destroy();

                    if ( layIndex == 8 )
                        initOsmPreview();
                    else
                    {
                        initPreview();
                        changeLayer( layIndex );
                    }



                    break;

                case 5:
                    showLayer('mainPageDiv');
                    hideLayer( 'niftyFloat' );
                    hideLayer('baseOptionsDiv');
                    hideLayer('previewMapDiv');
                    hideLayer('step3Div');
                    hideLayer('previewMapTitle');
                    hideLayer('previewPageDiv');

                    onBaseMapOptionSelected();
                    break;
                case 6:
                    
                    var forma = document.forms['Step1Form'];
                    var mapname = forma.mapname.value;
                    if ( !validarCadena( mapname) )
                    {
                        alert('Enter a map title');
                        return;
                    }

                    hideLayer('mainPageDiv');
                    var forma = document.forms['Step1Form'];
                    var params = "no_edit=yes&pid=<%=project.getId()%>&" +
                                 "mapname=" + forma.mapname.value + "&" +
                                 "subtitle=" + forma.subtitle.value + "&" +
                                 "description=" + forma.description.value;
                    //alert( params );

                    showLayer('previewPageDiv');
                    showLayer('niftyFloat');
                    centerNifty();
                    executeAjax( 'viewmap.do', params, 'previewPageDiv' );
                    break;
                default:
                    break;
            }



        }


        function showAddress(address) {
          geocoder.getLatLng(
            address,
            function(point) {
              if (!point)
              {
                //alert(address + " not found");
              }
              else
              {
                center = getLonLat( point.lng(), point.lat() );
                osmCenter = getLonLat( point.lng(), point.lat() );

                if ( isOsm )
                    map.setCenter( osmCenter,10);
                else
                    map.setCenter( center, 10 );

              }
            }
          );
        }

        function searchAddress() {
            var forma = document.forms['NewMapForm'];
            var address = forma.address.value;
            showAddress( address );
        }

        function changeLayer( lay ) {

            lay = parseInt( lay );

            if ( lay == 8 )
            {
                if ( ! isOsm )
                    toggleOsm();
                isOsm = true;
            }
            else
            {
                if ( isOsm )
                    toggleOsm();
                map.setBaseLayer( map.layers[lay] );
                isOsm = false;
            }

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

        function initPreview() {

            previewMap = new OpenLayers.Map('previewMapDiv', {
                  controls:[],
                  maxResolution: "auto",
                  restrictedExtent: savedExtent
            });


        var wmsA = new OpenLayers.Layer.WMS( "OpenLayers WMS ",
                  "http://labs.metacarta.com/wms/vmap0",
                  {layers: 'basic'} );

        var gphyA = new OpenLayers.Layer.Google(
            "Google Physical a",
            {type: G_PHYSICAL_MAP }
        );
        var gmapA = new OpenLayers.Layer.Google(
            "Google Streets a", // the default
            {}
        );
        var ghybA = new OpenLayers.Layer.Google(
            "Google Hybrid a",
            {type: G_HYBRID_MAP }
        );
        var gsatA = new OpenLayers.Layer.Google(
            "Google Satellite a",
            {type: G_SATELLITE_MAP}
        );

        var shadedA = new OpenLayers.Layer.VirtualEarth("Shaded a", {
            type: VEMapStyle.Shaded
        });
        var hybridA = new OpenLayers.Layer.VirtualEarth("Hybrid a", {
            type: VEMapStyle.Hybrid
        });
        var aerialA = new OpenLayers.Layer.VirtualEarth("Aerial a", {
            type: VEMapStyle.Aerial
        });


        var yahooA = new OpenLayers.Layer.Yahoo("Yahoo a");

        previewMap.addLayers([shadedA, hybridA, aerialA, gphyA, gmapA, ghybA, gsatA, yahooA ]);


        //previewMap.setCenter( center, savedZoom );
        previewMap.zoomToMaxExtent();

        var forma = document.forms['NewMapForm'];
        var layIndex = getSelectedValue( forma.layer );

        previewMap.setBaseLayer( previewMap.layers[layIndex] );


        }

        function initOsmPreview() {

            previewMap = new OpenLayers.Map('previewMapDiv', {
                  controls:[],
                  maxResolution: "auto",
                  restrictedExtent: savedExtent,
                  projection: pro1,
                  displayProjection: pro2

            });

        var tahB = new OpenLayers.Layer.OSM.Osmarender("Open Street Map 2");

        previewMap.addLayers([ tahB ]);

        //previewMap.setCenter( savedCenter, savedZoom );
        previewMap.zoomToMaxExtent();


        }


        function saveThis() {

            showPreviewMap();

            var forma = document.forms['Step1Form'];

            if ( isOsm )
            {
                savedExtent.transform(pro1,pro2);
                savedCenter.transform(pro1,pro2);
            }

            var bbox = savedExtent.toBBOX();
            forma.bbox.value = bbox;
            forma.zoom.value = savedZoom;
            forma.center.value = savedCenter.toShortString();
            var lay = getSelectedValue( document.forms['NewMapForm'].layer );
            var backmap = "gphy";
            var layerNames =new Array( "shaded", "hybrid", "aerial", "gphy", "gmap", "ghyb", "gsat", "yahoo", "tah" );
            forma.backmap.value = layerNames[ lay ];

            forma.action = 'newmap.do';

            forma.submit();

        }

        function showPreview() {

            var forma = document.forms['Step1Form'];
            var w = window.open( 'about:blank','popup', 'resizable=yes,menubar=no,location=no,toolbar=no,status=no,scrollbars=yes,directories=no,width=550,height=430,left=80,top=50');
            forma.target = 'popup';
            forma.action = 'viewmap.do';
            
            forma.submit();

        }

        function centerNifty() {

            var niftyFloat = document.getElementById('niftyFloat');

            if ( isIE() )
            {
                //var height = document.body.offsetHeight;
                var width  = document.body.offsetWidth;
                var height = document.body.offsetHeight;
                
                var newH = height - ( height + niftyFloat.style.height ) / 2;
                var newW = width - ( width + niftyFloat.style.width) / 2 ;

                niftyFloat.style.top = newH + 'px';
                niftyFloat.style.left = newW + 'px';
                alert('ok');
            }
            else
            {
                var height = window.innerHeight;
                var width  = window.innerWidth;
                newH = height - ( height + niftyFloat.clientHeight ) / 2;
                newW = width - ( width + niftyFloat.clientWidth) / 2 ;
                niftyFloat.style.top = newH + 'px';
                niftyFloat.style.left = newW + 'px';

            }


        }

</script>
<link href="styles/easy.css" rel="stylesheet" type="text/css" />

<!-- Step 1 : Enter the map name -->
<style type="text/css">
</style>
<div id="step1" >
    <form name="Step1Form" action="#" >

<input type="hidden" name="formSubmited" value="yes" />
<input type="hidden" name="pid" value="<%=project.getId()%>" />

<input type="hidden" name="backmap" />
<input type="hidden" name="bbox" />
<input type="hidden" name="zoom" />
<input type="hidden" name="center" />
<input type="hidden" name="no_edit" value="yes" />

        <p><span class="Estilo6" >MAP TITLE AND DESCRIPTION</span><br/>
          <br/>
          <br/>
        </p>
        <table width="506" border="0">
          <tr>
            <td width="131" height="31">
           <span class="Estilo1" >Map Title</span>            </td>
            <td width="365">
             <logic:present name="mapname" scope="request" >
               <input type="text" name="mapname" value="<bean:write name="mapname" scope="request" />" />               </logic:present>
             <logic:notPresent name="mapname" scope="request" >
               <input type="text" name="mapname" value="" />
             </logic:notPresent>            </td>
          </tr>
          <tr>
            <td><span class="Estilo1">Subtitle</span></td>
            <td><input type="text" name="subtitle" />
                <br/><br/>
            </td>
          </tr>
          <tr>
            <td><span class="Estilo1">Description</span></td>
            <td><label>
              <textarea name="description" id="description" cols="45" rows="5"></textarea>
            </label></td>
          </tr>
        </table>
        <p><br/>
          <br/>
        </p>

        <br/>
        <br/>
        <button type="button" onclick="toStep(6)" >Next</button>
    </form>
<br/>
</div>

<div id="baseOptionsDiv" >
<form name="BaseOptionsForm" action="#" >
    <span class="Estilo6" >SET THE BACKGROUND MAP</span>
    <br/>
    <br/>
    <p>
        <span class="Estilo8" >CHOOSE YOUR BACKGROUND MAP</span>    </p>
    <br/>

    <p class="Estilo1" >
        <span class="Estilo1" >Next you need to choose what kind of Background Map to use, and then set your map's boundaries.
        <br/>
        </p>
    <p class="Estilo1" >&nbsp;</p>
    <p class="Estilo1" >Please choose an option:    </p>
    <p>
      <input type="radio" name="baseMapOption" checked="checked" value="1" >
        <span class="Estilo1">Use Google Maps/Open Layers as your background map
          </input>
        <br/>
        <input type="radio" name="baseMapOption" value="2" >
          Browse for other maps in the OurMap database </span><span class="Estilo9">(not currently available)
  </input>
  </span><span class="Estilo1"><br/>
  <input type="radio" name="baseMapOption" value="3" >
    Upload your own map to use as a background map </span><span class="Estilo9">(not currently available)</span><span class="Estilo1">
  </input>
  </span><br/>
  </p>
    <p>&nbsp;</p>
    <p class="Estilo1">NOTE TO USERS: </p>
    <p class="Estilo1">&nbsp;</p>
    <p align="justify" class="Estilo1">Your choice of a Background  Map will affect what kind of geographic detail your map shows. Also, some maps  do not cover the whole Earth. So it is important to select a Background Map  that will cover the region you want to map in the appropriate detail. </p>
    <p align="justify" class="Estilo1">&nbsp;</p>
    <p align="justify" class="Estilo1">We recommend option  1 (Google Maps / Open Layers) as the easiest option for getting started and  choosing your map boundaries, because this option provides up to 10 detailed background  maps to choose from. And these maps cover nearly the whole planet in a fair  amount of detail. So these maps should suit the purposes of most projects, at  least in cases where you don&rsquo;t need a specialized type of map feature detail. </p>
    <p align="justify" class="Estilo1">TIP:&nbsp; You can always use Google Maps to choose your  map boundaries, and then add or upload another Background Map later. You can  also change your map&rsquo;s boundaries later on if you need to refine them. </p>
    <p align="justify" class="Estilo1">&nbsp;</p>
    <p align="justify" class="Estilo1">Option 2 lets you browse  the OurMap database to find another background map that covers your region. We  have uploaded a number of maps here that may be useful if you are looking for a  map with a more specialized kind of detail for your area. (For example, a  geological map or map with lots of specialized biological information.)</p>
    <p align="justify" class="Estilo1">&nbsp;</p>
    <p align="justify" class="Estilo1">Finally, choosing  Option 3 will allow you to upload your own map to use as a background. In this  case, you will need to have a map saved on your computer in a compatible file  type (shapefile or kml). Note that if you do upload your own map in this way, it will then  be stored in our map database, and you can choose to make it available to other  users in the future. It will then be available for browsing in Option 2. </p>
    <p align="justify" class="Estilo1">&nbsp;</p>
    <p align="justify" class="Estilo1">Or, if you have a  scanned or hand-drawn map that is not yet calibrated with GPS coordinates, you  can also upload your map as a simple JPG image and then calibrate it manually to  establish the GPS coordinates. This will require some extra steps, and you will  need to go out and gather some GPS coordinates in your map area, so this option  is recommended only for advanced users. </p>
    <p class="Estilo1"><br/>
        </p>
    <button type="button" name="b1" onclick="toStep(1)" >Back</button>        
    <button type="button" name="b1" onclick="toStep(5)" >Next</button>
    
</form> 
</div>

<div id="googleDiv" >
    
<form name="NewMapForm" action="javascript:;" >

<p>
  <input type="hidden" name="formSubmited" value="yes" />
  <input type="hidden" name="bbox" />
  <input type="hidden" name="zoom" />
  <input type="hidden" name="center" />
  <input type="hidden" name="pid" value="<%=project.getId()%>" />
  <input type="hidden" name="backmap" />
  <input type="hidden" name="mapname"  />
  <input type="hidden" name="subtitle" />
  
  <span class="Estilo6" >SET THE BACKGROUND MAP</span></p>
<p>&nbsp;</p>
<p class="Estilo8">SET YOUR MAP&rsquo;S OUTER BOUNDARIES:</p>
<p class="Estilo8">&nbsp;</p>
<p align="justify" class="Estilo1">This will set the maximum area/zoom for your map. Map users will only be able to plot map data within these boundaries, so make sure they cover the entire area you want to map. </p>
<p align="justify" class="Estilo1">Select the desired view for your map below, and press &quot;Save&quot; when you are ready. </p>
<p align="justify" class="Estilo1">&nbsp;</p>
<p align="justify" class="Estilo1">Use the blue zoom control to zoom the map.  To pan the map, use your mouse to click and drag the map. </p>
<p align="justify"><span class="Estilo1">You can also use the search tool to type in the name of a specific place, and then hit GO to find it.</span><br />
</p>
<p class="Estilo1">&nbsp;</p>
      <div id="asdf" style="height:25px; vertical-align: middle; display: table-cell;" width="100%" >
            <table width="100%" border="0">
            
<tr>
                              <td width="16">&nbsp;</td>
            <td width="406">

                <input type="text" name="address" />
              <button type="button"  onclick="searchAddress()" >GO</button>                </td>


              <td width="98" valign="center">               </td>
      <td width="99">      </td>
              <td width="83">&nbsp;</td>                      
        <td width="468" >
                  <span class="Estilo8"  >Background Map</span>
<select name="layer" onchange="changeLayer( getSelectedValue(this) )" >
                      <option value="5" >Google Hybrid</option>
                      <option value="3" >Google Physical</option>
                      <option value="4" >Google Streets</option>
                      <option value="6" >Google Satellite</option>
                      <option value="7" >Yahoo Maps</option>
                      <option value="8" >Open Streets Maps</option>
                      <option value="0" >Bing Shaded</option>
                      <option value="1" >Bing Hybrid</option>
                      <option value="2" >Bing Aerial</option>
                  </select>                  </td>
              </tr>
            </table>
            </div>
</form>
</div><div id='map' class='fullmap' style="display:none"></div>

<div id="buttonsDiv" > 
    <p>
      <br/>
      
        <button type="button" onclick="toStep(2)" >Back</button>      
        <button type="button" onclick="saveThis()" >Save</button>




    </p>
</div>



<div id="existingBaseDiv" style="display:none">
    <p>
        <span class="Estilo6" >Choose an existing map for your base map</span>
    </p>
    <br/>

   Not available
</div>

<div id="uploadMapDiv" style="display:none" >
    <p>
        <span class="Estilo6" >Upload your own map</span>
    </p>
    <br/>

   Not available
</div>



<div id="previewMapTitle" style="display:none" >
    
        <span class="Estilo1" >Map Title:</span>
        <span class="Estilo8" ><div id="mapnameDiv2"></div></span>
    

</div>

<div id="previewMapDiv" class="smallmap" style="display:none" ></div>

<div id="step3Div" style="display:none" >
    <br/>
    <button type="button" onclick="saveThis()" >Save</button>
    <button type="button" onclick="previewMap.destroy();toStep(5)" >Back</button>
    <button type="button" onclick="toStep(1)" >Cancel</button>

</div>


  </tiles:put>
</div>


<div id="previewPageDiv" >
</div>

<div id="niftyFloat" style="display:none" >
<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
<p align="center">
<table  border="0">
  <tr>
    <td width="163" class="Estilo4">
        <a href="#" onclick="toStep(1)" >
        <div align="center" class="Estilo12">
            &lt; Back
        </div>
        </a>
    </td>
    <td width="171" class="Estilo4">
        <a href="#" onclick="toStep(2)" >
        <div align="center" class="Estilo12">Next &gt;</div>
        </a>
    </td>
  </tr>
</table>
<p align="center">&nbsp;</p>
<div align="center"><b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b>

</div>
</div>

</tiles:insert>



        <script language="javascript" >


            document.forms['BaseOptionsForm'].baseMapOption[ 0 ].checked = 'checked';

            <logic:present name="mapname" scope="request" >
                document.forms['Step1Form'].mapname.value = '<bean:write name="mapname" scope="request" />';
                toStep(2);
            </logic:present>
            <logic:notPresent name="mapname" scope="request" >
                toStep(1);
            </logic:notPresent>

	</script>
