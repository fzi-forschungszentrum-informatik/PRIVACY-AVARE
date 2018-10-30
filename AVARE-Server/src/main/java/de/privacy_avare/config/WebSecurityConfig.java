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

import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Klasse dient zur Konfiguration von BasicAuth für Swagger-UI. Weitere
 * Sicherheitseinstellungen können vorgenommen werden.
 * 
 * @author Lukas Struppek
 * @version 1.0
 *
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private String username;
	private String password;

	/**
	 * Konstruktor liest aus dem File application.properties die festgelegten
	 * Zugangsdaten für den Admin-Zugang aus. Bei Fehlern werden die Zugangsdaten
	 * auf default-Werte gesetzt.
	 */
	public WebSecurityConfig() {

		InputStream inputStream = null;
		try {
			inputStream = getClass().getResourceAsStream("/application.properties");
			Properties properties = new Properties(new DefaultProperties());
			properties.load(inputStream);

			this.username = properties.getProperty("admin.username");
			this.password = properties.getProperty("admin.password");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Methode dient zur Konfiguration des UserManagers. Damit werden u.a.
	 * bestehende Nutzer verwaltet.
	 * 
	 * @return Konfigurierter UserDetailsService
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername(this.username).password(this.password).roles("Admin").build());
		return manager;
	}

	/**
	 * Legt für Swagger-Ui und den DevController eine Authorisierung fest.
	 * 
	 * @param http
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/swagger-ui.html").authenticated().antMatchers("/v1/profiles/**").permitAll()
				.antMatchers("/v1/dev/**").authenticated().antMatchers("/v1/newProfiles/**").permitAll().and()
				.httpBasic().and().csrf().disable();
	}
}
