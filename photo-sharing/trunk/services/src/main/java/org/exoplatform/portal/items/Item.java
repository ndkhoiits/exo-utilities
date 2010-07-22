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
package org.exoplatform.portal.items;

import java.util.Date;

import org.chromattic.api.annotations.Id;
import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.Path;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Jul 22, 2010  
 */


@PrimaryType(name = "nt:hierarchyNode")
public abstract class Item
{
   /**
    * Return the item id
    * 
    * @return the item id
    */
   @Id
   public abstract String getId();
   
   /**
    * Return the item path
    * 
    * @return the item path
    */
   @Path
   public abstract String getPath();
   
   /**
    * Return the item name
    * 
    * @return the item name
    */
   @Name
   public abstract String getName();
   
   /**
    * Return the directory containing this file
    * 
    * @return the parent directory
    */
   @ManyToOne
   public abstract Directory getParent();
   
   @Property(name = "jcr:created")
   public abstract Date getCreated();
}
