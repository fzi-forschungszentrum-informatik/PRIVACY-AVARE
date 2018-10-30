/*
 * Copyright 2017 Lukas Struppek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.privacy_avare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.service.Contact;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Klasse dient der Aktivierung und Konfiguration von Swagger bzw. Swagger UI.
 * Swagger wird zur Dokumentation der bereitgestellten REST-Schnittstellen verwendet.
 * 
 * @author Lukas Struppek
 * @version 1.0
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * Methode wird zur Konfiguration von Swagger genutzt. Weitere Methoden,
	 * welche zur Konfiguration dienen, müssen ihre Einstellungen über diese Methode
	 * in ein Docket übergeben.
	 * 
	 * @return Docket mit Konfigurationseinstellungen
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).apiInfo(setApiInfo()).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
				.build();
	}

	/**
	 * Methode konfiguriert den Infobereich auf der Swagger UI-Seite mit entsprechenden Informationen.
	 * @return Api Details
	 */
	private ApiInfo setApiInfo() {
		return new ApiInfoBuilder().title("SyncServer REST API")
				.description("Dokumentation der REST API für die Nutzung des SyncServers")
				.contact(new Contact("Lukas Struppek", "http://www.privacy-avare.de/", "lukas.struppek@gmail.com"))
				.license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.version("1.0").build();

	}
}