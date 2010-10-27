/*******************************************************************************

com.bnmi.ourmap.web.actions.DeleteSessionCategory.java
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

import com.bnmi.ourmap.model.Category;
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.SigarUtils;
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
public class DeleteSessionCategory extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        String catname = SigarUtils.validarCadena( request.getParameter("catname"));

        List<Category> cats = (List<Category>) session.getAttribute(Constantes.NEW_MAP_CATEGORIES);
        List<Keyword> cksAll = (List<Keyword>) session.getAttribute(Constantes.NEW_MAP_KEYWORDS);
        List<Keyword> cks = new ArrayList<Keyword>();

        if ( catname != null )
        {
            for( Keyword k : cksAll )
            {
                if ( k.getCatName() != null && k.getCatName().equals(catname) )
                    cks.add( k );
            }


            if ( ! cks.isEmpty() )
            {
                request.setAttribute("estado","FAIL");
                request.setAttribute("mensaje", "You cannot delete a Category that contains Keywords! Please move these keywords (or delete them using the Keywords editing page) before you delete this Category" );
                return mapping.findForward("results_jsp");
            }

            try
            {
                int index = -1;
                int cont = 0;
                for ( Category c : cats )
                {
                    if ( c.getCatName() != null && catname != null && c.getCatName().equalsIgnoreCase(catname) )
                        index = cont;
                    cont++;
                }

                if ( index >= 0 )
                    cats.remove(index);


                request.setAttribute("estado","OK");
                request.setAttribute("mensaje", "OK");

            }
            catch ( Exception ex )
            {
                request.setAttribute("estado","FAIL");
                request.setAttribute("mensaje", ex.getMessage() );
            }

        }
        else
        {
            request.setAttribute("estado","OK");
            request.setAttribute("mensaje", "OK");
        }


        return mapping.findForward("results_jsp");


    }


}
