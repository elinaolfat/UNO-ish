package CCT;

public class UnoGS { //Elina & Saleh
  public final static Card[] CARD = new Card[108]; //original deck
  public final static Player[] PLAYER = new Player[2]; //array that keeps track of all players]
  private Card[] shufCard; //shuffled deck
  private Card centerCard; //the card that's currently on the table
  private Player currPlayer; //the current player
  private int posInCard; //used in the Shuffler() method to keep track of the position in the original card array
  private char nextColor; //the color of the next card. Determined when a black card is played
  private boolean winner; //keeps track of whether there is a winner
  private boolean isShufEmpty; //keeps track of whether the shuffled deck is empty

  //========================================= CONSTRUCTOR =========================================//
  //Instantiating our variables
  public UnoGS() { //Elina & Saleh
    PLAYER[0] = new Player(0);
    PLAYER[1] = new Player(1);
    shufCard = new Card[108];
    centerCard = null;
    currPlayer = null;
    posInCard = 0;
    nextColor = 0;
    winner = false;
    isShufEmpty = false;
  }
  
  //======================================= PRIVATE METHODS =======================================//
  //generates a random integer between the given range
  private int RandNumInRange(int min, int max) { //Elina
    int num = (int)(Math.random() * (( max - min )+1)) + min;
    return num;
  }
  
  //Generates deck, adds all cards to the card array
  private void GenDeck() { //Elina & Saleh
    int cardPos = 0;
    for (int zero = 0; zero < 4; zero++) {
      CARD[cardPos] = new NumCard("RYGB".charAt(zero), 0);//Red, Yellow, Green, Blue
      cardPos++;
    }
    for (int colorPos = 0; colorPos<8; colorPos++) {
      for (int num = 1; num <=9; num++){
        CARD[cardPos] = new NumCard("RYGBRYGB".charAt(colorPos), num);
        cardPos++;
      }
      for (int action = 0; action<3; action++) { 
        CARD[cardPos] = new ActionCard("RYGBRYGB".charAt(colorPos), "SRT".charAt(action));//Skip, Reverse, Two plus
        cardPos++;
      }
    }
    for (int i = 0; i<8; i++) {
      CARD[cardPos] = new BlackCard('b', "CCCCFFFF".charAt(i));//black, Color change, Four plus
      cardPos++;
    }
  }
  
  //Fills random positions in the shufCard's array with Card objects from the original array
  private void Shuffler() { //Elina
    int posInShuf = this.RandNumInRange(0, 107);
    if (!this.IsFull(shufCard)){
      if (shufCard[posInShuf] == null){
        shufCard[posInShuf] = CARD[posInCard];
        posInCard++;
        Shuffler();
      }
      else {
        Shuffler();
      }
    }
  }
   
  //generates players starting hand and the initial center card
  private void StartingCards() { //Saleh
    while (true) {
      Card[] player0 = new Card[7];
      Card[] player1 = new Card[7];
      System.arraycopy(shufCard, 0, player0, 0, 7);
      shufCard = this.Remove(shufCard, player0);
      System.arraycopy(shufCard, 0, player1, 0, 7);
      shufCard = this.Remove(shufCard, player1);
      
  
      PLAYER[0].SetHand(player0);
      PLAYER[1].SetHand(player1);

      if(shufCard[0] instanceof NumCard){
        centerCard = this.shufCard[0];
        shufCard = RemoveFromArray(shufCard, centerCard) ;
        break;
      }
      else{
        this.Shuffler();
      }
    }
  }

  //Checks if card exists in the list or not
  //if exists, empties the position of that card in the list, then returns true
  //if doesn't exist, returns false
  private boolean CheckExist(Card c, Card[] list) { //Saleh
    for (int i = 0; i<list.length; i++){
      if (list[i] != null) {
        if ((c.GetColor() == list[i].GetColor()) && (c.GetInfo() == list[i].GetInfo())) {
          list[i] = null;
          return true;
        }
      }
    }
    return false;
  }

  //adds specified cards to array
  //creates temp array that represents currList with elements of addList included in it
  //copies all objects from currList and addList
  //returns reference of temp array
  private Card[] Add(Card[] currList, Card[] addList) { //Saleh
    Card[] temp = new Card[currList.length + addList.length];
    for (int i = 0; i < currList.length; i++) {
      temp[i] = currList[i];
    }
    int counter = 0; 
    for (int i = currList.length; i < temp.length; i++) {
      temp[i] = addList[counter];
      counter++;
    }
    return temp;
  }

  //removes specified cards from array
  //creates temp array that represents currList without elements of removeList
  //copies all objects from currList except for those who are in removeList
  //returns the reference of temp array
  private Card[] Remove(Card[] currList, Card[] removeList) { //Elina
    Card[] temp = new Card[currList.length - removeList.length];
    Card[] remove = new Card[removeList.length];
    System.arraycopy(removeList, 0, remove, 0, removeList.length);
    int counter = 0;
    for (int i = 0; i<currList.length; i++) {
      if (!(this.CheckExist(currList[i], remove))) {
        temp[counter] = currList[i];
        if (counter < temp.length-1) {
          counter++;
        }
      }
    }
    return temp;
  }
  
