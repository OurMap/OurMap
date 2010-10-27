<%-- ***************************************************************************

hs_content.jsp
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
<%@ page import="java.util.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@ page import="com.bnmi.ourmap.web.Utils" %>
<%@ page import="com.inga.utils.SigarUtils" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<bean:define name="hs" id="hs" scope="request" toScope="page" type="Hotspot" />
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>




<tiles:insert template="popup_layout.jsp">



    <tiles:put name="hs-subtitle" type="string" >
        <span class="Estilo6" >Hotspot Content</span>
    </tiles:put>

    <tiles:put name="hs-tabs" type="string" >
<div id="tabsJ"  >
<ul style="padding-left:<bean:write name="offset" scope="request" />px" >
<logic:present name="hs" property="hsDescription" >
<li><a href="viewhsdescription.do?hsId=<bean:write name="hs" property="hsId" />"><span>Summary</span></a></li>
</logic:present>
<li id="current" ><a href="viewhscontent.do?hsId=<bean:write name="hs" property="hsId" />"><span>Content</span></a></li>
<li><a href="viewhsmetadata.do?hsId=<bean:write name="hs" property="hsId" />"><span>Info</span></a></li>

<easy:ValidatePermission actionId="edit-media" elementId="<%=hs.getHsId()%>" elementType="<%=com.bnmi.ourmap.Constantes.HOTSPOT%>" >
<li><a href="viewhsedit.do?hsId=<bean:write name="hs" property="hsId" />"><span>Edit</span></a></li>
</easy:ValidatePermission>

</ul>
</div>
    </tiles:put>



<tiles:put name="hs-content" type="string" >


<script type="text/javascript" src="scripts/PluginDetect_QT.js"></script>

<script type="text/javascript" >
var minVersion = '6'

var QT = PluginDetect.isMinVersion('QuickTime', minVersion);
var myQTVersion = PluginDetect.getVersion('QuickTime') ;

var qtInstalled = PluginDetect.isMinVersion('QuickTime', '0');
var haveqt = true;
var qtMessage ;

if (QT == -2)
{
   // ActiveX is disabled
   qtMessage = 'Please enable ActiveX in order to detect your plugins installed.\nQuicktime is required to play video and audio on this site.' ;
   haveqt = false;
}
else if ( qtInstalled < 0 ) {
   qtMessage = 'QuickTime is required to playback media files.\nDownload the latest version at http://www.apple.com/quicktime/download\nThis update is required to play video and audio on this site.' ;
   haveqt = false;
}
else if (QT==0){
   qtMessage = 'QuickTime installed but version is unknown.\nDownload the latest version at http://www.apple.com/quicktime/download\nThis update is required to play video and audio on this site.' ;
   haveqt = false;
}
else if (QT==-1){
   qtMessage = 'QuickTime version ' + myQTVersion + ' is installed, but version 6 or higher is needed.\nDownload the latest version at http://www.apple.com/quicktime/download\nThis update is required to play video and audio on this site.' ;
   haveqt = false;
}

</script>

<script type="text/javascript" src="ajax.js"></script>
<script type="text/javascript" >

    var posx = 0;
    var posy = 0;
    var hasVideoOrAudio = false;

    document.onmousemove = onMouseMove;



    function mouseOverPic( src, objectId ) {

        src.style.color = '#ffffff' ;
        src.style.cursor = 'pointer';

        /*
        var thumb = document.getElementById('thumbnail');
        var newTop = posy - 10;
        thumb.style.top = newTop + 'px';
        var newLeft = posx + 40;
        thumb.style.left = newLeft + 'px' ;
        thumb.innerHTML = '<img src="getThumb.do?objectId=' + objectId + '" height="80px" />';

        showLayer( 'thumbnail' );
        */
    }

    function mouseOutPic( src ) {

        src.style.color = '#000000';
        src.style.cursor = 'normal';

        /*
        hideLayer('thumbnail');
        */
    }

    function onMouseMove(e) {

	if (!e) var e = window.event;
	if (e.pageX || e.pageY) 	{
		posx = e.pageX;
		posy = e.pageY;
	}
	else if (e.clientX || e.clientY) 	{
		posx = e.clientX + document.body.scrollLeft
			+ document.documentElement.scrollLeft;
		posy = e.clientY + document.body.scrollTop
			+ document.documentElement.scrollTop;
	}
    }


        function parentExists()
        {
             return (parent.location == window.location)? false : true;
        }

    function showInfo( divId, container ) {
        var div = document.getElementById( divId );
        var x = findPosX( container );
        var y = findPosY( container ) - 75;
        //alert( findPosY( container ) );
        div.style.top = y + 'px' ;
        //div.style.left = y + 'px' ;
        showLayer( divId );
    }



</script>




