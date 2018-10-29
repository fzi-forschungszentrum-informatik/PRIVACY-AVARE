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
export const GET_PROFILE = 'communication/GET_PROFILE'
export const SET_TIME = 'communication/SET_TIME'
export const SET_PROFILE = 'communication/SET_PROFILE'
export const SET_PREFERENCES = 'communication/SET_PREFERENCES'
export const LOAD_PROFILE = 'communication/LOAD_PROFILE';
export const LOAD_PROFILE_SUCCESS = 'communication/LOAD_PROFILE_S';
export const LOAD_PROFILE_FAIL = 'communication/LOAD_PROFILE_F';
export const UPLOAD_PROFILE = 'communication/UPLOAD_PROFILE'
export const UPLOAD_PROFILE_SUCCESS = 'communication/UPLOAD_PROFILE_S';
export const UPLOAD_PROFILE_FAIL = 'communication/UPLOAD_PROFILE_F';
export const LOAD_PREFERENCES = 'communication/LOAD_PREFERENCES';
export const LOAD_PREFERENCES_SUCCESS = 'communication/LOAD_PREFERENCES_S';
export const LOAD_PREFERENCES_FAIL = 'communication/LOAD_PREFERENCES_F';

import {SERVER} from '../../../../App';


export function setTime(time) {
    return {
        type: SET_TIME,
        load: time
    }
}

export function setProfile(profile) {
    return {
        type: SET_PROFILE,
        load: profile
    }
}

export function setPreferences(preferences, time) {
    return {
        type: SET_PREFERENCES,
        load: {
            preferences: preferences,
            time: time
        }
    }
}

export function loadPreferences(profile, time) {
    return {
        types: [
            LOAD_PREFERENCES,
            LOAD_PREFERENCES_SUCCESS,
            LOAD_PREFERENCES_FAIL
        ],
        promise : {
            url: SERVER + '/v1/profiles/' + profile + '/' + time,
            method: 'GET',
            headers: {
                "Accept": "*/*"
            }
        }
    }
}

export function getProfile() {
    return {
        types: [
          LOAD_PROFILE,
          LOAD_PROFILE_SUCCESS,
          LOAD_PROFILE_FAIL  
        ],
        promise: {
            url: SERVER + '/v1/newProfiles',
            method: 'POST',
            headers: {
                "Accept": "*/*"
            }

        }
    }
}

export function uploadProfile(id, clientProflieChange, preferences) {
    console.log('Uploading')
    return {
        types: [
            UPLOAD_PROFILE,
            UPLOAD_PROFILE_SUCCESS,
            UPLOAD_PROFILE_FAIL  
        ],
        promise : {
            url: SERVER + '/v1/profiles/' + id + '/' + clientProflieChange,
            method: 'PUT',
            headers: {
                "Accept": "*/*",
            },
            body : preferences,
            
           
        }
    }
}
