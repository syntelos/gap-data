
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Accept a file path source, and a target path expression to copy the
 * source file to.
 */
public class Update {

    public static void usage(){
        System.err.println("Usage");
        System.err.println();
        System.err.println("  Update source [target[':'target]]*");
        System.err.println();
    }
    public static void main(String[] argv){
        if (1 < argv.length){

            File src = new File(argv[0]);
            if (src.isFile()){
                File[] targets = List(src,argv);
                if (null != targets){
                    final long srclen = src.length();
                    try {
                        FileChannel source = new FileInputStream(src).getChannel();
                        try {
                            for (File tgt: targets){
                                source.position(0);
                                FileChannel target = new FileOutputStream(src).getChannel();
                                try {
                                    source.transferTo(0L,srclen,target);
                                }
                                finally {
                                    target.close();
                                }
                                System.out.printf("Updated %s\n",tgt.getPath());
                            }
                        }
                        finally {
                            source.close();
                        }
                    }
                    catch (IOException exc){
                        exc.printStackTrace();
                        System.exit(1);
                    }
                }
                System.exit(0);
            }
            else {
                System.err.printf("Source file not found %s\n",src.getPath());
                System.exit(1);
            }
        }
        else {
            usage();
            System.exit(1);
        }
    }

    private final static File[] List(File src, String[] argv){
        File[] list = null;
        for (int cc = 1, count = argv.length; cc < count; cc++){
            String[] files = argv[cc].split(":");
            for (int ccc = 0, ccz = argv.length; ccc < ccz; ccc++){

                File tgt = new File(files[ccc]);

                if (tgt.isDirectory()){
                    tgt = new File(tgt,src.getName());
                }

                list = Add(list,tgt);
            }
        }
        return list;
    }
    public final static File[] Add(File[] list, File item){
        if (null == item)
            return list;
        else if (null == list)
            return new File[]{item};
        else {
            int len = list.length;
            File[] copier = new File[len+1];
            System.arraycopy(list,0,copier,0,len);
            copier[len] = item;
            return copier;
        }
    }
}
