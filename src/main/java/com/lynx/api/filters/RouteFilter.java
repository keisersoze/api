package com.lynx.api.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

public class RouteFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(RouteFilter.class);

    @Qualifier("proxyRequestHelper")
    @Autowired
    private ProxyRequestHelper helper;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        URL url = ctx.getRouteHost();

        String uri = this.helper.buildZuulRequestURI(request);

        log.info(String.format("Routing the request to %s service at %s",uri,url));

        return null;
    }

}
