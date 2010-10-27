/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresUserRolDAO.java
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
import com.bnmi.ourmap.model.UserRol;
import com.bnmi.ourmap.model.CriteriosUserRol;
import com.bnmi.ourmap.dao.UserRolDAO;
import java.util.Vector;

/*
 * PostgresUserRolDAO.java
 *
 * Created on Mon Dec 07 21:48:21 COT 2009
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresUserRolDAO extends BasicDAO implements UserRolDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @SuppressWarnings("unchecked")
    @Override
    public Vector<UserRol> find(CriteriosUserRol criteria) throws NoConnectionException, BDException {
        Vector<UserRol> results = new Vector<UserRol>();
        
        String sql = "select * from user_rol";
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getUserid() != null )
            _sh.addAndClause("userid = '" + criteria.getUserid() + "'" );
        if ( criteria.getRol() != null )
            _sh.addAndClause("rol = '" + criteria.getRol() + "'" );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        //sql = sql + " order by ...";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public UserRol get( java.lang.String userid, java.lang.String rol ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from user_rol where userid = '" + userid + "'"  + " and rol = '" + rol + "'" ;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (UserRol) results.firstElement();
    }

    @Override
    public Integer create(UserRol registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(UserRol registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into user_rol (");
        sql.append("userid, rol");
        sql.append(")");
        sql.append(" values (");
        
           if ( registro.getUserid() == null )
               sql.append( "null" + "," );
           else
               sql.append( "'" + registro.getUserid() + "'" + "," );
           if ( registro.getRol() == null )
               sql.append( "null" );
           else
               sql.append( "'" + registro.getRol() + "'" );
        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(UserRol registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(UserRol registro) {
        
        String sql = "update user_rol";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        
        sql = sql + " set " + sh.getClause() + " where userid = '" + registro.getUserid() + "'"  + " and rol = '" + registro.getRol() + "'" ;
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.String userid, java.lang.String rol ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(userid, rol) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.String userid, java.lang.String rol) {
        
        String sql = "delete from user_rol where userid = '" + userid + "'"  + " and rol = '" + rol + "'" ;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<UserRol> results = new Vector<UserRol>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    UserRol o = new UserRol();
                    
                    o.setUserid( rs.getString("userid") );
                    o.setRol( rs.getString("rol") );
                    
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