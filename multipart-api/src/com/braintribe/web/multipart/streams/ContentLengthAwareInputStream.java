// ============================================================================
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License along with this library; See http://www.gnu.org/licenses/.
// ============================================================================
package com.braintribe.web.multipart.streams;

import java.io.IOException;
import java.io.InputStream;

public class ContentLengthAwareInputStream extends AbstractPartInputStream {

	private final InputStream delegate;
	private long counter = 0;
	private final long contentLength;
	
	public ContentLengthAwareInputStream(InputStream delegate, long l) {
		this.delegate = delegate;
		this.contentLength = l;
	}
	
	@Override
	public int read() throws IOException {
		if (counter > contentLength)
			throw new IllegalStateException("Internal counter exceeded content length");
		if (counter == contentLength)
			return -1;
		
		counter++;
		return delegate.read();
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (counter > contentLength)
			throw new IllegalStateException("Internal counter exceeded content length");
		if (counter == contentLength)
			return -1;
		
		int bytesLeftToRead = (int) Math.min(contentLength - counter, Integer.MAX_VALUE);
		int numBytesToRead = Math.min(bytesLeftToRead, len);
		
		int numBytesRead = delegate.read(b, off, numBytesToRead);
		counter += numBytesRead;
		
		return numBytesRead;
	}

	@Override
	public int available() throws IOException {
		return (int) Math.min(contentLength - counter, Integer.MAX_VALUE);
	}
	
	public long remainingBytes() {
		return contentLength - counter;
	}
	
	@Override
	public byte[] getUnreadBuffer() {
		// This implementation does not use any buffer
		return null;
	}
	
}
