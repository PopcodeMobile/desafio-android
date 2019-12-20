import styled from 'styled-components/native';
import { RectButton } from 'react-native-gesture-handler';

export const Container = styled.View`
  flex: 1;
  padding: 30px;
`;

export const SubmitButton = styled(RectButton)`
  justify-content: center;
  align-items: center;
  background: #073ac7;
  border-radius: 4px;
  margin-left: 10px;
  padding: 0 12px;
  opacity: ${props => (props.loading ? 0.6 : 1)}
`;

export const List = styled.FlatList.attrs({
  showsVerticalScrollIndicator: false,
})`
  margin-top: 20px;
`;

export const Person = styled.View`
  align-items: center;
  margin: 0 20px 30px;

`;

export const Name = styled.Text`
  font-size: 14px;
  color: #FFD700;
  background: #333;
  font-weight: bold;
  margin-top: 4px;
  text-align: center;
`;

export const ProfileButton = styled(RectButton)`
  margin-top: 10px;
  align-self: stretch;
  border-radius: 8px;
  background: #073ac7;
  justify-content: center;
  align-items: center;
  height: 30px;
`;

export const Excluir = styled(RectButton)`

  justify-content: center;
  align-items: center;

`;

export const ProfileButtonText = styled.Text`
  font-size: 14px;
  font-weight: bold;
  color: #fff;
  text-transform: uppercase;
`;
