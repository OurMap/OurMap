/*******************************************************************************

com.bnmi.ourmap.model.CriteriosMap.java
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
 * CriteriosMap.java
 *
 * Created on Mon Jun 07 13:22:38 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class CriteriosMap implements java.io.Serializable {

   private java.lang.Integer mapid;
   private java.lang.String mapname;
   private com.inga.utils.Point leftBottom;
   private com.inga.utils.Point rightTop;
   private java.lang.String backmap;
   private java.lang.Integer zoom;
   private com.inga.utils.Point center;
   private java.lang.Integer projectId;
   private DateRange created;
   private DateRange modified;
   private java.lang.String createdBy;
   private java.lang.String modifiedBy;
   private java.lang.String subtitle;
   private java.lang.Integer hotspotsMode;
   private java.lang.String description;
   private java.lang.String disKs;
   private java.lang.Integer iconsMode;
   private java.lang.Integer privacy;
   private java.lang.Integer hotspotsEditable;
   private java.lang.Integer mediaEditable;
   private java.lang.String catsEnabled;
   private java.lang.Integer displayMode;

    /** Creates a new instance of CriteriosMap*/
    public CriteriosMap() {
        mapid = null;
        mapname = null;
        leftBottom = null;
        rightTop = null;
        backmap = null;
        zoom = null;
        center = null;
        projectId = null;
        created = null;
        modified = null;
        createdBy = null;
        modifiedBy = null;
        subtitle = null;
        hotspotsMode = null;
        description = null;
        disKs = null;
        iconsMode = null;
        privacy = null;
        hotspotsEditable = null;
        mediaEditable = null;
        catsEnabled = null;
        displayMode = null;
    }

    @Override
    public String toString() {
    return 
        " mapid=" + getMapid() + 
        " mapname=" + getMapname() + 
        " leftBottom=" + getLeftBottom() + 
        " rightTop=" + getRightTop() + 
        " backmap=" + getBackmap() + 
        " zoom=" + getZoom() + 
        " center=" + getCenter() + 
        " projectId=" + getProjectId() + 
        " created=" + getCreated() + 
        " modified=" + getModified() + 
        " createdBy=" + getCreatedBy() + 
        " modifiedBy=" + getModifiedBy() + 
        " subtitle=" + getSubtitle() + 
        " hotspotsMode=" + getHotspotsMode() + 
        " description=" + getDescription() + 
        " disKs=" + getDisKs() + 
        " iconsMode=" + getIconsMode() + 
        " privacy=" + getPrivacy() + 
        " hotspotsEditable=" + getHotspotsEditable() + 
        " mediaEditable=" + getMediaEditable() + 
        " catsEnabled=" + getCatsEnabled() + 
        " displayMode=" + getDisplayMode()
        ;
    }

    public String getCriteriaStr() {
        SqlClauseHelper sh = new SqlClauseHelper();

        if ( getMapid() != null )
            sh.append("&", "mapid=" + String.valueOf(getMapid()) );
        if ( getMapname() != null )
            sh.append("&", "mapname=" + String.valueOf(getMapname()) );
        if ( getLeftBottom() != null )
            sh.append("&", "leftBottom=" + String.valueOf(getLeftBottom()) );
        if ( getRightTop() != null )
            sh.append("&", "rightTop=" + String.valueOf(getRightTop()) );
        if ( getBackmap() != null )
            sh.append("&", "backmap=" + String.valueOf(getBackmap()) );
        if ( getZoom() != null )
            sh.append("&", "zoom=" + String.valueOf(getZoom()) );
        if ( getCenter() != null )
            sh.append("&", "center=" + String.valueOf(getCenter()) );
        if ( getProjectId() != null )
            sh.append("&", "projectId=" + String.valueOf(getProjectId()) );
        if ( getCreated() != null )
            sh.append("&", "created=" + String.valueOf(getCreated()) );
        if ( getModified() != null )
            sh.append("&", "modified=" + String.valueOf(getModified()) );
        if ( getCreatedBy() != null )
            sh.append("&", "createdBy=" + String.valueOf(getCreatedBy()) );
        if ( getModifiedBy() != null )
            sh.append("&", "modifiedBy=" + String.valueOf(getModifiedBy()) );
        if ( getSubtitle() != null )
            sh.append("&", "subtitle=" + String.valueOf(getSubtitle()) );
        if ( getHotspotsMode() != null )
            sh.append("&", "hotspotsMode=" + String.valueOf(getHotspotsMode()) );
        if ( getDescription() != null )
            sh.append("&", "description=" + String.valueOf(getDescription()) );
        if ( getDisKs() != null )
            sh.append("&", "disKs=" + String.valueOf(getDisKs()) );
        if ( getIconsMode() != null )
            sh.append("&", "iconsMode=" + String.valueOf(getIconsMode()) );
        if ( getPrivacy() != null )
            sh.append("&", "privacy=" + String.valueOf(getPrivacy()) );
        if ( getHotspotsEditable() != null )
            sh.append("&", "hotspotsEditable=" + String.valueOf(getHotspotsEditable()) );
        if ( getMediaEditable() != null )
            sh.append("&", "mediaEditable=" + String.valueOf(getMediaEditable()) );
        if ( getCatsEnabled() != null )
            sh.append("&", "catsEnabled=" + String.valueOf(getCatsEnabled()) );
        if ( getDisplayMode() != null )
            sh.append("&", "displayMode=" + String.valueOf(getDisplayMode()) );

        String cadena = sh.toString();
        if ( cadena.length() >  0 )
            cadena = "&" + cadena;

        return cadena;
    }

    public java.lang.Integer getMapid() {
        return mapid;
    }

    public void setMapid(java.lang.Integer value ) {
        mapid = value;
    }

    public java.lang.String getMapname() {
        return mapname;
    }

    public void setMapname(java.lang.String value ) {
        mapname = value;
    }

    public com.inga.utils.Point getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(com.inga.utils.Point value ) {
        leftBottom = value;
    }

    public com.inga.utils.Point getRightTop() {
        return rightTop;
    }

    public void setRightTop(com.inga.utils.Point value ) {
        rightTop = value;
    }

    public java.lang.String getBackmap() {
        return backmap;
    }

    public void setBackmap(java.lang.String value ) {
        backmap = value;
    }

    public java.lang.Integer getZoom() {
        return zoom;
    }

    public void setZoom(java.lang.Integer value ) {
        zoom = value;
    }

    public com.inga.utils.Point getCenter() {
        return center;
    }

    public void setCenter(com.inga.utils.Point value ) {
        center = value;
    }

    public java.lang.Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(java.lang.Integer value ) {
        projectId = value;
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

    public java.lang.String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(java.lang.String value ) {
        subtitle = value;
    }

    public java.lang.Integer getHotspotsMode() {
        return hotspotsMode;
    }

    public void setHotspotsMode(java.lang.Integer value ) {
        hotspotsMode = value;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String value ) {
        description = value;
    }

    public java.lang.String getDisKs() {
        return disKs;
    }

    public void setDisKs(java.lang.String value ) {
        disKs = value;
    }

    public java.lang.Integer getIconsMode() {
        return iconsMode;
    }

    public void setIconsMode(java.lang.Integer value ) {
        iconsMode = value;
    }

    public java.lang.Integer getPrivacy() {
        return privacy;
    }

    public void setPrivacy(java.lang.Integer value ) {
        privacy = value;
    }

    public java.lang.Integer getHotspotsEditable() {
        return hotspotsEditable;
    }

    public void setHotspotsEditable(java.lang.Integer value ) {
        hotspotsEditable = value;
    }

    public java.lang.Integer getMediaEditable() {
        return mediaEditable;
    }

    public void setMediaEditable(java.lang.Integer value ) {
        mediaEditable = value;
    }

    public java.lang.String getCatsEnabled() {
        return catsEnabled;
    }

    public void setCatsEnabled(java.lang.String value ) {
        catsEnabled = value;
    }

    public java.lang.Integer getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(java.lang.Integer value ) {
        displayMode = value;
    }

}