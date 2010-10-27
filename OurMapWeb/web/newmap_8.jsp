<%-- ***************************************************************************

newmap_8.jsp
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
    Created on : 26-ene-2010, 12:42:55
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

<form name="SaveMapForm" action="newmap.do" method="post" >
    <p>
      <input type="hidden" name="status" value="9" />
      <span class="Estilo6" >SAVE MAP</span>      </p>
    <p>&nbsp;</p>
    <div class="Estilo1" align="justify">
    Are you ready to save your map?
    <br/><br/>
    If you still want to make changes to your map settings, use the Map Creation menu on the left to go back to an earlier step.
    <br/><br/>
    If you are ready to accept your settings and start using your new map, just hit the "Save" button below.
    <br/><br/>
    Don't worry – if you want to change some of your map's settings later, after you start using it, you can do that at any time. Just visit the Map Configuration options on your map's left menu to make further adjustments.
    <br/><br/>
    The Map Configuration pages will also allow you to do several useful things not covered during the map creation process:
    <ul>
        <li> ADD / MANAGE MEMBERS: Use this configuration page to invite other OurMap users to become “Members” or “Co-Owners” of your map. This will allow these people to visit your map and interact with it.
        </li>
        <li> VIEWING AND EDITING RIGHTS: The settings on this page determine exactly what kind of viewing and editing powers your map’s Members will have. You can also set your map so that the general public (unregistered site visitors) can see it and, if desired, even add content.
        </li>
        <li> CONTENT DISPLAY OPTIONS: There are several different ways that the multimedia content added to hotspots can be displayed on the map view screen. Use this page to choose one option for your map, or leave the choice open to the creators of individual hotspots.
        </li>
    </ul>

Make sure to visit the Map Configuration pages soon, and become familiar with their function.
<br/><br/>
Special Note: After hitting the "Save" button your map will be created. A link to this new map should soon appear on your Project page. You may need to "refresh" your web browser in order for the link to appear the first time.
 <br/><br/>
Happy Mapping!
<br/><br/>

    </div>

                </p>
    <button type="button" onclick="document.location='newmap.do?status=7'" >Back</button>
<button type="submit" >Save Map</button>
</form>
<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>

<tiles:put name="sidearea" type="string" >
    
</tiles:put>
</tiles:insert>

