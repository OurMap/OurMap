/*******************************************************************************

com.bnmi.ourmap.model.CriteriosIcon.java
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
import com.inga.utils.SqlClauseHelper;

/*
 * CriteriosIcon.java
 *
 * Created on Wed Jan 27 14:28:05 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class CriteriosIcon implements java.io.Serializable {

   private java.lang.Integer iconId;
   private java.lang.String filename;
   private java.lang.Integer iconType;
   private java.lang.Integer iconData;

    /** Creates a new instance of CriteriosIcon*/
    public CriteriosIcon() {
        iconId = null;
        filename = null;
        iconType = null;
        iconData = null;
    }

    @Override
    public String toString() {
    return 
        " iconId=" + getIconId() + 
        " filename=" + getFilename() + 
        " iconType=" + getIconType() + 
        " iconData=" + getIconData()
        ;
    }

    public String getCriteriaStr() {
        SqlClauseHelper sh = new SqlClauseHelper();

        if ( getIconId() != null )
            sh.append("&", "iconId=" + String.valueOf(getIconId()) );
        if ( getFilename() != null )
            sh.append("&", "filename=" + String.valueOf(getFilename()) );
        if ( getIconType() != null )
            sh.append("&", "iconType=" + String.valueOf(getIconType()) );
        if ( getIconData() != null )
            sh.append("&", "iconData=" + String.valueOf(getIconData()) );

        String cadena = sh.toString();
        if ( cadena.length() >  0 )
            cadena = "&" + cadena;

        return cadena;
    }

    public java.lang.Integer getIconId() {
        return iconId;
    }

    public void setIconId(java.lang.Integer value ) {
        iconId = value;
    }

    public java.lang.String getFilename() {
        return filename;
    }

    public void setFilename(java.lang.String value ) {
        filename = value;
    }

    public java.lang.Integer getIconType() {
        return iconType;
    }

    public void setIconType(java.lang.Integer value ) {
        iconType = value;
    }

    public java.lang.Integer getIconData() {
        return iconData;
    }

    public void setIconData(java.lang.Integer value ) {
        iconData = value;
    }

}