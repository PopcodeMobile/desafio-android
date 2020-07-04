import 'package:entrevista_pop/providers/characters.dart';
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
  @override
  void initState() {
    super.initState();
    fetchCharacters(currentPage);
    this.controlScrollAndLoading();
  }

  fetchCharacters(int page) async {
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
    Provider.of<Characters>(context, listen: false).clearList();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Consumer<Characters>(
        builder: (context, characters, child) {
          return ListView.builder(
            controller: _scrollController..addListener(() {}),
            itemCount: characters.totalCharactersCount,
            itemBuilder: (context, index) {
              final character = characters.characters.values.elementAt(index);
              final gender =
                  "${character.gender[0].toUpperCase()}${character.gender.substring(1)}";

              return Card(
                child: InkWell(
                  onTap: () {},
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: ListTile(
                      leading: CircleAvatar(
                        backgroundColor: Theme.of(context).primaryColor,
                        child: Text(
                          character.name
                              .replaceAll(new RegExp(r"(\W)"), '')
                              .substring(0, 2)
                              .toUpperCase(),
                          style:
                              TextStyle(color: Theme.of(context).accentColor),
                        ),
                      ),
                      title: Text(character.name),
                      subtitle: Row(children: [
                        Text("${character.mass} KGs - "),
                        Text("$gender - "),
                        Text("${character.height} cm")
                      ]),
                    ),
                  ),
                ),
              );
            },
          );
        },
        child: CharactersList(),
      ),
    );
  }
}
