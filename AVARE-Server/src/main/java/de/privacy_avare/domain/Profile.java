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

package de.privacy_avare.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

/**
 * Die Klasse repräsentiert die Profildaten in der Datenbank. Diese beinhalten
 * die Eigenschaften _id, lastProfileChange, lastProfileContact und preferences.
 * Sie ist grundsätzlich mit verschiedenen Datenbanksystemen kompatibel. Für das
 * Abspeichern von Abfragen aus einer CouchDB-Datenbank wird eine Instanz von
 * ProfileCouchDB verwendet, welche eine Unterklasse von Profile darstellt.
 * 
 * @author Lukas Struppek
 * @version 1.0
 */

@Document
public class Profile {
	/**
	 * Entspricht der ProfileID, mit welcher ein Profil eindeutig identifiziert
	 * werden kann. Wird auch zur Identifikation der Dokumente innerhalb der
	 * Datenbank verwendet.
	 */
	@Id
	@Field
	protected
	String _id;

	/**
	 * Beinhaltet den Zeitpunkt, zu welchem das Profil zuletzt geändert wurde. Eine
	 * Änderung besteht bei Erzeugung des Profils bzw. der Anpassung der
	 * Nutzerpräferenzen.
	 */
	@Field
	protected
	Date lastProfileChange;

	/**
	 * Beinhaltet den Zeitpunkt, zu welchem das Profil zuletzt in der Datenbank
	 * abgerufen bzw. bearbeitet wurde. Hierzu zählen u.a. die Operationen Lesen,
	 * Schreiben und Erzeugen eines Profils.
	 */
	@Field
	protected
	Date lastProfileContact;

	/**
	 * Repräsentiert die Nutzerpräferenzen.
	 */
	@Field
	protected
	String preferences;

	/**
	 * Default-Konstruktor, welcher die Eigenschaften der Klasse auf default-Werte
	 * setzt. Der Zeitpunkt lastProfileChange wird auf den 1. Jan. 1970 gesetzt. Der
	 * Zeitpunkt lastProfileContact auf den Zeitpunkt des Aufrufs. Die Preferences
	 * werden als leerer String gesetzt. Die ProfileId wird noch nicht gesetzt. Um
	 * Fehler zu vermeiden, sollte stets versucht werden, bei der Erzeugung eines
	 * neuen Profils direkt über den Konstruktor eine ProfileId festzulegen.
	 * 
	 * Der Konstruktor muss außerdem für die automatische Konvertierung zwischen
	 * JSON und POJOs vorhanden sein.
	 */
	public Profile() {
		// Setze lastProfileChange auf 1. Jan. 1970
		this.lastProfileChange = new Date(0L);
		// Setze lastProfileContact auf aktuellen Zeitpunkt
		this.lastProfileContact = GregorianCalendar.getInstance(Locale.GERMANY).getTime();
		this.preferences = "";
	}

	/**
	 * Erzeugt ein neues Profile-Objekt mit Id. Setzt lastProfileChange auf den 1.
	 * Jan. 1970 und lastProfileContact auf den aktuellen Zeitpunkt. Restliche Werte
	 * werden mit Default-Werten belegt. Der Konstruktor ist dem default-Konstruktor
	 * stets vorzuziehen, um Fehlerquellen bezüglich einer fehlender bzw. falscher
	 * ProfileId zu vermeiden.
	 * 
	 * @param id
	 *            ProfileID, mit welcher ein neues Profil erzeugt werden soll.
	 */
	public Profile(String id) {
		this._id = id;
		// Setze lastProfileChange auf 1. Jan. 1970
		this.lastProfileChange = new Date(0L);
		// Setze lastProfileContact auf aktuellen Zeitpunkt
		this.lastProfileContact = GregorianCalendar.getInstance(Locale.GERMANY).getTime();
		this.preferences = "";
	}

	/**
	 * Erzeugt ein neues Profile-Objekt und setzt die Instanzvariablen entsprechend
	 * den übergebenen Parametern.
	 * 
	 * @param id
	 *            Die zu setzende ProfileId
	 * @param lastProfileChange
	 *            Der zu setzende Änderungszeitpunkt.
	 * @param lastProfileContact
	 *            Der zu setzende Kontaktzeitpunkt.
	 * @param preferences
	 *            Die zu setzenden Preferences.
	 */
	public Profile(String id, Date lastProfileChange, Date lastProfileContact, String preferences) {
		this._id = id;
		this.lastProfileChange = lastProfileChange;
		this.lastProfileContact = lastProfileContact;
		this.preferences = preferences;
	}