  private void SetCurrPlayer(int id ) { //Elina
    this.currPlayer = PLAYER[id];
  }

  //======================================= PUBLIC  METHODS =======================================//
  //accesses all the methods that generate the initial game structure
  public void StartGame() { //Elina & Saleh
    this.SetCurrPlayer(0);
    this.GenDeck();
    this.Shuffler();
    this.StartingCards();
  }

  //getter method
  //Returns shuffled card object
  public Card[] GetShufCard() { //Saleh
    return shufCard;
  }

  //setter method
  //Sets the shufCard array to the array reference given
  public void SetShufCard (Card[] shufCard) { //Saleh
    this.shufCard = shufCard;
  }
  
  //getter method
  //Returns centerCard object
  public Card GetCenterCard() { //Elina
    return this.centerCard;
  }
  
  //setter method
  //sets the value of center card to the new card, used in main whenever a player throws their card
  public void SetCenterCard (Card centerCard) { //Elina
    this.centerCard = centerCard;
  }

  //setter method
  //sets the next color determined by the current player, used whenever a black card is played
  public void SetNextColor (char nextColor) { //Saleh
    this.nextColor = nextColor;
  }
  
  //compares the card played by current player with the center card
  //returns true if the card played is acceptable
  public boolean CompareCards(Card c) { //Saleh
    if (c.GetColor() == 'b') {
      return true;
    }
    else{
      if (centerCard.GetColor() == 'b'){
        if (c.GetColor() == this.nextColor) {
          return true;
        }
      }
      else if (c.GetColor() == centerCard.GetColor() || c.GetInfo() == centerCard.GetInfo()) {
        return true;
      }
      return false;
    }
  }

  //applies the game rules by checking the centercard 
  public void ApplyRules() { //Elina
    Card[] cardsToAdd;
    if ((centerCard.GetColor() == 'b') && (centerCard.GetInfo() == 'F')) {
      cardsToAdd = new Card[4];
      System.arraycopy(shufCard, 0, cardsToAdd, 0, 4);
      currPlayer.SetHand(this.Add(currPlayer.GetHand(), cardsToAdd));
      shufCard = this.Remove(shufCard,cardsToAdd);
    }
    else if (centerCard.GetInfo() == 'S') {
      this.SwitchPlayer();
    }
    else if (centerCard.GetInfo() == 'R') {
      this.SwitchPlayer();
    }
    else if (centerCard.GetInfo() == 'T') {
      cardsToAdd = new Card[2];
      System.arraycopy(shufCard, 0, cardsToAdd, 0, 2);
      currPlayer.SetHand(this.Add(currPlayer.GetHand(), cardsToAdd));
      shufCard = this.Remove(shufCard,cardsToAdd);
    }
  }
  
  //getter method
  //Get whether there is a winner or not
  //returns true if yes
  //returns if not
  public boolean GetWinner() {//Elina
    return this.winner;
  }

  //setter method, sets the winner variable to true
  public void SetWinner() {//Elina
    this.winner = true;
  }

  //getter method
  //Get whether shufCard is empty
  //returns true if empty
  //returns false if not
  public boolean GetIsShufEmpty() { //Saleh
    return this.isShufEmpty;
  }
 
  //setter method, sets the shufCard array to empty
  public void SetShufToEmpty() { //Saleh
    this.isShufEmpty = true;
  }
    
  //checks whether the array is full or not
  //returns true if no more empty slots left in the array
  //returns false as soon as it finds a null slot in the array
  public boolean IsFull(Card[] list) { //Saleh
    for (int i = 0; i < list.length; i++) {
      if (list[i] == null) {
        return false;
      }
    }
    return true;
  }
  
  //getter method, returns the object of the current player 
  public Player GetCurrPlayer() { //Elina
      return currPlayer;
   }
  
  //getter method takes in the player's id, and returns the object of the player
  public Player GetPlayer(int id ) { //Elina
      return PLAYER[id];
   }
  
   //Switches players, using a switch statement (player 1's id is 0, player 2's id is 1)
  public void SwitchPlayer() { //Saleh
    switch (currPlayer.GetId()) {
      case 0:
        currPlayer = PLAYER[1];
        break;
      case 1:
        currPlayer = PLAYER[0];
        break;
    }
  }

  //Adds cards to the hand of the player given to it
  public void AddToArray(int id, Card c) { //Saleh
    Card[] arr = {c};
    PLAYER[id].SetHand(this.Add(PLAYER[id].GetHand(), arr));
  }
  
  //Removes cards from the hand of the player given to it
  public void RemoveFromArray(int id, Card c) { //Elina
    Card[] arr = {c};
    PLAYER[id].SetHand(this.Remove(PLAYER[id].GetHand(), arr));
  }

  //removes the given card object from the given array
  public Card[] RemoveFromArray(Card[] list, Card c) { //Elina
    Card[] arr = {c};
    return this.Remove(list, arr);
  }
}