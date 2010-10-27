<%-- ***************************************************************************

mapmembers.jsp
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
    Created on : 07-mar-2010, 16:45:51
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<bean:define id="map" name="map"  scope="request" toScope="page" type="com.bnmi.ourmap.model.Map" />
<bean:define id="principal" name="<%=Constantes.USER%>"  scope="session" toScope="page" type="com.bnmi.ourmap.model.User" />

<tiles:insert template="layout_hint.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu2.jsp" type="page" />
  <tiles:put name="content" type="string"  >
   <script type="text/javascript" src="ajax.js"></script>
   <script type="text/javascript" >

       var mapid = <bean:write name="map" property="mapid" />

       function getSelectedItems( boxname ) {
           var box = document.getElementById( boxname );
           var selectedItems = new Array();
           var j = 0;
           for (var i = 0; i < box.options.length; i++)
              if ( box.options[i].selected )
                  selectedItems[j++] = box.options[i].value ;
           return selectedItems;
       }

       function getSelectedOptions() {
          var allUsersBox = document.getElementById('allUsersBox');
          var selectedItems = getSelectedItems( 'allUsersBox' );
          var newOptions = new Array();
          var k = 0;
          for ( var i = 0; i < allUsersBox.options.length; i++ )
          {
              var currentOpt = allUsersBox.options[i];
              var val = currentOpt.value.trim();
              var label = currentOpt.text.trim();
              if ( contains(selectedItems,val) )
              {
                  var newOpt = document.createElement('OPTION');
                  newOpt.value = val ;
                  newOpt.text = label;
                  newOptions[k++] = newOpt;
              }
          }
          return newOptions ;
          
       }

       function contains(arr, key ) {
           for ( var i = 0; i < arr.length; i++ )
               if ( arr[i] == key )
                   return true;
           return false;
       }

       function getSelectedItemsStr( selectedItems ) {

           var selectedItemsStr = '';

           for ( var i = 0; i < selectedItems.length; i++ )
                selectedItemsStr = selectedItemsStr + "," + selectedItems[i];

           return selectedItemsStr ;
       }

       function addSelectedMembers() {

           var selectedItems = getSelectedItems('allUsersBox');
           if ( selectedItems.length <= 0 )
               return;
           var selectedItemsStr = getSelectedItemsStr( selectedItems );

           var params = 'selectedUsers=' + selectedItemsStr + '&operation=add&mapid=' + mapid ;

           var http_request = createRequest();

           makePOSTRequest( http_request, 'addmapmember.do' , params, function() {
           if (http_request.readyState == 4 && http_request.status == 200 )
           {
             var xmlDoc = http_request.responseXML;
             var root = xmlDoc.documentElement;
             var estado = getChild( root, 'estado' );
             if ( estado && getText(estado) == 'OK' )
             {
                ////////////////////////////////////////////////////////////////

                //refreshParent();

                var newOptions = getSelectedOptions();
                var membersBox = document.getElementById('membersBox');

                var currentItems = new Array();
                for ( var i = 0; i < membersBox.length; i++ )
                    currentItems[i] = membersBox.options[i].value ;

                for ( var i = 0; i < newOptions.length; i++ )
                {
                    var opt = newOptions[i];
                    if ( ! contains( currentItems, opt.value ) )
                    {
                        try
                        {
                            membersBox.options.add( opt, null );
                        }
                        catch ( ex )
                        {
                            membersBox.options[ membersBox.options.length - 1 ] = opt ;
                        }

                        removeItem( 'ownersBox', opt.value );
                    }
                }
                ////////////////////////////////////////////////////////////////
             }
             else if ( estado && getText(estado) == 'FAIL' )
             {
                 var mensaje = getChild( root, 'mensaje' );
                 alert( getText(mensaje) );
             }

           }
           });

       }

       function removeItem(boxname, item )
       {
           var box = document.getElementById( boxname );
           for ( var i = 0 ; box.options.length; i++ )
           {
               if ( box.options[i].value == item )
               {
                   box.remove( i );
                   break;
               }
           }
       }

       function removeSelectedMembers() {
           var selectedItems = getSelectedItems('membersBox');
           
           var selectedItemsStr = getSelectedItemsStr( selectedItems );
           var params = 'selectedUsers=' + selectedItemsStr + '&operation=remove&mapid=' + mapid ;
           var http_request = createRequest();
           makePOSTRequest( http_request, 'addmapmember.do' , params, function() {
           if (http_request.readyState == 4 && http_request.status == 200 )
           {
             var xmlDoc = http_request.responseXML;
             var root = xmlDoc.documentElement;
             var estado = getChild( root, 'estado' );
             if ( estado && getText(estado) == 'OK' )
             {
                 //refreshParent();

                 var selectedItems = getSelectedItems('membersBox');
                 for ( var i = 0; i < selectedItems.length; i++ )
                     removeItem( 'membersBox', selectedItems[i] );

             }
             else if ( estado && getText(estado) == 'FAIL' )
             {
                 var mensaje = getChild( root, 'mensaje' );
                 alert( getText(mensaje) );
             }

           }
           });

       }

       function addSelectedOwners() {

           var selectedItems = getSelectedItems('allUsersBox');
           var selectedItemsStr = getSelectedItemsStr( selectedItems );

           var params = 'selectedUsers=' + selectedItemsStr + '&operation=add&mapid=' + mapid ;

           var http_request = createRequest();

           makePOSTRequest( http_request, 'addmapowner.do' , params, function() {
           if (http_request.readyState == 4 && http_request.status == 200 )
           {
             var xmlDoc = http_request.responseXML;
             var root = xmlDoc.documentElement;
             var estado = getChild( root, 'estado' );
             if ( estado && getText(estado) == 'OK' )
             {
                ////////////////////////////////////////////////////////////////
                 //refreshParent();

                var newOptions = getSelectedOptions();
                var forma = document.forms[0];
                var ownersBox = forma.ownersBox;
                var currentItems = new Array();
                for ( var i = 0; i < ownersBox.length; i++ )
                    currentItems[i] = ownersBox.options[i].value ;

                for ( var i = 0; i < newOptions.length; i++ )
                {
                    var opt = newOptions[i];
                    if ( ! contains( currentItems, opt.value ) )
                    {
                        try
                        {
                            ownersBox.options.add( opt, null );
                        }
                        catch ( ex )
                        {
                            ownersBox.options.add( opt );
                        }
                    }

                    removeItem('membersBox', opt.value );
                }
                ////////////////////////////////////////////////////////////////
             }
             else if ( estado && getText(estado) == 'FAIL' )
             {
                 var mensaje = getChild( root, 'mensaje' );
                 alert( getText(mensaje) );
             }

           }
           });
       }


       function removeSelectedOwners() {
           var selectedItems = getSelectedItems('ownersBox');

           var principalId = '<bean:write name="principal" property="id" />' ;
           var principalInList = contains( selectedItems, principalId );

           if ( principalInList )
              if( ! confirm( 'You are about to remove yourself as a Co-Owner of this map. If you do this, you will no longer have access to these Configuration pages - you will be ejected from this map session and the window will be closed. You may no longer have access to this map at all.\nDo you want to continue?') )
                  return;

           var selectedItemsStr = getSelectedItemsStr( selectedItems );
           var params = 'selectedUsers=' + selectedItemsStr + '&operation=remove&mapid=' + mapid ;
           var http_request = createRequest();
           makePOSTRequest( http_request, 'addmapowner.do' , params, function() {
           if (http_request.readyState == 4 && http_request.status == 200 )
           {
             var xmlDoc = http_request.responseXML;
             var root = xmlDoc.documentElement;
             var estado = getChild( root, 'estado' );
             if ( estado && getText(estado) == 'OK' )
             {

                 if ( principalInList )
                 {
                    refreshParent();
                    executeAjax('logout.do','',null);
                    //alert('Session terminated.');
                    window.top.opener=null;
                    window.close();
                    return;
                 }

                 var selectedItems = getSelectedItems('ownersBox');
                 for ( var i = 0; i < selectedItems.length; i++ )
                     removeItem( 'ownersBox', selectedItems[i] );

             }
             else if ( estado && getText(estado) == 'FAIL' )
             {
                 var mensaje = getChild( root, 'mensaje' );
                 alert( getText(mensaje) );
             }

           }
           });
       }


       function addAll() {
           
           var forma = document.forms[0];
           var allUsersBox = forma.allUsersBox;

           for (var i = 0; i < allUsersBox.options.length; i++)
               allUsersBox.options[i].selected = true;
           
           forma.action.value = 'add' ;
           forma.submit();

       }

       function removeAll() {

           var forma = document.forms[0];
           var membersBox = forma.membersBox;

           for ( var i = 0; i < membersBox.options.length; i++ )
               membersBox.options[i].selected = true;

           forma.action.value = 'remove';
           forma.submit();
       }


   </script>



      <span class="Estilo6" >ADD/MANAGE MEMBERS</span>
      <br/>
      <br/>
      <div align="justify" class="Estilo1" >

