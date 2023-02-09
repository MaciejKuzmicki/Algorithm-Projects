package pl.edu.pw.ee;

import java.util.ArrayList;

public class LongestCommonSubsequence {

    private final String topStr;
    private final String leftStr;

    private final int w;
    private final int k;

    private Element[] array;

    private ArrayList<Integer> path = new ArrayList<>();

    public LongestCommonSubsequence(String topStr, String leftStr) {
        if (topStr == null || leftStr == null) {
            throw new NullPointerException();
        }
        this.topStr = topStr;
        this.leftStr = leftStr;
        this.k = topStr.length() + 1;
        this.w = leftStr.length() + 1;
    }

    public String findLCS() {
        makeAnArray();
        String output = "";
        int current;
        for (int i = 1; i < w; i++) {
            for (int j = 1; j < k; j++) {
                current = j + i * k;
                if (topStr.charAt(j - 1) == leftStr.charAt(i - 1)) {
                    array[current].setNumber(array[current - k - 1].getNumber() + 1);
                    array[current].setDirection("Up-Left");
                } else {
                    if (array[current - k].getNumber() >= array[current - 1].getNumber()) {
                        array[current].setNumber(array[current - k].getNumber());
                        array[current].setDirection("up");
                    } else {
                        array[current].setNumber(array[current - 1].getNumber());
                        array[current].setDirection("left");

                    }
                }
            }
        }
        current = w * k - 1;
        while (array[current].getNumber() != 0) {
            path.add(current);
            if (array[current].getDirection().equals("left")) {
                current -= 1;
            } else if (array[current].getDirection().equals("up")) {
                current -= k;
            } else if (array[current].getDirection().equals("Up-Left")) {
                current = current - k - 1;
                output = topStr.charAt(current % k) + output;
            }
        }
        return output;
    }


    public void display() {
        if (leftStr.length() == 0 || topStr.length() == 0) {
            throw new IllegalArgumentException("Strings cannot be empty");
        }
        if (array == null) {
            throw new IllegalStateException("This method cannot run before finding LCS");
        }
        displayPlusesAndMinuses();
        displayBlankSpaces();
        displayLettersOnTop();
        displayBlankSpaces();
        for (int i = 0; i < w; i++) {
            displayPlusesAndMinuses();
            displaySigns(i);
            displayNumbersAndLetters(i - 1);
            displayBlankSpaces();
        }
        displayPlusesAndMinuses();
    }

    public void displayNumbersAndLetters(int number) {
        if (number == -1) {
            System.out.print("|     ");
        } else {
            checkSpecialSigns(number, leftStr);
        }
        for (int i = 1; i <= k; i++) {
            int i1 = (number + 1) * k + i - 1;
            if (array[i1].getDirection() == null || !path.contains(i1)) {
                System.out.print("|  " + array[i1].getNumber() + "  ");
            } else if (array[i1].getDirection().equals("left") && path.contains(i1)) {
                System.out.print("|< " + array[i1].getNumber() + "  ");
            } else {
                System.out.print("|  " + array[i1].getNumber() + "  ");
            }
        }
        System.out.print("|\n");
    }

    private void checkSpecialSigns(int number, String string) {
        if (string.charAt(number) == '\n') {
            System.out.print("| \\n  ");
        } else if (string.charAt(number) == '\t') {
            System.out.print("| \\t  ");
        } else if (string.charAt(number) == '\r') {
            System.out.print("| \\r  ");
        } else if (string.charAt(number) == '\"') {
            System.out.print("| \"   ");
        } else if (string.charAt(number) == '\'') {
            System.out.print("| \'   ");
        } else if (string.charAt(number) == '\\') {
            System.out.print("| \\  ");
        } else System.out.print("|  " + string.charAt(number) + "  ");
    }

    public void displaySigns(int w) {
        System.out.print("|     ");
        for (int i = 0; i < k; i++) {
            if (array[w * k + i].getDirection() == null) {
                System.out.print("|     ");
            } else if (array[w * k + i].getDirection().equals("up") && path.contains(w * k + i)) {
                System.out.print("|  ^  ");
            } else if (array[w * k + i].getDirection().equals("Up-Left") && path.contains(w * k + i)) {
                System.out.print("|\\    ");
            } else {
                System.out.print("|     ");
            }
        }
        System.out.println("|");
    }

    public void makeAnArray() {
        array = new Element[w * k];
        for (int i = 0; i < w * k; i++) {
            array[i] = new Element(0);
        }
    }

    public void displayPlusesAndMinuses() {
        for (int i = 0; i <= k; i++) {
            System.out.print("+-----");
        }
        System.out.print("+\n");
    }

    public void displayBlankSpaces() {
        for (int i = 0; i <= k; i++) {
            System.out.print("|     ");
        }
        System.out.print("|\n");
    }

    public void displayLettersOnTop() {
        System.out.print("|     |     ");
        for (int i = 0; i <= k - 2; i++) {
            checkSpecialSigns(i, topStr);
        }
        System.out.println("|");
    }

}
