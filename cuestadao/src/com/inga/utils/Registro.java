/**

com.inga.utils.Registro.java
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

package com.inga.utils;

import com.inga.security.User;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Created on Jan 30th 2007, 10:32
 * @author Manuel Camilo Cuesta
 *
 */
public class Registro {

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int FATAL = 6;
    
    private static int level = DEBUG;
    
    public static final String LOGGER_NAME = "monitor";    
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS");
    private static String prefix = null;
    

    public static void debug(Object message) {
        String strPrefix = "";
        if ( prefix != null && prefix.length() > 0 )
            strPrefix = prefix + " ";

        if ( level <= DEBUG )
            System.out.println( strPrefix + message.toString() );

    }

    public static void debug(User user , Object message) {

        if ( level <= DEBUG )
            printMessage( user, message );
    }

    
    public static void info(Object message) {
        
        String strPrefix = "";
        if ( prefix != null && prefix.length() > 0 )
            strPrefix = prefix + " ";
        
        if ( level <= INFO )
            System.out.println( getDate() + " " + strPrefix + message.toString() );

    }

    private static void printMessage(User user, Object message ) {
        try
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append( getDate() + " " );
            if ( prefix != null && prefix.length() > 0 )
                buffer.append( prefix + " " );
            buffer.append( user.getLogin() + " " );
            if ( user.getRemoteAddress() != null )
                buffer.append( user.getRemoteAddress() + " " );

            buffer.append( message );
            System.out.println( buffer.toString() );

        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            System.out.println( "Error Registro :" + ex );
        }

    }

    public static void verbose(User user, Object message) {
        if ( level <= VERBOSE )
            printMessage( user, message );
    }

    
    public static void info(User user, Object message) {
        if ( level <= INFO )
            printMessage(user,message);
    }

    public static void info(User user, Object message, Writer out) {

        if ( level <= INFO )
        {
            try
            {
                StringBuffer buffer = new StringBuffer();
                buffer.append( getDate() + " " );
                if ( prefix != null && prefix.length() > 0 )
                    buffer.append( prefix + " " );
                buffer.append( user.getLogin() + " " );
                if ( user.getRemoteAddress() != null )
                   buffer.append( user.getRemoteAddress() + " " );
                buffer.append( message );
                out.write( buffer.toString() );
                out.flush();
            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
                System.out.println( "Error Registro :" + ex );
            }
            
        }
    }
    
    
    
    public static void warn(String user, String message) {

        String strPrefix = "";
        if ( prefix != null && prefix.length() > 0 )
            strPrefix = prefix + " ";

        if ( level <= WARN )
            System.out.println( getDate() + " " + strPrefix + user + " " + message );
    }
    
    public static void error(String message) {

        String strPrefix = "";
        if ( prefix != null && prefix.length() > 0 )
            strPrefix = prefix + " ";

        if ( level <= ERROR )
            System.out.println( getDate() + " " + strPrefix + message );
    }
    
    public static String getDate() {
        return  df.format( new Date() );
    }
    
    public static void setLevel(int value) {
        if ( value >= DEBUG && value <= FATAL )
            level = value;
    }

    /**
     * @return the prefix
     */
    public static String getPrefix() {
        return prefix;
    }

    /**
     * @param aPrefix the prefix to set
     */
    public static void setPrefix(String aPrefix) {
           prefix = aPrefix;
    }
    
    
}
