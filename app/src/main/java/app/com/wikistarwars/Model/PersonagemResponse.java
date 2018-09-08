package app.com.wikistarwars.Model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;

public class PersonagemResponse  {

    private int page;
    private int totalPages;

    @SerializedName("results")
    private RealmList<Personagem> results;

    @SerializedName("count")
    private int count;

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private String previous;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public RealmList<Personagem> getResults() {
        return results;
    }

    public void setResults(RealmList<Personagem> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }


}
