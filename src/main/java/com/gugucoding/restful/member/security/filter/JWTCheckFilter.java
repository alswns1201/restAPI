package com.gugucoding.restful.member.security.filter;

import com.gugucoding.restful.member.security.auth.CustomUserPrincipal;
import com.gugucoding.restful.member.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.util.CastUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    /*
        JWTCheckFilter 가 동작하지 않아야 하는 곳  : 예 'api/v1/token/make , token/refresh 등
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return super.shouldNotFilter(request);
        if(request.getServletPath().startsWith("/api/v1/token/"))
        {
            return true;
        }
        // 경로 지정 필요.
        return  false;
    }


    /*
        Acess Token에 대한 처리 ( 보통 Authorization  을 header에 넣어서 처리 )
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("JWTCheckFilter do Filter -----------------------------");

        logger.info(request.getRequestURI());
        String headerStr  = request.getHeader("Authorization");

        if(headerStr == null || headerStr.startsWith("Bearer ")){
            handleException(response, new Exception("Access Token not fount"));
            return;
        }

        //문제가 없다면
        String accessToken = headerStr.substring(7);
        try {
            Map<String,Object> tokenMap = jwtUtil.validateToken(accessToken);

            log.info("token value =---------------------");
            log.info(tokenMap);

            String mid = tokenMap.get("mid").toString();
            String[] roles = tokenMap.get("role").toString().split(",");

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            new CustomUserPrincipal(mid),
                            null,
                            Arrays.stream(roles).map(role-> new SimpleGrantedAuthority("ROLE_"+role))
                                    .collect(Collectors.toList())
                    );

            //SecurityContextHolder에 Authentication 객체를 저장.
            // 이후 SecurityContextHolder를 통해서 Authentication 객체를 꺼내서 사용 할 수 있다.
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);



            filterChain.doFilter(request,response);
        }
        catch (Exception e){
            handleException(response,e);
        }
    }

    private void handleException(HttpServletResponse response , Exception e) throws IOException{
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().println("err :" + e.getMessage());
    }


}
