<%-- ***************************************************************************

icons.jsp
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
    Created on : 03-mar-2010, 10:34:55
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<%@taglib  uri="/WEB-INF/cuesta.tld" prefix="cuesta" %>


<html>
    <head>
        <meta http-equiv="Pragma" content="no-cache" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OurMap</title>
        <LINK REL="stylesheet" TYPE="text/css" HREF="styles/easy.css" />
        <link rel="stylesheet" href="styles/icons.css" type="text/css" />
        <script src="ajax.js" type="text/javascript" ></script>
        <script type="text/javascript" >
            function categoryChanged() {
                var forma = document.forms['IconForm'];
                forma.submit();
            }

            function mouseOverIcon( src, objectId ) {

                var posx = src.x;
                var posy = src.y;

                var thumb = document.getElementById('thumbnail');

                
            }

            function mouseOutIcon() {

            }

        </script>
    </head>

    <body>

        <div id="thumbnail" style="position:absolute;display:none;background-color: #ffffff" ></div>

        <form name="IconForm" method="post" action="viewicons.do" >

        <span class="Estilo4">Choose an icon from the following:</span>
        <br/>
        <br/>
        <span class="Estilo4" >Icon Category:</span>
        <cuesta:combo name="category" label="catname" value="id" addNull="false" list="categories" selected="selectedCategory" scope="request"  onchange="categoryChanged()"/>

        <br/>
        <br/>
        
        <logic:empty name="icons" scope="request" >
            <span class="Estilo4" >No icons found on this group</span>
        </logic:empty>
        <logic:notEmpty name="icons" scope="request" >
<ol class="icons"  >
<logic:iterate id="icon" name="icons" scope="request" type="EasyObject" >

    <li  >
    <a title="Choose" >
        <img alt="choose" onmouseover="mouseOverIcon(this,<bean:write name="icon" property="objectId" />)"  id="<bean:write name="icon" property="objectId" />" src="<bean:write name="filesFolder" scope="application" />/<%=icon.getPath()%>" onclick="window.opener.onIconSelected(<bean:write name="icon" property="objectId" />);window.close()" />
    </a>
    </li>

</logic:iterate>
</ol>
        </logic:notEmpty>

        </form>
        
    </body>
</html>