Use this page to sign up other site users to be "Members" or "Co-Owners" of this map. Only registered users who have already joined the map's parent project are eligible. These names are shown in the "List of Eligible Users". Simply highlight the name of a user in the left column and then use the "Add" buttons to make them a Member or Co-Owner.




      </div>
      <br/>

      <html:form action="addmapmember" method="post" >
          <input type="hidden" name="mapid" value="<bean:write name="mapid" scope="request" />" />
          <input type="hidden" name="operation" />


<bean:size name="allUsers" id="numUsers" />
<bean:size name="members" id="numOwn" />

<!-- List Start ____________________________________________________________ -->



    <table width="607">
<tr>
            <td width="225">
                <span class="Estilo8" >
                  List of Eligible Users            </span>            </td>
            
          <td width="166"></td>
      <td width="200">
                <span class="Estilo8" >
                    Members                </span>            </td>
        </tr>
        <tr>
            <td>

                
                <select
                    id="allUsersBox"
                    multiple="multiple"
                    
                    style="height:339px;width:200px;"
                    name="allUsersBox"
                    >
                    <logic:iterate name="allUsers" id="user" >
                        <option value="<bean:write name="user" property="id" />" ><bean:write name="user" property="nombre" /></option>
                    </logic:iterate>
		</select><br/>

                
            </td>
            <td>
            <br/>
            <br/>
            <br/>
            <div align="center" >
                <button id="btnToHasNot" type="button" onclick="addSelectedMembers()" >Add as a Member >></button>
                <p>&nbsp;</p>
                <p>&nbsp;</p>
                <p>&nbsp;</p>
                <p>&nbsp;</p>
                <p>&nbsp;</p>
                <p>&nbsp;</p>
                <p><br/>
                  
                </p>
              <button id="btnToHasNot" type="button" onclick="addSelectedOwners()" >Add as a Co-Owner >></button>
                <br/>



            </div>


            </td>
            <td>

		<select name="membersBox"
                        id="membersBox"
                        multiple="multiple"
                        style="height:120px;width:200px;">

        <logic:iterate name="members" id="member" >
            <option value="<bean:write name="member" property="id" />" ><bean:write name="member" property="nombre" /></option>
        </logic:iterate>

                </select>

                <br/>
                <button id="btnToHas" type="button" onclick="removeSelectedMembers()" >Remove Member(s)</button>
