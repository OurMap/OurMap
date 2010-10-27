/*******************************************************************************

com.bnmi.ourmap.model.Category.java
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
 * Category.java
 *
 * Created on Sun Aug 22 17:34:10 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class Category implements java.io.Serializable {

   private java.lang.Integer catId;
   private java.lang.Integer mapId;
   private java.lang.String catName;
   private java.lang.Integer iconId;
   private java.lang.String createdBy;
   private java.sql.Timestamp created;

    /** Creates a new instance of Category*/
    public Category() {
        catId = null;
        mapId = null;
        catName = null;
        iconId = null;
        createdBy = null;
        created = null;
    }

    @Override
    public String toString() {
    return 
        " catId=" + getCatId() + 
        " mapId=" + getMapId() + 
        " catName=" + getCatName() + 
        " iconId=" + getIconId() + 
        " createdBy=" + getCreatedBy() + 
        " created=" + getCreated()
        ;
    }

    public java.lang.Integer getCatId() {
        return catId;
    }

    public void setCatId(java.lang.Integer value ) {
        catId = value;
    }

    public java.lang.Integer getMapId() {
        return mapId;
    }

    public void setMapId(java.lang.Integer value ) {
        mapId = value;
    }

    public java.lang.String getCatName() {
        return catName;
    }

    public void setCatName(java.lang.String value ) {
        catName = SigarUtils.validarCadena(value);
    }

    public java.lang.Integer getIconId() {
        return iconId;
    }

    public void setIconId(java.lang.Integer value ) {
        iconId = value;
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

}