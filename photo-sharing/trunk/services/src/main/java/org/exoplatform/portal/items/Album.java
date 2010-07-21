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

import java.util.List;

import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Jul 21, 2010  
 */
@PrimaryType(name = "fs:album")
public abstract class Album
{
   /**
    * Return the album name
    * @return the album name
    */
   @Name
   public abstract String getName();
   
   /**
    * Return the album title
    * 
    * @return the album title
    */
   @Property(name = "title")
   public abstract String getTitle();
   
   /**
    * Update the album title
    * @param title the new album title
    */
   public abstract void setTitle(String title);
   
   /**
    * Return the album description
    * @return
    */
   @Property(name = "description")
   public abstract String getDescription();
   
   /**
    * Update the album description
    * @param description
    */
   public abstract void setDescription(String description);
   
   /**
    * Return the list of album children
    * @return
    */
   @OneToMany
   public abstract List<Album> getChildren();
   
   /**
    * Return the album parent
    * @return the album parent
    */
   @ManyToOne
   public abstract Album getParent();
   
   /**
    * Update the album parent
    * @param cat the album parent
    */
   public abstract void setParent(Album cat);
}

