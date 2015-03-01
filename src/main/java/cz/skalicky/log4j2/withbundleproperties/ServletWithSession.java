package cz.skalicky.log4j2.withbundleproperties;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = { "/index-session.html" })
public class ServletWithSession extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(ServletWithSession.class);

    private static LocalDateTime startTime = null;
    private static final AtomicLong visitors = new AtomicLong(0l);
    private static final String FORGET_PARAMETER_NAME = "forget";
    private static final String REVISION = "SESSION_D";

    @Override
    protected void doGet(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) throws ServletException, IOException {

        if (Boolean.TRUE.toString().equals(httpServletRequest.getParameter(FORGET_PARAMETER_NAME))) {
            final boolean createNewSessionIfNotExists = true;
            httpServletRequest.getSession(createNewSessionIfNotExists).invalidate();
            httpServletResponse.sendRedirect(httpServletRequest.getRequestURI());
            logger.info("REDIRECTION!!!");
            return;
        }
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
        final boolean createNewSessionIfNotExists = true;
        final HttpSession httpSession = httpServletRequest.getSession(createNewSessionIfNotExists);
        if (httpSession.isNew()) {
            httpSession.setAttribute("userArrived", LocalDateTime.now());
        }
        httpServletResponse.setContentType("text/html");
        final String responseBody = String.format(getHtmlPageTemplate(), startTime,
                visitors.incrementAndGet(), httpSession.getId(), httpSession.getAttribute("userArrived"),
                httpServletRequest.getRequestURI(), httpServletRequest.getRequestURI());
        logger.info(responseBody);
        httpServletResponse.getWriter().println(responseBody);
    }

    private String getHtmlPageTemplate() {
        // @formatter:off
        return "<html>"
                + "<head><title>Example</title></head>"
                + "<body>"
                + "  <p>Revision: " + REVISION + "</p>"
                + "  <p>Start Time: %s</p>"
                + "  <p>Page Visits: %d</p>"
                + "  <p>Session ID: %s</p>"
                + "  <p>Session Started: %s</p>"
                + "  <p><a href=\"%s\">Refresh</a> <a href=\"%s?" + FORGET_PARAMETER_NAME + "=true\">New Session</a></p>"
                + "</body>"
                + "</html>";
        // @formatter:on
    }

}
