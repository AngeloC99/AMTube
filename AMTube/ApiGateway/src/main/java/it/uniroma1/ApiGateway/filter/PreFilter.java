package it.uniroma1.ApiGateway.filter;

import javax.servlet.http.HttpServletRequest;

import com.sun.tools.jconsole.JConsoleContext;
import it.uniroma1.ApiGateway.services.VerificationService;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.json.simple.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;

@Component
public class PreFilter extends ZuulFilter {
	private static Logger log = LoggerFactory.getLogger(PreFilter.class);
	private final VerificationService verificationService;

	public PreFilter(VerificationService verificationService) {
		this.verificationService = verificationService;
	}

	  @Override
	  public String filterType() {
	    return "pre";
	  }

	  @Override
	  public int filterOrder() {
	    return 1;
	  }

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if (!request.getRequestURL().toString().contains("/auth") & !request.getRequestURL().toString().contains("/users") & !Objects.equals(request.getMethod(), "OPTIONS")) {
			return true;
		}
		return false;
	}

	  @Override
	  public Object run() {
	    RequestContext ctx = RequestContext.getCurrentContext();
	    HttpServletRequest request = ctx.getRequest();
		log.info("Auth header from ctx: " + request.getHeader("Authorization"));


		  log.info("Custom PreFilter: " + String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

		  try {
			  ResponseEntity<String> response = this.verificationService.verificationRequest(request.getHeader("Authorization"));
			  if(response.getStatusCode().is4xxClientError()) {
				  ctx.unset();
				  ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			  }
			  else {
				  JSONObject jsonObj = this.verificationService.parseHttpResponse(response);
				  String id = jsonObj.get("id").toString();
				  String username = jsonObj.get("username").toString();
				  ctx.addZuulRequestHeader("X-AUTH-USER-ID", id);
				  ctx.addZuulRequestHeader("X-AUTH-USER-USERNAME", username);
				  log.info("Returning to PreFilter... AUTH-USER: "+ctx.getRequest().getHeader("X-AUTH-USER-ID"));
				  log.info("ID: " + id+", username: "+username);
			  }
		  } catch (URISyntaxException | ParseException e) {
			  throw new RuntimeException(e);
		  }

		  return null;
	  }
}