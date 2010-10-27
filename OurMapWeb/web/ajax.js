/*******************************************************************************

ajax.js
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

*******************************************************************************/


// Variables globales ----------------------------------------------------------

// Funciones varias ------------------------------------------------------------

  function nuevaVentana( url ) {
	window.open( url,'popup', 'resizable=yes,menubar=no,location=no,toolbar=no,status=no,scrollbars=yes,directories=no,width=550,height=430,left=80,top=50');
  }

  function refreshParent() {
      try
      {
          window.opener.location.reload( true );
      }
      catch ( e )
      {

      }
  }

function validarCadena(str) {
  if ( str == null ) 
      return false;
  if ( str.length == 0 )
      return false;
  if ( str == 'null' )
      return false;
  return true;
}

function validateUsername(fld) {
    var error = "";
    var illegalChars = /\W/; // allow letters, numbers, and underscores
    var val = fld.value.trim();

    if (val == "") {
        error = "You didn't enter a username.\n";
    } else if ((val.length < 1) ) {
        error = "Min length for User ID: 5 characters\n";
    } else if ((val.length > 20) ) {
        error = "Max length for User ID: 10 characters\n";
    } else if (illegalChars.test( val )) {
        error = "The username contains illegal characters.\n";
    } else {
        fld.style.background = 'White';
    }
    return error;
}

function validatePassword(fld) {
    var error = "";
    var val = fld.value.trim();

    if ( val == "") {
        error = "Password is required.\n";
    } else if ((val.length < 5 ) ) {
        error = "Min length for password: 5 characters\n";
    } else if (( val.length > 10 )) {
        error = "Max length for password: 10 characters\n";
    } else {
        fld.style.background = 'White';
    }
    return error;
}


function validarNumero(num) {

  if ( num == null )
     return false;
  if ( num == 'null' )
      return false;
  if ( num.length == 0 )
      return false;
  if ( isNaN(num) )
      return false;

  return true;
}


/** Obtiene el valor del objeto seleccionado en un select, o dropdown list */
function getSelectedValue( selectObject ) {
    return selectObject.options[ selectObject.selectedIndex ].value;
}

function setSelectValue( selectObject, val ) {
    for ( var i = 0; i < selectObject.options.length; i++ )
    {
        if ( selectObject.options[i].value == val )
        {
            selectObject.selectedIndex = i;
            break;
        }
    }
}

// Obtiene el valor seleccionado de un radio
// returns the value of the radio button that is checked
// returns an empty string if none are checked, or
// there are no radio buttons
function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if ( radioObj.checked )
		    return radioObj.value;
		else
		    return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

/** Carga el correspondiente url en en frame de menú */
function cargarMenu(url) {
    top.frames['barramenu'].location=url;
}


function checkAll(field, val)
{
   for (i = 0; i < field.length; i++)
	field[i].checked = val ;
}

function toggleBox(szDivID, iState) // 1 visible, 0 hidden
{
   var obj = document.layers ? document.layers[szDivID] :
   document.getElementById ?  document.getElementById(szDivID).style :
   document.all[szDivID].style;
   obj.visibility = document.layers ? (iState ? "show" : "hide") :
   (iState ? "visible" : "hidden");
}

function toggleLayer( whichLayer )
{
  var elem, vis;
  if( document.getElementById ) // this is the way the standards work
    elem = document.getElementById( whichLayer );
  else if( document.all ) // this is the way old msie versions work
      elem = document.all[whichLayer];
  else if( document.layers ) // this is the way nn4 works
    elem = document.layers[whichLayer];
  vis = elem.style;
  // if the style.display value is blank we try to figure it out here
  if(vis.display==''&&elem.offsetWidth!=undefined&&elem.offsetHeight!=undefined)
    vis.display = (elem.offsetWidth!=0&&elem.offsetHeight!=0)?'block':'none';
  vis.display = (vis.display==''||vis.display=='block')?'none':'block';
}

function isShown( whichLayer ) {
  var elem, vis;
  if( document.getElementById ) // this is the way the standards work
    elem = document.getElementById( whichLayer );
  else if( document.all ) // this is the way old msie versions work
      elem = document.all[whichLayer];
  else if( document.layers ) // this is the way nn4 works
    elem = document.layers[whichLayer];
  vis = elem.style;
  if ( vis.display==''||vis.display=='block')
      return true;
  else
      return false;
}

