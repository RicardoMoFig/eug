/*
 * EU3History.java
 *
 * Created on May 9, 2007, 1:33 PM
 */

package eug.specific.eu3;

import eug.shared.GenericObject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Class which deals with EU3 histories, such as are found in the history
 * subfolder or in saved games.
 * @since EUGFile 1.04.00pre1
 * @author Michael Myers
 */
public final class EU3History {
    
    private EU3History() { }
    
    
    private static final Comparator<String> DATE_COMPARATOR =
            new Comparator<String>() {
        
        private final Map<String, String[]> splitMap =
                new HashMap<String, String[]>(100);
        
        private final Pattern dot = Pattern.compile("\\.");
        
        private final String[] split(final String s) {
            String[] split = splitMap.get(s);
            if (split == null) {
                split = dot.split(s);
                splitMap.put(s, split);
            }
            return split;
        }
        
        private final Map<String, Integer> intMap =
                new HashMap<String, Integer>(100);
        
        private final Integer getInt(final String s) {
            Integer i = intMap.get(s);
            if (i == null) {
                i = Integer.valueOf(s);
                intMap.put(s, i);
            }
            return i;
        }
        
        public final int compare(final String s1, final String s2) {
            final String[] s1Split = split(s1);
            final String[] s2Split = split(s2);
            
            int ret = getInt(s1Split[0]).compareTo(getInt(s2Split[0]));
            if (ret != 0)
                return ret;
            
            ret = getInt(s1Split[1]).compareTo(getInt(s2Split[1]));
            if (ret != 0)
                return ret;
            
            return getInt(s1Split[2]).compareTo(getInt(s2Split[2]));
        }
    };
    
//    private static final Comparator<GenericObject> DATE_OBJECT_COMPARATOR =
//            new Comparator<GenericObject>() {
//        
//        private final Map<String, String[]> splitMap =
//                new HashMap<String, String[]>(100);
//        
//        private final String[] split(final String s) {
//            String[] split = splitMap.get(s);
//            if (split == null) {
//                split = s.split("\\.");
//                splitMap.put(s, split);
//            }
//            return split;
//        }
//        
//        private final Map<String, Integer> intMap =
//                new HashMap<String, Integer>(100);
//        
//        private final Integer getInt(final String s) {
//            Integer i = intMap.get(s);
//            if (i == null) {
//                i = Integer.valueOf(s);
//                intMap.put(s, i);
//            }
//            return i;
//        }
//        
//        public final int compare(final GenericObject o1, final GenericObject o2) {
//            final String[] s1Split = split(o1.name);
//            final String[] s2Split = split(o2.name);
//            
//            int ret = getInt(s1Split[0]).compareTo(getInt(s2Split[0]));
//            if (ret != 0)
//                return ret;
//            
//            ret = getInt(s1Split[1]).compareTo(getInt(s2Split[1]));
//            if (ret != 0)
//                return ret;
//            
//            return getInt(s1Split[2]).compareTo(getInt(s2Split[2]));
//        }
//    };
    
    private static final Pattern DATE_PATTERN =
            Pattern.compile("[0-9]{1,4}\\.[0-9]{1,2}\\.[0-9]{1,2}");
    
    
    public static final GenericObject getHistObject(final GenericObject history, String name) {
        if (history == null)
            return null;
        
        GenericObject value = history.getChild(name);
        String lastDate = "0.0.0";
        for (GenericObject date : history.children) {
            if (!DATE_PATTERN.matcher(date.name).matches()) {
                if (!date.name.equals("advisor"))
                    System.err.println(date.name + " is not a valid date");
                continue;
            }
            
            if (DATE_COMPARATOR.compare(date.name, lastDate) >= 0) {
                GenericObject newVal = date.getChild(name);
                if (newVal != null) {
                    value = newVal;
                    lastDate = date.name;
                }
            }
        }
        return value;
    }
    
