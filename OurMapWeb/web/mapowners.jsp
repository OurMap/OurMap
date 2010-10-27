<%-- ***************************************************************************

mapowners.jsp
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
    Created on : 07-mar-2010, 16:45:51
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout.jsp">
  <tiles:put name="header" value="header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >
   <script type="text/javascript" src="ajax.js"></script>
   <script language="javascript" >

       function addSelected() {
           var forma = document.forms[0];
           forma.action.value = 'add' ;
           forma.submit();
       }

       function removeSelected() {
           var forma = document.forms[0];
           forma.action.value = 'remove' ;
           forma.submit();
       }

       function addAll() {
           var forma = document.forms[0];
           var notOwnersBox = forma.notOwnersBox;

           for (var i = 0; i < notOwnersBox.options.length; i++)
               notOwnersBox.options[i].selected = true;
           
           forma.action.value = 'add' ;
           forma.submit();

       }

       function removeAll() {

           var forma = document.forms[0];
           var ownersBox = forma.ownersBox;

           for ( var i = 0; i < ownersBox.options.length; i++ )
               ownersBox.options[i].selected = true;

           forma.action.value = 'remove';
           forma.submit();
       }


   </script>

<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />


      <span class="Estilo6" >MAP OWNERSHIP MANAGMENT</span>
      <br/>
      <br/>
      <table>
          <tr>
              <td class="Estilo8" >Map:</td>
              <td class="Estilo1" ><bean:write name="map" property="mapname" /></td>
          </tr>
          <tr>
              <td class="Estilo8" >Creator:</td>
              <td class="Estilo1" ><bean:write name="map" property="createdBy" /></td>
          </tr>
      </table>
          <br/>

      <html:form action="addmapowner" method="post" >
          <input type="hidden" name="mapid" value="<bean:write name="mapid" scope="request" />" />
          <input type="hidden" name="action" />


<bean:size name="potentialMapOwners" id="numPot" />
<bean:size name="owners" id="numOwn" />

<!-- List Start ____________________________________________________________ -->



    <table>
        <tr>
            <td>
                <span class="Estilo1" >
                    Not owners
                </span>
            </td>
            
            <td></td>
            <td>
                <span class="Estilo1" >
                    Owners
                </span>
            </td>
        </tr>
        <tr>
            <td>

                
                <select
                    id="lbHas"
                    multiple="multiple"
                    ondblclick="javascript:MoveSelected('lbHas','lbHasNot','text','string');"
                    style="height:350px;width:300px;"
                    name="notOwnersBox"
                    >
                    <logic:iterate name="potentialMapOwners" id="pot" >
                        <option value="<bean:write name="pot" />" ><bean:write name="pot" /></option>
                    </logic:iterate>
		</select><br/>

                <em><a id="lbHasLabel" style="color:Grey;">Listing <bean:write name="numPot" /> item(s)</a></em>
            </td>
            <td>
            <br/>
            <br/>
            <br/>
                <button id="btnToHasNot" type="button" onclick="addSelected()" >&nbsp;>&nbsp;</button>
                <button id="btnAllToHasNot" type="button" onclick="addAll()" >&nbsp;>>&nbsp;</button>
                <button id="btnAllToHas" type="button" onclick="removeAll()" >&nbsp;<<&nbsp;</button>
                <button id="btnToHas" type="button" onclick="removeSelected()" >&nbsp;<&nbsp;</button>


            </td>
            <td>


		<select name="ownersBox"
                        id="lbHasNot"
                        multiple="multiple"
                        ondblclick="javascript:MoveSelected('lbHasNot','lbHas','text','string');"
                        style="height:350px;width:300px;">

        <logic:iterate name="owners" id="owner" >
            <option value="<bean:write name="owner" />" ><bean:write name="owner" /></option>
        </logic:iterate>

                </select>
                <br/>
		<em><a id="lbHasNotLabel" style="color:grey;">Listing <bean:write name="numOwn" /> item(s) </a></em>
            </td>
        </tr>
    </table>

    <select id="lbHasStorage" style="display:none;">

    </select>
    <select id="lbHasNotStorage" style="display:none;" />

<!-- List End ______________________________________________________________ -->

      </html:form>

  </tiles:put>
</tiles:insert>


