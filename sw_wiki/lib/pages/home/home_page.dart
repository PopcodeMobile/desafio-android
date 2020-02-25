import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'home_controller.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final homeController = Modular.get<HomeController>();

  @override
  Widget build(_) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Star Wars WIKI"),
      ),
      body: Observer(builder: (BuildContext context) {
        if (homeController.characters.error != null) {
          return Center(
              child: RaisedButton(
            onPressed: () {
              homeController.fetchCharacters();
            },
            child: Text("Try again"),
          ));
        }

        if (homeController.characters.value == null) {
          return Center(child: CircularProgressIndicator());
        }

        var list = homeController.characters.value;

        return ListView.builder(
            itemCount: list.length,
            itemBuilder: (BuildContext context, int index) {
              return Card(
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(5.0)),
                elevation: 3.0,
                color: Theme.of(context).primaryColor.withAlpha(100),
                child: ListTile(
                  // contentPadding: EdgeInsets.all(0),

                  title: InkWell(
                    child: Text(list[index].name),
                    onTap: () {},
                  ),
                  subtitle: Text(
                    'Peso:${list[index].mass}kg, Altura: ${list[index].height}cm, Genero: ${list[index].gender}',
                  ),
                  trailing: IconButton(
                    tooltip: 'Favorite',
                    icon: Icon(
                      Icons.star_border,
                      color: Colors.yellow.withAlpha(150),
                      size: 35.0,
                    ),
                    onPressed: () {},
                    splashColor: Theme.of(context).primaryColor,
                  ),
                ),
              );
            });
      }),
      floatingActionButton: FloatingActionButton(
          child: Icon(Icons.add),
          onPressed: () {
            // homeController.text = "from home";
            // Navigator.pushNamed(context, '/detail/${homeController.text}');
            Modular.to.pushNamed('/detail');
          }),
    );
  }
}
