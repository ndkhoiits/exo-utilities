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
import java.util.Date;

import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.Owner;
import org.chromattic.api.annotations.PrimaryType;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Jul 22, 2010  
 */

@PrimaryType(name = "nt:file")
public abstract class Document extends Item
{
   @OneToOne
   @Owner
   @MappedBy("jcr:content")
   protected abstract Content getContent();
   
   protected abstract void setContent(Content content);
   
   @Create
   protected abstract Content createContent();
   
   public void update(String mimeType, InputStream data)
   {
      Content content = getContent();
      
      if (content == null)
      {
         content = createContent();
         setContent(content);
      }
      
      content.setData(data);
      content.setMimeType(mimeType);
      content.setLastModified(new Date());
   }
}
