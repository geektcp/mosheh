package com.geektcp.common.mosheh.socket.text;

import com.geektcp.common.mosheh.socket.comparator.Writable;
import com.geektcp.common.mosheh.system.Sys;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
/**
 * @author geektcp on 2018/2/22 1:14.
 */
public class ConnectionHeader implements Writable {

    private String protocol;

    public ConnectionHeader() {

    }

    public ConnectionHeader(String protocol) {
        this.protocol = protocol;
    }

    public void readFields(DataInput in) throws IOException {
        protocol = Text.readString(in);
        if (protocol.isEmpty()) {
            protocol = null;
        } else {
            Sys.p("The protocol is: {}", protocol);
        }
    }

    public void write(DataOutput out) throws IOException {
        Text.writeString(out, (protocol == null) ? "" : protocol);
    }
}
