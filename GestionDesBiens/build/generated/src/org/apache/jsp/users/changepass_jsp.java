package org.apache.jsp.users;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class changepass_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"../css/style.css\"/>\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"../css/jquery.datetimepicker.css\"/>\n");
      out.write("\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("        <script type=\"text/javascript\" language=\"javascript\" src=\"http://code.jquery.com/jquery-1.11.3.min.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" language=\"javascript\" src=\"http://code.jquery.com/ui/1.11.3/jquery-ui.js\"></script>\n");
      out.write("        <script type='text/javascript' src=\"http://codeinnovators.meximas.com/pdfexport/jspdf.debug.js\"></script>\n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <input type=\"hidden\" id=\"user\" value=\"");
      out.print( request.getUserPrincipal().getName());
      out.write("\" />\n");
      out.write("        <div id=\"loads\"></div>\n");
      out.write("\n");
      out.write("        <div class=\"Items\">\n");
      out.write("            <table> \n");
      out.write("\n");
      out.write("                <tr><td>Name</td><td><input id=\"name\" type=\"text\" value=\"\" /></td></tr>\n");
      out.write("                <tr><td>Current password</td><td><input id=\"old\" type=\"password\" value=\"\" /></td></tr>\n");
      out.write("                <tr><td>password</td><td><input id=\"password\" type=\"password\" value=\"\" /></td></tr>\n");
      out.write("                <tr><td>Confirm password</td><td><input id=\"password2\" type=\"password\" value=\"\" /></td></tr>\n");
      out.write("                <tr><td colspan=\"2\"></td><input id=\"date\" type=\"hidden\" value=\"\" /></tr>\n");
      out.write("                <tr><td colspan=\"2\"><button id=\"save\" value=\"save\">Save</button></td></tr>\n");
      out.write("            </table>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <script type=\"text/javascript\">\n");
      out.write("            var user = $(\"#user\").val();\n");
      out.write("            $(document).ready(function () {\n");
      out.write("                edit();\n");
      out.write("\n");
      out.write("                function edit() {\n");
      out.write("\n");
      out.write("\n");
      out.write("                    $.ajax({\n");
      out.write("                        url: 'http://localhost:8080/GestionDesBiens/webresources/model.users/' + user,\n");
      out.write("                        type: \"GET\",\n");
      out.write("                        dataType: \"json\",\n");
      out.write("                        success: function (data) {\n");
      out.write("                            $('#loads').empty();\n");
      out.write("                            $(\"#password\").val();\n");
      out.write("                            $(\"#old\").val();\n");
      out.write("                            $(\"#name\").val(data.name);\n");
      out.write("                            $(\"#date\").val(data.registerDt);\n");
      out.write("\n");
      out.write("                        }\n");
      out.write("                    });\n");
      out.write("\n");
      out.write("                }\n");
      out.write("\n");
      out.write("\n");
      out.write("                $('#save').click(function (e) {\n");
      out.write("\n");
      out.write("                    var name = $(\"#name\").val();\n");
      out.write("                    var pass = $(\"#password\").val();\n");
      out.write("                    var pass2 = $(\"#password2\").val();\n");
      out.write("                    var date = $(\"#date\").val();\n");
      out.write("                    $.ajax({\n");
      out.write("                        url: 'http://localhost:8080/GestionDesBiens/webresources/model.users/' + user,\n");
      out.write("                        type: \"GET\",\n");
      out.write("                        dataType: \"json\",\n");
      out.write("                        success: function (data) {\n");
      out.write("                            var check = data.password;\n");
      out.write("                            if (check == $(\"#old\").val) {\n");
      out.write("                                if (pass = pass2) {\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("                                    var edit = '{\"name\":\"' + name + '\",\"password\":\"' + pass + '\",\"registerDt\":\"' + date + '\",\"username\":\"' + user + '\"}';\n");
      out.write("                                    jQuery.ajax({\n");
      out.write("                                        url: \"http://localhost:8080/GestionDesBiens/webresources/model.users/\" + user,\n");
      out.write("                                        type: 'PUT',\n");
      out.write("                                        data: edit,\n");
      out.write("                                        contentType: 'application/json; charset=utf-8',\n");
      out.write("                                        success: function (html) {\n");
      out.write("                                            //noinspection DocumentWriteJS\n");
      out.write("                                            alert(\"password updated\")\n");
      out.write("\n");
      out.write("                                        }\n");
      out.write("                                    });\n");
      out.write("                                }\n");
      out.write("                                else {\n");
      out.write("                                    alert(\"Password doesn't match\");\n");
      out.write("                                }\n");
      out.write("\n");
      out.write("\n");
      out.write("                            }\n");
      out.write("                            else{\n");
      out.write("                                alert(\"Kindly insert your correct current password\");\n");
      out.write("                            }\n");
      out.write("                        }\n");
      out.write("                    });\n");
      out.write("\n");
      out.write("                });\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("            });\n");
      out.write("\n");
      out.write("        </script>\n");
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
