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
//import { Root } from 'native-base';
import { DefaultTheme, Provider as PaperProvider } from 'react-native-paper';
import {
    createSwitchNavigator,
    createDrawerNavigator
} from 'react-navigation';

import PreferenceStack from './src/views/preferences/PreferenceStack';
import LoadingScreen from './src/views/welcome/LoadingScreen';
import WelcomeScreen from './src/views/welcome/WelcomeScreen';
import ShowJsonFile from './src/views/preliminary/ShowJsonFile';
import InfoScreen from './src/views/informative/InfoScreen';
import PrivacyStatement from './src/views/informative/PrivacyStatement';
import SideBar from './src/views/_shared/SideBar';

//Import Redux and React-Redux
import { Provider as StoreProvider } from 'react-redux'
import { combineReducers, createStore, applyMiddleware } from 'redux'

//Import reducers and middleware + on action to track the network
import communication from './src/redux/modules/communication/reducer'
import network from './src/redux/modules/network/reducer'
import apps from './src/redux/modules/apps/reducer'
import categories from './src/redux/modules/categories/reducer'
import fetchMiddleware from './src/redux/middleware/fetchMiddleware'
import { setConnectivity } from './src/redux/modules/network/actions'
import TransferProfile from './src/views/syncronization/TransferProfile';
import HomeScreen from './src/views/preliminary/HomeScreen';
import Items from './src/views/preliminary/Items';
import TransferScreen from './src/views/preliminary/TransferScreen';
import AvareBoxStartScreen from './src/views/preliminary/AvareBoxStartScreen';

//TODO: I think this should be setup somewhere else (server, store and network), and not exported from here
//Constant Adress of the Server
export const SERVER = 'http://193.196.36.83:8443' // IP + Port of the host, or  http://localhost:8443 for testing on pc

//Setting up the store
const reducers = combineReducers({ communication: communication, network: network, apps: apps, categories: categories });
export const store = createStore(reducers, applyMiddleware(fetchMiddleware));
const unsubscribe = store.subscribe(() => console.log(store.getState()))

//Track network 
export const onConnectivityChange = (reach) => {
    console.log('Network change');
    console.log(reach)
    store.dispatch(setConnectivity(reach));
}

// The main app: containing all links to screens in the drawer
const MainStack = createDrawerNavigator(
    {
        Home: {
            screen: PreferenceStack
        },
        Privacy: {
            screen: PrivacyStatement
        },
        Info: {
            screen: InfoScreen
        },
        ShowFile: {
            screen: ShowJsonFile
        },
        TestHome: {
            screen: HomeScreen,
        },
        TestItems: {
            screen: Items,
        },
        TestTransfer: {
            screen: TransferScreen,
        },
        AvareBox: {
            screen: AvareBoxStartScreen
        }
        // TODO: Further Options in Drawer like initialize sync, deconnect from sync, about, help etc...
    },
    {
        contentComponent: props => <SideBar {...props} />,
        title: 'AVARE'
    }

)

// The root stack: containing a welcome Screen and the Main App
const RootStack = createSwitchNavigator(
    {
        Loading: {
            screen: LoadingScreen
        },
        Welcome: {
            screen: WelcomeScreen
        },
        Main: {
            screen: MainStack
        },
        Transfer: {
            screen: TransferProfile,
        },
    },
    {
        initialRouteName: 'Loading',
        navigationOptions: {
            header: null,
        }
    }
)

//Added Provider to supply the store
//Root apparently needed for Toast from nativebase to work
//TODO: maybe use ToastAndroid from react-native rather than from nativebase to avoid this
const theme = {
    ...DefaultTheme,
    colors: {
        ...DefaultTheme.colors,
        primary: '#fed767',
        accent: '#fed767',
        background: 'white',
        surface: 'white',
        text: '#352a15',
        // disabled
        // placeholder
        backdrop: 'rgba(52, 52, 52, 0.8)',
        // own colors:
        primaryDark: '#c8a637',
        primaryLight: '#ffff98'
    },
    fonts: {
        ...DefaultTheme.fonts,
        regular: 'Rubik-Regular',
        medium: 'Rubik-Medium',
        light: 'Rubik-Light',
        thin: 'Rubik-LightItalic'
    }
}
export default class App extends React.Component {
    render() {
        return (
            <StoreProvider store={store}>
                <PaperProvider theme={theme}>
                    <RootStack />
                </PaperProvider>
            </StoreProvider>

        );
    }
}