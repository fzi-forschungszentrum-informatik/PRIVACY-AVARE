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
export const LOAD_CATEGORIES = 'categories/load'; 
    // Läd die Kategorien vom Server (Nötig? Der Server speichert nur eine Datei für alle Settings)

export const ADD_CATEGORY = 'categories/add';
    // Fügt eine Kategorie dem Array an bestehenden Kategorien hinzu

export const UPDATE_CATEGORY = 'categories/update'
    // Updated eine bestehende Kategorie mit neuen Eigenschaften

export const REMOVE_CATEGORY = 'categories/remove'
    // Entfernt eine Kategorie aus dem Array (Falls Nutzer einen Fehler gemacht hat?)

export const UPLOAD_CATEGORIES = 'categories/upload'
    // Uploaded die Kategoiren auf den Server (Nötig? Server speichert nur eine Datei für alle Settings)

export const UPDATE_STATUS_CATEGORY = 'categories/updateStatus'

export const UPDATE_LOCATION_CATEGORY  = 'categories/updateLocation'

export const ADD_HORIZONTAL_CALENDAR  = 'categories/addHorizontalCalendar'

export const REMOVE_HORIZONTAL_CALENDAR  = 'categories/removeHorizontalCalendar'

export const ADD_VERTICAL_CALENDAR  = 'categories/addVerticalCalendar'

export const REMOVE_VERTICAL_CALENDAR  = 'categories/removeVerticalCalendar'


export const ADD_HORIZONTAL_CONTACT  = 'categories/addHorizontalContact'

export const REMOVE_HORIZONTAL_CONTACT  = 'categories/removeHorizontalContact'

export const ADD_VERTICAL_CONTACT  = 'categories/addVerticalContact'

export const REMOVE_VERTICAL_CONTACT  = 'categories/removeVerticalContact'

export const ADD_DEFAULT_CATEGORY = 'categories/addDefault'

export const RESET_CATEGORIES = 'categories/reset'

export function loadCategories(categories) {
    return {
        type: LOAD_CATEGORIES,
        payload: categories,
    }
}

export function addCategory(category) {
    return {
        type: ADD_CATEGORY,
        payload: category
    };
}

export function updateCategory(category) {
    return {
        type: UPDATE_CATEGORY,
        payload: category
    };
}

export function removeCategory(category) {
    return {
        type: REMOVE_CATEGORY,
        payload: category
    }
}

export function uploadCategories() {
    return {
        
    }
}

export function addHorizontalContactsFilter(_id, contact) {
    return {
        type: ADD_HORIZONTAL_CONTACT,
        payload: {
            _id,
            contact
        }
    }
}

export function addVerticalContactsFilter(_id, field) {
    return {
        type: ADD_VERTICAL_CONTACT,
        payload: {
            _id,
            field
        }
    }
}

export function removeVerticalContactsFilter(_id, field) {
    return {
        type: REMOVE_VERTICAL_CONTACT,
        payload: {
            _id,
            field
        }
    }
}

export function removeHorizontalContactsFilter(_id, contact) {
    return {
        type: REMOVE_HORIZONTAL_CONTACT,
        payload: {
            _id,
            contact
        }
    }
}

export function addHorizontalCalendarFilter(_id, calendar) {
    return {
        type: ADD_HORIZONTAL_CALENDAR,
        payload: {
            _id,
            calendar
        }
    }
}

export function removeHorizontalCalendarFilter(_id, calendar) {
    return {
        type: REMOVE_HORIZONTAL_CALENDAR,
        payload: {
            _id,
            calendar
        }
    }
}

export function addVerticalCalendarFilter(_id, field) {
    return {
        type: ADD_VERTICAL_CALENDAR,
        payload: {
            _id,
            field
        }
    }
}

export function removeVerticalCalendarFilter(_id, field) {
    return {
        type: REMOVE_VERTICAL_CALENDAR,
        payload: {
            _id,
            field
        }
    }
}

export function updateSettingStatusOfCategory(_id, setting, newStatus) {
    return {
        type: UPDATE_STATUS_CATEGORY,
        payload: {
            _id: _id,
            setting: setting,
            newStatus: newStatus,
        }
    }
}

export function setLocationFilter(_id, value) {
    return {
        type: UPDATE_LOCATION_CATEGORY,
        payload: {
            _id: _id,
            value
        }
    }
}

export function addDefaultCategory(_id, name) {
    return {
        type: ADD_DEFAULT_CATEGORY,
        payload: {
            _id: _id,
            name: name,
        },
    }
}

export function resetCategories() {
    return {
        type: RESET_CATEGORIES,
    }
}