/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresProjectDAO.java
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
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.model.CriteriosProject;
import com.bnmi.ourmap.dao.ProjectDAO;
import java.util.Vector;

/*
 * PostgresProjectDAO.java
 *
 * Created on Fri Mar 05 19:22:58 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresProjectDAO extends BasicDAO implements ProjectDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA4 );

    @SuppressWarnings("unchecked")
    @Override
    public Vector<Project> find(CriteriosProject criteria) throws NoConnectionException, BDException {
        Vector<Project> results = new Vector<Project>();
        
        String sql = "select * from projects";
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getId() != null )
            _sh.addAndClause("id = " + criteria.getId().intValue() );
        if ( criteria.getNombre() != null )
            _sh.addAndClause("nombre = '" + SigarUtils.escape(criteria.getNombre()) + "'" );
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
        if ( criteria.getCreatedBy() != null )
            _sh.addAndClause("created_by = '" + SigarUtils.escape(criteria.getCreatedBy()) + "'" );
        if ( criteria.getModifiedBy() != null )
            _sh.addAndClause("modified_by = '" + SigarUtils.escape(criteria.getModifiedBy()) + "'" );
        if ( criteria.getPwd() != null )
            _sh.addAndClause("pwd = '" + SigarUtils.escape(criteria.getPwd()) + "'" );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        sql = sql + " order by created";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public Project get( java.lang.Integer id ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from projects where id = " + id;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (Project) results.firstElement();
    }

    @Override
    public Integer create(Project registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(Project registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into projects (");
        sql.append("id, nombre, descr, created, modified, created_by, modified_by, pwd");
        sql.append(")");
        sql.append(" values (");
        
        if ( registro.getId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getId().intValue() + "," );
        if ( registro.getNombre() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getNombre()) + "'" + "," );
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
        if ( registro.getCreatedBy() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" + "," );
        if ( registro.getModifiedBy() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getModifiedBy()) + "'" + "," );
        if ( registro.getPwd() == null )
             sql.append( "null" );
        else
            sql.append( "'" + SigarUtils.escape(registro.getPwd()) + "'" );
        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(Project registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(Project registro) {
        
        String sql = "update projects";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        if ( registro.getNombre() != null )
            sh.append(",", "nombre = " + "'" + SigarUtils.escape(registro.getNombre()) + "'" );
        if ( registro.getDescr() != null )
            sh.append(",", "descr = " + "'" + SigarUtils.escape(registro.getDescr()) + "'" );
        if ( registro.getCreated() != null )
            sh.append(",", "created = to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getModified() != null )
            sh.append(",", "modified = to_timestamp('" + _df.format( registro.getModified() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getCreatedBy() != null )
            sh.append(",", "created_by = " + "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" );
        if ( registro.getModifiedBy() != null )
            sh.append(",", "modified_by = " + "'" + SigarUtils.escape(registro.getModifiedBy()) + "'" );
        if ( registro.getPwd() != null )
            sh.append(",", "pwd = " + "'" + SigarUtils.escape(registro.getPwd()) + "'" );
        
        sql = sql + " set " + sh.getClause() + " where id = " + registro.getId();
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.Integer id ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(id) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.Integer id) {
        
        String sql = "delete from projects where id = " + id;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<Project> results = new Vector<Project>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    Project o = new Project();
                    
                    o.setId( new Integer(rs.getInt("id")) );
                    o.setNombre( rs.getString("nombre") );
                    o.setDescr( rs.getString("descr") );
                    o.setCreated( rs.getTimestamp("created") );
                    o.setModified( rs.getTimestamp("modified") );
                    o.setCreatedBy( rs.getString("created_by") );
                    o.setModifiedBy( rs.getString("modified_by") );
                    o.setPwd( rs.getString("pwd") );
                    
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