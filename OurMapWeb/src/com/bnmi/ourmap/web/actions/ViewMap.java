/*******************************************************************************

com.bnmi.ourmap.web.actions.ViewMap.java
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
import com.bnmi.ourmap.exceptions.SecurityIssue;
import com.bnmi.ourmap.model.Category;
import com.bnmi.ourmap.model.CriteriosCategory;
import com.bnmi.ourmap.model.CriteriosHotspot;
import com.bnmi.ourmap.model.CriteriosKeyword;
import com.bnmi.ourmap.model.CriteriosLayer;
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.model.User;
import com.bnmi.ourmap.web.Constantes;
import com.inga.utils.ReturnMessage;
import com.inga.utils.SqlClauseHelper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/**
 *
 * @author Manuel Camilo Cuesta
 */
public class ViewMap extends Action {


  private static Logger log = Logger.getLogger(ViewMap.class.getName());

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        EasyDelegate del = (EasyDelegate) session.getAttribute(Constantes.DELEGATE);
        ViewMapForm forma = (ViewMapForm) form;
        User principal = (User) session.getAttribute(Constantes.USER);


        // Must do these tasks:
        // 1. Get a map
        // 2. Get a project
        // 3. Get a layer list
        // 4. Get a layer string, just for easyness of use

        // So fill this data up
        Map map = forma.getMap();
        Project p = null;



        // If the map exists, check permissions to see it
        if ( forma.isDbMap() )
        {
            // Get the project
            p = del.getProject( map.getProjectId() );


            // Check for consistency BEFORE checking for permissions
            del.checkProjectConsistency(p);

            ReturnMessage r = del.hasPermission( principal, "view", map.getMapid(), com.bnmi.ourmap.Constantes.MAP ) ;
            boolean hasPermission = r.isSuccess();
            if ( ! hasPermission )
                throw new SecurityIssue(r.getMessage());

        }


        LinkedHashMap<Layer,LinkedHashMap<Category,LinkedHashSet<Keyword>>> layerMap = new LinkedHashMap<Layer,LinkedHashMap<Category,LinkedHashSet<Keyword>>>();

        List<Layer> layers = null;
        List<Category> cats = null;
        List<Keyword> keywords = null;
        LinkedHashSet<Keyword> nocats = new LinkedHashSet<Keyword>();
        User owner = null ;

        String layerStr = null;


