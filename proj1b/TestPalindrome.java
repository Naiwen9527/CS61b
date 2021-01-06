import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
        assertFalse(palindrome.isPalindrome("aA"));
        assertFalse(palindrome.isPalindrome(null));

        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertTrue(palindrome.isPalindrome("acedb", cc));
        assertTrue(palindrome.isPalindrome("acdb", cc));
        assertFalse(palindrome.isPalindrome("aifk", cc));
        assertFalse(palindrome.isPalindrome("abcecba", cc));
        assertFalse(palindrome.isPalindrome("acetdb", cc));

        CharacterComparator cd = new OffByN(1);
        assertTrue(palindrome.isPalindrome("flake", cd));
        assertTrue(palindrome.isPalindrome("acedb", cd));
        assertTrue(palindrome.isPalindrome("acdb", cd));
        assertFalse(palindrome.isPalindrome("aifk", cd));
        assertFalse(palindrome.isPalindrome("abcecba", cd));
        assertFalse(palindrome.isPalindrome("acetdb", cd));
    }
}
