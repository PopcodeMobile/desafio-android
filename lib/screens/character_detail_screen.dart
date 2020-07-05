import 'package:entrevista_pop/utils/constants.dart';
import 'package:flutter/material.dart';

import 'package:entrevista_pop/providers/character.dart';
import 'package:entrevista_pop/widgets/character_detail.dart';
import 'package:hive/hive.dart';

class CharacterDetailScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print('built');
    final Character character = ModalRoute.of(context).settings.arguments;
    return Scaffold(
      appBar: AppBar(
        title: Text(
          character.name,
          style: TextStyle(color: Theme.of(context).accentColor),
        ),
      ),
      body: CharacterDetail(character),
      floatingActionButton: FavoriteFloatButton(character),
    );
  }
}

class FavoriteFloatButton extends StatefulWidget {
  final Character character;
  FavoriteFloatButton(this.character);
  @override
  _FavoriteFloatButtonState createState() => _FavoriteFloatButtonState();
}

class _FavoriteFloatButtonState extends State<FavoriteFloatButton> {
  Box<String> favoritesBox;

  @override
  void initState() {
    super.initState();
    favoritesBox = Hive.box(Constants.favoritesBox);
  }

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
      backgroundColor: Theme.of(context).primaryColor,
      child: Icon(
        Icons.star,
        color: favoritesBox.get(widget.character.id) != null
            ? Theme.of(context).accentColor
            : Colors.white,
      ),
      onPressed: () {
        setState(() {
          widget.character.toggleAsFavorite(favoritesBox, context);
        });
      },
    );
  }
}
