import 'package:entrevista_pop/providers/characters.dart';
import 'package:entrevista_pop/screens/favorites_screen.dart';
import 'package:entrevista_pop/screens/home_screen.dart';
import 'package:entrevista_pop/screens/search_screen.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class NavigateBottomScreen extends StatefulWidget {
  @override
  NavigateBottomScreenState createState() => NavigateBottomScreenState();
}

class NavigateBottomScreenState extends State<NavigateBottomScreen> {
  int _index = 0;
  final List<Widget> _screens = [
    HomeScreen(),
    FavoritesScreen(),
    SearchScreen()
  ];

  @override
  Widget build(BuildContext context) {
    final characters = Provider.of<Characters>(context, listen: false);
    return SafeArea(
      child: Scaffold(
        body: _screens[_index],
        bottomNavigationBar: BottomNavigationBar(
          backgroundColor: Theme.of(context).accentColor,
          onTap: (index) {
            if (index != 2) {
              characters.clearSearch();
            } else if (index == 1) {
              characters.setFavorites();
            }
            setState(() {
              _index = index;
            });
          },
          currentIndex: _index,
          items: [
            BottomNavigationBarItem(
              icon: Icon(Icons.people),
              title: Text('Lista'),
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.star),
              title: Text('Favoritos'),
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.search),
              title: Text('Pesquisar'),
            ),
          ],
        ),
      ),
    );
  }
}
