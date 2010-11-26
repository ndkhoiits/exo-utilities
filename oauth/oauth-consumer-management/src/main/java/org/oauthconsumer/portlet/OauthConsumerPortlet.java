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

import org.exoplatform.portal.gadget.core.GadgetConsumerInfoService.ExoConsumerInfo;
import org.oauthconsumer.service.ConsumerInfoDataStorage;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
      ConsumerInfoDataStorage oAuthStore = new ConsumerInfoDataStorage();
      List<ExoConsumerInfo> consumers = oAuthStore.getConsumers();
      request.setAttribute("consumers", consumers);
      request.getAttribute("consumers");
      request.getAttribute("errorMsg");
      String jspPage = "index.jsp";
      getPortletContext().getRequestDispatcher("/jsp/" + jspPage).include(request, response);
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
      
      Map<String, String> errorMsg = new HashMap<String, String>();
      if (consumerKey == null || consumerKey.trim().length() == 0)
      {
         errorMsg.put("consumer_key", "Please input consumer key");
      }
      if (consumerSecret == null || consumerSecret.trim().length() == 0)
      {
         errorMsg.put("consumer_secret", "Please input consumer secret");
      }
      if (gadgetURIStr == null || gadgetURIStr.trim().length() == 0)
      {
         errorMsg.put("gadget_uri", "Please input gadget uri");
      }
      if (serviceName == null || serviceName.trim().length() == 0)
      {
         errorMsg.put("service_name", "Please input service name");
      }
      
      if (errorMsg.size() == 0)
      {
         ConsumerInfoDataStorage oAuthStore = new ConsumerInfoDataStorage();
         oAuthStore.addKey(consumerKey, consumerSecret, keyTypeStr, callbackURL, gadgetURIStr, serviceName);
      }
      else
      {
         Map<String, String> lastValues = new HashMap<String, String>();
         lastValues.put("consumer_key", consumerKey);
         lastValues.put("consumer_secret", consumerSecret);
         lastValues.put("gadget_uri", gadgetURIStr);
         lastValues.put("service_name", serviceName);
         lastValues.put("key_type", keyTypeStr);
         request.setAttribute("errorMsg", errorMsg);
         request.setAttribute("lastValue", lastValues);
      }
   }
}
