package puupaska;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Tree<T> implements Iterable<Node<T>>{
    private Node<T> parentNode;
    private List<Node<T>> nodeList = new ArrayList<>();


    public Tree (Node<T> parentNode) {
        this.parentNode = parentNode;
    }



    public Tree() {}

    public boolean isEmpty () {
        return parentNode == null;
    }

    Node<T> getParentNode () {
        return parentNode;
    }

    @Override
    public String toString () {
        return parentNode.toString();
    }

    @Override
    public Iterator<Node<T>> iterator() {
        return new TreeIterator<>(this);
    }
}