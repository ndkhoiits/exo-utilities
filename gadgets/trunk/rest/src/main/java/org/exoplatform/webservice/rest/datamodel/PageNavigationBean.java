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

import java.util.List;

import org.exoplatform.webservice.rest.annotation.FieldMapping;
import org.exoplatform.webservice.rest.api.SerializableBean;
import org.exoplatform.portal.config.model.PageNavigation;
import org.exoplatform.portal.config.model.PageNode;

/**
 * @author <a href="mailto:hoang281283@gmail.com">Minh Hoang TO</a>
 * @date
 */

public class PageNavigationBean implements SerializableBean
{

   @FieldMapping(fieldName = "ownerType", getterMethod = "getOwnerType" , propertyName = "ownerType")
   private String ownerType;
   
   @FieldMapping(fieldName = "ownerId", getterMethod = "getOwnerId", propertyName = "ownerId")
   private String ownerId;
   
   @FieldMapping(fieldName = "priority", getterMethod = "getPriority", propertyName = "navigationPriority")
   private int priority;
   
   @FieldMapping(fieldName = "children", getterMethod = "getChildren", propertyName = "nodesUnderNavigation")
   private String children;
   
   public PageNavigationBean(String _ownerType, String _ownerId, int _priority, String _children)
   {
      this.ownerType = _ownerType;
      this.ownerId = _ownerId;
      this.priority = _priority;
      this.children = _children;
   }
   
   public String getOwnerType()
   {
      return this.ownerType;
   }
   
   public String getOwnerId()
   {
      return this.ownerId;
   }
   
   public int getPriority()
   {
      return this.priority;
   }
   
   public String getChildren()
   {
      return this.children;
   }
   
   public static PageNavigationBean build(PageNavigation pageNavigation)
   {
      StringBuffer buffer = new StringBuffer();
      List<PageNode> nodes = pageNavigation.getNodes();
      
      for(PageNode node : nodes)
      {
         buffer.append(node.getName() + "|");
      }
      
      return new PageNavigationBean(pageNavigation.getOwnerType(), pageNavigation.getOwnerId(), pageNavigation.getPriority(), buffer.toString());
   }
}
