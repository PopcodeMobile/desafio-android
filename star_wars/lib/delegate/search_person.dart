import 'package:flutter/material.dart';
import 'package:starwars/model/person_model.dart';
import 'package:starwars/view/favorite_tile.dart';

class SearchPerson extends SearchDelegate<String> {
  @override
  List<Widget> buildActions(BuildContext context) {
    return [
      IconButton(
        icon: Icon(Icons.clear),
        onPressed: () {
          query = "";
        },
      )
    ];
  }

  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
      icon: AnimatedIcon(
          icon: AnimatedIcons.menu_arrow, progress: transitionAnimation),
      onPressed: () {
        close(context, null);
      },
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    if(query.isEmpty)
      return Container();
    else
      return FutureBuilder<List<Person>>(
        future: getName(query),
        builder: (context, snapshot){
          if(!snapshot.hasData){
            return Center(
              child: CircularProgressIndicator(),
            );
          } else {
            return ListView.builder(
              itemBuilder: (context, index) {
                return FavoriteTile(snapshot.data[index]);
              },
              itemCount: snapshot.data.length,
            );
          }
        },
      );
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    if(query.isEmpty)
      return Container();
    else
      return FutureBuilder<List<Person>>(
        future: getName(query),
        builder: (context, snapshot){
          if(!snapshot.hasData){
            return Center(
              child: CircularProgressIndicator(),
            );
          } else {
            return ListView.builder(
              itemBuilder: (context, index) {
                return FavoriteTile(snapshot.data[index]);
              },
              itemCount: snapshot.data.length,
            );
          }
        },
      );
}
}


Future<List<Person>> getName(String name) async {
  PersonDatabase datab = PersonDatabase();
  List<Person> people = await datab.searchPersons(name);
  return people;
}