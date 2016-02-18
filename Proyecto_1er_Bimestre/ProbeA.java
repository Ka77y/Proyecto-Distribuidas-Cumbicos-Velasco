

import java.net.*;
import java.io.*;

public class ProbeA
{
    //public static void main(String args[]) {
	public void sondas()
	{
      try
			{
        	int cont =0;
					FileWriter fw = new FileWriter("log.out",true);

    				Runtime r = Runtime.getRuntime();
    				Runtime r2 = Runtime.getRuntime();

					Process p = r.exec("uptime > log.out");
					p.waitFor();
					Process p2 = r2.exec("free > log.out");
					p2.waitFor();
					BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
					BufferedReader b2 = new	BufferedReader(new InputStreamReader(p2.getInputStream()));

					String line = "", line2 = "";

					while ((line = b.readLine()) != null)
					{
		  				fw.write(line.substring(44,61));
					}

					while ((line2 = b2.readLine()) != null)
					{
						if (cont==1)
							fw.write("\t"+line2.substring(20,31));

						cont++;
					}

					fw.write("\n");
					b.close();
					fw.close();
			}

			catch(IOException e1)	{
			System.out.println(e1);
			}

      catch(InterruptedException e2) {
			System.out.println(e2);
			}
  }
}
