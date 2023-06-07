import java.util.*;


public class Account{
    
    Scanner input = new Scanner(System.in); 

    //resource variables
    int fates=0;
    int primo = 0; 
    int acFate = 0; 
    

    //pull history varables
    int eFive = 0 ; //event 5 star character/item, what is normally desirable
    int sFive = 0 ; //standard 5 star character/item, what is normally undesriable
    int four = 0 ; //4 star character/item, get one every 10 pulls
    int three = 0; //fodder 3 star items, useless things to fill up the pool

    //wishing probability variables
    int pityCounter = 0; //5 star pity
    int fourPity = 0; //4 star pity
    final int FIVE_STAR_PITY = 89;
    final int FOUR_STAR_PITY = 9;
    boolean hardPity = false; 
    //variables for standard banner
    int spityCounter =0;
    int sfourPity =0;
    

    

    //constructor 
    public Account(int fates, int primos, int acFate){
        this.primo = primos;
        this.fates = fates; 
        this.acFate = acFate; 

    }

    


    //chooses which fate to convert into, how many fates, check if enough primos using check primos, then convert primos and add to the correct variable
    public void convertOption(){
        
        int numFate = 0; 

        System.out.println("\nYou have " + primo + " primogems" ); 
        System.out.println("How many fates would you like to convert? ");
        numFate = input.nextInt(); 
        while(checkPrimos(numFate)==false){
            System.out.println("You have " + this.primo + " primos. " + (numFate * 160) + "primos are required for " + numFate + " fates. ");
            System.out.println("Please put in a valid number of fates to convert to: (Enter \"0\" to exit to main menu)");
            numFate = input.nextInt(); 

        }

        if (numFate==0){
            return;
        }

        System.out.println("You are converting " + numFate + " fates. ");
        
        

        System.out.println("Which fate would you like to convert to? \n" 
        + "1 - Acquaint Fates \n" 
        + "2 - Intertwined Fates \n"); 
        int option = 0;
        option=input.nextInt();

        if(option==1){
            this.primo = this.primo - numFate * 160 ; 
            this.acFate += numFate; 
        }else if (option==2){
            this.primo = this.primo - numFate * 160 ; 
            this.fates += numFate; 
        }else{
            System.out.println("Invalid input, please try again. \n");
            this.convertOption();
        }

        

    }

    //check if user has enough primos
    public boolean checkPrimos (int numConvert){
        if (numConvert<=(this.primo/160)){
            return true;
        }else
        return false; 
    }

    //prints out all the player's resources
    public void displayResource(){
        System.out.println("\nYou have " + primo + " primogems, " + acFate +" acquaint fates, " + fates + " intertwined fates. \n \n "); 
    }

    //displays all the items obtained from wishing
    public void wishHistory (){
        System.out.println("\nYou have (" + three +  ") 3-star items, (" + four + ") 4-star items, (" + sFive + ") standard 5-star items, and (" 
        + eFive + ") event 5-star items. \n" );
        System.out.println("Your current 5-star pity is " + pityCounter + " and your 4-star pity is " + fourPity + " on event banner. \n");
        System.out.println("Your next event 5-star is " + (this.hardPity?"guaranteed":"not guaranteed")+ ". \n"); //ternary operator 
        System.out.println("Your current 5-star pity is " + spityCounter + " and your 4-star pity is " + sfourPity + " on standard banner. \n");
    }


    //method for wishing
    public void pullingOnBanner(){
        int option = 0; 
        boolean pulled= false;
        while(pulled==false){
        System.out.println("You have " + fates + " intertwined fates. ");
        System.out.println("Would you like to do 1 pull or 10 pull? \n" 
        + "1 - 1 pull \n" 
        + "2 - 10 pull \n"
        + "3 - Exit to main menu \n"); 
        
        option = input.nextInt(); 

        if (option==1){
            if(fates<1){
                System.out.println("Not enough intertwined fates, please try again~ \n");
                continue;
            }
            fates -= 1;
            pulled = true; 
            this.probabilityFiveStar(1);
            
            
        }else if (option==2){
            if(fates<10){
                System.out.println("You need " + (10-fates) + " more intertwined fates, please try again~ \n");
                continue;
            }
            fates -= 10;
            pulled = true; 
            this.probabilityFiveStar(10);
            


        }else if(option ==3 ){
            System.out.println("Backing to main menu \n ");
            return; 
        }else {
            System.out.println("Invalid input, please try again! ");
            this.pullingOnBanner();
        }

    }


    }
    //method calculates the factor (will be used to divide by 1000) for determining probability
    // 0.6% chance throughout most pity number, except for around 75 pity to 83 pity
    //https://www.hoyolab.com/article/260980 
    // factor = 1.76*10^15*e^(-0.392*pityCounter) --- factor =176*10^13*e^(-0.392*pityCounter)
    public int probabilityFactor(int pityCounter){
        int factor = 6 ; 
        if (pityCounter>=76 && pityCounter<=83){
            factor = (int)(176 * Math.pow(10, 13) * Math.exp(-0.392 * pityCounter));
            //System.out.println("factor: " + factor + " at " + pityCounter); //for debugging purpose
        }


        return factor; 
    }


