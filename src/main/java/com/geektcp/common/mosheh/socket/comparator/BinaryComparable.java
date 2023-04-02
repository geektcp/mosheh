/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.geektcp.common.mosheh.socket.comparator;
/**
 * @author geektcp on 2018/2/22 1:14.
 */
public abstract class BinaryComparable implements Comparable<BinaryComparable> {

    public abstract int getLength();

    public abstract byte[] getBytes();

    public int compareTo(BinaryComparable other) {
        if (this == other) {
            return 0;
        }

        return WritableComparator.compareBytes(getBytes(), 0, getLength(),
                other.getBytes(), 0, other.getLength());
    }

    public int compareTo(byte[] other, int off, int len) {
        return WritableComparator.compareBytes(getBytes(), 0, getLength(),
                other, off, len);
    }

    public boolean equals(Object other) {
        if (!(other instanceof BinaryComparable)) {
            return false;
        }

        BinaryComparable that = (BinaryComparable) other;
        if (this.getLength() != that.getLength()) {
            return false;
        }

        return this.compareTo(that) == 0;
    }

    public int hashCode() {
        return WritableComparator.hashBytes(getBytes(), getLength());
    }
}
