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
package org.exoplatform.webservice.rest.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.exoplatform.webservice.rest.annotation.FieldMapping;
import org.exoplatform.webservice.rest.api.AbstractMapper;
import org.exoplatform.webservice.rest.api.SerializableBean;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * @author <a href="mailto:hoang281283@gmail.com">Minh Hoang TO</a>
 * @date
 */

public class DefaultMapper extends AbstractMapper
{
   private static DefaultMapper instance;
   
   private DefaultMapper()
   {
      
   }
   
   public static DefaultMapper getInstance()
   {
      if(instance == null)
      {
         instance = new DefaultMapper();
      }
      return instance;
   }
   
//   @Override
//   public String serializeBean(SerializableBean bean)
//   {
//      StringBuffer buffer = new StringBuffer();
//      
//      Class clazz = bean.getClass();
//      
//      Field[] allFields = clazz.getDeclaredFields();
//      if(allFields.length > 0) {
//         buffer.append("<" + bean.getClass().getCanonicalName() + ">");
//
//         for(Field field : allFields)
//         {         
//            FieldMapping fieldMappingAnnotation = field.getAnnotation(FieldMapping.class);
//            if(fieldMappingAnnotation != null)
//            {
//               String propertyName = fieldMappingAnnotation.propertyName();
//               
//               String getterMethod = fieldMappingAnnotation.getterMethod();
//               String propertyValue = invokeMethod(getterMethod, clazz, bean);
//               
//               buffer.append("<" + propertyName + ">" + propertyValue + "</" + propertyName + ">");
//            }
//         }
//         buffer.append("</" + bean.getClass().getCanonicalName() + ">");
//      }
//      return buffer.toString();
//   }
   
   @Override
   public JSONObject serializeBean(SerializableBean bean)
   {
      JSONObject o = new JSONObject();
      
      Class clazz = bean.getClass();
      
      Field[] allFields = clazz.getDeclaredFields();
      if(allFields.length > 0) {
         try {
            
            for(Field field : allFields)
            {         
               FieldMapping fieldMappingAnnotation = field.getAnnotation(FieldMapping.class);
               if(fieldMappingAnnotation != null)
               {

                  String propertyName = fieldMappingAnnotation.propertyName();
                  
                  String getterMethod = fieldMappingAnnotation.getterMethod();
                  String propertyValue = invokeMethod(getterMethod, clazz, bean);
                  
                  o.put(propertyName, propertyValue);                  
               }
            }
         } catch(Exception e) {
            System.out.println("error: " + e.getMessage());
         }
      }
      return o;
   }
   
   private String invokeMethod(String getterMethod, Class clazz, SerializableBean bean)
   {
      try
      {
         Method method = clazz.getMethod(getterMethod);
         Object result = method.invoke(bean);
         if (result != null)
         {
            return result.toString();
         }
         else
         {
            return "";
         }
      }
      catch (NoSuchMethodException noMethodEx)
      {
         return "Verify the method " + getterMethod;
      }
      catch (InvocationTargetException invocationEx)
      {
         return "Invocation exception " + getterMethod;
      }
      catch (IllegalAccessException illegalAccessEx)
      {
         return "Illegal access exception " + getterMethod;
      }
   }
   
   @Override
   public SerializableBean deserialize(JSONObject source)
   {
      return null;
   }
   
}
