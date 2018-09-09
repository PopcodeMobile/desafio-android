package app.com.wikistarwars.Model;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import app.com.wikistarwars.Api.FavouriteService;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class PersonagemRealm {

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
            this.context = context;
        }
    }

    public static PersonagemRealm getRealmInstance(Context context) {

        if (dbManager == null) {
            dbManager = new PersonagemRealm(context);
        }
        return dbManager;
    }

    public static String realmToJson(RealmResults results) {
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

    public void addOrUpdate(RealmList personagem) {

//        RealmResults<Personagem> p = dbManager.getAllPersonagens();
//        realm.beginTransaction();
//        for(int i =0; personagem.size() ;i++){
//            if(p.contains(personagem.get(i)))
//            {
//
//            }else{
//                realm.copyToRealmOrUpdate(personagem.get(i));
//            }
//        }
//        realm.commitTransaction();
    }


    public void createOrUpdateAllFromJson(String personagem) {

        realm.beginTransaction();
        realm.createOrUpdateAllFromJson(Personagem.class, personagem);
        realm.commitTransaction();

    }

    public RealmResults getAllPersonagens() {
        realm.beginTransaction();
        RealmResults realmPersonagens = realm.where(Personagem.class).findAll();
        realm.commitTransaction();
        return realmPersonagens;
    }

    public RealmResults getAllFavouritePersonagens() {
        RealmResults<Favorite> favorites = getAllFavourite();
        if(favorites.size()==0)
            return favorites;
        realm.beginTransaction();

        RealmQuery<Personagem> favoritosQuery = realm.where(Personagem.class);
        int i = 0;
        for(Favorite f : favorites) {
            if(i != 0) {
                favoritosQuery  = favoritosQuery .or();
            }
            favoritosQuery  = favoritosQuery .equalTo("name", f.getName());
            i++;
        }
          favoritosQuery.findAll();
        realm.commitTransaction();
        return favoritosQuery.findAll();
    }

    public RealmResults getAllFavourite() {
        realm.beginTransaction();
        RealmResults realmPersonagens = realm.where(Favorite.class).findAll();
        realm.commitTransaction();
        return realmPersonagens;
    }

    public RealmResults getFavouriteByName(String name) {
        realm.beginTransaction();
        RealmResults realmPersonagens = realm.where(Favorite.class).equalTo("name", name).findAll();
        realm.commitTransaction();
        return realmPersonagens;
    }

    public RealmResults searchPersonagens(String name) {
        realm.beginTransaction();
        RealmResults personagens = realm.where(Personagem.class).contains("name", name, Case.INSENSITIVE).findAll();
        realm.commitTransaction();
        return personagens;
    }

    public Personagem getPersonagem(String name) {
        realm.beginTransaction();
        Personagem personagens = realm.where(Personagem.class).equalTo("name", name).findFirst();
        realm.commitTransaction();
        return personagens;
    }


    public void addRemoveFavourite(String name) throws IOException {
        RealmResults favoritos = getFavouriteByName(name);
        realm.beginTransaction();
        if (favoritos.size() == 0) {
            Favorite f = new Favorite();
            f.setName(name);
            realm.copyToRealmOrUpdate(f);
        } else {
            realm.where(Favorite.class).equalTo("name", name).findAll().deleteAllFromRealm();
        }
        realm.commitTransaction();
        if(favoritos.size() == 0)
        new FavouriteService(context).addFavourite(getAllFavouritePersonagens(), context);
    }

    public void addPendingFavourite() throws IOException {
        new FavouriteService(context).addFavourite(getAllFavouritePersonagens(), context);
    }


}
