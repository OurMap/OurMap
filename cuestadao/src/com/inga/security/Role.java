/**

com.inga.security.Role.java
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


/*
 * Role.java
 *
 * Created on Aug 9 2006, 0:00
 * @author Manuel Camilo Cuesta
 *
 */
public class Role {

  private String[] permissions;
  private String name;

  /** Creates a new instance of Role */
  public Role(String pName, String[] pPermissions) {
    setName(pName);
    setPermissions(pPermissions);
  }

  public Role() {
  }

  public String getName() {
    return name;
  }

  public void setName(String pName) {
    name = pName;
  }

  public String[] getPermissions() {
    return permissions;
  }

  public void setPermissions(String perm[]) {
    permissions = perm;
  }

  public boolean hasPermission(String url) {
    for (int i = 0; i < permissions.length; i++) {
      String regex = permissions[i];

      // Se reemplaza '.' por '\.'
      regex = regex.replaceAll("\\.", "\\\\.");
      // Se reemplaza '*' por '.*'
      regex = regex.replaceAll("\\*", ".*");
      
      if ( url.matches(regex) )
        return true;
    }
    return false;
  }
  


    @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(name + " (");
    for (int i = 0; i < permissions.length; i++) {
      str.append(permissions[i]);
      if (i < permissions.length - 1)
        str.append(", ");
    }
    str.append(")");
    return str.toString();
  }
}
