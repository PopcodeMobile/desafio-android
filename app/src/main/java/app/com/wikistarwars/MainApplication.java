package app.com.wikistarwars;

import android.app.Application;

import app.com.wikistarwars.Model.Personagem;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            Realm.init(this);
            RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                    .initialData(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.createObject(Personagem.class);
                        }})
                    .build();
            Realm.deleteRealm(realmConfig); // Delete Realm between app restarts.
            Realm.setDefaultConfiguration(realmConfig);
        }
    }
