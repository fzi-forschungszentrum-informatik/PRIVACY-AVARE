/*
        Copyright 2016-2018 AVARE project team

        AVARE-Project was financed by the Baden-Württemberg Stiftung gGmbH (www.bwstiftung.de).
        Project partners are FZI Forschungszentrum Informatik am Karlsruher
        Institut für Technologie (www.fzi.de) and Karlsruher
        Institut für Technologie (www.kit.edu).

        Files under this folder (and the subfolders) with "Created by AVARE Project ..."-Notice
	    are our work and licensed under Apache Licence, Version 2.0"

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
import {store} from '../../App'

const key = 5;

export function encrypt(text) {
    
    result = "";
    for (i = 0; i < text.length; i++) {
        result = result +  String.fromCharCode(text.charCodeAt(i) + key);
    }

    return result;
}

export function decrypt(text) {
    result = "";
    for (i = 0; i < text.length; i++) {
        result = result +  String.fromCharCode(text.charCodeAt(i) - key);
    }

    return result;
}

export function getEncryptedPreferences() {
    preferenceObject = {
        apps: store.getState().apps,
        categories: store.getState().categories,
    }

    return encrypt(JSON.stringify(preferenceObject));
}

