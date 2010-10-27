/*******************************************************************************

com.bnmi.ourmap.web.actions.EditHsForm.java
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
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.web.Constantes;
import com.bnmi.ourmap.web.Utils;
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
public class EditHsForm extends ActionForm {

    private Hotspot hs;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        try
        {
            HttpSession session = request.getSession();
            EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

            Integer hsId = SigarUtils.parseInt( request.getParameter("hsId"));
            hs = del.getHotspot(hsId);
            Layer la = del.getLayer(hs.getHsLayer());
            Map map = del.getMap(la.getMapId());
            Project p = del.getProject( map.getProjectId());

            del.checkProjectConsistency(p);

            request.setAttribute("hs", hs );
            request.setAttribute("mapid", la.getMapId() );
            request.setAttribute("map", map );
            request.setAttribute("project", p );

            String formSubmited = SigarUtils.validarCadena( request.getParameter("formSubmited") );
            if ( formSubmited == null )
            {
                // Unscape it when you show it (formSubmited == null )
                // And escape it when you receive it.
                hs.setHsDescription( Utils.unscapeHtmlForTextArea(hs.getHsDescription()));
                errors.add("formSubmited", new ActionMessage("formSubmited") );
            }
            else
            {
                String title = SigarUtils.validarCadena( request.getParameter("title"));
                String summary = request.getParameter("summary");

                title = Utils.escapeHtml(title);
                summary = Utils.escapeHtml(summary);

                if ( title != null )
                    hs.setHsName(title);

                hs.setHsDescription( summary );
                
            }


        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            errors.add("error", new ActionMessage("formSubmited") );
        }

        return errors;

    }

    public Hotspot getHotspot() {
        return hs;
    }



}
