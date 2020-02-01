import 'package:flutter/material.dart';
import 'package:starchars/data/Character.dart';
import 'package:starchars/data/CharacterManager.dart';
import 'package:starchars/data/DatabaseManager.dart';
import 'package:starchars/user_interface/char.dart';


class CharList extends StatefulWidget {
  _InfiniteScrollListViewState createState() => _InfiniteScrollListViewState();
}

class _InfiniteScrollListViewState extends State<CharList> {
  ScrollController _scrollController = ScrollController();
  List<Character> _listViewData;
  bool done = false;


  @override
  void initState() {
    super.initState();
    getFirstBatch();
    _scrollController.addListener(() {
      if (_scrollController.position.pixels ==
          _scrollController.position.maxScrollExtent) {
        _loadMore();
      }
    });
  }

  void getFirstBatch() async {
    _listViewData = await CharacterManager.getCharChunk(1, 10);
    setState(() {

    });
  }


  _loadMore() async {
    List<Character> more = await CharacterManager.getCharChunk(_listViewData[_listViewData.length - 1].id + 1, 10);
    setState(() {
      print('loading more,...');

        _listViewData..addAll(more);
    });
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }


  @override
  Widget build(BuildContext context) {
    while (_listViewData == null) {
      return Center(child: CircularProgressIndicator(backgroundColor: Color.fromRGBO(126, 120, 99, 1.0),));
    }

      return Scaffold(
        body: new Container(
        color: Color.fromRGBO(126, 120, 99, 1.0),
          child: ListView.builder(
          itemCount: _listViewData.length,
          controller: _scrollController,
          itemBuilder: (context, index) {
            return CharItem(_listViewData[index]);
          },
        ),
      ));

  }
}











