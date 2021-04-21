package structures;

public class Node<T> {

	private T data;
	private int height;
	Node<T> leftNode;
	Node<T> rightNode;

	public Node(T data) {
		this.setData(data);
		this.height = 1;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Node<T> getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node<T> node) {
		leftNode = node;
	}

	public Node<T> getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node<T> node) {
		rightNode = node;
	}
}
