/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sms_oceansim;
import java.util.*;
/**
 *
 * 
 * @author ssoldner21
 */
public class SMS_OceanSim {
    
    public static char[][] WORLD; //global visual output of the world
    //equivalent array size to WORLD, used to determine if an item can move out of the space it is in (ie, hasn't already moved this turn)
    public static boolean[][] canMoveOut;
    //equivalent to WORLD, determine if F/S has already spawned this turn
    public static boolean[][] canSpawn;
    public static Random randomGenerator = new Random(); //random num gen
    
    //Chance each object will move
    public static int BOATMOVE = 500;
    public static int ICEMOVE = 250;
    public static int FISHMOVE = 1000;
    public static int SHARKMOVE = 1000;
    
    public static String STATUS = "";
    public static String MOVESTATUS = "";
    public static String MATESTATUS = "";
    
    public static void createWorld() //create blank world based on user input
    {
        System.out.print("Enter the number of rows: ");
        int rows = SavitchIn.readInt();
        System.out.print("Enter the number of columns: ");
        int cols = SavitchIn.readInt();
        
        WORLD = new char[rows][cols];
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                WORLD[i][j] = 'O';
            }
        }
    }
    
    public static void createCanMoveOut() //create the equivalent array to determine if an object has moved
    {
        canMoveOut = new boolean[WORLD.length][WORLD[0].length];
        
        for (int i = 0; i < canMoveOut.length; i++)
        {
            for (int j = 0; j < canMoveOut[0].length; j++)
            {
                canMoveOut[i][j] = true;
            }
        }
    }
    
    public static void createCanSpawn() //create the equivalent array to determine if an object can Spawn
    {
        canSpawn = new boolean[WORLD.length][WORLD[0].length];
        
        for (int i = 0; i < canSpawn.length; i++)
        {
            for (int j = 0; j < canSpawn[0].length; j++)
            {
                canSpawn[i][j] = true;
            }
        }
    }
    
    public static void setCanMoveOut()//reset canMoveOut array at the end of a "turn"
    {
        for (int i = 0; i < WORLD.length; i++)
        {
            for (int j = 0; j < WORLD[0].length; j++)
            {
                canMoveOut[i][j] = true;
            }
        }
    }
    
    public static void setCanSpawn()//reset canSpawn array at the end of a "turn"
    {
        for (int i = 0; i < WORLD.length; i++)
        {
            for (int j = 0; j < WORLD[0].length; j++)
            {
                canSpawn[i][j] = true;
            }
        }
    }
    
    public static void printWorld()
    {
        char[][] Xaxis;
        
        for (int i = 0; i < WORLD.length; i++) 
        {
            if (WORLD.length - i <= 9) //print Y axis coordinates
            {
                System.out.print("  " + (WORLD.length - i) + " ");
            }
            else if (WORLD.length - i > 9 && WORLD.length - i <= 99)
            {
                System.out.print(" " + (WORLD.length - i) + " ");
            }
            else if (WORLD.length - i > 99 && WORLD.length - i <= 999)
            {
                System.out.print((WORLD.length - i) + " ");
            }
            
            for (int j = 0; j < WORLD[0].length; j++)
            {
                System.out.print(WORLD[i][j] + " ");
            }
            System.out.println();            
        }
        
        //array for Xaxis coordinates
        Xaxis = new char[3][WORLD[0].length + 1];
        //Populate array with proper values
        for (int i = 0; i < Xaxis[0].length; i++)
        {
            if (i <= 9)
            {
                Xaxis[0][i] = (char)(i+48);
                Xaxis[1][i] = ' ';
                Xaxis[2][i] = ' ';
            }
            else if (i > 9 && i <= 99)
            {
                Xaxis[0][i] = (char)(i/10+48);
                Xaxis[1][i] = (char)(i%10+48);
                Xaxis[2][i] = ' ';
            }
            else if (i > 99 && i <= 999)
            {
                Xaxis[0][i] = (char)(i/100+48);
                Xaxis[1][i] = (char)((i/10)%10+48);
                Xaxis[2][i] = (char)(i%10+48);
            }
        }
        //Print X axis array under Ocean world
        for (int i = 0; i < Xaxis.length; i++) //print X axis coordinates
        {
            System.out.print("  ");
            for (int j = 0; j < Xaxis[0].length; j++)
            {
                System.out.print(Xaxis[i][j] + " ");
            }
            System.out.println();
        }
        
        //System.out.print(MOVESTATUS);
        //System.out.print(STATUS);
        
        STATUS = "";
        MOVESTATUS = "";
    }
    
    public static int[] getCoordinates()// get coords for user input
    {
        int[] coord = new int[2];  

        System.out.print("Please enter the X coordinate: ");
        coord[0] = SavitchIn.readInt();
        while (coord[0] <= 0 || coord[0] > WORLD.length)
        {
            System.out.print("Please enter the X coordinates within the available world: ");
            coord[0] = SavitchIn.readInt();
        }
        
        System.out.print("Please enter the Y coordinate: ");
        coord[1] = SavitchIn.readInt();
        while (coord[1] <= 0 || coord[1] > WORLD[0].length)
        {
            System.out.print("Please enter the Y coordinate: ");
            coord[1] = SavitchIn.readInt();
        }
        //convert from user view to WORLD coordinates
        coord[0] = coord[0] - 1;
        coord[1] = WORLD[0].length - coord[1];
        return coord;
    }
    
    public static void insertObject(int[] coords, char object)
    {
        WORLD[coords[1]][coords[0]] = object;
        canMoveOut[coords[1]][coords[0]] = false;
    }
    
    public static void addObject(int startingObjects, char object)
    {
        int[] coords = new int[2];
        
        for (int i = 0; i < startingObjects; i++)
        {
            coords[1] = randomGenerator.nextInt(WORLD.length);
            coords[0] = randomGenerator.nextInt(WORLD[0].length);
            
            if (canMoveOut[coords[1]][coords[0]] == true)//if the space is free add an item there
            {
                insertObject(coords, object);
            }
            else//if the space is take, try again
            {
                i--;
            }
        }
    }
    
    public static void boatCollision(int currentX, int currentY, int newX, int newY, char objectMoving, char object)
    {
        int collisionRandom = randomGenerator.nextInt(1000);
        
        if (objectMoving == 'B')
        {
            objectMove(currentX, currentY, 'B', BOATMOVE);
        }
        else if (objectMoving == 'I')
        {
            if (collisionRandom < 500) //50% chance ice destroyed
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'B';
            }
            else
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'I';
            }
        }
        else if (objectMoving == 'F')
        {
            if (collisionRandom < 250)
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'B';
            }
            else
            {
                objectMove(currentX, currentY, 'F', FISHMOVE);
            }
        }
        else if (objectMoving == 'S')
        {
            if (collisionRandom < 100)
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'B';
            }
            else if (collisionRandom >= 100 && collisionRandom < 200)
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'S';
            }
            else
            {
                objectMove(currentX, currentY, 'S', SHARKMOVE);
            }
        }
    }
    
    public static void iceCollision(int currentX, int currentY, int newX, int newY, char objectMoving, char object)
    {
        int collisionRandom = randomGenerator.nextInt(1000);
        
        if (objectMoving == 'B')
        {
            if (collisionRandom < 500) //50% chance ice destroyed
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'B';
            }
            else
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'I';
            }
        }
        else if (objectMoving == 'I')
        {
            if (collisionRandom < 500)
            {
                WORLD[currentX][currentY] = 'O';
            }
            else
            {
                objectMove(currentX, currentY, 'I', ICEMOVE);
            }
        }
        else if (objectMoving == 'F')
        {
            objectMove(currentX, currentY, 'F', 1);
        }
        else if (objectMoving == 'S')
        {
            objectMove(currentX, currentY, 'S', 1);
        }
    }
     
    public static void fishCollision(int currentX, int currentY, int newX, int newY, char objectMoving, char object)
    {
        int collisionRandom = randomGenerator.nextInt(1000);
        
        if (objectMoving == 'B')
        {
            if (collisionRandom < 250)
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'B';
            }
            else
            {
                objectMove(currentX, currentY, 'B', 1);
            }
        }
        else if (objectMoving == 'I')
        {
            objectMove(currentX, currentY, 'I', 1);
        }
        else if (objectMoving == 'F')
        {
            objectMove(currentX, currentY, 'F', 1);//changed from $$$$MOVE
        }
        else if (objectMoving == 'S')
        {
            WORLD[currentX][currentY] = 'O';
            WORLD[newX][newY] = 'S';
        }
    }
      
    public static void sharkCollision(int currentX, int currentY, int newX, int newY, char objectMoving, char object)
    {
        int collisionRandom = randomGenerator.nextInt(1000);
        
        if (objectMoving == 'B')
        {
            if (collisionRandom < 100)
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'B';
            }
            else if (collisionRandom >= 100 && collisionRandom < 200)
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'S';
            }
            else
            {
                objectMove(currentX, currentY, 'B', BOATMOVE);
            }
        }
        else if (objectMoving == 'I')
        {
            objectMove(currentX, currentY, 'I', ICEMOVE);
        }
        else if (objectMoving == 'F')
        {
            WORLD[currentX][currentY] = 'O';
            WORLD[newX][newY] = 'S';
        }
        else if (objectMoving == 'S')
        {
            if (collisionRandom < 200)
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'S';
            }
            else
            {
                objectMove(currentX, currentY, 'S', SHARKMOVE);
            }
        }
    }
       
    public static void rockCollision(int currentX, int currentY, int newX, int newY, char objectMoving, char object)
    {
        int collisionRandom = randomGenerator.nextInt(1000);
        
        if (objectMoving == 'B')
        {
            if (collisionRandom > 500)
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'R';
            }
            else
            {
                objectMove(currentX, currentY, 'B', BOATMOVE);
            }
        }
        else if (objectMoving == 'I')
        {
            if (collisionRandom > 500)
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'R';
            }
            else
            {
                WORLD[currentX][currentY] = 'O';
                WORLD[newX][newY] = 'I';
            }
        }
        else if (objectMoving == 'F')
        {
             objectMove(currentX, currentY, 'F', 1);
        }
        else if (objectMoving == 'S')
        {
            objectMove(currentX, currentY, 'S', 1);
        }
    }
    
    public static void collision(int currentX, int currentY, int newX, int newY, char objectMoving, char objectHit)
    {
        char object = 'c';
        
        if (objectHit == 'B')
        {
            boatCollision(currentX, currentY, newX, newY, objectMoving, object);
        }
        else if (objectHit == 'I')
        {
            iceCollision(currentX, currentY, newX, newY, objectMoving, object);
        }
        else if (objectHit == 'R')
        {
            rockCollision(currentX, currentY, newX, newY, objectMoving, object);
        }
        else if (objectHit == 'F')
        {
            fishCollision(currentX, currentY, newX, newY, objectMoving, object);
        }
        else if (objectHit == 'S')
        {
            sharkCollision(currentX, currentY, newX, newY, objectMoving, object);
        }
    }
    
    public static void objectMove(int X, int Y, char object, int chance)
    {
        int moveOccurs = randomGenerator.nextInt(1000); 
        if (moveOccurs < chance)//
        {
            //validate
            if (moveOccurs < (chance/4) && canMoveOut[X][Y] == true)//25% chance of each direction
            {//move up
                if (X-1 > 0)
                {
                    MOVESTATUS += object + " moving up \n";
                    //if new space is open ocean
                    if (WORLD[X-1][Y] == 'O')
                    {
                        WORLD[X][Y] = 'O';
                        WORLD[X-1][Y] = object;
                        canMoveOut[X-1][Y] = false;
                    }
                    else //if new space is occupied
                    {
                        STATUS += "Collision occuring \n";
                        collision(X, Y, X-1, Y, object, WORLD[X-1][Y]); //object in new space
                        //WORLD[X][Y] = object in old space
                    }
                }
                else
                {
                    //can't move
                }
                
            }
            else if ((moveOccurs >= (chance/4) && moveOccurs < (chance/2)) && canMoveOut[X][Y] == true)
            {//move down
                if (X+1 < WORLD.length)
                {
                    MOVESTATUS += object + " moving down \n";
                    //can move
                    if (WORLD[X+1][Y] == 'O')
                    {
                        WORLD[X][Y] = 'O';
                        WORLD[X+1][Y] = object;
                        canMoveOut[X+1][Y] = false;
                    }
                    else
                    {
                        STATUS += "Collision occuring \n";
                        collision(X, Y, X+1, Y, object, WORLD[X+1][Y]);
                    }
                }
                else
                {
                    //can't move
                }
            }
            else if ((moveOccurs >= (chance/2) && moveOccurs < (chance/3)) && canMoveOut[X][Y] == true)
            {//move right
                if (Y+1 < WORLD[0].length)
                {
                    MOVESTATUS += object + " moving right \n";
                    //can move
                    if (WORLD[X][Y+1] == 'O')
                    {
                        
                        WORLD[X][Y] = 'O';
                        WORLD[X][Y+1] = object;
                        canMoveOut[X][Y+1] = false;
                        //move right
                    }
                    else
                    {
                        STATUS += "Collision occuring \n";
                        collision(X, Y, X, Y+1, object, WORLD[X][Y+1]);
                    }
                }
                else
                {
                    //can't move
                }
            }
            else if (moveOccurs >= (chance/3) && canMoveOut[X][Y] == true)
            {//move left
                if (Y-1 > 0)
                {
                    MOVESTATUS += object + " moving left \n";
                    //can move
                    if (WORLD[X][Y-1] == 'O')
                    {
                        
                        WORLD[X][Y] = 'O';
                        WORLD[X][Y-1] = object;
                        canMoveOut[X][Y-1] = false;
                    }else
                    {
                        STATUS += "Collision occuring \n";
                        collision(X, Y, X, Y-1, object, WORLD[X][Y-1]);
                    }
                }
                else
                {
                    //can't move
                }
            }
        }
    }
    
    public static void boatDeath(int X, int Y)
    {
        int death = randomGenerator.nextInt(1000); 
        if (death < 50) //5% chance of random boath sinks
        {
            WORLD[X][Y] = 'O';
        }
    }
        
    public static void iceDeath(int X, int Y)
    {
        int death = randomGenerator.nextInt(1000); 
        if (death < 50) //5% chance of random ice melts
        {
            WORLD[X][Y] = 'O';
        }
    }
            
    public static void fishDeath(int X, int Y)
    {
        int death = randomGenerator.nextInt(1000); 
        if (death < 50) //5% chance of random fish death
        {
            WORLD[X][Y] = 'O';
        }
    }
    
    public static void sharkDeath(int X, int Y)
    {
        int death = randomGenerator.nextInt(1000); 
        if (death < 50) //5% chance of random shark death
        {
            WORLD[X][Y] = 'O';
        }
    }
    
    public static void randomDeath()
    {
        for (int i = 0; i < WORLD.length; i++)
        {
            for (int j = 0; j < WORLD[0].length; j++)
            {
                //TESTING System.out.println(currentCoordinates[0] + " " + currentCoordinates[1]);
                switch(WORLD[i][j])
                {
                    case 'B':
                        boatDeath(i, j);
                        break;
                    case 'I':
                        iceDeath(i, j);
                        break;
                    case 'F':
                        fishDeath(i, j);
                        break;
                    case 'S':
                        sharkDeath(i, j);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
    public static void randomMovement()
    {
        for (int i = 0; i < WORLD.length; i++)
        {
            for (int j = 0; j < WORLD[0].length; j++)
            {
                switch(WORLD[i][j])
                {
                    case 'B':
                        objectMove(i, j, 'B', BOATMOVE);
                        break;
                    case 'I':
                        objectMove(i, j, 'I', ICEMOVE);
                        break;
                    case 'R':
                        break;
                    case 'F':
                        objectMove(i, j, 'F', FISHMOVE);
                        break;
                    case 'S':
                        objectMove(i, j, 'S', SHARKMOVE);
                        break;
                    default:
                        break;
                }
            }
        }
        setCanMoveOut();
        
    }
    
    public static void birthFishOrShark(int X, int Y, int secondX, int secondY, char FishOrShark)
    {
        boolean birthComplete = false;
        
        if (X-1 > 0) //up1
        {
            MATESTATUS += "checking up1 \n" + (char)X + (char)Y; 
            if (WORLD[X-1][Y] == 'O' && birthComplete == false)
            {
                WORLD[X-1][Y] = FishOrShark;
                canSpawn[X-1][Y] = false;
                canSpawn[X][Y] = false;
                canSpawn[secondX][secondY] = false;
                
                birthComplete = true;
                MATESTATUS += "fish added \n";
            }  
        }
        
        if (secondX-1 > 0)//up2
        {
            MATESTATUS += "checking up2 \n";
            if (WORLD[secondX-1][Y] == 'O' && birthComplete == false)
            {
                WORLD[secondX-1][Y] = FishOrShark;
                canSpawn[secondX-1][Y] = false;
                canSpawn[X][Y] = false;
                canSpawn[secondX][secondY] = false;
                birthComplete = true;
            }
        }
        
        if (X+1 < WORLD.length) //down1
        {
            MATESTATUS += "checking D1 \n";
            if (WORLD[X+1][Y] == 'O' && birthComplete == false)
            { 
                WORLD[X+1][Y] = FishOrShark;
                canSpawn[X+1][Y] = false;
                canSpawn[X][Y] = false;
                canSpawn[secondX][secondY] = false;
                birthComplete = true;
            }
        }
        
        if (secondX+1 < WORLD.length)//down 2
        {
            MATESTATUS += "checking D2 \n";
            if (WORLD[secondX+1][Y] == 'O' && birthComplete == false)
            {
                WORLD[secondX+1][Y] = FishOrShark;
                canSpawn[secondX+1][Y] = false;
                canSpawn[X][Y] = false;
                canSpawn[secondX][secondY] = false;
                birthComplete = true;
            }
        }
        
        if (Y-1 > 0) //left1
        {
            MATESTATUS += "checking L1 \n";
            if (WORLD[X][Y-1] == 'O' && birthComplete == false)
            {
                WORLD[X][Y-1] = FishOrShark;
                canSpawn[X][Y-1] = false;
                canSpawn[X][Y] = false;
                canSpawn[secondX][secondY] = false;
                birthComplete = true;
            }
        }
       
        if (secondY-1 > 0)//left 2
        {
            MATESTATUS += "checking L2 \n";
            if (WORLD[secondX][secondY-1] == 'O' && birthComplete == false)
            {
                WORLD[secondX][secondY-1] = FishOrShark;
                canSpawn[secondX][secondY-1] = false;
                canSpawn[X][Y] = false;
                canSpawn[secondX][secondY] = false;
                birthComplete = true;
            }
        }
        
       if (Y+1 < WORLD[0].length) //right1
        {
            MATESTATUS += "checking R1 \n";
            if (WORLD[X][Y+1] == 'O' && birthComplete == false)
            {
                WORLD[X][Y+1] = FishOrShark;
                canSpawn[X][Y+1] = false;
                canSpawn[X][Y] = false;
                canSpawn[secondX][secondY] = false;
                birthComplete = true;
            }
        }
        
        if (secondY+1 < WORLD[0].length) //right2
        {
            MATESTATUS += "checking R2 \n";
            if (WORLD[secondX][secondY+1] == 'O' && birthComplete == false)
            {
                WORLD[secondX][secondY+1] = FishOrShark;
                canSpawn[secondX][secondY+1] = false;
                canSpawn[X][Y] = false;
                canSpawn[secondX][secondY] = false;
                birthComplete = true;
            }
        }
    }

    public static void checkFishOrShark(int X, int Y, char FishOrShark)
    {
        //check above, below, left and right for another Fish or Shark
        //Cycle around a a fish or shark looking for another fish or shark
        //only check valid array location
        //up:(X-1, Y) down:(X+1, Y) left:(X, Y+1) right:(X, Y-1)
        if (X-1 > 0) //space above still in array
        {
            if (WORLD[X-1][Y] == FishOrShark && canSpawn[X-1][Y] == true)
            {
                birthFishOrShark(X, Y, X-1, Y, FishOrShark);
            }
        }
        
        if (X+1 < WORLD.length)
        {
            if (WORLD[X+1][Y] == FishOrShark&& canSpawn[X+1][Y] == true)
            {
                birthFishOrShark(X, Y, X+1, Y, FishOrShark);
                
            }
        }
        
        if (Y-1 > 0)
        {
            if (WORLD[X][Y-1] == FishOrShark && canSpawn[X][Y-1] == true)
            {
                birthFishOrShark(X, Y, X, Y-1, FishOrShark);
            }
        }
        
        if (Y+1 < WORLD[0].length)
        {
            if (WORLD[X][Y+1] == FishOrShark && canSpawn[X][Y+1] == true)
            {
                birthFishOrShark(X, Y, X, Y-1, FishOrShark);
            }
        }
        
    }
    
    public static void FSMating()
    {
        char currentFishOrShark = 0;
        
        for (int i = 0; i < WORLD.length; i++)
        {
            for (int j = 0; j < WORLD[0].length; j++)
            {
                currentFishOrShark = WORLD[i][j];
                
                if (currentFishOrShark == 'F' || currentFishOrShark == 'S')
                {
                    checkFishOrShark(i, j, currentFishOrShark);
                }
            }
        } 
        setCanSpawn();
    }
    
    public static void randomSpawn()
    {
        for (int i = 0; i < WORLD.length; i++)
        {
            for (int j = 0; j < WORLD[0].length; j++)
            {
                int spawn = randomGenerator.nextInt(1000);
                int fish = randomGenerator.nextInt(2);
                if (WORLD[i][j] == 'O')
                {
                    if ((i == 0) || (j == 0) || 
                       (j == WORLD[0].length - 1) || (i == WORLD.length - 1)) //if on perimeter
                    {
                        if (spawn < 50) //5% chance boat spawn
                        {
                            WORLD[i][j] = 'B';
                        }
                        else if (spawn >= 50 && spawn < 100) //5% chance ice spawn
                        {
                            WORLD[i][j] = 'I';
                        }
                    }
                }
            }
        }
    }
    
    public static void advanceWorld()
    {        
        randomDeath();
        randomMovement();
        FSMating();
        randomSpawn();
    }
    
    public static void switchboardLogic()
    {
        char input = SavitchIn.readLineNonwhiteChar();
        
        while (input != 'E' || input != 'e')
        {
            switch (input)
            {
                case 'C':
                case'c':
                    // TODO add method to pass a unit of time and move all the objects
                    advanceWorld();
                    break;
                case 'E':
                case 'e':
                    System.out.println("Thank you, Come again.");
                    System.exit(0);
                    // TODO add exit message and warning catch
                    break;
                case 'B':
                case 'b':
                    insertObject(getCoordinates(), 'B');
                    break;
                case 'I':
                case 'i':
                    insertObject(getCoordinates(), 'I');
                    break;
                case 'R':
                case 'r':
                    insertObject(getCoordinates(), 'R');
                    break;
                case 'F':
                case 'f':
                    insertObject(getCoordinates(), 'F');
                    break;
                case 'S':
                case 's':
                    insertObject(getCoordinates(), 'S');
                    break;
                default: 
                    System.out.println("That was an invalid entry, please enter another selection.");
            }
            printWorld();
            printSwitchboard();
            input = SavitchIn.readLineNonwhiteChar();
        } 
        
    }

    public static void printSwitchboard()
    {
        System.out.println("********************************************************");
        System.out.println("*Use the following controls to interact with the world *");
        System.out.println("********************************************************");
        System.out.println("* C |   Advance the world                              *");
        System.out.println("* E |   Exit the world                                 *");
        System.out.println("* B |   Insert a Boat into the world                   *");
        System.out.println("* I |   Insert an Iceberg into the world               *");
        System.out.println("* R |   Insert a Rock into the world                   *");
        System.out.println("* F |   Insert a Fish into the world                   *");
        System.out.println("* S |   Insert a Shark into the world                  *");
        System.out.println("********************************************************");
        System.out.print("* Enter your selection: ");
    }
    
    public static void startingObjects()
    {
        System.out.println("********************************************************");
        System.out.println("*How many of each object is present at start?          *");
        System.out.println("********************************************************");
        System.out.print("* Boats  : ");
        int startingBoats = SavitchIn.readInt(); 
        addObject(startingBoats, 'B');
        System.out.print("* Ice    : ");
        int startingIce = SavitchIn.readInt();
        addObject(startingIce, 'I');
        System.out.print("* Rocks  : ");
        int startingRocks = SavitchIn.readInt();
        addObject(startingRocks, 'R');
        System.out.print("* Fish   : ");
        int startingFish = SavitchIn.readInt();
        addObject(startingFish, 'F');
        System.out.print("* Sharks : ");
        int startingSharks = SavitchIn.readInt();
        addObject(startingSharks, 'S');
    }
    
    public static void oceanSim()
    {
        createWorld();
        createCanMoveOut();
        createCanSpawn();
        startingObjects();
        printWorld();
        printSwitchboard();
        switchboardLogic();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        oceanSim();
    }
}

/*Current status:
 * 
 * User input size of world: done
 * 
 * 
 * ----error on small worlds (1, 10)
 * ----error when an object is completely encircled and tries to move
 * 
 * Randomly populate world with starting objects: done
 * switchboard: done
 * movement: done
 * collisions: done
 * error checking: done
 * BIRFS will not move off the edge: done
 * 
 * random death: done
 * boat/ice spawn: done
 * 
 * 
 * place % chances into variables (GLOBAL subset): done
 */