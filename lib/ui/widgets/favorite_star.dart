import 'package:flutter/material.dart';

class FavoriteStar extends StatelessWidget {
  bool isFavorite;

  Function onTap;


  FavoriteStar({this.onTap, this.isFavorite});
  @override
  Widget build(BuildContext context) {
    return Container(
          alignment: Alignment.centerRight,
          child: GestureDetector(
            onTap: () {
              this.onTap();
            },
            child: this.isFavorite
                ? Icon(
                    Icons.star,
                    color: Colors.orangeAccent,
                    size: 58,
                  )
                : Icon(
                    Icons.star_border,
                    color: Colors.orangeAccent,
                    size: 58,
                  ),
          ),
        );
  }
}