package com.xdarkness.search.index;

public class IndexerThread extends Thread {
	private Indexer indexer;

	public void run() {
		try {
			if (this.indexer.isUpdateFlag())
				this.indexer.update();
			else {
				this.indexer.create();
			}
		} finally {
			synchronized (this.indexer) {
				this.indexer.aliveThreadCount -= 1;
			}
		}
	}

	public Indexer getIndexer() {
		return this.indexer;
	}

	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}
}

/*
 * com.xdarkness.search.index.IndexerThread JD-Core Version: 0.6.0
 */