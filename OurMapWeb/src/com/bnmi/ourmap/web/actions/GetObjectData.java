/*******************************************************************************

com.bnmi.ourmap.web.actions.GetObjectData.java
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
import com.bnmi.ourmap.control.EasyManager;
import com.bnmi.ourmap.dao.DAOFactory;
import com.bnmi.ourmap.model.EasyObject;
import com.bnmi.ourmap.web.Constantes;
import com.inga.exception.RegistroNoExisteException;
import com.inga.security.User;
import com.inga.utils.SigarUtils;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class GetObjectData extends Action {

    private static Logger log = Logger.getLogger( GetObjectData.class );

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);


        User u = (User) session.getAttribute(Constantes.PRINCIPAL);
        HashSet<Integer> downloadedObjects = (HashSet) session.getAttribute( Constantes.DOWNLOADED_OBJECTS );
        if ( downloadedObjects == null )
        {
            downloadedObjects = new HashSet<Integer>();
            session.setAttribute( Constantes.DOWNLOADED_OBJECTS, downloadedObjects );
        }
        

        Integer objId = SigarUtils.parseInt( request.getParameter("objectId"));

        if ( ! downloadedObjects.contains(objId) )
        {
            try
            {

                if ( EasyManager.dao == DAOFactory.FILE )
                {

                    Hashtable<Integer,String> linkCache = null;
                    if ( session.getAttribute(Constantes.LINK_CACHE) == null )
                    {
                        linkCache = new Hashtable<Integer,String>();
                        session.setAttribute(Constantes.LINK_CACHE, linkCache );
                    }
                    linkCache = (Hashtable<Integer,String>) session.getAttribute(Constantes.LINK_CACHE);

                    String url = null;

                    //if ( linkCache.containsKey(objId) )
                    if ( false )
                    {
                        url = linkCache.get(objId);
                    }
                    else
                    {
                        if ( objId != null )
                        {
                            EasyObject o = del.getObject( objId );
                            String filesFolder = (String) this.getServlet().getServletContext().getAttribute("filesFolder");
                            String filename = o.getPath();
                            url = filesFolder + "/" + filename;
                            //linkCache.put(objId, url);
                        }
                        else
                        {
                            url = "nihil";
                        }
                    }
                    response.sendRedirect( url );
                    return null;
                }

                EasyObject o = del.getObject( objId );

                del.fillData(o,1,"objects");
                Integer type = o.getObjType();
                String mime = "";

                String ext = "";
                if ( o.getExtension() != null )
                    ext = o.getExtension().trim();

                switch ( type )
                {
                    case com.bnmi.ourmap.Constantes.ICON :
                    case com.bnmi.ourmap.Constantes.IMAGE :

                        mime = "image/jpeg";

                        if ( ext != null && ext.length() > 0 )
                        {
                            if ( ext.equals("jpg") )
                                mime = "image/jpeg";
                            else
                                mime = "image/" + ext ;
                        }
                        response.setHeader("Content-type", mime );

                        // Set content as an attachment
                        /*
                        if ( ext != null && ext.length() > 0 )
                            response.setHeader( "Content-Disposition", "attachment; filename=\"" + o.getObjName() + "." + ext + "\"" );
                        else
                            response.setHeader( "Content-Disposition", "attachment; filename=\"" + o.getObjName() + "\"" );
                        */
                        downloadedObjects.add( objId );

                        break;
                        
                    case com.bnmi.ourmap.Constantes.AUDIO :

                        mime = "audio/wav" ;
                        
                        if ( ext != null && ext.length() > 0 )
                        {
                            if( ext.equalsIgnoreCase("amr") )
                            {
                                mime = "audio/amr";
                                //mime = "application/octet-stream";
                                //response.setHeader("Accept-Ranges", "bytes" );
                            }
                            else if ( ext.equalsIgnoreCase("mp3") )
                                mime = "audio/mpeg";
                            else
                                mime = "audio/" + ext;
                        }

                        /*
                        // Set content as an attachment
                        if ( ext != null && ext.length() > 0 )
                            response.setHeader( "Content-Disposition", "attachment; filename=\"" + o.getObjName() + "." + ext + "\"" );
                        else
                            response.setHeader( "Content-Disposition", "attachment; filename=\"" + o.getObjName() + "\"" );
                        */

                        response.setHeader("Content-type", mime );


                        break;
                    case com.bnmi.ourmap.Constantes.VIDEO :

                        mime = "video/mpeg" ;

                        if ( ext != null && ext.length() > 0 )
                        {
                            if ( ext.equals("mpg") )
                                mime = "video/mpeg";
                            else if ( ext.equals("wmv") )
                                mime = "video/x-ms-wmv";
                            else if ( ext.equalsIgnoreCase("3gp"))
                                mime = "video/3gpp";
                            else if ( ext.equalsIgnoreCase("avi"))
                                mime = "video/x-msvideo";
                            else
                                mime = "video/" + ext ;
                        }

                        // Set content as an attachment
                        /*
                        if ( ext != null && ext.length() > 0 )
                            response.setHeader( "Content-Disposition", "attachment; filename=\"" + o.getObjName() + "." + ext + "\"" );
                        else
                            response.setHeader( "Content-Disposition", "attachment; filename=\"" + o.getObjName() + "\"" );
                        */
                        //log.debug( "mime:" + mime );
                        //System.out.println(mime);
                        response.setHeader("Content-type", mime );
                        
                        break;

                    default:
                        break;

                }

                byte[] data = o.getObjData();
                response.setHeader("Content-Length", String.valueOf(data.length) );

                OutputStream out = response.getOutputStream();

                byte[] buf = new byte[1024];
                int count = 0;
                int size = data.length;
                while ( size > count && count >= 0)
                {
                    int i = 0;
                    for ( ; i < 1024 && count < size; i++ )
                        buf[ i  ] = data[count++];
                    out.write(buf, 0, i);
                    out.flush();
                }

                if ( type != null && 
                     ( type.intValue() == com.bnmi.ourmap.Constantes.VIDEO ||
                       type.intValue() == com.bnmi.ourmap.Constantes.AUDIO
                     )
                    )
                {
                   log.debug(u.getId() + " " + o.getObjName() + " bytes written: " + count + " " + mime );
                }

                //out.write( data );

                // Objects are remembered as "downloaded", and therefor the next
                // try to download the image will return status 304, not modified
                //downloadedObjects.add( objId );

            }
            catch ( RegistroNoExisteException ex )
            {

            }
        }
        else
        {
            // Already downloaded
            response.setStatus(304);
        }

        return null;
    }




}
