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
import React, { Component} from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View,
    TextInput,
    NetInfo,
  
} from 'react-native';
import { connect } from 'react-redux';
import { getProfile, uploadProfile, setPreferences} from '../../redux/modules/communication/actions'
import {onConnectivityChange} from '../../../App'
import { getEncryptedPreferences } from '../../encryption/Encryptor';

import { Container, Header, Body, Left, Right, Icon, Title, Button} from 'native-base';

class Items extends Component {

    constructor(props) {
        super(props);

        this.state ={
            text: "",
        }
        
        NetInfo.addEventListener(
            'connectionChange',
            onConnectivityChange
        );
    }

    /*componentWillMount() {
        console.log('Mounting Items')
        this.setState({
            text:""
        })
        
        NetInfo.addEventListener(
            'connectionChange',
            onConnectivityChange
        );

    }*/

    componentWillUnmount() {
        NetInfo.removeEventListener(
            'connectionChange',
            onConnectivityChange
        )
    }

    /*componentWillReceiveProps(nextProps) {
        this.setState({
            text: nextProps.preferences,
        })
        
    }*/
   

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
                        <Title>{ "Items"}</Title>
                    </Body>
                    <Right />
                </Header>
                <View style={styles.container}>
                <TextInput onChangeText={(text) => {
                    this.setState({ ...this.state, text: text })
                    //
                }
                } value={this.state.text}
                />
                <Button block onPress={() => {
                    console.log("id: " + this.props.id)
                    console.log("date: " + this.props.time)
                    encryptedPreferences = getEncryptedPreferences();
                    console.log("text: " + encryptedPreferences)
                    this.props.dispatch(uploadProfile(this.props.id, this.props.time, encryptedPreferences));
                }
                }>
                    <Text>Upload Profile</Text>
                </Button>
                <Button block onPress={() => {
                    this.props.dispatch(setPreferences(this.state.text, getISODate(new Date())));
                }}
                >
                    <Text>Update Preferences</Text>
                </Button>

                <Button block onPress={() => {
                    this.props.dispatch(getProfile());
                }}
                >
                    <Text>Neues Profil</Text>
                </Button>
                </View>
            </Container>
            
        );
    }
};



const mapStateToProps = (state) => {
    console.log('Mapping items')
    return {
        id: state.communication.profile,
        preferences: state.communication.preferences,
        time: state.communication.time,

    };
}

export const getISODate = (date) => {
    console.log(date);
    date = date - date.getTimezoneOffset() * 60 * 1000
    console.log(date);
    newDate = new Date(date)
    var timestamp = newDate.toISOString();

    timestamp = timestamp.substring(0, timestamp.length - 1);
    timestamp = timestamp.replace(":", "-");
    timestamp = timestamp.replace(":", "-");
    timestamp = timestamp.replace(".", "-");
    return timestamp;
}
const styles = StyleSheet.create({
    container: {
        backgroundColor: '#ecf0f1',
        flex: 1,
    },
    toolbar: {
        backgroundColor: '#3498db',
        color: '#fff',
        fontSize: 20,
        textAlign: 'center',
        padding: 10,
        ...Platform.select({
            ios: {
                paddingTop: 30,
            },
            android: {
                paddingTop: 10,
            },
        }),
    },
});
export default connect(mapStateToProps)(Items);