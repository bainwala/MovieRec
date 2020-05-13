package recsys;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Matrix {

    public static ArrayList<Movie> dataset = new ArrayList<>();

    public static void main(String[] args) throws Exception{
    	Random random = new Random();
    	Scanner sc = new Scanner(System.in);
        populateDataSet();
        numericalNormalize();
        //must do Normalization before choices
        ArrayList<Movie> choices = new ArrayList<>();
        
        int count = 0;
        System.out.println("Enter Number of the movie you like.");
        while(count < 3) {
        	Movie m1 = dataset.get(random.nextInt(dataset.size()));
        	Movie m2 = dataset.get(random.nextInt(dataset.size()));
        	Movie m3 = dataset.get(random.nextInt(dataset.size()));
        	System.out.println("1." + m1.getTitle());
        	System.out.println("2." + m2.getTitle());
        	System.out.println("3." + m3.getTitle());
        	System.out.println("4. None of the above");
        	int v = sc.nextInt();
        	if(v==1) {
        		choices.add(m1);
        		dataset.remove(m1);
        		count++;
        	} else if(v==2) {
        		choices.add(m2);
        		dataset.remove(m2);
        		count++;
        	} else if(v==3) {
        		choices.add(m3);
        		dataset.remove(m3);
        		count++;
        	}
        }
        System.out.println();
        numericalAnalysis(choices);
        textualAnalysis(choices);
        findIndex();
        Collections.sort(dataset, Movie::compareTo);
        for(int i = 0; i < 5; i++){
            System.out.println(dataset.get(i).getTitle());
        }
    }

    public static void populateDataSet() throws IOException, ParseException, FileNotFoundException {
        File rawData = new File("/Users/himanshubainwala/Desktop/movies.json");
        FileReader reader = new FileReader(rawData);

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        //Read JSON file
        JSONArray movieList = (JSONArray) jsonParser.parse(reader);

        //Fill out arrayList of movie objects
        for(Object var: movieList) {
            Movie temp = parseMovies( (JSONObject) var);
            dataset.add(temp);
        }

    }

    public static Movie parseMovies(JSONObject current){

        //get the JSONArray and then convert to ArrayList for keywords
        JSONArray keywordsJ = (JSONArray)current.get("keywords");
        ArrayList<String> keywords = new ArrayList<>();
        for(Object var: keywordsJ){
            JSONObject inner = (JSONObject) var;
            String innerWords = (String) inner.get("name");
            keywords.add(innerWords);
        }

        //get the JSONArray and then convert to ArrayList for genres
        JSONArray genresJ = (JSONArray)current.get("genres");
        ArrayList<String> genres = new ArrayList<>();
        for(Object var: genresJ){
            JSONObject inner = (JSONObject) var;
            String innerWords = (String) inner.get("name");
            genres.add(innerWords);
        }

        //get the JSONArray and then convert to ArrayList for companies
        JSONArray companyJ = (JSONArray)current.get("production_companies");
        ArrayList<String> company = new ArrayList<>();
        for(Object var: companyJ){
            JSONObject inner = (JSONObject) var;
            String innerWords = (String) inner.get("name");
            company.add(innerWords);
        }

        //Singleton values
        String title = current.get("original_title").toString();
        Double popular = Double.parseDouble(current.get("popularity").toString());
        Double vote = Double.parseDouble(current.get("vote_average").toString());
        //Account for some null runtimes with avg. movie length
        Double length = 120.0;
        if(current.get("runtime").toString().compareTo("")!=0)
            length = Double.parseDouble(current.get("runtime").toString());

        Movie movie = new Movie(
                keywords,
                genres,
                company,
                popular,
                vote,
                length,
                title);

        return movie;
    }

    public static void numericalNormalize(){
        //data for normalization
        double popularAvg = 21.4923;
        double popularStd = 31.8166;
        double voteAvg = 6.0921;
        double voteStd = 1.1946;
        double lengthAvg = 106.8813;
        double lengthStd = 22.6088;

        //normalizing all data
        for(Movie curr: dataset){
            curr.setPopular((curr.getPopular() - popularAvg) / popularStd);
            curr.setVote((curr.getVote() - voteAvg) / voteStd);
            curr.setLength((curr.getLength() - lengthAvg) / lengthStd);
        }
    }

    public static void numericalAnalysis(ArrayList<Movie> choices) {
        //find average of choice group
        double choicePopAvg = 0;
        double choiceVoteAvg = 0;
        double choiceLengthAvg = 0;

        for(Movie curr: choices){
            choicePopAvg += curr.getPopular();
            choiceVoteAvg += curr.getVote();
            choiceLengthAvg += curr.getLength();
        }

        choicePopAvg /= choices.size();
        choiceVoteAvg /= choices.size();
        choiceLengthAvg /= choices.size();

        //find difference of avg choice group and each other movie
        for(Movie curr: dataset){
            curr.setNumScore(
                    Math.abs(choicePopAvg - curr.getPopular()) +
                            Math.abs(choiceVoteAvg - curr.getVote()) +
                            Math.abs(choiceLengthAvg - curr.getLength())
            );
        }

    }

    public static void textualAnalysis(ArrayList<Movie> choices){

        //Lets first deal with keywords
        ArrayList<String> keywordChoice = new ArrayList<>();
        for(Movie curr: choices){
            keywordChoice.addAll(curr.getKeywords());
        }
        //Compare our bank of keywords to every other movie, assign a keyword score
        for(Movie curr: dataset){
            double test = 0.0;
            for(String a: keywordChoice){
                for(String b: curr.getKeywords()){
                    test += JaroWinklerDistance.similarity(a,b);
                }
            }
            //lower is less similar
            test /= (keywordChoice.size() * curr.getKeywords().size());
            test = 1 - test;
            curr.setKeywordScore(test);
        }

        //next the genres
        ArrayList<String> genreChoice = new ArrayList<>();
        for(Movie curr: choices){
            genreChoice.addAll(curr.getGenres());
        }

        for(Movie curr: dataset){
            double test = 0.0;
            for(String a: genreChoice){
                for(String b: curr.getKeywords()){
                    test += JaroWinklerDistance.similarity(a,b);
                }
            }
            //lower is less similar
            test /= (genreChoice.size() * curr.getKeywords().size());
            test = 1 - test;
            curr.setGenreScore(test);
        }

        //finally the companies
        ArrayList<String> companyChoice = new ArrayList<>();
        for(Movie curr: choices){
            companyChoice.addAll(curr.getCompanies());
        }

        for(Movie curr: dataset){
            double test = 0.0;
            for(String a: companyChoice){
                for(String b: curr.getKeywords()){
                    test += JaroWinklerDistance.similarity(a,b);
                }
            }
            //lower is less similar
            test /= (companyChoice.size() * curr.getKeywords().size());
            test = 1 - test;
            curr.setCompanyScore(test);
        }

        //lower numScore is more similar
        //lower textScore is more similar
    }

    public static void findIndex(){
        for(Movie curr: dataset){
            double temp = curr.getKeywordScore() + curr.getGenreScore() + curr.getCompanyScore();
            temp /= 3;
            curr.setIndexScore(curr.getNumScore() * temp);
        }
    }

}
