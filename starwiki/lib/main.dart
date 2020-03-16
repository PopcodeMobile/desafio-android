import 'package:flutter/material.dart';
import 'package:starwiki/fetcher.dart';
import 'package:starwiki/database_helper.dart';
import 'package:starwiki/personDetailScreen.dart';
import 'dart:io';

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
  final _favoriteHandler = FavoriteHandler();
  TextEditingController editingController = TextEditingController();
  DatabaseHelper databaseHelper = DatabaseHelper();

  bool _isLoading;
  bool _showingFavorites;
  //String _searchQuery;
  final _peopleDatabaseList = List<People>();
  final _peopleList = List<People>();

  @override
  void initState() {
    super.initState();
    _isLoading = true;
    _showingFavorites = false;
    //_searchQuery = '';
    _getPeople();
  }

  void _getPeople() async {
    _isLoading = true;
    List<People> databaseList = List<People>();
    var result;

    try {
      result = await InternetAddress.lookup('example.com').timeout(Duration(seconds: 10));
    } on SocketException catch (_) {
      print("SocketException");
    }

    if (result != null && result[0].rawAddress.isNotEmpty) {
      await _pageFetcher.fetch().then((List<People> fetchedPeople) async {
        await databaseHelper.createOrUpdatePeople(fetchedPeople);
      });
    }

    await databaseHelper.getPeopleList().then((List<People> fetchedPeople) {
      databaseList.addAll(fetchedPeople);
    });

    if (result != null && result[0].rawAddress.isNotEmpty) {
      for (var i = 0; i < databaseList.length; i++) {
        if (databaseList[i].favLater != 'false') {
          databaseList[i] = await _addFavoriteOnStart(databaseList[i], databaseList[i].favLater);
        }
      }
    }

    setState(() {
      _peopleDatabaseList.addAll(databaseList);
      _peopleList.addAll(_peopleDatabaseList);
      _isLoading = false;
    });
  }

  Future<People> _addFavoriteOnStart(People person, String id) async {
    await _favoriteHandler.createFavorite(person, id).then((Map<String, String> response) async {
      if (response["status"] == "success") {
        await databaseHelper.addFavoritePeople(person);
        person.isFav = 'true';
        person.favLater = 'false';
      }
    });
    return person;
  }

  Future<Map<String, String>> _addFavorite(People person, String id) async {
    Map<String, String> message;
    await _favoriteHandler.createFavorite(person, id).then((Map<String, String> response) async {
      message = response;
      if (response["status"] == "success") {
        await databaseHelper.addFavoritePeople(person);
        _updateFavorite(person, 'true');
      } else if (response["status"] == "connection error") {
        //do nothing
      } else {
        await databaseHelper.saveFavoriteForLater(person, id);
        _saveFavoriteForLater(person, id);
      }
    });
    return message;
  }

  void _removeFavorite(People person) async {
    await databaseHelper.removeFavoritePeople(person);
    _updateFavorite(person, 'false');
  }

  void _updateFavorite(People person, String isFav) async {
    int personDatabaseIndex, personListIndex;
    personDatabaseIndex = _peopleDatabaseList.indexOf(person);
    personListIndex = _peopleList.indexOf(person);
    setState(() {
      _peopleDatabaseList[personDatabaseIndex].isFav = isFav;
      _peopleList[personListIndex].isFav = isFav;
    });
  }

  void _saveFavoriteForLater(People person, String id) async {
    int personDatabaseIndex, personListIndex;
    personDatabaseIndex = _peopleDatabaseList.indexOf(person);
    personListIndex = _peopleList.indexOf(person);
    setState(() {
      _peopleDatabaseList[personDatabaseIndex].favLater = id;
      _peopleList[personListIndex].favLater = id;
    });
  }

  Future<String> _inputDialog(BuildContext context) async {
    String favId = '';
    return showDialog(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Enter favorite ID'),
          content: new Row(
            children: <Widget>[
              new Expanded(
                child: new TextField(
                  autofocus: true,
                  decoration: new InputDecoration(
                    labelText: 'Favorite ID', hintText: 'eg. 1'),
                  onChanged: (value) {
                    favId = value;
                  },
                ),
              )
            ]
          ),
          actions: <Widget>[
            FlatButton(
              child: Text('Ok'),
              onPressed: () {
                Navigator.of(context).pop(favId);
              },
            ),
          ],
        );
      }
    );
  }

  Future<void> _favoriteResult(BuildContext context, Map<String, String> message) async {
    return showDialog(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text("Status: " + message["status"]),
          content: Text(message["message"]),
          actions: <Widget>[
            FlatButton(
              child: Text('OK'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      }
    );
  }

  List<People> _filterSearch(String input) {
    List<People> dummySearchList = List<People>();
    dummySearchList.addAll(_peopleDatabaseList);
    List<People> dummyListData = List<People>();

    String query = input.toLowerCase();

    if (query.isNotEmpty) {      
      dummySearchList.forEach((item) {
        if (_showingFavorites) {
          if (item.name.toLowerCase().contains(query) && item.isFav == 'true') {
            dummyListData.add(item);
          }
        } else {
          if (item.name.toLowerCase().contains(query)) {
            dummyListData.add(item);
          }
        }        
      });
    } else {
      if (_showingFavorites) {
        dummySearchList.forEach((item) {
          if (item.isFav == 'true') {
            dummyListData.add(item);
          }
        });
      } else {
        dummyListData.addAll(dummySearchList);
      }
    }

    return dummyListData;
  }

  @override
  Widget build(BuildContext context) {      
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
        actions: <Widget>[
          IconButton(
            icon: Icon(
              _showingFavorites ? Icons.favorite : Icons.favorite_border,
              color: _showingFavorites ? Colors.red : null),
            onPressed: () {
              setState(() {
                _showingFavorites ? _showingFavorites = false : _showingFavorites = true;
                _peopleList.clear();
                _peopleList.addAll(_filterSearch(editingController.text));
              });
            }
          )
        ],
      ),
      body: Container(
        child: Column(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextField(
                onChanged: (value) {
                  List<People> filteredList = _filterSearch(value);
                  setState(() {
                    _peopleList.clear();
                    _peopleList.addAll(filteredList);
                  });
                },
                controller: editingController,
                decoration: InputDecoration(
                  labelText: "Search",
                  hintText: "e.g. \"Obi\", \"vader\", \"kywalker\"",
                  prefixIcon: Icon(Icons.search),
                  border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(25.0)))
                ),
              ),
            ),
            Expanded(
              child: resultsList(_peopleList),
            )
          ]
        ),
      ),
    );
  }

  ListView resultsList(List<People> resultsList) {
      return ListView.builder(
        itemCount: _isLoading ? 1 : resultsList.length,
        itemBuilder: (BuildContext context, int index) {
          if (_isLoading) {
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
              _buildRow(resultsList[index]),
            ],
          );
        }
      );
    }

  Widget _buildRow(People person) {
      final isFavorite = person.isFav == 'true';

      return ListTile(
                title: Text(person.name),
                subtitle: Text(
                  "Height: " + person.height + " cm; " +
                  "Gender: " + person.gender + "; " +
                  "Mass: " + person.mass + " kg"),
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => PersonDetailScreen(
                        person: person,
                        onFavorite: (String isFav) {
                          _updateFavorite(person, isFav);
                        },
                        onFavoriteFail: (String id) {
                          _saveFavoriteForLater(person, id);
                        },
                      )
                    )
                  );
                },
                trailing: IconButton(
                  icon: Icon(
                    isFavorite ? Icons.favorite : Icons.favorite_border,
                    color: isFavorite ? Colors.red : null), 
                  onPressed: () async {
                    if (isFavorite){
                      _removeFavorite(person);
                    } else {
                      final String favId = await _inputDialog(context);
                      final Map<String, String> message = await _addFavorite(person, favId);
                      _favoriteResult(context, message);
                    }
                  }
                ),
              );
    }
}

/*

1. Implement lazy loading
2. Optional aesthetic changes

*/