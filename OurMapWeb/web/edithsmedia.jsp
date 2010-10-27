<%-- ***************************************************************************

edithsmedia.jsp
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
    Created on : 24-feb-2010, 13:27:27
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<%@taglib  uri="/WEB-INF/cuesta.tld" prefix="cuesta" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_hint2.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />

  <logic:present name="hsId" scope="request" >
  <tiles:put name="leftMenu" value="edithsmenu.jsp" type="page" />
  </logic:present>

  <logic:notPresent name="hsId" scope="request" >
  <tiles:put name="leftMenu" value="newhs_left_menu.jsp" type="page" />
  </logic:notPresent>


  <tiles:put name="content" type="string"  >

      <logic:present name="hs" scope="request" >
<bean:define name="hs" id="hs" scope="request" toScope="page" type="Hotspot" />
      </logic:present>

<script src="drag3/dom-drag.js" type="text/javascript" ></script>
<script src="drag3/draglist.js" type="text/javascript" ></script>


<script type="text/javascript" src="ajax.js" ></script>

<script language="javascript" type="text/javascript" >

    function init() {
    }

    function pageOnDragEnd() {
        //alert('on drag end');
        refreshOrder();
        updateNumbers();
    }

    function refreshOrder() {

        var itemList = listItems();
        
        if ( !itemList )
            return;

        var http_request = createRequest();

        var params;
        var url ;
        
<logic:present name="hs" scope="request" >
        params = 'itemList=' + itemList + '&hsId=<bean:write name="hs" property="hsId" ignore="true" />';
        url = 'orderobjects.do';
</logic:present>
        <logic:notPresent name="hs" scope="request" >
        url = 'ordersessionobjects.do' ;
        params = 'itemList=' + itemList;
</logic:notPresent>


        makePOSTRequest( http_request, url , params, function() {
        if (http_request.readyState == 4 && http_request.status == 200 )
        {
          var xmlDoc = http_request.responseXML;
          var root = xmlDoc.documentElement;
          var estado = getChild( root, 'estado' );
          if ( estado && getText(estado) == 'OK' )
          {

          }
          else if ( estado && getText(estado) == 'FAIL' )
          {
              //nothing
          }
        }
        });

    }

        function listItems() {

            var container = document.getElementById('draglist_container');
            var lista = getChildren( container, 'P' );
            var itemList = new Array();

            var size = lista.length;
            for ( var i = 0; i < size; i ++)
            {
                var child = lista[i];
                itemList[i] = child.id;
            }


            if ( size >  0)
                return itemList;
            else
                return null;

        }

        function updateNumbers() {

            var container = document.getElementById('draglist_container');
            var items = getChildren( container, 'P' );
            var size = items.length;

            for ( var i = 0; i < size; i++ )
            {
                var item = items[i];
                var slotNumberDiv = findElement( item, 'slotNumber' );
                var slotNumber = i + 1;
                slotNumberDiv.innerHTML = '' + slotNumber;

            }
        }

        function showRefreshMessage() {
            var divUploadMessage = document.getElementById('uploadMessage');
            divUploadMessage.innerHTML = 'Uploading <img src="images/busy.gif" />';
        }

        function hideRefreshMessage() {
            var divUploadMessage = document.getElementById('uploadMessage');
            divUploadMessage.innerHTML = '';
        }



        function removeUploadObject( objId ) {

            if( !confirm('Delete object?') )
                return;

            var params = 'objectId=' + objId ; ;

            showRefreshMessage();

            var http_request = createRequest();
            var url ;
        <logic:present name="hs" scope="request" >
            url = 'removeHsUpload.do';
        </logic:present>
        <logic:notPresent name="hs" scope="request" >
            url = 'removeUpload.do';
        </logic:notPresent>



            makePOSTRequest( http_request, url , params, function() {
                if (http_request.readyState == 4)
                {
                    if (http_request.status == 200)
                    {
                                <logic:present name="hs" scope="request" >
                        document.location = 'edithsmedia.do?hsId=<bean:write name="hs" property="hsId" ignore="true" />';
                                </logic:present>
                                <logic:notPresent name="hs" scope="request" >
                        document.location = 'addnewmediahs.do';
                                </logic:notPresent>

                    }
                    else
                        alert('There was a problem with the request.');
                }
            });

        }




