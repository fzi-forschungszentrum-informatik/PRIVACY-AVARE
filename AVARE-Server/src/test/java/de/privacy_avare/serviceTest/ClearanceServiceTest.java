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

package de.privacy_avare.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.test.context.junit4.SpringRunner;

import de.privacy_avare.config.DefaultProperties;
import de.privacy_avare.domain.Profile;
import de.privacy_avare.repository.ProfileRepository;
import de.privacy_avare.service.ClearanceService;

/**
 * Integrationstest für den ClearanceService, welcher veraltete Profile aus der
 * Datenbank löscht und den Speicher aufräumt.
 * 
 * Test kann nur bei leerer Datenbank korrekt durchgeführt werden, da das
 * Testergebnis anhand der Anzahl vorhandener Dokumente in der Datenbank
 * verifiziert wird.
 * 
 * @author Lukas Struppek
 * @version 1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClearanceServiceTest {
	/**
	 * Dient zum Aufruf des Services
	 */
	@Autowired
	ClearanceService clearanceService;

	/**
	 * Dient zum direkten Zugriff auf die Datenbank
	 */
	@Autowired
	ProfileRepository profileRepository;

	/**
	 * Dient zum Zugriff auf REST-Schnittstellen
	 */
	@Autowired
	TestRestTemplate restTemplate;

	/**
	 * Repräsentiert den Zeitraum, nach welchem ein Profil als zum Löschen
	 * freigegeben gilt.
	 */
	private int monthsBeforeDeletion;

	/**
	 * Speichert Referenzen auf alle veralteten Profile, die im zuge des Tests
	 * erzeugt werden.
	 */
	private Profile[] outdatedProfiles;

	/**
	 * Speichert Referenzen auf alle aktuelle Profile, die im zuge des Tests erzeugt
	 * werden.
	 */
	private Profile[] upToDateProfiles;

	/**
	 * Default-Konstruktor ohne erweiterte Funktionalität.
	 */
	public ClearanceServiceTest() {

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

			this.monthsBeforeDeletion = Integer.valueOf(properties.getProperty("server.monthsBeforeDeletion"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generiert veraltete und aktuelle Profile in der Datenbank. Die veralteten
	 * Profile sollten im Zuge des ClearanceServices gelöscht werden, die aktuellen
	 * Profile in der Datenbank beibehalten werden.
	 */
	@Before
	public void generateMockProfiles() {
		this.outdatedProfiles = new Profile[30];
		Calendar outdatedTimestamp = GregorianCalendar.getInstance(Locale.GERMANY);
		outdatedTimestamp.set(Calendar.DATE, outdatedTimestamp.get(Calendar.DATE) - (30 * this.monthsBeforeDeletion));
		outdatedTimestamp.set(Calendar.DATE, outdatedTimestamp.get(Calendar.DATE) - 1);
		for (int i = 0; i < outdatedProfiles.length; ++i) {
			this.outdatedProfiles[i] = new Profile();
			String id = "aaaaaa" + (1000000000 + i);
			this.outdatedProfiles[i].set_id(id);
			this.outdatedProfiles[i].setPreferences("Outdated Profile");
			this.outdatedProfiles[i].setLastProfileChange(new Date());
			this.outdatedProfiles[i].setLastProfileContact(outdatedTimestamp.getTime());
		}

		this.upToDateProfiles = new Profile[60];
		Calendar upToDateTimestamp = GregorianCalendar.getInstance(Locale.GERMANY);
		outdatedTimestamp.set(Calendar.DATE, outdatedTimestamp.get(Calendar.DATE) - (30 * this.monthsBeforeDeletion));
		outdatedTimestamp.set(Calendar.DATE, outdatedTimestamp.get(Calendar.DATE) + 1);
		for (int i = 0; i < upToDateProfiles.length; ++i) {
			this.upToDateProfiles[i] = new Profile();
			String id = "aaaaaa" + (2000000000 + i);
			this.upToDateProfiles[i].set_id(id);
			this.upToDateProfiles[i].setPreferences("Up To Date Profile");
			this.upToDateProfiles[i].setLastProfileChange(new Date());
			this.upToDateProfiles[i].setLastProfileContact(upToDateTimestamp.getTime());
		}

		profileRepository.save(Arrays.asList(outdatedProfiles));
		profileRepository.save(Arrays.asList(upToDateProfiles));
	}

	/**
	 * Löscht die erzeugten, aktuellen Profile aus der Datenbank, da diese im Zuge
	 * des ClearanceServices nicht gelöscht werden.
	 */
	@After
	public void deleteGeneratedProfils() {
		profileRepository.delete(Arrays.asList(upToDateProfiles));
	}

	/**
	 * Integrationstest für den Cleaning-Prozess cleanDatabase, welcher in regelmäßigen
	 * Abständen durchgeführt wird. Hierbei wird geprüft, ob veraltete Profile
	 * korrekt gelöscht werden bzw. aktuelle Profile in der Datenbank beibehalten
	 * werden.
	 */
	@Test
	public void testCleanDatabase() {
		// Prüfen, ob in der Datenbank sowohl die veralteten als auch die aktuellen
		// Profile gespeichert sind
		assertThat(profileRepository.count()).isEqualTo(this.outdatedProfiles.length + this.upToDateProfiles.length);

		// Aufruf des Cleaning-Prozesses
		clearanceService.cleanDatabase();

		// Prüfen, ob nur noch die aktuellen Profile in der Datenbank enthalten sind
		assertThat(profileRepository.count()).isEqualTo(this.upToDateProfiles.length);
		Iterable<Profile> listUpToDateProfilesInDB = profileRepository.findAll();
		assertThat(listUpToDateProfilesInDB.toString()).isEqualTo(Arrays.asList(this.upToDateProfiles).toString());

		// Prüfen, ob veraltete Profile nicht mehr in der Datenbank vorhanden sind
		String[] outdatedProfilesIds = new String[outdatedProfiles.length];
		for (int i = 0; i < outdatedProfiles.length; ++i) {
			outdatedProfilesIds[i] = outdatedProfiles[i].get_id();
		}
		Iterable<Profile> listOutdatedProfilesInDB = profileRepository.findAll(Arrays.asList(outdatedProfilesIds));
		assertThat(listOutdatedProfilesInDB).isNullOrEmpty();
	}

}
