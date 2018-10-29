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
    ADD_CATEGORY,
    UPDATE_CATEGORY,
    REMOVE_CATEGORY,
    UPDATE_STATUS_CATEGORY,
    ADD_HORIZONTAL_CONTACT,
    REMOVE_HORIZONTAL_CONTACT,
    ADD_VERTICAL_CONTACT,
    REMOVE_VERTICAL_CONTACT,
    LOAD_CATEGORIES,
    ADD_DEFAULT_CATEGORY,
    RESET_CATEGORIES,
    UPDATE_LOCATION_CATEGORY,
    ADD_HORIZONTAL_CALENDAR,
    REMOVE_HORIZONTAL_CALENDAR,
    ADD_VERTICAL_CALENDAR,
    REMOVE_VERTICAL_CALENDAR
} from './actions';

import _ from 'lodash';
import initialPreferences from '../../../storage/initalPreferences'

//TODO: otherwise the global state looks like { ..., categories: categories: [] }
//const initialState = {
//    categories: []
//};
const initialState = [];

export default function reducer (state = initialState, action) {
    switch (action.type) {

        case ADD_CATEGORY: 
            return [ 
                ...state,
                // categories: [...state.categories, action.payload]
                action.payload
            ]
            
        case UPDATE_CATEGORY: 
            return {
                ...state,
                categories: state.categories.map(category => {
                    if (category._id === action.payload._id) {
                        return {
                            ...action.payload
                        };
                    }
                    return category;
                })
            };

        case REMOVE_CATEGORY:
            return {
                ...state,
                categories: state.categories.filter (
                    category => category._id !== action.payload._id
                )
            };

        case ADD_HORIZONTAL_CALENDAR:
            return state.map(category => {
                if (category._id !== action.payload._id) {
                    return category;
                }
                var newCategory = _.cloneDeep(category)
                newCategory.settings.calendar.filterSettings.horizontal.push(action.payload.calendar);

                return newCategory;
            });

        case REMOVE_HORIZONTAL_CALENDAR:
            return state.map(category => {
                if (category._id !== action.payload._id) {
                    return category;
                }
                var newCategory = _.cloneDeep(category)
                let oldArray = newCategory.settings.calendar.filterSettings.horizontal;
                newCategory.settings.calendar.filterSettings.horizontal = oldArray.filter((element) => element != action.payload.calendar);

                return newCategory;
            });

        case ADD_VERTICAL_CALENDAR:
            return state.map(category => {
                if (category._id !== action.payload._id) {
                    return category;
                }
                var newCategory = _.cloneDeep(category)
                newCategory.settings.calendar.filterSettings.vertical.push(action.payload.field);

                return newCategory;
            });

        case REMOVE_VERTICAL_CALENDAR:
            return state.map(category => {
                if (category._id !== action.payload._id) {
                    return category;
                }
                var newCategory = _.cloneDeep(category)
                let oldArray = newCategory.settings.calendar.filterSettings.vertical;
                newCategory.settings.calendar.filterSettings.vertical = oldArray.filter((element) => element != action.payload.field);

                return newCategory;
            });

        case ADD_HORIZONTAL_CONTACT:
            return state.map(category => {
                if (category._id !== action.payload._id) {
                    return category;
                }
                var newCategory = _.cloneDeep(category)
                newCategory.settings.contacts.filterSettings.horizontal.push(action.payload.contact);

                return newCategory;
            });

        case REMOVE_HORIZONTAL_CONTACT:
            return state.map(category => {
                if (category._id !== action.payload._id) {
                    return category;
                }
                var newCategory = _.cloneDeep(category)
                let oldArray = newCategory.settings.contacts.filterSettings.horizontal;
                newCategory.settings.contacts.filterSettings.horizontal = oldArray.filter((element) => element != action.payload.contact);

                return newCategory;
            });

        case ADD_VERTICAL_CONTACT:
            return state.map(category => {
                if (category._id !== action.payload._id) {
                    return category;
                }
                var newCategory = _.cloneDeep(category)
                newCategory.settings.contacts.filterSettings.vertical.push(action.payload.field);

                return newCategory;
            });

        case REMOVE_VERTICAL_CONTACT:
            return state.map(category => {
                if (category._id !== action.payload._id) {
                    return category;
                }
                var newCategory = _.cloneDeep(category)
                let oldArray = newCategory.settings.contacts.filterSettings.vertical;
                newCategory.settings.contacts.filterSettings.vertical = oldArray.filter((element) => element != action.payload.field);

                return newCategory;
            });


        case UPDATE_LOCATION_CATEGORY:
            return state.map((category) => {
                if (category._id !== action.payload._id) {
                    return category;
                }

                var newCategory = _.cloneDeep(category) // TODO: in case of performance issues, one could go the tedious way rather than cloneDeep 
                newCategory.settings.location.filterSettings.distance = action.payload.value

                return newCategory;
            })

        case UPDATE_STATUS_CATEGORY: // TODO: updating nested objects tedious, see https://github.com/reduxjs/redux/blob/master/docs/recipes/reducers/ImmutableUpdatePatterns.md
            return state.map(category => {
                if (category._id !== action.payload._id) {
                    // try {
                    // } catch (error) {
                    // console.log(error)
                    // return category;
                    // }
                    return category;
                }
                var newCategory = _.cloneDeep(category) // TODO: in case of performance issues, one could go the tedious way rather than cloneDeep 
                newCategory.settings[action.payload.setting].status = action.payload.newStatus

                return newCategory;
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

        // return {
        // ...state,
        // categories: state.categories.map(category => {
        // if (category._id === action.payload._id) {
        // try {
        // category.settings[action.payload.setting].status = action.payload.newStatus  
        // } catch (error) {
        // console.log(error)
        // return category
        // }
        // 
        // return {
        // ...category,
        // };
        // }
        // return category;
        // })
        // };

        case LOAD_CATEGORIES:
            return action.payload;


        case ADD_DEFAULT_CATEGORY:
            var newCategory = _.cloneDeep(initialPreferences.categories[0]);
            newCategory._id = action.payload._id;
            newCategory.name = action.payload.name;
            return {
                ...state,
                newCategory,
            }

        case RESET_CATEGORIES: {
            return initialState;
        }
        default:
            return state;

    }
}