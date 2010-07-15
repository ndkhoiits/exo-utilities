/*
 * Copyright (C) 2003-2010 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.webservice.rest.datamodel;

import org.exoplatform.webservice.rest.annotation.FieldMapping;
import org.exoplatform.webservice.rest.api.SerializableBean;
import org.exoplatform.services.organization.User;

/**
 * Created by The eXo Platform SAS
 * Author : Kien Nguyen
 *          nguyenanhkien2a@gmail.com
 * Jul 13, 2010  
 */
public class UserBean implements SerializableBean
{   
   @FieldMapping(fieldName = "userName", getterMethod = "getUserName", propertyName = "userName")
   private String userName;
   
   @FieldMapping(fieldName = "firstName", getterMethod = "getFirstName", propertyName = "firstName")
   private String firstName;
   
   @FieldMapping(fieldName = "lastName", getterMethod = "getLastName", propertyName = "lastName")
   private String lastName;
   
   @FieldMapping(fieldName = "email", getterMethod = "getEmail", propertyName = "email")
   private String email;
   
   public UserBean(String _userName, String _firstName, String _lastName, String _email)
   {
      this.userName = _userName;
      this.firstName = _firstName;
      this.lastName = _lastName;
      this.email = _email;
   }
   
   public String getUserName()
   {
      return this.userName;
   }
   
   public String getFirstName()
   {
      return this.firstName;
   }
   
   public String getLastName()
   {
      return this.lastName;
   }
   
   public String getEmail()
   {
      return this.email;
   }
   
   public static UserBean build(User userData)
   {      
      return new UserBean(userData.getUserName(), userData.getFirstName(), userData.getLastName(), userData.getEmail());
   }
}
