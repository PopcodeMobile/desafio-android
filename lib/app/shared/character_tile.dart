import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/screens/character_details/character_details_page.dart';
import 'package:wiki_star_wars/app/screens/character_favorites/character_favorites_bloc.dart';
import 'package:wiki_star_wars/app/screens/home/home_bloc.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';
import 'package:wiki_star_wars/app/shared/custom_icon_button.dart';
import 'package:wiki_star_wars/app/shared/custom_snack_bar.dart';
import 'package:wiki_star_wars/app/shared/text_widget.dart';

class CharacterTile extends StatefulWidget {
  final CharacterModel characterModel;
  final HomeBloc homeBloc;
  final CharacterFavoritesBloc favoriteBloc;
  final GlobalKey<ScaffoldState> scaffoldKey;

  const CharacterTile({
    @required this.characterModel,
    @required this.homeBloc,
    this.favoriteBloc,
    @required this.scaffoldKey,
  });

  @override
  _CharacterTileState createState() => _CharacterTileState();
}

class _CharacterTileState extends State<CharacterTile> {
  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {
        Navigator.of(context).push(MaterialPageRoute(
          builder: (context) => CharacterDetailsPage(
            characterModel: widget.characterModel,
            homeBloc: widget.homeBloc,
          ),
        ));
      },
      child: Card(
        color: Colors.transparent,
        shape: StadiumBorder(
          side: BorderSide(
            color: SecondaryColor,
            width: 2.0,
          ),
        ),
        child: Container(
          padding: const EdgeInsets.all(8.0),
          color: Colors.transparent,
          child: ListTile(
            title: TextWidget(
              text: widget.characterModel.name,
              fontSize: 16,
              color: SecondaryColor,
            ),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                TextWidget(
                  text: "Height ${widget.characterModel.height}",
                  color: SecondaryColor,
                ),
                TextWidget(
                  text: "Gender: ${widget.characterModel.gender}",
                  color: SecondaryColor,
                ),
                TextWidget(
                  text: "Mass: ${widget.characterModel.mass}",
                  color: SecondaryColor,
                ),
              ],
            ),
            trailing: CustomIconButton(
              icon: (widget.characterModel.favorite == 'false')
                  ? Icons.star_border
                  : Icons.star,
              onPressed: () async {
                if (widget.characterModel.favorite == 'false') {
                  // if not favorite, add as favorite

                  Map<String, String> result =
                      await widget.homeBloc.addFavorite(widget.characterModel);

                  widget.scaffoldKey.currentState.removeCurrentSnackBar();
                  widget.scaffoldKey.currentState.showSnackBar(
                    customSnackBarWidget(
                      context: context,
                      textContent: result["message"],
                      secondsDuration: 3,
                    ),
                  );
                } else {
                  // else, remove from favorites

                  await widget.homeBloc.removeFavorite(widget.characterModel);
                  if (widget.favoriteBloc != null)
                    await widget.favoriteBloc
                        .removeFavorite(widget.characterModel);
                }
              },
            ),
          ),
        ),
      ),
    );
  }
}
