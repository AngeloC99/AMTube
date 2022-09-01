package it.uniroma1.ApiGateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class PreFilterOptions extends ZuulFilter{
    private final Logger log = LoggerFactory.getLogger(RouteFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (Objects.equals(request.getMethod(), "OPTIONS")) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("PreFilterOptions: " + String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        return ResponseEntity.status(200).build();
    }
}