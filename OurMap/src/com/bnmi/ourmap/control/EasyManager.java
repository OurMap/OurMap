/*******************************************************************************

com.bnmi.ourmap.control.Constantes.java
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
package com.bnmi.ourmap.control;
 
import com.bnmi.ourmap.Constantes;
import com.bnmi.ourmap.dao.ActionDAO;
import com.bnmi.ourmap.dao.CatKwDAO;
import com.bnmi.ourmap.dao.CategoryDAO;
import com.bnmi.ourmap.dao.DAOFactory;
import com.bnmi.ourmap.dao.HotspotDAO;
import com.bnmi.ourmap.dao.HotspotObjectDAO;
import com.bnmi.ourmap.dao.IconDAO;
import com.bnmi.ourmap.dao.KeywordDAO;
import com.bnmi.ourmap.dao.LayerDAO;
import com.bnmi.ourmap.dao.MapDAO;
import com.bnmi.ourmap.dao.ObjCategoryDAO;
import com.bnmi.ourmap.dao.ObjectDAO;
import com.bnmi.ourmap.dao.PermissionDAO;
import com.bnmi.ourmap.dao.ProjectDAO;
import com.bnmi.ourmap.dao.RolActionDAO;
import com.bnmi.ourmap.dao.RolDAO;
import com.bnmi.ourmap.dao.UserDAO;
import com.bnmi.ourmap.dao.UserProjectDAO;
import com.bnmi.ourmap.dao.UserRolDAO;
import com.bnmi.ourmap.daoimpl.DrupalPermissionDAO;
import com.bnmi.ourmap.daoimpl.PostgresUtilDAO;
import com.bnmi.ourmap.exceptions.KeywordHasHotspots;
import com.bnmi.ourmap.exceptions.LayerHasHotspots;
import com.bnmi.ourmap.exceptions.SecurityIssue;
import com.bnmi.ourmap.exceptions.UserDontExists;
import com.bnmi.ourmap.exceptions.WrongPassword;
import com.bnmi.ourmap.model.Action;
import com.bnmi.ourmap.model.Category;
import com.bnmi.ourmap.model.CriteriosCategory;
import com.bnmi.ourmap.model.CriteriosHotspot;
import com.bnmi.ourmap.model.CriteriosHotspotObject;
import com.bnmi.ourmap.model.CriteriosKeyword;
import com.bnmi.ourmap.model.CriteriosLayer;
import com.bnmi.ourmap.model.CriteriosMap;
import com.bnmi.ourmap.model.CriteriosObjCategory;
import com.bnmi.ourmap.model.CriteriosObject;
import com.bnmi.ourmap.model.CriteriosProject;
import com.bnmi.ourmap.model.CriteriosRol;
import com.bnmi.ourmap.model.CriteriosRolAction;
import com.bnmi.ourmap.model.CriteriosUser;
import com.bnmi.ourmap.model.EasyObject;
import com.bnmi.ourmap.model.Hotspot;
import com.bnmi.ourmap.model.HotspotObject;
import com.bnmi.ourmap.model.Keyword;
import com.bnmi.ourmap.model.Layer;
import com.bnmi.ourmap.model.Map;
import com.bnmi.ourmap.model.ObjCategory;
import com.bnmi.ourmap.model.Permission;
import com.bnmi.ourmap.model.Project;
import com.bnmi.ourmap.model.Rol;
import com.bnmi.ourmap.model.RolAction;
import com.bnmi.ourmap.model.User;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.exception.RegistroNoExisteException;
import com.inga.utils.ReturnMessage;
import com.inga.utils.SigarUtils;
import java.sql.Connection;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Camilo
 */
public class EasyManager {


    private static Logger log = Logger.getLogger(EasyManager.class);
    
    private Connection conn = null;
    private Connection drupalConn = null;
    private com.inga.security.User user = null;
    private com.bnmi.ourmap.model.User myUser = null;

    private UserDAO userDao;
    private ActionDAO actionDao;
    private ProjectDAO projectDao;
    private RolActionDAO rolActionDao;
    private RolDAO roleDao;
    private UserProjectDAO userProjectDao;
    private UserRolDAO userRolDao;
    private MapDAO mapDao;
    private LayerDAO layerDao;
    private IconDAO iconDao;
    private HotspotDAO hotspotDao;
    private ObjectDAO objectDao;
    private HotspotObjectDAO hotspotObjectDao;
    private CategoryDAO categoryDao;
    private KeywordDAO keywordDao;
    private CatKwDAO catKwDao;
    private ObjCategoryDAO objCategoryDao;
    private PermissionDAO permissionDao;

    public static int dao;
    //@PersistenceContext(name="EasyPU1")
    //static EntityManager drupalEm = null;
 

    public EasyManager(com.inga.security.User pUser, 
                       final Connection pConn,
                       final Connection pDrupalConn ) {
        user = pUser;
        conn = pConn;
        drupalConn = pDrupalConn;

        try
        {
            this.setAutoCommit(true);
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }

        dao = DAOFactory.FILE ;
        DAOFactory factory = DAOFactory.getDAOFactory( dao );

        userDao = factory.getUserDAO();
        userDao.setConnection(drupalConn);
        userDao.setUser(user);

        projectDao = factory.getProjectDAO();
        projectDao.setConnection( drupalConn );
        projectDao.setUser( user );


        actionDao = factory.getActionDAO();
        actionDao.setConnection( conn );
        actionDao.setUser( user );


        rolActionDao = factory.getRolActionDAO();
        rolActionDao.setConnection( conn );
        rolActionDao.setUser( user );

        roleDao = factory.getRolDAO();
        roleDao.setConnection( conn );
        roleDao.setUser( user );

        userProjectDao = factory.getUserProjectDAO();
        userProjectDao.setConnection( conn );
        userProjectDao.setUser( user );

        userRolDao = factory.getUserRolDAO();
        userRolDao.setConnection( conn );
        userRolDao.setUser( user );

        mapDao = factory.getMapDAO();
        mapDao.setConnection( conn );
        mapDao.setUser( user );

        layerDao = factory.getLayerDAO();
        layerDao.setConnection(conn);
        layerDao.setUser(user);


        iconDao = factory.getIconDAO();
        iconDao.setUser( user );
        iconDao.setConnection(conn);
        

        
        hotspotDao = factory.getHotspotDAO();
        hotspotDao.setConnection(conn);
        hotspotDao.setUser(user);

        objectDao = factory.getObjectDAO();
        objectDao.setConnection( conn );
        objectDao.setUser( user );

        hotspotObjectDao = factory.getHotspotObjectDAO();
        hotspotObjectDao.setConnection(conn);
        hotspotObjectDao.setUser( user );

        categoryDao = factory.getCategoryDAO();
        categoryDao.setConnection( conn );
        categoryDao.setUser( user );

        keywordDao = factory.getKeywordDAO();
        keywordDao.setConnection( conn );
        keywordDao.setUser( user );

        catKwDao = factory.getCatKwDAO();
        catKwDao.setConnection( conn );
        catKwDao.setUser( user );

        objCategoryDao = factory.getObjCategoryDAO();
        objCategoryDao.setConnection( conn );
        objCategoryDao.setUser( user );

        permissionDao = factory.getPermissionDAO();
        permissionDao.setConnection( conn );
        permissionDao.setUser( user );

        if ( permissionDao instanceof DrupalPermissionDAO )
            ((DrupalPermissionDAO) permissionDao ).setDrupalConn(drupalConn);


    }


