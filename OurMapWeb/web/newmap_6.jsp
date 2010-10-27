<%-- ***************************************************************************

newmap_6.jsp
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
    Created on : 26-ene-2010, 12:05:09
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
<script type="text/javascript" src="ajax.js"></script>
<script type="text/javascript" >

    var savedRow = null ;

    function showEditForm( src ) {

        while ( src && src.tagName !=  'TR' )
                 src = src.parentNode;

        var rowNumber = src.rowIndex;
        savedRow = rowNumber ;
        var table = document.getElementById('keywordsTable');
        var row = table.rows[ rowNumber ];

        var cell1 = row.cells[0];
        var cell2 = row.cells[1];
        var cell3 = row.cells[2];
        var kwSaved = cell1.innerHTML.trim();

        cell1.innerHTML = "<input type='text' name='kwValue' id='" + rowNumber + "' value='" + kwSaved + "' />" ;
        cell2.innerHTML = "<a href='#' onclick='saveKey(" + rowNumber + ")' >Save</a>" ;
        cell3.innerHTML = "<a href='#' onclick='cancelEdit()' >Cancel</a>" ;

    }

    function saveDefaultKey() {
        //saveKey( savedRow );
    }

    function saveKey( rowNumber ) {

        var forma = document.forms['NewKeywordForm'];
        var kwInput = findElement( forma, rowNumber );
        var kwValue = kwInput.value ;

        cancelEdit();

        if ( ! validarCadena(kwValue) )
            return;

        var params = 'index=' + rowNumber + '&kwValue=' + kwValue ;
        //alert(params);
        callAjax(params, 'editsessionkeyword.do' );

    }

    function cancelEdit() {
        listSessionKeywords();
    }



    function listSessionKeywords() {
        var url = 'list_session_keywords.jsp';
        var parameters = '';
        var divId = 'listKeywordsDiv';
        var http_request = createRequest();

        makePOSTRequest( http_request, url , parameters, function() {
            if (http_request.readyState == 4)
            {
                if (http_request.status == 200)
                {
                    document.getElementById( divId ).innerHTML = http_request.responseText;
                    checkEmptyList();
                }
                else
                    alert('There was a problem with the request.');
            }
        });

    }

    function checkEmptyList() {
        
        var table = document.getElementById('keywordsTable');
        var forma = document.forms['NewKeywordForm'];
        var nextButton = forma.nextButton;

        
        if( table == null  )
        {
            nextButton.disabled = true;            
        }
        else
        {
            var size = table.rows.length;
            
            if ( size == 0 )
            {
                nextButton.disabled = true;
            }
            else
                nextButton.disabled = false;
        }

    }

    function callAjax( params, commandUrl ) {

        var http_request = createRequest();
        makePOSTRequest( http_request, commandUrl, params, function() {
        if (http_request.readyState == 4 && http_request.status == 200 )
        {
          var xmlDoc = http_request.responseXML;
          var root = xmlDoc.documentElement;
          var estado = getChild( root, 'estado' );
          if ( estado && getText(estado) == 'OK' )
          {
              listSessionKeywords();
          }
          else if ( estado && getText(estado) == 'FAIL' )
          {
              var mensaje = getChild( root, 'mensaje' );
              alert( getText(mensaje) );
          }

        }
        });

    }

    function addKeyword() {
        var forma = document.forms['NewKeywordForm'];
        var kw_value = forma.kw_value.value;
        if ( kw_value != null && kw_value.length > 0 )
        {
            var params = 'kw_value=' + kw_value;
            callAjax( params, 'newsessionkeyword.do' );
        }
        forma.kw_value.value = '';
        forma.kw_value.focus();


    }

    function deleteKeyword(src) {

        while ( src && src.tagName !=  'TR' )
                 src = src.parentNode;
        var rowNumber = src.rowIndex;

        var table = document.getElementById('keywordsTable');
        var row = table.rows[rowNumber];
        var kw_value = row.cells[0].innerHTML;
        
        if ( kw_value != null && kw_value.length > 0 )
        {
            var params = 'kw_value=' + kw_value;
            callAjax( params, 'deletesessionkeyword.do' );
        }

    }

    function moveUp( src ) {

        while ( src && src.tagName !=  'TR' )
                src = src.parentNode;

        var rowNumber = src.rowIndex - 1;

        var params = "index=" + rowNumber + '&move=up';
        callAjax( params, 'editsessionkeyword.do' );

    }

    function moveDown( src ) {
        while ( src && src.tagName !=  'TR' )
                src = src.parentNode;

        var rowNumber = src.rowIndex - 1;

        var params = "index=" + rowNumber + '&move=down';
        callAjax( params, 'editsessionkeyword.do' );
    }



</script>
<link href="styles/easy.css" rel="stylesheet" type="text/css">

<span class="Estilo6" >CREATE KEYWORDS</span>

    <form name="NewKeywordForm" method="post" action="javascript:addKeyword()" >

        <br/>
        <p class="Estilo1">Create your list of Keywords. These are the  Keywords that users will be able to choose from when creating new map hotspots.</p>
        <p>&nbsp;</p>
        <p class="Estilo1">Enter a new keyword and  press &quot;Add&quot;. </p>
        <br/>
    

        Keyword: <input type="text" name="kw_value" />

        <button type="button" onclick="addKeyword()" >Add</button>

    <br/>
    <br/>
    <div id="listKeywordsDiv" >
        <table id="keywordsTable" ></table>
    </div>
    <br/>
    <logic:present name="map-error" scope="request" >
    <span class="Estilo9"><bean:write name="map-error" scope="request" /></span><br/>
    </logic:present>
<br/> 
<button type="button" onclick="document.location='newmap.do?status=5'">Back</button>
<button type="button" name="nextButton" onclick="document.location='newmap.do?status=6.4'" disabled >Save</button>
    </form>

<script type="text/javascript" >
    listSessionKeywords();
    var forma = document.forms['NewKeywordForm'];
    forma.kw_value.focus();
</script>
<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>

<tiles:put name="sidearea" type="string" />

</tiles:insert>

