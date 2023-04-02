package com.geektcp.common.mosheh.socket.text;



import com.geektcp.common.mosheh.socket.comparator.BinaryComparable;
import com.geektcp.common.mosheh.socket.comparator.WritableComparable;
import com.geektcp.common.mosheh.socket.util.WritableUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
/**
 * @author geektcp on 2018/2/22 1:14.
 */
public class Text extends BinaryComparable
        implements WritableComparable<BinaryComparable> {

    private static ThreadLocal<CharsetEncoder> ENCODER_FACTORY =
            new ThreadLocal<CharsetEncoder>() {
                protected CharsetEncoder initialValue() {
                    return Charset.forName("UTF-8").newEncoder().
                            onMalformedInput(CodingErrorAction.REPORT).
                            onUnmappableCharacter(CodingErrorAction.REPORT);
                }
            };

    private static ThreadLocal<CharsetDecoder> DECODER_FACTORY =
            new ThreadLocal<CharsetDecoder>() {
                protected CharsetDecoder initialValue() {
                    return Charset.forName("UTF-8").newDecoder().
                            onMalformedInput(CodingErrorAction.REPORT).
                            onUnmappableCharacter(CodingErrorAction.REPORT);
                }
            };

    private byte[] bytes;
    private int length;

    public static String readString(DataInput in) throws IOException {
        int length = WritableUtils.readVInt(in);
        byte[] bytes = new byte[length];
        in.readFully(bytes, 0, length);

        return decode(bytes);
    }

    public static int writeString(DataOutput out, String s) throws IOException {
        ByteBuffer bytes = encode(s);
        int length = bytes.limit();
        WritableUtils.writeVInt(out, length);
        out.write(bytes.array(), 0, length);

        return length;
    }

    public static String decode(byte[] utf8) throws CharacterCodingException {
        return decode(ByteBuffer.wrap(utf8), true);
    }

    private static String decode(ByteBuffer utf8, boolean replace)
            throws CharacterCodingException {
        CharsetDecoder decoder = DECODER_FACTORY.get();
        if (replace) {
            decoder.onMalformedInput(CodingErrorAction.REPLACE);
            decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        }
        String str = decoder.decode(utf8).toString();
        if (replace) {
            decoder.onMalformedInput(CodingErrorAction.REPORT);
            decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        }

        return str;
    }

    public static ByteBuffer encode(String string)
            throws CharacterCodingException {
        return encode(string, true);
    }

    public static ByteBuffer encode(String string, boolean replace)
            throws CharacterCodingException {
        CharsetEncoder encoder = ENCODER_FACTORY.get();
        if (replace) {
            encoder.onMalformedInput(CodingErrorAction.REPLACE);
            encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        }

        ByteBuffer bytes = encoder.encode(CharBuffer.wrap(string.toCharArray()));
        if (replace) {
            encoder.onMalformedInput(CodingErrorAction.REPORT);
            encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        }

        return bytes;
    }

    private void setCapacity(int len, boolean keepData) {
        if (bytes == null || bytes.length < len) {
            byte[] newBytes = new byte[len];
            if (bytes != null && keepData) {
                System.arraycopy(bytes, 0, newBytes, 0, length);
            }
            bytes = newBytes;
        }
    }

    public void write(DataOutput out) throws IOException {
        WritableUtils.writeVInt(out, length);
        out.write(bytes, 0, length);
    }

    public void readFields(DataInput in) throws IOException {
        int newLength = WritableUtils.readVInt(in);
        setCapacity(newLength, false);
        in.readFully(bytes, 0, newLength);
        length = newLength;
    }

    public int getLength() {
        return length;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
