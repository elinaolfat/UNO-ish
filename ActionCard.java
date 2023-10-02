package CCT;
public class ActionCard extends Card{ //Saleh
  private char color;
  private char action;
  
    //constructor, instantianting the variables
  public ActionCard(char color, char action){
    super();
    this.action = action;
    this.color = color;
  }
  
  //getter method
  //returns the color of the card
  public char GetColor(){
    return color;
  }
  
  //getter method
  //returns the type of the card
  public char GetInfo(){
    return action;
  }
}