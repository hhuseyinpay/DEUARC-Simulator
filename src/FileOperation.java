import java.io.*;

/**
 * Created by hhp on 29/04/2017.
 */


class FileOperation {

    public boolean extentionChecker(String path) {

        int len = path.length();
        for (int i = len; i > 0; i--) {
            if (path.charAt(i - 1) == '.') {
                return path.substring(i).equals("asm") || path.substring(i).equals("basm");
            }
        }
        return false;
    }

    public String readFile(String path) {
        InputStream is;
        BufferedReader buf;
        StringBuilder sb = new StringBuilder();
        try {
            is = new FileInputStream(path);

            buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


}