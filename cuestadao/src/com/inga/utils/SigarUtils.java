/**

com.inga.utils.SigarUtils.java
Version: 1.0

********************************************************************************
Author:
Manuel Cuesta, programmer <camilocuesta@hotmail.com>

**************************************************

cuestadao is Copyright (c) 2010, Manuel Cuesta  <camilocuesta@hotmail.com >
All rights reserved.

Published under the terms of the new BSD license.
See: [http://github.com/m-cuesta/tiers] for the full license and other info.

LICENSE:

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.

Neither the name of Manuel Cuesta nor the names of its contributors may be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.


**************************************************
Revision History / Change Log:

**************************************************
Notes:

*******************************************************************************/

package com.inga.utils;

import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTree;
import org.apache.log4j.Logger;

/*
 * SigarUtils.java
 *
 * Created on Jan 30th 2007, 10:32
 * @author Manuel Camilo Cuesta
 *
 */
public class SigarUtils {

  private static Logger log = Logger.getLogger( SigarUtils.class );
     
  public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
  public static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    

  public static final String FECHA1 = "dd/MM/yyyy HH:mm:ss";
  public static final String FECHA2 = "MM/dd/yyyy";
  public static final String FECHA3 = "dd/MM/yyyy";
  public static final String FECHA4 = "dd/MM/yyyy HH:mm:ss.SSS";
  public static final String ORACLE_FECHA1 = "dd/mm/yyyy hh24:mi:ss";
  public static final String POSTGRES_FECHA4 = "DD/MM/YYYY HH24:MI:SS.MS";
  public static final String FECHA5 = "yyyy-MM-dd HH:mm:ss.SSS";
    
    
  private static NumberFormat nf = NumberFormat.getInstance();
  private static SimpleDateFormat _df = new SimpleDateFormat(FECHA2);
  
  public final static long ONE_SECOND = 1000;
  public final static long ONE_MINUTE = ONE_SECOND * 60;
  public final static long ONE_HOUR   = ONE_MINUTE * 60;
  public final static long ONE_DAY    = ONE_HOUR * 24;    
    
  /** Fecha inicial por defecto para calcular los tiempos acumulados de estados -> Enero 1, 2006 */
  public static final Date DEFAULT_FECHA_INICIO = new Fecha(1,1,2007);
    
  

    public static String replace(String input, String find, String replacement) {
        
        Pattern p = Pattern.compile(find);
        Matcher m = p.matcher( input );
        
        return m.replaceAll( replacement );
        
    }
    
    
    public static String tolistString(Collection c, String separator, String wrap) {
        String listStr = "";
        Iterator ite = c.iterator();
        while ( ite.hasNext() )
        {
            String item = (String) ite.next();
            
            if ( listStr.length() > 0 )
                listStr = listStr + separator;
            
            listStr = listStr + wrap + item + wrap;
        }
        return listStr;
    }
    
    public static Vector stringListToVector(String listStr, String separator ) {
        String items[] = listStr.split(separator);
        Vector<String> v = new Vector<String>();
        
        for ( int i = 0; i < items.length; i++ )
            v.add( items[i] );
                
        return v;
    }

    /** Hace rollback de todas las transacciones hasta el momento */
    public static void rollback(java.sql.Connection conn) throws BDException, NoConnectionException {
        try
        {
            conn.rollback();
            log.info("Rolled back");
        }
        catch ( NullPointerException nE)
        {
            nE.printStackTrace();
            throw new NoConnectionException();
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new BDException(ex);
        }
    }
    
    /** Hace commit de todas las transacciones hasta el momento */
    public static void commit(java.sql.Connection conn) throws BDException, NoConnectionException {
        try 
        {
            conn.commit();
        } 
        catch ( NullPointerException nE)
        {
            nE.printStackTrace();
            throw new NoConnectionException();
        }
        catch (SQLException ex) 
        {
            ex.printStackTrace();
            throw new BDException(ex);
        }
        
    }
    
    /** Marca el inicio de una transacción */
    public static Savepoint begin(java.sql.Connection conn) throws BDException, NoConnectionException {
        try
        {
            return conn.setSavepoint();
        }
        catch ( NullPointerException nE)
        {
            nE.printStackTrace();
            throw new NoConnectionException();
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new BDException(ex);
        }
        
    }

    /** Hace rollback de una transacción hasta el punto de especificado sp */
    public static void rollback(Savepoint sp, java.sql.Connection conn) throws BDException, NoConnectionException {
        try
        {
            conn.rollback(sp);
            log.info( "Rolled back");
        }
        catch ( NullPointerException nE)
        {
            nE.printStackTrace();
            throw new NoConnectionException();
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new BDException(ex);
        }
    }
    
    public static void setAutoCommit(java.sql.Connection conn,boolean valor) throws BDException{
        try{
            conn.setAutoCommit(valor);
        }
        catch (Exception e){
            throw new BDException(e);
        }
    }
    
