import 'package:flutter/material.dart';

class CustomCard extends StatelessWidget {
  final String text, label;

  CustomCard({this.text: '', this.label: ''});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 60.0,
      padding: EdgeInsets.symmetric(vertical: 0.0, horizontal: 20.0),
      child: Card(
        shape:
            RoundedRectangleBorder(borderRadius: BorderRadius.circular(10.0)),
        clipBehavior: Clip.hardEdge,
        color: Colors.transparent,
        child: Container(
          color: Colors.yellow.withOpacity(0.5),
          padding: EdgeInsets.symmetric(horizontal: 10),
          child: Row(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text(
                label.isEmpty ? '' : '$label:',
                style: TextStyle(fontSize: 20.0),
              ),
              Expanded(flex: 1, child: SizedBox()),
              Text(
                text,
                style: TextStyle(
                  fontFamily: 'Times',
                  fontSize: 20.0,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}