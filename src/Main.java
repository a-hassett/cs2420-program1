import java.util.Objects;

public class Main {
    /** RUN THIS METHOD TO ACTIVATE THE PROGRAM **/
    public static void main (String[] args){
        System.out.println("Welcome to Word Ladders!");

        // BUILD DICTIONARY AND GAME
        LadderGame game = new LadderGame("dictionary.txt");

        // BUILD BINARY TREE OF ONE COLUMN OF WORDSLIST
        BinarySearchTree<String> dictTree = new BinarySearchTree<>();
        java.util.Collections.shuffle(game.wordsList[game.getColumn()], new java.util.Random(System.currentTimeMillis()));
        for(String word : game.wordsList[game.getColumn()]){
            dictTree.insert(word);
        }

        // Check if our words are in the dictionary
        if(!(dictTree.search(game.getStartWord()) && dictTree.search(game.getEndWord()))) {
            System.out.println("Invalid input: one of more of your words does not exist in the dictionary");
            System.exit(1);
        }

        // BUILD LADDERS
        LinkedList<LadderInfo> partials = new LinkedList<>();
        findLadder(dictTree, partials, game.getStartWord(), game.getEndWord());
    }

    /** FIND A VALID WORD LADDER BETWEEN TWO WORDS OF EQUAL LENGTH **/
    private static void findLadder(BinarySearchTree<String> dictionary, LinkedList<LadderInfo> partials, String startWord, String endWord){
        int numEnqueues = 0;

        // special case
        if(Objects.equals(startWord, endWord)){
            LadderInfo ladder = new LadderInfo(startWord, 0, startWord);
            System.out.println(ladder);
            return;
        }

        dictionary.remove(startWord);
        partials.enqueue(new LadderInfo(startWord, 0, startWord));
        numEnqueues++;

        boolean done = false;

        while((partials.size() > 0) && (!done)){
            LadderInfo info = partials.dequeue();

            char[] word = info.word.toCharArray();  // holds original word for reference
            char[] dynamicWord = info.word.toCharArray();  // will change in the for loops below

            // CHECK FOR EVERY POSSIBLE WORD PATH
            for(int i = 0; i < dynamicWord.length; i++){  // change each letter of word one at a time
                for(int j = (int)'a'; j <= (int)'z'; j++){  // go through entire alphabet
                    // split and recombine one-letter-off words
                    dynamicWord[i] = (char)j;
                    String checkWord = String.valueOf(dynamicWord);

                    if(dictionary.search(checkWord)){
                        dictionary.remove(checkWord);

                        // ADD TO QUEUE IF VALID WORD
                        LadderInfo newLadder = new LadderInfo(checkWord, info.moves + 1, info.ladder + " " + checkWord);
                        partials.enqueue(newLadder);
                        numEnqueues++;

                        // IF FOUND, PRINT TO SCREEN
                        if(checkWord.equals(endWord)){
                            System.out.println(newLadder);
                            System.out.println("Total enqueues: " + numEnqueues + "\n");
                            return;
                        }
                    }
                }
                dynamicWord[i] = word[i];
            }
        }
        // IF NOT FOUND, PROVE REASONABLE ATTEMPT
        System.out.println("Could not find a word ladder :(");
        System.out.println("Total enqueues: " + numEnqueues);
    }
}
