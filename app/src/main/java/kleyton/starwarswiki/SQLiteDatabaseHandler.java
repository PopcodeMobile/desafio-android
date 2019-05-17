package kleyton.starwarswiki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION  = 4;
    private static final String DATABASE_NAME  = "PeopleDB";
    private static final String TABLE_NAME     = "PERSON";
    private static final String KEY_NAME       = "name";
    private static final String KEY_HEIGHT     = "height";
    private static final String KEY_GENDER     = "gender";
    private static final String KEY_MASS       = "mass";
    private static final String KEY_HAIR_COLOR = "hair_color";
    private static final String KEY_SKIN_COLOR = "skin_color";
    private static final String KEY_EYE_COLOR  = "eye_color";
    private static final String KEY_BIRTH_YEAR = "birth_year";
    private static final String KEY_HOMEWORLD  = "homeworld";
    private static final String KEY_SPECIES    = "species";
    private static final String KEY_ISBOOKMARK = "isbookmark";
    private static final String[] COLUMNS      = {KEY_NAME, KEY_HEIGHT, KEY_GENDER, KEY_MASS, KEY_HAIR_COLOR, KEY_SKIN_COLOR,
                                                  KEY_EYE_COLOR, KEY_BIRTH_YEAR, KEY_HOMEWORLD, KEY_SPECIES, KEY_ISBOOKMARK};

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
    }

    public boolean personExists(Person person) {
        String query      = "SELECT * FROM PERSON WHERE name='" + person.getName() + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor     = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    public List<Person> getAllCharacters() {

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
