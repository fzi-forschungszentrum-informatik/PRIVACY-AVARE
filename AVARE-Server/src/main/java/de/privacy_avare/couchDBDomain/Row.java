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

/**
 * Klasse dient als Bestandteil der Domain AllProfiles. Sie existiert als
 * separate Klasse, da eine automatische JSON-Konvertierung nicht mit internen
 * bzw. anonymen Klassen kompatibel ist.
 * 
 * Rows repräsentieren bei der Abfrage aller Dokumente aus einer
 * CouchDB-Datenbank die einzelnen Dokumente bzw. deren Id.
 * 
 * @author Lukas Struppek
 * @version 1.0
 */
public class Row {
	/**
	 * Repräsentiert die _id eines Dokuments aus einer CouchDB-Datenbank..
	 */
	private String id;

	/**
	 * default-Konstruktor ohne zusätzliche Funktionalität.
	 */
	public Row() {

	}

	/**
	 * Ruft die Eigenschaft _id des Profils ab.
	 * 
	 * @return Die ProfileID.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setzt die _id des Profils.
	 * 
	 * @param id
	 *            Die zu setzende _id.
	 */
	public void setId(String id) {
		this.id = id;
	}
}