/**

com.inga.utils.Fecha.java
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * Fecha.java
 *
 * Created on Jan 30th 2007, 10:32
 * @author Manuel Camilo Cuesta
 *
 */
public class Fecha extends Date {
   
    private static final long serialVersionUID = -5506734657547780315L;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    /** Crea una nueva instancia de Fecha */
    public Fecha(int day, int month, int year) {
        super();
        GregorianCalendar gc = new GregorianCalendar(year,month - 1 ,day);
        setTime( gc.getTimeInMillis() );
    }
    
    public Fecha() {
        super();
    }

    public Fecha(java.util.Date pDate) {
        super();
        setTime( pDate.getTime() );
    }
    
    /** Crea una nueva instancia de Fecha */
    public Fecha(int day, int month, int year, int hours, int mins, int secs) {
        super();
        GregorianCalendar gc = new GregorianCalendar(year,month - 1 ,day, hours, mins, secs);
        setTime( gc.getTimeInMillis() );
    }
    
    public java.sql.Timestamp getTimestamp() {
        return new Timestamp( getTime() );
    }
    
    @Override
    public String toString() {
        return df.format( this );
    }
    
    public String print(String format) {
        SimpleDateFormat printFormat = new SimpleDateFormat(format);
        return printFormat.format(this);
    }
    
}
