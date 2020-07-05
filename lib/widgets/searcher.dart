import 'package:entrevista_pop/providers/character.dart';
import 'package:entrevista_pop/providers/characters.dart';
import 'package:entrevista_pop/widgets/character_tile.dart';
import 'package:entrevista_pop/widgets/request_error_message.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class Searcher extends StatefulWidget {
  @override
  _SearcherState createState() => _SearcherState();
}

class _SearcherState extends State<Searcher> {
  int _currentPage = 1;
  bool _searching = false;
  bool _loadingMore = false;
  bool _loadingError = false;

  TextEditingController _searchController = TextEditingController();
  Characters _characters;

  @override
  void initState() {
    super.initState();
    _characters = Provider.of<Characters>(context, listen: false);
  }

  @override
  void dispose() {
    super.dispose();
    _characters.clearSearch();
  }

  search() async {
    try {
      await _characters.search(_searchController.text.trim(), _currentPage);
      if (_loadingError) {
        setState(() {
          _loadingError = false;
        });
      }
    } catch (e) {
      _loadingError = true;
    } finally {
      setState(() {
        _searching = false;
        _loadingMore = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    final characters = Provider.of<Characters>(context);

    final mediaQueryData = MediaQuery.of(context);

    Widget searchResultArea = Column(
      children: <Widget>[
        Container(
          margin: EdgeInsets.only(top: 35, bottom: 15),
          height: MediaQuery.of(context).size.width,
          child: ListView.builder(
            itemCount: characters.searchResult.length,
            itemBuilder: (context, index) {
              final Character character =
                  characters.searchResult.values.toList()[index];

              return ChangeNotifierProvider.value(
                value: character,
                child: CharacterTile(),
              );
            },
          ),
        ),
        if (characters.nextPage != null)
          FlatButton(
            child: _loadingMore
                ? CircularProgressIndicator()
                : Text('Carregar mais'),
            onPressed: characters.nextPage != null
                ? () {
                    if (characters.nextPage != null) {
                      setState(() {
                        _currentPage++;
                        _loadingMore = true;
                      });
                      search();
                    }
                  }
                : null,
          )
      ],
    );

    if (_loadingError) {
      searchResultArea = Container(
          margin: EdgeInsets.only(top: 25), child: RequestErrorMessage());
    }

    return Padding(
      padding: EdgeInsets.all(16),
      child: Container(
        height: MediaQuery.of(context).size.width * 2,
        width: double.infinity,
        child: SingleChildScrollView(
          child: Column(
            children: <Widget>[
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(
                    child: TextField(
                      controller: _searchController,
                    ),
                    width: mediaQueryData.size.width * 0.6,
                  ),
                  FlatButton(
                    onPressed: () {
                      /** Remove o foco do teclado */
                      FocusScopeNode currentFocus = FocusScope.of(context);

                      if (!currentFocus.hasPrimaryFocus) {
                        currentFocus.unfocus();
                      }
                      setState(() {
                        _currentPage = 1;
                        _searching = true;
                      });
                      search();
                    },
                    child: _searching
                        ? CircularProgressIndicator()
                        : Text('Pesquisar'),
                  ),
                ],
              ),
              if (characters.searchResult.isNotEmpty || _loadingError)
                searchResultArea
            ],
          ),
        ),
      ),
    );
  }
}
