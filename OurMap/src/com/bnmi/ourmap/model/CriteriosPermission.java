/*******************************************************************************

com.bnmi.ourmap.model.CriteriosPermission.java
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
 * CriteriosPermission.java
 *
 * Created on Thu Mar 04 11:32:13 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class CriteriosPermission implements java.io.Serializable {

   private java.lang.String userid;
   private java.lang.Integer elementId;
   private java.lang.Integer elementType;
   private java.lang.Integer ownership;
   private java.lang.Integer member;

    /** Creates a new instance of CriteriosPermission*/
    public CriteriosPermission() {
        userid = null;
        elementId = null;
        elementType = null;
        ownership = null;
        member = null;
    }

    @Override
    public String toString() {
    return 
        " userid=" + getUserid() + 
        " elementId=" + getElementId() + 
        " elementType=" + getElementType() + 
        " ownership=" + getOwnership() + 
        " member=" + getMember()
        ;
    }

    public String getCriteriaStr() {
        SqlClauseHelper sh = new SqlClauseHelper();

        if ( getUserid() != null )
            sh.append("&", "userid=" + String.valueOf(getUserid()) );
        if ( getElementId() != null )
            sh.append("&", "elementId=" + String.valueOf(getElementId()) );
        if ( getElementType() != null )
            sh.append("&", "elementType=" + String.valueOf(getElementType()) );
        if ( getOwnership() != null )
            sh.append("&", "ownership=" + String.valueOf(getOwnership()) );
        if ( getMember() != null )
            sh.append("&", "member=" + String.valueOf(getMember()) );

        String cadena = sh.toString();
        if ( cadena.length() >  0 )
            cadena = "&" + cadena;

        return cadena;
    }

    public java.lang.String getUserid() {
        return userid;
    }

    public void setUserid(java.lang.String value ) {
        userid = value;
    }

    public java.lang.Integer getElementId() {
        return elementId;
    }

    public void setElementId(java.lang.Integer value ) {
        elementId = value;
    }

    public java.lang.Integer getElementType() {
        return elementType;
    }

    public void setElementType(java.lang.Integer value ) {
        elementType = value;
    }

    public java.lang.Integer getOwnership() {
        return ownership;
    }

    public void setOwnership(java.lang.Integer value ) {
        ownership = value;
    }

    public java.lang.Integer getMember() {
        return member;
    }

    public void setMember(java.lang.Integer value ) {
        member = value;
    }

}