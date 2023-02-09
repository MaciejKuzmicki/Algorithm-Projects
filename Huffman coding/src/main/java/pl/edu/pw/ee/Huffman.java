package pl.edu.pw.ee;

import java.io.*;
import java.util.*;

public class Huffman {
    private String currentResultForCodingValues = "";
    private int bytesAfterCompression = 0;
    private int bytesAfterDeCompression = 0;

    public int huffman(String pathToRootDir, boolean compress) {
        try {
            if (compress) {
                return compress(iterateThroughFiles(new File(pathToRootDir).listFiles(), ".txt").getAbsolutePath());
            } else {
                return decompress(iterateThroughFiles(new File(pathToRootDir).listFiles(), ".cmp").getAbsolutePath());
            }
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    public int compress(String pathToFile) {
        int[] characterOccurrence = readFileToCountCharacters(pathToFile);
        Node head = buildTree(characterOccurrence);
        HashMap<Integer, String> decodedValues = new HashMap<>();
        getValuesWhenCompress(head, decodedValues);
        readFileToCompressData(pathToFile, decodedValues);
        writeCounterToFile(characterOccurrence, pathToFile);
        return bytesAfterCompression * 8;
    }

    public File iterateThroughFiles(File[] files, String extension) {
        for (File file : files) {
            if (file.isDirectory()) {
                throw new IllegalArgumentException("In this directory should not be any other directories");
            } else if (file.getAbsolutePath().substring(file.getAbsolutePath().length() - 4).equals(extension)) {
                return file;
            }
        }
        return null;
    }

    public Node buildTree(int[] characterOccurrence) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < 256; i++) {
            if (characterOccurrence[i] > 0) {
                priorityQueue.add(new Node(characterOccurrence[i], i));
            }
        }
        while (priorityQueue.size() > 1) {
            priorityQueue.add(linkNodes(priorityQueue.poll(), priorityQueue.poll()));
        }
        Node head = priorityQueue.poll();
        return head;
    }

    public void getValuesWhenCompress(Node head, HashMap<Integer, String> map) {
        if (head != null) {
            if (head.getKey() != -1) {
                map.put(head.getKey(), currentResultForCodingValues);
                if (currentResultForCodingValues.length() == 0) return;
                currentResultForCodingValues = currentResultForCodingValues.substring(0, currentResultForCodingValues.length() - 1);
                return;
            }
            if (head.getLeft() != null) {
                currentResultForCodingValues += "1";
                getValuesWhenCompress(head.getLeft(), map);
            }
            if (head.getRight() != null) {
                currentResultForCodingValues += "0";
                getValuesWhenCompress(head.getRight(), map);
            }
            if (currentResultForCodingValues.length() == 0) return;
            currentResultForCodingValues = currentResultForCodingValues.substring(0, currentResultForCodingValues.length() - 1);
        }
    }

    public void getValuesWhenDecompress(Node head, HashMap<String, Integer> map) {
        if (head != null) {
            if (head.getKey() != -1) {
                map.put(currentResultForCodingValues, head.getKey());
                if (currentResultForCodingValues.length() == 0) return;
                currentResultForCodingValues = currentResultForCodingValues.substring(0, currentResultForCodingValues.length() - 1);
                return;
            }
            if (head.getLeft() != null) {
                currentResultForCodingValues += "1";
                getValuesWhenDecompress(head.getLeft(), map);
            }
            if (head.getRight() != null) {
                currentResultForCodingValues += "0";
                getValuesWhenDecompress(head.getRight(), map);
            }
            if (currentResultForCodingValues.length() == 0) return;
            currentResultForCodingValues = currentResultForCodingValues.substring(0, currentResultForCodingValues.length() - 1);
        }
    }

