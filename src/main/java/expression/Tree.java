package expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Tree<T> implements Iterable<Node<T>>{
    private static final int ROWS_ALLOWED = 10;

    private Node<T> parentNode;
    private List<Node<T>> nodeList = new ArrayList<>();


    public Tree (Node<T> parentNode) {
        this.parentNode = parentNode;
    }



    public Tree() {}

    public boolean isEmpty () {
        return parentNode == null;
    }

    public Node<T> getParentNode () {
        return parentNode;
    }

    @Override
    public String toString () {
        String string = parentNode.toString();
        if (string.split("\n").length > ROWS_ALLOWED) {
            List<String> split = Arrays.asList(string.split("\n"));

            string =
                       String.join("\n", split.subList(0, ROWS_ALLOWED / 2))
                    + "\n\t\t\t...\n"
                    + String.join("\n", split.subList(split.size() - ROWS_ALLOWED / 2, split.size()));
        }
        return string;
    }

    @Override
    public Iterator<Node<T>> iterator() {
        return new TreeIterator<>(this);
    }
}