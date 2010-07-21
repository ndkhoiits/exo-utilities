/**
 * Copyright (C) 2010 eXo Platform SAS.
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

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.chromattic.api.ChromatticSession;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:trongtt@gmail.com">Tran The Trong</a>
 * @version $Revision$
 */

public class TestAlbum extends TestCase
{
   public void testCategoryCreation()
   {
      ChromatticBuilder builder = ChromatticBuilder.create();
      builder.add(Album.class);
      Chromattic chromattic = builder.build();
      ChromatticSession session = chromattic.openSession();

      try
      {
         Album album = session.insert(Album.class, "Album1");
         album.setTitle("My Album");
         System.out.println("\n\n\n\n" + album.getTitle());
         session.save();
      }
      finally
      {
         session.close();
      }
   }
}
