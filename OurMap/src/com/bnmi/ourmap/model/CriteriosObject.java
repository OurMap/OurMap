/*******************************************************************************

com.bnmi.ourmap.model.CriteriosObject.java
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
 * CriteriosObject.java
 *
 * Created on Mon Jan 04 18:22:56 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class CriteriosObject implements java.io.Serializable {

   private java.lang.Integer objectId;
   private java.lang.String objName;
   private java.lang.String objDescription;
   private java.lang.Integer objType;
   private java.lang.String extension;
   private java.lang.Integer objSize;
   private java.lang.String createdBy;
   private java.lang.String modifiedBy;
   private DateRange created;
   private DateRange modified;
   private Integer status;
   private Integer category;

    /** Creates a new instance of CriteriosObject*/
    public CriteriosObject() {
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
        status = null;
        category = null;
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
        " status=" + getStatus() +
        " category=" + getCategory()
        ;
    }

    public String getCriteriaStr() {
        SqlClauseHelper sh = new SqlClauseHelper();

        if ( getObjectId() != null )
            sh.append("&", "objectId=" + String.valueOf(getObjectId()) );
        if ( getObjName() != null )
            sh.append("&", "objName=" + String.valueOf(getObjName()) );
        if ( getObjDescription() != null )
            sh.append("&", "objDescription=" + String.valueOf(getObjDescription()) );
        if ( getObjType() != null )
            sh.append("&", "objType=" + String.valueOf(getObjType()) );
        if ( getExtension() != null )
            sh.append("&", "extension=" + String.valueOf(getExtension()) );
        if ( getObjSize() != null )
            sh.append("&", "objSize=" + String.valueOf(getObjSize()) );
        if ( getCreatedBy() != null )
            sh.append("&", "createdBy=" + String.valueOf(getCreatedBy()) );
        if ( getModifiedBy() != null )
            sh.append("&", "modifiedBy=" + String.valueOf(getModifiedBy()) );
        if ( getCreated() != null )
            sh.append("&", "created=" + String.valueOf(getCreated()) );
        if ( getModified() != null )
            sh.append("&", "modified=" + String.valueOf(getModified()) );

        String cadena = sh.toString();
        if ( cadena.length() >  0 )
            cadena = "&" + cadena;

        return cadena;
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
        objName = value;
    }

    public java.lang.String getObjDescription() {
        return objDescription;
    }

    public void setObjDescription(java.lang.String value ) {
        objDescription = value;
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
        extension = value;
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
        createdBy = value;
    }

    public java.lang.String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(java.lang.String value ) {
        modifiedBy = value;
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



}