/*******************************************************************************

com.bnmi.ourmap.web.actions.OrgCategories.java
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
import com.bnmi.ourmap.model.Category;
import com.bnmi.ourmap.model.CriteriosCategory;
import com.bnmi.ourmap.model.CriteriosKeyword;
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.SigarUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
public class OrgCategories extends Action {

    public static final String NO_CAT = "ZZ_no_cat_";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

        Integer mapid = SigarUtils.parseInt( request.getParameter("mapid"));
        Map m = del.getMap(mapid);
        Project p = del.getProject(m.getProjectId());



        LinkedHashMap<Category,List<Keyword>> keymap = new LinkedHashMap<Category,List<Keyword>>();
        List<Keyword> nocat = new ArrayList<Keyword>();
        CriteriosCategory cr2 = new CriteriosCategory();
        cr2.setMapId(mapid);
        List<Category> cats = del.findCategorys(cr2);
        for ( Category c : cats )
            keymap.put( c, new ArrayList<Keyword>() );


        CriteriosKeyword criteria = new CriteriosKeyword();
        criteria.setMapId(mapid);
        List<Keyword> keywords = del.findKeywords( criteria );
        Integer catId = null;
        Category cat = null;
        List<Keyword> keysByCat;
        for ( Keyword k : keywords )
        {
            catId = k.getCatId();
            if ( catId == null )
            {
                nocat.add( k );
                continue;
            }
            else
            {
                
                for ( Category tempCat : cats )
                    if ( tempCat.getCatId().intValue() == catId.intValue() )
                    {
                        cat = tempCat;
                        break;
                    }

                
                keysByCat = keymap.get( cat );
                keysByCat.add(k);

            }



        }

        if ( nocat.isEmpty() )
            request.setAttribute("allkeyshavecats", "true" );
        else
            request.setAttribute("allkeyshavecats", "false" );
        
        request.setAttribute( "keymap", keymap );
        request.setAttribute( "nocat", nocat );

        request.setAttribute("map", m );
        request.setAttribute("project", p );
        request.setAttribute("mapid", mapid );



        String catsEnabled = m.getCatsEnabled();
        System.out.println( catsEnabled );
        if ( catsEnabled != null && catsEnabled.equalsIgnoreCase("t") )
            return mapping.findForward("editcategories");
        else
            return mapping.findForward("editcategories_disabled");

        
    }

}
