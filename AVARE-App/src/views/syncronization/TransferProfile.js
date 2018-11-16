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
*/import React, { Component } from 'react';
import { View, Text, TextInput, Button, StyleSheet, NetInfo, } from 'react-native';
import { connect } from 'react-redux'
import { setProfile, loadPreferences, setTime} from '../../redux/modules/communication/actions'
import { onConnectivityChange } from '../../../App'
import {getISODate} from '../../functions/getIsoDate'

import {writeJsonFile} from '../../storage/RNFSControl' //'../features/Storage/RNFSControl';



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

  componentWillUnmount() {
    NetInfo.removeEventListener(
      'connectionChange',
      onConnectivityChange
    )
  }


  onConnectivityChange = (reach) => {
    console.log('Network change');
    console.log(reach)
    this.props.dispatch(setConnectivity(reach));
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.toolbar}>Transfer Screen</Text>
        <TextInput onChangeText={(text) => {
          this.setState({ ...this.state, profile: text })

        }
        } value={this.state.profile}
        />
        <Button title='Commit profile' onPress={() => {
          this.props.dispatch(setProfile(this.state.profile))
          this.props.dispatch(loadPreferences(this.state.profile, getISODate(new Date('01.01.1971')))) //force download of preferences 
          this.props.dispatch(setTime(getISODate(new Date())))
          this.props.navigation.navigate('Home');
        }}
        />
        <Button title='Create JSON' onPress={() => {
          writeJsonFile();
        }}
        />
        <Button title='Go home' onPress={()=> {
            this.props.navigation.navigate('Home');
        }}
        />
      </View>
    );
  }
}

const mapStateToProps = (state) => {
  return {
  }
}

export default connect(mapStateToProps)(TransferScreen)


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

  },
});
