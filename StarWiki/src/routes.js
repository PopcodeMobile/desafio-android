import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';

import Main from './pages/Main';
import People from './pages/People';
import Search from './pages/Search';
import Favorites from './pages/Favorites';

const Routes = createAppContainer(
  createStackNavigator(
    {
      Main,
      Search,
      People,
      Favorites,
    },
    {
      headerLayoutPreset: 'center',
      headerBackTitleVisible: false,
      defaultNavigationOptions: {
        headerStyle: {
          backgroundColor: '#073ac7',
        },
        headerTintColor: '#FFF',
      },
    }
  )
);

export default Routes;
