/*******************************************************************************

com.bnmi.ourmap.web.actions.NewHotspotForm.java
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
import com.bnmi.ourmap.model.CriteriosKeyword;
import com.bnmi.ourmap.model.CriteriosLayer;
import com.bnmi.ourmap.model.CriteriosObject;
import com.bnmi.ourmap.model.EasyObject;
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.web.Constantes;
import com.bnmi.ourmap.web.Utils;
import com.inga.utils.Point;
import com.inga.utils.SigarUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class NewHotspotForm extends ActionForm {

   private Double lon;
   private Double lat;
   private String hsName;
   private String hsDescription;
   private Integer hsLayer;
   private String formSubmited;
   private Integer mapid;
   private Integer iconId;
   private Integer selectedKeyword;
   private HttpSession session;
   private String op;
   
   private Point currentCenter;
   private Integer currentZoom;
   private String newKeyword;
   private String actualValue ;
   private String comboValue ;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
       super.reset(mapping, request);
       session = request.getSession();

       lon = 0.0;
       lat = 0.0;
       hsName = null;
       hsDescription = null;
       hsLayer = null;
       formSubmited = null;
       iconId = null;
       selectedKeyword = null;
       setOp(null);
       newKeyword = null;
       actualValue = null;
       comboValue = null;
       
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        super.validate(mapping, request);
        ActionErrors errors = new ActionErrors();
        
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

        setNewHsStatus( request, 1 );

        formSubmited = SigarUtils.validarCadena( request.getParameter("formSubmited"));
        boolean boolFormSubmited = false;
        if ( formSubmited != null )
            boolFormSubmited = true;


        mapid = SigarUtils.parseInt(request.getParameter("mapid"));
        if ( mapid == null )
            mapid = (Integer) request.getAttribute("mapid");
        if ( mapid == null )
            mapid = (Integer) session.getAttribute("new_hotspot_map");

        if ( mapid != null )
        {
            request.setAttribute("mapid", mapid );
            session.setAttribute("new_hotspot_map", mapid );
        }
        
        try
        {
            Map map = del.getMap(mapid);
            Project p = del.getProject( map.getProjectId() );

            lon = SigarUtils.parseDouble( request.getParameter("lon"));
            lat = SigarUtils.parseDouble( request.getParameter("lat"));
            hsDescription = SigarUtils.validarCadena( request.getParameter("hsDescription"));
            hsLayer = SigarUtils.parseInt( request.getParameter("hsLayer"));
            hsName = SigarUtils.validarCadena( request.getParameter("hsName"));
            iconId = SigarUtils.parseInt( request.getParameter("iconId"));
            selectedKeyword = SigarUtils.parseInt( request.getParameter("selectedKeyword"));
            setOp(SigarUtils.validarCadena(request.getParameter("op")));
            newKeyword = SigarUtils.validarCadena( request.getParameter("newKeyword"));
            actualValue = SigarUtils.validarCadena( request.getParameter("actualValue"));
            comboValue = SigarUtils.validarCadena( request.getParameter("comboValue"));

            hsDescription = Utils.escapeHtml(hsDescription);
            hsName = Utils.escapeHtml(hsName);

           session.setAttribute("newkeyword", newKeyword );

            Double currentLon = SigarUtils.parseDouble( request.getParameter("currentLon"));
            Double currentLat = SigarUtils.parseDouble( request.getParameter("currentLat"));
            if ( currentLon != null && currentLat != null )
            {
                currentCenter = new Point( currentLon, currentLat );
                session.setAttribute( "currentCenter", currentCenter );
            }
            currentZoom = SigarUtils.parseInt( request.getParameter("currentZoom"));
            if ( currentZoom != null )
                session.setAttribute( "currentZoom", currentZoom );


            if ( getOp() != null )
            {
                if ( op.equalsIgnoreCase("step0"))
                {
                    NewHotspotForm.resetHotspot(request);
                }

                if ( op.equalsIgnoreCase("cancel") ||
                     op.equalsIgnoreCase("back")   ||
                     op.equalsIgnoreCase("reset")
                   )
                    return errors;
            }


            if ( lon != null )
                request.setAttribute("lon", lon );
            if ( lat != null )
                request.setAttribute("lat", lat );

            updateHotspot( request, boolFormSubmited );

            Hotspot hs = (Hotspot) session.getAttribute("new_hotspot");

        




            CriteriosLayer cLa = new CriteriosLayer();
            cLa.setMapId(mapid);
            List<Layer> layers = del.findLayers(cLa);
            request.setAttribute("layers", layers );
            if ( hs.getHsLayer() != null )
                request.setAttribute("selectedLayer", hs.getHsLayer() );

            request.setAttribute("map", map );
            request.setAttribute("project", p );


            if ( map.getHotspotsMode() != null && map.getHotspotsMode().intValue() < 3 )
            {
                CriteriosKeyword cri = new CriteriosKeyword();
                cri.setMapId(mapid);
                List<Keyword> keywords = del.findKeywords( cri );
                request.setAttribute("keywords", keywords );
                Integer secKey = (Integer) session.getAttribute("selectedKeyword");
                if ( secKey != null )
                    request.setAttribute("selectedKeyword", secKey );

            }
            if ( map.getIconsMode() != null && map.getIconsMode().intValue() == 4 )
            {
                CriteriosObject cr2 = new CriteriosObject();
                cr2.setObjType( com.bnmi.ourmap.Constantes.ICON );
                List<EasyObject> icons = del.findObjects(cr2);
                request.setAttribute("icons", icons );

            }

            if ( formSubmited == null )
            {
                request.setAttribute("estado","input");
                errors.add("formSubmited", new ActionMessage("formSubmited"));

                return errors;
            }
            else
            {

                if ( hsName == null )
                {
                    errors.add("hsName", new ActionMessage("hsname.required") );
                    hs.setHsName( null );
                    request.setAttribute("hsName.required", "Enter a name");
                    return errors;
                }


                // If keywords are set to open, check that the user entered a new keyword, or an id for an
                // existing keyword
                if ( map.getHotspotsMode().intValue() == 2 )
                {
                    if ( newKeyword == null )
                    {
                        errors.add("newKeyword", new ActionMessage("hskeyword.required"));
                        request.setAttribute("hsKeyword.required", "You must set a keyword for your hotspot");
                        return errors;
                    }
                }

                if ( map.getIconsMode().intValue() == 4 )
                {
                    if ( hs.getIconId() == null )
                    {
                        errors.add("iconId", new ActionMessage("iconid.required") );
                        request.setAttribute("iconId.required", "Select an icon");
                        return errors;
                    }
                }

            }

        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }

        return errors;

    }

    public Hotspot getHotspot() {
        Hotspot h = (Hotspot) session.getAttribute("new_hotspot");
        return h;
    }

    public void updateHotspot(HttpServletRequest request, boolean boolFormSubmited ) {

        Hotspot h = (Hotspot) session.getAttribute("new_hotspot");
        if ( h == null )
            resetHotspot(request);
        h = (Hotspot) session.getAttribute("new_hotspot");

        if ( hsLayer != null )
            h.setHsLayer( hsLayer );
        if ( hsName != null )
            h.setHsName(hsName);
        if ( hsDescription != null )
            h.setHsDescription( hsDescription );
        if ( lon != null && lat != null )
            h.setHsPos( new Point(lon,lat));
        if ( iconId != null )
            h.setIconId(iconId);

        if ( boolFormSubmited )
        {
            session.setAttribute("selectedKeyword", selectedKeyword );
            h.setKeywordId( selectedKeyword );
        }


    }

    public static void setNewHsStatus( HttpServletRequest request, int status ) {
        System.out.println("setting new hs status");
        HttpSession session = request.getSession();
        Integer furthestNewHsStatus = (Integer) session.getAttribute(Constantes.FURTHEST_NEWHS_STATUS);
        if ( furthestNewHsStatus == null )
            furthestNewHsStatus = new Integer(0);
        if ( status > furthestNewHsStatus.intValue() )
            furthestNewHsStatus = new Integer(status);
        assert( furthestNewHsStatus != null );
        session.setAttribute( Constantes.FURTHEST_NEWHS_STATUS, furthestNewHsStatus );
        session.setAttribute(Constantes.NEWHS_STATUS, new Integer(status) );
        System.out.println("Furthest: " + session.getAttribute(Constantes.FURTHEST_NEW_MAP_STATUS));

    }

    public static void resetHotspot(HttpServletRequest request ) {

        Hotspot h = new Hotspot();
        HttpSession session = request.getSession();
        Integer mediaNow = 1;

        session.setAttribute("new_hotspot_media_now", mediaNow );


        session.setAttribute("new_hotspot", h );
        // Id for the selected keyword
        session.removeAttribute("new_hotspot_keyword");
        // Id for the map
        session.removeAttribute("new_hotspot_map");

        session.removeAttribute("new_hs_blocks");

        session.removeAttribute("newkeyword");

        session.removeAttribute(Constantes.FURTHEST_NEWHS_STATUS);
        session.removeAttribute(Constantes.NEWHS_STATUS);

        setNewHsStatus(request, 1);
        
    }

    /**
     * @return the op
     */
    public String getOp() {
        return op;
    }

    /**
     * @param op the op to set
     */
    public void setOp(String op) {
        this.op = op;
    }

    /**
     * @return the mapid
     */
    public Integer getMapid() {
        return mapid;
    }

    /**
     * @param mapid the mapid to set
     */
    public void setMapid(Integer mapid) {
        this.mapid = mapid;
    }

    /**
     * @return the newKeyword
     */
    public String getNewKeyword() {
        return newKeyword;
    }

    /**
     * @param newKeyword the newKeyword to set
     */
    public void setNewKeyword(String newKeyword) {
        this.newKeyword = newKeyword;
    }

    /**
     * @return the comboValue
     */
    public String getComboValue() {
        return comboValue;
    }

    /**
     * @param comboValue the comboValue to set
     */
    public void setComboValue(String comboValue) {
        this.comboValue = comboValue;
    }



}