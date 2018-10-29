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
import { NavigationActions } from 'react-navigation';
import {
  View,
  Keyboard,
  FlatList
} from 'react-native';
import {
  Toast,
} from 'native-base';
import { withTheme, FAB, Divider, List, TouchableRipple } from 'react-native-paper';

import { connect } from 'react-redux';
import HomeHeader from '../_shared/HomeHeader';
// import InstalledApps from 'react-native-installed-packages';

class ListCategories extends React.Component {
  static navigationOptions = {
    header: <HomeHeader />,
  }
  constructor(props) {
    super(props);

    //this.state = {
    //  modalVisible: false,
    //  newCategoryName: '',
    //  apps: [
    //    {
    //      name: 'Test',
    //      package: 'testpackage'
    //    }
    //  ],
    //}
  }

  componentDidMount() {
    // Keyboard.addListener('keyboardDidHide', () => this._keyboardDidHide());
  }

  _onAddCategory() {
    // TODO: this is not yet integrated with Redux
    let categoryName = this.state.newCategoryName;
    this.setState(prevState => ({
      categories: [...prevState.categories, categoryName]
    }));
    Toast.show({
      text: 'Kategorie \'' + categoryName + '\' erstellt!',
      position: 'center',
      buttonText: 'OK',
      type: 'success',
      duration: 10000
    });
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
      <View
          style={{ backgroundColor: colors.background }}
      >
        <FlatList
          data={this.props.categories}
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
              {/* <HomeHeader onOpen={() => this.openDrawer()} /> */}
              {/* <View style={{ padding: 8 }}> */}
              {/* <Item> */}
              {/* <Icon name="md-folder" style={{ color: 'darkgrey' }} /> */}
              {/* <Input */}
              {/* ref={component => this._textInput = component} */}
              {/* clearTextOnFocus={true} */}
              {/* onChangeText={(text) => { this._onChangeText(text); }} */}
              {/* // onKeyPress={(event) => { console.log('keypressed'); if(event.nativeEvent.key === 'Enter') this._onAddCategory(); }} */}
              {/* onSubmitEditing={() => { this._onAddCategory() }} */}
              {/* placeholder="Neue Kategorie erstellen ..." /> */}

              {/* </Item> */}
              {/* </View> */}
            </TouchableRipple>
          }
        />
        <FAB
          icon="apps"
          label="Apps verwalten"
          onPress={() => {
            this.props.navigation.navigate('Apps');
          }}
          style={{ position: 'absolute', margin: 16, right: 0, bottom: 0 }}
        />
      </View>

    );
  }
}

const mapStateToProps = (state) => {
  console.log('Mapping ListCategories');
  console.log(state);
  return {
    categories: state.categories
  };
}

export default connect(mapStateToProps)(withTheme(ListCategories));