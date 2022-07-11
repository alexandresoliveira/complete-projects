package dev.aleoliv.apps.blog.shared.components.storage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Profile("dev")
public class LocalBlogStorage implements BlogStorage {

	@Value("${blog.photo-collection.root-dir}")
	private String photoCollectionRootDir;

	private final ResourceLoader resourceLoader;
	
	public LocalBlogStorage(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	@Override
	public String save(String pathFile, String filename, MultipartFile multipartFile) throws IOException, URISyntaxException {
		
		Resource resource = resourceLoader.getResource("classpath:" + photoCollectionRootDir);
		
		if (resource.getFile().exists() && resource.getFile().isDirectory()) {
			Path pathDir = Paths.get(resource.getFile().getPath(), pathFile);
			Path path = Paths.get(resource.getFile().getPath(), pathFile, filename);
			Files.createDirectories(pathDir);
			Files.write(path, multipartFile.getBytes(), StandardOpenOption.CREATE_NEW);
			return String.format("%s/%s/%s", photoCollectionRootDir, pathFile, filename);
		}
		
		return "";
	}

	@Override
	public void delete(String filepath) throws IOException {
		Files.deleteIfExists(Paths.get(filepath));
	}

	@Override
	public String link(String filepath, String host, int port) {
		if (StringUtils.hasText(filepath)) {
			URI uri = UriComponentsBuilder.newInstance().host(host).port(port).path("/{filepath}").buildAndExpand(filepath).toUri();
			return uri.toString();
		}
		return null;
	}
}
