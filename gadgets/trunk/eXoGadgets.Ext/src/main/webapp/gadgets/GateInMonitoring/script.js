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
/**
 * init screen for home view
 */
function initHomeView() {
  var allServices = getServices();
  renderServicesForHome(allServices);
  gadgets.window.adjustHeight();
}
/**
 * init screen for canvas view
 */
function initCanvasView() {
  var allServices = getServices();
  renderServicesForCanvas(allServices);
  gadgets.window.adjustHeight();
}
/**
 * Get all services that registered
 * @return
 */
function getServices() {
  var url = buildUrl();
  responseText = this.getRequest(url);
  return responseText;
}
/**
 * Get all methods of a specific service
 * @param serviceName
 * @return
 */
function getMethods(serviceName) {
  var paths = [serviceName];
  var url = buildUrl(paths);
  
  responseText = httpRequest(url);
  
  return responseText;
}

function renderServicesForCanvas(jsonData) {
  renderSelector('servicesSelector', jsonData);
  
  var service = $('#servicesSelector').val();
  renderMethodsForCanvas(service);
  
  gadgets.window.adjustHeight();
}

function renderServicesForHome(jsonData) {
  renderSelector('servicesSelector', jsonData);
  
  //Get, render current service
  var service = $('#servicesSelector').val();
  var methods = getMethods(service);
  renderMethodsForHome(methods);
  $.jStorage.set('methods', methods);
  
  gadgets.window.adjustHeight();
}

function renderMethodsForHome(jsonData) {
  renderSelector('methodsSelector', jsonData, 'methods', 'name'); 
  
  //Get current selector
  var method = $('#methodsSelector').val();  
  var jsonData = $.jStorage.get('methods');
  
  var jsonMethod = find(jsonData, 'methods', 'name', method);
  renderObject('informationTable', jsonMethod, ['name', 'description' ,'method' , 'parameters']);
}

function renderMethodsForCanvas(serviceName) {
  //Get and render current service
  var methods = this.getMethods(serviceName);
  //var methods = '{"methods":[{"description":"Clear the template cache for a specified template identifier","name":"reloadTemplate","method":"put","parameters":[{"description":"The template id","name":"templateId"}, {"description":"The template id","name":"templateId1111"}]},{"description":"Clear the template cache","name":"reloadTemplates","method":"post","parameters":[]},{"description":"List the identifiers of the cached templates","name":"listCachedTemplates","method":"get","parameters":[]}],"description":"Template management service","name":"templateservice","properties":[]}';
  renderTableWithActions('informationTable', ['Name', 'Method', 'Parameters', 'Action'], methods, 'methods', ['name', 'method', 'parameters']);
}
/**
 * 
 * @param jsonData array of Object, ex: [{"description":"The template id","name":"templateId"}]
 * @return
 */
function renderParameters(jsonData) {
  return renderParamsTableWithTitle('parametersTable', ['name', 'description'], jsonData, ['name', 'description']);
}

/**
 * Render select box from json data
 * @param selectorId
 * @param jsonData array of object
 * @param key default is 'value' (current is often value or methods)
 * @param property indicate property name to display in selector box
 * @return
 */
function renderSelector(selectorId, jsonData, key, property) {  
  var selector = document.getElementById(selectorId);
  if(selector == null || selector == "undefined") {
    return;            
  }
    
  var jsonObject = eval("(" + jsonData + ")");
  if(jsonObject == undefined)
    return;
    
  removeAllChildren(selectorId);
  
  if(key == null || key == undefined)
    key = 'value';
  
  var count = jsonObject[key].length
  for ( var i = 0; i < count ; i++ ) {            
    var option = document.createElement("option");
    option.setAttribute("style", "width:100%");
    
    var value;
    if(property == null || property == undefined) {
      value =  jsonObject[key][i];           
    } else {
      value =  (jsonObject[key])[i][property];  
    }
    
    option.setAttribute("value", value);
    option.innerHTML = value;

    selector.appendChild(option);  
  }
}
/**
 * Render a parameters table with title
 * @param tableId
 * @param titles array of string to indicate title of table
 * @param jsonObject array of data
 * @param properties array of String to indicate keys for gathering values
 * @param isInput default is 0, not has input field
 * @return
 */
