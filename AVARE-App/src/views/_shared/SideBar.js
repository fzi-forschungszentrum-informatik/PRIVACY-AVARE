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
import { ScrollView } from 'react-native';
import { DrawerActions, SafeAreaView } from 'react-navigation';
import { Drawer } from 'react-native-paper';

export default class SideBar extends Component {
    //TODO: https://codeburst.io/custom-drawer-using-react-navigation-80abbab489f7
    render() {
        let mainSection = this.props.items.slice(0, 1);
        let infoSection = this.props.items.slice(1, 3);
        let preliminarySection = this.props.items.slice(3);
        return(
            <ScrollView>
                <SafeAreaView style={{ flex: 1}} forceInset={{ top: 'always', horizontal: 'never' }}>
                    <Drawer.Section>
                        {
                            mainSection.map((item) => {

                                return (<Drawer.Item key={item.key} label="Meine Präferenzen"
                                    onPress={() => {
                                        this.props.navigation.navigate(item.routeName);
                                        this.props.navigation.dispatch(DrawerActions.closeDrawer());
                                    }} />)
                            })
                        }
                    </Drawer.Section>
                    <Drawer.Section>
                        {
                            infoSection.map((item, index) => {
                                //TODO: this is currently an ugly solution to custom labels
                                let label = (index === 0) ? 'Datenschutzerklärung' : 'Experteneinstellungen'//: 'Über AVARE';
                                return (<Drawer.Item key={item.key} label={label}
                                    onPress={() => {
                                        this.props.navigation.navigate(item.routeName);
                                        this.props.navigation.dispatch(DrawerActions.closeDrawer());
                                    }} />)
                            })
                        }
                    </Drawer.Section>
                    
                    {/*}
                    <Drawer.Section title="Testscreens">
                        {
                            preliminarySection.map((item) => {

                                return (<Drawer.Item key={item.key} label={item.key}
                                    onPress={() => {
                                        this.props.navigation.navigate(item.routeName);
                                        this.props.navigation.dispatch(DrawerActions.closeDrawer());
                                    }} />)
                            })
                        }
                    </Drawer.Section>*/}

                </SafeAreaView>
            </ScrollView>
        )
    }
}