package com.deploy.Travalue.config.swagger;

import com.deploy.Travalue.config.interceptor.Auth;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
public class AuthorizationSwaggerConfig implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {
        if (context.findAnnotation(Auth.class).isPresent()) {
            context.operationBuilder()
                .requestParameters(List.of(authorizationHeader()))
                .authorizations(List.of(SecurityReference.builder()
                    .reference("Authorization")
                    .scopes(authorizationScopes())
                    .build()))
                .build();
        }
    }

    private AuthorizationScope[] authorizationScopes() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("", "");
        return authorizationScopes;
    }

    private RequestParameter authorizationHeader() {
        return new RequestParameterBuilder()
            .name("Authorization")
            .required(false)
            .in(ParameterType.HEADER)
            .build();
    }

    @Override
    public boolean supports(@NotNull DocumentationType delimiter) {
        return true;
    }
}
