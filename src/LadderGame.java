import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class LadderGame {
    static int maxWordSize = 15;
    ArrayList<String>[] wordsList = new ArrayList[maxWordSize];
    private String startWord;
    private String endWord;
    private int column;

    /** SET UP START AND END WORDS **/
    public LadderGame(String dictionaryFileName) {
        for (int i = 0; i < maxWordSize; i++) {
            wordsList[i] = new ArrayList<>();
        }
        try {
            Scanner reader = new Scanner(new File(dictionaryFileName));
            while (reader.hasNext()) {
                String word = reader.next().toLowerCase();
                if (word.length() < maxWordSize) {
                    wordsList[word.length()].add(word);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        Scanner input = new Scanner(System.in);
        System.out.println("\nWould you like to provide words or have them randomly selected for you?");
        System.out.print("Enter (1) for provided or (2) for random: ");

        int inputType = input.nextInt();

        if (inputType == 1) {
            // GET WORDS FROM USER
            Scanner grabber = new Scanner(System.in);
            System.out.print("First word: ");
            startWord = grabber.nextLine().toLowerCase();
            System.out.print("Second word: ");
            endWord = grabber.nextLine().toLowerCase();

            // check if they are valid
            if ((startWord.length() <= 0) || (startWord.length() > maxWordSize)) {
                System.out.println("Invalid input: words must be between 1 and 15 letters");
                System.exit(1);
            } else if (startWord.length() == endWord.length()) {
                column = startWord.length();
                System.out.println("\nYou're building a ladder from " + startWord + " to " + endWord + "\n");
            } else {
                System.out.println("Invalid input: words are not same length");
                System.exit(1);
            }

        } else if (inputType == 2) {
            // PICK RANDOM WORDS
            int colLength = 0;

            // if the column is empty, pick a new column
            while(colLength <= 0){
                column = (int) (Math.random() * maxWordSize);
                colLength = wordsList[column].size();
            }

            // if the random words are the same, pick new ones
            do {
                startWord = wordsList[column].get((int) (Math.random() * colLength));
                endWord = wordsList[column].get((int) (Math.random() * colLength));
            } while (startWord == endWord);

            System.out.println("\nYou're building a ladder from " + startWord + " to " + endWord + "\n");

        } else {
            System.out.println("Invalid type: did not enter (1) or (2)");
            System.exit(1);
        }
    }

    public String getStartWord(){
        return startWord;
    }
    public String getEndWord(){
        return endWord;
    }
    public int getColumn(){
        return column;
    }
}

