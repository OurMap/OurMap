/*******************************************************************************

com.bnmi.ourmap.dao.DAOFactory.java
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

package com.bnmi.ourmap.dao;

/** Clase abstracta que implementa una fábrica de DAOs
 *
 * @author Manuel Camilo Cuesta
 */
public abstract class DAOFactory {

  // List of DAO types supported by the factory
  public static final int ORACLE = 1;
  public static final int POSTGRES = 2;
  public static final int DRUPAL = 3;
  public static final int FILE = 4;

  // There will be a method for each DAO that can be
  // created. The concrete factories will have to
  // implement these methods.
  public abstract UserDAO getUserDAO();
  public abstract RolDAO getRolDAO();
  public abstract ActionDAO getActionDAO();
  public abstract ProjectDAO getProjectDAO();
  public abstract RolActionDAO getRolActionDAO();
  public abstract UserProjectDAO getUserProjectDAO();
  public abstract UserRolDAO    getUserRolDAO();
  public abstract MapDAO        getMapDAO();
  public abstract LayerDAO      getLayerDAO();
  public abstract IconDAO       getIconDAO();
  public abstract HotspotDAO    getHotspotDAO();
  public abstract ObjectDAO     getObjectDAO();
  public abstract HotspotObjectDAO getHotspotObjectDAO();
  public abstract CategoryDAO   getCategoryDAO();
  public abstract KeywordDAO    getKeywordDAO();
  public abstract CatKwDAO      getCatKwDAO();
  public abstract ObjCategoryDAO getObjCategoryDAO();
  public abstract PermissionDAO getPermissionDAO();


  public static DAOFactory getDAOFactory(int whichFactory) {

    switch (whichFactory)
    {
        case POSTGRES:
          return new PostgresDAOFactory();
        case DRUPAL :
          return new DrupalDAOFactory();
        case FILE:
          return new FileDAOFactory();
      default        :
          return null;
    }
  }


}

