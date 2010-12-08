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
package net.oauth.example.consumer;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import net.oauth.ConsumerProperties;
import net.oauth.OAuthConsumer;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Anh Kien
 *          nguyenanhkien2a@gmail.com
 * Dec 3, 2010  
 */
public class ExoOAuthConsumerStorage
{
   private static ConsumerProperties consumers = null;
   private static Properties consumerProperties = null;
   
   public static OAuthConsumer getConsumer(String name)
   throws IOException {
      if (consumers == null) {
          String resourceName = "consumer.properties";
          consumerProperties = ConsumerProperties
                  .getProperties(ExoOAuthConsumerStorage.class.getResource(resourceName));
          consumers = new ConsumerProperties(consumerProperties);
      }

      synchronized (consumerProperties) {
          String key = name + ".callbackURL";
          String value = consumerProperties.getProperty(key);
          if (value == null) {
              // Compute the callbackURL from the servlet context.
              URL resource = ExoOAuthConsumerStorage.class.getResource(ExoOAuth3LeggedCallback.PATH);
              if (resource != null) {
                  value = resource.toExternalForm();
              } else {
                  value = ExoOAuth3LeggedCallback.PATH;
              }
              consumerProperties.setProperty(key, value);
          }
      }

      OAuthConsumer consumer = consumers.getConsumer(name);
      return consumer;
   }
}
