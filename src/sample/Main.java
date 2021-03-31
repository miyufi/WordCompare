package sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main
{
    public static String getKey(String str) {
        char[] arr = str.toLowerCase().toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }

    static boolean strmatch(String str, String pattern,
                            int n, int m)
    {
        if (m == 0)
            return (n == 0);

        boolean[][] lookup = new boolean[n + 1][m + 1];

        for (int i = 0; i < n + 1; i++)
            Arrays.fill(lookup[i], false);

        lookup[0][0] = true;

        for (int j = 1; j <= m; j++)
            if (pattern.charAt(j - 1) == '*')
                lookup[0][j] = lookup[0][j - 1];

        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                if (pattern.charAt(j - 1) == '*')
                    lookup[i][j] = lookup[i][j - 1]
                            || lookup[i - 1][j];

                else if (pattern.charAt(j - 1) == '?'
                        || str.charAt(i - 1)
                        == pattern.charAt(j - 1))
                    lookup[i][j] = lookup[i - 1][j - 1];

                else
                    lookup[i][j] = false;
            }
        }
        return lookup[n][m];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String[] mixedup = new String [1];
        String key, match = null;

        System.out.print("Test Word: ");
        Scanner s = new Scanner(System.in);

        mixedup[0] = s.nextLine();

        ArrayList<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("wordlist.txt"))) {
            while (br.ready()) {
                result.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] correct = result.toArray(new String[0]);

        Map<String, String> lookup = new HashMap<>(correct.length);
        for (String str : correct) {
            lookup.put(getKey(str), str);
        }

        for (String mix : mixedup) {
                key = getKey(mix);
                match = lookup.get(key);
                break;
        }
        String[] str = {};
        String[] pattern = mixedup;

        System.out.println(match);

        for (int i = 0; i < str.length; i++) {
            if (strmatch(str[i], pattern[0], str.length,
                    pattern.length))
                System.out.println(str[i]);
        }
    }
}