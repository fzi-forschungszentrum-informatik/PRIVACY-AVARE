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

package de.privacy_avare.errorHandler;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.mapping.model.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import de.privacy_avare.dto.ErrorInformation;
import de.privacy_avare.exeption.ClientPreferencesOutdatedException;
import de.privacy_avare.exeption.MalformedProfileIdException;
import de.privacy_avare.exeption.NoProfilesInDatabaseException;
import de.privacy_avare.exeption.ProfileAlreadyExistsException;
import de.privacy_avare.exeption.ProfileNotFoundException;
import de.privacy_avare.exeption.ProfileSetOnDeletionException;
import de.privacy_avare.exeption.ServerPreferencesOutdatedException;

/**
 * Klasse fängt Programmweit auftretende Exception ab und liefert an den
 * aufrufenden Dienst eine ResponseEntity mit detaillierten Fehlerinformationen
 * zurück.
 * 
 * @author Lukas Struppek
 * @version 1.0
 * @see ResponseEntity
 * @see ErrorInformation
 */

@ControllerAdvice
public class ExeptionHandlingController {

	/**
	 * Kümmert sich um das abfangen von ClientProfileOutdatedException.
	 * 
	 * @param cpoe
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see ClientPreferencesOutdatedException
	 */
	@ExceptionHandler(value = ClientPreferencesOutdatedException.class)
	public ResponseEntity<ErrorInformation> handleClientPreferencesOutdatedException(
			ClientPreferencesOutdatedException cpoe, HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Client Profil veraltet");
		errorInformation.setException(cpoe.getClass().getName());
		errorInformation.setStatus(HttpStatus.CONFLICT.value());
		errorInformation.setDetail(cpoe.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.CONFLICT);
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von HttpClientErrorException.
	 * 
	 * @param hcee
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see <a href =
	 *      "https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/HttpClientErrorException.html">HttpClientErrorException</a>
	 */
	@ExceptionHandler(value = HttpClientErrorException.class)
	public ResponseEntity<ErrorInformation> handleHttpClientErrorException(HttpClientErrorException hcee,
			HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Fehler bei HTTP-Aufruf");
		errorInformation.setException(hcee.getClass().getName());
		errorInformation.setStatus(hcee.getRawStatusCode());
		errorInformation.setDetail(hcee.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				hcee.getStatusCode());
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von HttpMessageNotReadableException.
	 * 
	 * @param hmnre
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see <a href =
	 *      "https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/http/converter/HttpMessageNotReadableException.html">HttpMessageNotReadableException</a>
	 */
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorInformation> handleHttpMessageNotReadableException(HttpMessageNotReadableException hmnre,
			HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("HTTP-Methode nicht ausführbar");
		errorInformation.setException(hmnre.getClass().getName());
		errorInformation.setStatus(HttpStatus.BAD_REQUEST.value());
		errorInformation.setDetail(hmnre.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.BAD_REQUEST);
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von HttpRequestMethodNotSupportedException.
	 * 
	 * @param htmnse
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see <a href =
	 *      "https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/HttpRequestMethodNotSupportedException.html">HttpRequestMethodNotSupportedException</a>
	 */
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorInformation> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException htmnse, HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("HTTP-Methode nicht unterstützt");
		errorInformation.setException(htmnse.getClass().getName());
		errorInformation.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
		errorInformation.setDetail(htmnse.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.METHOD_NOT_ALLOWED);
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von MalformedProfileIdException.
	 * 
	 * @param mpide
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see MalformedProfileIdException
	 */
	@ExceptionHandler(value = MalformedProfileIdException.class)
	public ResponseEntity<ErrorInformation> handleMalformedProfileIdException(MalformedProfileIdException mpide,
			HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Ungültige ProfilId");
		errorInformation.setException(mpide.getClass().getName());
		errorInformation.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		errorInformation.setDetail(mpide.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.UNPROCESSABLE_ENTITY);
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von MappingException.
	 * 
	 * @param me
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see <a href =
	 *      "https://docs.spring.io/spring-data/commons/docs/1.10.0.M1/api/org/springframework/data/mapping/model/MappingException.html">MappingException</a>
	 */
	@ExceptionHandler(value = MappingException.class)
	public ResponseEntity<ErrorInformation> handleMappingException(MappingException me, HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Daten entsprechen nicht denen der Profile-Klasse -> Konvertierungsfehler");
		errorInformation.setException(me.getClass().getName());
		errorInformation.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorInformation.setDetail(me.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von MethodArgumentTypeMismatchException.
	 * 
	 * @param matme
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see <a href =
	 *      "https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/messaging/handler/annotation/support/MethodArgumentTypeMismatchException.html">MethodArgumentTypeMismatchException</a>
	 */
	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorInformation> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException matme, HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Falscher Datentyp im Parameter");
		errorInformation.setException(matme.getClass().getName());
		errorInformation.setStatus(HttpStatus.BAD_REQUEST.value());
		errorInformation.setDetail(matme.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.BAD_REQUEST);
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von NoProfilesInDatabaseException.
	 * 
	 * @param npide
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see NoProfilesInDatabaseException
	 */
	@ExceptionHandler(value = NoProfilesInDatabaseException.class)
	public ResponseEntity<ErrorInformation> handleNoProfilesInDatabaseException(NoProfilesInDatabaseException npide,
			HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Keine Profile in DB vorhanden.");
		errorInformation.setException(npide.getClass().getName());
		errorInformation.setStatus(HttpStatus.NOT_FOUND.value());
		errorInformation.setDetail(npide.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.NOT_FOUND);
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von ProfileAlreadyExistsException.
	 * 
	 * @param paee
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see ProfileAlreadyExistsException
	 */
	@ExceptionHandler(value = ProfileAlreadyExistsException.class)
	public ResponseEntity<ErrorInformation> handleProfileAlreadyExistsException(ProfileAlreadyExistsException paee,
			HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Profil bereits vorhanden");
		errorInformation.setException(paee.getClass().getName());
		errorInformation.setStatus(HttpStatus.CONFLICT.value());
		errorInformation.setDetail(paee.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.CONFLICT);
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von ProfileNotFoundExceptions.
	 * 
	 * @param pnfe
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see ProfileNotFoundException
	 */
	@ExceptionHandler(value = ProfileNotFoundException.class)
	public ResponseEntity<ErrorInformation> handleProfileNotFoundException(ProfileNotFoundException pnfe,
			HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Profil nicht gefunden");
		errorInformation.setException(pnfe.getClass().getName());
		errorInformation.setStatus(HttpStatus.NOT_FOUND.value());
		errorInformation.setDetail(pnfe.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.NOT_FOUND);
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von ProfileSetOnDeletionException. In der
	 * aktuellen Programmversion ohne Einsatz.
	 * 
	 * @param psode
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see ProfileSetOnDeletionException
	 */
	@ExceptionHandler(value = ProfileSetOnDeletionException.class)
	public ResponseEntity<ErrorInformation> handleProfileSetOnDeletionException(ProfileSetOnDeletionException psode,
			HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Profil zum Löschen freigegeben");
		errorInformation.setException(psode.getClass().getName());
		errorInformation.setStatus(HttpStatus.GONE.value());
		errorInformation.setDetail(psode.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.GONE);
		return responseEntity;
	}

	/**
	 * Kümmert sich um das abfangen von ServerProfileOutdatedException.
	 * 
	 * @param spoe
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 * @see ServerPreferencesOutdatedException
	 */
	@ExceptionHandler(value = ServerPreferencesOutdatedException.class)
	public ResponseEntity<ErrorInformation> handleServerPreferencesOutdatedException(
			ServerPreferencesOutdatedException spoe, HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Server Profil veraltet");
		errorInformation.setException(spoe.getClass().getName());
		errorInformation.setStatus(HttpStatus.CONFLICT.value());
		errorInformation.setDetail(spoe.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.CONFLICT);
		return responseEntity;
	}
	
	/**
	 * Kümmert sich um das abfangen von allen restlichen Fehlern, die nicht explizit von anderen Exception Handlers abgefangen werden.
	 * 
	 * @param e
	 *            Aufgetretene Exception.
	 * @param request
	 *            Aufgerufene URI.
	 * @return Informationen zum Fehler.
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorInformation> handleDefaultException(
			Exception e, HttpServletRequest request) {
		ErrorInformation errorInformation = new ErrorInformation();
		errorInformation.setTitle("Sonstige Fehler");
		errorInformation.setException(e.getClass().getName());
		errorInformation.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorInformation.setDetail(e.getMessage());
		errorInformation.setRequestedURI(request.getRequestURI());
		errorInformation.setTimestamp(new Date());
		errorInformation.setAdditionalInformation("Aufgetretener Fehler wird nicht separat von einem Exception Handler behandelt.");
		ResponseEntity<ErrorInformation> responseEntity = new ResponseEntity<ErrorInformation>(errorInformation, null,
				HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}
}
