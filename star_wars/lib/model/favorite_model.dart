import 'package:starwars/model/person_model.dart';

class FavoriteModel{

  Future <void> setFav(String id) async {
    PersonDatabase datab = PersonDatabase();
    Person person = await datab.getPerson(id);
    Map<String,dynamic> tempPerson = person.toJson();

    if(tempPerson["favorito"] == null || tempPerson["favorito"] == "0"){
      tempPerson["favorito"] = "1";
    }else{
      tempPerson["favorito"] = "0";
    }
    person = Person.fromJson(tempPerson);
    await datab.updatePerson(person);
  }

}