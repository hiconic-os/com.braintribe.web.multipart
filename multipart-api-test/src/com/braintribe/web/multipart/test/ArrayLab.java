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

import com.braintribe.utils.IOTools;
import com.braintribe.utils.TimeTracker;

public class ArrayLab {
	public static void main(String[] args) {
		byte[] data = new byte[IOTools.SIZE_16K];
		byte[] data2 = new byte[IOTools.SIZE_16K];

		TimeTracker.start("unchecked array copy");

		for (int j = 0; j < 100000; j++) {
			if (data[0] == '\n') {
				System.out.println("schnucki");
			}

			System.arraycopy(data, 0, data2, 0, data.length);
		}

		TimeTracker.stopAndPrint("unchecked array copy");

		TimeTracker.start("checked");

		for (int j = 0; j < 100000; j++) {

			for (int i = 0; i < data.length; i++) {
				byte b = data[i];
				if (b == '\n') {
					System.out.println("found");
				} else {
					data2[i] = b;
				}
			}
			System.arraycopy(data, 0, data2, 0, data.length);
		}

		TimeTracker.stopAndPrint("checked");

		TimeTracker.start("unchecked");

		for (int j = 0; j < 100000; j++) {

			for (int i = 0; i < data.length; i++) {
				byte b = data[i];
				data2[i] = b;
			}
			// System.arraycopy(data, 0, data2, 0, data.length);
		}

		TimeTracker.stopAndPrint("unchecked");

	}
}
