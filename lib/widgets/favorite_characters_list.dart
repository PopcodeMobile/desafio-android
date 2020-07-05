import 'package:entrevista_pop/providers/character.dart';
import 'package:entrevista_pop/providers/characters.dart';
import 'package:entrevista_pop/widgets/character_tile.dart';
import 'package:flutter/material.dart';

import 'package:provider/provider.dart';

class FavoriteCharactersList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final Characters characters = Provider.of<Characters>(context);
    final List<Character> favoriteCharacters = characters.favoriteCharacters;

    return characters.favoriteCharactersCount > 0
        ? Padding(
            padding: EdgeInsets.all(8),
            child: ListView.builder(
              itemCount: favoriteCharacters.length,
              itemBuilder: (context, index) {
                return ChangeNotifierProvider.value(
                  value: favoriteCharacters[index],
                  builder: (context, child) {
                    return CharacterTile();
                  },
                );
              },
            ),
          )
        : Center(
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                'A força ainda não está com você. Favorite uma personagem para vê-la aqui.',
                textAlign: TextAlign.center,
                textScaleFactor: 1.5,
              ),
            ),
          );
  }
}
