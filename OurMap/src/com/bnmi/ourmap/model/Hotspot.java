/*******************************************************************************

com.bnmi.ourmap.model.Hotspot.java
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
 * Hotspot.java
 *
 * Created on Sat Feb 27 23:46:05 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class Hotspot implements java.io.Serializable {

   private java.lang.Integer hsId;
   private com.inga.utils.Point hsPos;
   private java.lang.String hsName;
   private java.lang.String hsDescription;
   private java.lang.Integer hsLayer;
   private java.lang.Double altitude;
   private java.sql.Timestamp created;
   private java.sql.Timestamp modified;
   private java.lang.String createdBy;
   private java.lang.String modifiedBy;
   private java.lang.Integer iconId;
   private java.lang.Integer keywordId;
   private java.lang.Integer displayMode;
   private java.lang.Integer proximityRadius;
   private Keyword keyword;
   private String creatorName;
   private boolean forceSetDescription;

    /** Creates a new instance of Hotspot*/
    public Hotspot() {
        hsId = null;
        hsPos = null;
        hsName = null;
        hsDescription = null;
        hsLayer = null;
        altitude = null;
        created = null;
        modified = null;
        createdBy = null;
        modifiedBy = null;
        iconId = null;
        keywordId = null;
        displayMode = null;
        proximityRadius = null;
        keyword = null;
        creatorName = null;
        forceSetDescription = false;
    }

    @Override
    public String toString() {
    return 
        " hsId=" + getHsId() + 
        " hsPos=" + getHsPos() + 
        " hsName=" + getHsName() + 
        " hsDescription=" + getHsDescription() + 
        " hsLayer=" + getHsLayer() + 
        " altitude=" + getAltitude() + 
        " created=" + getCreated() + 
        " modified=" + getModified() + 
        " createdBy=" + getCreatedBy() + 
        " modifiedBy=" + getModifiedBy() + 
        " iconId=" + getIconId() + 
        " keywordId=" + getKeywordId() + 
        " displayMode=" + getDisplayMode() + 
        " proximityRadius=" + getProximityRadius()
        ;
    }

    public java.lang.Integer getHsId() {
        return hsId;
    }

    public void setHsId(java.lang.Integer value ) {
        hsId = value;
    }

    public com.inga.utils.Point getHsPos() {
        return hsPos;
    }

    public void setHsPos(com.inga.utils.Point value ) {
        hsPos = value;
    }

    public java.lang.String getHsName() {
        return hsName;
    }

    public void setHsName(java.lang.String value ) {
        hsName = SigarUtils.validarCadena(value);
    }

    public java.lang.String getHsDescription() {
        return hsDescription;
    }

    public void setHsDescription(java.lang.String value ) {
        hsDescription = SigarUtils.validarCadena(value);
    }

    public java.lang.Integer getHsLayer() {
        return hsLayer;
    }

    public void setHsLayer(java.lang.Integer value ) {
        hsLayer = value;
    }

    public java.lang.Double getAltitude() {
        return altitude;
    }

    public void setAltitude(java.lang.Double value ) {
        altitude = value;
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

    public java.lang.Integer getIconId() {
        return iconId;
    }

    public void setIconId(java.lang.Integer value ) {
        iconId = value;
    }

    public java.lang.Integer getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(java.lang.Integer value ) {
        keywordId = value;
    }

    public java.lang.Integer getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(java.lang.Integer value ) {
        displayMode = value;
    }

    public java.lang.Integer getProximityRadius() {
        return proximityRadius;
    }

    public void setProximityRadius(java.lang.Integer value ) {
        proximityRadius = value;
    }

    /**
     * @return the keyword
     */
    public Keyword getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }


    /**
     * @return the forceSetDescription
     */
    public boolean isForceSetDescription() {
        return forceSetDescription;
    }

    /**
     * @param forceSetDescription the forceSetDescription to set
     */
    public void setForceSetDescription(boolean forceSetDescription) {
        this.forceSetDescription = forceSetDescription;
    }

    /**
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

}