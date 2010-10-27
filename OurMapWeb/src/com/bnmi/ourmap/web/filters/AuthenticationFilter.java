/*******************************************************************************

com.bnmi.ourmap.web.filters.AuthenticationFilter.java
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

package com.bnmi.ourmap.web.filters;



import com.bnmi.ourmap.exceptions.NoData;
import com.bnmi.ourmap.web.Constantes;
import com.bnmi.ourmap.web.Utils;
import com.inga.security.AdministradorSeguridad;
import com.inga.security.User;
import com.inga.utils.SigarUtils;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest; 
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;


/**
 * Valida la autenticación del usuario.
 * 
 * La autenticación se realiza cada vez que no haya sesión, haya expirado la sesión, o bien el navegador no tenga en el caché las
 * credenciales del usuario.
 * 
 * De lo contrario no se vuelve a verificar por razones de desempeño
 * 
 * @author Manuel Camilo Cuesta Cuesta 
  */
public class AuthenticationFilter implements Filter {

  private static Logger log = Logger.getLogger(AuthenticationFilter.class.getName());

  
  private static final boolean debug = true;
  
  private static String include[] = 
  { 
    "*.do",
    "*.jsp"
  };
  
  private static final String exclude[] = 
   { 
    "*logon.do*",
    "*index.jsp",
    "/WEB-INF/*",
    "/bye.jsp"
   };
  
  // private static Logger logger = Logger.getLogger(AuthenticationFilter.class);
  // The filter configuration object we are associated with. If
  // this value is null, this filter instance is not currently
  // configured.
  private FilterConfig filterConfig = null;

  /**
   * 
   * @param request
   *          The servlet request we are processing
   * @param result
   *          The servlet response we are creating
   * @param chain
   *          The filter chain we are processing
   * 
   * @exception IOException
   *              if an input/output error occurs
   * @exception ServletException
   *              if a servlet error occurs
   */
    @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
  ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    HttpSession session = request.getSession();
    String resource = request.getServletPath();
    String queryString = SigarUtils.validarCadena( request.getQueryString() );

    request.setCharacterEncoding("UTF-8");
    
    if ( needsFiltering(resource) )
    {

        // We check first if the user is in the session

        User u = null;

        String userid = SigarUtils.validarCadena( request.getParameter("auserid"));
        if ( userid == null )
        {
            // a user is alreay on the session
            u = (User) session.getAttribute(Constantes.PRINCIPAL);
        }
        else
        {
            // User name was given, check if it is the same as the current user logged in
            u = (User) session.getAttribute(Constantes.PRINCIPAL);
            if ( u != null )
            {
                if ( u.getId() != null && u.getLogin() != null )
                {
                    if (  ! u.getLogin().equals(userid) )
                    {
                        // Reset session if another user is trying to login
                        Utils.resetSession(request);
                        u = null ;
                    }
                }
            }
        }

        // No user on session, verify the user credentials given on the request
        if ( u == null )
        {
            try
            {
                Utils.resetSession(request);
                u = Utils.getUserFromRequest( request );
                Utils.validate(request, u);
            }
            catch ( Exception ex )
            {
                u  = null;
                //this.invalidate(request, response);
            }

        }

       //Registro.info( "Needs filter :" + resource  + " Sid:" + session.getId() );
       if ( u != null && queryString != null )
       {
           if ( ! resource.matches(".*getObject.do.*") &&
                ! resource.matches(".*listhotspots.do.*")
              )
              Utils.info( request, " -> " + resource + " " + queryString );
       }
       processFilter(request,response,chain,u,resource);
    }
    else 
    {
      chain.doFilter(request,response);
    }


    
  }
  
  public boolean needsFiltering(String resource) {
    boolean needsFilter = false;
    
    if ( isInList(include, resource ) )
        needsFilter = true;
    
    if ( isInList(exclude, resource) )
        needsFilter = false;

    return needsFilter;
    
  }
  
  public boolean isInList(String list[], String s) {
    for ( int i = 0; i < list.length; i++ )
        if ( SigarUtils.matches(list[i], s ) )
            return true;
    return false;
  }
  
  public void processFilter(HttpServletRequest request, HttpServletResponse response,FilterChain chain, User u, String resource)
  {
    try 
    {
      if ( u == null ) 
        invalidate(request,response);
      else 
        chain.doFilter(request, response);
    }
    catch (Exception t)
    {
      log.info( "Error processing request: " + t );
      try
      {
          ByteArrayOutputStream bout = new ByteArrayOutputStream();
          PrintWriter pw = new PrintWriter( bout );
          t.printStackTrace(pw);
          pw.close();
          bout.close();
          log.info( bout.toString() );
      }
      catch  (Exception ex )
      {

      }

      sendProcessingError(t, request, response);
    }
  }
  

  /**
   * Return the filter configuration object for this filter.
   */
  public FilterConfig getFilterConfig() {
    return (this.filterConfig);
  }

  /**
   * Set the filter configuration object for this filter.
   * 
   * @param filterConfig
   *          The filter configuration object
   */
  public void setFilterConfig(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }

  /**
   * Destroy method for this filter
   * 
   */
  public void destroy() {
  }

  /**
   * Init method for this filter
   * 
   */
  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
    if (filterConfig != null) 
    {
        
      log.debug("AuthenticationFilter:Initializing filter");
      
      try 
      {
        AdministradorSeguridad.init();
      }
      catch (Exception ex) {
        log.debug("Manager : " + ex.getMessage());
      }

    }
  }

  /**
   * Return a String representation of this object.
   */
    @Override
  public String toString() {
    if (filterConfig == null)
      return ("AuthenticationFilter()");
    StringBuilder sb = new StringBuilder("AuthenticationFilter(");
    sb.append(filterConfig);
    sb.append(")");
    return (sb.toString());
  }

  private void sendProcessingError(Throwable t, HttpServletRequest request, HttpServletResponse response) {


      if ( t instanceof java.lang.Exception )
      {
          Exception ex = (Exception) t ;
          ex.printStackTrace();
      }


    try
    {
      Throwable cause = t;


      while ( cause.getCause() != null )
            cause = cause.getCause();


      if ( cause instanceof NullPointerException )
      {
          cause = new NoData();
      }

      request.setAttribute("ursache", cause );

      request.getRequestDispatcher("/error.jsp").forward(request, response);
      
    }
    catch (Exception ex) {
      ex.printStackTrace(System.out);
    }
  }


  private void invalidate(HttpServletRequest request, HttpServletResponse myResp) {
    try 
    {
      request.getRequestDispatcher("/nosession.jsp").forward(request, myResp);
    }
    catch (Exception ex) {
      ex.printStackTrace(System.out);
    }
  }

}
