
package com.phm.test.wisdm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author phm
 */
public class WISDMReader extends FileReader {
    public WISDMReader (String path) throws IOException {
        super (path);
    }
    public WISDMReader (File path) throws IOException {
        super (path);
    }
    public WISDMReader (FileDescriptor fd) {
        super (fd);
    }
    
    public boolean read (List<WISDMRecord> data) {
        BufferedReader br = new BufferedReader(this);
        br.lines().forEach((String x) -> {
            if (x.length() > 1) {
                WISDMRecord tmp = new WISDMRecord ();
                String r = x.split(";")[0];
                StringTokenizer st = new StringTokenizer (r, ",");
                if (st.countTokens() >= 6) {
                    tmp.userid = Integer.valueOf(st.nextToken());
                    tmp.activity = Activity.valueOf(st.nextToken());
                    tmp.timestamp = Long.valueOf(st.nextToken());
                    tmp.accX = Float.valueOf(st.nextToken());
                    tmp.accY = Float.valueOf(st.nextToken());
                    tmp.accZ = Float.valueOf(st.nextToken());
                    //System.out.println (tmp.toString());
                }
                data.add(tmp);
            }
        });
        return true;
    }
}
