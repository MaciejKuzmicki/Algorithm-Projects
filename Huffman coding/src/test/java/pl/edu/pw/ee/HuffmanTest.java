package pl.edu.pw.ee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class HuffmanTest {

    private Huffman huffman;

    @Before
    public void setUp() {
        huffman = new Huffman();
    }

    @Test(expected = NullPointerException.class)
    public void compressWhenFileIsNull() {
        //when
        huffman.huffman(null, true);
        //then
        assert false;
    }

    @Test(expected = NullPointerException.class)
    public void decompressWhenFileIsNull() {
        //when
        huffman.huffman(null, false);
        //then
        assert false;
    }

    @Test
    public void testLinkNodes() {
        //given
        Node first = new Node(2, 4);
        Node second = new Node(3, 5);
        //when
        Node output = huffman.linkNodes(first, second);
        //then
        int expectedValueOfNewNode = 5;
        int expectedValueOfLeftChild = 2;
        int expectedValueOfRightChild = 3;
        Assert.assertEquals(expectedValueOfNewNode, output.getValue());
        Assert.assertEquals(expectedValueOfLeftChild, output.getLeft().getValue());
        Assert.assertEquals(expectedValueOfRightChild, output.getRight().getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void compressWhenFileContainsValuesOutsideTheASCIITAble() {
        //when
        huffman.huffman("test3", true);
        //then
        assert false;
    }

    @Test
    public void compressAndDecompress() {
        //when
        int bitesAfterCompression = huffman.huffman("test1", true);
        int bytesAfterDecompression = huffman.huffman("test1", false);
        //then
        int bitesExpectedAfterCompression = 672;
        int bytesExpectedAfterDecompression = 171;
        Assert.assertEquals(bitesExpectedAfterCompression, bitesAfterCompression);
        Assert.assertEquals(bytesExpectedAfterDecompression, bytesAfterDecompression);
    }

    @Test
    public void compressAndDecompressLargeFile() {
        //when
        int bitesAfterCompression = huffman.huffman("test7", true);
        int bytesAfterDecompression = huffman.huffman("test7", false);
        //then
        int bitesExpectedAfterCompression = 450488;
        int bytesExpectedAfterDecompression = 91857;
        Assert.assertEquals(bitesExpectedAfterCompression, bitesAfterCompression);
        Assert.assertEquals(bytesExpectedAfterDecompression, bytesAfterDecompression);
    }

    @Test(expected = NullPointerException.class)
    public void decompressWithoutKey() {
        //when
        int bytesAfterDecompression = huffman.huffman("test4", false);
        //then
        assert false;
    }

    @Test
    public void onlyDecompress() {
        //when
        int bytesAfterDecompression = huffman.huffman("test2", false);
        //then
        int expected = 171;
        Assert.assertEquals(expected, bytesAfterDecompression);
    }

    @Test(expected = NoSuchElementException.class)
    public void testWhenKeyIsBroken() {
        //when
        int bytesAfterDecompression = huffman.huffman("test5", false);
        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenInDirectoryIsAnotherDirectory() {
        //when
        int bytesAfterDecompression = huffman.huffman("test6", false);
        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenCounterIsNull() {
        //when
        huffman.writeCounterToFile(null, null);
        //then
        assert false;
    }

}
