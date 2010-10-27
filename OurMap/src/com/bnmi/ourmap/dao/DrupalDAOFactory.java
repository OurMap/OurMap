/*******************************************************************************

com.bnmi.ourmap.dao.DrupalDAOFactory.java
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

import com.bnmi.ourmap.daoimpl.DrupalPermissionDAO;
import com.bnmi.ourmap.daoimpl.MySqlProjectDAO;
import com.bnmi.ourmap.daoimpl.MySqlUserDAO;
import com.bnmi.ourmap.daoimpl.PostgresActionDAO;
import com.bnmi.ourmap.daoimpl.PostgresCatKwDAO;
import com.bnmi.ourmap.daoimpl.PostgresCategoryDAO;
import com.bnmi.ourmap.daoimpl.PostgresHotspotDAO;
import com.bnmi.ourmap.daoimpl.PostgresHotspotObjectDAO;
import com.bnmi.ourmap.daoimpl.PostgresIconDAO;
import com.bnmi.ourmap.daoimpl.PostgresKeywordDAO;
import com.bnmi.ourmap.daoimpl.PostgresLayerDAO;
import com.bnmi.ourmap.daoimpl.PostgresMapDAO;
import com.bnmi.ourmap.daoimpl.PostgresObjCategoryDAOImpl;
import com.bnmi.ourmap.daoimpl.PostgresObjectDAO;
import com.bnmi.ourmap.daoimpl.PostgresRolActionDAO;
import com.bnmi.ourmap.daoimpl.PostgresRolDAO;
import com.bnmi.ourmap.daoimpl.PostgresUserProjectDAO;
import com.bnmi.ourmap.daoimpl.PostgresUserRolDAO;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class DrupalDAOFactory extends DAOFactory {

    @Override
    public UserDAO getUserDAO() {
        return new MySqlUserDAO();
    }

    @Override
    public RolDAO getRolDAO() {
        return new PostgresRolDAO();
    }

    @Override
    public ActionDAO getActionDAO() {
        return new PostgresActionDAO();
    }

    @Override
    public ProjectDAO getProjectDAO() {
        return new MySqlProjectDAO();
    }

    @Override
    public RolActionDAO getRolActionDAO() {
        return new PostgresRolActionDAO();
    }

    @Override
    public UserProjectDAO getUserProjectDAO() {
        return new PostgresUserProjectDAO();
    }

    @Override
    public UserRolDAO getUserRolDAO() {
        return new PostgresUserRolDAO();
    }

    @Override
    public MapDAO getMapDAO() {
        return new PostgresMapDAO();
    }

    @Override
    public LayerDAO getLayerDAO() {
        return new PostgresLayerDAO();
    }

    @Override
    public IconDAO getIconDAO() {
        return new PostgresIconDAO();

    }

    @Override
    public HotspotDAO getHotspotDAO() {
        return new PostgresHotspotDAO();
    }

    @Override
    public ObjectDAO getObjectDAO() {
        return new PostgresObjectDAO();
    }

    @Override
    public HotspotObjectDAO getHotspotObjectDAO() {
        return new PostgresHotspotObjectDAO();
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new PostgresCategoryDAO();
    }

    @Override
    public KeywordDAO getKeywordDAO() {
        return new PostgresKeywordDAO();
    }

    @Override
    public CatKwDAO getCatKwDAO() {
        return new PostgresCatKwDAO();
    }

    @Override
    public ObjCategoryDAO getObjCategoryDAO() {
        return new PostgresObjCategoryDAOImpl();
    }

    @Override
    public PermissionDAO getPermissionDAO() {
        return new DrupalPermissionDAO();
    }

 
}