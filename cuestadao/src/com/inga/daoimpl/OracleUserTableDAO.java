/**

com.inga.daoimpl.OracleUserTableDAO.java
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

package com.inga.daoimpl;

import com.inga.dao.BasicDAO;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.inga.dao.UserTableDAO;
import com.inga.modelo.CriteriosUserTable;
import com.inga.modelo.UserTable;
import com.inga.utils.SqlClauseHelper;
import java.util.Vector;

/** Models a OracleUserTableDAO
 *
 * OracleUserTableDAO.java
 *
 * Created on Tue Apr 17 14:36:49 COT 2007
 * @author Manuel Camilo Cuesta
 *
 */
public class OracleUserTableDAO extends BasicDAO implements UserTableDAO{

    @SuppressWarnings("unchecked")
    @Override
    public Vector<UserTable> find(CriteriosUserTable criteria) throws NoConnectionException, BDException {
        Vector<UserTable> results = new Vector<UserTable>();
        
        String sql = "select table_name from user_tables union select view_name as table_name from user_views";
        
        SqlClauseHelper sh = new SqlClauseHelper();
        
        if ( criteria.getTableName() != null )
            sh.addAndClause("TABLE_NAME = '" + criteria.getTableName() + "'" );
        
        
        String clause = sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        results = executeQuery( sql );
        
        
        return results;
     }
        

    @Override 
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<UserTable> results = new Vector<UserTable>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    UserTable o = new UserTable();
                    
                    o.setTableName( rs.getString("TABLE_NAME") );
                    
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

}