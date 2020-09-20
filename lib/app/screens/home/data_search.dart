import 'package:flutter/material.dart';
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/services/character_local_service.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';
import 'package:wiki_star_wars/app/shared/custom_icon_button.dart';
import 'package:wiki_star_wars/app/shared/text_widget.dart';

class DataSearch extends SearchDelegate<CharacterModel> {
  CharacterModel characterModel;

  @override
  ThemeData appBarTheme(BuildContext context) {
    assert(context != null);
    final ThemeData theme = Theme.of(context);
    assert(theme != null);
    return theme.copyWith(
      primaryColor: PrimaryColor,
      primaryIconTheme: theme.primaryIconTheme.copyWith(color: SecondaryColor),
      primaryColorBrightness: Brightness.dark,
      primaryTextTheme: theme.textTheme,
    );
  }

  @override
  List<Widget> buildActions(BuildContext context) {
    return [
      CustomIconButton(
        icon: Icons.clear,
        onPressed: () => query = "",
      ),
    ];
  }

  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
      icon: AnimatedIcon(
        icon: AnimatedIcons.menu_arrow,
        progress: transitionAnimation,
      ),
      onPressed: () {
        close(context, null);
      },
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    Future.delayed(Duration.zero)
        .then((value) => close(context, characterModel));
    return Container();
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    if (query.isEmpty)
      return Container();
    else
      return FutureBuilder<List<CharacterModel>>(
        future: suggestions(query),
        builder: (context, snapshot) {
          if (!snapshot.hasData)
            return Center(child: CircularProgressIndicator());
          else {
            return ListView.builder(
                itemCount: snapshot.data.length,
                itemBuilder: (context, index) {
                  CharacterModel character = snapshot.data[index];
                  characterModel = snapshot.data[0];
                  return InkWell(
                    onTap: () {
                      close(context, character);
                    },
                    child: Card(
                      shape: StadiumBorder(
                        side: BorderSide(
                          color: SecondaryColor,
                          width: 2.0,
                        ),
                      ),
                      child: Container(
                        padding: const EdgeInsets.all(8.0),
                        color: PrimaryColor,
                        child: ListTile(
                          title: TextWidget(
                            text: character.name,
                            fontSize: 16,
                            color: SecondaryColor,
                          ),
                          subtitle: Column(
                            crossAxisAlignment: CrossAxisAlignment.stretch,
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              TextWidget(
                                text: "Height ${character.height}",
                                color: SecondaryColor,
                              ),
                              TextWidget(
                                text: "Gender: ${character.gender}",
                                color: SecondaryColor,
                              ),
                              TextWidget(
                                text: "Mass: ${character.mass}",
                                color: SecondaryColor,
                              ),
                            ],
                          ),
                          trailing: Icon(
                            (character.favorite == 'false')
                                ? Icons.star_border
                                : Icons.star,
                            color: SecondaryColor,
                          ),
                        ),
                      ),
                    ),
                  );
                });
          }
        },
      );
  }
}

Future<List<CharacterModel>> suggestions(String search) async {
  CharacterLocalService characters = CharacterLocalService();
  return await characters.getSuggestions(search);
}
