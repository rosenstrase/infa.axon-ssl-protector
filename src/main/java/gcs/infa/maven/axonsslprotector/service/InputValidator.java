package gcs.infa.maven.axonsslprotector.service;

public class InputValidator implements ValidationService {

	// PATTERNS
	private static final String EMAIL_PATTERN = "^[\\w.-]+@informatica\\.com$";

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
