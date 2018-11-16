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
import React from 'react'
// TODO: probably rather use native-base button for consistency, but right now it's not shown
import { View } from 'react-native';
// import { StackActions, NavigationActions } from 'react-navigation';

import { withTheme, Button, Text, Title, Paragraph } from 'react-native-paper';
import { addCategory } from '../../redux/modules/categories/actions';
import initialPreferences from '../../storage/initalPreferences';
import { writeJsonFile } from '../../storage/RNFSControl';
import { connect } from 'react-redux';
import uuid from 'uuid/v4';


//TODO: hmmm... as the app should also work offline only, we need to think about the purpose of getProfile
// import { getProfile } from '../persistence/modules/communication/actions';


class WelcomeScreen extends React.Component {

    constructor(props) {
        super(props);
    };

    //loadProfile = async function () {
    //    console.log('...loading');
    //    // await this.props.dispatch(getProfile())
    //    // this.props.navigation.dispatch(resetHome);
    //};

    initProfile() {
        console.log('Initialize new profile');

        // TODO: Having to manually generate an ID and writeJsonFile is not DRY. Best practice for this?
        initialPreferences.categories.forEach((category) => {
            category._id = uuid();
            this.props.dispatch(addCategory(category));
        });
        writeJsonFile();

        this.props.navigation.navigate('Main');
    }

    render() {

        const { colors } = this.props.theme;
        return (
            <View style={{ backgroundColor: colors.primary, flex: 1, flexDirection: 'column', justifyContent: 'center' }}>

                <Title style={{ textAlign: 'center' }}>Willkommen zu Avare</Title>

                <Paragraph style={{ paddingLeft: 16, paddingRight: 16, textAlign: 'center' }}>
                    Scheinbar wird die App zum ersten Mal genutzt.
                </Paragraph>
                <Paragraph style={{ paddingLeft: 16, paddingRight: 16, textAlign: 'center' }}>Du kannst ein neues Einstellungs-Profil erstellen,
                    oder deine Avare-Einstellungen von einem anderen Gerät synchronisieren.
                </Paragraph>

                <View style={{ marginTop: 16, flexDirection: 'row', justifyContent: 'center', alignItems: 'center'}}>
                    <Button mode='contained' onPress={() => { this.initProfile() }}>Neues Profil</Button>

                    <Button disabled={true} style={{ opacity: 0.5 }}
                    //onPress={() => this.props.navigation.navigate('Transfer')}
                    >
                    Sync
                    </Button>
                </View>
            </View>
        );
    }
}

export default connect()(withTheme(WelcomeScreen));