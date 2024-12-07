import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Requester {

    //declare variables
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    int result;
    Scanner input;

    Requester(){

        input = new Scanner(System.in);
    }
}


