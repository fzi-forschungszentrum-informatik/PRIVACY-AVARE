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
import { Button } from 'react-native';
import { View, Text } from 'native-base';
// import { StackActions, NavigationActions } from 'react-navigation';

import { connect } from 'react-redux'
import { addCategory } from '../../redux/modules/categories/actions';
import initialPreferences from '../../storage/initalPreferences';
import { writeJsonFile,readJsonFile } from '../../storage/RNFSControl';
import uuid from 'uuid/v4';

import styles from './styles'

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
        //<Image source={require('../styles/Avare_Logo.png')} stlye = {styles.logo}/>
        return (
            <View style={styles.container}>

                <Text style={styles.header}>Willkommen zum Avare-Manager</Text>

                <Text style={styles.normalText}>Scheinbar wird die App zum ersten Mal genutzt</Text>
                <Text style={styles.normalText}>Möchten Sie ein neues Profil erstellen, oder ein bestehendes Nutzen?</Text>

                <View style={styles.buttonContainer}>
                    <View style={{ padding: 10 }}>
                        <Button title="Neues Profil" onPress={() => { this.initProfile() }}></Button>
                    </View>

                    <View style={{ padding: 10 }}>
                        <Button title="Bestehendes Profil" disabled={false}
                            // onPress={() => {this.props.navigation.dispatch(resetTransfer)}}
                            onPress={() => this.props.navigation.navigate('Transfer')}
                        >
                        </Button>
                    </View>

                </View>
            </View>
        );
    }
}

//const resetHome = StackActions.reset({
//    index: 1,
//    actions: [
//        NavigationActions.navigate({ routeName: 'Home' }),
//        NavigationActions.navigate({ routeName: 'PrototypHome' }),
//
//    ],
//});
//
//const resetTransfer = StackActions.reset({
//    index: 2,
//    actions: [
//        NavigationActions.navigate({ routeName: 'Home' }),
//        NavigationActions.navigate({ routeName: 'PrototypHome' }),
//        NavigationActions.navigate({ routeName: 'PrototypTransferScreen' }),
//
//    ],
//});

//TODO: is mapping to state needed here?
//const mapStateToProps = (state) => {
//    return {
//
//    }
//}
// export default connect(mapStateToProps)(WelcomeScreen);
export default connect()(WelcomeScreen);