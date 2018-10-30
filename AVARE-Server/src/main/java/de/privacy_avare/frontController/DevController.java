package de.privacy_avare.frontController;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import de.privacy_avare.domain.Profile;
import de.privacy_avare.dto.ErrorInformation;
import de.privacy_avare.exeption.NoProfilesInDatabaseException;
import de.privacy_avare.exeption.ProfileAlreadyExistsException;
import de.privacy_avare.exeption.ProfileNotFoundException;
import de.privacy_avare.repository.ProfileRepositoryCouchDBImpl;
import de.privacy_avare.service.ClearanceService;
import de.privacy_avare.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Die Klasse stellt eine REST-API zur Erleichterung der Entwicklung zur
 * Verfügung, über welche externe Anfragen bezüglich bereits existierender
 * Profile gestellt werden können. Zur Verarbeitung der Anfragen werden diese an
 * entsprechende Services weitergeleitet. Die Schnittstelle ist ausdrücklich
 * nicht für die Verwendung in Endprodukten gedacht, sondern dient lediglich zu
 * Testzwecken währen der Entwicklung.
 * 
 * Daher ist die Schnittstelle mit BasicAuth gesichert und kann nur von Anwender
 * mit Admin-Rechten verwendet werden.
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
@RestController("DevControllerV1")
@RequestMapping(value = "/v1/dev")
@Api(tags = "Entwickler Funktionen")
public class DevController {
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
	 * Service stellt Verbindung zur Datenbank bereit und wird dazu genutzt, Profile
	 * ohne Überprüfung in der Datenbank zu speichern.
	 */
	@Autowired
	private ProfileRepositoryCouchDBImpl profileRepository;

	/**
	 * Service zum Aufräumen veralteter Profile in der Datenbank.
	 */
	@Autowired
	private ClearanceService clearanceService;

