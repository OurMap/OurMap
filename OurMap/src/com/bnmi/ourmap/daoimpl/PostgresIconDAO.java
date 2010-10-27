/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresIconDAO.java
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

import com.bnmi.ourmap.Constantes;
import com.inga.dao.BasicDAO;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.bnmi.ourmap.model.Icon;
import com.bnmi.ourmap.dao.IconDAO;
import com.inga.dao.Sentencia;
import java.util.Vector;

/*
 * PostgresIconDAO.java
 *
 * Created on Wed Jan 27 14:28:05 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresIconDAO extends BasicDAO implements IconDAO {

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<Icon> results = new Vector<Icon>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    Icon o = new Icon();
                    
                    o.setIconId( new Integer(rs.getInt("object_id")) );
                    
                    results.add( o );
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            rs.close();
        }
        catch ( SQLException e )
        {
            throw new BDException(e);
        }
        
        return results;
    }

    @Override
    public Integer getDefaultId() throws NoConnectionException, BDException {

        if ( conn == null )
            throw new NoConnectionException();

        String sql = "select object_id from objects where obj_type = " + Constantes.ICON + " order by object_id limit 1";
        Sentencia sq = new Sentencia(user,conn);
        ResultSet rs = sq.executeQuery(sql);
        try
        {
            if ( rs.next() )
            {
                Integer first= rs.getInt("object_id");
                return first;
            }
            else
                return null;

        }
        catch ( SQLException ex )
        {
            throw new BDException(ex);
        }
    }
    
}