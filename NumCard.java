package CCT;
public class NumCard extends Card { //Saleh
    private char color; 
    private int num;

    //constructor, instantianting the variables
    public NumCard(char color, int num){
      super();
      this.color = color;
      this.num = num;
    }

    //getter method
    //returns the color of the card
    public char GetColor(){
      return color;
    }
    
    //getter method
    //returns the type of the card
    //using upcasting to convert int to char
    public char GetInfo(){
      char charNum = (char)(num + '0');
      return charNum;
    }
  }
