/*******************************************************************************

com.bnmi.ourmap.web.actions.EditKeyword.java
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
import com.bnmi.ourmap.model.CriteriosKeyword;
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
public class EditKeyword  extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);

        Integer toCat = SigarUtils.parseInt( request.getParameter("catId"));
        String kwValue = SigarUtils.validarCadena( request.getParameter("kwValue"));
        Integer kwId = SigarUtils.parseInt( request.getParameter("kwId"));
        String move = SigarUtils.validarCadena( request.getParameter("move") );
        Integer iconId = SigarUtils.parseInt( request.getParameter("iconId"));
        
        Keyword k = new Keyword();
        k.setCatId( toCat );
        k.setKwId(kwId);
        k.setKwValue(kwValue);
        k.setIconId(iconId);

        // Get all the keywords on the map
        System.out.println( kwId );
        Keyword current = del.getKeyword(kwId);
        CriteriosKeyword criteria = new CriteriosKeyword();
        criteria.setMapId( current.getMapId() );
        List<Keyword> keysOnMap = del.findKeywords(criteria);
        int numKeys = keysOnMap.size();
        int index = 0;
        for ( Keyword ke : keysOnMap )
            ke.setIndex(index++);

        // Set the index of the keyword in question
        int currentIndex = 0;
        for ( Keyword ke : keysOnMap )
            if ( ke.getKwId().equals(kwId))
                currentIndex = ke.getIndex();

        

        try
        {
            if ( move == null )
                del.updateKeyword( k );
            else
            {
                if ( move.equalsIgnoreCase("up") )
                {
                    if ( currentIndex > 0 )
                    {
                        Keyword above = keysOnMap.get(currentIndex - 1);
                        Keyword toMove = keysOnMap.get(currentIndex );
                        above.setIndex( currentIndex );
                        toMove.setIndex( currentIndex - 1);

                        for ( Keyword upK : keysOnMap )
                        {
                            Keyword updateK = new Keyword();
                            updateK.setKwId( upK.getKwId() );
                            updateK.setIndex( upK.getIndex());
                            del.updateKeyword(updateK);
                        }

                        
                    }
                }
                if ( move.equalsIgnoreCase("down"))
                {
                    if ( currentIndex < numKeys - 1 )
                    {
                        Keyword below = keysOnMap.get( currentIndex + 1 );
                        Keyword toMove = keysOnMap.get( currentIndex );
                        below.setIndex( currentIndex );
                        toMove.setIndex( currentIndex + 1);

                        for ( Keyword upK : keysOnMap )
                        {
                            Keyword updateK = new Keyword();
                            updateK.setKwId( upK.getKwId() );
                            updateK.setIndex( upK.getIndex());
                            del.updateKeyword(updateK);
                        }


                    }
                }
            }

            request.setAttribute("estado","OK");
            request.setAttribute("mensaje", "OK");

        }
        catch ( Exception ex )
        {
            request.setAttribute("estado","FAIL");
            request.setAttribute("mensaje", ex.getMessage() );

        }

        return mapping.findForward("results_jsp");

    }


}