    public static final GenericObject getHistObject(final GenericObject history, String name, String date) {
        if (history == null)
            return null;
        
        GenericObject value = history.getChild(name);
        String lastDate = "0.0.0";
        for (GenericObject dateObj : history.children) {
            if (!DATE_PATTERN.matcher(dateObj.name).matches()) {
                if (!dateObj.name.equals("advisor"))
                    System.err.println(dateObj.name + " is not a valid date");
                continue;
            }
            
            if (DATE_COMPARATOR.compare(dateObj.name, lastDate) >= 0 &&
                    DATE_COMPARATOR.compare(dateObj.name, date) <= 0) {
                // The new date is after the old date and before or equal to the target date
                GenericObject newVal = dateObj.getChild(name);
                if (newVal != null) {
                    value = newVal;
                    lastDate = dateObj.name;
                }
            }
        }
        return value;
    }
    
    
    public static final String getHistString(final GenericObject history, String name) {
        if (history == null)
            return null;
        
        String value = history.getString(name);
        String lastDate = "0.0.0";
        for (GenericObject date : history.children) {
            if (!DATE_PATTERN.matcher(date.name).matches()) {
                if (!date.name.equals("advisor"))
                    System.err.println(date.name + " is not a valid date");
                continue;
            }
            
            if (DATE_COMPARATOR.compare(date.name, lastDate) >= 0) {
                String newVal = date.getString(name);
                if (newVal.length() != 0) {
                    value = newVal;
                    lastDate = date.name;
                }
            }
        }
        return value;
    }
    
    public static final String getHistString(final GenericObject history, String name, String date) {
        if (history == null)
            return null;
        
        String value = history.getString(name);
        String lastDate = "0.0.0";
        for (GenericObject dateObj : history.children) {
            if (!DATE_PATTERN.matcher(dateObj.name).matches()) {
                if (!dateObj.name.equals("advisor"))
                    System.err.println(dateObj.name + " is not a valid date");
                continue;
            }
            
            if (DATE_COMPARATOR.compare(dateObj.name, lastDate) >= 0 &&
                    DATE_COMPARATOR.compare(dateObj.name, date) <= 0) {
                // The new date is after the old date and before or equal to the target date
                String newVal = dateObj.getString(name);
                if (newVal.length() != 0) {
                    value = newVal;
                    lastDate = dateObj.name;
                }
            }
        }
        return value;
    }
    
    /** @since EUGFile 1.05.00pre1 */
    public static final List<String> getHistStrings(final GenericObject history, String name, String date) {
        if (history == null)
            return null;
        
        final List<String> values = history.getStrings(name);
        
        for (GenericObject dateObj : history.children) {
            if (!DATE_PATTERN.matcher(dateObj.name).matches()) {
                if (!dateObj.name.equals("advisor"))
                    System.err.println(dateObj.name + " is not a valid date");
                continue;
            }
            
            if (DATE_COMPARATOR.compare(dateObj.name, date) <= 0) {
                // The new date is before or equal to the target date
                String value = dateObj.getString(name);
                if (value.length() != 0) {
                    values.add(value);
                }
            }
        }
        
        return values;
    }
    
    /** @since EUGFile 1.05.00pre1 */
    public static final List<String> isCoreOf(final GenericObject history, String date) {
        if (history == null)
            return null;
        
        final List<String> values = history.getStrings("add_core");
        
//        java.util.Collections.sort(history.children, DATE_OBJECT_COMPARATOR);
        
        for (GenericObject dateObj : history.children) {
            if (!DATE_PATTERN.matcher(dateObj.name).matches()) {
                if (!dateObj.name.equals("advisor"))
                    System.err.println(dateObj.name + " is not a valid date");
                continue;
            }
            
            if (DATE_COMPARATOR.compare(dateObj.name, date) <= 0) {
                values.removeAll(dateObj.getStrings("remove_core"));
                values.addAll(dateObj.getStrings("add_core"));
            }
        }
        
        return values;
    }
}