    //probability component method for event banner
    public void probabilityFiveStar (int numPull){
        Random dice = new Random(); 
        int roll = 0; 

        for (int i=0; i<numPull; i++){

            //soft pity
            if(pityCounter==FIVE_STAR_PITY && (hardPity==false)){
                fiftyFifty();
                this.pityCounter = 0;

                continue;
            }

            //hard pity
            if(pityCounter==FIVE_STAR_PITY && (hardPity==true)){
                this.eFive++;
                this.pityCounter = 0;
                hardPity=false; 
                System.out.println("Congratulations! You have obtained a 5 star event character/item. \n");
                continue;
            }

            roll = dice.nextInt(1001); 
            //probability on rolls, non-endpoint probability
            if (roll<=(probabilityFactor(pityCounter))){
                if (hardPity==true){
                    this.eFive++;
                    this.pityCounter =0;
                    hardPity = false; 
                    System.out.println("Congratulations! You have obtained a 5 star event character/item. \n");
                    continue;
                }
                fiftyFifty();
                continue;
            }

            if(fourPity==FOUR_STAR_PITY){
                this.four++; 
                fourPity=0;
                System.out.println("You have obtained a 4 star character/item. \n");
                continue; 
            }

            if (roll<=25){
                this.four++;
                fourPity = 0;
                System.out.println("You have obtained a 4-star character/item. \n");
                continue; 
            }

            this.three++; 
            System.out.println("You have obtained a 3-star item. \n");


            pityCounter++; //increment the pity counter if no 5 star is chanced
            fourPity++; //increment the four star pity counter if no 4 star is chanced

        }
    }

   

    //fifty fifty determination
    public void fiftyFifty (){
        Random dice = new Random();
        int roll = 0;
        roll = dice.nextInt(2); //0 for getting standard character, 1 for getting event character
        System.out.println("Congratulations! You have obtained a 5-star " + ((roll==1)?"event":"standard") + " character! \n" );
        if (roll==1){
            this.eFive++;
            hardPity = false; 
        }
        if (roll==0) {
            this.sFive++; 
            this.hardPity = true; 
        }
    }
    //standard banner wish
    public void pullingOnStandard(){
        int option = 0; 
        boolean pulled= false;

        while(pulled==false){
            System.out.println("You have " + acFate + " acquaint fates. ");
            System.out.println("Would you like to do 1 pull or 10 pull? \n" 
            + "1 - 1 pull \n" 
            + "2 - 10 pull \n"
            + "3 - Exit to main menu \n"); 
            
            option = input.nextInt(); 

            if (option==1){
                if(acFate<1){
                    System.out.println("Not enough intertwined fates, please try again~ \n");
                    continue;
                }
                acFate -= 1;
                pulled = true; 
                this.probabilityStandard(1); /**/
                
                
            }else if (option==2){
                if(acFate<10){
                    System.out.println("You need " + (10-acFate) + " more intertwined fates, please try again~ \n");
                    continue;
                }
                acFate -= 10;
                pulled = true; 
                this.probabilityStandard(10); /* */
                


            }else if(option ==3 ){
                System.out.println("Backing to main menu \n ");
                return; 
            }else {
                System.out.println("Invalid input, please try again! ");
                this.pullingOnStandard();
            }
        }
    }

    //probability component method
    public void probabilityStandard(int numPull){
        Random dice = new Random(); 
        int roll = 0; 

        for (int i=0; i<numPull; i++){


            //pity
            if(spityCounter==FIVE_STAR_PITY){
                this.sFive++;
                this.spityCounter = 0;
                System.out.println("Congratulations! You have obtained a 5 star standard character/item. \n");
                continue;
            }

            roll = dice.nextInt(1000); 
            //probability on rolls, non-endpoint probability
            if (roll<=(probabilityFactor(spityCounter))){
                
                    this.sFive++;
                    System.out.println("Congratulations! You have obtained a 5-star standard character/item. \n");
                    this.spityCounter =0;
                    
                continue;
            }

            if(sfourPity==FOUR_STAR_PITY){
                this.four++; 
                sfourPity=0;
                System.out.println("You have obtained a 4 star character/item. \n");
                continue; 
            }

            if (roll<=25){
                this.four++;
                sfourPity = 0;
                System.out.println("You have obtained a 4-star character/item. \n");
                continue; 
            }

            this.three++; 
            System.out.println("You have obtained a 3-star item. \n");


            spityCounter++; //increment the pity counter if no 5 star is chanced
            sfourPity++; //increment the four star pity counter if no 4 star is chanced

        }
    }
}
