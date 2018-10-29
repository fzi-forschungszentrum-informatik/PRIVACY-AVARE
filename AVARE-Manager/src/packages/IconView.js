import { PropTypes } from 'prop-types';
import { View, requireNativeComponent, NativeModules } from 'react-native';

let iface = {
	name: 'IconView',
	propTypes: {
		package: PropTypes.string,
		...View.propTypes
	}
}
module.exports = requireNativeComponent('RNIconView', iface);