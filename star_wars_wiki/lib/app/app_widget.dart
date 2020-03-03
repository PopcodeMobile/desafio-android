import 'package:flutter/material.dart';
import 'package:splashscreen/splashscreen.dart';
import 'package:star_wars_wiki/app/modules/home/home_module.dart';

class AppWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Star Wars Wiki',
      theme: ThemeData(
        primarySwatch: Colors.orange,
      ),
      home: SplashScreen(
        seconds: 5,
        backgroundColor: Colors.orange.withGreen(140),
        image: Image.asset('assets/images/bb8.gif'),
        photoSize: 150,
        navigateAfterSeconds: HomeModule(),
        loaderColor: Colors.white,
        title: Text(
          'Star Wars Wiki',
          style: TextStyle(
            fontFamily: 'Star Jedi',
            fontSize: 35,
          ),
        ),
        loadingText: Text(
          'May the force be with you',
          style: TextStyle(
            color: Colors.white,
            fontFamily: 'Star Jedi',
            fontSize: 18.0,
          ),
        ),
      ),
    );
  }
}
