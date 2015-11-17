package com.company.dictionary.impl;

import com.company.dictionary.*;
import com.company.dictionary.Dictionary;
import java.util.*;

public class DictionaryTreeImpl
        implements DictionaryTree
{
    private TreeNode head;
    private ArrayList<Word> index;
    private LinkedHashMap<Integer, Integer> similarMap;
    private Integer dictionarySize;

    /**
     * точность подбора похожих слов, сколько ошибок подряд допустимо.
     * т.е. насколько глубоко нужно делать обход всех нижних уровней дерева словаря после найденной ошибки
     *
     * выше значение – дольше работа, точнее результат,
     * глубже проходим – более полно заполняем массив совпадений символов ( similarMap )
     */
    private static final int MISTAKES_IN_SEQUENCE_AVAILABLE = 3;

    public DictionaryTreeImpl() {
        head = new TreeNode();
        index = new ArrayList<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void build(Dictionary dictionary) {
        clear();
        dictionarySize = dictionary.size();
        for (Iterator<Word> iter = dictionary.iterator(); iter.hasNext();) {
            addWord(iter.next());
        }
    }

    @Override
    public void addWord(Word word) {
        index.add(word);
        String wordString = word.toString();
        int currentIndex = index.size() - 1;
        addWordInternal(wordString, 0, head, currentIndex);
    }

    @Override
    public boolean search(Word word) {
        similarMap = new LinkedHashMap<>(dictionarySize, 0.75f, true);
        String wordString = word.toString();
        return searchInternal(wordString, 0, head, MISTAKES_IN_SEQUENCE_AVAILABLE);
    }

    @Override
    public ArrayList<Word> getSimilarWords(int limit, String queryWord) {
        ArrayList<Word> similarWords = new ArrayList<>();
        List<Map.Entry<Integer, Integer>> similarWordsList = new LinkedList<>();
        int queryWordLength = queryWord.length();
        for (Iterator<Map.Entry<Integer, Integer>> iter = similarMap.entrySet().iterator(); iter.hasNext();) {
            Map.Entry<Integer, Integer> entry = iter.next();
            String similarityWord = index.get(entry.getKey()).toString();
            int similarityWordLength = similarityWord.length();
            int equalsSymCount = entry.getValue();
            int similarityValue = (int) Math.floor((equalsSymCount * 100) / Integer.max(queryWordLength, similarityWordLength));
            similarWordsList.add(new AbstractMap.SimpleEntry<>(entry.getKey(), similarityValue));
        }
        Collections.sort(similarWordsList, (Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2)
                -> o2.getValue().compareTo(o1.getValue()));
        int count = 0;
        for (Iterator<Map.Entry<Integer, Integer>> iter = similarWordsList.iterator(); ++count <= limit && iter.hasNext();) {
            Map.Entry<Integer, Integer> entry = iter.next();
            similarWords.add(new WordImpl(index.get(entry.getKey()).toString()));
        }
        return similarWords;
    }

    private void addWordInternal(String wordString, int charIndex, TreeNode root, int belongsToIndex) {
        HashMap<Character, TreeNode> nodes = root.childNodes;
        int nextCharIndex = charIndex + 1;
        Character addCharacter = Character.toLowerCase(wordString.charAt(charIndex));
        TreeNode node = nodes.get(addCharacter);
        if (node != null) {
            node.belongsTo.add(belongsToIndex);
            if (nextCharIndex < wordString.length() && node.hasChildNodes()) {
                addWordInternal(wordString, nextCharIndex, node, belongsToIndex);
                return;
            }
        }
        boolean isWordLastCharacter = (nextCharIndex == wordString.length());
        TreeNode newNode = new TreeNode(
            new HashMap<>(),
            new ArrayList<>(Collections.singletonList(belongsToIndex)),
            isWordLastCharacter
        );
        root.addChildNode(addCharacter, newNode);
        if (! isWordLastCharacter) {
            addWordInternal(wordString, nextCharIndex, newNode, belongsToIndex);
        }
    }

    private boolean searchInternal(String wordString, int charIndex, TreeNode root, int mistakesAvailable) {
        HashMap<Character, TreeNode> nodes = root.childNodes;
        int nextCharIndex = charIndex + 1;
        Character searchCharacter = Character.toLowerCase(wordString.charAt(charIndex));
        TreeNode node = nodes.get(searchCharacter);
        if (node != null) {
            ensureSimilarity(node.belongsTo);
            if (nextCharIndex == wordString.length()) {
                return node.isWordLastCharacter;
            } else if (nextCharIndex < wordString.length()) {
                return node.hasChildNodes() && searchInternal(wordString, nextCharIndex, node, MISTAKES_IN_SEQUENCE_AVAILABLE);
            }
        }
        // Точное вхождение не найдено, ищем похожие слова с учетом 'mistakesAvailable' ошибок.
        if (mistakesAvailable > 0 && nextCharIndex < wordString.length() && !nodes.isEmpty()) {
            for (Iterator<TreeNode> iter = nodes.values().iterator(); iter.hasNext();) {
                searchInternal(wordString, nextCharIndex, iter.next(), mistakesAvailable - 1);
            }
        }
        return false;
    }

    private void ensureSimilarity(ArrayList<Integer> indexArray) {
        for (Iterator<Integer> iter = indexArray.iterator(); iter.hasNext();) {
            Integer belongsToIndex = iter.next();
            similarMap.put(belongsToIndex, similarMap.getOrDefault(belongsToIndex, 0) + 1);
        }
    }

    @Override
    public void clear() {
        head = new TreeNode();
        index = new ArrayList<>();
    }

    static final class TreeNode {
        HashMap<Character, TreeNode> childNodes;
        ArrayList<Integer> belongsTo;
        boolean isWordLastCharacter;

        TreeNode() {
            this(new HashMap<>(), new ArrayList<>(), false);
        }

        TreeNode(HashMap<Character, TreeNode> childNodes, ArrayList<Integer> belongsTo, boolean isWordLastCharacter) {
            this.childNodes = childNodes;
            this.belongsTo = belongsTo;
            this.isWordLastCharacter = isWordLastCharacter;
        }

        public void addChildNode(Character addCharacter, TreeNode node) {
            childNodes.put(addCharacter, node);
        }

        public boolean hasChildNodes() {
            return childNodes.size() > 0;
        }
    }
}
