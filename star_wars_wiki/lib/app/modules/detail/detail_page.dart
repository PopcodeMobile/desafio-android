import 'package:flutter/material.dart';
import 'package:star_wars_wiki/app/modules/detail/custom_card_widget.dart';
import 'package:star_wars_wiki/app/modules/home/home_bloc.dart';
import 'package:star_wars_wiki/app/modules/home/home_module.dart';
import 'package:star_wars_wiki/shared/models/character_model.dart';

import 'detail_bloc.dart';

class DetailPage extends StatefulWidget {
  @override
  _DetailPageState createState() => _DetailPageState();
}

class _DetailPageState extends State<DetailPage> {
  String planet = '', specie = '';
  final bloc = HomeModule.to.getBloc<DetailBloc>();
  final homeBloc = HomeModule.to.getBloc<HomeBloc>();
  CharacterModel char;

  @override
  void initState() {
    setState(() {
      char = homeBloc.selectedChar;
      bloc.fetchInfo(planetPath: char.homeworld, species: char.species);
      bloc.responseIn.add(null);
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var _titleSize = 300 / homeBloc.selectedChar.name.length;
    if (_titleSize > 30)
      _titleSize = 30;
    else if (_titleSize < 16) _titleSize = 16;

    return Scaffold(
      appBar: AppBar(
        title: Text(
          homeBloc.selectedChar.name.toLowerCase(),
          style: TextStyle(
            fontFamily: 'Star Jedi',
            fontSize: _titleSize,
          ),
        ),
        centerTitle: true,
        actions: <Widget>[
          Builder(
            builder: (context) => IconButton(
              icon: char.fav ? Icon(Icons.star) : Icon(Icons.star_border),
              onPressed: () {
                homeBloc.favoriteCharacter(char).then((result) {
                  setState(() {
                    Scaffold.of(context).showSnackBar(
                      SnackBar(
                        duration: Duration(milliseconds: 2000),
                        backgroundColor: Colors.black38,
                        content: Text(
                          result,
                          textAlign: TextAlign.center,
                        ),
                        elevation: 10.0,
                        behavior: SnackBarBehavior.floating,
                        shape: StadiumBorder(),
                      ),
                    );
                  });
                });
              },
              iconSize: 35.0,
            ),
          ),
        ],
      ),
      body: Container(
        color: Colors.orange.withOpacity(0.4),
        child: ListView(
          padding: EdgeInsets.symmetric(vertical: 10.0),
          children: <Widget>[
            StreamBuilder(
                stream: bloc.responseOut,
                builder: (context, snapshot) {
                  if (snapshot.hasError) {
                    return Center(child: Text(snapshot.error.toString()));
                  }

                  if (snapshot.hasData) {
                    return Column(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                            CustomCard(
                              label: 'Height',
                              text: char.height == 'unknown'
                                  ? char.height
                                  : '${char.height} cm',
                            ),
                            CustomCard(
                              label: 'Mass',
                              text: char.mass == 'unknown'
                                  ? char.mass
                                  : '${char.mass} kg',
                            ),
                            CustomCard(
                              label: 'Hair',
                              text: char.hairColor,
                            ),
                            CustomCard(
                              label: 'Skin Color',
                              text: char.skinColor,
                            ),
                            CustomCard(
                              label: 'Birth Year',
                              text: char.birthYear,
                            ),
                            CustomCard(
                              label: 'Gender',
                              text: char.gender,
                            ),
                            CustomCard(
                              label: 'Homeworld',
                              text: bloc.planet.name,
                            ),
                          ] +
                          [
                            CustomCard(
                              label: 'Species',
                              text: bloc.speciesList.isNotEmpty
                                  ? bloc.speciesList[0].name
                                  : '',
                            )
                          ] +
                          bloc.speciesList
                              .skip(1)
                              .map(
                                (specie) => CustomCard(
                                  text: specie.name,
                                ),
                              )
                              .toList(),
                    );
                  } else {
                    return Center(child: CircularProgressIndicator());
                  }
                }),
          ],
        ),
      ),
    );
  }
}
