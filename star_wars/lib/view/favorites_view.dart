import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:starwars/controller/favorite_controller.dart';
import 'package:starwars/view/favorite_tile.dart';

class Favorites extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final FavoriteController getData =
    BlocProvider.of<FavoriteController>(context);

    getData.getDbFavorites();

    return Scaffold(
      appBar: AppBar(
        title: Text("Favoritos"),
        backgroundColor: Colors.black38,
        centerTitle: true,
        actions: <Widget>[
        ],
      ),
      body: StreamBuilder(
        stream: BlocProvider.of<FavoriteController>(context).outPutData,
        builder: (context, snapshot) {
          if (snapshot.hasData)
            return ListView.builder(
              itemBuilder: (context, index) {
                return FavoriteTile(snapshot.data[index]);
              },
              itemCount: snapshot.data.length,
            );
          else
            return Container(
            );
        },
      ),
      backgroundColor: Colors.white,
    );
  }
}