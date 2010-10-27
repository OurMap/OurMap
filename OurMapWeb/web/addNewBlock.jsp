<%-- ***************************************************************************

addNewBlock.jsp
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

<tiles:insert template="layout.jsp">
  <tiles:put name="header" value="header.jsp" type="page" />
  <tiles:put name="leftMenu" value="leftMenu.jsp" type="page" />
  <tiles:put name="content" type="string"  >
<!-- CONTENT HERE _________________________________________________________________ -->

<link href="draglist/common.css" rel="stylesheet" type="text/css">
<link href="draglist/lists.css" rel="stylesheet" type="text/css">
<link href="draglist/slides.css" rel="stylesheet" type="text/css">

<script src="draglist/core.js" ></script>
<script src="draglist/events.js" ></script>
<script src="draglist/css.js" ></script>
<script src="draglist/coordinates.js" ></script>
<script src="draglist/drag.js" ></script>
<script src="draglist/dragsort.js" ></script>


<script language="JavaScript"><!--
var ESCAPE = 27
var ENTER = 13
var TAB = 9

var coordinates = ToolMan.coordinates()
var dragsort = ToolMan.dragsort()

window.onload = function() {

	dragsort.makeListSortable(document.getElementById("slideshow"), setHandle)
}

function setHandle(item) {
	item.toolManDragGroup.setHandle(findHandle(item))
}

function findHandle(item) {
	var children = item.getElementsByTagName("div")
	for (var i = 0; i < children.length; i++) {
		var child = children[i]

		if (child.getAttribute("class") == null) continue

		if (child.getAttribute("class").indexOf("handle") >= 0)
			return child
	}
	return item
}

function join(name, isDoubleClick) {
	var view = document.getElementById(name + "View")
	view.editor = document.getElementById(name + "Edit")

	var showEditor = function(event) {
		event = fixEvent(event)

		var view = this
		var editor = view.editor

		if (!editor) return true

		if (editor.currentView != null) {
			editor.blur()
		}
		editor.currentView = view

		var topLeft = coordinates.topLeftOffset(view)
		topLeft.reposition(editor)
		if (editor.nodeName == 'TEXTAREA') {
			editor.style['width'] = view.offsetWidth + "px"
			editor.style['height'] = view.offsetHeight + "px"
		}
		editor.value = view.innerHTML
		editor.style['visibility'] = 'visible'
		view.style['visibility'] = 'hidden'
		editor.focus()
		return false
	}

	if (isDoubleClick) {
		view.ondblclick = showEditor
	} else {
		view.onclick = showEditor
	}

	view.editor.onblur = function(event) {
		event = fixEvent(event)

		var editor = event.target
		var view = editor.currentView

		if (!editor.abandonChanges) view.innerHTML = editor.value
		editor.abandonChanges = false
		editor.style['visibility'] = 'hidden'
		editor.value = '' // fixes firefox 1.0 bug
		view.style['visibility'] = 'visible'
		editor.currentView = null

		return true
	}

	view.editor.onkeydown = function(event) {
		event = fixEvent(event)

		var editor = event.target
		if (event.keyCode == TAB) {
			editor.blur()
			return false
		}
	}

	view.editor.onkeyup = function(event) {
		event = fixEvent(event)

		var editor = event.target
		if (event.keyCode == ESCAPE) {
			editor.abandonChanges = true
			editor.blur()
			return false
		} else if (event.keyCode == TAB) {
			return false
		} else {
			return true
		}
	}

	// TODO: this method is duplicated elsewhere
	function fixEvent(event) {
		if (!event)
			event = window.event

		if (event.target)
		{
			if (event.target.nodeType == 3)
				event.target = event.target.parentNode
		}
		else if (event.srcElement)
		{
			event.target = event.srcElement
		}

		return event
	}

}
//-->
</script>



<script src="ajax.js" ></script>
<script language="javascript" >
</script>
<link href="styles/easy.css" rel="stylesheet" type="text/css">

<p><span class="Estilo6" >Add New Block</span>  </p>
<p><br/>
</p>
<table width="322" border="0" >
  <tr>
    <td width="163">Block Name:</td>
    <td width="143"><input type="text" name="blockName" /></td>
  </tr>
  <tr>
    <td>Block Subtitle:</td>
    <td><input type="text" name="blockSubtitle" /></td>
  </tr>
</table>
<p><br/>
  <button type="button">Add</button>
  <br/>
</p>
<p>




<p>&nbsp;</p>
<p>&nbsp;</p>



<p>&nbsp;</p>
<table width="641" border="1">
  <tr>
    <td width="453">&nbsp;</td>
    <td width="172">&nbsp;</td>
  </tr>
  <tr>
    <td height="123">
    <table>
      <tbody>
        <tr>
          <td width="385"><div id="slideEditors">
              <!--
	IE 5.5+ BugFix: don't put these inside a 'position: relative' layer
	-->
              <input id="oneEdit" name="oneEdit" class="inplace" />
              <input id="twoEdit" name="oneEdit" class="inplace" />
              <input id="threeEdit" name="oneEdit" class="inplace" />
              <input id="fourEdit" name="oneEdit" class="inplace" />
              <input id="fiveEdit" name="oneEdit" class="inplace" />
              <input id="sixEdit" name="oneEdit" class="inplace" />
            </div>
              <ul id="slideshow" class="slideshow">
                <li class="slide">

 <table width="176" height="120" border="1">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>

                  <div id="twoView" class="view">Block 1</div>
                </li>
                <li class="slide">
                  <div class="thumb handle"><img src="dragableSlide2.png" /></div>
                  <div id="twoView" class="view">Slide 2</div>
                </li>
                <li class="slide">
                  <div class="thumb handle"><img src="dragableSlide3.png" /></div>
                  <div id="threeView" class="view">Slide 3</div>
                </li>
                <li class="slide">
                  <div class="thumb handle"><img src="dragableSlide4.png" /></div>
                  <div id="fourView" class="view">Slide 4</div>
                </li>
                <li class="slide">
                  <div class="thumb handle"><img src="dragableSlide5.png" /></div>
                  <div id="fiveView" class="view">Slide 5</div>
                </li>
                <li class="slide">
                  <div class="thumb handle"><img src="dragableSlide6.png" /></div>
                  <div id="sixView" class="view">Slide 6</div>
                </li>
              </ul></td>
        </tr>
      </tbody>
    </table>    </td>
    <td>&nbsp;</td>
  </tr>
</table>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p><a name="slideshow-example"></a>
</p>


  <!-- CONTENT ENDS HERE ____________________________________________________________ -->
</p>
</tiles:put>
</tiles:insert>

