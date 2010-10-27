/*******************************************************************************

com.bnmi.ourmap.web.actions.ViewMapForm.java
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
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.SigarUtils;
import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class ViewMapForm extends ActionForm {

   private Integer mapid;
   private Map map;
   // The user can play with the controns? Only if noEdit is false.
   private boolean noEdit = false;
   private boolean dbMap = false;
   private Integer pid;

  private static Logger log = Logger.getLogger(ViewMapForm.class.getName());


    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
       super.reset(mapping, request);

       mapid = null;
       map = null;
       noEdit = false;
       dbMap = false;
       pid = null;
       
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        super.validate(mapping, request);
        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);


        com.inga.security.User retrieve = (com.inga.security.User) session.getAttribute(Constantes.PRINCIPAL);
        int browser = Constantes.MSIE; // default to MSIE ;

        String userDescription = retrieve.getDescription();
        if ( userDescription != null )
        {

            if( userDescription.contains("Safari") )
                browser = Constantes.SAFARI;
            else if ( userDescription.contains("MSIE") )
                browser = Constantes.MSIE ;
            else
                browser = Constantes.FIREFOX ;

        }

        switch ( browser )
        {
            case Constantes.MSIE :
                request.setAttribute("cols", "44" );
                break;
            case Constantes.SAFARI:
                request.setAttribute("cols", "29" );
                break;
            case Constantes.FIREFOX :
                request.setAttribute("cols", "39" );
                break;

        }

        Integer focusHs = SigarUtils.parseInt( request.getParameter("focusHs") );
        if ( focusHs != null )
            request.setAttribute("focusHs", focusHs );

        String viewHsEditPage = SigarUtils.validarCadena( request.getParameter("viewHsEditPage"));
        if ( viewHsEditPage != null )
            request.setAttribute("viewHsEditPage", "true" );

        // Maps
        String noEditMode = (String) request.getParameter("no_edit");
        if ( noEditMode == null )
            noEditMode = (String) request.getAttribute("no_edit" );
        if ( noEditMode != null )
            request.setAttribute("no_edit", "yes" );


        // Where does the map come from?
        mapid = SigarUtils.parseInt( request.getParameter("mapid") );
        if ( mapid == null )
            mapid = (Integer) request.getAttribute("mapid");

        if ( mapid == null )
        {
            // Map doesn't come from the data base, create a volatile map for the request only
            dbMap = false;
            map = (Map) session.getAttribute(Constantes.NEW_MAP);
        }
        else
        {
            // Get the map from the database
            try
            {
                map = del.getMap(mapid);
                dbMap = true;
            }
            catch ( Exception ex )
            {
                log.info( "No map found???" + ex.getMessage() );
                ex.printStackTrace( new PrintStream(System.out));

                ex.printStackTrace();
                //session.invalidate();
            }

        }


        log.info( "DB Map? " + dbMap );

        String mapMode = SigarUtils.validarCadena( request.getParameter("mapMode"));
        if ( mapMode != null )
            request.setAttribute("mapMode", mapMode );



        return errors;

    }


    public Map getMap() {
        return map;
    }

    /**
     * @return the noEdit
     */
    public boolean isNoEdit() {
        return noEdit;
    }

    /**
     * @param noEdit the noEdit to set
     */
    public void setNoEdit(boolean noEdit) {
        this.noEdit = noEdit;
    }

    /**
     * @return the dbMap
     */
    public boolean isDbMap() {
        return dbMap;
    }

    /**
     * @param dbMap the dbMap to set
     */
    public void setDbMap(boolean dbMap) {
        this.dbMap = dbMap;
    }

    /**
     * @return the pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

}