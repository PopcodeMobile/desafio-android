package com.example.entrevistapopcode.db.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.entrevistapopcode.api.entity.entity.Person;
import com.example.entrevistapopcode.db.repository.PersonRepository;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {

    private PersonRepository mRepository;

    private LiveData<List<Person>> mAllWords;

    public PersonViewModel (Application application) {
        super(application);
        mRepository = new PersonRepository(application);
        //mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Person>> getAllWords() {
        mAllWords = mRepository.getAllWords();
        return mAllWords; }

    public LiveData<List<Person>> getAllPersonById(String name) {
        mAllWords = mRepository.getAllPersonById(name);
        return mAllWords;
    }

    public LiveData<List<Person>> getAllFavorite() {
        mAllWords = mRepository.getAllFavorite();
        return mAllWords; }

    public void insert(Person person) { mRepository.insert(person); }

    public LiveData<Boolean> isFav(String name) { return mRepository.isFav(name); }

    public void insertFavorite(String name, boolean value) { mRepository.insertFavorite(name, value); }

    public LiveData<Person> getPerson(String name) {
       return mRepository.getPerson(name); }


}