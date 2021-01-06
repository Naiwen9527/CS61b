

public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque wordList = new LinkedListDeque();
        for (char i : word.toCharArray()) {
            wordList.addLast(i);
        }
        return wordList;
    }

    public boolean isPalindrome(String word) {

        if (word == null) {
            return false;
        }

        Deque wordList = wordToDeque(word);

        if (wordList.size() <= 1) {
            return true;
        }

        while (wordList.size() > 1) {
            if (wordList.removeFirst() != wordList.removeLast()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {

        if (word == null) {
            return false;
        }

        Deque wordList = wordToDeque(word);

        if (wordList.size() <= 1) {
            return true;
        }

        while (wordList.size() > 1) {

            if (wordList.size() <= 1) {
                return true;
            }
            String x = wordList.removeFirst().toString();
            String y = wordList.removeLast().toString();

            if (!cc.equalChars(x.charAt(0), y.charAt(0))) {
                return false;
            }

        }
        return true;
    }

    private static void main(String[] args) {
        Palindrome p = new Palindrome();
        //Deque shit = p.wordToDeque("racecar");
        CharacterComparator cc = new OffByOne();
        p.isPalindrome("acegedb", cc);
    }

}
