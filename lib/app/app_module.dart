import 'package:wiki_star_wars/app/services/favorites_error_service.dart';
import 'package:wiki_star_wars/app/services/connection_service.dart';
import 'package:wiki_star_wars/app/services/character_favorite_service.dart';
import 'package:wiki_star_wars/app/screens/character_favorites/character_favorites_bloc.dart';
import 'package:wiki_star_wars/app/services/character_local_service.dart';
import 'package:wiki_star_wars/app/screens/character_details/character_details_bloc.dart';
import 'package:wiki_star_wars/app/services/character_service.dart';
import 'package:wiki_star_wars/app/app_bloc.dart';
import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/material.dart';
import 'package:wiki_star_wars/app/app_widget.dart';

class AppModule extends ModuleWidget {
  @override
  List<Bloc> get blocs => [
        Bloc((i) => CharacterFavoritesBloc()),
        Bloc((i) => CharacterDetailsBloc()),
        Bloc((i) => AppBloc()),
      ];

  @override
  List<Dependency> get dependencies => [
        Dependency((i) => FavoritesErrorService()),
        Dependency((i) => ConnectionService()),
        Dependency((i) => CharacterFavoriteService()),
        Dependency((i) => CharacterLocalService()),
        Dependency((i) => CharacterService()),
      ];

  @override
  Widget get view => AppWidget();

  static Inject get to => Inject<AppModule>.of();
}
