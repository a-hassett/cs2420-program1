public class LadderInfo {
    public String word;
    public int moves;
    public String ladder;

    public LadderInfo(String word, int moves, String ladder){
        this.word = word;
        this.moves = moves;
        this.ladder = ladder;
    }

    /** ACTS LIKE A DUNDER THAT PRINTS **/
    public String toString(){
        return "Word " + word + " Moves " + moves  + " Ladder [" + ladder + "]";
    }
}