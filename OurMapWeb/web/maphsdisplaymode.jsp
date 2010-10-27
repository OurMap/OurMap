<%-- ***************************************************************************

maphsdisplaymode.jsp
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
    Created on : 07-jun-2010, 15:49:52
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<bean:define id="project" name="project"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Project" />
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu2.jsp" type="page" />
  <tiles:put name="content" type="string"  >
   <script type="text/javascript" src="ajax.js"></script>
   <script type="text/javascript" >
   </script>
   <form name="MapHsDisplayMode" method="post" action="maphsdisplaymode.do" >
       <input type="hidden" name="formSubmited" value="yes" />
       <input type="hidden" name="mapid" value="<bean:write name="map" property="mapid" />" />
      <span class="Estilo6" >EDIT THE DISPLAY OPTION FOR HOTSPOT MEDIA CONTENT</span>
      <span class="Estilo1" >
          <br/>
          <br/>
          <div style=" text-align: justify" >
There are four possible settings that determine how the media content uploaded to map Hotspots will be displayed in the Hotspot pop-up window:
<br/>
<br/>
1) SCROLLING PAGE: All of a Hotspot's media content will be displayed on a single scrolling page in the Content tab. Individual media files will appear in the order set by the Hotspot's creator. To view the text and media files, users simply scroll down the page.
<br/><br/>
2) SIMPLE SLIDESHOW: The Content tab of a Hotspot will display uploaded media files as a simple click-through "slidehow", starting with the first file in the list. The order of files in the slideshow is set (and can be edited) by the Hotspot's owner/editor.
<br/><br/>
3) SLIDESHOW WITH TABLE OF CONTENTS: The title of each uploaded media file will appear in a list when the user first opens the Content tab of the pop-up window. To see individual media files, users simply click on a title. From there, users can also click through the files one by one as a slideshow.
<br/><br/>
4) FLEXIBLE: The choice of display option is left "open" or "flexible" so that each map user who creates a Hotspot can choose between options 1, 2 or 3 above. This allows map contributors to decide for themselves which display option is best for the content in that particular Hotspot.
<br/><br/>
Different display options create the opportunity to present mapped data and stories in different ways, so choose the option that best suits the goals of this particular map and its community.
<br/><br/>
NOTE: If users have already created Hotspots on this map in Flexible mode, then using this tool to lock in the display setting to option 1,2 or 3 will override all of their previous decisions and cause all Hotspots on this map to display content in your selected mode.          <br/>
          <br/>
          <span class="Estilo4" >Choose your display option</span>
          <br/>
          <br/>
          <table border="0" >
              <tr>
                  <td width="40px" ><input type="radio" name="displayMode" value="2" /></td>
                  <td>
          Scrolling Page
                  </td>
              </tr>
              <tr><td colspan="2" ></td></tr>
              <tr>
              <td width="40px" ><input type="radio" name="displayMode" value="3" /></td>
              <td>
          Simple Slideshow
              </td>
              </tr>
              <tr><td colspan="2" ></td></tr>
              <tr>
                  <td width="40px" ><input type="radio" name="displayMode" value="1" /></td>
                  <td>
          Slideshow with Table of Contents
                  </td>
              </tr>
              <tr><td colspan="2" ></td></tr>

              <tr>
                  <td width="40px" ><input type="radio" name="displayMode" value="0" /></td>
                  <td>
          Flexible (the display option for each Hotspot will be chosen individually by it's creator)
                  </td>
              </tr>
              <tr><td colspan="2" ><br/></td></tr>


          </table>

      <button type="button" onclick="document.location='mapconfiguration.do?mapid=<bean:write name="map" property="mapid" />'" >Cancel</button>
      <button type="submit" >Save</button>
          </div>
      </span>
      <script type="text/javascript" >
           var forma = document.forms['MapHsDisplayMode'];
           var displayMode = <bean:write name="map" property="displayMode" /> ;
           for ( var i = 0; i < forma.displayMode.length; i++ )
           {
                var value = forma.displayMode[i].value ;
                if ( value == displayMode )
                {
                    forma.displayMode[i].checked = 'checked' ;
                    break;
                }
           }
      </script>
  
  </form>
  </tiles:put>
   <tiles:put name="sidearea" type="string" />
</tiles:insert>


