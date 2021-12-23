package com.onoprienko.linkscrawler;

import com.onoprienko.linkscrawler.dao.jdbc.JdbcDomainDao;
import com.onoprienko.linkscrawler.dao.jdbc.JdbcLinkDao;
import com.onoprienko.linkscrawler.service.DomainService;
import com.onoprienko.linkscrawler.service.LinkService;
import com.onoprienko.linkscrawler.web.servlet.AllRequestServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {
        JdbcDomainDao jdbcDomainDao = new JdbcDomainDao();
        JdbcLinkDao jdbcLinkDao = new JdbcLinkDao();
        LinkService linkService = new LinkService(jdbcLinkDao);
        DomainService domainService = new DomainService(jdbcDomainDao, linkService);
        AllRequestServlet allRequestServlet = new AllRequestServlet(domainService, linkService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(allRequestServlet), "/");

        Server server = new Server(8090);
        server.setHandler(contextHandler);

        server.start();
    }
}
