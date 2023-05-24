/*Personal project because I like a certain game called "Genshin Impact."
 * Just coding for fun mimicking the game's gacha system (essentially probability of getting certain things in the game). 
 * There are resources that the player gets from progressing in the game and players can choose to spend them in order
 * to have a chance of getting a character or item in the game. 
 * Acquaint fates: for standard banner
 * Intertwined fates: for event banners (what players really want)
 * Primogems: can be converted to intertwined fates or acquaint fates(not optimal for acquaint fates), 180 primogems converts to 1 fate
 * -----------------------------------------------------------------------------------------------------------------------------
 * In genshin, event 5 stars are desirable most of the time, and standard 5 stars means that one lost the 50% chance of getting the event 5 star on first 5 star pity. 
 * After losing the 50% chance once, the second time of getting a 5 star will definitely be event 5 star. 
 * Each 5 star pity is 90 pulls or wishes. So a guaranteed event 5 star assuming no pull history will be 180, though you might get 5 star on the first pity, or maybe even before that. 
 *------------------------------------------------------------------------------------------------------------------------------------
 *  Each 4 star pity is every 10 wishes. 
 * 
 * PS: Honkai Star Rail uses the same system, changing the name of currencies will suffice for a honkai star rail wishing simulator!!!!
 */



import java.util.*;
 

public class App  {
    
   
    public static void main(String[] args) {
        
        int primogems, acFate, intFate = 0; 
        Scanner input = new Scanner(System.in); 
        int command = 99; //variable to hold the command of the player at menu
        

        System.out.println("Welcome to Genshin Wishing! How many primogems do you have? (Please Enter a number): ");
        primogems = input.nextInt(); 
        System.out.println("How many acquaint fates do you have? (Please Enter a number)");
        acFate = input.nextInt();
        System.out.println("How many intertwined fates do you have? (Please Enter a number)");
        intFate =  input.nextInt();

        

        Account wish = new Account(intFate, primogems, acFate); //creating the wish object with constructor taking in parameters
        
        do {
            System.out.println("Enter a number to select an option: \n"
            + "0 - Exit Application \n"
            + "1 - Wish on Event Banner \n"
            + "2 - Convert Primogems to fates \n"
            + "3 - Show Wish History \n"
            + "4 - Show Account Resources \n"
            + "5 - Wish on Standard Banner \n");

            command = input.nextInt(); 


            //option selection
            if (command == 0 ){
                System.exit(0);
            } else if (command  == 1){
                wish.pullingOnBanner();
            }else if (command == 2){
                wish.convertOption(); 
            }else if (command ==3){
                wish.wishHistory();
            }else if(command ==4){
                wish.displayResource(); 
            }else if(command ==5 ){
                wish.pullingOnStandard(); 
            }else {
                System.out.println("Invalid input, please try again: ");
                continue; 
            }


            

        }while (command!=0);

        


        input.close();

    }



}
