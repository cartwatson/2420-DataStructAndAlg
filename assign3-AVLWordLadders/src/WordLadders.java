public class WordLadders {
    public static void main(String[] args) {

        LadderGame g = new LadderGame("dictionary.txt");

        // list words
        g.listWords(2, 10);
        g.listWords(3, 10);
        g.listWords(4, 10);
        g.listWords(5, 10);
        g.listWords(6, 10);

        // words one away from <word>
        System.out.println("--- Words One Away from 'slow' ---"); // header text
        for (String word : g.oneAway("slow", false)) {
            System.out.println(word);
        }
        System.out.println();
        System.out.println("--- Words One Away from 'oops' ---"); // header text
        for (String word : g.oneAway("oops", false)) {
            System.out.println(word);
        }
        System.out.println();

        System.out.println("--- Word Ladders ---");
        g.play("oops", "tots");
        g.play("ride", "ands");
        g.play("happily", "angrily");
        g.play("slow", "fast");
        g.play("stone", "money");
        g.play("biff", "axis");
        g.play("fungi", "among");
        g.play("kiss", "woof");
    }
}
