package telran.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import telran.socket.task.ClientHandler;

public class ServerSocketAppl {

	public static void main(String[] args) throws InterruptedException {
		int port = 9000;
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			while (true) {
				System.out.println("Server is waiting...");
				Socket socket = serverSocket.accept();
				System.out.println("Connection established");
				System.out.println("Client host: " + socket.getInetAddress() + ":" + socket.getPort());
				ClientHandler task = new ClientHandler(socket);
				executorService.execute(task);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.MINUTES);
		}

	}

}
