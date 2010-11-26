<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" import="java.util.ArrayList"%>

<head>
<link rel="stylesheet" type="text/css"
   href="/skin/DefaultStyleSheet.css">
</head>
<portlet:defineObjects />
<div>
<form action='<portlet:actionURL name="addConsumer" />' method="post">
<div class="AddConsumer">
<table align="center">
   <tr>
      <td>Service Name*</td>
      <td><input type="text" name="service_name" value='<c:out value="${lastValue.service_name}"></c:out>' /></td>
      <td class="errMsg"><c:out value="${requestScope.errorMsg.service_name}" /></td>
   </tr>
   <tr>
      <td>GadgetURI*</td>
      <td><input type="text" name="gadget_uri" value='<c:out value="${lastValue.gadget_uri}"></c:out>' /></td>
      <td class="errMsg"><c:out value="${requestScope.errorMsg.gadget_uri}" /></td>
   </tr>
   <tr>
      <td>Consumer key*</td>
      <td><input type="text" name="consumer_key" value='<c:out value="${lastValue.consumer_key}"></c:out>' /></td>
      <td class="errMsg"><c:out value="${requestScope.errorMsg.consumer_key}" /></td>
   </tr>
   <tr>
      <td>Consumer secret*</td>
      <td><input type="text" name="consumer_secret" value='<c:out value="${lastValue.consumer_secret}"></c:out>' /></td>
      <td class="errMsg"><c:out value="${requestScope.errorMsg.consumer_secret}" /></td>
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
<br />
<c:if test="${not empty consumers}">
   <table border="1">
      <tr bgcolor="#99CCFF">
         <td valign="top"><b>Service Name</b></td>
         <td valign="top"><b>Gadget URI</b></td>
         <td valign="top"><b>Consumer Key</b></td>
         <td valign="top"><b>Consumer Secret</b></td>
      </tr>
      <c:forEach var="consumer" items="${consumers}">
         <tr>
            <td><c:out value="${consumer.serviceName}"></c:out></td>
            <td><c:out value="${consumer.gadgetURI}"></c:out></td>
            <td><c:out value="${consumer.consumerKey}"></c:out></td>
            <td><c:out value="${consumer.consumerSecret}"></c:out></td>
         </tr>
      </c:forEach>
   </table>
</c:if>

<c:out value="${name}"></c:out>
</div>