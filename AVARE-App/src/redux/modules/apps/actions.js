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
export const LOAD_APPS = 'apps/load'; 
    // Läd die Apps vom Server (Nötig? Der Server speichert nur eine Datei für alle Settings)

export const ADD_APP = 'apps/add';
    // Fügt eine App dem Array an bestehenden Apps hinzu

export const UPDATE_APP = 'apps/update'
    // Updated eine bestehende App mit neuen Eigenschaften

export const REMOVE_APP = 'apps/remove'
    // Entfernt eine App aus dem Array (Falls Nutzer einen Fehler gemacht hat?)

export const UPLOAD_APPS = 'apps/upload'
    // Uploaded die Apps vom Server (Nötig? Server speichert nur eine Datei für alle Settings)

export const RESET_APPS = 'apps/reset';

export const UPDATE_STATUS_APP = 'apps/updateStatus'

export const UPDATE_LOCATION_APP  = 'apps/updateLocation'

export const ADD_HORIZONTAL_CALENDAR  = 'apps/addHorizontalCalendar'

export const REMOVE_HORIZONTAL_CALENDAR  = 'apps/removeHorizontalCalendar'

export const ADD_VERTICAL_CALENDAR  = 'apps/addVerticalCalendar'

export const REMOVE_VERTICAL_CALENDAR  = 'apps/removeVerticalCalendar'

export const ADD_HORIZONTAL_CONTACT  = 'apps/addHorizontalContact'

export const REMOVE_HORIZONTAL_CONTACT  = 'apps/removeHorizontalContact'

export const ADD_VERTICAL_CONTACT  = 'apps/addVerticalContact'

export const REMOVE_VERTICAL_CONTACT  = 'apps/removeVerticalContact'


export function loadApps(apps) {
    return {
        type : LOAD_APPS,
        payload: apps,

    }
}

export function addApp(app) {
    return {
        type: ADD_APP,
        payload: app
    };
}

export function updateApp(app) {
    return {
        type: UPDATE_APP,
        payload: app
    };
}

export function removeApp(appID) {
    return {
        type: REMOVE_APP,
        payload: { _id: appID }
    }
}

/*export function uploadApps() {
    return {
        
    }
}*/

export function resetApps() {
    return {
        type: RESET_APPS,
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

export function updateSettingStatusOfApp(_id, setting, newStatus) {
    return {
        type: UPDATE_STATUS_APP,
        payload: {
            _id: _id,
            setting: setting,
            newStatus: newStatus,
        }
    }
}

export function setLocationFilter(_id, value) {
    return {
        type: UPDATE_LOCATION_APP,
        payload: {
            _id: _id,
            value
        }
    }
}