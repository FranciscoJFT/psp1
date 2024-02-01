package com.mycompany.psp1;

import java.io.*;
import java.net.*;

public class PSP1 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor esperando conexiones en el puerto 12345...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

                // Manejar la conexión en un hilo separado
                CalculatorHandler handler = new CalculatorHandler(clientSocket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class CalculatorHandler extends Thread {
    private Socket clientSocket;

    public CalculatorHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Leer datos del cliente
            String num1 = reader.readLine();
            String num2 = reader.readLine();
            String operation = reader.readLine();

            // Realizar la operación
            double result = performOperation(Double.parseDouble(num1), Double.parseDouble(num2), operation);

            // Enviar el resultado al cliente
            writer.println("El resultado es: " + result);

            // Cerrar la conexión
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double performOperation(double num1, double num2, String operation) {
        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    System.err.println("Error: División por cero.");
                    return Double.NaN;
                }
            default:
                System.err.println("Error: Operación no válida.");
                return Double.NaN;
        }
    }
}
