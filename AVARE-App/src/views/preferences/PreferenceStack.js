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
// all screens related to privacy preferences
// User can navigate to category screen, to a list of preferences for one app
// or to a screen showing one preference
import React from 'react';

//import { Footer, FooterTab, Button, Text } from 'native-base';
import { createMaterialTopTabNavigator, createStackNavigator, withNavigation } from 'react-navigation';

import ListApps from './ListApps';
import ListCategories from './ListCategories';

import CategoryTabs from './CategoryTabs';
import GenericPreferences from './datatypes/GenericPreferences';
import ContactsFilterPreferences from './datatypes/ContactsFilter';
import ContactFieldsFilter from './datatypes/ContactFieldsFilter';
import ContactItemsFilter from './datatypes/ContactItemsFilter';
import LocationFilter from './datatypes/LocationFilter';
import CalendarFilter from './datatypes/CalendarFilter';
import CalendarFieldsFilter from './datatypes/CalendarFieldsFilter';
import CalendarItemsFilter from './datatypes/CalendarItemsFilter';
import AppPreferences from './AppPreferences';
import HomeHeader from '../_shared/HomeHeader';
import PreferencesHeader from '../_shared/PreferencesHeader';

//const MainTabs = createMaterialTopTabNavigator(
//    {
//        ListApps: {
//            screen: ListApps,
//        },
//        ListCategories: {
//            screen: ListCategories,
//        }
//    },
//    {
//        initialRouteName: 'ListCategories',
//        // tabBarComponent: props => {
//            // return (
//                // <Footer>
//                    // <FooterTab>
//                        // <Button
//                            // active={props.navigation.state.index === 0}
//                            // onPress={() => props.navigation.navigate('ListApps')}
//                        // >
//                            // <Text>Apps</Text>
//                        // </Button>
//                        // <Button
//                            // active={props.navigation.state.index === 1}
//                            // onPress={() => props.navigation.navigate('ListCategories')}
//                        // >
//                            // <Text>Kategorien</Text>
//                        // </Button>
//                    // </FooterTab>
//                // </Footer>
//            // )
//        // }
//    }
//);
const PreferenceStack = createStackNavigator(
    {
        Main: {
            screen: ListCategories 
        },
        Apps: {
            screen: ListApps
        },
        Category: {
            screen: CategoryTabs,
            navigationOptions: ({ navigation }) => {
                return {
                    header: <PreferencesHeader hasTabs={true} title={navigation.getParam('categoryName', 'Kategorie')} />
                }
            }
        },
        Preference: {
            screen: GenericPreferences
        },
        ContactsFilter: {
            screen: ContactsFilterPreferences
        },
        ContactFieldsFilter: {
            screen: ContactFieldsFilter
        },
        ContactItemsFilter: {
            screen: ContactItemsFilter
        },
        LocationFilter: {
            screen: LocationFilter
        },
        CalendarFilter: {
            screen: CalendarFilter
        },
        CalendarFieldsFilter: {
            screen: CalendarFieldsFilter
        },
        CalendarItemsFilter: {
            screen: CalendarItemsFilter
        },
        AppPreferences: {
            screen: AppPreferences,
            navigationOptions: ({ navigation }) => {
                return {
                    header: <PreferencesHeader title={navigation.getParam('appName', 'App')} />
                }
            }
        },

    },
    {
        initialRouteName: 'Main',
        navigationOptions: {
            header: null,
        }
    }
);

export default withNavigation(PreferenceStack);