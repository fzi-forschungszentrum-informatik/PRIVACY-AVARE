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

package de.privacy_avare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Einstiegspunkt für das Server-Programm. Startet automatisch einen embedded
 * Tomcat-Server.
 * 
 * In der main-Methode können weitere Konfigurationen vorgenommen werden.
 * 
 * @author Lukas Struppek
 * @version 1.0
 */

@SpringBootApplication
@EnableScheduling
public class SyncServerMain {
	/**
	 * Methode dient als Einstiegspunkt des Server-Programms.
	 * 
	 * @param args
	 *            Zusätzliche Parameter, die beim Programmstart übergeben werden.
	 */
	public static void main(String[] args) {
		System.out.println("*****************************************************");
		System.out.println("\t Copyright 2017 Lukas Struppek");
		System.out.println("\t This Software is releases under version 2.0 of the Apache License");
		System.out.println("\t See LICENSE.txt for further information");
		System.out.println("*****************************************************");
		SpringApplication app = new SpringApplication(SyncServerMain.class);
		app.run(args);
	}
}