/*******************************************************************************

com.bnmi.ourmap.web.tags.GetObjectTag.java
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

package com.bnmi.ourmap.web.tags;

import com.bnmi.ourmap.control.EasyDelegate;
import com.bnmi.ourmap.control.EasyManager;
import com.bnmi.ourmap.dao.DAOFactory;
import com.bnmi.ourmap.daoimpl.FileObjectDAO;
import com.bnmi.ourmap.model.EasyObject;
import com.bnmi.ourmap.web.Constantes;
import java.util.Hashtable;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class GetObjectTag extends SimpleTagSupport {

    private Integer id;
    private boolean temp = false;
    private boolean thumb = false;
    private boolean fileSystem = false;

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {

        JspWriter out = getJspContext().getOut();



        if ( EasyManager.dao == DAOFactory.FILE )
            fileSystem = true;
        else
            fileSystem = false;

        try
        {
            // TODO: insert code to write html before writing the body content.
            // e.g.:
            //
            // out.println("<strong>" + attribute_1 + "</strong>");
            // out.println("    <blockquote>");

            if ( id == null )
            {
               out.print( "nihil" );
               return;
            }


            String url = null;

            if ( ! fileSystem )
            {
               if ( temp )
               {
                    if ( thumb )
                    {
                        url = "getThumbT.do?objectId=" + id;
                    }
                    else
                    {
                        url = "getThumbT.do?objectId=" + id;
                    }
               }
               else
               {
                    if ( thumb )
                    {
                        url = "getThumb.do?objectId=" + id;
                    }
                    else
                    {
                        url = "getObject.do?objectId=" + id;
                    }
               }
            }
            else
            {

                PageContext pageContext = (PageContext) getJspContext();
                HttpSession session = pageContext.getSession();
                String filesFolder = (String) pageContext.getServletContext().getAttribute("filesFolder");
                EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);


                Hashtable<Integer,String> linkCache = null;
                if ( session.getAttribute(Constantes.LINK_CACHE) == null )
                {
                    linkCache = new Hashtable<Integer,String>();
                    session.setAttribute(Constantes.LINK_CACHE, linkCache );
                }
                linkCache = (Hashtable<Integer,String>) session.getAttribute(Constantes.LINK_CACHE);

                if ( temp )
                {
                    EasyObject eo = del.getTempObject(id);
                    if ( eo.getObjType() != null &&
                         eo.getObjType().intValue() == com.bnmi.ourmap.Constantes.IMAGE &&
                         thumb )
                    {
                        url = filesFolder + "/" + FileObjectDAO.getFullpath(eo, "thumb_temp_objects", "" );
                    }
                    else
                    {
                        url = filesFolder + "/" + eo.getPath();
                    }

                }
                else
                {
                    

                    //if ( linkCache.containsKey(id) )
                    if ( false )
                    {
                        url = linkCache.get(id);
                    }
                    else
                    {
                        EasyObject eo = del.getObject(id);
                        if ( eo.getObjType() != null &&
                             eo.getObjType().intValue() == com.bnmi.ourmap.Constantes.IMAGE &&
                             thumb )
                        {
                            url = filesFolder + "/" + FileObjectDAO.getFullpath(eo, "thumb_objects", "" );
                        }
                        else
                        {
                            url = filesFolder + "/" + eo.getPath();
                        }
                    }
                }

                //if ( id != null )
                //    linkCache.put(id, url);


            }

            out.print( url );


            JspFragment f = getJspBody();
            if (f != null)
                f.invoke(out);




            // TODO: insert code to write html after writing the body content.
            // e.g.:
            //
            // out.println("    </blockquote>");

        } catch (Exception ex) {
            throw new JspException("Error in GetObjectTag tag", ex);
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public void setThumb(boolean thumb) {
        this.thumb = thumb;
    }

}
