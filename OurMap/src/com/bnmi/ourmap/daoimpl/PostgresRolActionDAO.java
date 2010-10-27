/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresRolActionDAO.java
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
import com.bnmi.ourmap.model.RolAction;
import com.bnmi.ourmap.model.CriteriosRolAction;
import com.bnmi.ourmap.dao.RolActionDAO;
import java.util.Vector;

/*
 * PostgresRolActionDAO.java
 *
 * Created on Mon Dec 07 21:22:19 COT 2009
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresRolActionDAO extends BasicDAO implements RolActionDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @SuppressWarnings("unchecked")
    @Override
    public Vector<RolAction> find(CriteriosRolAction criteria) throws NoConnectionException, BDException {
        Vector<RolAction> results = new Vector<RolAction>();
        
        String sql = "select * from rol_action";
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getRol() != null )
            _sh.addAndClause("rol = '" + criteria.getRol() + "'" );
        if ( criteria.getAid() != null )
            _sh.addAndClause("aid = '" + criteria.getAid() + "'" );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        //sql = sql + " order by ...";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public RolAction get( java.lang.String rol, java.lang.String aid ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from rol_action where rol = '" + rol + "'"  + " and aid = '" + aid + "'" ;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (RolAction) results.firstElement();
    }

    @Override
    public Integer create(RolAction registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(RolAction registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into rol_action (");
        sql.append("rol, aid");
        sql.append(")");
        sql.append(" values (");
        
           if ( registro.getRol() == null )
               sql.append( "null" + "," );
           else
               sql.append( "'" + registro.getRol() + "'" + "," );
           if ( registro.getAid() == null )
               sql.append( "null" );
           else
               sql.append( "'" + registro.getAid() + "'" );
        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(RolAction registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(RolAction registro) {
        
        String sql = "update rol_action";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        
        sql = sql + " set " + sh.getClause() + " where rol = '" + registro.getRol() + "'"  + " and aid = '" + registro.getAid() + "'" ;
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.String rol, java.lang.String aid ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(rol, aid) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.String rol, java.lang.String aid) {
        
        String sql = "delete from rol_action where rol = '" + rol + "'"  + " and aid = '" + aid + "'" ;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<RolAction> results = new Vector<RolAction>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    RolAction o = new RolAction();
                    
                    o.setRol( rs.getString("rol") );
                    o.setAid( rs.getString("aid") );
                    
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