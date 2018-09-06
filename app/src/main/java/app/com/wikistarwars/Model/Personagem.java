package app.com.wikistarwars.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Personagem implements Parcelable {

    @SerializedName("name")
    private String name;
    @SerializedName("height")
    private String height;
    @SerializedName("gender")
    private String gender;
    @SerializedName("mass")
    private String mass;

    public Personagem(String name, String height) {
        this.name = name;
        this.height = height;
    }

    public Personagem(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.height);
    }

    protected Personagem(Parcel in) {
        this.name = in.readString();
        this.height = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public static final Creator<Personagem> CREATOR = new Creator<Personagem>() {
        @Override
        public Personagem createFromParcel(Parcel source) {
            return new Personagem(source);
        }

        @Override
        public Personagem[] newArray(int size) {
            return new Personagem[size];
        }
    };
}
