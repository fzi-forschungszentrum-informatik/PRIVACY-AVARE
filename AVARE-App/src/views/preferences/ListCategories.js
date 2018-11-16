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
//List all App-Categories (starting with predefined list). Provide possibility to add/remove Category. (optional) Provide possibility to sort apps.
import React from 'react';
import {
  View,
  ScrollView,
  Keyboard,
  FlatList
} from 'react-native';
import { Dialog, Paragraph, Button, withTheme, FAB, Divider, List, TouchableRipple } from 'react-native-paper';
import { withNavigation } from 'react-navigation';

import { connect } from 'react-redux';
import HomeHeader from '../_shared/HomeHeader';
// import InstalledApps from 'react-native-installed-packages';

class ListCategories extends React.Component {
  //static navigationOptions = {
  //  header: ({ state }) => <HomeHeader onAddCategoryPressed={state.params.onAddCategoryPressed} />,
  //}
  static navigationOptions = ({ navigation }) => {
    const {state} = navigation;
    return {
      header: state.params ? <HomeHeader onAddCategoryPressed={state.params.onAddCategoryPressed} /> : <HomeHeader />
    }
  }
  constructor(props) {
    super(props);

    this.state = {
      dialogVisible: false,
      newCategoryName: '',
    }
  }
  componentWillMount() {
    const { setParams } = this.props.navigation;
    setParams({ onAddCategoryPressed: this._showDialog.bind(this) });
  }

  //componentDidMount() {
  //  // Keyboard.addListener('keyboardDidHide', () => this._keyboardDidHide());
  //  this.navigation.setParams({
  //    onAddCategoryPressed: this._onAddCategoryPressed
  //  })
  //}

  _showDialog() {
    console.log('Adding Category')
    this.setState({ dialogVisible: true })

  }
  _hideDialog() {
    this.setState({ dialogVisible: false })

  }

  // clear text input when keyboard hides
  _keyboardDidHide() {
    this._textInput.setNativeProps({ text: '' });
  }

  _onChangeText(text) {
    this.setState({ newCategoryName: text });
  }


  render() {
    const { colors } = this.props.theme;
    return (
      <View style={{ backgroundColor: colors.background, flex: 1 }}>

        <ScrollView>

          <List.Section title="Eigene Kategorien">
            <Divider />
            <FlatList
              data={this.props.ownCategories}
              keyExtractor={(item, index) => item._id}
              ItemSeparatorComponent={() => { return (<Divider />) }}
              renderItem={({ item }) =>
                <TouchableRipple
                  onPress={() => {
                    //TODO: shorter Timeout or is it performance problem?
                    setTimeout(() => {
                      this.props.navigation.navigate('Category', { context: 'category', contextID: item._id, categoryName: item.name });
                    });
                  }}
                // background={TouchableNativeFeedback.SelectableBackground()}
                // rippleColor="rgba(0, 0, 0, .32)"
                >
                  <List.Item
                    title={item.name}
                    left={(props) => <List.Icon {...props} icon="folder" />}
                    description={(() => {
                      if (item.count == 0) {
                        return "Noch keine Apps"
                      } else if (item.count == 1) {
                        return "Eine App"
                      } else {
                        return item.count + " Apps"
                      }
                    })()
                    }
                  />
                </TouchableRipple>
              }
            />

            <Divider />
          </List.Section>
          <List.Section title="AVARE-Kategorien">
            <Divider />
            <FlatList
              data={this.props.predefinedCategories}
              keyExtractor={(item, index) => item._id}
              ItemSeparatorComponent={() => { return (<Divider />) }}
              renderItem={({ item }) =>
                <TouchableRipple
                  onPress={() => {
                    //TODO: shorter Timeout or is it performance problem?
                    setTimeout(() => {
                      this.props.navigation.navigate('Category', { context: 'category', contextID: item._id, categoryName: item.name });
                    });
                  }}
                // background={TouchableNativeFeedback.SelectableBackground()}
                // rippleColor="rgba(0, 0, 0, .32)"
                >
                  <List.Item
                    title={item.name}
                    left={(props) => <List.Icon {...props} icon="folder" />}
                  />
                </TouchableRipple>
              }
            />
          </List.Section>

        </ScrollView>
        {/* TODO: pull this in HomeHeader. Then we also don't have to push functions around */}
        {/* may also fix backdrop issue */}
        <Dialog
             visible={this.state.dialogVisible}
             onDismiss={this._hideDialog.bind(this)}>
            <Dialog.Title>Alert</Dialog.Title>
            <Dialog.Content>
              <Paragraph>This is simple dialog</Paragraph>
            </Dialog.Content>
            <Dialog.Actions>
              <Button onPress={this._hideDialog.bind(this)}>Done</Button>
            </Dialog.Actions>
          </Dialog>
        <FAB
          icon="apps"
          label="Apps verwalten"
          onPress={() => {
            this.props.navigation.navigate('Apps');
          }}
          style={{ position: 'absolute', alignSelf: 'flex-end', margin: 16, right: 0, bottom: 0 }}
        />

      </View>
    );
  }
}

const mapStateToProps = (state) => {
  let categories = state.categories;
  //TODO: also self-created categories with zero apps
  let ownCategories = [];
  let predefinedCategories = [];
  for (let i = 0; i < categories.length; i++) {
    let appsInCategory = state.apps.filter((app) => app.category_id == categories[i]._id)
    categories[i].count = appsInCategory.length;
    if (categories[i].count > 0) {
      ownCategories.push(categories[i]);
    } else {
      predefinedCategories.push(categories[i]);
    }
  }
  return {
    ownCategories,
    predefinedCategories
  };
}

export default connect(mapStateToProps)(withTheme(withNavigation(ListCategories)));