<logic:empty name="objects" scope="request" >
    <div align="center" >
        <br/>
        <br/>
        <span class="Estilo4" >No content available</span>    </div>
</logic:empty>

<logic:notEmpty name="objects" scope="request" >



<!-- Content List __________________________________________________________ -->
<logic:equal name="displayMode" scope="request" value="1" >

<table width="100%" border="0" >
<logic:iterate name="objects" scope="request" id="eo" >

    <logic:equal name="eo" property="objType" value="1" >
  <tr>
      <td width="5%" >
          <a href="viewhsobject.do?objectId=<bean:write name="eo" property="objectId" />" >
              <img src="images/camera.png" height="28" onmouseover="mouseOverPic(<bean:write name="eo" property="objectId" />)" onmouseout="mouseOutPic()" />
          </a>      </td>
    <td width="85%" height="31"  >

        <span class="Estilo22"  onmouseOver="mouseOverPic(this)" onmouseout="mouseOutPic(this)" onclick="document.location='viewhsobject.do?objectId=<bean:write name="eo" property="objectId" />'" >
    <bean:write name="eo" property="objName" />
        </span>

    </td>
    <td width="10%" class="Estilo1">    </td>
  </tr>
    </logic:equal>

    <logic:equal name="eo" property="objType" value="2" >
  <tr>
      <td width="5%" >
          <a href="viewhsobject.do?objectId=<bean:write name="eo" property="objectId" />" >
          <img src="images/audio.png" height="28" />
          </a>
      </td>
    <td width="85%" height="31" class="Estilo1">

            <span class="Estilo22"  onmouseOver="mouseOverPic(this)" onmouseout="mouseOutPic(this)" onclick="document.location='viewhsobject.do?objectId=<bean:write name="eo" property="objectId" />'" >
    <bean:write name="eo" property="objName" />
            </span>

    </td>
    <td width="10%" class="Estilo1">    </td>
  </tr>
    </logic:equal>

  <!-- Video _______________________________________________________________ -->
    <logic:equal name="eo" property="objType" value="3" >
  <tr>
      <td width="5%" >
          <a href="viewhsobject.do?objectId=<bean:write name="eo" property="objectId" />" >
          <img alt="video" src="images/video1.png" height="28" />
          </a>
      </td>
    <td width="85%" height="31" class="Estilo1">

            <span class="Estilo22"  onmouseOver="mouseOverPic(this)" onmouseout="mouseOutPic(this)" onclick="document.location='viewhsobject.do?objectId=<bean:write name="eo" property="objectId" />'" >
    <bean:write name="eo" property="objName" />
            </span>

    </td>
    <td width="10%" class="Estilo1">    </td>
  </tr>
    </logic:equal>



    <logic:equal name="eo" property="objType" value="5" >
  <tr>
      <td width="5%" >
          <a href="viewhsobject.do?objectId=<bean:write name="eo" property="objectId" />" >
          <img src="images/text.png" height="28" />
          </a>
      </td>

    <td width="85%" height="31" class="Estilo1">

            <span class="Estilo22"  onmouseOver="mouseOverPic(this)" onmouseout="mouseOutPic(this)" onclick="document.location='viewhsobject.do?objectId=<bean:write name="eo" property="objectId" />'" >
    <bean:write name="eo" property="objName" />
            </span>

    </td>
    <td width="10%" class="Estilo1">    </td>
  </tr>
    </logic:equal>


</logic:iterate>
</table>

</logic:equal>


<logic:equal name="displayMode" scope="request" value="2" >

<% int index = 0; %>
<table width="100%" border="0" >
<logic:iterate name="objects" scope="request" id="eo" type="EasyObject" >
<% ++index; %>
    <tr>

        <td width="99%" >
    <div align="center" style=" margin-bottom: 8px " >
<span class="Estilo22" ><bean:write name="eo" property="objName" /></span>
    </div>

        </td>

        <td  >
            <div align="right" >
            <a href="#" >
                <img src="images/i_icon.png" alt="View Details" 
                     onmouseover="showInfo('info_<bean:write name="eo" property="objectId" />', this )"
                     onmouseout="hideLayer('info_<bean:write name="eo" property="objectId" />')" />
            </a>

            <%
                String creatorName = "";
                if ( eo.getCreatorName() != null )
                    creatorName = eo.getCreatorName();

                int floatingY = -200;
                Integer browser = (Integer) session.getAttribute("browser");
                switch ( browser )
                {
                    case Constantes.SAFARI :
                        floatingY = 0 ;
                        break;
                    case Constantes.MSIE :
                        floatingY = 0;
                        break;
                    case Constantes.FIREFOX:
                        floatingY = 0 ;
                        break;
                }

            %>
        
 
  <div style=" padding-left:3px; width: 480px; background-color: #ffeeff; z-index:5000; border: silver; border-style: solid; display:none; position:absolute; top:-17px; left:<%=floatingY%>px;" id="info_<bean:write name="eo" property="objectId" />" >
      <table width="100%" border="0" >
  <tr>
      <td>

    <span class="Estilo28">Title:</span>
    <span class="Estilo29"><%=SigarUtils.ellipsis(eo.getObjName(),20)%></span>

    <span class="Estilo28">File Type:</span>
    <span class="Estilo29"><bean:write name="eo" property="typeName" /></span>

    <span class="Estilo28">Time added:</span>
    <span class="Estilo29"><bean:write name="eo" property="created" format="h:mm:ss a Z" /></span>

    <span class="Estilo28">Date added:</span>
    <span class="Estilo29"><bean:write name="eo" property="created" format="MM/dd/yyyy" /></span>

    <span class="Estilo28">Order of Appearance:</span>
    <span class="Estilo29"><%=index%></span>

    <span class="Estilo28">Created by:</span>
    <span class="Estilo29">
        <%=SigarUtils.ellipsis(creatorName,20)%>
    </span>

      </td>
  </tr>
