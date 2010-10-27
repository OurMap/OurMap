/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresHotspotDAO.java
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
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.CriteriosHotspot;
import com.bnmi.ourmap.dao.HotspotDAO;
import java.util.List;
import java.util.Vector;

/*
 * PostgresHotspotDAO.java
 *
 * Created on Sat Feb 27 23:46:05 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresHotspotDAO extends BasicDAO implements HotspotDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA4 );

    @SuppressWarnings("unchecked")
    @Override
    public Vector<Hotspot> find(CriteriosHotspot criteria) throws NoConnectionException, BDException {
        Vector<Hotspot> results = new Vector<Hotspot>();
        
        String sql = "select * from hotspots";

        List<Integer> layers = criteria.getHsLayerList();
        SqlClauseHelper lh = new SqlClauseHelper();
        if ( layers != null )
            for ( Integer li : layers )
                lh.append(",", String.valueOf(li) );

        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getHsId() != null )
            _sh.addAndClause("hs_id = " + criteria.getHsId().intValue() );
        if ( criteria.getHsPos() != null )
            _sh.addAndClause("hs_pos = '" + criteria.getHsPos() + "'" );
        if ( criteria.getGeneral() != null )
        {
            _sh.addAndClause(" ( lower(hs_name) like lower('%" + SigarUtils.escape(criteria.getGeneral()) + "%')"  + " OR " +
                                "lower(hs_description) like lower('%" + SigarUtils.escape(criteria.getGeneral()) + "%')" +
                             " )"
                             );
        }
        else
        {
            if ( criteria.getHsName() != null )
                _sh.addAndClause("hs_name = '%" + SigarUtils.escape(criteria.getHsName()) + "%'" );
            if ( criteria.getHsDescription() != null )
                _sh.addAndClause("hs_description = '%" + SigarUtils.escape(criteria.getHsDescription()) + "%'" );
        }
        if ( criteria.getHsLayer() != null )
            _sh.addAndClause("hs_layer = " + criteria.getHsLayer().intValue() );

        if ( criteria.getHsLayerList() != null )
            _sh.addAndClause("hs_layer in (" + lh.toString() + ")" );
        if ( criteria.getAltitude() != null )
            _sh.addAndClause("altitude = " + criteria.getAltitude() );
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
        if ( criteria.getIconId() != null )
            _sh.addAndClause("icon_id = " + criteria.getIconId().intValue() );
        if ( criteria.getKeywordId() != null )
            _sh.addAndClause("keyword_id = " + criteria.getKeywordId().intValue() );
        if ( criteria.getDisplayMode() != null )
            _sh.addAndClause("display_mode = " + criteria.getDisplayMode().intValue() );
        if ( criteria.getProximityRadius() != null )
            _sh.addAndClause("proximity_radius = " + criteria.getProximityRadius().intValue() );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        //sql = sql + " order by ...";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public Hotspot get( java.lang.Integer hsId ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from hotspots where hs_id = " + hsId;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (Hotspot) results.firstElement();
    }

    @Override
    public Integer create(Hotspot registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(Hotspot registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into hotspots (");
        sql.append("hs_id, hs_pos, hs_name, hs_description, hs_layer, altitude, created, modified, created_by, modified_by, icon_id, keyword_id, display_mode, proximity_radius, creator_name ");
        sql.append(")");
        sql.append(" values (");
        
        if ( registro.getHsId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getHsId().intValue() + "," );
        if ( registro.getHsPos() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + registro.getHsPos() + "'" + "," );
        if ( registro.getHsName() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getHsName()) + "'" + "," );
        if ( registro.getHsDescription() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getHsDescription()) + "'" + "," );
        if ( registro.getHsLayer() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getHsLayer().intValue() + "," );
        if ( registro.getAltitude() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getAltitude() + "," );
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
        if ( registro.getIconId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getIconId().intValue() + "," );
        if ( registro.getKeywordId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getKeywordId().intValue() + "," );
        if ( registro.getDisplayMode() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getDisplayMode().intValue() + "," );
        if ( registro.getProximityRadius() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getProximityRadius().intValue() + "," );
        if ( registro.getCreatorName() == null )
             sql.append( "null" );
        else
            sql.append( "'" + registro.getCreatorName() + "'" );

        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(Hotspot registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(Hotspot registro) {
        
        String sql = "update hotspots";
        SqlClauseHelper sh = new SqlClauseHelper();

        
        if ( registro.getHsPos() != null )
            sh.append(",", "hs_pos = " + "'" + registro.getHsPos() + "'" );
        if ( registro.getHsName() != null )
            sh.append(",", "hs_name = " + "'" + SigarUtils.escape(registro.getHsName()) + "'" );

        if ( registro.isForceSetDescription() || registro.getHsDescription() != null )
        {
            if ( registro.getHsDescription() != null )
               sh.append(",", "hs_description = " + "'" + SigarUtils.escape(registro.getHsDescription()) + "'" );
            else
               sh.append(",", "hs_description = " + "''" );
        }

        if ( registro.getHsLayer() != null )
            sh.append(",", "hs_layer = " + registro.getHsLayer().intValue()  );
        if ( registro.getAltitude() != null )
            sh.append(",", "altitude = " + registro.getAltitude() );
        if ( registro.getCreated() != null )
            sh.append(",", "created = to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getModified() != null )
            sh.append(",", "modified = to_timestamp('" + _df.format( registro.getModified() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getCreatedBy() != null )
            sh.append(",", "created_by = " + "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" );
        if ( registro.getModifiedBy() != null )
            sh.append(",", "modified_by = " + "'" + SigarUtils.escape(registro.getModifiedBy()) + "'" );
        if ( registro.getIconId() != null )
            sh.append(",", "icon_id = " + registro.getIconId().intValue()  );
        if ( registro.getKeywordId() != null )
            sh.append(",", "keyword_id = " + registro.getKeywordId().intValue()  );
        if ( registro.getDisplayMode() != null )
            sh.append(",", "display_mode = " + registro.getDisplayMode().intValue()  );
        if ( registro.getProximityRadius() != null )
            sh.append(",", "proximity_radius = " + registro.getProximityRadius().intValue()  );
        if ( registro.getCreatorName() != null )
            sh.append(",", "creator_name = " + "'" + registro.getCreatorName() + "'" );
        
        sql = sql + " set " + sh.getClause() + " where hs_id = " + registro.getHsId();
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.Integer hsId ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(hsId) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.Integer hsId) {
        
        String sql = "delete from hotspots where hs_id = " + hsId;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<Hotspot> results = new Vector<Hotspot>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    Hotspot o = new Hotspot();
                    
                    o.setHsId( new Integer(rs.getInt("hs_id")) );

                    org.postgresql.geometric.PGpoint pointhsPos;
                    pointhsPos = (org.postgresql.geometric.PGpoint) rs.getObject( "hs_pos" );
                    if ( ! rs.wasNull() )
                        o.setHsPos( new com.inga.utils.Point(pointhsPos.x,pointhsPos.y) );

                    o.setHsName( rs.getString("hs_name") );
                    o.setHsDescription( rs.getString("hs_description") );
                    o.setHsLayer( new Integer(rs.getInt("hs_layer")) );
                    if ( rs.wasNull() ) o.setHsLayer( null );
                    o.setAltitude( (java.lang.Double) rs.getObject("altitude") );
                    o.setCreated( rs.getTimestamp("created") );
                    o.setModified( rs.getTimestamp("modified") );
                    o.setCreatedBy( rs.getString("created_by") );
                    o.setModifiedBy( rs.getString("modified_by") );
                    o.setIconId( new Integer(rs.getInt("icon_id")) );
                    if ( rs.wasNull() ) o.setIconId( null );
                    o.setKeywordId( new Integer(rs.getInt("keyword_id")) );
                    if ( rs.wasNull() ) o.setKeywordId( null );
                    o.setDisplayMode( new Integer(rs.getInt("display_mode")) );
                    if ( rs.wasNull() ) o.setDisplayMode( null );

                    o.setCreatorName( rs.getString("creator_name"));



                    o.setProximityRadius( new Integer(rs.getInt("proximity_radius")) );
                    if ( rs.wasNull() ) o.setProximityRadius( null );

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