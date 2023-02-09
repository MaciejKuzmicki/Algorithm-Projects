package pl.edu.pw.ee;

import org.junit.Assert;
import org.junit.Test;

public class LongestCommonSubsequenceTest {
    private LongestCommonSubsequence test;

    @Test
    public void test() {
        //given
        test = new LongestCommonSubsequence("KANAPKI", "NAPISY");
        //when
        String expected = "NAPI";
        Assert.assertEquals(expected, test.findLCS());
    }

    @Test(expected = NullPointerException.class)
    public void testWhenNull() {
        //given
        test = new LongestCommonSubsequence(null, null);
        //then
        assert false;
    }

    @Test
    public void theSameStrings() {
        //given
        test = new LongestCommonSubsequence("Kanapki", "Kanapki");
        //then
        String expected = "Kanapki";
        Assert.assertEquals(expected, test.findLCS());
    }

    @Test
    public void emptyStrings() {
        //given
        test = new LongestCommonSubsequence("", "");
        //then
        String expected = "";
        Assert.assertEquals(expected, test.findLCS());
    }

    @Test
    public void emptyString() {
        //given
        test = new LongestCommonSubsequence("Kanapki", "");
        //then
        String expected = "";
        Assert.assertEquals(expected, test.findLCS());
    }

    @Test
    public void newLineSign() {
        //given
        test = new LongestCommonSubsequence("często_z_odkrywaniem", "rzeczy_nie_trzeba\n_się_spieszyć");
        //then
        String expected = "cz__raie";
        Assert.assertEquals(expected, test.findLCS());
    }

    @Test
    public void tabSign() {
        //given
        test = new LongestCommonSubsequence("fdasg\tfd", "dsafg\tfdf");
        //then
        String expected = "dsg\tfd";
        Assert.assertEquals(expected, test.findLCS());
    }

    @Test(expected = IllegalStateException.class)
    public void displayBeforeFindingLCS() {
        //given
        test = new LongestCommonSubsequence("KANAPKI", "NAPISY");
        test.display();
        //then
        assert false;
    }

    @Test
    public void differentLettersInWords() {
        //given
        test = new LongestCommonSubsequence("KANAPKI", "ROWERY");
        //then
        String expected = "";
        Assert.assertEquals(expected, test.findLCS());
    }
    
    @Test
    public void wej() {
        test = new LongestCommonSubsequence("NOWY", "ROK");
        test.findLCS();
        test.display();
    }
}
