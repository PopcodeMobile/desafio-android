import 'package:flutter/material.dart';
import 'package:sw_wiki/pages/home.page.dart';

void main() => runApp(SWApp());

class SWApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'SW Wiki',
      theme: ThemeData(
        primarySwatch: Colors.yellow,
      ),
      home: HomePage(),
    );
  }
}
