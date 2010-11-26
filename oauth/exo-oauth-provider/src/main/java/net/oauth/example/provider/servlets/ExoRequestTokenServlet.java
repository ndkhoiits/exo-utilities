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
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.example.provider.core.ExoOAuthProvider;
import net.oauth.server.OAuthServlet;

import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.web.AbstractHttpServlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */
public class ExoRequestTokenServlet extends AbstractHttpServlet
{

   @Override
   protected void afterInit(ServletConfig config) throws ServletException
   {
      try
      {
         ExoOAuthProvider.loadConsumers(config);
      }
      catch (IOException e)
      {
         throw new ServletException(e.getMessage());
      }
   }

   @Override
   protected void onService(ExoContainer container, HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException
   {
      try
      {
         OAuthMessage requestMessage = OAuthServlet.getMessage(req, null);

         OAuthConsumer consumer = ExoOAuthProvider.getConsumer(requestMessage);

         OAuthAccessor accessor = new OAuthAccessor(consumer);
         ExoOAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
         {
            // Support the 'Variable Accessor Secret' extension
            // described in http://oauth.pbwiki.com/AccessorSecret
            String secret = requestMessage.getParameter("oauth_accessor_secret");
            if (secret != null)
            {
               accessor.setProperty(OAuthConsumer.ACCESSOR_SECRET, secret);
            }
         }
         // generate request_token and secret
         ExoOAuthProvider.generateRequestToken(accessor);

         res.setContentType("text/plain");
         OutputStream out = res.getOutputStream();
         OAuth.formEncode(
            OAuth.newList("oauth_token", accessor.requestToken, "oauth_token_secret", accessor.tokenSecret), out);
         out.close();

      }
      catch (Exception e)
      {
         ExoOAuthProvider.handleException(e, req, res, true);
      }

   }
}
