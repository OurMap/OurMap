/*******************************************************************************

com.bnmi.ourmap.web.actions.AddMapOwnerForm.java
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

package com.bnmi.ourmap.web.actions;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class AddMapOwnerForm extends ActionForm {

    private String[] allUsersBox;
    private String[] ownersBox;
    private String mapid;
    private String operation;
    private String selectedUsers;
    private List<String> selectedUsersList;


    public String getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(String pSelectedUsers) {
        selectedUsers = pSelectedUsers;
    }

    public List<String> getSelectedUsersList() {
        selectedUsersList = new ArrayList<String>();

        if ( selectedUsers != null && ! selectedUsers.isEmpty() )
        {
            String usersArray[] = selectedUsers.split(",");
            for ( String user : usersArray )
                if ( user != null && ! user.isEmpty() )
                    selectedUsersList.add( user );
        }

        return selectedUsersList;
    }



    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        allUsersBox = new String[0];
        ownersBox = new String[0];
        mapid = null;
        operation = null;
    }


    /**
     * @return the mapid
     */
    public String getMapid() {
        return mapid;
    }

    /**
     * @param mapid the mapid to set
     */
    public void setMapid(String mapid) {
        this.mapid = mapid;
    }

    /**
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String action) {
        this.operation = action;
    }

    /**
     * @return the AddMapMemberForm
     */
    public String[] getAllUsersBox() {
        return allUsersBox;
    }

    /**
     * @param AddMapMemberForm the AddMapMemberForm to set
     */
    public void setAllUsersBox(String[] notMembersBox) {
        this.allUsersBox = notMembersBox;
    }

    /**
     * @return the ownersBox
     */
    public String[] getOwnersBox() {
        return ownersBox;
    }

    /**
     * @param ownersBox the ownersBox to set
     */
    public void setOwnersBox(String[] ownersBox) {
        this.ownersBox = ownersBox;
    }


}
