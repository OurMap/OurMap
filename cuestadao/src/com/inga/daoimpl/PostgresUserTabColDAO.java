/**

com.inga.daoimpl.PostgresUserTabColDAO.java
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
import com.inga.dao.UserTabColDAO;
import com.inga.modelo.CriteriosUserTabCol;
import com.inga.modelo.UserTabCol;
import java.util.Vector;

/** Models a PostgresUserTabColDAO
 *
 * PostgresUserTabColDAO.java
 *
 * Created on Tue Apr 17 14:31:42 COT 2007
 * @author Manuel Camilo Cuesta
 *
 */
public class PostgresUserTabColDAO extends BasicDAO implements UserTabColDAO {
    
    @SuppressWarnings("unchecked")
    @Override
    public Vector<UserTabCol> find(CriteriosUserTabCol criteria) throws NoConnectionException, BDException {
        Vector<UserTabCol> results = new Vector<UserTabCol>();
        
        String tableName = criteria.getTableName();
        String sql = "SELECT " + "'" + tableName + "'" + " as table_name, a.attname as column_name, pg_catalog.format_type(a.atttypid, a.atttypmod) as data_type,(SELECT substring(pg_catalog.pg_get_expr(d.adbin, d.adrelid) for 128) FROM pg_catalog.pg_attrdef d WHERE d.adrelid = a.attrelid AND d.adnum = a.attnum AND a.atthasdef), a.attnotnull as nullable, a.attnum FROM pg_catalog.pg_attribute a WHERE a.attrelid =  ( SELECT c.oid FROM pg_catalog.pg_class c LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace WHERE c.relname ~ '^" + tableName + "$' AND pg_catalog.pg_table_is_visible(c.oid) ) AND a.attnum > 0 AND NOT a.attisdropped ORDER BY a.attnum";
        
        results = executeQuery( sql );
        
        
        return results;
     }
        


    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<UserTabCol> results = new Vector<UserTabCol>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    UserTabCol o = new UserTabCol();
                    
                    o.setTableName( rs.getString("TABLE_NAME") );
                    o.setColumnName( rs.getString("COLUMN_NAME") );
                    o.setDataType( rs.getString("DATA_TYPE") );
                    o.setNullable( rs.getString("NULLABLE") );
                    
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
    public Vector<UserTabCol> getPrimaryKeys(String tablename) throws NoConnectionException, BDException {
        String sql = "SELECT 	pg_class.relname AS TABLE_NAME," +
                " 	a.attname AS COLUMN_NAME," +
                "	pg_catalog.format_type(a.atttypid, a.atttypmod) as data_type," +
                "	a.attnotnull as nullable," +
                " 	a.attnum" +
                " FROM" +
                " 	pg_class," +
                " 	pg_attribute a," +
                " 	pg_index" +
                " WHERE" +
                "       pg_class.relname = '" + tablename + "'" +
                " AND 	pg_class.oid = a.attrelid " +
                " AND   pg_class.oid = pg_index.indrelid " +
                " AND   a.attnum = any( pg_index.indKey )" +
                " AND   pg_index.indisprimary = 't'";
        
        return executeQuery( sql );
    }
    
        
    
}