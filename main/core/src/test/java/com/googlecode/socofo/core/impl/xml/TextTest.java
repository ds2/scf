package com.googlecode.socofo.core.impl.xml;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by deindesign on 03.02.16.
 */
public class TextTest {
    private Text to;

    @BeforeTest
    public void onMethod(){
        to=new Text();
    }

    @Test
    public void testEmptyInnerContent(){
        to.setInnerContent(" ");
        Assert.assertTrue(to.hasEmptyInnerContent());
    }

    @Test
    public void testEmptyInnerContent2(){
        to.setInnerContent(" \n");
        Assert.assertTrue(to.hasEmptyInnerContent());
    }

    @Test
    public void testEmptyInnerContent3(){
        to.setInnerContent("\n\t       \n\t");
        Assert.assertTrue(to.hasEmptyInnerContent());
    }
}
