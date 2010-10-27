<%-- ***************************************************************************

newmap_72.jsp
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
    Created on : 29-ene-2010, 19:06:15
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="java.util.*" %>
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
<script src="ajax.js"></script>
<script language="javascript" >

    var currentCat = null;
    var currentKey = null;
    var selectMode = null;



    function showIconsForCat( catname ) {
        currentCat = catname;
        selectMode = "cats";

        nuevaVentana('viewicons.do');

    }

    function showIconsForKey( kw_value ) {
        hideLayer('tableDiv');
        currentKey = kw_value;
        selectMode = "keys";

    }


    function onIconSelected( objectId ) {

        var params = '' ;
        var url = '';


        if ( selectMode == 'cats')
        {
           params = 'catName=' + currentCat + '&iconId=' + objectId ;
           url = 'editsessioncategory.do';
        }
        else if ( selectMode == 'keys' )
        {
           params = 'kw_value=' + currentKey + '&iconId=' + objectId ;
           url = 'editsessionkeyword.do';
        }



         var http_request = createRequest();

         makePOSTRequest( http_request, url, params, function() {
         if (http_request.readyState == 4 && http_request.status == 200 )
         {
           var xmlDoc = http_request.responseXML;
           var root = xmlDoc.documentElement;
           var estado = getChild( root, 'estado' );
           if ( estado && getText(estado) == 'OK' )
           {
               var forma = document.forms['forma'];
               forma.status.value = '7.2';
               forma.submit();
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
<link href="styles/easy.css" rel="stylesheet" type="text/css">
 

<p><span class="Estilo6" >ASSIGN ICONS TO CATEGORIES</span></p>
<p>&nbsp;</p>
<p><span class="Estilo1">Use the &ldquo;Add Icon&rdquo; buttons to assign an Icon to each of your Categories. You must assign an Icon to each Category before hitting &ldquo;Save&rdquo; to move on. </span></p>
<p><br/>
</p>
<div id="tableDiv" >
<form name="forma" action="newmap.do" method="post" >
    <input type="hidden" name="status" value="8" />
<p>&nbsp;</p>
<logic:empty name="cats" scope="request" >
        <span class="Estilo1" >No categories defined</span>
    </logic:empty>
<logic:notEmpty name="cats" scope="request" >
    <table width="550" >
        <tr>
            <th width="60%" class="Estilo8" height="30" >CATEGORIES</th>
            <th width="20%"></th>
            <th width="20%"></th>
        </tr>
<logic:iterate name="cats" id="cat" type="Category" scope="request" >
        <tr>
            <td height="30" >
    <bean:write name="cat" property="catName" /> 
            </td>
            <td class="Estilo1" >
                <logic:present name="cat" property="iconId" >
            <img src="<easy:GetObjectTag id="<%=cat.getIconId()%>" />" />
                </logic:present>
                <logic:notPresent name="cat" property="iconId" >
                    
                </logic:notPresent>
            </td>
            <td class="Estilo1" >
                <a href="javascript:showIconsForCat('<bean:write name="cat" property="catName" /> ')" >
                <logic:present name="cat" property="iconId" >
                Change Icon
                </logic:present>
                <logic:notPresent name="cat" property="iconId" >
                Add Icon
                </logic:notPresent>
                </a>
            </td>
        </tr>
</logic:iterate>


    </table>
</logic:notEmpty>

<p><br/>
  
  
  
        <br/>
        <br/>
</p>
<button type="button" onclick="document.location='newmap.do?status=7'" >Back</button>
<logic:present name="allCatsAndKeysHaveIcons" scope="request" >
<button type="submit" name="nextButton" >Save</button>
</logic:present>
<logic:notPresent name="allCatsAndKeysHaveIcons" scope="request" >
<button type="submit" name="nextButton" disabled >Save</button>
</logic:notPresent>
</form>

</div>


<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>
  <tiles:put name="sidearea" type="string" />
</tiles:insert>

