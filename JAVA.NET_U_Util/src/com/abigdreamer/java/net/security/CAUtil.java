package com.abigdreamer.java.net.security;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInputStream;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import com.abigdreamer.java.net.util.DateUtil;

public class CAUtil {
	public static KeyPair generateRSAKeyPair() throws Exception {
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "BC");
		gen.initialize(1024, new SecureRandom());
		return gen.generateKeyPair();
	}

	public static X509Certificate createCA(PublicKey pubKey,
			PrivateKey privKey, String issuerDN, int limit) throws Exception {
		X509V3CertificateGenerator v3CertGen = new X509V3CertificateGenerator();
		v3CertGen.setSerialNumber(BigInteger
				.valueOf(System.currentTimeMillis()));
		X509Principal dn = new X509Principal(issuerDN);
		v3CertGen.setIssuerDN(dn);
		v3CertGen.setSubjectDN(dn);

		Date today = new Date(System.currentTimeMillis());
		Date endDay = DateUtil.addMonth(today, limit);
		v3CertGen.setNotBefore(today);
		v3CertGen.setNotAfter(endDay);
		v3CertGen.setPublicKey(pubKey);
		v3CertGen.setSignatureAlgorithm("SHA1WithRSA");

		ByteArrayInputStream bIn = new ByteArrayInputStream(pubKey.getEncoded());
		SubjectPublicKeyInfo info = new SubjectPublicKeyInfo(
				(ASN1Sequence) new DERInputStream(bIn).readObject());
		v3CertGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
				new SubjectKeyIdentifier(info));
		v3CertGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
				new AuthorityKeyIdentifier(info));
		v3CertGen.addExtension(X509Extensions.BasicConstraints, false,
				new BasicConstraints(true));
		v3CertGen.addExtension(X509Extensions.KeyUsage, false, new KeyUsage(6));

		X509Certificate cert = v3CertGen.generate(privKey, "BC");
		cert.checkValidity(new Date());
		cert.verify(pubKey);
		return cert;
	}
}