	/**
	 * Generiert ein Profil mit veraltetem lastProfileContact und der Preference
	 * "Veraltetes Profil". Die Methode wird u.a. dazu verwendet, um den
	 * Aufräumprozess zu testen.
	 * 
	 * @return ResponseEntity, welche im Body die ProfileId des generierten Profils
	 *         enthält.
	 * @throws ProfileAlreadyExistsException
	 *             Generierte ProfileId bereits vergeben.
	 * @throws ResourceAccessException
	 *             Keine Instanz von CouchDB gefunden.
	 */
	@RequestMapping(value = "outdatedProfile", method = RequestMethod.POST)
	@ApiOperation(value = "Generiert ein neues, veraltetes Profil", notes = "Generiert ein neues Profil in der Datenbank mit lastProfileContact = 0 und preferences = 'Veraltetes PRofil'. Wird zum Testen des Aufräumprozesses genutzt", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Profil erfolgreich erzeugt und abgespeichert", response = String.class),
			@ApiResponse(code = 409, message = "Generierte ProfileId bereits vergeben \n \n Geworfene Exception: \n de.privacy_avare.exeption.ProfileAlreadyExistsException", response = ErrorInformation.class),
			@ApiResponse(code = 500, message = "Keine Instanz unter der angegebenen Adresse gefunden", response = ErrorInformation.class) })
	public ResponseEntity<String> createProfile() throws ProfileAlreadyExistsException {
		Profile serverProfile = profileService.createNewProfile();
		serverProfile.setLastProfileContact(new Date(0));
		serverProfile.setPreferences("Veraltetes Profil");
		profileRepository.save(serverProfile);
		ResponseEntity<String> response = new ResponseEntity<String>(serverProfile.get_id(), HttpStatus.CREATED);
		return response;
	}

	/**
	 * Sucht alle Profile in der Datenbank und liefert diese in Form einer Liste
	 * zurück, unabhängig vom Status der einzelnen Profile. Werden in der DB keine
	 * Profile gefunden, so wird eine ProfileNotFoundException zurückgeliefert.
	 * 
	 * @return Liste mit allen in der DB enthaltenen Profilen.
	 * @throws NoProfilesInDatabaseException
	 *             Keine Profile in DB vorhanden.
	 * @throws ResourceAccessException
	 *             Keine Instanz von CouchDB gefunden.
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ApiOperation(value = "Liest alle Profile aus DB", notes = "Sucht in der DB nach allen vorhandenen Profilen. Vorhandene Profile werden in einer Menge innerhalb eines JSON-Dokuments zurückgeliefert. "
			+ "Die Methode dient hauptsächlich zu Testzwecken in der Entwicklung. Je nach Anzahl der Profile in der DB kann die Bearbeitungsdauer der Anfrage stark variieren. "
			+ "\n \n Zeitstempel lastProfileContact der einzelnen Profile werden aktualisiert.", response = HashMap.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Profile erfolgreich geladen", response = HashMap.class),
			@ApiResponse(code = 404, message = "Datenbank nicht gefunden", response = ErrorInformation.class),
			@ApiResponse(code = 409, message = "Keine Profile in DB gefunden \n \n Geworfene Exception: \n de.privacy_avare.exeption.NoProfilesInDatabaseException", response = ErrorInformation.class),
			@ApiResponse(code = 500, message = "Keine Instanz unter der angegebenen Adresse gefunden", response = ErrorInformation.class) })

	public ResponseEntity<List<HashMap<String, Object>>> getAllProfiles() throws NoProfilesInDatabaseException {
		Iterable<Profile> serverList = profileService.getAllProfiles();
		List<HashMap<String, Object>> responseList = new LinkedList<HashMap<String, Object>>();
		for (Profile p : serverList) {
			responseList.add(p.toHashMap());
		}
		ResponseEntity<List<HashMap<String, Object>>> response = new ResponseEntity<List<HashMap<String, Object>>>(
				responseList, HttpStatus.OK);
		return response;
	}

	/**
	 * Sucht in der Datenbank nach der übergebenen ProfileId und liefert die
	 * Eigenschaft lastProfileContact des gefundenes Profil zurück.
	 * 
	 * In jedem Fall wird der Zeitpunkt lastContact anschließend angepasst.
	 * 
	 * Wird kein Profil mit der übergebenen ProfileId gefunden, so wird eine
	 * ProfileNotFoundException zurückgegeben.
	 * 
	 * @param id
	 *            ProfileId des gesuchten Profils.
	 * @return Eigenschaft lastProfileContact des DB-Profils.
	 * @throws ProfileNotFoundException
	 *             Kein Profil mit entsprechender ID gefunden.
	 * @throws ResourceAccessException
	 *             Keine Instanz von CouchDB gefunden.
	 */
	@RequestMapping(value = "/{id}/lastProfileContact", method = RequestMethod.GET)
	@ApiOperation(value = "Liest lastProfileContact aus DB", notes = "Sucht in der DB nach vorhandenem Profil. Wird ein Profil mit entsprechender ProfileId gefunden, so wird der gespeicherte Zeitpunkt lastProfileContact zurückgeliefert. "
			+ "\n \n ZeitstempellastProfileContact wird <b>nicht aktualisiert</b>.", response = Date.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "lastProfileContact erfolgreich geladen", response = Date.class),
			@ApiResponse(code = 404, message = "Kein Profil mit entsprechender Id gefunden  \n \n Geworfene Exception: \n de.privacy_avare.exeption.ProfileNotFoundException", response = ErrorInformation.class),
			@ApiResponse(code = 500, message = "Keine Instanz unter der angegebenen Adresse gefunden", response = ErrorInformation.class) })
	public ResponseEntity<Date> getLastProfileContact(
			@ApiParam(value = "ProfileId des entsprechenden Profils", required = true) @PathVariable("id") String id) {
		Date serverLastProfileContact = profileService.getLastProfileContact(id);
		ResponseEntity<Date> response = new ResponseEntity<Date>(serverLastProfileContact, HttpStatus.OK);
		return response;
	}

	/**
	 * Sucht in der Datenbank nach der übergebenen ProfileId und liefert die
	 * Eigenschaft lastProfileChange des gefundenes Profil zurück. In jedem Fall
	 * wird der Zeitpunkt lastContact angepasst.
	 * 
	 * Wird kein Profil mit der übergebenen ProfileId gefunden, so wird eine
	 * ProfileNotFoundException zurückgegeben.
	 * 
	 * @param id
	 *            ProfileId des gesuchten Profils.
	 * @return Eigenschaft lastProfileChange des DB-Profils.
	 * @throws ProfileNotFoundException
	 *             Kein Profil mit entsprechender ID gefunden.
	 * @throws ResourceAccessException
	 *             Keine Instanz von CouchDB gefunden.
	 * 
	 */
	@RequestMapping(value = "/{id}/lastProfileChange", method = RequestMethod.GET)
	@ApiOperation(value = "Liest lastProfileChange aus DB", notes = "Sucht in der DB nach vorhandenem Profil. Wird ein Profil mit entsprechender ProfileId gefunden, so wird der gespeicherte Zeitpunkt lastProfileChange zurückgeliefert. "
			+ "\n \n Zeitstempel lastProfileContact wird <b>nicht aktualisiert</b>.", response = Date.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "lastProfileChange erfolgreich geladen", response = Date.class),
			@ApiResponse(code = 404, message = "Kein Profil mit entsprechender Id gefunden  \n \n Geworfene Exception: \n de.privacy_avare.exeption.ProfileNotFoundException", response = ErrorInformation.class),
			@ApiResponse(code = 500, message = "Keine Instanz unter der angegebenen Adresse gefunden", response = ErrorInformation.class) })
	public ResponseEntity<Date> getLastProfileChange(
			@ApiParam(value = "ProfileId des entsprechenden Profils", required = true) @PathVariable("id") String id) {
		Date serverLastProfileChange = profileService.getLastProfileChange(id);
		ResponseEntity<Date> response = new ResponseEntity<Date>(serverLastProfileChange, HttpStatus.OK);
		return response;
	}

	/**
	 * Sucht in der Datenbank nach der übergebenen ProfileId und liefert ein
	 * gefundenes Profil unabhängig von dessen lastProfileChange zurück. In jedem
	 * Fall wird der Zeitpunkt lastProfileContact angepasst.
	 * 
	 * Wird kein Profil mit der übergebenen ProfileId gefunden, so wird eine
	 * ProfileNotFoundException zurückgegeben.
	 * 
	 * 
	 * @param id
	 *            ProfileId des gesuchten Profils.
	 * @return Preferences des Profils in der Datenbank.
	 * @throws ProfileNotFoundException
	 *             Kein Profil mit entsprechender ID gefunden.
	 * @throws ResourceAccessException
	 *             Keine Instanz von CouchDB gefunden.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Liest Preferences aus DB ohne Vergleich der Zeitstempel", notes = "Sucht in der DB nach vorhandenem Profil. "
			+ "Wird ein Profil mit entsprechender ProfileId gefunden, so werden die gespeicherten Preferences <b>ohne Vergleich des lastProfileChange</b> zurückgeliefert. "
			+ "\n \n Zeitstempel lastProfileContact wird aktualisiert.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Preferences erfolgreich geladen", response = String.class),
			@ApiResponse(code = 404, message = "Kein Profil mit entsprechender Id gefunden  \n \n Geworfene Exception: \n de.privacy_avare.exeption.ProfileNotFoundException", response = ErrorInformation.class),
			@ApiResponse(code = 500, message = "Keine Instanz unter der angegebenen Adresse gefunden", response = ErrorInformation.class) })
	public ResponseEntity<String> pullProfilePreferencesIgnoringLastProfileChange(
			@ApiParam(value = "ProfileId des entsprechenden Profils", required = true) @PathVariable("id") String id) {
		Profile serverProfile = profileService.getProfileById(id);
		ResponseEntity<String> response = new ResponseEntity<String>(serverProfile.getPreferences(), HttpStatus.OK);
		return response;
	}

	/**
	 * Beendet das Serverprogramm über System.exit(0).
	 */
	@RequestMapping(value = "/exit", method = RequestMethod.PUT)
	@ApiOperation(value = "Beendet das Serverprogramm", notes = "Beendet das Programm mithilfe eines Aufrufs von System.exit(0). ", response = Void.class)
	public void exit() {
		System.exit(0);
	}

	/**
	 * Ruft die Methode zum Löschen veralteter Profile auf. Die Methode entspricht
	 * dem manuellen Aufruf des Aufräumprozesses, welcher in einem festgelegten
	 * Zeitintervall automatisch durchgeführt wird.
	 * 
	 * @throws ResourceAccessException
	 *             Keine Instanz von CouchDB gefunden.
	 */
	@RequestMapping(value = "/clean", method = RequestMethod.POST)
	@ApiOperation(value = "Löschen aller veralteten Profile in DB", notes = "Entspricht dem Methodenaufruf des automatisierten Löschens. ", response = Void.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Aufräumen erfolgreich gestartet", response = String.class),
			@ApiResponse(code = 500, message = "Keine Instanz unter der angegebenen Adresse gefunden", response = ErrorInformation.class) })
	public void cleanDatabase() {
		clearanceService.cleanDatabase();
	}

	/**
	 * Überprüft, ob eine laufende Instanz von CouchDB unter der angegebenen Adresse
	 * zu finden ist. Als Adresse ist hierzu lediglich die URI ohne HTTP und ohne
	 * Port anzugeben. Es wird lediglich bei einer unverschlüsselten HTTP-Verbindung
	 * und Verwendung des Standard-Ports 5984 ein zuverlässiges Ergebnis geliefert.
	 * 
	 * @param address
	 *            Adresse, unter welcher nach einer CouchDB-Instanz gesucht werden
	 *            soll.
	 * @return JSON-Dokument mit Informationen zum CouchDB-System.
	 * @throws Exception Sonstige auftretende Exceptions.
	 * @throws ResourceAccessException
	 *             Keine Instanz von CouchDB gefunden.
	 */
	@RequestMapping(value = "/couchdb/{address}", method = RequestMethod.GET)
	@ApiOperation(value = "Sucht nach einer Instanz von CouchDB", notes = "Sucht bei der übergebenen Adresse, ob eine laufende CouchDB-Instanz vorhanden ist. "
			+ "Die Adresse wird ohne Angabe von http:// und ohne Port übergeben. Die Methode liefert nur bei Verwendung von http (ohne Verschlüsselung) und dem default-Port 5984 ein zuverlässiges Ergebnis.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "CouchDB Instanz gefunden", response = String.class),
			@ApiResponse(code = 500, message = "Keine Instanz unter der angegebenen Adresse gefunden", response = ErrorInformation.class) })
	public ResponseEntity<String> checkCouchDb(@PathVariable("address") String address) throws Exception {
		System.out.println(address);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		String url = "http://" + address + ":5984";
		response = restTemplate.getForEntity(url, String.class);
		return response;
	}

	/**
	 * Überprüft, ob in einem angebundenen CouchDB-System eine Datenbank mit dem im
	 * Parameter spezifizierten Namen vorhanden ist.
	 * 
	 * @param databaseName
	 *            Name der zu suchenden Datenbank.
	 * @return Boolean, ob Datenbank vorhanden ist.
	 * @throws ResourceAccessException
	 *             Keine Instanz von CouchDB gefunden.
	 */
	@RequestMapping(value = "/database/{databaseName}", method = RequestMethod.GET)
	@ApiOperation(value = "Prüft, ob in CouchDB eine bestimmte Database vorhanden ist", notes = "Es wird überprüft, ob der Datenbankserver eine Database mit dem im Parameter spezifizierten Namen besitzt.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Preferences erfolgreich geladen", response = Boolean.class),
			@ApiResponse(code = 500, message = "Sonstiger Fehler beim Prüfen des Datenbanksystems", response = ErrorInformation.class) })
	public ResponseEntity<Boolean> checkDatabase(@PathVariable("databaseName") String databaseName) {
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(profileRepository.existsDatabase(databaseName),
				HttpStatus.OK);
		return response;
	}

	/**
	 * Methode generiert im angebundenen CouchDB-System eine neue Datenbank mit dem
	 * im Parameter spezifizierten Titel.
	 * 
	 * @param databaseName
	 *            Titel der anzulegenden Datenbank.
	 * @return Status, ob Datenbank erstellt werden konnte.
	 * @throws Exception Sonstige auftretende Exceptions.
	 * @throws HttpClientErrorException
	 *             Datenbank bereits vorhanden.
	 * @throws ResourceAccessException
	 *             Keine Instanz von CouchDB gefunden.
	 */
	@RequestMapping(value = "/database/{databaseName}", method = RequestMethod.POST)
	@ApiOperation(value = "Erstellt eine neue CouchDB-Database", notes = "Gemäß dem Parameter wird eine neue Database in der verknüpften CouchDB-Datenbank erstellt, falls noch nicht vorhanden.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Datenbank erfolgreich erstellt", response = String.class),
			@ApiResponse(code = 412, message = "Datenbank bereits vorhanden", response = ErrorInformation.class),
			@ApiResponse(code = 500, message = "Sonstiger Fehler beim Prüfen der Datenbank", response = ErrorInformation.class) })
	public ResponseEntity<String> createDatabase(@PathVariable("databaseName") String databaseName) throws Exception {
		ResponseEntity<String> response = new ResponseEntity<String>(profileRepository.createDatabase(databaseName),
				HttpStatus.CREATED);
		return response;
	}

	/**
	 * Methode generiert im angebundenen CouchDB-System die Standard-Datenbanken _global_chagnes, _metadata, _replicator und _users.
	 * 
	 * @return Status, ob Datenbank erstellt werden konnte.
	 * @throws Exception Fehler beim Erzeugen der Datenbanken (unspezifiziert).
	 */
	@RequestMapping(value = "/database/defaultDatabases", method = RequestMethod.POST)
	@ApiOperation(value = "Erstellt die default Databases in CouchDB", notes = "Erzeugt die Datenbanken _global_chagnes, _metadata, _replicator und _users. "
			+ "Diese werden standardmäßig bei der Installation von CouchDB angelegt, fehlen jedoch beim Docker Image.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Datenbanken erfolgreich erstellt", response = Void.class),
			@ApiResponse(code = 500, message = "Sonstiger Fehler beim Erstellen der Datenbanken", response = ErrorInformation.class) })
	public ResponseEntity<Void> createDefaultDatabases() throws Exception {
		boolean exceptionOccurred = false;
		try {
			profileRepository.createDatabase("_global_changes");
		} catch (Exception e) {
			exceptionOccurred = true;
		}
		try {
			profileRepository.createDatabase("_metadata");
		} catch (Exception e) {
			exceptionOccurred = true;
		}
		try {
			profileRepository.createDatabase("_replicator");
		} catch (Exception e) {
			exceptionOccurred = true;
		}
		try {
			profileRepository.createDatabase("_users");
		} catch (Exception e) {
			exceptionOccurred = true;
		}
		
		if(exceptionOccurred == true) {
			throw new Exception("Fehler beim Erstellen der Datenbanken");
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
