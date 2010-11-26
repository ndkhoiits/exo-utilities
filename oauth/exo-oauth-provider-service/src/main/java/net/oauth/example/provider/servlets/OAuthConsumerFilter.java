/*
 * Copyright (C) 2010 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package net.oauth.example.provider.servlets;

import net.oauth.example.provider.core.ExoOAuthProviderService;

import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.web.AbstractFilter;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */
public class OAuthConsumerFilter extends AbstractFilter
{

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException
   {
      try
      {
         OAuthMessage requestMessage = OAuthServlet.getMessage((HttpServletRequest)request, null);

         ExoContainer container = getContainer();
         ExoOAuthProviderService provider = (ExoOAuthProviderService)container.getComponentInstanceOfType(ExoOAuthProviderService.class);
         OAuthAccessor accessor = provider.getAccessor(requestMessage);
         
         ExoOAuthProviderService.VALIDATOR.validateMessage(requestMessage, accessor);
         request.setAttribute("OAUTH_USER_ID", accessor.getProperty("user"));
         chain.doFilter(request, response);
      }
      catch (Exception e)
      {
         ExoOAuthProviderService.handleException(e, (HttpServletRequest)request, (HttpServletResponse)response, false);
      }

   }

   public void destroy()
   {
      // TODO Auto-generated method stub
      
   }
}
