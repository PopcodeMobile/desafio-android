// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'character.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic

mixin _$Character on _CharacterBase, Store {
  final _$nameAtom = Atom(name: '_CharacterBase.name');

  @override
  String get name {
    _$nameAtom.context.enforceReadPolicy(_$nameAtom);
    _$nameAtom.reportObserved();
    return super.name;
  }

  @override
  set name(String value) {
    _$nameAtom.context.conditionallyRunInAction(() {
      super.name = value;
      _$nameAtom.reportChanged();
    }, _$nameAtom, name: '${_$nameAtom.name}_set');
  }

  final _$heightAtom = Atom(name: '_CharacterBase.height');

  @override
  int get height {
    _$heightAtom.context.enforceReadPolicy(_$heightAtom);
    _$heightAtom.reportObserved();
    return super.height;
  }

  @override
  set height(int value) {
    _$heightAtom.context.conditionallyRunInAction(() {
      super.height = value;
      _$heightAtom.reportChanged();
    }, _$heightAtom, name: '${_$heightAtom.name}_set');
  }

  final _$massAtom = Atom(name: '_CharacterBase.mass');

  @override
  int get mass {
    _$massAtom.context.enforceReadPolicy(_$massAtom);
    _$massAtom.reportObserved();
    return super.mass;
  }

  @override
  set mass(int value) {
    _$massAtom.context.conditionallyRunInAction(() {
      super.mass = value;
      _$massAtom.reportChanged();
    }, _$massAtom, name: '${_$massAtom.name}_set');
  }

  final _$hairColorAtom = Atom(name: '_CharacterBase.hairColor');

  @override
  String get hairColor {
    _$hairColorAtom.context.enforceReadPolicy(_$hairColorAtom);
    _$hairColorAtom.reportObserved();
    return super.hairColor;
  }

  @override
  set hairColor(String value) {
    _$hairColorAtom.context.conditionallyRunInAction(() {
      super.hairColor = value;
      _$hairColorAtom.reportChanged();
    }, _$hairColorAtom, name: '${_$hairColorAtom.name}_set');
  }

  final _$skinColorAtom = Atom(name: '_CharacterBase.skinColor');

  @override
  String get skinColor {
    _$skinColorAtom.context.enforceReadPolicy(_$skinColorAtom);
    _$skinColorAtom.reportObserved();
    return super.skinColor;
  }

  @override
  set skinColor(String value) {
    _$skinColorAtom.context.conditionallyRunInAction(() {
      super.skinColor = value;
      _$skinColorAtom.reportChanged();
    }, _$skinColorAtom, name: '${_$skinColorAtom.name}_set');
  }

  final _$eyeColorAtom = Atom(name: '_CharacterBase.eyeColor');

  @override
  String get eyeColor {
    _$eyeColorAtom.context.enforceReadPolicy(_$eyeColorAtom);
    _$eyeColorAtom.reportObserved();
    return super.eyeColor;
  }

  @override
  set eyeColor(String value) {
    _$eyeColorAtom.context.conditionallyRunInAction(() {
      super.eyeColor = value;
      _$eyeColorAtom.reportChanged();
    }, _$eyeColorAtom, name: '${_$eyeColorAtom.name}_set');
  }

  final _$birthYearAtom = Atom(name: '_CharacterBase.birthYear');

  @override
  int get birthYear {
    _$birthYearAtom.context.enforceReadPolicy(_$birthYearAtom);
    _$birthYearAtom.reportObserved();
    return super.birthYear;
  }

  @override
  set birthYear(int value) {
    _$birthYearAtom.context.conditionallyRunInAction(() {
      super.birthYear = value;
      _$birthYearAtom.reportChanged();
    }, _$birthYearAtom, name: '${_$birthYearAtom.name}_set');
  }

  final _$genderAtom = Atom(name: '_CharacterBase.gender');

  @override
  String get gender {
    _$genderAtom.context.enforceReadPolicy(_$genderAtom);
    _$genderAtom.reportObserved();
    return super.gender;
  }

  @override
  set gender(String value) {
    _$genderAtom.context.conditionallyRunInAction(() {
      super.gender = value;
      _$genderAtom.reportChanged();
    }, _$genderAtom, name: '${_$genderAtom.name}_set');
  }

  final _$homeworldAtom = Atom(name: '_CharacterBase.homeworld');

  @override
  String get homeworld {
    _$homeworldAtom.context.enforceReadPolicy(_$homeworldAtom);
    _$homeworldAtom.reportObserved();
    return super.homeworld;
  }

  @override
  set homeworld(String value) {
    _$homeworldAtom.context.conditionallyRunInAction(() {
      super.homeworld = value;
      _$homeworldAtom.reportChanged();
    }, _$homeworldAtom, name: '${_$homeworldAtom.name}_set');
  }

  final _$speciesAtom = Atom(name: '_CharacterBase.species');

  @override
  String get species {
    _$speciesAtom.context.enforceReadPolicy(_$speciesAtom);
    _$speciesAtom.reportObserved();
    return super.species;
  }

  @override
  set species(String value) {
    _$speciesAtom.context.conditionallyRunInAction(() {
      super.species = value;
      _$speciesAtom.reportChanged();
    }, _$speciesAtom, name: '${_$speciesAtom.name}_set');
  }

  final _$_CharacterBaseActionController =
      ActionController(name: '_CharacterBase');

  @override
  dynamic setName(String value) {
    final _$actionInfo = _$_CharacterBaseActionController.startAction();
    try {
      return super.setName(value);
    } finally {
      _$_CharacterBaseActionController.endAction(_$actionInfo);
    }
  }

  @override
  String toString() {
    final string =
        'name: ${name.toString()},height: ${height.toString()},mass: ${mass.toString()},hairColor: ${hairColor.toString()},skinColor: ${skinColor.toString()},eyeColor: ${eyeColor.toString()},birthYear: ${birthYear.toString()},gender: ${gender.toString()},homeworld: ${homeworld.toString()},species: ${species.toString()}';
    return '{$string}';
  }
}
