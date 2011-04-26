package com.xdarkness.search;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ZvingTokenizer extends Tokenizer {
	public TokenStream st;
	public Token currentToken;
	public boolean splitingFlag = false;
	public int offSet;
	public StringBuffer sb = new StringBuffer();

	public ZvingTokenizer(String fieldName, Reader reader) {
		// TODO need to check the where's the 'zving' come from
		fieldName = fieldName.replace("zving", "sky");
		this.st = new IKAnalyzer().tokenStream(fieldName, reader);
		this.input = reader;
	}

	public Token next() throws IOException {
		if (!this.splitingFlag) {
			this.currentToken = this.st.next();
			if (this.currentToken == null) {
				this.splitingFlag = true;
			} else {
				this.sb.append(new String(this.currentToken.termBuffer(), 0,
						this.currentToken.termLength()));
				return this.currentToken;
			}
		}
		if ((this.splitingFlag) && (this.offSet < this.sb.length())) {
			return new Token(this.sb.substring(this.offSet, this.offSet + 1),
					this.offSet, ++this.offSet);
		}

		return null;
	}
}

/*
 * com.xdarkness.search.ZvingTokenizer JD-Core Version: 0.6.0
 */