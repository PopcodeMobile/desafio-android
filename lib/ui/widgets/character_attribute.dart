import 'package:flutter/material.dart';

class CharacterAttribute extends StatelessWidget {
  final String attribute;
  final String value;
  final String description;
  final Icon icon;
  final TextStyle textStyle;

  const CharacterAttribute({Key key, this.attribute = '', this.value = '', this.description = '', this.icon = const Icon(Icons.person), this.textStyle}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.centerLeft,
      child: Row(
        children: <Widget>[
          icon,
          SizedBox(
            width: 14,
          ),
          Text('$attribute', style: textStyle,),
          Text(
            ": $value $description",
            style: textStyle,
          ),
        ],
      ),
    );
  }
}
