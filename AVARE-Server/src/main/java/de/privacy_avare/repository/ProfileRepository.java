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

package de.privacy_avare.repository;

import java.util.Date;
import java.util.List;

/**
 * Interface definiert Methoden zum Datenbankzugriff. 
 * Die Methoden sind grundsätzlich nicht an eine gewisse Art von Datenbank gebunden. 
 * Somit kann die zugrundeliegende Datenbank flexibel gegen Alternativen ausgetauscht werden.
 * Durch die Annotation @N1qlPrimaryIndexed wird automatisch ein Primary Index erzeugt, 
 * welcher für die erweiterten Datenbankabfragen benötigt wird.
 * 
 * @autor Lukas Struppek
 * @version 1.0
 */

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.repository.CrudRepository;

import de.privacy_avare.domain.Profile;

/**
 * Interface definiert diverse Methoden zur Interaktion zwischen Serverprogramm
 * und zugehöriger Datenbank bereit. Methoden sind mithilfe von Spring Data
 * definiert und somit mit einer Vielzahl an Datenbanklösungen kompatibel. Ein
 * Austausch der Datenbank ist somit ohne Änderungen im Programmcode möglich.
 * Anpassung der Einstellungen in der Datei application.properties sind dennoch
 * notwendig.
 * 
 * Das Interface kann mit kompatiblen Datenbanksystemen mittels
 * Dependency-Injection durch @Autowired verwendet werden.
 * 
 * Für die Verwendung von CouchDB wurde aufgrund der fehlenden Spring
 * Data-Kompatibilität mit der Klasse ProfileRepositoryCouchDBImpl eine manuelle
 * Implementierung erstellt.
 * 
 * 
 * @author Lukas Struppek
 * @version 1.0
 * @see CrudRepository
 */
@N1qlPrimaryIndexed
public interface ProfileRepository extends CrudRepository<Profile, String> {

	/**
	 * Liefert alle Profile aus der Datenbank, absteigend nach ProfileId sortiert,
	 * zurück.
	 * 
	 * @return Liste aller vorhanden Profile.
	 */
	List<Profile> findAllByOrderByIdAsc();

	/**
	 * Liefert alle Profile aus der Datenbank, bei welchen der Zeitstempel
	 * lastProfileContact vor dem Zeitpunkt des Parameters date liegt.
	 * 
	 * @param date
	 *            Zeitpunkt, bis zu welchem alle Profile gesucht werden.
	 * @return Profile mit lastProfileContact vor Zeitpunkt des Parameters.
	 */
	List<Profile> findAllByLastProfileContactBefore(Date date);

	/**
	 * Liefert den Zeitpunkt lastProfileContact eines einzelnen Profils zurück.
	 * 
	 * @param id
	 *            ProfileId des gesuchten Profils.
	 * @return Zeitpunkt lastProfileContact.
	 */
	Date findLastProfileContactById(String id);

	/**
	 * Liefert den Zeitpunkt lastProfileChange eines einzelnen Profils zurück.
	 * 
	 * @param id
	 *            ProfileId des gesuchten Profils.
	 * @return Zeitpunkt lastProfileChange.
	 */
	Date findLastProfileChangeById(String id);

	/**
	 * Liefert die preferences eines Profils zurück.
	 * 
	 * @param id
	 *            ProfileId des gesuchten Profils.
	 * @return Preferences eines Profils.
	 */
	String findPreferencesById(String id);

}
