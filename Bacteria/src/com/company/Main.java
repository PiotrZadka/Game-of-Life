package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {
    static int x = 10;
    static int y = 10;
    static int[][] inputTable = new int[x][y];
    static int[][] newTable = new int[x][y];

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // x and y location for provided bacteria coordinates
        int xLoc;
        int yLoc;

        // Placeholder variables for setting fixed amount of generations
        int gen = 0;
        int amount = 0;

        String input = "";

        // Populating bacteria array with "dead" cells. dead = 0
        for(int i = 0; i < inputTable.length; i++ ) {
            for (int j = 0; j < inputTable.length; j++) {
                inputTable[i][j] = 0;
            }
        }

        // Populating bacteria dish with "alive" cells using provided coordinates. alive = 1
        while(!input.equals("end")){
            input = reader.readLine();
            for(int i = 0; i < inputTable.length; i++ ){
                for(int j = 0; j < inputTable.length; j++){
                    if(!input.equals("end")) {
                        String[] extractedValue = input.split(",");
                        xLoc = Integer.parseInt(extractedValue[0]);
                        yLoc = Integer.parseInt(extractedValue[1]);
                        inputTable[xLoc][yLoc] = 1;
                    }
                }
            }
        }
        //Continuous generations can be set to fixed amout by setting while(gen < amount)
        while(true) {
            printDishAfterChange(inputTable);
            System.out.println(" - - - BREAK - - - ");
            try{
                Thread.sleep((1000));
            }catch (Exception e){
                e.printStackTrace();
            }
            // un-comment below if added fixed generation amount in while loop
            // gen++
        }

    }


    public static void printDishAfterChange(int [][] bufferTable){
        for(int i = 0; i < bufferTable.length; i++ ){
            for(int j = 0; j < bufferTable.length; j++) {
                // count each grid element for surrounding neighbours ( 8 surrounding elements )
                int AliveNeighboursCount  = checkSurroundingAliveBacteria(bufferTable, i,j);
                //System.out.print("\nCELL["+i+"]["+j+"]"+AliveNeighboursCount);

                // Check if bacteria is dead and has 3 neighbours to come alive
                if(bufferTable[i][j] == 0 && AliveNeighboursCount == 3){
                    newTable[i][j] = 1; // if yes change it's state to alive

                // Check if bacteria is alive and has less then 2 or more than 3 neighbours
                }else if(bufferTable[i][j] == 1 && (AliveNeighboursCount < 2 || AliveNeighboursCount > 3)){
                    newTable[i][j] = 0;

                // otherwise ( has 2 or 3 neighbours ) keep the state
                }else{
                    newTable[i][j] = bufferTable[i][j];
                }
            }
        }
        //print new generation
        for(int i = 0; i < newTable.length; i++ ) {
            for (int j = 0; j < newTable.length; j++) {
                System.out.print(newTable[j][i] + " ");
                inputTable[i][j] = newTable[i][j];
            }
            System.out.println();
        }
    }

    // Method to check 3x3 grid around bacteria with x & y location
    public static int checkSurroundingAliveBacteria(int[][]tableToCheck, int bacteriaX, int bacteriaY){
        int neighbours = 0;
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++) {

                // Checking if the grid 3x3 is withing grid boundaries
                if (bacteriaX + i > 0 && bacteriaY + j > 0 && bacteriaX + i < tableToCheck.length && bacteriaY + j < tableToCheck.length){
                    neighbours = neighbours + tableToCheck[bacteriaX + i][bacteriaY + j];
                }
            }
        }
        //subtract center bacteria from count
        neighbours = neighbours - tableToCheck[bacteriaX][bacteriaY];
        return neighbours;
    }
}



