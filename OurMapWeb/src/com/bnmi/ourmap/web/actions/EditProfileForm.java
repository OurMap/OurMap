/*******************************************************************************

com.bnmi.ourmap.web.actions.EditProfileForm.java
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
import com.bnmi.ourmap.model.Rol;
import com.bnmi.ourmap.model.User;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.SigarUtils;
import java.util.ArrayList;
import java.util.List;
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
public class EditProfileForm extends ActionForm {

    private String userid;
    private java.lang.String pwd;
    private java.lang.String nombre;
    private java.lang.String lastname;
    private java.lang.String descr;
    private String formSubmited;
    private String rol;

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);


        userid = SigarUtils.validarCadena( request.getParameter("userid") );
        pwd = SigarUtils.validarCadena( request.getParameter("pwd"));
        nombre = SigarUtils.validarCadena( request.getParameter("nombre"));
        lastname = SigarUtils.validarCadena( request.getParameter("lastname"));
        descr = SigarUtils.validarCadena( request.getParameter("descr"));
        rol = SigarUtils.validarCadena(request.getParameter("rol"));

        try
        {

            //this.reset(mapping, request);
            User theUser = del.getUser(userid);

            request.setAttribute("usuario", getUser() );
            Rol theRol = del.getRol( theUser.getRol() );
            request.setAttribute("rolename", theRol.getRolname());

            User principal = (User) session.getAttribute(Constantes.USER);
            Rol principalRol = del.getRol(principal.getRol());
            List<Rol> allRoles = del.listRoles();
            List<Rol> showRoles = new ArrayList<Rol>();
            for ( Rol r : allRoles )
            {
                if ( r.getWeight().intValue() <= principalRol.getWeight().intValue() )
                   showRoles.add( r );
            }

            request.setAttribute("roles", showRoles );
            request.setAttribute("selectedRol", theRol.getRol() );


            formSubmited = SigarUtils.validarCadena( request.getParameter("formSubmited"));
            if ( formSubmited == null )
            {
                this.setPwd(theUser.getPwd());
                this.setNombre(theUser.getNombre());
                this.setLastname(theUser.getLastname());
                errors.add("formSubmited", new ActionMessage("formSubmited"));
                return errors;
            }
            else
            {
                if ( nombre == null )
                {
                    errors.add("userid", new ActionMessage("name.required"));
                    request.setAttribute("error-message", "User Name required");
                    return errors;
                }
                if ( pwd == null )
                {
                    errors.add("userid", new ActionMessage("pwd.required"));
                    request.setAttribute("error-message", "Password required");
                    return errors;
                }
                return errors;
            }




        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }


        return errors;
        
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        EasyDelegate del = (EasyDelegate) request.getSession().getAttribute(Constantes.DELEGATE);
        userid = SigarUtils.validarCadena( request.getParameter("userid"));
        try
        {
            User u = del.getUser(userid);

            userid = u.getId();
            nombre = u.getNombre();
            lastname = u.getLastname();
            pwd = u.getPwd();
            descr = u.getDescr();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }

    }


    /**
     * @return the pwd
     */
    public java.lang.String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(java.lang.String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the lastname
     */
    public java.lang.String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(java.lang.String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the descr
     */
    public java.lang.String getDescr() {
        return descr;
    }

    /**
     * @param descr the descr to set
     */
    public void setDescr(java.lang.String descr) {
        this.descr = descr;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public User getUser() {
        User u = new User();
        u.setId(userid);
        u.setNombre(nombre);
        u.setPwd(pwd);
        u.setLastname(lastname);
        u.setDescr(descr);
        u.setRol(rol);
        return u;
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
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

}
