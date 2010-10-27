/*******************************************************************************

com.bnmi.ourmap.web.actions.ListHotspotsForm.java
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

import com.bnmi.ourmap.model.CriteriosHotspot;
import com.inga.utils.Point;
import com.inga.utils.SigarUtils; 
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class ListHotspotsForm  extends ActionForm {

   private Double lon;
   private Double lat;
   private String hsName;
   private String hsDescription;
   private String hsCreator;
   private String[] hsLayer;
   private String general;
   private Integer mapid;
   


    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
       super.reset(mapping, request);

       lon = 0.0;
       lat = 0.0;
       hsName = null;
       hsDescription = null;
       hsCreator = null;
       setHsLayer(new String[0]);
       general = null;
       mapid = null;
       hsLayer = null;
       
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        super.validate(mapping, request);
        ActionErrors errors = new ActionErrors();
        hsDescription = SigarUtils.validarCadena( request.getParameter("hsDescription"));
        hsName = SigarUtils.validarCadena( request.getParameter("hsName"));
        lon = SigarUtils.parseDouble( request.getParameter("lon"));
        lat = SigarUtils.parseDouble( request.getParameter("lat"));
        hsCreator = SigarUtils.validarCadena( request.getParameter("creator"));
        general = SigarUtils.validarCadena( request.getParameter("general"));
        mapid = SigarUtils.parseInt( request.getParameter("mapid"));
        

        return errors;

    }

    public CriteriosHotspot getCriteriosHotspot() {

        CriteriosHotspot r = new CriteriosHotspot();

        ArrayList<Integer> layerList = null;
        if ( hsLayer != null )
        {
            if ( hsLayer.length > 0 )
            {
                layerList = new ArrayList<Integer>();
                for ( String lid : hsLayer )
                    layerList.add( SigarUtils.parseInt(lid) );
            }
        }

        r.setHsDescription(hsDescription);
        r.setHsLayerList( layerList );
        r.setHsName(hsName);
        Point pos = null;
        if ( lon != null && lat != null )
            pos = new Point(lon,lat);
        r.setHsPos( pos );
        r.setGeneral(general);
        r.setMapid(mapid);

        return r;
    }

    /**
     * @return the hsLayer
     */
    public String[] getHsLayer() {
        return hsLayer;
    }

    /**
     * @param hsLayer the hsLayer to set
     */
    public void setHsLayer(String[] hsLayer) {
        this.hsLayer = hsLayer;
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



}