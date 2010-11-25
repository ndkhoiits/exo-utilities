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
import net.oauth.example.provider.core.ExoOAuthProvider;
import net.oauth.server.OAuthServlet;

import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.web.AbstractHttpServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */
public class ExoAuthorizationServlet extends AbstractHttpServlet
{
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
   {

      try
      {
         
         ExoContainer pcontainer = PortalContainer.getInstance();
         
         OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);

         OAuthAccessor accessor = ExoOAuthProvider.getAccessor(requestMessage);

         if (Boolean.TRUE.equals(accessor.getProperty("authorized")))
         {
            // already authorized send the user back
            returnToConsumer(request, response, accessor);
         }
         else
         {
            sendToAuthorizePage(request, response, accessor);
         }

      }
      catch (Exception e)
      {
         ExoOAuthProvider.handleException(e, request, response, true);
      }

   }

   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
   {

      try
      {
         OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);

         OAuthAccessor accessor = ExoOAuthProvider.getAccessor(requestMessage);

         String userId = request.getParameter("userId");
         if (userId == null)
         {
            sendToAuthorizePage(request, response, accessor);
         }
         // set userId in accessor and mark it as authorized
         ExoOAuthProvider.markAsAuthorized(accessor, userId);

         returnToConsumer(request, response, accessor);

      }
      catch (Exception e)
      {
         ExoOAuthProvider.handleException(e, request, response, true);
      }
   }

   private void sendToAuthorizePage(HttpServletRequest request, HttpServletResponse response, OAuthAccessor accessor)
      throws IOException, ServletException
   {
      String callback = request.getParameter("oauth_callback");
      if (callback == null || callback.length() <= 0)
      {
         callback = "none";
      }
      String consumer_description = (String)accessor.consumer.getProperty("description");
      request.setAttribute("CONS_DESC", consumer_description);
      request.setAttribute("CALLBACK", callback);
      request.setAttribute("TOKEN", accessor.requestToken);
      request.getRequestDispatcher //
         ("/authorize.jsp").forward(request, response);

   }

   private void returnToConsumer(HttpServletRequest request, HttpServletResponse response, OAuthAccessor accessor)
      throws IOException, ServletException
   {
      // send the user back to site's callBackUrl
      String callback = request.getParameter("oauth_callback");
      if ("none".equals(callback) && accessor.consumer.callbackURL != null
         && accessor.consumer.callbackURL.length() > 0)
      {
         // first check if we have something in our properties file
         callback = accessor.consumer.callbackURL;
      }

      if ("none".equals(callback))
      {
         // no call back it must be a client
         response.setContentType("text/plain");
         PrintWriter out = response.getWriter();
         out.println("You have successfully authorized '" + accessor.consumer.getProperty("description")
            + "'. Please close this browser window and click continue" + " in the client.");
         out.close();
      }
      else
      {
         // if callback is not passed in, use the callback from config
         if (callback == null || callback.length() <= 0)
            callback = accessor.consumer.callbackURL;
         String token = accessor.requestToken;
         if (token != null)
         {
            callback = OAuth.addParameters(callback, "oauth_token", token);
         }

         response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
         response.setHeader("Location", callback);
      }
   }

   private static final long serialVersionUID = 1L;

}
