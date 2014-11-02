package by.gravity.client.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.gravity.client.utils.StringUtils;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty(null));
        assertFalse(StringUtils.isEmpty(" "));
        assertFalse(StringUtils.isEmpty("t"));

    }

    @Test
    public void testIsNumeric() throws Exception {

        assertTrue(StringUtils.isNumeric("123"));
        assertTrue(StringUtils.isNumeric("123.0"));
        assertTrue(StringUtils.isNumeric("0.123"));
        assertTrue(StringUtils.isNumeric("-123.0"));
        assertFalse(StringUtils.isNumeric("0.123f"));
        assertFalse(StringUtils.isNumeric("0.123e"));
    }
}