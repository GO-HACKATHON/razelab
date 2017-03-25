package com.razelab.hackathon.server.usage.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UsageUtil {

	final static String terminalError = "Terminal Error";

	public static String runTerminal(String command) throws IOException {
		// TODO access python script
		Process process = Runtime.getRuntime().exec(command);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		String s = null;
		// read the output from the command
		System.out.println("Here is the standard output of the command:\n");
		while ((s = stdInput.readLine()) != null) {
			return s;
		}

		// read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
			return terminalError;
		}

		return terminalError;
	}
}
