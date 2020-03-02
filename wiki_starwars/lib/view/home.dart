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
        title: Text('Wiki Star Wars'),
        backgroundColor: Color(0xFF182E59),
      ),
      body: Container(
        color: Color(0xFF0C1A35),
        padding: EdgeInsets.all(10),
        child: Column(
          children: <Widget>[
            Text('Filtrar por:', style: TextStyle(color: Colors.white),),
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
                          hintStyle: TextStyle(fontSize: 12, color: Colors.white),
                          labelStyle: TextStyle(color: Color(0xFF0387E9)),
                          border: OutlineInputBorder(),
                          suffixIcon: IconButton(
                            onPressed: (){},
                            tooltip: 'Pesquisar',
                            icon: Icon(Icons.search),
                            color: Color(0xFF0387E9),
                          ),
                          labelText: 'Pesquisar',
                          hintText: 'Nome, planeta ou especie'
                      )
                  ),
                ),

                SizedBox(
                  child: IconButton(
                    tooltip: 'Favoritos',
                    onPressed: (){},
                    icon: Icon(Icons.star, color: Colors.yellow, size: 32,),
                    hoverColor: Colors.yellowAccent,
                    splashColor: Colors.yellowAccent,
                  ),
                )
              ],
            ),



          ],
        ),
      ),
    );
  }
}
