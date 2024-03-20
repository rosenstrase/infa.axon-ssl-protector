package gcs.infa.maven.axonsslprotector.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class InputValidator implements ValidationService {

	private static final int MAX_COUNTRY_CODE_LENGTH = 2;
	private Set<String> validCountryCodes;

	public InputValidator() {
		// Load country codes from a JSON file during object construction
		loadValidCountryCodes("/axon-ssl-protector/ISO3166-1.alpha2.json");
	}

	private void loadValidCountryCodes(String jsonFilePath) {
		try {
			InputStream inputStream = getClass().getResourceAsStream(jsonFilePath);
			InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
			JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
			validCountryCodes = new HashSet<>();
			JsonArray countryCodesArray = jsonObject.getAsJsonArray("validCountryCodes");
			for (int i = 0; i < countryCodesArray.size(); i++) {
				String countryCode = countryCodesArray.get(i).getAsString();
				if (countryCode.length() <= MAX_COUNTRY_CODE_LENGTH) {
					validCountryCodes.add(countryCode);
				} else {
					System.err.println("Invalid country code: " + countryCode);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to load country codes", e);
		}
	}

	// PATTERNS
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

	@Override
	public boolean isValidEmail(String email) {
		return email.matches(EMAIL_PATTERN);
	}

	@Override
	public boolean isValidCommonName(String commonName) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isValidOrgUnit(String organizationalunit) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isValidOrg(String organization) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isValidLocality(String locality) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isValidState(String state) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isValidCountry(String country) {
		// TODO Auto-generated method stub
		return true;
	}

}
