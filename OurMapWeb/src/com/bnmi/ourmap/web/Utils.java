/*******************************************************************************

com.bnmi.ourmap.web.Utils.java
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

package com.bnmi.ourmap.web;

import com.bnmi.ourmap.control.EasyDelegate;
import com.bnmi.ourmap.exceptions.UserDontExists;
import com.bnmi.ourmap.exceptions.WrongPassword;
import com.bnmi.ourmap.model.CriteriosUser;
import com.bnmi.ourmap.model.User;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.exception.RegistroNoExisteException;
import com.inga.utils.SigarUtils;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class Utils {

    private static Logger log = Logger.getLogger( Utils.class );


    public static void validate(HttpServletRequest request, com.inga.security.User u ) throws NoConnectionException, IOException, BDException, UserDontExists, WrongPassword, RegistroNoExisteException {

            HttpSession session = request.getSession();

            EasyDelegate del = new EasyDelegate(u);

            if ( u.getId() != null )
               del.validate( u.getId(), u.getPassword());
            else
               del.validateByName( u.getLogin(), u.getPassword() );

            CriteriosUser findUser = new CriteriosUser();
            if ( u.getId() != null )
               findUser.setId( u.getId() );
            else
               findUser.setNombre( u.getLogin() );
            List<User> users = del.findUsers(findUser);
            User user = users.get(0);
            u.setId( user.getId() );
            u.setLogin( user.getNombre()) ;
            del.setMyUser(user);

            session.setAttribute( Constantes.DELEGATE, del );
            session.setAttribute( Constantes.PRINCIPAL, u );
            session.setAttribute( Constantes.USER, user );

            com.inga.security.User retrieve = (com.inga.security.User) session.getAttribute(Constantes.PRINCIPAL);


            // Muestra el mensaje de bienvenida
            log.info("User connected " + retrieve.getId() + " desde " + retrieve.getRemoteAddress() + " " + retrieve.getDescription() );
            Utils.info( request, "OurMap Web Version: " + Constantes.VERSION );

        int browser = Constantes.MSIE; // default to MSIE ;

        String userDescription = retrieve.getDescription();
        if ( userDescription != null )
        {

            if( userDescription.contains("Safari") )
                browser = Constantes.SAFARI;
            else if ( userDescription.contains("MSIE") )
                browser = Constantes.MSIE ;
            else if ( userDescription.contains("Mozilla") )
                browser = Constantes.FIREFOX ;
            else
                browser = Constantes.UNKNOWN_BROWSER;

        }

        session.setAttribute("browser", new Integer(browser) );

        switch ( browser )
        {
            case Constantes.MSIE :
                session.setAttribute("baseOffset", new Integer(252));
                break;
            case Constantes.SAFARI:
            case Constantes.FIREFOX :
                session.setAttribute("baseOffset", new Integer(279));
                break;

        }


    }

    public static String escapeHtml(String text) {

        if ( text != null )
        {
            text = text.replace("<br/>", "\n" );
            //text = text.replace("<","&lt;").replace(">","&gt;");
        }

        return text;
    }

    public static String unscapeHtml(String text) {

        if ( text != null )
        {
            text = text.replace("  ", " &nbsp;");
            text = text.replace("<", "&lt;" );
            text = text.replace(">", "&gt;" );
            text = text.replace("'", "&#39;" );
            text = text.replace("\"", "&quot;");
            text = text.replace("\n", "<br/>" );

        }

        return text;
    }

    public static String unscapeHtmlForTextArea(String text) {
        if ( text != null )
        {
            text= "\n" + text ;
        }
        return text;
    }


    public static com.inga.security.User getUserFromRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        com.inga.security.User u = new com.inga.security.User();
        String userid = SigarUtils.validarCadena (request.getParameter("auserid"));
        String login = SigarUtils.validarCadena( request.getParameter("user"));
        String password = SigarUtils.validarCadena( request.getParameter("apwd"));
        u.setId( userid );
        u.setLogin( login );
        u.setPassword(password);
        u.setRemoteAddress(request.getRemoteHost() );
        u.setDescription( request.getHeader("user-agent") + " " + session.getId() );

        return u;
    }

    public static void resetSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if ( session != null )
        {
            try
            {
                session.invalidate();
            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }
        }
        request.getSession(true);
    }

    public static void info( HttpServletRequest request, String message  ) {
        try
        {
            HttpSession session = request.getSession();
            User principal = (User) session.getAttribute(Constantes.USER);
            log.info( principal.getId() + " " + session.getId() + " " + request.getRemoteHost() + " " + message );
        }
        catch ( Exception ex )
        {
            // niente
        }
    }

    public static void debug( HttpServletRequest request, String message ) {
        try
        {
            HttpSession session = request.getSession();
            User principal = (User) session.getAttribute(Constantes.USER);
            log.debug( principal.getId() + " " + session.getId() + " " + request.getRemoteHost() + " " + message );
        }
        catch ( Exception ex )
        {
            // niente
        }
    }


}
