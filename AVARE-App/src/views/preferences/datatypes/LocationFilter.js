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
import { View, Slider } from 'react-native';
import { withTheme, Title, Paragraph } from 'react-native-paper';
import PreferencesHeader from '../../_shared/PreferencesHeader';

import { setLocationFilter as filterLocationCategory } from '../../../redux/modules/categories/actions';
import { setLocationFilter as filterLocationApp } from '../../../redux/modules/apps/actions';
import { writeJsonFile } from '../../../storage/RNFSControl';
import { connect } from 'react-redux';

class LocationFilter extends React.Component {
  constructor(props) {
    super(props);
    
    let contextID = this.props.navigation.state.params.contextID;
    let context = this.props.navigation.state.params.context;
    this.state = {
      context,
      contextID
    }
    
  }

  setLocationFilter(value){
    if (this.state.context == "category") {
      this.props.dispatch(filterLocationCategory(this.state.contextID, value));
    } else {
      console.log('setting location filter to ' + value);
      this.props.dispatch(filterLocationApp(this.state.contextID, value));
    }
    writeJsonFile();
  }

  render() {
    let { colors } = this.props.theme;

    let contextObject;
    if (this.state.context == "category") {
      contextObject = this.props.categories.find((element) => { return element._id == this.state.contextID });
    } else {
      contextObject = this.props.apps.find((element) => { return element._id == this.state.contextID });
    }
    let distance = contextObject.settings.location.filterSettings.distance;

    return (
      <View style={{ backgroundColor: colors.background, height: 300 }}>
        <PreferencesHeader title="Standortfilter" />
        <Paragraph>Standortunschärfe:</Paragraph>
        <Slider style={{ marginTop: 16 }} value={distance} minimumValue={1} maximumValue={100} step={1} onSlidingComplete={(value) => { this.setLocationFilter(value); }} />
      </View>
    );

  }
}

const mapStateToProps = (state) => {
  console.log(state);

  return {
    categories: state.categories,
    apps: state.apps
  };
}

export default connect(mapStateToProps)(withTheme(LocationFilter));