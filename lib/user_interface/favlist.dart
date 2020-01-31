import 'package:flutter/material.dart';
import 'package:starchars/data/Character.dart';
import 'package:starchars/data/CharacterManager.dart';
import 'package:starchars/data/DatabaseManager.dart';
import 'package:starchars/user_interface/char.dart';

class FavList extends StatefulWidget {
  _InfiniteScrollListViewState createState() => _InfiniteScrollListViewState();
}

class _InfiniteScrollListViewState extends State<FavList> {
  ScrollController _scrollController = ScrollController();
  List<Character> _listViewData = List();
  bool done = false;

  @override
  void initState() {
    super.initState();
    getFavorites();
  }

  void getFavorites() async {
    _listViewData = await CharacterManager.getFavorites();
    setState(() {});
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    if (_listViewData.isEmpty) {
      return Container(
        color: Color.fromRGBO(126, 120, 99, 1.0),
        child: Center(
            child: Text(
          "You don't have any favorites :(",
          style: TextStyle(fontSize: 20, color: Colors.white),
        )),
      );
    }

    return Scaffold(
        body: new Container(
      color: Color.fromRGBO(126, 120, 99, 1.0),
      child: ListView.builder(
        itemCount: _listViewData.length,
        controller: _scrollController,
        itemBuilder: (context, index) {
          return CharItem(_listViewData[index], notifyParent: refresh);
        },
      ),
    ));
  }

  refresh() {
    getFavorites();
  }
}
