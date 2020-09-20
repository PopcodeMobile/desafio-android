import 'package:flutter/material.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';

class CustomIconButton extends StatelessWidget {
  final IconData icon;
  final Function onPressed;

  CustomIconButton({
    @required this.icon,
    @required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    return IconButton(
      splashColor: SecondaryColor,
      icon: Icon(
        this.icon,
        color: SecondaryColor,
      ),
      onPressed: this.onPressed,
    );
  }
}
