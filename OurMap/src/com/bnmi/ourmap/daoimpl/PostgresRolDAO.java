/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresRolDAO.java
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
import com.bnmi.ourmap.model.Rol;
import com.bnmi.ourmap.model.CriteriosRol;
import com.bnmi.ourmap.dao.RolDAO;
import java.util.Vector;

/*
 * PostgresRolDAO.java
 *
 * Created on Fri Mar 05 18:34:15 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresRolDAO extends BasicDAO implements RolDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA4 );

    @SuppressWarnings("unchecked")
    @Override
    public Vector<Rol> find(CriteriosRol criteria) throws NoConnectionException, BDException {
        Vector<Rol> results = new Vector<Rol>();
        
        String sql = "select * from rol";
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getRol() != null )
            _sh.addAndClause("rol = '" + SigarUtils.escape(criteria.getRol()) + "'" );
        if ( criteria.getRolname() != null )
            _sh.addAndClause("rolname = '" + SigarUtils.escape(criteria.getRolname()) + "'" );
        if ( criteria.getWeight() != null )
            _sh.addAndClause("weight = " + criteria.getWeight().intValue() );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        sql = sql + " order by weight desc";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public Rol get( java.lang.String rol ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from rol where rol = '" + rol + "'" ;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (Rol) results.firstElement();
    }

    @Override
    public Integer create(Rol registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(Rol registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into rol (");
        sql.append("rol, rolname, weight");
        sql.append(")");
        sql.append(" values (");
        
        if ( registro.getRol() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getRol()) + "'" + "," );
        if ( registro.getRolname() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getRolname()) + "'" + "," );
        if ( registro.getWeight() == null )
             sql.append( "null" );
        else
            sql.append( registro.getWeight().intValue() );
        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(Rol registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(Rol registro) {
        
        String sql = "update rol";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        if ( registro.getRolname() != null )
            sh.append(",", "rolname = " + "'" + SigarUtils.escape(registro.getRolname()) + "'" );
        if ( registro.getWeight() != null )
            sh.append(",", "weight = " + registro.getWeight().intValue()  );
        
        sql = sql + " set " + sh.getClause() + " where rol = '" + registro.getRol() + "'" ;
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.String rol ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(rol) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.String rol) {
        
        String sql = "delete from rol where rol = '" + rol + "'" ;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<Rol> results = new Vector<Rol>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    Rol o = new Rol();
                    
                    o.setRol( rs.getString("rol") );
                    o.setRolname( rs.getString("rolname") );
                    o.setWeight( new Integer(rs.getInt("weight")) );
                    
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