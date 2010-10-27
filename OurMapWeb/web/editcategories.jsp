<%-- ***************************************************************************

editcategories.jsp
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
    Created on : 04-abr-2010, 22:13:36
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<bean:define id="keymap" name="keymap"  scope="request" toScope="page" type="LinkedHashMap<Category,List<Keyword>>" />


<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu2.jsp" type="page" />
  <tiles:put name="content" type="string"  >

<link rel="stylesheet" href="dragrepis/repis.css" type="text/css" media="screen" />
<script type="text/javascript" src="dragrepis/drag.js"></script>

   <script type="text/javascript" src="ajax.js"></script>
   <script type="text/javascript" >

    mapid = '<bean:write name="map" property="mapid" />' ;


    function initPage() {
        REDIPS.drag.init();
        var forma = document.forms['NewCategoryForm'] ;
        forma.catname.focus();
    }

    window.onload = initPage ;

    function addCategory() {

        var forma = document.forms['NewCategoryForm'];
        var catname = forma.catname.value;

        if ( !validarCadena(catname) )
            return;

        forma.submit();

        //var params = 'catname=' + catname + '&mapid=' + mapid ;
        //callAjax( params, 'newcategory.do' );
        forma.catname.value = '';
        
    }


    function deleteCategory( catId ) {

        //alert( 'Delete: ' + catname );
        catId = catId.trim();
        var params = 'catId=' + catId;
        callAjax( params, 'deletecategory.do' );

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
              document.location = 'orgcats.do?mapid=' + mapid;
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

    <span class="Estilo6" >EDIT AND ORGANIZE CATEGORIES</span>
      <br/>
    
    <logic:present name="map-error" scope="request" >
    <p class="Estilo9"><bean:write name="map-error" scope="request" /></p>
    </logic:present>

    <logic:present name="keyword_needs_category" >
        <br/>
        <span class="Estilo1" style="color:red" >
Please assign your new Keyword to a Category and then hit "Save".
        </span>
        <br/>
    </logic:present>


    <br/>
    <span class="Estilo1" >
Use this page to edit your existing Categories or add some new ones.
<br/><br/>
You can also put any unassigned Keywords into a Category, or move Keywords between Categories, by dragging and dropping them with your mouse.
<br/><br/>
To add a new Category, type the Category name in the box below and press "Add".

  </span>
    <form name="NewCategoryForm" action="newcategory.do" >
        <input type="hidden" name="mapid" value="<bean:write name="map" property="mapid" />" />
    <div id="addCategoryButtonDiv" >

    </div>
        <br/>

     <div id="newCategoryFormDiv" >
         Category Name: <input type="text" name="catname" />
         <button type="button" onclick="addCategory()" >Add</button>
     </div>
    </form>

<!-- tables inside this DIV could have draggable content -->

<br/>

<span class="Estilo4" >CATEGORIES:</span>
<br/>
<br/>
<div id="drag" >


<logic:iterate name="keymap" id="k" type="java.util.Map.Entry<Category,List<Keyword>>" scope="request" >
<bean:define name="k" property="key" id="cat" type="Category" />
    <bean:define name="k" property="value" id="keywords" type="List<Keyword>" />
    
<table width="200px"  >
    <tr>
    <td  class="mark" id="<bean:write name="cat" property="catId"  />" >

        <b><bean:write name="cat" property="catName"  /></b>

        &nbsp;&nbsp;&nbsp;
                    <div align="right" class="Estilo20" >
                    <a href="#" onclick="deleteCategory('<bean:write name="cat" property="catId"/>')" >Delete</a>
                    </div>




    </td>
    </tr>
    <tr>
        <td>
        <logic:iterate name="keywords" id="key" type="Keyword" >

            <div id="<bean:write name="key" property="kwId" />" class="drag t1">
            <bean:write name="key" property="kwValue" />
            </div>

        </logic:iterate>
        </td>



    </tr>
</table>
    
    </logic:iterate>



    <br/>

<table id="nocatstable" width="100%" style="background-color: #FCE5ED;" >
    <tr>
        <td id="null" class="mark" style="background-color: #F6CFDC;" >
            <span style="color: #8F1736" ><b>Unassigned Keywords (not in a Category yet)</b></span>
        </td>
    </tr>
    <tr>
        <td>
        <logic:iterate name="nocat" id="key" type="Keyword" >
            <div id="<bean:write name="key" property="kwId" />" class="drag t1">
            <bean:write name="key" property="kwValue" />
            </div>
        </logic:iterate>

        </td>
    </tr>
</table>





<div id="obj_new"></div>
<div id="dragInfoDiv" style="display:none" >

<table id="table3">
<colgroup><col width="100"/><col width="100"/><col width="100"/><col width="100"/><col width="100"/></colgroup>
<tr style="background-color: #eee">
<td id="message" class="mark" title="You can not drop here">Table3</td>
</tr>
</table>
</div>

</div>


<logic:equal name="allkeyshavecats" value="false" >
    <button disabled type="button" onclick="document.location='saveeditcategories.do?mapid=<bean:write name="map" property="mapid" />'">Done</button>
</logic:equal>
<logic:equal name="allkeyshavecats" value="true" >
    <button type="button" onclick="document.location='saveeditcategories.do?mapid=<bean:write name="map" property="mapid" />'">Done</button>
</logic:equal>



  </tiles:put>

<tiles:put name="sidearea" type="string" >
    
</tiles:put>

</tiles:insert>


