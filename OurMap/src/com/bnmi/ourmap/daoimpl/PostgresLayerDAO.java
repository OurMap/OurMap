/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresLayerDAO.java
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
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.CriteriosLayer;
import com.bnmi.ourmap.dao.LayerDAO;
import java.util.Vector;

/*
 * PostgresLayerDAO.java
 *
 * Created on Wed Jan 27 17:53:42 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresLayerDAO extends BasicDAO implements LayerDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA4 );

    @SuppressWarnings("unchecked")
    @Override
    public Vector<Layer> find(CriteriosLayer criteria) throws NoConnectionException, BDException {
        Vector<Layer> results = new Vector<Layer>();
        
        String sql = "select * from layers";
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getLayerId() != null )
            _sh.addAndClause("layer_id = " + criteria.getLayerId().intValue() );
        if ( criteria.getLayerName() != null )
            _sh.addAndClause("layer_name = '" + SigarUtils.escape(criteria.getLayerName()) + "'" );
        if ( criteria.getLayerDescription() != null )
            _sh.addAndClause("layer_description = '" + SigarUtils.escape(criteria.getLayerDescription()) + "'" );
        if ( criteria.getMapId() != null )
            _sh.addAndClause("map_id = " + criteria.getMapId().intValue() );
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
        if ( criteria.getIndex() != null )
            _sh.addAndClause("index = " + criteria.getIndex().intValue() );
        if ( criteria.getIconId() != null )
            _sh.addAndClause("icon_id = " + criteria.getIconId().intValue() );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        sql = sql + " order by index";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public Layer get( java.lang.Integer layerId ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from layers where layer_id = " + layerId;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (Layer) results.firstElement();
    }

    @Override
    public Integer create(Layer registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(Layer registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into layers (");
        sql.append("layer_id, layer_name, layer_description, map_id, created, modified, created_by, modified_by, index, icon_id");
        sql.append(")");
        sql.append(" values (");
        
        if ( registro.getLayerId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getLayerId().intValue() + "," );
        if ( registro.getLayerName() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getLayerName()) + "'" + "," );
        if ( registro.getLayerDescription() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getLayerDescription()) + "'" + "," );
        if ( registro.getMapId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getMapId().intValue() + "," );
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
        if ( registro.getIndex() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getIndex().intValue() + "," );
        if ( registro.getIconId() == null )
             sql.append( "null" );
        else
            sql.append( registro.getIconId().intValue() );
        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(Layer registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(Layer registro) {
        
        String sql = "update layers";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        if ( registro.getLayerName() != null )
            sh.append(",", "layer_name = " + "'" + SigarUtils.escape(registro.getLayerName()) + "'" );
        if ( registro.getLayerDescription() != null )
            sh.append(",", "layer_description = " + "'" + SigarUtils.escape(registro.getLayerDescription()) + "'" );
        if ( registro.getMapId() != null )
            sh.append(",", "map_id = " + registro.getMapId().intValue()  );
        if ( registro.getCreated() != null )
            sh.append(",", "created = to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getModified() != null )
            sh.append(",", "modified = to_timestamp('" + _df.format( registro.getModified() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getCreatedBy() != null )
            sh.append(",", "created_by = " + "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" );
        if ( registro.getModifiedBy() != null )
            sh.append(",", "modified_by = " + "'" + SigarUtils.escape(registro.getModifiedBy()) + "'" );
        if ( registro.getIndex() != null )
            sh.append(",", "index = " + registro.getIndex().intValue()  );
        if ( registro.getIconId() != null )
            sh.append(",", "icon_id = " + registro.getIconId().intValue()  );
        
        sql = sql + " set " + sh.getClause() + " where layer_id = " + registro.getLayerId();
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.Integer layerId ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(layerId) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.Integer layerId) {
        
        String sql = "delete from layers where layer_id = " + layerId;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<Layer> results = new Vector<Layer>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    Layer o = new Layer();
                    
                    o.setLayerId( new Integer(rs.getInt("layer_id")) );
                    o.setLayerName( rs.getString("layer_name") );
                    o.setLayerDescription( rs.getString("layer_description") );
                    o.setMapId( new Integer(rs.getInt("map_id")) );
                    o.setCreated( rs.getTimestamp("created") );
                    o.setModified( rs.getTimestamp("modified") );
                    o.setCreatedBy( rs.getString("created_by") );
                    o.setModifiedBy( rs.getString("modified_by") );
                    o.setIndex( new Integer(rs.getInt("index")) );
                    o.setIconId( new Integer(rs.getInt("icon_id")) );
                    if ( rs.wasNull() ) o.setIconId( null );
                    
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