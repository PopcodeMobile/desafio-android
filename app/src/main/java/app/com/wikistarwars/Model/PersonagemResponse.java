package app.com.wikistarwars.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonagemResponse implements Parcelable {

    private int page;
    private int totalPages;

    @SerializedName("results")
    private List<Personagem> results;

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

    public List<Personagem> getResults() {
        return results;
    }

    public void setResults(List<Personagem> results) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeTypedList(this.results);
        dest.writeInt(this.count);
        dest.writeInt(this.totalPages);
    }

    public PersonagemResponse() {
    }

    protected PersonagemResponse(Parcel in) {
        this.page = in.readInt();
        this.results = in.createTypedArrayList(Personagem.CREATOR);
        this.count = in.readInt();
        this.totalPages = in.readInt();
    }

    public static final Creator<PersonagemResponse> CREATOR = new Creator<PersonagemResponse>() {
        @Override
        public PersonagemResponse createFromParcel(Parcel source) {
            return new PersonagemResponse(source);
        }

        @Override
        public PersonagemResponse[] newArray(int size) {
            return new PersonagemResponse[size];
        }
    };
}
