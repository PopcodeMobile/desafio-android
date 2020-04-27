import 'package:flutter/material.dart';
import 'package:getflutter/getflutter.dart';

class Loading extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: GFLoader(
        type: GFLoaderType.custom,
        child: Image(
            image: AssetImage("assets/images/eclipse_loading.gif"),
            width: 70,
            height: 70),
      ),
    );
  }
}
