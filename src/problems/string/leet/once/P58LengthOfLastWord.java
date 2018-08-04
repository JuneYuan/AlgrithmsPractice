package problems.string.leet.once;

import java.util.Arrays;
import org.junit.Test;

public class P58LengthOfLastWord {

    public int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0)   return 0;
        String[] words = s.split(" ");
        System.out.println(Arrays.toString(words));
        return words[words.length - 1].length();
    }

    @Test
    public void test() {
    	lengthOfLastWord("hello world");
    }
}
