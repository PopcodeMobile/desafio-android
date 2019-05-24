package com.example.starwarswiki.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;
import com.example.starwarswiki.structural.Planet;

import org.json.JSONObject;


/**
 *
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StarWarsWIKI.db";
    private static final String PEOPLE_TABLE_NAME = "people";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_MASS = "mass";
    private static final String KEY_HAIR_COLOR = "hair_color";
    private static final String KEY_SKIN_COLOR = "skin_color";
    private static final String KEY_EYE_COLOR = "eye_color";
    private static final String KEY_BIRTH_YEAR = "birth_year";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_HOMEWORLD = "homeworld";
    private static final String KEY_SPECIES = "species";
    private static final String PLANETS_TABLE_NAME = "planets";
//    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_HEIGHT, KEY_MASS,
//            KEY_HAIR_COLOR, KEY_SKIN_COLOR,KEY_EYE_COLOR,KEY_BIRTH_YEAR, KEY_GENDER,KEY_HOMEWORLD,KEY_SPECIES};
//    private static final String[] COLUMNS_SIMPLIFIED = {KEY_ID, KEY_NAME, KEY_HEIGHT, KEY_MASS};

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTablePeople = "CREATE TABLE "+ PEOPLE_TABLE_NAME +" (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT," +
                KEY_HEIGHT + " TEXT," +
                KEY_MASS + " TEXT," +
                KEY_HAIR_COLOR + " TEXT," +
                KEY_SKIN_COLOR + " TEXT," +
                KEY_EYE_COLOR + " TEXT," +
                KEY_BIRTH_YEAR + " TEXT," +
                KEY_GENDER + " TEXT," +
                KEY_HOMEWORLD + " TEXT," +
                KEY_SPECIES + " TEXT" +
                ")";
        String createTablePlanets = "CREATE TABLE " + PLANETS_TABLE_NAME + " (" +
                KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT" +
                ")";
        db.execSQL(createTablePeople);
        db.execSQL(createTablePlanets);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }

    /**
     * Receive a single person and store it's attributes to the table {@link #PEOPLE_TABLE_NAME}
     * @param person {@see #Person}
     */
    public void insertPerson(Person person) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, person.getName());
        values.put(KEY_HEIGHT, person.getHeight());
        values.put(KEY_MASS, person.getMass());
        values.put(KEY_HAIR_COLOR, person.getHairColor());
        values.put(KEY_SKIN_COLOR, person.getSkinColor());
        values.put(KEY_EYE_COLOR, person.getEyeColor());
        values.put(KEY_BIRTH_YEAR, person.getBirthYear());
        values.put(KEY_GENDER, person.getGender());
        values.put(KEY_HOMEWORLD, person.getHomeworldURL());
        values.put(KEY_SPECIES, person.getSpeciesURL().toString());

        db.insert(PEOPLE_TABLE_NAME, null, values);
        db.close();
    }

    // PEOPLE CODE

    public void insertPeople (People people) {
        for (int i = 0; i < people.getList().size(); i++) {
            insertPerson(people.getList().get(i));
        }
    }

    public People searchPersonByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Person person = new Person();
        People list = new People();
        String query = "SELECT * FROM "+ PEOPLE_TABLE_NAME + " WHERE " +KEY_NAME + " LIKE '%" + name + "%'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while(cursor.moveToNext()) {

                person.setName(cursor.getString(1));
                person.setHeight(cursor.getString(2));
                person.setMass(cursor.getString(3));
                person.setHairColor(cursor.getString(4));
                person.setSkinColor(cursor.getString(5));
                person.setEyeColor(cursor.getString(6));
                person.setBirthYear(cursor.getString(7));
                person.setGender(cursor.getString(8));
                person.setHomeworldURL(cursor.getString(9));
                person.setSpecies(cursor.getString(10));
            }
            list.getList().add(person);
        }

        cursor.close();
        db.close();
        return list;
    }
    //END PEOPLE CODE

    //PLANETS CODE
    public void insertPlanet (Planet planet) {
        
    }
    //END PLANETS CODE
}
