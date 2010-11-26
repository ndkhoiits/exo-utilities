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

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.example.provider.core.ExoOAuthProviderService;
import net.oauth.server.OAuthServlet;

import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.web.AbstractHttpServlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */
public class ExoAccessTokenServlet extends AbstractHttpServlet
{
   @Override
   protected void onService(ExoContainer container, HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException
   {
      try
      {
         OAuthMessage requestMessage = OAuthServlet.getMessage(req, null);

         ExoOAuthProviderService provider = (ExoOAuthProviderService)container.getComponentInstanceOfType(ExoOAuthProviderService.class);
         OAuthAccessor accessor = provider.getAccessor(requestMessage);
         ExoOAuthProviderService.VALIDATOR.validateMessage(requestMessage, accessor);

         // make sure token is authorized
         if (!Boolean.TRUE.equals(accessor.getProperty("authorized")))
         {
            OAuthProblemException problem = new OAuthProblemException("permission_denied");
            throw problem;
         }
         
         // generate access token and secret
         provider.generateAccessToken(accessor);

         res.setContentType("text/plain");
         OutputStream out = res.getOutputStream();
         OAuth.formEncode(
            OAuth.newList("oauth_token", accessor.accessToken, "oauth_token_secret", accessor.tokenSecret), out);
         out.close();

      }
      catch (Exception e)
      {
         ExoOAuthProviderService.handleException(e, req, res, true);
      }
   }
}
