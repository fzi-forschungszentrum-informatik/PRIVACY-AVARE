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
*/import { StyleSheet } from 'react-native'

export default StyleSheet.create({
    box2: {
        flex:2,
    },
    box1: {
        flex:1,
    },
    container: {
        backgroundColor: '#ecf0f1',
        flex:1,
        flexDirection:'column',
        alignItems: 'center',
        justifyContent: 'center'
      },
    logo : {
        resizeMode: 'contain',
    },
    buttonContainer: {
        //flex:1,gg
        //padding: 10,
        flexDirection: 'row',
        justifyContent: 'space-evenly'
    },
    header: {
        padding: 20,
        fontSize: 20,
        textAlign: 'center'
    },
    normalText : {
        textAlign: 'center',
        padding : 10,
    },
});
