import 'package:http/http.dart' as http;

class AuxTestConnexion {
  final String _site;

  AuxTestConnexion(this._site);

  Future<bool> connexion() async {
    bool connected = true;
    try {
      http.Response conecTest = await http.get(_site);
      if (conecTest.statusCode != 200) {
        connected = false;
      }
    } catch (e) {
      connected = false;
    }
    return connected;
  }
}
