package recsys;
import java.util.ArrayList;
import java.util.Comparator;

public class Movie implements Comparable<Movie>{
    private ArrayList<String> keywords;
    private ArrayList<String> genres;
    private ArrayList<String> companies;
    private Double popular;
    private Double vote;
    private Double length;
    private String title;

    private Double numScore;

    private Double keywordScore;
    private Double genreScore;
    private Double companyScore;


    private Double indexScore;

    public double getNumScore() {
        return numScore;
    }

    public void setNumScore(double numScore) {
        this.numScore = numScore;
    }

    public Movie(ArrayList<String> keywords, ArrayList<String> genres, ArrayList<String> companies, Double popular, Double vote, Double length, String title) {
        this.keywords = keywords;
        this.genres = genres;
        this.companies = companies;
        this.popular = popular;
        this.vote = vote;
        this.length = length;
        this.title = title;
    }

    public void setKeywordScore(Double keywordScore) {
        this.keywordScore = keywordScore;
    }

    public Double getKeywordScore() {
        return keywordScore;
    }

    public Double getGenreScore() {
        return genreScore;
    }

    public void setGenreScore(Double genreScore) {
        this.genreScore = genreScore;
    }

    public Double getCompanyScore() {
        return companyScore;
    }

    public void setCompanyScore(Double companyScore) {
        this.companyScore = companyScore;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<String> companies) {
        this.companies = companies;
    }

    public Double getPopular() {
        return popular;
    }

    public void setPopular(Double popular) {
        this.popular = popular;
    }

    public Double getVote() {
        return vote;
    }

    public void setVote(Double vote) {
        this.vote = vote;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumScore(Double numScore) {
        this.numScore = numScore;
    }

    public Double getIndexScore() {
        return indexScore;
    }

    public void setIndexScore(Double indexScore) {
        this.indexScore = indexScore;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "keywords=" + keywords +
                ", genres=" + genres +
                ", companies=" + companies +
                ", popular=" + popular +
                ", vote=" + vote +
                ", length=" + length +
                ", title='" + title + '\'' +
                ", numScore=" + numScore +
                ", keywordScore=" + keywordScore +
                ", genreScore=" + genreScore +
                ", companyScore=" + companyScore +
                ", indexScore=" + indexScore +
                '}';
    }

    @Override
    public int compareTo(Movie o) {
        return this.indexScore.compareTo(o.indexScore);
    }
}