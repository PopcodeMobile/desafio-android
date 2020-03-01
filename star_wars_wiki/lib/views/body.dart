import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:get_it/get_it.dart';
import 'package:star_wars_wiki/controllers/character_controller.dart';

class Body extends StatefulWidget {
  @override
  _BodyState createState() => _BodyState();
}

class _BodyState extends State<Body> {

  final controller = GetIt.I.get<CharacterController>();
  
  /*
  ScrollController _scrollController = new ScrollController();

  @override
  void initState() {
    controller.getMoreData();
    super.initState();
    //_scrollController.addListener(() {
    //  if (_scrollController.position.pixels ==
    //      _scrollController.position.maxScrollExtent) {
    //    controller.getMoreData();
    //  }
    //});
  }
  
  @override
  void dispose() {
    //_scrollController.dispose();
    super.dispose();
  }
  */

  @override
  Widget build(BuildContext context) {
    controller.getMoreData();
    return Observer(
      builder: (_) {
        return ListView.separated(
          itemCount: controller.charList.length + 1,
          itemBuilder: (context, index) {
            if (index == controller.charList.length) {
              return _buildProgressIndicator();
            } else {
              String subtitle = controller.formatSubtitle(index);
              return Observer(builder: (_) {
                return ListTile(
                  contentPadding: const EdgeInsets.symmetric(horizontal: 8.0),
                  title: Text(controller.charList[index].name),
                  subtitle: Text(subtitle),
                  leading: IconButton(
                    icon: Icon(Icons.star_border),
                    onPressed: () {}
                  ),
                  trailing: Icon(Icons.chevron_right),
                );
              });
            }
          },
          separatorBuilder: (context, index) { 
            return Divider(
              height: 2.0,
              indent: 20.0,
              endIndent: 20.0,
            );
          },
        );
      }
    );
  }

  _buildProgressIndicator() {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Center(
        child: CircularProgressIndicator(),
        ),
    );
  }
}