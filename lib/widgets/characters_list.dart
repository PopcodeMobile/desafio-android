import 'package:entrevista_pop/providers/characters.dart';
import 'package:entrevista_pop/utils/app_routes.dart';
import 'package:entrevista_pop/widgets/character_tile.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class CharactersList extends StatefulWidget {
  @override
  _CharactersListState createState() => _CharactersListState();
}

class _CharactersListState extends State<CharactersList> {
  ScrollController _scrollController = ScrollController();

  int _currentPage = 1;
  bool _scrollLoading = true;
  bool _loadingError = false;
  Function _clearList;
  @override
  void initState() {
    super.initState();
    fetchCharacters(_currentPage);

    this.controlScrollAndLoading();

    _clearList = Provider.of<Characters>(context, listen: false).clearList;
  }

  Future<void> fetchCharacters(int page) async {
    final Characters characters = Provider.of(context, listen: false);
    try {
      await characters.fetchCharacters(page);
      if (_loadingError) {
        setState(() {
          _loadingError = false;
        });
      }
      final nextPage = Provider.of<Characters>(context, listen: false).nextPage;

      setState(() {
        _currentPage = nextPage;
      });
    } catch (error) {
      print(error);
      setState(() {
        _loadingError = true;
      });
    } finally {
      setState(() {
        _scrollLoading = false;
      });
    }
  }

  controlScrollAndLoading() {
    /**
     * Escuta aos eventos de rolagem para detectar
     * quando realizar o carregamento de novos itens.
     */
    _scrollController.addListener(() {
      final fetchTrigger = 0.85 * _scrollController.position.maxScrollExtent;
      final nextPage = Provider.of<Characters>(context, listen: false).nextPage;

      if (nextPage != null &&
          _scrollController.position.pixels > fetchTrigger &&
          !_scrollLoading) {
        setState(() {
          _scrollLoading = true;
        });

        fetchCharacters(_currentPage);
      }
    });
  }

  @override
  void dispose() {
    super.dispose();
    _scrollController.dispose();
    _clearList();
  }

  @override
  Widget build(BuildContext context) {
    final _characters = Provider.of<Characters>(context, listen: false);

    return RefreshIndicator(
      onRefresh: () async {
        setState(() {
          _currentPage = 1;
        });
        _clearList();
        await fetchCharacters(_currentPage);
      },
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        /** 
        * Consome o provider de personagens maneira a evitar renderizações 
        * desnecessárias do componente pai;
        */
        child: _loadingError && _characters.totalCharactersCount == 0
            ? Center(
                child: Text('Impossível carregar a lista de personagens.'),
              )
            : Consumer<Characters>(
                builder: (context, characters, child) {
                  return Stack(
                    children: [
                      ListView.builder(
                        controller: _scrollController..addListener(() {}),
                        itemCount: characters.totalCharactersCount,
                        itemBuilder: (context, index) {
                          final character =
                              characters.characters.values.elementAt(index);

                          /**
                           * Provém os dados de cada personagem
                           * para o componente abaixo afim de evitar
                           * passagem de parâmetros desnecessários
                           * através de contrutor.
                           */
                          return ChangeNotifierProvider.value(
                            value: character,
                            child: CharacterTile(),
                          );
                        },
                      ),
                      if (_scrollLoading)
                        Positioned.fill(
                          bottom: 20,
                          child: Align(
                            alignment: Alignment.bottomCenter,
                            child: Container(
                                width: 35,
                                height: 35,
                                padding: EdgeInsets.all(8),
                                decoration: BoxDecoration(
                                  boxShadow: [
                                    BoxShadow(
                                        blurRadius: 1,
                                        color: Colors.black38,
                                        offset: Offset.fromDirection(1),
                                        spreadRadius: 1)
                                  ],
                                  color: Colors.white,
                                  shape: BoxShape.circle,
                                ),
                                child: CircularProgressIndicator(
                                  strokeWidth: 3,
                                  backgroundColor: Colors.white,
                                )),
                          ),
                        ),
                    ],
                  );
                },
                /** 
           * Informa qual componente pai 
           * para apresentaao dos dados consumidos a partir
           * do provider.
           */
                child: CharactersList(),
              ),
      ),
    );
  }
}
