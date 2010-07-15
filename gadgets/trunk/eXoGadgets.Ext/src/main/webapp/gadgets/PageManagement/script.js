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

function getAllPages() {
  var url = "http://localhost:8080/portal/rest/dataManager/query/pages/*/*";
  var request =  new XMLHttpRequest() ;
  request.open('GET', url, false) ;
  request.setRequestHeader("Cache-Control", "max-age=86400") ;
  request.send(null) ;
  responseText = request.responseText ;

    renderPagesList(responseText);
}

function deletePage(pageId) {
  if(confirm('Do you want to delete this page?')) {
    var url = "http://localhost:8080/portal/rest/dataManager/delete/pages?objectId=" + encodeURIComponent(pageId);        
              
    var request =  new XMLHttpRequest() ;
    request.open('GET', url, false) ;
    request.setRequestHeader("Cache-Control", "max-age=86400") ;
    request.send(null) ;
    
    if("success" == request.responseText)
      getAllPages();
  }     
}

function searchPage() {
  var filterString = document.getElementById("SearchInput").value;
  var searchTypeObject = document.getElementById("SearchType");
  var searchType = searchTypeObject[searchTypeObject.selectedIndex].value;
  
  var url = "http://localhost:8080/portal/rest/dataManager/query/pages/";
  if(filterString != "") {
    if(searchType == "Owner id") {
      url += "*/" + filterString;
    } else if(searchType == "Owner type") {
      url += filterString + "/*";
    } else {
      url += "*/*";
    }
  } else {
    url += "*/*";
  }
  
  var request =  new XMLHttpRequest() ;
  request.open('GET', url, false) ;
  request.setRequestHeader("Cache-Control", "max-age=86400") ;
  request.send(null) ;
  responseText = request.responseText ;       

  renderPagesList(responseText);
}