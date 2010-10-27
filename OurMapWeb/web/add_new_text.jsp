<%-- ***************************************************************************

com.bnmi.ourmap.web.actions..java
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

<bean:define id="project" name="project"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Project" />
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />



<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <logic:present name="hsId" scope="request" >
  <tiles:put name="leftMenu" value="edithsmenu.jsp" type="page" />
  </logic:present>

  <logic:notPresent name="hsId" scope="request" >
  <tiles:put name="leftMenu" value="newhs_left_menu.jsp" type="page" />
  </logic:notPresent>
  <tiles:put name="content" type="string"  >
   <script type="text/javascript" src="ajax.js"></script>
   <script type="text/javascript" >



        function verify(objForm) {



            var mediaType = 5;


            var arrExtensions;
            switch ( mediaType )
            {
                case 1:

                    arrExtensions = new Array("bmp", "gif", "jpg", "jpeg", "png");

/*
                    var size = objForm.mediaFile.files[0].fileSize;
                    if ( size > 4500000 )
                    {
                        alert( 'Image size must be up to 4.5 mb');
                        return false;
                    }
*/

                    if ( ! validarCadena(objForm.mediaTitle.value) )
                    {
                        alert('Image Title is required');
                        return false;
                    }

                    break;
                case 2:
                    arrExtensions = new Array("wav", "mp3", "amr" );

                    if ( ! validarCadena(objForm.mediaTitle.value) )
                    {
                        alert('Media Title is required');
                        return false;
                    }

                    break;
                case 3:
                    arrExtensions = new Array("mov", "mp4", "wmv", "flv", "mpg", "mpeg", "avi" );
                    break;
                case 5:
                    if ( ! validarCadena( objForm.textTitle.value ) )
                    {
                        alert('Text Title is required');
                        return false;
                    }
                    if ( ! validarCadena( objForm.textContent.value ) )
                    {
                        alert('Text Content required');
                        return false;
                    }
                    return true;
                default:
                    arrExtensions = new Array("*" );
                    break;

            }




            var strFilePath = objForm.mediaFile.value;

            var arrTmp = strFilePath.split(".");
            var strExtension = arrTmp[arrTmp.length-1].toLowerCase();
            var blnExists = false;
            for (var i=0; i<arrExtensions.length; i++) {
                    if (strExtension == arrExtensions[i]) {
                            blnExists = true;
                            break;
                    }
            }

            if ( ! blnExists )
            {
                if ( mediaType == 1 )
                    alert('Not a valid image file');
                else if ( mediaType == 2 )
                    alert('Not a valid audio file');
                else if ( mediaType == 3 )
                    alert('Not a valid video file');
                else
                    alert('Not a valid file');

            }

            return blnExists;
        }

        function addMedia() {

            var forma = document.forms['NewMediaForm'];

            var valid = verify( forma );

            <logic:present name="hsId" >
                    forma.action = 'newhsmedia.do' ;
            </logic:present>


            if ( ! valid )
                return;
            else
            {
                forma.addButton.disabled = true ;
                forma.submit();
            }


        }



   </script>


      <span class="Estilo6" >ADD A NEW TEXT</span>
      <br/>
      <br/>
<form name="NewMediaForm" method="post" action="newmedia.do" enctype="multipart/form-data"  >
    <input type="hidden" name="blockDisplay" value="1" />
    <input type="hidden" name="mediaType" value="5" />
    <input type="hidden" name="mapid" value="<bean:write name="map" property="mapid" />" />
<input type="hidden" name="hsId" value="<bean:write name="hsId" ignore="true" />" />

    <p>
      <span class="Estilo1" >Please use the boxes below to enter a Title and then a Body for your new text file:</span></p>
    <br/>
    <p class="Estilo4">Title</p>
    <p>
      <input type="text" name="textTitle" id="textTitle" onkeypress="return disableEnterKey(event)" />
    </p>
    <br/>
    <p><span class="Estilo4">Body of Text</span><BR/>
      <textarea name="textContent" cols="70" rows="12"></textarea>
      <br/>
                </p>
</div>

    <br/>
    <span class="Estilo1"  ><div id="saveComment" >After entering your text, please hit the "Add Text" button to create your text file.</div></span>
    <br/>

<logic:present name="hsId" >
    <button type="button" onclick="cancelThis('edithsmedia.do?hsId=<bean:write name="hsId" />')" >Cancel</button>
</logic:present>
<logic:notPresent name="hsId" >
<button type="button" onclick="cancelThis('addnewmediahs.do')" >Cancel</button>
</logic:notPresent>
<button name="addButton" type="button" onclick="addMedia()" >Add Text</button>


</form>
  </tiles:put>

      <tiles:put name="sidearea" type="string" >
          
      </tiles:put>

</tiles:insert>


