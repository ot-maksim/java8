package org.example.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameHelper {
    private static final String ALPHABET = "abcdefg";
    private final int gridSize = 49;
    private final int gridLength = 7;
    private final int[] grid = new int[gridSize];
    private int comCount = 0;

    public String getUserInput(String prompt) {
        String inputLine = "";
        System.out.print(prompt.concat(": "));
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0) return "";
        } catch (IOException ex) {
            System.out.printf("Exception %s", ex);
        }
        return inputLine.toLowerCase();
    }

    public ArrayList<String> placeDotCom(int dotComSize) {
        int[] dotComCoordinates = new int[dotComSize];
        placeDotComInGrid(dotComSize, dotComCoordinates);
        return getDotComCoordinatesInGrid(dotComSize, dotComCoordinates);
    }

    private ArrayList<String> getDotComCoordinatesInGrid(int comSize, int[] dotComCoordinates) {
        int x = 0;

        ArrayList<String> alphaCells = new ArrayList<>();
        while (x < comSize) {
            grid[dotComCoordinates[x]] = 1;
            int row = (int) (dotComCoordinates[x] / gridLength);
            int column = dotComCoordinates[x] % gridLength;
            String columnTmp = String.valueOf(ALPHABET.charAt(column));
            alphaCells.add(columnTmp.concat(Integer.toString(row)));
            System.out.printf("coordinate is %s\n", alphaCells.get(x));
            x++;
        }
        return alphaCells;
    }

    private void placeDotComInGrid(int comSize, int[] dotComCoordinates) {
        int increment = getIncrement();
        int attempts = 0;
        int location;
        boolean success = false;
        while (!success & attempts++ < 200) {
            location = (int) (Math.random() * gridSize);
            System.out.printf("try location %s in grid\n", location);
            int x = 0;
            success = true;
            while (success && x < comSize) {
                if (grid[location] == 0) {
                    dotComCoordinates[x++] = location;
                    location += increment;
                    success = isLocationOutOfBoundaries(location, x);
                    if (!success) {
                        System.out.printf("location %s is out of grid boundaries\n", location);
                    } else {
                        System.out.printf("current location %s is\n", location);
                    }
                } else {
                    System.out.printf("location %s is already used in grid\n", location);
                    success = false;
                }
            }
        }
    }

    private boolean isLocationOutOfBoundaries(int location, int x) {
        return location <= gridSize && (x <= 0 || (location % gridLength != 0));
    }

    private int getIncrement() {
        comCount++;
        int incr = 1;
        if ((comCount % 2) == 1) {
            incr = gridLength;
        }
        return incr;
    }
}
