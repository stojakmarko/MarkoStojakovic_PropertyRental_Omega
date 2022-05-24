package com.example.propertyrental.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthorizeFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwt;
    private CustemDetailUserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("/sign")){
            filterChain.doFilter(request,response);
        }else{
            try {
                String token = request.getHeader("Authorization");
                if(token != null && token.startsWith("Bearer ")){
                    token = token.substring(7);
                    String username = jwt.getUserNameFromToken(token);
                    if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
                        UserDetails user = userService.loadUserByUsername(username);
                        if(jwt.validateToken(token,user)){
                            UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                            authenticate.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticate);
                        }
                    }
                }
                filterChain.doFilter(request,response);

            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());

            }
        }
        
    }
}
