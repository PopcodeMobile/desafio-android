import 'package:flutter/material.dart';

import 'constants.dart';

class BodyBackground extends StatelessWidget {
  final Widget child;
  BodyBackground({@required this.child});
  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: PrimaryColor,
        image: DecorationImage(
          image: AssetImage(PrimaryBackground),
          fit: BoxFit.cover,
        ),
      ),
      child: this.child,
    );
  }
}