    /*
    private void drupalInit() {
        
        if ( drupalEm == null )
        {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("EasyPU1");
            drupalEm = emf.createEntityManager();
        }
        drupalConn = ((oracle.toplink.essentials.ejb.cmp3.EntityManager) drupalEm ).getServerSession().getDefaultConnectionPool().acquireConnection().getConnection();

    }
    */
    
    
    
    /** List all users */
    public List<com.bnmi.ourmap.model.User> listUsers() throws NoConnectionException, BDException {

        CriteriosUser criteria = new CriteriosUser();
        return findUsers( criteria );
    }

    /** Validates the user and password
     *  @exception WrongPassword The users exists but the password is wrong
     *  @exception UserDontExists The user name is wrong
     *  @return True if the user and password match 
     */
    public boolean validate(String userid, String password) throws NoConnectionException, BDException, UserDontExists, WrongPassword {

        if ( userid != null && userid.equals(Constantes.VISITOR_ID) )
            return true;
        
        CriteriosUser criteria = new CriteriosUser();
        criteria.setId(userid);
        //criteria.setNombre(userid);
        List<com.bnmi.ourmap.model.User> users = userDao.find(criteria);

        if ( users.isEmpty() )
            throw new UserDontExists();

        com.bnmi.ourmap.model.User u = users.get(0);
        if ( ! u.getPwd().equalsIgnoreCase( password ) )
            throw new WrongPassword();

        return true;
        
    }

    /** Validates the user and password
     *  @exception WrongPassword The users exists but the password is wrong
     *  @exception UserDontExists The user name is wrong
     *  @return True if the user and password match
     */
    public boolean validateByName(String userid, String password) throws NoConnectionException, BDException, UserDontExists, WrongPassword {

        CriteriosUser criteria = new CriteriosUser();
        //criteria.setId(userid);
        criteria.setNombre(userid);
        List<com.bnmi.ourmap.model.User> users = userDao.find(criteria);

        if ( users.isEmpty() )
            throw new UserDontExists();

        com.bnmi.ourmap.model.User u = users.get(0);
        if ( ! u.getPwd().equalsIgnoreCase( password ) )
            throw new WrongPassword();

        return true;

    }



