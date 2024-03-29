package com.deploy.Travalue.config.resolver;

import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.jwt.JwtService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import com.deploy.Travalue.exception.ErrorCode;
import com.deploy.Travalue.exception.model.TravalueException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserIdResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) && Long.class.equals(
            parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter,
        ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) {
        if (parameter.getMethodAnnotation(Auth.class) == null) {
            throw new TravalueException("인증이 필요한 컨트롤러 입니다. @Auth 어노테이션을 붙여주세요.", ErrorCode.NO_ANNOTATION_EXCEPTION);
        }

        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final String token = request.getHeader("Authorization").replace("Bearer ", "");
        ;

        if (!jwtService.verifyToken(token)) {
            throw new TravalueException(
                String.format("USER_ID를 가져오지 못했습니다. (%s - %s)", parameter.getClass(),
                    parameter.getMethod()), ErrorCode.UNAUTHORIZED_EXCEPTION);
        }

        final String subject = jwtService.getSubject(token);
        try {
            return Long.parseLong(subject);
        } catch (final NumberFormatException e) {
            throw new TravalueException(
                String.format("USER_ID를 가져오지 못했습니다. (%s - %s)", parameter.getClass(),
                    parameter.getMethod()), ErrorCode.UNAUTHORIZED_EXCEPTION);
        }
    }
}
