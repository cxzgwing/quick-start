package cxzgwing.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.sun.istack.internal.NotNull;

public class FileUtil {

    /**
     * 读取文件内容到Stream流中，按行读取
     * 
     * @param filePath 文件路径
     * @return 文件内容Stream流
     * @throws IOException IOException
     */
    public static Stream<String> readLines(@NotNull String filePath) throws IOException {
        if (CommonUtil.isEmpty(filePath) || !(new File(filePath)).exists()) {
            return new ArrayList<String>().stream();
        }
        return Files.lines(Paths.get(filePath));
    }

    /**
     * 读取文件内容，一次性加载全部数据，默认UTF_8编码，注意OutOfMemoryError
     * 
     * @param filePath 文件路径
     * @return 文件内容List
     * @throws IOException IOException
     */
    public static List<String> readAllLines(@NotNull String filePath) throws IOException {
        if (CommonUtil.isEmpty(filePath) || !(new File(filePath)).exists()) {
            return new ArrayList<>();
        }
        return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
    }

    /**
     * 读取文件内容（可换行）
     * 
     * @param filePath 文件路径
     * @return 文件内容字符串
     * @throws IOException IOException
     */
    public static String readAll(@NotNull String filePath) throws IOException {
        if (CommonUtil.isEmpty(filePath) || !(new File(filePath)).exists()) {
            return "";
        }
        return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
    }

    /**
     * 读取文件内容（带缓冲的流读取，默认缓冲区8k）（不换行）
     * 
     * @param filePath 文件路径
     * @return 文件内容字符串
     */
    public static String read(@NotNull String filePath) {
        if (CommonUtil.isEmpty(filePath) || !(new File(filePath)).exists()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(Paths.get(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseResource(br);
        }
        return sb.toString();
    }

    /**
     * 写文件（追加内容）
     * 
     * @param filePath 文件路径
     * @param content 文件内容
     */
    public static void write(@NotNull String filePath, @NotNull String content) throws Exception {

        if (CommonUtil.isEmpty(filePath) || CommonUtil.isEmpty(content)) {
            throw new Exception("写文件失败：文件路径或文件内容为空");
        }

        FileOutputStream fos = null;
        FileChannel channel = null;
        try {

            File file = createFileIfAbsent(filePath);

            fos = new FileOutputStream(file, true);
            channel = fos.getChannel();

            writeContent(content, channel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseResource(channel, fos);
        }
    }

    /**
     * 写文件（追加内容）
     *
     * @param filePath 文件路径
     * @param content 文件内容
     */
    public static void writeReplace(@NotNull String filePath, @NotNull String content)
            throws Exception {
        if (CommonUtil.isEmpty(filePath) || CommonUtil.isEmpty(content)) {
            throw new Exception("写文件失败：文件路径或文件内容为空");
        }

        FileOutputStream fos = null;
        FileChannel channel = null;
        try {

            File file = createFileIfAbsent(filePath);

            fos = new FileOutputStream(file, false);
            channel = fos.getChannel();

            writeContent(content, channel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseResource(channel, fos);
        }
    }

    private static void releaseResource(BufferedReader br) {
        if (br != null) {
            try {
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static File createFileIfAbsent(@NotNull String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return file;
    }

    private static void writeContent(@NotNull String content, FileChannel channel)
            throws IOException {
        ByteBuffer buf = ByteBuffer.wrap(content.getBytes());
        buf.put(content.getBytes());
        buf.flip();
        channel.write(buf);
    }

    private static void releaseResource(FileChannel channel, FileOutputStream fos) {
        try {
            if (channel != null) {
                channel.close();
            }
            if (fos != null) {
                fos.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
