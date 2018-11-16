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
import { StatusBar } from 'react-native';
import { withTheme, Appbar } from 'react-native-paper';
import { withNavigation, StackActions } from 'react-navigation';

class PreferencesHeader extends React.Component {
    constructor(props) {
        super(props);

    }

    render() {
        const { colors } = this.props.theme;
        return (
            <Appbar.Header style={this.props.hasTabs ? {elevation: 0} : {}}>
                <StatusBar backgroundColor={colors.primaryDark} />
                <Appbar.Action icon="arrow-back" color={colors.text} onPress={() => this.props.navigation.dispatch(StackActions.pop())} />
                <Appbar.Content title={this.props.title} color={colors.text} />
                
            </Appbar.Header>
        )
    }
}

export default withNavigation(withTheme(PreferencesHeader));