/**
 * Copyright (C) 2009 eXo Platform SAS.
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

package org.oauthconsumer.portlet;

import org.oauthconsumer.service.StorageConsumerInfo;
import org.w3c.dom.Element;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.MimeResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author <a href="mailto:khoi.nguyen@exoplatform.com">Nguyen Duc Khoi</a>  
 * Nov 24, 2010
 */
public class OauthConsumerPortlet extends GenericPortlet
{
   @Override
   protected void doHeaders(RenderRequest request, RenderResponse response)
   {
      super.doHeaders(request, response);
      Element cssElement = response.createElement("link");
      cssElement.setAttribute("href", response.encodeURL(request.getContextPath() + "/skin/DefaultStylesheet.css"));
      cssElement.setAttribute("rel", "stylesheet");
      cssElement.setAttribute("type", "text/css");
      
      response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, cssElement);
      
   }
   @RenderMode(name = "view")
   public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      getPortletContext().getRequestDispatcher("/jsp/index.jsp").include(request, response);
   }
   
   @RenderMode(name = "edit")
   public void edit(RenderRequest request, RenderResponse response) throws PortletException, IOException 
   {
      getPortletContext().getRequestDispatcher("/jsp/index.jsp").include(request, response);
   }
   
   @ProcessAction(name = "addConsumer")
   public void addConsumer(ActionRequest request, ActionResponse response)
   {
      String consumerKey = request.getParameter("consumer_key");
      String consumerSecret = request.getParameter("consumer_secret");
      String keyTypeStr = request.getParameter("key_type");
      String callbackURL = request.getParameter("callback_url");
      String gadgetURIStr = request.getParameter("gadget_uri");
      String serviceName = request.getParameter("service_name");

      StorageConsumerInfo oAuthStore = new StorageConsumerInfo();
      oAuthStore.addKey(consumerKey, consumerSecret, keyTypeStr, callbackURL, gadgetURIStr, serviceName);
   }
}
