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
package net.oauth.example.provider.core;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.OAuthValidator;
import net.oauth.server.OAuthServlet;

import org.apache.commons.codec.digest.DigestUtils;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.security.Identity;
import org.picocontainer.Startable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */

public class ExoOAuth3LeggedProviderService implements Startable
{
   private ExoCache<String, OAuthAccessor> tokens;
   
   public ExoOAuth3LeggedProviderService(OAuthValidator validator, CacheService cService)
   {
      tokens = cService.getCacheInstance(ExoOAuth3LeggedProviderService.class.getSimpleName());
   }

   private static final Map<String, OAuthConsumer> ALL_CONSUMERS = Collections
      .synchronizedMap(new HashMap<String, OAuthConsumer>(10));
   
   public void start()
   {
      try
      {
         loadConsumers();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   public void stop()
   {
   }

   private void loadConsumers() throws IOException
   {
      Properties p = new Properties();
      String resourceName = "provider.properties";
      URL resource = ExoOAuth3LeggedProviderService.class.getResource(resourceName);
      if (resource == null)
      {
         throw new IOException("resource not found: " + resourceName);
      }
      InputStream stream = resource.openStream();
      try
      {
         p.load(stream);
      }
      finally
      {
         stream.close();
      }

      // for each entry in the properties file create a OAuthConsumer
      for (Map.Entry<Object, Object> prop : p.entrySet())
      {
         String consumer_key = (String)prop.getKey();
         // make sure it's key not additional properties
         if (!consumer_key.contains("."))
         {
            String consumer_secret = (String)prop.getValue();
            if (consumer_secret != null)
            {
               String consumer_description = (String)p.getProperty(consumer_key + ".description");
               String consumer_callback_url = (String)p.getProperty(consumer_key + ".callbackURL");
               // Create OAuthConsumer w/ key and secret
               OAuthConsumer consumer = new OAuthConsumer(consumer_callback_url, consumer_key, consumer_secret, null);
               consumer.setProperty("name", consumer_key);
               consumer.setProperty("description", consumer_description);
               ALL_CONSUMERS.put(consumer_key, consumer);
            }
         }
      }

   }

   public synchronized OAuthConsumer getConsumer(OAuthMessage requestMessage) throws IOException, OAuthProblemException
   {

      OAuthConsumer consumer = null;
      // try to load from local cache if not throw exception
      String consumer_key = requestMessage.getConsumerKey();

      consumer = ExoOAuth3LeggedProviderService.ALL_CONSUMERS.get(consumer_key);

      if (consumer == null)
      {
         OAuthProblemException problem = new OAuthProblemException("token_rejected");
         throw problem;
      }

      return consumer;
   }

   /**
    * Get the access token and token secret for the given oauth_token. 
    */
   public OAuthAccessor getAccessor(OAuthMessage requestMessage) throws IOException, OAuthProblemException
   {

      // try to load from local cache if not throw exception
      String consumer_token = requestMessage.getToken();
      OAuthAccessor accessor = null;

      OAuthAccessor a = tokens.get(consumer_token);
      if (a != null)
      {
         if (a.requestToken != null)
         {
            if (a.requestToken.equals(consumer_token))
            {
               accessor = a;
            }
         }
         else if (a.accessToken != null)
         {
            if (a.accessToken.equals(consumer_token))
            {
               accessor = a;
            }
         }
      }

      if (accessor == null)
      {
         throw new OAuthProblemException("token_expired");
      }

      return accessor;
   }

   /**
    * Set the access token 
    */
   public synchronized void markAsAuthorized(OAuthAccessor accessor, Identity identity) throws OAuthException
   {

      // first remove the accessor from cache
      //      ALL_TOKENS.remove(accessor);

      accessor.setProperty("user", identity.getUserId());
      accessor.setProperty("user_roles", identity.getRoles());
      accessor.setProperty("authorized", Boolean.TRUE);

      // update token in local cache
      //      ALL_TOKENS.add(accessor);
   }

   /**
    * Generate a fresh request token and secret for a consumer.
    * 
    * @throws OAuthException
    */
   public synchronized void generateRequestToken(OAuthAccessor accessor) throws OAuthException
   {

      // generate oauth_token and oauth_secret
      String consumer_key = (String)accessor.consumer.getProperty("name");
      // generate token and secret based on consumer_key

      // for now use md5 of name + current time as token
      String token_data = consumer_key + System.nanoTime();
      String token = DigestUtils.md5Hex(token_data);
      // for now use md5 of name + current time + token as secret
      String secret_data = consumer_key + System.nanoTime() + token;
      String secret = DigestUtils.md5Hex(secret_data);

      accessor.requestToken = token;
      accessor.tokenSecret = secret;
      accessor.accessToken = null;

      // add to the local cache
      tokens.put(accessor.requestToken, accessor);
      System.out.println(tokens.getCacheSize());
   }

   /**
    * Generate a fresh request token and secret for a consumer.
    * 
    * @throws OAuthException
    */
   public synchronized void generateAccessToken(OAuthAccessor accessor) throws OAuthException
   {

      // generate oauth_token and oauth_secret
      String consumer_key = (String)accessor.consumer.getProperty("name");
      // generate token and secret based on consumer_key

      // for now use md5 of name + current time as token
      String token_data = consumer_key + System.nanoTime();
      String token = DigestUtils.md5Hex(token_data);
      // first remove the accessor from cache
      //      ALL_TOKENS.remove(accessor);
      tokens.remove(accessor.requestToken);

      accessor.requestToken = null;
      accessor.accessToken = token;

      // update token in local cache
      tokens.put(accessor.accessToken, accessor);
   }

   public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response,
      boolean sendBody) throws IOException, ServletException
   {
      String realm = (request.isSecure()) ? "https://" : "http://";
      realm += request.getLocalName();
      OAuthServlet.handleException(response, e, realm, sendBody);
   }
}
