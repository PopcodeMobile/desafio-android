import 'package:flutter/material.dart';

class LockScreen extends StatelessWidget {
  final Stream<bool> stream;

  LockScreen({@required this.stream});

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<bool>(
      stream: this.stream,
      initialData: false,
      builder: (context, snapshot) {
        return IgnorePointer(
          ignoring: !snapshot.data,
          child: Container(
            color: snapshot.data ? Colors.black26 : Colors.transparent,
          ),
        );
      },
    );
  }
}
