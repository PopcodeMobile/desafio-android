import 'dart:convert';

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

  @override
  Widget build(BuildContext context) {
    var charactersCount = Provider.of<CharacterBloc>(context, listen: false)
        .listOfCharacters
        .length;

    return Scaffold(
      appBar: AppBar(
        title: Text("Personagens"),
      ),
      body: Container(
        decoration: BoxDecoration(color: Colors.black),
        child: Swiper(
          itemBuilder: (BuildContext context, int index) {
            //ITEM CARD
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
                        return Column(children: <Widget>[
                          Text(bloc.listOfCharacters[index].name, style: TextStyle(fontSize: 18),),
                          Text(bloc.listOfCharacters[index].height, style: TextStyle(fontSize: 18),),
                        ],);
                      },
                    ),
                  ],
                ),
              ),
            );
          },
          itemCount: charactersCount,
          itemWidth: 310,
          itemHeight: 400,
          layout: SwiperLayout.STACK,
          loop: false,
          onIndexChanged: (index) {
            print("index = $index charCount = $charactersCount");
            if (index == charactersCount - 1) {
              fetch();
              print("more");
            }
          },
        ),
      ),
    );
  }

  void fetch() {
    Provider.of<CharacterBloc>(context).load();
  }
}
