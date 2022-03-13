package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {

    public static void processDir(String dirName, String resultFileName) {
        Path in = Paths.get(dirName);
        Path out = Paths.get(resultFileName);
        Charset csIn = Charset.forName("Cp1250");
        Charset csOut = StandardCharsets.UTF_8;

        try {
            FileChannel channelOut = FileChannel.open(out, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

            Files.walkFileTree(in, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
                {
                    FileChannel channelIn = FileChannel.open(file)  ;
                    ByteBuffer buffer = ByteBuffer.allocateDirect((int) channelIn.size());

                    channelIn.read(buffer);

                    buffer.flip();
                    channelOut.write(csOut.encode(csIn.decode(buffer) + " "));

                    channelIn.close();

                    return FileVisitResult.CONTINUE;
                }
            });
            channelOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
