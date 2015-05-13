/**
	BlockTrackR - Minecraft monitoring plugin designed to capture, index, and correlate real-time data in a searchable repository.
    Copyright (C) 2015 - Damion (Volition21) Volition21@Hackion.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.Volition21.BlockTrackR.Utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class BTRJSONTools {

	/**
	 * Gets the JSON data and returns the string value relative to the
	 * key that was parsed to this method.
	 * 
	 * @param key The key of the value to retrieve.
	 * @return The value relative to the key.
	 */
	public String getValueFromJSON(String key) {
		BTRDebugger.DLog("getJSONasString val: " + key);
		if (!(getJSONasMap() == null)) {
			BTRDebugger.DLog("getJSONasMap not null");
			Map<?, ?> METADATA = getJSONasMap();
			String Meta_Result = (String) METADATA.get(key);
			BTRDebugger.DLog("getJSONasString return " + Meta_Result);
			return Meta_Result;
		} else {
			BTRDebugger.DLog("getJSONasMap is null");
			return null;
			// Couldn't get the metadata for the plugin, do something creative.
		}
	}

	/**
	 * Gets the JSON data and returns it to the caller as a Map.
	 */
	public Map<?, ?> getJSONasMap() {
		URL url;
		try {
			url = new URL("http://volition21.com/META-INF/BlockTrackR/");
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			JsonParserFactory factory = JsonParserFactory.getInstance();
			JSONParser parser = factory.newJsonParser();
			Map<?, ?> jsonData = parser.parseJson(builder.toString());
			return jsonData;
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public Map<?, ?> getOpsJSONasMap() {
		try {
			String line;
			String ConfDir = "ops.json";
			FileInputStream in = new FileInputStream(ConfDir);
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			JsonParserFactory factory = JsonParserFactory.getInstance();
			JSONParser parser = factory.newJsonParser();
			Map<?, ?> jsonData = parser.parseJson(builder.toString());
			in.close();
			return jsonData;
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

}
