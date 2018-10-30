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

package de.privacy_avare.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.privacy_avare.service.ClearanceService;

/**
 * Klasse enthält zeitgesteuerte Aufgaben. Zur Aktivierung der Zeitsteuerung ist
 * die Annotation @EnableScheduling in der main-Methode des Programms zu setzen.
 * Muster für Scheduling: Sekunde, Minute, Stunde, Tag, Monat, Wochentag.
 * Trennung der einzelnen Parameter durch Leerzeichen. Detailliertere
 * Informationen zur Festlegung von zeitgesteuerten Methodenaufrufen sind der
 * CronSequenceGenerator-API zu entnehmen.
 * 
 * 
 * @author Lukas Struppek
 * @version 1.0
 * @see de.privacy_avare.SyncServerMain
 * @see <a href=
 *      "https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html">CronSequenceGenerator
 *      </a>
 */

@Component
public class ScheduledTasks {

	/**
	 * Instanz des Services, welcher die Funktionalität der Aufräumarbeiten des
	 * Servers übernimmt.
	 */
	@Autowired
	private ClearanceService clearanceService;

	/**
	 * Löschen deaktivierte Profile aus der Datenbank. Aufruf jeden Montag, 03:00:00
	 * Uhr. Es werden alle Profile in der Datenbank gesucht und gelöscht, deren
	 * lastProfileContactTimestamp weiter als der in den application.properties
	 * festgelegte Anzahl an Monate in der Vergangenheit liegen.
	 * 
	 * Eine Überprüfung der Profile auf unSync erfolgt nicht. Diese Profile werden
	 * ebenso nach der festgelegten Anzahl an Monaten ohne Kontakt gelöscht.
	 */
	@Scheduled(cron = "0 0 3 * * MON", zone = "Europe/Berlin")
	public void cleanDataBase() {
		clearanceService.cleanDatabase();
	}
}
