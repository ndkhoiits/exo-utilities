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
package khoind.samples;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;

/**
 * Created by The eXo Platform SAS
 * Author : Nguyen Duc Khoi
 *          khoi.nguyen@exoplatform.com
 * Aug 9, 2010  
 */
public class HelloWorldController implements Controller
{
   private HelloService helloService;
   
   /**
    * action method.
    */
   public void handleActionRequest(ActionRequest request, ActionResponse response) throws Exception
   {
      //-- do nothing the Hello World portlet doesn't receive
      //-- action requests.
      System.out.println("\n\n\n\n\n hahahah");
   }

   /**
    * render method.
    */
   public ModelAndView handleRenderRequest(RenderRequest request, RenderResponse response) throws Exception
   {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("helloWorldMessage", "Hello My boss");
      return new ModelAndView("helloWorld", model);
   }

   /**
    * @param helloService the helloService to set
    */
   public void setHelloService(HelloService helloService)
   {
      this.helloService = helloService;
   }

   /**
    * @return the helloService
    */
   public HelloService getHelloService()
   {
      return helloService;
   }
}
