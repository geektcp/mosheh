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

package com.geektcp.common.mosheh.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author geektcp on 2019/9/28.
 */
public class ListStack<Item> implements ThyStack<Item> {

    private Node top = null;
    private int N = 0;

    private class Node {
        Item item;
        Node next;
    }

    @Override
    public ThyStack<Item> push(Item item) {
        Node newTop = new Node();
        newTop.item = item;
        newTop.next = top;
        top = newTop;
        N++;

        return this;
    }

    @Override
    public Item pop() throws Exception {
        if (isEmpty()) {
            throw new Exception("stack is empty");
        }
        Item item = top.item;
        top = top.next;
        N--;

        return item;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node cur = top;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item item = cur.item;
                cur = cur.next;
                return item;
            }
        };

    }
}
