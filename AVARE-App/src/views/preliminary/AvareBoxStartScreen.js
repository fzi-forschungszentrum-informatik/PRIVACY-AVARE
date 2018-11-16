import React from 'react';
import { View, Button } from 'react-native';
import AvareBox from '../../packages/AvareBox';

export default class AvareBoxStartScreen extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <View>
            <Button title="Starte Avare Box" onPress={() => {
                console.log('Starting Avare Box');
                AvareBox.startAvareBox();
            }}/>
        </View>
    )
  }
}