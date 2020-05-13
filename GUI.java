
package recsys;
import javax.swing.*;
import javax.swing.border.Border;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GUI implements ActionListener {
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	Random random = new Random();
	public static ArrayList<Movie> dataset = new ArrayList<>();
	public static ArrayList<Movie> choices = new ArrayList<>();
	private JLabel label = new JLabel();
    private static JFrame frame;
    static int count=0;
    
    public static void display() {
    	frame = new JFrame();
    	JLabel l1 = new JLabel();
    	JLabel l2 = new JLabel();
    	JLabel l3 = new JLabel();
    	JLabel l4 = new JLabel();
    	JLabel l5 = new JLabel();
    	JLabel l6 = new JLabel("Enjoy Movie Night! :)");
    	
        numericalAnalysis(choices);
        textualAnalysis(choices);
        findIndex();
        Collections.sort(dataset, Movie::compareTo);
    	
    	l1.setText(dataset.get(0).getTitle());
    	l2.setText(dataset.get(1).getTitle());
    	l3.setText(dataset.get(2).getTitle());
    	l4.setText(dataset.get(3).getTitle());
    	l5.setText(dataset.get(4).getTitle());
    	Border border = BorderFactory.createLineBorder(Color.RED, 3);
    	l1.setBorder(border);
    	l2.setBorder(border);
    	l3.setBorder(border);
    	l4.setBorder(border);
    	l5.setBorder(border);
    	JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(l1);
        panel.add(l2);
        panel.add(l3);	
        panel.add(l4);
        panel.add(l5);
        panel.add(l6);
        // set up the frame and display it
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("RECOMMENDED MOVIES");
        frame.pack();
        frame.setVisible(true);
    }

    public GUI() {
    		frame = new JFrame();
	    	Movie m1 = dataset.get(random.nextInt(dataset.size()));
	    	Movie m2 = dataset.get(random.nextInt(dataset.size()));
	    	Movie m3 = dataset.get(random.nextInt(dataset.size()));
	    	
	        // the clickable button
	        button1 = new JButton(m1.getTitle());
	        button1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		if(count < 2) {
	        			count++;
	        			choices.add(m1);
	        			new GUI();
	        		} else {
	        			choices.add(m1);
	        			display();
	        		}
	        	}
	        });
	        button2 = new JButton(m2.getTitle());
	        button2.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		if(count < 2) {
	        			count++;
	        			choices.add(m2);
	        			new GUI();
	        		} else {
	        			choices.add(m2);
	        			display();
	        		}
	        	}
	        });
	        button3 = new JButton(m3.getTitle());
	        button3.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		if(count < 2) {
	        			count++;
	        			choices.add(m3);
	        			new GUI();
	        		} else {
	        			choices.add(m3);
	        			display();
	        		}       		
	        	}
	        });
	        button4 = new JButton("None Of The Above");
	        button4.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		new GUI();
	        	}
	        });
	
	        // the panel with the button and text
	        JPanel panel = new JPanel();
	        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
	        panel.setLayout(new GridLayout(0, 1));
	        label.setText("Choose Your Favorite Movie!");
	        panel.add(label);
	        panel.add(button1);
	        panel.add(button2);
	        panel.add(button3);
	        panel.add(button4);
    	
	        // set up the frame and display it
	        frame.add(panel, BorderLayout.CENTER);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setTitle("GUI");
	        frame.pack();
	        frame.setVisible(true);
    	}
    

    // process the button clicks
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

    // create one Frame
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
    	populateDataSet();
    	numericalNormalize();
        new GUI();
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