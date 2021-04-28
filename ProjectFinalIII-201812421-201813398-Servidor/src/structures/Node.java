package structures;

public class Node<T> {

	private T data;
	private Node<T> right;
	private Node<T> left;
	private int height;

	public Node(T data) {
		this.data = data;
		this.height = 0;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public Node<T> getRight() {
		return right;
	}

	public void setRight(Node<T> next) {
		this.right = next;
	}

	public Node<T> getLeft() {
		return left;
	}

	public void setLeft(Node<T> previous) {
		this.left = previous;
	}

	@Override
	public String toString() {
		return "Node [data=" + data + ", next=" + right + ", previous=" + left + "]";
	}
}