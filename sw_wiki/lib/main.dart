import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:sw_wiki/app/app_module.dart';

void main() => runApp(
      ModularApp(
        module: AppModule(),
      ),
    );
