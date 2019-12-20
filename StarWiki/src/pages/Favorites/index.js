import React, {Component} from 'react';
import AsyncStorage from '@react-native-community/async-storage';
import Icon from 'react-native-vector-icons/MaterialIcons';
import {
  Container,
  List,
  Person,
  Name,
  ProfileButton,
  ProfileButtonText,
  Excluir,
 } from './styles';

export default class Favorites extends Component {

  constructor(props) {
    super(props);
    this.state = {
    people: [],
  };
}
  async componentWillUnmount(){
    this.setState({people: []});
  }
  async componentDidMount() {

    const person = await AsyncStorage.getItem('favorites');

    this.setState({
      people: JSON.parse(person),
   });

  }

  removeFavorite = async (item) => {
    const { people } = this.state;
    const filtrado = people.filter(obje => obje.name !== item.name);

    this.setState({
      people: filtrado,
   });

   await AsyncStorage.setItem('favorites', JSON.stringify(filtrado));
  };

  handleNavigate = (person) => {
    const { navigation } = this.props;
    navigation.navigate('People', { person });
  };

  render() {
    const { people } = this.state;

    return (
      <Container>
        <List
          data={people}
          keyExtractor={people => people.name}
          renderItem={({item}) => (
            <Person>
              <Excluir onPress={() => this.removeFavorite(item)}>
                <Icon name="delete" size={20} color="red"/>
              </Excluir>
              <Name>{item.name}</Name>
              <ProfileButton onPress={() => this.handleNavigate(item)}>
                <ProfileButtonText>Details</ProfileButtonText>
              </ProfileButton>
            </Person>
          )}
        />
      </Container>
  );

 }
}

Favorites.navigationOptions = {
  title: 'My Favorites',
};

//
