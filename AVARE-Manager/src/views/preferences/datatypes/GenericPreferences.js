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
import { ListItem, Text, Right, Switch, Body } from 'native-base';
import { View } from 'react-native';
import { withTheme, List, RadioButton, IconButton } from 'react-native-paper';
import PreferencesHeader from '../../_shared/PreferencesHeader';
import { withNavigation } from 'react-navigation';


import { writeJsonFile } from '../../../storage/RNFSControl';
import { updateSettingStatusOfCategory } from '../../../redux/modules/categories/actions';
import { updateSettingStatusOfApp } from '../../../redux/modules/apps/actions';
import { connect } from 'react-redux';

class GenericPreferences extends React.Component {
  constructor(props) {
    super(props);

    let settingsName = this.props.navigation.state.params.name;
    let contextID = this.props.navigation.state.params.contextID;
    let context = this.props.navigation.state.params.context;
    let settingKey = this.props.navigation.state.params.settingKey;
    let contextObject;
    if (context == "category") {
      contextObject = this.props.categories.find(function (element) { return element._id == contextID; })
    } else { // context == "app"
      contextObject = this.props.apps.find(function (element) { return element._id == contextID; })
    }
    this.state = {
      contextID,
      context,
      settingKey,
      setting: contextObject.settings[settingKey],
      name: settingsName
    }
  }

  //TODO: this seems overtly verbose, passing data from redux to props to state to props again...better solution?
  //updating the setting to immediately reflect change in radio buttons
  static getDerivedStateFromProps(props, state) {
    let settingKey = props.navigation.state.params.settingKey;
    let contextObject;
    if (state.context == "category") {
      contextObject = props.categories.find(function (element) { return element._id == state.contextID; })
    } else { // context == "app"
      contextObject = props.apps.find(function (element) { return element._id == state.contextID; })
    }
    // let category = props.categories.find(function (element) { return element._id == state.contextID; })
    return {
      ...state,
      setting: contextObject.settings[settingKey]
    }
  }

  updatePreference(preference) {
    if (this.state.context == "category") {
      this.props.dispatch(updateSettingStatusOfCategory(this.state.contextID, this.state.settingKey, preference));
    } else {
      console.log('updating app');
      this.props.dispatch(updateSettingStatusOfApp(this.state.contextID, this.state.settingKey, preference));
    }
    writeJsonFile();
  }

  // TODO: maybe we can have a generic FilterPreferences Screen that adapts according to Context
  render() {
    const { colors } = this.props.theme;
    let switchState = (this.state.setting.status == "inherit");
    return (
      <View
        style={{ backgroundColor: colors.background }}
      >
        <PreferencesHeader title={this.state.name} />
        {
          (this.state.context == "app") && // only show if we are looking at app settings
          <ListItem onPress={() => console.log('List item pressed')}>
            <Body>
              <Text>Einstellungen aus Kategorie</Text>
            </Body>
            <Right>
              <Switch value={switchState} onValueChange={(value) => {
                console.log('newValue: ' + value);
                if (value) {
                  console.log('setting to inherit')
                  this.updatePreference('inherit');
                } else {
                  console.log('setting to blocked')
                  this.updatePreference('blocked');
                }
              }} />
            </Right>
          </ListItem>
        }
        <List.Section
          title="Zugriff auf Daten ..."
          pointerEvents={(this.state.setting.status == "inherit") ? 'none' : 'auto'} style={(this.state.setting.status == "inherit") ? { opacity: 0.5 } : {}}
        >
          <List.Item
            left={() => <RadioButton status={this.state.setting.status == 'blocked' ? 'checked' : 'unchecked'} />}

            title="Blockieren"
            onPress={this.updatePreference.bind(this, 'blocked')}
          >
          </List.Item>
          <List.Item
            style={{padding: 10}}
            left={(props) => <RadioButton style={{backgroundColor: 'red'}} status={this.state.setting.status == 'filtered' ? 'checked' : 'unchecked'} />}
            title="Filtern"
            onPress={this.updatePreference.bind(this, 'filtered')}
            right={(props) =>
              <IconButton {...props} icon="settings" mode="text" size={20}
                onPress={() => {
                  if (this.state.settingKey == "contacts") {
                    this.props.navigation.navigate('ContactsFilter', { contextID: this.state.contextID, context: this.state.context })
                  } else if (this.state.settingKey == "location") {
                    this.props.navigation.navigate('LocationFilter', { contextID: this.state.contextID, context: this.state.context })
                  } else if (this.state.settingKey == "calendar") {
                    this.props.navigation.navigate('CalendarFilter', { contextID: this.state.contextID, context: this.state.context })
                  }

                }}
              />
            }
          />
          <List.Item
            left={() => <RadioButton status={this.state.setting.status == 'enabled' ? 'checked' : 'unchecked'} />}
            title="Freigeben"
            onPress={this.updatePreference.bind(this, 'enabled')}
          >
          </List.Item>
        </List.Section>
      </View>
    );

  }
}

const mapStateToProps = (state) => {
  return {
    categories: state.categories,
    apps: state.apps
  };
}

export default connect(mapStateToProps)(withTheme(withNavigation(GenericPreferences)));