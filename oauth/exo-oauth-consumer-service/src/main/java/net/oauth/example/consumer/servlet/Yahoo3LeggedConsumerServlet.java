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
package net.oauth.example.consumer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.example.consumer.ExoOAuthMessage;
import net.oauth.example.consumer.service.OAuth3LeggedConsumerService;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Anh Kien
 *          nguyenanhkien2a@gmail.com
 * Dec 4, 2010  
 */
public class Yahoo3LeggedConsumerServlet extends HttpServlet
{
   private static final long serialVersionUID = 1L;
   
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      String consumer = "yahoo";
      String restEndpointUrl = "http://appstore.apps.yahooapis.com/social/rest/";     
      
      try {
        OAuth3LeggedConsumerService oauthService = new OAuth3LeggedConsumerService();
        ExoOAuthMessage result = oauthService.send(consumer, restEndpointUrl, request, response);
        OAuth3LeggedConsumerService.copyResponse(result, response);
      } catch (Exception e) {
         OAuth3LeggedConsumerService.handleException(e, request, response, consumer);
      }
   }
}
