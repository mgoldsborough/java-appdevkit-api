package com.mg2.adk.response;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author MG2 Innovations LLC
 *
 */
public class Product {

    /**
     * The name of the file.
     */
    String filename;

    /**
     * The file's content type.
     */
    String content_type;

    /**
     * The base-64 encoded file content.
     */
    String enc_content;

    public Product() {

    }

    /**
     * Gets the filename of the file which was originally uploaded to
     * AppDevKit.com.
     * 
     * @return The filename.
     */
    public String getFilename() {
	return filename;
    }

    /**
     * Gets the content type of the file which was originally uploaed to
     * AppDevKit.com.
     * 
     * @return The file's content type.
     */
    public String getContent_type() {
	return content_type;
    }

    /**
     * Gets the encoded content.
     * 
     * @return The base64 encoded content.
     */
    public String getEncodedContent() {
	return enc_content;
    }

    /**
     * Gets the unencoded content. Base64 decodes the encoded content.
     * 
     * @return The unencoded content.
     */
    public byte[] getContent() {
	return Base64.decodeBase64(enc_content);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Filename: ");
	sb.append(filename);
	sb.append("\nContent Type: ");
	sb.append(content_type);

	return sb.toString();
    }
}
