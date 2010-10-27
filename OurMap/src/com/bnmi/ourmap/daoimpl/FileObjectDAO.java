/*******************************************************************************

com.bnmi.ourmap.daoimpl.FileObjectDAO.java
Version: 1.0

********************************************************************************
Original Authors:
Manuel Cuesta, lead programmer <camilocuesta@hotmail.com>
Angus Leech, lead designer <alpinefabulist@yahoo.com>
Full credits at: <http://www.ourmapmaker.ca/content/about-ourmap/credits>  

For questions or comments please contact us at: [ourmap@ourmapmaker.ca]
********************************************************************************

OurMap is Copyright (c) 2010, The Banff Centre <ourmap@ourmapmaker.ca>
All rights reserved.

Published under the terms of the new BSD license.

See  <www.ourmapmaker.ca/content/about-ourmap>  for more information about the 
OurMap software and the license.

Full sourcecode, documentation and license info is also available here:
http://github.com/OurMap/OurMap

LICENSE:

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of The Banff Centre nor the names of its contributors may be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


********************************************************************************
Revision History / Change Log:

Version 1.0 released Oct.2010

********************************************************************************
Notes:

*******************************************************************************/
package com.bnmi.ourmap.daoimpl;

import com.bnmi.ourmap.Constantes;
import com.bnmi.ourmap.dao.ObjectDAO;
import com.bnmi.ourmap.model.EasyObject;
import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.exception.RegistroNoExisteException;
import com.inga.utils.SigarUtils;
import com.inga.utils.SqlClauseHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;

public class FileObjectDAO extends PostgresObjectDAO implements ObjectDAO {

    private static Logger log = Logger.getLogger(FileObjectDAO.class);

    private static String baseDir = "/usermedia";

    /**
     * @return the baseDir
     */
    public static String getBaseDir() {
        return baseDir;
    }

    /**
     * @param aBaseDir the baseDir to set
     */
    public static void setBaseDir(String aBaseDir) {
        baseDir = aBaseDir;
    }

    @Override
    protected Integer createObject(EasyObject registro, boolean isTemp ) throws NoConnectionException, BDException {

        Integer pk;
        String tableName;
        boolean image = false;
        if ( isTemp )
        {
            pk = PostgresUtilDAO.getNextVal(user,conn,"sq_temp_objects");
            tableName = "temp_objects";
        }
        else
        {
            pk = PostgresUtilDAO.getNextVal(user,conn,"sq_objects");
            tableName = "objects";
        }
        if ( registro.getObjType() != null && registro.getObjType().intValue() == Constantes.IMAGE )
            image = true;


        registro.setObjectId( pk );
        String url = getFullpath( registro , tableName, "" );
        registro.setPath( url );
        
        this.executeUpdate( this.getCreateStr(registro, isTemp));

        if ( registro.getObjData() != null )
            writeData(registro.getObjectId(), registro.getObjData(), 1, tableName );

        if ( registro.getObjData2() != null )
            writeData(registro.getObjectId(), registro.getObjData2(), 2, "thumb_" + tableName );


        return pk;

    }


    @Override
    public int delete( java.lang.Integer objectId ) throws NoConnectionException, BDException {

        EasyObject eo = null;

        try
        {
            eo = this.get(objectId);
        }
        catch ( Exception ex )
        {
            eo = null;
        }

        int rows = super.delete( objectId );

        String tablename = "objects";
        
        String filename = getFullpath( eo, tablename );
        File f = new File(filename);
        if ( f.exists() )
           f.delete();

        filename = getFullpath( eo, "thumb_" + tablename );
        f = new File(filename);
        if ( f.exists() )
           f.delete();


        return rows;
    }

