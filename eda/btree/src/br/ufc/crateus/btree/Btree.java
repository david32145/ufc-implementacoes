package br.ufc.crateus.btree;

import java.io.IOException;

import br.ufc.crateus.btree.st.Tuple;

public class Btree<K extends Comparable<K>, V> {
	private Page<K> root;
	private PageSerializer<K> pageSerializer;
	private DataSerializer<V> dataSerializer;

	private final static long NULL = -1;

	public Btree(PageSerializer<K> pageSerializer, DataSerializer<V> dataSerializer) throws IOException {
		this.pageSerializer = pageSerializer;
		this.dataSerializer = dataSerializer;
		this.root = this.pageSerializer.root();
	}

	public void put(K key, V value) throws IOException {
		put(root, key, value);
		if (root.overflow()) {
			Page<K> nv = root.split();
			Tuple<K, Tuple<Long, Long>> t = nv.pageST().removeMin();
			nv.setLeft(t.second().second());
			nv.save();

			BinarySearchST<K, Tuple<Long, Long>> np = new BinarySearchST<>(pageSerializer.pageSize());
			Page<K> newRoot = pageSerializer.append(root.offset(), np, false);

			newRoot.put(t.first(), t.second().first(), nv.offset());

			newRoot.setLeft(root.offset());

			pageSerializer.setRoot(newRoot.offset());

			root = newRoot;
		}
		root.save();
	}

	public void put(Page<K> page, K key, V value) throws IOException {
		if (page.leaf()) {
			if (!page.contains(key)) {
				long valueOffset = dataSerializer.append(value);
				page.put(key, valueOffset, NULL);
				pageSerializer.updateKeys(PageSerializer.KEYS_INCREMENT);
			} else {
				long valueOffset = page.get(key);
				dataSerializer.write(valueOffset, value);
			}
		} else {
			Page<K> next = page.next(key);
			if (next != null) {
				put(next, key, value);
				if (next.overflow()) {
					Page<K> nv = next.split();
					Tuple<K, Tuple<Long, Long>> t = nv.pageST().removeMin();
					nv.save();
					page.put(t.first(), t.second().first(), nv.offset());
				} else {
					next.save();
				}
			} else {
				long valueOffset = page.get(key);
				dataSerializer.write(valueOffset, value);
			}

		}
	}

	public V get(K key) throws IOException {
		V value = get(root, key);
		return value;
	}

	private V get(Page<K> page, K key) throws IOException {
		if (page.contains(key))
			return dataSerializer.read(page.get(key));
		if (!page.leaf())
			return get(page.next(key), key);
		return null;
	}

	public K max() throws IOException {
		return max(root);
	}

	private K max(Page<K> page) throws IOException {
		if (page.leaf()) {
			return page.pageST().max();
		} else {
			return max(pageSerializer.read(page.right()));
		}
	}

	public K min() throws IOException {
		return min(root);
	}

	private K min(Page<K> page) throws IOException {
		if (page.leaf())
			return page.pageST().min();
		else
			return min(pageSerializer.read(page.getLeft()));
	}

	public void removeMin() throws IOException {
		if (root.pageST().size() == 0)
			return;
		else pageSerializer.updateKeys(PageSerializer.KEYS_DECREMENT);
		// se tem pelo menos uma chave então é possível remover, assim posso diminuir antes
		if (root.leaf()) {
			root.pageST().removeMin();
			root.save();
		} else {
			Page<K> left = pageSerializer.read(root.getLeft());
			removeMin(left);
			if (left.underflow()) {
				Tuple<K, Tuple<Long, Long>> pageMin = root.pageST().minAll();
				Page<K> right = pageSerializer.read(pageMin.second().second());
				if (right.minimal() && root.pageST().size() == 1) {
					concat(root, left, right, pageMin.first());
					root = left;
					pageSerializer.setRoot(root.offset());
				} else if (right.minimal()) {
					concat(root, left, right, pageMin.first());
					root.save();
				} else {
					rotateLeft(root, left, right, pageMin.first());
				}
			} else {
				left.save();
			}
		}
	}

	private Tuple<K, Tuple<Long, Long>> removeMin(Page<K> page) throws IOException {
		if (page.leaf()) {
			Tuple<K, Tuple<Long, Long>> min = page.pageST().removeMin();
			return min;
		} else {
			Page<K> left = pageSerializer.read(page.getLeft());
			Tuple<K, Tuple<Long, Long>> min = removeMin(left);
			if (left.underflow()) {
				Tuple<K, Tuple<Long, Long>> pageMin = page.pageST().minAll();
				Page<K> right = pageSerializer.read(pageMin.second().second());
				if (right.minimal()) {
					concat(page, left, right, pageMin.first());
				} else {
					rotateLeft(page, left, right, pageMin.first());
				}
			} else {
				left.save();
			}
			return min;
		}
	}

