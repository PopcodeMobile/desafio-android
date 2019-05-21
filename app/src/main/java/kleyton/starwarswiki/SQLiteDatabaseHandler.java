package kleyton.starwarswiki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION  = 23;
    private static final String DATABASE_NAME  = "PeopleDB";
    private static final String TABLE_NAME     = "PERSON";

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE PERSON (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "height TEXT," +
                "gender TEXT," +
                "mass TEXT," +
                "hair_color TEXT," +
                "skin_color TEXT," +
                "eye_color TEXT," +
                "birth_year TEXT," +
                "homeworld TEXT," +
                "species TEXT," +
                "isbookmark TEXT)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void insertPerson(Person person) {
        SQLiteDatabase db           = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", person.getName());
        contentValues.put("height", person.getHeight());
        contentValues.put("gender", person.getGender());
        contentValues.put("mass", person.getMass());
        contentValues.put("hair_color", person.getHair_color());
        contentValues.put("skin_color", person.getSkin_color());
        contentValues.put("eye_color", person.getEye_color());
        contentValues.put("birth_year", person.getBirth_year());
        contentValues.put("homeworld", person.getHomeworld());
        contentValues.put("species", person.getSpecies());
        contentValues.put("isbookmark", person.getIsbookmark());
        db.insert(TABLE_NAME, null,contentValues);
        db.close();
        Log.d("DATABASE", "----Person inserted----");
    }

    public void updatePerson(Person person) {
        SQLiteDatabase db           = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", person.getName());
        contentValues.put("height", person.getHeight());
        contentValues.put("gender", person.getGender());
        contentValues.put("mass", person.getMass());
        contentValues.put("hair_color", person.getHair_color());
        contentValues.put("skin_color", person.getSkin_color());
        contentValues.put("eye_color", person.getEye_color());
        contentValues.put("birth_year", person.getBirth_year());
        contentValues.put("homeworld", person.getHomeworld());
        contentValues.put("species", person.getSpecies());
        contentValues.put("isbookmark", person.getIsbookmark());
        String where = "name='" + person.getName() + "'";
        db.update(TABLE_NAME, contentValues, where, null);
        db.close();
        Log.d("DATABASE", "----Person updated----");
    }

    public void removePerson(String name) {
        String query = "DELETE FROM PERSON WHERE name='" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
        Log.d("DATABASE", "----Person removed----");
    }

    public boolean personExists(String name) {
        boolean result;
        String query      = "SELECT * FROM PERSON WHERE name='" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor     = db.rawQuery(query, null);
        result =  cursor.moveToFirst();
        db.close();
        return result;
    }

    public List<Person> searchPerson(String name) {
        List<Person> people = new ArrayList<>();
        String query        = "SELECT * FROM PERSON WHERE name LIKE '%" + name + "%'";
        SQLiteDatabase db   = this.getReadableDatabase();
        Cursor cursor       = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setName(cursor.getString(1));
                person.setHeight(cursor.getString(2));
                person.setGender(cursor.getString(3));
                person.setMass(cursor.getString(4));
                person.setHair_color(cursor.getString(5));
                person.setSkin_color(cursor.getString(6));
                person.setEye_color(cursor.getString(7));
                person.setBirth_year(cursor.getString(8));
                person.setHomeworld(cursor.getString(9));
                person.setSpecies(cursor.getString(10));
                person.setIsbookmark(cursor.getString(11));
                people.add(person);
            } while (cursor.moveToNext());
        }
        db.close();
        return people;
    }

    public boolean isBookmark(String name) {
        boolean result;
        String query      = "SELECT * FROM PERSON WHERE isbookmark='yes' AND name='" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor     = db.rawQuery(query, null);
        result =  cursor.moveToFirst();
        db.close();
        return result;
    }

    public void setBookmark(String name) {
        String query = "UPDATE PERSON SET isbookmark = 'yes' WHERE name='" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void removeBookmark(String name) {
        String query = "UPDATE PERSON SET isbookmark = 'no' WHERE name='" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public boolean homeWorldUpdated(String name) {
        String query      = "SELECT homeworld FROM PERSON WHERE name='" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor     = db.rawQuery(query, null);
        boolean updated;

        if (cursor.moveToFirst()) {
            String homeworld = cursor.getString(0);
            if (homeworld.contains("https")){
                updated = false;
            } else {
                updated = true;
            }
        } else {
            updated = false;
        }
        db.close();
        return updated;
    }

    public boolean speciesUpdated(String name) {
        String query      = "SELECT species FROM PERSON WHERE name='" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor     = db.rawQuery(query, null);
        boolean updated;

        if (cursor.moveToFirst()) {
            String species = cursor.getString(0);
            if (species.contains("https")){
                updated = false;
            } else {
                updated = true;
            }
        } else {
            updated = false;
        }
        db.close();
        return updated;
    }

    public String getHomeworld(String name) {
        String query        = "SELECT homeworld FROM PERSON WHERE name='" + name + "'";
        SQLiteDatabase db   = this.getReadableDatabase();
        Cursor cursor       = db.rawQuery(query, null);
        String homeworld    = "";

        if (cursor.moveToFirst()) {
            homeworld = cursor.getString(0);
        }

        db.close();
        return homeworld.trim();
    }

    public String getSpecies(String name) {
        String query        = "SELECT species FROM PERSON WHERE name='" + name + "'";
        SQLiteDatabase db   = this.getReadableDatabase();
        Cursor cursor       = db.rawQuery(query, null);
        String species      = "";

        if (cursor.moveToFirst()) {
            species = cursor.getString(0);
        }

        db.close();
        return species.trim();
    }

    public void updateHomeworld(String homeworld, String name) {
        String query      = "UPDATE PERSON SET homeworld = ' " + homeworld +" ' WHERE name='" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void updateSpecies(String species, String name) {
        String query      = "UPDATE PERSON SET species = ' " + species +" ' WHERE name='" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public List<Person> getAllBookmarks() {

        List<Person> people = new ArrayList<>();
        String selectQuery  = "SELECT * FROM PERSON WHERE isbookmark='yes'";
        SQLiteDatabase db   = this.getReadableDatabase();
        Cursor cursor       = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setName(cursor.getString(1));
                person.setHeight(cursor.getString(2));
                person.setGender(cursor.getString(3));
                person.setMass(cursor.getString(4));
                person.setHair_color(cursor.getString(5));
                person.setSkin_color(cursor.getString(6));
                person.setEye_color(cursor.getString(7));
                person.setBirth_year(cursor.getString(8));
                person.setHomeworld(cursor.getString(9));
                person.setSpecies(cursor.getString(10));
                person.setIsbookmark(cursor.getString(11));
                people.add(person);
            } while (cursor.moveToNext());
        }

        db.close();
        return people;
    }

    public List<Person> getPeople() {

        List<Person> people = new ArrayList<>();
        String selectQuery  = "SELECT * FROM PERSON";
        SQLiteDatabase db   = this.getReadableDatabase();
        Cursor cursor       = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setName(cursor.getString(1));
                person.setHeight(cursor.getString(2));
                person.setGender(cursor.getString(3));
                person.setMass(cursor.getString(4));
                person.setHair_color(cursor.getString(5));
                person.setSkin_color(cursor.getString(6));
                person.setEye_color(cursor.getString(7));
                person.setBirth_year(cursor.getString(8));
                person.setHomeworld(cursor.getString(9));
                person.setSpecies(cursor.getString(10));
                person.setIsbookmark(cursor.getString(11));
                people.add(person);
            } while (cursor.moveToNext());
        }

        db.close();
        return people;
    }
}
