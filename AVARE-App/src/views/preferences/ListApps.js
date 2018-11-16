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
import AvareBox from '../../packages/AvareBox';
import { View, FlatList, ScrollView, StyleSheet } from 'react-native';
import { withTheme, Button, List, Divider, Checkbox, Portal, Dialog } from 'react-native-paper';
import IconView from '../../packages/IconView';
import AppsInfo from '../../packages/AppsInfo';
import { connect } from 'react-redux';
import { addApp, removeApp } from '../../redux/modules/apps/actions';
import { writeJsonFile } from '../../storage/RNFSControl';
import _ from 'lodash';
import initialPreferences from '../../storage/initalPreferences';
import AppsHeader from '../_shared/AppsHeader';

class ListApps extends React.Component {
  static navigationOptions = {
    header: <AppsHeader />,
  }

  constructor(props) {
    super(props);
    this.state = {
      selectedApp: null,
      allApps: [],
      modalVisible: false
    }
  }

  selectApp(app) {
    this.setState({
      selectedApp: app
    });
    this.setModalVisible(true)
  }

  removeApp(appId) {
    this.props.dispatch(removeApp(appId));
    writeJsonFile();
    
    AvareBox.removeApp(appId);
  }

  addSelectedAppToCategory(category) {
    let settings = _.cloneDeep(initialPreferences.apps);
    let app = {
      _id: this.state.selectedApp,
      category_id: category,
      settings
    }
    console.log('Adding App:');
    console.log(app);
    this.props.dispatch(addApp(app));
    writeJsonFile();

    AvareBox.addApp(app._id);
  }

  setModalVisible(visible) {
    this.setState({ modalVisible: visible });
  }

//  handleOnScroll = event => {
//    this.setState({
//      scrollOffset: event.nativeEvent.contentOffset.y
//    });
//  };
//
//  handleScrollTo = p => {
//    if (this.scrollViewRef) {
//      this.scrollViewRef.scrollTo(p);
//    }
//  };
  render() {
    const { colors } = this.props.theme;
    return (

      <View
        style={{ backgroundColor: colors.background }}
      >
        <FlatList
          data={this.state.allApps}
          ItemSeparatorComponent={() => { return (<Divider />) }}
          keyExtractor={(app, index) => app.package}
          renderItem={({ item }) => {
            let itemChecked = false;
            if (this.props.apps.some !== undefined) {
              itemChecked = this.props.apps.some((element) => { return element._id == item.package });
            }
            return (
              <List.Item
                title={item.name}
                onPress={() => {
                  if (!itemChecked) {
                    this.selectApp(item.package)
                  } else {
                    this.removeApp(item.package);
                  }
                }}
                //TODO: it would be possible to show icons like this, but currently this results in checkbox not being aligned properly
                // left={() => <List.Icon icon={({size, color}) => (<IconView style={{height: size, width: size }} package={item.package} />)} />}
                right={() => <Checkbox status={itemChecked ? 'checked' : 'unchecked'} />}
              />
            )
          }}
        />
        {/* //TODO: the dialog does appear on a white background and not transparent, currently I don't know how to change this with paper Dialog */}
        <Portal>

          <Dialog
            visible={this.state.modalVisible}
            onDismiss={() => this.setModalVisible(false)}
            dismissable={true}
          >
            <Dialog.Title>Wähle eine Kategorie</Dialog.Title>

            <Dialog.ScrollArea
              style={{ height: 400 }}
            >
              <FlatList
                data={this.props.categories}
                // TODO: the height is just assumed here. should be properly calculated
                // but this gives a significant performance boost (esp. needed in dev mode)
                getItemLayout={(data, index) => ({ length: 80, offset: 80 * index, index})}
                ItemSeparatorComponent={() => { return (<Divider />) }}
                keyExtractor={(category, index) => category._id}
                renderItem={({ item }) => {
                  return (
                    <List.Item
                      title={item.name}
                      onPress={() => {
                        this.addSelectedAppToCategory(item._id);
                        this.setModalVisible(false);
                      }
                      }
                    />
                  );
                }}
              />
            </Dialog.ScrollArea>

            <Dialog.Actions>
              <Button onPress={() => {
                this.setModalVisible(false);
              }}>Abbrechen</Button>
            </Dialog.Actions>
          </Dialog>

        </Portal>
      </View>

    );
  }

  componentDidMount() {
    AppsInfo.getApps((msg) => {
      //console.log('Error getting Apps: ' + msg);
    },
      (apps) => {
        //console.log(apps);
        this.setState({
          allApps: apps
        });

      });

  }
}


//const styles = StyleSheet.create({
//
//  scrollableModal: {
//    height: 500,
//    backgroundColor: "white",
//    borderRadius: 4,
//    borderColor: "rgba(0, 0, 0, 0.1)"
//
//  },
//});

const mapStateToProps = (state) => {
  //console.log(state);
  return {
    categories: state.categories,
    apps: state.apps
  };
}

export default connect(mapStateToProps)(withTheme(ListApps));