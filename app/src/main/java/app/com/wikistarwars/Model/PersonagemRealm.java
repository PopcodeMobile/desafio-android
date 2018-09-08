package app.com.wikistarwars.Model;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import app.com.wikistarwars.Api.FavouriteService;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import okhttp3.Response;

public class PersonagemRealm  {

    private static PersonagemRealm dbManager;
    private static Realm realm;
    private String realmName = "RealmPersonagem";
    private Context context;
    private PersonagemRealm(Context context) {

        if (realm == null) {
            //Realm Object is Created
            Realm.init(context);
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .name(realmName)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(configuration);
           this.context=context;
        }
    }

    public static PersonagemRealm getRealmInstance(Context context) {

        if (dbManager == null) {
            dbManager = new PersonagemRealm(context);
        }
        return dbManager;
    }

    public static String realmToJson(RealmResults results){
        realm.beginTransaction();
        String result = new Gson().toJson(realm.copyFromRealm(results));
        realm.commitTransaction();
        return result;
    }


    public void addOrUpdateRealmList(RealmList personagem) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(personagem);
        realm.commitTransaction();
    }


    public RealmResults getAllPersonagens() {
        realm.beginTransaction();
        RealmResults realmPersonagens = realm.where(Personagem.class).findAll();
        realm.commitTransaction();
        return realmPersonagens;
    }
    public RealmResults getAllFavouritePersonagens() {
        realm.beginTransaction();
        RealmResults realmPersonagens = realm.where(Personagem.class).equalTo("favourite",true).findAll();
        realm.commitTransaction();
        return realmPersonagens;
    }

    public RealmResults searchPersonagens(String name) {
        realm.beginTransaction();
        RealmResults personagens = realm.where(Personagem.class).contains("name",name, Case.INSENSITIVE).findAll();
        realm.commitTransaction();
        return personagens;
    }

    public Personagem getPersonagem(String name) {
        realm.beginTransaction();
        Personagem personagens = realm.where(Personagem.class).equalTo("name",name).findFirst();
        realm.commitTransaction();
        return personagens;
    }


    public void addRemoveFavourite(String name) throws IOException {
        realm.beginTransaction();
        Personagem p = realm.where(Personagem.class).equalTo("name",name).findFirst();
        p.setFavourite(!p.isFavourite());
        realm.copyToRealmOrUpdate(p);
        realm.commitTransaction();

        if(p.isFavourite())
        new FavouriteService(context).addFavourite(getAllFavouritePersonagens(),context);
    }

    public void addPendingFavourite() throws IOException {
             new FavouriteService(context).addFavourite(getAllFavouritePersonagens(),context);
    }


}
