/*******************************************************************************

com.bnmi.ourmap.model.Project.java
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

import com.inga.utils.SigarUtils;

/*
 * Project.java
 *
 * Created on Fri Mar 05 19:22:58 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class Project implements java.io.Serializable {

   private java.lang.Integer id;
   private java.lang.String nombre;
   private java.lang.String descr;
   private java.sql.Timestamp created;
   private java.sql.Timestamp modified;
   private java.lang.String createdBy;
   private java.lang.String modifiedBy;
   private java.lang.String pwd;
   private String ownerName;

    /** Creates a new instance of Project*/
    public Project() {
        id = null;
        nombre = null;
        descr = null;
        created = null;
        modified = null;
        createdBy = null;
        modifiedBy = null;
        pwd = null;
        ownerName = null;
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
        " pwd=" + getPwd() +
        " owner=" + getOwnerName()
        ;
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
        nombre = SigarUtils.validarCadena(value);
    }

    public java.lang.String getDescr() {
        return descr;
    }

    public void setDescr(java.lang.String value ) {
        descr = value;
    }

    public java.sql.Timestamp getCreated() {
        return created;
    }

    public void setCreated(java.sql.Timestamp value ) {
        created = value;
    }

    public java.sql.Timestamp getModified() {
        return modified;
    }

    public void setModified(java.sql.Timestamp value ) {
        modified = value;
    }

    public java.lang.String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(java.lang.String value ) {
        createdBy = SigarUtils.validarCadena(value);
    }

    public java.lang.String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(java.lang.String value ) {
        modifiedBy = SigarUtils.validarCadena(value);
    }

    public java.lang.String getPwd() {
        return pwd;
    }

    public void setPwd(java.lang.String value ) {
        pwd = SigarUtils.validarCadena(value);
    }

    /**
     * @return the ownerName
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @param ownerName the ownerName to set
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

}