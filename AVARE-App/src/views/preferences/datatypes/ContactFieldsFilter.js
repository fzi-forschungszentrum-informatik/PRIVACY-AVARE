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
import { List, withTheme, Checkbox } from 'react-native-paper';
import PreferencesHeader from '../../_shared/PreferencesHeader';

import { addVerticalContactsFilter as addVerticalCategoryFilter, removeVerticalContactsFilter as removeVerticalCategoryFilter } from '../../../redux/modules/categories/actions';
import { addVerticalContactsFilter as addVerticalAppFilter, removeVerticalContactsFilter as removeVerticalAppFilter } from '../../../redux/modules/apps/actions';
import { writeJsonFile } from '../../../storage/RNFSControl';
import { connect } from 'react-redux';

class ContactFieldsFilter extends Component {
  constructor(props) {
    super(props);

    let contextID = this.props.navigation.state.params.contextID;
    let context = this.props.navigation.state.params.context;
    // TODO: das sind eigentlich nur Datentypen
    // einem Kontakt können beliebig viele E-Mail-Adressen zugeordnet werden
    // kann man hier nach subtypen geschäftlich/privat etc. filtern?
    // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Phone
    this.state = {
      context,
      contextID,
      fields: [
        {
          // see https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.StructuredName
          _id: "GIVEN_NAME",
          description: "Vorname"
        },
        {
          // see https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.StructuredName
          _id: "FAMILY_NAME",
          description: "Nachname"
        },
        {
          // see https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.StructuredName
          _id: "TYPE_BIRTHDAY",
          description: "Geburtsdatum"
        },
        {
          // see https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Photo
          _id: "PHOTO",
          description: "Foto",
        },
        {
          // see https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Organization
          _id: "ORGANIZATION",
          description: "Unternehmen",
        },
        {
          // see https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Phone
          _id: "PHONE.TYPE_MOBILE",
          description: "Telefon – Mobil",
        },
        {
          // see https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Phone
          _id: "PHONE.TYPE_HOME",
          description: "Telefon – Privat",
        },
        {
          // see https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Phone
          _id: "PHONE.TYPE_WORK",
          description: "Telefon – Geschäftlich",
        },
        {
          // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Email
          _id: "EMAIL.TYPE_HOME",
          description: "E-Mail – Privat",
        },
        {
          // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Email
          _id: "EMAIL.TYPE_WORK",
          description: "E-Mail – Geschäftlich",
        },
        {
          // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.StructuredPostal
          _id: "STREET",
          description: "Straße",
        },
        {
          // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.StructuredPostal
          _id: "POSTCODE",
          description: "Postleitzahl",
        },
        {
          // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.StructuredPostal
          _id: "CITY",
          description: "Stadt",
        },
        {
          // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.StructuredPostal
          _id: "COUNTRY",
          description: "Land",
        },
        {
          // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.StructuredPostal
          _id: "IM.PROTOCOL_SKYPE",
          description: "Chat – Skype",
        },
        {
          // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.StructuredPostal
          _id: "IM.PROTOCOL_ICQ",
          description: "Chat – ICQ",
        },
        {
          // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Website
          _id: "WEBSITE",
          description: "Webseite",
        },
        {

          // https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Note
          _id: "NOTE",
          description: "Notizen"
        }
      ]
    }

  }

  addVerticalFilter(field) {
    if (this.state.context == "category") {
      this.props.dispatch(addVerticalCategoryFilter(this.state.contextID, field));
    } else {
      this.props.dispatch(addVerticalAppFilter(this.state.contextID, field));
    }
    writeJsonFile();
  }

  removeVerticalFilter(field) {
    if (this.state.context == "category") {
      this.props.dispatch(removeVerticalCategoryFilter(this.state.contextID, field));
    } else {
      this.props.dispatch(removeVerticalAppFilter(this.state.contextID, field));
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
    let verticalFilter = contextObject.settings.contacts.filterSettings.vertical;
    return (
      <View style={{ backgroundColor: colors.background }}>
        <PreferencesHeader title="Kontaktfilter (Felder)" />
        <FlatList
          data={this.state.fields}
          extraData={this.props}
          keyExtractor={(item, index) => index}
          renderItem={({ item }) => {
            let itemChecked = verticalFilter.some((element) => {
              return element == item._id
            })

            return (
              <List.Item
                right={() => 
                  <Checkbox status={itemChecked ? 'checked' : 'unchecked'} />
                }
                title={item.description}
                onPress={
                  () => {
                    if (!itemChecked) {
                      this.addVerticalFilter(item._id);
                    } else {
                      this.removeVerticalFilter(item._id)
                    }
                  }
                } />
            )
          }}
        />
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

export default connect(mapStateToProps)(withTheme(ContactFieldsFilter));