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
GateInMonitoring.prototype.registerHandler = function() {
	
	//======================Handler======================================//
	$("#servicesSelector").change(function () {
	  var serviceName = gadgets.util.unescapeString($(this).val());
	  var methodsURL = eXo.gadget.GateInMonitoring.SERVICES_URL + "/" + encodeURIComponent(serviceName);
	  
	  var currView = gadgets.views.getCurrentView().getName();
	  if (currView == "home") {
	  	eXo.gadget.GateInMonitoring.makeRequest(methodsURL, eXo.gadget.GateInMonitoring.renderMethodSelector);
	  } else {
	  	eXo.gadget.GateInMonitoring.makeRequest(methodsURL, eXo.gadget.GateInMonitoring.renderMethodsForCanvas);
	  }
	});

	$("#methodsSelector").change(function () {
	  var methodName = gadgets.util.unescapeString($(this).val());

	  var methodData = $(this).data('methods');
	  var method = null;
	  for (var i = 0; i < methodData.length; i++) {
	  	if (methodData[i].name == methodName) {
	  		method = methodData[i];
	  	}
	  }

	  eXo.gadget.GateInMonitoring.renderMethodDetail(method);
	});
	
	$('button.ExecuteIcon').live('click', function() {
		var tr = $(this.parentNode.parentNode);
		var methodName = gadgets.util.unescapeString($(".methodName", tr).text().trim());
	  var reqMethod = gadgets.util.unescapeString($(".reqMethod", tr).text().trim());
	  var serviceName = gadgets.util.unescapeString($("#servicesSelector").val());
	  var param = $("form", tr).serialize();
	  
		var execLink = eXo.gadget.GateInMonitoring.SERVICES_URL + "/" + 
												encodeURIComponent(serviceName) + "/" + 
												encodeURIComponent(methodName);
		eXo.gadget.GateInMonitoring.makeRequest(execLink, eXo.gadget.GateInMonitoring.showMinimessage, param, "text", reqMethod);
	});
};

/**
 * @param reqUrl - String
 * @param callback - Function
 * @param sendData - Data that will be send to server 
 * @param returnType - String html/xml/json/script
 * @param reqMethod - GET/POST/PUT...
 * @return XMLHttpRequest object
 */
GateInMonitoring.prototype.makeRequest = function(reqUrl, callback, sendData, returnType, reqMethod) {	
	reqMethod = reqMethod ? reqMethod : "GET";
	returnType = returnType ? returnType : "json";
	
	return $.ajax({
					  url: reqUrl,
					  type: reqMethod,					  
					  success: callback,
					  contentType: "application/x-www-form-urlencoded",
					  error: function() {alert("error");},
					  data: sendData,
					  dataType: returnType		  					  					  
					});
};