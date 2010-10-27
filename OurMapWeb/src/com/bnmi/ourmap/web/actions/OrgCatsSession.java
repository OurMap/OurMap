/*******************************************************************************

com.bnmi.ourmap.web.actions.OrgCatsSession.java
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class OrgCatsSession extends Action {

    public static final String NO_CAT = "ZZ_no_cat_";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();


        LinkedHashMap<Category,List<Keyword>> keymap = new LinkedHashMap<Category,List<Keyword>>();
        List<Keyword> nocat = new ArrayList<Keyword>();

        populateKeyMap( session, keymap, nocat );

        request.setAttribute( "keymap", keymap );
        request.setAttribute( "nocat", nocat );

        return mapping.findForward("org_cats_session");


    }

    public static void populateKeyMap(HttpSession session, LinkedHashMap<Category,List<Keyword>> keymap, List<Keyword> nocat ) {

        List<Category> cats = (List<Category>) session.getAttribute(Constantes.NEW_MAP_CATEGORIES);

        
        Collections.sort(cats, new CategoryComparator() );

        for ( Category c : cats )
            keymap.put( c, new ArrayList<Keyword>() );

        List<Keyword> keywords = (List<Keyword>) session.getAttribute(Constantes.NEW_MAP_KEYWORDS);
        String catName = null;
        Category cat = null;
        List<Keyword> keysByCat;
        for ( Keyword k : keywords )
        {
            catName = k.getCatName();
            if ( catName == null )
            {
                nocat.add( k );
                continue;
            }
            else
            {
                for ( Category tempCat : cats )
                    if ( tempCat.getCatName().equals(catName) )
                    {
                        cat = tempCat;
                        break;
                    }
            }

            keysByCat = keymap.get( cat );
            keysByCat.add(k);

        }

    }

}

class CategoryComparator implements Comparator<Category> {

    // Comparator interface requires defining compare method.
    @Override
    public int compare(Category o1, Category o2) {

        String catname1 = o1.getCatName();
        String catname2 = o2.getCatName();

        if ( catname1 == null && catname2 == null )
            return 0;
        if ( catname1 != null && catname2 == null )
            return -1;
        if ( catname1 == null && catname2 != null )
            return 1;
        return catname1.compareTo(catname2);

    }
}

