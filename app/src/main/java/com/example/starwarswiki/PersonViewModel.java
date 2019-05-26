package com.example.starwarswiki;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.starwarswiki.handlers.PersonRepository;
import com.example.starwarswiki.structural.Person;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository personRepository;
    private LiveData<List<Person>> lisfOfPerson;
    private LiveData<List<Person>> queryResult;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        personRepository = new PersonRepository(application);
        lisfOfPerson = personRepository.getAllPerson();
    }

    public LiveData<List<Person>> getAllPerson () { return lisfOfPerson; }

    public LiveData<List<Person>> queryByName(String name) {
        queryResult = personRepository.queryByName(name);

        return queryResult;
    }

//    public void insert(Person person) { personRepository.insert(person); }
    public void insert(List<Person> listOfPerson) {personRepository.insertList(listOfPerson);}
}
