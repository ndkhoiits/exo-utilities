package t3.samples.portlets.markup.headers;

import org.w3c.dom.Element;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.GenericPortlet;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class SettingMarkupHeadElementPortlet extends GenericPortlet {
   
   @Override
   protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      response.setContentType("text/html");
      PrintWriter w = response.getWriter();
      w.write("<div class=\"portlet-section-header\">You should see <i>\"Setting Markup Head Element Portlet in VIEW mode\"</i> text in the page's title</div>");

      PortletURL renderURL = response.createRenderURL();
      renderURL.setPortletMode(PortletMode.EDIT);
      w.write("<p> Click <a href=\"" + renderURL.toString()
         + "\">here</a> to switch to EDIT mode");
      Element element = response.createElement("title");
      element.setTextContent("Setting Markup Head Element Portlet in VIEW mode");
      response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, element);
   }

   @Override
   protected void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      response.setContentType("text/html");
      PrintWriter w = response.getWriter();
      w.write("<div class=\"portlet-section-header\">You should see <i>\"Setting Markup Head Element Portlet in EDIT mode\"</i> text in the page's title</div>");

      PortletURL renderURL = response.createRenderURL();
      renderURL.setPortletMode(PortletMode.VIEW);
      w.write("<p> Click <a href=\"" + renderURL.toString()
         + "\">here</a> to switch to VIEW mode");
      Element element = response.createElement("title");
      element.setTextContent("Setting Markup Head Element Portlet in EDIT mode");
      response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, element);
   }
}
