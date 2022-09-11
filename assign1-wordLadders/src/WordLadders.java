public class WordLadders {
    public static void main(String[] args) {

        LadderGame g = new LadderGame("dictionary.txt");

        // list words
        g.listWords(6, 10);

        // words one away from <word>
        for (String word : g.oneAway("slow", false)) {
            System.out.println(word);
        }
        System.out.println();

//        System.out.println("--- Word Ladders ---");
//        g.play("oops", "tots");
//        g.play("ride", "ands");
//        g.play("happily", "angrily");
//        g.play("slow", "fast");
//        g.play("stone", "money");
//        g.play("biff", "axis");
//        g.play("fungi", "among");
//        g.play("kiss", "woof");
    }
}
