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
import React, { Component } from 'react';
import { View, FlatList } from 'react-native';
import { withTheme, List, Checkbox } from 'react-native-paper';
import PreferencesHeader from '../../_shared/PreferencesHeader';

import { addVerticalCalendarFilter as addVerticalCategoryFilter, removeVerticalCalendarFilter as removeVerticalCategoryFilter } from '../../../redux/modules/categories/actions';
import { addVerticalCalendarFilter as addVerticalAppFilter, removeVerticalCalendarFilter as removeVerticalAppFilter } from '../../../redux/modules/apps/actions';
import { writeJsonFile } from '../../../storage/RNFSControl';
import { connect } from 'react-redux';

class CalendarFieldsFilter extends Component {
    constructor(props) {
        super(props);

        let contextID = this.props.navigation.state.params.contextID;
        let context = this.props.navigation.state.params.context;

        // TODO: Events nach AccessLevel filtern: Standard, Privat, Öffentlich - > Kalenderindividuell?
        // https://developer.android.com/reference/android/provider/CalendarContract.Events
        this.state = {
            contextID,
            context,
            fields: [
                {
                    _id: "ORGANIZER",
                    description: "Organisator"
                },
                {
                    _id: "TITLE",
                    description: "Titel"
                },
                {
                    _id: "EVENT_LOCATION",
                    description: "Ort"
                },
                {
                    _id: "DESCRIPTION",
                    description: "Beschreibung",
                },
                {
                    _id: "DTSTART",
                    description: "Start",
                },
                {
                    _id: "DTEND",
                    description: "Ende",
                },
                {
                    _id: "DURATION",
                    description: "Dauer",
                },

            ]
        }

    }

    addVerticalFilter(field) {
        if (this.state.context == "category") {
            this.props.dispatch(addVerticalCategoryFilter(this.state.contextID, field));
        } else {
            this.props.dispatch(addVerticalAppFilter(this.state.contextID, field));
        }
        writeJsonFile();
    }

    removeVerticalFilter(field) {
        if (this.state.context == "category") {
            this.props.dispatch(removeVerticalCategoryFilter(this.state.contextID, field));
        } else {
            this.props.dispatch(removeVerticalAppFilter(this.state.contextID, field));
        }
        writeJsonFile();
    }

    render() {
        let { colors } = this.props.theme;

        let contextObject;
        if (this.state.context == "category") {
            contextObject = this.props.categories.find((element) => { return element._id == this.state.contextID });
        } else {
            contextObject = this.props.apps.find((element) => { return element._id == this.state.contextID });
        }
        let verticalFilter = contextObject.settings.calendar.filterSettings.vertical;
        return (
            <View style={{ backgroundColor: colors.background }}>
                <PreferencesHeader title="Kalenderfilter (Felder)" />
                <FlatList
                    data={this.state.fields}
                    extraData={this.props}
                    keyExtractor={(item, index) => item._id}
                    renderItem={({ item }) => {
                        let itemChecked = verticalFilter.some((element) => {
                            return element == item._id
                        })

                        return (
                            <List.Item
                                title={item.description}
                                right={() => <Checkbox status={itemChecked ? 'checked' : 'unchecked'} />}
                                onPress={
                                    () => {
                                        if (!itemChecked) {
                                            this.addVerticalFilter(item._id);
                                        } else {
                                            this.removeVerticalFilter(item._id)
                                        }
                                    }
                                } />
                        )


                    }}
                />
            </View>
        );
    }
}

const mapStateToProps = (state) => {
    console.log(state);

    return {
        categories: state.categories,
        apps: state.apps
    };
}

export default connect(mapStateToProps)(withTheme(CalendarFieldsFilter));