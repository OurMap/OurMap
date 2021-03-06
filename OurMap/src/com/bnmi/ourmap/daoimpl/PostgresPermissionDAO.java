/*******************************************************************************

com.bnmi.ourmap.daoimpl.PostgresPermissionDAO.java
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
import com.bnmi.ourmap.model.Permission;
import com.bnmi.ourmap.model.CriteriosPermission;
import com.bnmi.ourmap.dao.PermissionDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/*
 * PostgresPermissionDAO.java
 *
 * Created on Thu Mar 04 11:32:13 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
public class PostgresPermissionDAO extends BasicDAO implements PermissionDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA4 );


        
    @Override
    public Permission get( java.lang.String userid, java.lang.Integer elementId, java.lang.Integer elementType ) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from permissions where userid = '" + userid + "'"  + " and element_id = " + elementId + " and element_type = " + elementType;
        
        Vector results  = executeQuery( sql );
         
        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "" );
        
        return (Permission) results.firstElement();
    }

    @Override
    public Integer create(Permission registro) throws NoConnectionException, BDException {

        int rows = executeUpdate( getCreateStr(registro) );
        
        return new Integer(rows);
    }

    public String getCreateStr(Permission registro) {

        StringBuffer sql = new StringBuffer();

        sql.append( "insert into permissions (");
        sql.append("userid, element_id, element_type, ownership, member");
        sql.append(")");
        sql.append(" values (");
        
        if ( registro.getUserid() == null )
             sql.append( "null" + "," );
        else
            sql.append( "'" + SigarUtils.escape(registro.getUserid()) + "'" + "," );
        if ( registro.getElementId() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getElementId().intValue() + "," );
        if ( registro.getElementType() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getElementType().intValue() + "," );
        if ( registro.getOwnership() == null )
             sql.append( "null" + "," );
        else
            sql.append( registro.getOwnership().intValue() + "," );
        if ( registro.getMember() == null )
             sql.append( "null" );
        else
            sql.append( registro.getMember().intValue() );
        sql.append( ")" );
        
        return sql.toString();
        
    }
    @Override
    public int update(Permission registro) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getUpdateStr(registro) );
        
        return rows;
    }
    
    public static String getUpdateStr(Permission registro) {
        
        String sql = "update permissions";
        SqlClauseHelper sh = new SqlClauseHelper();
        
        if ( registro.getOwnership() != null )
            sh.append(",", "ownership = " + registro.getOwnership().intValue()  );
        if ( registro.getMember() != null )
            sh.append(",", "member = " + registro.getMember().intValue()  );
        
        sql = sql + " set " + sh.getClause() + " where userid = '" + registro.getUserid() + "'"  + " and element_id = " + registro.getElementId() + " and element_type = " + registro.getElementType();
        
        return sql.toString();
    }
    
    @Override
    public int delete( java.lang.String userid, java.lang.Integer elementId, java.lang.Integer elementType ) throws NoConnectionException, BDException {
        
        int rows = executeUpdate( getDeleteStr(userid, elementId, elementType) );
        
        return rows;        
    }

    public static String getDeleteStr(java.lang.String userid, java.lang.Integer elementId, java.lang.Integer elementType) {
        
        String sql = "delete from permissions where userid = '" + userid + "'"  + " and element_id = " + elementId + " and element_type = " + elementType;
        
        return sql;
    }

    @Override
    protected Vector extract(ResultSet rs ) throws BDException {
        Vector<Permission> results = new Vector<Permission>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    Permission o = new Permission();
                    
                    o.setUserid( rs.getString("userid") );
                    o.setElementId( new Integer(rs.getInt("element_id")) );
                    o.setElementType( new Integer(rs.getInt("element_type")) );
                    o.setOwnership( new Integer(rs.getInt("ownership")) );
                    o.setMember( new Integer(rs.getInt("member")) );
                    
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

    @SuppressWarnings("unchecked")
    public Vector<Permission> find(CriteriosPermission criteria) throws NoConnectionException, BDException {
        Vector<Permission> results = new Vector<Permission>();

        String sql = "select * from permissions";

        SqlClauseHelper _sh = new SqlClauseHelper();

        if ( criteria.getUserid() != null )
            _sh.addAndClause("userid = '" + SigarUtils.escape(criteria.getUserid()) + "'" );
        if ( criteria.getElementId() != null )
            _sh.addAndClause("element_id = " + criteria.getElementId().intValue() );
        if ( criteria.getElementType() != null )
            _sh.addAndClause("element_type = " + criteria.getElementType().intValue() );
        if ( criteria.getOwnership() != null )
            _sh.addAndClause("ownership = " + criteria.getOwnership().intValue() );
        if ( criteria.getMember() != null )
            _sh.addAndClause("member = " + criteria.getMember().intValue() );


        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;

        // Aqui' puede especificar el ordenamiento de los registros
        //sql = sql + " order by ...";
        results = executeQuery( sql );


        return results;
     }

    @Override
    public List<String> getOwners(Integer elementId, Integer elementType) throws NoConnectionException, BDException {
        CriteriosPermission findOwners = new CriteriosPermission();
        findOwners.setElementId(elementId);
        findOwners.setElementType(elementType);
        findOwners.setOwnership(1);
        Vector<Permission> results = find( findOwners );
        List<String> owners = new ArrayList<String>();
        for ( Permission p : results )
            owners.add( p.getUserid() );
        return owners;
    }

    @Override
    public List<String> getMembers(Integer elementId, Integer elementType) throws NoConnectionException, BDException {
        CriteriosPermission findMembers = new CriteriosPermission();
        findMembers.setElementId(elementId);
        findMembers.setElementType(elementType);
        findMembers.setMember(1);
        Vector<Permission> results = find( findMembers );
        List<String> members = new ArrayList<String>();
        for ( Permission p : results )
            members.add( p.getUserid() );
        return members;
    }






}


/*

    @SuppressWarnings("unchecked")
    @Override
    public Vector<Permission> find(CriteriosPermission criteria) throws NoConnectionException, BDException {
        Vector<Permission> results = new Vector<Permission>();
        
        String sql = "select * from permissions";

        SqlClauseHelper _sh = new SqlClauseHelper();

        if ( criteria.getUserid() != null )
            _sh.addAndClause("userid = '" + SigarUtils.escape(criteria.getUserid()) + "'" );
        if ( criteria.getElementId() != null )
            _sh.addAndClause("element_id = " + criteria.getElementId().intValue() );
        if ( criteria.getElementType() != null )
            _sh.addAndClause("element_type = " + criteria.getElementType().intValue() );
        if ( criteria.getOwnership() != null )
            _sh.addAndClause("ownership = " + criteria.getOwnership().intValue() );
        if ( criteria.getMember() != null )
            _sh.addAndClause("member = " + criteria.getMember().intValue() );


        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " where " + clause;

        // Aqui' puede especificar el ordenamiento de los registros
        //sql = sql + " order by ...";
        results = executeQuery( sql );


        return results;
     }
 */