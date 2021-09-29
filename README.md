# Desafio Android

O desafio consiste em criar uma Wiki de Star Wars onde é mostrada uma lista de personagens e o usuário pode favoritar alguns deles. É recomendado a utilização de Kotlin ou Java no projeto.

O candidato deve dar **fork** neste repositório e após o termino do desenvolvimento, realizar um **pull request** para análise do time. Em casos especiais o candidato tambem pode enviar o projeto compactado para hugo@popcode.com.br. Prefira vários commits durante o projeto invés de um unico commit no final.

A documentação das APIs que serão utilizadas no desafio estão disponíveis nos links abaixo:

http://swapi.dev/

http://docs.starwarsfavorites.apiary.io/#

### Lista de Personagens

Para obter os personagens, sua aplicação deverá utilizar o recurso `people` da Swapi (documentação disponível no topo do documento). A aplicação deve exibir todos os 87 personagens e permitir pesquisar o personagem pelo nome. Sugerimos exibir as primeiras páginas enquanto carrega as outras, em um formato de scroll infinito. ✅

A lista de itens deve exibir as seguintes informações:
+ Nome [name] ✔
+ Altura [height] ✔
+ Genero [gender] ✔
+ Peso [mass] ✔

Os dados devem ser salvos em banco de dados local para acesso offline e atualizados sempre que a tela for aberta. ✔

### Detalhes do Personagem

Ao clicar em um item da lista o seu app deve mostrar as informações abaixo:

+ name ✔
+ height ✔
+ mass ✔
+ hair_color ✔
+ skin_color ✔
+ eye_color ✔
+ birth_year ✔
+ gender ✔
+ Nome do Planeta Natal ✔
+ Nome da Espécie ✔

A busca pelo nome do planeta e da espécie deve ser feita em paralelo. ✔

### Favoritos

Na lista e nos detalhes deve ser possível adicionar e remover um personagem a sua lista de favoritos. ✔ Tambem deve ser possível filtrar quais personagens foram favoritados na lista principal. ✔

##### Adição e Remoção de Favoritos

URL BASE: http://private-782d3-starwarsfavorites.apiary-mock.com/

Ao adicionar um favorito a aplicação deve fazer um request para a api starwarsfavorites (documentação disponível no topo do documento). 
A aplicação deve:
+ Exibir a mensagem de retorno da API em caso de sucesso ou erro. ✔
+ Reenviar a requisição da próxima vez que o app for aberto em caso de erro. ✔
+ Salvar no banco de dados local quais personagens foram favoritados. ✔
+ Tratar a remoção de favoritos apenas no banco de dados local. ✔

Em metade das requisições enviadas para a api starwarsfavorites a aplicação deve adicionar o header `Prefer` com o valor `status=400`. ✔

P.S.: O candidato deve escolher o ID. ✔

# Implementações diferenciadas 🚀

+ O aplicativo também fica bonito quando está na horizontal. ✅
+ O aplicativo pode mudar de tema, podendo implementar mais temas de cores diferentes facilmente. ✅
+ O aplicativo tem animações de views compartilhadas. ✅
+ O aplicativo tem animações na recyclerview. ✅
+ O aplicativo tem animações na tela de detalhes. ✅

##### Bugs na implementação 👀

+ Mostra os itens favoritados quando é feito uma pesquisa, mesmo se o item não tiver nada haver com a pesquisa.

###### Possiveis soluções 

+ Ao invés de usar o RemoteMediator para pesquisar e ver todos os itens, pode-se separar e usar um PageSource e verificar se há algum favorito na lista
+ Usar tabelas diferentes

---
#### LICENSE
```
MIT License

Copyright (c) 2017 Popcode Mobile Solutions

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
