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
//Listing all Apps for one Category and providing some means to sort app into another category.
import React from 'react';
import { Divider, withTheme, List } from 'react-native-paper';
import { FlatList } from 'react-native';
import { withNavigation } from 'react-navigation';
import { connect } from 'react-redux';
import AppsInfo from '../../packages/AppsInfo';

class CategoryApps extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      categoryID : this.props.navigation.state.params.contextID,
      // selectedApp: null,
      allApps: [],
      // modalVisible: false
    }
  }
  
  render() {
    const { colors } = this.props.theme;
    let appsInCategory = this.props.apps.filter((app) => app.category_id == this.state.categoryID);
    let appInfos = this.state.allApps.filter((app) => appsInCategory.find(({_id}) => _id == app.package));
    console.log('CategoryApps');
    console.log(appsInCategory);
    console.log(appInfos);
    return (
      <FlatList
        style={{ backgroundColor: colors.background }}

        ItemSeparatorComponent={() => { return (<Divider />) }}
        keyExtractor={(item, index) => item.package}
        data={appInfos}
        renderItem={({ item }, index, section) => {

          return (
            <List.Item
              onPress={() => this.props.navigation.navigate('AppPreferences', { appName: item.name, contextID: item.package, context: "app" })}
              title={item.name}
            />)
        }} />
    );

  }

  //TODO: if this causes performance problems, one could store the name of apps in the json-file
  componentDidMount() {
    AppsInfo.getApps((msg) => {
      console.log('Error getting Apps: ' + msg);
    },
      (apps) => {
        console.log(apps);
        this.setState({
          allApps: apps
        });

      });

  }
}

const mapStateToProps = (state) => {
  return {
    apps: state.apps
  };
}

export default connect(mapStateToProps)(withTheme(withNavigation(CategoryApps)));