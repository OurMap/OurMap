/**

com.inga.dao.Sentencia.java
Version: 1.0

********************************************************************************
Author:
Manuel Cuesta, programmer <camilocuesta@hotmail.com>

**************************************************

cuestadao is Copyright (c) 2010, Manuel Cuesta  <camilocuesta@hotmail.com >
All rights reserved.

Published under the terms of the new BSD license.
See: [http://github.com/m-cuesta/tiers] for the full license and other info.

LICENSE:

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.

Neither the name of Manuel Cuesta nor the names of its contributors may be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.


**************************************************
Revision History / Change Log:

**************************************************
Notes:

*******************************************************************************/

package com.inga.dao;

import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.security.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/*
 * Sentencia.java
 *
 * Models an sql statement
 *
 * Created on March 22nd 2007, 9:14
 * @author Manuel Camilo Cuesta
 *
 */
public class Sentencia implements ConnectionUser {
    
    private static Logger log = Logger.getLogger( Sentencia.class );
    private Connection conn;
    private java.sql.Statement stmt;
    private ResultSet rs;
    private User user;

    
    /**
     * Crea una instancia de Sentencia
     */
    public Sentencia(User pUser, Connection pConn) throws BDException { 
        
        user = pUser;
        this.conn = pConn;
        
        try
        {
            this.stmt = conn.createStatement();
        }
        catch (SQLException e)
        {
            throw new BDException(e);
        }
    }
    
    public ResultSet executeQuery(String sql) throws NoConnectionException, BDException {
        
        // Si no hay conexión
        if ( getConnection() == null )
            throw new NoConnectionException();

        //Registro.debug( getUser(), sql );
        log.debug( getUser().getId() + " " + sql );
        
        try 
        {
            this.rs = stmt.executeQuery(sql);
        } 
        catch (SQLException ex) 
        {
            throw new BDException(ex);
        }
        
        return rs;
        
    }
    
    public int executeUpdate(String sql) throws NoConnectionException, BDException {
        // Si no hay conexión
        if ( getConnection() == null )
            throw new NoConnectionException();

        int affected = -1;
        try 
        {
            //Registro.info( getUser(),sql );
            log.info( getUser().getId() + " " + sql );
            affected = stmt.executeUpdate(sql);
        } 
        catch (SQLException ex) 
        {
            throw new BDException(ex);
        }
        
        return affected;
        
    }
    
    public void close() {
        if ( this.rs != null )
        {
            try
            {
                rs.close();
            }
            catch ( Exception e )
            {
                // Nada
            }
        }
        if ( this.stmt != null)
        {
            try
            {
                stmt.close();
            }
            catch ( Exception e )
            {
                // Nada
            }
        }
        
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Connection getConnection() {
        return conn;
    }

    @Override
    public void setConnection(Connection pConn) {
        conn = pConn;
    }

    @Override
    public void setUser(User pUser) {
        user = pUser;
    }


    
    
    
    
}
