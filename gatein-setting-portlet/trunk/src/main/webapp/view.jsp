<%@page import="org.exoplatform.sysconfig.component.SystemConfigPortlet"%>
<%@page import="java.util.Enumeration"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@page import="org.exoplatform.commons.utils.PropertyManager"%>
<portlet:defineObjects/>
<%
String[] configs = (String[])renderRequest.getAttribute(SystemConfigPortlet.CONFIG_PROPERTIES);
%>
<h2>Config system</h2>
<div>
	<%if(configs != null && configs.length > 0) { %>
	<form method="post" action="<portlet:actionURL>
									<portlet:param name="javax.portlet.action" value="saveConfig" />
								</portlet:actionURL>">
		<%
		for(String key : configs) {
			%>
			<label for<%=key%>><%=key %></label>
			<input id<%=key%> name="<%=key%>" value="<%=PropertyManager.getProperty(key)%>"/>
			<br/>
			<%
		}
		%>
		<input type="submit" value="Save" />
	</form>
	<%} else {%>
		<strong>Khong co thuoc tinh nao de setup</strong>
	<%} %>
</div>