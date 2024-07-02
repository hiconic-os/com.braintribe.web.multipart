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
package com.braintribe.web.multipart.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

import com.braintribe.utils.IOTools;
import com.braintribe.utils.lcd.StringTools;
import com.braintribe.web.multipart.api.PartReader;
import com.braintribe.web.multipart.api.SequentialFormDataReader;
import com.braintribe.web.multipart.impl.Multiparts;

public class TestSequentialFormData {

	@Test
	public void testFormData() throws Exception {
		String boundary = "dart-http-boundary-UwOHLFgJ9JJ-q0EMtbEmsnopqW5Dwul.tsaJCbTVnCLylh.qmUm";

		final byte[] data;
		try (InputStream in = TestSequentialFormData.class.getClassLoader().getResourceAsStream("com/braintribe/web/multipart/test/formdata.bin")) {
			data = IOTools.slurpBytes(in);
		}

		try (SequentialFormDataReader formDataReader = Multiparts.formDataReader(new ByteArrayInputStream(data), boundary).autoCloseInput()
				.sequential()) {
			PartReader part = formDataReader.next();
			while (part != null) {
				String partName = part.getName();
				if (partName == null) {
					System.err.println("part name is null");
				}
				System.out.println(partName);
				String partContent = part.getContentAsString();
				System.out.println(StringTools.getFirstNCharacters(partContent, 20).replace("\r\n", ""));
				part = formDataReader.next();
			}
		}
	}
}
