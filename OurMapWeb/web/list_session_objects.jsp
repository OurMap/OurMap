<%-- ***************************************************************************

list_session_objects.jsp
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
    Created on : 07-ene-2010, 22:43:32
--%>

<%@ page  pageEncoding="UTF-8" %>
<!DOCTYPE html >
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>
<link href="styles/easy.css" rel="stylesheet" type="text/css">


<logic:present name="object_list" scope="request" >

    <ul id="slideshow" class="slideshow">
        <% int slotNumber = 1; %>
    <logic:iterate name="object_list" scope="request" id="o" type="EasyObject" >
    <li class="slide" id="<%=o.getObjectId()%>">
        <table border="0" width="160px" >
            <tr>
                <td class="Estilo4" width="15px">
        <div id="slotNumber" align="center" class="Estilo1" ><%=slotNumber%></div>
                </td>
                <td width="145px" >
    <table width="145px" border="1">
    <tr>
      <td >
        <logic:equal name="o" property="objType" value="1" >
            <table width="100%" >
                <tr>
                    <td colspan="2" class="Estilo8" >
                        <div align="center" >
                            <% if ( o.getObjName().length() > 20 )
                                   out.write( o.getObjName().substring(0,20) + "..." );
                               else
                                   out.write ( o.getObjName() );
                            %>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div align="center" ><img src="getThumbT.do?objectId=<%=o.getObjectId()%>" width="70" /></div>
                    </td>
                </tr>

                <tr>
                    <td class="Estilo20" >
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

          <br/>
              <img src="images/text.png" width="30" />

          </div>

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
        <br/>
        </div>

        <div align="center" >
        <img src="images/audio.png" width="30" />
        </div>
      </logic:equal>


      </td>
    </tr>
    </table>

    <table width="145px" border="0" >
    <tr>
    <td width="60" height="20" class="Estilo20" >
        <div align="left" >
    <a href="editsessionobject.do?objectId=<%=o.getObjectId()%>" ><span class="Estilo1" >Edit</span></a>
        </div>
    </td>

    <td width="60" class="Estilo20" >
        <div align="right" >
    <a href="javascript:removeUploadObject(<%=o.getObjectId()%>)" ><span class="Estilo1" >Delete</span></a>
        </div>
    </td>
    
    </tr>
    </table>

                </td>
            </tr>
        </table>




    

    </li>
    <% slotNumber++; %>
    </logic:iterate>


    </ul>


    
</logic:present>

<logic:empty name="object_list" scope="request" >
    <div align="center" >
    <span class="Estilo4" > No objects </span>
    </div>
</logic:empty>