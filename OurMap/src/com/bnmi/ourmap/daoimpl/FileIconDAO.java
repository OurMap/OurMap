/*******************************************************************************

com.bnmi.ourmap.daoimpl.FileIconDAO.java
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

package com.bnmi.ourmap.daoimpl;

import com.bnmi.ourmap.model.CriteriosIcon;
import com.bnmi.ourmap.model.Icon;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.exception.RegistroNoExisteException;
import com.inga.security.User;
import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.util.Vector;

public class FileIconDAO {

    private String folder;
    private User user;

    public Vector<Icon> find(CriteriosIcon criteria) throws NoConnectionException, BDException {
        Vector<Icon> icons = new Vector<Icon>();
        String regex = ".*";
        /*
        if ( criteria.getRegex() != null )
            regex = criteria.getRegex();


        MyFileFilter filter = new MyFileFilter( regex );

        File myFolder = new File(folder + File.separator + "images" + File.separator + "icons");
        File files[] = myFolder.listFiles(filter);

        for ( File f : files )
            icons.add( new Icon( f.getName() ));
*/
        return icons;

    }

    public Icon get(String filename) throws NoConnectionException, BDException, RegistroNoExisteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Integer create(Icon registro) throws NoConnectionException, BDException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int update(Icon registro) throws NoConnectionException, BDException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int delete(String filename) throws NoConnectionException, BDException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public User getUser() {
        return user;
    }

    public Connection getConnection() {
        return null;
    }

    public void setConnection(Connection pConn) {

    }

    public void setUser(User pUser) {
        user = pUser;
    }

    /**
     * @return the folder
     */
    public String getFolder() {
        return folder;
    }

    /**
     * @param folder the folder to set
     */
    public void setFolder(String folder) {
        this.folder = folder;
    }


    class MyFileFilter implements FilenameFilter {

        private String regex;

        public MyFileFilter(String regex) {
            this.regex = regex;
        }

        public boolean accept(File dir, String name) {

            if ( name.matches(regex) )
                return true;
            else
                return false;
            
        }

    }

}
