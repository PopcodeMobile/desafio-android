import 'package:flutter/material.dart';

class HomeMenuDrawer extends StatefulWidget {
  HomeMenuDrawer({Key key}) : super(key: key);

  _HomeMenuDrawerState createState() => _HomeMenuDrawerState();
}

class _HomeMenuDrawerState extends State<HomeMenuDrawer> {
  @override
  Widget build(BuildContext context) {
    return ListView(
      children: <Widget>[
        UserAccountsDrawerHeader(
          decoration: BoxDecoration(image: DecorationImage(image: AssetImage('assets/images/yellow_vector.jpg'), fit: BoxFit.cover)),
          accountName: Text("Star Wars Wiki", style: TextStyle(fontSize: 20, fontFamily: "Kanit", color: Colors.black)),
          // currentAccountPicture: ClipOval(
          //   child: Image.asset(
          //     "assets/images/joao.jpg",
          //     width: 10,
          //     height: 10,
          //     fit: BoxFit.cover,
          //   ),
          // ),
        ),
        Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              linkMenuDrawer('Página Inicial', () {
                Navigator.pushNamed(context, '/');
              }),
              linkMenuDrawer('Lista de Favoritos', () {
                Navigator.pushNamed(context, '/favorites');
              }),
            ]),
      ],
    );
  }
}

//WIDGET COM TINTA AO PRESSIONAR BOTÃO / REDIRECIONA PARA OUTRAS TELAS
Widget linkMenuDrawer(String title, Function onPressed) {
  return InkWell(
    onTap: onPressed,
    splashColor: Colors.yellowAccent,
    child: Container(
      padding: EdgeInsets.symmetric(vertical: 13, horizontal: 15),
      width: double.infinity,
      child: Text(
        title,
        style: TextStyle(fontSize: 17.0, fontFamily: "Kanit"),
      ),
    ),
  );
}
