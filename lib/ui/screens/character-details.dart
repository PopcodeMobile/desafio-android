import 'package:entrevista_android/blocs/character-service.dart';
import 'package:entrevista_android/models/character.dart';
import 'package:entrevista_android/services/swapi-client.dart';
import 'package:entrevista_android/ui/shared/star_animation.dart';
import 'package:entrevista_android/ui/widgets/character_attribute.dart';
import 'package:entrevista_android/ui/widgets/favorite_star.dart';
import 'package:flutter/material.dart';
import 'package:giffy_dialog/giffy_dialog.dart';
import 'package:provider/provider.dart';

class CharacterDetails extends StatefulWidget {
  final Character character;

  const CharacterDetails({Key key, this.character}) : super(key: key);

  @override
  _CharacterDetailsState createState() => _CharacterDetailsState();
}

class _CharacterDetailsState extends State<CharacterDetails> {

 Character character;
  final _api = SwapiClient();
  @override
  void initState() {
    this.character = widget.character;
    super.initState();
  }
  
  

  checkFavorite() {
    showDialog(
        context: context,
        builder: (_) => NetworkGiffyDialog(
              image: Image.network(
                "https://raw.githubusercontent.com/tnorthcutt/gifs/master/star-wars-kylo-ren-darth-vader-lightsaber-gift.gif",
                fit: BoxFit.cover,
              ),
              title: buildCharacterQuestion(),
              onlyOkButton: true,
              buttonRadius: 20,
              buttonOkColor: Colors.black87,
              onOkButtonPressed: () {
                CharacterService()
                    .markFavorite(character);
                    setState(() {
                      character.isFavorite = !character.isFavorite;
                    });

                //to close dialog
                Navigator.pop(context);
              },
            ));
  }

  Text buildCharacterQuestion() {
    var question;

    if (!character.isFavorite) {
      question =
          'Do you want ${character.name} to be part of your Favorites?';
    } else {
      question = 'If it is what you want...';
    }

    return Text(question,
        textAlign: TextAlign.center,
        style: TextStyle(
            fontSize: 22.0,
            fontWeight: FontWeight.w600,
            color: Colors.black87));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 3,
        
        actions: <Widget>[buildStarWarsLogo()] ,
        title: Text('${character.name}',
            style: TextStyle(
                fontFamily: 'Lato', fontWeight: FontWeight.w600, fontSize: 22)),
      ),
      body: Container(
        color: Colors.black87,
        child: Stack(
          //
          children: <Widget>[
            StarAnimation(),
            topContent(context),
            
            Padding(
              padding: EdgeInsets.only(
                  top: MediaQuery.of(context).size.height / 2.5),
              child: bottomContent(context),
            ),
          ],
        ),
      ),
    );
  }

  Container buildStarWarsLogo() {
    return Container(
            alignment: Alignment.center,
            width: 100,
            height: 100,
            decoration: BoxDecoration(
                image: DecorationImage(
                    image: ExactAssetImage('assets/imgs/star-wars-logo.png'),
                    fit: BoxFit.cover)),
          );
  }

  topContentText() => Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          FavoriteStar(isFavorite: character.isFavorite, onTap: checkFavorite,),
          SizedBox(height: 12.0),
          Text(
            '${character.name}',
            style: TextStyle(color: Colors.black, fontSize: 45.0),
          ),
          SizedBox(height: 10.0),
          Align(
            alignment: Alignment.centerRight,
            child: Text(
              '${character.gender}',
              style: TextStyle(color: Colors.black, fontSize: 32.0),
            ),
          ),
        ],
      );

 

  topContent(BuildContext context) {
    return Stack(
      children: <Widget>[
        ClipPath(
          clipper: HeaderClipper(),
          child: Container(
            height: MediaQuery.of(context).size.height * 0.5,
            padding: EdgeInsets.all(40.0),
            width: MediaQuery.of(context).size.width,
            decoration: BoxDecoration(color: Colors.yellow),
            child: Center(
              child: topContentText(),
            ),
          ),
        ),
      ],
    );
  }

  bottomContent(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(left: 18),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        children: <Widget>[
          CharacterAttribute(
            attribute: 'Height',
            description: "cm",
            value: character.height,
            textStyle: TextStyle(
                color: Colors.white, fontSize: 22, fontFamily: 'Lato'),
            icon: Icon(
              Icons.person,
              color: Colors.white,
            ),
          ),
          SizedBox(
            height: 20,
          ),
          CharacterAttribute(
            attribute: 'Mass',
            description: "kg",
            value: character.mass,
            textStyle: TextStyle(
                color: Colors.white, fontSize: 22, fontFamily: 'Lato'),
            icon: Icon(
              Icons.person,
              color: Colors.white,
            ),
          ),
          SizedBox(
            height: 20,
          ),
          CharacterAttribute(
            attribute: 'Hair Color',
            value: character.hair_color,
            textStyle: TextStyle(
                color: Colors.white, fontSize: 22, fontFamily: 'Lato'),
            icon: Icon(
              Icons.person,
              color: Colors.white,
            ),
          ),
          SizedBox(
            height: 20,
          ),
          CharacterAttribute(
            attribute: 'Skin Color',
            value: character.skin_color,
            textStyle: TextStyle(
                color: Colors.white, fontSize: 22, fontFamily: 'Lato'),
            icon: Icon(
              Icons.person,
              color: Colors.white,
            ),
          ),
          SizedBox(
            height: 20,
          ),
          CharacterAttribute(
            attribute: 'Specie',
            value: character.specie.isEmpty ? '' : character.specie[0],
            textStyle: TextStyle(
                color: Colors.white, fontSize: 22, fontFamily: 'Lato'),
            icon: Icon(
              Icons.person,
              color: Colors.white,
            ),
          ),
          SizedBox(
            height: 20,
          ),
          CharacterAttribute(
            attribute: 'Birth Planet',
            value: character.birth_planet,
            textStyle: TextStyle(
                color: Colors.white, fontSize: 22, fontFamily: 'Lato'),
            icon: Icon(
              Icons.person,
              color: Colors.white,
            ),
          ),
          SizedBox(
            height: 20,
          ),
          CharacterAttribute(
            attribute: 'Birth Year',
            value: character.birth_year,
            textStyle: TextStyle(
                color: Colors.white, fontSize: 22, fontFamily: 'Lato'),
            icon: Icon(
              Icons.person,
              color: Colors.white,
            ),
          ),
        ],
      ),
    );
  }
}

class HeaderClipper extends CustomClipper<Path> {
  @override
  Path getClip(Size size) {
    Path path = Path();
    var sw = size.width;
    var sh = size.height;

    path.lineTo(sw, 0);
    path.lineTo(sw, sh);
    path.cubicTo(sw, sh * 0.7, 0, sh * 0.8, 0, sh * 0.55);
    path.lineTo(0, sh);
    path.close();
    return path;
  }

  @override
  bool shouldReclip(CustomClipper<Path> oldClipper) {
    return false;
  }
}