</script>



<form name="EditHsMediaForm" method="post" action="edithsmedia.do" >
<input type="hidden" name="hsId" value="<bean:write name="hs" property="hsId" ignore="true" />" />
<input type="hidden" name="formSubmited" value="yes" />

</form>


<form name="NewMediaForm" method="post" action="newhsmedia.do" enctype="multipart/form-data" target="uploadFrame" >
<input type="hidden" name="hsId" value="<bean:write name="hs" property="hsId" ignore="true" />" />
<input type="hidden" name="itemList" />


      <span class="Estilo6" >
          <div id="pageTitle"  >ADD / EDIT HOTSPOT CONTENT</div>
      </span>
      <br/>
<span class="Estilo1" >
    Adding media to your hotspot is easy. Just click the Add Media button below to add a photo, audio, video or text to your hotspot.
</span>
      <br/>
      <br/>



<div id="addMediaButton1" >
    <table width="475" border="0">
    <tr>
    <td colspan="3" >

    </td>
    </tr>
    <tr>
    <td width="108">
        <logic:present name="hs" scope="request" >
        <button type="button" name="addMediaButton" onclick="document.location='choosemediatype.do?hsId=<bean:write name="hs" property="hsId" ignore="true" />'" >Add Media</button>
        </logic:present>
        <logic:notPresent name="hs" scope="request" >
        <button type="button" name="addMediaButton" onclick="document.location='choosemediatype.do?mapid=<bean:write name="map" property="mapid" />'" >Add Media</button>        </logic:notPresent>
    <br/>

    </td>
    <td width="50">    </td>
    <td width="38">    </td>
    <td width="261">
    <div id="uploadMessage" ></div>

    </td>
    </tr>
    </table>

</div>






<!-- End Media Pane ________________________________________________________ -->
<br/>

<div id="mediaTable" >

    <span class="Estilo1" >
When you add a new piece of media to your hotspot, it will appear as a block in the column below. Use your mouse to click and drag the block into the desired order. The order of the blocks (indicated by the number next to the block) will determine their order of display inside your hotspot’s content window.
<br/>
<br/>
Click the “Edit” buttons to edit the titles and captions of individual media files. You can also edit the content of text files using these buttons.
<br/>
    </span>
    <br/>
      <span class="Estilo8" >
ADDED MEDIA FILES:
      </span>
<br/>
<br/>






<logic:present name="object_list" scope="request" >

    <table border="0" width="651" >
        <tr>
  <td width="159" style="padding-left:2px;padding-top:10px" bgcolor="#eeeeee" >


<!-- _______________________________________________________________________ -->
<div id="draglist_container">

<% int cont = 0; %>

<logic:iterate name="object_list" scope="request" id="o" type="EasyObject" >


    <p id="<bean:write name="o" property="objectId" />" style="position: relative; left: 0px; top: 0px;" align="center" >

<table width="160px" border="0" >
    <tr>
        <td class="Estilo4" width="15px">
<div id="slotNumber" align="center" class="Estilo1" ><%=cont+1%></div>
        </td>
        <td>


