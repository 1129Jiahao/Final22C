package common;




import java.io.*;

public class FileUtil {

    /**Author：Jiahao Huang
     * Delete specified file operation
     * @param file
     * @param lineToRemove
     */
    public void removeLineFromFile(String file, String lineToRemove) {

        try {

            File inFile = new File(file);

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            // Construct the new file that will later be renamed to the original
            // filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            // Read from the original file and write to the new
            // unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(lineToRemove)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            // Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Author：Jiahao Huang
     * modifyFile
     * @param filePath
     * @param old
     * @param nwe
     */
    public  void modifyFile(String filePath,String old,String nwe){
        FileUtil modifyFile = new FileUtil();
        modifyFile.writeFile(filePath,modifyFile.readFileContent(filePath,old,nwe));
    }

    // Read the file
    public String readFileContent(String filePath, String oldString, String newString) {
        BufferedReader br = null;
        String line = null;
        StringBuffer bufAll = new StringBuffer();// Save all content after modification
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            while ((line = br.readLine()) != null) {
                StringBuffer buf = new StringBuffer();
                // Modify content core code
                if (line.startsWith(oldString)&&line.equals(oldString)) {
                    buf.append(line);
                    int indexOf = line.indexOf(oldString);
                    buf.replace(indexOf, indexOf + oldString.length(), newString);
                    buf.append(System.getProperty("line.separator"));// Add line break
                    bufAll.append(buf);
                } else {
                    buf.append(line);
                    buf.append(System.getProperty("line.separator"));
                    bufAll.append(buf);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    br = null;
                }
            }
        }
        return bufAll.toString();
    }

    // Write file
    public boolean writeFile(String filePath, String content) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            bw.write(content);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    bw = null;
                }
            }
        }
        return true;
    }


}

