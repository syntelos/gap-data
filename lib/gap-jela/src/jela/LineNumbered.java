/*
 * Copyright (c) 2009 John Pritchard and the Jela Project Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jela;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * 
 * @author J. Pritchard
 */
public final class LineNumbered
    extends PrintWriter
{

    public LineNumbered(String text){
        super(new CharArrayWriter());
        int newlines = 0;
        StringTokenizer strtok = new StringTokenizer(text,"\n", true);
        for (int lno = 1; strtok.hasMoreTokens(); ){

            String line = strtok.nextToken();
            if ("\n".equals(line)){
                newlines += 1;
                if (1 < newlines){

                    if (lno < 10)
                        this.printf("   %d    \n",lno);
                    else if (lno < 100)
                        this.printf("  %d    \n",lno);
                    else if (lno < 1000)
                        this.printf(" %d    \n",lno);
                    else
                        this.printf("%d    \n",lno);

                    lno++;
                }
            }
            else {
                newlines = 0;

                if (lno < 10)
                    this.printf("   %d    %s\n",lno,line);
                else if (lno < 100)
                    this.printf("  %d    %s\n",lno,line);
                else if (lno < 1000)
                    this.printf(" %d    %s\n",lno,line);
                else
                    this.printf("%d    %s\n",lno,line);

                lno++;
            }
        }
    }

    public String toString(){
        CharArrayWriter out = (CharArrayWriter)this.out;
        return out.toString();        
    }
}
