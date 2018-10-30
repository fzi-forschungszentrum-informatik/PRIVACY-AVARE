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

package de.privacy_avare.frontController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.privacy_avare.domain.Profile;
import de.privacy_avare.dto.ErrorInformation;
import de.privacy_avare.exeption.MalformedProfileIdException;
import de.privacy_avare.exeption.ProfileAlreadyExistsException;
import de.privacy_avare.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * Die Klasse stellt eine REST-API zur Verfügung, über welche externe Anfragen
 * zur Erzeugung neuer Profile gestellt werden können. Zur Verarbeitung der
 * Anfragen werden diese an entsprechende interne Services weitergeleitet.
 * 
 * Die Antworten auf REST-Anfragen werden stets in Form von
 * ResponseEntity-Objekten zurückgeliefert, welche neben dem eigentlichen Inhalt
 * verschiedene, zusätzliche Informationen bereitstellen.
 * 
 * Eine REST-Dokumentation wird über Swagger bereitgestellt.
 * 
 * @author Lukas Struppek
 * @version 1.0
 * @see de.privacy_avare.service.ProfileService
 * @see org.springframework.http.ResponseEntity
 */

@RestController("newProfileControllerV1")
@RequestMapping(value = "/v1/newProfiles")
@Api(tags = "Neue Profile")
public class NewProfileController {

	/**
	 * Service stellt diverse Methoden zur Verarbeitung von Profilen sowie der
	 * Ablage in der Datenbank bzw. dem Abruf von Profilen aus der Datenbank bereit.
	 * Instanz wird über Dependency Injection bereitgestellt.
	 * 
	 * @see de.privacy_avare.service.ProfileService
	 */
	@Autowired
	private ProfileService profileService;

	/**
	 * Default-Konstruktor ohne erweiterte Funktionalität.
	 */
	public NewProfileController() {
	}

	/**
	 * Erzeugung eines neuen Profils inkl. Generierung einer neuen UserID. Das
	 * erzeugte Profil wird automatisch in der Datenbank mit default-Werten
	 * hinterlegt. Entspricht UC1 ohne Parameter.
	 * 
	 * @return ResponseEntity, welche im Body die ProfileId des generierten Profils
	 *         enthält.
	 * 
	 * @throws ProfileAlreadyExistsException
	 *             Generierte ProfileId bereits vergeben.
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiOperation(value = "Generiert ein neues Profil", notes = "Es wird ein neues Profil mit einer neuen ProfileId generiert und in der Datenbank abgelegt. "
			+ "Der Wert von lastProfileChange wird auf den default-Wert gesetzt. Der Wert von lastProfileContact wird auf den Zeitpunkt des Aufrufes gesetzt. "
			+ "Preferences sind als leerer String gesetzt. "
			+ "\n \n Die neu generierte ProfileId wird im Response Body zurückgeliefert.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Profil erfolgreich erzeugt und abgespeichert", response = String.class),
			@ApiResponse(code = 409, message = "Generierte ProfileId bereits vergeben \n \n Geworfene Exception: \n de.privacy_avare.exeption.ProfileAlreadyExistsException", response = ErrorInformation.class) })
	public ResponseEntity<String> createProfile() throws ProfileAlreadyExistsException {
		Profile serverProfile = profileService.createNewProfile();
		ResponseEntity<String> response = new ResponseEntity<String>(serverProfile.get_id(), HttpStatus.CREATED);
		return response;
	}

	/**
	 * Erzeugung eines neuen Profils basierend auf gegebener ProfileId. Das erzeugte
	 * Profil wird automatisch in der Datenbank mit default-Werten hinterlegt.
	 * Entspricht UC1 mit vorhandener ProfileId.
	 * 
	 * @param id
	 *            ProfileId, mit welcher ein neues Profil erzeugt werden soll.
	 * @return ResponseEntity, welche im Body die ProfileId des generierten Profils
	 *         enthält.
	 * @throws ProfileAlreadyExistsException
	 *             Übergebene ProfileId bereits vergeben.
	 * @throws MalformedProfileIdException
	 *             Übergebene ProfileId entspricht nicht dem Aufbau einer gültigen
	 *             ProfileID.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@ApiOperation(value = "Generiert ein neues Profil basierend auf vorhandener Id", notes = "Es wird ein neues Profil mit der im Pfad definierten ProfileId erzeugt und in der Datenbank abgelegt. "
			+ "Der Wert von lastProfileChange wird auf den default-Wert gesetzt. Der Wert von lastProfileContact wird auf den Zeitpunkt des Aufrufes gesetzt. "
			+ "Preferences sind als leerer String gesetzt. "
			+ "\n \n Die ProfileId wird im Response Body zurückgeliefert.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Profil erfolgreich erzeugt und abgespeichert", response = String.class),
			@ApiResponse(code = 409, message = "ProfileId bereits vergeben \n \n Geworfene Exception: \n de.privacy_avare.exeption.ProfileAlreadyExistsException", response = ErrorInformation.class),
			@ApiResponse(code = 422, message = "Übergebene Id besitzt ungültiges Format \n \n Geworfene Exception: \n de.privacy_avare.exeption.MalformedProfileIdException", response = ErrorInformation.class) })
	public ResponseEntity<String> createProfile(
			@ApiParam(value = "ProfileId", required = true) @PathVariable("id") String id)
			throws ProfileAlreadyExistsException, MalformedProfileIdException {
		Profile serverProfile = profileService.createNewProfile(id);
		ResponseEntity<String> response = new ResponseEntity<String>(serverProfile.get_id(), HttpStatus.CREATED);
		return response;
	}
}
