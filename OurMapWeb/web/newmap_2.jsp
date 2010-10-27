<%-- ***************************************************************************

newmap_2.jsp
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
    Created on : 25-ene-2010, 3:15:10
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

<script src="ajax.js"></script>

<script language="javascript" >
    function optionSelected() {
        var forma = document.forms['BaseOptionsForm'];
        var opt = getCheckedValue( forma.baseMapOption );
        opt = parseInt( opt );
        switch ( opt )
        {
            case 1:
                forma.status.value = '3';
                forma.submit();
                break;
            default:
                alert('Sorry, that option is not presently available. Please choose another option');
                break;
        }

    }
</script>

<div id="baseOptionsDiv" >
<form name="BaseOptionsForm" action="newmap.do" >
    <input type="hidden" name="status" value="3" />
    <span class="Estilo6" >SET THE BACKGROUND MAP</span>
    <br/>
    <br/>
    <p>
        <span class="Estilo8" >STEP 1: CHOOSE YOUR BACKGROUND MAP</span>    </p>
    <br/>

    <p class="Estilo1" >
        <span class="Estilo1" >
            In order to define your map area, you’ll first need to choose what style of Background Map to use for your project. (Note:  for the moment only one option is available. In the future, there will be others.)
        <br/>
        </p>
    <p class="Estilo1" >&nbsp;</p>
    <p class="Estilo1" >Please choose an option:    </p>
    <p>
      <input type="radio" name="baseMapOption" checked="checked" value="1" >
        <span class="Estilo1">Use Google Maps/Open Layers as your background map
          </input>
        <br/>
        <input type="radio" name="baseMapOption" value="2" >
          Browse for other maps in the OurMap database </span><span class="Estilo9">(not currently available)
  </input>
  </span><span class="Estilo1"><br/>
  <input type="radio" name="baseMapOption" value="3" >
    Upload your own map to use as a background map </span><span class="Estilo9">(not currently available)</span><span class="Estilo1">
  </input>
  </span><br/>
  </p>
  <br/>
    <p>
     <button type="button" name="b1" onclick="document.location='newmap.do?status=0'"  >Back</button>
      <button type="button" onclick="optionSelected()" name="b1"  >Next</button>
    </p>
    <br/>
    <!--
    <p class="Estilo1">NOTE TO USERS: </p>
    <p class="Estilo1">&nbsp;</p>
    <p align="justify" class="Estilo1">Your choice of a Background  Map will affect what kind of geographic detail your map shows. Also, some maps  do not cover the whole Earth. So it is important to select a Background Map  that will cover the region you want to map in the appropriate detail. </p>
    <p align="justify" class="Estilo1">&nbsp;</p>
    <p align="justify" class="Estilo1">We recommend option  1 (Google Maps / Open Layers) as the easiest option for getting started and  choosing your map boundaries, because this option provides up to 10 detailed background  maps to choose from. And these maps cover nearly the whole planet in a fair  amount of detail. So these maps should suit the purposes of most projects, at  least in cases where you don&rsquo;t need a specialized type of map feature detail. </p>
    <p align="justify" class="Estilo1">TIP:&nbsp; You can always use Google Maps to choose your  map boundaries, and then add or upload another Background Map later. You can  also change your map&rsquo;s boundaries later on if you need to refine them. </p>
    <p align="justify" class="Estilo1">&nbsp;</p>
    <p align="justify" class="Estilo1">Option 2 lets you browse  the OurMap database to find another background map that covers your region. We  have uploaded a number of maps here that may be useful if you are looking for a  map with a more specialized kind of detail for your area. (For example, a  geological map or map with lots of specialized biological information.)</p>
    <p align="justify" class="Estilo1">&nbsp;</p>
    <p align="justify" class="Estilo1">Finally, choosing  Option 3 will allow you to upload your own map to use as a background. In this  case, you will need to have a map saved on your computer in a compatible file  type (shapefile or kml). Note that if you do upload your own map in this way, it will then  be stored in our map database, and you can choose to make it available to other  users in the future. It will then be available for browsing in Option 2. </p>
    <p align="justify" class="Estilo1">&nbsp;</p>
    <p align="justify" class="Estilo1">Or, if you have a  scanned or hand-drawn map that is not yet calibrated with GPS coordinates, you  can also upload your map as a simple JPG image and then calibrate it manually to  establish the GPS coordinates. This will require some extra steps, and you will  need to go out and gather some GPS coordinates in your map area, so this option  is recommended only for advanced users. </p>
    <p class="Estilo1"><br/>    
        </p>
    -->
    </form>
</div>


<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>

<tiles:put name="sidearea" type="string" ></tiles:put>
</tiles:insert>

