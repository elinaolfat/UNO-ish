package CCT;
public class Player { //Saleh
  private int playerId;
  private Card[] playerHand;
  
  //constructor, instantianting the variables
  public Player(int id) {
    this.playerId = id;
    playerHand = new Card[7];
  }
  
  //getter method
  //returns the id of the player
  public int GetId() {
    return playerId;
  }
  
  //getter method
  //returns the hand card object of the player
  public Card[] GetHand() {
    return this.playerHand;
  }

  //setter method
  //sets the hand of the player
  public void SetHand(Card[] hand) {
    this.playerHand = hand;
  }
}