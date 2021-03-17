//package com.news.update.component;
//
//import java.util.Random;
//import java.util.Scanner;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//
//        int[][] a = new int[100][100];
//        int[][] b = new int[100][];
//        int m = 0, n = 0;
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("i=");
//        m = scanner.nextInt();
//        System.out.print("j=");
//        n = scanner.nextInt();
//        Random random = new Random();
//
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                a[i][j] = random.nextInt(100);
//                System.out.print(a[i][j] + "\t");
//            }
//            System.out.println();
//        }
//        System.out.println();
//
//        int temp;
//        for (int j = 0; j < 1; j++) {
//            for (int i = 0; i < m - 1; i++) {
//                if (a[i][j] > a[i + 1][j]) {
//                    temp = a[i][j];
//                    a[i][j] = a[i + 1][j];
//                    a[i + 1][j] = temp;
//                }
//
//                System.out.print(a[i][j] + "\t");
//            }
//            System.out.print(a[m-1][j]);
//            System.out.println();
//        }
//
//
//    }
//}
