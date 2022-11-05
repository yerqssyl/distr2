package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Processor implements Runnable{
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    public void countingOccurenceOfLetters() throws FileNotFoundException {
        System.out.println("countingOccurenceOfLetters() function runned!");
        Scanner input = new Scanner(new File("C:\\Users\\yerzh\\Downloads\\abc.txt"));
        while (input.hasNextLine()) {
            String answer = input.nextLine();
            answer = answer.toLowerCase();
            char someChar = 'a';
            int count = 0;

            for (int i = 0; i < answer.length(); i++) {
                if (answer.charAt(i) == someChar) {
                    count++;
                }
            }
//            System.out.println("a - " + count)f;
        }
    }

    public void primesUpToN(int N)
    {
//        System.out.print("primesUpToN() function runned!");
        int x, y, flg;
//        System.out.println(
//                "All the Prime numbers within 1 and " + N
//                        + " are:");
        for (x = 1; x <= N; x++) {
            if (x == 1 || x == 0)
                continue;
            flg = 1;
            for (y = 2; y <= x / 2; ++y) {
                if (x % y == 0) {
                    flg = 0;
                    break;
                }
            }
            if (flg == 1) {
//                System.out.print(x + " ");
            }
        }
    }

    public void process() throws IOException {
        System.out.println("Got request:");
        System.out.println(request.toString());
        System.out.flush();
        PrintWriter output = new PrintWriter(socket.getOutputStream());

        //increasing response time by counting occurence of letter 'a' in some txt document
        countingOccurenceOfLetters();

        //increasing response time by prime of numbers
        for (int i = 0; i < 9999; i++) {
            primesUpToN((int) (Math.random() * (999 + 1)));
        }

        //different response for different requests
        if (request.getRequestLine().toString().equals("GET /create/item1 HTTP/1.1")) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Item Creation</title></head>");
            output.println("<body><p>Item Created</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }

        //different response for different requests
        else if (request.getRequestLine().toString().equals("GET /delete/item1 HTTP/1.1")) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Item Deletion!</title></head>");
            output.println("<body><p>Item Deleted!</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }

        //different response for different requests
        else if (request.getRequestLine().toString().equals("GET /exec/item1 HTTP/1.1")) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Execution of something</title></head>");
            output.println("<body><p>Something executed!</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }
        //different response for different requests
        else {
            // We are returning a simple web page now.
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Hello</title></head>");
            output.println("<body><p>Hello, world!</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }
    }

    @Override
    public void run() {
        try {
            process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