    /** Lists actions by rol
     *
     * @param rol
     * @return List of actions of the given rol
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Action> getActions(Rol rol) throws NoConnectionException, BDException {
        CriteriosRolAction criteria = new CriteriosRolAction();
        criteria.setRol(rol.getRol());
        List<RolAction> ras = rolActionDao.find(criteria);
        ArrayList<Action> as = new ArrayList<Action>();
        for ( RolAction ra : ras )
            as.add( new Action(ra.getAid()));

        return as;
    }

    public User getUser(String userid) throws NoConnectionException, BDException, BDException, RegistroNoExisteException {

        if ( userid != null && userid.equals(Constantes.VISITOR_ID ) )
            return getVisitor();

        return userDao.get(userid );
        
    }

    private ReturnMessage internalHasPermission(User u, String actionId, Integer elementId, Integer elementType ) throws NoConnectionException, BDException {

        // overlord has full access
        if ( isOverlord(u) )
            return new ReturnMessage(true,null);

        // No element given, or element type. I'll check the validation based only
        // actions granted to roles
        if ( elementId == null && elementType == null )
        {

            CriteriosRolAction criteria = new CriteriosRolAction();
            criteria.setRol( u.getRol() );
            List<RolAction> ras = rolActionDao.find(criteria);

            for ( RolAction a : ras )
                if ( a.getAid().equals( actionId) )
                    return new ReturnMessage(true,null); // the role has the requested action


        }


        // Both element id and element type were given, we are talking of a
        // map, hotspot, object, or project here
        if ( elementId != null && elementType != null )
        {

            boolean isCreator = isCreator(u.getId(), elementId, elementType);
            boolean isOwner   = isOwner( u.getId(), elementId, elementType );
            boolean isMember  = isMember( u.getId(), elementId, elementType );

            Map map = null;

            switch ( elementType )
            {
                // Rules for PROJECTS
                // -------------------------------------------------------------
                case Constantes.PROJECT:

                    // Actions:
                    // 1. view
                    // 2. create_map


                    if ( actionId.equalsIgnoreCase("view"))
                        return new ReturnMessage(isCreator | isOwner | isMember, "You are not a member of this project") ;
                    if ( actionId.equalsIgnoreCase("create_map"))
                        return new ReturnMessage(isCreator | isOwner, "Only the project creator or the owners can add maps to this project." ) ;
                    if ( actionId.equalsIgnoreCase("creator-owner"))
                        return new ReturnMessage(isCreator | isOwner, "You don't have ownership rights on this project." ) ;
                    else
                        return new ReturnMessage(isCreator,"You are not the project creator.") ;


                // Rules for maps
                // -------------------------------------------------------------
                case Constantes.MAP:

                    // Actions:
                    // 1. delete
                    // 2. add-hotspots
                    // 3. view
                    // 4. map-creator-owner
                    // 5. manage_owners
                    // 6. manage_members

                    // Check if you have permissions on the map's project
                    boolean isProjectMember = false;

                    try
                    {
                        map = getMap( elementId );
                        Project p = getProject(map.getProjectId());

                        // If you are project owner (creator), then you have permission for
                        // all the actions on a map
                        if ( isCreator( u.getId(), p.getId(), Constantes.PROJECT) )
                            return new ReturnMessage(true,null);

                        // True, if the user is a creator, owner or member of the project
                        ReturnMessage rProject = hasPermission(u, "view", p.getId(), Constantes.PROJECT );
                        if ( rProject.isSuccess() )
                            isProjectMember = true;

                    }
                    catch ( Exception ex )
                    {
                        return new ReturnMessage(false, ex.getMessage() );
                    }

                    if ( actionId.equalsIgnoreCase("delete"))
                    {
                        return new ReturnMessage(isCreator,"Only the map creator can delete this map.")  ;
                    }

                    // Second question : Who should be allowed to add new map features/ hotspots to the map?
                    if ( actionId.equalsIgnoreCase("add-hotspot") )
                    {
                        
                        try
                        {
                            Integer editableHs = map.getHotspotsEditable();

                            switch ( editableHs )
                            {
                                case Constantes.ADD_HS_OWNERS_ONLY:
                                    if ( isProjectMember )
                                        return new ReturnMessage(isCreator | isOwner, "Only the creator or the owner of this map can add hotspots." );
                                    else
                                        return new ReturnMessage(false,"You are not a member of this project.");
                                case Constantes.ADD_HS_OWNERS_MEMBERS_ONLY:
                                    if ( isProjectMember )
                                       return new ReturnMessage(isCreator | isOwner | isMember, "You are not a member of this map." );
                                    else
                                       return new ReturnMessage(false,"You are not a member of this project");
                                case Constantes.ADD_HS_ALL:
                                    return new ReturnMessage(true,null);
                            }

                        }
                        catch ( Exception ex )
                        {
                            return new ReturnMessage(false,"" + ex.getMessage() );
                        }

                    }

                    // First question : "Please select a publishing option"
                    if ( actionId.equalsIgnoreCase("view") )
                    {

                        try
                        {

                            // Owners and co-owners have always permission for the 1st question
                            if ( isCreator | isOwner )
                                return new ReturnMessage(true,null);

                            Integer mapPrivacy  = map.getPrivacy();
                            if ( mapPrivacy == null )
                                mapPrivacy = com.bnmi.ourmap.Constantes.PRIVATE ;

                            switch ( mapPrivacy )
                            {

                                // Grant only to creator or co-owner
                                case Constantes.PRIVATE:
                                    if ( isProjectMember )
                                        return new ReturnMessage(isCreator | isOwner, "Only the creator or the owner of this map can view this map." );
                                    else
                                        return new ReturnMessage( false, "Only the creator or the owner of this map can view this map." );

                                // Grant to creator, co-owner or member
                                case Constantes.SECURE:
                                    if ( isProjectMember )
                                       return new ReturnMessage(isCreator | isOwner | isMember, "You are not a member of this map.");
                                    else
                                       return new ReturnMessage( false, "You are not a member of this map.");
                                // Open for all. Access granted
                                case Constantes.PUBLIC:
                                    return new ReturnMessage(true,null);
                            }

                        }
                        catch ( Exception ex )
                        {
                            return new ReturnMessage(false, ex.getMessage() );
                        }
                    }

                    if ( actionId.equalsIgnoreCase("map-creator-owner"))
                    {

                        if ( isProjectMember )
                            return new ReturnMessage(isCreator | isOwner,"You don't have ownership rights on this map." );
                        else
                            return new ReturnMessage( false, "You are not a member of this project." );
                    }


                    if ( actionId.equalsIgnoreCase("manage_owners"))
                    {
                        // Can manage owners if the user is an owner
                        if ( isProjectMember )
                            return new ReturnMessage(isCreator | isOwner,"You are not an owner of this project." );
                        else
                            return new ReturnMessage(false, "You are not a member of this project.");
                    }
                    if ( actionId.equalsIgnoreCase("manage_members"))
                    {
                        // Can manage members if the user is an owner
                        if ( isProjectMember )
                            return new ReturnMessage(isCreator | isOwner,"You are not an owner of this project.");
                        else
                            return new ReturnMessage(false, "You are not a project member.");
                    }

                    break;

                // Rules for Hotspots
                // -------------------------------------------------------------
                case Constantes.HOTSPOT:

                    Hotspot hs = null;
                    Layer la = null;

                    try
                    {
                        hs = getHotspot(elementId);
                        la = getLayer( hs.getHsLayer() ) ;
                        map = getMap( la.getMapId() );
                        Project p = getProject( map.getProjectId() );

                        if ( isCreator(u.getId(), p.getId(), Constantes.PROJECT) )
                            return new ReturnMessage(true,null);

                        ReturnMessage mapRights = internalHasPermission(u, "map-creator-owner", map.getMapid(), Constantes.MAP );
                        if ( mapRights.isSuccess() )
                            return new ReturnMessage(true,null);

                        
                    }
                    catch ( Exception ex )
                    {
                        return new ReturnMessage(false, ex.getMessage() );
                    }

                    if ( actionId.equalsIgnoreCase("creator-owner"))
                    {
                        if ( isCreator | isOwner )
                            return new ReturnMessage( true, null );

                    }


                    // Delete hotspot
                    if ( actionId.equalsIgnoreCase("delete") )
                    {
                        try
                        {

                            boolean isMapOwner = isOwner( u.getId(), map.getMapid(), Constantes.MAP );
                            boolean isMapCreator = isCreator(u.getId(), map.getMapid(), Constantes.MAP );

                            // Map Owners and co-owners have always permission for delete hotspots
                            if ( isMapCreator | isMapOwner )
                                return new ReturnMessage(true,null);

                            return new ReturnMessage(false,"You can't delete this hotspot because you don't have ownership rights on this map.");

                        }
                        catch ( Exception ex )
                        {
                            return new ReturnMessage(false, ex.getMessage() );
                        }
                    }


                    // Third question: Who should be allowed to add new media files to hotspots that have already been created?
                    if ( actionId.equalsIgnoreCase("edit-media") )
                    {
                        try
                        {
                            hs = getHotspot(elementId);
                            la = getLayer( hs.getHsLayer() ) ;
                            map = getMap( la.getMapId() );

                            boolean isMapOwner = isOwner( u.getId(), map.getMapid(), Constantes.MAP );
                            boolean isMapMember = isMember( u.getId(), map.getMapid(), Constantes.MAP );
                            boolean isMapCreator = isCreator(u.getId(), map.getMapid(), Constantes.MAP );

                            // Map Owners and co-owners have always permission for the 2nd question
                            if ( isMapCreator | isMapOwner )
                                return new ReturnMessage(true,null);

                            Integer editableMedia = map.getMediaEditable();
                            if ( editableMedia == null )
                                editableMedia = Constantes.EDIT_MEDIA_HS_MAP_OWNERS_ONLY;

                            switch ( editableMedia )
                            {
                                case Constantes.EDIT_MEDIA_HS_OWNER_ONLY:
                                    return new ReturnMessage(isCreator, "Only the creator of this hotspot can add content." );
                                case Constantes.EDIT_MEDIA_HS_ALL:
                                    return new ReturnMessage(true,null);
                                case Constantes.EDIT_MEDIA_HS_MAP_MEMBERS_ONLY:
                                    return new ReturnMessage(isMapMember,"Only map members can add content to this hotspot.") ;
                                case Constantes.EDIT_MEDIA_HS_MAP_OWNERS_ONLY:
                                    return new ReturnMessage( isMapCreator | isMapOwner, "You don't have ownership rights on the map of this hotspot." );
                            }

                        }
                        catch ( Exception ex )
                        {
                            return new ReturnMessage(false, ex.getMessage() );
                        }

                    }

                    break;

                // Rules for Objects
                // -------------------------------------------------------------
                case Constantes.OBJECT:
                    break;


            }
        }

        // Special cases
        // None ?


        return new ReturnMessage(false, "You don't have enough permissions." );

    }

    /** Checks if the user has permission over the given action
     * 
     * @param userId
     * @param actionId
     * @return
     * @throws NoConnectionException
     * @throws BDException
     * @throws BDException
     */
    public ReturnMessage hasPermission(User u, String actionId, Integer elementId, Integer elementType ) throws NoConnectionException, BDException {
        ReturnMessage r = this.internalHasPermission(u, actionId, elementId, elementType);
        boolean permissionGranted = r.isSuccess() ;
        String permStr = "DENIED" ;
        if ( permissionGranted )
            permStr = "GRANTED";
        log.debug( myUser.getId() + " " + actionId + " E:" + elementId + " T:" + elementType + " > " + permStr );

        return r;
    }

