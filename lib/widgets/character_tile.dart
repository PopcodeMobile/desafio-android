import 'package:entrevista_pop/providers/character.dart';
import 'package:entrevista_pop/utils/app_routes.dart';
import 'package:entrevista_pop/utils/functions.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class CharacterTile extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    /**
     * Recebe a personagem a partir do provider definido
     * um nível acima.  
     */
    final Character character = Provider.of(context, listen: false);
    final gender = capitalize(character.gender);

    /* Apresentação visual da personagem */
    return Card(
      child: Column(
        children: <Widget>[
          ListTile(
            leading: CircleAvatar(
              backgroundColor: Theme.of(context).primaryColor,
              child: Text(
                character.name
                    .replaceAll(new RegExp(r"(\W)"), '')
                    .substring(0, 2)
                    .toUpperCase(),
                style: TextStyle(color: Theme.of(context).accentColor),
              ),
            ),
            title: Text(character.name),
            subtitle: FittedBox(
              fit: BoxFit.scaleDown,
              alignment: Alignment.centerLeft,
              child: Row(
                children: [
                  Text(character.mass != "unknown"
                      ? "${character.mass} KG - "
                      : 'N/A - '),
                  Text("$gender - "),
                  Text(character.height != "unknown"
                      ? "${character.height} cm"
                      : "N/A")
                ],
              ),
            ),
            trailing: IconButton(
              onPressed: () {},
              icon: Icon(
                Icons.star,
                color: Theme.of(context).primaryColor,
              ),
            ),
          ),
          Divider(),
          InkWell(
            onTap: () {
              Navigator.of(context)
                  .pushNamed(AppRoutes.CHARACTER_DETAIL, arguments: character);
            },
            child: Container(
              margin: EdgeInsets.symmetric(horizontal: 20),
              padding: const EdgeInsets.all(10.0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.end,
                children: <Widget>[
                  Text('Detalhes da personagem'),
                  SizedBox(width: 10),
                  Icon(
                    Icons.arrow_forward,
                    color: Theme.of(context).primaryColor,
                  ),
                ],
              ),
            ),
          )
        ],
      ),
    );
  }
}
