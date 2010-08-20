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
	var monitor = eXo.gadget.GateInMonitoring; 
	monitor.registerHandler();
  monitor.makeRequest(monitor.SERVICES_URL, monitor.renderServiceSelector);
};

GateInMonitoring.prototype.renderServiceSelector = function(services) {
	var servicesSelector = $("#servicesSelector");
	var optionsHtml = "";
	
	if (services && services.value) {
		var serviceNames = services.value;
		
		for (var i = 0; i < serviceNames.length; i++) {
			optionsHtml += "<option>" + gadgets.util.escapeString(serviceNames[i]) + "</option>";
		}
	}	
	 
	servicesSelector.html(optionsHtml);
	servicesSelector.change();
};

GateInMonitoring.prototype.renderMethodSelector = function(methodData) {
	var methodSelector = $("#methodsSelector");
	var optionsHtml = "";
	var methods = null;
	
	if (methodData && methodData.methods) {
		methods = methodData.methods;
		
		for (var i = 0; i < methods.length; i++) {
			optionsHtml += "<option>" + gadgets.util.escapeString(methods[i].name) + "</option>";
		}		
	}	
	
	methodSelector.html(optionsHtml);
	methodSelector.data('methods', methods);
	methodSelector.change();
};

GateInMonitoring.prototype.renderMethodDetail = function(method) {
	if (!method) {
		method = {name:"", description:"", method:"", parameters:[]};
	}
	var util = gadgets.util;
	
	$("#methodName").html(util.escapeString(method.name));
	$("#methodDescription").html(util.escapeString(method.description));
	$("#reqMethod").html(util.escapeString(method.method));
	
	var paramTable = "<table>";
	for (var i = 0; i < method.parameters.length; i++) {
		paramTable += "<tr><td>" + util.escapeString(method.parameters[i].name) + "</td></tr>";
	}
	
	if (paramTable == "<table>") {
		paramTable += "<tr><td>[]</td></tr>";
	}
	paramTable += "</table>";
	$("#parametersTable").html(paramTable);
	gadgets.window.adjustHeight();
};

GateInMonitoring.prototype.renderMethodsForCanvas = function(methodData) {
	var methods = methodData.methods;
	var methodForCanvas = "";
	var util = gadgets.util;
	
	for (var i = 0; i < methods.length; i++) {
		var method = methods[i];						
		var methodName = util.escapeString(method.name);
		var reqMethod = util.escapeString(method.method);
		
		var rowClass = i % 2 == 0 ? "EvenRow" : "OddRow";
		methodForCanvas += "<tr " + rowClass + ">" +
													"<td class='methodName'>" + methodName + "</td>" +	
													"<td class='reqMethod'>" + reqMethod + "</td>" +
												  "<td><form>";
		for (var j = 0; j < method.parameters.length; j++) {
			methodForCanvas +=  "<div class='SkinID'>" + util.escapeString(method.parameters[j].name) + " " +
													"<input type='text' name='" + util.escapeString(method.parameters[j].name) + "'>" +
													"</div>";
		}
		methodForCanvas += "</form></td>" +
												"<td>"+
													"<div class='UIAction'> " +
														"<table class='ActionContainer'>" +
															"<tbody>" +
																"<tr>" +
																	"<td>" +
																		"<div class='ActionButton GadgetStyle'>" +
																			"<div class='ButtonLeft'>" +
																				"<div class='ButtonRight'>" +
																					"<div class='ButtonMiddle'>" +
																						"<div class='Icon SmallGroup16x16Icon'>" +
																							"Execute" +
																						"</div>" +
																					"</div>" +
																				"</div>" +
																			"</div>" +
																		"</div>" +
																	"</td>" +
																"</tr>" +
															"</tbody>" +
														"</table>" +
													"</div>" +
												"</td></tr>";	
	}												
	$("#methodsForCanvas").html(methodForCanvas);
	gadgets.window.adjustHeight();
};

GateInMonitoring.prototype.showMinimessage = function(jsonMessage) {
	var parsedObj;
	try {
		parsedObj = gadgets.json.parse(jsonMessage);
	} catch(e) {
		parsedObj = jsonMessage;
	}
	var htmlTable = $.trim(eXo.gadget.GateInMonitoring.objToTable(parsedObj));
	if (htmlTable == "" || htmlTable == "empty object") {
		htmlTable = "Method's executed, return no result";
	}
	
	var msg = new gadgets.MiniMessage("GateInMonitoring", document.getElementById("resultMessage"));
	var executeMsg = msg.createDismissibleMessage(htmlTable);
	executeMsg.style.height = "100px";
	executeMsg.style.overflow = "auto";	
	gadgets.window.adjustHeight();
};

GateInMonitoring.prototype.objToTable = function(obj) {	
	var type = typeof(obj);	
	if (type != "object") {
		return gadgets.util.escapeString(obj + "");
	}
	
	if (!obj || $.isEmptyObject(obj) || 
			(obj.constructor == Array && obj.length == 0)) {
		return "empty object";
	}
	
	var str = "<table>";
	if (obj.constructor == Array) {	
		for (var i = 0; i < obj.length; i++) {
			str += "<tr><td>";
			str += eXo.gadget.GateInMonitoring.objToTable(obj[i]);
			str += "</td></tr>";
		}		
	} else {
		str += "<tr>";
		for(var prop in obj) {
			str += "<td>";
			str += eXo.gadget.GateInMonitoring.objToTable(prop);
			str += "</td>";
		}
		str += "</tr>";
		
		str += "<tr>";
		for (var prop in obj) {
			str += "<td>";
			str += eXo.gadget.GateInMonitoring.objToTable(obj[prop]);
			str += "</td>";
		}
		str += "</tr>";
	}
	
	str += "</table>";
	return str;
};

eXo.gadget.GateInMonitoring = new GateInMonitoring();
