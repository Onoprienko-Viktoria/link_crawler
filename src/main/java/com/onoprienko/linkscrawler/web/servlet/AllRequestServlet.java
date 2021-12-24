package com.onoprienko.linkscrawler.web.servlet;

import com.onoprienko.linkscrawler.entity.Domain;
import com.onoprienko.linkscrawler.entity.Link;
import com.onoprienko.linkscrawler.service.DomainService;
import com.onoprienko.linkscrawler.service.LinkService;
import com.onoprienko.linkscrawler.web.utils.PageGenerator;
import com.onoprienko.linkscrawler.web.utils.LinksFilter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AllRequestServlet extends HttpServlet {
    private DomainService domainService;
    private LinkService linkService;

    public AllRequestServlet(DomainService domainService, LinkService linkService) {
        this.domainService = domainService;
        this.linkService = linkService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();

        try {
            List<Domain> domains = domainService.findAll();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("domains", domains);
            String page = pageGenerator.getPage("domains_list.html", parameters);
            resp.getWriter().write(page);
        } catch (Exception e) {
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        try {
            String link = req.getParameter("domain");
            String domainName = LinksFilter.getDomainFromLink(link);
            List<Link> links = linkService.findAllByDomain(domainName);
            Domain domain = domainService.getDomain(domainName, links);
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("links", links);
            parameters.put("domain", domain);
            String page = pageGenerator.getPage("links_list.html", parameters);
            resp.getWriter().write(page);
        } catch (Exception e) {
            resp.getWriter().write(e.getMessage());
        }
    }
}
