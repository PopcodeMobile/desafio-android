import 'package:flutter/material.dart';
import 'package:starchars/user_interface/char.dart';

class CharList extends StatefulWidget {
  _InfiniteScrollListViewState createState() => _InfiniteScrollListViewState();
}

class _InfiniteScrollListViewState extends State<CharList> {
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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView.builder(
        itemCount: _listViewData.length,
        controller: _scrollController,
        itemBuilder: (context, index) {
          return CharItem(_listViewData[index]);
        },
      ),
    );
  }
}
