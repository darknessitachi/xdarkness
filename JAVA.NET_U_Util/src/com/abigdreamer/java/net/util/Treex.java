package com.abigdreamer.java.net.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
public class Treex {
	public TreeNode root = new TreeNode();

	public TreeNode getRoot() {
		return this.root;
	}

	public TreeNode getNode(Object data) {
		TreeIterator ti = iterator();
		while (ti.hasNext()) {
			TreeNode tn = (TreeNode) ti.next();
			if (tn.getData().equals(data)) {
				return tn;
			}
		}
		return null;
	}

	public TreeIterator iterator() {
		return new TreeIterator(this.root);
	}

	public TreeIterator iterator(TreeNode node) {
		return new TreeIterator(node);
	}

	public String toString() {
		return toString(Formatter.DefaultFormatter);
	}

	public String toString(Formatter f) {
		StringBuffer sb = new StringBuffer();
		TreeIterator ti = iterator();
		while (ti.hasNext()) {
			TreeNode tn = (TreeNode) ti.next();
			for (int i = 0; i < tn.getLevel() - 1; ++i)
				;
			TreeNode p = tn.getParent();
			String str = "";
			while ((p != null) && (!p.isRoot())) {
				if (p.isLast())
					str = "  " + str;
				else {
					str = "│ " + str;
				}
				p = p.getParent();
			}
			sb.append(str);
			if (!tn.isRoot()) {
				if (tn.isLast())
					sb.append("└─");
				else {
					sb.append("├─");
				}
			}
			sb.append(f.format(tn.getData()));
			sb.append("\n");
		}
		return sb.toString();
	}

	public TreeNode[] toArray() {
		TreeIterator ti = new TreeIterator(this.root);
		ArrayList arr = new ArrayList();
		while (ti.hasNext()) {
			arr.add(ti.next());
		}
		TreeNode[] tns = new TreeNode[arr.size()];
		for (int i = 0; i < arr.size(); ++i) {
			tns[i] = ((TreeNode) arr.get(i));
		}
		return tns;
	}


	public class TreeIterator implements Iterator {
		private Treex.TreeNode last;
		Treex.TreeNode next;

		TreeIterator(Treex.TreeNode node) {
			this.next = node;
		}

		public boolean hasNext() {
			return this.next != null;
		}

		public Object next() {
			if (this.next == null) {
				throw new NoSuchElementException();
			}
			this.last = this.next;
			if (this.next.hasChild()) {// has child, put the point to first
				// child
				this.next = this.next.getChildren().get(0);
			} else {
				while (this.next.getNextSibling() == null) {
					this.next = this.next.getParent();
					if(this.next.isRoot()) {
						this.next = null;
			            return this.last;
					}
				}
				this.next = this.next.getNextSibling();

			}
			return this.last;
		}

		public void remove() {
			if (this.last == null) {
				throw new IllegalStateException();
			}
			this.last.getChildren().remove(this.last);
			this.last = null;
		}
	}

	public static class TreeNode {
		private int level;
		private Object data;
		private Treex.TreeNodeList children = new Treex.TreeNodeList(this);
		private TreeNode parent;
		private int pos;

		public TreeNode addChild(Object data) {
			TreeNode tn = new TreeNode();
			tn.level = (this.level + 1);
			tn.data = data;
			tn.parent = this;
			tn.pos = this.children.size();
			this.children.arr.add(tn);
			return tn;
		}

		public void removeChild(Object data) {
			for (int i = 0; i < this.children.size(); ++i)
				if (this.children.get(i).getData().equals(data)) {
					this.children.remove(i);
					return;
				}
		}

		public TreeNode getPreviousSibling() {
			if (this.pos == 0) {
				return null;
			}
			return this.parent.getChildren().get(this.pos - 1);
		}

		/**
		 * get sibling node
		 * 
		 * @return
		 */
		public TreeNode getNextSibling() {
			if ((this.parent == null)
					|| (this.pos == this.parent.getChildren().size() - 1)) {
				return null;
			}
			return this.parent.getChildren().get(this.pos + 1);
		}

		public int getLevel() {
			return this.level;
		}

		public boolean isRoot() {
			return this.parent == null;
		}

		public boolean isLast() {
			return (this.parent == null)
					|| (this.pos == this.parent.getChildren().size() - 1);
		}

		public boolean hasChild() {
			return this.children.size() != 0;
		}

		public TreeNode getParent() {
			return this.parent;
		}

		public int getPosition() {
			return this.pos;
		}

		public Treex.TreeNodeList getChildren() {
			return this.children;
		}

		public Object getData() {
			return this.data;
		}

		public void setData(Object data) {
			this.data = data;
		}
	}

	public static class TreeNodeList {
		protected ArrayList arr = new ArrayList();
		private Treex.TreeNode parent;

		public TreeNodeList(Treex.TreeNode parent) {
			this.parent = parent;
		}

		public void add(Treex.TreeNode node) {
			this.parent.addChild(node);
		}

		public Treex.TreeNode remove(Treex.TreeNode node) {
			int pos = node.getPosition();
			for (int i = pos + 1; i < this.arr.size(); ++i) {
				Treex.TreeNode tn = (Treex.TreeNode) this.arr.get(i);
				tn.pos -= 1;
			}
			this.arr.remove(node);
			return node;
		}

		public Treex.TreeNode remove(int i) {
			return remove((Treex.TreeNode) this.arr.get(i));
		}

		public Treex.TreeNode get(int i) {
			return (Treex.TreeNode) this.arr.get(i);
		}

		public Treex.TreeNode last() {
			return (Treex.TreeNode) this.arr.get(this.arr.size() - 1);
		}

		public int size() {
			return this.arr.size();
		}
	}
}