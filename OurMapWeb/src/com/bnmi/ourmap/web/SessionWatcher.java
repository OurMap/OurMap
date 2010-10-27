/*******************************************************************************

com.bnmi.ourmap.web.SessionWatcher.java
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
import com.inga.security.User;
import com.inga.utils.SigarUtils;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

/**
 *
 * @author Manuel Camilo Cuesta
 */
public class SessionWatcher implements HttpSessionListener, HttpSessionActivationListener {

    private static Logger log = Logger.getLogger(SessionWatcher.class);
    
    public static boolean debug = true;
    private static SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    
    /** Se llama cuando la sesi'on es creada */
    public void sessionCreated(HttpSessionEvent se) {
    }

    /** Se llama justo antes de invalidar la sesi'on*/
    public void sessionDestroyed(HttpSessionEvent se) {
        
        try
        {
        
            HttpSession session = se.getSession();

            long startTime = session.getCreationTime();
            long now = System.currentTimeMillis();
            String timeStr = SigarUtils.millisecondToDHMS(now-startTime);
            
            User u = (User) session.getAttribute(Constantes.PRINCIPAL);
            if ( u != null )
                log.info("Good bye " + u.getId() + " " + u.getRemoteAddress() + " " + session.getId() + " " + timeStr + " connected.");
            else
                log.info("Closing session " + session.getId() + " " + timeStr + " connected." );

            EasyDelegate del = (EasyDelegate) se.getSession().getAttribute(Constantes.DELEGATE);
            if ( del != null )
            {

                session.removeAttribute(Constantes.DELEGATE);
                session.removeAttribute(Constantes.PRINCIPAL);

                try
                {
                    del.clean();
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                    log.info("Could not close the connection " + session.getId() );
                }
                
            }
        }
        catch ( Exception e )
        {
            log.info("Error " + e.getMessage() );
            e.printStackTrace();
        }
        
    }
    
    public void sessionWillPassivate(HttpSessionEvent se) {
    }

    public void sessionDidActivate(HttpSessionEvent se) {
    }
    
}
