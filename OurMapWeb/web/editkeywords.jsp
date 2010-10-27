<%-- ***************************************************************************

editkeywords.jsp
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
    Created on : 01-abr-2010, 3:22:17
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu2.jsp" type="page" />
  <tiles:put name="content" type="string"  >
   <script type="text/javascript" src="ajax.js"></script>
   <script type="text/javascript" >

    var mapid = <bean:write name="map" property="mapid" /> ;

    function initPage() {
        var forma = document.forms['NewKeywordForm'] ;
        forma.kw_value.focus();
    }

    window.onload = initPage ;

    function addKeyword() {

        var forma = document.forms['NewKeywordForm'];
        var kw_value = forma.kw_value.value;
        if ( kw_value != null && kw_value.length > 0 )
        {
            forma.action = 'newkeyword.do' ;
            forma.submit();
            forma.kw_value.value = '';
            forma.action = 'javascript:addKeyword()' ;

        }
        
        forma.kw_value.focus();


    }

    function deleteKeyword( kwId ) {

        if ( ! confirm("Keyword will be deleted. Continue?") )
            return;

        callAjax( 'kwId=' + kwId , 'deletekeyword.do' );

    }

    function callAjax(params, commandUrl ) {


        
        var http_request = createRequest();
        makePOSTRequest( http_request, commandUrl, params, function() {
        if (http_request.readyState == 4 && http_request.status == 200 )
        {
          var xmlDoc = http_request.responseXML;
          var root = xmlDoc.documentElement;
          var estado = getChild( root, 'estado' );
          //alert( estado );
          if ( estado && getText(estado) == 'OK' )
          {
              document.location = 'editkeywords.do?mapid=' + mapid;
          }
          else if ( estado && getText(estado) == 'FAIL' )
          {
              var mensaje = getChild( root, 'mensaje' );
              alert( getText(mensaje) );
          }

        }
        });

    }

    function showEditForm( src, kwId ) {
        
        while ( src && src.tagName !=  'TR' )
                 src = src.parentNode;

        var rowNumber = src.rowIndex;
        var table = document.getElementById('keywordsTable');
        var row = table.rows[ rowNumber ];

        var cell1 = row.cells[0];
        var cell2 = row.cells[1];
        var cell3 = row.cells[2];
        var kwSaved = cell1.innerHTML.trim();

        cell1.innerHTML = "<input type='text' name='kwValue' id='" + kwId + "' value='" + kwSaved + "' />" ;
        cell2.innerHTML = "<a href='#' onclick='saveKey(" + kwId + ")' >Save</a>" ;
        cell3.innerHTML = "<a href='#' onclick='cancelEdit()' >Cancel</a>" ;

    }

    function cancelEdit() {
        document.location = 'editkeywords.do?mapid=' + mapid ;
    }

    function saveKey(kwId) {

        var forma = document.forms['NewKeywordForm'];
        var kwInput = findElement( forma, kwId );
        var kwValue = kwInput.value ;

        cancelEdit();
        
        if ( ! validarCadena(kwValue) )
            return;

        var params = 'kwId=' + kwId + '&kwValue=' + kwValue ;
        //alert(params);
        callAjax(params, 'editkeyword.do' );

    }


    function moveUp( kwId ) {

        var params = "kwId=" + kwId + '&move=up';
        callAjax( params, 'editkeyword.do' );

    }

    function moveDown( kwId ) {
        var params = "kwId=" + kwId + '&move=down';
        callAjax( params, 'editkeyword.do' );
    }

   </script>

<span class="Estilo6" >ADD/EDIT MAP KEYWORDS</span>


    <form name="NewKeywordForm" method="post" action="javascript:addKeyword()" >
        <input type="hidden" name="mapid" value="<bean:write name="map" property="mapid" />" />
        <br/>
        <span class="Estilo1" >
Use the text box below to create new Keywords and add them to your list. Just type the name of your new Keyword into the box and then press "Add".
        <br/>
        <br/>
You can also use the controls below to edit, delete or re-order your existing Keywords.
<br/>
        </span>

        <br/>

        Keyword: <input type="text" name="kw_value" />

        <button type="button" onclick="addKeyword()" >Add</button>

    <br/>
    <br/>


            <table id="keywordsTable" width="629" border="0" >

<tr>
<td width="367" class="Estilo8">Keywords</td>
<td width="36" class="Estilo8"></td>
<td width="52" class="Estilo8"></td>
<td width="69" class="Estilo8"></td>
<td width="83" class="Estilo8"></td>
</tr>


            <% int cont = 0; %>
            <bean:size name="keys" id="numKeys" scope="request" />
            <logic:iterate name="keys" id="key" type="Keyword" scope="request" >

        <tr>
          <td >
              <bean:write name="key" property="kwValue" />
          </td>
          <td  >
              <a href="#" onclick="showEditForm(this,<bean:write name="key" property="kwId" />)" >
                  Edit
              </a>
          </td>
          <td  >
              <a href="#" onclick="deleteKeyword(<bean:write name="key" property="kwId" />)" >
                  Delete
              </a>
          </td>

          <td  >
              <% if  ( cont != 0 ) { %>
              <a href="#" onclick="moveUp(<bean:write name="key" property="kwId" />)" >
                  Move Up
              </a>
              <% } %>
          </td>

          <td  >
              <% if  ( cont != numKeys - 1 ) { %>
              <a href="#" onclick="moveDown(<bean:write name="key" property="kwId" />)" >
                  Move Down
              </a>
              <% } %>
          </td>

        </tr>
            <% cont++; %>
            </logic:iterate>
        </table>



    <br/>
    <logic:present name="map-error" scope="request" >
    <span class="Estilo9"><bean:write name="map-error" scope="request" /></span><br/>
    </logic:present>
<br/>

<button type="button" onclick="document.location='mapconfiguration.do?mapid=<bean:write name="map" property="mapid" />'">Done</button>
    </form>

  </tiles:put>
  <tiles:put name="sidearea" type="string" ></tiles:put>
</tiles:insert>