function renderParamsTableWithTitle(tableId, titles, jsonObject, properties, isInput) { 
  if(jsonObject == undefined || jsonObject.length <= 0)
    return;

  var kTable = document.getElementById(tableId);
  
  if(kTable != null && kTable != undefined) {
    removeAllChildren(tableId);           
  } else {
    kTable = document.createElement("table");
    kTable.setAttribute("id", tableId);
    kTable.setAttribute("style", "width:100%");
  }
  
  if(titles != null && titles != undefined) {
    kTable.appendChild(createTableTitles(titles));
  }
  
  var count = jsonObject.length
  for ( var i = 0; i < count ; i++ ) {    
    var kRow = document.createElement("tr");
    kRow.setAttribute("style", "width:100%");
    kRow.setAttribute("class", i%2 ? "OddRow":"EvenRow");  
    
    if(properties != null && properties != undefined) {
      for(p in properties) {
        var kCell = document.createElement("td");  
        if(jsonObject[i][properties[p]] == undefined)
          kCell.innerHTML = "<p>none</p>";
        else
          kCell.innerHTML = "<p>" + jsonObject[i][properties[p]] + "</p";
                
        kRow.appendChild(kCell);   
      }
    }
    
    if(isInput != undefined && isInput == 1) {
	  var kCell = document.createElement("td");
      var kInput = document.createElement("input");
	  kInput.setAttribute("type", "text");
	  kInput.setAttribute("title", "Type your parameter");
	  kInput.setAttribute("value", "");
	   
	  kCell.appendChild(kInput);
      kRow.appendChild(kCell); 
    }
    
    kTable.appendChild(kRow);        
  }
  
  return kTable;
}

/**
 * Create parameter table, columns meet in properties and has extra input column
 * @param tableId
 * @param jsonData array of data
 * @param key
 * @param properties array of String to indicate keys for gathering values
 * @return
 */
function renderParamsTable(tableId, jsonObject, properties, isInput) { 
  if(jsonObject == undefined || jsonObject.length <= 0)
    return;
  
  var kTable = document.getElementById(tableId);
  
  if(kTable != null && kTable != undefined) {
    removeAllChildren(tableId);           
  } else {
    kTable = document.createElement("table");
    kTable.setAttribute("id", tableId);
    kTable.setAttribute("style", "width:100%");
  }

  var count = jsonObject.length
  for ( var i = 0; i < count ; i++ ) {    
    var kRow = document.createElement("tr");
    kRow.setAttribute("style", "width:100%");
    kRow.setAttribute("class", i%2 ? "OddRow":"EvenRow");  
    
    if(properties != null && properties != undefined) {
      for(p in properties) {
        var kCell = document.createElement("td");  
        if(jsonObject[i][properties[p]] == undefined)
          kCell.innerHTML = "<p>none</p>";
        else
          kCell.innerHTML = "<p>" + jsonObject[i][properties[p]] + "</p";
                
        kRow.appendChild(kCell);   
      }
    }
    
    if(isInput != undefined && isInput == 1) {
      var kCell = document.createElement("td");
      var kInput = document.createElement("input");
	  kInput.setAttribute("type", "text");
	  kInput.setAttribute("title", "Type your parameter");
	  kInput.setAttribute("value", "");
		  
	  kCell.appendChild(kInput);
      kRow.appendChild(kCell); 
    }
    
    kTable.appendChild(kRow);        
  }
  
  return kTable;
}

/**
 * Create a table with action for every row
 * @param tableId
 * @param jsonData array of data
 * @param key
 * @param properties array of String, atleast must have two properties: 0 is name, 1 is http method
 * @return
 */
