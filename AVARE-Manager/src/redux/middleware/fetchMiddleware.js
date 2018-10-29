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
import { Alert, ToastAndroid } from 'react-native';
import {loadPreferences, LOAD_PREFERENCES_SUCCESS} from '../modules/communication/actions';
import {setPending} from '../modules/network/actions'
import { decrypt } from '../../encryption/Encryptor';
import { loadApps } from '../modules/apps/actions';
import { loadCategories } from '../modules/categories/actions';
import { writeJsonFile } from '../../storage/RNFSControl';

import {NavigationActions} from 'react-navigation'

export default function fetchMiddleware({ dispatch, getState }) {
    return next => action => {
        const { promise, types, ...rest } = action;
        if (!promise) {
            return next(action);
        }
        console.log("Checking connection")
        console.log(getState().network.isOffline)

        const [REQUEST, SUCCESS, FAILURE] = types;

        if(getState().network.isOffline) {
            ToastAndroid.show('Error: device is offline', ToastAndroid.SHORT)
            next({...rest, type: FAILURE});
        }
             
        dispatch(setPending("Request"))
        next({ ...rest, type: REQUEST });
        console.log("Requesting Server " + REQUEST)
        if (getState().network.isOffline) {
            Alert.alert('Keine Verbindung zum Server')
            return next({...rest, type: FAILURE})
        }
        const actionPromise = fetch(promise.url, promise);

        actionPromise
            .then(response => response.text())
            .then(payload => {
                dispatch(setPending(""));
                if (payload.startsWith('{"title":')) {

                    errorObj = JSON.parse(payload)
                    payload = errorObj.title
                    next({ ...rest, payload, type: FAILURE });
                    console.log(errorObj.exception)

                    switch (errorObj.exception) {
                        case "de.privacy_avare.exeption.ClientPreferencesOutdatedException":
                            ToastAndroid.show('Client outdated, automated download from Server started', ToastAndroid.LONG);
                            
                            profile = getState().communication.profile
                            time = getState().communication.time
                            console.log("Dispatching " + profile + " | " + time);

                            var actionObject = loadPreferences(profile,time)
                            
                            dispatch(actionObject)

                        default: ;
                    }

                } else {
                    
                    if (SUCCESS === LOAD_PREFERENCES_SUCCESS) { // Es wurde erfolgreich eine Preferenz erhalten (alternativ wäre Profil)
                        ToastAndroid.show('Got Preferences', ToastAndroid.SHORT);
                        //console.log(decrypt(payload));
                        preferenceObject = JSON.parse(decrypt(payload));
                        dispatch(loadApps(preferenceObject.apps));
                        dispatch(loadCategories(preferenceObject.categories));
                        writeJsonFile();
                        dispatch(NavigationActions.navigate({routeName: 'Home'}));

                    }
                    

                    next({ ...rest, payload, type: SUCCESS })
                }

            })
            .catch(error => {
                ToastAndroid.show('Error: No Server response', ToastAndroid.SHORT)
                console.log(error)
                dispatch(setPending(""))
                next({ ...rest, error, type: FAILURE })
            })
                


        return actionPromise;
    }
}