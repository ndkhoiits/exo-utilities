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
eXo = {
		gadget : {}
};

function GateInMonitoring() {
	this.SERVICES_URL = "http://localhost:8080/portal/rest/management";	
}

GateInMonitoring.prototype.init = function() {
	registerHandler();
	var monitor = eXo.gadget.GateInMonitoring; 
  makeRequest(monitor.SERVICES_URL, monitor.renderServiceSelector);
};

GateInMonitoring.prototype.renderServiceSelector = function(services) {
	if (!services || !services.value) return;
	
	var serviceNames = services.value;
	var optionsHtml = "";
	for (var i = 0; i < serviceNames.length; i++) {
		optionsHtml += "<option>" + serviceNames[i] + "</option>";
	}
	var servicesSelector = $("#servicesSelector"); 
	servicesSelector.html(optionsHtml);
	servicesSelector.change();
};

GateInMonitoring.prototype.renderMethodSelector = function(methodData) {
	if (!methodData || !methodData.methods) return;
	
	var methodSelector = $(".home #methodsSelector");
	
	var methods = methodData.methods;
	var optionsHtml = "";
	for (var i = 0; i < methods.length; i++) {
		optionsHtml += "<option>" + methods[i].name + "</option>";
	}
	methodSelector.html(optionsHtml);			
	methodSelector.data('methods', methods);
	methodSelector.change();
};

GateInMonitoring.prototype.renderMethodDetail = function(method) {
	if (!method) return;
	
	$("#methodName").html(method.name);
	$("#methodDescription").html(method.description);
	$("#reqMethod").html(method.method);
	
	var paramTable = "";
	for (var i = 0; i < method.parameters.length; i++) {
		var rowClass = i % 2 == 0 ? "EvenRow" : "OddRow";
		paramTable += "<tr class='" + rowClass + "'><td>" + method.parameters[i].name + "</td></tr>";
	}
	
	if (paramTable == "") {
		paramTable = "<tr><td>[]</td></tr>";
	}
	$("#parametersTable").html(paramTable);
	gadgets.window.adjustHeight();
};

GateInMonitoring.prototype.renderMethodsForCanvas = function(methodData) {
	var methods = methodData.methods;
	var methodForCanvas = "";
	for (var i = 0; i < methods.length; i++) {
		var method = methods[i];						
		
		methodForCanvas += "<tr><td class='methodName'>" + method.name + "</td>" +	"<td class='reqMethod'>" + method.method + "</td>" +
												"<td><form>" + "<table style='width: 100%;'>";
		for (var j = 0; j < method.parameters.length; j++) {
			var rowClass = j % 2 == 0 ? "EvenRow" : "OddRow";
			methodForCanvas += "<tr class='" + rowClass + "'><td>" + method.parameters[j].name + "</td>" +
													"<td><input type='text' name='" + method.parameters[j].name + "'></td>" +
													"</tr>";
		}
		methodForCanvas += "</table></form>" +
												"</td>" +
												"<td><button class='ExecuteIcon' title='Execute this method'/></td></tr>";			
	}												
	$("#methodsForCanvas").html(methodForCanvas);
	gadgets.window.adjustHeight();
};

GateInMonitoring.prototype.showMinimessage = function(jsonMessage) {
//var message;
//
////BUG BUG: I don't know some results of server, such as below samples, how to fix?
//if(jsonMessage == '{}' || jsonMessage == '{"value":[]}' || jsonMessage == "") {
//  message = jsonMessage;
//} else {
//  var message = renderTable('msgTable', null, jsonMessage, 'value');
//  if(message == undefined) {
//    message = "Execute method successfully!";
//  }
//}

	var msg = new gadgets.MiniMessage("message");
	var executeMsg = msg.createDismissibleMessage(gadgets.json.stringify(jsonMessage));
	executeMsg.style.height = "220px";
	executeMsg.style.overflow = "auto";
	gadgets.window.adjustHeight();
};

/**
 * @param reqUrl - String
 * @param callback - Function
 * @param sendData - Data that will be send to server 
 * @param returnType - String html/xml/json/script
 * @param reqMethod - GET/POST/PUT...
 * @return XMLHttpRequest object
 */
function makeRequest(reqUrl, callback, sendData, returnType, reqMethod) {	
	reqMethod = reqMethod ? reqMethod : "GET";
	returnType = returnType ? returnType : "json";
	
	return $.ajax({
					  url: reqUrl,
					  type: reqMethod,					  
					  success: callback,
					  error: function() {alert("error");},
					  data: sendData,
					  dataType: returnType		  					  					  
					});
}

eXo.gadget.GateInMonitoring = new GateInMonitoring();