    /** Updates user
     *
     * @param u User with the date to be updated
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateUser(User u ) throws NoConnectionException, BDException {
        userDao.update(u);
    }

    public boolean isCreator(String uid, Integer elementId, Integer elementType )  {
        try
        {

            switch ( elementType )
            {
                case Constantes.PROJECT:
                    Project p = this.getProject(elementId);
                    return  p.getCreatedBy().equalsIgnoreCase(uid) ;

                case Constantes.MAP:
                    Map m = this.getMap(elementId);
                    return  m.getCreatedBy().equalsIgnoreCase(uid) ;

                case Constantes.HOTSPOT:
                    Hotspot h = this.getHotspot(elementId);
                    return  h.getCreatedBy().equalsIgnoreCase(uid) ;

                case Constantes.OBJECT:
                    EasyObject o = this.getObject(elementId);
                    return  o.getCreatedBy().equalsIgnoreCase(uid) ;
                    
                default:
                    return false;
                    
            }
        }
        catch ( Exception ex )
        {
            return false;
        }
    }





    /** Determines is the user is overlord
     * 
     * @param u
     * @return
     * @throws NoConnectionException
     * @throws BDException
     * @throws BDException
     */
    public boolean isOverlord(User u) {

        if ( u.getRol() != null && u.getRol().equals("overlord") )
            return true;

        return false;
    }

    /** Creates a project
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createProject( Project registro) throws NoConnectionException, BDException, SecurityIssue {

        ReturnMessage r = hasPermission( myUser, "create_project", null, null ) ;
        if ( ! r.isSuccess() )
                throw new SecurityIssue(r.getMessage());

        Integer pk = PostgresUtilDAO.getNextVal(user, getConn(), "sq_projects");
        registro.setId(pk);
        Timestamp now = new Timestamp( System.currentTimeMillis());
        registro.setCreated( now );
        registro.setModified( now );
        registro.setCreatedBy(user.getId());
        projectDao.create(registro);
        
    }

    /** Updates project information
     * 
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateProject( Project p ) throws NoConnectionException, BDException {

        p.setModified( new Timestamp(System.currentTimeMillis()));
        p.setModifiedBy( user.getId() );

        projectDao.update(p);
    }

    /** Find projects by the give criteria
     * 
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Project> findProjects(CriteriosProject criteria) throws NoConnectionException, BDException {
        List<Project> results = projectDao.find(criteria);
        for ( Project p : results )
            setProjectData( p );
        return results ;
    }

    public List<Project> myProjects(User u) throws NoConnectionException, BDException {
        List<Project> allProjects = projectDao.find(new CriteriosProject());
        List<Project> myProjects = new ArrayList<Project>();
        for ( Project p : allProjects )
        {
            ReturnMessage r = hasPermission(u, "view", p.getId(), Constantes.PROJECT );
            if ( r.isSuccess() )
                myProjects.add( p );
        }
        return myProjects;
    }

    public Project getProject(Integer pid) throws NoConnectionException, BDException, RegistroNoExisteException {
        Project p = projectDao.get(pid);
        setProjectData( p );
        return p;
    }

    private void setProjectData(Project p ) throws NoConnectionException, BDException {
        try
        {
            User owner = this.getUser(p.getCreatedBy());
            p.setOwnerName( owner.getNombre() );
        }
        catch ( Exception ex )
        {
            // niente
        }
    }

    /** Deletes a project
     * 
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteProject(Integer pid) throws NoConnectionException, BDException {
        projectDao.delete(pid);
    }

    /** Creates a map
     *
     * @param p
     * @throws NoConnectionException 
     * @throws BDException
     */
    public Integer createMap( Map registro) throws NoConnectionException, BDException, SecurityIssue {

            ReturnMessage r = hasPermission( myUser, "create_map", registro.getProjectId(), Constantes.PROJECT ) ;
            if ( ! r.isSuccess() )
                  throw new SecurityIssue(r.getMessage());

            Integer pk = PostgresUtilDAO.getNextVal(user, getConn(), "sq_maps");
            registro.setMapid(pk);
            Timestamp now = new Timestamp( System.currentTimeMillis());
            registro.setCreated( now );
            registro.setModified( now );
            registro.setCreatedBy(user.getId());
            registro.setCreatorName( user.getLogin() );

            registro.setPrivacy( Constantes.SECURE );
            registro.setHotspotsEditable( Constantes.ADD_HS_OWNERS_MEMBERS_ONLY );
            registro.setMediaEditable( Constantes.EDIT_MEDIA_HS_OWNER_ONLY );

            registro.setDisplayMode(Constantes.DM_FLEXIBLE);
            mapDao.create(registro);

            /*
            Permission perm = new Permission();
            perm.setUserid( user.getId() );
            perm.setElementId( pk );
            perm.setElementType(new Integer(1));
            perm.setMember(new Integer(0));
            perm.setOwnership(new Integer(1));
            createPermission(perm);
            */
            return pk;
            

    }