function showLayer( whichLayer )
{
  var elem, vis;
  if( document.getElementById ) // this is the way the standards work
    elem = document.getElementById( whichLayer );
  else if( document.all ) // this is the way old msie versions work
      elem = document.all[whichLayer];
  else if( document.layers ) // this is the way nn4 works
    elem = document.layers[whichLayer];
  vis = elem.style;
  // if the style.display value is blank we try to figure it out here
  if(vis.display==''&&elem.offsetWidth!=undefined&&elem.offsetHeight!=undefined)
    vis.display = 'block' ;
  vis.display = 'block' ;
}

function hideLayer( whichLayer )
{
  var elem, vis;
  if( document.getElementById ) // this is the way the standards work
    elem = document.getElementById( whichLayer );
  else if( document.all ) // this is the way old msie versions work
      elem = document.all[whichLayer];
  else if( document.layers ) // this is the way nn4 works
    elem = document.layers[whichLayer];
  vis = elem.style;
  // if the style.display value is blank we try to figure it out here
  if(vis.display==''&&elem.offsetWidth!=undefined&&elem.offsetHeight!=undefined)
    vis.display = 'none' ;
  vis.display = 'none';
}

  function findPosX(obj)
  {
    var curleft = 0;
    if(obj.offsetParent)
        while(1)
        {
          curleft += obj.offsetLeft;
          if(!obj.offsetParent)
            break;
          obj = obj.offsetParent;
        }
    else if(obj.x)
        curleft += obj.x;
    return curleft;
  }
 function findPosY(obj)
  {
    var curtop = 0;
    if(obj.offsetParent)
        while(1)
        {
          curtop += obj.offsetTop;
          if(!obj.offsetParent)
            break;
          obj = obj.offsetParent;
        }
    else if(obj.y)
        curtop += obj.y;
    return curtop;
  }




// Funciones DOM ---------------------------------------------------------------

  // Elimina espacios delante y atrás de la cadena
  String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
  }  
    
  // Obtiene un hijo de un nodo por su nombre
  function getChild( node, childName ) {
      var size = node.childNodes.length;
      var i = 0;
      for ( i = 0; i < size; i ++ )
      {
          var child = node.childNodes[i];
          if ( child.nodeName == childName )
             return child;
      }
      
      return null;
  }
  
  
  
  // Obtiene los hijos de un nodo por su nombre
  function getChildren( node, childName ) {
      var size = node.childNodes.length;
      var i = 0;
      var cont = 0;
      var child;
      for ( i = 0; i < size; i ++ )
      {
          child = node.childNodes[i];
          if ( child.nodeName == childName )
             cont++;
      }
      
      var children = new Array(cont);
      var cont2 = 0;
      for ( i = 0; i < size; i ++ )
      {
          child = node.childNodes[i];
          if ( child.nodeName == childName )
             children[cont2++] = child;
      }
      
      return children;
  }
  
  
  // Obtiene un atributo de un nodo
  function getAttribute( node, attName ) {
      var size = node.attributes.length;
      var i = 0;
      for ( i = 0; i < size; i ++ )
      {
          var child = node.attributes[i];
          if ( child.nodeName == attName )
             return child;
      }
      
      return null;
  
  }

  // Obtiene el text o valor de un nodo, sin espacios adelante o atrás
  function getText( node ) {
     var text = getChild( node, '#text' );
     if ( text ) 
        return text.nodeValue.trim();
     else
        return null;
  }

  // Obtiene el primer nodo con id "nodeId" que encuentre entre los descendientes
  // de "node"
  function findElement(node, nodeId ) {

    if ( node.id == nodeId )
        return node;

    for ( var i = 0; i < node.childNodes.length; i++ )
    {
        var temp = findElement( node.childNodes[i], nodeId );
        if ( temp )
            return temp
    }

    
    return null;
            
}    

function isIE() {
    if ( navigator.appName == 'Microsoft Internet Explorer' )
        return true;
    else
        return false;
}

function pause(numberMillis)
{
var now = new Date();
var exitTime = now.getTime() + numberMillis;
while (true)
{
now = new Date();
if (now.getTime() > exitTime)
return;
}
}

