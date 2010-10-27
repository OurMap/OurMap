/*******************************************************************************

com.bnmi.ourmap.model.CriteriosLayer.java
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
 * CriteriosLayer.java
 *
 * Created on Wed Jan 27 17:53:42 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class CriteriosLayer implements java.io.Serializable {

   private java.lang.Integer layerId;
   private java.lang.String layerName;
   private java.lang.String layerDescription;
   private java.lang.Integer mapId;
   private DateRange created;
   private DateRange modified;
   private java.lang.String createdBy;
   private java.lang.String modifiedBy;
   private java.lang.Integer index;
   private java.lang.Integer iconId;

    /** Creates a new instance of CriteriosLayer*/
    public CriteriosLayer() {
        layerId = null;
        layerName = null;
        layerDescription = null;
        mapId = null;
        created = null;
        modified = null;
        createdBy = null;
        modifiedBy = null;
        index = null;
        iconId = null;
    }

    @Override
    public String toString() {
    return 
        " layerId=" + getLayerId() + 
        " layerName=" + getLayerName() + 
        " layerDescription=" + getLayerDescription() + 
        " mapId=" + getMapId() + 
        " created=" + getCreated() + 
        " modified=" + getModified() + 
        " createdBy=" + getCreatedBy() + 
        " modifiedBy=" + getModifiedBy() + 
        " index=" + getIndex() + 
        " iconId=" + getIconId()
        ;
    }

    public String getCriteriaStr() {
        SqlClauseHelper sh = new SqlClauseHelper();

        if ( getLayerId() != null )
            sh.append("&", "layerId=" + String.valueOf(getLayerId()) );
        if ( getLayerName() != null )
            sh.append("&", "layerName=" + String.valueOf(getLayerName()) );
        if ( getLayerDescription() != null )
            sh.append("&", "layerDescription=" + String.valueOf(getLayerDescription()) );
        if ( getMapId() != null )
            sh.append("&", "mapId=" + String.valueOf(getMapId()) );
        if ( getCreated() != null )
            sh.append("&", "created=" + String.valueOf(getCreated()) );
        if ( getModified() != null )
            sh.append("&", "modified=" + String.valueOf(getModified()) );
        if ( getCreatedBy() != null )
            sh.append("&", "createdBy=" + String.valueOf(getCreatedBy()) );
        if ( getModifiedBy() != null )
            sh.append("&", "modifiedBy=" + String.valueOf(getModifiedBy()) );
        if ( getIndex() != null )
            sh.append("&", "index=" + String.valueOf(getIndex()) );
        if ( getIconId() != null )
            sh.append("&", "iconId=" + String.valueOf(getIconId()) );

        String cadena = sh.toString();
        if ( cadena.length() >  0 )
            cadena = "&" + cadena;

        return cadena;
    }

    public java.lang.Integer getLayerId() {
        return layerId;
    }

    public void setLayerId(java.lang.Integer value ) {
        layerId = value;
    }

    public java.lang.String getLayerName() {
        return layerName;
    }

    public void setLayerName(java.lang.String value ) {
        layerName = value;
    }

    public java.lang.String getLayerDescription() {
        return layerDescription;
    }

    public void setLayerDescription(java.lang.String value ) {
        layerDescription = value;
    }

    public java.lang.Integer getMapId() {
        return mapId;
    }

    public void setMapId(java.lang.Integer value ) {
        mapId = value;
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

    public java.lang.Integer getIndex() {
        return index;
    }

    public void setIndex(java.lang.Integer value ) {
        index = value;
    }

    public java.lang.Integer getIconId() {
        return iconId;
    }

    public void setIconId(java.lang.Integer value ) {
        iconId = value;
    }

}