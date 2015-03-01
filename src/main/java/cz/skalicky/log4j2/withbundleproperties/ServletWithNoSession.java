package cz.skalicky.log4j2.withbundleproperties;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = { "/index-no-session.html" })
public class ServletWithNoSession extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(ServletWithSession.class);

    private static LocalDateTime startTime = null;
    private static final AtomicLong visitors = new AtomicLong(0l);
    private static final String REVISION = "NO_SESSION_D";

    @Override
    protected void doGet(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) throws ServletException, IOException {

        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
        httpServletResponse.setContentType("text/html");
        final String responseBody = String.format(getHtmlPageTemplate(), startTime,
                visitors.incrementAndGet(), httpServletRequest.getRequestURI());
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
                + "  <p><a href=\"%s\">Refresh</a></p>"
                + "</body>"
                + "</html>";
        // @formatter:on
    }

}
