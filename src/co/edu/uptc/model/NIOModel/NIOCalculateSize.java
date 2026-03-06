package co.edu.uptc.model.NIOModel;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class NIOCalculateSize {

    public long calculateSizeRecursive(Path path) throws IOException{
        if (!exists(path)) return -1;
        if (isFile(path)) return getFileSize(path);
        return calculateDirectory(path);
    }

    private boolean exists (Path path) {
        return Files.exists(path);
    }

    private boolean isFile (Path path) {
        return Files.isRegularFile(path);
    }

    private long getFileSize (Path path) throws IOException{
        return Files.size(path);
        
    }

    private long calculateDirectory (Path directory) throws IOException{
        DirectoryStream<Path> list = getFiles(directory);
        return sumList(list);
    }

    private DirectoryStream<Path> getFiles (Path directory) throws IOException{
        return Files.newDirectoryStream(directory);
    }

    private long sumList (DirectoryStream<Path> list) throws IOException{
        if (list == null) return 0;

        long total = 0;
        for (Path path : list) {
            total += Files.size(path);
        }
        return total;
    }
}
