/*******************************************************************************

com.bnmi.ourmap.model.HotspotObject.java
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

/*
 * HotspotObject.java
 *
 * Created on Thu Feb 11 14:10:18 COT 2010
 * by DaoGen2
 * Author: Camilo Cuesta
 *
 */
@SuppressWarnings("serial")
public class HotspotObject implements java.io.Serializable {

   private java.lang.Integer hotspot;
   private java.lang.Integer objectId;
   private java.lang.Integer index;
   private java.lang.Integer block;

    /** Creates a new instance of HotspotObject*/
    public HotspotObject() {
        hotspot = null;
        objectId = null;
        index = null;
        block = null;
    }

    @Override
    public String toString() {
    return 
        " hotspot=" + getHotspot() + 
        " objectId=" + getObjectId() + 
        " index=" + getIndex() + 
        " block=" + getBlock()
        ;
    }

    public java.lang.Integer getHotspot() {
        return hotspot;
    }

    public void setHotspot(java.lang.Integer value ) {
        hotspot = value;
    }

    public java.lang.Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(java.lang.Integer value ) {
        objectId = value;
    }

    public java.lang.Integer getIndex() {
        return index;
    }

    public void setIndex(java.lang.Integer value ) {
        index = value;
    }

    public java.lang.Integer getBlock() {
        return block;
    }

    public void setBlock(java.lang.Integer value ) {
        block = value;
    }

}