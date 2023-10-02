package CCT;
public class BlackCard extends Card{ //Elina
  private char color;
  private char type;

  //constructor, instantianting the variables
  public BlackCard(char color, char type){
    super();
    this.color = color;
    this.type = type;
  }
  
  //getter method
  //returns the color of the card
  public char GetColor(){
    return color;
  }

  //getter method
  //returns the type of the card
  public char GetInfo(){
    return type;
  }
}
