package com.cheadtech.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Movie implements Parcelable {
    public Movie(
            Integer voteCount,
            Integer id,
            Boolean video,
            Double voteAverage,
            String title,
            Double popularity,
            String posterPath,
            String originalLanguage,
            String originalTitle,
            ArrayList<Integer> genreIds,
            String backdropPath,
            Boolean adult,
            String overview,
            String releaseDate
    ) {
        this.vote_count = voteCount;
        this.id = id;
        this.video = video;
        this.vote_average = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = posterPath;
        this.original_language = originalLanguage;
        this.original_title = originalTitle;
        this.genre_ids = genreIds;
        this.backdrop_path = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.release_date = releaseDate;
    }
    public Integer vote_count;
    public Integer id;
    public Boolean video;
    public Double vote_average;
    public String title;
    public Double popularity;
    public String poster_path;
    public String original_language;
    public String original_title;
    public ArrayList<Integer> genre_ids;
    public String backdrop_path;
    public Boolean adult;
    public String overview;
    public String release_date;

    protected Movie(Parcel in) {
        if (in.readByte() == 0) {
            vote_count = null;
        } else {
            vote_count = in.readInt();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        byte tmpVideo = in.readByte();
        video = tmpVideo == 0 ? null : tmpVideo == 1;
        if (in.readByte() == 0) {
            vote_average = null;
        } else {
            vote_average = in.readDouble();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        poster_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        backdrop_path = in.readString();
        byte tmpAdult = in.readByte();
        adult = tmpAdult == 0 ? null : tmpAdult == 1;
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (vote_count == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(vote_count);
        }
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeByte((byte) (video == null ? 0 : video ? 1 : 2));
        if (vote_average == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(vote_average);
        }
        parcel.writeString(title);
        if (popularity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(popularity);
        }
        parcel.writeString(poster_path);
        parcel.writeString(original_language);
        parcel.writeString(original_title);
        parcel.writeString(backdrop_path);
        parcel.writeByte((byte) (adult == null ? 0 : adult ? 1 : 2));
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }
}
