/*******************************************************************************

com.bnmi.ourmap.model.Layer.java
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
 * Layer.java
 *
 * Created on Wed Jan 27 17:53:42 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class Layer implements java.io.Serializable {

   private java.lang.Integer layerId;
   private java.lang.String layerName;
   private java.lang.String layerDescription;
   private java.lang.Integer mapId;
   private java.sql.Timestamp created;
   private java.sql.Timestamp modified;
   private java.lang.String createdBy;
   private java.lang.String modifiedBy;
   private java.lang.Integer index;
   private java.lang.Integer iconId;

    /** Creates a new instance of Layer*/
    public Layer() {
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
        layerName = SigarUtils.validarCadena(value);
    }

    public java.lang.String getLayerDescription() {
        return layerDescription;
    }

    public void setLayerDescription(java.lang.String value ) {
        layerDescription = SigarUtils.validarCadena(value);
    }

    public java.lang.Integer getMapId() {
        return mapId;
    }

    public void setMapId(java.lang.Integer value ) {
        mapId = value;
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