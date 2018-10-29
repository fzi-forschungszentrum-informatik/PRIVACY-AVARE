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
import React from 'react';
import { View, FlatList } from 'react-native';
import { withTheme, List, Checkbox, Colors } from 'react-native-paper';
import PreferencesHeader from '../../_shared/PreferencesHeader';

import RNCalendarEvents from 'react-native-calendar-events';
import { addHorizontalCalendarFilter as addHorizontalCategoryFilter, removeHorizontalCalendarFilter as removeHorizontalCategoryFilter } from '../../../redux/modules/categories/actions';
import { addHorizontalCalendarFilter as addHorizontalAppFilter, removeHorizontalCalendarFilter as removeHorizontalAppFilter } from '../../../redux/modules/apps/actions';
import { writeJsonFile } from '../../../storage/RNFSControl';
import { connect } from 'react-redux';

class CalendarItemsFilter extends React.Component {
    constructor(props) {
        super(props);

        RNCalendarEvents.findCalendars().then((result) => {
            this.loadCalendarsInState(result)
        }, (error) => {
            console.log("Error: " + error)
        });

        let contextID = this.props.navigation.state.params.contextID;
        let context = this.props.navigation.state.params.context;
        this.state = {
            contextID,
            context,
            calendars: []
        }

    }

    loadCalendarsInState(calendars) {
        this.setState({ calendars });
    }

    addHorizontalFilter(calendarID) {
        if (this.state.context == "category") {
            this.props.dispatch(addHorizontalCategoryFilter(this.state.contextID, calendarID));
        } else {
            this.props.dispatch(addHorizontalAppFilter(this.state.contextID, calendarID));
        }
        writeJsonFile();
    }

    removeHorizontalFilter(calendarID) {
        if (this.state.context == "category") {
            this.props.dispatch(removeHorizontalCategoryFilter(this.state.contextID, calendarID));
        } else {
            this.props.dispatch(removeHorizontalAppFilter(this.state.contextID, calendarID));
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
        let horizontalFilter = contextObject.settings.calendar.filterSettings.horizontal;
        return (
            <View style={{ backgroundColor: colors.background }}>
                <PreferencesHeader title="Kalenderfilter" />
                <FlatList
                    data={this.state.calendars}
                    extraData={this.props}
                    keyExtractor={(item, index) => item.id}
                    renderItem={({ item }) => {
                        let itemChecked = horizontalFilter.some((element) => {
                            return element == item.id
                        })

                        return (
                            <List.Item
                                title={item.title}
                                right={() => <Checkbox status={itemChecked ? 'checked' : 'unchecked'} />}
                                onPress={
                                    () => {
                                        if (!itemChecked) {
                                            this.addHorizontalFilter(item.id);
                                        } else {
                                            this.removeHorizontalFilter(item.id)
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

export default connect(mapStateToProps)(withTheme(CalendarItemsFilter));