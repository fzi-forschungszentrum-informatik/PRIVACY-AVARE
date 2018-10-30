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

package de.privacy_avare.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Klasse dient der problembasierten Darstellung von auftretetenden Exceptions.
 * Der Aufbau der Klasse orientierte sich dabei an der Definition von HTTP
 * Problem Details der UC Berkeley:
 * 
 * 
 * @author Lukas Struppek
 * @version 1.0 * @see <a href=
 *          "https://tools.ietf.org/html/draft-nottingham-http-problem-07">Problem
 *          Details for HTTP APIs draft-nottingham-http-problem-07 </a>
 *
 */
@ApiModel(value = "ErrorInformation Model", description = "Model für ErrorInformation")
public class ErrorInformation {
	/**
	 * Titel der Fehlermeldung.
	 */
	@ApiModelProperty(value = "Titel der Fehlermeldung", required = true, position = 0, example = "Profil nicht gefunden")
	private String title;

	/**
	 * Art des Fehlers.
	 */
	@ApiModelProperty(value = "Die geworfene Exception", required = true, position = 1, example = "de.privacy_avare.exeption.ProfileNotFoundException")
	private String exception;

	/**
	 * HTTP Statuscode
	 */
	@ApiModelProperty(value = "Http Status Code", required = true, position = 2, example = "404")
	private int status;

	/**
	 * Beschreibung der Art des Fehlers. In der Regel die Nachricht der Exception.
	 */
	@ApiModelProperty(value = "Nachricht der Exception", required = true, position = 3, example= "Kein Profil mit entsprechender ID gefunden")
	private String detail;

	/**
	 * Aufruf des Clients, welcher den Fehler ausgelöst hast.
	 */
	@ApiModelProperty(value = "Aufgerufene URI, die den Fehler ausgelöst hat", required = true, position = 4, example = "/v1/profiles/3764p2481xfbi76")
	private String requestedURI;

	/**
	 * Zeitpunkt, zu welchem der Fehler aufgetreten ist. Format orientiert sich am
	 * Übertragungsformat für Zeitpunkte, wie es auch in anderen Klassen der
	 * Anwendung genutzt wird.
	 */
	@ApiModelProperty(value = "Zeitpunkt des Fehlerauftritts", required = true, position = 5, example = "2017-09-22T14-19-25-071")
	private String timestamp;

	/**
	 * Bietet die Möglichkeit, zusätzliche Informationen zu liefern. Standard ist
	 * ein leerer String.
	 */
	@ApiModelProperty(value = "Zusätzliche Informationen, falls verfügbar", required = false, position = 6, example = "")
	private String additionalInformation;

	/**
	 * Dient der Konvertierung von Zeitpunkten in Strings.
	 */
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss-SSS");

	/**
	 * Default-Konstruktor
	 */
	public ErrorInformation() {

	}

	/**
	 * Konstruktor, welcher alle Eigenschaften bereits mit Werten versieht.
	 * 
	 * @param title
	 *            Titel der Fehlermeldung.
	 * @param exception
	 *            Art des Fehlers.
	 * @param status
	 *            HTTP Statuscode
	 * @param detail
	 *            Detailliertere Fehlerbeschreibung
	 * @param requestedURI
	 *            URI-Aufruf, welcher die Fehlermeldung ausgelöst hast.
	 * @param timestamp
	 *            Zeitpunkt des Fehlerauftretens.
	 */
	public ErrorInformation(String title, String exception, int status, String detail, String requestedURI,
			String timestamp) {
		super();
		this.title = title;
		this.exception = exception;
		this.status = status;
		this.detail = detail;
		this.requestedURI = requestedURI;
		this.timestamp = timestamp;
	}

	/**
	 * Getter für den Titel der Fehlermeldung.
	 * 
	 * @return Titel der Fehlermeldung.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter für den Titel der Fehlermeldung.
	 * 
	 * @param title
	 *            Titel der Fehlermeldung.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter für die Art der Fehlermeldung.
	 * 
	 * @return Art der Fehlermeldung
	 */
	public String getException() {
		return exception;
	}

	/**
	 * Setter für die Art der Fehlermeldung.
	 * 
	 * @param exception
	 *            Art der Fehlermeldung.
	 */
	public void setException(String exception) {
		this.exception = exception;
	}

	/**
	 * Getter für den Statuscode.
	 * 
	 * @return HTTP-Statuscode.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Setter für den HTTP-Statuscode.
	 * 
	 * @param status
	 *            HTTP-Statuscode.
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Getter für die Fehlerdetails.
	 * 
	 * @return Fehlerdetails
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * Setter für die Fehlerdetails.
	 * 
	 * @param detail
	 *            Fehlerdetails.
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * Getter für die aufgerufene URI.
	 * 
	 * @return Aufgerufene URI
	 */
	public String getRequestedURI() {
		return requestedURI;
	}

	/**
	 * Setter für die aufgerufene URI.
	 * 
	 * @param requestedURI
	 *            Aufgerufene URI.
	 */
	public void setRequestedURI(String requestedURI) {
		this.requestedURI = requestedURI;
	}

	/**
	 * Getter für den Zeitpunkt des Fehlers.
	 * 
	 * @return Zeitpunkt des Fehlers
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Setter für die Zeitpunkt des Fehlers im Stringformat.
	 * 
	 * @param timestamp
	 *            Zeitpunkt des Fehlers.
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Setter für die Zeitpunkt des Fehlers als Date-Objekt. Automatische
	 * Konvertierung in entsprechende String-Repräsentation.
	 * 
	 * @param d
	 *            Zeitpunkt des Fehlers.
	 */
	public void setTimestamp(Date d) {
		this.timestamp = this.format.format(d);
	}

	/**
	 * Getter für zusätzliche Informationen.
	 * 
	 * @return Zusätzliche Informationen.
	 */
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * Setter für zusätzliche Informationen.
	 * 
	 * @param additionalInformation Zusätzliche Informationen.
	 */
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

}
