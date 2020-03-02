// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'character_controller.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic

mixin _$CharacterController on _CharacterControllerBase, Store {
  Computed<bool> _$hasNextPageComputed;

  @override
  bool get hasNextPage =>
      (_$hasNextPageComputed ??= Computed<bool>(() => super.hasNextPage)).value;

  final _$_nextPageAtom = Atom(name: '_CharacterControllerBase._nextPage');

  @override
  String get _nextPage {
    _$_nextPageAtom.context.enforceReadPolicy(_$_nextPageAtom);
    _$_nextPageAtom.reportObserved();
    return super._nextPage;
  }

  @override
  set _nextPage(String value) {
    _$_nextPageAtom.context.conditionallyRunInAction(() {
      super._nextPage = value;
      _$_nextPageAtom.reportChanged();
    }, _$_nextPageAtom, name: '${_$_nextPageAtom.name}_set');
  }

  final _$charListAtom = Atom(name: '_CharacterControllerBase.charList');

  @override
  ObservableList<Character> get charList {
    _$charListAtom.context.enforceReadPolicy(_$charListAtom);
    _$charListAtom.reportObserved();
    return super.charList;
  }

  @override
  set charList(ObservableList<Character> value) {
    _$charListAtom.context.conditionallyRunInAction(() {
      super.charList = value;
      _$charListAtom.reportChanged();
    }, _$charListAtom, name: '${_$charListAtom.name}_set');
  }

  final _$getMoreDataAsyncAction = AsyncAction('getMoreData');

  @override
  Future getMoreData() {
    return _$getMoreDataAsyncAction.run(() => super.getMoreData());
  }

  final _$_CharacterControllerBaseActionController =
      ActionController(name: '_CharacterControllerBase');

  @override
  String formatSubtitle(dynamic index) {
    final _$actionInfo =
        _$_CharacterControllerBaseActionController.startAction();
    try {
      return super.formatSubtitle(index);
    } finally {
      _$_CharacterControllerBaseActionController.endAction(_$actionInfo);
    }
  }

  @override
  String toString() {
    final string =
        'charList: ${charList.toString()},hasNextPage: ${hasNextPage.toString()}';
    return '{$string}';
  }
}
