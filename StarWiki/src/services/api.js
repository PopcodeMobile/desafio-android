import axios from 'axios';

const api = axios.create({
  baseURL: 'https://swapi.co/api',
});

export default api;
