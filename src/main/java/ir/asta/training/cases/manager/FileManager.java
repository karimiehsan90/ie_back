package ir.asta.training.cases.manager;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Named("fileManager")
public class FileManager {

    public String saveFile(Attachment attachment) throws IOException {
        String file = null;
        if (attachment != null) {
            String filename = "webapps/ticketing/" + attachment.getContentDisposition().getParameter("filename");
            Path path;
            do {
                int index = filename.lastIndexOf(".");
                String fName = filename.substring(0, index);
                String extension = filename.substring(index + 1);
                filename = fName + "1." + extension;
                path = Paths.get(filename);
            } while (Files.exists(path));
            file = filename.substring("webapps".length());
            InputStream in = attachment.getObject(InputStream.class);
            Files.copy(in, path);
        }
        return file;
    }
}
