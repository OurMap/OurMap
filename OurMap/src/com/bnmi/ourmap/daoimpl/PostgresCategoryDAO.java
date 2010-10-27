/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresCategoryDAO.java
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
import com.bnmi.ourmap.model.Category;
import com.bnmi.ourmap.model.CriteriosCategory;
import com.bnmi.ourmap.dao.CategoryDAO;
import java.util.Vector;

/*
 * PostgresCategoryDAO.java
 *
 * Created on Sun Aug 22 17:34:10 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresCategoryDAO extends BasicDAO implements CategoryDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA4 );

    @SuppressWarnings("unchecked")
    @Override
    public Vector<Category> find(CriteriosCategory criteria) throws NoConnectionException, BDException {
        Vector<Category> results = new Vector<Category>();
        
        String sql = "select * from categories";
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getCatId() != null )
            _sh.addAndClause("cat_id = " + criteria.getCatId().intValue() );
        if ( criteria.getMapId() != null )
            _sh.addAndClause("map_id = " + criteria.getMapId().intValue() );
        if ( criteria.getCatName() != null )
            _sh.addAndClause("cat_name = '" + SigarUtils.escape(criteria.getCatName()) + "'" );
        if ( criteria.getIconId() != null )
            _sh.addAndClause("icon_id = " + criteria.getIconId().intValue() );
        if ( criteria.getCreatedBy() != null )
            _sh.addAndClause("created_by = '" + SigarUtils.escape(criteria.getCreatedBy()) + "'" );
        if ( criteria.getCreated() != null )
        {
           if ( criteria.getCreated().getInicio() != null )
               _sh.addAndClause("created >= to_timestamp('" + _df.format(criteria.getCreated().getInicio()) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
           if ( criteria.getCreated().getFin() != null )
               _sh.addAndClause("created <= to_timestamp('" + _df.format(criteria.getCreated().getFin()) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        }
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        sql = sql + " order by created";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public Category get( java.lang.Integer catId ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from categories where cat_id = " + catId;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (Category) results.firstElement();
    }

    @Override
    public Integer create(Category registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(Category registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into categories (");
        sql.append("cat_id, map_id, cat_name, icon_id, created_by, created");
        sql.append(")");
        sql.append(" values (");
        
        if ( registro.getCatId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getCatId().intValue() + "," );
        if ( registro.getMapId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getMapId().intValue() + "," );
        if ( registro.getCatName() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getCatName()) + "'" + "," );
        if ( registro.getIconId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getIconId().intValue() + "," );
        if ( registro.getCreatedBy() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" + "," );
        if ( registro.getCreated() == null )
             sql.append( "null" );
        else
            sql.append( "to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(Category registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(Category registro) {
        
        String sql = "update categories";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        if ( registro.getMapId() != null )
            sh.append(",", "map_id = " + registro.getMapId().intValue()  );
        if ( registro.getCatName() != null )
            sh.append(",", "cat_name = " + "'" + SigarUtils.escape(registro.getCatName()) + "'" );
        if ( registro.getIconId() != null )
            sh.append(",", "icon_id = " + registro.getIconId().intValue()  );
        if ( registro.getCreatedBy() != null )
            sh.append(",", "created_by = " + "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" );
        if ( registro.getCreated() != null )
            sh.append(",", "created = to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        
        sql = sql + " set " + sh.getClause() + " where cat_id = " + registro.getCatId();
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.Integer catId ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(catId) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.Integer catId) {
        
        String sql = "delete from categories where cat_id = " + catId;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<Category> results = new Vector<Category>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    Category o = new Category();
                    
                    o.setCatId( new Integer(rs.getInt("cat_id")) );
                    o.setMapId( new Integer(rs.getInt("map_id")) );
                    o.setCatName( rs.getString("cat_name") );
                    o.setIconId( new Integer(rs.getInt("icon_id")) );
                    o.setCreatedBy( rs.getString("created_by") );
                    o.setCreated( rs.getTimestamp("created") );
                    
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