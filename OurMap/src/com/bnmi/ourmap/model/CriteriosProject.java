/*******************************************************************************

com.bnmi.ourmap.model.CriteriosProject.java
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

package com.bnmi.ourmap.model;
import com.inga.utils.DateRange;
import com.inga.utils.SqlClauseHelper;

/*
 * CriteriosProject.java
 *
 * Created on Fri Mar 05 19:22:58 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class CriteriosProject implements java.io.Serializable {

   private java.lang.Integer id;
   private java.lang.String nombre;
   private java.lang.String descr;
   private DateRange created;
   private DateRange modified;
   private java.lang.String createdBy;
   private java.lang.String modifiedBy;
   private java.lang.String pwd;

    /** Creates a new instance of CriteriosProject*/
    public CriteriosProject() {
        id = null;
        nombre = null;
        descr = null;
        created = null;
        modified = null;
        createdBy = null;
        modifiedBy = null;
        pwd = null;
    }

    @Override
    public String toString() {
    return 
        " id=" + getId() + 
        " nombre=" + getNombre() + 
        " descr=" + getDescr() + 
        " created=" + getCreated() + 
        " modified=" + getModified() + 
        " createdBy=" + getCreatedBy() + 
        " modifiedBy=" + getModifiedBy() + 
        " pwd=" + getPwd()
        ;
    }

    public String getCriteriaStr() {
        SqlClauseHelper sh = new SqlClauseHelper();

        if ( getId() != null )
            sh.append("&", "id=" + String.valueOf(getId()) );
        if ( getNombre() != null )
            sh.append("&", "nombre=" + String.valueOf(getNombre()) );
        if ( getDescr() != null )
            sh.append("&", "descr=" + String.valueOf(getDescr()) );
        if ( getCreated() != null )
            sh.append("&", "created=" + String.valueOf(getCreated()) );
        if ( getModified() != null )
            sh.append("&", "modified=" + String.valueOf(getModified()) );
        if ( getCreatedBy() != null )
            sh.append("&", "createdBy=" + String.valueOf(getCreatedBy()) );
        if ( getModifiedBy() != null )
            sh.append("&", "modifiedBy=" + String.valueOf(getModifiedBy()) );
        if ( getPwd() != null )
            sh.append("&", "pwd=" + String.valueOf(getPwd()) );

        String cadena = sh.toString();
        if ( cadena.length() >  0 )
            cadena = "&" + cadena;

        return cadena;
    }

    public java.lang.Integer getId() {
        return id;
    }

    public void setId(java.lang.Integer value ) {
        id = value;
    }

    public java.lang.String getNombre() {
        return nombre;
    }

    public void setNombre(java.lang.String value ) {
        nombre = value;
    }

    public java.lang.String getDescr() {
        return descr;
    }

    public void setDescr(java.lang.String value ) {
        descr = value;
    }

    public DateRange getCreated() {
        return created;
    }

    public void setCreated(DateRange value ) {
        created = value;
    }

    public DateRange getModified() {
        return modified;
    }

    public void setModified(DateRange value ) {
        modified = value;
    }

    public java.lang.String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(java.lang.String value ) {
        createdBy = value;
    }

    public java.lang.String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(java.lang.String value ) {
        modifiedBy = value;
    }

    public java.lang.String getPwd() {
        return pwd;
    }

    public void setPwd(java.lang.String value ) {
        pwd = value;
    }

}