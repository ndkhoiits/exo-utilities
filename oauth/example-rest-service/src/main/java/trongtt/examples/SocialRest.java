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
package trongtt.examples;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.exoplatform.services.rest.resource.ResourceContainer;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Anh Kien
 *          nguyenanhkien2a@gmail.com
 * Dec 2, 2010  
 */
@Path("SocialRest")
public class SocialRest implements ResourceContainer
{
   @GET
   @Path("infos")
   public Response execute(@Context HttpServletRequest request)
   {
      //String response = "{\"entry\":{\"demo\":{\"subject\":\"test\",\"java\":\"oauth\"}}}";
      String response = "SocialRest hello you";
      return Response.ok(response).build();
   }
}
