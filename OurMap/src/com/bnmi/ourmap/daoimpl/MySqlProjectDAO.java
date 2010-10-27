/*******************************************************************************

com.bnmi.ourmap.daoimpl.MySqlProjectDAO.java
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

import com.bnmi.ourmap.dao.ProjectDAO;
import com.bnmi.ourmap.model.CriteriosProject;
import com.bnmi.ourmap.model.Project;
import com.inga.dao.BasicDAO;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.exception.RegistroNoExisteException;
import com.inga.utils.SigarUtils;
import com.inga.utils.SqlClauseHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class MySqlProjectDAO extends BasicDAO implements ProjectDAO {

    private static SimpleDateFormat _df = new SimpleDateFormat( SigarUtils.FECHA5 );


    public static String getFindStr(CriteriosProject criteria) {
        String sql = "select * from node where type = 'project'";

        SqlClauseHelper _sh = new SqlClauseHelper();

        if ( criteria.getId() != null )
            _sh.addAndClause("nid = " + criteria.getId().intValue() );
        if ( criteria.getNombre() != null )
            _sh.addAndClause("title = '" + SigarUtils.escape(criteria.getNombre()) + "'" );
//        if ( criteria.getDescr() != null )
//            _sh.addAndClause("descr = '" + SigarUtils.escape(criteria.getDescr()) + "'" );
        if ( criteria.getCreated() != null )
        {
           if ( criteria.getCreated().getInicio() != null )
               _sh.addAndClause("created >= timestamp('" + _df.format(criteria.getCreated().getInicio()) + "')" );
           if ( criteria.getCreated().getFin() != null )
               _sh.addAndClause("created <= timestamp('" + _df.format(criteria.getCreated().getFin()) + "')" );
        }
        if ( criteria.getCreatedBy() != null )
            _sh.addAndClause("uid = '" + SigarUtils.escape(criteria.getCreatedBy()) + "'" );



        String clause = _sh.getClause();
        if ( clause.length() > 0 )
            sql = sql + " and " + clause;

        // Aqui' puede especificar el ordenamiento de los registros
        sql = sql + " order by created";
        return sql ;

    }

    @Override
    public Vector<Project> find(CriteriosProject criteria) throws NoConnectionException, BDException {
        Vector<Project> results = null;
        results = this.executeQuery( getFindStr(criteria));
        return results ;
    }

    @Override
    public Project get(Integer id) throws NoConnectionException, BDException, RegistroNoExisteException {
        String sql = "select * from node where type = 'project' and nid = " + id;


        Vector results  = executeQuery( sql );

        if ( results.isEmpty() )
            throw new RegistroNoExisteException( "No projects found: " + id );

        return (Project) results.firstElement();
    }

    @Override
    public Integer create(Project registro) throws NoConnectionException, BDException {
        throw new UnsupportedOperationException("Create Project: Not supported.");
    }

    @Override
    public int update(Project registro) throws NoConnectionException, BDException {
        throw new UnsupportedOperationException("Update Project: Not supported.");
    }

    @Override
    public int delete(Integer id) throws NoConnectionException, BDException {
        throw new UnsupportedOperationException("Delete Project: Not supported.");
    }

    @Override
    protected Vector extract(ResultSet rs) throws BDException {
        Vector<Project> results = new Vector<Project>();
        try
        {
            while ( rs.next() )
            {
                try
                {
                    Project o = new Project();

                    o.setId( new Integer(rs.getInt("nid")) );
                    o.setNombre( rs.getString("title") );
//                    o.setDescr( rs.getString("descr") );
                    int createdInt = rs.getInt("created");
                    if ( !rs.wasNull() && createdInt != 0 )
                        o.setCreated( rs.getTimestamp("created") );
                    //o.setModified( rs.getTimestamp("modified") );
                    o.setCreatedBy( rs.getString("uid") );

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
