<%-- ***************************************************************************

popup_layout.jsp
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
<%@ page import="com.inga.utils.SigarUtils" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html >

<%--
    Document   : Popup TemplateTemplate
    Created on : 19-Feb-2009, 9:05
    Author     : Camilo
--%>

<bean:define name="hs" id="hs" scope="request" toScope="page" type="Hotspot" />

<%
    int maxLength = 23 ;
    int maxWordLength = 20;
    String longName = SigarUtils.breakWord( hs.getHsName(), maxWordLength );
    String shortName = longName;
    if ( shortName.length() > maxLength )
        shortName = shortName.substring(0,maxLength) + "..." ;

    longName = SigarUtils.replace( longName, "'", "&#39;" );
    shortName = SigarUtils.replace( shortName, "'", "&#39;" );
    
%>

<html>
    <head>
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>OurMap</title>
    <link href="styles/nifty.css" rel="stylesheet" type="text/css" />
    <link href="styles/easy.css" rel="stylesheet" type="text/css" />
    <link href="tabs/tabjbrowse.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="ajax.js"></script>

<script type="text/javascript" >

    function mouseOverTitle() {
        var spanTitle = document.getElementById('spanTitle');
        spanTitle.innerHTML = '<%=longName%>' ;
    }

    function mouseOutTitle() {
        var spanTitle = document.getElementById('spanTitle');
        spanTitle.innerHTML = '<%=shortName%>';
    }
    
</script>

</head>

<body  >

<div id="niftyBlue" style="position:absolute;clear: both;top:0px;left:0px;width:250px;z-index: 200" onmouseover="mouseOverTitle()" onmouseout="mouseOutTitle()" >
<b class="rtopblue"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
<div align="left" style="padding-left:10px" >
<span id="spanTitle" class="Estilo21"   >
    <%=shortName%>
</span>

</div>
<b class="rbottomblue"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b>
</div>

    
<tiles:insert attribute="hs-tabs" />


<div style="position:absolute;clear: both;top:35px;left:20px;width:529px;" >
<tiles:insert attribute="hs-subtitle" />
</div>


    <div id="contentDiv"
           style="width:502px;
                height:343px;
                position:absolute;
                clear: both;
                
                top:57px;
                left:20px;
                background-color:#e1e1e1;
                overflow:auto;
                padding:15px 14px 0px 14px;
                " >
<tiles:insert attribute="hs-content" />
    </div>



    <div id="footerDiv" style="width:528px;
                height:20px;
                position:absolute;
                clear: both;
                top:420px;
                left:20px;
                " >
		<tiles:insert attribute="hs-footer" />
    </div>



</body>
</html>

