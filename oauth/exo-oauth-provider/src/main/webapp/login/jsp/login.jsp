<%--

    Copyright (C) 2009 eXo Platform SAS.
    
    This is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation; either version 2.1 of
    the License, or (at your option) any later version.
    
    This software is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    Lesser General Public License for more details.
    
    You should have received a copy of the GNU Lesser General Public
    License along with this software; if not, write to the Free
    Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
    02110-1301 USA, or see the FSF site: http://www.fsf.org.

--%>
<%@ page language="java" %>
<%
   String contextPath = request.getContextPath();
   String username = request.getParameter("username");
   String password = request.getParameter("password");
   if(username == null)
      username = "";
   if(password == null) 
      password = "";
%>
<!DOCTYPE html 
    PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
           "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>OAuth Login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>   
    <link rel="shortcut icon" type="image/x-icon"  href="<%=contextPath%>/favicon.ico" />
    <link rel='stylesheet' type='text/css' href='<%=contextPath%>/login/skin/Stylesheet.css'/>
    <script type="text/javascript" src="/eXoResources/javascript/eXo.js"></script>
    <script type="text/javascript" src="/eXoResources/javascript/eXo/portal/UIPortalControl.js"></script>
  </head>
  <body style="text-align: center; background: #b5b6b6; font-family: arial, tahoma, verdana">
    <div class="UILogin">
      <div class="LoginHeader"></div>
      <div class="LoginContent">
        <div class="WelcomeText">OAuth Login</div>
        <div class="CenterLoginContent">
          <%/*Begin form*/%>
          <%
          if(username.length() > 0 || password.length() > 0) {
          %>
          <font color="red">Sign in failed. Wrong username or password.</font><%}%>
          <form name="loginForm" action="<%= contextPath + "/authorize"%>" method="post" style="margin: 0px;">
                <input type="hidden" name="oauth_token" value="${param.oauth_token}" />             
                <input type="hidden" name="oauth_callback" value="${param.oauth_callback}" />                      
                <table> 
                  <tr class="FieldContainer">
                      <td class="FieldLabel">UserName</td>
                      <td><input class="UserName" name="username" value="<%=username%>"/></td>
                      </tr>
                    <tr class="FieldContainer" id="UIPortalLoginFormControl" onkeypress="eXo.portal.UIPortalControl.onEnterPress(event);">
                      <td class="FieldLabel">Password</td>
                      <td><input class="Password" type="password" name="password" value=""/></td>
                    </tr>                    
                  </table>
                  <div id="UIPortalLoginFormAction" class="LoginButton" onclick="login();">
                    <table class="LoginButtonContainer">
                        <tr>
                          <td class="Button">
                            <div class="LeftButton">
                              <div class="RightButton">
                                <div class="MiddleButton">
                                    <a href="#">Signin</a>
                                </div>
                              </div>
                            </div>
                          </td>
                         </tr>
                    </table>
                  </div>
                  <div class="ClearLeft"><span></span></div>
                  <script type='text/javascript'>                       
              function login() {
                document.loginForm.submit();                   
              }
            </script>
                </form>
                <%/*End form*/%>
        </div>
      </div>
    </div>
    <span style="font-size: 11px; color: #3f3f3f; text-align: center">Copyright &copy 2010. All rights reserved, Red Hat, Inc and eXo Platform SAS</span>
  </body>
</html>