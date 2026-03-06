package co.edu.uptc.model.NIOModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.DeleteFile;
import co.edu.uptc.model.ListDirectory;
import co.edu.uptc.model.SearchFile;
import co.edu.uptc.model.ValidateFile;

public class NIOManagerFileImp implements ModelInterface{

    private File directory = null;

    public void setDirectory(File directoryToSearch) {
        this.directory = directoryToSearch;
    }

    public File getDirectory() {
        return directory;
    }

    public void startInitialPath(String path) throws FileNotFoundException{
        //El tipo de archivo "0" es para solo validar si existe (Sin nesecidad de si es carpeta o archivo)
        ValidateFile validateFile = new ValidateFile(path, 0);
        validateFile.validatePath();
        File directory = new File(path);
        setDirectory(directory);
    }

    @Override
    public String exec() throws Exception {
        return "";
    }

    @Override
    public void validateAllPathDirectory(String path) throws FileNotFoundException {
        ValidateFile validateFile = new ValidateFile(path,1);
        validateFile.validateType();
        validateFile.validateDirectory();
    }

    @Override
    public void validateAllPathFile(String path) throws FileNotFoundException {
        ValidateFile validateFile = new ValidateFile(path,0);
        validateFile.validateType();
        validateFile.validateFile();
    }


    @Override
    public List<String> listDirectory() {
        ListDirectory listDirectory = new ListDirectory();
        File directory = getDirectory();
        return listDirectory.listDirectoryRecursive(directory);
    }

    @Override
    public String searchFile(String nameToSearch) {
        SearchFile searchFile = new SearchFile();
        File directory = getDirectory();
        String regexPattern = nameToSearch.toLowerCase().replace(".", "\\.").replace("*", ".*").replace("?", ".");
        String result = searchFile.searchFileRecursive(directory, regexPattern);
        if (result != null) {
            return result;
        }else{
            return "Archivo " + nameToSearch + " no existe en la ubicación, revisa bien el nombre :)";
        }
    }
    @Override
    public long calculateSize(String initialPath) throws IOException{
        Path path = Paths.get(initialPath);

        if(!Files.exists(path)){
            return -1;
        }
        NIOCalculateSize nioCalculateSize = new NIOCalculateSize();
        return nioCalculateSize.calculateSizeRecursive(path);
    }

    @Override
    public boolean deleteFile(String nameToSearch) {
        DeleteFile deleteFile = new DeleteFile();
        File directory = getDirectory();
        return deleteFile.deleteFileRecursive(directory,nameToSearch);
    }
}
