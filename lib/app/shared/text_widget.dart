import 'package:flutter/material.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';

class TextWidget extends StatelessWidget {
  final String text;
  final double fontSize;
  final TextAlign textAlign;
  final FontWeight fontWeight;
  final Color color;
  final String fontFamily;
  TextWidget({
    @required this.text,
    this.fontSize,
    this.textAlign,
    this.fontWeight,
    this.color,
    this.fontFamily = UBUNTU_MONO,
  });
  @override
  Widget build(BuildContext context) {
    return Text(
      this.text,
      textAlign: this.textAlign,
      style: TextStyle(
        fontSize: this.fontSize,
        fontFamily: this.fontFamily,
        fontWeight: this.fontWeight,
        color: this.color,
      ),
    );
  }
}
