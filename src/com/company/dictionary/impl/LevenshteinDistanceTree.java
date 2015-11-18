package com.company.dictionary.impl;

import com.company.dictionary.Dictionary;
import com.company.dictionary.DictionaryTree;
import com.company.dictionary.Word;
import java.util.*;

/**
 * BK-Tree implementation
 * using Levenshtein distance
 */
public class LevenshteinDistanceTree
        implements DictionaryTree
{
    private TreeNode root;
    private LinkedList<Word> lastSearchSimilarWords;
    private static final int ERROR_MAX_COUNT = 2;

    public LevenshteinDistanceTree() {
        lastSearchSimilarWords = new LinkedList<>();
    }

    @Override
    public void build(Dictionary dictionary) {
        clear();
        for (Iterator<Word> iter = dictionary.iterator(); iter.hasNext();) {
            addWord(iter.next());
        }
    }

    @Override
    public void addWord(Word word) {
        if (root == null) {
            root = new TreeNode(word.toString());
        } else {
            addWordInternal(word.toString(), root);
        }
    }

    @Override
    public boolean search(Word word) {
        lastSearchSimilarWords.clear();
        return searchInternal(word.toString(), root);
    }

    private void addWordInternal(String wordString, TreeNode node) {
        int levenshteinDistance = calcLevenshteinDistance(wordString, node.getValue());
        if (levenshteinDistance == 0) return;
        TreeNode childNode = node.getChild(levenshteinDistance);
        if (childNode != null) {
            addWordInternal(wordString, childNode);
        } else {
            node.addChild(levenshteinDistance, new TreeNode(wordString));
        }
    }

    private boolean searchInternal(String wordString, TreeNode node) {
        int levenshteinDistance = calcLevenshteinDistance(wordString, node.getValue());
        if (levenshteinDistance <= ERROR_MAX_COUNT) {
            lastSearchSimilarWords.addFirst(new WordImpl(node.getValue()));
        }
        if (levenshteinDistance == 0) return true;
        int downIndex = Integer.max(1, levenshteinDistance - ERROR_MAX_COUNT);
        int upIndex = levenshteinDistance + ERROR_MAX_COUNT;
        List<Boolean> recursiveCallResults = new LinkedList<>();
        for (int index = downIndex; index <= upIndex; ++index) {
            TreeNode childNode = node.getChild(index);
            if (childNode != null) {
                recursiveCallResults.add(searchInternal(wordString, node.getChild(index)));
            }
        }
        for (Boolean result : recursiveCallResults) {
            if (result.equals(Boolean.TRUE)) return true;
        }
        return false;
    }

    // returns number of add/edit/delete operations required for equality.
    private int calcLevenshteinDistance(String first, String second) {
        int firstLength = first.length();
        int secondLength = second.length();
        int maxLength = Integer.min(firstLength, secondLength);
        int editOperations = 0;
        for (int index = 0; index < maxLength; ++index) {
            Character ch1 = Character.toLowerCase(first.charAt(index));
            Character ch2 = Character.toLowerCase(second.charAt(index));
            if (! ch1.equals(ch2)) ++editOperations;
        }
        // edit actions number + add/delete actions number
        return (editOperations + Math.abs(firstLength - secondLength));
    }

    @Override
    public List<Word> getSimilarWords(int limit) {
        LinkedList<Word> similarWords = new LinkedList<>();
        int count = 0;
        for (Iterator<Word> iter = lastSearchSimilarWords.iterator(); iter.hasNext() && count++ < limit;) {
            similarWords.add(iter.next());
        }
        return similarWords;
    }

    @Override
    public void clear() {
        root = null;
    }

    static final class TreeNode
    {
        private String word;
        private HashMap<Integer, TreeNode> childs; // Levenshtein distance as index

        TreeNode(String word) {
            this(word, new HashMap<>());
        }

        TreeNode(String word, HashMap<Integer, TreeNode> childs) {
            this.word = word;
            this.childs = childs;
        }

        public String getValue() {
            return word;
        }

        public boolean hasChilds() {
            return childs.size() > 0;
        }

        public TreeNode getChild(int levenshteinDistance) {
            return childs.get(levenshteinDistance);
        }

        public HashMap<Integer, TreeNode> getChilds() {
            return childs;
        }

        public void addChild(int levenshteinDistance, TreeNode node) {
            childs.put(levenshteinDistance, node);
        }
    }
}
