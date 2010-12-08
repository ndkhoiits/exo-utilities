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
import net.oauth.example.consumer.service.ExoOAuth2LeggedConsumerService;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Anh Kien
 *          nguyenanhkien2a@gmail.com
 * Dec 4, 2010  
 */
public class Orkut2LeggedConsumerServlet extends HttpServlet
{
   private static final long serialVersionUID = 1L;
   
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String consumer = "orkut";
    String restEndpointUrl = "http://sandbox.orkut.com/social/rest/appdata/@me/@self/@app?xoauth_requestor_id=03067092798963641994";     
    
    try {
      ExoOAuth2LeggedConsumerService oauthService = new ExoOAuth2LeggedConsumerService();
      ExoOAuthMessage result = oauthService.send(consumer, restEndpointUrl, request, response);
      ExoOAuth2LeggedConsumerService.copyResponse(result, response);
    } catch (Exception e) {
      ExoOAuth2LeggedConsumerService.handleException(e, request, response, consumer);
    }
   }
}
