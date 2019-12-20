/* eslint-disable no-unused-vars */
import React, { Component } from 'react';
import { Keyboard, ActivityIndicator } from 'react-native';
import AsyncStorage from '@react-native-community/async-storage';
import Icon from 'react-native-vector-icons/MaterialIcons';
import axios from 'axios';
import PropTypes from 'prop-types';
import api from '../../services/api';


import {
  Container,
  Form,
  Input,
  SubmitButton,
  List,
  Person,
  Name,
  Height,
  ProfileButton,
  ProfileButtonText,
} from './styles';

export default class Main extends Component {
  constructor(props) {
    super(props);
    this.state = {
      newPeople: '',
      people: [],
      search: [],
      loading: false,
    };
  }

  async componentDidMount() {

    const { people } = this.state;

    const peop = await AsyncStorage.getItem('people');

    if (peop) {
      this.setState({ people: JSON.parse(peop) });
    }else {

      this.setState({ loading: true});
      const starwarsPeople = await this.getAllStarwarsPeople();
      const data = starwarsPeople.map(function(item) {
        return {
          url: item.url,
          name: item.name,
          height: item.height,
          gender: item.gender,
          mass: item.mass,
          hair_color: item.hair_color,
          skin_color: item.skin_color,
          eye_color: item.eye_color,
          birth_year: item.birth_year,
          planet: item.homeworld,
          species: item.species,
          favorite: false,
        };
      });

      this.setState({
        people: [...people].concat(data),
        loading: false,
      });

    }

  }

  componentDidUpdate(_, prevState) {
    const { people } = this.state;

    if (prevState !== people) {
      AsyncStorage.setItem('people', JSON.stringify(people));
    }
  }

  getAllStarwarsPeople = () => {
    let peo = [];

    return axios('https://swapi.co/api/people/')
      .then(response => {

        peo = response.data.results;
        return response.data.count;
      })
      .then(count => {

        const numberOfPagesLeft = Math.ceil((count - 1) / 10);
        const promises = [];

        for (let i = 2; i <= numberOfPagesLeft; i += 1) {
          promises.push(axios(`https://swapi.co/api/people?page=${i}`));
        }
        return Promise.all(promises);
      })
      .then(response => {

        peo = response.reduce(
          (acc, data) => [...acc, ...data.data.results],
          peo
        );
        return peo;
      });

  };

  handleSearchPeople = async () => {
    let pesquisa = [];
    this.setState({ loading: true, search: [] });

    const { newPeople } = this.state;

    const response = await api.get(`/people/?search=${newPeople}`);
    const arr = response.data.results;

    pesquisa = arr.map(function(item) {
      let objeto = {
        url: item.url,
        name: item.name,
        height: item.height,
        gender: item.gender,
        mass: item.mass,
        hair_color: item.hair_color,
        skin_color: item.skin_color,
        eye_color: item.eye_color,
        birth_year: item.birth_year,
        planet: item.homeworld,
        species: item.species,
        favorite: false,
      };

      return objeto;

    });

    this.setState({
      search: [pesquisa],
      newPeople: '',
      loading: false,
    });
    this.handleNavigateSearch(pesquisa);
    Keyboard.dismiss();
  };

  handleNavigate = person => {
    const { navigation } = this.props;
    navigation.navigate('People', { person });
  };

  handleNavigateFavo = () => {
    const { navigation } = this.props;
    navigation.navigate('Favorites');
  };

  handleNavigateSearch = person => {
    const { navigation } = this.props;

    navigation.navigate('Search', { person });
  };

  render() {
    const { people, newPeople, loading } = this.state;
    return (
      <Container>
        <Form>
          <Input
            autoCorrect={false}
            autoCapitalixe="none"
            placeholder="Search Character"
            value={newPeople}
            onChangeText={text => this.setState({ newPeople: text })}
            returnKeyType="search"
          />
          <SubmitButton
            loading={loading}
            onPress={() => {
              this.handleSearchPeople();
            }}
          >
            {loading ? (
              <ActivityIndicator color="#FFF" />
            ) : (
              <Icon name="search" size={20} color="#FFF" />
            )}
          </SubmitButton>
          <SubmitButton
            onPress={() => {
              this.handleNavigateFavo();
            }}
          >
            <Icon name="star" size={20} color="#FFF" />
          </SubmitButton>
        </Form>
        <List
          data={people}
          keyExtractor={p => p.name}
          renderItem={({ item }) => (
            <Person>
              <Name>{item.name}</Name>
              <Height>Height: {item.height}</Height>
              <Height>Gender: {item.gender}</Height>
              <Height>Mass: {item.mass}</Height>
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
Main.propTypes = {
  navigation: PropTypes.object.isRequired,
};

Main.navigationOptions = {
  title: 'StarWiki',
};
