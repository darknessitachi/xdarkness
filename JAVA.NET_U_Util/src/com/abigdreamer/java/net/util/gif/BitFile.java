package com.abigdreamer.java.net.util.gif;

import java.io.IOException;
import java.io.OutputStream;

class BitFile {
	OutputStream output_;
	byte[] buffer_;
	int index_;
	int bitsLeft_;

	public BitFile(OutputStream output) {
		this.output_ = output;
		this.buffer_ = new byte[256];
		this.index_ = 0;
		this.bitsLeft_ = 8;
	}

	public void Flush() throws IOException {
		int numBytes = this.index_ + (this.bitsLeft_ == 8 ? 0 : 1);
		if (numBytes > 0) {
			this.output_.write(numBytes);
			this.output_.write(this.buffer_, 0, numBytes);
			this.buffer_[0] = 0;
			this.index_ = 0;
			this.bitsLeft_ = 8;
		}
	}

	public void WriteBits(int bits, int numbits) throws IOException {
		int bitsWritten = 0;
		int numBytes = 255;
		do {
			if (((this.index_ == 254) && (this.bitsLeft_ == 0))
					|| (this.index_ > 254)) {
				this.output_.write(numBytes);
				this.output_.write(this.buffer_, 0, numBytes);

				this.buffer_[0] = 0;
				this.index_ = 0;
				this.bitsLeft_ = 8;
			}

			if (numbits <= this.bitsLeft_) {
				int tmp91_88 = this.index_;
				byte[] tmp91_84 = this.buffer_;
				tmp91_84[tmp91_88] = (byte) (tmp91_84[tmp91_88] | (bits & (1 << numbits) - 1) << 8 - this.bitsLeft_);
				bitsWritten += numbits;
				this.bitsLeft_ -= numbits;
				numbits = 0;
			} else {
				int tmp138_135 = this.index_;
				byte[] tmp138_131 = this.buffer_;
				tmp138_131[tmp138_135] = (byte) (tmp138_131[tmp138_135] | (bits & (1 << this.bitsLeft_) - 1) << 8 - this.bitsLeft_);
				bitsWritten += this.bitsLeft_;
				bits >>= this.bitsLeft_;
				numbits -= this.bitsLeft_;
				this.buffer_[(++this.index_)] = 0;
				this.bitsLeft_ = 8;
			}
		} while (numbits != 0);
	}
}

/*
 * com.xdarkness.framework.utility.gif.BitFile JD-Core Version: 0.6.0
 */