import 'package:flutter/material.dart';

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  TextEditingController _search = TextEditingController();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text('WIKI Star Wars'),
      ),
      body: Container(
        padding: EdgeInsets.all(10),
        child: Column(
          children: <Widget>[
            Text('Filtrar por:'),
            SizedBox(height: 6,),
            Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                SizedBox(
                  width: 290,
                  height: 40,
                  child: TextField(
                      decoration: InputDecoration(
                          hintStyle: TextStyle(fontSize: 12),
                          border: OutlineInputBorder(),
                          suffixIcon: IconButton(
                            onPressed: (){},
                            icon: Icon(Icons.search),
                          ),
                          labelText: 'Pesquisar',
                          hintText: 'Nome, planeta, especie')),
                ),
                SizedBox(
                  child: IconButton(
                    tooltip: 'Favoritos',
                    onPressed: (){},
                    icon: Icon(Icons.star, color: Colors.yellow, size: 32,),
                  ),
                )
              ],
            )
          ],
        ),
      ),
    );
  }
}
