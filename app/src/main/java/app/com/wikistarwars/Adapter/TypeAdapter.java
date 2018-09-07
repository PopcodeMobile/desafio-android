//package app.com.wikistarwars.Adapter;
//
//import android.util.JsonWriter;
//
//import com.google.gson.TypeAdapter;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonToken;
//
//import app.com.wikistarwars.Model.Personagem;
//import app.com.wikistarwars.Model.PersonagemResponse;
//import io.realm.RealmList;
//import io.realm.internal.IOException;
//
//public class RealmStringListTypeAdapter extends TypeAdapter<RealmList<Personagem>> {
//    public static final TypeAdapter<RealmList<Personagem>> INSTANCE =
//            new RealmStringListTypeAdapter().nullSafe();
//
//    private RealmStringListTypeAdapter() { }
//
//    @Override
//    public void write(com.google.gson.stream.JsonWriter out, RealmList<Personagem> value) throws java.io.IOException {
//
//    }
//
//    @Override public void write(JsonWriter out, RealmList<PersonagemResponse> src) throws IOException {
//        out.beginArray();
//        for(Personagem realmString : src) { out.value(realmString.value); }
//        out.endArray();
//    }
//
//    @Override public RealmList<Personagem> read(JsonReader in) throws IOException {
//        RealmList<Personagem> realmStrings = new RealmList<>();
//        in.beginArray();
//        while (in.hasNext()) {
//            if(in.peek() == JsonToken.NULL) {
//                in.nextNull();
//            } else {
//                Personagem realmString = new Personagem();
//                realmString.value = in.nextString();
//                realmStrings.add(realmString);
//            }
//        }
//        in.endArray();
//        return realmStrings;
//    }
//}