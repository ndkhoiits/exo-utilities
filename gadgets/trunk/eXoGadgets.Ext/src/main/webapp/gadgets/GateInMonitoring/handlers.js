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
 $(document).ready( function() { 
	
	//======================Handler======================================//
	$(".home #servicesSelector").change(function () {
	  var service = $("option:selected", this).val();
	  
	  //Get, render and store methods
	  var methods = getMethods(service);
	  $.jStorage.set('methods', methods);
	  renderMethodsForHome(methods);
	  
	  gadgets.window.adjustHeight();
	});

	$(".home #methodsSelector").change(function () {
	  var method = $("option:selected", this).val();

	  var jsonData = $.jStorage.get('methods');
	  var jsonMethod = find(jsonData, 'methods', 'name', method);
	  renderObject('informationTable', jsonMethod, ['name', 'description' ,'method' , 'parameters']);

	  gadgets.window.adjustHeight();
	});

	$(".canvas #servicesSelector").change(function () {	  
	  //Get and render current service
	  var service = $("option:selected", this).val();
	  renderMethodsForCanvas(service);
	  
	  gadgets.window.adjustHeight();
	});

	$('button.ExecuteIcon').live('click', function() {
	  var service = $('#servicesSelector').val();  
	  
	  $method = $(this).parent().parent().children().get(0).innerHTML;
	  $httpMethod = $(this).parent().parent().children().get(1).innerHTML;
	  $parametersTable = $(this).parent().parent().children().get(2).innerHTML;
	  
	  var parameters = null;	  
	  if($parametersTable != undefined) {    
	    parameters = getParameters($parametersTable);
	  }
	  
	  //Hard code for testing reloadTemplate method, because of getting parameters value incorrectly now
	  if(service == "templateservice" && $method == "reloadTemplate") {
	    parameters = "templateId=par:/groovy/groovy/webui/component/UIFooterPortlet.gtmpl";
	  }

	  responseText = executeMethod(service, $method, parameters, $httpMethod)
	  showMinimessage(responseText);
	});

});