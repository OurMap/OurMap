/*******************************************************************************

com.bnmi.ourmap.web.actions.ListHotspots.java
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
import com.bnmi.ourmap.model.Category;
import com.bnmi.ourmap.model.CriteriosHotspot;
import com.bnmi.ourmap.model.CriteriosLayer;
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.SigarUtils;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
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
public class ListHotspots extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);
        ListHotspotsForm forma = (ListHotspotsForm) form;

        CriteriosHotspot criteria = forma.getCriteriosHotspot();

        if ( criteria.getMapid() != null )
        {
            CriteriosLayer crLa = new CriteriosLayer();
            crLa.setMapId( criteria.getMapid() );
            List<Layer> las = del.findLayers(crLa);
            List<Integer> mapLayers = new ArrayList<Integer>();
            for ( Layer la : las )
                mapLayers.add( la.getLayerId() );

            List<Integer> laIds = criteria.getHsLayerList();
            if ( laIds == null )
                laIds = new Vector<Integer>();
            laIds.addAll( mapLayers );
            criteria.setHsLayerList(laIds);
        }

        List<Hotspot> hotspots = del.findHotspots(criteria);

        Integer mapid = criteria.getMapid();
        if ( mapid == null )
        {
            if ( forma.getHsLayer() != null )
            {
                String ls[] = forma.getHsLayer();
                if ( ls.length > 0 )
                {
                    Layer l = del.getLayer( SigarUtils.parseInt(ls[0]));
                    mapid = l.getMapId();
                }
            }
        }

        if ( mapid != null )
        {

            Map map = del.getMap( mapid );
            Integer iconsMode = map.getIconsMode();
            if ( iconsMode != null )
            {
                switch ( iconsMode )
                {
                    // Layers
                    case 1:
                        Hashtable<Integer,Integer> iconsForLayers = new Hashtable<Integer,Integer>();

                        for ( Hotspot h : hotspots )
                        {
                            if ( iconsForLayers.get( h.getHsLayer() ) == null )
                            {
                                Layer l = del.getLayer( h.getHsLayer() );
                                iconsForLayers.put( l.getLayerId(), l.getIconId() );
                            }

                            h.setIconId( iconsForLayers.get( h.getHsLayer() ) );
                        }

                        break;
                    // Categories
                    case 2:
                        Hashtable<Integer,Integer> iconsForCats = new Hashtable<Integer,Integer>();
                        for ( Hotspot h: hotspots )
                        {

                            //if ( iconsForCats.get( h.getKeywordId()) == null )
                            {
                                Keyword k = del.getKeyword( h.getKeywordId() );
                                Category c = del.getCategory( k.getCatId() );
                                iconsForCats.put( k.getKwId(), c.getIconId() );
                            }

                            h.setIconId( iconsForCats.get(h.getKeywordId()));
                        }

                        break;
                    // Keywords
                    case 3:
                        Hashtable<Integer,Integer> iconsForKeys = new Hashtable<Integer,Integer>();
                        for ( Hotspot h : hotspots )
                        {
                            if ( iconsForKeys.get(h.getKeywordId()) == null )
                            {
                                Keyword k = del.getKeyword(h.getKeywordId());
                                iconsForKeys.put( k.getKwId(), k.getIconId() );
                            }

                            h.setIconId( iconsForKeys.get(h.getKeywordId()));
                        }

                        break;

                }
            }
        }


        request.setAttribute("hotspots", hotspots );
        
        return mapping.findForward("listhotspots_jsp");


    }


}
