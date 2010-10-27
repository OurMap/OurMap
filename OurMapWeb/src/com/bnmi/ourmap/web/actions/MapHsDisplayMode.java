/*******************************************************************************

com.bnmi.ourmap.web.actions.MapHsDisplayMode.java
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

package com.bnmi.ourmap.web.actions;

import com.bnmi.ourmap.control.EasyDelegate;
import com.bnmi.ourmap.model.CriteriosHotspot;
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.SigarUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class MapHsDisplayMode extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

        String formSubmited = SigarUtils.validarCadena(request.getParameter("formSubmited"));
        Integer mapid = SigarUtils.parseInt( request.getParameter("mapid"));
        Map m = del.getMap(mapid);
        Integer oldDisplayMode = m.getDisplayMode();
        Project p = del.getProject( m.getProjectId() );
        request.setAttribute("mapid", mapid );
        request.setAttribute("map", m );
        request.setAttribute("project", p );

        if ( formSubmited == null )
        {
            return mapping.findForward("maphsdisplaymode");
        }
        else
        {
            Integer displayMode = SigarUtils.parseInt( request.getParameter("displayMode"));
            Map updatedMap = new Map();
            updatedMap.setMapid( m.getMapid() );
            updatedMap.setDisplayMode( displayMode );
            del.updateMap(updatedMap);

            if ( oldDisplayMode != null && displayMode != null )
            {
                /*
                 * Another issue that this situation raises is: what happens if
                 * owners initially set the configuration of the display option
                 * to a firm setting, but then switch it to "open ended". If
                 * this happens, then the editing option should then appear for
                 * HS editors, and the option to choose should appear during HS
                 * creation. However, if possible all pre-existing Hotspots
                 * (that never had an option chosen by their individual
                 * creators) should be set by default to the setting they
                 * originally had (when the map was locked to one choice).
                 */
                if (    oldDisplayMode.intValue() != com.bnmi.ourmap.Constantes.MAP_HS_CONTENT_FLEXIBLE
                     && displayMode.intValue()    == com.bnmi.ourmap.Constantes.MAP_HS_CONTENT_FLEXIBLE     )
                {
                    CriteriosHotspot findMapHotspots = new CriteriosHotspot();
                    List<Hotspot> mapHotspots = del.findHotspots( findMapHotspots );
                    for ( Hotspot hs : mapHotspots )
                    {
                        Hotspot updatedHs = new Hotspot();
                        updatedHs.setHsId( hs.getHsId() );
                        updatedHs.setDisplayMode( oldDisplayMode );
                        del.updateHotspot( updatedHs );
                    }
                }
            }


            response.sendRedirect("mapconfiguration.do?mapid=" + mapid );
            return null;
        }

    }


}
