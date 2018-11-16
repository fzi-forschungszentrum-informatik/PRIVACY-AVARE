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
//var RNFS = require('react-native-fs');
import RNFS from 'react-native-fs'
import { ToastAndroid } from 'react-native'

import { store } from '../../App';
import { resetApps } from '../redux/modules/apps/actions';
import { resetCategories } from '../redux/modules/categories/actions';

/*
import { loadApps } from '../../persistence/modules/apps/actions';
import { loadCategories } from '../../persistence/modules/categories/actions';
import { setProfile, setTime } from '../../persistence/modules/communication/actions';
*/

export function writeJsonFile() {
    var path = RNFS.DocumentDirectoryPath + '/preferences.json';
    var storeState = store.getState();
    var jsonObject = {
        profile: storeState.communication.profile,
        time: storeState.communication.time,
        apps: storeState.apps,
        categories : storeState.categories,
    }
    RNFS.writeFile(path, JSON.stringify(jsonObject), 'utf8')
        .then((success) => {
            ToastAndroid.show('File written',ToastAndroid.SHORT)
        })
        .catch((err) => {
            ToastAndroid.show('Error', ToastAndroid.SHORT)
            console.log(err.message);
        })
    
}

export function deleteJsonFile() {
    
    deleteFile('preferences.json'); //also empties store
    
}

export function deleteFile(nameOfFile) {
    var path = RNFS.DocumentDirectoryPath + '/' + nameOfFile;

    return RNFS.unlink(path)
        .then(() => {
            store.dispatch(resetApps());
            store.dispatch(resetCategories());
            ToastAndroid.show('File deleted', ToastAndroid.SHORT);
        })
        .catch((err) => {
            console.log(err.message);
            ToastAndroid.show('Error while deleting', ToastAndroid.SHORT);
        })
} 

export function readJsonFile() {
    return RNFS.readDir(RNFS.DocumentDirectoryPath)
        .then((result) => {
            console.log('GOT RESULT', result);

            for (i = 0 ; i < result.length; i++) {
                if (result[i].name === "preferences.json") {
                    return Promise.all([RNFS.stat(result[i].path), result[i].path]);
                }
            }
            
            throw "No file";
        })
        .then((statResult) => {
            
              
            if (statResult[0].isFile()) {
                return RNFS.readFile(statResult[1], 'utf8');
            }
            
        })
        

}

export function basic() {
    //var RNFS = require('react-native-fs');
    // get a list of files and directories in the main bundle
    RNFS.readDir(RNFS.DocumentDirectoryPath)
        .then((result) => {
            console.log('GOT RESULT', result);

            for (i = 0 ; i < result.length; i++) {
                if (result[i].name === "preferences.json") {
                    return Promise.all([RNFS.stat(result[i].path), result[i].path]);
                }
            }
            // stat the first file
            return Promise.all([RNFS.stat(result[1].path), result[1].path]);
        })
        .then((statResult) => {
            if (statResult[0].isFile()) {
                return RNFS.readFile(statResult[1], 'utf8');
            }

            return 'no file';
        })
        .then((contents) => {
            // log the file contents
            console.log(contents);
        })
        .catch((err) => {
            console.log(err.message, err.code);
        })
}

/*
export function fileCreation() {
    //var RNFS = require('react-native-fs');
    var path = RNFS.DocumentDirectoryPath + '/test2.txt';

    RNFS.writeFile(path, 'TEST TEST', 'utf8')
        .then((success) => {
            console.log('File WRITTEN!');
        })
        .catch((err) => {
            console.log(err.message)
        })
    }
    */