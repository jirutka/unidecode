/**
 *
 */
package cz.jirutka.unidecode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Transliterate Unicode string to a valid 7-bit or 8-bit string with
 * characters from the specified charset.
 *
 * @see <a href="https://pypi.python.org/pypi/Unidecode">Python's Unidecode</a>

 * @author <a href="mailto:xuender@gmail.com">ender</a>
 * @author Jakub Jirutka <jakub@jirutka.cz></jakub@jirutka.cz>
 */
public class Unidecode {

    private static final String
            PREFIX = "/cz/jirutka/unidecode/",
            ASCII = PREFIX + "ascii",
            LATIN2 = PREFIX + "latin2";

    /**
     * Array to cache already loaded maps.
     */
    private final String[][] cache = new String[256][];

    /**
     * Resource paths where to look for mapping files.
     */
    private final String[] mapLookupPaths;


    private Unidecode(String[] mapLookupPaths) {
        this.mapLookupPaths = mapLookupPaths;
    }

    public static Unidecode withCharset(String charset) {
        final String[] paths;

        switch (charset.toUpperCase()) {
            case "ISO-8859-2": // next
            case "LATIN-2"   : paths = new String[]{ LATIN2, ASCII }; break;
            case "ASCII"     : paths = new String[]{ ASCII }; break;
            default: throw new IllegalArgumentException("Unknown charset: " + charset);
        }
        return new Unidecode(paths);
    }


    /**
     * Transliterate an Unicode string into an ASCII string.
     *
     * @param str Unicode String to transliterate.
     * @return ASCII string.
     */
    public String decode(String str) {
        if (str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            int codepoint = str.codePointAt(i);

            // Basic ASCII
            if (codepoint < 0x80) {
                sb.append(c);
                continue;
            }
            // Characters in Private Use Area and above are ignored
            if (codepoint > 0xffff) {
                continue;
            }
            int section = codepoint >> 8;   // Chop off the last two hex digits
            int position = codepoint % 256; // Last two hex digits

            String[] table = getCache(section);
            if (table != null && table.length > position) {
                sb.append(table[position]);
            }
        }
        return sb.toString().trim();
    }

    /**
     * Transliterate Unicode string to a initials.
     *
     * @param str Unicode string to transliterate.
     * @return String initials.
     */
    public String toInitials(String str) {

        if (str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        Pattern p = Pattern.compile("^\\w|\\s+\\w");
        Matcher m = p.matcher(decode(str));

        while (m.find()) {
            sb.append(m.group().replaceAll(" ", ""));
        }
        return sb.toString();
    }


    private String[] getCache(int section) {

        String[] ret = cache[section];

        if (ret == null) {
            URL resource = getMapResource(section);
            if (resource == null) {
                return null;
            }
            try (InputStream inStream = resource.openStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

                ret = new String[256];

                int i = 0;
                String line = null;
                while ((line = reader.readLine()) != null) {
                    ret[i] = line;
                    i++;
                }
                cache[section] = ret;

            } catch (IOException ex) {
                // No match: ignore this character and carry on.
                cache[section] = new String[]{};
            }
        } else if (ret.length == 0) {
            return null;
        }
        return ret;
    }

    private URL getMapResource(int section) {

        String fileName = String.format("X%03x", section);

        for (String path : mapLookupPaths) {
            URL resource = getClass().getResource(path + '/' + fileName);
            if (resource != null) {
                return resource;
            }
        }
        return null;
    }
}
