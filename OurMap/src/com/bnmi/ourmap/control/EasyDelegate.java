/*******************************************************************************

com.bnmi.ourmap.control.EasyDelegate.java
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
import com.bnmi.ourmap.daoimpl.FileObjectDAO;
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
import com.bnmi.ourmap.model.User;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.exception.RegistroNoExisteException;
import com.inga.utils.ReturnMessage;
import com.inga.utils.SigarUtils;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class EasyDelegate { 

    private static Logger log = Logger.getLogger(EasyDelegate.class);
    public static final Integer UNIQUE_ACCESS = new Integer(1) ;


    private Connection conn = null;
    private Connection drupalConn = null;
    private com.inga.security.User user = null;
    private EasyManager em = null;

    /** Constructor
     *
     * @param u The user given would be responsible all the changes and executions this instance does.
     */
    public EasyDelegate(com.inga.security.User u) {
        this.user = u;

        try
        {
            init();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }

    }


    /** Gives a salutation
     * 
     * @return Salutates user
     */
    public String salute() {
        return "Welcome to OurMap";
    }

    private void init() throws NoConnectionException, IOException, BDException {

        if ( em != null )
        {
            Connection myConn = em.getConn();
            if ( myConn != null )
            {
                try
                {
                    if ( ! myConn.isClosed() )
                    {
                        SigarUtils.setAutoCommit(conn, true);
                        return;
                    }
                }
                catch ( Exception ex )
                {
                    // niente
                }
            }

        }

        File testFile = new File(".");
        System.out.println( "Local directory: " + testFile.getAbsolutePath() );


        Properties props = SigarUtils.loadProperties("ourmap.properties");

        conn = SigarUtils.getConnection(props.getProperty("db_driver"),
                                        props.getProperty("db_url"),
                                        props.getProperty("db_user"),
                                        props.getProperty("db_password") );

        drupalConn = SigarUtils.getConnection( props.getProperty("drupal_db_driver"),
                                               props.getProperty("drupal_db_url"),
                                               props.getProperty("drupal_db_user"),
                                               props.getProperty("drupal_db_password") );


        if ( this.user == null )
        {
            user = new com.inga.security.User();
            user.setLogin("delegate");
            user.setNombre("delegate");
            user.setRemoteAddress("localhost");
        }

        em = new EasyManager( user, conn, drupalConn );
        log.info(user.getId() + " " + "Manager instance created" + " Version:" + Constantes.VERSION );
        FileObjectDAO.setBaseDir( props.getProperty("baseDir"));

    }

    /** List all users
     * 
     * @return
     * @throws NoConnectionException
     * @throws IOException
     * @throws BDException
     */
    public List<com.bnmi.ourmap.model.User> listUsers() throws NoConnectionException, IOException, BDException {
        init();
        return em.listUsers();
    }

    /** Validates the user and password on the system */
    public boolean validate(String userid, String password) throws NoConnectionException, IOException, BDException, UserDontExists, WrongPassword {
        init();
        return em.validate(userid, password);
    }

    /** Validates the user and password on the system */
    public boolean validateByName(String userid, String password) throws NoConnectionException, IOException, BDException, UserDontExists, WrongPassword {
        init();
        return em.validateByName(userid, password);
    }


    /** Clean used resources such as opened connections */
    public void clean() {
        SigarUtils.closeConnection(conn);
        SigarUtils.closeConnection(drupalConn);
    }

    /** Gets the current connection it is using
     * 
     * @return The current connection
     */
    public Connection getConnection() {
        return conn;
    }

    /** List actions by role
     * 
     * @param r
     * @return
     * @throws NoConnectionException
     * @throws IOException
     * @throws IOException
     * @throws BDException
     * @throws BDException 
     */
    public List<Action> getActions(com.bnmi.ourmap.model.Rol r) throws NoConnectionException, IOException, IOException, BDException, BDException {
        init();
        return em.getActions(r);
    }

    /** Retrieves user data from its id
     * 
     * @param userid
     * @return
     * @throws NoConnectionException
     * @throws IOException
     * @throws BDException
     * @throws RegistroNoExisteException
     */
    public User getUser(String userid) throws NoConnectionException, IOException, BDException, RegistroNoExisteException {
        init();
        return em.getUser(userid);
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
    public ReturnMessage hasPermission(User u, String actionId, Integer elementId, Integer elementType) throws NoConnectionException, BDException, IOException {
        init();
        return em.hasPermission(u, actionId, elementId, elementType );
    }

    /** Updates user
     *
     * @param u User with the date to be updated
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateUser(User u ) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.updateUser(u);
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
    public boolean isOverlord(User u)  {
        return em.isOverlord(u);
    }

    /**
     *
     * @param u
     * @param elementId
     * @param elementType
     * @return
     */
    public boolean isCreator(String uid, Integer elementId, Integer elementType ) throws NoConnectionException, IOException, BDException  {
       init();
       return em.isCreator(uid, elementId, elementType);
    }


    /** Creates a project
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createProject( Project p) throws NoConnectionException, BDException, IOException, SecurityIssue {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.createProject(p);
        }
    }

    /** Updates project information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateProject( Project p ) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.updateProject(p);
        }
    }

    /** Find projects by the given criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Project> findProjects(CriteriosProject criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findProjects(criteria);
    }

    /** Deletes a project
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteProject(Integer pid) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.deleteProject(pid);
        }
    }

    /**
     * 
     * @param pid
     * @return
     * @throws NoConnectionException
     * @throws BDException
     * @throws RegistroNoExisteException
     */
    public Project getProject(Integer pid) throws NoConnectionException, BDException, RegistroNoExisteException {
        return em.getProject(pid);
    }

    /** Creates a map
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public Integer createMap( Map p) throws NoConnectionException, BDException, IOException, SecurityIssue {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            return em.createMap(p);
        }
    }

    /** Updates map information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateMap( Map p ) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.updateMap(p);
        }
    }

    /** Find maps by the given criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Map> findMaps(CriteriosMap criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findMaps(criteria);
    }

    /** Deletes a map
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */

    public void deleteMap(Integer pid) throws NoConnectionException, BDException, IOException, SecurityIssue {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.deleteMap(pid);
        }
    }

    /**
     *
     * @param pid
     * @return
     * @throws NoConnectionException
     * @throws BDException
     * @throws RegistroNoExisteException
     */
    public Map getMap(Integer pid) throws NoConnectionException, BDException, RegistroNoExisteException {
        return em.getMap(pid);
    }


    /** Creates a layer
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public Integer createLayer( Layer r) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            return em.createLayer(r);
        }
    }

    /** Updates layer information
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateLayer( Layer r ) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.updateLayer(r);
        }
    }

    /** Find layers by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Layer> findLayers(CriteriosLayer criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findLayers(criteria);
    }

    public Layer getLayer(Integer id) throws NoConnectionException, BDException, RegistroNoExisteException, IOException {
        init();
        return em.getLayer(id);
    } 

    /** Deletes a layer
     *
     * @param id
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteLayer(Integer id) throws NoConnectionException, BDException, IOException, LayerHasHotspots {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.deleteLayer(id);
        }

    }



    /** Creates a hotspot
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public Integer createHotspot( Hotspot r) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            return em.createHotspot(r);
        }
    }

    /** Updates hotspot information
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateHotspot( Hotspot r ) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.updateHotspot(r);
        }
    }

    /** Find hotspots by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Hotspot> findHotspots(CriteriosHotspot criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findHotspots(criteria);

    }

    public Hotspot getHotspot(Integer id) throws NoConnectionException, BDException, RegistroNoExisteException, IOException {
        init();
        return em.getHotspot(id);
    }

    /** Deletes a hotspot
     *
     * @param id
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteHotspot(Integer id) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.deleteHotspot(id);
        }
    }

    /** Creates a object
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public Integer createObject( EasyObject r) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            return em.createObject(r);
        }
    }

    /** Updates object information
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateObject( EasyObject r ) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.updateObject(r);
        }
    }

    /** Find objects by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<EasyObject> findObjects(CriteriosObject criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findObjects(criteria);
    }


    public EasyObject getObject(Integer id) throws NoConnectionException, BDException, RegistroNoExisteException, IOException {
        init();
        return em.getObject(id);
    }

    public EasyObject getTempObject(Integer id) throws NoConnectionException, BDException, RegistroNoExisteException, IOException {
        init();
        return em.getTempObject(id);
    }



    /** Deletes a object
     *
     * @param id
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteObject(Integer id) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.deleteObject(id);
        }
    }

    public void deleteTemp(Integer id) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.deleteTemp(id);
        }
    }

    public void fillData(EasyObject o, int datafield, String table ) throws BDException, IOException, NoConnectionException {
        init();
        em.fillData(o,datafield,table);
    }

    public Integer createTempObject(EasyObject o)  throws BDException, NoConnectionException, IOException {
        init();
        return em.createTempObject(o);
    }


    public void copyDataFromTemp(Integer tempObjectId, Integer objectId ) throws BDException, NoConnectionException, IOException {
        init();
        em.copyDataFromTemp(tempObjectId, objectId);
    }

    /** Creates a HotspotObject
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public Integer createHotspotObject( HotspotObject r) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            return em.createHotspotObject(r);
        }
    }

    /** Updates HotspotObject information
     *
     * @param r
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateHotspotObject( HotspotObject r ) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.updateHotspotObject(r);
        }
    }

    /** Find HotspotObjects by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<HotspotObject> findHotspotObjects(CriteriosHotspotObject criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findHotspotObjects(criteria);
    }

    public HotspotObject getHotspotObject(Integer hsId, Integer objectId) throws NoConnectionException, BDException, RegistroNoExisteException, IOException {
        init();
        return em.getHotspotObject(hsId, objectId);
    }

    public List<EasyObject> getObjects(Integer hsId,int block) throws NoConnectionException, BDException, RegistroNoExisteException, IOException {
        init();
        return em.getObjects(hsId,block);
    }


    /** Deletes a HotspotObject
     *
     * @param id
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteHotspotObject(Integer hsId, Integer objId) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.deleteHotspotObject(hsId, objId);
        }
    }


    /** Creates a category
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createCategory( Category p) throws NoConnectionException, BDException, IOException {
        init();
        em.createCategory(p);
    }

    /** Updates category information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateCategory( Category p ) throws NoConnectionException, BDException, IOException {
        init();
        em.updateCategory(p);
    }

    /** Find categorys by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Category> findCategorys(CriteriosCategory criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findCategorys(criteria);
    }

    public Category getCategory(Integer pid) throws NoConnectionException, BDException, RegistroNoExisteException, IOException {
        init();
        return em.getCategory(pid);
    }

    /** Deletes a category
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteCategory(Integer pid) throws NoConnectionException, BDException, IOException {
        init();
        em.deleteCategory(pid);
    }

    /** Creates a keyword
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createKeyword( Keyword p) throws NoConnectionException, BDException, IOException {
        init();
        em.createKeyword(p);
    }

    /** Updates keyword information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateKeyword( Keyword p ) throws NoConnectionException, BDException, IOException {
        init();
        em.updateKeyword(p);
    }

    /** Find keywords by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<Keyword> findKeywords(CriteriosKeyword criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findKeywords(criteria);
    }

    public Keyword getKeyword(Integer pid) throws NoConnectionException, BDException, RegistroNoExisteException, IOException {
        init();
        return em.getKeyword(pid);
    }

    /** Deletes a keyword
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteKeyword(Integer pid) throws NoConnectionException, BDException, IOException, KeywordHasHotspots {
        init();
        em.deleteKeyword(pid);
    }

    public void writeData(Integer objectId, byte[] data, int datafield, String table ) throws BDException, NoConnectionException, IOException {
        init();
        em.writeData(objectId, data, datafield, table);

    }

    /** Creates a ObjCategory
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createObjCategory( ObjCategory p) throws NoConnectionException, BDException, IOException {
        init();
        em.createObjCategory(p);
    }

    /** Updates ObjCategory information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updateObjCategory( ObjCategory p ) throws NoConnectionException, BDException, IOException {
        init();
        em.updateObjCategory(p);
    }

    /** Find ObjCategorys by the given criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<ObjCategory> findObjCategorys(CriteriosObjCategory criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findObjCategorys(criteria);
    }

    /** Deletes a ObjCategory
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteObjCategory(Integer pid) throws NoConnectionException, BDException, IOException {
        init();
        em.deleteObjCategory(pid);
    }

    /**
     *
     * @param pid
     * @return
     * @throws NoConnectionException
     * @throws BDException
     * @throws RegistroNoExisteException
     */
    public ObjCategory getObjCategory(Integer pid) throws NoConnectionException, BDException, RegistroNoExisteException {
        return em.getObjCategory(pid);
    }


    /** Creates a Permission
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createPermission( Permission p) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.createPermission(p);
        }
    }

    /** Updates Permission information
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void updatePermission( Permission p ) throws NoConnectionException, BDException, IOException {
        init();
        synchronized ( UNIQUE_ACCESS )
        {
            em.updatePermission(p);
        }
    }


    /** Find Permissions by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    /*
    public List<Permission> findPermissions(CriteriosPermission criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findPermissions(criteria);
    }
     *
     */

    public Permission getPermission(String userId, Integer elementId, Integer elementType) throws IOException, NoConnectionException, BDException, RegistroNoExisteException {
        init();
        return em.getPermission(userId, elementId, elementType);
    }

    /** Deletes a Permission
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deletePermission(String userId, Integer elementId, Integer elementType) throws NoConnectionException, BDException, IOException {
        init();
        em.deletePermission(userId, elementId, elementType);
    }

    /** Creates a User
     *
     * @param p
     * @throws NoConnectionException
     * @throws BDException
     */
    public void createUser( User p) throws NoConnectionException, BDException, IOException {
        init();
        em.createUser(p);
    }


    /** Find Users by the give criteria
     *
     * @param criteria
     * @return
     * @throws NoConnectionException
     * @throws BDException
     */
    public List<User> findUsers(CriteriosUser criteria) throws NoConnectionException, BDException, IOException {
        init();
        return em.findUsers(criteria);
    }


    /** Deletes a User
     *
     * @param pid
     * @throws NoConnectionException
     * @throws BDException
     */
    public void deleteUser(String userid) throws NoConnectionException, BDException, IOException {
        init();
        em.deleteUser(userid);
    }


    public List<Rol> listRoles() throws NoConnectionException, BDException, IOException {
        init();
        return em.listRoles();
    }

    public Rol getRol(String rolname) throws NoConnectionException, BDException, IOException {
        init();
        return em.getRol(rolname);
    }


    public List<String> getMapMembers(Integer mapid) throws NoConnectionException, BDException, IOException, RegistroNoExisteException {
        init();
        return em.getMapMembers(mapid);
    }

    public List<String> getMapOwners(Integer mapid) throws NoConnectionException, BDException, IOException, RegistroNoExisteException {
        init();
        return em.getMapOwners(mapid);
    }


    public Integer getDefaultIconId() throws NoConnectionException, BDException, IOException {
        init();
        return em.getDefaultIconId();
    }

    /**
     * @return the myUser
     */
    public com.bnmi.ourmap.model.User getMyUser() {
        return em.getMyUser();
    }

    /**
     * @param myUser the myUser to set
     */
    public void setMyUser(com.bnmi.ourmap.model.User myUser) {
        em.setMyUser(myUser);
    }


    public List<User> getUsers(Collection<String> uids ) throws NoConnectionException, BDException, IOException {
        init();
        return em.getUsers(uids);
    }

    public List<User> getProjectMembers( Integer pid ) throws NoConnectionException, BDException, IOException {
        init();
        return em.getProjectMembers(pid);
    }

    public List<Project> myProjects(User u) throws NoConnectionException, BDException, IOException {
        init();
        return em.myProjects(u);
    }

    public void grantMembership( String uid, Integer elementId, Integer elementType ) throws NoConnectionException, BDException, IOException {
        init();
        em.grantMembership( uid, elementId, elementType );
    }

    public void grantOwnership( String uid, Integer elementId, Integer elementType ) throws NoConnectionException, BDException, IOException {
        init();
        em.grantOwnership( uid, elementId, elementType );
    }

    public void revokeMembership( String uid, Integer elementId, Integer elementType ) throws NoConnectionException, BDException, IOException {
        init();
        em.revokeMembership( uid, elementId, elementType );
    }

    public void revokeOwnership( String uid, Integer elementId, Integer elementType ) throws NoConnectionException, BDException, IOException {
        init();
        em.revokeOwnership( uid, elementId, elementType );
    }

    public static User getVisitor() {
        return EasyManager.getVisitor();
    }

    public void checkMapConsistency(Map map) throws NoConnectionException, BDException, IOException {
        init();
        em.checkMapConsistency(map);
    }

    public void checkProjectConsistency( Project p ) throws NoConnectionException, BDException, IOException {
        init();
        em.checkProjectConsistency(p);
    }

    public String getStatus(User u, Integer elementId, Integer elementType ) throws NoConnectionException, BDException, IOException {
        init();
        return em.getStatus(u,elementId,elementType);
    }

}