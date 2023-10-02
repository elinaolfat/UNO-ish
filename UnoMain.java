package CCT;
import java.util.Scanner;

public class UnoMain {
  //main method
  public static void main(String[] args) { //Saleh & Elina
    Scanner input = new Scanner(System.in);
    UnoGS gs = new UnoGS();
    Displayer disp = new Displayer();
    int cardPos = 0;
    int action = 0;

    //calling start game method to generate the deck, set the current player and shuffle the deck
    gs.StartGame();
    //calling dispcentercard to display the center card
    disp.DispCenterCard(gs.GetCenterCard());

    //using while loop to break whenever nessecary
    while (true) {
      //displaying the current player
      disp.DispPlayerHand(gs.GetCurrPlayer());
      System.out.print("Choose a card position (starting with 1, from L2R) or draw a card (press 0): ");
      //while loop to catch errors
      while(true) { 
        cardPos = input.nextInt();
        cardPos--;
        input.nextLine();
        
        //checking if the number the player chose is a correct position
        if ((cardPos < gs.GetCurrPlayer().GetHand().length) && (cardPos >= 0)) {
          //compares the cards to see if the player can use it
          if (gs.CompareCards(gs.GetCurrPlayer().GetHand()[cardPos])) {
            //if there's only one card left in the player's hand and the player drops it they win
            if(gs.GetCurrPlayer().GetHand().length == 1) {
              gs.SetWinner();
            }
            //setting the center card as the card the player chose
            gs.SetCenterCard(gs.GetCurrPlayer().GetHand()[cardPos]);
            //removing the card the player chose from their hand
            gs.RemoveFromArray(gs.GetCurrPlayer().GetId(), gs.GetCurrPlayer().GetHand()[cardPos]);
            break;
          }
          //if the player chooses a wrong pos or an incompatible card they get this error
          else {
            System.out.print("Please choose a valid card: ");
          }
        }
        //if the player enters 0 (-1 in array) it enters while loop to pick up cards
        else if(cardPos == -1) {
          //while loop to break when nessecary
          while(true) {
            //using try and catch if the shuffled deck's length reaches 0 to avoid index out of bound and declaring the game a tie
            try {
              gs.AddToArray(gs.GetCurrPlayer().GetId(), gs.GetShufCard()[0]);//adds new card to players hand
              gs.SetShufCard(gs.RemoveFromArray(gs.GetShufCard(), gs.GetShufCard()[0])); //removing cards from the deck 
              int pos = gs.GetCurrPlayer().GetHand().length-1; //since the element in position 0 of the shufCard is now removed, and added to the last position of the pleyer hand, from here on we'll be using pos to access the card
              System.out.println("The card you drew is: "+gs.GetCurrPlayer().GetHand()[pos].GetColor() + "" + gs.GetCurrPlayer().GetHand()[pos].GetInfo());
              if(gs.CompareCards(gs.GetCurrPlayer().GetHand()[pos])){ //checks whether compatible with center card or not
                System.out.print("Press 1 to throw, press 0 to keep: ");  //checking if the player still wants to pick up a card or throw the compatible card given to them
                while(true) {//error handling
                  action = input.nextInt();
                  input.nextLine();
                  if((action == 0) || (action == 1)) {
                    break;
                  }
                  //if the player enters an invalid key
                  else {
                    System.out.print("Please press a valid key: ");
                  }
                }
                //if the player decides to drop the compatible card 
                if(action == 1) {
                  gs.SetCenterCard(gs.GetCurrPlayer().GetHand()[pos]);
                  gs.RemoveFromArray(gs.GetCurrPlayer().GetId(), gs.GetCurrPlayer().GetHand()[pos]);
                  if(gs.GetCurrPlayer().GetHand().length == 1) {
                    gs.SetWinner();
                  }
                  break;
                }
              }
            } 
            //catching the index out of bounds error to decalre the game as a tie
            catch(java.lang.ArrayIndexOutOfBoundsException e) {
              gs.SetShufToEmpty();
              break;
            }
          }
          break;
        }

        else{
          System.out.print("Please pick a valid position: ");
        }
      }
      //if any player wins
      //calling get currplayer to declare the winner
      if (gs.GetWinner()) {
        System.out.println("Player "+gs.GetCurrPlayer().GetId()+" wins!");
        break;
      }
      //checking if there's anymore cards left by calling getisshuffempty
      if(gs.GetIsShufEmpty()){
        System.out.println("No more cards left, game ends with a tie!");
        break;
      }

      //displys reverse card if neccessary
      if (gs.GetCenterCard().GetInfo() == 'R') {
        disp.DispReverseCard(gs.GetCenterCard());
      }

      disp.DispCenterCard(gs.GetCenterCard());
      //if a player throws a black card
      if (gs.GetCenterCard() instanceof BlackCard) {
        //while loop to break whenever nessecary
        while(true) {
          System.out.print("Choose a color (R, Y, G, B): ");
          String color = input.nextLine().toUpperCase();
          char colorChar = color.charAt(0);
          //checking if the player entered a valid color
          if ((colorChar == 'R') || (colorChar == 'Y') || (colorChar == 'G') || (colorChar == 'B')) {
            gs.SetNextColor(colorChar);
            break;
          }
          else {
            System.out.print("Please enter a valid color: ");
          }
        }
      }
      //after the current player's move switching players and applying the rules
      gs.SwitchPlayer();
      gs.ApplyRules();

      //to display to how many cards added or if an action card is played
      switch (gs.GetCenterCard().GetInfo()){
        case 'F':
          disp.DispCardsAdded(gs.GetCurrPlayer(), 4);
          break;
        case 'T':
          disp.DispCardsAdded(gs.GetCurrPlayer(), 2);
          break;
        case 'S':
        case 'R':
          disp.DispPlayerTurn(gs.GetCurrPlayer());
          break;
      }
    }
    input.close();
  }
}
  
