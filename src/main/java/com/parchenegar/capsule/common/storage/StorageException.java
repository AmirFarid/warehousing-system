package com.parchenegar.capsule.common.storage;

import java.nio.file.Path;

public class StorageException extends RuntimeException
{
    public StorageException(String message)
    {
        super(message);
    }

    public StorageException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public static StorageException emptyFileException(String filename)
    {
        return new StorageException("Failed to store empty file " + filename);
    }

    public static StorageException emptyFileException(Path filename)
    {
        return new StorageException("Failed to store empty file " + filename);
    }

    public static StorageException outsideRelativePath(String filename)
    {
        return new StorageException(
                "Cannot store file with relative path outside current directory " + filename
        );
    }

    public static StorageException outsideRelativePath(Path filename)
    {
        return new StorageException(
                "Cannot store file with relative path outside current directory " + filename
        );
    }

    public static StorageException storeFailed(Path filename, Throwable throwable)
    {
        return new StorageException("Failed to store file " + filename, throwable);
    }

    public static StorageException storeFailed(String filename, Throwable throwable)
    {
        return new StorageException("Failed to store file " + filename, throwable);
    }

    public static StorageException readFailed(Throwable throwable)
    {
        return new StorageException("Failed to read stored files", throwable);
    }

    public static StorageException initializeStorage(Throwable throwable)
    {
        return new StorageException("Could not initialize storage location", throwable);
    }

}
