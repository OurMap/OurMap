/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresUserProjectDAO.java
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

import com.inga.dao.BasicDAO;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.exception.RegistroNoExisteException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.inga.utils.SqlClauseHelper;
import com.bnmi.ourmap.model.UserProject;
import com.bnmi.ourmap.model.CriteriosUserProject;
import com.bnmi.ourmap.dao.UserProjectDAO;
import java.util.Vector;

/*
 * PostgresUserProjectDAO.java
 *
 * Created on Tue Dec 08 19:37:36 COT 2009
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresUserProjectDAO extends BasicDAO implements UserProjectDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @SuppressWarnings("unchecked")
    @Override
    public Vector<UserProject> find(CriteriosUserProject criteria) throws NoConnectionException, BDException {
        Vector<UserProject> results = new Vector<UserProject>();
        
        String sql = "select * from user_project";
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getUserid() != null )
            _sh.addAndClause("userid = '" + criteria.getUserid() + "'" );
        if ( criteria.getProject() != null )
            _sh.addAndClause("project = " + criteria.getProject().intValue() );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        //sql = sql + " order by ...";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public UserProject get( java.lang.String userid, java.lang.Integer project ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from user_project where userid = '" + userid + "'"  + " and project = " + project;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (UserProject) results.firstElement();
    }

    @Override
    public Integer create(UserProject registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(UserProject registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into user_project (");
        sql.append("userid, project");
        sql.append(")");
        sql.append(" values (");
        
           if ( registro.getUserid() == null )
               sql.append( "null" + "," );
           else
               sql.append( "'" + registro.getUserid() + "'" + "," );
           if ( registro.getProject() == null )
               sql.append( "null" );
           else
               sql.append( registro.getProject().intValue() );
        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(UserProject registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(UserProject registro) {
        
        String sql = "update user_project";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        
        sql = sql + " set " + sh.getClause() + " where userid = '" + registro.getUserid() + "'"  + " and project = " + registro.getProject();
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.String userid, java.lang.Integer project ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(userid, project) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.String userid, java.lang.Integer project) {
        
        String sql = "delete from user_project where userid = '" + userid + "'"  + " and project = " + project;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<UserProject> results = new Vector<UserProject>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    UserProject o = new UserProject();
                    
                    o.setUserid( rs.getString("userid") );
                    o.setProject( new Integer(rs.getInt("project")) );
                    
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