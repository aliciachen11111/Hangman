  /*
variables: ArrayList of Strings, String current word, String what is showing, int num of guesses
methods: add string to word list, start new game, guess, reveal word, 

this is the Game class, the game object can run games of hangman and store a word bank

Author: Alicia Chen
*/
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;


public class Game implements Serializable {
  //variables
  private static final long serialVersionUID = 1L;
  private ArrayList<String> wordBank; //player adds word bank
  private ArrayList<String> letters; //letters that player has guessed
  private transient String currentWord; //current word player is trying to guess
  private transient String show; //string with correct letters guessed and - for missing letters
  private transient Scanner s;
  private transient String guess; //letter being guessed
  private int max; //max amt of werong guesses
  private transient int wrongGuesses;
  private boolean wordBankEnabled; //shows word bank

  //iniitalize game or smth
  public Game(){
    this(false);
  }
  
  public Game(boolean s){
    wordBank = new ArrayList<>();
    letters = new ArrayList<>();
    max = 8;
    wrongGuesses = 0;
    wordBankEnabled = s;
  }

  
  public void play(){

    //check if owrkd bak empty
    if (wordBank.size() == 0){
      System.out.println("there are no words in the word bank. Please add some words to play.");
      return;
    }

    //set up new game
    letters.clear();
    wrongGuesses=0;
    currentWord = wordBank.get(Utils.randInt(0, wordBank.size() - 1));
    show = "";
    for (int i = 0; i < currentWord.length(); i++){
      show += "-";
    }

    
    while (wrongGuesses < max){
      System.out.println("letters guessed: " + getLetters());
      if(wordBankEnabled){
        System.out.println(getWordBank());
      }



      
      guess = Utils.inputStr("word: " + show + "\n guess a letter: ");
      guess = guess.toLowerCase().trim();
      while (guess.trim().length() > 1 || letters.indexOf(guess) != -1 || (guess.compareTo("z") > 0 || guess.compareTo("a") < 0) ){
        while (guess.trim().length() > 1){
          
          guess  = Utils.inputStr("guess a single letter please: ");
          guess = guess.toLowerCase().trim();
          
        }
        while(letters.indexOf(guess) != -1){
          guess = Utils.inputStr("that letter has been guessed already. please guess a new letter: ");
          guess = guess.toLowerCase().trim();
        }
        while(guess.compareTo("z") > 0 || guess.compareTo("a") < 0){
          guess = Utils.inputStr("guess a letter. no symbols allowed >:((( ( ( ( : ");
          guess = guess.toLowerCase().trim();
        }
        
      } //check for invalid inputs: too many letters, letter already guessed, input is not a number
      
      
      letters.add(guess);
      for (int j = 0; j < currentWord.length(); j++){
        if (currentWord.substring(j, j+1).equals(guess)){
          show = show.substring(0, j) + guess + show.substring(j + 1, currentWord.length());
        }
      } // put guessed letters in show 
      
      
      if(show.indexOf(guess) == -1)
        wrongGuesses ++;
      

      
      if (currentWord.equals(show)){
        System.out.println("congratulations! you have guessed the word " + show + " with " + wrongGuesses + " wrong guesses :) "); //win
      return;
      }



      //draw the man
      System.out.println("    | ");
      if (wrongGuesses >= 1 && wrongGuesses <= 7){
        System.out.println("  ___");
        System.out.println(" /   \\");
        System.out.println(" \\__ /");
      }
      if (wrongGuesses == 2){
        System.out.println("   |  ");
        System.out.println("   |  ");
        System.out.println("   |  ");
      }
      if (wrongGuesses == 3){
        System.out.println("   |  ");
        System.out.println("---|");
        System.out.println("   |  ");
      }
      if (wrongGuesses >= 4){
        System.out.println("   |  ");
        System.out.println("---|---");
        System.out.println("   |  ");
      }
      if (wrongGuesses == 5){
        System.out.println("  /");
        System.out.println(" /");
      }
      if (wrongGuesses == 6)
      {
        System.out.println("  /  \\ \n /    \\");
      }
      if (wrongGuesses == 7){
        System.out.println("  ___");
        System.out.println(" /.  \\");
        System.out.println(" \\__ /");        
        System.out.println("   |  ");
        System.out.println("---|---");
        System.out.println("   |  ");
        System.out.println("  /  \\ \n /    \\");
      }
      System.out.println();
      //print the man








      
    
    }
    
    System.out.println("    | ");
    System.out.println("  ___");
    System.out.println(" /x x\\");
    System.out.println(" \\__ /");
    System.out.println("    |\n----|----");
    System.out.println("    |\n  /   \\ \n /     \\");
    System.out.println();
    System.out.println("you lost L the word was " + currentWord); //lost
    return;

  }  
  public void addWord(String s){
    s = s.toLowerCase();
    for (int i = 0; i < s.length(); i ++)
      {
        if (s.substring(i, i+1).compareTo("a") < 0 || s.substring(i, i+1).compareTo("z") > 0){
          System.out.println("words dont have symbols stupid");
          return;
        }
      }
    wordBank.add(s.toLowerCase());
  }
  
  public String getLetterList(){
    String s = "";
    for (int i = 0; i < letters.size(); i++){
      s += letters.get(i) + " ";
    }
    return s;
  } //for testing

  public String getLetters(){
    String a = "letters guessed: ";
    for (String s : letters){
      a += s + ", ";
    }
    return a.substring(0, a.length() - 2);
  }

  public String getWordBank(){
    String a = "word bank: ";
    for (String s : wordBank){
      a += s + ", ";
    }
    return a.substring(0, a.length() - 2);
  }

  public void setWordBankStatus(boolean a){
    wordBankEnabled = a;
  }



  public boolean save() {
    
    String fileName = "wordBank.ser";
    
    try {
      FileOutputStream fos = new FileOutputStream(fileName);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(this);
      oos.close();
      fos.close();
      return true;
    } catch (IOException e) {
      System.err.println(e);
      return false;
    }
  }

  // Returns a State class from serialized state stored in the
  // file name + "State.ser", or null if unable to deserialize 
  public static Game restore () {
    String fileName = "wordBank.ser";
    
    try {
		  FileInputStream fis = new FileInputStream(fileName);
      ObjectInputStream ois = new ObjectInputStream(fis);
      Game s = (Game) ois.readObject();
	    ois.close();
	    fis.close();
      return s;
	  } catch(Exception e) { // IOException, ClassNotFoundException
	    return null;
	  }
  }




  
}
  







  
  