    /** Updates map information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateMap( Map registro ) throws NoConnectionException, BDException {

        registro.setModified( new Timestamp(System.currentTimeMillis()));
        registro.setModifiedBy( user.getId() );

        mapDao.update(registro);
        
    }

    /** Find maps by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Map> findMaps(CriteriosMap criteria) throws NoConnectionException, BDException {
        return mapDao.find(criteria);
    }

    public Map getMap(Integer pid) throws NoConnectionException, BDException, RegistroNoExisteException {
        return mapDao.get(pid);
    }

    /** Deletes a map
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException 
     */
    public void deleteMap(Integer mapid) throws NoConnectionException, BDException, SecurityIssue {

        ReturnMessage r = hasPermission( myUser, "delete", mapid, Constantes.MAP ) ;
        boolean permissionGranted = r.isSuccess() ;
        if ( ! permissionGranted )
            throw new SecurityIssue(r.getMessage());

        // List keywords
        CriteriosKeyword keySearcher = new CriteriosKeyword();
        keySearcher.setMapId(mapid);
        List<Keyword> keys = this.findKeywords(keySearcher);

        // List Categories
        CriteriosCategory catSearcher = new CriteriosCategory();
        catSearcher.setMapId(mapid);
        List<Category> cats = findCategorys( catSearcher );

        // List layers
        CriteriosLayer layerSearcher = new CriteriosLayer();
        layerSearcher.setMapId(mapid);
        List<Layer> layers = findLayers( layerSearcher );

        // List hotspots
        List<Hotspot> hotspots = new ArrayList<Hotspot>();
        for ( Layer la : layers )
        {
            CriteriosHotspot hsSearcher = new CriteriosHotspot();
            hsSearcher.setHsLayer( la.getLayerId() );
            hotspots.addAll( this.findHotspots(hsSearcher));
        }

        //List hotspots-objects
        List<HotspotObject> hos = new ArrayList<HotspotObject>();
        List<Integer> media = new ArrayList<Integer>();
        for ( Hotspot hs : hotspots )
        {
            CriteriosHotspotObject hosSearcher = new CriteriosHotspotObject();
            hosSearcher.setHotspot(hs.getHsId());
            hos.addAll( this.findHotspotObjects(hosSearcher));
        }

        // List objects
        for ( HotspotObject ho : hos )
            media.add( ho.getObjectId() );


        SigarUtils.setAutoCommit(conn, false );
        Savepoint begin = SigarUtils.begin(conn);

        try
        {


            // Delete hotspot - object
            for ( HotspotObject ho : hos )
                this.deleteHotspotObject( ho.getHotspot(), ho.getObjectId() );

            // Delete objects
            for ( Integer eo : media )
                this.deleteObject(eo);

            // Delete hotspots
            for ( Hotspot hs : hotspots )
                this.deleteHotspot(hs.getHsId());

            // Delete keywords
            for ( Keyword k : keys )
                this.deleteKeyword(k.getKwId());

            // Delete categories
            for ( Category cat : cats )
                deleteCategory( cat.getCatId() );


            // Delete layers
            for ( Layer la : layers )
                layerDao.delete( la.getLayerId() );

            // Delete map
            mapDao.delete(mapid);

            SigarUtils.commit(conn);

        }
        catch ( Exception ex )
        {
            SigarUtils.rollback(begin, conn);
            ex.printStackTrace();
        }
        finally
        {
            SigarUtils.setAutoCommit(conn, true );
        }


    }

    public void setAutoCommit( boolean autoCommit ) throws BDException {
        SigarUtils.setAutoCommit(conn, true );
    }



