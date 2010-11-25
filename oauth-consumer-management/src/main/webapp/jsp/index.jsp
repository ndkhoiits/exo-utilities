<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<link rel="stylesheet" type="text/css"
   href="/skin/DefaultStyleSheet.css">
</head>

<div>
<form action='<portlet:actionURL name="addConsumer" />' method="post">
<div class="AddConsumer">
<table align="center">
   <tr>
      <td>Service Name</td>
      <td><input type="text" name="service_name" /></td>
   </tr>
   <tr>
      <td>GadgetURI</td>
      <td><input type="text" name="gadget_uri" /></td>
   </tr>
   <tr>
      <td>Consumer key</td>
      <td><input type="text" name="consumer_key" /></td>
   </tr>
   <tr>
      <td>Consumer secret</td>
      <td><input type="text" name="consumer_secret" /></td>
   </tr>
   <tr>
      <td>Key Type</td>
      <td><select name="key_type">
         <option value="PLAINTEXT">PLAINTEXT</option>
         <option value="RSA_PRIVATE">RSA_PRIVATE</option>
         <option value="HMAC_SYMMETRIC">HMAC_SYMMETRIC</option>
      </select></td>
   </tr>
</table>
<input type="submit" value="Add consumer" class="Submit" /></div>
</form>
</div>