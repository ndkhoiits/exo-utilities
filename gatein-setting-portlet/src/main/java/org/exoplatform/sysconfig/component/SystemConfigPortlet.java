/**
 * 
 */
package org.exoplatform.sysconfig.component;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.exoplatform.commons.utils.PropertyManager;

/**
 * @author <a href="mailto:tuyen.nguyenthe@exoplatform.com">Tuyen Nguyen The</a>
 * @version $Revision$
 */
public class SystemConfigPortlet extends GenericPortlet {
	public static final String CONFIG_PROPERTIES= "config_properties";
	public static final String CONFIG_PARAM = "config-param";
	private static String[] configs = null;
	
	@RenderMode(name="view")
	public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		if(configs == null) {
			String conf = getInitParameter(CONFIG_PARAM);
			configs = conf.split(";");
		}
		Properties prop = System.getProperties();
		for(Object key : prop.keySet()) {
			System.out.println("KEY: " + key.toString() + "#" + prop.getProperty(key.toString(), ""));
		}
		request.setAttribute(CONFIG_PROPERTIES, configs);
		getPortletContext().getRequestDispatcher("/view.jsp").include(request, response);
	}
	
	@ProcessAction(name="saveConfig")
	public void processSaveConfig(ActionRequest request, ActionResponse response) throws PortletException, IOException {
		for(String key : configs) {
			String value = request.getParameter(key);
			if(value != null) {
				PropertyManager.setProperty(key, value);
			}
		}
	}
}
