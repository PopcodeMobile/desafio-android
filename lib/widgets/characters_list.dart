import 'package:entrevista_pop/providers/characters.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class CharactersList extends StatefulWidget {
  @override
  _CharactersListState createState() => _CharactersListState();
}

class _CharactersListState extends State<CharactersList> {
  int currentPage = 1;
  @override
  void initState() {
    super.initState();
    fetchCharacters(currentPage);
  }

  fetchCharacters(int page) {
    final Characters characters = Provider.of(context, listen: false);
    characters.fetchCharacters(page);
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.symmetric(
        vertical: 10,
        horizontal: 15,
      ),
      child: Consumer<Characters>(
        builder: (context, characters, child) {
          return ListView.builder(
            itemCount: characters.totalCharactersCount,
            itemBuilder: (context, index) {
              final character = characters.characters.values.elementAt(index);
              final gender =
                  "${character.gender[0].toUpperCase()}${character.gender.substring(1)}";

              return Card(
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: ListTile(
                    leading: CircleAvatar(
                      backgroundColor: Theme.of(context).primaryColor,
                      child: Text(
                        character.name.substring(0, 2).toUpperCase(),
                        style: TextStyle(color: Theme.of(context).accentColor),
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
              );
            },
          );
        },
        child: CharactersList(),
      ),
    );
  }
}
