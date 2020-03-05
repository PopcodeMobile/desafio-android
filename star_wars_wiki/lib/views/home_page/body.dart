import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:get_it/get_it.dart';
import 'package:star_wars_wiki/controllers/character_controller.dart';
import 'package:star_wars_wiki/database/database_provider.dart';
import 'package:star_wars_wiki/views/details_page/character_details.dart';

class Body extends StatelessWidget {

  final controller = GetIt.I.get<CharacterController>();

  @override
  Widget build(BuildContext context) {
    //DatabaseProvider.db.deleteAll();
    return Observer(
      builder: (_) {
        return ListView.separated(
          itemCount: controller.charList.length + 1,
          itemBuilder: (context, index) {
            if (index < controller.charList.length) {
              return _customListTile(context, index);
            } else {
              controller.getMoreData();
              return (controller.hasNextPage) ? _buildProgressIndicator() : null;
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

  _customListTile(context, index) {
    //String subtitle = controller.formatSubtitle(index);
    return Observer(builder: (_) {
      return ListTile(
        contentPadding: const EdgeInsets.symmetric(horizontal: 8.0),
        title: Text('${controller.charList[index].id} ${controller.charList[index].name}'),
        //subtitle: Text(subtitle),
        leading: IconButton(
          icon: controller.charList[index].isFavorite ?
            Icon(
              Icons.star,
              color: Theme.of(context).accentColor,
            ) :
            Icon(
              Icons.star_border
            ),
          onPressed: () {
            controller.setFavorite(index);
          }
        ),
        trailing: Icon(Icons.chevron_right),
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => CharacterDetails(index)),
          );
        },
      );
    });
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