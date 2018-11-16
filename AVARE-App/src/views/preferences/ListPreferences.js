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
//Listing all Preferences for one Category (showing state like filtered, blocked, ...) for each
//Listing all Preferences for one App (showing state like filtered, blocked, ...) for each
import React from 'react';
import { ScrollView, FlatList } from 'react-native';
import { withNavigation } from 'react-navigation';
import { settingsSaved, settingsLive, settingsIdentity, states } from '../../storage/preferenceDescriptions';
import { withTheme, List, Divider, Text } from 'react-native-paper';
import { connect } from 'react-redux';

class ListPreferences extends React.Component {
  constructor(props) {
    super(props);

    let contextID = this.props.navigation.state.params.contextID;
    let context = this.props.navigation.state.params.context;
    
    this.state = {
      context,
      contextID,
    }
  }

  render() {
    const { colors } = this.props.theme;
    let styleNotImplemented = { backgroundColor: 'lightgrey' };

    //TODO: this should probably happen in another life cycle method
    let contextObject;
    if (this.state.context == "category") {
      contextObject = this.props.categories.find((element) => { return element._id == this.state.contextID });
    } else {
      contextObject = this.props.apps.find((element) => { return element._id == this.state.contextID });
    }
    let settings = contextObject.settings;

    return (
      <ScrollView
        style={{ backgroundColor: colors.background }}
      >
        <List.Section title="Gespeicherte Daten">
          <Divider />
          <FlatList
            renderItem={({ item }, index, section) => {
              let stateDescription = !item.implemented ? "N/A" : states[settings[item.key].status];
              return (
                <List.Item
                  onPress={() => {
                    if (item.implemented) {
                      this.props.navigation.navigate('Preference', { context: this.state.context, contextID: this.state.contextID, settingKey: item.key, name: item.name })
                    }
                  }}

                  style={item.implemented ? {} : styleNotImplemented}
                  title={item.name + " (" + stateDescription +")"}
                  description={item.description}
                  left={props => <List.Icon {...props} icon={item.icon} />}
                />)
            }
            }
            data={settingsSaved}
            extraData={this.props}
            keyExtractor={(item, index) => item.key}
            ItemSeparatorComponent={() => { return (<Divider />) }}
          />
        </List.Section>
        <List.Section title="Live-Daten">
          <Divider />
          <FlatList
            renderItem={({ item }, index, section) => {
              let stateDescription = !item.implemented ? "N/A" : states[settings[item.key].status];
              return (
                <List.Item
                  onPress={() => {
                    if (item.implemented) {
                      this.props.navigation.navigate('Preference', { context: this.state.context, contextID: this.state.contextID, settingKey: item.key, name: item.name })
                    }
                  }}
                  style={item.implemented ? {} : styleNotImplemented}
                  title={item.name + " (" + stateDescription +")"}
                  description={item.description}
                  left={props => <List.Icon {...props} icon={item.icon} />}
                />)
            }
            }
            data={settingsLive}
            extraData={this.props}
            keyExtractor={(item, index) => item.key}
            ItemSeparatorComponent={() => { return (<Divider />) }}
          />
        </List.Section>
        <List.Section title="Identität">
          <Divider />
          <FlatList
            renderItem={({ item }, index, section) => {
              let stateDescription = !item.implemented ? "N/A" : states[settings[item.key].status];
              return (
                <List.Item
                  onPress={() => {
                    if (item.implemented) {
                      this.props.navigation.navigate('Preference', { context: this.state.context, contextID: this.state.contextID, settingKey: item.key, name: item.name })
                    }
                  }}
                  style={item.implemented ? {} : styleNotImplemented}
                  title={item.name + " (" + stateDescription +")"}
                  description={item.description}
                  left={props => <List.Icon {...props} icon={item.icon} />}
                />)
            }
            }
            data={settingsIdentity}
            extraData={this.props}
            keyExtractor={(item, index) => item.key}
            ItemSeparatorComponent={() => { return (<Divider />) }}
          />
        </List.Section>
      </ScrollView>
    );

  }
}
const mapStateToProps = (state) => {
  return {
    categories: state.categories,
    apps: state.apps
  };
}


export default connect(mapStateToProps)(withNavigation(withTheme(ListPreferences)));