package dev.aleoliv.apps.blog.shared.components.storage;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.multipart.MultipartFile;

public interface BlogStorage {

	String save(String pathFile, String filename, MultipartFile multipartFile) throws IOException, URISyntaxException;

	String link(String filepath, String host, int port);
	
	void delete(String filepath) throws IOException;
}
