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

import net.oauth.example.provider.core.ExoOAuth3LeggedProviderService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */
public class OAuthProtectedServlet extends HttpServlet
{

   @Override
   protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      try
      {
         String userId = (String)req.getAttribute("OAUTH_USER_ID");
         resp.setContentType("text/plain");
         PrintWriter out = resp.getWriter();
         out.println("[Your UserId:" + userId + "]");
         for (Object item : req.getParameterMap().entrySet())
         {
            Map.Entry parameter = (Map.Entry)item;
            String[] values = (String[])parameter.getValue();
            for (String value : values)
            {
               out.println(parameter.getKey() + ": " + value);
            }
         }
         out.close();

      }
      catch (Exception e)
      {
         ExoOAuth3LeggedProviderService.handleException(e, req, resp, false);
      }
   }
}
