package com.example.geospatialserver.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API микросервиса geospatial-server",
                description = "Микросервис для данных о проблемных точках",
                contact = @Contact(
                        name = "Дарья Вавилова",
                        email = "dashavavilova111002@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(url = "/", description = "GEOGREEN Server URL")
        }
)
public class OpenApiConfiguration {
}
