package structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class AVLtree<T> {

	private Node<T> root;
	private Comparator<T> comparator;
	private ArrayList<T> listTree;

	public AVLtree(Comparator<T> comparator) {
		root = null;
		this.comparator = comparator;
	}

	public void insert(T data) {
		root = insert(data, root);
	}

	private Node<T> insert(T data, Node<T> node) {
		if (node == null) {
			node = new Node<T>(data);
		} else if (comparator.compare(data, node.getData()) < 0) {
			node.setLeft(insert(data, node.getLeft()));
			if (height(node.getLeft()) - height(node.getRight()) == 2) {
				if (comparator.compare(data, node.getData()) < 0) {
					node = rotateLeft(node);
				} else {
					node.setLeft(rotateRight(node.getLeft()));
					node = rotateLeft(node);
				}
			}
		} else if (comparator.compare(data, node.getData()) > 0) {
			node.setRight(insert(data, node.getRight()));
			if (height(node.getRight()) - height(node.getLeft()) == 2) {
				if (comparator.compare(data, node.getRight().getData()) > 0) {
					node = rotateRight(node);
				} else {
					node.setRight(rotateLeft(node.getRight()));
					node = rotateRight(node);
				}
			}
		} else
			;
		node.setHeight(max(height(node.getLeft()), height(node.getRight())) + 1);
		return node;
	}

	public void delete(T data) {
		delete(data, root);
	}

	private Node<T> delete(T data, Node<T> nodo) {
		if (comparator.compare(data, nodo.getData()) == 0) {
			root = null;
		} else if (comparator.compare(data, nodo.getData()) < 0) {
			nodo.setLeft(delete(data, nodo.getLeft()));
		} else if (comparator.compare(data, nodo.getData()) > 0) {
			nodo.setRight(delete(data, nodo.getRight()));
		} else if (nodo.getLeft() != null && nodo.getRight() != null) {
			nodo.setData(encontrarMin(nodo.getRight()).getData());
			nodo.setRight(delete(nodo.getData(), nodo.getRight()));
		} else
			nodo = (nodo.getLeft() != null) ? nodo.getLeft() : nodo.getRight();
		return nodo;
	}

	private Node<T> encontrarMin(Node<T> nodo) {
		if (nodo == null)
			return null;
		else if (nodo.getLeft() == null)
			return nodo;
		return encontrarMin(nodo.getLeft());
	}

	private Node<T> encontrarMax(Node<T> nodo) {
		if (nodo != null)
			while (nodo.getRight() != null)
				nodo = nodo.getRight();
		return nodo;
	}

	public boolean exist(T data) {
		boolean exist = false;
		Node<T> temp = root;
		int indication = 0;
		while (temp != null && !exist) {
			indication = comparator.compare(data, temp.getData());
			if (indication < 0) {
				temp = temp.getLeft();
			}
			if (indication > 0) {
				temp = temp.getRight();
			} else if (indication == 0)
				exist = true;
		}
		if (isEmpty())
			exist = false;
		return exist;
	}

	public T find(T data) {
		boolean exist = false;
		T find = null;
		Node<T> temp = root;
		int indication = 0;
		while (temp != null && !exist) {
			indication = comparator.compare(data, temp.getData());
			if (indication < 0) {
				temp = temp.getLeft();
			} else if (indication > 0) {
				temp = temp.getRight();
			} else if (indication == 0) {
				find = temp.getData();
				exist = true;
			}
		}
		return find;
	}

	public T encontrarMin() {
		Node<T> n = encontrarMin(root);
		return n.getData();
	}

	public T encontrarMax() {
	Node<T> n = encontrarMax(root);
		return n.getData();
    }

	public boolean isEmpty() {
		return (root == null);
	}

	private static int max(int lhs, int rhs) {
		return lhs > rhs ? lhs : rhs;
	}

	private Node<T> rotateRight(Node<T> n) {
		Node<T> nroot = n.getRight();
		n.setRight(nroot.getLeft());
		nroot.setLeft(n);
		n.setHeight(max(height(n.getLeft()), height(n.getRight())) + 1);
		nroot.setHeight(max(height(nroot.getRight()), n.getHeight()) + 1);
		return nroot;
	}

	private Node<T> rotateLeft(Node<T> n) {
		Node<T> nroot = n.getLeft();
		n.setLeft(nroot.getRight());
		nroot.setRight(n);
		n.setHeight(max(height(n.getLeft()), height(n.getRight())) + 1);
		nroot.setHeight(max(height(nroot.getLeft()), n.getHeight()) + 1);
		return nroot;
	}

	private int height(Node<T> n) {
		return n == null ? -1 : n.getHeight();
	}

	public void showPre(Node<T> auxNode) {
		if (auxNode != null) {
			System.out.println(auxNode.getData() + "-->");
			showPre(auxNode.getLeft());
			showPre(auxNode.getRight());
		}
	}

	public Iterator<T> preorderIterator() {
		listTree = new ArrayList<T>();
		preRunTree(root, listTree);
		return listTree.iterator();
	}

	public Iterator<T> inorderIterator() {
		listTree = new ArrayList<T>();
		inRunTree(root, listTree);
		return listTree.iterator();
	}

	public Iterator<T> postIrderIterator() {
		listTree = new ArrayList<T>();
		postRunTree(root, listTree);
		return listTree.iterator();
	}

	private void postRunTree(Node<T> temp, ArrayList<T> orderedTree) {
		if (temp.getLeft() != null)
			if (!isInArray(temp.getLeft().getData()))
				postRunTree(temp.getLeft(), orderedTree);
		if (temp.getRight() != null)
			if (!isInArray(temp.getRight().getData()))
				postRunTree(temp.getRight(), orderedTree);
		orderedTree.add(temp.getData());
	}

	private void preRunTree(Node<T> temp, ArrayList<T> orderedTree) {
		orderedTree.add(temp.getData());
		if (temp.getLeft() != null)
			if (!isInArray(temp.getLeft().getData()))
				preRunTree(temp.getLeft(), orderedTree);
		if (temp.getRight() != null)
			if (!isInArray(temp.getRight().getData()))
				preRunTree(temp.getRight(), orderedTree);
	}

	private void inRunTree(Node<T> temp, ArrayList<T> orderedTree) {
		if (temp.getLeft() != null)
			if (!isInArray(temp.getLeft().getData()))
				inRunTree(temp.getLeft(), orderedTree);
		orderedTree.add(temp.getData());
		if (temp.getRight() != null)
			if (!isInArray(temp.getRight().getData()))
				inRunTree(temp.getRight(), orderedTree);
	}

	public void showPost(Node<T> auxNode) {
		if (auxNode != null) {
			showPost(auxNode.getLeft());
			showPost(auxNode.getRight());
			System.out.println(auxNode.getData() + "-->");
		}
	}

	private boolean isInArray(T data) {
		boolean itIs = false;
		for (T t : listTree) {
			if (comparator.compare(t, data) == 0) {
				itIs = true;
			}
		}
		return itIs;
	}

	public int calculateHeight() {
		return calculateHeight(root);
	}

	private int calculateHeight(Node<T> actual) {
		if (actual == null)
			return -1;
		else
			return 1 + Math.max(calculateHeight(actual.getLeft()), calculateHeight(actual.getRight()));
	}
}