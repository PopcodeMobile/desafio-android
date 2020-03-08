import 'package:flutter/material.dart';
import 'package:starwiki/fetcher.dart';
import 'package:starwiki/database_helper.dart';
import 'package:starwiki/personDetailScreen.dart';

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
  //bool _hasMore;
  bool _dbUpdated = false;
  //int _peopleIndex = 0;
  //int _peoplePageResults = 10;
  //int _maxPeople = 0;
  final _peopleList = List<People>();
  var _people = List<People>();

  @override
  void initState() {
    super.initState();
    _isLoading = true;
    //_hasMore = true;
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

    await databaseHelper.getPeopleList().then((List<People> fetchedPeople) {
      if (fetchedPeople.isEmpty) {
        setState(() {
          _isLoading = false;
        });
      } else {
        setState(() {
          _isLoading = false;
          _peopleList.addAll(fetchedPeople);
          _people.addAll(_peopleList);
        });
      }
    });
  }

  void _filterSearch(String query) async {
    List<People> dummySearchList = List<People>();
    dummySearchList.addAll(_peopleList);
    if (query.isNotEmpty) {
      List<People> dummyListData = List<People>();
      dummySearchList.forEach((item) {
        if (item.name.contains(query)) {
          dummyListData.add(item);
        }
      });
      setState(() {
        _people.clear();
        _people.addAll(dummyListData);
      });
      return;
    } else {
      setState(() {
        _people.clear();
        _people.addAll(_peopleList);
      });
    }
  }

  @override
  Widget build(BuildContext context) {

    ListView resultsList() {
      return ListView.builder(
        itemCount: _isLoading ? 1 : _people.length,
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
              ListTile(
                title: Text(_people[index].name),
                subtitle: Text(
                  "Height: " + _people[index].height + " cm; " +
                  "Gender: " + _people[index].gender + "; " +
                  "Mass: " + _people[index].mass + " kg"),
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => PersonDetailScreen(person: _people[index],))
                  );
                },
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
