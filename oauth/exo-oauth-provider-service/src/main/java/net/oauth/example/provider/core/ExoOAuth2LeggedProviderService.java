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
import net.oauth.OAuthValidator;

import org.exoplatform.services.cache.CacheService;

import java.io.IOException;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Anh Kien
 *          nguyenanhkien2a@gmail.com
 * Dec 1, 2010  
 */
public class ExoOAuth2LeggedProviderService extends ExoOAuth3LeggedProviderService
{    
   public ExoOAuth2LeggedProviderService(OAuthValidator validator, CacheService cService)
   {
      super(validator, cService);
   }

   public OAuthAccessor getAccessor(OAuthMessage requestMessage) throws IOException, OAuthProblemException
   {
      OAuthConsumer authConsumer = getConsumer(requestMessage);
      OAuthAccessor accessor = new OAuthAccessor(authConsumer);      
      return accessor;
   }
}
