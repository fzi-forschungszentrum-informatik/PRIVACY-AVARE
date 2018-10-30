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

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;

/**
 * Klasse dient zur Speicherung von Abfragen aller Profile aus einer
 * CouchDB-Datenbank. Sie findet hierzu Verwendung in den Repository-Methoden
 * findAll() und count(). Weitere Verwendung im Programmkontext sind nicht
 * geplant.
 * 
 * @author Lukas Struppek
 * @version 1.0
 *
 */

@Document
public class AllProfiles {
	/**
	 * Entspricht einer Liste alle in der CouchDB-Datenbank enthaltenen Dokumenten,
	 * repräsentiert durch deren jeweilige _id.
	 */
	@Field
	private List<Row> rows;

	/**
	 * Die Anzahl an verschiedenen Dokumenten, welche in der CouchDB-Datenbank
	 * aktuell gespeichert sind.
	 */
	@Field
	private int total_rows;

	/**
	 * default-Konstruktor ohne erweiterte Funktionalität.
	 */
	public AllProfiles() {

	}

	/**
	 * Konstruktor, welcher zum setzen der Eigenschaften rows und total_rows genutzt
	 * werden kann.
	 * 
	 * @param rows
	 *            Liste mit ProfileIds, repräsentiert als Row-Objekt.
	 * @param total_rows
	 *            Gesamtzahl an Dokumenten in der Datenbank.
	 */
	public AllProfiles(List<Row> rows, int total_rows) {
		super();
		this.rows = rows;
		this.total_rows = total_rows;
	}

	/**
	 * Ruft die Liste mit allen Row-Objekten, welche die IDs enthalten, ab.
	 * 
	 * @return Liste mit Row-Objekten.
	 */
	public List<Row> getRows() {
		return rows;
	}

	/**
	 * Setzt die Liste mit Row-Objekten, welche die einzelnen IDs enthalten.
	 * 
	 * @param rows
	 *            Liste mit Row-Objekten.
	 */

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	/**
	 * Ruft die Anzahl an Dokumenten in der DB ab.
	 * 
	 * @return Anzahl an Dokumenten.
	 */
	public int getTotal_rows() {
		return total_rows;
	}

	/**
	 * Setzt die Anzahl aller Profile in der DB.
	 * 
	 * @param total_rows
	 *            Der zu setzende Änderungszeitpunkt.
	 */

	public void setTotal_rows(int total_rows) {
		this.total_rows = total_rows;
	}

	/**
	 * Liefert eine Liste mit den IDs aller Dokumente aus der DB zurück, wobei diese
	 * sich bereits im String-Format befinden, was die Weiterverarbeitung
	 * erleichtert.
	 * 
	 * @return Liste mit allen IDs im Stringformat.
	 */
	public List<String> getAllIds() {
		List<String> list = new ArrayList<String>();
		for (Row row : this.rows) {
			list.add(row.getId());
		}
		return list;
	}

}
