/*******************************************************************************

com.bnmi.ourmap.web.actions.ViewProjectForm.java
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
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.model.User;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.ReturnMessage;
import com.inga.utils.SigarUtils;
import java.util.HashSet;
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
public class ViewProjectForm extends ActionForm {
    
    private String pid;
    private String pwd;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        pid = null;
        pwd = null;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);
        User principal = (User) session.getAttribute(Constantes.USER);

        try
        {
            ReturnMessage r = del.hasPermission(principal, "view", SigarUtils.parseInt(pid), com.bnmi.ourmap.Constantes.PROJECT);
            boolean granted = r.isSuccess();
            if ( granted ) 
                return errors;
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
        
        HashSet<Integer> projectsOpened = (HashSet) session.getAttribute(Constantes.OPENED_PROJECTS);
        if ( projectsOpened == null )
        {
            projectsOpened = new HashSet<Integer>();
            session.setAttribute(Constantes.OPENED_PROJECTS, projectsOpened );
        }


        if ( projectsOpened.contains( SigarUtils.parseInt(pid) ) )
            return errors;
        

        if ( pwd == null )
            pwd = (String) request.getAttribute("pwd");

        if ( pid == null )
            pid = (String) request.getAttribute("pid");

        if ( pid == null )
        {
            errors.add( "formSubmited", new ActionMessage("formSubmited"));
            request.setAttribute("error-message", "Project not found");
            return errors;
        }

        if ( pwd == null )
        {
            errors.add( "formSubmited", new ActionMessage("formSubmited"));
            return errors;
        }

        if ( pid != null && pwd != null )
        {
            try
            {
                Project p = del.getProject(SigarUtils.parseInt(pid));
                if ( ! p.getPwd().equalsIgnoreCase(pwd) )
                {
                    errors.add( "formSubmited", new ActionMessage("formSubmited"));
                    request.setAttribute("error-message", "Wrong password");
                    return errors;
                }
                else
                {
                    projectsOpened.add(p.getId());
                    return errors;
                }

            }
            catch ( Exception ex )
            {
                errors.add( "formSubmited", new ActionMessage("formSubmited"));
                request.setAttribute("error-message", ex.getMessage() );
                return errors;
            }
        }

        return errors;
        
    }

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