        if ( forma.isDbMap() )
        {
            // Get everything from the database

            try
            {
                owner = del.getUser( map.getCreatedBy() );
            }
            catch ( Exception ex )
            {
                owner = new User();
            }



            Integer hotspotsMode = map.getHotspotsMode();
            String disKsStr = map.getDisKs();
            boolean disKs = false;
            if ( disKsStr != null && disKsStr.toLowerCase().startsWith("t"))
                disKs = true;

            boolean catsEnabled = false ;
            if ( map.getCatsEnabled() != null && map.getCatsEnabled().toLowerCase().startsWith("t"))
                catsEnabled = true;

            // Get the layers
            CriteriosLayer criteria = new CriteriosLayer();
            criteria.setMapId( map.getMapid() );
            layers = del.findLayers( criteria );

            ///// Get data for layer selector __________________________________
            for ( Layer la : layers )
            {

                LinkedHashMap<Category,LinkedHashSet<Keyword>> layerData = new LinkedHashMap<Category,LinkedHashSet<Keyword>>();
                layerMap.put(la, layerData);

                if ( disKs )
                {
                    CriteriosHotspot findHs = new CriteriosHotspot();
                    findHs.setHsLayer(la.getLayerId());
                    List<Hotspot> hotspotsInLayer = del.findHotspots(findHs);

                    Category nocat = new Category();
                    nocat.setCatId(-1);
                    LinkedHashSet<Keyword> nocatKeywords = new LinkedHashSet<Keyword>();

                    for ( Hotspot hs : hotspotsInLayer )
                    {
                        try
                        {
                            switch ( hotspotsMode )
                            {
                                case com.bnmi.ourmap.Constantes.PRESET:
                                case com.bnmi.ourmap.Constantes.USER_DEFINED:

                                    Integer kwId = hs.getKeywordId();
                                    Keyword k = del.getKeyword(kwId);

                                    if ( catsEnabled )
                                    {
                                        Integer catId = k.getCatId();
                                        LinkedHashSet<Keyword> catKeywords;

                                        if ( catId != null )
                                        {
                                            //Look if the category is already stored, by its id
                                            Set<Category> catsOnLayer = layerData.keySet();
                                            Category hsCat = null;
                                            for ( Category c1 : catsOnLayer )
                                            {
                                                if ( catId.intValue() == c1.getCatId().intValue() )
                                                {
                                                    hsCat = c1;
                                                    break;
                                                }
                                            }

                                            if ( hsCat == null )
                                            {
                                                hsCat = del.getCategory(catId);
                                                catKeywords = new LinkedHashSet<Keyword>();
                                                layerData.put(hsCat, catKeywords );
                                            }

                                            assert hsCat != null ;

                                            catKeywords = layerData.get(hsCat);
                                            catKeywords.add(k);



                                        }
                                        else
                                        {
                                            // For some reason, there are keywords with no category
                                            // This shouldn't happen, but if that's the case then put the keywords
                                            // on the "nocat" category
                                            nocatKeywords.add(k);

                                        }

                                    }
                                    else
                                    {
                                            nocatKeywords.add(k);
                                    }

                                    break;
                                case com.bnmi.ourmap.Constantes.NONE:
                                    break;
                            }
                        }
                        catch ( Exception ex )
                        {
                            ex.printStackTrace();
                        }
                    }

                    layerData.put(nocat, nocatKeywords );

                }


            }
            ///// End get data for layer selector ______________________________

            

            // Get the categories
            CriteriosCategory catFinder = new CriteriosCategory();
            catFinder.setMapId( map.getMapid() );
            cats = del.findCategorys(catFinder);

            // Get the keywords
            CriteriosKeyword keyFinder = new CriteriosKeyword();
            keyFinder.setMapId( map.getMapid() );
            keywords = del.findKeywords(keyFinder);

            LinkedHashMap<Category,List<Keyword>> catmap = new LinkedHashMap<Category,List<Keyword>>();
            for ( Category cat : cats )
            {
                ArrayList<Keyword> myKeys = new ArrayList<Keyword>();
                for ( Keyword k : keywords )
                {
                    Integer catId = k.getCatId();

                    if ( catId == null )
                        nocats.add(k);
                    else 
                    {
                        if ( catId.equals(cat.getCatId()) )
                           myKeys.add( k );
                    }
                }

                catmap.put(cat, myKeys );
            }

            request.setAttribute("catmap", catmap );

            // Build the layers string, to facilitate to create layers
            SqlClauseHelper sh = new SqlClauseHelper();
            for ( Layer l : layers )
                sh.append(",", "layer_" + l.getLayerId() );
            if ( sh.length() > 0 )
                sh.insert(0, "," );
            layerStr = sh.toString();

        }
        else
        {
            // Create volatile stuff
            p = del.getProject( map.getProjectId() );
            layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);
            cats = (List<Category>) session.getAttribute(Constantes.NEW_MAP_CATEGORIES);
            keywords = (List<Keyword>) session.getAttribute(Constantes.NEW_MAP_KEYWORDS);
            owner = new User();

            for ( Layer la : layers )
                layerMap.put(la, new LinkedHashMap<Category,LinkedHashSet<Keyword>>() );

            layerStr = "";


        }


        request.setAttribute("layerMap", layerMap );
        request.setAttribute("layers", layers );
        request.setAttribute("project", p );
        request.setAttribute("map", map );
        request.setAttribute("layerString", layerStr );
        request.setAttribute("cats", cats );
        request.setAttribute("keywords", keywords );
        request.setAttribute("_nocats_", nocats );
        request.setAttribute("owner", owner );

        return mapping.findForward("viewmap_jsp");


    }

}
