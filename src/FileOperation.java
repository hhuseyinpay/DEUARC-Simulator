/**
 * Created by hhp on 29/04/2017.
 */

public class FileOperation {

    public boolean extentionChecker(String path) {

        int len = path.length();
        for (int i = len; i > 0; i--) {
            if (path.charAt(i - 1) == '.') {
                return path.substring(i).equals("asm") || path.substring(i).equals("basm");
            }
        }
        return false;
    }


}