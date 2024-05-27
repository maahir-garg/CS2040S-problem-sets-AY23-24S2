import java.util.ArrayList;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';

    private class TrieNode {
        // TODO: Create your TrieNode class here.
        TrieNode[] presentChars = new TrieNode[62];
        boolean flag = false;

        public TrieNode() {
            for (int i = 0; i < 62; i++) {
                presentChars[i] = null;
            }
        }

    }

    private TrieNode root;

    public Trie() {
        // TODO: Initialise a trie class here.
        root = new TrieNode();
    }

    int indexOf(char c) {
        int ASCII = (int) c;
        if (ASCII>=48 && ASCII <= 57) {
            return ASCII - 48;
        } else if (ASCII >= 65 && ASCII <= 90) {
            return ASCII - 55;
        } else if (ASCII >= 97 && ASCII <= 122){
            return ASCII - 61;
        } else {
            return -1;
        }
    }


    /**
     * Inserts string s into the Trie.
     *
     * @param s string to insert into the Trie
     */
    void insert(String s) {
        TrieNode curnode = root;
        for (int i = 0; i < s.length(); i++) {
            char letter = s.charAt(i);
            int index = indexOf(letter);
            if (curnode.presentChars[index] == null) {
                curnode.presentChars[index] = new TrieNode();
            }
            curnode = curnode.presentChars[index];
        }
        curnode.flag = true;
    }


    /**
     * Checks whether string s exists inside the Trie or not.
     *
     * @param s string to check for
     * @return whether string s is inside the Trie
     */
    boolean contains(String s) {
        TrieNode curnode = root;
        if (curnode == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            char letter = s.charAt(i);
            int index = indexOf(letter);
            if (index==-1 || curnode.presentChars[index] == null) {
                return false;
            }
            curnode = curnode.presentChars[index];
        }
        return curnode.flag;
    }

//    char[] options(TrieNode node) {
//        char[] available = new char[];
//        int j = 0;
//        for (int i = 0; i < 62; i++) {
//            if (node.presentChars[i] != null) {
//                available[j++] = toChar(i);
//            }
//        }
//        return available;
//    }

    char toChar(int index) {
        if (index <= 9) {
            return (char) (index + 47);
        } else if (index <= 35) {
            return (char) (index + 55);
        } else {
            return (char) (index + 61);
        }
    }

    /**
     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the
     * results into the specified ArrayList. Only returns at most the first limit results.
     *
     * @param s       pattern to match prefixes with
     * @param results array to add the results into
     * @param limit   max number of strings to add into results
     */
//    void prefixSearch(String s, ArrayList<String> results, int limit) {
//        TrieNode curnode = root;
//        int strLen  = s.length();
//        StringBuilder curword = new StringBuilder();
//        for (int i = 0; i < strLen; i++) {
//
//            if (s.charAt(i) == '.') {
//                for (char option: options(curnode)) {
//                    StringBuilder new_options = new StringBuilder(curword).append(option);
//                }
//
//            }
//        }
//
//        // TODO
//    }
    public void prefixSearch(String s, ArrayList<String> results, int limit) {
        performSearch(root, new StringBuilder(), s, results, limit);
    }

    private void performSearch(TrieNode node, StringBuilder currentWord, String s, ArrayList<String> results, int limit) {
        if (results.size() >= limit) {
            return;
        }

        if (s.isEmpty()) {
            if (node.flag) {
                results.add(currentWord.toString());
            }

            for (int i = 0; i < node.presentChars.length; i++) {
                if (node.presentChars[i] != null) {
                    char ch = toChar(i);
                    performSearch(node.presentChars[i], currentWord.append(ch), s, results, limit);
                    currentWord.deleteCharAt(currentWord.length() - 1);
                }
            }
        } else {
            char ch = s.charAt(0);
            int index = ch == '.' ? -1 : indexOf(ch);

            if (index == -1) {
                for (int i = 0; i < node.presentChars.length; i++) {
                    if (node.presentChars[i] != null) {
                        char childCh = toChar(i);
                        performSearch(node.presentChars[i], currentWord.append(childCh), s.substring(1), results, limit);
                        currentWord.deleteCharAt(currentWord.length() - 1);
                    }
                }
            } else {
                if (node.presentChars[index] != null) {
                    performSearch(node.presentChars[index], currentWord.append(ch), s.substring(1), results, limit);
                    currentWord.deleteCharAt(currentWord.length() - 1);
                }
            }
        }
    }


    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");
        t.insert("aaa");


        String[] result1 = t.prefixSearch("pe", 10);
        String[] result2 = t.prefixSearch("pe.", 10);

        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