	/**
	 * Ruft die ProfileID des Profils ab.
	 * 
	 * @return Die ProfileID.
	 */
	public String get_id() {
		return _id;
	}

	/**
	 * Setzt die ProfileID im Profil.
	 * 
	 * @param id
	 *            Die zu setzende ProfileID.
	 */
	public void set_id(String id) {
		this._id = id;
	}

	/**
	 * Ruft den letzten Änderungszeitpunkt des Profils ab.
	 * 
	 * @return Der letzte Änderungszeitpunkt.
	 */
	public Date getLastProfileChange() {
		return lastProfileChange;
	}

	/**
	 * Setzt den letzten Änderungszeitpunkt im Profil.
	 * 
	 * @param lastProfileChange
	 *            Der zu setzende Änderungszeitpunkt.
	 */
	public void setLastProfileChange(Date lastProfileChange) {
		this.lastProfileChange = lastProfileChange;
	}

	/**
	 * Ruft den letzten Kontaktzeitpunkt des Profils ab.
	 * 
	 * @return Der letzte Kontaktzeitpunkt.
	 */
	public Date getLastProfileContact() {
		return lastProfileContact;
	}

	/**
	 * Setzt den letzten Kontaktzeitpunkt im Profil.
	 * 
	 * @param lastProfileContact
	 *            Der zu setzende Kontaktzeitpunkt.
	 */
	public void setLastProfileContact(Date lastProfileContact) {
		this.lastProfileContact = lastProfileContact;
	}

	/**
	 * Ruft die Preferences des Profils ab.
	 * 
	 * @return Die Profileinstellungen
	 */
	public String getPreferences() {
		return preferences;
	}

	/**
	 * Setzt die Preferences im Profil.
	 * 
	 * @param preferences
	 *            Die zu setzende Preferences.
	 */
	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	/**
	 * Liefert eine Repräsentation des aktuellen Zustand des Objekts zurück als
	 * String zurück. Preferences sind im String nicht enthalten.
	 * 
	 * @return Aktueller Zustand des Objekts
	 */
	@Override
	public String toString() {
		GregorianCalendar localLastProfileChange = new GregorianCalendar();
		localLastProfileChange.setTime(this.lastProfileChange);
		GregorianCalendar localLastProfileContact = new GregorianCalendar();
		localLastProfileContact.setTime(this.lastProfileContact);

		String strLastProfileChange = "[" + localLastProfileChange.get(Calendar.DATE) + "."
				+ localLastProfileChange.get(Calendar.MONTH) + "." + localLastProfileChange.get(Calendar.YEAR) + " "
				+ localLastProfileChange.get(Calendar.HOUR_OF_DAY) + ":" + localLastProfileChange.get(Calendar.MINUTE)
				+ ":" + localLastProfileChange.get(Calendar.SECOND) + "]";
		String strLastProfileContact = "[" + localLastProfileContact.get(Calendar.DATE) + "."
				+ localLastProfileContact.get(Calendar.MONTH) + "." + localLastProfileContact.get(Calendar.YEAR) + " "
				+ localLastProfileContact.get(Calendar.HOUR_OF_DAY) + ":" + localLastProfileContact.get(Calendar.MINUTE)
				+ ":" + localLastProfileContact.get(Calendar.SECOND) + "]";

		String result = "{" + "id: " + this._id + ", lastProfileChange: " + strLastProfileChange
				+ ", lastProfileContact: " + strLastProfileContact + "}";
		return result;
	}

	/**
	 * Generiert eine HashMap mit allen Eigenschaften und ihren entsprechenden
	 * Zuständen eines Profile-Objekts zurück.
	 * 
	 * @return Generierte HashMap mit allen aktuellen Zuständen des Objekts.
	 */
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", this._id);
		map.put("lastProfileChange", this.lastProfileChange);
		map.put("lastProfileContact", this.lastProfileContact);
		map.put("preferences", this.preferences);

		return map;
	}
}
