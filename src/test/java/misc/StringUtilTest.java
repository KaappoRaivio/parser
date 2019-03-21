package misc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void getCurrentCommaPlace() {
        assertEquals(StringUtil.getCurrentCommaPlace("12.32"), 2);
        assertEquals(StringUtil.getCurrentCommaPlace("1232"), 4);
    }

    @Test
    void findPattern() {
        assertEquals(StringUtil.findPattern("1.23123"), new Pair<String, String>("", "123"));
        assertEquals(StringUtil.findPattern("53.3"), new Pair<String, String>("5", "3"));
    }

    @Test
    void moveComma() {
        assertEquals(StringUtil.moveComma("53.3", 2), "5330");
        assertEquals(StringUtil.moveComma("53.3", -1), "5.33");
        assertEquals(StringUtil.moveComma("53.3", -2), "0.533");
        assertEquals(StringUtil.moveComma("99.9", 1), "999");
    }
}