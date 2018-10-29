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
import {store} from '../../../App'
import {
    //AsyncStorage,
    //Alert,
    ToastAndroid,
} from 'react-native';

import {
    setProfile, setTime, 
    //setPreferences, getProfile
} from '../modules/communication/actions';
//import {setPending} from '../modules/network/actions'
//import {getISODate} from '../../views/Items'
import { readJsonFile } from '../../features/Storage/RNFSControl';
import { loadCategories } from '../modules/categories/actions';
import { loadApps } from '../modules/apps/actions';

/*
const keyID = '@MyApp:keyID'
const keyPreference = '@MyApp:keyPreference'
const keyTime = '@MyApp:keyTime'
*/

export const onLoad = () => {
    readJsonFile()
    .then((contents) => {
        console.log(contents);
        if (contents === 'no file') {
            console.log('no file');
            return false;
        }
        // log the file contents

        preferenceObject = JSON.parse(contents);
        store.dispatch(setProfile(preferenceObject.profile));
        store.dispatch(setTime(preferenceObject.time));
        store.dispatch(loadApps(preferenceObject.apps));
        store.dispatch(loadCategories(preferenceObject.categories));

        ToastAndroid.show('Json loaded', ToastAndroid.SHORT);
        console.log(contents);
        return true;
        
        
        
    })
    .catch((err) => {
        console.log(err.message, err.code);
        
    })
    
    /*
    try {
        store.dispatch(setPending("Reading")),
        storedID = AsyncStorage.getItem(keyID);
        storedID.then(response => {
            console.log("profile: " + response)
            if (response !== null) {
                store.dispatch(setProfile(response))
                console.log("ID resolved")
            } else {
                store.dispatch(getProfile())
            }
        })


        storedTime = AsyncStorage.getItem(keyTime);
        storedTime.then(response => {
            console.log("time: " + response)
            if (response !== null) {
                store.dispatch(setTime(response))
                console.log("Time resolved")
            }
        })


        storedPreference = AsyncStorage.getItem(keyPreference);
        storedPreference.then(response => {
            console.log("preference: " + response)
            if (response !== null) {
                store.dispatch(setPreferences(response, store.getState().communication.time))
                console.log("Preference resolved")
            };
            store.dispatch(setPending(""));
        })

    } catch (error) {
        Alert.alert('Error while Loading');
        store.dispatch(setPending(""))
    }
    
}


export const clearStore = () => {
    AsyncStorage.multiRemove([keyID, keyPreference, keyTime])
}
export const saveStore = () => {
    try {
        store.dispatch(setPending("Writing"))
        var com = store.getState().communication
        console.log(com);
        storedProfile = AsyncStorage.setItem(keyID, com.profile)
        storedProfile.then(response => {
            console.log("profile saved: " + response)
        })
        storedPref = AsyncStorage.setItem(keyPreference, com.preferences)
        storedPref.then(response => {
            console.log("Preferences saved: " + response)
        })
        storedTi = AsyncStorage.setItem(keyTime, getISODate(new Date()))
        storedTi.then(response => {
            store.dispatch(setPending(""))
            console.log("Time saved: " + response)
        })

    } catch (error) {
        console.log(error)
        Alert.alert('Error while saving')
        store.dispatch(setPending(""))
    }
    */
}
