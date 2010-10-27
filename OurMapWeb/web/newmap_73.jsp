<%-- ***************************************************************************

newmap_73.jsp
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
    Created on : 29-ene-2010, 19:06:24
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

<bean:define id="map" name="new_map"  scope="session" toScope="page" type="com.bnmi.ourmap.model.Map" />
<script src="ajax.js"></script>
<script language="javascript" >

    function showIconsForKey( kw_value ) {
        currentKey = kw_value.trim();
        nuevaVentana('viewicons.do');

    }

    function onIconSelected( objectId ) {

        var params = '' ;
        var url = '';

        params = 'kwValue=' + currentKey + '&iconId=' + objectId ;
        url = 'editsessionkeyword.do';
        //alert( params );

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
               forma.status.value = '7.3';
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
<p><span class="Estilo6" >ASSIGN ICONS TO KEYWORDS</span></p>
<p>&nbsp;</p>
<p class="Estilo1">Use the &ldquo;Add Icon&rdquo; buttons to assign an Icon to each of your Keywords. You must assign an Icon to each Keyword before hitting &ldquo;Save&rdquo; to move on. </p>
<p><br/>
</p>
<form name="forma" action="newmap.do" method="post" >
    <input type="hidden" name="status" value="8" />
    <br/>

    <logic:empty name="keywords" scope="request" >
         <span class="Estilo1" >No keywords </span>
    </logic:empty>
<div id="tableDiv" >
    <logic:notEmpty name="keywords" scope="request" >
    <table width="550" >
        <tr>
            <th width="60%" class="Estilo8" height="30" >KEYWORDS</th>
            <th width="20%"></th>
            <th width="20%"></th>
        </tr>
<logic:iterate name="keywords" id="key" type="Keyword" scope="request" >
        <tr>
            <td height="30px" >
    <bean:write name="key" property="kwValue" />
            </td>
            <td class="Estilo1" >
                <logic:present name="key" property="iconId" >
            <img src="<easy:GetObjectTag id="<%=key.getIconId()%>" />" width="20" height="20" />
                </logic:present>
                <logic:notPresent name="key" property="iconId" >
                
                </logic:notPresent>
            </td>
            <td class="Estilo1" >
                <a href="javascript:showIconsForKey('<bean:write name="key" property="kwValue" /> ')" >
                <logic:present name="key" property="iconId" >
                Change Icon
                </logic:present>
                <logic:notPresent name="key" property="iconId" >
                Add Icon
                </logic:notPresent>
                </a>
            </td>
        </tr>
</logic:iterate>


    </table>
        
    </logic:notEmpty>
</div>


        <br/>
        <br/>

<button type="button" onclick="document.location='newmap.do?status=7'" >Back</button>
<logic:present name="allKeywordsHaveIcons" scope="request" >
<button type="submit" name="nextButton" >Save</button>
</logic:present>
<logic:notPresent name="allKeywordsHaveIcons" scope="request" >
<button type="submit" name="nextButton" disabled >Save</button>
</logic:notPresent>
</form>

<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>

<tiles:put name="sidearea" type="string" />
</tiles:insert>

