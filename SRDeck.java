import java.util.*;
import java.util.Random;
import java.util.Arrays;

/**
* This class will instantiate the deck, and will create both draw and discard piles.
* It also includes functions to shuffle the discard pile, and also to check if the 
* draw pile is empty, so that the user will know when to implement a shuffle.
* 
* @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
*/

public class SRDeck {

	//intialize two array lists to handle the draw and discard piles
	public ArrayList<SRCard> drawPile;
	public ArrayList<SRCard> discardPile;

	//Create the deck and all of the individual cards
	public SRDeck() {
		discardPile = new ArrayList<SRCard>(45);
		drawPile = new ArrayList<SRCard>(45);
		//Add cards to the drawPile according to how many of each kind
		for(int i = 0; i<4; i++) {
			drawPile.add(new SRCard(0));
		}for(int i = 0; i<5; i++) {
			drawPile.add(new SRCard(1));
		}for(int i = 0; i<4; i++) {
			drawPile.add(new SRCard(2));
		}for(int i = 0; i<4; i++) {
			drawPile.add(new SRCard(3));
		}for(int i = 0; i<4; i++) {
			drawPile.add(new SRCard(4));
		}for(int i = 0; i<4; i++) {
			drawPile.add(new SRCard(5));
		}for(int i = 0; i<4; i++) {
			drawPile.add(new SRCard(7));
		}for(int i = 0; i<4; i++) {
			drawPile.add(new SRCard(8));
		}for(int i = 0; i<4; i++) {
			drawPile.add(new SRCard(10));
		}for(int i = 0; i<4; i++) {
			drawPile.add(new SRCard(11));
		}for(int i = 0; i<4; i++) {
			drawPile.add(new SRCard(12));
		}
		//*******  Comment out when testing this class
		shuffle(); //Shuffles the new deck
	}

	//This function will shuffle the discard pile into a random set of cards
	public void shuffle() {
		Random rand = new Random();
		for (int i = 0; i < drawPile.size(); i++) {
			int j = rand.nextInt(drawPile.size());
			SRCard temp = drawPile.get(i);
			drawPile.set(i, drawPile.get(j));
			drawPile.set(j, temp);			
		}
	}	
	
	//Will select the top card off of the drawPile and return it
	public SRCard drawCard() {
			SRCard drawCard = drawPile.get(0); //Choose top card of the deck
			discardPile.add(drawPile.get(0)); //Add chosen card to discardPile
			drawPile.remove(0); //Remove top card off the deck
			return drawCard;		
	}

	//Check to see if drawPile is empty
	public boolean isEmpty() {
		if (drawPile.isEmpty()) {
			drawPile = discardPile; //Make a new drawPile
			shuffle(); //Shuffle the new drawPile
			discardPile = new ArrayList<SRCard>(45); //Make a new discardPile
			return true;
		}
		else 
			return false;
	}
	/*
	//Tests the SRDeck class
	public static void main(String[] args){
		//Initialize new cards and a new deck
		SRCard card = new SRCard(0);
		SRDeck deck = new SRDeck();
		SRRule rule = new SRRule();
		System.out.println("Draw Pile: " + deck.drawPile); //Tests that the drawPile is composed correctly
		//Uncomment the following statement when testing this class.  Comment out the shuffle function that is implemented within SRDeck above marked by *****
		//deck.shuffle(); //Runs the shuffle function.
		System.out.println("Shuffled Draw Pile: " + deck.drawPile); //Will re-display the drawPile to see if the shuffle function worked
		//Iterate through the entire deck
		for(int y=0;y<=44;y++){
			System.out.println("Using Draw Card: " + deck.drawCard()); //Again shows the toString function in the SRCard class.  Proves drawCard is working.
			//Uncomment and set loop to iterate more than 45 times to test isEmpty
			//System.out.println("Is the draw pile empty?" + deck.isEmpty());
		}
		System.out.println("Discard Pile: " + deck.discardPile); //Shows that the discardPile has been created
		System.out.println("Empty Draw Pile: " + deck.drawPile); //Shows that the drawPile has been reduced to empty
		//This will test the isEmpty function
		//By changing the number of iterations in the loop above to a number below 44, we see the isEmpty will still work but will report "false" instead of "true"
		//We will also see that the drawPile has now been refilled and that the discardPile has been reduced to empty.
		//We will once again call the shuffle function to make a new drawPile.
		System.out.println("Is the draw pile empty? " + deck.isEmpty());
		System.out.println("New Shuffled Draw Pile: " + deck.drawPile);
		System.out.println("New Empty Discard Pile: " + deck.discardPile);
	}
	*/

}