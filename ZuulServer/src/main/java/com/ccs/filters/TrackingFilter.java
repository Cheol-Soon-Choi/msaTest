package com.ccs.filters;

import com.ccs.config.ServiceConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrackingFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Autowired
    private ServiceConfig serviceConfig;

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

//        if (isCorrelationIdPresent()) {
//            logger.debug("***** tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
//        } else {
//            filterUtils.setCorrelationId(generateCorrelationId());
//            logger.debug("***** tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
//        }

        System.out.println("***** The organization id from the token is : " + getOrganizationId());
        filterUtils.setOrgId(getOrganizationId());
        logger.debug("***** Processing incoming request for {}.", ctx.getRequest().getRequestURI());

        return null;
    }

//    private boolean isCorrelationIdPresent() {
//        if (filterUtils.getCorrelationId() != null) {
//            return true;
//        }
//
//        return false;
//    }
//
//    private String generateCorrelationId() {
//        return java.util.UUID.randomUUID().toString();
//    }

    private String getOrganizationId() {

        String result = "";
        if (filterUtils.getAuthToken() != null) {
            String authToken = filterUtils.getAuthToken().replace("Bearer ", "");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(serviceConfig.getJwtSigningKey().getBytes("UTF-8"))
                        .parseClaimsJws(authToken).getBody();
                result = (String) claims.get("organizationId");
                logger.debug("***** claims: {}", claims);
            } catch (SignatureException e) {
                logger.error("Invalid JWT signature: {}", e.getMessage());
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT token: {}", e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.error("JWT token is expired: {}", e.getMessage());
            } catch (UnsupportedJwtException e) {
                logger.error("JWT token is unsupported: {}", e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.error("JWT claims string is empty: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Exception : {}", e.getMessage());
            }
        }
        return result;
    }

}