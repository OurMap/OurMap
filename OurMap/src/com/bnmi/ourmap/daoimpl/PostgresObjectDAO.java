/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresObjectDAO.java
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
import com.bnmi.ourmap.model.EasyObject;
import com.bnmi.ourmap.model.CriteriosObject;
import com.bnmi.ourmap.dao.ObjectDAO;
import java.sql.PreparedStatement;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.Vector;
import org.apache.log4j.Logger;

/*
 * PostgresObjectDAO.java
 *
 * Created on Mon Jan 04 18:22:56 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresObjectDAO extends BasicDAO implements ObjectDAO {

    private static Logger log = Logger.getLogger( PostgresObjectDAO.class );

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA4 );
    private String selectStatement = "SELECT object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, status, dimensions, path, creator_name FROM objects";
    private String tempSelectStatement = "SELECT object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, status, dimensions, path, creator_name FROM temp_objects";

    @SuppressWarnings("unchecked")
    @Override
    public Vector<EasyObject> find(CriteriosObject criteria) throws NoConnectionException, BDException {
        Vector<EasyObject> results = new Vector<EasyObject>();
        
        String sql = selectStatement;
        
        SqlClauseHelper _sh = new SqlClauseHelper();
        
        if ( criteria.getObjectId() != null )
            _sh.addAndClause("object_id = " + criteria.getObjectId().intValue() );
        if ( criteria.getObjName() != null )
            _sh.addAndClause("obj_name = '" + SigarUtils.escape(criteria.getObjName()) + "'" );
        if ( criteria.getObjDescription() != null )
            _sh.addAndClause("obj_description = '" + SigarUtils.escape(criteria.getObjDescription()) + "'" );
        if ( criteria.getObjType() != null )
            _sh.addAndClause("obj_type = " + criteria.getObjType().intValue() );
        if ( criteria.getExtension() != null )
            _sh.addAndClause("extension = '" + SigarUtils.escape(criteria.getExtension()) + "'" );
        if ( criteria.getObjSize() != null )
            _sh.addAndClause("obj_size = " + criteria.getObjSize().intValue() );
        if ( criteria.getCreatedBy() != null )
            _sh.addAndClause("created_by = '" + SigarUtils.escape(criteria.getCreatedBy()) + "'" );
        if ( criteria.getModifiedBy() != null )
            _sh.addAndClause("modified_by = '" + SigarUtils.escape(criteria.getModifiedBy()) + "'" );
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
        if ( criteria.getStatus() != null )
            _sh.addAndClause("status = " + criteria.getStatus() );
        if ( criteria.getCategory() != null )
            _sh.addAndClause("category = " + criteria.getCategory() );
        
        
        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;
        
        // Aqui' puede especificar el ordenamiento de los registros
        //sql = sql + " order by ...";
        results = executeQuery( sql );
        
        
        return results;
     }
        
    @Override
    public EasyObject get( java.lang.Integer objectId ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = selectStatement + " where object_id = " + objectId;
        
        Vector results  = executeQuery( sql );
        
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (EasyObject) results.firstElement();
    }

    @Override
    public Integer create(EasyObject registro) throws NoConnectionException, BDException {
        return createObject( registro, false );
    }

    public String getCreateStr(EasyObject registro, boolean isTemp) {

        StringBuffer sql = new StringBuffer();

        if ( isTemp )
            sql.append( "insert into temp_objects (");
        else
            sql.append( "insert into objects (");

        sql.append("object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, status, category, dimensions, path, creator_name ");
        sql.append(")");
        sql.append(" values (");
        
        if ( registro.getObjectId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getObjectId().intValue() + "," );
        if ( registro.getObjName() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getObjName()) + "'" + "," );
        if ( registro.getObjDescription() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getObjDescription()) + "'" + "," );
        if ( registro.getObjType() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getObjType().intValue() + "," );
        if ( registro.getExtension() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getExtension()) + "'" + "," );
        if ( registro.getObjSize() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getObjSize().intValue() + "," );
        if ( registro.getCreatedBy() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getCreatedBy()) + "'" + "," );
        if ( registro.getModifiedBy() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getModifiedBy()) + "'" + "," );
        if ( registro.getCreated() == null )
             sql.append( "null" + "," );
        else
            sql.append( "to_timestamp('" + _df.format( registro.getCreated() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" + "," );
        if ( registro.getModified() == null )
             sql.append( "null" + ","  );
        else
            sql.append( "to_timestamp('" + _df.format( registro.getModified() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" + "," );
        if ( registro.getStatus() == null )
             sql.append( "null" + ","  );
        else
            sql.append( registro.getStatus() + "," );

        if ( registro.getCategory() == null )
            sql.append( "null" + "," );
        else
            sql.append( registro.getCategory() + "," );

        if ( registro.getDimensions() == null )
            sql.append("null" + "," );
        else
            sql.append( "'" + registro.getDimensions() + "'" + "," );

        if ( registro.getPath() == null )
            sql.append("null" + "'" );
        else
            sql.append( "'" + registro.getPath() + "'" + "," );

        if ( registro.getCreatorName() == null )
            sql.append("null");
        else
            sql.append( "'" + registro.getCreatorName() + "'" );


        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(EasyObject registro) throws NoConnectionException, BDException {

        registro.setModified( new Timestamp(System.currentTimeMillis()));
        registro.setModifiedBy( user.getId() );
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(EasyObject registro) {
        
        String sql = "update objects";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        if ( registro.getObjName() != null )
            sh.append(",", "obj_name = " + "'" + SigarUtils.escape(registro.getObjName()) + "'" );
        if ( registro.getObjDescription() != null )
            sh.append(",", "obj_description = " + "'" + SigarUtils.escape(registro.getObjDescription()) + "'" );
        if ( registro.getObjType() != null )
            sh.append(",", "obj_type = " + registro.getObjType().intValue()  );
        if ( registro.getExtension() != null )
            sh.append(",", "extension = " + "'" + SigarUtils.escape(registro.getExtension()) + "'" );
        if ( registro.getObjSize() != null )
            sh.append(",", "obj_size = " + registro.getObjSize().intValue()  );
        if ( registro.getModifiedBy() != null )
            sh.append(",", "modified_by = " + "'" + SigarUtils.escape(registro.getModifiedBy()) + "'" );
        if ( registro.getModified() != null )
            sh.append(",", "modified = to_timestamp('" + _df.format( registro.getModified() ) + "','" + SigarUtils.POSTGRES_FECHA4 + "')" );
        if ( registro.getStatus() != null )
            sh.append( ",", "status = " + registro.getStatus() );
        if ( registro.getDimensions() != null )
            sh.append( ",", "dimensions = '" + registro.getDimensions() + "'" );
        if ( registro.getCategory() != null )
            sh.append( ",", "category = " + registro.getCategory() );
        if ( registro.getPath() != null )
            sh.append( ",", "path = '" + registro.getPath() + "'" );
        if ( registro.getCreatorName() != null )
            sh.append( ",", "creator_name = " + "'" + registro.getCreatorName() + "'" );
        
        sql = sql + " set " + sh.getClause() + " where object_id = " + registro.getObjectId();
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.Integer objectId ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(objectId) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.Integer objectId) {
        String sql = "delete from objects where object_id = " + objectId;
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {

        Vector<EasyObject> results = new Vector<EasyObject>();


        try
        {
            while ( rs.next() )
            {
                try
                {
                    EasyObject o = new EasyObject();

                    o.setObjectId( new Integer(rs.getInt("object_id")) );
                    o.setObjName( rs.getString("obj_name") );
                    o.setObjDescription( rs.getString("obj_description") );
                    o.setObjType( new Integer(rs.getInt("obj_type")) );
                    o.setExtension( rs.getString("extension") );
                    o.setObjSize( new Integer(rs.getInt("obj_size")) );
                    o.setCreatedBy( rs.getString("created_by") );
                    o.setModifiedBy( rs.getString("modified_by") );
                    o.setCreated( rs.getTimestamp("created") );
                    o.setModified( rs.getTimestamp("modified") );
                    o.setStatus( rs.getInt("status"));
                    //o.setObjData( (byte[]) rs.getBytes("obj_data") );

                    org.postgresql.geometric.PGpoint rsPoint;
                    rsPoint = (org.postgresql.geometric.PGpoint) rs.getObject( "dimensions" );
                    if ( ! rs.wasNull() )
                        o.setDimensions( new com.inga.utils.Point(rsPoint.x,rsPoint.y) );

                    o.setPath( rs.getString("path") );
                    o.setCreatorName( rs.getString("creator_name"));

                    results.add( o );
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch ( SQLException ex )
        {
            throw new BDException(ex);
        }
        
        return results;
    }

    protected Integer createObject(EasyObject registro, boolean isTemp ) throws NoConnectionException, BDException {

        Integer pk;
        String tableName;
        if ( isTemp )
        {
            pk = PostgresUtilDAO.getNextVal(user,conn,"sq_temp_objects");
            tableName = "temp_objects";
        }
        else
        {
            pk = PostgresUtilDAO.getNextVal(user,conn,"sq_objects");
            tableName = "objects";
        }
        registro.setObjectId( pk );
        Timestamp now = new Timestamp(System.currentTimeMillis());
        registro.setCreated( now );
        registro.setModified( now );
        registro.setCreatedBy(user.getId());
        
        this.executeUpdate( this.getCreateStr(registro, isTemp));

        if ( registro.getObjData() != null )
            writeData(registro.getObjectId(), registro.getObjData(), 1, tableName );

        if ( registro.getObjData2() != null )
            writeData(registro.getObjectId(), registro.getObjData2(), 2, tableName );

        return pk;

    }

    @Override
    public Integer createTemp(EasyObject registro) throws NoConnectionException, BDException {
        return createObject(registro,true);
    }

    @Override
    public void copyDataFromTemp(Integer tempObjectId, Integer objectId ) throws NoConnectionException, BDException {

    
        String sql = "update objects " +
                     " set obj_data  = ( select obj_data from temp_objects where object_id = " + tempObjectId + " )," +
                     " obj_data2 = ( select obj_data2 from temp_objects where object_id = " + tempObjectId + " )" +
                     " where object_id = " + objectId;

        executeUpdate( sql );

    }

    @Override
    public int deleteTemp(Integer objectId) throws NoConnectionException, BDException {
        String sql = "delete from temp_objects where object_id = " + objectId;
        int rows = executeUpdate( sql );
        return rows;
   }

    @Override
    public void fillData(EasyObject o, int datafield, String table ) throws BDException, NoConnectionException {
        Integer pk = o.getObjectId();

        String field = "obj_data";
        switch ( datafield )
        {
            case 1:
                field = "obj_data";
                break;
            case 2:
                field = "obj_data2";
                break;
        }

        String sql = "SELECT " + field + " FROM " + table + " WHERE object_id = ?";

        SigarUtils.setAutoCommit(conn, false);
        Savepoint begin = SigarUtils.begin(conn);
        try
        {

            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1, pk );
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                byte[] data = rs.getBytes(1);
                switch ( datafield )
                {
                    case 1:
                        o.setObjData(data);
                        break;
                    case 2:
                        o.setObjData2(data);
                        break;
                }
            }
            rs.close();
            ps.close();
            SigarUtils.commit(conn);

        }
        catch ( SQLException sEx )
        {
            try
            {
                conn.rollback(begin);
            }
            catch (Exception ex )
            {
                ex.printStackTrace();
            }
            
            throw new BDException(sEx);
        }
        finally
        {
            SigarUtils.setAutoCommit(conn, true);
        }

    }

    @Override
    public EasyObject getTemp(Integer objectId) throws NoConnectionException, BDException, RegistroNoExisteException {

        String sql = tempSelectStatement + " where object_id = " + objectId;

        Vector results  = executeQuery( sql );

        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );

        return (EasyObject) results.firstElement();
   }

    @Override
    public void writeData(Integer objectId, byte[] data, int datafield, String table) throws BDException, NoConnectionException {

        SigarUtils.setAutoCommit(conn, false );
        Savepoint begin = SigarUtils.begin(conn);

        
        if ( data != null )
        {
            try
            {
                String field = "obj_data";
                switch ( datafield )
                {
                    case 1:
                        field = "obj_data";
                        break;
                    case 2:
                        field = "obj_data2";
                        break;
                }
                String sql = "UPDATE " + table + " SET " + field + " = ? WHERE object_id = " + objectId ;
                PreparedStatement ps = conn.prepareStatement( sql );
                //ps.setBinaryStream(1, bin, data.length );
                ps.setBytes(1, data );
                log.info(user.getId() + " " +  sql );
                ps.executeUpdate();
                ps.close();
                SigarUtils.commit(conn);
            }
            catch ( Exception ex )
            {
                SigarUtils.rollback(begin, conn);
                throw new BDException(ex);
            }
            finally
            {
                SigarUtils.setAutoCommit(conn, true );
            }

        }

    }
    
}