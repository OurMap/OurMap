/*******************************************************************************

com.bnmi.ourmap.web.actions.EditSessionKeyword.java
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

import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.SigarUtils;
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
public class EditSessionKeyword extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        String kwValue = SigarUtils.validarCadena( request.getParameter("kwValue"));
        String catName = request.getParameter("catname");
        Integer iconId = SigarUtils.parseInt( request.getParameter("iconId"));
        String move = SigarUtils.validarCadena( request.getParameter("move"));
        Integer index = SigarUtils.parseInt( request.getParameter("index"));

        int myKeyIndex = 0;
        if ( index != null )
           myKeyIndex = index;

        Keyword keySwap = null;
        
        try
        {
            List<Keyword> keywords = (List<Keyword>) session.getAttribute(Constantes.NEW_MAP_KEYWORDS);
            int lastIndex = keywords.size() - 1;


            Keyword k = null;
            for ( Keyword ke : keywords )
                if ( ke.getKwValue().equals( kwValue) )
                    k = ke;

            if ( k != null )
            {
                if ( catName != null )
                {
                    if ( catName.equalsIgnoreCase("null") )
                        k.setCatName( null );
                    else if ( catName.length() == 0)
                        k.setCatName( null );
                    else
                        k.setCatName( catName );
                }

                if ( iconId != null )
                    k.setIconId(iconId);
            }

            if ( kwValue != null && index != null )
            {
                k = keywords.get(index-1);
                k.setKwValue( kwValue );
            }


            ////////////////////////////////////////////////////////////////////
            if ( move != null )
            {
                if ( move.equalsIgnoreCase("up") )
                {
                    if ( myKeyIndex > 0 )
                    {
                        Keyword keyAbove = keywords.get(myKeyIndex - 1 );
                        Keyword current = keywords.get(myKeyIndex);
                        keySwap = new Keyword();

                        keySwap.setKwValue( keyAbove.getKwValue() );
                        keySwap.setCatName( keyAbove.getCatName() );
                        keySwap.setIconId( keyAbove.getIconId() );

                        keyAbove.setKwValue( current.getKwValue() );
                        keyAbove.setCatName( current.getCatName() );
                        keyAbove.setIconId( current.getIconId() );

                        current.setKwValue( keySwap.getKwValue() );
                        current.setCatName( keySwap.getCatName() );
                        current.setIconId( keySwap.getIconId() );

                    }
                }
                if ( move.equalsIgnoreCase("down") )
                {
                    if ( myKeyIndex < lastIndex )
                    {
                        Keyword keyBelow = keywords.get( myKeyIndex + 1);
                        Keyword current = keywords.get(myKeyIndex);
                        keySwap = new Keyword();

                        keySwap.setKwValue( keyBelow.getKwValue() );
                        keySwap.setCatName( keyBelow.getCatName() );
                        keySwap.setIconId( keyBelow.getIconId() );

                        keyBelow.setKwValue( current.getKwValue() );
                        keyBelow.setCatName( current.getCatName() );
                        keyBelow.setIconId( current.getIconId() );

                        current.setKwValue( keySwap.getKwValue() );
                        current.setCatName( keySwap.getCatName() );
                        current.setIconId( keySwap.getIconId() );

                    }
                }

            }
        

            ////////////////////////////////////////////////////////////////////

            request.setAttribute("estado","OK");
            request.setAttribute("mensaje", "OK");
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();

            request.setAttribute("estado","FAIL");
            request.setAttribute("mensaje", ex.getMessage() );
        }

        return mapping.findForward("results_jsp");
        
    }




}
