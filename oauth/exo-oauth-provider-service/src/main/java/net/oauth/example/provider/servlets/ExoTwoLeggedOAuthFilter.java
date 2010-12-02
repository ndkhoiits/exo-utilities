/*
 * Copyright (C) 2003-2010 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package net.oauth.example.provider.servlets;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.example.provider.core.ExoOAuthProviderService;
import net.oauth.example.provider.core.ExoTwoLeggedOAuthProviderService;
import net.oauth.server.OAuthServlet;

import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.web.AbstractFilter;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Anh Kien
 *          nguyenanhkien2a@gmail.com
 * Dec 2, 2010  
 */
public class ExoTwoLeggedOAuthFilter extends AbstractFilter
{

   /* (non-Javadoc)
    * @see javax.servlet.Filter#destroy()
    */
   @Override
   public void destroy()
   {
      // TODO Auto-generated method stub

   }

   /* (non-Javadoc)
    * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
    */
   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
         ServletException
   {
      
      try
      {
         OAuthMessage requestMessage = OAuthServlet.getMessage((HttpServletRequest)request, null);
         requestMessage.requireParameters(OAuth.OAUTH_CONSUMER_KEY, OAuth.OAUTH_SIGNATURE, OAuth.OAUTH_SIGNATURE_METHOD);
         
         ExoContainer container = getContainer();
         ExoTwoLeggedOAuthProviderService provider = (ExoTwoLeggedOAuthProviderService)container.getComponentInstanceOfType(ExoTwoLeggedOAuthProviderService.class);
         OAuthAccessor accessor = provider.getAccessor(requestMessage);
         ExoOAuthProviderService.VALIDATOR.validateMessage(requestMessage, accessor);
         
         chain.doFilter(request, response);
      }
      catch (Exception e)
      {
         ExoOAuthProviderService.handleException(e, (HttpServletRequest)request, (HttpServletResponse)response, false);
      }

   }
}