    public static String validarCadena(String value) {
        if ( value != null && value.trim().length() > 0 && !value.equalsIgnoreCase("null") )
            return value.trim();
        else
            return null;
    }
    
    public static Integer parseInt(String value) {
        try
        {
            int temp = Integer.parseInt( value );
            return new Integer(temp);
        }
        catch ( Exception e )
        {
            return null;
        }
        
    }   

    public static Double parseDouble(String value) {
        try
        {
            double temp = Double.parseDouble( value );
            return new Double(temp);
        }
        catch ( Exception e )
        {
            return null;
        }

    }
    
    public static BigDecimal parseDecimal(String value) {
        try
        {
            return new BigDecimal( value );
        }
        catch ( Exception e )
        {
            return null;
        }
        
    }   
    
    public static Date parseDate(String value) {
        Date date;
        try
        {
            date = _df.parse(value);
        }
        catch ( Exception e )
        {
            date = null;
        }    
        return date;
    }

    public static Date parseDate(String value, String format) {
        Date date;
        SimpleDateFormat sd = new SimpleDateFormat(format);
        try
        {
            date = sd.parse(value);
        }
        catch ( Exception e )
        {
            date = null;
        }
        return date;
    }


    
   public static boolean matches(String pattern, String input) {
    
     // Se reemplaza '.' por '\.'
     String regex = pattern.replaceAll("\\.", "\\\\.");
     // Se reemplaza '*' por '.*'
     regex = regex.replaceAll("\\*", ".*");
    
     boolean matches = input.matches(regex);
     //System.out.println( "Does " + input + " match " + pattern + " ? " + matches );
     return matches;
     
   }
   
   /** Ignore case */
   public static boolean imatches(String pattern, String input) {
       
     String patternLow = pattern.toLowerCase();
     String inputLow = input.toLowerCase();
     return matches( patternLow , inputLow );
    
   }
    
public static String millisecondToDHMS(long duration) {
    String res = "";
    long temp = 0;
    if (duration >= ONE_SECOND) {
       temp = duration / ONE_DAY;
       if (temp > 0) {
         res = temp + " day";
         if (temp > 1) {
            res += "s";
         }
         duration -= temp * ONE_DAY;

         if (duration >= ONE_MINUTE) {
            res += ", ";
         }
       }

       temp = duration / ONE_HOUR;
       if (temp > 0) {
          res += temp + "h";
          if (temp > 1) {
             res += "s";
          }
          duration -= temp * ONE_HOUR;
          if (duration >= ONE_MINUTE) {
             res += ", ";
          }
       }

       temp = duration / ONE_MINUTE;
       if (temp > 0) {
          res += temp + "min";
          if (temp > 1) {
              res += "s";
          }
          duration -= temp * ONE_MINUTE;

          if(duration >= ONE_SECOND) {
              res += ", ";
          }
       }

       temp = duration / ONE_SECOND;
       if (temp > 0) {
          res += temp + "sec";
          if (temp > 1) {
              res += "s";
          }
       }
       return res;
    }
    else {
       return "0 segundos";
    }
  }   

    public static String getPercentage(long duration,long lapso) {
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        return nf.format( (float) duration / (float) lapso*100 ) + "%";
    }
    
    public static void printList( List results ) {
        
        for ( Object o : results )
            System.out.println( o.toString() );
        
        System.out.println( results.size() + " records.");
    }    
    
    public static Properties loadProperties(String filename) throws FileNotFoundException, IOException {
        File file = new File( filename );
        FileInputStream fin = new FileInputStream(file);
        log.info("Reading configuration file: " + file.getAbsolutePath() );
        Properties props = new Properties();
        props.load( fin );
        fin.close();
        return props;
    }
    
    public static Connection getConnection(String driver, String url, String user, String password) throws BDException {
        
        try
        {
    
          Class.forName(driver).newInstance();
          Connection conn = DriverManager.getConnection(url,user,password);

          log.debug("Using connection: " + conn );
          return conn;
            
        }
        catch ( Exception e )
        {
            throw new BDException(e);
        }
    }
    
    public static void closeConnection( Connection conn) {
        try
        {
            log.info( "Closing conection " + conn );
            conn.close();
        }
        catch ( Exception e )
        {
            log.debug( "Could not close connection : " + e.getMessage() );
        }
    }
    
    
    public String organizarSentencia(String messedUpQuery) {
        return messedUpQuery;
    }
    
    public static DateRange getSafeDateRange(Date fechaInicio, Date fechaFin ) {
        
        // Si no hay fecha inicial, la fecha inicial por defecto es la constante dada
        if ( fechaInicio == null )
            fechaInicio = DEFAULT_FECHA_INICIO;

        // Fecha actual
        Date now = new Date();

        // Si se provee una fecha fin, pero es futura, la fecha fin queda como el momento actual
        if ( fechaFin != null )
        {
            if ( fechaFin.getTime() > now.getTime() )
                fechaFin = now;
        }
        // Si no hay fecha final, la fecha final por defecto es el momento actual
        else if ( fechaFin == null ) 
            fechaFin = now;
        
        
        
        return new DateRange( fechaInicio, fechaFin );
        
    }
    
