/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresUserDAO.java
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
import com.inga.utils.SigarUtils;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.exception.RegistroNoExisteException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.inga.utils.SqlClauseHelper;
import com.bnmi.ourmap.model.User;
import com.bnmi.ourmap.model.CriteriosUser;
import com.bnmi.ourmap.dao.UserDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/*
 * PostgresUserDAO.java
 *
 * Created on Thu Mar 18 03:51:48 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresUserDAO extends BasicDAO implements UserDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA4 );

    @SuppressWarnings("unchecked")
    @Override
    public Vector<User> find(CriteriosUser criteria) throws NoConnectionException, BDException {
        Vector<User> results = new Vector<User>();
        
        String sql = "select * from users";
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getId() != null )
            _sh.addAndClause("lower(id) = lower('" + SigarUtils.escape(criteria.getId()) + "')" );
        if ( criteria.getPwd() != null )
            _sh.addAndClause("lower(pwd) = lower('" + SigarUtils.escape(criteria.getPwd()) + "')" );
        if ( criteria.getNombre() != null )
            _sh.addAndClause("nombre = '" + SigarUtils.escape(criteria.getNombre()) + "'" );
        if ( criteria.getLastname() != null )
            _sh.addAndClause("lastname = '" + SigarUtils.escape(criteria.getLastname()) + "'" );
        if ( criteria.getDescr() != null )
            _sh.addAndClause("descr = '" + SigarUtils.escape(criteria.getDescr()) + "'" );
        if ( criteria.getCreated() != null )
        {
           if ( criteria.getCreated().getInicio() != null )
               _sh.addAndClause("created >= to_timestamp('" + _df.format(criteria.getCreated().getInicio()) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
           if ( criteria.getCreated().getFin() != null )
               _sh.addAndClause("created <= to_timestamp('" + _df.format(criteria.getCreated().getFin()) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        }
        if ( criteria.getModified() != null )
        {
           if ( criteria.getModified().getInicio() != null )
               _sh.addAndClause("modified >= to_timestamp('" + _df.format(criteria.getModified().getInicio()) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
           if ( criteria.getModified().getFin() != null )
               _sh.addAndClause("modified <= to_timestamp('" + _df.format(criteria.getModified().getFin()) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        }

        if ( criteria.isNotOverlord() )
        {
            _sh.addAndClause(" rol IN ( 'admin', 'teacher' )");
        }
        else
        {
            if ( criteria.getRol() != null )
                _sh.addAndClause("rol = '" + SigarUtils.escape(criteria.getRol()) + "'" );
        }

        if ( criteria.getCreatedBy() != null )
            _sh.addAndClause("created_by = '" + SigarUtils.escape(criteria.getCreatedBy()) + "'" );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        //sql = sql + " order by ...";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public User get( java.lang.String id ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from users where lower(id) = lower('" + id + "')" ;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (User) results.firstElement();
    }

    @Override
    public Integer create(User registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(User registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into users (");
        sql.append("id, pwd, nombre, lastname, descr, created, modified, rol, created_by");
        sql.append(")");
        sql.append(" values (");
        
        if ( registro.getId() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getId()) + "'" + "," );
        if ( registro.getPwd() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getPwd()) + "'" + "," );
        if ( registro.getNombre() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getNombre()) + "'" + "," );
        if ( registro.getLastname() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getLastname()) + "'" + "," );
        if ( registro.getDescr() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getDescr()) + "'" + "," );
        if ( registro.getCreated() == null )
             sql.append( "null" + "," );
        else
            sql.append( "to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" + "," );
        if ( registro.getModified() == null )
             sql.append( "null" + "," );
        else
            sql.append( "to_timestamp('" + _df.format( registro.getModified() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" + "," );
        if ( registro.getRol() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getRol()) + "'" + "," );
        if ( registro.getCreatedBy() == null )
             sql.append( "null" );
        else
            sql.append( "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" );
        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(User registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(User registro) {
        
        String sql = "update users";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        if ( registro.getPwd() != null )
            sh.append(",", "pwd = " + "'" + SigarUtils.escape(registro.getPwd()) + "'" );
        if ( registro.getNombre() != null )
            sh.append(",", "nombre = " + "'" + SigarUtils.escape(registro.getNombre()) + "'" );
        if ( registro.getLastname() != null )
            sh.append(",", "lastname = " + "'" + SigarUtils.escape(registro.getLastname()) + "'" );
        if ( registro.getDescr() != null )
            sh.append(",", "descr = " + "'" + SigarUtils.escape(registro.getDescr()) + "'" );
        if ( registro.getCreated() != null )
            sh.append(",", "created = to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getModified() != null )
            sh.append(",", "modified = to_timestamp('" + _df.format( registro.getModified() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getRol() != null )
            sh.append(",", "rol = " + "'" + SigarUtils.escape(registro.getRol()) + "'" );
        if ( registro.getCreatedBy() != null )
            sh.append(",", "created_by = " + "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" );
        
        sql = sql + " set " + sh.getClause() + " where id = '" + registro.getId() + "'" ;
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.String id ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(id) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.String id) {
        
        String sql = "delete from users where id = '" + id + "'" ;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<User> results = new Vector<User>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    User o = new User();
                    
                    o.setId( rs.getString("id") );
                    o.setPwd( rs.getString("pwd") );
                    o.setNombre( rs.getString("nombre") );
                    o.setLastname( rs.getString("lastname") );
                    o.setDescr( rs.getString("descr") );
                    o.setCreated( rs.getTimestamp("created") );
                    o.setModified( rs.getTimestamp("modified") );
                    o.setRol( rs.getString("rol") );
                    o.setCreatedBy( rs.getString("created_by") );
                    
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
    public List<User> get(Collection<String> uids) throws NoConnectionException, BDException {

        if ( uids.isEmpty() )
            return new ArrayList<User>();

        String sql = "select * from users" ;
        SqlClauseHelper sh = new SqlClauseHelper();
        for ( String uid : uids )
             sh.append(",", "'" + uid + "'" );
        sql = sql + " where id IN ( " + sh.toString() + " ) ";
        return executeQuery( sql );
    }

 
}