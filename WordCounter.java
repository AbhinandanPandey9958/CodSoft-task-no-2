import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Word Counter!");
        
        String text = getInputText(scanner);
        if (text == null) {
            return; // Exit the program if input is invalid or empty
        }
        
        String[] words = text.split("\\s+|\\p{Punct}");
        
        int wordCount = 0;
        
        for (String word : words) {
            if (!word.isEmpty()) { // Ignore empty words
                wordCount++;
            }
        }
        
        System.out.println("Total number of words: " + wordCount);

        
        Set<String> stopWords = getStopWords();
        if (stopWords != null) {
            List<String> filteredWords = filterStopWords(words, stopWords);
            
            Map<String, Integer> wordFrequency = countWordFrequency(filteredWords);
            
            System.out.println("Number of unique words (excluding stop words): " + wordFrequency.size());
            
            System.out.println("Word frequency:");
            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
        
        scanner.close();
    }
    
    private static String getInputText(Scanner scanner) {
        System.out.print("Enter '1' to input text manually, or '2' to provide a file: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()
        
        String text = null;
        
        if (choice == 1) {
            System.out.print("Enter the text: ");
            text = scanner.nextLine();
        } else if (choice == 2) {
            System.out.print("Enter the file path: ");
            String filePath = scanner.nextLine();
            
            try {
                File file = new File(filePath);
                Scanner fileScanner = new Scanner(file);
                
                while (fileScanner.hasNextLine()) {
                    text += fileScanner.nextLine() + " ";
                }
                
                fileScanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please make sure the file path is correct.");
            }
        } else {
            System.out.println("Invalid input. Please try again.");
        }
        
        return text;
    }
    
    private static Set<String> getStopWords() {
        Set<String> stopWords = new HashSet<>();
        stopWords.add("the");
        stopWords.add("and");
        stopWords.add("is");
        stopWords.add("are");
        stopWords.add("of");
        return stopWords;
    }
    
    private static List<String> filterStopWords(String[] words, Set<String> stopWords) {
        List<String> filteredWords = new ArrayList<>();
        
        for (String word : words) {
            if (!word.isEmpty() && !stopWords.contains(word.toLowerCase())) {
                filteredWords.add(word);
            }
        }
        
        return filteredWords;
    }
    
    private static Map<String, Integer> countWordFrequency(List<String> words) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        
        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
        
        return wordFrequency;
    }
}