    public static String talk(String host, int port, String request) throws UnknownHostException, IOException {
        
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String response = null;

        echoSocket = new Socket(host, port );
        out = new PrintWriter(echoSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(
                                    echoSocket.getInputStream()));

        out.println(request);
        response = in.readLine();

        out.close();
        in.close();
        echoSocket.close();


        return response;
            
    }
    
    public static String talk(Socket socket, String request) throws IOException  {
        
        PrintWriter out = null;
        BufferedReader in = null;
        String response = null;

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(
                                    socket.getInputStream()));

        out.println(request);
        response = in.readLine();

        out.close();
        in.close();
        socket.close();


        return response;
            
    }
        
    
    public static void saveToFile( List<String> list , String filename, boolean append ) throws FileNotFoundException, IOException {
        FileWriter f = new FileWriter(filename, append);
        PrintWriter out  = new PrintWriter(f);
                
        
        Iterator<String> ite = list.iterator();
        while ( ite.hasNext() )
            out.println( ite.next() );
        
        out.close();
        f.close();
    }    
    
    public static ArrayList<String> loadStrings(String filename) throws FileNotFoundException, IOException {
        ArrayList<String> lines = new ArrayList<String>();

        FileInputStream fin = new FileInputStream(filename);
        BufferedReader bin = new BufferedReader(new InputStreamReader(fin));

        String line;
        while ( (line = bin.readLine()) != null ) 
            lines.add(line);
        
        fin.close();
        bin.close();
        
        return lines;

    }
    
    public static void storeProperties(Properties props, String filename, String message) {

        try
        {
            FileOutputStream fout = new FileOutputStream( filename );
            props.store(fout,message);
            fout.close();
        }
        catch ( IOException iox) 
        {
            System.out.println("No se pudo guardar la configuración " + iox.getMessage() );
        }

    }
    
    public static String getText(InputStream in ) throws IOException  {
        
        
        BufferedReader bin = new BufferedReader( new InputStreamReader(in) );

        StringBuilder buffer = new StringBuilder();
        String line;

        while ( (line = bin.readLine()) != null) 
            buffer.append(line + "\n");


        in.close();

        return buffer.toString();
            
        
    }    
    
    public static void collapseAll(JTree tree) {
       int row = tree.getRowCount() - 1;
       while (row >= 0) 
       {
        tree.collapseRow(row);
        row--;
       }
    }

    public static String encrypt(String message, String method ) throws NoSuchAlgorithmException {


        byte[] defaultBytes = message.getBytes();

        MessageDigest algorithm = MessageDigest.getInstance( method );
        algorithm.reset();

        algorithm.update(defaultBytes);
        byte messageDigest[] = algorithm.digest();

        System.out.println( messageDigest.length + " bytes");

        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<messageDigest.length;i++) 
            hexString.append(Integer.toHexString( 0xff & messageDigest[i]));
        

        return hexString.toString();


    }

    public static String escape(String in) {

        if ( in == null )
            return null;

        String out = new String(in);

        out = out.replaceAll("'", "''");
        out = out.replaceAll("\\\\", "\\\\\\\\");

        return out;
    }


    public static Boolean validarBoleano(String bool) {
        if ( bool != null )
        {
            if ( bool.toLowerCase().startsWith("t") )
                return true;
            else
                return false;
        }
        else
            return null;
    }

    public static String escapeHtml( String text ) {
       return text.replace("<","&lt;").replace(">","&gt;");
    }

    public static long copy(InputStream in, OutputStream out ) throws IOException {
        byte[] buffer = new byte[1024];
        int numRead;
        long numWritten = 0;
        while ((numRead = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, numRead);
            numWritten += numRead;
        }
        return numWritten;
    }

    public static String ellipsis(String text, int maxLength ) {
        if ( text == null )
            return null;
        if ( text.length() > maxLength )
        {
            return text.substring(0,maxLength) + "..." ;
        }
        else
        {
            return text;
        }
    }

    public static String breakWord(String text, int maxWordLength) {

        if ( text == null )
            return null;

        int size = text.length();
        String notSpace = "\\S";
        int cont = 0;

        for ( int i = 0; i < size; i++ )
        {
            if ( String.valueOf(text.charAt(i)).matches(notSpace) )
            {
                if ( ++cont % maxWordLength == 0 )
                    text = text.substring(0, i) + " " + text.substring(i,text.length());
            }
            else
            {
                if ( cont > 0 )
                    cont = 0;
            }
        }

        return text;

    }

    public static BufferedImage shrink(BufferedImage source, double factor) {
        int w = (int) (source.getWidth() * factor);
        int h = (int) (source.getHeight() * factor);

        Image image = source.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = result.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return result;
        
    }


    
}
