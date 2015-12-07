package game.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Map {
    public static File logDir = new File("maps");

    public File fileDir;
    private int[][] map;

    public Map() {
        map = new int[BattleMap.WIDTH][BattleMap.HEIGHT];
        for (int[] ints : map) {
            for (int i = 0; i < ints.length; i++) {
                ints[i] = 112;
            }
        }
    }

    public Map(int[][] map) {
        this.map = map;
    }

    public Map(int[][] map, File file) {
        this.map = map;
        this.fileDir = file;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public static ArrayList<Map> loadMaps() {
        ArrayList<Map> maps = new ArrayList<>();
        for (File map : logDir.listFiles()) {
            maps.add(new Map(loadMap(map), map));
            System.out.println(map);
        }
        return maps;
    }

    public static int[][] loadMap(File file) {
        int[][] arr = new int[0][0];
        try {
            Scanner scanner = new Scanner(file);
            int x = -1;
            int y = -1;
            int i = 0;
            int j = -1;
            while (scanner.hasNextInt()) {
                if (i < arr.length) {
                    if (j + 1 < y) {
                        arr[i][++j] = scanner.nextInt();
                    } else {
                        arr[++i][j = 0] = scanner.nextInt();
                    }
                } else if (i != 0 && i + 1 >= arr.length) {
                    break;
                }
                if (x == -1) {
                    x = scanner.nextInt();
                } else if (y == -1) {
                    y = scanner.nextInt();
                    arr = new int[x][y];
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public boolean isSaved() {
        return fileDir != null;
    }


    public void saveMap() {
        try {
            if (fileDir.exists()) {
                fileDir.delete();
            }
            FileOutputStream out = new FileOutputStream(fileDir, false);
            out.write((map.length + " " + map[0].length).getBytes());
            for (int[] arr : map) {
                String x = "\n" + Arrays.toString(arr);
                x = x.replace(',', ' ');
                x = x.replace('[', ' ');
                x = x.replace(']', ' ');
                out.write(x.getBytes());
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMap(int[][] map) {
        try {
            int files = logDir.listFiles().length;
            File file = new File(logDir, "DnDMap" + (files + 1) + ".txt");

            FileOutputStream out = new FileOutputStream(file, false);
            out.write((map.length + " " + map[0].length).getBytes());
            for (int[] arr : map) {
                String x = "\n" + Arrays.toString(arr);
                x = x.replace(',', ' ');
                x = x.replace('[', ' ');
                x = x.replace(']', ' ');
                out.write(x.getBytes());
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
