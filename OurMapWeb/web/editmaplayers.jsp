<%-- ***************************************************************************

editmaplayers.jsp
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
    Created on : 31-mar-2010, 12:56:23
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<bean:define id="project" name="project"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Project" />
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu2.jsp" type="page" />
  <tiles:put name="content" type="string"  >
<script type="text/javascript" src="ajax.js" ></script>
<script type="text/javascript" >

    var savedLayerName = '';
    var editOn = false;
    var nowEditing = 0;
    var mapid = <bean:write name="map" property="mapid" /> ;
    var iconsMode = parseInt(<bean:write name="map" property="iconsMode" />) ;

    function showNewLayerForm() {

        var forma = document.forms['NewLayerForm'];
        forma.layerName.value = '';
        showLayer('divNewLayer');
        hideLayer('divAddButton');

    }

    function cancelNewLayerForm() {

        //hideLayer('divNewLayer');
        //showLayer('divAddButton');

    }

    function addLayer() {

        var forma = document.forms['NewLayerForm'];
        var layerName = forma.layerName.value;


        if ( !validarCadena(layerName) )
            return;


        forma.action = 'newlayer.do' ;
        forma.submit();

        forma.action = 'javascript:addLayer()';
        forma.layerName.value = '';
        
        //forma.submit();
        //var params = 'layerName=' + layerName + '&mapid=' + mapid ;
        //callAjax( params, 'newlayer.do' );

    }

    function deleteLayer( src, layerId ) {

        if ( ! confirm('Layer will be deleted. Continue?') )
            return;

        var params = "layerId=" + layerId ;
        callAjax( params, 'deletelayer.do' );

    }

    function moveUp( layerId ) {


        var params = "layerId=" + layerId + '&move=up';
        callAjax( params, 'editlayer.do' );

    }

    function callAjax(params, commandUrl ) {

        var http_request = createRequest();
        makePOSTRequest( http_request, commandUrl, params, function() {
        if (http_request.readyState == 4 && http_request.status == 200 )
        {
          var xmlDoc = http_request.responseXML;
          var root = xmlDoc.documentElement;
          var estado = getChild( root, 'estado' );
          if ( estado && getText(estado) == 'OK' )
          {
              document.location = 'editmaplayers.do?mapid=' + mapid ;
          }
          else if ( estado && getText(estado) == 'FAIL' )
          {
              var mensaje = getChild( root, 'mensaje' );
              alert( getText(mensaje) );
          }

        }
        });

    }

    function moveDown( layerId ) {

        var params = "layerId=" + layerId + '&move=down';
        callAjax( params, 'editlayer.do' );
    }

    function listLayers() {
        executeAjax( 'listlayers.do','mapid=<bean:write name="map" property="mapid" />', 'layersTableDiv' );
    }


    function showEditLayer(src, layerId ) {

        while ( src && src.tagName !=  'TR' )
                 src = src.parentNode;

        var rowNumber = src.rowIndex;


        showEditLayerByRowNumber( rowNumber, layerId );
        editOn = true;
        nowEditing = rowNumber;


    }

    function cancelEditLayer( src ) {

        while ( src && src.tagName !=  'TR' )
                 src = src.parentNode;

        var rowNumber = src.rowIndex;

        var table = document.getElementById('layersTable');
        var row = table.rows[ rowNumber ];

        row.cells[0].innerHTML = savedLayerName;
        row.cells[1].innerHTML = '<a href="#" onclick="showEditLayer(this)" >Edit</a>';
        row.cells[2].innerHTML = '<a href="#" onclick="deleteLayer(this)" >Delete</a>';


        editOn = false;

    }

    function editLayer( src, layerId ) {



        var forma = document.forms['NewLayerForm'];
        var editName =  forma.editName.value.trim();

        var params = 'layerId=' + layerId + '&editName=' + editName ;
        //alert( params );
        callAjax( params, 'editlayer.do' );

        
    }

    function hideLayerForm() {
        showLayer('divAddButton');
        hideLayer('divNewLayer');
    }

    function showEditLayerByRowNumber( rowNumber, layerId ) {

         var table = document.getElementById("layersTable");
         var row = table.rows[ rowNumber ];

         var cell1 = row.cells[0];
         var cell2 = row.cells[2];
         var cell3 = row.cells[1];
         var cell4 = row.cells[2];
         var cell5 = row.cells[4];

         savedLayerName = cell1.innerHTML;



         cell1.innerHTML = "<input type='text' name='editName' value='" + cell1.innerHTML.trim() + "'  size='14' />";

         cell3.innerHTML = "<a href='#' onclick='editLayer(this," + layerId + ")' >Save</a>";
         cell4.innerHTML = "<a href='#' onclick='cancelEditLayer(this)' >Cancel</a>";
         //cell2.innerHTML = "";

    }

    function initPage() {
        
        listLayers();
        
        var forma = document.forms['NewLayerForm'];
        
        forma.layerName.focus();
        
    }


    window.onload = initPage;



</script>

      <span class="Estilo6" >EDIT MAP LAYERS</span>
      <br/>

<form name="NewLayerForm" action="javascript:addLayer()" method="post"  >

    <input type="hidden" name="status" value="5" />
    <input type="hidden" name="layersListStr" />
    <input type="hidden" name="mapid" value="<bean:write name="map" property="mapid" />" />


<p>&nbsp;</p>
<div align="justify" class="Estilo1" >
Use the text box below to create new Layers. Just type the name of your new Layer into the box and then press “Add” to add it to the map. You can also use the controls below to rename (edit), delete or rearrange the existing map layers. 
</div>
<br/>

<logic:present name="map-error" scope="request" >
<p align="justify" class="Estilo9"><bean:write name="map-error" scope="request" /></p>
</logic:present>

    <div id="divNewLayer" >
    <table  border="0">
    <tr>
    <td >Layer Name:&nbsp;&nbsp;</td>
    <td >
    <input type="text" name="layerName" id="layerName"  />
    <input type="button" name="newLayer" id="newLayer" value="Add" onclick="addLayer()" />
    </td>
    </tr>
    </table>
    </div>

<br/>

<div id="layersTableDiv" >
<table id="layersTable" border='0' >
</table>
</div>
<p>&nbsp;</p>



<p align="justify">&nbsp;</p><br/><br/>
<p align="justify">

    <button type="button" onclick="document.location='mapconfiguration.do?mapid=<bean:write name="map" property="mapid" />'" >Done</button>
</p>

</form>

  </tiles:put>

      <tiles:put name="sidearea" type="string" />

</tiles:insert>


