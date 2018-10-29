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
import { View, FlatList } from 'react-native';
import { withTheme, List, Checkbox } from 'react-native-paper';
import PreferencesHeader from '../../_shared/PreferencesHeader';
import Contacts from 'react-native-contacts';
import _ from 'lodash';

import { addHorizontalContactsFilter as addHorizontalCategoryFilter, removeHorizontalContactsFilter as removeHorizontalCategoryFilter } from '../../../redux/modules/categories/actions';
import { addHorizontalContactsFilter as addHorizontalAppFilter, removeHorizontalContactsFilter as removeHorizontalAppFilter } from '../../../redux/modules/apps/actions';
import { writeJsonFile } from '../../../storage/RNFSControl';
import { connect } from 'react-redux';

class ContactItemsFilter extends Component {
  constructor(props) {
    super(props);


    let contextID = this.props.navigation.state.params.contextID;
    let context = this.props.navigation.state.params.context;

    this.state = {
      contextID,
      context,
      contacts: [
      ]
    }

    Contacts.getAll((err, contacts) => {
      if(err) {
        console.log("Error loading contacts:");
        console.log(err);
      }
      console.log('loading contacts')
      console.log(contacts);
      this.setState({contacts});
      // this.loadContactsInState(contacts)
    });


  }

//  loadContactsInState(contacts) {
//    let contextObject;
//    if (this.state.context == "category") {
//      contextObject = this.props.categories.find((element) => { return element._id == this.state.contextID });
//    } else {
//      contextObject = this.props.apps.find((element) => { return element._id == this.state.contextID });
//    }
//    let horizontalFilter = contextObject.settings.contacts.filterSettings.horizontal;
//
//    for (let i = 0; i < contacts.length; i++) {
//      let itemChecked = horizontalFilter.some((element) => {
//        return element == contacts[i].recordID
//      })
//      
//      contacts[i].checked = itemChecked;
//
//      if(itemChecked) {
//        // console.log('loading:')
//        // console.log(contacts[i]);
//      }
//    }
//    this.setState({ contacts });
//    // this.forceUpdate();
//  }

  addHorizontalFilter(contactID) {
    if (this.state.context == "category") {
      this.props.dispatch(addHorizontalCategoryFilter(this.state.contextID, contactID));
    } else {
      this.props.dispatch(addHorizontalAppFilter(this.state.contextID, contactID));
    }
    writeJsonFile();
    // this.loadContactsInState(this.state.contacts);
  }

  removeHorizontalFilter(contactID) {
    console.log('removing contact: ' + contactID);
    if (this.state.context == "category") {
      this.props.dispatch(removeHorizontalCategoryFilter(this.state.contextID, contactID));
    } else {
      this.props.dispatch(removeHorizontalAppFilter(this.state.contextID, contactID));
    }
    writeJsonFile();
    // this.loadContactsInState(this.state.contacts);
  }

//  componentDidUpdate(prevProps, prevState, snapshot) {
//    // for each element in horizontalFilter update state
//    // TODO: this should probably be done in another life cycle function
//    // let contacts = _.cloneDeep(this.state.contacts);
//    // console.log('updateing contacts')
//    // console.log(contacts);
//    // let checkedHasChanged
  //  if(prevProps.categories !== this.props.categories) {
    //  console.log('component did update: load contacts')
    //  console.log(prevState.contacts);
    //  console.log(this.state.contacts);
    //  this.loadContactsInState(this.state.contacts);
  //  }
//  }

  render() {
    let { colors } = this.props.theme;
    console.log('rendering');
    console.log(this.state.contacts);
    // let extraData = this.state.context == 'category' ? this.props.categories : this.props.apps;

    let contextObject;
    if (this.state.context == "category") {
      contextObject = this.props.categories.find((element) => { return element._id == this.state.contextID });
    } else {
      contextObject = this.props.apps.find((element) => { return element._id == this.state.contextID });
    }
    let horizontalFilter = contextObject.settings.contacts.filterSettings.horizontal;

    // for (let i = 0; i < contacts.length; i++) {
      // let itemChecked = horizontalFilter.some((element) => {
        // return element == contacts[i].recordID
      // })
      
      // contacts[i].checked = itemChecked;

      // if(itemChecked) {
        // console.log('loading:')
        // console.log(contacts[i]);
      // }
    // }
 
    return (
      <View style={{ backgroundColor: colors.background }}>
        <PreferencesHeader title="Kontaktfilter (Personen)" />
        <FlatList
          data={this.state.contacts}
          keyExtractor={(item, index) => item.recordID}
          // to tell the flatlist to rerender, when the store changes
          extraData={this.props}
          renderItem={({ item }) => {
            let itemChecked = horizontalFilter.some((element) => {
              return element == item.recordID
            })
            return (
              <ContactItem checked={itemChecked} name={item.givenName + " " + item.familyName}
                onAdd={this.addHorizontalFilter.bind(this)}
                onRemove={this.removeHorizontalFilter.bind(this)}
                contactID={item.recordID}
              />
            )
          }}
        />
      </View>
    );
  }
}

class ContactItem extends React.Component {
  render() {
    return (
      <List.Item
        right={() => <Checkbox status={this.props.checked ? 'checked' : 'unchecked'} />}
        title={this.props.name}
        onPress={
          () => {
            if (!this.props.checked) {
              this.props.onAdd(this.props.contactID);
            } else {
              this.props.onRemove(this.props.contactID)
            }
          }
        }
      />
    )
  }
}

const mapStateToProps = (state) => {
  return {
    categories: state.categories,
    apps: state.apps
  };
}

export default connect(mapStateToProps)(withTheme(ContactItemsFilter));