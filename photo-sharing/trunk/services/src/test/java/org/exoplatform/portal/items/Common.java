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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Jul 22, 2010  
 */
public class Common
{
   private String path;
   
   private String fileName;

   public void setPath(String path)
   {
      this.path = path;
   }

   public String getPath()
   {
      return path;
   }

   public void setFileName(String fileName)
   {
      this.fileName = fileName;
   }

   public String getFileName()
   {
      return fileName;
   }
   
   public void writeData(InputStream is) throws IOException
   {
      String dir = getWorkingDir();
      FileOutputStream fos = new FileOutputStream(new File(dir + fileName));
      byte[] buf = new byte[1024];
      int i = 0;
      while ((i = is.read(buf)) != -1)
      {
         fos.write(buf, 0, i);
      }
   }

   public File readFile() throws FileNotFoundException
   {
      URL url = Common.class.getResource(path);
      File file = new File(url.getFile());
      return file;
   }

   private String getWorkingDir()
   {
      URL url = Common.class.getResource(path);

      File file = new File(url.getFile());
      String dir = file.getParent();
      return dir;
   }
   
   public static void main(String[] args) throws IOException
   {
      FileInputStream fis1 = new FileInputStream(new File("/home/khoinguyen/mkdev.txt"));
      FileInputStream fis2 = new FileInputStream(new File("/home/khoinguyen/mkdev.txt"));
      byte[] buff = new byte[1024];
      byte[] buff2 = new byte[1024];
      fis1.read(buff);
      fis2.read(buff2);
      System.out.println(new String(buff));
      System.out.println(new String(buff2));
   }
  
}
