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
package org.exoplatform.portal.utilities.portletexamples.portlet;

import java.io.IOException;
import java.util.Map;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * 
 * @author <a href="mailto:ndkhoi168@gmail.com">Nguyen Duc Khoi</a>
 */
public class UserAttributeExamplePortlet extends GenericPortlet
{
   @Override
   protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      @SuppressWarnings("unchecked")
      Map<String, String> userAttributes = (Map<String, String>) request.getAttribute(PortletRequest.USER_INFO);
      String firstName = "";
      String lastName = "";
      String gender = "";
      
      if (userAttributes != null)
      {
         firstName = (String)userAttributes.get("user.name.given");
         lastName = (String)userAttributes.get("user.name.family");
         gender = (String)userAttributes.get("user.gender");
         request.setAttribute("firstname", firstName);
         request.setAttribute("lastname", lastName);
         request.setAttribute("gender", gender);
      }
      getPortletContext().getRequestDispatcher("/jsp/view.jsp").include(request, response);
   }
}