// Specific functions
function cancelThis( url ) {
    if ( confirm('Unsaved data will be lost. Continue?') )
       document.location = url ;
}

function disableEnterKey(e)
{
     var key;
     if(window.event)
          key = window.event.keyCode; //IE
     else
          key = e.which; //firefox

     return (key != 13);
}



// Funciones Ajax --------------------------------------------------------------

function  createRequest() {

      var http_request = false;

      if (window.XMLHttpRequest) 
      { // Mozilla, Safari,...
         http_request = new XMLHttpRequest();

         if (http_request.overrideMimeType) 
         {
            // set type accordingly to anticipated content type
            //http_request.overrideMimeType('text/xml');
            //http_request.overrideMimeType('text/html');
         }
      } 
      else if (window.ActiveXObject) 
      { // IE
         try 
         {
            http_request = new ActiveXObject("Msxml2.XMLHTTP");
         } 
         catch (e) 
         {
            try 
            {
               http_request = new ActiveXObject("Microsoft.XMLHTTP");
            } 
            catch (e) {}
         }
      }

      if (!http_request) 
      {
         alert('Cannot create XMLHTTP instance');
         return false;
      }

      return http_request;


}

/** Ejecuta un requerimiento GET AJAX
* url :  es la dirección a la que quiere acceder
* onReadyFunction : Función a la que se debe llamar cuando llegue el requerimiento
*
*/
function makeRequest(url, onReadyFunction ) {

    var http_request = createRequest();

    if (!http_request) 
    {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }

    http_request.onreadystatechange = onReadyFunction;
    http_request.open('GET', url, true);
    http_request.send(null);
    
    return true;

}

/** Ejecuta un requerimiento POST con AJAX */
function makePOSTRequest(http_request, url, parameters, onReadyFunction ) {
    
      http_request.onreadystatechange = onReadyFunction ;
      http_request.open('POST', url, true);
      http_request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
      http_request.setRequestHeader("Content-length", parameters.length);
      http_request.setRequestHeader("Connection", "close");
      http_request.send(parameters);

}

/** Ejecuta el comando ajax, especificado por el nombre del comando "cmd", y
*   escribe el resultado en el div del documento con el id "divId"
*/
function executeAjax( url, parameters, divId ) {

    var http_request = createRequest();

    makePOSTRequest( http_request, url , parameters, function() {
        if (http_request.readyState == 4) 
        {
            if (http_request.status == 200) 
                document.getElementById( divId ).innerHTML = http_request.responseText;
            else
            {
                //alert('There was a problem with the request.');
            }
        }
    });


}

// Funciones Ajax para el text suggester ---------------------------------------

//Called from keyup on the search textbox.
//Starts the AJAX request.
function searchSuggest(url, parameters, ac ) {

    var searchReq = createRequest();

    if (searchReq.readyState == 4 || searchReq.readyState == 0) 
    {
        
        makePOSTRequest( searchReq, url, parameters, function() {

            if (searchReq.readyState == 4 ) 
             {
                
		var xmlDoc = searchReq.responseXML;
                var root = xmlDoc.documentElement;

                
                var body = getChild(root, "body" );

                var nombres = getChildren( body, "nombre" );
                var size = nombres.length;
                var nomArray = new Array(size);
                for (i = 0; i < size ; i++ ) 
                       nomArray[i] = getText(nombres[i]);
                
                ac.actb_keywords = nomArray;

             }
            
        });

    } 
}

/*
 *
 * Template for ajax method
         var http_request = createRequest();

         var params =  'layerId=' + layerId;

         makePOSTRequest( http_request, 'deleteLayer.do' , params, function() {
         if (http_request.readyState == 4 && http_request.status == 200 )
         {
           var xmlDoc = http_request.responseXML;
           var root = xmlDoc.documentElement;
           var estado = getChild( root, 'estado' );
           if ( estado && getText(estado) == 'OK' )
           {
               t.deleteRow( rowNumber );
           }
           else if ( estado && getText(estado) == 'FAIL' )
           {
               var mensaje = getChild( root, 'mensaje' );
               alert( getText(mensaje) );
           }

         }
         });

 *  *
 */