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
import { View, Text, StyleSheet, NetInfo, CheckBox, ToastAndroid} from 'react-native';
import { connect } from 'react-redux';

import { setConnectivity } from '../../redux/modules/network/actions';
import { onConnectivityChange} from '../../../App'

import { Container, Header, Body, Left, Right, Icon, Title, Button} from 'native-base';

class HomeScreen extends Component {


    constructor(props) {
        super(props);

        NetInfo.addEventListener(
            'connectionChange',
            onConnectivityChange
        );
        NetInfo.isConnected.fetch().then((connectionInfo) => {
            console.log('Checking State');
            this.props.dispatch(setConnectivity(connectionInfo));
        });
        
    }
    /*
    componentWillMount() {
        NetInfo.addEventListener(
            'connectionChange',
            onConnectivityChange
        );
        NetInfo.isConnected.fetch().then((connectionInfo) => {
            console.log('Checking State');
            this.props.dispatch(setConnectivity(connectionInfo));
        })
        onLoad();
    }
    */


    componentWillUnmount() {
        NetInfo.removeEventListener(
            'connectionChange',
            onConnectivityChange
        )
    }

    render() {
        return (
            <Container>
                <Header hasTabs>
                    <Left>
                        <Button transparent
                            onPress={() => this.props.navigation.goBack()}
                        >
                            <Icon name="arrow-back" />
                        </Button>
                    </Left>
                    <Body>
                        <Title>{ "Prototyp Home"}</Title>
                    </Body>
                    <Right />
                </Header>
                <View style={styles.container}>
                <Text style={styles.toolbar}>Willkommen zum Avare Manager</Text>
                <View style={styles.infoPanel}>
                    <Text style={styles.infoText}>Online</Text>
                    <CheckBox value={this.props.isOnline} disabled={true} flex={0.5} padding={10} />
                    <Text flex={0.5} padding={10}>{this.props.pending}</Text>
                </View>
                <View style={styles.infoPanel}>
                    <Text style={styles.infoText}>Gespeichertes Profil:</Text>
                    <Text style={styles.infoStatus}>{this.props.profile}</Text>
                </View>
                <View style={styles.infoPanel}>
                    <Text style={styles.infoText}>Letzte Änderung:</Text>
                    <Text style={styles.infoStatus}>{this.props.time}</Text>
                </View>
                <Button
                    block
                    onPress={() => this.props.navigation.navigate('PrototypItems')}
                >
                    <Text>Go to Items</Text>
                </Button>
                <Button
                    block
                    onPress={() => this.props.navigation.navigate('PrototypTransferScreen')}
                >
                    <Text>Go to Transfer</Text>
                </Button>

            </View>
            </Container>
            
        );
    }
}

const mapStateToProps = (state) => {
    console.log('Mapping')

    return {
        profile: state.communication.profile,
        time: state.communication.time,
        isOnline: state.network.isOnline,
        pending: state.network.pending
    };
}

const styles = StyleSheet.create({
    container: {
        backgroundColor: 'rgb(247,212,146)',
        flex: 1,
        justifyContent: 'flex-start'
    },
    infoPanel: {
        flexDirection: 'row',
    },
    infoText: {
        flex: 1,
        backgroundColor: '#3498db',
        color: '#fff',
        textAlign: 'left',
        fontSize: 15,
        padding: 10,
    },
    infoStatus: {
        flex: 1,
        backgroundColor: 'grey',
        color: '#fff',
        textAlign: 'right',
        padding: 10,
    },
    toolbar: {
        backgroundColor: 'rgb(214,103,50)',
        color: '#fff',
        fontSize: 20,
        textAlign: 'center',
        textAlignVertical: 'center',
        padding: 10,
    },
    button: {
        backgroundColor: 'red',
        color: '#fff',
        fontSize: 20,
        textAlign: 'center',
        padding: 10,
    }
});

export default connect(mapStateToProps)(HomeScreen);