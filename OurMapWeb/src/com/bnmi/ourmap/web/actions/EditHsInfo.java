/*******************************************************************************

com.bnmi.ourmap.web.actions.EditHsInfo.java
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
import com.bnmi.ourmap.exceptions.SecurityIssue;
import com.bnmi.ourmap.model.Category;
import com.bnmi.ourmap.model.CriteriosKeyword;
import com.bnmi.ourmap.model.CriteriosLayer;
import com.bnmi.ourmap.model.CriteriosObject;
import com.bnmi.ourmap.model.EasyObject;
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.model.User;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.Point;
import com.inga.utils.ReturnMessage;
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
public class EditHsInfo extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

        Integer hsId = SigarUtils.parseInt( request.getParameter("hsId") );

        User principal = (User) session.getAttribute(Constantes.USER);
        ReturnMessage perm = del.hasPermission( principal, "creator-owner", hsId, com.bnmi.ourmap.Constantes.HOTSPOT );
        if ( ! perm.isSuccess() )
            throw new SecurityIssue( perm.getMessage() );


        String formSubmited = SigarUtils.validarCadena( request.getParameter("formSubmited") );
        Hotspot hs = del.getHotspot(hsId);
        String creatorName = "";
        try
        {
            User creator = del.getUser(hs.getCreatedBy());
            creatorName = creator.getNombre();
        }
        catch ( Exception ex )
        {

        }
        Layer la = del.getLayer(hs.getHsLayer());
        Integer mapid = la.getMapId();
        Map map = del.getMap( mapid );
        Project p = del.getProject( map.getProjectId() );
        Point leftBottom = map.getLeftBottom();
        Point rightTop   = map.getRightTop();

        CriteriosLayer criteriaLayer = new CriteriosLayer();
        criteriaLayer.setMapId(mapid);
        List<Layer> layers = del.findLayers(criteriaLayer);

        CriteriosObject criteria = new CriteriosObject();
        criteria.setObjType( com.bnmi.ourmap.Constantes.ICON );
        List<EasyObject> icons = del.findObjects( criteria );


        request.setAttribute("hs", hs);
        request.setAttribute("creatorName", creatorName );
        request.setAttribute("layer", la );
        request.setAttribute("mapid" , mapid );
        request.setAttribute("map", map );
        request.setAttribute("project", p );
        request.setAttribute("layers" , layers );
        request.setAttribute("selectedLayer", String.valueOf(la.getLayerId()) );
        request.setAttribute("icons", icons );
        request.setAttribute("leftBottom", leftBottom );
        request.setAttribute("rightTop", rightTop );

        Integer iconsMode = map.getIconsMode();
        Keyword k = null;
        Category c = null;

        if ( iconsMode != null )
        {
            switch ( iconsMode )
            {
                // Layers
                case 1:
                    hs.setIconId( la.getIconId() );
                    break;
                // Categories
                case 2:
                    k = del.getKeyword( hs.getKeywordId() );
                    c = del.getCategory( k.getCatId() );
                    hs.setIconId( c.getIconId() );
                    break;
                // Keywords
                case 3:
                    k = del.getKeyword( hs.getKeywordId() );
                    hs.setIconId( k.getIconId() );
                    break;
            }
        }

        try
        {
            k = del.getKeyword( hs.getKeywordId() );
        }
        catch ( Exception ex )
        {

        }

        try
        {
            c = del.getCategory( k.getCatId() );
        }
        catch ( Exception ex )
        {
            
        }


        if ( k != null )
            request.setAttribute( "keyword", k );
        if ( c != null )
            request.setAttribute( "category", c );


        Integer hotspotsMode = map.getHotspotsMode();
        switch ( hotspotsMode )
        {
            case 1:
                CriteriosKeyword criteriaKeys = new CriteriosKeyword();
                criteriaKeys.setMapId(mapid);
                List<Keyword> keys = del.findKeywords(criteriaKeys);
                request.setAttribute("keys", keys );
                request.setAttribute("selectedKey", String.valueOf(hs.getKeywordId()) );
                break;
            case 2:
                break;
            case 3:
                break;
        }

        if ( formSubmited == null )
            return mapping.findForward("edithsinfo");
        
        String title  = SigarUtils.validarCadena( request.getParameter("title") );
        String lonStr = SigarUtils.validarCadena( request.getParameter("lon") );
        String latStr = SigarUtils.validarCadena( request.getParameter("lat") );
        Integer kwId = SigarUtils.parseInt( request.getParameter("kwId") );
        String kwValue = SigarUtils.validarCadena( request.getParameter("kwValue"));
        Integer proximityRadius = SigarUtils.parseInt( request.getParameter("proximityRadius"));
        Integer layerId = SigarUtils.parseInt( request.getParameter("layerId") );
        Integer iconId= SigarUtils.parseInt( request.getParameter("iconId"));


        Hotspot updateHs = new Hotspot();
        updateHs.setHsId( hsId );

        if ( map.getIconsMode().intValue() == 4 )
        {
            if ( iconId != null )
                updateHs.setIconId(iconId);
        }

        if ( map.getHotspotsMode() == 2 && kwValue != null )
        {
            Integer theKeyId = hs.getKeywordId();
            Keyword theKey = del.getKeyword(theKeyId);
            theKey.setKwValue(kwValue);
            del.updateKeyword(theKey);
        }


        if ( title != null )
            updateHs.setHsName(title);

        Point position = hs.getHsPos();
        try
        {
            Double lon = Double.parseDouble(lonStr);
            Double lat = Double.parseDouble(latStr);
            position.setX(lon);
            position.setY(lat);
            updateHs.setHsPos(position);
        }
        catch ( Exception ex )
        {
            // niente
        }

        if ( proximityRadius != null )
            updateHs.setProximityRadius(proximityRadius);

        if ( layerId != null )
            updateHs.setHsLayer(layerId);

        if ( kwId != null )
            updateHs.setKeywordId(kwId);

        del.updateHotspot( updateHs );

        response.sendRedirect( "viewmap.do?mapid=" + mapid + "&focusHs=" + hsId ) ;

        return null;

        
    }


}
