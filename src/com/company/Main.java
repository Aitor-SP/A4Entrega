package com.company;

import javax.crypto.SecretKey;
import javax.security.auth.DestroyFailedException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws DestroyFailedException, IOException {

        Scanner input = new Scanner(System.in);

        // 1.5 Xifrar i desxifrar un text en clar amb una clau generada amb el codi 1.1.1
        System.out.print("Introduzca el texto a cifrar: ");
        String texto = input.nextLine();
        SecretKey secretKey = Cifrar.keygenKeyGeneration(256);
        byte[] cifrar = Cifrar.encryptData(texto.getBytes(), secretKey);
        System.out.println(cifrar);
        byte[] descifrar = Cifrar.decryptData(cifrar, secretKey);
        System.out.println(new String(descifrar));

        // 1.6 Xifrar i desxifrar un text en clar amb una clau (codi 1.1.2) generada a partir de la paraula de pas
        System.out.print("Introduzca el texto a cifrar con password: ");
        String textoPassword = input.nextLine();
        System.out.print("Introduce la contraseña: ");
        String password = input.nextLine();
        SecretKey secretKeyPassword = Cifrar.passwordKeyGeneration(password,256);
        byte[] cifrarPassword = Cifrar.encryptData(textoPassword.getBytes(), secretKeyPassword);
        System.out.println(cifrarPassword);
        byte[] descifrarPassword = Cifrar.decryptData(cifrarPassword, secretKeyPassword);
        System.out.println(new String(descifrarPassword));

        // 1.7 Prova alguns dels mètodes que proporciona la classe SecretKey
        System.out.println("Metodo getAlgorithm: "+secretKey.getAlgorithm());
            // Devuelve el nombre del algoritmo usado en esa clave.

        System.out.println("Metodo getEncoded: "+secretKey.getEncoded());
            // Devuelve la clave en su formato de codificación

        System.out.println("Metodo getFormat: "+secretKey.getFormat());
            // Devuelve el nombre del formato de codificación

        // 1.8 Desxifra el text del punt 6 i comprova que donant una paraula de pas incorrecte salta l'excepció BadPaddingException
        System.out.print("Introduzca el texto a cifrar con password: ");
        String textoP = input.nextLine();
        SecretKey secretKeyP = Cifrar.passwordKeyGeneration("hola", 256);
        byte[] cifrarP = Cifrar.encryptData(textoP.getBytes(), secretKeyP);
        System.out.println(cifrarP);
        SecretKey secretKeyMal = Cifrar.passwordKeyGeneration("error",256);
        byte[] descifrarP = Cifrar.decryptData(cifrarP, secretKeyMal);
        System.out.println(new String(descifrarP));

        // 2 Donat un text xifrat (textamagat) amb algoritme estàndard AES i clau simètrica generada amb el mètode SHA-256 a partir d’una contrasenya, i donat un fitxer (clausA4.txt) on hi ha possibles contrasenyes correctes, fes un programa per trobar la bona i desxifrar el missatge.
        Path path = Paths.get("D:/Usuarios/Aitor/Downloads/Firefox/textamagat");
        byte[] archivo = Files.readAllBytes(path);

        File f = new File("D:/Usuarios/Aitor/Downloads/Firefox/clausA4.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br =  new BufferedReader(fr);

        String line = br.readLine();

        while(line != null ) {
            SecretKey pass = Cifrar.passwordKeyGeneration(line,128);
            byte[] descifrado = Cifrar.decryptData(archivo, pass);
            System.out.println("lectura: " + line +" -> " + descifrado);
            line = br.readLine();
        }
    }
}
