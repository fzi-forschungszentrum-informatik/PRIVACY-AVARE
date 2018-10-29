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
import { getISODate } from "../../../functions/getIsoDate";

export function findApp(apps, id) {
    apps.map(app => {
        if (app.id === id) {
            return app;
        }
    });
}

blockable = ["location","internet"]

export function addBlockToApp(app, settingToBlock, durationInMinutes) {
    isBlockable = false;
    for (i = 0; i < blockable.length; i++) {
        if (blockable[i] == settingToBlock) {
            isBlockable = true
        }
    }

    if (isBlockable) {
        endTime = getISODate(new Date(Date.now() + durationInMinutes * 60 * 1000));
        app.settings[settingToBlock].status = "blocked";
        
        app.settings[settingToBlock].blockSettings.blockEnd = endTime;
        console.log('Blocked until ' + endTime);
    } else {
        console.log("Error while adding Block: " + settingToBlock + " is not blockable.")
    }
}

export function createApp(_id, category_id) {
    return {
        _id: _id,
        category_id : category_id,
        settings: {
            location: {
                status: "filtered",
                blockSettings: {
                    blockEnd : "2018-09-03T19:00:00"
                },
                filterSettings: {
                    distance: 50,
                },
            },
            contacts: {
                status: "filtered",
                filterSettings: {
                    horizontal: [
                        "LOOKUP_KEY_1",
                    ],
                    vertical: [
                        "StructuredName.CONTENT_ITEM_TYPE",
                        "Organization.CONTENT_ITEM_TYPE",
                    ],
                },
            },
        }
    }
}

export function test () {
    apps = [];
    _id = 1;
    category_id = 1;

    for (i = 0 ; i < 10 ; i++) {
        apps[i] = createApp(_id, category_id)
        _id++;
    }

    addBlockToApp(apps[0],"location",3)
    console.log(apps)
    console.log(JSON.stringify(apps))


}