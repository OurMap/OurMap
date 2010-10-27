/*******************************************************************************

com.bnmi.ourmap.web.actions.EditSessionLayer.java
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

import com.bnmi.ourmap.model.Layer;
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
public class EditSessionLayer extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        Integer index = SigarUtils.parseInt( request.getParameter("index"));
        String layerName = SigarUtils.validarCadena(request.getParameter("editName"));
        String layerDescription = SigarUtils.validarCadena( request.getParameter("editDescription"));
        String move = SigarUtils.validarCadena( request.getParameter("move") );
        Integer iconId = SigarUtils.parseInt( request.getParameter("iconId"));

        List<Layer> layersInSession = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);

        Layer actualLayer = layersInSession.get( index );
        int myLayerIndex = index;

        if ( layerName != null )
            actualLayer.setLayerName(layerName);
        if ( layerDescription != null)
            actualLayer.setLayerDescription(layerDescription);
        if ( iconId != null )
            actualLayer.setIconId(iconId);

        Layer layerSwap = null;


        // Reorder layers if neccesary
        if ( move != null )
        {
            int numLayers = layersInSession.size();
            int lastIndex = numLayers - 1;

            if ( move.equalsIgnoreCase("up") )
            {
                if ( myLayerIndex > 0 )
                {
                    Layer layerAbove = layersInSession.get(myLayerIndex - 1 );
                    Layer current = layersInSession.get(myLayerIndex);
                    layerSwap = new Layer();

                    layerSwap.setLayerName( layerAbove.getLayerName() );
                    layerSwap.setLayerDescription( layerAbove.getLayerDescription() );
                    layerAbove.setLayerName( current.getLayerName() );
                    layerAbove.setLayerDescription( current.getLayerDescription() );
                    current.setLayerName( layerSwap.getLayerName() );
                    current.setLayerDescription( layerSwap.getLayerDescription() );

                }
            }
            if ( move.equalsIgnoreCase("down") )
            {
                if ( myLayerIndex < lastIndex )
                {
                    Layer layerBelow = layersInSession.get( myLayerIndex + 1);
                    Layer current = layersInSession.get(myLayerIndex);
                    layerSwap = new Layer();

                    layerSwap.setLayerName( layerBelow.getLayerName() );
                    layerSwap.setLayerDescription( layerBelow.getLayerDescription() );
                    layerBelow.setLayerName( current.getLayerName() );
                    layerBelow.setLayerDescription( current.getLayerDescription() );
                    current.setLayerName( layerSwap.getLayerName() );
                    current.setLayerDescription( layerSwap.getLayerDescription() );

                }
            }
        }

        request.setAttribute("estado","OK");
        request.setAttribute("mensaje", "OK");
        
        return mapping.findForward("results_jsp");

    }



}