function renderTableWithActions(tableId, titles, jsonData, key, properties) {
  var jsonObject = eval("(" + jsonData + ")");
  if(jsonObject == undefined || jsonObject[key].length <= 0 || properties == undefined)
    return;
  
  var kTable = document.getElementById(tableId);
  
  if(kTable != null && kTable != undefined) {
    removeAllChildren(tableId);           
  } else {
    kTable = document.createElement("table");
    kTable.setAttribute("id", tableId);
    kTable.setAttribute("style", "width:100%");
  }
  
  if(titles != null && titles != undefined) {
    kTable.appendChild(createTableTitles(titles));
  }

  kBody = document.createElement("tbody"); 
  kBody.setAttribute("id", "kBody");
  kTable.appendChild(kBody);
    
  var count = jsonObject[key].length;
  for ( var i = 0; i < count ; i++ ) {    
    var paramsTableId = null;
    var kRow = document.createElement("tr");
    kRow.setAttribute("style", "width:100%");
    kRow.setAttribute("class", i%2 ? "OddRow":"EvenRow");     
    
    var kCell;
    for(p in properties) {
      kCell = document.createElement("td");  
      if((jsonObject[key])[i][properties[p]] == undefined)
        kCell.innerHTML = "none";
      else {
        if(properties[p] == "parameters") {//render parameters in a cell
          var methodName = (jsonObject[key])[i][properties[0]];
          var jsonMethod = find(jsonData, 'methods', 'name', methodName);
          var params = renderParamsTable('parametersTable ' + methodName, jsonMethod['parameters'], ['name'], 1);
          if(params != null) {
            kCell.appendChild(params);
			kCell.setAttribute("style", "height: auto;");
            paramsTableId = 'parametersTable ' + methodName;
          }
        } else {
          kCell.innerHTML = (jsonObject[key])[i][properties[p]];
          kCell.setAttribute("id", properties[p] + i);
        }
      }
             
      kRow.appendChild(kCell);  
    }
  
    //Insert button
//    kCell = document.createElement("td");  
//    kCell.innerHTML =  "<img title='Execute' class='ExecuteIcon' src='/eXoGadgets.Ext/skin/image/Blank.gif' />";  
//    kCell.setAttribute("style", "width:50%");            
//    kRow.appendChild(kCell);
    
    kCell = document.createElement("td");  
    kCell.innerHTML =  "<button title='Execute this method' class='ExecuteIcon' value='Execute'/>";        
    kRow.appendChild(kCell);
    
    kBody.appendChild(kRow);        
  }
  
  return kTable;
}

/**
 * Create a html table
 * @param tableId
 * @param jsonData array of data
 * @param key
 * @param properties array of String, atleast must have two properties: 0 is name, 1 is http method
 * @return
 */
function renderTable(tableId, titles, jsonData, key, properties) {
  var jsonObject = eval("(" + jsonData + ")");
  if(jsonObject == undefined || jsonObject[key].length <= 0)
    return;
  
  var kTable = document.getElementById(tableId);
  
  if(kTable != null && kTable != undefined) {
    removeAllChildren(tableId);           
  } else {
    kTable = document.createElement("table");
    kTable.setAttribute("id", tableId);
    kTable.setAttribute("style", "width:100%");
  }
  
  if(titles != null && titles != undefined) {
    kTable.appendChild(createTableTitles(titles));
  }
    
  var count = jsonObject[key].length;
  for ( var i = 0; i < count ; i++ ) {    
    var paramsTableId = null;
    var kRow = document.createElement("tr");
    kRow.setAttribute("style", "width:100%");
    kRow.setAttribute("class", i%2 ? "OddRow":"EvenRow");     
    
    var kCell;
	  if(properties != undefined) {
	    for(p in properties) {
	      kCell = document.createElement("td");  
	      if((jsonObject[key])[i][properties[p]] == undefined)
	        kCell.innerHTML = "none";
	      else {
	        if(properties[p] == "parameters") {//render parameters in a cell
	          var methodName = (jsonObject[key])[i][properties[0]];
	          var jsonMethod = find(jsonData, 'methods', 'name', methodName);
	          var params = renderParamsTable('parametersTable ' + methodName, jsonMethod['parameters'], ['name']);
	          if(params != null) {
	            kCell.appendChild(params);
				kCell.setAttribute("style", "height: auto;");
	            paramsTableId = 'parametersTable ' + methodName;
	          }
	        } else {
	          kCell.innerHTML = (jsonObject[key])[i][properties[p]];
	          kCell.setAttribute("id", properties[p] + i);
	        }
	      }
		  }
	} else {
		kCell = document.createElement("td");
		kCell.innerHTML = (jsonObject[key])[i];
	}
             
    kRow.appendChild(kCell);  
    
    kTable.appendChild(kRow);        
  }
  
  return kTable;
}

