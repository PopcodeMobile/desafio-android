
import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';
import 'package:starwars/helper/aux_get_data.dart';
import 'package:starwars/model/person_model.dart';
import 'package:http/http.dart' as http;
import 'package:starwars/model/person_model.dart';

class GetDataController implements BlocBase {

  BehaviorSubject<List<Person>> _dataController =
      BehaviorSubject<List<Person>>();
  Stream<List<Person>> get outData => _dataController.stream;
  Sink<List<Person>> get inData => _dataController.sink;

  PersonDatabase datab = PersonDatabase();

  Future<void> getData() async {

    bool conectado = await conexion();
    if(conectado){
      List<Person> people = List<Person>();
      for (int id = 1; id <= 1; id++) { // Por enquanto, pegar somente 1 para teste
        http.Response response =
        await http.get("https://swapi.co/api/people/$id/");
        if (response.statusCode == 200) {
          var decoded = await AuxGetData(response).getPersonModified();
          Person character = Person.fromJson(decoded, id);
          await datab.savePerson(character);
          people.add(character);
          inData.add(people);
        }
      }
    }else{
      List<Person> people = await datab.getAllPersons();
      inData.add(people);
    }

  }
  
  Future <bool> conexion() async{
    bool conected = true;
    try {
      http.Response conecTest = await http.get("https://swapi.co/");
    }catch(e){
      conected = false;
    }
    return conected;
  }

  @override
  void dispose() {
    _dataController.close();
  }
}
