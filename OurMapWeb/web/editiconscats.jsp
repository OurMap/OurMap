<%-- ***************************************************************************

editiconscats.jsp
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
<!DOCTYPE html >
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu2.jsp" type="page" />
  <tiles:put name="content" type="string"  >
<!-- CONTENT HERE _________________________________________________________________ -->
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />
<script type="text/javascript" src="ajax.js"></script>
<script type="text/javascript" >

    var currentCat = null; 
    var selectMode = null;
    var mapid = <bean:write name="map" property="mapid" /> ;

    function showIconsForCat( catId ) {
        currentCat = catId.trim();
        nuevaVentana('viewicons.do');
    }

    function onIconSelected( objectId ) {

       var params = 'catId=' + currentCat + '&iconId=' + objectId ;
       var url = 'editcategory.do';
       //alert( params );
       callAjax( params, url );

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
              document.location = 'editicons.do?mapid=' + mapid;
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
 <link rel="stylesheet" href="styles/icons.css" type="text/css" />

<p><span class="Estilo6" >EDIT ICONS</span></p>



          <logic:present name="category_icon_missing" >
              <br/>
          <span style="color:red" >
          Some categories don't have an icon associated. Please set an icon the all categories of the map.
          </span>
          <br/>
          </logic:present>


<logic:present name="newcategory" >
<br/>
<span style="color: red" >
A default icon has now been assigned to your new item. You can change this now or use the Map Configuration pages to edit the icon later
</span>
<br/>
</logic:present>



<br/>


<p><span class="Estilo1">
Use the "Change Icon" buttons to edit your assigned icons. When you are finished, hit "Save" to move on.
    </span></p>
    <p>
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
                <a href="javascript:showIconsForCat('<bean:write name="cat" property="catId" /> ')" >
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


<%
    Object newkeyword = session.getAttribute("newkeyword");
    Object newcategory = session.getAttribute("newcategory");
    Object newlayer = session.getAttribute("neylayer");
    boolean showBackButton = true;

    if ( newkeyword != null || newcategory != null || newlayer != null )
        showBackButton = false;
    if ( showBackButton ) {
%>

<% } %>
    <button type="button" onclick="document.location='saveiconcategories.do?mapid=<bean:write name="map" property="mapid" />'" >Done</button>
    
</form>

</div>




<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>

<tiles:put name="sidearea" type="string" ></tiles:put>

</tiles:insert>