/**
 * Create title of table
 * @param titles array of string that display as a column of table
 * @return
 */
function createTableTitles(titles) {
  var kHead = document.createElement("thead");   
  kHead.setAttribute("style", "width:100%");
  
  var kRow = document.createElement("tr");   
  kRow.setAttribute("style", "width:100%");
  
  kHead.appendChild(kRow);
  
  var count = titles.length
  for ( var i = 0; i < count ; i++ ) {   
    var kCell = document.createElement("td");   
    kCell.setAttribute("style", "width:" + 100*i/count + "%");
    kCell.innerHTML = titles[i];
    kRow.appendChild(kCell);
  }
  
  return kHead;
}
/**
 * parse a object to table
 * @param objectId
 * @param jsonObject
 * @param properties
 * @return
 */
function renderObject(objectId, jsonObject, properties) {
  var kTable = document.getElementById(objectId);
  
  if(kTable != null && kTable != undefined) {
    removeAllChildren(objectId);           
  } else {
    kTable = document.createElement("table");
    kTable.setAttribute("id", "kTable");
    kTable.setAttribute("style", "width:100%");
  }
  
  kTable.appendChild(createTableTitles(['Name', 'Value']));
      
  kBody = document.createElement("tbody"); 
  kBody.setAttribute("id", "kBody");
  kTable.appendChild(kBody);
  
  if(jsonObject == undefined)
    return;

  if(properties != null && properties != undefined) {
    for(p in properties) {
      var kRow = document.createElement("tr");
      kRow.setAttribute("style", "width:100%");
      
      var kCell = document.createElement("td");  
      kCell.innerHTML = properties[p];
      kRow.appendChild(kCell);   
      
      kCell = document.createElement("td");  
      if(jsonObject[properties[p]] == undefined)
        kCell.innerHTML = "none";
      else {
        if(properties[p] == "parameters") {//render parameters in a cell
          var params = renderParamsTable('parametersTable ' + jsonObject[properties[0]], jsonObject['parameters'], ['name'], 0);
          if(params != null) {
            kCell.appendChild(params);
            kCell.setAttribute("style", "height: auto;");
          } else {
            kCell.innerHTML = "[]";
          }
        }
        else {
          kCell.innerHTML = jsonObject[properties[p]];
        }
      }
              
      kRow.appendChild(kCell);   
      kBody.appendChild(kRow);   
    }
  }
}

/**
 * find object from array, object can contains many properties
 * @param jsonData
 * @param key
 * @param filterName name of object
 * @param filterValue value to compare
 * @return
 */
function find(jsonData, key, filterName, filterValue) {
  var jsonObject = eval("(" + jsonData + ")");
  if(jsonObject == undefined)
    return;
    
  var count = jsonObject[key].length
  for ( var i = 0; i < count ; i++ ) {
    if((jsonObject[key])[i][filterName] == filterValue)
      return (jsonObject[key])[i];
  }
  
  return null;
}

/**
 * 
 * @param serviceName
 * @param methodName
 * @param params
 * @param httpMethod POST or GET or PUT
 * @return
 */
