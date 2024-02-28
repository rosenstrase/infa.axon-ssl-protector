package gcs.infa.maven.axonsslprotector.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

import gcs.infa.maven.axonsslprotector.util.SignatureAlgorithm;

public class GenerateKeypair implements KeyGenerator {

	private final KeyPair keyPair;
	private final int bitCount;
	private final SignatureAlgorithm algorithmIdentifier;

	public GenerateKeypair(SignatureAlgorithm algorithmIdentifier, int bitCount)
			throws NoSuchProviderException, NoSuchAlgorithmException {
		this.bitCount = bitCount;
		this.algorithmIdentifier = algorithmIdentifier;
		this.keyPair = generateKeyPair();
	}

	@Override
	public PrivateKey getPrivateKey() {
		return keyPair.getPrivate();
	}

	@Override
	public PublicKey getPublicKey() {
		return keyPair.getPublic();
	}

	@Override
	public KeyPair generateKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(this.algorithmIdentifier.toString());
		kpg.initialize(this.bitCount);
		return kpg.genKeyPair();
	}

}
