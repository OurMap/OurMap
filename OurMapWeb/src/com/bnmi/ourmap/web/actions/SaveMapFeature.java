/*******************************************************************************

com.bnmi.ourmap.web.actions.SaveMapFeature.java
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
import com.bnmi.ourmap.exceptions.NoData;
import com.bnmi.ourmap.model.EasyObject;
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.HotspotObject;
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.SigarUtils;
import java.util.LinkedHashMap;
import java.util.Set;
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
public class SaveMapFeature extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        try
        {
            HttpSession session = request.getSession();
            EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);


            Hotspot h = (Hotspot) session.getAttribute("new_hotspot");
            h.setProximityRadius(10);

            Integer layerId = h.getHsLayer();
            Layer la = del.getLayer(layerId);
            Map map = del.getMap( la.getMapId() );
            Integer mapid = (Integer) session.getAttribute("new_hotspot_map");
            Integer hotspotsMode = map.getHotspotsMode();
            // Map is set to open keywords
            if ( hotspotsMode != null && hotspotsMode.intValue() == 2 )
            {
                String newKeyword = SigarUtils.validarCadena( (String) session.getAttribute("newkeyword") );
                session.removeAttribute("newkeyword");

                if ( newKeyword != null )
                {

                    Keyword newKey = new Keyword();
                    newKey.setMapId( map.getMapid() );
                    newKey.setKwValue(newKeyword);
                    newKey.setCatId( null );
                    Integer defaultIcon = del.getDefaultIconId();
                    newKey.setIconId( defaultIcon );
                    del.createKeyword(newKey);
                    h.setKeywordId( newKey.getKwId() );
                }


            }


            del.createHotspot(h);

            Integer mediaNow = (Integer) session.getAttribute("new_hotspot_media_now"  );
            if ( mediaNow == null )
                mediaNow = 0;

            assert( mediaNow != null );

            LinkedHashMap<Integer,EasyObject> objectsToUpload = (LinkedHashMap<Integer,EasyObject>) session.getAttribute("objects_to_upload");
            if ( objectsToUpload != null && ! objectsToUpload.isEmpty() )
            {
                Set<Integer> keys = objectsToUpload.keySet();
                HotspotObject ho;
                int index = 0;
                for ( Integer tempObjectId : keys )
                {
                    EasyObject tempObject = objectsToUpload.get(tempObjectId);

                    if ( mediaNow.intValue() == 1 )
                    {
                        Integer objectId = del.createObject(tempObject);
                        del.copyDataFromTemp(tempObjectId, objectId);
                        ho = new HotspotObject();
                        ho.setHotspot( h.getHsId() );
                        ho.setObjectId( objectId );
                        ho.setIndex(index);
                        ho.setBlock( 0 );
                        del.createHotspotObject(ho);
                    }
                    
                    del.deleteTemp(tempObjectId);

                    index++;
                }
            }

            if ( objectsToUpload != null )
                objectsToUpload.clear();

            session.removeAttribute("objects_to_upload");




            NewHotspotForm.resetHotspot(request);
            response.sendRedirect("viewmap.do?mapid=" + mapid + "&focusHs=" + h.getHsId() );

            return null;

        }
        catch ( Exception ex )
        {
            throw new NoData("Data not available. Please return to the map view screen for information about this hotspot");
        }
        
    }

    
}
