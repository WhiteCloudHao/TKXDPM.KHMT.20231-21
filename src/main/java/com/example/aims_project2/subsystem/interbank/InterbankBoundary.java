package com.example.aims_project2.subsystem.interbank;

import com.example.aims_project2.common.exception.UnrecognizedException;
import com.example.aims_project2.utils.API;

public class InterbankBoundary {

	String query(String url, String data) {
		String response = null;
		try {
			response = API.post(url, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UnrecognizedException();
		}
		return response;
	}

}
