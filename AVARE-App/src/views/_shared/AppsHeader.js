import React from 'react';
import { StatusBar } from 'react-native';
import { withTheme, Appbar } from 'react-native-paper';
import { withNavigation, NavigationActions } from 'react-navigation';

class AppsHeader extends React.Component {

    render() {
        const { colors } = this.props.theme;
        return (
            <Appbar.Header>
                <StatusBar backgroundColor={colors.primaryDark} />
                <Appbar.Action icon="close" color={colors.text} onPress={() => this.props.navigation.dispatch(NavigationActions.back())} />
                <Appbar.Content title="Apps verwalten ..." color={colors.text} />
                
            </Appbar.Header>
        )
    }
}

export default withNavigation(withTheme(AppsHeader));