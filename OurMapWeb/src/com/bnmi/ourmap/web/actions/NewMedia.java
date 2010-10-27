/*******************************************************************************

com.bnmi.ourmap.web.actions.NewMedia.java
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
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.Point;
import com.inga.utils.SigarUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/** For temp objects. At new hotspot creation
 *
 * @author Manuel Camilo Cuesta
 */
public class NewMedia  extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        NewMediaForm forma = (NewMediaForm) form;
        EasyObject o = forma.getObj();
        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

        Integer mapid = SigarUtils.parseInt( request.getParameter("mapid"));
        Map m = del.getMap( mapid );
        Project p = del.getProject(m.getProjectId());
        request.setAttribute("mapid", mapid );
        request.setAttribute("map", m );
        request.setAttribute("project", p );


        if ( o.getObjType() == com.bnmi.ourmap.Constantes.IMAGE )
        {
            setImages(o);
        }

        Integer objId = del.createTempObject(o);
        
        // Clear data, because it is already on the db
        o.setObjData( null );
        System.gc();


        LinkedHashMap<Integer,EasyObject> objectsToUpload = (LinkedHashMap<Integer,EasyObject>) session.getAttribute("objects_to_upload");
        if ( objectsToUpload == null )
        {
            objectsToUpload = new LinkedHashMap<Integer,EasyObject>();
            session.setAttribute("objects_to_upload", objectsToUpload );
        }

        if ( forma.getItemList() != null )
        {
            List<Integer> itemList = forma.getItemList();

            LinkedHashMap<Integer,EasyObject> newItemList = new LinkedHashMap<Integer,EasyObject>();
            for( Integer id : itemList )
            {
                EasyObject eo = objectsToUpload.get( id );
                newItemList.put(id, eo);
            }
            session.setAttribute("objects_to_upload", newItemList );
        }


        if ( objId != null )
        {
            objectsToUpload = (LinkedHashMap<Integer,EasyObject>) session.getAttribute("objects_to_upload");
            objectsToUpload.put(objId,o);
        }

        response.sendRedirect("addnewmediahs.do");
        return null;
        //return mapping.findForward("add_new_media_hs");
        
    }

    public static float getFactor(int width, int height, int maxWidth, int maxHeight ) {

        if ( width > height && width > maxWidth )
            return  (float) maxWidth / (float) width;

        if ( height > width && height > maxHeight )
            return (float) maxHeight / (float) height;

        return 1.0f;
        
    }

    public static void setImages( EasyObject o ) throws IOException {
                byte[] data1 = o.getObjData();
                byte[] data2 = null;


                ByteArrayInputStream bin = new ByteArrayInputStream( data1 );
                BufferedImage bufImage = ImageIO.read( bin );

                int bufImageWidth  = 0;
                int bufImageHeight = 0;
                bufImageHeight = bufImage.getHeight();
                bufImageWidth  = bufImage.getWidth();

                BufferedImage bufImage2 = null;

                float factor = getFactor( bufImageWidth, bufImageHeight, Constantes.MAX_WIDTH, Constantes.MAX_HEIGHT );

                if ( factor < 1 )
                {
                    bufImage2 = SigarUtils.shrink( bufImage, factor);
                }
                else
                {
                    bufImage2 = bufImage;
                }

                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                ImageIO.write(bufImage2, o.getExtension(), bout );
                data2 = bout.toByteArray();

                o.setObjData(data2);
                o.setObjSize(data2.length);

                int savedWidth  = bufImage2.getWidth();
                int savedHeight = bufImage2.getHeight();
                Point dimensions = new Point(savedWidth,savedHeight);
                o.setDimensions(dimensions);


                factor = getFactor( bufImageWidth, bufImageHeight, Constantes.MAX_WIDTH_THUMB, Constantes.MAX_HEIGHT_THUMB );

                if ( factor < 1 )
                    bufImage2 = SigarUtils.shrink( bufImage, factor);

                bout = new ByteArrayOutputStream();
                ImageIO.write(bufImage2, o.getExtension(), bout );
                data2 = bout.toByteArray();

                o.setObjData2(data2);


                bout.close();
                data1 = null;
                data2 = null;
                System.gc();

    }


}

