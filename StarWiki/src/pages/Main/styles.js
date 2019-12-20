import styled from 'styled-components/native';
import { RectButton } from 'react-native-gesture-handler';

export const Container = styled.View`
  flex: 1;
  padding: 30px;

`;

export const Form = styled.View`
  flex-direction: row;
  padding-bottom: 20px;
  border-bottom-width: 1px;
  border-color: #eee;
`;

export const Input = styled.TextInput.attrs({
  placeholderTextColor: '#999',
})`
  flex: 1;
  height: 40px;
  background: #eee;
  border-radius: 4px;
  padding: 0 15px;
  border: 1px solid #eee;
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

  border-radius: 4px;
  font-weight: bold;
  margin-top: 4px;
  text-align: center;
  align-items: center;
  justify-content: space-between;
`;

export const Height = styled.Text`
  font-size: 13px;
  line-height: 18px;
  color: #999;
  margin-top: 5px;
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

export const ProfileButtonText = styled.Text`
  font-size: 14px;
  font-weight: bold;
  color: #fff;
  text-transform: uppercase;
`;
