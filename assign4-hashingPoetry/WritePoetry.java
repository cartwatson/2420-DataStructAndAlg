import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;


public class WritePoetry {
    public String writePoem(String file, String startWord, int length, boolean printHashtable)
    {
        // Get setup to write poem
        // init hashtable
        HashTable<String, WordFreqInfo> hashTable = new HashTable<>();
        // read in file word by word
        // ----- w3schools code ----- https://www.w3schools.com/java/java_files_read.asp
        try {
            Scanner myReader = new Scanner(new File(file));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine(); // get lines
                if (Objects.equals(data, "")) {
                    continue;
                }
                // --- my code ---
                // parse into words
                String[] words = data.split("((?=[.,;:'?!@#$%^&*()_/+=]|\")|(?<=[.,;:'?!@#$%^&*()_/+=]|\"))| ");
                // make lowercase
                for (int j = 0; j < words.length; j++) {
                    words[j] = words[j].toLowerCase();
                    words[j] = words[j].replaceAll("\\s", "");
                }
                // add to hash table
                for (int i = 0; i < words.length; i++) {
                    // if word hasn't already been found
                    if (hashTable.find(words[i]) == null) {
                        hashTable.insert(words[i], new WordFreqInfo(words[i], 0)); // create new wordFreqInfo and add it to hash table
                    }
                    // if not at end updateFollows
                    if (i != words.length - 1) {
                        hashTable.find(words[i]).updateFollows(words[i + 1]);
                    }
                }
                // --- end of my code ---
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        // ----- end of w3schools code -----

        // init random
        Random rand = new Random();

        // --- write poem ---
        String poem = "";
        // generate poem of length
        String temp = startWord;
        for (int i = 0; i < (length - 1); i++) {
            poem += temp;
            WordFreqInfo wordFreqInfoTemp = hashTable.find(temp);
            Integer count = rand.nextInt(wordFreqInfoTemp.getOccurCount());
            temp = wordFreqInfoTemp.getFollowWord(count);
            // TODO: change to new only after a period
            // TODO: change to no space before punctuation

            if (temp.matches("((?=[.,;:'?!@#$%^&*()_/+=]|\")|(?<=[.,;:'?!@#$%^&*()_/+=]|\"))")) {
                poem += "\n";
            }
            else
            {
                poem += " ";
            }
        }
        poem += ".";

        if (printHashtable)
        {
            System.out.println(poem);
        }

        return poem;
    }
}
