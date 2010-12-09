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
package net.oauth.example.provider.core;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.server.OAuthServlet;

import org.exoplatform.container.ExoContainerContext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Anh Kien
 *          nguyenanhkien2a@gmail.com
 * Dec 1, 2010  
 */
public class ExoOAuth2LeggedProviderService
{    
   public ExoOAuth2LeggedProviderService() {
   }

   public synchronized OAuthConsumer getConsumer(OAuthMessage requestMessage) throws IOException, OAuthProblemException
   {

      OAuthConsumer consumer = null;
      // try to load from local cache if not throw exception
      String consumer_key = requestMessage.getConsumerKey();

      ExoOAuthConsumerStorage consumerManagement = (ExoOAuthConsumerStorage) ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(ExoOAuthConsumerStorage.class);
      consumer =  consumerManagement.getConsumer(consumer_key);

      if (consumer == null)
      {
         OAuthProblemException problem = new OAuthProblemException("token_rejected");
         throw problem;
      }

      return consumer;
   }
   
   public OAuthAccessor getAccessor(OAuthMessage requestMessage) throws IOException, OAuthProblemException
   {
      OAuthConsumer authConsumer = getConsumer(requestMessage);
      OAuthAccessor accessor = new OAuthAccessor(authConsumer);      
      return accessor;
   }
   
   public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response,
         boolean sendBody) throws IOException, ServletException
      {
         String realm = (request.isSecure()) ? "https://" : "http://";
         realm += request.getLocalName();
         OAuthServlet.handleException(response, e, realm, sendBody);
      }
}
