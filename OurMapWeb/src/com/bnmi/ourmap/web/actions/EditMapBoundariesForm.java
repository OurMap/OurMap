/*******************************************************************************

com.bnmi.ourmap.web.actions.EditMapBoundariesForm.java
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
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.Point;
import com.inga.utils.SigarUtils;
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
public class EditMapBoundariesForm extends ActionForm {

    private Integer mapid;
    private String formSubmited;


   private String backmap;
   private Integer pid;
   private Integer zoom;
   private String bbox;
   private Point center;
   private Point leftBottom;
   private Point rightTop;
   private Integer hotspotsMode;
   private String disKs;
   private Integer baseMapOption;
   private Integer iconsMode;


    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        mapid = null;
        formSubmited = null;
    }
 
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute( Constantes.DELEGATE );

        mapid = SigarUtils.parseInt( request.getParameter("mapid"));
        if ( mapid == null )
            mapid = (Integer) request.getAttribute("mapid");

        formSubmited = SigarUtils.validarCadena(request.getParameter("formSubmited"));

        hotspotsMode = SigarUtils.parseInt( request.getParameter("hotspotsMode"));
        disKs = SigarUtils.validarCadena( request.getParameter("disKs"));
        baseMapOption = SigarUtils.parseInt( request.getParameter("baseMapOption"));
        iconsMode = SigarUtils.parseInt( request.getParameter("iconsMode"));
        if ( baseMapOption == null )
            if ( session.getAttribute(Constantes.BASE_OPTION_SELECTED) != null )
                baseMapOption = (Integer) session.getAttribute(Constantes.BASE_OPTION_SELECTED);
        if ( baseMapOption == null )
            baseMapOption = new Integer(0);
        session.setAttribute(Constantes.BASE_OPTION_SELECTED, baseMapOption );
        backmap = SigarUtils.validarCadena( request.getParameter("backmap"));

        bbox = SigarUtils.validarCadena( request.getParameter("bbox"));
        String centerStr = SigarUtils.validarCadena( request.getParameter("center") );
        if ( centerStr != null )
        {
            String centerPoint[] = centerStr.split(",");
            Double centerX = SigarUtils.parseDouble( centerPoint[0]);
            Double centerY = SigarUtils.parseDouble(centerPoint[1]);
            center = new Point( centerX, centerY );
        }
        if ( bbox != null )
        {

            bbox = SigarUtils.replace(bbox, "left-bottom=\\(", "" );
            bbox = SigarUtils.replace(bbox, "\\) right-top=\\(", "," );
            bbox = SigarUtils.replace(bbox, "\\)", "" );

            String bounds[] = bbox.split(",");
            double left = SigarUtils.parseDouble( bounds[0] );
            double bottom = SigarUtils.parseDouble( bounds[1] );
            double right = SigarUtils.parseDouble( bounds[2] );
            double top = SigarUtils.parseDouble( bounds[3] );
            leftBottom = new Point( left, bottom );
            rightTop = new Point( right, top );
        }

        zoom = SigarUtils.parseInt( request.getParameter("zoom") );


        try
        {
            Map m = del.getMap(mapid);
            Project p = del.getProject( m.getProjectId() );

            del.checkProjectConsistency(p);
            
            request.setAttribute("new_map", m );
            request.setAttribute("map", m );
            request.setAttribute("project", p );

        }
        catch ( Exception ex )
        {
            
        }

        if ( formSubmited == null )
        {
            errors.add("formSubmited", new ActionMessage("formSubmited"));
        }


        return errors;

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


    public Map getMap() {

        Map m = new Map();

        m.setMapid(mapid);
        m.setProjectId(pid);
        m.setLeftBottom( leftBottom );
        m.setRightTop( rightTop );
        m.setBackmap( backmap );
        m.setZoom( zoom );
        m.setCenter(center);
        m.setHotspotsMode(hotspotsMode);
        m.setDisKs( disKs );
        m.setIconsMode( iconsMode );



        return m;

    }

}
