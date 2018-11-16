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
import { Container, Header, Left, Body, Title, Right, Button, Icon, Tabs, Tab, Text, ScrollableTab, View } from 'native-base';
import ListPreferences from './ListPreferences';
import CategoryApps from './CategoryApps';
import { createMaterialTopTabNavigator } from 'react-navigation';

import { connect } from 'react-redux';


const CategoryTabs = createMaterialTopTabNavigator({
    CategoryPreferences: {
        screen: ListPreferences,
        navigationOptions: {
            title: 'Einstellungen',
        }
    },
    CategoryApps: {
        screen: CategoryApps,
        navigationOptions: {
            title: 'Apps'
        }
    }
},{
    tabBarOptions: {
        upperCaseLabel: false,
        indicatorStyle: {
            backgroundColor: '#ffff98',
        },
        labelStyle: {
            fontFamily: 'Rubik-Medium',
            color: '#352a15'
        },
        style: {
            backgroundColor: '#fed767',
        }
    }
})

//class CategoryTabsOld extends React.Component {
//
//    constructor(props) {
//        super(props);
//
//        //TODO: passing redux store to props then to state here seems ugly...
//        let categoryID = this.props.navigation.state.params.categoryID;
//        this.state = {
//            category : this.props.categories.find(function(element) { return element._id == categoryID; })
//        }
//    }
//
//    render() {
//        return (
//            <Container>
//                <Header hasTabs>
//                    <Left>
//                        <Button transparent
//                            onPress={() => this.props.navigation.goBack()}
//                        >
//                            <Icon name="arrow-back" />
//                        </Button>
//                    </Left>
//                    <Body>
//                        <Title>{ this.state.category.name }</Title>
//                    </Body>
//                    <Right />
//                </Header>
//                <Tabs>
//                    <Tab heading="Einstellungen" renderTabBar={() => <ScrollableTab />} >
//                        {/* <ListPreferences context="category" settings={this.state.category.settings} contextID={this.state.category._id} /> */}
//                        <ListPreferences context="category"  contextID={this.state.category._id} />
//                    </Tab>
//                    <Tab heading="Apps">
//                        <CategoryApps categoryID={this.state.category._id} />
//                    </Tab>
//                </Tabs>
//            </Container>
//        )
//    }
//}
//
//const mapStateToProps = (state) => {
//  return {
//    categories: state.categories
//  };
//}
//
//export default connect(mapStateToProps)(CategoryTabs);
export default CategoryTabs;