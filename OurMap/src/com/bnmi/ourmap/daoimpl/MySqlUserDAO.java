/*******************************************************************************

com.bnmi.ourmap.daoimpl.MySqlUserDAO.java
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

import com.bnmi.ourmap.dao.UserDAO;
import com.bnmi.ourmap.model.CriteriosUser;
import com.bnmi.ourmap.model.User;
import com.inga.dao.BasicDAO;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.exception.RegistroNoExisteException;
import com.inga.utils.SigarUtils;
import com.inga.utils.SqlClauseHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class MySqlUserDAO extends BasicDAO implements UserDAO {

    /*
+-----+--------------------+
| rid | name               |
+-----+--------------------+
|   1 | anonymous user     |
|   2 | authenticated user |
|   3 | Teacher            |
|   4 | Student            |
|   7 | dummy role         |
|   6 | Admin              |
+-----+--------------------+
*/
    
    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA5 );

    private static final String SELECT_STR = "select u.uid as uid, u.name as name, u.pass as pass, u.created as created, r.rid as rol from users u, users_roles r where u.uid = r.uid";

    public static String getFindStr(CriteriosUser criteria ) {
        
        String sql = SELECT_STR ;

        SqlClauseHelper _sh = new SqlClauseHelper();

        if ( criteria.getId() != null )
            _sh.addAndClause("u.uid = " + SigarUtils.escape(criteria.getId()) );
        if ( criteria.getPwd() != null )
            _sh.addAndClause("u.pass = '" + SigarUtils.escape(criteria.getPwd()) + "'" );
        if ( criteria.getNombre() != null )
            _sh.addAndClause("u.name = '" + SigarUtils.escape(criteria.getNombre()) + "'" );
        if ( criteria.getCreated() != null )
        {
           if ( criteria.getCreated().getInicio() != null )
               _sh.addAndClause("u.created >= timestamp('" + _df.format(criteria.getCreated().getInicio()) + "')" );
           if ( criteria.getCreated().getFin() != null )
               _sh.addAndClause("u.created <= timestamp('" + _df.format(criteria.getCreated().getFin()) + "')" );
        }

        if ( criteria.isNotOverlord() )
        {
            _sh.addAndClause( "rid in ( 3, 4 )");
        }
        else
        {
            Integer rol = 0;
            if ( criteria.getRol() != null )
            {
                if ( criteria.getRol().equals("overlord") )
                    _sh.addAndClause("rid = 6");
                if ( criteria.getRol().equals("admin"))
                    _sh.addAndClause("rid = 3");
                if ( criteria.getRol().equals("participa"))
                    _sh.addAndClause("rid = 4");
            }
        }


        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " and " + clause;

        // Aqui' puede especificar el ordenamiento de los registros
        //sql = sql + " order by ...";

        return sql;

    }


    @Override
    public Vector<User> find(CriteriosUser criteria) throws NoConnectionException, BDException {

        Vector<User> result = null;


        result = this.executeQuery( getFindStr(criteria) );

        return result;
    }

    @Override
    public User get(String id) throws NoConnectionException, BDException, RegistroNoExisteException {

        String sql = SELECT_STR + " AND u.uid = " + id ;

        Vector results  = executeQuery( sql );

        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );


        return (User) results.firstElement();
    }

    @Override
    public Integer create(User registro) throws NoConnectionException, BDException {
        throw new UnsupportedOperationException("Create user: Not supported.");
    }

    @Override
    public int update(User registro) throws NoConnectionException, BDException {
        throw new UnsupportedOperationException("Update user: Not supported.");
    }

    @Override
    public int delete(String id) throws NoConnectionException, BDException {
        throw new UnsupportedOperationException("Delete user: Not supported.");
    }



    @Override
    protected Vector extract(ResultSet rs) throws BDException {
        Vector<User> result = new Vector<User>();


        try
        {
            while ( rs.next() )
            {
                try
                {
                    User o = new User();

                    o.setId( rs.getString("uid"));
                    o.setPwd( rs.getString("pass"));
                    o.setNombre( rs.getString("name"));
                    o.setCreated( rs.getTimestamp("created"));
                    Integer rol = rs.getInt("rol");
                    switch ( rol )
                    {
                        case 6:
                            o.setRol("overlord");
                            break;
                        case 3:
                            o.setRol("admin");
                            break;
                        case 4:
                            o.setRol("participa");
                            break;
                        default:
                            o.setRol(null);
                    }

                    result.add(o);
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

        return result;
    }

    @Override
    public List<User> get(Collection<String> uids) throws NoConnectionException, BDException {

        if ( uids.isEmpty() )
            return new ArrayList<User>();

        String sql = SELECT_STR ;
        SqlClauseHelper sh = new SqlClauseHelper();
        for ( String uid : uids )
             sh.append(",", uid);

        sql = sql + " AND u.uid IN ( " + sh.toString() + " ) ";
        return executeQuery( sql );
        
    }



}
