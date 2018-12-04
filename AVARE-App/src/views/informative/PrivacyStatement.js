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
import { View } from 'react-native';
import { withTheme, Paragraph, Title } from 'react-native-paper';
import HomeHeader from '../_shared/HomeHeader';

class PrivacyStatement extends React.Component {
//    static navigationOptions = {
//        header: <HomeHeader />,
//    }

    render() {
        const { colors } = this.props.theme;
        return (

            <View
                style={{ backgroundColor: colors.background }}
            >
            <HomeHeader />
            <View style={{ padding: 8 }}>
            <Title>Datenschutzerklärung</Title>
            <Paragraph>Lorem Ipsum</Paragraph>
            </View>
            </View>

        );
    }
}

export default withTheme(PrivacyStatement);