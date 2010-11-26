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

package org.oauthconsumer.service;

import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.portal.gadget.core.GadgetConsumerInfoService;
import org.exoplatform.portal.gadget.core.GadgetConsumerInfoService.ExoConsumerInfo;

import java.net.URISyntaxException;
import java.util.List;

/**
 * @author <a href="mailto:khoi.nguyen@exoplatform.com">Nguyen Duc Khoi</a>  
 * Nov 24, 2010
 */
public class ConsumerInfoDataStorage
{
   public void addKey(String consumerKey, String consumerSecret, String keyTypeStr, String callbackURL, String gadgetURIStr, String serviceName)
   {
      ExoContainer container = PortalContainer.getInstance();
      GadgetConsumerInfoService service =
         (GadgetConsumerInfoService)container.getComponentInstance(GadgetConsumerInfoService.class);
      try
      {
         service.addConsumerInfo(consumerKey, consumerSecret, keyTypeStr, callbackURL, gadgetURIStr, serviceName);
      }
      catch (URISyntaxException exp)
      {
         exp.printStackTrace();
      }
   }
   
   public List<ExoConsumerInfo> getConsumers()
   {
      ExoContainer container = PortalContainer.getInstance();
      GadgetConsumerInfoService service =
         (GadgetConsumerInfoService)container.getComponentInstance(GadgetConsumerInfoService.class);
      List<ExoConsumerInfo> listConsumer = service.getConsumers();
      
      return listConsumer;
   }
}
