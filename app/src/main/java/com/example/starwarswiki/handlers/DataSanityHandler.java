package com.example.starwarswiki.handlers;

import android.content.Context;

import androidx.room.Room;

import com.example.starwarswiki.structural.People;
import com.example.starwarswiki.structural.Person;
import com.example.starwarswiki.structural.Planet;
import com.example.starwarswiki.structural.Planets;

import java.util.List;


/**
 * This class will fetch all info in order in background and will check if all info has been fetched
 */
public class DataSanityHandler {

    private String loadStauts = "unloaded";
    private int fetchPlanetsIndex = 0;
    private Context context;
    private AppDatabase db;
    public DataSanityHandler (Context context) {
        //db = Room.databaseBuilder(context, AppDatabase.class, "app-database").build();
        //new PeopleHandler(context).execute("https://swapi.co/api/personList?format=json");
        //this.context = context;
    }

    /**
     * Insert people into the DD
     * @param personList the list of Person objects is updated as new data is fetched
     * @param previousResult Result of the AsyncCall that ran before, null if it is the first
     * @return The function returns the updated list of Person
     */
    public List<Person> fetchPeople (People previousResult, List<Person> personList) {
        if(previousResult.getPrevious() == null) {
            personList = previousResult.getList();
//            db.insertPeople(previousResult);
//            new PeopleHandler(this).execute(previousResult.getNext());
//            new PlanetsNameHandler(this).execute(personList.get(fetchPlanetsIndex).getHomeworldURL()+"?format=json");
        } else if (previousResult.getNext() != null) {
            personList.addAll(previousResult.getList());
//            db.insertPeople(previousResult);
//            new PeopleHandler(this).execute(previousResult.getNext());
        } else {
            personList.addAll(previousResult.getList());
//            db.insertPeople(previousResult);
//            person = db.searchPersonByName("j").getList().get(0).getName();
        }
        return personList;
    }

    public List<Planet> fecthPlanetsNames(Planets previousResult, List<Planet> planetList) {
        if (previousResult.getPrevious() == null) {
            planetList = previousResult.getListOfPlanet();
        } else {
            planetList.addAll(previousResult.getListOfPlanet());
        }

        return planetList;
    }
}
