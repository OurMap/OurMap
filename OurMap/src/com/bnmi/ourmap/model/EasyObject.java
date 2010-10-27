/*******************************************************************************

com.bnmi.ourmap.model.EasyObject.java
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

import com.bnmi.ourmap.Constantes;
import com.inga.utils.Point;
import com.inga.utils.SigarUtils;

/*
 * EasyObject.java
 *
 * Created on Mon Jan 04 18:22:56 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class EasyObject implements java.io.Serializable {

   private java.lang.Integer objectId;
   private java.lang.String objName;
   private java.lang.String objDescription;
   private java.lang.Integer objType;
   private java.lang.String extension;
   private java.lang.Integer objSize;
   private java.lang.String createdBy;
   private java.lang.String modifiedBy;
   private java.sql.Timestamp created;
   private java.sql.Timestamp modified;
   private byte[] objData;
   private byte[] objData2;
   private Integer status;
   private String label;
   private Point dimensions;
   private Integer category;
   private String creatorName;
   private String path;

    /** Creates a new instance of EasyObject*/
    public EasyObject() {
        objectId = null;
        objName = null;
        objDescription = null;
        objType = null;
        extension = null;
        objSize = null;
        createdBy = null;
        modifiedBy = null;
        created = null;
        modified = null;
        objData = null;
        status = null;
        objData2 = null;
        label = null;
        dimensions = null;
        category = null;
        creatorName = null;
        path = null;

    }

    @Override
    public String toString() {
    return 
        " objectId=" + getObjectId() + 
        " objName=" + getObjName() + 
        " objDescription=" + getObjDescription() + 
        " objType=" + getObjType() + 
        " extension=" + getExtension() + 
        " objSize=" + getObjSize() + 
        " createdBy=" + getCreatedBy() + 
        " modifiedBy=" + getModifiedBy() + 
        " created=" + getCreated() + 
        " modified=" + getModified() + 
        " objData=" + getObjData() +
        " status=" + getStatus() +
        " label= " + getLabel() +
        " dimensions=" + getDimensions() +
        " category=" + getCategory()
        ;
    }

    public java.lang.Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(java.lang.Integer value ) {
        objectId = value;
    }

    public java.lang.String getObjName() {
        return objName;
    }

    public void setObjName(java.lang.String value ) {
        objName = SigarUtils.validarCadena(value);
    }

    public java.lang.String getObjDescription() {
        return objDescription;
    }

    public java.lang.String getShortDescription() {
        if ( objDescription == null )
            return "";
        if ( objDescription.length() < 40 )
            return objDescription;
        else
            return objDescription.substring(0,40) + "...";
    }


    public void setObjDescription(java.lang.String value ) {
        objDescription = value ;
    }

    public java.lang.Integer getObjType() {
        return objType;
    }

    public void setObjType(java.lang.Integer value ) {
        objType = value;
    }

    public java.lang.String getExtension() {
        return extension;
    }

    public void setExtension(java.lang.String value ) {
        extension = SigarUtils.validarCadena(value);
    }

    public java.lang.Integer getObjSize() {
        return objSize;
    }

    public void setObjSize(java.lang.Integer value ) {
        objSize = value;
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

    public byte[] getObjData() {
        return objData;
    }

    public void setObjData(byte[] value ) {
        objData = value;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        if ( objType == null )
            return "";
        else if ( objType.intValue() == Constantes.IMAGE )
            return "Image";
        else if ( objType.intValue() == Constantes.AUDIO )
            return "Audio";
        else if ( objType.intValue() == Constantes.VIDEO )
            return "Video";
        else if ( objType.intValue() == Constantes.ICON )
            return "Icon";
        else if ( objType.intValue() == Constantes.TEXT )
            return "Text";

        else
            return "";

    }

    /**
     * @return the objData2
     */
    public byte[] getObjData2() {
        return objData2;
    }

    /**
     * @param objData2 the objData2 to set
     */
    public void setObjData2(byte[] objData2) {
        this.objData2 = objData2;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the dimensions
     */
    public Point getDimensions() {
        return dimensions;
    }

    /**
     * @param dimensions the dimensions to set
     */
    public void setDimensions(Point dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * @return the category
     */
    public Integer getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Integer category) {
        this.category = category;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
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