    /** Creates a layer
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public Integer createLayer( Layer registro ) throws NoConnectionException, BDException {
        Integer pk = PostgresUtilDAO.getNextVal(user, getConn(),"sq_layers");
        registro.setLayerId( pk );
        Timestamp now = new Timestamp(System.currentTimeMillis());
        registro.setCreated( now );
        registro.setModified( now );
        registro.setCreatedBy(user.getId());
        return layerDao.create(registro);
    }

    /** Updates layer information
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateLayer( Layer registro ) throws NoConnectionException, BDException {

        registro.setModified( new Timestamp(System.currentTimeMillis()));
        registro.setModifiedBy( user.getId() );

        layerDao.update(registro);
        
    }

    /** Find layers by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Layer> findLayers(CriteriosLayer criteria) throws NoConnectionException, BDException {
        return layerDao.find(criteria);
    }

    public Layer getLayer(Integer id) throws NoConnectionException, BDException, RegistroNoExisteException {
        return layerDao.get(id);
    }

    /** Deletes a layer
     *
     * @param id
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteLayer(Integer id) throws NoConnectionException, BDException, LayerHasHotspots {
        try
        {

            CriteriosHotspot c1 = new CriteriosHotspot();
            c1.setHsLayer(id);
            List<Hotspot> hs = findHotspots(c1);
            if ( ! hs.isEmpty() )
                throw new LayerHasHotspots();

            Layer old = getLayer( id );
            layerDao.delete(id);
            CriteriosLayer cri = new CriteriosLayer();
            cri.setMapId( old.getMapId() );
            List<Layer> layersOnMap = this.findLayers( cri );

            Layer temp = new Layer();
            int cont = 0;
            for ( Layer la : layersOnMap )
            {
                temp.setLayerId( la.getLayerId() );
                temp.setIndex(cont);
                updateLayer( temp );
                cont++;
            }
            
        }
        catch ( RegistroNoExisteException ex )
        {
            return;
        }

    }




    /** Creates a hotspot
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public Integer createHotspot( Hotspot registro ) throws NoConnectionException, BDException {

        Integer pk = PostgresUtilDAO.getNextVal(user, getConn(), "sq_hotspots");
        registro.setHsId(pk);
        registro.setCreatedBy(user.getId());
        registro.setCreatorName(user.getLogin());
        if ( registro.getProximityRadius() == null )
            registro.setProximityRadius( new Integer(10) );
        Timestamp now = new Timestamp( System.currentTimeMillis());
        registro.setCreated(now);
        registro.setModified(now);

        return hotspotDao.create(registro);


    }

    /** Updates hotspot information
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateHotspot( Hotspot registro ) throws NoConnectionException, BDException {

        registro.setModified( new Timestamp(System.currentTimeMillis()));
        registro.setModifiedBy( user.getId() );

        hotspotDao.update(registro);
    }

    /** Find hotspots by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Hotspot> findHotspots(CriteriosHotspot criteria) throws NoConnectionException, BDException {
        List<Hotspot> results = hotspotDao.find(criteria);
        for ( Hotspot hs : results )
            setHotspotData( hs );
        return results;
    }

    private void setHotspotData(Hotspot hs) {
        try
        {
            Integer kwId = hs.getKeywordId();
            if ( kwId != null )
            {
                Keyword k = this.getKeyword(kwId);
                hs.setKeyword(k);
            }
        }
        catch ( Exception ex )
        {
            // niente
        }


    }

    public Hotspot getHotspot(Integer id) throws NoConnectionException, BDException, RegistroNoExisteException {
        Hotspot hs = hotspotDao.get(id);
        setHotspotData( hs );
        return hs;
    }

    /** Deletes a hotspot
     *
     * @param id
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteHotspot(Integer id) throws NoConnectionException, BDException {
        hotspotDao.delete(id);
    }

    /** Creates a object
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public Integer createObject( EasyObject registro ) throws NoConnectionException, BDException {

        Timestamp now = new Timestamp(System.currentTimeMillis());
        registro.setCreated( now );
        registro.setModified( now );
        registro.setCreatedBy(user.getId());
        registro.setCreatorName( user.getLogin() );

        return objectDao.create(registro);
    }

    /** Updates object information
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateObject( EasyObject r ) throws NoConnectionException, BDException {
        objectDao.update(r);
    }

    /** Find objects by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<EasyObject> findObjects(CriteriosObject criteria) throws NoConnectionException, BDException {
        return objectDao.find(criteria);
    }

    public EasyObject getObject(Integer id) throws NoConnectionException, BDException, RegistroNoExisteException {
        EasyObject eo = objectDao.get(id);
        return eo;
    }

    public EasyObject getTempObject(Integer id) throws NoConnectionException, BDException, RegistroNoExisteException {
        return objectDao.getTemp(id);
    }



    /** Deletes a object
     *
     * @param id
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteObject(Integer id) throws NoConnectionException, BDException {
        objectDao.delete(id);
    }

    public void deleteTemp(Integer id) throws NoConnectionException, BDException {
        objectDao.deleteTemp(id);
    }

    public void fillData(EasyObject o, int datafield, String table ) throws BDException, NoConnectionException {
        objectDao.fillData(o,datafield,table);
    }

    public void writeData(Integer objectId, byte[] data, int datafield, String table ) throws BDException, NoConnectionException {
        objectDao.writeData(objectId, data, datafield, table);
    }

    public Integer createTempObject(EasyObject o)  throws BDException, NoConnectionException {
        return objectDao.createTemp(o);
    }

    public void copyDataFromTemp(Integer tempObjectId, Integer objectId ) throws BDException, NoConnectionException {
        objectDao.copyDataFromTemp(tempObjectId, objectId);
    }

    /** Creates a HotspotObject
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public Integer createHotspotObject( HotspotObject r) throws NoConnectionException, BDException {
        return hotspotObjectDao.create(r);
    }

    /** Updates HotspotObject information
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateHotspotObject( HotspotObject r ) throws NoConnectionException, BDException {
        hotspotObjectDao.update(r);
    }

    /** Find HotspotObjects by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<HotspotObject> findHotspotObjects(CriteriosHotspotObject criteria) throws NoConnectionException, BDException {
        return hotspotObjectDao.find(criteria);
    }

    public HotspotObject getHotspotObject(Integer hsId, Integer objectId) throws NoConnectionException, BDException, RegistroNoExisteException {
        return hotspotObjectDao.get(hsId, objectId);
    }

    public List<EasyObject> getObjects(Integer hsId, int block) throws NoConnectionException, BDException, RegistroNoExisteException {
        CriteriosHotspotObject criteria = new CriteriosHotspotObject();
        criteria.setHotspot(hsId);
        criteria.setBlock(block);
        List<HotspotObject> hos = this.findHotspotObjects(criteria);
        List<EasyObject> objects = new ArrayList<EasyObject>();
        for( HotspotObject ho : hos )
            objects.add( getObject( ho.getObjectId() ) );
        return objects;
    }


    /** Deletes a HotspotObject
     *
     * @param id
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteHotspotObject(Integer hsId, Integer objId) throws NoConnectionException, BDException {
        hotspotObjectDao.delete(hsId, objId);
    }


    /** Creates a category
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createCategory( Category p) throws NoConnectionException, BDException {

        Integer pk = PostgresUtilDAO.getNextVal(user, getConn(),"sq_categories");
        p.setCatId(pk);
        Timestamp now = new Timestamp( (new Date()).getTime() );
        p.setCreated( now );
        p.setCreatedBy( user.getId() );
        categoryDao.create(p);
        
    }

    /** Updates category information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateCategory( Category p ) throws NoConnectionException, BDException {
        categoryDao.update(p);
    }

    /** Find categorys by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Category> findCategorys(CriteriosCategory criteria) throws NoConnectionException, BDException {
        return categoryDao.find(criteria);
    }

    public Category getCategory(Integer pid) throws NoConnectionException, BDException, RegistroNoExisteException {
        return categoryDao.get(pid);
    }

    /** Deletes a category
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteCategory(Integer pid) throws NoConnectionException, BDException {
        categoryDao.delete(pid);
    }

    /** Creates a keyword
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createKeyword( Keyword p) throws NoConnectionException, BDException {
        Integer pk = PostgresUtilDAO.getNextVal(user, getConn(),"sq_keywords");
        p.setCreatedBy( user.getId() );
        p.setCreated( new Timestamp(System.currentTimeMillis()) );
        p.setKwId(pk);
        keywordDao.create(p);
    }

    /** Updates keyword information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateKeyword( Keyword p ) throws NoConnectionException, BDException {
        keywordDao.update(p);
    }

    /** Find keywords by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Keyword> findKeywords(CriteriosKeyword criteria) throws NoConnectionException, BDException {
        List<Keyword> keywords = keywordDao.find(criteria);
        for ( Keyword k : keywords )
        {
            try
            {
                if ( k.getCatId() != null )
                     k.setCatName( getCategory( k.getCatId() ).getCatName() );
            }
            catch ( Exception ex )
            {

            }

        }
        return keywords;
    }

    public Keyword getKeyword(Integer pid) throws NoConnectionException, BDException, RegistroNoExisteException {
        Keyword k = keywordDao.get(pid);
        try
        {
            if ( k.getCatId() != null )
                 k.setCatName( getCategory( k.getCatId() ).getCatName() );
             k.setCatName( getCategory( k.getCatId() ).getCatName() );
        }
        catch ( Exception ex )
        {

        }

        return k;
    }



    /** Deletes a keyword
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteKeyword(Integer pid) throws NoConnectionException, BDException, KeywordHasHotspots {

        CriteriosHotspot c1 = new CriteriosHotspot();
        c1.setKeywordId(pid);
        List<Hotspot> hs = findHotspots(c1);
        if ( !hs.isEmpty() )
            throw new KeywordHasHotspots();

        keywordDao.delete(pid);
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /** Creates a ObjCategory
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createObjCategory( ObjCategory p) throws NoConnectionException, BDException {
        Integer pk = PostgresUtilDAO.getNextVal(user, getConn(), "sq_maps");
        p.setId(pk);
        objCategoryDao.create(p);
    }

    /** Updates ObjCategory information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateObjCategory( ObjCategory p ) throws NoConnectionException, BDException {
        objCategoryDao.update(p);
    }

    /** Find ObjCategorys by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<ObjCategory> findObjCategorys(CriteriosObjCategory criteria) throws NoConnectionException, BDException {
        return objCategoryDao.find(criteria);
    }

    public ObjCategory getObjCategory(Integer pid) throws NoConnectionException, BDException, RegistroNoExisteException {
        return objCategoryDao.get(pid);
    }

    /** Deletes a ObjCategory
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteObjCategory(Integer pid) throws NoConnectionException, BDException {
        objCategoryDao.delete(pid);
    }

    /** Creates a Permission
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createPermission( Permission p) throws NoConnectionException, BDException {
        permissionDao.create(p);
    }

    /** Updates Permission information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updatePermission( Permission p ) throws NoConnectionException, BDException {
        permissionDao.update(p);
        SigarUtils.commit(conn);

    }

    /** Find Permissions by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    /*
    public List<Permission> findPermissions(CriteriosPermission criteria) throws NoConnectionException, BDException {
        return permissionDao.find(criteria);
    }
    */

