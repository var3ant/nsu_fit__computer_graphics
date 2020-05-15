package Borzov;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private InitView view;
    private List<String> errors;
    private int k,n,m;
    private double a,b,c,d;
    private int[][] newrgbs;
    public Parser(InitView view) {
        this.view = view;
        errors = new ArrayList<>();
    }

    public String[] pars(String filePath){
        FileReader fr = null;
        try {
            fr = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            return new String[]{e.getMessage()};
        }
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(fr);
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
                return new String[]{e.getMessage()};
            }
            lines.add(line);
        }
        parsABCD(lines.get(0));
        parsNM(lines.get(1));
        parsK(lines.get(2));
        StringBuilder builder = new StringBuilder();
        for(int i=3;i<lines.size();i++) {
            builder.append(lines.get(i));
            builder.append(System.getProperty("line.separator"));
        }
        parsRGB(builder.toString());
        view.setNM(n, m);
        view.setABCD(a, b, c, d);
        view.setIsolines(k, newrgbs);
        view.repaint();
        return getErrors();
    }

    private String[] getErrors() {
        String[] toReturn = errors.toArray(new String[0]);
        errors = new ArrayList<>();
        return toReturn;
    }

    public String[] pars(String nmText, String abcdText, String kText, String colorsText) {
        if(!parsABCD(abcdText) || !parsNM(nmText) ||  !parsK(kText) || !parsRGB(colorsText)) {
            return getErrors();
        }
        view.setNM(n, m);
        view.setABCD(a, b, c, d);
        view.setIsolines(k, newrgbs);
        view.repaint();
        return getErrors();
    }
    private boolean parsABCD(String abcdText) {
        String[] abcdTextArr = abcdText.split(",");
        if (abcdTextArr.length != 4) {
            addError("invalid count of arguments in a b c d");
            return false;
        } else {
            try {
                a = Double.parseDouble(abcdTextArr[0]);
                b = Double.parseDouble(abcdTextArr[1]);
                c = Double.parseDouble(abcdTextArr[2]);
                d = Double.parseDouble(abcdTextArr[3]);
            } catch (Exception e1) {
                addError("invalid type of a, b, c or d");
                return false;
            }
        }
        return true;
    }
    private boolean parsK(String kText) {
        try {
            k = Integer.parseInt(kText);
        } catch (Exception e1) {
            addError("invalid type of k");
            return false;
        }
        return true;
    }

    private boolean parsRGB(String colorsText) {
        String[] rgbText = colorsText.split(System.getProperty("line.separator"));
        if (rgbText.length != k + 2) {
            addError("invalid count of rgb, expected " + (k + 2));
            return false;
        }
       newrgbs = new int[k + 2][3];
        for (int i = 0; i < newrgbs.length; i++) {
            String[] rgb = rgbText[i].split(" ");
            if (rgb.length != 3) {
                addError("invalid rgb in line " + (i + 1));
                return false;
            }
            try {
                newrgbs[i][0] = Integer.parseInt(rgb[0]);
                newrgbs[i][1] = Integer.parseInt(rgb[1]);
                newrgbs[i][2] = Integer.parseInt(rgb[2]);
            } catch (Exception e1) {
                addError("invalid format of RGB");
                return false;
            }
        }
        return true;
    }

    private boolean parsNM(String nmText) {
        String[] nmTextArr = nmText.split(" ");
        if (nmTextArr.length != 2) {
            addError("invalid count of arguments in n m");
            return false;
        } else {
            try {
                n = Integer.parseInt(nmTextArr[0]);
                m = Integer.parseInt(nmTextArr[1]);
            } catch (Exception e1) {
                addError("invalid type of N or M");
                return false;
            }
        }
        return true;
    }
    private void addError(String error) {
        this.errors.add(error);
    }
}