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
import { Button } from 'react-native';
import { Text, Content } from 'native-base';
import { readJsonFile } from '../../storage/RNFSControl';


export default class ShowJsonFile extends React.Component {
    static navigationOptions = {
        drawerLabel: 'Json File'
    }

    constructor(props) {
        super(props);

        this.state = {
            fileContent: "Not actually the file content"
        }

    }

    render() {
        return(
            <Content padder>
                <Button title="Return" onPress={ () => { this.props.navigation.navigate('Home'); } }></Button>
                <Text selectable={true}>{this.state.fileContent}</Text>
            </Content>
        )
    }

    componentDidMount() {
        readJsonFile().then((content) => {
            this.setState({ fileContent: content });
        }).catch((error) => {
            console.log(error);
        });

        //.then((content) => {
        //this.setState({
        //    fileContent: content
        //})
        //});

    }
}