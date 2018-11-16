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
import {
    ADD_APP,
    UPDATE_APP,
    REMOVE_APP,
    LOAD_APPS,
    RESET_APPS,
    ADD_HORIZONTAL_CONTACT,
    REMOVE_HORIZONTAL_CONTACT,
    ADD_VERTICAL_CONTACT,
    REMOVE_VERTICAL_CONTACT,
    ADD_HORIZONTAL_CALENDAR,
    REMOVE_HORIZONTAL_CALENDAR,
    ADD_VERTICAL_CALENDAR,
    REMOVE_VERTICAL_CALENDAR,
    UPDATE_LOCATION_APP,
    UPDATE_STATUS_APP,

} from './actions';
import _ from 'lodash';


// const initialState = {

    // apps: []
    
// };
const initialState = [];

export default function reducer (state = initialState, action) {
    switch (action.type) {

        case ADD_APP:
            return [
                ...state,
                action.payload
            ]

        case UPDATE_APP:
            return {
                ...state,
                apps: state.apps.map(app => {
                    if (app._id === action.payload._id) {
                        return {
                            ...action.payload
                        };
                    }
                    return app;
                })
            };

        case REMOVE_APP:
            return  state.filter(app => app._id !== action.payload._id)
            // {
                // ...state,
                // apps: state.apps.filter(
                    // app => app._id !== action.payload._id
                // )
            // };

        case LOAD_APPS:
            return action.payload;


        case RESET_APPS:
            return initialState;

        case ADD_HORIZONTAL_CALENDAR:
            return state.map(app => {
                if (app._id !== action.payload._id) {
                    return app;
                }
                var newApp = _.cloneDeep(app)
                newApp.settings.calendar.filterSettings.horizontal.push(action.payload.calendar);

                return newApp;
            });

        case REMOVE_HORIZONTAL_CALENDAR:
            return state.map(app => {
                if (app._id !== action.payload._id) {
                    return app;
                }
                var newApp = _.cloneDeep(app)
                let oldArray = newApp.settings.calendar.filterSettings.horizontal;
                newApp.settings.calendar.filterSettings.horizontal = oldArray.filter((element) => element != action.payload.calendar);

                return newApp;
            });

        case ADD_VERTICAL_CALENDAR:
            return state.map(app => {
                if (app._id !== action.payload._id) {
                    return app;
                }
                var newApp = _.cloneDeep(app)
                newApp.settings.calendar.filterSettings.vertical.push(action.payload.field);

                return newApp;
            });

        case REMOVE_VERTICAL_CALENDAR:
            return state.map(app => {
                if (app._id !== action.payload._id) {
                    return app;
                }
                var newApp = _.cloneDeep(app)
                let oldArray = newApp.settings.calendar.filterSettings.vertical;
                newApp.settings.calendar.filterSettings.vertical = oldArray.filter((element) => element != action.payload.field);

                return newApp;
            });

        case ADD_HORIZONTAL_CONTACT:
            return state.map(app => {
                if (app._id !== action.payload._id) {
                    return app;
                }
                var newApp = _.cloneDeep(app)
                newApp.settings.contacts.filterSettings.horizontal.push(action.payload.contact);

                return newApp;
            });

        case REMOVE_HORIZONTAL_CONTACT:
            return state.map(app => {
                if (app._id !== action.payload._id) {
                    return app;
                }
                var newApp = _.cloneDeep(app)
                let oldArray = newApp.settings.contacts.filterSettings.horizontal;
                newApp.settings.contacts.filterSettings.horizontal = oldArray.filter((element) => element != action.payload.contact);

                return newApp;
            });

        case ADD_VERTICAL_CONTACT:
            return state.map(app => {
                if (app._id !== action.payload._id) {
                    return app;
                }
                var newApp = _.cloneDeep(app)
                newApp.settings.contacts.filterSettings.vertical.push(action.payload.field);

                return newApp;
            });

        case REMOVE_VERTICAL_CONTACT:
            return state.map(app => {
                if (app._id !== action.payload._id) {
                    return app;
                }
                var newApp = _.cloneDeep(app)
                let oldArray = newApp.settings.contacts.filterSettings.vertical;
                newApp.settings.contacts.filterSettings.vertical = oldArray.filter((element) => element != action.payload.field);

                return newApp;
            });


        case UPDATE_LOCATION_APP:
            return state.map((app) => {
                if (app._id !== action.payload._id) {
                    return app;
                }

                var newApp = _.cloneDeep(app) // TODO: in case of performance issues, one could go the tedious way rather than cloneDeep 
                newApp.settings.location.filterSettings.distance = action.payload.value

                return newApp;
            })

        case UPDATE_STATUS_APP: // TODO: updating nested objects tedious, see https://github.com/reduxjs/redux/blob/master/docs/recipes/reducers/ImmutableUpdatePatterns.md
            return state.map(app => {
                if (app._id !== action.payload._id) {
                    // try {
                    // } catch (error) {
                    // console.log(error)
                    // return category;
                    // }
                    return app;
                }
                var newApp = _.cloneDeep(app) // TODO: in case of performance issues, one could go the tedious way rather than cloneDeep 
                newApp.settings[action.payload.setting].status = action.payload.newStatus

                return newApp;
                /*
                return {
                    ...category,
                    settings: {
                        ...category.settings, // TODO: hmmm, what about other nested settings? should these be cloned too?
                        [action.payload.setting]: {
                            ...category.settings[action.payload.setting],
                            status: action.payload.newStatus,
                        }
                    }
                    // category.settings[action.payload.setting].status = action.payload.newStatus
                };
                */
            })


        default:
            return state;

    }
}