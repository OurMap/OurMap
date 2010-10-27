/**

com.inga.model.CriteriosUserTabCol.java
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

package com.inga.modelo;

/** Models a CriteriosUserTabCol
 *
 * CriteriosUserTabCol.java
 *
 * Created on Jan 30th 2007, 10:32
 * @author Manuel Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class CriteriosUserTabCol implements java.io.Serializable {

   private java.lang.String tableName;
   private java.lang.String columnName;
   private java.lang.String dataType;
   private java.lang.String nullable;

    /** Creates a new instance of CriteriosUserTabCol*/
    public CriteriosUserTabCol() {
        tableName = null;
        columnName = null;
        dataType = null;
        nullable = null;
    }

    @Override
    public String toString() {
    return 
        " tableName = " + getTableName() + 
        " columnName = " + getColumnName() + 
        " dataType = " + getDataType() + 
        " nullable = " + getNullable()
        ;
    }

    public java.lang.String getTableName() {
        return tableName;
    }

    public void setTableName(java.lang.String value ) {
        tableName = value;
    }

    public java.lang.String getColumnName() {
        return columnName;
    }

    public void setColumnName(java.lang.String value ) {
        columnName = value;
    }

    public java.lang.String getDataType() {
        return dataType;
    }

    public void setDataType(java.lang.String value ) {
        dataType = value;
    }

    public java.lang.String getNullable() {
        return nullable;
    }

    public void setNullable(java.lang.String value ) {
        nullable = value;
    }

}