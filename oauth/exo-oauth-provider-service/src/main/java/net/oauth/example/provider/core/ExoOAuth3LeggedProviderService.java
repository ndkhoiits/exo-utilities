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
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.OAuthValidator;

import org.apache.commons.codec.digest.DigestUtils;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.security.Identity;

import java.io.IOException;

/**
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */

public class ExoOAuth3LeggedProviderService  extends ExoOAuth2LeggedProviderService
{
   private ExoCache<String, OAuthAccessor> tokens;
   
   public ExoOAuth3LeggedProviderService(OAuthValidator validator, CacheService cService)
   {
      tokens = cService.getCacheInstance(ExoOAuth3LeggedProviderService.class.getSimpleName());
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
}
