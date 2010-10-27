<%-- ***************************************************************************

new_project.jsp
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

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<link href="styles/easy.css" rel="stylesheet" type="text/css" />


<tiles:insert template="layout.jsp">
  <tiles:put name="header" value="header.jsp" type="page" />
  <tiles:put name="leftMenu" value="leftMenu.jsp" type="page" />
  <tiles:put name="content" type="string"  >

        <span class="Estilo6" >CREATE NEW PROJECT</span>
        <br/>
        <br/>
        <span class="Estilo1" >Please enter a Name and a short Description for your new Project</span>
        <br/>
        <br/>

            <form name="form1" method="post" action="newproject.do" >
			<table width="62%" border="0">

              <tr>
                <td height="30" class="Estilo8">Name</td>
                <td height="30" class="Estilo1"><input type="text" name="nombre" value="" /></td>
              </tr>

              <tr>
                <td height="30" class="Estilo8">Description</td>
                <td height="30" class="Estilo1"><input type="text" name="descr" value="" /></td>
              </tr>

              <tr>
                  <td colspan="2" class="Estilo1" >
                    <br/>
                  </td>
              </tr>


              <tr>
                  <td colspan="2" class="Estilo1" >
              Please enter a Password for your project. This password will be required by all users in order to view your new Project page and Maps, so make sure your password is easy to remember and that you record it somewhere for safekeeping. You should only give this password to users who you have authorized to look at your project.
                  </td>
              </tr>

              <tr>
                  <td colspan="2" class="Estilo1" >
                    <br/>
                  </td>
              </tr>

              <tr>
                <td height="30" class="Estilo8">Password</td>
                <td height="30" class="Estilo1"><input type="text" name="pwd" value="" /></td>
              </tr>

            </table>

<br/>

                        <input type="hidden" name="formSubmited" value="yes" />
                        <button type="button" onclick="back()" >Cancel</button>
                        <button type="button" onclick="save()">Save</button>
                        <p>&nbsp;</p>
            </form>


  </tiles:put>
</tiles:insert>
<script src="ajax.js"></script>
<script language="javascript" >

  	function back() {
	   document.location.href = 'main.jsp' ;
	}

	function save() {
           var forma = document.forms[0];
           var pName = forma.nombre.value;
           var pDescr = forma.descr.value;

           if ( !validarCadena(pName) )
           {
               alert('Enter a project name');
               return;
           }

           var error = validatePassword(forma.pwd);
           if ( error != null && error.length > 0 )
           {
               alert( error );
               return;
           }

           forma.submit();

	}

</script>
