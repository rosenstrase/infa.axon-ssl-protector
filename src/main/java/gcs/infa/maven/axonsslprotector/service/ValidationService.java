package gcs.infa.maven.axonsslprotector.service;

public interface ValidationService {
	boolean isValidEmail(String email);

	boolean isValidCommonName(String commonName);

	boolean isValidOrgUnit(String organizationalunit);

	boolean isValidOrg(String organization);

	boolean isValidLocality(String locality);

	boolean isValidState(String state);

	boolean isValidCountry(String country);
}
