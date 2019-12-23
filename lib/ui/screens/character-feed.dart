import 'package:entrevista_android/blocs/character-bloc.dart';

import 'package:flutter/material.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
import 'package:provider/provider.dart';

class CharacterFeed extends StatefulWidget {
  @override
  _CharacterFeedState createState() {
    return _CharacterFeedState();
  }
}

class _CharacterFeedState extends State<CharacterFeed> {
  ScrollController _scrollController = ScrollController();

  //
  CharacterBloc bloc;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();

    var blocProvider = Provider.of<CharacterBloc>(context);
    if (blocProvider != this.bloc) {
      this.bloc = blocProvider;
      this.bloc.load();
    }
  }

  @override
  void dispose() {
    _scrollController.dispose(); //
    super.dispose();
  }

  //"Icon made by Pixel perfect from www.flaticon.com"
  @override
  Widget build(BuildContext context) {
    // length of characters loaded
    var charactersCount = Provider.of<CharacterBloc>(context, listen: false)
        .listOfCharacters
        .length;

    return Scaffold(
      backgroundColor: Colors.black,
      appBar: AppBar(
        title: Text("Personagens",
            style: TextStyle(
                fontFamily: 'Lato', fontWeight: FontWeight.w600, fontSize: 22)),
        backgroundColor:
            Theme.of(context).appBarTheme.copyWith(color: Colors.yellow).color,
      ),
      body: Container(
        child: Swiper(
          itemBuilder: (BuildContext context, int index) {
            //ITEM CARD
            return CharacterItem(
              indexPosition: index,
            );
          },
          itemCount: charactersCount,
          itemWidth: 310,
          itemHeight: 400,
          layout: SwiperLayout.STACK,
          loop: false,
          onIndexChanged: (index) {
            /* the http requests are lazy, every time user reachs the end of swiper cards
              a new request is made 
            */
            if (index == charactersCount - 1) {
              fetch();
            }
          },
        ),
      ),
    );
  }

  /// triggers bloc to get more paginated data
  void fetch() {
    Provider.of<CharacterBloc>(context).load();
  }
}

class CharacterItem extends StatelessWidget {
  final int indexPosition;

  CharacterItem({Key key, @required this.indexPosition}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          color: Colors.white, borderRadius: BorderRadius.circular(12)),
      child: SizedBox(
        height: 188,
        width: 288,
        child: Column(
          children: <Widget>[
            Consumer<CharacterBloc>(
              builder: (context, bloc, child) {
                return Container(
                  padding: EdgeInsets.all(14),
                  child: Column(
                    children: <Widget>[
                      Container(
                          padding: EdgeInsets.symmetric(vertical: 20),
                          child: Text(
                            bloc.listOfCharacters[indexPosition].name,
                            style: TextStyle(
                                fontFamily: 'Lato',
                                fontWeight: FontWeight.bold,
                                fontSize: 26),
                          )),
                      Divider(
                        thickness: 1,
                      ),
                      SizedBox(height: 12),
                      buildCharacterAttribute(
                        attribute: "Height",
                        description: "cm",
                        value: bloc.listOfCharacters[indexPosition].height,
                        icon: Icon(Icons.person),
                      ),
                      SizedBox(
                        height: 20,
                      ),
                      buildCharacterAttribute(
                        attribute: "Mass",
                        description: "kg",
                        value: bloc.listOfCharacters[indexPosition].mass,
                        icon: Icon(Icons.person),
                      ),
                      SizedBox(
                        height: 20,
                      ),
                      buildCharacterAttribute(
                        attribute: "Gender",
                        value: bloc.listOfCharacters[indexPosition].gender,
                        icon: Icon(Icons.person),
                      ),
                    ],
                  ),
                );
              },
            ),
          ],
        ),
      ),
    );
  }

  Widget buildCharacterAttribute(
      {String attribute = "",
      String value = "",
      String description = "",
      Icon icon}) {
    return Container(
      alignment: Alignment.centerLeft,
      child: Row(
        children: <Widget>[
          Icon(Icons.person),
          SizedBox(
            width: 14,
          ),
          Text(
            "$attribute: $value $description",
            style: TextStyle(fontFamily: 'Lato', fontSize: 22),
          ),
        ],
      ),
    );
  }
}
