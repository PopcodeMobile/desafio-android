import 'dart:async';

import 'package:entrevista_android/blocs/character-service.dart';
import 'package:entrevista_android/models/character.dart';
import 'package:entrevista_android/services/swapi-client.dart';
import 'package:entrevista_android/ui/screens/character-details.dart';
import 'package:entrevista_android/ui/shared/star_animation.dart';
import 'package:entrevista_android/ui/shared/toast.dart';
import 'package:entrevista_android/ui/widgets/character_attribute.dart';
import 'package:entrevista_android/ui/widgets/favorite_star.dart';
import 'package:flutter/material.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
import 'package:fluttertoast/fluttertoast.dart';

class CharacterFeed extends StatefulWidget {
  @override
  _CharacterFeedState createState() {
    return _CharacterFeedState();
  }


}

class _CharacterFeedState extends State<CharacterFeed> {
  ScrollController _scrollController = ScrollController();

  final _swiperController = new SwiperController();

  StarAnimation _starAnimation;

  CharacterService _bloc;

  StreamController _streamController = StreamController<List<Character>>();

  Stream<List<Character>> get characterStream => _streamController.stream;
set characterStream(Stream newStream) => newStream;
  StreamSink get sink => _streamController.sink;


  @override
  void initState() {
    _bloc = CharacterService();
    _load();
    super.initState();
    _starAnimation = StarAnimation();
  }

  _load() async {
    await _bloc.load();
    var list = await _bloc.readAllfromDatabase();
    sink.add(list);
  }

  @override
  void dispose() {
    _streamController.close();
    _scrollController.dispose(); //
    super.dispose();
  }

  _childCallback(String message){
    _load();
    showToastMessage(message);
  }

  

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: <Widget>[
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 15),
            child: IconButton(
              icon: Icon(Icons.search),
              onPressed: () {
                Navigator.of(context).pushNamed('/search');
              },
            ),
          )
        ],
        title: Text("Characters",
            style: TextStyle(
                fontFamily: 'Lato', fontWeight: FontWeight.w600, fontSize: 22)),
        backgroundColor:
            Theme.of(context).appBarTheme.copyWith(color: Colors.yellow).color,
      ),
      body: Stack(
        alignment: Alignment.center,
        children: <Widget>[
          Container(
            child: _starAnimation,
          ),
          ListView(
            children: <Widget>[
              Container(
                margin: EdgeInsets.symmetric(vertical: 100),
                child: StreamBuilder<List<Character>>(
                    stream: characterStream,
                    builder: (context, snapshot) {
                      if (snapshot.connectionState == ConnectionState.waiting)
                        return Center(
                          child: CircularProgressIndicator(),
                        );

                      return Swiper(
                        autoplay: false,
                        itemCount: snapshot.data.length,
                        itemBuilder: (BuildContext context, int index) {
                          return CharacterItem(
                            indexPosition: index,
                            character: snapshot.data[index],
                            callback: _childCallback,
                          );
                        },
                        controller: _swiperController,
                        itemWidth: 310,
                        autoplayDisableOnInteraction: true,
                        itemHeight: 400,
                        layout: SwiperLayout.STACK,
                        loop: false,
                        onTap: (index) {
                          Character c = snapshot.data[index];
                          loadCharacter(c).then((character) {
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => CharacterDetails(
                                        character: character)));
                          });
                        },
                        onIndexChanged: (index) {
                          if (index != 86) {
                            _load();
                          }
                        },
                      );
                    }),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Future<Character> loadCharacter(Character c) async {
    var api = SwapiClient();
    var character = await api.loadDetails(c);
    return character;
  }
}

class CharacterItem extends StatelessWidget {
  final int indexPosition;
  final Character character;
  final Function callback;

  CharacterItem(
      {Key key, @required this.indexPosition, @required this.character, this.callback})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          color: Colors.grey[200], borderRadius: BorderRadius.circular(12)),
      child: SizedBox(
        height: 188,
        width: 288,
        child: Column(
          children: <Widget>[
            _buildItem(this.character),
          ],
        ),
      ),
    );
  }

  Container _buildItem(Character character) {
    return Container(
      padding: EdgeInsets.all(14),
      child: Column(
        children: <Widget>[
          Container(
              padding: EdgeInsets.symmetric(vertical: 20),
              child: Text(
                character.name,
                style: TextStyle(
                    fontFamily: 'Lato',
                    fontWeight: FontWeight.bold,
                    fontSize: 26),
              )),
          Divider(
            thickness: 1,
          ),
          SizedBox(height: 12),
          CharacterAttribute(
            attribute: 'Height',
            textStyle: TextStyle(
                color: Colors.black, fontSize: 28, fontFamily: 'Lato'),
            description: 'cm',
            value: character.height,
            icon: Icon(Icons.person),
          ),
          SizedBox(
            height: 20,
          ),
          CharacterAttribute(
            attribute: 'Mass',
            description: "kg",
            textStyle: TextStyle(
                color: Colors.black, fontSize: 28, fontFamily: 'Lato'),
            value: character.mass,
            icon: Icon(Icons.person),
          ),
          SizedBox(
            height: 20,
          ),
          CharacterAttribute(
            attribute: 'Gender',
            textStyle: TextStyle(
                color: Colors.black, fontSize: 28, fontFamily: 'Lato'),
            value: character.gender,
            icon: Icon(Icons.person),
          ),
          SizedBox(
            height: 20,
          ),
          Padding(
            padding: const EdgeInsets.only(right: 118),
            child: FavoriteStar(isFavorite: character.isFavorite, onTap: () async{
              var message = await CharacterService().markFavorite(character);
              callback(message);
              
            },),
          )
        ],
      ),
    );
  }
}
