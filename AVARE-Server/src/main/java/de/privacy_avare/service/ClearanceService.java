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

package de.privacy_avare.service;

import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.privacy_avare.config.DefaultProperties;
import de.privacy_avare.domain.Profile;
import de.privacy_avare.repository.ProfileRepository;

/**
 * Der Service dient zum endgültigen Löschen von Profilen aus der Datenbank. Der
 * Service ist hauptsächlich für die Anwendung in einem Scheduler gedacht.
 * 
 * @author Lukas Struppek
 * @version 1.0
 *
 */

@Service
public class ClearanceService {

	/**
	 * Service zum Abruf der Profile aus der Datenbank.
	 */
	@Autowired
	private ProfileRepository profileRepository;

	private static int monthsBeforeDeletion;
	private static String adress;
	private static int port;
	private static String databaseName;

	static {
		InputStream inputStream = null;
		try {
			inputStream = ClearanceService.class.getResourceAsStream("/application.properties");
			Properties properties = new Properties(new DefaultProperties());
			properties.load(inputStream);

			monthsBeforeDeletion = Integer.valueOf(properties.getProperty("server.monthsBeforeDeletion"));
			adress = properties.getProperty("couchdb.adress");
			port = Integer.valueOf(properties.getProperty("couchdb.port"));
			databaseName = properties.getProperty("couchdb.databaseName");
		} catch (Exception e) {
			e.printStackTrace();
			monthsBeforeDeletion = 18;
			adress = "http://localhost";
			port = 5984;
			databaseName = "profiles";

		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Folgender Zeitraum ohne Profilkontakt vor dem Loeschen wurde festgelegt:");
			System.out.println("\t Zeitraum in Monaten: " + monthsBeforeDeletion);
			System.out.println("************************************************");
		}
	}

	/**
	 * Konstruktor zum Festlegen des Zeitraums, nach dem ein Profil ohne Kontakt
	 * gelöscht wird. Zeitraum wird in application.properties festgelegt.
	 * Default-Wert sind 18 Monate.
	 */
	public ClearanceService() {

	}

	/**
	 * Löscht alle Profile in der Datenbank endgültig, auf welche länger als 18
	 * Monate nicht zugegriffen wurde. Der Zugriffszeitpunkt wird anhand der
	 * Eigenschaft lastProfileContact geprüft.
	 */
	public void cleanDatabase() {
		// Berechnung Zeitpunkt vor 'monthsBeforeDeletion' Monaten
		Calendar cal = GregorianCalendar.getInstance(Locale.GERMANY);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - (30 * monthsBeforeDeletion));

		// Suchen und Löschen aller Profile mit lastProfileContact vor
		// 'monthsBeforeDeletion' Monaten oder länger
		System.out.println("************************************************");
		System.out.println("\t Clearance-Prozess gestartet um " + GregorianCalendar.getInstance(Locale.GERMANY).getTime());

		Iterable<Profile> unusedProfiles = profileRepository.findAllByLastProfileContactBefore(cal.getTime());

		System.out.println("\t Anzahl geloeschter Profile: " + unusedProfiles.spliterator().getExactSizeIfKnown());

		profileRepository.delete(unusedProfiles);

		// Aufruf des _compact-Befehls von CouchDB
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = adress + ":" + port + "/" + databaseName + "/" + "_compact";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>("", headers);
			restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			System.out.println("\t Compact-Befehl durchgeführt: true");
		} catch (Exception e) {
			System.out.println("\t Compact-Befehl durchgeführt: false");
			e.printStackTrace();
		}

		System.out.println("\t Clearance-Prozess beendet um " + GregorianCalendar.getInstance(Locale.GERMANY).getTime());
		System.out.println("************************************************");

	}

}
