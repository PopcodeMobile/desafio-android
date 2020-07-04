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

  int currentPage = 1;
  bool scrollLoading = true;
  Function _clearList;
  @override
  void initState() {
    super.initState();
    fetchCharacters(currentPage);

    this.controlScrollAndLoading();

    _clearList = Provider.of<Characters>(context, listen: false).clearList;
  }

  Future<void> fetchCharacters(int page) async {
    final Characters characters = Provider.of(context, listen: false);
    await characters.fetchCharacters(page);

    final nextPage = Provider.of<Characters>(context, listen: false).nextPage;

    setState(() {
      currentPage = nextPage;
      scrollLoading = false;
    });
  }

  controlScrollAndLoading() {
    _scrollController.addListener(() {
      final fetchTrigger = 0.9 * _scrollController.position.maxScrollExtent;
      final nextPage = Provider.of<Characters>(context, listen: false).nextPage;

      if (_scrollController.position.pixels > fetchTrigger &&
          nextPage != null &&
          !scrollLoading) {
        setState(() {
          scrollLoading = true;
        });

        fetchCharacters(currentPage);
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
    return Padding(
      padding: const EdgeInsets.all(8.0),
      /** 
      * Consome o provider de personagens maneira a evitar renderizações 
      * desnecessárias do componente pai;
      */
      child: Consumer<Characters>(
        builder: (context, characters, child) {
          return ListView.builder(
            controller: _scrollController..addListener(() {}),
            itemCount: characters.totalCharactersCount,
            itemBuilder: (context, index) {
              final character = characters.characters.values.elementAt(index);

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
          );
        },
        /** 
         * Informa qual componente pai 
         * para apresentaao dos dados consumidos a partir
         * do provider.
         */
        child: CharactersList(),
      ),
    );
  }
}
