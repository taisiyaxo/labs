package lab7.test;

import lab7.StreamClass;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class StreamTest {

    @Test
    public void testWriteBinary() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int[] intArray = {1, 3, 156, 338};
        StreamClass.writeBinary(baos, intArray);
        byte[] expected = {0,0,0,1, 0,0,0,3, 0,0,0,-100, 0,0,1,82};
        byte[] actual = baos.toByteArray();
        assertArrayEquals(actual, expected);
    }

    @Test
    public void testReadBinary() throws IOException {
        ByteArrayInputStream baos = new ByteArrayInputStream(new byte[]{0,0,0,1, 0,0,0,3, 0,0,0,-100, 0,0,1,82});
        int[] expected = {1, 3, 156, 338};
        int[] actual= StreamClass.readBinary(baos, 4);
        assertArrayEquals(actual, expected);

    }

    @Test
    public void testWriteChars() throws IOException {
        StringWriter baos = new StringWriter();

        String expectedStr = "1 23 -456 12 ";
        int[] expected = {1, 23, -456, 12};
        StreamClass.writeTextTxt(baos,expected);
        assertEquals(baos.toString(), expectedStr);

    }

    @Test
    public void testReadChars() throws IOException {
        StringReader baos = new StringReader("1 23 -456 12 ");
        int[] expected = {1, 23, -456, 12};
        int[] actual= StreamClass.readTextTxt(baos, 4);
        assertArrayEquals(actual, expected);

    }

    @Test
    public void testRandomAccessFile() throws IOException {
        File file=new File("file.txt");
        int[] actual = {1, 23, -456, 12};
        try(RandomAccessFile rfFile=new RandomAccessFile(file, "rw")){
            for (int j : actual) {
                rfFile.writeInt(j);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] expected={-456, 12};
        assertArrayEquals(expected, StreamClass.readFixPosition(file, 8));
    }
}