    public void readFileToCompressData(String pathToFile, HashMap<Integer, String> map) {
        try {
            File file = new File(pathToFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String pathName = pathToFile.substring(0, pathToFile.length() - 4) + ".cmp";
            File newOutput = new File(pathName);
            newOutput.createNewFile();
            FileWriter writer = new FileWriter(pathName);
            int character = 0;
            String decodedValue = "";
            while ((character = bufferedReader.read()) != -1) {
                decodedValue += map.get(character);
            }
            writer.write(Integer.toString(7 - decodedValue.length() % 7));
            bytesAfterCompression++;
            for (int i = 0; i < decodedValue.length(); i = i + 7) {
                String stringToCodeFromBinaryToAscii;
                if (decodedValue.length() - i >= 7) {
                    stringToCodeFromBinaryToAscii = decodedValue.substring(i, i + 7);
                } else {
                    stringToCodeFromBinaryToAscii = decodedValue.substring(i);
                    for (int j = 0; j < 7 - decodedValue.length() % 7; j++) {
                        stringToCodeFromBinaryToAscii += "0";
                    }
                }
                writer.write((char) Integer.parseInt(stringToCodeFromBinaryToAscii, 2));
                bytesAfterCompression++;
            }
            writer.close();
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException f) {
            throw new NullPointerException();
        }
    }

    public int[] readFileToCountCharacters(String pathToFile) {
        int[] counter = new int[256];
        try {
            File file = new File(pathToFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            for (int i = 0; i < 256; i++) {
                counter[i] = 0;
            }
            int character;
            while ((character = bufferedReader.read()) != -1) {
                if (character < 0 || character > 256) {
                    throw new IllegalArgumentException("File cannot contain characters outside the ASCII table");
                }
                counter[character]++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException g) {
            throw new NullPointerException("File cannot be null");
        }
        return counter;
    }

    public int decompress(String pathToFile) {
        if (pathToFile == null) {
            throw new NullPointerException("pathToRootDir cannot be null");
        }
        int[] counter = readCounter(pathToFile);
        Node tree = buildTree(counter);
        HashMap<String, Integer> decodedValues = new HashMap<>();
        getValuesWhenDecompress(tree, decodedValues);
        String fileContent = "";
        String currentContent = "";
        String tempBinaryValue = "";
        int numberOfZerosAtTheEnd;
        boolean readingEnded = false;
        try {
            String pathName = pathToFile.substring(0, pathToFile.length() - 4);
            File file = new File(pathName + ".cmp");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            File compressedNewFile = new File(pathName + ".decomp");
            compressedNewFile.createNewFile();
            PrintWriter writer = new PrintWriter(compressedNewFile);
            int character = -2;
            numberOfZerosAtTheEnd = bufferedReader.read() - 48;
            while (character != -1) {
                for (int i = 0; i < 1000; i++) {
                    character = bufferedReader.read();
                    if (character == -1) {
                        fileContent = fileContent.substring(0, fileContent.length() - numberOfZerosAtTheEnd);
                        readingEnded = true;
                        break;
                    } else {
                        tempBinaryValue = Integer.toBinaryString(character);
                        for (int j = tempBinaryValue.length(); j < 7; j++) {
                            fileContent += "0";
                        }
                        fileContent += tempBinaryValue;
                    }
                }
                while (true) {
                    if (!readingEnded && fileContent.length() < 100) {
                        break;
                    } else if (fileContent.length() == 0) {
                        break;
                    } else {
                        for (int i = 0; i < fileContent.length(); i++) {
                            currentContent += fileContent.charAt(i);
                            if (decodedValues.containsKey(currentContent)) {
                                writer.write(decodedValues.get(currentContent));
                                bytesAfterDeCompression++;
                                currentContent = "";
                                fileContent = fileContent.substring(i + 1);
                                break;
                            }
                        }
                    }
                }
            }
            writer.close();
        } catch (NullPointerException g) {
            throw new NullPointerException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytesAfterDeCompression;
    }

    public Node linkNodes(Node node1, Node node2) {
        Node newNode = new Node(node1.getValue() + node2.getValue(), -1, node1, node2);
        return newNode;
    }

    public void writeCounterToFile(int[] counter, String pathToFile) {
        if (counter == null) {
            throw new IllegalArgumentException("Counter array cannot be null");
        }
        try {
            String pathName = pathToFile.substring(0, pathToFile.length() - 4) + ".key";
            File newFile = new File(pathName);
            newFile.createNewFile();
            PrintWriter output = new PrintWriter(pathName);
            String outputString = "";
            for (int i = 0; i < counter.length; i++) {
                if (counter[i] > 0) {
                    outputString += counter[i] + " " + i + " ";
                }
            }
            output.println(outputString);
            output.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException f) {
            throw new RuntimeException(f);
        }
    }

    public int[] readCounter(String pathToFile) {
        int[] counter = new int[256];
        try {
            File file = new File(pathToFile.substring(0, pathToFile.length() - 4) + ".key");
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < counter.length; i++) {
                counter[i] = 0;
            }
            while (scanner.hasNext()) {
                int occurrence = scanner.nextInt();
                int character = scanner.nextInt();
                counter[character] = occurrence;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException f) {
            throw new NullPointerException();
        } catch (NoSuchElementException g) {
            throw new NoSuchElementException();
        }
        return counter;
    }
}
