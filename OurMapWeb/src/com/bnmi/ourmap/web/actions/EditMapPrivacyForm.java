/*******************************************************************************

com.bnmi.ourmap.web.actions.EditMapPrivacyForm.java
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
public class EditMapPrivacyForm extends ActionForm {
    
    private String mapid;
    private String editableHs;
    private String editableMedia;
    private String privacy;
    private String formSubmited;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        mapid = null;
        privacy = null;
        formSubmited = null;
        editableHs = null;
        editableMedia = null;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

        try
        {
            Integer mapidInt = SigarUtils.parseInt( mapid );
            Map map = del.getMap(mapidInt);

            Project p = del.getProject(map.getProjectId());
            del.checkProjectConsistency( p );
            request.setAttribute("map", map );
            request.setAttribute("project", p );
            
        }
        catch ( Exception ex )
        {

        }

        if ( formSubmited == null )
        {
            
            Map map = (Map) request.getAttribute("map");
            Integer myPrivacy = map.getPrivacy();
            if ( myPrivacy == null )
                myPrivacy = new Integer(0);
            privacy = String.valueOf( myPrivacy );

            Integer myEditableHs = map.getHotspotsEditable();
            if ( myEditableHs == null )
                myEditableHs = new Integer(0);
            editableHs = String.valueOf( myEditableHs );   


            Integer myEditableMedia = map.getMediaEditable();
            if ( myEditableMedia == null )
                myEditableMedia = new Integer(0);
            editableMedia = String.valueOf( myEditableMedia );

            
            errors.add( "formSubmited", new ActionMessage("formSubmited"));
            return errors;
        }


        return errors;

    }

    /**
     * @return the mapid
     */
    public String getMapid() {
        return mapid;
    }

    /**
     * @param mapid the mapid to set
     */
    public void setMapid(String mapid) {
        this.mapid = mapid;
    }



    /**
     * @return the formSubmited
     */
    public String getFormSubmited() {
        return formSubmited;
    }

    /**
     * @param formSubmited the formSubmited to set
     */
    public void setFormSubmited(String formSubmited) {
        this.formSubmited = formSubmited;
    }

    /**
     * @return the privacy
     */
    public String getPrivacy() {
        return privacy;
    }

    /**
     * @param privacy the privacy to set
     */
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    /**
     * @return the editableHs
     */
    public String getEditableHs() {
        return editableHs;
    }

    /**
     * @param editableHs the editableHs to set
     */
    public void setEditableHs(String editableHs) {
        this.editableHs = editableHs;
    }

    /**
     * @return the editableMedia
     */
    public String getEditableMedia() {
        return editableMedia;
    }

    /**
     * @param editableMedia the editableMedia to set
     */
    public void setEditableMedia(String editableMedia) {
        this.editableMedia = editableMedia;
    }



}
