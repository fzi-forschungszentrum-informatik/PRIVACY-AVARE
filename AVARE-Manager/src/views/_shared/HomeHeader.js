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
import { UIManager, findNodeHandle, StatusBar } from 'react-native';
import { withTheme, Appbar } from 'react-native-paper';
import { withNavigation, DrawerActions } from 'react-navigation';

class HomeHeader extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            icon: null
        }
    }
    render() {
        const { colors } = this.props.theme;
        return (
            <Appbar.Header>
                <StatusBar backgroundColor={colors.primaryDark} />
                {/* <Appbar.Action icon="menu" onPress={() => this.props.navigation.openDrawer()} /> */}
                <Appbar.Action icon="menu" color={colors.text} onPress={() => this.props.navigation.dispatch(DrawerActions.openDrawer())} />
                <Appbar.Content title="AVARE" color={colors.text} />
                <Appbar.Action ref={this.onRef}  icon="more-vert" color={colors.text} onPress={() => {
                    //TODO: this uses default Roboto font, which seems hard to change
                    // possible alternative: https://github.com/mxck/react-native-material-menu
                    UIManager.showPopupMenu(
                        findNodeHandle(this.state.icon),
                        ['Hilfe', 'Kategorie hinzufügen'],
                        () => { console.log('Error')},
                        () => { console.log('Success')}
                    );
                }}
                />
            </Appbar.Header>
        )
    }

    onRef = icon => {
        if (!this.state.icon) {
            this.setState({ icon })
        }
    }
}

export default withNavigation(withTheme(HomeHeader));