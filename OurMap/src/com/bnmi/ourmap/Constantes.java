/*******************************************************************************

com.bnmi.ourmap.Constantes.java
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

package com.bnmi.ourmap;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class Constantes {

    public static final int TEMP = 0;
    public static final int AWAIT_FOR_APPROVAL = 1;
    public static final int ACTIVE = 2;

    // Object types
    public static final int IMAGE = 1;
    public static final int AUDIO = 2;
    public static final int VIDEO = 3;
    public static final int ICON = 4;
    public static final int TEXT = 5;

    //Element types
    public static final int MAP = 1;
    public static final int HOTSPOT = 2;
    public static final int OBJECT = 3;
    public static final int PROJECT = 4;

    // Privacy Settrings
    public static final int PRIVATE = 0;
    public static final int SECURE = 1 ;
    public static final int PUBLIC = 2 ;

    // Add hostpots options
    public static final int ADD_HS_OWNERS_ONLY = 0;
    public static final int ADD_HS_OWNERS_MEMBERS_ONLY = 1;
    public static final int ADD_HS_ALL = 2;

    // Edit media options
    public static final int EDIT_MEDIA_HS_OWNER_ONLY = 0;
    public static final int EDIT_MEDIA_HS_MAP_MEMBERS_ONLY = 1;
    public static final int EDIT_MEDIA_HS_ALL = 2;
    public static final int EDIT_MEDIA_HS_MAP_OWNERS_ONLY = 3;

    // Keywords mode (hotspots_mode)
    public static final int PRESET = 1;
    public static final int USER_DEFINED = 2;
    public static final int NONE = 3;

    public static final int DM_SCROLLING_PAGE = 2;
    public static final int DM_SIMPLE_SLIDESHOW = 3;
    public static final int DM_SLIDESHOW_CONTENT = 1 ;
    public static final int DM_FLEXIBLE = 0;
    public static final int MAP_HS_CONTENT_FLEXIBLE = 0 ;
    public static final int MAP_HS_CONTENT_DEFAULT = 2;
    public static final String VISITOR_ID = "100000099";
    public static final String VERSION = "1.4" ;

}
