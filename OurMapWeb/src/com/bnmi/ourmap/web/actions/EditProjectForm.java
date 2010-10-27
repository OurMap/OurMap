/*******************************************************************************

com.bnmi.ourmap.web.actions.EditProjectForm.java
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
public class EditProjectForm extends ActionForm {

    private String pid;
    private String nombre;
    private String descr;
    private String pwd;
    private String formSubmited = null;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        nombre = null;
        descr = null;
        pwd = null;
        formSubmited = null;

    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

        formSubmited = SigarUtils.validarCadena( request.getParameter("formSubmited"));
        if ( formSubmited == null )
        {
            Integer id = SigarUtils.parseInt(pid);
            try
            {
                Project p = del.getProject(id);
                this.setNombre(p.getNombre());
                this.setPwd(p.getPwd());
                this.setDescr(p.getDescr());
                request.setAttribute("project", p );

            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }

            errors.add("formSubmited", new ActionMessage("formSubmited"));
            return errors;
        }
        else
        {
            if ( pwd == null )
            {
                errors.add("formSubmited", new ActionMessage("formSubmited"));
                request.setAttribute("error-message", "Password required");
                return errors;
            }
            if ( pwd.length() < 5 )
            {
                errors.add("formSubmited", new ActionMessage("formSubmited"));
                request.setAttribute("error-message", "Min length for password: 5 characters");
                return errors;
            }
            if ( pwd.length() > 10 )
            {
                errors.add("formSubmited", new ActionMessage("formSubmited"));
                request.setAttribute("error-message", "Max length for password: 10 characters");
                return errors;
            }
            if ( nombre == null )
            {
                errors.add("formSubmited", new ActionMessage("formSubmited"));
                request.setAttribute("error-message", "Project name required");
                return errors;
            }
            if ( nombre.length() == 0 )
            {
                errors.add("formSubmited", new ActionMessage("formSubmited"));
                request.setAttribute("error-message", "Project name required");
                return errors;
            }



        }

        return errors;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * @param descr the descr to set
     */
    public void setDescr(String descr) {
        this.descr = descr;
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

    public Project getProject() {
        
        Project p = new Project();
        p.setId(SigarUtils.parseInt(pid));
        if ( SigarUtils.validarCadena(nombre) != null )
            p.setNombre(nombre);
        if ( descr != null )
            p.setDescr(descr);
        else
            p.setDescr("");
        if ( SigarUtils.validarCadena(pwd) != null )
            p.setPwd(pwd);

        return p;
    }


}
