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

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.chromattic.exo.RepositoryBootstrap;

import junit.framework.TestCase;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Jul 22, 2010  
 */
public abstract class AbstractTestCase extends TestCase
{
   protected Chromattic chromattic;
   @Override
   protected void setUp() throws Exception
   {
      RepositoryBootstrap bootstrap = new RepositoryBootstrap();
      bootstrap.bootstrap();
      
      ChromatticBuilder builder = ChromatticBuilder.create();
      builder.add(Content.class);
      builder.add(Directory.class);
      builder.add(Document.class);
      builder.add(Item.class);
      
      chromattic = builder.build();
      
      super.setUp();
   }
}
