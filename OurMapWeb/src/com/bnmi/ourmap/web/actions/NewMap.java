/*******************************************************************************

com.bnmi.ourmap.web.actions.NewMap.java
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
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.web.Constantes;
import com.bnmi.ourmap.web.Utils;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.exception.RegistroNoExisteException;
import com.inga.utils.SqlClauseHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
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
public class NewMap extends Action {

    private String requestedStatus;
    private String currentStatus;
    private String checkedStatus;
    private HttpSession session;
    private static Logger log = Logger.getLogger(NewMap.class.getName());


    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        session = request.getSession();
        NewMapForm forma = (NewMapForm) form;
        requestedStatus = forma.getStatus();

        try
        {
            Map sessionMap = (Map) session.getAttribute(Constantes.NEW_MAP );
            System.out.println( "Map: " + sessionMap );
            System.out.println( "Project: " + forma.getPid() );
            if ( sessionMap == null )
            {
                if ( forma.getPid() == null )
                {
                    throw new NullPointerException();
                }
            }

            currentStatus  = (String) session.getAttribute(Constantes.NEW_MAP_STATUS);


            String nav = forma.getNav();
            if ( nav != null  && nav.equalsIgnoreCase("back") )
                requestedStatus = this.getValidatedPreviousStep(currentStatus);


            // Pre-condition:
            // requestStatus = unknown
            // currentStatus = unknown
            if ( requestedStatus == null )
            {
                if ( currentStatus == null )
                {
                    // Cold start, reset values
                    reset( session );
                }
                else
                {
                    // User didn't specify the requested status, let's equal it to the session status
                    requestedStatus = currentStatus;
                }
            }
            else
            {
                if ( currentStatus == null )
                {
                    // Cold start, reset values
                    reset( session );
                }
                else
                {
                    // Map creation in process
                }
            }

            // Post-condition: All these objects are not null
            assert( requestedStatus != null );
            assert( currentStatus != null );
            assert( session.getAttribute(Constantes.NEW_MAP_STATUS) != null );

            Utils.info(request, currentStatus + " -> " + requestedStatus );

            // Getting the map
            sessionMap = (Map) session.getAttribute(Constantes.NEW_MAP );
            if ( sessionMap == null )
            {
                sessionMap = new Map();
                session.setAttribute(Constantes.NEW_MAP , sessionMap );
                request.setAttribute(Constantes.NEW_MAP , sessionMap );
            }
            updateMap(forma.getMap(), sessionMap);
            assert( session.getAttribute(Constantes.NEW_MAP) != null );


            float requestedFloat = Float.parseFloat(requestedStatus);

            // Set the furthest step achieved of map creation
            float furthestFloat = 0;
            if ( session.getAttribute(Constantes.FURTHEST_NEW_MAP_STATUS) != null)
                furthestFloat = Float.parseFloat( (String) session.getAttribute(Constantes.FURTHEST_NEW_MAP_STATUS));
            if ( requestedFloat > furthestFloat )
            {
                session.setAttribute( Constantes.FURTHEST_NEW_MAP_STATUS, requestedStatus );
                request.setAttribute("firstTimeHere", "yes" );
            }

            boolean verified = true;
            // Determining if it's going up
            float requestedFl = Float.parseFloat(requestedStatus);
            float currentFl   = Float.parseFloat(currentStatus);
            if ( requestedFl > currentFl )
                // It is going up
                verified = verifyConsistency(requestedStatus,request);




            final String dispatchStatus = (!verified) ? checkedStatus : process( forma, sessionMap, request );

            session.setAttribute( Constantes.NEW_MAP_STATUS, dispatchStatus );
            session.setAttribute( "previousMapStatus", currentStatus );
            addHistory( dispatchStatus );

            // Dispatch
            if ( dispatchStatus.equals("10"))
            {
                reset( session );
                return null;
            }
            else if ( dispatchStatus.equals("9") )
            {
                reset( session );
                response.sendRedirect("viewmap.do?mapid=" + String.valueOf((Integer) request.getAttribute("mapid")));
                return null;
            }
            else
            {
                if ( dispatchStatus.equals("-1") )
                {
                    response.sendRedirect("nosession.jsp");
                    return null;
                }

                ActionForward forward = dispatchStatus(mapping, dispatchStatus );

                return forward;
            }

        }
        catch ( java.lang.NullPointerException nux )
        {
            response.sendRedirect("nosession.jsp");
            return null;
        }
    }

    private void addHistory(String step) {

        Vector<String> history = (Vector<String>) session.getAttribute("new_map_history");
        if ( history.contains(step) )
            return;
        String previousStep = getPreviousStep( step );
        int index = history.indexOf( previousStep );
        history.insertElementAt(step, index + 1 );
    }



    private String getValidatedPreviousStep( String step ) {
        Map newMap = (Map) session.getAttribute( Constantes.NEW_MAP );

        List<String> invalidStates = new ArrayList<String>();
        currentStatus = (String) session.getAttribute( Constantes.NEW_MAP_STATUS );
        float currentFl = Float.parseFloat( currentStatus );
        if ( currentFl == 7f )
        {
            Integer hotspotsMode = newMap.getHotspotsMode();
            if ( hotspotsMode == null )
                hotspotsMode = com.bnmi.ourmap.Constantes.NONE ;

            switch ( hotspotsMode )
            {
                case com.bnmi.ourmap.Constantes.PRESET :
                    break;
                case com.bnmi.ourmap.Constantes.USER_DEFINED:
                    break;
                case com.bnmi.ourmap.Constantes.NONE:
                    return "5";
            }
        }


        try
        {
            Vector<String> history = (Vector<String>) session.getAttribute("new_map_history");

            if ( ! history.isEmpty() )
            {
                String currStepStr;
                float currStep;
                float stepFl = Float.parseFloat( step );
                for ( int i = history.size() - 1; i >= 0; i-- )
                {
                    currStepStr = history.elementAt(i);

                    if ( invalidStates.contains(currStepStr) )
                        continue;

                    currStep = Float.parseFloat( currStepStr );
                    if ( currStep < stepFl )
                    {
                        if ( (int) currStep == currStep )
                            return String.valueOf((int) currStep);
                        else
                            return String.valueOf(currStep);
                    }
                }
            }

        }
        catch ( Exception ex )
        {
            // niente
        }

        return "0";


    }

    private String getPreviousStep(String step ) {

        try
        {
            Vector<String> history = (Vector<String>) session.getAttribute("new_map_history");

            if ( ! history.isEmpty() )
            {
                String currStepStr;
                float currStep;
                float stepFl = Float.parseFloat( step );
                for ( int i = history.size() - 1; i >= 0; i-- )
                {
                    currStepStr = history.elementAt(i);
                    currStep = Float.parseFloat( currStepStr );
                    if ( currStep < stepFl )
                    {
                        if ( (int) currStep == currStep )
                            return String.valueOf((int) currStep);
                        else
                            return String.valueOf(currStep);
                    }
                }
            }

        }
        catch ( Exception ex )
        {
            // niente
        }

        return "0";



    }

    private float getLastHistory() {
        Vector<String> history = (Vector<String>) session.getAttribute("new_map_history");
        if ( history != null && !history.isEmpty() )
        {
            try
            {
                return Float.parseFloat( history.lastElement() );
            }
            catch ( Exception ex )
            {

            }
        }
        return -1.0f;
    }

    private void reset(HttpSession session) {
        
        String resetValue = "0";
        session.removeAttribute( Constantes.NEW_MAP_STATUS );

        currentStatus = new String(resetValue);
        requestedStatus = new String(resetValue);
        Vector<String> history = new Vector<String>();

        session.setAttribute( Constantes.NEW_MAP_STATUS, currentStatus );
        session.setAttribute( "previousMapStatus", "-1" );
        session.setAttribute( "new_map_history", history );

        Map newMap = new Map();
        List<Layer> layers = new ArrayList<Layer>();
        session.setAttribute(Constantes.NEW_MAP, newMap );
        session.setAttribute(Constantes.NEW_MAP_LAYERS, layers );
        session.removeAttribute(Constantes.FURTHEST_NEW_MAP_STATUS);
        session.setAttribute(Constantes.FURTHEST_NEW_MAP_STATUS, resetValue );
        List<Keyword> keywords = new ArrayList<Keyword>();
        List<Category> cats = new ArrayList<Category>();
        session.setAttribute(Constantes.NEW_MAP_KEYWORDS, keywords );
        session.setAttribute(Constantes.NEW_MAP_CATEGORIES, cats );
        session.setAttribute("catsnow", "f" );

    }

    private String process( NewMapForm forma, Map sessionMap, HttpServletRequest request ) throws NoConnectionException, BDException, RegistroNoExisteException, IOException, SecurityIssue {

        String dispatchStatus = "0";

        EasyDelegate del = (EasyDelegate) request.getSession().getAttribute(Constantes.DELEGATE);

        updateMap( forma.getMap(), sessionMap );

        // Here, do the checks when it matters to you where the request is comming from
        // You are comming from state 4, save your layers first
        if ( currentStatus.equals("4") )
        {
            String layersListStr = (String) request.getParameter("layersListStr");
            if ( layersListStr != null )
            {
                List<Layer> layers = new ArrayList<Layer>();
                session.setAttribute(Constantes.NEW_MAP_LAYERS, layers );
                String layerValues[] = layersListStr.split("#78954#");

                int index = 0;
                for ( int i = 0; i < layerValues.length; i++ )
                {
                    try
                    {
                        String layerName = layerValues[i];
                        String layerDescription = layerValues[++i];
                        Layer l = new Layer();
                        l.setLayerName( layerName );
                        l.setLayerDescription( layerDescription );
                        l.setIndex(index++);
                        layers.add(l);
                    }
                    catch ( Exception ex )
                    {

                    }
                }

            }
        }


        dispatchStatus = requestedStatus;

        // This goes after setting "newStatus" above
        // Here, do the checks when it matters to you where the request wants to go
        if ( requestedStatus.equals("1") )
        {
            
            Project p = del.getProject( sessionMap.getProjectId() );
            List<Layer> layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);
            String layerStr = "";
            // Build the layers string, to make it easy to create layers
            SqlClauseHelper sh = new SqlClauseHelper();
            for ( Layer l : layers )
                sh.append(",", "layer_" + l.getLayerId() );
            if ( sh.length() > 0 )
                sh.insert(0, "," );
            layerStr = sh.toString();

            request.setAttribute("map", sessionMap );
            request.setAttribute("project", p );
            request.setAttribute("layers", layers );
            request.setAttribute("layerStr", layerStr );
            request.setAttribute("no_edit", "yes" );
            request.setAttribute("no_map", "yes" );

        }
        else if ( requestedStatus.equals("3"))
        {
            int opt = (Integer) session.getAttribute(Constantes.BASE_OPTION_SELECTED);
            switch ( opt )
            {
                case 1:
                    dispatchStatus = requestedStatus;
                    break;
                default:
                    dispatchStatus = currentStatus;
                    break;
            }


        }
        else if ( requestedStatus.equals("3.1") )
        {
            Project p = del.getProject( sessionMap.getProjectId() );
            List<Layer> layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);
            String layerStr = "";
            // Build the layers string, to make it easy to create layers
            SqlClauseHelper sh = new SqlClauseHelper();
            for ( Layer l : layers )
                sh.append(",", "layer_" + l.getLayerId() );
            if ( sh.length() > 0 )
                sh.insert(0, "," );
            layerStr = sh.toString();

            request.setAttribute("map", sessionMap );
            request.setAttribute("project", p );
            request.setAttribute("layers", layers );
            request.setAttribute("layerStr", layerStr );
            request.setAttribute("no_edit", "yes" );
            
        }
        else if (  requestedStatus.equals("4") )
        {
            List<Layer> layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);
            request.setAttribute("numLayers", layers.size());
            request.setAttribute("layers", layers );

        }
        else if ( requestedStatus.equals("4.1") )
        {
            Project p = del.getProject( sessionMap.getProjectId() );
            List<Layer> layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);
            String layerStr = "";
            // Build the layers string, to make it easy to create layers
            SqlClauseHelper sh = new SqlClauseHelper();
            for ( Layer l : layers )
                sh.append(",", "layer_" + l.getLayerId() );
            if ( sh.length() > 0 )
                sh.insert(0, "," );
            layerStr = sh.toString();

            request.setAttribute("map", sessionMap );
            request.setAttribute("project", p );
            request.setAttribute("layers", layers );
            request.setAttribute("layerStr", layerStr );
            request.setAttribute("no_edit", "yes" );

        }
        /*
        else if ( requestedStatus.equals("6.9") )
        {
            Project p = del.getProject( sessionMap.getProjectId() );
            List<Layer> layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);
            String layerStr = "";
            // Build the layers string, to make it easy to create layers
            SqlClauseHelper sh = new SqlClauseHelper();
            for ( Layer l : layers )
                sh.append(",", "layer_" + l.getLayerId() );
            if ( sh.length() > 0 )
                sh.insert(0, "," );
            layerStr = sh.toString();

            
            LinkedHashMap<Layer,LinkedHashMap<Category,LinkedHashSet<Keyword>>> layerMap = new LinkedHashMap<Layer,LinkedHashMap<Category,LinkedHashSet<Keyword>>>();
            List<Category> cats = new ArrayList<Category>();
            List<Keyword> keywords = new ArrayList<Keyword>();
            LinkedHashSet<Keyword> nocats = new LinkedHashSet<Keyword>();
            request.setAttribute("layerMap", layerMap );
            request.setAttribute("layerString", layerStr );
            request.setAttribute("cats", cats );
            request.setAttribute("keywords", keywords );
            request.setAttribute("_nocats_", nocats );
            
            
            request.setAttribute("map", sessionMap );
            request.setAttribute("project", p );
            request.setAttribute("layers", layers );
            request.setAttribute("layerStr", layerStr );
            request.setAttribute("no_edit", "yes" );

        }
    */
        else if ( requestedStatus.equals("7.5") )
        {
            Project p = del.getProject( sessionMap.getProjectId() );
            List<Layer> layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);
            String layerStr = "";
            // Build the layers string, to make it easy to create layers
            SqlClauseHelper sh = new SqlClauseHelper();
            for ( Layer l : layers )
                sh.append(",", "layer_" + l.getLayerId() );
            if ( sh.length() > 0 )
                sh.insert(0, "," );
            layerStr = sh.toString();

            request.setAttribute("map", sessionMap );
            request.setAttribute("project", p );
            request.setAttribute("layers", layers );
            request.setAttribute("layerStr", layerStr );
            request.setAttribute("no_edit", "yes" );

        }

        else if (  requestedStatus.equals("9") )
        {
            List<Layer> layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);

            // Create map
            del.createMap(sessionMap);

            // Create layers
            int cont = 0;
            for ( Layer la : layers )
            {
                la.setMapId( sessionMap.getMapid() );
                la.setIndex( cont++ );
                del.createLayer(la);
            }

            int hotspotsMode = -1;
            if ( sessionMap.getHotspotsMode() != null )
                hotspotsMode = sessionMap.getHotspotsMode();

            // Save keywords and cateogories only if the map allows it
            if ( hotspotsMode == 1 )
            {
                // Create categories
                // To save the cat ids
                Hashtable<String,Integer> catIds = new Hashtable<String,Integer>();
                List<Category> cats = (List<Category>) session.getAttribute(Constantes.NEW_MAP_CATEGORIES);
                for ( Category c : cats )
                {
                    if ( c.getCatName() == null )
                        continue;
                    c.setMapId( sessionMap.getMapid() );
                    del.createCategory(c);
                    catIds.put( c.getCatName(), c.getCatId() );
                }

                // Save keywords
                List<Keyword> keywords = (List<Keyword>) session.getAttribute(Constantes.NEW_MAP_KEYWORDS);
                for( Keyword k : keywords )
                {
                    Integer catId = null;
                    if ( k.getCatName() != null )
                         catId = catIds.get( k.getCatName() );
                    k.setCatId( catId );
                    k.setMapId( sessionMap.getMapid() );
                    del.createKeyword(k);
                }

            }

            request.setAttribute( "pid" , new Integer(sessionMap.getProjectId()) );
            request.setAttribute( "mapid", new Integer(sessionMap.getMapid()));
            //reset(session);
            dispatchStatus = "9";
            
        }
        else if (  requestedStatus.equals("10") )
        {
            //reset(session);
            dispatchStatus = "10";
        }
        else if ( requestedStatus.equals("7.1") )
        {

            List<Layer> layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);
            request.setAttribute("layers", layers );

            boolean allLayersHaveIcons = true;
            for ( Layer la : layers )
                if ( la.getIconId() == null )
                    allLayersHaveIcons = false;

            if ( allLayersHaveIcons )
                request.setAttribute("allLayersHaveIcons", "yes" );
            
        }
        else if ( requestedStatus.equals("7.2") )
        {
            
            List<Category> cats = (List<Category>) session.getAttribute(Constantes.NEW_MAP_CATEGORIES);


            request.setAttribute("cats", cats );

            boolean allCatsAndKeysHaveIcons = true;
            for ( Category c : cats )
                  if ( c.getIconId() == null )
                      allCatsAndKeysHaveIcons = false;

            if ( allCatsAndKeysHaveIcons )
                request.setAttribute("allCatsAndKeysHaveIcons", "yes");
        }
        else if ( requestedStatus.equals("7.3") )
        {
            List<Keyword> keywords = (List<Keyword>) session.getAttribute(Constantes.NEW_MAP_KEYWORDS);
            request.setAttribute("keywords", keywords );

            boolean allKeywordsHaveIcons = true;
            for ( Keyword k : keywords )
                if ( k.getIconId() == null )
                    allKeywordsHaveIcons = false;

            if ( allKeywordsHaveIcons )
                request.setAttribute("allKeywordsHaveIcons", del);
            
        }


        return dispatchStatus;
        
    }

    private ActionForward dispatchStatus(ActionMapping mapping,String status ) {
        
        if ( status.equals("-1") )
        {
            log.info( "Null status at map creation" );
            return null;
        }
        if ( status.equals("0"))
        {
            return mapping.findForward( "newmap_0" );
        }
        else if ( status.equals("1")) {
            return mapping.findForward("viewmap_do");
        }
        else if ( status.equals("2")) {
            return mapping.findForward("newmap_3");
        }
        else if ( status.equals("3")) {
            return mapping.findForward("newmap_3");
        }
        else if ( status.equals("3.1")) {
            return mapping.findForward("viewmap_do");
        }
        else if ( status.equals("4")) {
            return mapping.findForward("newmap_4");
        }
        else if ( status.equals("4.1")) {
            return mapping.findForward("viewmap_do");
        }
        else if ( status.equals("5")) {
            return mapping.findForward("newmap_5");
        }
        else if ( status.equals("6")) {
            return mapping.findForward("newmap_6");
        }
        else if ( status.equals("7")) {
            return mapping.findForward("newmap_7");
        }
        else if ( status.equals("8")) {
            return mapping.findForward("newmap_8");
        }
        else if ( status.equals("9")) {
            return mapping.findForward("viewmap_do");
        }
        else if ( status.equals("10")) {
                return null;
        }
        else if ( status.equals("6.5")) {
            return mapping.findForward("newmap_65");
        }
        else if ( status.equals("6.6")) {
            return mapping.findForward("newmap_66");
        }
        else if ( status.equals("6.4")) {
            return mapping.findForward("newmap_64");
        }
        else if ( status.equals("7.1")) {
            return mapping.findForward("newmap_71");
        }
        else if ( status.equals("7.2")) {
            return mapping.findForward("newmap_72");
        }
        else if ( status.equals("7.3")) {
            return mapping.findForward("newmap_73");
        }
        else if ( status.equals("7.5")) {
            return mapping.findForward("viewmap_do");
        }




        else
            return null;
    }

    private void updateMap(Map registro, Map sessionMap ) {

        if ( registro.getMapname() != null )
            sessionMap.setMapname( registro.getMapname() );
        if ( registro.getLeftBottom() != null )
            sessionMap.setLeftBottom( registro.getLeftBottom() );
        if ( registro.getRightTop() != null )
            sessionMap.setRightTop( registro.getRightTop() );
        if ( registro.getBackmap() != null )
            sessionMap.setBackmap( registro.getBackmap() );
        if ( registro.getZoom() != null )
            sessionMap.setZoom( registro.getZoom() );
        if ( registro.getCenter() != null )
            sessionMap.setCenter( registro.getCenter() );
        if ( registro.getProjectId() != null )
            sessionMap.setProjectId( registro.getProjectId() );
        if ( registro.getSubtitle() != null )
            sessionMap.setSubtitle( registro.getSubtitle() );
        if ( registro.getHotspotsMode() != null )
            sessionMap.setHotspotsMode( registro.getHotspotsMode() );
        if ( registro.getDescription() != null )
            sessionMap.setDescription( registro.getDescription() );
        if ( registro.getDisKs() != null )
            sessionMap.setDisKs( registro.getDisKs() );
        if ( registro.getIconsMode() != null )
        {
            Integer previousIconMode = sessionMap.getIconsMode();
            if ( previousIconMode != null && previousIconMode.intValue() != registro.getIconsMode().intValue() )
                resetIcons(session);
            sessionMap.setIconsMode( registro.getIconsMode() );
        }
        if ( registro.getCatsEnabled() != null )
            sessionMap.setCatsEnabled( registro.getCatsEnabled() );
        if ( registro.getDisplayMode() != null )
            sessionMap.setDisplayMode( registro.getDisplayMode() );

    }

    public void resetIcons(HttpSession session) {
        List<Layer> layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);
        List<Category> cats = (List<Category>) session.getAttribute(Constantes.NEW_MAP_CATEGORIES);
        List<Keyword> keywords = (List<Keyword>) session.getAttribute(Constantes.NEW_MAP_KEYWORDS);

        for ( Layer l : layers )
            l.setIconId(null);
        for ( Category c : cats )
            c.setIconId( null );
        for ( Keyword k : keywords )
            k.setIconId(null);
    }

    /** Must set checkedStatus and set "map-error" attribute on the request
     *
     * @param requestedStatus
     * @param request
     * @return
     */
    private boolean verifyConsistency( final String requestedStatus, HttpServletRequest request ) {

        Map sessionMap = (Map) session.getAttribute(Constantes.NEW_MAP);

        float req = Float.parseFloat( requestedStatus );

        if ( req == 10 )
            return true;

        if ( req > 0 )
        {
            if ( sessionMap.getProjectId() == null )
            {
                checkedStatus = "-1";
                session.setAttribute(Constantes.FURTHEST_NEW_MAP_STATUS, "0" );
                return false;
            }
            if ( sessionMap.getMapname() == null )
            {
                request.setAttribute("map-error", "Enter a name for your map" );
                checkedStatus = "0";
                session.setAttribute(Constantes.FURTHEST_NEW_MAP_STATUS, "0" );
                return false;
            }
        }
        if ( req > 4 )
        {
            // Check layers
            List<Layer> layers = (List<Layer>) session.getAttribute(Constantes.NEW_MAP_LAYERS);
            if ( layers.isEmpty() )
            {
                request.setAttribute("map-error", "The map must have at least one layer" );
                checkedStatus = "4";
                return false;
            }
        }
        if ( req > 6 )
        {
            List<Keyword> keywords = (List<Keyword>) session.getAttribute(Constantes.NEW_MAP_KEYWORDS);
            if ( sessionMap.getHotspotsMode() != null && sessionMap.getHotspotsMode().intValue() == 1 )
            {
                if ( keywords.isEmpty() )

                {
                    request.setAttribute("map-error", "You must enter at least one keyword");
                    checkedStatus = "6";
                    return false;
                }
            }
        }
        if ( req > 6.5f )
        {
            List<Keyword> keywords = (List<Keyword>) session.getAttribute(Constantes.NEW_MAP_KEYWORDS);
            String catsnow = sessionMap.getCatsEnabled();

            if ( catsnow != null && catsnow.equalsIgnoreCase("t") )
            for ( Keyword k : keywords )
            {
                if ( k.getCatName() == null )
                {
                    request.setAttribute("map-error", "Note: All keywords must be organized into a category");
                    checkedStatus = "6.5";
                    return false;
                }
            }
        }

        return true;
    }

}
