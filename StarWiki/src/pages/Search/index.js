import React, {Component} from 'react';

import {
  Container,
  List,
  Person,
  Name,
  ProfileButton,
  ProfileButtonText
 } from './styles';

export default class Search extends Component {
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


    const { navigation } = this.props;

    const person = navigation.getParam('person');

    this.setState({people: person})
  }

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
