/**

com.inga.daoimpl.SsUserTabColDAO.java
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
import com.inga.dao.UserTabColDAO;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.modelo.CriteriosUserTabCol;
import com.inga.modelo.UserTabCol;
import com.inga.utils.SqlClauseHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * Created on Jan 30th 2007, 10:32
 * @author Manuel Camilo Cuesta
 *
 */
public class SsUserTabColDAO extends BasicDAO implements UserTabColDAO {

    @Override
    protected Vector extract(ResultSet rs) throws BDException {
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
                    o.setNullable( rs.getString("IS_NULLABLE") );

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
    public Vector<UserTabCol> find(CriteriosUserTabCol criteria) throws NoConnectionException, BDException {

        Vector<UserTabCol> results = new Vector<UserTabCol>();
        String sql = "SELECT table_name,column_name,data_type, is_nullable from information_schema.columns ";

        sql = sql.toUpperCase();
        
        SqlClauseHelper sh = new SqlClauseHelper();

        if ( criteria.getTableName() != null )
            sh.addAndClause("TABLE_NAME = '" + criteria.getTableName() + "'" );
        if ( criteria.getColumnName() != null )
            sh.addAndClause("COLUMN_NAME = '" + criteria.getColumnName() + "'" );
        if ( criteria.getDataType() != null )
            sh.addAndClause("DATA_TYPE = '" + criteria.getDataType() + "'" );
        if ( criteria.getNullable() != null )
            sh.addAndClause("NULLABLE = '" + criteria.getNullable() + "'" );


        String clause = sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;

        results = executeQuery( sql );

        return results;

    }

    @Override
    public Vector<UserTabCol> getPrimaryKeys(String tablename) throws NoConnectionException, BDException {

       String sql= "SELECT A.TABLE_NAME, B.COLUMN_NAME, C.DATA_TYPE, C.IS_NULLABLE \n" +
                   "     FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS A, INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE B,  \n" +
                   "     INFORMATION_SCHEMA.COLUMNS C \n" +
                   "WHERE \n"+
                   "    A.TABLE_NAME = '" + tablename + "'  \n" +
                   "AND CONSTRAINT_TYPE = 'PRIMARY KEY'  \n" +
                   "AND A.CONSTRAINT_NAME = B.CONSTRAINT_NAME  \n" +
                   "AND c.table_name = a.table_name  \n" + 
                   "AND c.column_name = b.column_name  " ;

       sql = sql.toUpperCase();

       Vector<UserTabCol> results = new Vector<UserTabCol>();
       results = executeQuery( sql );
       return results;
       
    }




}
