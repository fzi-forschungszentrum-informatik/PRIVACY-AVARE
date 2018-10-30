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

package de.privacy_avare.couchDBDomain;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;

import de.privacy_avare.domain.Profile;

/**
 * Wird intern zum Abspeichern von CouchDB-Abfragen verwendet und ergänzt die
 * Klasse um die Variable _rev, welche von CouchDB automatisch für jedes
 * Dokument angelegt und verwaltet wird.
 * 
 * @author Lukas Struppek
 * @version 1.0
 * @see de.privacy_avare.domain.Profile
 *
 */
@Document
public class ProfileCouchDB extends Profile {
	/**
	 * Repräsentiert die von CouchDB automatisch verwaltete Revision von Dokumenten.
	 * Sie wird u.a. dazu verwendet, Update- und Änderungsbefehle an CouchDB zu
	 * übermitteln.
	 */
	@Field
	String _rev;

	/**
	 * Default-Konstruktor ohne weitere Anpassungen.
	 */
	public ProfileCouchDB() {
		super();
	}

	/**
	 * Ruft die aktuelle Revision des Profils ab.
	 * 
	 * @return Revision des Dokuments
	 */
	public String get_rev() {
		return _rev;
	}

	/**
	 * Setzt die aktuelle Revision des Profils.
	 * 
	 * @param _rev Zu setzende Revisionsnummer.
	 */
	public void set_rev(String _rev) {
		this._rev = _rev;
	}

	/**
	 * Dient zur Überschreibung der Eigenschaften, welche ein Objekt von der Klasse
	 * Profile geerbt hat (id, lastProfileChange, lastProfileContact, preferences).
	 * Die Methode wird hauptsächlich verwendet, um Dokumente aus einer CouchDB zu
	 * aktualisieren bzw. zu überschreiben.
	 * 
	 * @param profile
	 *            Zu setzende Eigenschaften
	 */
	public void setDetails(Profile profile) {
		this._id = profile.get_id();
		this.lastProfileChange = profile.getLastProfileChange();
		this.lastProfileContact = profile.getLastProfileContact();
		this.preferences = profile.getPreferences();
	}

}
