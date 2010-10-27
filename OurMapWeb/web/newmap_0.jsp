<%-- ***************************************************************************

newmap_0.jsp
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

<script type="text/javascript" >


</script>

<bean:define id="map" name="new_map"  scope="session" toScope="page" type="com.bnmi.ourmap.model.Map" />

<div id="step1" >
    <form name="Step1Form" action="newmap.do" >

<input type="hidden" name="formSubmited" value="yes" />
<input type="hidden" name="pid" value="<%=map.getProjectId()%>" />

<input type="hidden" name="backmap" />
<input type="hidden" name="bbox" />
<input type="hidden" name="zoom" />
<input type="hidden" name="center" />
<input type="hidden" name="no_edit" value="yes" />
<input type="hidden" name="status" value="1" />

        <span class="Estilo6" >MAP TITLE AND DESCRIPTION</span><br/>
        

        <logic:present name="map-error" >
            <br/>
        <span class="Estilo9" ><bean:write name="map-error" ignore="true" /></span>
        </logic:present>

        <br/>

                    <div align="justify" >
        <span class="Estilo1" >Please enter a Title for your map and, if needed, a Sub-title. These titles will appear at the top of your Map View screen.
        </span></div>


        <br/>
        <table border="0" >
          <tr>
            <td width="90" height="31">
           <span class="Estilo8" >Map Title:</span>
            </td>
            <td width="400">
             <logic:present name="new_map" scope="session" >
                 <input size="59" type="text" name="mapname" value="<bean:write name="new_map" scope="session" property="mapname" />"  onkeypress="return disableEnterKey(event)" />
             </logic:present>
             <logic:notPresent name="new_map" scope="session" >
               <input size="59" type="text" name="mapname" value="" onkeypress="return disableEnterKey(event)" />
             </logic:notPresent>            </td>
          </tr>
          <tr>
            <td><span class="Estilo8" >Subtitle:</span></td>
            <td>
             <logic:present name="new_map" scope="session" >
               <input size="59" type="text" name="subtitle" value="<bean:write name="new_map" scope="session" property="subtitle" />" onkeypress="return disableEnterKey(event)" />
             </logic:present>
             <logic:notPresent name="new_map" scope="session" >
                <input size="59" type="text" name="subtitle" onkeypress="return disableEnterKey(event)" />
             </logic:notPresent>
            </td>

        </table>
        <br/>

                <div align="justify" >
                <span class="Estilo1" >You can also enter a short Description for  your map. This text will appear in the area just beside your map on the left,  and can be used to tell visitors some basic information about your mapping  project.</span>
                </div>

        <table border="0" >
                
          <tr>
            <td valign="top" class="Estilo8" width="90" ><br/>
            Description:</td>
            <%
                  int descriptionBoxSize = 45 ;
                  Integer browser = (Integer) session.getAttribute("browser");
                  if ( browser == null )
                      browser = 0;
                  if ( browser.intValue() == com.bnmi.ourmap.web.Constantes.MSIE )
                      descriptionBoxSize = 46 ;
            %>
            
            <td width="327">
                <br/>
             <logic:present name="new_map" scope="session" >
              <textarea name="description" id="description" cols="<%=descriptionBoxSize%>" rows="5"><bean:write name="new_map" scope="session" property="description" /></textarea>
             </logic:present>
             <logic:notPresent name="new_map" scope="session" >
              <textarea name="description" id="description" cols="<%=descriptionBoxSize%>" rows="5"></textarea>
             </logic:notPresent>              
            </td>
            
          </tr>
        </table>

        <br/>

                    <div align="justify" >
        <span class="Estilo1">
When you are finished entering your Titles and Description, please hit “Save” to move on to the next step.        </span>
                    </div>


        <br/>

        <button type="submit"  >Save</button>

    </form>
<br/>
</div>
 

<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>

<tiles:put name="sidearea" type="string"  ></tiles:put>
</tiles:insert>