<table width="135"  border="1" >
<tr>
    <td align="center"  >

        <logic:equal name="o" property="objType" value="1" >
            <table width="100%" >
                <tr>
                    <td colspan="2" class="Estilo8" >
                        <div align="center" >
                            <% if ( o.getObjName() != null )
                               {
                                   if ( o.getObjName().length() > 20 )
                                       out.write( o.getObjName().substring(0,20) + "..." );
                                   else
                                      out.write ( o.getObjName() );
                                }
                            %>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div align="center" >
                            <logic:present name="hs" scope="request" >
                            <img src="<easy:GetObjectTag id="<%=o.getObjectId()%>" thumb="true" />" width="70" />
                            </logic:present>
                            <logic:notPresent name="hs" scope="request" >
                            <img src="<easy:GetObjectTag id="<%=o.getObjectId()%>" thumb="true" temp="true" />" width="70" />
                            </logic:notPresent>
                        </div>
                    </td>
                </tr>



            </table>
        </logic:equal>
      <logic:equal name="o" property="objType" value="5" >
          <div align="center" >
          <span class="Estilo8" >
                            <% if ( o.getObjName().length() > 20 )
                                   out.write( o.getObjName().substring(0,20) + "..." );
                               else
                                   out.write ( o.getObjName() );
                            %>
          </span>
          </div>
          <span class="Estilo20" >
        <div align="center" style="padding: 10px 0 10px 0;">
        <img src="images/text.png" height="28" />
              </div>
          </span>
      </logic:equal>


      <logic:equal name="o" property="objType" value="2" >
          <div align="center" >
          <span class="Estilo8" >
                            <% if ( o.getObjName().length() > 20 )
                                   out.write( o.getObjName().substring(0,20) + "..." );
                               else
                                   out.write ( o.getObjName() );
                            %>
          </span>
          </div>
        <div align="center" style="padding: 10px 0 10px 0;">
        <img src="images/audio.png" width="30" />
        </div>


      </logic:equal>


      <logic:equal name="o" property="objType" value="3" >
          <div align="center" >
          <span class="Estilo8" >
                            <% if ( o.getObjName().length() > 20 )
                                   out.write( o.getObjName().substring(0,20) + "..." );
                               else
                                   out.write ( o.getObjName() );
                            %>
          </span>
          </div>
        <div align="center" style="padding: 10px 0 10px 0;">
        <img alt="video" src="images/video1.png" width="30" />
        </div>
      </logic:equal>


      </td>
    </tr>
    </table>


    <table width="135" border="0" >
    <tr>
    <td width="67" class="Estilo20" >
        <div align="left" >
            <logic:present name="hs" scope="request" >
<a href="edithsobject.do?objectId=<%=o.getObjectId()%>" ><span class="Estilo1" >Edit</span></a>
            </logic:present>
            <logic:notPresent name="hs" scope="request" >
<a href="editsessionobject.do?objectId=<%=o.getObjectId()%>" ><span class="Estilo1" >Edit</span></a>
            </logic:notPresent>
        </div>
    <td width="67" class="Estilo20" >
        <div align="right" >
<a href="javascript:removeUploadObject(<%=o.getObjectId()%>)" ><span class="Estilo1" >Delete</span></a>
        </div>
    </td>
    </tr>
    </table>


                </td>
            </tr>
        </table>

<br/>
<input name="draglist_items[<%=cont+1%>]" value="<%=cont%>" type="hidden">
</p>

<% cont++; %>
</logic:iterate>




<!--  // end of draglist_container div -->

</div>


<script type="text/javascript" >

// a bit ugly. draglist.js assumes the existence of a global
// dragListIndex array.

var dragListIndex = new Array();

// manager classes.

draglist_manager = new fv_dragList( 'draglist_container' );

// queries all top level <divs> under the draglist_container
// div and sets up dragging.

draglist_manager.setup();

// addes the newly created dragList to the list of draglists on
// the page (i.e. we can have more than one on a page)

addDragList( draglist_manager );

</script>

<!-- _______________________________________________________________________ -->


  </td>

  <td width="22">&nbsp;</td>
  <td width="456">
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <table width="456" >
          <tr>
              <td width="175"></td>
              <td width="167">


                  <logic:present name="hs" scope="request" >
            <button type="button" onclick="document.location='viewmap.do?mapid=<bean:write name="mapid" scope="request" />&focusHs=<bean:write name="hs" property="hsId" ignore="true" />'" >Done</button>
                  </logic:present>
                  <logic:notPresent name="hs" scope="request" >
            <button type="button" onclick="document.location='addcontentnew.do'" >Back</button>
            <button type="button" onclick="document.location='SaveMapFeature.do'" >  Save Hotspot </button>
                  </logic:notPresent>


              </td>

          </tr>
      </table>


      <p>&nbsp;</p></td>


        </tr>
    </table>



</logic:present>

</div>

<logic:empty name="object_list" scope="request" >
    <div align="center" >
    <span class="Estilo4" > </span>
    </div>
</logic:empty>

</form>







  </tiles:put>

<tiles:put name="hints" type="string" >
1) When you add a new piece of media to your hotspot, it will appear as a block in the column under "Added Media Files". Use your mouse to click and drag the block into the desired order. The order of the blocks (indicated by the number next to the block) will determine their order of display inside your hotspot’s content window.
<br/>
<br/>
2) Click the "Edit" buttons to edit the titles and captions of individual media files. You can also edit the content of text files using these buttons.
</tiles:put>

</tiles:insert>


