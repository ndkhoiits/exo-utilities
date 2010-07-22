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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;

import org.chromattic.api.ChromatticSession;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Jul 22, 2010  
 */

public class TestSaveData extends AbstractTestCase
{
   private final String PATH = "/conf/test.txt";
   private File file;
   private Common common;
   
   @Override
   protected void setUp() throws Exception
   {
      common = new Common();
      common.setPath(PATH);
      file = common.readFile();
      super.setUp();
   }

   public void testCreateItem() throws Exception
   {
      ChromatticSession session = chromattic.openSession();
      Directory dir = session.insert(Directory.class, "Album");
      assertNotNull(session.findByPath(Directory.class, "Album"));
      assertEquals("Album", dir.getName());
      
      Document doc = dir.addDocument("Image", new MimetypesFileTypeMap().getContentType(file), new FileInputStream(file));
      assertEquals("Image", doc.getName());
      
      session.save();
      session.close();
   }

   public void testReadItem() throws IOException
   {
//      common.setFileName("/test1.txt");

      ChromatticSession session = chromattic.openSession();

      Directory dir = session.findByPath(Directory.class, "Album");
      Document doc = dir.getDocument("Image");

      InputStream is = doc.getContent().getData();
      FileInputStream fis1 = new FileInputStream(file);
      byte[] buff1 = new byte[1024];
      byte[] buff2 = new byte[1024];
      fis1.read(buff1);
      is.read(buff2);
      assertEquals(new String(buff1), new String(buff2));
//      common.writeData(is);

      session.close();
   }
}
