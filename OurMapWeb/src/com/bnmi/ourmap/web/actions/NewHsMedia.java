/*******************************************************************************

com.bnmi.ourmap.web.actions.NewHsMedia.java
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
import com.bnmi.ourmap.model.EasyObject;
import com.bnmi.ourmap.model.HotspotObject;
import com.bnmi.ourmap.web.Constantes;
import com.bnmi.ourmap.web.Utils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/** For adding media to an existing hotspot
 *
 * @author Manuel Camilo Cuesta
 */
public class NewHsMedia  extends Action {

    private static Logger log = Logger.getLogger(NewHsMedia.class.getName());

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Integer mediaType = null;
        Integer hsId = null;
        try
        {
            NewHsMediaForm forma = (NewHsMediaForm) form;
            HttpSession session = request.getSession();
            EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

            EasyObject eo = forma.getObj();
            hsId = forma.getHsId();
            mediaType = eo.getObjType();

            List<EasyObject> myObjects = del.getObjects(hsId, 0 );

            if ( eo.getObjType() == com.bnmi.ourmap.Constantes.IMAGE )
                NewMedia.setImages(eo);

            del.createObject(eo);
            HotspotObject ho = new HotspotObject();
            ho.setHotspot(hsId);
            ho.setBlock(0);
            ho.setObjectId( eo.getObjectId() );
            ho.setIndex( myObjects.size() );

            del.createHotspotObject(ho);

            request.setAttribute("mediaObject", eo );

            response.sendRedirect( "edithsmedia.do?hsId=" + hsId );

            return null;

        }
        catch ( java.lang.OutOfMemoryError memox )
        {
            Utils.info( request, "File too big. Hotspot: " + hsId );
            if ( hsId != null && mediaType != null )
            {
                String params = "hsId=" + hsId + "&errorCode=toobig" ;
                switch ( mediaType )
                {
                    case com.bnmi.ourmap.Constantes.IMAGE:
                        response.sendRedirect("addnewimage.do?" + params );
                        break;
                    case com.bnmi.ourmap.Constantes.AUDIO:
                        response.sendRedirect("addnewaudio.do?" + params );
                        break;
                    case com.bnmi.ourmap.Constantes.TEXT:
                        response.sendRedirect("addnewtext.do?" + params );
                        break;
                    case com.bnmi.ourmap.Constantes.VIDEO:
                        response.sendRedirect("addnewvideo.do?" + params );
                        break;
                }
                return null;

            }
            else
            {
                request.setAttribute("error", "Error uploading file");
                return mapping.findForward("error_jsp");
            }

        }

    }


}