</table>

            </div>
            </div>
   
        </td>
    </tr>
    <tr>

        <td colspan="2" >

<!-- Image _________________________________________________________________ -->
<logic:equal name="eo" property="objType" value="1" >


    <div id="imageHolder" align="center"  >

        

        <% if ( eo.getDimensions() == null ) { %>
            <img id="hsPic" src="<easy:GetObjectTag id="<%=eo.getObjectId()%>" />" height="320" style="float:left;padding-right:5px" alt="<bean:write name="eo" property="objName" />" />
        <% } else {%>
        <% if ( eo.getDimensions().getY() > eo.getDimensions().getX() ) { %>
        <img id="hsPic" src="<easy:GetObjectTag id="<%=eo.getObjectId()%>" />" height="320" style="float:left;padding-right:5px" alt="<bean:write name="eo" property="objName" />" />
        <% } else { %>
        <img id="hsPic" src="<easy:GetObjectTag id="<%=eo.getObjectId()%>" />" width="350" style="float:left;padding-right:5px" alt="<bean:write name="eo" property="objName" />" />
        <%   }  %>
        <% }  %>

    <div align="justify" >
    <span class="Estilo1" >
        <%=Utils.unscapeHtml(eo.getObjDescription())%>
    </span>
    </div>

    </div>

</logic:equal>

<!-- Audio _________________________________________________________________ -->
<logic:equal name="eo" property="objType" value="2" >


        <div align="center" >
                <br/>
                <img src="images/audio.png" />
                <br/>
                <br/>
                <br/>



<script type="text/javascript" >
    if ( haveqt )
        document.write('<embed name="media_<bean:write name="eo" property="objectId" />"        SRC="<easy:GetObjectTag id="<%=eo.getObjectId()%>" />"        WIDTH="240"        HEIGHT="20"        AUTOPLAY="false"        CONTROLLER="true"        LOOP=false PLUGINSPAGE="http://www.apple.com/quicktime/" ></embed>');
    else
        hasVideoOrAudio = true ;
</script>


        </div>

<br/>
<div align="justify" >
<span class="Estilo1" >
            <%=Utils.unscapeHtml(eo.getObjDescription())%>
</span>
</div>
</logic:equal>

<!-- Video _________________________________________________________________ -->
<logic:equal name="eo" property="objType" value="3" >


        <div align="center" >


<script type="text/javascript" >
    if ( haveqt )
        document.write('<embed name="media_<bean:write name="eo" property="objectId" />"        SRC="<easy:GetObjectTag id="<%=eo.getObjectId()%>" />"        AUTOPLAY="false"        WIDTH="388"        HEIGHT="291"        SCALE="tofit"        CONTROLLER="true"        LOOP=false PLUGINSPAGE="http://www.apple.com/quicktime/" ></embed>');
    else
        hasVideoOrAudio = true ;

</script>




        </div>

        <br/>

<div align="justify" >
<span class="Estilo1" >
            <%=Utils.unscapeHtml(eo.getObjDescription())%>
</span>
</div>    
</logic:equal>

<!-- Text _________________________________________________________________ -->
<logic:equal name="eo" property="objType" value="5" >


<div align="justify" >
    <span class="Estilo1" >
                <%=Utils.unscapeHtml(eo.getObjDescription())%>
    </span>
</div>
    
</logic:equal>

        </td>

    </tr>


  <tr>
  <td colspan="2" ><hr/><br/></td>
  </tr>
</logic:iterate>
</table>
</logic:equal>

</logic:notEmpty>



<script type="text/javascript" >
    if ( ! haveqt && hasVideoOrAudio )
        alert( qtMessage );

</script>
</tiles:put>


  <tiles:put name="hs-footer" value="hs-footer.jsp" type="page"  >
  </tiles:put>
</tiles:insert>


