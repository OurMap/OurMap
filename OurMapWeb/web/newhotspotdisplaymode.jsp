<%-- ***************************************************************************

newhotspotdisplaymode.jsp
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
    Created on : 03-jun-2010, 12:44:36
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
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="newhs_left_menu.jsp" type="page" />
  <tiles:put name="content" type="string"  >
   <script type="text/javascript" src="ajax.js"></script>
   <script type="text/javascript" >
   </script>

      <span class="Estilo6" >CHOOSE A DISPLAY OPTION FOR YOUR HOTSPOT CONTENT</span>
      <br/>
      <br/>

      <form name="NewHotspotDisplayMode" action="newhotspotdisplaymode.do" method="post" >
      <input type="hidden" name="formSubmited" value="yes" />

      <span class="Estilo1" >
         Please select a Display Option for the media content of your new hotspot. This setting will affect the way that uploaded media files are displayed in the Content tab.
      <br/>
      <br/>

      <input type="radio" name="displayMode" value="2" checked="checked" />Scrolling Page<br/>
      <input type="radio" name="displayMode" value="3" />Simple Slideshow<br/>
      <input type="radio" name="displayMode" value="1" />Slideshow with Table of Contents<br/>

       
      <br/>
NOTE: Your choice of Display Option can be changed later using the Hotspot's Edit pages.
      <br/>
      <br/>
      
      <button type="button" onclick="document.location='newhotspot.do'" >Back</button>
      <button type="submit" >Next</button>
      </span>

      </form>

      <script type="text/javascript" >
          var selectedDisplayMode = <bean:write name="new_hotspot" property="displayMode" scope="session" /> ;
          var forma = document.forms['NewHotspotDisplayMode'];
          var selectedIndex = 0;
          for ( var i = 0; i < forma.displayMode.length; i ++ )
          {
              var value = forma.displayMode[i].value ;
              if ( value == selectedDisplayMode )
              {
                  selectedIndex = i;
              }
          }
          forma.displayMode[selectedIndex].checked = 'checked' ;
          
      </script>

  </tiles:put>
  <tiles:put name="sidearea" type="string" />
</tiles:insert>


