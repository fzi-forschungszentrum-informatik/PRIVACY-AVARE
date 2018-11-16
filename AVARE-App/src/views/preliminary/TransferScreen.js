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
import { View, Text, TextInput,StyleSheet, NetInfo, } from 'react-native';
import { connect } from 'react-redux'
import { setProfile, loadPreferences, setTime, setPreferences } from '../../redux/modules/communication/actions'
import { onConnectivityChange } from '../../../App'
import {getISODate} from '../../functions/getIsoDate'

import {test} from '../../redux/modules/apps/index'
import { basic, writeJsonFile, deleteFile, readJsonFile } from '../../storage/RNFSControl';
import { addCategorie, updateSettingStatusOfCategory, addDefaultCategory } from '../../redux/modules/categories/actions';
import { createCategory } from '../../redux/modules/categories';
import { Container, Header, Body, Left, Right, Icon, Title, Button} from 'native-base';


class TransferScreen extends Component {
  
  constructor(props) {
    super(props);

    this.state = {
      profile: "",
    }
    NetInfo.addEventListener(
      'connectionChange',
      onConnectivityChange
    );
  }

  /*
  componentWillMount() {
    this.setState({
      profile: ""
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

  /*
  componentWillReceiveProps(nextProps) {
    if (this.props.profile !== nextProps.profile) {
      this.setState({
        profile: nextProps.profile
      })
    }
  }
  */

  onConnectivityChange = (reach) => {
    console.log('Network change');
    console.log(reach)
    this.props.dispatch(setConnectivity(reach));
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
                        <Title>{ "TransferScreen"}</Title>
                    </Body>
                    <Right />
                </Header>
        <View style={styles.container}>
         
          <TextInput onChangeText={(text) => {
            this.setState({ ...this.state, profile: text })

          }
          } value={this.state.profile}
          />
          <Button block onPress={() => {
            this.props.dispatch(setProfile(this.state.profile))
            //this.props.dispatch(loadPreferences(this.state.profile, getISODate(new Date('01.01.1971')))) //force download of preferences 
            this.props.dispatch(setTime(getISODate(new Date())))
          }}
          >
            <Text> Commit profile</Text>
          </Button>
          <Button block onPress={() => {
            test();
          }}
          >
            <Text> Test Json </Text>
          </Button>
          
          <Button block onPress={() => {
            basic();
          }}
          >
            <Text>Test show Files</Text>
          </Button>

          <Button block onPress={() => {
            this.props.dispatch(addCategorie(createCategory(1)));
          }}
          >
            <Text>Add Category</Text>
          </Button>

          <Button block onPress={() => {
            this.props.dispatch(updateSettingStatusOfCategory(1, "contacts","blocked"));
          }}
          >
            <Text>Block Category</Text>
          </Button>

          <Button block onPress={() => {
            writeJsonFile();
          }}
          >
            <Text>Create JSON</Text>
          </Button>

          <Button block onPress={() => {
            readJsonFile();
          }}
          >
            <Text>Read JSON</Text>
          </Button>

          <Button block onPress={() => {
            deleteFile('preferences.json');
          }}
          >
            <Text>Delete File</Text>
          </Button>

          <Button block onPress={() => {
            preferenceObject = {
              apps: this.props.apps,
              categories : this.props.categories,
            }
            this.props.dispatch(setPreferences(JSON.stringify(preferenceObject), getISODate(new Date())));
          }}
          >
            <Text>Create Preferences</Text>
          </Button>

          <Button block onPress={() => {
            
            this.props.dispatch(addDefaultCategory(27,"Test"));
          }}
          >
            <Text>Add new</Text>
          </Button>

          <View style={styles.infoPanel}>
            <Text style={styles.infoText}>Gespeichertes Profil:</Text>
            <Text style={styles.infoStatus}>{this.props.profile}</Text>
          </View>
        </View>
      </Container>
      
    );
  }
}

const mapStateToProps = (state) => {
  return {
    profile: state.communication.profile,
    apps: state.apps,
    categories : state.categories,
  }
}

export default connect(mapStateToProps)(TransferScreen)


const styles = StyleSheet.create({
  container: {
    backgroundColor: '#ecf0f1',
    flex: 1,
    justifyContent: 'space-between',
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
    backgroundColor: '#3498db',
    color: '#fff',
    fontSize: 20,
    textAlign: 'center',
    padding: 10,

  },
});
