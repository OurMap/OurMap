/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresKeywordDAO.java
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
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.model.CriteriosKeyword;
import com.bnmi.ourmap.dao.KeywordDAO;
import java.util.Vector;

/*
 * PostgresKeywordDAO.java
 *
 * Created on Wed Apr 07 10:20:56 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresKeywordDAO extends BasicDAO implements KeywordDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA4 );

    @SuppressWarnings("unchecked")
    @Override
    public Vector<Keyword> find(CriteriosKeyword criteria) throws NoConnectionException, BDException {
        Vector<Keyword> results = new Vector<Keyword>();
        
        String sql = "select * from keywords";
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getKwId() != null )
            _sh.addAndClause("kw_id = " + criteria.getKwId().intValue() );
        if ( criteria.getMapId() != null )
            _sh.addAndClause("map_id = " + criteria.getMapId().intValue() );
        if ( criteria.getKwValue() != null )
            _sh.addAndClause("kw_value = '" + SigarUtils.escape(criteria.getKwValue()) + "'" );
        if ( criteria.getCatId() != null )
            _sh.addAndClause("cat_id = " + criteria.getCatId().intValue() );
        if ( criteria.getCreatedBy() != null )
            _sh.addAndClause("created_by = '" + SigarUtils.escape(criteria.getCreatedBy()) + "'" );
        if ( criteria.getCreated() != null )
        {
           if ( criteria.getCreated().getInicio() != null )
               _sh.addAndClause("created >= to_timestamp('" + _df.format(criteria.getCreated().getInicio()) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
           if ( criteria.getCreated().getFin() != null )
               _sh.addAndClause("created <= to_timestamp('" + _df.format(criteria.getCreated().getFin()) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        }
        if ( criteria.getIconId() != null )
            _sh.addAndClause("icon_id = " + criteria.getIconId().intValue() );
        if ( criteria.getIndex() != null )
            _sh.addAndClause("index = " + criteria.getIndex().intValue() );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        sql = sql + " order by index";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public Keyword get( java.lang.Integer kwId ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from keywords where kw_id = " + kwId;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (Keyword) results.firstElement();
    }

    @Override
    public Integer create(Keyword registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(Keyword registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into keywords (");
        sql.append("kw_id, map_id, kw_value, cat_id, created_by, created, icon_id, index");
        sql.append(")");
        sql.append(" values (");
        
        if ( registro.getKwId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getKwId().intValue() + "," );
        if ( registro.getMapId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getMapId().intValue() + "," );
        if ( registro.getKwValue() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getKwValue()) + "'" + "," );
        if ( registro.getCatId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getCatId().intValue() + "," );
        if ( registro.getCreatedBy() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" + "," );
        if ( registro.getCreated() == null )
             sql.append( "null" + "," );
        else
            sql.append( "to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" + "," );
        if ( registro.getIconId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getIconId().intValue() + "," );
        if ( registro.getIndex() == null )
             sql.append( "null" );
        else
            sql.append( registro.getIndex().intValue() );
        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(Keyword registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(Keyword registro) {
        
        String sql = "update keywords";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        if ( registro.getMapId() != null )
            sh.append(",", "map_id = " + registro.getMapId().intValue()  );
        if ( registro.getKwValue() != null )
            sh.append(",", "kw_value = " + "'" + SigarUtils.escape(registro.getKwValue()) + "'" );
        if ( registro.getCatId() != null )
            sh.append(",", "cat_id = " + registro.getCatId().intValue()  );
        else
            sh.append(",", "cat_id = null" );
        if ( registro.getCreatedBy() != null )
            sh.append(",", "created_by = " + "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" );
        if ( registro.getCreated() != null )
            sh.append(",", "created = to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getIconId() != null )
            sh.append(",", "icon_id = " + registro.getIconId().intValue()  );
        if ( registro.getIndex() != null )
            sh.append(",", "index = " + registro.getIndex().intValue()  );
        
        sql = sql + " set " + sh.getClause() + " where kw_id = " + registro.getKwId();
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.Integer kwId ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(kwId) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.Integer kwId) {
        
        String sql = "delete from keywords where kw_id = " + kwId;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<Keyword> results = new Vector<Keyword>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    Keyword o = new Keyword();
                    
                    o.setKwId( new Integer(rs.getInt("kw_id")) );
                    o.setMapId( new Integer(rs.getInt("map_id")) );
                    o.setKwValue( rs.getString("kw_value") );
                    o.setCatId( new Integer(rs.getInt("cat_id")) );
                    if ( rs.wasNull() ) o.setCatId( null );
                    o.setCreatedBy( rs.getString("created_by") );
                    o.setCreated( rs.getTimestamp("created") );
                    o.setIconId( new Integer(rs.getInt("icon_id")) );
                    o.setIndex( new Integer(rs.getInt("index")) );
                    //if ( rs.wasNull() ) o.setIndex( null );
                    
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