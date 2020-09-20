import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/screens/character_details/character_details_page.dart';
import 'package:wiki_star_wars/app/screens/character_favorites/character_favorites_page.dart';
import 'package:wiki_star_wars/app/screens/home/data_search.dart';
import 'package:wiki_star_wars/app/screens/home/home_bloc.dart';
import 'package:wiki_star_wars/app/shared/body_background.dart';
import 'package:wiki_star_wars/app/shared/character_tile.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';
import 'package:wiki_star_wars/app/shared/custom_icon_button.dart';
import 'package:wiki_star_wars/app/shared/lock_screen.dart';
import 'package:wiki_star_wars/app/shared/text_widget.dart';

class HomePage extends StatefulWidget {
  final String title;
  const HomePage({Key key, this.title = "Wiki StarWars"}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  HomeBloc _homeBloc = HomeBloc();

  @override
  void initState() {
    _homeBloc.init();
    super.initState();
  }

  @override
  void dispose() {
    _homeBloc.dispose();
    super.dispose();
  }

  final _scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Scaffold(
          key: _scaffoldKey,
          appBar: AppBar(
            leading: CustomIconButton(
              icon: Icons.star,
              onPressed: () {
                Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) =>
                      CharacterFavoritesPage(homeBloc: _homeBloc),
                ));
              },
            ),
            centerTitle: true,
            title: TextWidget(
              text: widget.title,
              fontFamily: STAR_JEDI,
              color: SecondaryColor,
            ),
            actions: [
              CustomIconButton(
                icon: Icons.search,
                onPressed: () async {
                  CharacterModel character = await showSearch(
                      context: context, delegate: DataSearch());
                  if (character != null)
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => CharacterDetailsPage(
                            characterModel: character, homeBloc: _homeBloc),
                      ),
                    );
                },
              ),
              CustomIconButton(
                icon: Icons.refresh,
                onPressed: () {
                  _homeBloc.changeCharacters(null);
                  _homeBloc.init();
                },
              ),
            ],
          ),
          body: BodyBackground(
            child: StreamBuilder<List<CharacterModel>>(
              stream: _homeBloc.outCharacters,
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
                        text: "No characters found", color: SecondaryColor),
                  );
                else {
                  return ListView.builder(
                    padding: const EdgeInsets.all(8),
                    itemCount: snapshot.data.length + 1,
                    itemBuilder: (context, index) {
                      if (index < snapshot.data.length) {
                        return CharacterTile(
                          characterModel: snapshot.data[index],
                          homeBloc: _homeBloc,
                          scaffoldKey: _scaffoldKey,
                        );
                      } else {
                        if (_homeBloc.nextPage())
                          return Container(
                            width: 50,
                            height: 50,
                            child: Center(child: CircularProgressIndicator()),
                          );
                        else
                          return Padding(
                            padding: const EdgeInsets.only(bottom: 8),
                            child: Container(),
                          );
                      }
                    },
                  );
                }
              },
            ),
          ),
        ),
        LockScreen(stream: _homeBloc.outLoading),
      ],
    );
  }
}
