import 'package:flutter/material.dart';
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/screens/character_favorites/character_favorites_bloc.dart';
import 'package:wiki_star_wars/app/screens/home/home_bloc.dart';
import 'package:wiki_star_wars/app/shared/body_background.dart';
import 'package:wiki_star_wars/app/shared/character_tile.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';
import 'package:wiki_star_wars/app/shared/custom_icon_button.dart';
import 'package:wiki_star_wars/app/shared/text_widget.dart';

class CharacterFavoritesPage extends StatefulWidget {
  final String title;
  final HomeBloc homeBloc;
  const CharacterFavoritesPage({
    Key key,
    this.title = "Character Favorites",
    @required this.homeBloc,
  }) : super(key: key);

  @override
  _CharacterFavoritesPageState createState() => _CharacterFavoritesPageState();
}

class _CharacterFavoritesPageState extends State<CharacterFavoritesPage> {
  CharacterFavoritesBloc _favoriteBloc = CharacterFavoritesBloc();

  @override
  void initState() {
    _favoriteBloc.getCharacterFavorites();
    super.initState();
  }

  @override
  void dispose() {
    _favoriteBloc.dispose();
    super.dispose();
  }

  final _scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      backgroundColor: PrimaryColor,
      appBar: AppBar(
        leading: CustomIconButton(
          icon: Icons.arrow_back,
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
        title: TextWidget(
          text: widget.title,
          fontFamily: STAR_JEDI,
          color: SecondaryColor,
          fontSize: 16,
        ),
      ),
      body: BodyBackground(
        child: StreamBuilder<List<CharacterModel>>(
          stream: _favoriteBloc.outCharacters,
          builder: (context, snapshot) {
            if (snapshot.hasError)
              return Center(
                child: TextWidget(
                  text: snapshot.error,
                  color: SecondaryColor,
                ),
              );
            else if (!snapshot.hasData)
              return Center(child: CircularProgressIndicator());
            else if (snapshot.data.length == 0)
              return Center(
                child: TextWidget(
                  text: "Your favorites will appear here...",
                  color: SecondaryColor,
                ),
              );
            else {
              return ListView.builder(
                itemCount: snapshot.data.length,
                itemBuilder: (context, index) {
                  return CharacterTile(
                    characterModel: snapshot.data[index],
                    homeBloc: widget.homeBloc,
                    favoriteBloc: _favoriteBloc,
                    scaffoldKey: _scaffoldKey,
                  );
                },
              );
            }
          },
        ),
      ),
    );
  }
}