    @Override
    public void copyDataFromTemp(Integer tempObjectId, Integer objectId ) throws NoConnectionException, BDException {


        try
        {


            EasyObject tempEo = this.getTemp(tempObjectId);
            EasyObject eo = this.get(objectId);

            String tempFilename = baseDir + "/" + tempEo.getPath();
            String eoFile = getFullpath( eo, "objects" );

            String eoPath = getPath( eo, "objects" );
            File realFile = new File( eoFile );
            File tempFile = new File( tempFilename );
            File dir = new File(eoPath);


            if ( tempFile.exists() )
            {

                if ( !dir.exists() )
                    dir.mkdirs();

                if ( realFile.exists())
                {
                    if ( ! realFile.canWrite() )
                        throw new IOException("Can't overwrite file " + realFile );
                }
                else
                {
                    if ( ! dir.canWrite() )
                        throw new IOException("Can't write to directory " + dir );
                }

                FileInputStream in = new FileInputStream( tempFile );
                FileOutputStream out = new FileOutputStream( realFile );
                SigarUtils.copy(in, out);
                in.close();
                out.close();

                EasyObject updatedObject = new EasyObject();
                updatedObject.setObjectId(objectId);
                updatedObject.setPath( getFullpath( eo, "objects", "" ) );
                update( updatedObject );

                if ( tempEo.getObjType() != null && tempEo.getObjType().intValue() == Constantes.IMAGE )
                {

                    String tempThumbFilename = getFullpath( tempEo, "thumb_temp_objects" );
                    File tempThumbFile = new File( tempThumbFilename );

                    if ( tempThumbFile.exists() )
                    {
                        String thumbPath = getPath( eo, "thumb_objects" );
                        File thumbDir = new File( thumbPath );
                        String thumbFilename = getFullpath( eo, "thumb_objects" );
                        File thumbFile = new File( thumbFilename );

                        if ( ! thumbDir.exists() )
                            thumbDir.mkdirs();

                        if ( thumbFile.exists() )
                        {
                            if ( ! thumbFile.canWrite() )
                                throw new IOException( "Can't overwrite file " + thumbFile );
                        }
                        else
                        {
                            if ( ! thumbDir.canWrite() )
                                throw new IOException( "Can't write to directory " + thumbDir );
                        }

                        in = new FileInputStream( tempThumbFile );
                        out = new FileOutputStream( thumbFile );
                        SigarUtils.copy(in, out);
                        in.close();
                        out.close();

                    }
                }

            }
            



        }
        catch ( RegistroNoExisteException rex )
        {

        }
        catch ( IOException iox )
        {
           throw new BDException(iox);
        }


        /**@todo get the temp and the real object and then get the temp file and copy to the real destination */

    }


    @Override
    public int deleteTemp(Integer objectId) throws NoConnectionException, BDException {

        EasyObject eo = null;

        try
        {
            eo = this.getTemp(objectId);
        }
        catch ( Exception ex )
        {
            eo = null;
        }

        int rows = super.deleteTemp(objectId);

        String filename = getFullpath( eo, "temp_objects" );
        File f = new File(filename);
        if ( f.exists() )
           f.delete();

        filename = getFullpath( eo, "thumb_temp_objects" );
        f = new File(filename);
        if ( f.exists() )
           f.delete();

        return rows;
        
   }

    @Override
    public void writeData(Integer objectId, byte[] data, int datafield, String table) throws BDException, NoConnectionException {

        try
        {

            if ( data == null )
                return;
            
            EasyObject eo = null;
            
            if ( table.equalsIgnoreCase("objects") ||
                 table.equalsIgnoreCase("thumb_objects")
               )
                eo = this.get(objectId);
            if ( table.equalsIgnoreCase("temp_objects") ||
                 table.equalsIgnoreCase("thumb_temp_objects")
               )
                eo = this.getTemp(objectId);

            String directory = getPath( eo , table );
            File dir = new File( directory );
            if ( ! dir.exists() )
                dir.mkdirs(); 
            
            String fullpath = getFullpath(eo, table);
            File file = new File( fullpath );
            if ( file.exists() )
            {
                if ( ! file.canWrite() )
                    throw new IOException("Can't overwrite file " + file );
            }
            else
            {
                if ( ! dir.canWrite() )
                    throw new IOException("Can't write to directory " + dir );

            }

            FileOutputStream fout = new FileOutputStream( fullpath );
            fout.write( data );
            fout.close();

            
        }
        catch ( RegistroNoExisteException ex )
        {
           log.info(ex.getMessage());
        }
        catch ( IOException iox )
        {
            new BDException( iox );
        }

    }

    public static String getFilename( EasyObject eo ) {
        SqlClauseHelper sh = new SqlClauseHelper();
        sh.append( String.valueOf( eo.getObjectId()) );
        if ( eo.getExtension() != null )
            sh.append( "." , eo.getExtension() );
        return sh.toString().toLowerCase();
    }

    public static String getFullpath( EasyObject eo, String table) {
        return getFullpath( eo, table, baseDir );
    }

    public static String getFullpath( EasyObject eo, String table, String base ) {
        String directory = getPath( eo, table, base );
        String filename = getFilename( eo );
        return directory + "/" + filename ;
    }

    public static String getPath(EasyObject eo, String table) {
        return getPath(eo,table,baseDir);
    }

    public static String getPath(EasyObject eo, String table, String base ) {

        SqlClauseHelper sh = new SqlClauseHelper();

        sh.append( base );
        if ( table.equalsIgnoreCase("temp_objects") )
        {
            sh.append( "/" , "temp");
        }
        if ( table.equalsIgnoreCase("objects") )
        {

        }
        if ( table.equalsIgnoreCase("thumb_temp_objects") )
        {
            sh.append("/", "temp");
            sh.append( "/", "thumb");
        }
        if ( table.equalsIgnoreCase("thumb_objects") )
        {
            sh.append( "/", "thumb");
        }

        if ( eo.getObjType() != null && eo.getObjType().intValue() == Constantes.ICON )
            sh.append( "/", "icons" );
        else
        {
            if ( eo.getCreatedBy() != null && eo.getCreatedBy().length() > 0 )
                sh.append( "/", eo.getCreatedBy() );
        }


        return sh.toString();
    }




 }
