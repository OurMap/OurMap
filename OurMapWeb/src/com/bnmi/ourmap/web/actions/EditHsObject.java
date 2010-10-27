/*******************************************************************************

com.bnmi.ourmap.web.actions.EditHsObject.java
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
import com.bnmi.ourmap.model.CriteriosHotspotObject;
import com.bnmi.ourmap.model.EasyObject;
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.HotspotObject;
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.web.Constantes;
import com.bnmi.ourmap.web.Utils;
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
public class EditHsObject extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

        Integer objectId    = SigarUtils.parseInt( request.getParameter("objectId"));
        String title        = SigarUtils.validarCadena(request.getParameter("mediaTitle"));
        String description  = request.getParameter("mediaComments") ;
        String formSubmited = SigarUtils.validarCadena(request.getParameter("formSubmited"));



        EasyObject eo = null;
        Integer hsId = null;


        eo = del.getObject(objectId);
        CriteriosHotspotObject c1 = new CriteriosHotspotObject();
        c1.setObjectId(eo.getObjectId());
        List<HotspotObject> hos = del.findHotspotObjects(c1);
        if ( ! hos.isEmpty() )
            hsId = hos.get(0).getHotspot();

        Hotspot h = del.getHotspot(hsId);
        Layer la = del.getLayer(h.getHsLayer());
        Map map = del.getMap(la.getMapId());
        Project p = del.getProject( map.getProjectId() );
        request.setAttribute("mapid", map.getMapid() );
        request.setAttribute("map", map );
        request.setAttribute("project", p);

        if ( formSubmited != null )
        {


            if ( title != null )
                eo.setObjName( Utils.escapeHtml(title) );

            if ( description != null )
                eo.setObjDescription( Utils.escapeHtml(description) );

            del.updateObject(eo);

            response.sendRedirect( "edithsmedia.do?hsId=" + hsId );
            
            
        }
        else
        {

            eo.setObjDescription( Utils.unscapeHtmlForTextArea(eo.getObjDescription()));
            request.setAttribute("hsId", hsId );
            request.setAttribute("eo", eo );
            return mapping.findForward("edithsobject");
        }

        return null;
    }




}