	public void remove(K key) throws IOException{
		if(root.pageST().size() == 0) return;
		if (root.leaf()) {
			if (root.contains(key)) {
				root.pageST().remove(key);
				pageSerializer.updateKeys(PageSerializer.KEYS_DECREMENT);
			}
		} else {
			Page<K> next;
			K keyNext;
			if(!root.contains(key)) {
				Tuple<Page<K>, K> pageAndKey = root.nextAndKey(key);
				next = pageAndKey.first();
				keyNext = pageAndKey.second();
				remove(next, key);
			}else {
				Tuple<Long, Long> value = root.pageST().remove(key);
				next = pageSerializer.read(value.second());
				Tuple<K, Tuple<Long, Long>> min = removeMin(next);
				root.put(min.first(), min.second().first(), value.second());
				keyNext = min.first();
				pageSerializer.updateKeys(PageSerializer.KEYS_DECREMENT);
			}if (next.underflow()) {
				Page<K> syster = root.syster(next, keyNext);
				if (next.offset() == root.getLeft()) {
					if(syster.minimal() && root.pageST().size() == 1) {
						concat(root, next, syster, keyNext);
						root = next;
						pageSerializer.setRoot(root.offset());
					}else if (syster.minimal()) {
						concat(root, next, syster, keyNext);
					} else {
						rotateLeft(root, next, syster, keyNext);
					}
				}else {
					if(syster.minimal() && root.pageST().size() == 1) {
						concat(root, syster, next, keyNext);
						root = syster;
						pageSerializer.setRoot(root.offset());
					}else if (syster.minimal()) {
						concat(root, syster, next, keyNext);
					} else {
						rotateRigth(root, syster, next, keyNext);
					}
				}
			}else {
				next.save();
			}
		}
		root.save();
	}

	public void remove(Page<K> page, K key) throws IOException {
		if (page.leaf()) {
			if (page.contains(key)) {
				page.pageST().remove(key);
				pageSerializer.updateKeys(PageSerializer.KEYS_DECREMENT);
			}
		} else {
			Page<K> next;
			K keyNext;
			if(!page.contains(key)) {
				Tuple<Page<K>, K> pageAndKey = page.nextAndKey(key);
				next = pageAndKey.first();
				keyNext = pageAndKey.second();
				remove(next, key);
			}else {
				Tuple<Long, Long> value = page.pageST().remove(key);
				next = pageSerializer.read(value.second());
				Tuple<K, Tuple<Long, Long>> min = removeMin(next);
				page.put(min.first(), min.second().first(), value.second());
				keyNext = min.first();
				pageSerializer.updateKeys(PageSerializer.KEYS_DECREMENT);
			}if (next.underflow()) {
				Page<K> syster = page.syster(next, keyNext);
				if (next.offset() == page.getLeft()) {
					if (syster.minimal()) {
						concat(page, next, syster, keyNext);
					} else {
						rotateLeft(page, next, syster, keyNext);
					}
				} else {
					if (syster.minimal()) {
						concat(page, syster, next, keyNext);
					} else {
						rotateRigth(page, syster, next, keyNext);
					}
				}
			}else {
				next.save();
			}
		}
	}

	public int size() {
		return pageSerializer.readSize();
	}

	private void rotateLeft(Page<K> root, Page<K> left, Page<K> right, K key) throws IOException {
		Tuple<Long, Long> value = root.pageST().remove(key);
		Tuple<K, Tuple<Long, Long>> minRigth = right.pageST().removeMin();

		long leftRigth = right.getLeft();
		right.setLeft(minRigth.second().second());

		left.put(key, value.first(), leftRigth);
		root.put(minRigth.first(), minRigth.second().first(), right.offset());

		left.save();
		right.save();

	}

	private void rotateRigth(Page<K> root, Page<K> left, Page<K> right, K key) throws IOException {
		Tuple<Long, Long> value = root.pageST().remove(key);
		Tuple<K, Tuple<Long, Long>> maxLeft = left.pageST().removeMax();

		long leftRigth = right.getLeft();
		right.setLeft(maxLeft.second().second());

		right.put(key, value.first(), leftRigth);
		root.put(maxLeft.first(), maxLeft.second().first(), right.offset());

		left.save();
		right.save();
	}

	private void concat(Page<K> root, Page<K> left, Page<K> right, K key) throws IOException {
		Tuple<Long, Long> value = root.pageST().remove(key);

		left.put(key, value.first(), right.getLeft());

		left.merge(right);

		right.flushPage();

		left.save();
	}
}
