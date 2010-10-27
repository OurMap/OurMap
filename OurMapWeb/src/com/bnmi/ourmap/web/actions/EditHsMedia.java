/*******************************************************************************

com.bnmi.ourmap.web.actions.EditHsMedia.java
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
import com.bnmi.ourmap.model.EasyObject;
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.model.User;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.ReturnMessage;
import com.inga.utils.SigarUtils;
import com.inga.utils.StringItem;
import java.util.ArrayList;
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
public class EditHsMedia extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

        Integer hsId = SigarUtils.parseInt( request.getParameter("hsId"));

        User principal = (User) session.getAttribute(Constantes.USER);
        ReturnMessage perm = del.hasPermission( principal, "edit-media", hsId, com.bnmi.ourmap.Constantes.HOTSPOT );
        if ( ! perm.isSuccess() )
            throw new SecurityIssue( perm.getMessage() );

        Hotspot hs = del.getHotspot(hsId);
        Layer la = del.getLayer( hs.getHsLayer() );
        Integer mapid = la.getMapId();
        Map map = del.getMap(la.getMapId());
        Project p = del.getProject(map.getProjectId());
        request.setAttribute("mapid", mapid );
        request.setAttribute("hs", hs );
        request.setAttribute("hsId", hsId );
        request.setAttribute("map", map );
        request.setAttribute("project", p );
        request.setAttribute("mode" , "edit" );

        String formSubmited = SigarUtils.validarCadena( request.getParameter("formSubmited") );

        if ( formSubmited == null )
        {
            List<EasyObject> objectsList = del.getObjects(hsId, 0 );
            request.setAttribute("object_list", objectsList );
            List<StringItem> displayOptions = new ArrayList<StringItem>();
            displayOptions.add( new StringItem( "Simple List", "1") );
            displayOptions.add( new StringItem( "Scrolling Media","2") );
            request.setAttribute("displayOptions", displayOptions );
            request.setAttribute("selectedDisplayOption", hs.getDisplayMode() );

            return mapping.findForward("edithsmedia");
            //return mapping.findForward("add_new_media_hs");

        }
        else
        {
            
            Hotspot updatedHs = new Hotspot();
            updatedHs.setHsId(hsId);
            del.updateHotspot(updatedHs);

            response.sendRedirect( "viewmap.do?mapid=" + mapid + "&focusHs=" + hsId );

            return null;
        }


    }


}
