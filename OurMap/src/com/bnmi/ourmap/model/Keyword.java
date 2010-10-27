/*******************************************************************************

com.bnmi.ourmap.model.Keyword.java
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
 * Keyword.java
 *
 * Created on Wed Apr 07 10:20:56 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class Keyword implements java.io.Serializable {

   private java.lang.Integer kwId;
   private java.lang.Integer mapId;
   private java.lang.String kwValue;
   private java.lang.Integer catId;
   private java.lang.String createdBy;
   private java.sql.Timestamp created;
   private java.lang.Integer iconId;
   private java.lang.Integer index;
   private String catName;

    /** Creates a new instance of Keyword*/
    public Keyword() {
        kwId = null;
        mapId = null;
        kwValue = null;
        catId = null;
        createdBy = null;
        created = null;
        iconId = null;
        index = null;
        catName = null;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof Keyword)
        {
            Keyword otherObj = (Keyword) obj;
            if ( this.kwId != null && otherObj.getKwId() != null )
                return kwId.intValue() == otherObj.getKwId().intValue();
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.kwId != null ? this.kwId.hashCode() : 0);
        return hash;
    }


    @Override
    public String toString() {
    return 
        " kwId=" + getKwId() + 
        " mapId=" + getMapId() + 
        " kwValue=" + getKwValue() + 
        " catId=" + getCatId() + 
        " createdBy=" + getCreatedBy() + 
        " created=" + getCreated() + 
        " iconId=" + getIconId() + 
        " index=" + getIndex() +
        " catName=" + getCatName()
        ;
    }

    public java.lang.Integer getKwId() {
        return kwId;
    }

    public void setKwId(java.lang.Integer value ) {
        kwId = value;
    }

    public java.lang.Integer getMapId() {
        return mapId;
    }

    public void setMapId(java.lang.Integer value ) {
        mapId = value;
    }

    public java.lang.String getKwValue() {
        return kwValue;
    }

    public void setKwValue(java.lang.String value ) {
        kwValue = SigarUtils.validarCadena(value);
    }

    public java.lang.Integer getCatId() {
        return catId;
    }

    public void setCatId(java.lang.Integer value ) {
        catId = value;
    }

    public java.lang.String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(java.lang.String value ) {
        createdBy = SigarUtils.validarCadena(value);
    }

    public java.sql.Timestamp getCreated() {
        return created;
    }

    public void setCreated(java.sql.Timestamp value ) {
        created = value;
    }

    public java.lang.Integer getIconId() {
        return iconId;
    }

    public void setIconId(java.lang.Integer value ) {
        iconId = value;
    }

    public java.lang.Integer getIndex() {
        return index;
    }

    public void setIndex(java.lang.Integer value ) {
        index = value;
    }

    /**
     * @return the catName
     */
    public String getCatName() {
        return catName;
    }

    /**
     * @param catName the catName to set
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

}