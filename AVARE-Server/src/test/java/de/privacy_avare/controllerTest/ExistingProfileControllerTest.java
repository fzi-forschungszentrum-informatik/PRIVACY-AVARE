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

package de.privacy_avare.controllerTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import de.privacy_avare.config.DefaultProperties;
import de.privacy_avare.domain.Profile;
import de.privacy_avare.repository.ProfileRepository;

/**
 * Integrationstest für den REST-Controller ExistingProfileController, welcher
 * Schnittstellen zur Interaktion mit existierenden Profilen in der Datenbank
 * bereitstellt.
 * 
 * @author Lukas Struppek
 * @version 1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ExistingProfileControllerTest {
	/**
	 * Dient zum Aufruf der REST-API.
	 */
	@Autowired
	TestRestTemplate restTemplate;

	/**
	 * Dient zum direkten Datenbankzugriff ohne Umwege über REST-API.
	 */
	@Autowired
	ProfileRepository profileRepository;

	/**
	 * Speichert die im Zuge des Tests generierten ProfileIds.
	 */
	private LinkedList<String> generatedIds = new LinkedList<String>();

	/**
	 * Dient zum konvertieren von Zeitpunkten ins String-Format, um die REST-API
	 * entsprechend zu nutzen.
	 */
	private SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy-MM-dd'T'HH-mm-ss-SSS");

	/**
	 * Speichert die zum Testen erzeugte ProfileId.
	 */
	private String mockId;

	/**
	 * Speichert die Einstellung minTimeDifference aus dem File
	 * application.properties.
	 */
	private int minTimeDifference;

	/**
	 * Default-Konstruktor ohne erweiterte Funktionalität.
	 */
	public ExistingProfileControllerTest() {

	}

	/**
	 * Generiert eine ProfileId, welche in der Datenbank noch nicht vorhanden sein
	 * kann. Die ProfileId enthält nur Kleinbuchstaben.
	 */
	@Before
	public void generateMockProfile() {
		// Generierung einer MockId nach dem Schema xxxxxx1234567890, wobei x für
		// beliebige Kleinbuchstaben gilt.
		StringBuffer mockId = new StringBuffer();
		mockId.append("a");
		char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		for (int i = 0; i < 5; ++i) {
			mockId.append(chars[(int) (Math.random() * 26)]);
		}
		mockId.append("1234567890");
		this.mockId = mockId.toString().toLowerCase();
		restTemplate.postForEntity("/v1/newProfiles/" + this.mockId, null, String.class);
	}

	/**
	 * Lädt die Einstellungen aus dem application.properties-File.
	 */
	@Before
	public void loadApplicationProperties() {
		Reader reader = null;
		try {
			reader = new FileReader("src/main/resources/application.properties");
			Properties properties = new Properties(new DefaultProperties());
			properties.load(reader);

			this.minTimeDifference = Integer.valueOf(properties.getProperty("server.minTimeDifference"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Löscht alle aus den Tests verbleibenden Profile aus der Datenbank.
	 */
	@After
	public void deleteGeneratedProfils() {
		for (String id : generatedIds) {
			profileRepository.delete(id);
		}
		generatedIds.clear();
	}

	/**
	 * Integrationstest für REST-API DELETE /v1/profiles/{id}. Zunächst wird geprüft, ob
	 * ein bereits vorhandenes Profil korrekt auf ein unsync-Profil gesetzt wird und
	 * die Eigenschaft lastProfileChange korrekt angepasst wird (HttpStatus 200).
	 * 
	 * Anschließend wird geprüft, ob ein bereits aus unsync gesetztes Profil durch
	 * erneuten Unsync-Befehl geändert wird oder die Datenbank ihre Konsistenz
	 * beibehält (HttpStatus 200).
	 * 
	 * Weiterhin wird geprüft, ob beim Versuch des Löschens mittels einer nicht
	 * vorhandenen ProfileId, einer ProfileId mit ungültigem Format und keiner
	 * Übergabe einer ProfileId eine entsprechende Fehlermeldung zurückgegeben wird
	 * (HttpStatus 404).
	 */
	@Test
	public void testDeleteProfile() {
		String mockUnsyncPreferences = "Profil von Synchronisation ausgeschlossen!";
		generatedIds.add(this.mockId);

		// Überprüfung des Löschens eines Profils, bei welchem dies ein unsync-Profil
		// erhält.
		ResponseEntity<Void> responseEntity1 = restTemplate.exchange("/v1/profiles/" + this.mockId, HttpMethod.DELETE,
				new HttpEntity<String>(mockUnsyncPreferences), Void.class);
		assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
		Profile mockProfile1 = profileRepository.findOne(this.mockId);
		assertThat(mockProfile1.getPreferences()).isEqualTo(mockUnsyncPreferences);
		assertThat(mockProfile1.getLastProfileChange())
				.isAfterYear(GregorianCalendar.getInstance(Locale.GERMANY).get(Calendar.YEAR) + 99);
		assertThat(mockProfile1.getLastProfileChange())
				.isBeforeYear(GregorianCalendar.getInstance(Locale.GERMANY).get(Calendar.YEAR) + 101);

		// Überprüfung des Löschens des Profils, nachdem dies bereits aus unsync gesetzt
		// wurde.
		ResponseEntity<Void> responseEntity2 = restTemplate.exchange("/v1/profiles/" + this.mockId, HttpMethod.DELETE,
				new HttpEntity<String>(mockUnsyncPreferences), Void.class);
		assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);
		Profile mockProfile2 = profileRepository.findOne(this.mockId);
		assertThat(mockProfile2.getPreferences()).isEqualTo(mockUnsyncPreferences);
		assertThat(mockProfile2.getLastProfileChange())
				.isAfterYear(GregorianCalendar.getInstance(Locale.GERMANY).get(Calendar.YEAR) + 99);
		assertThat(mockProfile2.getLastProfileChange())
				.isBeforeYear(GregorianCalendar.getInstance(Locale.GERMANY).get(Calendar.YEAR) + 101);

		// Überprüfung des Löschens eines nicht vorhandenen Profils mit gültiger
		// ProfileId.
		ResponseEntity<String> responseEntity3 = restTemplate.exchange("/v1/profiles/" + this.mockId.replace('a', 'b'),
				HttpMethod.DELETE, new HttpEntity<String>(mockUnsyncPreferences), String.class);
		assertThat(responseEntity3.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity3.getBody()).containsSequence("ProfileNotFoundException");

		// Überprüfung des Löschens eines Profils mit ungültigem Format der übergebenen
		// ProfileId.
		ResponseEntity<String> responseEntity4 = restTemplate.exchange("/v1/profiles/" + this.mockId + "b",
				HttpMethod.DELETE, new HttpEntity<String>(mockUnsyncPreferences), String.class);
		assertThat(responseEntity4.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity4.getBody()).containsSequence("ProfileNotFoundException");

		// Überprüfung des Löschens eines Profils ohne Übergabe der ProfileId
		ResponseEntity<String> responseEntity5 = restTemplate.exchange("/v1/profiles/", HttpMethod.DELETE,
				new HttpEntity<String>(mockUnsyncPreferences), String.class);
		assertThat(responseEntity5.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity5.getBody()).containsSequence("Not Found");
	}

	/**
	 * Integrationstest für REST-API PUT /v1/profiles/{id}/{clientProfileChange}. Zunächst
	 * wird überprüft, ob bei einem neu erzeugten Profil die Preferences beim ersten
	 * Mal korrekt überschrieben werden (HttpStatus 200).
	 * 
	 * Anschließend wird geprüft, ob bei zu geringen Zeitunterschiedenem beim Push
	 * dieser entsprechend abgelehnt wird (HttpStatus 409).
	 * 
	 * Daneben wird die Reaktion auf leere Preferences bzw. ein ungültiges Format
	 * für den Zeitstempel korrekt beantwortet werden (HttpStatus 400)
	 * 
	 * Weiterhin wird geprüft, ob Preferences mit genügend Aktualität korrekt in die
	 * Datenbank geschrieben werden und die Eigenschaften lastProfileChange und
	 * lastProfileContact entsprechend angepasst werden (HttpStatus 200).
	 * 
	 * Abschließend wird geprüft, ob nicht in der Datenbank vorhandene Profile bzw.
	 * ProfileIds mit ungültigem Format korrekt abgefangen werden (HttpStatus 404).
	 */
	@Test
	public void testPushProfilePreferences() {
		String mockPreferences = "Die Präferenzen wurden erfolgreich aktualisiert";
		generatedIds.add(this.mockId);
		ResponseEntity<String> responseEntity;

		// Überprüfung, ob erstes Setzen der Preferences nach Erzeugung des Profils
		// fehlerfrei abläuft und die Eigenschaft lastProfileChange sowie
		// lastProfileContact korrekt angepasst werden.
		responseEntity = restTemplate.exchange("/v1/profiles/" + this.mockId + "/" + dateFormat.format(new Date()),
				HttpMethod.PUT, new HttpEntity<String>("Initiale Preferences"), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Profile dbProfile = profileRepository.findOne(this.mockId);
		assertThat(dbProfile.getPreferences()).isEqualTo("Initiale Preferences");
		assertThat(dbProfile.getLastProfileChange()).isCloseTo(GregorianCalendar.getInstance(Locale.GERMANY).getTime(),
				1000);
		assertThat(dbProfile.getLastProfileContact()).isCloseTo(GregorianCalendar.getInstance(Locale.GERMANY).getTime(),
				1000);

		// Überprüfung, ob Überschreiben der Preferences unterhalb des minimalen
		// Zeitabstandes minTimeDifference korrekt abgelehnt wird.
		responseEntity = restTemplate.exchange("/v1/profiles/" + this.mockId + "/" + dateFormat.format(new Date()),
				HttpMethod.PUT, new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(responseEntity.getBody()).containsSequence("ClientPreferencesOutdatedException");

		// Überprüfung, ob Überschreibung der Preferencen mit gültigen Preferences und
		// falschem Format des Datums abgelehnt werden.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(new Date()).replace("T", ""), HttpMethod.PUT,
				new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(responseEntity.getBody()).containsSequence("MethodArgumentTypeMismatchException");

		// Überprüfung, ob Ersetzen der Preferences mit leeren Preferences und
		// genügend Aktualität des Profils abgelehnt wird.
		Calendar calendar = GregorianCalendar.getInstance(Locale.GERMANY);
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 1);
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(calendar.getTime()), HttpMethod.PUT,
				new HttpEntity<String>(""), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(responseEntity.getBody()).containsSequence("HttpMessageNotReadableException");

		// Überprüfung, ob Ersetzen der Preferences mit gültigen Preferences und
		// genügend Aktualität des Profils fehlerfrei abläuft. Dabei werden auch die
		// angepassten Werte von lastProfileChange und lastProfileContact untersucht.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(calendar.getTime()), HttpMethod.PUT,
				new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		dbProfile = profileRepository.findOne(this.mockId);
		assertThat(dbProfile.getPreferences()).isEqualTo(mockPreferences);
		assertThat(dbProfile.getLastProfileChange()).isEqualTo(calendar.getTime());
		assertThat(dbProfile.getLastProfileContact()).isCloseTo(GregorianCalendar.getInstance(Locale.GERMANY).getTime(),
				1000);

		// Überprüfung, ob nicht in der Datenbank vorhandene ProfileIds korrekt erkannt
		// werden.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId.replace('a', 'b') + "/" + dateFormat.format(new Date()), HttpMethod.PUT,
				new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity.getBody()).containsSequence("ProfileNotFoundException");

		// Überprüfung, ob bei ungültigen ProfileIds ebenfalls eine
		// korrekte Fehlermeldung geworfen wird.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId.substring(1) + "/" + dateFormat.format(new Date()), HttpMethod.PUT,
				new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity.getBody()).containsSequence("ProfileNotFoundException");
	}

	/**
	 * Integrationstest für REST-API PUT /v1/profiles/{id}/{clientProfileChange}/false.
	 * Entspricht dem Test ohne Angabe des Overwrite-Flags.
	 * 
	 * Zunächst wird überprüft, ob bei einem neu erzeugten Profil die Preferences
	 * beim ersten Mal korrekt überschrieben werden (HttpStatus 200).
	 * 
	 * Anschließend wird geprüft, ob bei zu geringen Zeitunterschiedenem beim Push
	 * dieser entsprechend abgelehnt wird (HttpStatus 409).
	 * 
	 * Daneben wird die Reaktion auf leere Preferences bzw. ein ungültiges Format
	 * für den Zeitstempel korrekt beantwortet werden (HttpStatus 400)
	 * 
	 * Weiterhin wird geprüft, ob Preferences mit genügend Aktualität korrekt in die
	 * Datenbank geschrieben werden und die Eigenschaften lastProfileChange und
	 * lastProfileContact entsprechend angepasst werden (HttpStatus 200).
	 * 
	 * Abschließend wird geprüft, ob nicht in der Datenbank vorhandene Profile bzw.
	 * ProfileIds mit ungültigem Format korrekt abgefangen werden (HttpStatus 404).
	 */
	@Test
	public void testPushProfilePreferencesWithoutOverwriting() {
		String mockPreferences = "Die Präferenzen wurden erfolgreich aktualisiert";
		generatedIds.add(this.mockId);
		ResponseEntity<String> responseEntity;

		// Überprüfung, ob erstes Setzen der Preferences nach Erzeugung des Profils
		// fehlerfrei abläuft und die Eigenschaft lastProfileChange sowie
		// lastProfileContact korrekt angepasst werden.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(new Date()) + "/false", HttpMethod.PUT,
				new HttpEntity<String>("Initiale Preferences"), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Profile dbProfile = profileRepository.findOne(this.mockId);
		assertThat(dbProfile.getPreferences()).isEqualTo("Initiale Preferences");
		assertThat(dbProfile.getLastProfileChange()).isCloseTo(GregorianCalendar.getInstance(Locale.GERMANY).getTime(),
				1000);
		assertThat(dbProfile.getLastProfileContact()).isCloseTo(GregorianCalendar.getInstance(Locale.GERMANY).getTime(),
				1000);

		// Überprüfung, ob Überschreiben der Preferences unterhalb des minimalen
		// Zeitabstandes minTimeDifference korrekt abgelehnt wird.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(new Date()) + "/false", HttpMethod.PUT,
				new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(responseEntity.getBody()).containsSequence("ClientPreferencesOutdatedException");

		// Überprüfung, ob Überschreibung der Preferencen mit gültigen Preferences und
		// falschem Format des Datums abgelehnt werden.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(new Date()).replace("T", "") + "/false",
				HttpMethod.PUT, new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(responseEntity.getBody()).containsSequence("MethodArgumentTypeMismatchException");

		// Überprüfung, ob Ersetzen der Preferences mit leeren Preferences und
		// genügend Aktualität des Profils abgelehnt wird.
		Calendar calendar = GregorianCalendar.getInstance(Locale.GERMANY);
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 1);
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(calendar.getTime()) + "/false", HttpMethod.PUT,
				new HttpEntity<String>(""), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(responseEntity.getBody()).containsSequence("HttpMessageNotReadableException");

		// Überprüfung, ob Ersetzen der Preferences mit gültigen Preferences und
		// genügend Aktualität des Profils fehlerfrei abläuft. Dabei werden auch die
		// angepassten Werte von lastProfileChange und lastProfileContact untersucht.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(calendar.getTime()) + "/false", HttpMethod.PUT,
				new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		dbProfile = profileRepository.findOne(this.mockId);
		assertThat(dbProfile.getPreferences()).isEqualTo(mockPreferences);
		assertThat(dbProfile.getLastProfileChange()).isEqualTo(calendar.getTime());
		assertThat(dbProfile.getLastProfileContact()).isCloseTo(GregorianCalendar.getInstance(Locale.GERMANY).getTime(),
				1000);

		// Überprüfung, ob nicht in der Datenbank vorhandene ProfileIds korrekt erkannt
		// werden.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId.replace('a', 'b') + "/" + dateFormat.format(new Date()) + "/false",
				HttpMethod.PUT, new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity.getBody()).containsSequence("ProfileNotFoundException");

		// Überprüfung, ob bei ungültigen ProfileIds ebenfalls eine
		// korrekte Fehlermeldung geworfen wird.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId.substring(1) + "/" + dateFormat.format(new Date()) + "/false",
				HttpMethod.PUT, new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity.getBody()).containsSequence("ProfileNotFoundException");
	}

	/**
	 * Integrationstest für REST-API PUT /v1/profiles/{id}/{clientProfileChange}/true.
	 * 
	 * Zunächst wird geprüft, ob bei neu erzeugten Profilen und bereits bestehenden
	 * Profilen die Preferences, lastProfileContact und lastProfileChange
	 * entsprechend angepasst werden (HttpStatus 200).
	 * 
	 * Anschließend wird geprüft, ob bei falschem Format des Zeitstempels bzw. bei
	 * Übergabe eines leeren Strings entsprechend eine Fehlermeldung gesendet wird
	 * (HttpStatus 400).
	 * 
	 * Schließlich wird noch geprüft, ob bei ungültigen bzw. nicht in der Datenbank
	 * vorhandenen Profile eine entsprechende Fehlermeldung zurückgeliefert wird
	 * (HttpStatus 404).
	 * 
	 */
	@Test
	public void testPushProfilePreferencesWithOverwriting() {
		String mockPreferences = "Die Präferenzen wurden erfolgreich aktualisiert";
		generatedIds.add(this.mockId);
		ResponseEntity<String> responseEntity;

		// Überprüfung, ob erstes Setzen der Preferences nach Erzeugung des Profils
		// fehlerfrei abläuft und die Eigenschaft lastProfileChange sowie
		// lastProfileContact korrekt angepasst werden.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(new Date()) + "/true", HttpMethod.PUT,
				new HttpEntity<String>("Initiale Preferences"), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Profile dbProfile = profileRepository.findOne(this.mockId);
		assertThat(dbProfile.getPreferences()).isEqualTo("Initiale Preferences");
		assertThat(dbProfile.getLastProfileChange()).isCloseTo(GregorianCalendar.getInstance(Locale.GERMANY).getTime(),
				1000);
		assertThat(dbProfile.getLastProfileContact()).isCloseTo(GregorianCalendar.getInstance(Locale.GERMANY).getTime(),
				1000);

		// Überprüfung, ob Überschreiben der Preferences unterhalb des minimalen
		// Zeitabstandes minTimeDifference korrekt überschrieben wird.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(new Date()) + "/true", HttpMethod.PUT,
				new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		dbProfile = profileRepository.findOne(this.mockId);
		assertThat(dbProfile.getPreferences()).containsSequence(mockPreferences);
		assertThat(dbProfile.getLastProfileChange()).isCloseTo(new Date(), 1000);
		assertThat(dbProfile.getLastProfileContact()).isCloseTo(new Date(), 1000);

		// Überprüfung, ob Überschreibung der Preferencen mit gültigen Preferences und
		// falschem Format des Datums abgelehnt werden.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(new Date()).replace("T", "") + "/true",
				HttpMethod.PUT, new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(responseEntity.getBody()).containsSequence("MethodArgumentTypeMismatchException");

		// Überprüfung, ob Ersetzen der Preferences mit leeren Preferences abgelehnt
		// wird.
		Calendar calendar = GregorianCalendar.getInstance(Locale.GERMANY);
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 1);
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(calendar.getTime()) + "/true", HttpMethod.PUT,
				new HttpEntity<String>(""), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(responseEntity.getBody()).containsSequence("HttpMessageNotReadableException");

		// Überprüfung, ob nicht in der Datenbank vorhandene ProfileIds korrekt erkannt
		// werden.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId.replace('a', 'b') + "/" + dateFormat.format(new Date()) + "/true",
				HttpMethod.PUT, new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity.getBody()).containsSequence("ProfileNotFoundException");

		// Überprüfung, ob bei ungültigen ProfileIds ebenfalls eine
		// korrekte Fehlermeldung geworfen wird.
		responseEntity = restTemplate.exchange(
				"/v1/profiles/" + this.mockId.substring(1) + "/" + dateFormat.format(new Date()) + "/true",
				HttpMethod.PUT, new HttpEntity<String>(mockPreferences), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity.getBody()).containsSequence("ProfileNotFoundException");
	}

	/**
	 * Integrationstest für REST-API GET /v1/profiles/{id}/{clientProfileChange}
	 * 
	 * Zunächst wird überprüft, ob bei zu geringem Zeitunterschied zwischen
	 * ClientProfile und ServerProfile entsprechend eine Meldung zurückgeliefert
	 * wird (HttpStatus 409).
	 * 
	 * Anschließend wird bei ausreichend Zeitunterschied geprüft, ob die Preferences
	 * entsprechend zurückgeliefert werden (HttpStatus 200).
	 * 
	 * Schließlich wird die Reaktion auf ein ungültiges Format des Zeitstempels
	 * (HttpStatus 400) und eine ungültige ProfileId (HttpStatus 404) überprüft.
	 * 
	 */
	@Test
	public void testPullProfilePreferences() {
		Calendar profileChangeCalendar = GregorianCalendar.getInstance(Locale.GERMANY);
		Calendar profileContactCalendar = GregorianCalendar.getInstance(Locale.GERMAN);
		String mockPreferences = "Gesetzte Preferences";
		Profile dbProfile = profileRepository.findOne(this.mockId);
		dbProfile.setPreferences(mockPreferences);
		dbProfile.setLastProfileChange(profileChangeCalendar.getTime());
		dbProfile.setLastProfileContact(profileContactCalendar.getTime());
		profileRepository.save(dbProfile);
		generatedIds.add(dbProfile.get_id());

		// Überprüfung des Abrufs bei zu geringem Zeitunterschied (innerhalb von
		// MinTimeDifference).
		profileChangeCalendar.set(Calendar.MINUTE, profileChangeCalendar.get(Calendar.MINUTE) - this.minTimeDifference);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(profileChangeCalendar.getTime()), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(responseEntity.getBody()).contains("ServerPreferencesOutdatedException");
		assertThat(profileRepository.findOne(this.mockId).getLastProfileContact())
				.isCloseTo(profileContactCalendar.getTime(), 1000);

		// Überprüfung des Abrufs bei ausreichendem Zeitunterschied.
		profileChangeCalendar.set(Calendar.MINUTE, profileChangeCalendar.get(Calendar.MINUTE) - 1);
		profileContactCalendar = GregorianCalendar.getInstance(Locale.GERMAN);
		responseEntity = restTemplate.getForEntity(
				"/v1/profiles/" + this.mockId + "/" + dateFormat.format(profileChangeCalendar.getTime()), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).contains("Gesetzte Preferences");
		assertThat(profileRepository.findOne(this.mockId).getLastProfileContact())
				.isCloseTo(profileContactCalendar.getTime(), 1000);
		assertThat(responseEntity.getBody()).contains(mockPreferences);

		// Überprüfung eines falschen Formats des Zeitstempels und gültiger ProfileId
		responseEntity = restTemplate.getForEntity("/v1/profiles/" + this.mockId + "/"
				+ dateFormat.format(profileChangeCalendar.getTime()).replace('T', '_'), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(responseEntity.getBody()).contains("MethodArgumentTypeMismatchException");

		// Überprüfung von ungültigem ProfileID-Format mit ausreichendem Zeitabstand
		responseEntity = restTemplate.getForEntity("/v1/profiles/" + this.mockId.replace('a', 'b') + "/"
				+ dateFormat.format(profileChangeCalendar.getTime()), String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity.getBody()).contains("ProfileNotFoundException");
	}

}
