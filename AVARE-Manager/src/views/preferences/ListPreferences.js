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

class ListPreferences extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      context: this.props.navigation.state.params.context,
      contextID: this.props.navigation.state.params.contextID
    }
  }

  render() {
    const { colors } = this.props.theme;
    let styleNotImplemented = { backgroundColor: 'lightgrey' };

    return (
      <ScrollView
        style={{ backgroundColor: colors.background }}
      >
        <List.Section title="Gespeicherte Daten">
          <Divider />
          <FlatList
            renderItem={({ item }, index, section) => {
              return (
                <List.Item
                  onPress={() => {
                    if (item.implemented) {
                      this.props.navigation.navigate('Preference', { context: this.state.context, contextID: this.state.contextID, settingKey: item.key, name: item.name })
                    }
                  }}

                  style={item.implemented ? {} : styleNotImplemented}
                  title={item.name}
                  description={item.description}
                  left={props => <List.Icon {...props} icon={item.icon} />}
                // right={() => <Text>nicht~{"\n"}implementiert</Text>}
                />)
            }
            }
            data={settingsSaved}
            keyExtractor={(item, index) => item.key}
            ItemSeparatorComponent={() => { return (<Divider />) }}
          />
        </List.Section>
        <List.Section title="Live-Daten">
          <Divider />
          <FlatList
            renderItem={({ item }, index, section) => {
              return (
                <List.Item
                  onPress={() => {
                    if (item.implemented) {
                      this.props.navigation.navigate('Preference', { context: this.state.context, contextID: this.state.contextID, settingKey: item.key, name: item.name })
                    }
                  }}
                  style={item.implemented ? {} : styleNotImplemented}
                  title={item.name}
                  description={item.description}
                  left={props => <List.Icon {...props} icon={item.icon} />}
                // right={() => <Text>nicht~{"\n"}implementiert</Text>}
                />)
            }
            }
            data={settingsLive}
            keyExtractor={(item, index) => item.key}
            ItemSeparatorComponent={() => { return (<Divider />) }}
          />
        </List.Section>
        <List.Section title="Identität">
          <Divider />
          <FlatList
            renderItem={({ item }, index, section) => {
              return (
                <List.Item
                  onPress={() => {
                    if (item.implemented) {
                      this.props.navigation.navigate('Preference', { context: this.state.context, contextID: this.state.contextID, settingKey: item.key, name: item.name })
                    }
                  }}
                  style={item.implemented ? {} : styleNotImplemented}
                  title={item.name}
                  description={item.description}
                  left={props => <List.Icon {...props} icon={item.icon} />}
                // right={() => <Text>nicht~{"\n"}implementiert</Text>}
                />)
            }
            }
            data={settingsIdentity}
            keyExtractor={(item, index) => item.key}
            ItemSeparatorComponent={() => { return (<Divider />) }}
          />
        </List.Section>

        {/* <List> */}
        {/* <Separator bordered> */}
        {/* <Text>GESPEICHERTE DATEN</Text> */}
        {/* </Separator> */}
        {/* { */}
        {/* // settingsSaved.map((setting, num) => { */}
        {/* // return ( */}
        {/* //  */}
        {/* // <PreferenceItem 
                  // key={num}
                  // implemented={setting.implemented}
                  // name={setting.name}
                  // description={setting.description}
                  // icon={setting.icon}
                  // state={setting.state}
                  // isLast={num === (settingsSaved.length - 1)}
                  // nav={this.props.navigation}
// 
                  // TODO: this will only work with the implemented calendar, contacts and location settings
                  // for other cases, one settings entry in the JSON file is not necessarily equal to a setting in the UI
                  // settingKey={setting.key}
                  // contextID={this.props.contextID}
                  // context={this.props.context}
                // />
// 
              // )
            // })
          // }*/}
        {/* <Separator bordered> */}
        {/* <Text>LIVE-DATEN</Text> */}
        {/* </Separator> */}
        {/* { 
            // settingsLive.map((setting, num) => {
// 
              // return (
                // <PreferenceItem
                  // key={num}
                  // name={setting.name}
                  // implemented={setting.implemented}
                  // description={setting.description}
                  // icon={setting.icon}
                  // state={setting.state}
                  // isLast={num === (settingsLive.length - 1)}
                  // nav={this.props.navigation}
// 
                  // TODO: this will only work with the implemented calendar, contacts and location settings
                  // for other cases, one settings entry in the JSON file is not necessarily equal to a setting in the UI
                  // settingKey={setting.key}
                  // contextID={this.props.contextID}
                  // context={this.props.context}
                // />
              // )
            // })
          // }*/}
        {/* <Separator bordered> */}
        {/* <Text>IDENTITÄT</Text> */}
        {/* </Separator> */}
        {/* { 
            // settingsIdentity.map((setting, num) => {
// 
              // return (
                // <PreferenceItem
                  // key={num}
                  // name={setting.name}
                  // implemented={setting.implemented}
                  // description={setting.description}
                  // icon={setting.icon}
                  // state={setting.state}
                  // isLast={num === (settingsIdentity.length - 1)}
                  // nav={this.props.navigation}
// 
                  // TODO: this will only work with the implemented calendar, contacts and location settings
                  // for other cases, one settings entry in the JSON file is not necessarily equal to a setting in the UI
                  // settingKey={setting.key}
                  // contextID={this.props.contextID}
                  // context={this.props.context}
                // />
              // )
            // })
          // }*/}
        {/* </List> */}
      </ScrollView>
    );

  }
}



export default withNavigation(withTheme(ListPreferences));