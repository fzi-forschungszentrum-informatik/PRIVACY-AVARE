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
import { GET_PROFILE, SET_TIME, LOAD_PROFILE_SUCCESS, SET_PREFERENCES, SET_PROFILE, LOAD_PREFERENCES_SUCCESS } from './actions'

const initialState = {
    time: "1970-01-01T01-00-00-000",
    profile: "",
    preferences: ""
}

export default function reducer(state = initialState, action) {

    switch (action.type) {

        /*case LOAD_PREFERENCES_SUCCESS:
            //console.log("Preference sucess" + action.payload)
            return {
                ...state,
                preferences: action.payload
            }
        */
        case LOAD_PROFILE_SUCCESS:
            console.log("Sucess " + action.payload)
            return {
                ...state,
                profile: action.payload
            }



        case SET_TIME:
            return {
                ...state,
                time: action.load
            }
        case SET_PROFILE:
            return {
                ...state,
                profile: action.load
            }
        case SET_PREFERENCES:
            return {
                ...state,
                preferences: action.load.preferences,
                time: action.load.time
            }

        default:
            return state;
    }
}