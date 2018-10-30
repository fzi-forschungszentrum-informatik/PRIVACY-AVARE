package de.privacy_avare.config;

import java.util.Properties;

/**
 * Klasse beinhaltet default-Einstellungen für die application.properties. Diese
 * werden geladen, falls es nicht möglich ist, aus dem entsprechenden
 * application.properties-File zu lesen.
 * 
 * @author Lukas Struppek
 * @version 1.0
 */
public class DefaultProperties extends Properties {
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor wird zur Festlegung der default-Werte genutzt.
	 */
	public DefaultProperties() {
		// Festlegung der Werte für Admin-Zugang
		this.setProperty("admin.username", "admin");
		this.setProperty("admin.password", "password");
		this.setProperty("management.port", "8443");

		// Festlegung der Werte für CouchDB-Verbindung
		this.setProperty("couchdb.adress", "http://localhost");
		this.setProperty("couchdb.port", "5984");
		this.setProperty("couchdb.databaseName", "profiles");

		// Festlegung der Werte für Zeitvergleiche
		this.setProperty("server.minTimeDifference", "5");
		this.setProperty("server.monthsBeforeDeletion", "18");
	}
}
