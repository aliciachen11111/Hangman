/*
Main - this is a runner class that runs the Game class

Author - Alicia Chen
*/
import java.util.Scanner;
class Main {
  public static void main(String[] args) {
    System.out.println("starting...");
    Scanner s = new Scanner(System.in);
   
    Game g = new Game();
    String action = "";
    // g.play();
    do{
      System.out.println("what action: a to add word to word list, n to start new game, c to change settings, s to save word bank and settings, r to resume previous game, q to quit");
      action = s.nextLine();
      switch (action){
        case "q":
          System.out.println("Thanks for playing!");
          break;
        case "a":
          g.addWord(Utils.inputStr("type a word to add to word bank: "));
          break;
        case "c":
          int a = Utils.inputNum("enter 1 if you want a word bank, enter 2 if you don't");
          if (a == 1)
              g.setWordBankStatus(true);
          else if (a == 2)
              g.setWordBankStatus(false);
          else
            System.out.println("bruh");
          
          
          
          break;
        case "r":
          g = Game.restore();
          System.out.println("last game restored");
          break;

        case "s":
          g.save();
          System.out.println("game saved");
          break;
        case "n":
          System.out.println("*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n");
          g.play();
          break;
        default:
          System.out.println("game broken come back when you can read instructions");
          break;
      }  
    }while (!action.equals("q"));
      
   }


  // Unit tests for Utils.java. Call this from main if you want to
  // see examples of the Utils methods
  public static void testUtils () {
    return;
    
    // int n = Utils.inputNum ("What's your age? ");
    // System.out.println ("You typed " + n);

    // int r = Utils.randInt (1, 100);
    // System.out.println ("A random number between 1-100 is " + r);

    // String[] strs = {"chocolate", "vanilla", "strawberry"};
    // s = Utils.randChoice(strs);
    // System.out.println ("A random flavor is: " + s);
  }
}