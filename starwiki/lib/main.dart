import 'package:flutter/material.dart';
import 'package:starwiki/fetcher.dart';
import 'package:starwiki/database_helper.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'StarWiki',
      theme: ThemeData(
        primarySwatch: Colors.grey,
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
  final _pageFetcher = PageFetcher();
  TextEditingController editingController = TextEditingController();
  DatabaseHelper databaseHelper = DatabaseHelper(); 

  bool _isLoading;
  bool _hasMore;
  bool _dbUpdated = false;
  int _peopleIndex = 0;
  int _peoplePageResults = 10;
  //int _maxPeople = 0;
  final _peopleList = List<People>();

  @override
  void initState() {
    super.initState();
    _isLoading = true;
    _hasMore = true;
    _getPeople();
  }

  void _getPeople() async {
    _isLoading = true;
    if (!_dbUpdated) {
      _dbUpdated = true;
      await _pageFetcher.fetch().then((List<People> fetchedPeople) async {
        await databaseHelper.createOrUpdatePeople(fetchedPeople);
      });
    }

    await databaseHelper.getPeopleList(_peopleIndex, _peoplePageResults).then((List<People> fetchedPeople) {
      _peopleIndex += fetchedPeople.length;
      if (fetchedPeople.isEmpty) {
        setState(() {
          _isLoading = false;
          _hasMore = false;
        });
      } else {
        setState(() {
          _isLoading = false;
          _peopleList.addAll(fetchedPeople);
        });
      }
    });
  }

  void _filterSearch(String query) async {
    
  }

  @override
  Widget build(BuildContext context) {

    ListView resultsList() {
      return ListView.builder(
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
                title: Text(_peopleList[index].name),
                subtitle: Text(
                  "Altura: " + _peopleList[index].height + "cm; " +
                  "Genero: " + _peopleList[index].gender + "; " +
                  "Peso: " + _peopleList[index].mass + "kg"),
              ),
            ],
          );
        }
      );
    }
    
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Container(
        child: Column(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextField(
                onChanged: (value) {
                  _filterSearch(value);
                },
                controller: editingController,
                decoration: InputDecoration(
                  labelText: "Search",
                  hintText: "Search",
                  prefixIcon: Icon(Icons.search),
                  border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(25.0)))
                ),
              ),
            ),
            Expanded(child: resultsList(),)
          ]
        ),
      ),
    );
  }
}
