package com.parchenegar.capsule.common.storage;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;


@Service
public class FileSystemStorageService implements StorageService
{
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties)
    {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    @PostConstruct
    public void init()
    {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw StorageException.initializeStorage(e);
        }
    }

    @Override
    public String store(MultipartFile file, Class clazz)
    {
        String filename = hashFilename(file, clazz);


        try {
            if (file.isEmpty()) {
                throw StorageException.emptyFileException(filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw StorageException.outsideRelativePath(filename);
            }


            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw StorageException.storeFailed(filename, e);
        }

        return filename;
    }

    @Override
    public Stream<Path> loadAll()
    {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw StorageException.readFailed(e);
        }
    }

    @Override
    public Path load(String filename)
    {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename)
    {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll()
    {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public String disk()
    {
        return "local-storage";
    }

    private String hashFilename(MultipartFile file, Class clazz)
    {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        filename += System.currentTimeMillis();
        filename = DigestUtils.md5DigestAsHex(filename.getBytes());
        filename += "." + FilenameUtils.getExtension(file.getOriginalFilename());
        filename = StringUtils.cleanPath(clazz.getSimpleName().toLowerCase() + "-" + filename);
        return filename;
    }

}