    public Permission getPermission(String userId, Integer elementId, Integer elementType) throws NoConnectionException, BDException, RegistroNoExisteException {
          return permissionDao.get(userId, elementId, elementType);
    }

    /** Deletes a Permission
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deletePermission(String userId, Integer elementId, Integer elementType) throws NoConnectionException, BDException {
        permissionDao.delete( userId, elementId, elementType );
    }

    public void checkProjectConsistency( Project p ) throws NoConnectionException, BDException {
        log.info( "Checking consistency for project " + p.getNombre() );
        CriteriosMap criteria = new CriteriosMap();
        criteria.setProjectId( p.getId() );
        List<Map> maps = this.findMaps( criteria );
        for ( Map map : maps )
            checkMapConsistency( map );
    }

    public void checkMapConsistency(Map map) throws NoConnectionException, BDException {

        //1. Get the map project
        //2. Get the project members
        //3. Get all users with membership and, or ownership permissions on this map
        //4. Substract 2 from 3
        //5. Revoke permissions for the resulting set

        Integer mapid = map.getMapid();

        //1. Get the map project
        Integer pid = map.getProjectId() ;

        //2. Get the project members
        List<User> projectMembersList = getProjectMembers( pid );
        HashSet<String> projectMembers = new HashSet<String>();
        for ( User u : projectMembersList )
            projectMembers.add( u.getId() );

        //3. Get all users with membership and, or ownership permissions on this map
        HashSet<String> ownersAndMembers = new HashSet<String>();
        ownersAndMembers.addAll(permissionDao.getMembers( mapid, Constantes.MAP) );
        ownersAndMembers.addAll( permissionDao.getOwners( mapid, Constantes.MAP) );

        //4. Substract 2 from 3
        HashSet<String> notProjectMembers = new HashSet<String>( ownersAndMembers);
        notProjectMembers.removeAll( projectMembers );

        if ( ! notProjectMembers.isEmpty() )
            log.info("Revoking map permissions for users : " + notProjectMembers );

        //5. Revoke permissions for the resulting set
        for ( String uid : notProjectMembers )
        {
            revokeMembership(uid, mapid, Constantes.MAP );
            revokeOwnership( uid, mapid, Constantes.MAP );
        }



    }

    public List<String> getMapOwners(Integer mapid) throws NoConnectionException, BDException, RegistroNoExisteException {

        //1. Get all users set
        //2. For the map's project, get all project owners, members
        //3. Intersect 1 and 2
        //4. List existing map owners
        //5. Get the map owners that are not project owners (substract 3 from 4, but first make a copy of 4 ).


        //1. Get all users set
        List<User> allUsers = new ArrayList<User>();
        allUsers.addAll( getTeachers() );
        allUsers.addAll( getStudents() );

        HashSet<String> users = new LinkedHashSet<String>();
        Hashtable<String,User> userTable = new Hashtable<String,User>();
        for ( User u : allUsers )
        {
            userTable.put(u.getId(), u );
            users.add( u.getId() );
        }


        //2. For the map's project, get all project owners, members
        HashSet<String> projectMembers = new LinkedHashSet<String>();
        Map m = getMap(mapid);
        Integer pid = m.getProjectId();
        projectMembers.addAll( permissionDao.getOwners(pid,Constantes.PROJECT) );
        projectMembers.addAll( permissionDao.getMembers(pid,Constantes.PROJECT) );

        //3. Intersect 1 and 2
        projectMembers.retainAll( users );


        //4.  List existing map owners
        List<String> owners = permissionDao.getOwners(mapid, Constantes.MAP);

        //5. Get the map owners that are not project members (substract 3 from 4, but first make a copy of 4 ).
        Set<String> notInProject = new HashSet<String>( owners );
        notInProject.removeAll( projectMembers );

        owners.retainAll( projectMembers );
        return owners;

    }


    public List<User> getProjectMembers( Integer pid ) throws NoConnectionException, BDException {


        // Algorithm:
        // 1. Select all users that are not overlords (teachers and students)
        // 2. Get a set of all project members and owners
        // 3. Intersect 1 and 2

        HashSet<String> users = new HashSet<String>();

        // 1. Select all admin users (teachers)
        List<User> allUsers = new ArrayList<User>();
        allUsers.addAll( getTeachers() );
        allUsers.addAll( getStudents() );
        Hashtable<String,User> userTable = new Hashtable<String,User>();
        for ( User us : allUsers )
        {
            userTable.put(us.getId(), us);
            users.add( us.getId() );
        }

        // Get the project owners and members
        List<String> projectMembers = new ArrayList<String>();
        projectMembers.addAll( permissionDao.getOwners(pid, Constantes.PROJECT ) );
        projectMembers.addAll( permissionDao.getMembers( pid, Constantes.PROJECT ) );

        // Intersection between teachers and student users, and project members and owners
        users.retainAll( projectMembers );

        List<User> projectOwnersMembersList = new ArrayList<User>();
        for ( String uid : users )
            if ( userTable.containsKey(uid) )
               projectOwnersMembersList.add(userTable.get(uid));

        return projectOwnersMembersList;
    }

    public List<User> getTeachers() throws NoConnectionException, BDException {
        CriteriosUser findAdmins = new CriteriosUser();
        findAdmins.setRol("admin");
        return findUsers(findAdmins);
    }

    public List<User> getStudents() throws NoConnectionException, BDException {
        CriteriosUser findStudents = new CriteriosUser();
        findStudents.setRol("participa");
        return findUsers(findStudents);
    }



    private Set<String> getOverlords() throws NoConnectionException, BDException {
        HashSet<String> overlordSet = new HashSet<String>();
        CriteriosUser criteria = new CriteriosUser();
        criteria.setRol("overlord");
        List<User> overlords = this.findUsers( criteria );
        for ( User u : overlords )
            overlordSet.add( u.getId() );
        return overlordSet;
    }

    public List<String> getMapMembers( Integer mapid ) throws NoConnectionException, BDException, RegistroNoExisteException {

        //1. Get all users set
        //2. For the map's project, get all project owners, members, and creator
        //3. Intersect 1 and 2
        //4. List existing map members
        //5. Get the map members that are not project members (substract 3 from 4, but first make a copy of 4 ).


        //1. Get all users set
        List<User> allUsers = new ArrayList<User>();
        allUsers.addAll( getTeachers() );
        allUsers.addAll( getStudents() );

        HashSet<String> users = new LinkedHashSet<String>();
        Hashtable<String,User> userTable = new Hashtable<String,User>();
        for ( User u : allUsers )
        {
            userTable.put(u.getId(), u );
            users.add( u.getId() );
        }


        //2. For the map's project, get all project owners, members
        HashSet<String> projectMembers = new LinkedHashSet<String>();
        Map m = getMap(mapid);
        Integer pid = m.getProjectId();
        projectMembers.addAll( permissionDao.getOwners(pid,Constantes.PROJECT) );
        projectMembers.addAll( permissionDao.getMembers(pid,Constantes.PROJECT) );

        //3. Intersect 1 and 2
        projectMembers.retainAll( users );


        //4.  List existing map members
        List<String> members = permissionDao.getMembers(mapid, Constantes.MAP);

        //5. Get the map members that are not project members (substract 3 from 4, but first make a copy of 4 ).
        Set<String> notInProject = new HashSet<String>( members );
        notInProject.removeAll( projectMembers );

        members.retainAll( projectMembers );
        return members;

    }



    /** Creates a User
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createUser( User p) throws NoConnectionException, BDException {

        p.setCreatedBy( user.getId() );
        Timestamp now = new Timestamp( new java.util.Date().getTime() );
        p.setCreated(now);

        userDao.create(p);
        
    }


    /** Find Users by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<User> findUsers(CriteriosUser criteria) throws NoConnectionException, BDException {

        if ( criteria.getId() != null && criteria.getId().equals(Constantes.VISITOR_ID) )
        {
            List<User> users = new ArrayList<User>();
            users.add( getVisitor() );
            return users ;
        }

        return userDao.find(criteria);
        
    }


    /** Deletes a User
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteUser(String userid) throws NoConnectionException, BDException {
        userDao.delete(userid);
    }

    public List<Rol> listRoles() throws NoConnectionException, BDException {

        /*
        if ( roles == null )
            roles = roleDao.find(new CriteriosRol());
        return roles;
*/
        return roleDao.find(new CriteriosRol());

    }

    public Rol getRol(String rolname) throws NoConnectionException, BDException {
        List<Rol> theRols = this.listRoles();
        for ( Rol r : theRols )
            if ( r.getRol().equals(rolname))
                return r;
        return null;
    }

    public boolean isOwner(String uid, Integer elementId, Integer elementType ) throws NoConnectionException, BDException {
        try
        {
            Permission p = this.getPermission(uid, elementId, elementType);
            if ( p.getOwnership() != null && p.getOwnership().intValue() == 1 )
                return true;
            else
                return false;
        }
        catch ( Exception ex )
        {
            return false;
        }
    }

    public boolean isMember(String uid, Integer elementId, Integer elementType ) throws NoConnectionException, BDException {
        try
        {
            Permission p = this.getPermission(uid, elementId, elementType);
            if ( p.getMember() != null && p.getMember().intValue() == 1 )
                return true;
            else
                return false;
        }
        catch ( RegistroNoExisteException re )
        {
            return false;
        }
    }

    public Integer getDefaultIconId() throws NoConnectionException, BDException {
        Integer defaultId = iconDao.getDefaultId();
        return defaultId;
    }

    /**
     * @return the myUser
     */
    public com.bnmi.ourmap.model.User getMyUser() {
        return myUser;
    }

    /**
     * @param myUser the myUser to set
     */
    public void setMyUser(com.bnmi.ourmap.model.User myUser) {
        this.myUser = myUser;
    }

    public List<User> getUsers(Collection<String> uids ) throws NoConnectionException, BDException {
        return userDao.get(uids);
    }

    public void grantMembership(String userid, Integer elementId, Integer elementType ) throws NoConnectionException, BDException {
        Permission perm = null;
        try
        {
            perm = getPermission(userid, elementId, elementType );
            perm.setMember( 1 );
            updatePermission( perm );
        }
        catch ( RegistroNoExisteException rex )
        {
            perm = new Permission();
            perm.setUserid( userid );
            perm.setElementId( elementId );
            perm.setElementType( elementType );
            perm.setMember( 1 );
            createPermission( perm );
        }
        
        revokeOwnership( userid, elementId, elementType );
    }

    public void grantOwnership(String userid, Integer elementId, Integer elementType ) throws NoConnectionException, BDException {
        Permission perm = null;
        try
        {
            perm = getPermission(userid,elementId,elementType);
            perm.setOwnership(1);
            updatePermission( perm );
        }
        catch ( RegistroNoExisteException rex )
        {
            perm = new Permission();
            perm.setUserid( userid );
            perm.setElementId( elementId );
            perm.setElementType( elementType );
            perm.setOwnership( 1 );
            createPermission( perm );
        }
        
        revokeMembership( userid, elementId, elementType );
    }

    public void revokeMembership( String userid, Integer elementId, Integer elementType ) throws NoConnectionException, BDException {
        try
        {
            Permission perm = getPermission(userid,elementId,elementType);
            boolean isOwner = isOwner(userid,elementId,elementType);
            if ( isOwner )
            {
                perm.setMember(0);
                updatePermission(perm);
            }
            else
            {
                deletePermission(userid,elementId,elementType);
            }
        }
        catch ( RegistroNoExisteException rex )
        {
            // niente
        }
    }

    public void revokeOwnership( String userid, Integer elementId, Integer elementType ) throws NoConnectionException, BDException {
        try
        {
            Permission perm = getPermission(userid, elementId, elementType );
            boolean isMember = isMember( userid, elementId, elementType );
            if ( isMember )
            {
                perm.setOwnership( 0 );
                updatePermission( perm );
            }
            else
            {
                deletePermission( userid, elementId, elementType );
            }
        }
        catch ( RegistroNoExisteException rex )
        {
            //niente
        }
    }

    public static User getVisitor() {
        User visitor = new User();
        visitor.setId(Constantes.VISITOR_ID);
        visitor.setNombre("Visitor");
        visitor.setRol("visitor");
        visitor.setDescr("Visitor");
        return visitor;
    }

    public String getStatus(User u, Integer elementId, Integer elementType ) {

        try
        {
            String uid = u.getId();
            if ( isOverlord( u ) )
                 return "Admin";
            
            if ( isCreator(uid,elementId,elementType) )
                return "Owner";

            if ( elementType.intValue() == Constantes.MAP )
            {
                ReturnMessage projectPerm = hasPermission( u, "map-creator-owner", elementId, Constantes.MAP );
                if ( projectPerm.isSuccess() )
                    return "Co-Owner";
            }


            if ( isOwner(uid,elementId,elementType) )
                return "Co-Owner";

            if ( isMember(uid,elementId,elementType))
                return "Member";

            return "Visitor";
        }
        catch ( Exception ex )
        {
            return "Visitor";
        }

    }

}
