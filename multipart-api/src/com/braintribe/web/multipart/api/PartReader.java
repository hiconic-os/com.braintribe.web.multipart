// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
//
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
package com.braintribe.web.multipart.api;

import java.io.IOException;
import java.io.InputStream;

public interface PartReader extends PartHeader {
	InputStream openStream() throws IOException;
	/**
	 * @deprecated Use {@link #openStream()} instead.
	 */
	@Deprecated
	InputStream openTransferEncodingAwareInputStream() throws IOException;
	String getContentAsString() throws IOException;
	String getContentAsString(String charset) throws IOException;
	void consume() throws IOException;

	PartStreamingStatus getStatus();
	boolean isConsumed();
	boolean isBackedUp();

	void backup() throws IOException;
}
