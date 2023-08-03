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

/**
 * @author geektcp on 2019/9/28.
 */
public class ListQueue<Item> implements ThyQueue<Item> {

    private Node first;
    private Node last;
    int N = 0;

    private class Node {
        Item item;
        Node next;
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
    public ThyQueue<Item> add(Item item) {
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        if (isEmpty()) {
            last = newNode;
            first = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        N++;
        return this;
    }

    @Override
    public Item remove() throws Exception {
        if (isEmpty()) {
            throw new Exception("queue is empty");
        }
        Node node = first;
        first = first.next;
        N--;
        if (isEmpty()) {
            last = null;
        }

        return node.item;
    }


    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Node cur = first;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public Item next() {
                Item item = cur.item;
                cur = cur.next;
                return item;
            }
        };
    }
}
