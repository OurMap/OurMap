/*******************************************************************************

com.bnmi.ourmap.web.actions.EditMapForm.java
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
import com.inga.utils.SigarUtils;
import javax.servlet.ServletRequest;
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
public class EditMapForm extends ActionForm {

    private String formSubmited;
    private Integer mapid;
    private String mapname;
    private String subtitle;
    private String description;
    private Integer hotspotsMode;
    private Map m;


    @Override
    public void reset(ActionMapping mapping, ServletRequest request) {
        super.reset(mapping, request);
        m = new Map();
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);



        ActionErrors errors = new ActionErrors();

        formSubmited = SigarUtils.validarCadena( request.getParameter("formSubmited"));
        hotspotsMode = SigarUtils.parseInt( request.getParameter("hotspotMode") );
        mapname = SigarUtils.validarCadena( request.getParameter("mapname"));
        mapid = SigarUtils.parseInt( request.getParameter("mapid"));
        if ( mapid == null )
            mapid = (Integer) request.getAttribute("mapid");
        request.setAttribute( "mapid", mapid );
        setSubtitle(SigarUtils.validarCadena(request.getParameter("subtitle")));
        description = SigarUtils.validarCadena( request.getParameter("description"));

        try
        {
            m = del.getMap( mapid );
            Project p = del.getProject( m.getProjectId() );

            request.setAttribute("map", m );
            request.setAttribute("project", p );

            if ( formSubmited == null )
            {
                mapname = m.getMapname();
                subtitle = m.getSubtitle();
                description = m.getDescription();

                errors.add( "formSubmited", new ActionMessage("formSubmited"));
                return errors;
            }
            else
            {
                if ( mapname == null )
                {
                    errors.add( "mapname", new ActionMessage("mapname.required"));
                    request.setAttribute("error-message", "Map Title is required");
                    return errors;
                }
            }

        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }

        return errors;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        formSubmited = null;

    }

    public Integer getMapid() {
        return mapid;
    }

    /**
     * @return the mapname
     */
    public String getMapname() {
        return mapname;
    }

    /**
     * @param mapname the mapname to set
     */
    public void setMapname(String mapname) {
        this.mapname = mapname;
    }

    /**
     * @return the subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * @param subtitle the subtitle to set
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * @return the hotspotsMode
     */
    public Integer getHotspotsMode() {
        return hotspotsMode;
    }

    /**
     * @param hotspotsMode the hotspotsMode to set
     */
    public void setHotspotsMode(Integer hotspotsMode) {
        this.hotspotsMode = hotspotsMode;
    }

    public Map getMap() {
        m = new Map();
        m.setMapid(mapid);
        m.setMapname(mapname);
        m.setSubtitle(subtitle);
        m.setDescription( description );
        m.setHotspotsMode( hotspotsMode );
        return m;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
