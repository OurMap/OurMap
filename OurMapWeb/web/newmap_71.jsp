<%-- ***************************************************************************

newmap_71.jsp
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
    Created on : 29-ene-2010, 7:54:44
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@ page import="java.util.*" %>
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

 
<script type="text/javascript" src="ajax.js"></script>
<script type="text/javascript" >

    var currentIndex = -1;

    function showIcons(index) {
        currentIndex = index;
        nuevaVentana('viewicons.do');


    }


    function onIconSelected( objectId ) {


         var params = 'index=' + currentIndex + '&iconId=' + objectId ;

         var http_request = createRequest();

         makePOSTRequest( http_request, 'editsessionlayer.do' , params, function() {
         if (http_request.readyState == 4 && http_request.status == 200 )
         {
           var xmlDoc = http_request.responseXML;
           var root = xmlDoc.documentElement;
           var estado = getChild( root, 'estado' );
           if ( estado && getText(estado) == 'OK' )
           {
               var forma = document.forms['forma'];
               forma.status.value = '7.1';
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

<form name="forma" action="newmap.do" method="post" >
        <p>
          <input type="hidden" name="status" value="7.5" />
          
          <span class="Estilo6" >ASSIGN ICONS TO LAYERS</span>
          <br/>
          </p>
        <p>&nbsp;</p>
        <p class="Estilo1">Use the &ldquo;Add Icon&rdquo; buttons to assign an Icon to each of your map&rsquo;s Layers. You must assign an Icon to each Layer before hitting &ldquo;Save&rdquo; to move on. </p>
        <p><br/>
                        </p>
      <div id="layersTableDiv" >
<table width="550" border="0"  >
    <tr>
        <td  width="60%" height="30" class="Estilo8" >
        Layer Name		</td>
        <td width="20%" ></td>
        <td width="20%" ></td>
    </tr>
    <% int cont = 0; %>
<logic:iterate id="layer" name="layers" scope="request" type="Layer" >
    <tr>
        <td height="30px" >
    <bean:write name="layer" property="layerName" /><br/>
        </td>
        <td>
            <logic:present name="layer" property="iconId" >
            <img src="<easy:GetObjectTag id="<%=layer.getIconId()%>" />" width="20" heigh="20" />
            </logic:present>
            <logic:notPresent name="layer" property="iconId" >
            
            </logic:notPresent>
        </td>
        <td>
            <logic:present name="layer" property="iconId" >
            <a href="javascript:showIcons(<%=cont++%>)" >Change Icon</a>
            </logic:present>
            <logic:notPresent name="layer" property="iconId" >
            <a href="javascript:showIcons(<%=cont++%>)" >Add Icon</a>
            </logic:notPresent>


        </td>
    </tr>
</logic:iterate>
</table>

<br/>
<br/>

</div>



<br/>
<br/>
<button type="button" onclick="document.location='newmap.do?status=7'" >Back</button>
<logic:present name="allLayersHaveIcons" scope="request" >
<button type="submit" name="nextButton" >Save</button>
</logic:present>
<logic:notPresent name="allLayersHaveIcons" scope="request" >
<button type="submit" name="nextButton" disabled >Save</button>
</logic:notPresent>
</form>


<script language="javascript" >
</script>


<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>
  <tiles:put name="sidearea" type="string" />
</tiles:insert>

