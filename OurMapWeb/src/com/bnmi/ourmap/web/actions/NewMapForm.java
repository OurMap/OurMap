/*******************************************************************************

com.bnmi.ourmap.web.actions.NewMapForm.java
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

import com.bnmi.ourmap.model.Category;
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.web.Constantes;
import com.bnmi.ourmap.web.Utils;
import com.inga.utils.Point;
import com.inga.utils.SigarUtils;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class NewMapForm extends ActionForm {


   private String mapname;
   private String backmap;
   private Integer pid;
   private Integer zoom;
   private String bbox;
   private Point center;
   private Point leftBottom;
   private Point rightTop;
   private String subtitle;
   private String description;
   private Integer hotspotsMode;
   private String disKs;
   private String status;
   private Integer baseMapOption;
   private Integer iconsMode;
   private String catsnow;
   private Integer displayMode;
   private String nav;


    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
       super.reset(mapping, request);

       mapname = null;
       backmap = null;
       pid = null;
       zoom = null;
       bbox = null;
       center = null;
       leftBottom = null;
       rightTop = null;
       subtitle = null;
       description = null;
       hotspotsMode = null;
       disKs = null;
       setStatus(null);
       baseMapOption = null;
       iconsMode = null;
       catsnow = null;
       nav = null;

    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        super.validate(mapping, request);
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();


        try
        {
            setStatus(SigarUtils.validarCadena(request.getParameter("status")));
            pid = SigarUtils.parseInt( request.getParameter("pid") );
            mapname = SigarUtils.validarCadena( request.getParameter("mapname"));
            subtitle = SigarUtils.validarCadena( request.getParameter("subtitle"));
            description = SigarUtils.validarCadena( request.getParameter("description"));
            description = Utils.escapeHtml(description);
            hotspotsMode = SigarUtils.parseInt( request.getParameter("hotspotsMode"));
            if ( hotspotsMode != null && hotspotsMode.intValue() == com.bnmi.ourmap.Constantes.NONE )
            {
                List<Keyword> keywords = new ArrayList<Keyword>();
                List<Category> cats = new ArrayList<Category>();
                session.setAttribute(Constantes.NEW_MAP_KEYWORDS, keywords );
                session.setAttribute(Constantes.NEW_MAP_CATEGORIES, cats );
                session.setAttribute("catsnow", "f" );
            }
            disKs = SigarUtils.validarCadena( request.getParameter("disKs"));
            
            //baseMapOption = SigarUtils.parseInt( request.getParameter("baseMapOption"));
            baseMapOption = 1 ;
            iconsMode = SigarUtils.parseInt( request.getParameter("iconsMode"));
            displayMode = SigarUtils.parseInt( request.getParameter("displayMode"));
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
            catsnow = SigarUtils.validarCadena( request.getParameter("catsnow"));

            
        }
        catch ( Exception ex )
        {
          ex.printStackTrace();
        }

        return errors;

    }


    public Map getMap() {

        Map m = new Map(); 

        m.setMapname(mapname);
        m.setProjectId(pid);
        m.setSubtitle(subtitle);
        m.setDescription( description );
        m.setLeftBottom( leftBottom );
        m.setRightTop( rightTop );
        m.setBackmap( backmap );
        m.setZoom( zoom );
        m.setCenter(center);
        m.setHotspotsMode(hotspotsMode);
        m.setDisKs( disKs );
        m.setIconsMode( iconsMode );
        m.setCatsEnabled( catsnow );
        m.setDisplayMode( displayMode );



        return m;

    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the baseMapOption
     */
    public Integer getBaseMapOption() {
        return baseMapOption;
    }

    /**
     * @param baseMapOption the baseMapOption to set
     */
    public void setBaseMapOption(Integer baseMapOption) {
        this.baseMapOption = baseMapOption;
    }

    /**
     * @return the nav
     */
    public String getNav() {
        return nav;
    }

    /**
     * @param nav the nav to set
     */
    public void setNav(String nav) {
        this.nav = nav;
    }

    /**
     * @return the pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }




}