/*******************************************************************************

com.bnmi.ourmap.model.CriteriosAction.java
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
 * CriteriosAction.java
 *
 * Created on Tue Dec 08 19:36:39 COT 2009
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class CriteriosAction implements java.io.Serializable {

   private java.lang.String aid;
   private java.lang.String label;
   private java.lang.String descr;

    /** Creates a new instance of CriteriosAction*/
    public CriteriosAction() {
        aid = null;
        label = null;
        descr = null;
    }

    @Override
    public String toString() {
    return 
        " aid=" + getAid() + 
        " label=" + getLabel() + 
        " descr=" + getDescr()
        ;
    }

    public String getCriteriaStr() {
        SqlClauseHelper sh = new SqlClauseHelper();

        if ( getAid() != null )
            sh.append("&", "aid=" + String.valueOf(getAid()) );
        if ( getLabel() != null )
            sh.append("&", "label=" + String.valueOf(getLabel()) );
        if ( getDescr() != null )
            sh.append("&", "descr=" + String.valueOf(getDescr()) );

        String cadena = sh.toString();
        if ( cadena.length() >  0 )
            cadena = "&" + cadena;

        return cadena;
    }

    public java.lang.String getAid() {
        return aid;
    }

    public void setAid(java.lang.String value ) {
        aid = value;
    }

    public java.lang.String getLabel() {
        return label;
    }

    public void setLabel(java.lang.String value ) {
        label = value;
    }

    public java.lang.String getDescr() {
        return descr;
    }

    public void setDescr(java.lang.String value ) {
        descr = value;
    }

}