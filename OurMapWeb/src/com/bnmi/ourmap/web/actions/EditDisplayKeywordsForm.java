/*******************************************************************************

com.bnmi.ourmap.web.actions.EditDisplayKeywordsForm.java
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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class EditDisplayKeywordsForm extends ActionForm {


    
    private Integer mapid;
    private String dsKs;
    private Map m;
    private Project p;
    private List choices = null;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        mapid = null;
        dsKs = null;
        choices = new ArrayList();
        
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);


        Integer hotspotsMode = null;

        try
        {

            mapid = SigarUtils.parseInt( request.getParameter("mapid"));
            if ( mapid == null )
                mapid = (Integer) request.getAttribute("mapid");
            m = del.getMap(mapid);
            p = del.getProject(m.getProjectId());
            del.checkProjectConsistency( p );
            hotspotsMode = m.getHotspotsMode();
            request.setAttribute("mapid", mapid );
            request.setAttribute("map", m );
            request.setAttribute("project", p );

            choices.add(new LabelValueBean("Yes", "t"));
            choices.add(new LabelValueBean("No", "f"));

            request.setAttribute("choices", choices);


        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }

        String formSubmited = SigarUtils.validarCadena(request.getParameter("formSubmited"));

        // Keywords are disabled:
        if ( hotspotsMode != null && hotspotsMode.intValue() == 3 )
        {
            request.setAttribute("keywords_disabled", "true");
            return errors;
        }
        else
        {
            if ( formSubmited == null )
            {
                if ( m.getDisKs() != null && m.getDisKs().equalsIgnoreCase("t") )
                    dsKs = "t";
                else
                    dsKs = "f";
                errors.add("formSubmited", new ActionMessage("formSubmited"));
            }

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

    /**
     * @return the dsKs
     */
    public String getDsKs() {
        return dsKs;
    }

    /**
     * @param dsKs the dsKs to set
     */
    public void setDsKs(String dsKs) {
        this.dsKs = dsKs;
    }

    /**
     * @return the m
     */
    public Map getMap() {
        return m;
    }

    /**
     * @param m the m to set
     */
    public void setMap(Map m) {
        this.m = m;
    }

    /**
     * @return the p
     */
    public Project getProject() {
        return p;
    }

    /**
     * @param p the p to set
     */
    public void setProject(Project p) {
        this.p = p;
    }


}
