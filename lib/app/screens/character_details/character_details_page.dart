import 'package:flutter/material.dart';
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/screens/character_details/character_details_bloc.dart';
import 'package:wiki_star_wars/app/screens/home/home_bloc.dart';
import 'package:wiki_star_wars/app/shared/body_background.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';
import 'package:wiki_star_wars/app/shared/custom_icon_button.dart';
import 'package:wiki_star_wars/app/shared/custom_snack_bar.dart';
import 'package:wiki_star_wars/app/shared/lock_screen.dart';
import 'package:wiki_star_wars/app/shared/text_widget.dart';

class CharacterDetailsPage extends StatefulWidget {
  final String title;
  final CharacterModel characterModel;
  final HomeBloc homeBloc;
  const CharacterDetailsPage({
    Key key,
    this.title = "Character Details",
    @required this.characterModel,
    @required this.homeBloc,
  }) : super(key: key);

  @override
  _CharacterDetailsPageState createState() => _CharacterDetailsPageState();
}

class _CharacterDetailsPageState extends State<CharacterDetailsPage> {
  CharacterDetailsBloc _characterDetailsBloc = CharacterDetailsBloc();

  @override
  void initState() {
    _characterDetailsBloc.changeCharacterData(widget.characterModel);
    super.initState();
  }

  @override
  void dispose() {
    _characterDetailsBloc.dispose();
    super.dispose();
  }

  final _scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Scaffold(
          key: _scaffoldKey,
          backgroundColor: Colors.black,
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
            actions: [
              CustomIconButton(
                icon: (widget.characterModel.favorite == 'false')
                    ? Icons.star_border
                    : Icons.star,
                onPressed: () async {
                  if (widget.characterModel.favorite == 'false') {
                    // if not favorite, add as favorite

                    Map<String, String> result = await widget.homeBloc
                        .addFavorite(widget.characterModel);

                    _scaffoldKey.currentState.removeCurrentSnackBar();
                    _scaffoldKey.currentState.showSnackBar(
                      customSnackBarWidget(
                        context: context,
                        textContent: result["message"],
                        secondsDuration: 3,
                      ),
                    );

                    setState(() => widget.characterModel.favorite = 'true');
                  } else {
                    // else, remove from favorites

                    await widget.homeBloc.removeFavorite(widget.characterModel);

                    setState(() => widget.characterModel.favorite = 'false');
                  }
                },
              ),
            ],
          ),
          body: BodyBackground(
            child: StreamBuilder<CharacterModel>(
                stream: _characterDetailsBloc.outCharacter,
                builder: (context, snapshot) {
                  if (!snapshot.hasData)
                    return Center(child: CircularProgressIndicator());
                  else {
                    CharacterModel character = snapshot.data;
                    return ListView(
                      children: [
                        SizedBox(height: 10),
                        TextWidget(
                          text: character.name,
                          fontSize: 22,
                          textAlign: TextAlign.center,
                          fontWeight: FontWeight.bold,
                          color: SecondaryColor,
                        ),
                        SizedBox(height: 10),
                        Table(
                          border: TableBorder(
                            horizontalInside: BorderSide(
                              color: SecondaryColor,
                              style: BorderStyle.solid,
                              width: 1.0,
                            ),
                          ),
                          children: [
                            _addRowTable("Height", character.height),
                            _addRowTable("Mass", character.mass),
                            _addRowTable("Hair Color", character.hairColor),
                            _addRowTable("Skin Color", character.skinColor),
                            _addRowTable("Eye Color", character.eyeColor),
                            _addRowTable("Birth Year", character.birthYear),
                            _addRowTable("Gender", character.gender),
                            _addRowTable("Homeworld", character.homeworld),
                            _addRowTable("Specie", character.species),
                          ],
                        ),
                      ],
                    );
                  }
                }),
          ),
        ),
        LockScreen(stream: widget.homeBloc.outLoading),
      ],
    );
  }
}

_addRowTable(String col1, String col2) {
  return TableRow(
    children: [
      Padding(
        padding: const EdgeInsets.all(8.0),
        child: TextWidget(
          text: col1,
          fontSize: 16,
          fontWeight: FontWeight.bold,
          color: SecondaryColor,
        ),
      ),
      Padding(
        padding: const EdgeInsets.all(8.0),
        child: TextWidget(
          text: col2,
          fontSize: 16,
          color: SecondaryColor,
        ),
      ),
    ],
  );
}
