import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:line_icons/line_icons.dart';
import 'package:starchars/user_interface/charlist.dart';
import 'package:starchars/user_interface/favlist.dart';
import 'package:starchars/user_interface/searchlist.dart';


class TabPage extends StatefulWidget {
  @override
  _TabPageState createState() => _TabPageState();
}

class _TabPageState extends State<TabPage> {
  int selectedIndex = 0;

  PageController controller = PageController();

  List<GButton> tabs = new List();
  List<Color> colors = [
    Color.fromRGBO(126, 120, 99, 1.0),
    Color.fromRGBO(173, 125, 55, 1.0),
    Color.fromRGBO(46, 85, 124, 1.0),
  ];

  @override
  void initState() {
    super.initState();

    var padding = EdgeInsets.symmetric(horizontal: 12, vertical: 5);
    double gap = 30;

    tabs.add(GButton(
      gap: gap,
      iconActiveColor: Color.fromRGBO(126, 120, 99, 1.0),
      iconColor: Colors.black,
      textColor: Color.fromRGBO(126, 120, 99, 1.0),
      color: Color.fromRGBO(126, 120, 99, 0.2),
      iconSize: 24,
      padding: padding,
      icon: LineIcons.user,
      // textStyle: t.textStyle,
      text: 'Characters',
    ));

    tabs.add(GButton(
      gap: gap,
      iconActiveColor: Color.fromRGBO(173, 125, 55, 1.0),
      iconColor: Colors.black,
      textColor: Color.fromRGBO(173, 125, 55, 1.0),
      color: Color.fromRGBO(173, 125, 55, 0.2),
      iconSize: 24,
      padding: padding,
      icon: LineIcons.star_o,
      // textStyle: t.textStyle,
      text: 'Favorites',
    ));

    tabs.add(GButton(
      gap: gap,
      iconActiveColor: Color.fromRGBO(46, 85, 124, 1.0),
      iconColor: Colors.black,
      textColor: Color.fromRGBO(46, 85, 124, 1.0),
      color: Color.fromRGBO(46, 85, 124, 0.2),
      iconSize: 24,
      padding: padding,
      icon: LineIcons.search,
      // textStyle: t.textStyle,
      text: 'Search',
    ));

  }


  @override
  Widget build(BuildContext context) {


    return MaterialApp(

      theme: ThemeData(
        brightness: Brightness.light,
        primaryColor: Colors.yellow[700],
      ),

      home: Scaffold(
        extendBody: true,
        appBar: AppBar(
          brightness: Brightness.dark,
          title: Text('Star Chars', style: TextStyle(color: Colors.black, fontFamily: 'StarWars')),
          ),
        body: PageView.builder(
          onPageChanged: (page) {
            setState(() {
              selectedIndex = page;
            });
          },
          controller: controller,
          itemBuilder: (context, position) {

          if(position == 0){
            return CharList();
          }

          else if (position == 1){
            return FavList();
          }

          else if (position == 2){
            return Debug();
          }


          return Container(color: colors[position],);




          },
          itemCount: tabs.length, // Can be null
        ),


        bottomNavigationBar:
            SafeArea(
          child: Container(
            margin: EdgeInsets.symmetric(horizontal: 0),
            decoration: BoxDecoration(color :Colors.white,boxShadow: [
              BoxShadow(spreadRadius: -10, blurRadius: 60, color: Colors.black.withOpacity(.20), offset: Offset(0,15))
            ]),
            child: Padding(
              padding:
              const EdgeInsets.symmetric(horizontal: 8.0, vertical: 5),
              child: GNav(
                  tabs: tabs,
                  selectedIndex: selectedIndex,
                  onTabChange: (index) {
                    print(index);
                    setState(() {
                      selectedIndex = index;
                    });
                    controller.jumpToPage(index);
                  }),
            ),
          ),
        ),

      ),
    );
  }
}