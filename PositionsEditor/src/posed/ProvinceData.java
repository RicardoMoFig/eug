/*
 * ProvinceData.java
 *
 * Created on January 26, 2007, 6:30 PM
 */

package posed;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 *
 * @author Michael Myers
 */
public final class ProvinceData {
    
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(ProvinceData.class.getName());
    
    private java.util.Map<Integer, Province> rgbMap;
    private Province[] allProvs;
    private static final int ALPHA = 0xff << 24;
    
    private static final Pattern SEMICOLON = Pattern.compile(";");
    
    /**
     * Creates a new instance of ProvinceData.
     * @param numProvs the number of provinces in the map.
     * @param defFileName the filename of the province color definitions.
     * @param useLocalization whether to use localized province names or the names found in definition.csv
     * @param provinceLocFmt if useLocalization is true, the localization format is used
     *        to turn the province ID into a key for use in looking up localization
     */
    public ProvinceData(int numProvs, String defFileName, boolean useLocalization, String provinceLocFmt) {
//        final int numProvs = Integer.parseInt(map.getString("max_provinces"));
        
        rgbMap = new HashMap<>(numProvs);
        allProvs = new Province[numProvs];
        
        defFileName = defFileName.replace('\\', '/');
        if (!defFileName.contains("/"))
            defFileName = "map/" + defFileName;
        
        try {
            parseDefs(defFileName, numProvs, useLocalization, provinceLocFmt);
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, "Could not load province definitions", ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Could not load province definitions", ex);
        }
    }
    
    private Integer tryParseInt(String s) {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException ex) {
            try {
                return Double.valueOf(s).intValue();
            } catch (NumberFormatException e2) {
                return null;
            }
        }
    }
    
    private void parseDefs(String fileName, int numProvs, boolean useLocalization, String provinceLocFmt)
            throws FileNotFoundException, IOException {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine();  // eat first line

            for (int i = 1; i < numProvs; i++) {
                String line;
                if ((line = reader.readLine()) == null) {
                    break;
                }
                if (line.isEmpty())
                    continue;

                String[] arr = SEMICOLON.split(line);

                if (arr == null) {
                    log.log(Level.WARNING, "Badly formatted province definition: {0}", line);
                    continue;
                }
                
                Integer id = tryParseInt(arr[0]);
                Integer r = tryParseInt(arr[1]);
                Integer g = tryParseInt(arr[2]);
                Integer b = tryParseInt(arr[3]);
                
                if (id == null) {
                    log.log(Level.WARNING, "Failed to read province id in definition line: {0}", line);
                    continue;
                }
                if (r == null) {
                    log.log(Level.WARNING, "Failed to read province red in definition line: {0}", line);
                    continue;
                }
                if (g == null) {
                    log.log(Level.WARNING, "Failed to read province green in definition line: {0}", line);
                    continue;
                }
                if (b == null) {
                    log.log(Level.WARNING, "Failed to read province blue in definition line: {0}", line);
                    continue;
                }

                int color = ALPHA;
                color += (r & 0xFF) << 16;
                color += (g & 0xFF) << 8;
                color += (b & 0xFF);

                String provName;
                if (useLocalization)
                    provName = Text.getText(String.format(provinceLocFmt, Integer.parseInt(arr[0])));
                else if (arr.length > 4)
                    provName = arr[4];
                else
                    provName = "NO NAME GIVEN #" + id;

                final Province p = new Province(id, provName, color);

                rgbMap.put(color, p);
                allProvs[id] = p;
            }
        }
        
        final Province ti = new Province(0, "Terra Incognita", ALPHA);
        rgbMap.put(ALPHA, ti);
        allProvs[0] = ti;
    }
    
    public Province getProv(final Color color) {
        return getProv(color.getRGB());
    }
    
//    /**
//     * @param rgb a string with three integer components separated by commas.
//     * @return the province with the given color.
//     */
//    public Province getProv(final String rgb) {
//        final String[] components = rgb.split(",");
//        final int red = Integer.parseInt(components[0]);
//        final int green = Integer.parseInt(components[1]);
//        final int blue = Integer.parseInt(components[2]);
//        return getProv(new Color(red, green, blue));
//    }
    
    public Province getProv(final int rgb) {
        return rgbMap.get(rgb);
    }
    
    public Province getProvByID(int id) {
        return allProvs[id];
    }
    
    public Province getProvByName(String name) {
        for (Province prov : rgbMap.values()) {
            if (name.equalsIgnoreCase(prov.name))
                return prov;
        }
        return null;
    }
    
    public int getRGB(int provId) {
        for (java.util.Map.Entry<Integer, Province> e : rgbMap.entrySet()) {
            if (e.getValue().getId() == provId) {
                return e.getKey();
            }
        }
        return 0;
    }
    
    /** @return a shallow copy of the province array. */
    public Province[] getAllProvs() {
        final Province[] ret = new Province[allProvs.length];
        System.arraycopy(allProvs, 0, ret, 0, allProvs.length);
        return ret;
    }
    
    /**
     * Class that encapsulates the data associated with an entry in the province
     * definition file.
     */
    public static final class Province {
        
        private final int id;
        private final String name;
        private final int color;
        
        Province(int id, String name, int color) {
            this.id = id;
            this.name = name;
            this.color = color;
        }
        
        public int getId() {
            return id;
        }
        
        public String getName() {
            return name;
        }
        
        public int getColor() {
            return color;
        }
        
        @Override
        public String toString() {
            return id + " = " + name;
        }
    }
}
