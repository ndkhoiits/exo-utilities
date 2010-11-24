/*
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
package trongtt.examples;

import org.exoplatform.services.rest.resource.ResourceContainer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * 
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */
@Path("SimpleRest")
public class SimpleRest implements ResourceContainer
{
   @GET
   @Path("/hello/{user}/")
   public Response helloUser(@PathParam("user") String user)
   {
      String response = "Hello " + user;
      return Response.ok(response).build();
   }
   
   @GET
   @Path("infos")
   public Response infos(@Context HttpServletRequest request)
   {
      String response = "Hello " + request.getParameter("name");
      return Response.ok(response).build();
   }
}
