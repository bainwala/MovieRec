# Movie Recommendation System

This algorithm deals with accepting a choice of the user's favorite movies and returning a list of movies that the user might like because of their choices. With Recommender systems being used in most aspects of today's world, our program aims to understand the in-depth algorithmic approach for one sector: movies. 

## How to use?
A basic code editor that recognizes JAVA is required. There are 7 java class files and a JSON data set that are required for the user to implement the program. The 'matrix' class contains a String where the user should enter their local filepath of the JSON dataset in order for the class to read it. 
After running the code, a GUI will pop up. The user will be given a random selection of movies among which they will choose their favorites. Based on their selections, they are provided a list of 5 movies that they are sure to enjoy!
 
## Dataset

The dataset used is was originally a CSV file that we converted into a JSON file that contains 5000 movies and has the following fields: 
- **Keywords** : Contains the most important keywords that describe the movie.
- **Genres** : Contains the genres that describe the movie.
- **Production Companies** : Contains the name of the companies that contributed to the creation of the movie.
- **Runtime** : Contains the running time of the movie.
- **Popularity** : Comtains the rating of the movie by viewers.
- **Vote** : Contains the votes by the viewers. 

## Algorithmic Approach
The algorithm focuses on dividing the numerical properties and the textual properties and then using them to find the similarity between the user chosen movies and the rest of the movies in the dataset. 
The numerical properties were normalized so that a field with inherently large values does not compromise a field with inherently smaller values. We aim to minimize the distance between the numerical fields between the entered movies and the rest of the movies. Moreover, we implement the Jaro-Winkler distance that compares the text similarity between the entered movies and the rest of the movies. 
The top 5 similar movies are then displayed to the user. 

## Development Team
This project was developed by Himanshu Bainwala, Rayaan Attari and Everett Hayes in the JAVA environment as the final project for CSC: Object Oriented Programming at Grinnell College.




   
