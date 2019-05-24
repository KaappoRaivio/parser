package expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeIterator<T> implements Iterator<Node<T>> {
    private List<Node<T>> nodeList = new ArrayList<>();
    private Tree<T> tree;
    private int currentIndex = 0;

    public TreeIterator(Tree<T> tree) {
        this.tree = tree;
        dfs(tree.getParentNode());
    }

    private void dfs (Node<T> current) {
        for (var child : current.getChildren()) {
            dfs(child);
        }

        nodeList.add(current);
    }


    @Override
    public boolean hasNext() {
        return currentIndex < nodeList.size();
    }

    @Override
    public Node<T> next() {
        var temp = nodeList.get(currentIndex);
        currentIndex += 1;
        return temp;
    }
}

