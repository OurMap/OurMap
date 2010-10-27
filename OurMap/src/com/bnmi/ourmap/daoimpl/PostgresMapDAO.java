/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresMapDAO.java
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
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.CriteriosMap;
import com.bnmi.ourmap.dao.MapDAO;
import java.util.Vector;

/*
 * PostgresMapDAO.java
 *
 * Created on Mon Jun 07 13:22:38 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresMapDAO extends BasicDAO implements MapDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA4 );

    @SuppressWarnings("unchecked")
    @Override
    public Vector<Map> find(CriteriosMap criteria) throws NoConnectionException, BDException {
        Vector<Map> results = new Vector<Map>();
        
        String sql = "select * from maps";
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getMapid() != null )
            _sh.addAndClause("mapid = " + criteria.getMapid().intValue() );
        if ( criteria.getMapname() != null )
            _sh.addAndClause("mapname = '" + SigarUtils.escape(criteria.getMapname()) + "'" );
        if ( criteria.getLeftBottom() != null )
            _sh.addAndClause("left_bottom = '" + criteria.getLeftBottom() + "'" );
        if ( criteria.getRightTop() != null )
            _sh.addAndClause("right_top = '" + criteria.getRightTop() + "'" );
        if ( criteria.getBackmap() != null )
            _sh.addAndClause("backmap = '" + SigarUtils.escape(criteria.getBackmap()) + "'" );
        if ( criteria.getZoom() != null )
            _sh.addAndClause("zoom = " + criteria.getZoom().intValue() );
        if ( criteria.getCenter() != null )
            _sh.addAndClause("center = '" + criteria.getCenter() + "'" );
        if ( criteria.getProjectId() != null )
            _sh.addAndClause("project_id = " + criteria.getProjectId().intValue() );
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
        if ( criteria.getSubtitle() != null )
            _sh.addAndClause("subtitle = '" + SigarUtils.escape(criteria.getSubtitle()) + "'" );
        if ( criteria.getHotspotsMode() != null )
            _sh.addAndClause("hotspots_mode = " + criteria.getHotspotsMode().intValue() );
        if ( criteria.getDescription() != null )
            _sh.addAndClause("description = '" + SigarUtils.escape(criteria.getDescription()) + "'" );
        if ( criteria.getDisKs() != null )
            _sh.addAndClause("dis_ks = '" + SigarUtils.escape(criteria.getDisKs()) + "'" );
        if ( criteria.getIconsMode() != null )
            _sh.addAndClause("icons_mode = " + criteria.getIconsMode().intValue() );
        if ( criteria.getPrivacy() != null )
            _sh.addAndClause("privacy = " + criteria.getPrivacy().intValue() );
        if ( criteria.getHotspotsEditable() != null )
            _sh.addAndClause("hotspots_editable = " + criteria.getHotspotsEditable().intValue() );
        if ( criteria.getMediaEditable() != null )
            _sh.addAndClause("media_editable = " + criteria.getMediaEditable().intValue() );
        if ( criteria.getCatsEnabled() != null )
            _sh.addAndClause("cats_enabled = '" + SigarUtils.escape(criteria.getCatsEnabled()) + "'" );
        if ( criteria.getDisplayMode() != null )
            _sh.addAndClause("display_mode = " + criteria.getDisplayMode().intValue() );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        sql = sql + " order by created";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public Map get( java.lang.Integer mapid ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from maps where mapid = " + mapid;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (Map) results.firstElement();
    }

    @Override
    public Integer create(Map registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(Map registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into maps (");
        sql.append("mapid, mapname, left_bottom, right_top, backmap, zoom, center, project_id, created, modified, created_by, modified_by, subtitle, hotspots_mode, description, dis_ks, icons_mode, privacy, hotspots_editable, media_editable, cats_enabled, display_mode, creator_name");
        sql.append(")");
        sql.append(" values (");
        
        if ( registro.getMapid() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getMapid().intValue() + "," );
        if ( registro.getMapname() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getMapname()) + "'" + "," );
        if ( registro.getLeftBottom() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + registro.getLeftBottom() + "'" + "," );
        if ( registro.getRightTop() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + registro.getRightTop() + "'" + "," );
        if ( registro.getBackmap() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getBackmap()) + "'" + "," );
        if ( registro.getZoom() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getZoom().intValue() + "," );
        if ( registro.getCenter() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + registro.getCenter() + "'" + "," );
        if ( registro.getProjectId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getProjectId().intValue() + "," );
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
        if ( registro.getSubtitle() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getSubtitle()) + "'" + "," );
        if ( registro.getHotspotsMode() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getHotspotsMode().intValue() + "," );
        if ( registro.getDescription() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getDescription()) + "'" + "," );
        if ( registro.getDisKs() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getDisKs()) + "'" + "," );
        if ( registro.getIconsMode() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getIconsMode().intValue() + "," );
        if ( registro.getPrivacy() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getPrivacy().intValue() + "," );
        if ( registro.getHotspotsEditable() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getHotspotsEditable().intValue() + "," );
        if ( registro.getMediaEditable() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getMediaEditable().intValue() + "," );
        if ( registro.getCatsEnabled() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getCatsEnabled()) + "'" + "," );
        if ( registro.getDisplayMode() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getDisplayMode().intValue()+ "," );

        if ( registro.getCreatorName() == null )
             sql.append( "null"  );
        else
            sql.append( "'" + registro.getCreatorName() + "'" );


        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(Map registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(Map registro) {
        
        String sql = "update maps";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        if ( registro.getMapname() != null )
            sh.append(",", "mapname = " + "'" + SigarUtils.escape(registro.getMapname()) + "'" );
        if ( registro.getLeftBottom() != null )
            sh.append(",", "left_bottom = " + "'" + registro.getLeftBottom() + "'" );
        if ( registro.getRightTop() != null )
            sh.append(",", "right_top = " + "'" + registro.getRightTop() + "'" );
        if ( registro.getBackmap() != null )
            sh.append(",", "backmap = " + "'" + SigarUtils.escape(registro.getBackmap()) + "'" );
        if ( registro.getZoom() != null )
            sh.append(",", "zoom = " + registro.getZoom().intValue()  );
        if ( registro.getCenter() != null )
            sh.append(",", "center = " + "'" + registro.getCenter() + "'" );
        if ( registro.getProjectId() != null )
            sh.append(",", "project_id = " + registro.getProjectId().intValue()  );
        if ( registro.getCreated() != null )
            sh.append(",", "created = to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getModified() != null )
            sh.append(",", "modified = to_timestamp('" + _df.format( registro.getModified() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getCreatedBy() != null )
            sh.append(",", "created_by = " + "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" );
        if ( registro.getModifiedBy() != null )
            sh.append(",", "modified_by = " + "'" + SigarUtils.escape(registro.getModifiedBy()) + "'" );
        if ( registro.getSubtitle() != null )
            sh.append(",", "subtitle = " + "'" + SigarUtils.escape(registro.getSubtitle()) + "'" );
        if ( registro.getHotspotsMode() != null )
            sh.append(",", "hotspots_mode = " + registro.getHotspotsMode().intValue()  );
        if ( registro.getDescription() != null )
            sh.append(",", "description = " + "'" + SigarUtils.escape(registro.getDescription()) + "'" );
        if ( registro.getDisKs() != null )
            sh.append(",", "dis_ks = " + "'" + SigarUtils.escape(registro.getDisKs()) + "'" );
        if ( registro.getIconsMode() != null )
            sh.append(",", "icons_mode = " + registro.getIconsMode().intValue()  );
        if ( registro.getPrivacy() != null )
            sh.append(",", "privacy = " + registro.getPrivacy().intValue()  );
        if ( registro.getHotspotsEditable() != null )
            sh.append(",", "hotspots_editable = " + registro.getHotspotsEditable().intValue()  );
        if ( registro.getMediaEditable() != null )
            sh.append(",", "media_editable = " + registro.getMediaEditable().intValue()  );
        if ( registro.getCatsEnabled() != null )
            sh.append(",", "cats_enabled = " + "'" + SigarUtils.escape(registro.getCatsEnabled()) + "'" );
        if ( registro.getDisplayMode() != null )
            sh.append(",", "display_mode = " + registro.getDisplayMode().intValue()  );
        if ( registro.getCreatorName() != null )
            sh.append(",", "creator_name = " + "'" + registro.getCreatorName() + "'" );
        
        sql = sql + " set " + sh.getClause() + " where mapid = " + registro.getMapid();
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.Integer mapid ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(mapid) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.Integer mapid) {
        
        String sql = "delete from maps where mapid = " + mapid;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<Map> results = new Vector<Map>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    Map o = new Map();
                    
                    o.setMapid( new Integer(rs.getInt("mapid")) );
                    o.setMapname( rs.getString("mapname") );
                    org.postgresql.geometric.PGpoint pointleftBottom;
                    pointleftBottom = (org.postgresql.geometric.PGpoint) rs.getObject( "left_bottom" );
                    o.setLeftBottom( new com.inga.utils.Point(pointleftBottom.x,pointleftBottom.y) );
                    org.postgresql.geometric.PGpoint pointrightTop;
                    pointrightTop = (org.postgresql.geometric.PGpoint) rs.getObject( "right_top" );
                    o.setRightTop( new com.inga.utils.Point(pointrightTop.x,pointrightTop.y) );
                    o.setBackmap( rs.getString("backmap") );
                    o.setZoom( new Integer(rs.getInt("zoom")) );
                    org.postgresql.geometric.PGpoint pointcenter;
                    pointcenter = (org.postgresql.geometric.PGpoint) rs.getObject( "center" );
                    o.setCenter( new com.inga.utils.Point(pointcenter.x,pointcenter.y) );
                    o.setProjectId( new Integer(rs.getInt("project_id")) );
                    o.setCreated( rs.getTimestamp("created") );
                    o.setModified( rs.getTimestamp("modified") );
                    o.setCreatedBy( rs.getString("created_by") );
                    o.setModifiedBy( rs.getString("modified_by") );
                    o.setSubtitle( rs.getString("subtitle") );
                    o.setHotspotsMode( new Integer(rs.getInt("hotspots_mode")) );
                    o.setDescription( rs.getString("description") );
                    o.setDisKs( rs.getString("dis_ks") );
                    o.setIconsMode( new Integer(rs.getInt("icons_mode")) );
                    o.setPrivacy( new Integer(rs.getInt("privacy")) );
                    o.setHotspotsEditable( new Integer(rs.getInt("hotspots_editable")) );
                    o.setMediaEditable( new Integer(rs.getInt("media_editable")) );
                    o.setCatsEnabled( rs.getString("cats_enabled") );
                    o.setDisplayMode( new Integer(rs.getInt("display_mode")) );
                    o.setCreatorName ( rs.getString("creator_name") );
                    
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