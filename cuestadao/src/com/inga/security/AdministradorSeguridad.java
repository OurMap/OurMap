/**

com.inga.security.AdministradorSeguridad.java
Version: 1.0

********************************************************************************
Author:
Manuel Cuesta, programmer <camilocuesta@hotmail.com>

**************************************************

cuestadao is Copyright (c) 2010, Manuel Cuesta  <camilocuesta@hotmail.com >
All rights reserved.

Published under the terms of the new BSD license.
See: [http://github.com/m-cuesta/tiers] for the full license and other info.

LICENSE:

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.

Neither the name of Manuel Cuesta nor the names of its contributors may be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.


**************************************************
Revision History / Change Log:

**************************************************
Notes:

*******************************************************************************/

package com.inga.security;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * Administra la seguridad de la aplicación web.
 * 
 * Created on Jan 30th 2007, 10:32
 * @author Manuel Camilo Cuesta
 *
 */
public class AdministradorSeguridad {


  private static Hashtable<String,Role> roles;

  public static void init() throws FileNotFoundException, IOException {
    initRoles();
  }

  public static boolean hasPermission(User user, String resource) {
    
    if ( roles == null )
    {
      try 
      {
        init();
      }
      catch (Exception e) 
      {
        e.printStackTrace();
      }
    }
    
    if (resource == null || resource.length() == 0)
      return false;
    
    if (!resource.startsWith("/"))
      resource = "/" + resource;
    
    // No tiene permisos hasta que se verifique lo contrario
    boolean hasPermission = false;
    
    // Se obtienen todos los roles del usuario
    Vector userRoles = user.getRoles();
    
    // Se iteran los roles del usuario
    Iterator rolIt = userRoles.iterator();
    while ( rolIt.hasNext() ) 
    {
      // Nombre del rol del usuario
      String roleName = (String) rolIt.next();
      
      // Se verifica si ese rol está registrado en la aplicación
      Role rol = roles.get(roleName);
      
      // Si no esta registrado, continua verificando el siguiente rol
      if ( rol == null )
         continue;
      
      // Si está registrado, pero, ¿tiene permisos para el recurso?
      if (rol.hasPermission(resource))
         hasPermission = true;
    }
    return hasPermission;
  }

  private static void initRoles() throws FileNotFoundException, IOException {
    
    roles = new Hashtable<String,Role>();
    String permissions[] = new String[] {"*"};
    Role myRol = new Role();
    
    myRol.setName("admin");
    myRol.setPermissions( permissions );
    
    roles.put( myRol.getName(), myRol);
    
  }
 
}