function executeMethod(serviceName, methodName, params, httpMethod) {
  var paths = [serviceName, methodName];
  var url = buildUrl(paths);
  return httpRequest(url, params, httpMethod);
}

//=================HTTP functions==========================//
/**
 * 
 * @param paths array of path to add on trailer of url
 * @return
 */
function buildUrl(paths) {
  var url = "http://localhost:8080/portal/rest/management";
  
  if(paths != undefined && paths.length > 0) {
    for(var i = 0; i < paths.length; i++) {
      url += "/" + paths[i];
    }
  }
  
  return url;
}
/**
 * Send http request, default is GET method
 * @param url
 * @param params
 * @param method
 * @return
 */
function httpRequest(url, params, method) {
  if(method == 'POST' || method == 'post') {
    return postRequest(url, params);
  }
  else if(method == 'PUT' || method == 'put') {
    return putRequest(url, params);
  }
  else {
    return getRequest(url, params);
  }
}

/**
 * send http POST request to server at url
 * @param url
 * @param parameters
 * @return
 */
function postRequest(url, params) {
  var request =  new XMLHttpRequest() ;
  request.open('POST', url, false) ;
  request.setRequestHeader("Cache-Control", "max-age=86400") ;
  request.send(params) ;
  return request.responseText ;
}

/**
 * send http GET request to server at url
 * @param url
 * @param params
 * @return
 */
function getRequest(url, params) {
  var request =  new XMLHttpRequest() ;
  var tempUrl = url;
  if(params != undefined && params.length > 0)
    tempUrl += '?' + params;
  
  request.open('GET', tempUrl, false) ;
  request.setRequestHeader("Cache-Control", "max-age=86400") ;
  request.send(null) ;
  return request.responseText ;
}
/**
 * send http PUT request to server at url
 * @param url
 * @param params
 * @return
 */
function putRequest(url, params) {
  var request =  new XMLHttpRequest() ;
  request.open('PUT', url, false) ;
  request.setRequestHeader("Cache-Control", "max-age=86400") ;
  request.send(params) ;
  return request.responseText ;
}

//=======================ultility functions============================//
function removeAllChildren(object) {
  $("#" + object).empty();
}

function executeMethodHandler(nameCellId, httpMethodCellId, paramsTableId) {
  var service = $('#servicesSelectorCanvas').val();
  var method = $('#' + nameCellId).val();
  var httpMethod = $('#' + httpMethodCellId).val();;
  var params = null;
  
  if(paramsTableId != undefined) {    
    var params = null;
  }
  
  responseText = executeMethod(service, method, params, httpMethod)
  showMinimessage(responseText);
}

/**
 * Get parameter from html parameter table
 * @param parametersTable
 * @return string
 */
function getParameters(parametersTable) {
	var parameters = "";
  $table = $(parametersTable);
	$table.find('tr').each(function() {
  	$row = $(this);
  	$name = $row.find('td p').get(0);
  	$input = $row.find('td input').get(0);		
  	if(parameters != "")
  	   parameters += "&";
  	parameters += $name.innerHTML + "=" + $input.value
	});
	
	return parameters;
}
/**
 * show mini message box
 * @param jsonMessage
 * @return
 */
function showMinimessage(jsonMessage) {
  var message;
  
  //BUG BUG: I don't know some results of server, such as below samples, how to fix?
  if(jsonMessage == '{}' || jsonMessage == '{"value":[]}' || jsonMessage == "") {
    message = jsonMessage;
  } else {
    var message = renderTable('msgTable', null, jsonMessage, 'value');
    if(message == undefined) {
      message = "Execute method successfully!";
    }
  }

  var msg = new gadgets.MiniMessage("message");
  var executeMsg = msg.createDismissibleMessage(message);
  executeMsg.style.height = "220px";
  executeMsg.style.overflow = "auto";
  gadgets.window.adjustHeight();
}
/**
 * Get current view of gadget
 * @return
 */
function getViewName() {
  return gadgets.views.getCurrentView().getName();
}
