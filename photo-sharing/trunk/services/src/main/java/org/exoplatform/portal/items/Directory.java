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

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.PrimaryType;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Jul 21, 2010  
 */
@PrimaryType(name = "nt:folder")
public abstract class Directory extends Item
{
   @OneToMany
   protected abstract Map<String, Item> getItemMap();

   @OneToMany
   protected abstract Map<String, Document> getDocumentMap();

   @Create
   protected abstract Document createDocument();

   @Create
   protected abstract Directory createDirectory();

   public Collection<Item> getItems()
   {
      return getItemMap().values();
   }

   public Item getItem(String name)
   {
      return getItemMap().get(name);
   }

   public void removeItem(String name)
   {
      getItemMap().remove(name);
   }

   public Collection<Document> getDocuments()
   {
      return getDocumentMap().values();
   }

   public Document getDocument(String name)
   {
      return getDocumentMap().get(name);
   }

   public Directory addDirectory(String name)
   {
      Directory dir = createDirectory();
      getItemMap().put(name, dir);
      return dir;
   }

   public Document addDocument(String name, String mimeType, InputStream data)
   {
      Document document = createDocument();
      getItemMap().put(name, document);
      document.update(mimeType, data);
      return document;
   }
}
