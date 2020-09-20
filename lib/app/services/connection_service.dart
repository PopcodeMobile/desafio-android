import 'dart:io';

import 'package:bloc_pattern/bloc_pattern.dart';

class ConnectionService extends Disposable {
  Future<bool> verifyConnection() async {
    try {
      final result = await InternetAddress.lookup('google.com').timeout(
          Duration(seconds: 5),
          onTimeout: () => throw SocketException("Timeout"));
      if (result.isNotEmpty && result[0].rawAddress.isNotEmpty) {
        return true;
      }
    } on SocketException catch (_) {}
    return false;
  }

  @override
  void dispose() {}
}
