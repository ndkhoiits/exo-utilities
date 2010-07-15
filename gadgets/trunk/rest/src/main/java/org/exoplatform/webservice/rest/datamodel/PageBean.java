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
package org.exoplatform.webservice.rest.datamodel;

import org.exoplatform.webservice.rest.annotation.FieldMapping;
import org.exoplatform.webservice.rest.api.SerializableBean;
import org.exoplatform.portal.config.model.Page;

/**
 * @author <a href="mailto:hoang281283@gmail.com">Minh Hoang TO</a>
 * @date
 */

public class PageBean implements SerializableBean
{
   @FieldMapping(fieldName = "pageId", getterMethod = "getPageId" , propertyName = "pageId")
   private String pageId;
   
   @FieldMapping(fieldName = "pageTitle", getterMethod = "getPageTitle", propertyName = "pageTitle")
   private String pageTitle;
   
   @FieldMapping(fieldName = "accessPermissions", getterMethod = "getAccessPermissions", propertyName = "accessPermissions")
   private String accessPermissions;
   
   @FieldMapping(fieldName = "editPermission", getterMethod = "getEditPermission", propertyName = "editPermission")
   private String editPermission;
   
   public PageBean(String _pageId, String _pageTitle, String _accessPermissions, String _editPermission)
   {
      this.pageId = _pageId;
      this.pageTitle = _pageTitle;
      this.accessPermissions = _accessPermissions;
      this.editPermission = _editPermission;
   }
   
   public String getPageId()
   {
      return this.pageId;
   }
   
   public String getPageTitle()
   {
      return this.pageTitle;
   }
   
   public String getAccessPermissions()
   {
      return this.accessPermissions;
   }
   
   public String getEditPermission()
   {
      return this.editPermission;
   }
   
   public static PageBean build(Page pageData)
   {
      String accessPermissions = "";
      String[] dataAccessPermissions = pageData.getAccessPermissions();
      if(dataAccessPermissions != null)
      {
         StringBuffer buffer = new StringBuffer();
         for(String permission : dataAccessPermissions)
         {
            buffer.append(permission + ",");
         }
         accessPermissions = buffer.toString();
      }
      
      return new PageBean(pageData.getPageId(), pageData.getTitle(), accessPermissions, pageData.getEditPermission());
   }
}
