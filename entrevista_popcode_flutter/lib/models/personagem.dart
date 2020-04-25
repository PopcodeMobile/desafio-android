class Personagem {
   final String nome;
   final String altura;
   final String genero;
   final String peso;
   String id;
   String value;

   Personagem({this.nome, this.altura, this.genero, this.peso});

   factory Personagem.fromJson(Map<String, dynamic> json){
     return Personagem(
       nome: json['name'] as String,
       altura: json['height'] as String,
       genero: json['gender'] as String,
       peso: json['mass'] as String,
     );
   }
 
  //  Personagem.loading(){
  //   value = "Loading...";
  // }

  //  Personagem.fromMap(Map map){
  //    id = map['id'];
  //    value = map['value'];
  //  }
}