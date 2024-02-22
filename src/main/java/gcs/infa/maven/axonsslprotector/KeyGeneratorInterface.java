package gcs.infa.maven.axonsslprotector;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyGeneratorInterface {

	PrivateKey getPrivateKey();

	PublicKey getPublicKey();

	KeyPair generateKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException;

}
