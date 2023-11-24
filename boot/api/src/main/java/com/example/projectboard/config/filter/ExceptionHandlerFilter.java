package com.example.projectboard.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.projectboard.support.error.ApiException;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.response.ApiResponse;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            filterChain.doFilter(request, response);

        } catch (ApiException e) {
            setErrorResponse(response, e.getErrorType().getStatus().value(), e.getErrorType().getMessage());
        } catch (JwtException ex) {
            //토큰의 유효기간 만료
            setErrorResponse(response, ErrorType.INVALID_TOKEN.getStatus().value(), ErrorType.INVALID_TOKEN.getMessage());
        }
    }
    private void setErrorResponse(HttpServletResponse response, int errorCode, String message){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setStatus(ErrorType.INVALID_TOKEN.getStatus().value());

        try{
            response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error(ErrorType.INVALID_TOKEN)));
//            response.getWriter().write(objectMapper.writeValueAsString(resultDTO));
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
