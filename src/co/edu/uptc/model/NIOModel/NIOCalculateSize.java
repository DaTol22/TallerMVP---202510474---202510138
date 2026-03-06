package co.edu.uptc.model.NIOModel;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NIOCalculateSize {

    public long calcDirectorySize(Path initialPath) throws IOException{
        long size = 0;
        try (Stream<Path> content = Files.walk(initialPath, FileVisitOption.FOLLOW_LINKS)) {
            List<Path> iterableContent = content.collect(Collectors.toList());
            for(Path file : iterableContent){
                size += Files.size(file);
            }
        }
        return size;
    }
}
