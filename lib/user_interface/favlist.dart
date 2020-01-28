import 'package:flutter/material.dart';

class FavList extends StatefulWidget {
  _InfiniteScrollListViewState createState() => _InfiniteScrollListViewState();
}

class _InfiniteScrollListViewState extends State<FavList> {
  ScrollController _scrollController = ScrollController();

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(() {
      if (_scrollController.position.pixels ==
          _scrollController.position.maxScrollExtent) {
        _loadMore();
      }
    });
  }

  _loadMore() {
    setState(() {
      print('loading more,...');
      //if we're at the end of the list, add more items
      _listViewData..addAll(List<String>.from(_listViewData));
    });
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  List<String> _listViewData = [
    "Inducesmile.com",
    "Flutter Dev",
    "Android Dev",
    "iOS Dev!",
    "React Native Dev!",
    "React Dev!",
    "express Dev!",
    "Laravel Dev!",
    "Angular Dev!",
    "Adonis Dev!",
    "Next.js Dev!",
    "Node.js Dev!",
    "Vue.js Dev!",
    "Java Dev!",
    "C# Dev!",
    "C++ Dev!",
  ];

  int number = 0;

  @override
  Widget build(BuildContext context) {


    return Scaffold(
      backgroundColor: Colors.transparent,
      body: ListView.builder(
        itemCount: _listViewData.length,
        controller: _scrollController,
        itemBuilder: (context, index) {
          return Container(
              color: Color.fromRGBO(173, 125, 55, 1.0),
              padding: EdgeInsets.fromLTRB(10, 10, 10, 0),
              height: 120,
              width: double.maxFinite,
              child: Card(
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20.0)),
                color: Colors.white,
                child: RaisedButton(onPressed: _changeNumber,
                  child: Text(number.toString()),),
                elevation: 6,
              ));
        },
      ),
    );

  }
  void _changeNumber(){
    setState(() {
      number += 1;
    });

  }


}
