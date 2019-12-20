import React, {Component} from 'react';
import Icon from 'react-native-vector-icons/MaterialIcons';
import { Container, Header, Name, Bio, SubmitButton } from './styles';
import api from '../../services/api';
import AsyncStorage from '@react-native-community/async-storage';
import axios from 'axios';
import Dialog, { DialogContent } from 'react-native-popup-dialog';

export default class People extends Component {
  static navigationOptions = ({navigation}) =>({
    title: navigation.getParam('person').name,
  });

  constructor(props) {
    super(props);

    this.state = {
      detales: [],
      planet: '',
      species: '',
      favorites: [],
      content: '',
      isModalVisible: false,
    }

  }

  async componentDidMount() {

    const { navigation } = this.props;

    const person = navigation.getParam('person');

    try {

      const p = await this.homeWorldSearch(person);

      const e = await this.speciesSearch(person);

      this.setState({
        planet: p,
        species: e,
        detales: person,
      });

    } catch (error) {}

    const peop = await AsyncStorage.getItem('favorites');

    if (peop) {
      this.setState({ favorites: JSON.parse(peop) });
    }

  }

  homeWorldSearch = async(detales) => {

    const num = String(detales.planet).slice(29,31);
    try {
      const planeta = await api.get(`/planets/${num}`);
      return planeta.data.name;

    } catch (error) { }
  }

  speciesSearch = async(detales) => {

    const num = String(detales.species[0]).slice(29,31);
    try {
      const especie = await api.get(`/species/${num}`);
      return especie.data.name;

    } catch (error) {}

  }

  handleFavorite = async() => {
    const {detales, favorites} = this.state;

    if (detales.favorite === true) {
      this.setState(prevState => ({
        detales: {
          ...prevState.detales,
          favorite: false,
        }
      }));
    } else {
      this.setState(prevState => ({
        detales: {
          ...prevState.detales,
          favorite: true,
        }
      }));
    }

    let fav = [...favorites].concat(detales);

    if(fav) {
      await AsyncStorage.setItem('favorites', JSON.stringify(fav));
    }

    let resposta;

    try {
      resposta = await this.makeItFavorite(detales.name);
      this.setState({content: resposta.data.message, isModalVisible: true});
    } catch (error) {

    }
  }
  toggleModal = () => {
    this.setState({ isModalVisible: !this.state.isModalVisible });
  };

  makeItFavorite = (name) => {

    return axios.post(`http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/${name}`);
  }

  render() {

    const {detales, planet, species, content, isModalVisible} = this.state;
    console.disableYellowBox = true;
    return (
      <Container>
        <Header>
        <Name>{detales.name}</Name>
              <Bio>Height: {detales.height}</Bio>
              <Bio>Gender: {detales.gender}</Bio>
              <Bio>Mass: {detales.mass}</Bio>
              <Bio>Hair Color: {detales.hair_color}</Bio>
              <Bio>Skin Color: {detales.skin_color}</Bio>
              <Bio>Eye Color: {detales.eye_color}</Bio>
              <Bio>Birth Year: {detales.birth_year}</Bio>
              <Bio>Home Planet: {planet} </Bio>
              <Bio>Species: {species}</Bio>

        </Header>
        <SubmitButton onPress={this.handleFavorite}>
            {detales.favorite ? (
              <Icon name="star" size={20} color="#FFD700"/>
            ) : (
              <Icon name="star-border" size={20} color="#FFD700"/>
            )}
          </SubmitButton>
          <Dialog
            visible={isModalVisible}
            onTouchOutside={() => {
            this.toggleModal();
             }}
          >
          <DialogContent>
              <Bio>{content}</Bio>
          </DialogContent>
        </Dialog>
      </Container>
  );
 }
}
