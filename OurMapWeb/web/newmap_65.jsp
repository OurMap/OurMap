<%-- ***************************************************************************

newmap_65.jsp
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
    Created on : 26-ene-2010, 17:46:11
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

<link href="styles/easy.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="dragrepis/repis.css" type="text/css" media="screen" />


<script type="text/javascript" src="ajax.js"></script>

<script type="text/javascript" src="dragrepis/drag_session.js"></script>



<script language="javascript" >

    function addCategory() {
        var forma = document.forms['step32form'];
        var catname = forma.catname.value;

        var params = 'catname=' + catname;
        callAjaxCats( params, 'newsessioncategory.do' );
        forma.catname.value = '';
    }

    function deleteCategory( catname ) {

        var params = 'catname=' + catname;
        callAjaxCats( params, 'deletesessioncategory.do' );
        
    }


    function orgCatsSession() {
        var http_request = createRequest();
        var myDiv = document.getElementById('orgCatsDiv');
        var parameters = '';
        makePOSTRequest( http_request, 'orgcatssession.do' , parameters, function() {
        if (http_request.readyState == 4 && http_request.status == 200 )
        {
               myDiv.innerHTML = http_request.responseText;
               REDIPS.drag.init();
               checkAllKeysHaveCats();
        }
        });

    }

    function checkAllKeysHaveCats() {

        var table = document.getElementById('nocatstable');
        if ( table == null )
            disableNextButton( true );
        else
        {
            var cell = table.rows[1].cells[0];

            if ( cell.innerHTML.trim() == '' )
                disableNextButton( false );
            else
                disableNextButton( true );
        }

    }


    function disableNextButton( val ) {

        var forma = document.forms['step32form'];
        var nextButton = forma.nextButton;

        nextButton.disabled = val;
    }

    function callAjaxCats(params, commandUrl ) {

        var http_request = createRequest();
        makePOSTRequest( http_request, commandUrl, params, function() {
        if (http_request.readyState == 4 && http_request.status == 200 )
        {
          var xmlDoc = http_request.responseXML;
          var root = xmlDoc.documentElement;
          var estado = getChild( root, 'estado' );
          if ( estado && getText(estado) == 'OK' )
          {
              orgCatsSession();
          }
          else if ( estado && getText(estado) == 'FAIL' )
          {
              var mensaje = getChild( root, 'mensaje' );
              alert( getText(mensaje) );
          }

        }
        });

    }

</script>

    <span class="Estilo6" >CREATE AND ORGANIZE CATEGORIES</span>
      <br/> 
    
<br/>
    <p class="Estilo1">
Use this page to create some Categories for your Keywords. Then drag and drop your unassigned Keywords into the Category boxes to organize them. You must assign all of your Keywords to a Category before moving on.
    </p>

    <logic:present name="map-error" scope="request" >
    <p class="Estilo9" ><bean:write name="map-error" scope="request" /></p>
    </logic:present>


    <p class="Estilo1">&nbsp;</p>
    <form name="step32form" action="javascript:addCategory()" >
    <div id="addCategoryButtonDiv" >
        <span class="Estilo1">
        To add a new Category, type the Category name in the box below and press "Add"
        </span>
    </div>
        <br/>

     <div id="newCategoryFormDiv" >
         Category Name: <input type="text" name="catname" />
         <button type="button" onclick="addCategory()" >Add</button>
     </div>


    <div id="categoryList" ></div>
    <br/>


<span class="Estilo4" >CATEGORIES:</span>
<br/>



    <div id="orgCatsDiv" ></div>
    <button type="button" onclick="document.location='newmap.do?nav=back'" >Back</button>
    <button type="button" name="nextButton" disabled onclick="document.location='newmap.do?status=6.6'" >Save</button>
    </form>


<script language="javascript" >

    function initPage() {
        orgCatsSession();
        var forma = document.forms['step32form'];
        forma.catname.focus();
    }

    window.onload = initPage ;
    
</script>

<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>

<tiles:put name="sidearea" type="string" />
</tiles:insert>

