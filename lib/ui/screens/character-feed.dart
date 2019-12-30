import 'package:entrevista_android/blocs/character-bloc.dart';
import 'package:entrevista_android/ui/screens/character-details.dart';
import 'package:entrevista_android/ui/shared/star_animation.dart';
import 'package:entrevista_android/ui/widgets/character_attribute.dart';
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

  final _swiperController = new SwiperController();

  StarAnimation _starAnimation;

  @override
  void initState() {
    super.initState();
    
    _starAnimation = StarAnimation();
  }

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
                child: Swiper(
                  autoplay: false,
                  itemCount: bloc.listOfCharacters.length,
                  itemBuilder: (BuildContext context, int index) {
                    return CharacterItem(
                      indexPosition: index,
                    );
                  },
                  controller: _swiperController,
                  itemWidth: 310,
                  autoplayDisableOnInteraction: true,
                  itemHeight: 400,
                  layout: SwiperLayout.STACK,
                  loop: false,
                  onTap: (index) {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => CharacterDetails(
                                character: bloc.listOfCharacters[index])));
                  },
                  onIndexChanged: (index) {
                    /* the http requests are lazy, every time user reachs the end of swiper cards
                              a new request is made 
                            */
                    print("actual = ${bloc.listOfCharacters}");
                    if (index == bloc.listOfCharacters.length - 1) {
                      fetch();
                    }
                  },
                ),
              ),
            ],
          ),
        ],
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
          color: Colors.grey[200], borderRadius: BorderRadius.circular(12)),
      child: SizedBox(
        height: 188,
        width: 288,
        child: Column(
          children: <Widget>[
            Consumer<CharacterBloc>(
              builder: (context, bloc, child) {
                return _buildItem(bloc);
              },
            ),
          ],
        ),
      ),
    );
  }

  Container _buildItem(CharacterBloc bloc) {
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
          CharacterAttribute(
            attribute: 'Height',
            textStyle: TextStyle(
                color: Colors.black, fontSize: 28, fontFamily: 'Lato'),
            description: 'cm',
            value: bloc.listOfCharacters[indexPosition].height,
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
            value: bloc.listOfCharacters[indexPosition].mass,
            icon: Icon(Icons.person),
          ),
          SizedBox(
            height: 20,
          ),
          CharacterAttribute(
            attribute: 'Gender',
            textStyle: TextStyle(
                color: Colors.black, fontSize: 28, fontFamily: 'Lato'),
            value: bloc.listOfCharacters[indexPosition].gender,
            icon: Icon(Icons.person),
          ),
        ],
      ),
    );
  }
}
