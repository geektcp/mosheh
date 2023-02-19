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
package com.geektcp.common.core.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

class ThyString {

    private static String SEPARATOR = " ";

    private ThyString() {
    }

    public static void setStringSeparator(String separator){
        SEPARATOR = separator;
    }

    /**
     * @param src     source String
     * @param keyword the keyword
     * @return if contain keyword,return true,or false
     */
    public static boolean contains(String src, String keyword) {
        return src.contains(keyword);
    }

    public static boolean contains(String src, String... keywords) {
        return contains(false, false, src, keywords);
    }

    public static boolean contains(boolean orderly, String src, String... keywords) {
        return contains(orderly, false, src, keywords);
    }

    public static boolean contains(boolean orderly, boolean isIgnoreCase, String src, String... keywords) {
        if (Objects.isNull(src) || Objects.isNull(keywords)) {
            return false;
        }
        String[] wordArray = src.split(SEPARATOR);
        List<String> wordList = arrayToList(wordArray, isIgnoreCase);
        List<String> keywordList = arrayToList(keywords, isIgnoreCase);
        if (orderly) {
            return containWithOrder(wordList, keywordList);
        }
        return containWithoutOrder(wordList, keywordList);
    }

    public static List<String> arrayToList(String[] stringArray, boolean isIgnoreCase) {
        List<String> stringList = new ArrayList<>();
        if (Objects.isNull(stringArray)) {
            return stringList;
        }
        for (String str : stringArray) {
            if (Objects.isNull(str)) {
                continue;
            }
            String s = str.trim();
            if (isIgnoreCase) {
                s = s.toLowerCase();
            }
            stringList.add(s);
        }
        return stringList;
    }

    public static boolean containWithOrder(List<String> wordList, List<String> keywordList) {
        if (wordList.isEmpty() || keywordList.isEmpty()) {
            return false;
        }
        List<Boolean> resultList = new ArrayList<>();
        int keywordListSize = keywordList.size();
        for (String word : wordList) {
            if(keywordList.isEmpty()){
                break;
            }
            if (word.contains(keywordList.get(0))) {
                resultList.add(true);
                keywordList.remove(0);
            }
        }
        return resultList.size() == keywordListSize;
    }

    public static boolean containWithoutOrder(List<String> wordList, List<String> keywordList) {
        if (wordList.isEmpty() || keywordList.isEmpty()) {
            return false;
        }
        List<Boolean> resultList = new ArrayList<>();
        for (int j = 0; j < keywordList.size(); j++) {
            String keyword = keywordList.get(j);
            boolean isContain = false;
            for (int i = 0; i < wordList.size(); i++) {
                String word = wordList.get(i);
                if (word.contains(keyword)) {
                    isContain = true;
                    break;
                }
            }
            resultList.add(isContain);
        }
        for (Boolean result : resultList) {
            if (!result) {
                return false;
            }
        }
        return true;
    }


    public static boolean find(String src, String regex) {
        if (Objects.isNull(src) || Objects.isNull(regex)) {
            return false;
        }
        return matches(src, regex);
    }

    public static boolean matches(String src, String regex) {
        if (Objects.isNull(src) || Objects.isNull(regex)) {
            return false;
        }
        return Pattern.matches(regex, src);
    }

}
