package structures;

import java.util.Comparator;

public class DoubleList<T> {
	private Node<T> head;
	private Comparator<T> comparator;

	public DoubleList(Comparator<T> comparator) {
		this.head = null;
		this.comparator = comparator;
	}

	public void insert(T data) {
		Node<T> auxNode = new Node<T>(data);
		if (head == null) {
			head = auxNode;
			head.setLeftNode(null);
		} else {
			Node<T> actualNode = head;
			while (actualNode.getRightNode() != null) {
				actualNode = actualNode.getRightNode();
			}
			actualNode.setRightNode(auxNode);
			auxNode.setLeftNode(actualNode);
		}
	}

	public boolean exist(T data) {
		Node<T> actualNode = head;
		if (actualNode != null) {
			while (comparator.compare(actualNode.getData(), data) != 0) {
				if (actualNode.getRightNode() != null) {
					actualNode = actualNode.getRightNode();
				} else {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean isEmpty() {
		return head == null ? true : false;
	}

	public void remove(T data) {
		Node<T> actualNode = head;
		if (exist(data)) {
			if (comparator.compare(actualNode.getData(), data) == 0) {
				head = actualNode.getRightNode();
				head.setLeftNode(null);
			} else {
				Node<T> previousNode = head;
				while (comparator.compare(previousNode.getRightNode().getData(), data) != 0) {
					previousNode = previousNode.getRightNode();
				}
				actualNode = previousNode.getRightNode().getRightNode();
				if (actualNode != null) {
					actualNode.setLeftNode(previousNode);
				}
				previousNode.setRightNode(actualNode);
			}
		}
	}

	public String showForward() {
		String listMessage = " | ";
		Node<T> actualNode = head;
		if (actualNode != null) {
			while (actualNode.getRightNode() != null) {
				listMessage += actualNode.getData().toString() + " | ";
				actualNode = actualNode.getRightNode();
			}
			listMessage += actualNode.getData().toString();
			return listMessage;
		} else {
			return listMessage;
		}
	}

	public String showBackward() {
		String listMessage = " | ";
		Node<T> actualNode = head;
		if (actualNode != null) {
			while (actualNode.getRightNode() != null) {
				actualNode = actualNode.getRightNode();
			}
			while (actualNode.getLeftNode() != null) {
				listMessage += actualNode.getData().toString() + " | ";
				actualNode = actualNode.getLeftNode();
			}
			listMessage += actualNode.getData().toString();
			return listMessage;
		} else {
			return listMessage;
		}
	}
}