<br/>
<br/>
<span class="Estilo8" >Co-Owners</span>
<br/>
		<select name="ownersBox"
                        id="ownersBox"
                        multiple="multiple"
                        style="height:120px;width:200px;">

        <logic:iterate name="owners" id="owner" >
            <option value="<bean:write name="owner" property="id" />" ><bean:write name="owner" property="nombre" /></option>
        </logic:iterate>

                </select>
                <br/>
                <button id="btnToHas" type="button" onclick="removeSelectedOwners()" >Remove Co-Owner(s)</button>
<br/>


            </td>
        </tr>
        <tr>
            <td colspan="3" >
                <br/>
<div align="center" >
    <button type="button" onclick="document.location='mapconfiguration.do?mapid=<bean:write name="map" property="mapid" />'" >Done</button>
</div>

            </td>
        </tr>
    </table>

<br/>
<div class="Estilo1" style="text-align: justify">

</div>
<!-- List End ______________________________________________________________ -->

      </html:form>

  </tiles:put>
  <tiles:put name="hints" type="string" >
      <b>Members</b> will be allowed to view the map, and to add and edit hotspots, as long as the map’s Viewing and Editing Rights are set to allow those actions.
<br/><br/>
<b>Co-Owners</b> have unrestricted access to view the map, they can always add/edit/delete hotspots, and they are also allowed to edit the map’s configuration. Co-Owners may edit the Map Attributes, add new Members, change the Viewing and Editing Rights, etc. However, Co-Owners are not allowed to Delete the map.
<br/><br/>
<b>Important Note:</b> Just because users are signed up as Members or Co-Owners of the parent project does not automatically make them Members or Co-owners of project maps. Membership to maps is controlled separately. Only the Owner of a project automatically gets access to all project maps. All other users must be manually added to the map membership list using these tools.

  </tiles:put>
</tiles:insert>


