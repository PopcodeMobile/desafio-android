import 'package:flutter/material.dart';
import 'package:starwiki/people.dart';
import 'package:starwiki/fetcher.dart';

//const String peopleURL = "https://swapi.co/api/people/20/";

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'StarWiki',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'StarWiki Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  //List<People> _peopleList = new List();
  final _peopleList = List();
  final _pageFetcher = PageFetcher();

  bool _isLoading;
  bool _hasMore;

  @override
  void initState() {
    super.initState();
    _isLoading = true;
    _hasMore = true;
    _getPeople();
  }

  void _getPeople() {
    _isLoading = true;

    _pageFetcher.fetch().then((List<People> fetchedPage) {
      if (fetchedPage.isEmpty) {
        setState(() {
          _isLoading = false;
          _hasMore = false;
        });
      } else {
        setState(() {
          _isLoading = false;
          _peopleList.addAll(fetchedPage);
        });
      }
    });
  } 

  @override
  Widget build(BuildContext context) {
    
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: ListView.builder(
        itemCount: _hasMore ? _peopleList.length + 1 : _peopleList.length,
        itemBuilder: (BuildContext context, int index) {
          if (index >= _peopleList.length) {
            if (!_isLoading) {
              _getPeople();
            }
            return Center(
              child: SizedBox(
                child: CircularProgressIndicator(),                
                height: 24,
                width: 24,
              ),
            );
          }
          return Column(
            children: <Widget>[
              ListTile(
                title: Text(_peopleList[index].nome),
                subtitle: Text(
                  "Altura: " + _peopleList[index].altura + "cm; " +
                  "Genero: " + _peopleList[index].genero + "; " +
                  "Peso: " + _peopleList[index].massa + "kg"),
              ),
              Divider(),
            ],
          );
        }
      )
    );
  }
}
