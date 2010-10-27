/*******************************************************************************

com.bnmi.ourmap.model.CriteriosKeyword.java
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
 * CriteriosKeyword.java
 *
 * Created on Wed Apr 07 10:20:56 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class CriteriosKeyword implements java.io.Serializable {

   private java.lang.Integer kwId;
   private java.lang.Integer mapId;
   private java.lang.String kwValue;
   private java.lang.Integer catId;
   private java.lang.String createdBy;
   private DateRange created;
   private java.lang.Integer iconId;
   private java.lang.Integer index;

    /** Creates a new instance of CriteriosKeyword*/
    public CriteriosKeyword() {
        kwId = null;
        mapId = null;
        kwValue = null;
        catId = null;
        createdBy = null;
        created = null;
        iconId = null;
        index = null;
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
        " index=" + getIndex()
        ;
    }

    public String getCriteriaStr() {
        SqlClauseHelper sh = new SqlClauseHelper();

        if ( getKwId() != null )
            sh.append("&", "kwId=" + String.valueOf(getKwId()) );
        if ( getMapId() != null )
            sh.append("&", "mapId=" + String.valueOf(getMapId()) );
        if ( getKwValue() != null )
            sh.append("&", "kwValue=" + String.valueOf(getKwValue()) );
        if ( getCatId() != null )
            sh.append("&", "catId=" + String.valueOf(getCatId()) );
        if ( getCreatedBy() != null )
            sh.append("&", "createdBy=" + String.valueOf(getCreatedBy()) );
        if ( getCreated() != null )
            sh.append("&", "created=" + String.valueOf(getCreated()) );
        if ( getIconId() != null )
            sh.append("&", "iconId=" + String.valueOf(getIconId()) );
        if ( getIndex() != null )
            sh.append("&", "index=" + String.valueOf(getIndex()) );

        String cadena = sh.toString();
        if ( cadena.length() >  0 )
            cadena = "&" + cadena;

        return cadena;
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
        kwValue = value;
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
        createdBy = value;
    }

    public DateRange getCreated() {
        return created;
    }

    public void setCreated(DateRange value ) {
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

}