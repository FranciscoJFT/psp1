package com.mycompany.psp2;

import java.io.*;
import java.net.*;

public class PSP2 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Ingresar números y operación desde el cliente
            System.out.print("Ingrese el primer número: ");
            String num1 = reader.readLine();
            writer.println(num1);

            System.out.print("Ingrese el segundo número: ");
            String num2 = reader.readLine();
            writer.println(num2);

            System.out.print("Ingrese la operación (+, -, *, /): ");
            String operation = reader.readLine();
            writer.println(operation);

            // Recibir y mostrar el resultado
            BufferedReader resultReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String result = resultReader.readLine();
            System.out.println(result);

            // Cerrar la conexión
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
