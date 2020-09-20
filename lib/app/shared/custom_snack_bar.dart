import 'package:flutter/material.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';
import 'package:wiki_star_wars/app/shared/text_widget.dart';

SnackBar customSnackBarWidget({
  @required BuildContext context,
  @required String textContent,
  bool isSuccess = true,
  int secondsDuration = 1,
}) {
  return SnackBar(
    duration: Duration(seconds: secondsDuration),
    content: TextWidget(
      text: textContent,
      fontSize: 17,
      color: SecondaryColor,
    ),
    backgroundColor: isSuccess ? PrimaryColor : TertiaryColor,
  );
}
