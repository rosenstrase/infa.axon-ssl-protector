package gcs.infa.maven.axonsslprotector.service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyGenerator {

	PrivateKey getPrivateKey();

	PublicKey getPublicKey();

	KeyPair generateKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException;

}
