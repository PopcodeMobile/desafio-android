import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:star_wars_wiki/controllers/character_controller.dart';
import 'package:star_wars_wiki/views/character_profile.dart';

class CharactersList extends StatelessWidget {

  final AsyncSnapshot<Map> snapshot;
  CharactersList(this.snapshot);

  String _formatSubtitle(item) {
    String gender = item['gender'];
    String height = item['height'] + 'cm';
    String mass = item['mass'] + 'kg';
    return '$gender  •  $height  •  $mass';
  }

  @override
  Widget build(BuildContext context) {
    final controller = GetIt.I.get<CharacterController>();
    var results = snapshot.data['results'];
    return ListView.separated(
      itemCount: results.length,
      itemBuilder: (context, index) {
        String subtitle = _formatSubtitle(results[index]);
        return ListTile(
          contentPadding: const EdgeInsets.symmetric(horizontal: 8.0),
          title: Text(results[index]['name']),
          subtitle: Text(subtitle),
          leading: IconButton(
            icon: Icon(Icons.star_border),
            onPressed: () {}
          ),
          trailing: Icon(Icons.chevron_right),
          onTap: () {
            controller.setCharacterInfo(results[index]);
            Navigator.push(context, MaterialPageRoute(
              builder: (context) => CharacterProfile()
              )
            );
          },
        );
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
}